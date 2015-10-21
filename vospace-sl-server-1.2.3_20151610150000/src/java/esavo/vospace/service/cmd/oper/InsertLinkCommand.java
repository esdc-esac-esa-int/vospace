package esavo.vospace.service.cmd.oper;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;
import esavo.vospace.service.utils.ConfigProperties;
import esavo.vospace.storage.LocalFileSystemStorageManager;

public class InsertLinkCommand implements IVospaceCommand {

	/** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertLinkCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Local FileSystem Storage */
    protected LocalFileSystemStorageManager storage;
    
    protected final String fsPath = 
            ConfigProperties.getProperty("vospace.file.system");
    
    /** Result of the command execution */
    private NodeTO nodeTO;
        
    public InsertLinkCommand(NodeTO nodeTO) {
        log.debug("Into InsertLinkCommand()");
        this.nodeTO = nodeTO;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        this.storage = new LocalFileSystemStorageManager();
        
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InsertLinkCommand().execute()");
        
        LinkNodeTO linkNodeTO = (LinkNodeTO) this.nodeTO;
        insertLinkNode(linkNodeTO, getStorageURI(this.nodeTO.getURI()));
        
        log.debug("End of InsertLinkCommand().execute()");
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
    
    private void insertLinkNode (LinkNodeTO node, URI storageURI) {
        
        LinkNodeTO nodeInserted = null;
        try {
            nodeInserted = iQueryManagerVospace.
                    doInsertLinkNode(node, false);
        } catch (RemoteException e) {                    
        }
        
        //create Link into Local FileSystem Storage            
        URI uriDest = getStorageURI(node.getTarget());
                
        try {
            this.storage.createLink(storageURI, uriDest);
        } catch (VOSpaceException e) {
            databaseRollBack(nodeInserted.getURI());
        }
        
        this.nodeTO = nodeInserted;
    }
    
    /*
     * Remove the node already ingested into the database
     * in case of IO problems with the storage. 
     */
    private void databaseRollBack(VOSpaceURI nodeURI) {
    	log.debug("Roll back inserting LinkNode with URI [" + nodeURI + "].");
    	
        if (iQueryManagerVospace != null && nodeURI != null) {
            try {
                iQueryManagerVospace.doRemoveNode(nodeURI);
            } catch (RemoteException e) {
                //e.printStackTrace();
                log.debug("Impossible to execute Roll back for Node [" + nodeURI + "].");
            }
        }
    }

	@Override
	public Object getResult() {
		return this.nodeTO;
	}

}
