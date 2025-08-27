package my.project.fundtransfer.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundTransferResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String transactionId;
	
	private String message;

}
