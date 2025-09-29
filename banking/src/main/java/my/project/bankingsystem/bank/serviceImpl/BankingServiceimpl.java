package my.project.bankingsystem.bank.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import my.project.bankingsystem.bank.constants.AccountConstants;
import my.project.bankingsystem.bank.dtos.AccountDto;
import my.project.bankingsystem.bank.dtos.AccountResponse;
import my.project.bankingsystem.bank.dtos.AccountType;
import my.project.bankingsystem.bank.dtos.Response;
import my.project.bankingsystem.bank.entity.AccountEntity;
import my.project.bankingsystem.bank.exception.AccountClosingEx;
import my.project.bankingsystem.bank.exception.ResourceConflictEx;
import my.project.bankingsystem.bank.exception.ResourceNotFoundEx;
import my.project.bankingsystem.bank.mapper.ObjectMapper;
import my.project.bankingsystem.bank.repository.AccountRepo;
import my.project.bankingsystem.bank.service.BankingService;

@Slf4j
@Service
public class BankingServiceimpl implements BankingService {
	
	
	//Logger log = LoggerFactory.getLogger(BankingServiceimpl.class);  <NOT NEEDED IF WE'RE USING @slf4j ANNOTATION>
	
	@Autowired
	private AccountRepo accountRepo;

	//@SuppressWarnings("unchecked")
	@Override
	public Response createAccount(AccountDto accountDto) {
		
		log.info("createAccount service started!!!");
		try {
			//validate input
			if (accountDto.getUserId() == null)
				throw new ResourceNotFoundEx("The account id is unavailable!!");

			// check for account exist
			/*
			 * Optional<?> accountExist =
			 * accountRepo.findByUserIdAndAccountType(accountDto.getUserId(),
			 * AccountType.valueOf(accountDto.getAccountType()));
			 */
			
			
			AccountEntity accountExist = accountRepo.findByUserId(accountDto.getUserId());

			if(accountExist!=null)
				throw new ResourceConflictEx("Account is already exist (Have you updated the user ID???)");
			
			AccountEntity lastRow = accountRepo.findTopByOrderByAccountIdDesc();

			//save the data to db
			AccountEntity ent = new AccountEntity();
			Long accountNumber;
			if(lastRow!=null) {
				accountNumber = Integer.parseInt(lastRow.getAccountNumber()) + 1L;
			} else {
				accountNumber = 1_000_00L + (long)(Math.random() * 8_999_99L);
			}
			log.info("Account Number generated: "+ accountNumber);
			ent.setAccountStatus(accountDto.getAccountStatus());
			if(lastRow!=null)
				ent.setAccountNumber(accountNumber.toString());
			else
				ent.setAccountNumber(AccountConstants.ACC_PREFIX + accountNumber);
			ent.setAccountType(AccountType.valueOf(accountDto.getAccountType()));
			ent.setAvailableBalance(accountDto.getAvailableBalance());
			ent.setUserId(accountDto.getUserId());
			AccountEntity createdAccount = accountRepo.save(ent);
			
			AccountResponse res = new AccountResponse();
			BeanUtils.copyProperties(createdAccount, res);
			log.info("createAccount service ended!!!");
			return Response.builder().accountDetails(res).responseCode("Success")
							.message("Account Created succesfully (PLEASE NOTE THE ACCOUNT NUMBER SOMEWHERE TO REMEMBER)").build();
		} catch (ResourceNotFoundEx | ResourceConflictEx e) {
			log.error("Validation error: {}", e.getMessage());
			return Response.builder().responseCode("400").message(e.getMessage()).build();
		} catch (Exception e) {
			log.error("Unexpected error creating account: {}", e.getMessage());
			return Response.builder().responseCode("500").message("Failed to create account!!").build();
		}
	}

	@Override
	@Cacheable(value = "accounts")
	public AccountResponse getAccountDetails(String accountNumber) {
		
		log.info("getAccountDetails service started!!!");
		AccountEntity entity = accountRepo.findByAccountNumber(accountNumber);
		if(entity==null) {
			throw new ResourceNotFoundEx("The Account number is unavailable!!");
		}
		AccountResponse res = new AccountResponse();
		BeanUtils.copyProperties(entity, res);
		log.info("getAccountDetails service ended!!!");
		return res;
				
	}

