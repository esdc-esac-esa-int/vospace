package esavo.vospace.xml;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 *
 */
public class InvalidTransferException extends VOSpaceException {

  private static final long serialVersionUID = -5959437805105061277L;

  public InvalidTransferException(String message) {
    super(message);
  }

  public InvalidTransferException(Throwable t) {
    super(t);
  }

  public InvalidTransferException(Throwable t, String message) {
    super(t, message);
  }    
}
