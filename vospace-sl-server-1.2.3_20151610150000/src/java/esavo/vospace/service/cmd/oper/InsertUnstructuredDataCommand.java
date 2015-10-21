package esavo.vospace.service.cmd.oper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.constants.ClientConstants;
import esavo.vospace.exceptions.QuotaExceededException;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.rmi.RMIVospaceClient;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.storage.LocalFileSystemStorageManager;

/**
 * Insert an UnstructuredDataNode into VOSpace.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class InsertUnstructuredDataCommand implements IVospaceCommand {

	/* Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertUnstructuredDataCommand.class);
    
    /* RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /* Local FileSystem Storage */
    protected LocalFileSystemStorageManager storage;
    
    /* Result of the command execution */
    private NodeTO nodeTO;
    
    /* Object parameter */
    private UserTO ownerTO;
    
    /* Result of the execution */
    private UnstructuredDataNodeTO nodeInserted;
    
    /**
     * Constructor
     * @param nodeTO
     */
    public InsertUnstructuredDataCommand (NodeTO nodeTO) {
        log.debug("Into InsertUnstructuredDataCommand()");
        this.nodeTO = nodeTO;
        this.ownerTO = this.nodeTO.getUserTO();
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        this.storage = new LocalFileSystemStorageManager();
        
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InsertUnstructuredDataCommand().execute()");
        
        //Size check
        long size = NodeUtils.retrieveNodeSize(this.nodeTO);
        long quota = this.nodeTO.getUserTO().getQuota();
        long qlimit = this.ownerTO.getQLimit();
        
        if (qlimit < (quota + size)) {
            log.debug("Quota limit " + qlimit + " exceeded: Current quota =" + quota + ", File size =" + size);
            throw new QuotaExceededException(ClientConstants.QUOTA_EXCEEDED);
        }
        
        insertUnstructuredDataNode();
        
        log.debug("End of InsertUnstructuredDataCommand().execute()");
    }
    
    /*
     * Insert a node into the storage.
     */
    private void insertUnstructuredDataNode () {
        
        try {
            
            this.nodeInserted = iQueryManagerVospace.doInsertUnstructuredDataNode(
                    (UnstructuredDataNodeTO) this.nodeTO, false);        
        
            //Create file into Storage System
            URI storageURI = getStorageURI();
            File f = new File(storageURI); //create an empty file at uriPath
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    InputStream is = new FileInputStream(f); //read the empty file
                    storage.putBytes(storageURI, is); //put the empty content into the new file created
                } catch (Exception e) {
                    log.debug("IO Exception creating an UnstructuredDataNode [ " 
                            + this.nodeTO.getURI() + " ].");
                    databaseRollBack(nodeInserted.getURI());                    
                }
            }
        
            //Update quota
            this.nodeInserted.setUserTO(updateQuota());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Updates the owner quota. 
     */
    private UserTO updateQuota() {
        long size = NodeUtils.retrieveNodeSize(this.nodeTO);
        UserTO user = null;
        try {
            user = VospaceServiceImpl.doUpdateUserQuota(this.ownerTO.getName(), size, true);
        } catch (VOSpaceException e) {
            log.debug("InsertUnstructuredDataCommand -> updateQuota(): ERROR!");
            e.printStackTrace();
        }
        return user;
    }
    
    /*
     * Compose the URI of the file in the storage system.
     */
    private URI getStorageURI () {
        URI storageURI = null;
        
        String filePath = this.nodeTO.getURI().getPath();
        String uri = VOSConstants.FILESYSTEM_PATH + filePath;
        
        try {
            storageURI = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        return storageURI;
    }
    
    /*
     * Remove the node already ingested into the database
     * in case of IO problems with the storage. 
     */
    private void databaseRollBack(VOSpaceURI nodeURI) {
    	log.debug("Roll back inserting UnstructuredDataNode with URI [" + nodeURI + "].");
    	
        if (iQueryManagerVospace != null && nodeURI != null) {
            try {
                iQueryManagerVospace.doRemoveNode(nodeURI);
            } catch (RemoteException e) {
                e.printStackTrace();
                log.debug("Impossible to execute Roll back for Node [" + nodeURI + "].");
            }
        }
    }

	@Override
	public Object getResult() {
		return this.nodeInserted;
	}

}
