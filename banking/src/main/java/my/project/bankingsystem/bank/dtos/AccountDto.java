package my.project.bankingsystem.bank.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.project.bankingsystem.bank.customvalidator.ValidLength;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long accountId;

    private String accountNumber;

    private String accountType;

    private String accountStatus;

    private BigDecimal availableBalance;

    @ValidLength
    private Long userId;
}
