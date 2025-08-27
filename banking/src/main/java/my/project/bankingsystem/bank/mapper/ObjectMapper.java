package my.project.bankingsystem.bank.mapper;

import my.project.bankingsystem.bank.constants.AccountConstants;
import my.project.bankingsystem.bank.dtos.AccountDto;
import my.project.bankingsystem.bank.dtos.AccountType;
import my.project.bankingsystem.bank.entity.AccountEntity;

public final class ObjectMapper {
	
	public static AccountEntity dtoToEntity(AccountDto dto) {
		AccountEntity acc = new AccountEntity();
		acc.setAccountId(dto.getAccountId());
		acc.setAccountNumber(AccountConstants.ACC_PREFIX + dto.getAccountNumber());
		acc.setAccountStatus(dto.getAccountStatus());
		acc.setAccountType(AccountType.valueOf(dto.getAccountType()));
		acc.setAvailableBalance(dto.getAvailableBalance());
		acc.setUserId(dto.getUserId());
		
		return acc;
	}
	
	public static AccountDto entityToDto(AccountEntity entity) {
		AccountDto dto = new AccountDto();
		dto.setAccountId(entity.getAccountId());
		dto.setAccountNumber(entity.getAccountNumber());
		dto.setAccountStatus(entity.getAccountStatus());
		dto.setAccountType(entity.getAccountType().toString());
		dto.setAvailableBalance(entity.getAvailableBalance());
		dto.setUserId(entity.getUserId());
		
		return dto;
	}
	
	// For update query
	/*
	 * public static AccountEntity dtoToEntityForUpdate(AccountDto dto,
	 * AccountEntity entity) { AccountEntity acc = new AccountEntity();
	 * acc.setAccountId(entity.getAccountId());
	 * acc.setAccountNumber(entity.getAccountNumber());
	 * acc.setAccountStatus(dto.getAccountStatus());
	 * acc.setAccountType(dto.getAccountType());
	 * acc.setAvailableBalance(dto.getAvailableBalance());
	 * acc.setUserId(entity.getUserId());
	 * 
	 * return acc; }
	 */

}
