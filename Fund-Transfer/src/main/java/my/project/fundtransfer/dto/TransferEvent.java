package my.project.fundtransfer.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferEvent implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String fromAccount;
	
	private String toAccount;
	
	private String transactionReference;
	
	private BigDecimal amount;
	
	private TransactionStatus status;
	
	private TransferType transferType;

}
