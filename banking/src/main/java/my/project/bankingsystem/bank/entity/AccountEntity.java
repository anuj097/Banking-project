package my.project.bankingsystem.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.project.bankingsystem.bank.dtos.AccountType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accountId")
	private Long accountId;

	@Column(name = "accountNumber")
    private String accountNumber;

	@Column(name = "accountType")
	@Enumerated(EnumType.STRING)
    private AccountType accountType;

	@Column(name = "accountStatus")
    private String accountStatus;
	
	@CreationTimestamp
	private LocalDate dateAndTime;

	@Column(name = "availableBalance")
    private BigDecimal availableBalance;

	@Column(name = "userId")
    private Long userId;
}
