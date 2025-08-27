package my.project.fundtransfer.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import my.project.fundtransfer.Exception.AccountStatusEx;
import my.project.fundtransfer.Exception.InsufficientBalanceEx;
import my.project.fundtransfer.Exception.ResourceNotFoundEx;
import my.project.fundtransfer.dto.Response;

@RestControllerAdvice
public class FundTransferHandler {
	
	@ExceptionHandler(ResourceNotFoundEx.class)
	public ResponseEntity<Response> handleResourceException(ResourceNotFoundEx message) {
	
		Response res = Response.builder().responseCode("404").message(message.getMessage()).build();
		
		return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InsufficientBalanceEx.class)
	public ResponseEntity<Response> handleBalanceException(InsufficientBalanceEx message) {
	
		Response res = Response.builder().responseCode("400").message(message.getMessage()).build();
		
		return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(AccountStatusEx.class)
	public ResponseEntity<Response> handleAccountException(AccountStatusEx message) {
	
		Response res = Response.builder().responseCode("404").message(message.getMessage()).build();
		
		return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}

}
