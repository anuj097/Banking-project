package my.project.fundtransfer.externalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import my.project.fundtransfer.configuration.FeignClientConfig;
import my.project.fundtransfer.dto.AccountResponse;
import my.project.fundtransfer.dto.Response;


/*
 * 
 * 		THIS INTERFACE IS TO CONNECT WITH THE BANKING APPLICATION THROUGH FEIGN CLIENT 
 * 
 */

//@FeignClient(name = "Account-service", url = "http://localhost:9494/accounts", configuration = FeignClientConfig.class)
@FeignClient(name = "Account-service", url = "http://localhost:9090/accounts", configuration = FeignClientConfig.class)
public interface AccountService {

	@GetMapping("/accountDetail")
	public ResponseEntity<AccountResponse> getAccountDetails(@RequestParam String accountNumber);
	
	@PutMapping("/update")
	public ResponseEntity<Response> updateAccount(@RequestParam String accountNumber, @RequestBody AccountResponse accountDto);
}
