package esavo.vospace.service.utils.access;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.service.cmd.oper.QueryGroupCommand;
import esavo.vospace.service.utils.nodes.NodeUtils;

public class AccessUtils {
    
    public static final String R_TYPE = "read";
    public static final String RW_TYPE = "write";
    
    public static boolean isAuthorized (final UserTO user, final String operation, final VOSpaceURI uri) {
        NodeTO node = NodeUtils.getNode(uri, false);
        return isAuthorized(user, operation, node);
    }
    
    /**
     * Checks whether the user is authorized to do a specific operation.
     * @param user who execute the operation
     * @param operation: 'read' or 'write'
     * @param uri of the resource
     * @return true if it has access, false otherwise.
     */
    public static boolean isAuthorized (final String operation, final VOSpaceURI uri) {
        UserTO user = UserUtils.getCurrentContextUser();
        NodeTO node = NodeUtils.getNode(uri, false);
        return isAuthorized(user, operation, node);
    }
    
    public static boolean isAuthorized (final String operation, final NodeTO node) {
        UserTO user = UserUtils.getCurrentContextUser();
        /*String name = user.getName();
        String full = user.getFullName();
        String mail = user.getMail();*/
        return isAuthorized(user, operation, node);
    }
        
    public static boolean isAuthorized(final UserTO user, final String operation, final NodeTO node) {
        boolean authorized = false;
                
        if (isOwner(user, node)) {
            return true;
        }
        
        boolean seekProperty = false;
        if (operation.compareToIgnoreCase(R_TYPE) == 0) {
            seekProperty = true;
        } else if (operation.compareToIgnoreCase(RW_TYPE) == 0) {
            seekProperty = false;
        }
        // Is Authorized as User?
        boolean authorizedAsUser = authorizedAsUser(user, node, seekProperty);        
        if (authorizedAsUser) {
            return authorizedAsUser;
        }
                
        // Is Authorized as Group?
        authorized = authorizedAsGroup(user, node, seekProperty);
        
        return authorized;
    }
    
    /**
     * Checks whether the User is authorized by User
     * @param user
     * @param node
     * @param propertyOper
     * @return
     */
    private static boolean authorizedAsUser (UserTO user, NodeTO node, boolean seekProperty) {
        boolean authorized = false;        
        
        // searching the property
        List<UserTO> userList = new ArrayList<UserTO>();
        List<NodePropertyTO> properties = node.getNodeProperties();
        for (NodePropertyTO property : properties) {
            String propertyURI = property.getVoPropertyTO().getURI().toString();
            if (seekProperty) {
                if (property instanceof UserNodePropertyTO) {
                    userList.addAll(((UserNodePropertyTO) property).getUserList());
                }
            } else {
                if (propertyURI.compareTo(GeneralConstants.PROPERTY_USERWRITE) == 0) {
                    userList.addAll(((UserNodePropertyTO) property).getUserList());
                    break;
                }
            }
        }
        
        // searching me as authorized user
        for (UserTO userAuthorized : userList) {
            if (userAuthorized.getName().compareTo(user.getName()) == 0) {
                authorized = true;
                break;
            }
        }
        
        if (!authorized) {
            //recursive call
            VOSpaceURI uri = node.getURI();
            VOSpaceURI parentUri = null;
            try {
                parentUri = uri.getParentURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            NodeTO parent = NodeUtils.getNode(parentUri, true);
            if (parent != null)
                authorized = authorizedAsUser(user, parent, seekProperty);
        }
        
        return authorized;
    }
    
    /**
     * Checks whether 
     * @param user
     * @param node
     * @param propertyOper
     * @return
     */
    private static boolean authorizedAsGroup (UserTO user, NodeTO node, boolean seekProperty) {
        boolean authorized = false;        
        
        // searching the property
        List<GroupTO> groupList = new ArrayList<GroupTO>();
        List<NodePropertyTO> properties = node.getNodeProperties();
        for (NodePropertyTO property : properties) {
            String propertyURI = property.getVoPropertyTO().getURI().toString();
            if (seekProperty) {
                if (property instanceof GroupNodePropertyTO) {
                    groupList.addAll(((GroupNodePropertyTO) property).getGroupList());
                }
            } else {
                if (propertyURI.compareTo(GeneralConstants.PROPERTY_GROUPWRITE) == 0) {
                    groupList = ((GroupNodePropertyTO) property).getGroupList();
                    break;
                }
            }
        }        
        // searching the group where I belong to
        List<GroupTO> authorizedGroups = new ArrayList<GroupTO>();
        //Check if the user belongs to any of the groups
        for (GroupTO group : groupList) {
            String groupName = group.getName();
            UserTO manager = group.getManagerTO();
            String userName = manager.getName();
            List<GroupTO> persistentGroupList = getGroup(userName, groupName);
            GroupTO persistentGroup = persistentGroupList.get(0);
            if (persistentGroup != null) {
                List<UserTO> members = persistentGroup.getMembers();
                for (UserTO groupMember : members) {
                    if (groupMember.getName().compareTo(user.getName()) == 0) {
                        authorizedGroups.add(persistentGroup);
                        break;
                    }
                }
            }
        }
        
        if (authorizedGroups.size() > 0) {
            authorized = true;
        }
        
        if (!authorized) {
            //recursive call
            VOSpaceURI uri = node.getURI();
            VOSpaceURI parentUri = null;
            try {
                parentUri = uri.getParentURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            NodeTO parent = NodeUtils.getNode(parentUri, true);
            if (parent != null)
                authorized = authorizedAsGroup(user, parent, seekProperty);
        }
        
        return authorized;
    }
    
    private static List<GroupTO> getGroup(String user, String groupName) {
        QueryGroupCommand groupCommand = new QueryGroupCommand(user, groupName);
        try {
            groupCommand.execute();
        } catch (InternalFaultException e) {
        }
        return (List<GroupTO>) groupCommand.getResult();
    }
    
    /**
     * Check if user is owner of node.
     * @param user
     * @param node
     * @return true
     */
    public static boolean isOwner (final UserTO user, final NodeTO node) {        
        UserTO userTO = node.getUserTO();
        /*String userTOName = userTO.getName();
        String userName = user.getName();*/
        if (user.getName().compareToIgnoreCase(userTO.getName()) == 0) {
            return true;
        } else {
            
        }
        
        return false;
    }
}
