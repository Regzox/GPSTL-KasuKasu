package exceptions;

public class NotPermitedException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotPermitedException(String cause) {
	super(cause);
	}
}
