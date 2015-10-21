package esavo.vospace.service.utils.nodes;

import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.exceptions.database.ContainerNotFoundException;
import esavo.vospace.exceptions.InvalidURIException;
import esavo.vospace.exceptions.database.LinkFoundException;
import esavo.vospace.exceptions.database.NodeNotFoundException;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.access.UserUtils;

/**
 * Provide a set of tools to verify the URI resource.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class URIUtils {
    
    /**
     * Verify whether the VOSpaceURI is valid or not.
     * @param uri
     * @param checkTarget whether to check the last node.
     * @return true if uri is valid, false otherwise.
     * @throws InvalidURIException
     * @throws ContainerNotFoundException
     * @throws LinkFoundException
     * @throws NodeNotFoundException 
     */
    public static boolean verifyNodeURL(VOSpaceURI uri, boolean checkTarget, boolean allowRoot) 
            throws InvalidURIException, ContainerNotFoundException, LinkFoundException, 
            NodeNotFoundException {
        boolean verified = true;
        
        if (uri == null) {
            throw new InvalidURIException("Empty uri.");
        }
        
        checkURIPrefix(uri.getURIPrefix());
        // check bars, only 1 bar allowed separating nodes
        String path = uri.getPath();
        if (path.isEmpty() || path.length() < 2) {
            throw new InvalidURIException("Invalid path empty for URI [" + uri + "].");
        }
        String nodePath = uri.getPath().substring(1); //the first position is a bar
        String[] pathSplit = nodePath.split(VOSConstants.BAR);
        if (pathSplit.length < 2 && !allowRoot) { // /useracount
            throw new InvalidURIException("Invalid path [" + nodePath + "].");
        }
        for (String node : pathSplit) {
            if (node.isEmpty()) {
                throw new InvalidURIException("Invalid path [" + nodePath + "].");
            }
        }
        
        checkUserAccount(uri);
                
        checkURIPath(uri, checkTarget);
        
        return verified;
    }
    
    public static boolean isRoot (VOSpaceURI uri) {
        String nodeURI = "";
        if (uri != null)
            nodeURI = uri.toString();
        boolean isRoot = VospaceURIUtils.isVOSpaceRoot(nodeURI);
        if (!isRoot) {
            isRoot = VospaceURIUtils.isUserRoot(nodeURI);
        }        
        return isRoot;
    }
    
    /*
     * Check URI Prefix
     */
    private static boolean checkURIPrefix(String prefix) 
            throws InvalidURIException {
        boolean success = false;
        if (prefix.compareTo(VOSConstants.VOS_PREFIX) == 0) {
            success = true;
        } else 
            throw new InvalidURIException("Invalid URI Prefix for [" + prefix + "].");
        
        return success;
    }
    
    /*
     * Check User Account
     */
    private static boolean checkUserAccount(VOSpaceURI uri) throws InvalidURIException {
        boolean success = false;
        
        String userName = VospaceURIUtils.getUserAccount(uri.toString());
        
        if (userName.isEmpty()) {
            throw new InvalidURIException("Invalid User name for Path [" + uri + "].");
        }
        
        // get User from VOSpace
        //UserTO user = UserUtils.getUser(userName);        
        UserTO user = UserUtils.getCurrentContextUser();
        
        if (user == null /*|| userName.compareTo(user.getName()) != 0*/) {
            throw new InvalidURIException("User not found for Name [" + userName + "].");
        }
        
        return success;
    }
    
    /*
     * Check URI Path
     */
    private static boolean checkURIPath(VOSpaceURI uri, boolean checkTarget) 
            throws InvalidURIException, ContainerNotFoundException, LinkFoundException, 
            NodeNotFoundException {
        
        boolean success = true;
        
        // pathOfNodes = node1/node2/node3... 
        String pathOfNodes = VospaceURIUtils.getUserPath(uri.toString());       
        if (!pathOfNodes.isEmpty()) {
            
            String userName = VospaceURIUtils.getUserAccount(uri.toString()); // userName = snieto
            String[] pathSplit = pathOfNodes.split(VOSConstants.BAR); // pathSplit = node1|node2|node3...
            int count = 0;
            int size = pathSplit.length;
            String accumulatedPath = "";
            for (String node : pathSplit) {
                if (accumulatedPath.isEmpty()) {
                    accumulatedPath = node;
                } else
                    accumulatedPath += VOSConstants.BAR + node;
                VOSpaceURI nodeURI = new VOSpaceURI(VOSConstants.VOS_PREFIX + VOSConstants.BAR + 
                        userName + VOSConstants.BAR + accumulatedPath);
                
                if ((count != size-1) || ((count == size-1) && checkTarget)) {
                    NodeTO nodeTO = NodeUtils.getNode(nodeURI, false);
                    if (nodeTO == null) {
                        throw new ContainerNotFoundException("ContainerNotFound for URI [" + nodeURI + "].");
                    } else if (nodeTO instanceof LinkNodeTO) {
                        throw new LinkFoundException("LinkFound for URI [" + nodeURI + "]");
                    }
                }
                count++;
            }
        }
        
        return success;
    }
    
    public static String checkPathInfo (String path) {
        if (path == null) {
            path = "";
        }
        int barPos = path.lastIndexOf(VOSConstants.BAR);
        String pathChecked = path;
        //check last bar position
        int size = path.length();
        if ((size > 0) && (barPos == (size-1))) {
            pathChecked = path.substring(0, size-1);
        }
        return pathChecked;
    }
}
