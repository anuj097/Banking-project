package my.project.bankingsystem.bank.service;

import java.util.List;

import my.project.bankingsystem.bank.dtos.AccountDto;
import my.project.bankingsystem.bank.dtos.AccountResponse;
import my.project.bankingsystem.bank.dtos.Response;

public interface BankingService {

	public Response createAccount(AccountDto accountDto) throws Exception;

	public AccountResponse getAccountDetails(String accountNumber);

	public Response updateAccountStatus(String accountNumber, String accountStatus);

	public Response updateAccount(String accountNumber, AccountDto accountDto);

	public String getBalance(String accountNumber);

	public Response closeAccount(String accountNumber);
}
