package esavo.vospace.exceptions.database;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * DuplicateNodeException if the node is already in VOSpace
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class DuplicateNodeException extends VOSpaceException {

	/** Serial ID */
	private static final long serialVersionUID = -8045129192033792181L;

	public static final int code_409 = 409;
	
	public DuplicateNodeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DuplicateNodeException(Throwable t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	public DuplicateNodeException(Throwable t, String message) {
		super(t, message);
		// TODO Auto-generated constructor stub
	}

}
