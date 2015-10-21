package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IContainerNodeDao;
import esac.archive.vospace.persistence.dao.INodeDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.dao.IVoViewDao;
import esac.archive.vospace.persistence.model.ContainerNode;
import esac.archive.vospace.persistence.model.LinkNode;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.model.VoCapability;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VoView;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;
import esavo.vospace.dl.querymanager.util.QueryManagerUtils;

/**
 * Command which will ingest a ContainerNode into the database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestContainerNodeCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestContainerNodeCommand.class);
    
    /** Vospace Container Node DAO. */
    private IContainerNodeDao containerNodeDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getContainerNodeDAO(); 
    
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
    private ContainerNodeTO containerNodeTO;
    
    /** Transfer object result. */
    private ContainerNodeTO result;
    
    /** Ingested persistent object. */
    private ContainerNode containerNode;
    
    /** Persistent User owner name */
    private UserTO ownerTO;
    
    /** List of associated voCapabilities for input ContainerNode. */
    @SuppressWarnings("unused")
    private List<VoCapabilityTO> voCapabilityTOList;
    
    /** List of associated voViews for input ContainerNode. */
    @SuppressWarnings("unused")
    private List<VoViewTO> voViewTOList;
    
    /** List of associated nodeProperties for input ContainerNode */
    private List<NodePropertyTO> nodePropertyTOList;
    
    /** Overwrite information flag */
    private Boolean overwriteData;
    /**
     * Constructor
     * @param containerNodeTO
     * @param parent
     * @param inputCapabilityIds
     * @param inputProperties
     * @param inputViewId
     * @param inputChildren
     * @param overwrite
     */
    public IngestContainerNodeCommand(final ContainerNodeTO containerNodeTO,
            final Boolean overwrite) {        
        log.debug("Into IngestContainerNodeCommand()");
        this.containerNodeTO = containerNodeTO;
        this.ownerTO = this.containerNodeTO.getUserTO();
        // metadata about the ContainerNode
        this.nodePropertyTOList = this.containerNodeTO.getNodeProperties();
        //this.voCapabilityTOList = voCapabilityTOList;
        //this.voViewTOList = voViewTOList;
        this.overwriteData = overwrite;
    }
    
    @Override
    public void execute() {
        log.debug("Into IngestContainerNodeCommand().execute()");
        
        if (this.containerNodeTO == null) {
            throw new IllegalArgumentException("IngestContainerNodeCommand.execute() -> "
                    + "Input ContainerNode cannot be null.");
        }
        
        // Does this ContainerNode already exists??
        String path = QueryManagerUtils.getPath(this.containerNodeTO.getURI());
        Node node = this.nodeDao.queryNodeByUri(path);
        if ( node != null && !(node instanceof ContainerNode) ) {
            throw new UnsupportedOperationException("Node: [" + node.getNodeOid()
                    + "] already exists and is type of [" + node.getClass() + "]");
        } else if (node != null) {
            this.containerNode = this.containerNodeDao.queryContainerNodeById(node.getNodeOid());
        }
        
        if ((containerNode != null) && !this.overwriteData) {
            throw new UnsupportedOperationException("ContainerNode: [" + this.containerNodeTO.getURI()
                    + "] has already been ingested");
        } else if (this.containerNode == null) {
            this.containerNode = this.containerNodeDao.getEmptyContainerNode();
        }
        
        // retrieve ContainerNode owner (if exists)
        if (this.ownerTO == null) {
            throw new IllegalArgumentException("IngestContainerNodeCommand.execute() -> "
                    + "Input User owner cannot be null.");
        }        
        Users ownerUser = this.userDao.queryUserByName(this.ownerTO.getName());
        if (ownerUser == null) {
            throw new UnsupportedOperationException("ContainerNode: [" + this.containerNodeTO.getId()
                    + "] without User owner cannot be inserted."); 
        }
                
        // retrieve container parent node (if any)
        // allowed not parent for the ContainerNode
        String parentUriPath = QueryManagerUtils.getParentPath(this.containerNodeTO.getURI());
        if (parentUriPath != null && !parentUriPath.isEmpty()) {
            Node containerParent = this.nodeDao.queryNodeByUri(parentUriPath);
            if ((containerParent != null) && (containerParent instanceof ContainerNode)) {
                this.containerNode.setContainerNode(((ContainerNode) containerParent)); // Add parent
            } else if (containerParent instanceof LinkNode) {
                throw new IllegalArgumentException("IngestContainerNodeCommand.execute() -> "
                        + "LinkFound for uri [" + parentUriPath + "].");
            } else {
                throw new IllegalArgumentException("IngestContainerNodeCommand.execute() -> "
                        + "ContainerNotFound for uri [" + parentUriPath + "].");
            } 
        }
        
        // Attach Properties to ContainerNode (if any)
        Set<NodeProperty> propertySet;
        if (this.overwriteData) {
            propertySet = new HashSet<NodeProperty>();
        } else {
            propertySet = this.containerNode.getNodePropertySet();
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
                String value = nodePropertyTO.getValue();
                if (uri.compareTo(GeneralConstants.PROPERTY_LENGTH) == 0 &&
                        value.compareTo("4096") != 0) {
                    value = "4096";
                    nodePropertyTO.setValue(value);
                }
                nodeProperty.setValue(value);
                nodeProperty.setNode(this.containerNode);
                if (nodePropertyTO != null && !nodePropertyTO.getNill()) {
                    propertySet.add(nodeProperty);
                }
            }
        }
        
        // Attach Capabilities to ContainerNode (if any)
        /*Set<VoCapability> capabilitySet;
        if (this.overwriteData) {
            capabilitySet = new HashSet<VoCapability>();
        } else {
            capabilitySet = this.containerNode.getNodeCapabilities();
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
        
        // Attach Views to ContainerNode (if any)
        /*Set<VoView> viewSet;
        if (this.overwriteData) {
            viewSet = new HashSet<VoView>();
        } else {
            viewSet = this.containerNode.getViews();
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
        
        this.containerNode.setName(this.containerNodeTO.getURI().getName());
        this.containerNode.setUsers(ownerUser);
        this.containerNode.setNodeCapabilities(new HashSet<VoCapability>());
        this.containerNode.setNodePropertySet(propertySet);
        this.containerNode.setViews(new HashSet<VoView>());        
        this.containerNodeDao.insertOrUpdate(this.containerNode);
        
        // add the URI to the node
        String uriPath = 
                this.nodeDao.queryNodeURIById(this.containerNode.getNodeOid());        
        VOSpaceURI uri = QueryManagerUtils.composeURIFromPath(uriPath);
        
        this.result = VospaceTransferObjectMapper
                .populateContainerNodeTO(this.containerNode, uri, true, true);
        
        log.debug("End of IngestContainerNodeCommand.execute()");
    }
    
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
