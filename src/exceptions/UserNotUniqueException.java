package exceptions;

public class UserNotUniqueException extends DatabaseException {

	private static final long serialVersionUID = 972511493485534148L;

	public UserNotUniqueException(String msg) {
		super(msg);
	}
	
	public UserNotUniqueException() {
		super("User not unique");
	}
	
}
