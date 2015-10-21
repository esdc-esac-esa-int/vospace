package esavo.vospace.exceptions;


/**
 * InvalidURIException if the URI is not valid.
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class InvalidURIException extends VOSpaceException {

	/** Serial ID */
	private static final long serialVersionUID = -5173557171149504706L;
	
	public static final int code_400 = 400;
	
	public InvalidURIException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidURIException(Throwable t) {
		super(t);
	}

	public InvalidURIException(Throwable t, String message) {
		super(t, message);
	}
}
