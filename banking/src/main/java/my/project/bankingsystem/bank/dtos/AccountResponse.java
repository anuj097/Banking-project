package my.project.bankingsystem.bank.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long accountId;

    private String accountNumber;

    private AccountType accountType;

    private String accountStatus;

    private BigDecimal availableBalance;
    
    private LocalDate date;

    private Long userId;
}
