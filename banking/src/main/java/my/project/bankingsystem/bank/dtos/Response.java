package my.project.bankingsystem.bank.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String responseCode;
	
	private String message;
	
	private Object accountDetails;

}
