package esavo.vospace.exceptions;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 *
 */
public class VOSpaceException extends Exception {

  private static final long serialVersionUID = 1L;

  public VOSpaceException(String message) {
    super(message);
  }

  public VOSpaceException(Throwable t) {
    super(t);
  }

  public VOSpaceException(Throwable t, String message) {
    super(message, t);
  }
}