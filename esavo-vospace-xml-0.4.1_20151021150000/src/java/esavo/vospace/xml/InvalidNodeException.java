package esavo.vospace.xml;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 *
 */
public class InvalidNodeException extends VOSpaceException {

  private static final long serialVersionUID = -5959437805105061277L;

  public InvalidNodeException(String message) {
    super(message);
  }

  public InvalidNodeException(Throwable t) {
    super(t);
  }

  public InvalidNodeException(Throwable t, String message) {
    super(t, message);
  }    
}
