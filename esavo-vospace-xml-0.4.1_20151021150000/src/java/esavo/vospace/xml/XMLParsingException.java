package esavo.vospace.xml;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 *
 */
public class XMLParsingException extends VOSpaceException {

  private static final long serialVersionUID = 291325808214997278L;
  
  public XMLParsingException(String message) {
    super(message);
  }

  public XMLParsingException(Throwable t) {
    super(t);
  }

  public XMLParsingException(Throwable t, String message) {
    super(t, message);
  }  
}