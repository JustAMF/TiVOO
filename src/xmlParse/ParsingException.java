package xmlParse;

public class ParsingException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParsingException(String args, Throwable cause) {
		super(args, cause);
	}
	public ParsingException(String args) {
		super(args);
	}

}
