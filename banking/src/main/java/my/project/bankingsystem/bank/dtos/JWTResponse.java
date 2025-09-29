package my.project.bankingsystem.bank.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTResponse {
	private String jwtToken;
	private String username;
}
