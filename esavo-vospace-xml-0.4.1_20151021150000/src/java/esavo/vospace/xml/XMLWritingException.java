package esavo.vospace.xml;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 * 
 */
public class XMLWritingException extends VOSpaceException {

  private static final long serialVersionUID = 1274714067353066657L;

  public XMLWritingException(String message) {
    super(message);
  }

  public XMLWritingException(Throwable t) {
    super(t);
  }

  public XMLWritingException(Throwable t, String message) {
    super(t, message);
  }
}