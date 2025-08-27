package my.project.fundtransfer.service;

import my.project.fundtransfer.dto.FundTransferRequest;
import my.project.fundtransfer.dto.FundTransferResponse;
import my.project.fundtransfer.entity.FundTransferEntity;

public interface FundTransferService {
	
	public FundTransferResponse fundTransfer(FundTransferRequest request);
	
	public FundTransferEntity getDetails(Long id);

}
