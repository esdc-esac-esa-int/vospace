package esavo.vospace.exceptions.database;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * ContainerNotFoundException if the Container node does not exist.
 * @author Sara Nieto Copyright (c) 2013- European Space Agency
 *
 */
public class ContainerNotFoundException extends VOSpaceException {

	/** Serial ID */
	private static final long serialVersionUID = -899376179252023395L;

	public static final int code_404 = 404;
	
	public ContainerNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ContainerNotFoundException(Throwable t) {
		super(t);
	}

	public ContainerNotFoundException(Throwable t, String message) {
		super(t, message);
	}
}
