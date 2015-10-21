package esavo.vospace.service.utils;

import javax.servlet.http.HttpServletRequest;

public class GenericUtils {

    /*
     * Return the list of files requested in the POST HTTP call
     * which are comma separated.
     */
    public static String[] getFiles (String fileName) {
        String[] files = fileName.split(",");
        return files;
    }
    
    /*
     * Check if the browser is IE or not
     */
    public static boolean isIEBrowser (HttpServletRequest request) {
        
        String userAgent = request.getHeader("User-Agent");
        
        // Mozilla firefox
        // Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36
        // Internet Explorer
        // Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko
        
        // Identifies if the browser is IE or not
        if (userAgent.contains("Trident/"))
            return true;
        
        //System.out.println("-------------------------------------------------------------------------" + userAgent);
        
        return false;
    }
}
