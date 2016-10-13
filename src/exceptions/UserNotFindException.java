package exceptions;

public class UserNotFindException extends DatabaseException {

	private static final long serialVersionUID = 79704483614180802L;
	
	public UserNotFindException(String msg) {
		super(msg);
	}
	
	public UserNotFindException() {
		super("User not find");
	}

}
