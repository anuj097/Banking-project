package my.project.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import my.project.fundtransfer.dto.FundTransferRequest;
import my.project.fundtransfer.dto.FundTransferResponse;
import my.project.fundtransfer.entity.FundTransferEntity;
import my.project.fundtransfer.service.FundTransferService;

@RestController
@RequestMapping("/fund-transfer")
@Slf4j
public class FundTransferController {
	
	@Autowired
	private FundTransferService service;

	@PostMapping("/tansfer")
	@CircuitBreaker(name = "account_Details_Fetch", fallbackMethod = "fallbackMethod")
	public ResponseEntity<FundTransferResponse> fundTransfer(@RequestBody FundTransferRequest request) {
		
		return new ResponseEntity<> (service.fundTransfer(request), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getDetails/{id}")
	public ResponseEntity<FundTransferEntity> getDetails(@PathVariable Long id) {
		
		 FundTransferEntity ent = service.getDetails(id);
		 
		 return new ResponseEntity<>(ent, HttpStatus.FOUND);
	}
	
	private ResponseEntity<FundTransferResponse> fallbackMethod(FundTransferRequest request, Exception ex) {
		log.info("fundTransfer is not able to fetch the data at this moment!!!", ex);
		FundTransferResponse fdr = FundTransferResponse.builder()
								.message("Unable to Transfer the fund at this moment!!")
								.transactionId("ffa3b7-197b-9e13d28d88") // default id
								.build();
		return new ResponseEntity<> (fdr, HttpStatus.OK);
	}

}
