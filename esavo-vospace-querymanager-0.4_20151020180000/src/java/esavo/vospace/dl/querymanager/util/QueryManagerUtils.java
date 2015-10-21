package esavo.vospace.dl.querymanager.util;

import java.io.File;

import org.apache.log4j.Logger;

import esavo.vospace.common.model.transferobjects.VOSpaceURI;

/** Convenience class. */
public class QueryManagerUtils {

    /** Logger. */
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(QueryManagerUtils.class);
    
    protected final static String VOS_PREFIX = Constants.VOS_PREFIX;

    /**
     * Default constructor, it should not be called.
     */
    protected QueryManagerUtils() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }
    
    /**
     * Add Vospace prefix to path
     * @param path ( without bar at the beginning)
     * @return VospaceURI
     */
    public static VOSpaceURI composeURIFromPath (final String path) {
        VOSpaceURI uri = null;
        if (path.isEmpty()) {
            uri = new VOSpaceURI(VOS_PREFIX);
        } else 
            uri = new VOSpaceURI(VOS_PREFIX + File.separator + path);
        
        return uri;
    }
    
    /**
     * Retrieve the path from the given URI.
     * @param uri
     * @return
     */
    public static String getPath (VOSpaceURI uri) {
        String path = "";
        if (uri != null) {
            String pathWithbar = uri.getPath();
            if (!pathWithbar.isEmpty()  && pathWithbar.length() > 1) {
                path = uri.getPath().substring(1); //without bar
            }
        }
        return path;
    }
    
    /**
     * Retrieve the path of the parent uri.
     * @param uri
     * @return parent path
     */
    public static String getParentPath (VOSpaceURI uri) {
        String parentPath = "";
        if (uri != null) {
            String parentWithbar = uri.getParent();
            if (!parentWithbar.isEmpty() && parentWithbar.length() > 1) {
                parentPath = uri.getParent().substring(1); //without bar
            }
        }
        return parentPath;
    }
}
