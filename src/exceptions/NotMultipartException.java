package exceptions;

public class NotMultipartException extends Exception {

	private static final long serialVersionUID = 2345043758721715605L; 
	
	public NotMultipartException() {
		super("No multipart content");
	}

}
