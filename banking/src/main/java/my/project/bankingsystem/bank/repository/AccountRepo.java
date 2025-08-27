package my.project.bankingsystem.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.project.bankingsystem.bank.dtos.AccountType;
import my.project.bankingsystem.bank.entity.AccountEntity;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
	
	public Optional<AccountEntity> findByUserIdAndAccountType(Long userId, AccountType accountType);

	public AccountEntity findByAccountNumber(String accountNumber);
	
	public AccountEntity findByUserId(Long UserId);
	
	public AccountEntity findTopByOrderByAccountIdDesc();
}
