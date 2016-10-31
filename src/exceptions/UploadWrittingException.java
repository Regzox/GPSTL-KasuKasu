package exceptions;

public class UploadWrittingException extends Exception {

	private static final long serialVersionUID = -6043953023087107829L;

	public UploadWrittingException() {
		super("Writting uploaded file fails");
	}
	
}
