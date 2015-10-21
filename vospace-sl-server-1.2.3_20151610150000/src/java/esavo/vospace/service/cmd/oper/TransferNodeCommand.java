package esavo.vospace.service.cmd.oper;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceNodeType;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.constants.GeneralConstants;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.rmi.RMIVospaceClient;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.storage.LocalFileSystemStorageManager;

/**
 * Transfer a Node within the service.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class TransferNodeCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(TransferNodeCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    private VospaceServiceImpl vospaceService;
    
    /** Local FileSystem Storage */
    protected LocalFileSystemStorageManager storage;
    
    protected final String fsPath = VOSConstants.FILESYSTEM_PATH;
    
    /** Source Node Transfer Object. */
    private NodeTO sourceNode;
    
    /** Target Node Transfer Object. */
    private NodeTO targetNode;
    
    /** Target URI to perform the transfer. */
    private VOSpaceURI targetURI;
    
    /** Target URI to perform the source. */
    private VOSpaceURI sourceURI;
    
    /** Result URI */
    private VOSpaceURI resultURI;
    
    /** Determines the transfer type: copy and move. */
    private Boolean transfer;
    
    private UserTO user;
    
    /**
     * Constructor.
     * @param source
     * @param target
     * @param typeTransfer
     */
    public TransferNodeCommand(NodeTO source, final NodeTO target, 
            final Boolean typeTransfer, UserTO user) {
        log.debug("Into TransferNodeCommand()");
        this.sourceNode = source;
        this.targetNode = target;
        this.sourceURI = this.sourceNode.getURI();
        this.targetURI = this.targetNode.getURI();
        this.transfer = typeTransfer;
        this.user = user;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        this.storage = new LocalFileSystemStorageManager();
        this.vospaceService = new VospaceServiceImpl();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into TransferNodeCommand().execute()");
        
        if (this.sourceNode == null) {
            throw new IllegalArgumentException("TransferNodeCommand.execute() -> "
                    + "Input Source Node cannot be null.");
        }
        
        if (this.targetURI == null) {
            throw new IllegalArgumentException("TransferNodeCommand.execute() -> "
                    + "Input Target URI cannot be null.");
        }
        
        // check whether Target is not a child of Source. Not a movement into itself.
        isChildOf(this.targetURI, this.sourceURI);
        
        if (!this.transfer) { // Move            
            moveNode();            
        } else { // Copy
            copyNode(this.sourceNode, this.targetURI);
        }
                
        // storage file system transfer: move or copy
        transferStorageOperation(sourceURI, this.targetURI);
        
        log.debug("End of TransferNodeCommand().execute()");
    }
    
    /*
     * Make the movement (copy/move) into Storage System 
     */
    private void transferStorageOperation (VOSpaceURI sourceURI, VOSpaceURI targetURI) 
            throws InternalFaultException {
        
        URI source = getStorageURI(sourceURI);
        VOSpaceURI destURI = 
                new VOSpaceURI(targetURI + File.separator + sourceURI.getName());
        URI target = getStorageURI(destURI);
        try {
            if (this.transfer) {
                this.storage.copyBytes(source, target);
            } else {
                this.storage.moveBytes(source, target);
            }        
        } catch (VOSpaceException e) {
            databaseRollBack(this.resultURI);
            throw new InternalFaultException("InternalFault: Source [" + sourceURI + "]"
                    + " could not be transfered into Target [" + targetURI + "]");
        }
    }
    
    /*
     * Compose the URI of the file in the storage system.
     */
    private URI getStorageURI (VOSpaceURI nodeURI) {
        URI storageURI = null;
        
        String filePath = nodeURI.getPath();
        String uri = fsPath + filePath;
        
        try {
            storageURI = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        return storageURI;
    }
    
    private void databaseRollBack(VOSpaceURI cloneURI) {
        try {
            this.iQueryManagerVospace.doRemoveNode(cloneURI);
        } catch (RemoteException e) {
        }
    }
    
    private void moveNode () throws InternalFaultException {
                        
        try {
            
            this.sourceNode.setParent((ContainerNodeTO) this.targetNode);            
            NodeTO insertedNode = iQueryManagerVospace.doUpdateNodeParent(this.sourceNode);
            
            if (insertedNode != null) {
                this.resultURI = insertedNode.getURI();
            }
        } catch (RemoteException r) {
            throw new InternalFaultException("InternalFault: Source [" + this.sourceURI + "]"
                    + " could not be transfered into Target [" + this.targetURI + "]");
        }        
    }
    
    /*
     * Copy the following properties to the clone node:
     * Length, Description, MDate and BDate.
     */
    private void assignProperties(NodeTO clone, NodeTO node) {
        //retrieve length and description values from the node to be move/copied
        NodePropertyTO lengthP = 
                NodeUtils.retrieveNodeProperty(node, GeneralConstants.PROPERTY_LENGTH);
        NodePropertyTO descriptionP = 
                NodeUtils.retrieveNodeProperty(node, GeneralConstants.PROPERTY_DESCRIPTION);
        
        //Create basic properties
        List<NodePropertyTO> list = new ArrayList<NodePropertyTO>();
        NodePropertyTO length = null;
        if (lengthP != null)
            length = NodeUtils.createLengthProperty(Long.parseLong(lengthP.getValue()), false);
        else
            length = NodeUtils.createLengthProperty(0, false);
        list.add(length);
        
        NodePropertyTO description = null;
        if (descriptionP != null)
            description = NodeUtils.createDescriptionProperty(descriptionP.getValue(), false);
        else
            description = NodeUtils.createDescriptionProperty("", false);
        list.add(description);
        
        NodePropertyTO MDate = NodeUtils.createBDateProperty(new Date(), false);
        NodePropertyTO BDate = NodeUtils.createMDateProperty(new Date(), false);
        list.add(MDate);
        list.add(BDate);
        
        clone.setNodeProperties(list);
    }
    
    /*
     * Recursive method to copy the source node and all its children
     * in target.
     */
    private void copyNode (NodeTO nodeSource, VOSpaceURI target) 
            throws VOSpaceException {
        
        NodeTO insertedNode = null;        
            
        switch (nodeSource.getType()) {
        case CONTAINER_NODE:                
            ContainerNodeTO cloneContainer = new ContainerNodeTO();                
            cloneContainer.setUri(composeURI(nodeSource.getURI(), target));
            //the logged used is the new owner of the node to be copied/moved
            cloneContainer.setUserTO(this.user);
            assignProperties(cloneContainer, nodeSource);
            cloneContainer.setCapabilities(nodeSource.getCapabilities());

            insertedNode = vospaceService.doInsertContainerNode(cloneContainer);

            List<NodeTO> children = ((ContainerNodeTO)nodeSource).getNodeList();
            if (children != null && !children.isEmpty()) {
                for (NodeTO child : children) {
                    NodeTO nodeChild = child;
                    if(child.getType().compareTo(VOSpaceNodeType.CONTAINER_NODE) == 0){
                        VOSpaceURI uri = child.getURI();
                        nodeChild = NodeUtils.getNode(uri, true);
                    }
                    copyNode(nodeChild, insertedNode.getURI());
                }
            }
            break;
        case LINK_NODE:
            LinkNodeTO cloneLink = new LinkNodeTO();
            cloneLink.setTarget(((LinkNodeTO) nodeSource).getTarget());
            cloneLink.setUri(composeURI(nodeSource.getURI(), target));
            cloneLink.setUserTO(this.user);
            //cloneLink.setNodeProperties(nodeSource.getNodeProperties());
            assignProperties(cloneLink, nodeSource);
            cloneLink.setCapabilities(nodeSource.getCapabilities());

            insertedNode = vospaceService.doInsertLinkNode(cloneLink);

            break;
        case UNSTRUCTURED_DATA_NODE:                
            UnstructuredDataNodeTO cloneUnstructuredNode = new UnstructuredDataNodeTO();
            cloneUnstructuredNode.setUri(composeURI(nodeSource.getURI(), target));
            cloneUnstructuredNode.setUserTO(this.user);
            //cloneUnstructuredNode.setNodeProperties(nodeSource.getNodeProperties());
            assignProperties(cloneUnstructuredNode, nodeSource);
            cloneUnstructuredNode.setCapabilities(nodeSource.getCapabilities());

            insertedNode = vospaceService.doInsertUnstructuredDataNode(cloneUnstructuredNode);

            break;
        case STRUCTURED_DATA_NODE:
            StructuredDataNodeTO cloneStructuredNode = new StructuredDataNodeTO();
            cloneStructuredNode.setUri(composeURI(nodeSource.getURI(), target));
            cloneStructuredNode.setUserTO(this.user);
            //cloneStructuredNode.setNodeProperties(nodeSource.getNodeProperties());
            assignProperties(cloneStructuredNode, nodeSource);
            cloneStructuredNode.setCapabilities(nodeSource.getCapabilities());

            insertedNode = vospaceService.doInsertStructuredDataNode(cloneStructuredNode);

            break;
        case UNKNOWN:
            break;
        }

        if (insertedNode != null) {
            this.resultURI = insertedNode.getURI();
        }
      
    }
    
    /*
     * Compose a new URI as result og moving the source node into the target.
     */
    private VOSpaceURI composeURI (VOSpaceURI sourceURI, VOSpaceURI targetURI) {        
        String updatedURI = targetURI + File.separator + sourceURI.getName();        
        VOSpaceURI uri = new VOSpaceURI(updatedURI);        
        return uri;
    }
    
    /*
     * Get path from URI without bar at the start
     */
    private String[] getSplitPathFromURI(String path) {
        
        String pathWithoutBar = "";
        if (path.length() > 1) {
            pathWithoutBar = path.substring(1);
        }
        
        String[] split = pathWithoutBar.split(File.separator);
        
        return split;
    }
    
    /*
     * Check whether nodeURI1 is child of nodeURI2.
     */
    public void isChildOf(VOSpaceURI targetURI, VOSpaceURI sourceURI)
            throws InternalFaultException {
                
        String[] parentURI = getSplitPathFromURI(sourceURI.getPath());
        String[] childURI = getSplitPathFromURI(targetURI.getPath());
        
        boolean isChild = true;
        if (parentURI.length <  childURI.length) {
            int i = 0;
            for (String element : parentURI) {
                if (element.compareTo(childURI[i++]) != 0) {
                    isChild = false;
                }
            }
        } else
            isChild = false;
        
        if (isChild) {
            throw new InternalFaultException("InternalFault: Target [" + targetURI + "]"
                    + " is child of source [" + sourceURI + "]");
        }
    }

    @Override
    public Object getResult() {
        return null;
    }

}
