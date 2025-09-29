package my.project.bankingsystem.bank.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import my.project.bankingsystem.bank.dtos.AccountDto;
import my.project.bankingsystem.bank.dtos.AccountResponse;
import my.project.bankingsystem.bank.dtos.Response;
import my.project.bankingsystem.bank.service.BankingService;


@RestController
@RequestMapping("/accounts")
@Slf4j
public class BankingController {
	
	@Autowired
	private BankingService bankService;
	
	/*
	 * This is to create a Bank account
	 */
	
	@PostMapping("/create")
	public ResponseEntity<Response> createAccount(@RequestBody AccountDto accountDto) {
		
		try {
	        Response res = bankService.createAccount(accountDto);
	        return new ResponseEntity<>(res, HttpStatus.CREATED);
	    } catch (Exception e) {
	        log.error("Error creating account: {}", e.getMessage());
	        return new ResponseEntity<>(
	            null,
	            HttpStatus.INTERNAL_SERVER_ERROR
	        );
	    }
	}
	
	/*
	 * This is to fetch all the accounts available in the bank 
	 */
	
	@GetMapping("/accountDetail")
	public ResponseEntity<AccountResponse> getAccountDetails(@RequestParam String accountNumber) {
		AccountResponse response = bankService.getAccountDetails(accountNumber);
	    
	    if (response == null) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	}
	
	/*
	 * This is to update the account status
	 */
	
	@PatchMapping("/updateStatus")
	public ResponseEntity<Response> updateAccountStatus(@RequestParam String accountNumber , @RequestParam String accountStatus) {
		
		Response res = bankService.updateAccountStatus(accountNumber, accountStatus);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/*
	 * This is to update any of the details of account - PREFER IF multiple fields need to update
	 */
	
	@PutMapping("/update")
	public ResponseEntity<Response> updateAccount(@RequestParam String accountNumber, @RequestBody AccountDto accountDto) {
		Response res = bankService.updateAccount(accountNumber, accountDto);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/*
	 * This is to check the account balance
	 */
	
	@GetMapping("/accountBalance")
	public ResponseEntity<String> accountBalance(@RequestParam String accountNumber) {
		String balance = bankService.getBalance(accountNumber);
		
		return new ResponseEntity<>(balance, HttpStatus.OK);
	}
	
	@PutMapping("/closure")
	public ResponseEntity<Response> closeAccount(@RequestParam String accountNumber) {
		Response res = bankService.closeAccount(accountNumber);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/top3Account")
	public ResponseEntity<List<AccountDto>> getTop3Account(@RequestParam String by) {
		List<AccountDto> listDto = new ArrayList<>();
		if(by.equals("availableBalance")) {
			listDto = bankService.getTop3Accounts();
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		if(listDto!=null) {
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	

}
