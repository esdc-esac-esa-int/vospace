package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.GroupNodeProperty;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.UserNodeProperty;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoProperty;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

/**
 * Set the property values for a specific Node.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class UpdateNodePropertiesCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UpdateNodePropertiesCommand.class);
    
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
    
    /** NodTO Tranfer Object */
    private NodeTO nodeTO;
    
    /** Persistent node */
    private Node node;
    
    /** Persistent node result */
    private NodeTO nodeTOResult;
    
    /**
     * Constructor.
     * @param nodeTO
     */
    public UpdateNodePropertiesCommand(NodeTO nodeTO) {
        log.debug("Into UpdateNodeCommand()");
        this.nodeTO = nodeTO;
    }
    
    @Override
    public void execute() {
        log.debug("Into UpdateNodePropertiesCommand().execute()");
        
        if (this.nodeTO == null) {
            throw new IllegalArgumentException("UpdateNodeCommand.execute() -> "
                    + "Input Node cannot be null.");
        }
        
        // The node SHOULD already exists  
        String path = QueryManagerUtils.getPath(this.nodeTO.getURI());
        this.node = IDaoService.getNodeDAO().queryNodeByUri(path);
        if (node == null) {
            throw new UnsupportedOperationException("Node: [" + this.nodeTO.getId()
                    + "] does not exists");
        }
        
        Set<NodeProperty> savedProperties = this.node.getNodePropertySet(); // node persistent properties
        List<NodePropertyTO> newProperties = this.nodeTO.getNodeProperties();
        Iterator<NodePropertyTO> newPropertiesIt = newProperties.iterator();
        while(newPropertiesIt.hasNext()) {
            NodePropertyTO newProperty = newPropertiesIt.next();            
            NodeProperty savedProperty = findNodeProperty(newProperty, savedProperties);
            if (savedProperty != null) {
                if (newProperty.getNill()) {
                    // remove saved property from the collection
                    this.node.getNodePropertySet().remove(savedProperty);
                } else {
                    // update saved property value
                    if (newProperty instanceof UserNodePropertyTO) {
                        this.node.getNodePropertySet().remove(savedProperty);
                        
                        List<UserTO> userList = ((UserNodePropertyTO) newProperty).getUserList();
                        if (userList != null && !userList.isEmpty()) {
                            NodeProperty p = createProperty(newProperty);
                            if (p != null)
                                savedProperties.add(p);
                        }
                    } else if (newProperty instanceof GroupNodePropertyTO) {
                        
                        /*String uri = ((GroupNodePropertyTO)newProperty).getVoPropertyTO().getURI();
                        
                        List<GroupTO> list = ((GroupNodePropertyTO)newProperty).getGroupList();
                        String groupName = "";
                        if (list.size() > 0) {
                            groupName = list.get(0).getName();
                        }*/
                        
                        this.node.getNodePropertySet().remove(savedProperty);
                        
                        List<GroupTO> groupList = ((GroupNodePropertyTO) newProperty).getGroupList();                        
                        if (groupList != null && !groupList.isEmpty()) {
                            NodeProperty p = createProperty(newProperty);
                            if (p != null)
                                savedProperties.add(p);                            
                            
                        }
                    } else {
                        
                        //length cannot be updated for ContainerNodes
                        if ( !(this.nodeTO instanceof ContainerNodeTO && 
                                savedProperty.getVoProperty().getUri().
                                compareToIgnoreCase(GeneralConstants.PROPERTY_LENGTH) == 0)) {
                            
                            savedProperty.setValue(newProperty.getValue());
                        }
                    }
                }
            } else {
                //add property to the list of savedProperties
                NodeProperty p = createProperty(newProperty);
                if (p != null) {
                    savedProperties.add(p);
                }
            }
        }
        
        IDaoService.getNodeDAO().insertOrUpdate(this.node);
        
        Node nodeResult = IDaoService.getNodeDAO().queryNodeById(this.node.getNodeOid());
        
        // add the URI to the node
        String uriPath = 
                IDaoService.getNodeDAO().queryNodeURIById(nodeResult.getNodeOid());        
        VOSpaceURI uri = QueryManagerUtils.composeURIFromPath(uriPath);
        
        this.nodeTOResult = VospaceTransferObjectMapper.populateNodeTO(nodeResult, uri, true);
        
        log.debug("End of UpdateNodePropertiesCommand().execute()");
    }
    
    private Set<Groups> getGroupSet (List<GroupTO> groupList) {
        Set<Groups> groupSet = new HashSet<Groups>();
                
        for (GroupTO groupTO : groupList) {
            UserTO managerTO = groupTO.getManagerTO();
            if (managerTO == null) {
                log.debug("Cannot find Group [" + groupTO.getName() + "] with NULL manager.");
            } else {
                Users user = IDaoService.getUsersDAO().queryUserByName(managerTO.getName());
                Groups group = IDaoService.getGroupsDAO().queryGroupByOwnerAndName(
                        user, groupTO.getName());    
                groupSet.add(group);
            }
        }
        return groupSet;
    }
    
    private Set<Users> getUserSet (List<UserTO> userList) {
        Set<Users> userSet = new HashSet<Users>();
        for (UserTO userTO : userList) {
            String name = userTO.getName();
            Users user = IDaoService.getUsersDAO().queryUserByName(name);
            userSet.add(user);
        }
        return userSet;
    }
    
    private NodeProperty createProperty (NodePropertyTO property) {
        
        String uri = property.getVoPropertyTO().getURI();
        VOSpaceURI propertyURI = new VOSpaceURI(uri);
        VoProperty voProperty = IDaoService.getVoPropertyDAO().
                queryVoPropertyByUri(propertyURI.toString());  
        
        if (voProperty != null) {
            NodeProperty newProperty;
            
            String value = property.getValue();
            
            if (property instanceof GroupNodePropertyTO) {
                newProperty = new GroupNodeProperty();
                newProperty.setVoProperty(voProperty);
                newProperty.setNode(this.node);
                List<GroupTO> groupList = ((GroupNodePropertyTO) property).getGroupList();
                if (groupList != null && !groupList.isEmpty()) {
                    ((GroupNodeProperty) newProperty).setShareGroups(getGroupSet(groupList));
                }
            } else if (property instanceof UserNodePropertyTO) {
                newProperty = new UserNodeProperty();
                newProperty.setVoProperty(voProperty);
                newProperty.setNode(this.node);
                List<UserTO> userList = ((UserNodePropertyTO) property).getUserList();
                if (userList != null && !userList.isEmpty()) {
                    ((UserNodeProperty) newProperty).setShareUsers(getUserSet(userList));
                }
            } else {
                newProperty = new NodeProperty();
                newProperty.setValue(value);
                newProperty.setVoProperty(voProperty);
                newProperty.setNode(this.node);
            }
            return newProperty;
        }
        
        return null;
    }
    
    private NodeProperty findNodeProperty (NodePropertyTO property, 
            Set<NodeProperty> savedProperties) {
        NodeProperty found = null;
        String uri = property.getVoPropertyTO().getURI();
        for (NodeProperty savedProperty : savedProperties) {
            String savedPropertyUri = savedProperty.getVoProperty().getUri();
            if (uri.compareToIgnoreCase(savedPropertyUri) == 0) {
                return savedProperty;
            }
        }
        return found;
    }

    @Override
    public Object getResult() {
        return this.nodeTOResult;
    }

}
