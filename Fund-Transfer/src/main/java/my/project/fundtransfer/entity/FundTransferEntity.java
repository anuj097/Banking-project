package my.project.fundtransfer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.project.fundtransfer.dto.TransactionStatus;
import my.project.fundtransfer.dto.TransferType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fund_transfer")
public class FundTransferEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fundTransferId")
    private Long fundTransferId;

	@Column(name = "transactionReference")
    private String transactionReference;

	@Column(name = "fromAccount")
    private String fromAccount;

	@Column(name = "toAccount")
    private String toAccount;

	@Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "transferType")
    private TransferType transferType;

    @CreationTimestamp
    @Column(name = "transferredOn")
    private LocalDateTime transferredOn;

}
