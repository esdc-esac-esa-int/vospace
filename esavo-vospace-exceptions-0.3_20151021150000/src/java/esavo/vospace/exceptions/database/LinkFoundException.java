package esavo.vospace.exceptions.database;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * LinkFoundException if a link node is found into an URI
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class LinkFoundException extends VOSpaceException {

	/** Serial ID */
	private static final long serialVersionUID = -8475953125042941031L;

	public static final int code_404 = 404;
	
	public LinkFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LinkFoundException(Throwable t) {
		super(t);
	}

	public LinkFoundException(Throwable t, String message) {
		super(t, message);
	}
}
