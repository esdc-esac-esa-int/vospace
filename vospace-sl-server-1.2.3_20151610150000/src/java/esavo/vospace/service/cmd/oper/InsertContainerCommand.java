package esavo.vospace.service.cmd.oper;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.constants.ClientConstants;
import esavo.vospace.exceptions.QuotaExceededException;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InvalidURIException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.rmi.RMIVospaceClient;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.storage.LocalFileSystemStorageManager;

public class InsertContainerCommand implements IVospaceCommand {

	/* Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertContainerCommand.class);
    
    /* RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /* Local FileSystem Storage */
    protected LocalFileSystemStorageManager storage;
    
    /* Object parameter */
    private NodeTO nodeTO;
    
    /* Object parameter */
    private UserTO ownerTO;
    
    /* Result of the command execution */
    private ContainerNodeTO nodeInserted;
    
    private final int nodeSize = 4096; 
    
    /**
     * Constructor.
     * @param nodeTO
     */
    public InsertContainerCommand(final NodeTO nodeTO) {
        log.debug("Into InsertContainerCommand()");
        this.nodeTO = nodeTO;
        this.ownerTO = this.nodeTO.getUserTO();
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        this.storage = new LocalFileSystemStorageManager();
        
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InserContainerCommand().execute()");

        //Size check
        long quota = this.ownerTO.getQuota();
        long qlimit = this.ownerTO.getQLimit();
        
        if (qlimit < (quota + nodeSize)) {
            log.debug("Quota limit " + qlimit + " exceeded: Current quota =" + quota);
            throw new QuotaExceededException(ClientConstants.QUOTA_EXCEEDED);
        }

        insertContainerNode();
        
        log.debug("End of InserContainerCommand().execute()");
    }
    
    /*
     * Insert a node into the storage.
     */
    private void insertContainerNode () {
        
        try {
            
            this.nodeInserted = iQueryManagerVospace.doInsertContainerNode(
                    (ContainerNodeTO) this.nodeTO, true);        
            
            //create Container into Local FileSystem Storage and update quota
            URI uri = getStorageURI();
            this.storage.createContainer(uri);
            
            this.nodeInserted.setUserTO(updateQuota());
        } catch (Exception e) {
            e.printStackTrace();
            //databaseRollBack();
        }
    }
    
    /*
     * Updates the owner quota. 
     */
    private UserTO updateQuota() {
        UserTO user = null;
        try {
            user = VospaceServiceImpl.doUpdateUserQuota(this.ownerTO.getName(), (long) nodeSize, true);
        } catch (VOSpaceException e) {
            log.debug("InsertUnstructuredDataCommand -> updateQuota(): ERROR!");
            e.printStackTrace();
        }
        return user;
    }
    
    /*
     * Remove the node already ingested into the database
     * in case of IO problems with the storage. 
     */
    private void databaseRollBack() {
        VOSpaceURI nodeURI = this.nodeTO.getURI();
    	log.debug("Roll back inserting ContainerNode with URI [" + nodeURI + "].");
    	
        if (iQueryManagerVospace != null && nodeURI != null) {
            try {
                iQueryManagerVospace.doRemoveNode(nodeURI);
            } catch (RemoteException e) {
                //e.printStackTrace();
                log.debug("Impossible to execute Roll back for Node [" + nodeURI + "].");
            }
        }
    }
    
    /*
     * Compose the URI of the file in the storage system.
     */
    private URI getStorageURI () 
            throws InvalidURIException {
        
        URI storageURI = null;
        String uri = VOSConstants.FILESYSTEM_PATH;                
        VOSpaceURI nodeURI = this.nodeTO.getURI();
        if (nodeURI == null) {
            throw new InvalidURIException("URI cannot be null.");
        }
        
        if (nodeURI.toString().isEmpty()) {
            uri = VOSConstants.FILESYSTEM_PATH;
        }else {
            String filePath = this.nodeTO.getURI().getPath();
            uri = VOSConstants.FILESYSTEM_PATH + filePath;
        }
            
        try {
            storageURI = new URI(uri);
        } catch (URISyntaxException e) {
            throw new InvalidURIException("Failed to build the URI.");
        }
        
        return storageURI;
    }
    
    @Override
    public Object getResult() {
        return this.nodeInserted;
    }

}
