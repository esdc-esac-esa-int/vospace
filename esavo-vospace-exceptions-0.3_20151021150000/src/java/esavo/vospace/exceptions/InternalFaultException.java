/**
 * 
 */
package esavo.vospace.exceptions;


/**
 * InternalFaultException if an internal error occurs.
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class InternalFaultException extends VOSpaceException {

	/** Serial ID */
	private static final long serialVersionUID = -5117945364223683628L;
	
	public static final int code_500 = 500;

	public InternalFaultException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InternalFaultException(Throwable t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	public InternalFaultException(Throwable t, String message) {
		super(t, message);
		// TODO Auto-generated constructor stub
	}
}