	@Override
	@CacheEvict(value = "accounts", key = "#accountNumber", allEntries = true)
	public Response updateAccountStatus(String accountNumber, String accountStatus) {
		
		log.info("updateAccountStatus service started!!!");
		
		AccountEntity entity =  accountRepo.findByAccountNumber(accountNumber);
		
		if(entity == null) {
			throw new ResourceNotFoundEx("The account number is unavailable!!"); 
		} else {
			if(entity.getAccountStatus().equalsIgnoreCase(accountStatus))
				throw new ResourceConflictEx("The AccountStatus can't be updated!!");
			if(entity.getAvailableBalance().compareTo(BigDecimal.ZERO)!=0) {
				throw new AccountClosingEx("The Account Balance should be Zero before closing the Account!!");
			}
			else if(entity.getAccountStatus().equalsIgnoreCase("CLOSED"))
				throw new AccountClosingEx("The Account is already Closed!!");
			else
				entity.setAccountStatus(accountStatus);
		}
		
		accountRepo.save(entity);
		
		log.info("updateAccountStatus service ended!!!");
		
		return Response.builder().message("Account Status is updated!!!").responseCode("200").build();
	}

	@Override
	@CacheEvict(value = "accounts", key = "#accountDto.accountNumber + '-' + #accountDto.userId" , allEntries = true)
	public Response updateAccount(String accountNumber, AccountDto accountDto) {

		log.info("updateAccount serivce started!!!");
		
		AccountEntity entity =  accountRepo.findByAccountNumber(accountNumber);
		
		AccountEntity userAccount = new AccountEntity();
		
		if(entity == null) {
			throw new ResourceNotFoundEx("The account number is unavailable!!"); 
		} else {
			if(entity.getAccountStatus().equalsIgnoreCase("CLOSED")) {
				throw new AccountClosingEx("The Account is already closed!!");
			} else {
				BeanUtils.copyProperties(accountDto, userAccount);   //inbuilt method of Java (to convert any Object to another having similar data)
				userAccount.setAccountId(entity.getAccountId());
				userAccount.setAccountType(entity.getAccountType());
				userAccount.setAccountNumber(entity.getAccountNumber());
				userAccount.setUserId(entity.getUserId());
			}
		}
		
		accountRepo.save(userAccount);
		
		log.info("updateAccount service ended!!!");
		
		return Response.builder().responseCode("200").message("Account details are updated.").build();
	}

	@Override
	public String getBalance(String accountNumber) {

		log.info("getBalance serivce started!!!");
		AccountEntity entity =  accountRepo.findByAccountNumber(accountNumber);

		String balance = "";
		
		if(entity == null) {
			log.error("The account is unavailable");
			throw new ResourceNotFoundEx("The account number is unavailable!!"); 
		} else {
			balance = entity.getAvailableBalance().toString();
		}
		log.info("getBalance serivce ended!!!");
		return balance;
	}

	@Override
	@CacheEvict(value = "accounts", key = "#accountNumber", allEntries = true)
	public Response closeAccount(String accountNumber) {

		log.info("closeAccount serivce started!!!");
		
		AccountEntity ent = accountRepo.findByAccountNumber(accountNumber);
		
		if(ent == null) {
			log.error("The account is unavailable");
			throw new ResourceNotFoundEx("The account number is unavailable!!");
		} else {
			if(ent.getAvailableBalance().compareTo(BigDecimal.ZERO)!=0) {
				throw new AccountClosingEx("The account balance should be zero before closing!!");
			} else {
				ent.setAccountStatus("CLOSED");
				accountRepo.save(ent);
			}
		}
		log.info("closeAccount serivce ended!!!");
		return Response.builder().responseCode("200").message("Account is closed successfully!!").build();
	}

	@Override
	public List<AccountDto> getTop3Accounts() {
		List<AccountEntity> listEntity = accountRepo.findByAvailableBalance(); 
		List<AccountDto> listDto = listEntity.stream().map(ObjectMapper::entityToDto).toList();
		return listDto;
	}
	
	

}
