package esavo.vospace.exceptions;


/**
 * TypeNotSupportedException if the given type for a Node
 * is not supported in VOSpace.
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class TypeNotSupportedException extends VOSpaceException {

	/** Serial ID */
	private static final long serialVersionUID = -650135765665410781L;

	public static final int code_500 = 500;
	
	public TypeNotSupportedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public TypeNotSupportedException(Throwable t) {
		super(t);
	}

	public TypeNotSupportedException(Throwable t, String message) {
		super(t, message);
	}
}
