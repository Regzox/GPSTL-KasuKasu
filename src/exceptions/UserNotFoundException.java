package exceptions;

public class UserNotFoundException extends DatabaseException {

	private static final long serialVersionUID = 79704483614180802L;
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
	
	public UserNotFoundException() {
		super("User not find");
	}

}
