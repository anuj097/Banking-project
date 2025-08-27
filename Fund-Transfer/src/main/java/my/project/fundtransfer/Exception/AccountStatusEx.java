package my.project.fundtransfer.Exception;

public class AccountStatusEx extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AccountStatusEx(String message) {
		super(message);
	}

}
