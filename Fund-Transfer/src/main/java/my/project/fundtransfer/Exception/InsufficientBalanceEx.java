package my.project.fundtransfer.Exception;

public class InsufficientBalanceEx extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InsufficientBalanceEx(String message) {
		super(message);
	}

}
