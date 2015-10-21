/**
 * 
 */
package esavo.vospace.exceptions.database;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * NodeNotFoundException if the node was not found in VOSpace
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class NodeNotFoundException extends VOSpaceException {

	/** Serial ID */
	private static final long serialVersionUID = -1879288381630437397L;
	
	public static final int code_404 = 404;

	public NodeNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NodeNotFoundException(Throwable t) {
		super(t);
	}

	public NodeNotFoundException(Throwable t, String message) {
		super(t, message);
	}

}
