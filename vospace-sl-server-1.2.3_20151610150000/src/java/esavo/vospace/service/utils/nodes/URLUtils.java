package esavo.vospace.service.utils.nodes;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import esavo.vospace.exceptions.InvalidURIException;

public class URLUtils {    
    
    public static boolean verifyNodesRequest(String urlToCheck) throws InvalidURIException {
        
        URL url = null;
        try {
            url = new URL(urlToCheck);
        } catch (MalformedURLException e) {
            throw new InvalidURIException("Execute RestRequestUtils."
                    + "verifyNodesRequest() " + e.getMessage());
        }
        
        try {
            url.toURI();
        } catch (URISyntaxException e) {
            return false;
        }
        
        return true;
      }
}
