package my.project.bankingsystem.bank.exception;

public class AccountClosingEx extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AccountClosingEx(String message) {
		super(message);
	}
}
