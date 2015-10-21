package esavo.vospace.service.utils.nodes;

import esavo.vospace.service.utils.VOSConstants;

/**
 * URLUtils provides a set of utilities to retrieve certain information on a given uri.  
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class VospaceURIUtils {
    
    /**
     * Return true if the uri is the VOSpace root vos://esavo!vospace
     * @param uri - uri to be checked
     * @return true if the uri is the VOSpace root, otherwise false. 
     */
    public static boolean isVOSpaceRoot (String uri) {
        boolean isRoot = false;
        
        // vos://esavo!vospace
        if (VOSConstants.VOS_PREFIX.compareTo(uri) == 0)
            isRoot = true;
        
        return isRoot;
    }

    /**
     * Return true if the uri is the root of a user account.
     * @param uri - uri to be checked
     * @return true if the uri is the root of the user account,
     * otherwise false.
     */
    public static boolean isUserRoot (String uri) {
        boolean isRoot = false;
        String userRoot = getUserRoot(uri);
        if (!userRoot.isEmpty())
        {
            String path = getUserPath(uri);
            if (path.isEmpty()) {
                isRoot = true;
            }
        }
        return isRoot;
    }
    
    /**
     * Retrieve the path of a given uri (excluding the user name)
     * @param uri - uri to be checked
     * @return the path of the given uri.
     */
    public static String getUserPath (String uri) {
        String[] splitted = uri.split("/");
        String path = "";
        int count = 4; // vos://esavo!vospace/useraccount/XXX
        while (count < splitted.length) {
            path += splitted[count++];
            if (count != splitted.length)
                path += "/";
        }
        return path;
    }
    
    /**
     * Retrieve the user account of the given uri.
     * @param uri - uri to be checked
     * @return user account of the uri
     */
    public static String getUserAccount(String uri) {
        String name = getUserRoot(uri);
        String useraccount = "";
        
        if (!name.isEmpty()) {
            int lastBar = name.lastIndexOf("/");
            if (lastBar != -1)
                useraccount = name.substring(lastBar + 1);
        }
        return useraccount;
    }

    /**
     * Retrieve the root uri of the user in the given uri. 
     * @param uri - uri to be checked.
     * @return the uri of the format vos://esavo!vospace/useraccount
     */
    public static String getUserRoot (String uri) {
        String[] splitted = uri.split(VOSConstants.VOS_PREFIX);
        String path = "";
        int size = splitted.length;
        //System.out.println("Size: " + size);
        if (size > 1) {
            int bar = splitted[1].indexOf("/", 1); // /useraccount/XXX
            //System.out.println("Bar: " + bar);

            String useraccount;
            if (bar != -1) {
                useraccount = splitted[1].substring(0, bar);
                //System.out.println("User: " + useraccount);
            } else {
                useraccount = splitted[1].substring(0);
            }
            path = VOSConstants.VOS_PREFIX + useraccount;
        }
        return path;
    }
    
    /**
     * Retrieve if node with 'uri1' is parent of node with 'uri2'
     * @param uri1
     * @param uri2
     * @return true if 1 is parent of 2
     */
    public static boolean isParentOf (String parent, String child) {
        boolean isParent = false;
        
        if (!parent.isEmpty() && !child.isEmpty() && parent.compareTo(child) != 0) {
            int index = child.indexOf(parent);
            if (index != -1) {
                isParent = true;
            }
        }
        
        return isParent;
    }

}
