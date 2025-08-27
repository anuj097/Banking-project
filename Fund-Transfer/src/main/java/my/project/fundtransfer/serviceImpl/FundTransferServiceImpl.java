package my.project.fundtransfer.serviceImpl;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.project.fundtransfer.Exception.AccountStatusEx;
import my.project.fundtransfer.Exception.InsufficientBalanceEx;
import my.project.fundtransfer.Exception.ResourceNotFoundEx;
import my.project.fundtransfer.dto.AccountResponse;
import my.project.fundtransfer.dto.FundTransferRequest;
import my.project.fundtransfer.dto.FundTransferResponse;
import my.project.fundtransfer.dto.TransactionStatus;
import my.project.fundtransfer.dto.TransferEvent;
import my.project.fundtransfer.dto.TransferType;
import my.project.fundtransfer.entity.FundTransferEntity;
import my.project.fundtransfer.externalService.AccountService;
import my.project.fundtransfer.externalService.BalanceTransferProducer;
import my.project.fundtransfer.repository.FundTransferRepo;
import my.project.fundtransfer.service.FundTransferService;

@Slf4j
@Service
public class FundTransferServiceImpl implements FundTransferService {

	@Autowired
	private AccountService accountService;
	@Autowired
	private FundTransferRepo fundRepo;
	@Autowired
	private BalanceTransferProducer producer;
	
	@Override
	public FundTransferResponse fundTransfer(FundTransferRequest request) {
		log.info("FundTransfer service Started!!!");
		
		AccountResponse fromAccount;
		
		ResponseEntity<AccountResponse> fromResponse = accountService.getAccountDetails(request.getFromAccount());
		
		if(Objects.isNull(fromResponse.getBody())) {
			log.error("Requested account "+ request.getFromAccount() +" is not found on the server");
			throw new ResourceNotFoundEx("Requested Account not found on the server");
		}
		
		fromAccount = fromResponse.getBody();
		
		if(!fromAccount.getAccountStatus().equalsIgnoreCase("ACTIVE")) {
			log.error("The Requested account "+ request.getFromAccount() +" is not active");
			throw new AccountStatusEx("Account is not Active!!!");
		}
		
		log.info("fromAccount is fetched, toAccount is in progress!!!");
		
		AccountResponse toAccount;
		
		ResponseEntity<AccountResponse> toResponse = accountService.getAccountDetails(request.getToAccount());
		
		if(Objects.isNull(toResponse.getBody())) {
			log.error("Requested account "+ request.getToAccount() +" is not found on the server");
			throw new ResourceNotFoundEx("Requested Account not found on the server");
		}
		
		toAccount = toResponse.getBody();
		
		if(!toAccount.getAccountStatus().equalsIgnoreCase("ACTIVE")) {
			log.error("The Requested account "+ request.getToAccount() +" is not active");
			throw new AccountStatusEx("Account is not Active!!!");
		}
		
		if(fromAccount.getAvailableBalance().compareTo(request.getAmount())<0) {
			log.error("Required Amount is not available");
			throw new InsufficientBalanceEx("Required Amount is not available!!!");
		}
		
		String transectionId = InternalTransfer(fromAccount, toAccount, request.getAmount());

		//save data to database
		FundTransferEntity entity = FundTransferEntity.builder()
									.amount(request.getAmount())
									.fromAccount(fromAccount.getAccountNumber())
									.toAccount(toAccount.getAccountNumber())
									.transactionReference(transectionId)
									.status(TransactionStatus.SUCCESS)
									.transferType(TransferType.INTERNAL)
									.build();
		FundTransferEntity e =  fundRepo.save(entity);
		
		log.info("Entity details are( id: "+ e.getFundTransferId() +" from "+e.getFromAccount()
				+" account to "+ e.getToAccount()+"account.");
		
		//send message to kafka topic 
		TransferEvent event = new TransferEvent();
		BeanUtils.copyProperties(entity, event);
		/* TransferEvent event = TransferEvent.builder()
								.amount(request.getAmount())
								.fromAccount(fromAccount.getAccountNumber())
								.toAccount(toAccount.getAccountNumber())
								.transactionId(transectionId)
								.status(TransactionStatus.SUCCESS)
								.transferType(TransferType.INTERNAL)
								.build(); */
		producer.sendMessage(event);
		
		log.info("FundTransfer service ended!!!");
		return FundTransferResponse.builder().transactionId(transectionId).message("Transection is succesful").build();
	}

	private String InternalTransfer(AccountResponse fromAccount, AccountResponse toAccount, BigDecimal amount) {
		
		log.info("InternalTransfer service started!!");
		fromAccount.setAvailableBalance(fromAccount.getAvailableBalance().subtract(amount));
		accountService.updateAccount(fromAccount.getAccountNumber(), fromAccount);
		
		toAccount.setAvailableBalance(toAccount.getAvailableBalance().add(amount));
		accountService.updateAccount(toAccount.getAccountNumber(), toAccount);
		
		String transectionReference = UUID.randomUUID().toString();

		log.info("InternalTransfer service ended!!");
		return transectionReference;
	}

	@Override
	public FundTransferEntity getDetails(Long fundTransferId) {
		
		FundTransferEntity ent =  fundRepo.findById(fundTransferId).get();
		
		return ent;
		
	}

}
