package exceptions;

public class DatabaseException extends Exception {

	private static final long serialVersionUID = -4420127161660768417L;

	public DatabaseException(String msg) {
		super("<DatabaseException> " + msg);
	}
	
}
