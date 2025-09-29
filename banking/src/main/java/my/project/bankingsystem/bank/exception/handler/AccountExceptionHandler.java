package my.project.bankingsystem.bank.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import my.project.bankingsystem.bank.dtos.Response;
import my.project.bankingsystem.bank.exception.AccountClosingEx;
import my.project.bankingsystem.bank.exception.ResourceConflictEx;
import my.project.bankingsystem.bank.exception.ResourceNotFoundEx;

@RestControllerAdvice
public class AccountExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundEx.class)
	public ResponseEntity<Response> handleResourceException(ResourceNotFoundEx message) {
		
		Response res = Response.builder().message(message.getMessage()).responseCode("404").build();
		
		return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceConflictEx.class)
	public ResponseEntity<Response> handleResourceException(ResourceConflictEx message) {
	
		Response res = Response.builder().message(message.getMessage()).responseCode("409").build();
		
		return new ResponseEntity<>(res, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(AccountClosingEx.class)
	public ResponseEntity<Response> handleClosingException(AccountClosingEx message) {
	
		Response res = Response.builder().message(message.getMessage()).responseCode("406").build();
		
		return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
	}
	
	
}
