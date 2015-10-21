package esavo.vospace.service.utils.nodes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.users.ldap.UsersDirectory;
import esavo.vospace.service.utils.access.UserUtils;

/**
 * Set of utilities to manage the list of properties of nodes.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class NodeUtils {
    
    /** Logging variable. */
    private static Log Log = LogFactory.getLog(NodeUtils.class);    
    
    /**
     * Update the value of Length and Modification time of property.
     * @param properties
     * @param fileSize
     */
    public static void updateBasicProperties (List<NodePropertyTO> properties, long fileSize) {
        
        NodePropertyTO propertyD = retrieveNodeProperty(properties, GeneralConstants.PROPERTY_MTIME);
        propertyD = NodeUtils.updateMDateProperty(propertyD, false);
        
        NodePropertyTO propertyL = retrieveNodeProperty(properties, GeneralConstants.PROPERTY_LENGTH);
        propertyL.setValue(Long.toString(fileSize));
    }
    
    /**
     * Retrieve the size of a node from its property list
     * @param node
     * @return size of node
     */
    public static long retrieveNodeSize (NodeTO node) {
        NodePropertyTO property = retrieveNodeProperty(node, 
                GeneralConstants.PROPERTY_LENGTH);
        long length = 0;
        if (property != null)
            length = Long.parseLong(property.getValue());        
        return length;
    }
    
    /**
     * Generic function to retrieve the requested property by its URI.
     * @param list
     * @param voProperty
     * @return NodePropertyTO
     */
    public static NodePropertyTO retrieveNodeProperty (List<NodePropertyTO> list, String voProperty) {
        if (list != null && !list.isEmpty()) {
            for (NodePropertyTO property : list) {
                String uri = property.getVoPropertyTO().getURI();
                if (uri.compareTo(voProperty) == 0) {
                    return property;
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieve the property selected from the property list
     * @param node
     * @param voProperty
     * @return Property requested
     */
    public static NodePropertyTO retrieveNodeProperty (NodeTO node, String voProperty) {
        if (node != null) {
            List<NodePropertyTO> list = node.getNodeProperties();
            for (NodePropertyTO property : list) {
                String uri = property.getVoPropertyTO().getURI();
                if (uri.compareTo(voProperty) == 0) {
                    return property;
                }
            }
        }
        return null;
    }
    
    /**
     * create a new node of type UnstructuredDataNodeTO (by default)
     * @param currentUser
     * @param uriTarget
     * @param nodeSize
     * @return
     */
    public static UnstructuredDataNodeTO createDefaultNode (UserTO currentUser, 
            VOSpaceURI uriTarget, long nodeSize) {
        UnstructuredDataNodeTO nodeTarget = new UnstructuredDataNodeTO();
        nodeTarget.setUri(uriTarget);
        nodeTarget.setUserTO(currentUser);
        // Create properties
        NodePropertyTO lengthProp = NodeUtils.createLengthProperty(nodeSize, false);
        NodePropertyTO mDateProp = NodeUtils.createMDateProperty(new Date(), false);
        NodePropertyTO bDateProp = NodeUtils.createBDateProperty(new Date(), false);
        List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
        properties.add(lengthProp);
        properties.add(mDateProp);
        properties.add(bDateProp);
        nodeTarget.setNodeProperties(properties);
        return nodeTarget;
    }
    
    /**
     * Verifies if the given node already has the mandatory properties:
     * length, creation and modification date. Otherwise, it fulfils the 
     * missing properties for the node with values by default.
     * @param node TO
     */
    public static void checkBasicNodeProperties (NodeTO node) {
        boolean hasLength = false;
        boolean hasBDate = false;
        boolean hasMDate = false;
        if (node != null) {
            List<NodePropertyTO> list = node.getNodeProperties();
            for (NodePropertyTO property : list) {
                String uriProperty = property.getVoPropertyTO().getURI();
                if (uriProperty.compareTo(GeneralConstants.PROPERTY_LENGTH) == 0) {
                    hasLength = true;
                } else if (uriProperty.compareTo(GeneralConstants.PROPERTY_BTIME) == 0) {
                    hasBDate = true;
                } else if (uriProperty.compareTo(GeneralConstants.PROPERTY_MTIME) == 0) {
                    hasMDate = true;
                }
            }
            
            //Generate missing properties
            if (!hasLength) {
                NodePropertyTO lengthP = createLengthProperty(0, false);
                node.getNodeProperties().add(lengthP);
            }
            if (!hasBDate) {
                NodePropertyTO bDateP = createBDateProperty(new Date(), false);
                node.getNodeProperties().add(bDateP);
            }
            if (!hasMDate) {
                NodePropertyTO mDateP = createMDateProperty(new Date(), false);
                node.getNodeProperties().add(mDateP);
            }
        }
    }
    
    /**
     * Create a VoProperty
     * @param uri
     * @param readOnly
     * @return VoPropertyTO created
     */
    private static VoPropertyTO createVoProperty (String uri, boolean readOnly) {
        VoPropertyTO voProperty = new VoPropertyTO();        
        voProperty.setURI(uri);
        voProperty.setReadOnly(readOnly);        
        return voProperty;
    }
    
    /**
     * Create a Length property.    
     * @param length
     * @param nill
     * @return NodePropertyTO created
     */
    public static NodePropertyTO createLengthProperty (long length, Boolean nill) {
        String uri = GeneralConstants.PROPERTY_LENGTH;
        NodePropertyTO property = new NodePropertyTO();
        property.setVoPropertyTO(createVoProperty(uri, false));
        property.getVoPropertyTO().setURI(uri);
        property.setValue(Long.toString(length));
        property.setNill(nill);
        return property;
    }
    
    /**
     * Create a creation Date property.
     * @param bDate
     * @param nill
     * @return NodePropertyTO created
     */
    public static NodePropertyTO createBDateProperty (Date bDate, Boolean nill) {
        String uri = GeneralConstants.PROPERTY_BTIME;
        SimpleDateFormat formatter = new SimpleDateFormat(GeneralConstants.DATE_PATTERN);
        String dateFormated = formatter.format(bDate);
        NodePropertyTO property = new NodePropertyTO();
        property.setVoPropertyTO(createVoProperty(uri, false));
        property.getVoPropertyTO().setURI(uri);
        property.setValue(dateFormated);
        property.setNill(nill);
        return property;
    }
    
    /**
     * Create a modificationDate property.
     * @param mDate
     * @param nill
     * @return NodePropertyTO created
     */
    public static NodePropertyTO createMDateProperty (Date mDate, Boolean nill) {
        String uri = GeneralConstants.PROPERTY_MTIME;
        SimpleDateFormat formatter = new SimpleDateFormat(GeneralConstants.DATE_PATTERN);
        String dateFormated = formatter.format(mDate);
        NodePropertyTO property = new NodePropertyTO();
        property.setVoPropertyTO(createVoProperty(uri, false));
        property.getVoPropertyTO().setURI(uri);
        property.setValue(dateFormated);
        property.setNill(nill);
        return property;
    }
    
    /**
     * Create a description property
     * @param desc
     * @param nill
     * @return NodePropertyTO created
     */
    public static NodePropertyTO createDescriptionProperty (String desc, Boolean nill) {
        String uri = GeneralConstants.PROPERTY_DESCRIPTION;
        NodePropertyTO property = new NodePropertyTO();
        property.setVoPropertyTO(createVoProperty(uri, false));
        property.getVoPropertyTO().setURI(uri);
        property.setValue(desc);
        property.setNill(nill);
        return property;
    }
    
    /**
     * Update the given NodePropertyTO with the current modification date.
     * @param property
     * @param nill
     * @return NodePropertyTO modified
     */
    public static NodePropertyTO updateMDateProperty (NodePropertyTO property, Boolean nill) {
        SimpleDateFormat formatter = new SimpleDateFormat(GeneralConstants.DATE_PATTERN);
        String dateFormated = formatter.format(new Date());
        property.setValue(dateFormated);
        property.setNill(nill);
        return property;
    }
    
    /**
     * Check if Groups have manager for XML requests, if not add currentUser as owner.
     * @param nodeTO
     * @param currentUser
     * @return nodeTO
     */
    public static NodeTO updateGroupManager (NodeTO nodeTO, UserTO currentUser) {
        List<NodePropertyTO> list = nodeTO.getNodeProperties();
        for (NodePropertyTO element : list) {
            if (element instanceof GroupNodePropertyTO) {
                List<GroupTO> groupTO = ((GroupNodePropertyTO)element).getGroupList();
                if (groupTO != null && !groupTO.isEmpty()) {
                    for (GroupTO group : groupTO) {
                        
                        if (group.getManagerTO() == null) {
                            group.setManagerTO(currentUser);
                        }
                    }
                }
            }
        }
        return nodeTO;
    }
    
    /**
     * Assign user to node
     * @param nodeTO
     * @param currentUser
     * @return node updated.
     */
    public static NodeTO updateNodeOwner(NodeTO nodeTO, UserTO currentUser) {
        if (nodeTO != null && currentUser != null) {
            nodeTO.setUserTO(currentUser);
        }        
        return nodeTO;
    }
    
    /**
     * Retrieve a node.
     * @param uri
     * @param full
     * @return
     */
    public static NodeTO getNode(VOSpaceURI uri, boolean full) {
        NodeTO nodeTO = null;
        
        VospaceServiceImpl vospaceService = new VospaceServiceImpl();
        try {
            nodeTO = vospaceService.doQueryNode(uri, full);
        } catch (Exception e) { }
        return nodeTO;
    }
    
    /**
     * Retrives the list of users from LDAP whose mail is NOT null.
     * @param userList
     * @return validatedList of UserTO instances
     */
    public static List<UserTO> checkList (List<UserTO> userList) {
        
        // result of validating user list
        List<UserTO> validatedList = new ArrayList<UserTO>();
        
        for (UserTO user : userList) {
            String name = user.getName();                
            //search user by name                
            UserTO userDB = UserUtils.getUser(name);
            if (userDB == null) {
                //Retrieve user details directly from LDAP 
                UsersDirectory ldapSearch =  new UsersDirectory();
                UserTO userLdap = ldapSearch.searchLdapUser(name);
                if (userLdap != null) {
                    userLdap.setCreationDate(new Date());
                    userLdap.setQuota((long) 0);
                    userLdap.setQLimit(GeneralConstants.USER_QUOTA_LIMIT);
                    VospaceServiceImpl vospaceService = new VospaceServiceImpl();
                    try {
                        vospaceService.doInsertUser(userLdap);
                    } catch (VOSpaceException e) {
                        e.printStackTrace();
                    }
                    
                    validatedList.add(user);
                }
            } else {
                validatedList.add(user);
            }
        }
        return validatedList;
    }
    
    /**
     * Print the node properties for users and groups.
     */
    public static void printNodeProperties (NodeTO node) {
        UserNodePropertyTO pUW = (UserNodePropertyTO) retrieveNodeProperty(node, "vos://esavo!vospace/properties#userwrite");
        UserNodePropertyTO pUR = (UserNodePropertyTO) retrieveNodeProperty(node, "vos://esavo!vospace/properties#userread");
        GroupNodePropertyTO pGW = (GroupNodePropertyTO) retrieveNodeProperty(node, "vos://esavo!vospace/properties#groupwrite");
        GroupNodePropertyTO pGR = (GroupNodePropertyTO) retrieveNodeProperty(node, "vos://esavo!vospace/properties#groupread");
        
        if (pUR != null) {
            Log.debug("For UR: ");
            List<UserTO> users = pUR.getUserList();
            for (UserTO element : users) {
                Log.debug(element.getName());
            }
        }
        
        if (pUW != null) {
            Log.debug("For UW: ");
            List<UserTO> users = pUW.getUserList();
            for (UserTO element : users) {
                Log.debug(element.getName());
            }
        }
        
        if (pGR != null) {
            Log.debug("For GR: ");
            List<GroupTO> groups = pGR.getGroupList();
            for (GroupTO element : groups) {
                Log.debug(element.getName());
                UserTO man = element.getManagerTO();
                if (man != null) {
                    Log.debug(man.getName());
                }
            }
        }
        
        if (pGW != null) {
            Log.debug("For GW: ");
            List<GroupTO> groups = pGW.getGroupList();
            for (GroupTO element : groups) {
                Log.debug(element.getName());
                UserTO man = element.getManagerTO();
                if (man != null) {
                    Log.debug(man.getName());
                }
            }
        }
    }
}
