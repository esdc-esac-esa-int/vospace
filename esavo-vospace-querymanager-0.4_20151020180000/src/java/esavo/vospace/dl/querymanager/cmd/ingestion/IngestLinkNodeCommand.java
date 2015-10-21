package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.ILinkNodeDao;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoCapability;
import esac.archive.vospace.persistence.model.VoProperty;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

/**
 * Command which will ingest a LinkNode into the database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestLinkNodeCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestLinkNodeCommand.class);
    
    /** Vospace LinkNode DAO. */
    private ILinkNodeDao linkNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getLinkNodeDAO();
    
    /** Vospace Node DAO. */
    private INodeDao nodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getNodeDAO();
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** Vospace VoCapability DAO. */
    private IVoCapabilityDao voCapabilityDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoCapabilityDAO();
    
    /** Vospace VoProperty DAO. */
    private IVoPropertyDao voPropertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    /** Transfer object to be ingested. */
    private LinkNodeTO linkNodeTO;
    
    /** Ingested persistent object. */
    private LinkNode linkNode;
    
    /** Transfer object result. */
    private LinkNodeTO result;
    
    /** Persistent owner name */
    private UserTO userTO;
    
    /** List of associated voCapabilities for input ContainerNode. */
    @SuppressWarnings("unused")
    private List<VoCapabilityTO> voCapabilityTOList;
    
    /** List of associated nodeProperties for input ContainerNode */
    private List<NodePropertyTO> nodePropertyTOList;
        
    /** Overwrite information flag */
    private Boolean overwriteData;
    
    /**
     * Constructor.
     * @param linkNodeTO
     * @param vospaceTO
     * @param parentUriPath
     * @param userTO
     * @param voCapabilityTOList
     * @param nodePropertyTOList
     * @param overwriteData
     */
    public IngestLinkNodeCommand (final LinkNodeTO linkNodeTO,
            final Boolean overwriteData) {
        log.debug("Into IngestLinkNodeCommand()");
        this.linkNodeTO = linkNodeTO;
        this.userTO = this.linkNodeTO.getUserTO();
        //this.voCapabilityTOList = voCapabilityTOList;
        this.nodePropertyTOList = this.linkNodeTO.getNodeProperties();
        this.overwriteData = overwriteData;
    }
    
    @Override
    public void execute() {
        
        log.debug("Into IngestLinkNodeCommand().execute()");
        
        if (this.linkNodeTO == null) {
            throw new IllegalArgumentException("IngestLinkNodeCommand.execute() -> "
                    + "Input LinkNode cannot be null.");
        }
        
        // Does this LinkNode already exists??
        String path = QueryManagerUtils.getPath(this.linkNodeTO.getURI());
        Node node = this.nodeDao.queryNodeByUri(path);
        if ( node != null && !(node instanceof LinkNode) ) {
            throw new UnsupportedOperationException("LinkNode: [" + this.linkNodeTO.getId()
                    + "] has already been ingested");
        } else if (node != null) {
            this.linkNode = this.linkNodeDao.queryLinkNodeById(node.getNodeOid());
        }
        
        if ((linkNode != null) && !this.overwriteData) {
            throw new UnsupportedOperationException("LinkNode: [" + this.linkNodeTO.getId()
                    + "] has already been ingested");
        } else if (this.linkNode == null) {
            this.linkNode = this.linkNodeDao.getEmptyLinkNode();
        }
        
        // retrieve LinkNode owner (if exists)
        if (this.userTO == null) {
            throw new IllegalArgumentException("IngestLinkNodeCommand.execute() -> "
                    + "Input User owner cannot be null.");
        }        
        Users ownerUser = this.userDao.queryUserByName(this.userTO.getName());
        if (ownerUser == null) {
            throw new UnsupportedOperationException("LinkNode: [" + this.linkNodeTO.getId()
                    + "] without User owner cannot be inserted."); 
        }
        
        //Check target field
        if (this.linkNodeTO.getTarget() == null || this.linkNodeTO.getTarget().toString().isEmpty()) {
            throw new IllegalArgumentException("IngestLinkNodeCommand.execute() -> "
                    + "Input Target cannot be null.");
        }
        
        // retrieve link parent node (if any)
        // allowed not parent for the ContainerNode        
        String parentUriPath = QueryManagerUtils.getParentPath(this.linkNodeTO.getURI());
        if (parentUriPath != null && !parentUriPath.isEmpty()) {
            Node linkParent = this.nodeDao.queryNodeByUri(parentUriPath);
            if ((linkParent != null) && (linkParent instanceof ContainerNode)) {
                this.linkNode.setContainerNode(((ContainerNode) linkParent)); // Add parent
            } else if (linkParent instanceof LinkNode) {
                throw new IllegalArgumentException("IngestLinkNodeCommand.execute() -> "
                        + "LinkFound for uri [" + parentUriPath + "].");
            } else {
                throw new IllegalArgumentException("IngestLinkNodeCommand.execute() -> "
                        + "ContainerNotFound for uri [" + parentUriPath + "].");
            } 
        }
                
        // Attach Capabilities to LinkNode (if any)
        /*Set<VoCapability> capabilitySet;
        if (this.overwriteData) {
            capabilitySet = new HashSet<VoCapability>();
        } else {
            capabilitySet = this.linkNode.getNodeCapabilities();
        }        
        if ((this.voCapabilityTOList != null) && (this.voCapabilityTOList.size() > 0)) {
            for (VoCapabilityTO voCapabilityTO : this.voCapabilityTOList) {
                VoCapability persistentCapability = getVoCapabilityByUri(voCapabilityTO.getURI().toString());
                if (persistentCapability == null) {
                    throw new IllegalArgumentException("IngestLinkNodeCommand.execute() -> "
                            + "No Capability found for Capability Uri [" + voCapabilityTO.getURI() + "].");
                }
                capabilitySet.add(persistentCapability);
            }
        }*/
        
        // Attach Properties to LinkNode (if any)
        Set<NodeProperty> propertySet;
        if (this.overwriteData) {
            propertySet = new HashSet<NodeProperty>();
        } else {
            propertySet = this.linkNode.getNodePropertySet();
        }
        if ((this.nodePropertyTOList != null) && (this.nodePropertyTOList.size() > 0)) {            
            for (NodePropertyTO nodePropertyTO : this.nodePropertyTOList) {
                String uri = nodePropertyTO.getVoPropertyTO().getURI();
                VOSpaceURI propertyURI = new VOSpaceURI(uri);
                VoProperty persistentProperty = getVoPropertyByUri(propertyURI.toString());
                if (persistentProperty == null) {
                    throw new IllegalArgumentException("IngestLinkNodeCommand.execute() -> "
                            + "No Property found for Property Uri [" + propertyURI + "].");
                }
                NodeProperty nodeProperty = new NodeProperty();
                nodeProperty.setVoProperty(persistentProperty);
                nodeProperty.setValue(nodePropertyTO.getValue());
                nodeProperty.setNode(this.linkNode);
                if (!nodePropertyTO.getNill()) {
                    propertySet.add(nodeProperty);
                }
            }
        }
        
        //this.linkNode.setNodeOid(this.linkNodeTO.getId());
        this.linkNode.setUsers(ownerUser);
        this.linkNode.setName(this.linkNodeTO.getURI().getName());
        this.linkNode.setTarget(this.linkNodeTO.getTarget().toString());
        this.linkNode.setNodeCapabilities(new HashSet<VoCapability>());
        this.linkNode.setNodePropertySet(propertySet);
        
        // Sharing code
        //this.linkNode.setUsersAndRoles(shareUsersList);
        //this.linkNode.setShareGroup(groupList);
        
        this.linkNodeDao.insertOrUpdate(this.linkNode);
        
        // add the URI to the node
        String uriPath = 
                this.nodeDao.queryNodeURIById(this.linkNode.getNodeOid());        
        VOSpaceURI uri = QueryManagerUtils.composeURIFromPath(uriPath);
        
        // mapping to Transfer Object
        this.result = VospaceTransferObjectMapper.populateLinkNodeTO(this.linkNode, uri, true);
        
        log.debug("End of IngestLinkNodeCommand.execute()");

    }
    
    /**
     * Query VoCapability by uri.
     * @param voCapabilityId Input voCapability uri.
     * @return voCapability
     */
    @SuppressWarnings("unused")
    private VoCapability getVoCapabilityByUri(final String voCapabilityUri) {
        if (voCapabilityUri == null) {
            throw new IllegalArgumentException("IngestLinkNodeCommand.getVoCapabilityByUri() -> "
                    + "Input VoCapability Id cannot be null.");
        }
        VoCapability persistentCapability = this.voCapabilityDao.queryVoCapabilityByUri(voCapabilityUri);
        return persistentCapability;
    }
    
    /**
     * Query VoProperty by uri.
     * @param VoPropertyId Input VoProperty uri.
     * @return VoProperty
     */
    private VoProperty getVoPropertyByUri(final String voPropertyUri) {
        if (voPropertyUri == null) {
            throw new IllegalArgumentException("IngestLinkNodeCommand.getVoPropertyByUri() -> "
                    + "Input VoProperty Uri cannot be null.");
        }
        VoProperty persistentProperty = this.voPropertyDao.queryVoPropertyByUri(voPropertyUri);
        return persistentProperty;
    }
    
    /**
     * Ingest input VoCapability transfer objects.
     * @param voCapabilityTOList Input list of target objects
     * @param overwrite Shall we overwrite existing contents?
     * @return Set of persistent objects.
     * @throws ArchiveNestedException If any error is found.
     */
    /*private Set<VoCapability> ingestVoCapabilities(final List<VoCapabilityTO> voCapabilityTOList, 
            final boolean overwrite) throws ArchiveNestedException {    
        if (voCapabilityTOList == null) {
            return null;
        }
        Set<VoCapability> voCapabilitySet = new HashSet<VoCapability>();
        for (VoCapabilityTO voCapabilityTO : voCapabilityTOList) {
            VoCapability voCapability = ingestVoCapabily(voCapabilityTO, overwrite);
            voCapabilitySet.add(voCapability);
        }
        return voCapabilitySet;
    }*/
    
    /**
     * Ingest input VoCapability transfer object.
     * @param VoCapabilityTO
     * @param overwrite
     * @return persistent object
     * @throws ArchiveNestedException
     */
    /*private VoCapability ingestVoCapabily(final VoCapabilityTO voCapabilityTO, 
            final boolean overwrite) throws ArchiveNestedException {
        
        IQueryManagerCommand ingestVoCapability = VospaceQueryManagerCommandFactory
                .createVoCapabilityCommand(voCapabilityTO, overwrite);
        ingestVoCapability.execute();
        return (VoCapability) ingestVoCapability.getResult();
    }*/
    
    /**
     * INgest input NodeProperty transfer objects.
     * @param NodePropertyTOList
     * @param overwrite
     * @return Set of persistent objects.
     * @throws ArchiveNestedException
     */
    /*private Set<NodeProperty> ingestNodeProperties(final List<NodePropertyTO> NodePropertyTOList,
            final boolean overwrite) throws ArchiveNestedException {
        if (NodePropertyTOList != null) {
            return null;
        }
        Set<NodeProperty> nodePropertySet = new HashSet<NodeProperty>();
        for (NodePropertyTO nodePropertyTO : NodePropertyTOList) {
            NodeProperty nodeProperty = ingestNodeProperty(nodePropertyTO, overwrite);
            nodePropertySet.add(nodeProperty);
        }
        return nodePropertySet;
    }*/
    
    /**
     * Ingest input NodeProperty transfer object.
     * @param NodePropertyTO
     * @param overwrite
     * @return persistent object.
     * @throws ArchiveNestedException
     */
    /*private NodeProperty ingestNodeProperty(final NodePropertyTO NodePropertyTO,
            final boolean overwrite) throws ArchiveNestedException {
        return null;
    }*/
    
    /*private Map<Users,Role> ingestShareUsers(final Map<UserTO,RoleTO> shareUserTOList,
            final boolean overwrite) throws ArchiveNestedException {
        if (shareUserTOList != null) {
            return null;
        }        
        Map<Users,Role> shareSet = new HashMap<Users,Role>();
        for (Map.Entry<UserTO,RoleTO> entry : shareUserTOList.entrySet()) {
            UserTO userTO = entry.getKey();
            RoleTO roleTO = entry.getValue();
            Map.Entry<Users,Role> element = ingestShareUser(userTO, roleTO, overwrite);
            shareSet.put(element.getKey(), element.getValue());            
        }
        return shareSet;
    }*/
    
    /*private Map.Entry<Users,Role> ingestShareUser(final UserTO userTO, final RoleTO roleTO,
            final boolean overwrite) throws ArchiveNestedException {
        return null;
    }*/
    
    /*private Set<Groups> ingestGroups(final List<GroupTO> groupTOList,
            final boolean overwrite) throws ArchiveNestedException {
        if (groupTOList != null) {
            return null;
        }
        Set<Groups> groupSet = new HashSet<Groups>();
        for (GroupTO groupTO : groupTOList) {
            Groups group = ingestGroup(groupTO, overwrite);
            groupSet.add(group);
        }
        return groupSet;
    }
    
    private Groups ingestGroup(final GroupTO groupTO,
            final boolean overwrite) throws ArchiveNestedException {
        return null;
    }*/

    @Override
    public Object getResult() {
        return this.result;
    }

}
