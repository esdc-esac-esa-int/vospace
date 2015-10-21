package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 * 
 */
public class VOSpaceURI implements Serializable {

    private static final long serialVersionUID = 1660857685206570599L;
    
    private static final String URI_SCHEME = "vos";
    private URI uri;

  public VOSpaceURI(String s) throws IllegalArgumentException {
    try {
      this.uri = new URI(s);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }
  
  public VOSpaceURI(URI uri) throws IllegalArgumentException {    
    if(!URI_SCHEME.equals(uri.getScheme())) {
      throw new IllegalArgumentException("URI scheme must be " + URI_SCHEME); 
    }
    try {
      this.uri = new URI(URI_SCHEME, uri.getAuthority(), uri.getPath(),
          uri.getQuery(), uri.getFragment());
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Malformed URI " + uri.toString());
    }
  }
  
  public VOSpaceURI(String authority, String path) throws URISyntaxException {
    this(authority, path, null, null);
  }

  public VOSpaceURI(String authority, String path, String query, String fragment)
      throws URISyntaxException {
    this.uri = new URI(URI_SCHEME, authority, path, query, fragment);
  }

  public String getAuthority() {
    return this.uri.getAuthority();
  }

  public String getFragment() {
    return this.uri.getFragment();
  }

  public String getName() {
    String name = "";
    if(hasParent()) {
      String[] nodes = getPath().split("/");
      name = nodes[nodes.length - 1]; // last path component
    }
    return name;
  }  
  
  public String getParent() {
    String parent = null;
    if (hasParent()) {
      String path = getPath();
      parent = path.substring(0, path.lastIndexOf('/'));
    }
    return parent;
  }
  
  public VOSpaceURI getParentURI() throws URISyntaxException {
    VOSpaceURI parentURI = null;
    if (hasParent()) {
      parentURI = new VOSpaceURI(uri.getAuthority(), getParent());
    }
    return parentURI;
  }  

  public String getPath() {
    return this.uri.getPath();
  }

  public String getQuery() {
    return this.uri.getQuery();
  }

  public String getScheme() {
    return URI_SCHEME;
  }

  public URI getURI() {
    return this.uri;
  }

  @Override
  public String toString() {
    if(this.uri == null) {
      return null; 
    } else {
      return this.uri.toString();
    }
  }

  public String getURIPrefix() {
    return getScheme() + "://" + getAuthority();
  }
  
  public boolean hasParent() {
    String path = getPath();
    if (path == null || path.length() == 0 || path.equals("/")) {
      return false;
    }
    return true;
  }  
}

