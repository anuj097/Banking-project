package my.project.fundtransfer.Exception;

public class ResourceNotFoundEx extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundEx(String message) {
		super(message);
	}

}
