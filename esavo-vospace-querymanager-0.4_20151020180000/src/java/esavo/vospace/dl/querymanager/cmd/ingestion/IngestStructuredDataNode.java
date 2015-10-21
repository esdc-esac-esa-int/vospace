package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.IStructuredDataNodeDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.dao.IVoViewDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.StructuredDataNode;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoCapability;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VoView;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

/**
 * Ingest a StructuredDataNode into VOSpace.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestStructuredDataNode implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestStructuredDataNode.class);
    
    /** Vospace Structured Data Node DAO. */
    private IStructuredDataNodeDao structuredNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getStructuredDataNodeDAO();
    
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
    
    /** Vospace VoView DAO. */
    private IVoViewDao voViewDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoViewDAO();
    
    /** Transfer object to be ingested. */
    private StructuredDataNodeTO structuredDataNodeTO;
    
    /** Transfer object result. */
    private StructuredDataNodeTO result;
    
    /** Ingested persistent object. */
    private StructuredDataNode structuredDataNode;
    
    /** Persistent User owner name */
    private UserTO ownerTO;
    
    /** List of associated voCapabilities for input StructuredDataNode. */
    @SuppressWarnings("unused")
    private List<VoCapabilityTO> voCapabilityTOList;
    
    /** List of associated voViews for input StructuredDataNode. */
    @SuppressWarnings("unused")
    private List<VoViewTO> voViewTOList;
    
    /** List of associated nodeProperties for input StructuredDataNode */
    private List<NodePropertyTO> nodePropertyTOList;
    
    /** Overwrite information flag */
    private Boolean overwriteData;
    
    /**
     * Constructor.
     * @param structuredDataNodeTO
     * @param vospaceTO
     * @param parentUriPath
     * @param userTO
     * @param voCapabilityTOList
     * @param nodePropertyTOList
     * @param voViewTOList
     * @param overwrite
     */
    public IngestStructuredDataNode(final StructuredDataNodeTO structuredDataNodeTO, 
            final Boolean overwrite) {        
        log.debug("Into IngestStructuredDataNode()");
        this.structuredDataNodeTO = structuredDataNodeTO;
        this.ownerTO = this.structuredDataNodeTO.getUserTO();
        // metadata about the StructuredDataNode
        //this.voCapabilityTOList = voCapabilityTOList;
        this.nodePropertyTOList = this.structuredDataNodeTO.getNodeProperties();
        //this.voViewTOList = voViewTOList;
        this.overwriteData = overwrite;
    }
    
    @Override
    public void execute() {
        log.debug("Into IngestStructuredDataNode().execute()");
        
        if (this.structuredDataNodeTO == null) {
            throw new IllegalArgumentException("IngestStructuredDataNodeCommand.execute() -> "
                    + "Input StructuredDataNode cannot be null.");
        }
        
        // Does this StructuredDataNode already exists??
        String path = QueryManagerUtils.getPath(this.structuredDataNodeTO.getURI());
        Node node = this.nodeDao.queryNodeByUri(path);        
        if ( node != null && !(node instanceof ContainerNode) ) {
            throw new UnsupportedOperationException("Node: [" + node.getNodeOid()
                    + "] already exists and is type of [" + node.getClass() + "]");
        } else if (node != null) {
            this.structuredDataNode = this.structuredNodeDao.queryStructuredDataNodeById(node.getNodeOid());
        }
        
        if ((structuredDataNode != null) && !this.overwriteData) {
            throw new UnsupportedOperationException("StructuredDataNode: [" + this.structuredDataNodeTO.getId()
                    + "] has already been ingested");
        } else if (this.structuredDataNode == null) {
            this.structuredDataNode = this.structuredNodeDao.getEmptyStructuredDataNode();
        }
        
        // retrieve StructuredDataNode owner (if exists)
        if (this.ownerTO == null) {
            throw new IllegalArgumentException("IngestStructuredDataNodeCommand.execute() -> "
                    + "Input User owner cannot be null.");
        }        
        Users ownerUser = this.userDao.queryUserByName(this.ownerTO.getName());
        if (ownerUser == null) {
            throw new UnsupportedOperationException("StructuredDataNode: [" + this.structuredDataNodeTO.getId()
                    + "] without User owner cannot be inserted."); 
        }
        
        this.structuredDataNode.setName(this.structuredDataNodeTO.getURI().getName());
        this.structuredDataNode.setUsers(ownerUser);
        
        // retrieve structured data node parent (if any)
        // allowed not parent for the StructuredDataNode
        String parentUriPath = QueryManagerUtils.getParentPath(this.structuredDataNodeTO.getURI());
        if (parentUriPath != null && !parentUriPath.isEmpty()) {
            Node containerParent = this.nodeDao.queryNodeByUri(parentUriPath);
            if ((containerParent != null) && (containerParent instanceof ContainerNode)) {
                this.structuredDataNode.setContainerNode(((ContainerNode) containerParent)); // Add parent
            } else if (containerParent instanceof LinkNode) {
                throw new IllegalArgumentException("IngestStructuredDataNodeCommand.execute() -> "
                        + "LinkFound for uri [" + parentUriPath + "].");
            } else {
                throw new IllegalArgumentException("IngestStructuredDataNodeCommand.execute() -> "
                        + "ContainerNotFound for uri [" + parentUriPath + "].");
            } 
        }
        
        // Attach Capabilities to StructuredDataNode (if any)
        /*Set<VoCapability> capabilitySet;
        if (this.overwriteData) {
            capabilitySet = new HashSet<VoCapability>();
        } else {
            capabilitySet = this.structuredDataNode.getNodeCapabilities();
        }        
        if ((this.voCapabilityTOList != null) && (this.voCapabilityTOList.size() > 0)) {
            for (VoCapabilityTO voCapabilityTO : this.voCapabilityTOList) {
                VoCapability persistentCapability = getVoCapabilityByUri(voCapabilityTO.getURI().toString());
                if (persistentCapability == null) {
                    throw new IllegalArgumentException("IngestContainerNodeCommand.execute() -> "
                            + "No Capability found for Capability Uri [" + voCapabilityTO.getURI() + "].");
                }
                capabilitySet.add(persistentCapability);
            }
        }*/
        
        // Attach Properties to StructuredDataNode (if any)
        Set<NodeProperty> propertySet;
        if (this.overwriteData) {
            propertySet = new HashSet<NodeProperty>();
        } else {
            propertySet = this.structuredDataNode.getNodePropertySet();
        }
        if ((this.nodePropertyTOList != null) && (this.nodePropertyTOList.size() > 0)) {
            for (NodePropertyTO nodePropertyTO : this.nodePropertyTOList) {
                String uri = nodePropertyTO.getVoPropertyTO().getURI();
                VOSpaceURI propertyURI = new VOSpaceURI(uri);
                VoProperty persistentProperty = getVoPropertyByUri(propertyURI.toString());
                if (persistentProperty == null) {
                    throw new IllegalArgumentException("IngestContainerNodeCommand.execute() -> "
                            + "No Property found for Property Uri [" + propertyURI + "].");
                }
                
                NodeProperty nodeProperty = new NodeProperty();
                nodeProperty.setVoProperty(persistentProperty);
                nodeProperty.setValue(nodePropertyTO.getValue());
                nodeProperty.setNode(this.structuredDataNode);
                if (!nodePropertyTO.getNill()) {
                    propertySet.add(nodeProperty);
                }
            }
        }
        
        // Attach Views to ContainerNode (if any)
        /*Set<VoView> viewSet;
        if (this.overwriteData) {
            viewSet = new HashSet<VoView>();
        } else {
            viewSet = this.structuredDataNode.getViews();
        }
        if ((this.voViewTOList != null) && (this.voViewTOList.size() > 0)) {
            for (VoViewTO voViewTO : this.voViewTOList) {
                VoView persistentView = getVoViewByUri(voViewTO.getURI().toString());
                if (persistentView == null) {
                    throw new IllegalArgumentException("IngestContainerNodeCommand.execute() -> "
                            + "No View found for View Uri [" + voViewTO.getURI() + "].");
                }
                viewSet.add(persistentView);
            }
        }*/
        
        this.structuredDataNode.setNodeCapabilities(new HashSet<VoCapability>());
        this.structuredDataNode.setNodePropertySet(propertySet);
        this.structuredDataNode.setViews(new HashSet<VoView>());        
        this.structuredNodeDao.insertOrUpdate(this.structuredDataNode);        
        
        // add the URI to the node
        String uriPath = 
                this.nodeDao.queryNodeURIById(this.structuredDataNode.getNodeOid());        
        VOSpaceURI uri = QueryManagerUtils.composeURIFromPath(uriPath);
        // mapping Transfer Object
        this.result = VospaceTransferObjectMapper.
                populateStructuredDataNodeTO(this.structuredDataNode, uri, true);
        
        log.debug("End of IngestStructuredDataNodeCommand.execute()");

    }
    
    /*private String getStructuredDataNodeUriPath () {
        String containerUriPath = "";
        
        if (this.parentUriPath == null || this.parentUriPath.isEmpty()) {
            containerUriPath = this.structuredDataNodeTO.getName();
        } else {
            containerUriPath = this.parentUriPath + "/" + 
                    this.structuredDataNodeTO.getName();
        }
        
        return containerUriPath;
    }*/
    
    /**
     * Query VoCapability by uri.
     * @param voCapabilityId Input voCapability uri.
     * @return voCapability
     */
    @SuppressWarnings("unused")
    private VoCapability getVoCapabilityByUri(final String voCapabilityUri) {
        if (voCapabilityUri == null) {
            throw new IllegalArgumentException("IngestContainerNodeCommand.getVoCapabilityByUri() -> "
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
            throw new IllegalArgumentException("IngestContainerNodeCommand.getVoPropertyByUri() -> "
                    + "Input VoProperty Uri cannot be null.");
        }
        VoProperty persistentProperty = this.voPropertyDao.queryVoPropertyByUri(voPropertyUri);
        return persistentProperty;
    }
    
    /**
     * Query VoView by uri.
     * @param voViewUri Input VoView uri
     * @return VoView
     */
    @SuppressWarnings("unused")
    private VoView getVoViewByUri(final String voViewUri) {
        if (voViewUri == null) {
            throw new IllegalArgumentException("IngestContainerNodeCommand.getVoPropertyByUri() -> "
                    + "Input VoProperty Uri cannot be null.");
        }
        VoView persistentView = this.voViewDao.queryVoViewByUri(voViewUri);
        return persistentView;
    }

    @Override
    public Object getResult() {
        return this.result;
    }

}
