package esavo.vospace.service.cmd.service;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.json.JsonReader;
import esavo.vospace.json.JsonWriter;
import esavo.vospace.service.utils.ControlUtils;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;

public class ShareService {

    /** Logging variable. */
    @SuppressWarnings("unused")
    private static Log Log = LogFactory.getLog(ShareService.class);
    
    /** VOSpace Public Services*/
    private VospaceServiceImpl vospaceService;    
    private UserControlTO userControl;
    
    /**
     * Constructor.
     */
    public ShareService() {
        vospaceService = new VospaceServiceImpl();
        userControl = UserUtils.getCurrentContextControlUser();
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws VOSpaceException
     * @throws IOException
     */
    public void executeGetRequestForGroups(final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, IOException {
        
        UserTO currentUser = UserUtils.getCurrentContextUser();
        if (currentUser == null) {
            throw new ServerException("User not logged.");
        }
        String userName = currentUser.getName();
        
        //Retrieve nodes with READ permissions
        List<NodeTO> nodeReadList = vospaceService.doQueryGroupsNodeProperty(
                userName, GeneralConstants.PROPERTY_GROUPREAD);        
        //Retrieve nodes with WRITE permissions
        List<NodeTO> nodeWriteList = vospaceService.doQueryGroupsNodeProperty(
                userName, GeneralConstants.PROPERTY_GROUPWRITE);
        //Join in a unique list
        List<NodeTO> totalNodeList = new ArrayList<NodeTO>();
        totalNodeList.addAll(nodeReadList);
        totalNodeList.addAll(nodeWriteList);
        
        //Parse result
        JsonWriter.writeNodeList(totalNodeList, response.getOutputStream());
    }
    
    public void executeGetRequestForAll (final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, IOException {
        
        UserTO currentUser = UserUtils.getCurrentContextUser();
        if (currentUser == null) {
            throw new ServerException("User not logged.");
        }
        String userName = currentUser.getName();
        
        //For Groups
        
        //Retrieve nodes with READ permissions
        List<NodeTO> nodeGReadList = vospaceService.doQueryGroupsNodeProperty(
                userName, GeneralConstants.PROPERTY_GROUPREAD);        
        //Retrieve nodes with WRITE permissions
        List<NodeTO> nodeGWriteList = vospaceService.doQueryGroupsNodeProperty(
                userName, GeneralConstants.PROPERTY_GROUPWRITE);
        //Join in a unique list
        List<NodeTO> totalNodeList = new ArrayList<NodeTO>();
        totalNodeList.addAll(nodeGReadList);
        totalNodeList.addAll(nodeGWriteList);
        
        //For Users
        
        //Retrieve nodes with READ permissions
        List<NodeTO> nodeReadList = vospaceService.doQueryUsersNodeProperty(
                userName, GeneralConstants.PROPERTY_USERREAD);        
        //Retrieve nodes with WRITE permissions
        List<NodeTO> nodeWriteList = vospaceService.doQueryUsersNodeProperty(
                userName, GeneralConstants.PROPERTY_USERWRITE);
        
        Map<NodeTO, String> resultList = new HashMap<NodeTO, String>();
        for (NodeTO nodeTO : nodeReadList) {
            resultList.put(nodeTO, "viewer");
        }
        for (NodeTO nodeTO : nodeWriteList) {
            resultList.put(nodeTO, "editor");
        }        
        
        //List<NodeTO> completeList = new ArrayList<NodeTO>();
        Iterator<Entry<NodeTO, String>> it = resultList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = it.next();
            NodeTO node = (NodeTO) pair.getKey();
            /*NodeTO duplicated = contains(totalNodeList, node);
            if (duplicated != null) {}*/
            totalNodeList.add(node);
        }
        
        //Parse result
        JsonWriter.writeNodeList(totalNodeList, response.getOutputStream());
    }
    
    private NodeTO contains (List<NodeTO> list, NodeTO element) {
        
        boolean exists = false;
        NodeTO result = null;
        String elementUri = element.getURI().toString();
        for (NodeTO node : list) {
            String nodeUri = node.getURI().toString();
            if (nodeUri.compareToIgnoreCase(elementUri) == 0) {
                exists = true;
                result = node;
                break;
            }
        }
        return result;
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws VOSpaceException
     * @throws IOException
     */
    public void executeGetRequestForUsers(final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, IOException {
        
        UserTO currentUser = UserUtils.getCurrentContextUser();
        if (currentUser == null) {
            throw new ServerException("User not logged.");
        }
        String userName = currentUser.getName();
        
        //Retrieve nodes with READ permissions
        List<NodeTO> nodeReadList = vospaceService.doQueryUsersNodeProperty(
                userName, GeneralConstants.PROPERTY_USERREAD);        
        //Retrieve nodes with WRITE permissions
        List<NodeTO> nodeWriteList = vospaceService.doQueryUsersNodeProperty(
                userName, GeneralConstants.PROPERTY_USERWRITE);
        
        Map<NodeTO, String> resultList = new HashMap<NodeTO, String>();
        for (NodeTO nodeTO : nodeReadList) {
            resultList.put(nodeTO, "viewer");
        }
        for (NodeTO nodeTO : nodeWriteList) {
            resultList.put(nodeTO, "editor");
        }        
        
        //Parse result
        JsonWriter.writeSharedNodeList(resultList, response.getOutputStream());
    }
    
    /**
     * Allows to process a list of SetNode requests at a time and retrieve 
     * the list of modified nodes.
     * @param request
     * @param response
     * @throws VOSpaceException
     * @throws IOException
     */
    public void executePostRequest(final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, IOException {
        
        String type = request.getPathInfo();
        if (type != null && !type.isEmpty()) {
            type = type.substring(1); //avoid the slash '/group'
        }
        
        UserTO currentUser = UserUtils.getCurrentContextUser();
        if (currentUser == null) {
            throw new ServerException("User not logged.");
        }
        
        List<NodeTO> nodeList = JsonReader.readNodeList(request.getInputStream());
        List<NodeTO> nodeSetList = new ArrayList<NodeTO>();
        for (NodeTO node : nodeList) {
            
            Date startTampstamp = new Date(); //start timestamp logged
            
            NodeTO nodeUpdated = NodeUtils.updateGroupManager(node, currentUser);
            if(type.compareTo("user") == 0) {                
                checkUserList(node);
            }
            NodeTO nodeModified = VospaceServiceImpl.doSetNode(nodeUpdated);
            
            //transfer log
            ShareLogTO transferLog = ControlUtils.createShareLogTO( userControl, 
                    startTampstamp, new Date(), node.getURI().toString());
            VospaceServiceImpl vospaceService = new VospaceServiceImpl();
            vospaceService.doInsertShareLog(transferLog);
            
            nodeSetList.add(nodeModified);
        }
        
        JsonWriter.writeNodeList(nodeSetList, response.getOutputStream());
    }
    
    /*
     * Check whether the list of users for sharing with already exist in the database.
     * If they don't exist, they are retrieved from the ldap server and then inserted 
     * into users table.
     */
    private void checkUserList (NodeTO node) {
        UserNodePropertyTO userReadProperty = 
                (UserNodePropertyTO) NodeUtils.retrieveNodeProperty(node, GeneralConstants.PROPERTY_USERREAD);
        UserNodePropertyTO userWriteProperty = 
                (UserNodePropertyTO) NodeUtils.retrieveNodeProperty(node, GeneralConstants.PROPERTY_USERWRITE);
        if (userReadProperty != null) {
            List<UserTO> validatedList = NodeUtils.checkList(userReadProperty.getUserList());
            userReadProperty.getUserList().clear();
            if (!validatedList.isEmpty())
                userReadProperty.getUserList().addAll(validatedList);
        }
        
        if (userWriteProperty != null) {
            List<UserTO> validatedList = NodeUtils.checkList(userWriteProperty.getUserList());
            userWriteProperty.getUserList().clear();
            if (!validatedList.isEmpty())
                userWriteProperty.getUserList().addAll(validatedList);
        }
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws VOSpaceException
     * @throws IOException
     */
    public void executeDeleteRequest(final HttpServletRequest request, 
            final HttpServletResponse response) throws VOSpaceException, IOException {
        
        String type = request.getPathInfo();
        if (type != null && !type.isEmpty()) {
            type = type.substring(1); //avoid the slash '/group'
        }
        
        UserTO currentUser = UserUtils.getCurrentContextUser();
        if (currentUser == null) {
            throw new ServerException("User not logged.");
        }
        
        NodeTO node = JsonReader.readNode(request.getInputStream());
        NodeTO nodeUpdated = NodeUtils.updateGroupManager(node, currentUser);
        
        // Printing information
        /*Log.info("Printing 1...");
        NodeUtils.printNodeProperties(node);
        Log.info("Printing 2...");
        NodeUtils.printNodeProperties(nodeUpdated);
        Log.info("End of printing ...");*/
        NodeTO nodeModified = VospaceServiceImpl.doSetNode(nodeUpdated);
        
        JsonWriter.writeNode(nodeModified, response.getOutputStream());
    }
}
