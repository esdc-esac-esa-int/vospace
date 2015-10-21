package esavo.vospace.xml;

import org.jdom2.Namespace;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 *
 */
public enum VOSpaceNS {

  VOSPACE("vos","http://www.ivoa.net/xml/VOSpace/v2.0"),
  VOSPACE_TYPE("vost","http//www.ivoa.net/xml/VOSpaceType-v2.0"),
  SCHEMA_INSTANCE("xsi","http://www.w3.org/2001/XMLSchema-instance"),
  UWS("uws","http://www.ivoa.net/xml/UWS/v1.0");
  
  private String prefix, uri;
  
  private VOSpaceNS(String prefix, String uri) {
    this.prefix = prefix;
    this.uri=uri;
  }
  
  public Namespace getNamespace() {
    return Namespace.getNamespace(this.prefix, this.uri);
  }

  @Override
  public String toString() {
    String s = "";
    if(this.prefix != null) {
      s+=this.prefix;
    }
    if(this.uri != null) {
      s+="xmlns:" + this.prefix + "=\"" + this.uri + "\"";
    }
    return s;
  }
}
