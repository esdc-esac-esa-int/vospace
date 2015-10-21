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
import esac.archive.vospace.persistence.dao.IUnstructuredDataNodeDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.dao.IVoViewDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.UnstructuredDataNode;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoCapability;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VoView;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

/**
 * Ingest a UnstructuredDataNode into VOSpace.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestUnstructuredDataNode implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestStructuredDataNode.class);
        
    /** Vospace Unstructured Data Node DAO. */
    private IUnstructuredDataNodeDao unstructuredNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUnstructuredDataNodeDAO(); 
    
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
    private UnstructuredDataNodeTO unstructuredDataNodeTO;
    
    /** Transfer object result. */
    private UnstructuredDataNodeTO result;
    
    /** Ingested persistent object. */
    private UnstructuredDataNode unstructuredDataNode;
    
    /** Persistent UserTO */
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
    
    public IngestUnstructuredDataNode(final UnstructuredDataNodeTO unstructuredDataNodeTO,
            final Boolean overwrite) {        
        log.debug("Into IngestStructuredDataNode()");
        this.unstructuredDataNodeTO = unstructuredDataNodeTO;
        this.ownerTO = this.unstructuredDataNodeTO.getUserTO();
        // metadata about the UnstructuredDataNode
        //this.voCapabilityTOList = voCapabilityTOList;
        this.nodePropertyTOList = this.unstructuredDataNodeTO.getNodeProperties();
        //this.voViewTOList = voViewTOList;
        this.overwriteData = overwrite;
    }
    
    @Override
    public void execute() {
        
        log.debug("Into IngestUnstructuredDataNode().execute()");
        
        if (this.unstructuredDataNodeTO == null) {
            throw new IllegalArgumentException("IngestUnstructuredDataNodeCommand.execute() -> "
                    + "Input UnstructuredDataNode cannot be null.");
        }
        
        // Does this StructuredDataNode already exists??
        String path = QueryManagerUtils.getPath(this.unstructuredDataNodeTO.getURI());
        Node node = this.nodeDao.queryNodeByUri(path);        
        if ( node != null && !(node instanceof ContainerNode) ) {
            throw new UnsupportedOperationException("Node: [" + node.getNodeOid()
                    + "] already exists and is type of [" + node.getClass() + "]");
        } else if (node != null) {
            this.unstructuredDataNode = this.unstructuredNodeDao.queryUnstructuredDataNodeById(node.getNodeOid());
        }
        
        if ((unstructuredDataNode != null) && !this.overwriteData) {
            throw new UnsupportedOperationException("UnstructuredDataNode: [" + this.unstructuredDataNodeTO.getId()
                    + "] has already been ingested");
        } else if (this.unstructuredDataNode == null) {
            this.unstructuredDataNode = this.unstructuredNodeDao.getEmptyUnstructuredDataNode();
        }
        
        // retrieve UnstructuredDataNode owner (if exists)
        if (this.ownerTO == null) {
            throw new IllegalArgumentException("IngestUnstructuredDataNodeCommand.execute() -> "
                    + "Input User owner cannot be null.");
        }        
        Users ownerUser = this.userDao.queryUserByName(this.ownerTO.getName());
        if (ownerUser == null) {
            throw new UnsupportedOperationException("StructuredDataNode: [" + this.unstructuredDataNodeTO.getId()
                    + "] without User owner cannot be inserted."); 
        }
        
        this.unstructuredDataNode.setName(this.unstructuredDataNodeTO.getURI().getName());
        this.unstructuredDataNode.setUsers(ownerUser);
        
        // retrieve unstructured data node parent (if any)
        // allowed not parent for the UnstructuredDataNode
        String parentUriPath = QueryManagerUtils.getParentPath(this.unstructuredDataNodeTO.getURI());
        if (parentUriPath != null && !parentUriPath.isEmpty()) {
            Node containerParent = this.nodeDao.queryNodeByUri(parentUriPath);
            if ((containerParent != null) && (containerParent instanceof ContainerNode)) {
                this.unstructuredDataNode.setContainerNode(((ContainerNode) containerParent)); // Add parent
            } else if (containerParent instanceof LinkNode) {
                throw new IllegalArgumentException("IngestUnstructuredDataNodeCommand.execute() -> "
                        + "LinkFound for uri [" + parentUriPath + "].");
            } else {
                throw new IllegalArgumentException("IngestUnstructuredDataNodeCommand.execute() -> "
                        + "ContainerNotFound for uri [" + parentUriPath + "].");
            } 
        }
        
        // Attach Capabilities to UnstructuredDataNode (if any)
        /*Set<VoCapability> capabilitySet;
        if (this.overwriteData) {
            capabilitySet = new HashSet<VoCapability>();
        } else {
            capabilitySet = this.unstructuredDataNode.getNodeCapabilities();
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
        
        // Attach Properties to UnstructuredDataNode (if any)
        Set<NodeProperty> propertySet;
        if (this.overwriteData) {
            propertySet = new HashSet<NodeProperty>();
        } else {
            propertySet = this.unstructuredDataNode.getNodePropertySet();
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
                nodeProperty.setNode(this.unstructuredDataNode);
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
            viewSet = this.unstructuredDataNode.getViews();
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
        
        this.unstructuredDataNode.setNodeCapabilities(new HashSet<VoCapability>());
        this.unstructuredDataNode.setNodePropertySet(propertySet);
        this.unstructuredDataNode.setViews(new HashSet<VoView>());                
        this.unstructuredNodeDao.insertOrUpdate(this.unstructuredDataNode);        

        // add the URI to the node        
        VOSpaceURI uri = this.unstructuredDataNodeTO.getURI();
        
        if (uri == null) {
            String uriPath = 
                    this.nodeDao.queryNodeURIById(this.unstructuredDataNode.getNodeOid());        
            uri = QueryManagerUtils.composeURIFromPath(uriPath);
        }        
        
        // mapping Transfer Object
        this.result = VospaceTransferObjectMapper.
                populateUnstructuredDataNodeTO(this.unstructuredDataNode, uri, true);
                
        log.debug("End of IngestUnstructuredDataNodeCommand.execute()");

    }
    
    /**
     * Query VoCapability by uri.
     * @param voCapabilityId Input voCapability uri.
     * @return voCapability
     */
    @SuppressWarnings("unused")
    private VoCapability getVoCapabilityByUri(final String voCapabilityUri) {
        if (voCapabilityUri == null) {
            throw new IllegalArgumentException("IngestUnstructuredDataNodeCommand.getVoCapabilityByUri() -> "
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
            throw new IllegalArgumentException("IngestUnstructuredDataNodeCommand.getVoPropertyByUri() -> "
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
            throw new IllegalArgumentException("IngestUnstructuredDataNodeCommand.getVoPropertyByUri() -> "
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
