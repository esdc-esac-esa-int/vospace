package esavo.vospace.service.cmd.oper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceNodeType;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.service.VospaceServiceImpl;
import esavo.vospace.service.rmi.RMIVospaceClient;
import esavo.vospace.service.utils.VOSConstants;
import esavo.vospace.service.utils.nodes.NodeUtils;
import esavo.vospace.storage.LocalFileSystemStorageManager;

/**
 * Remove a node from Vospace database and filesystem
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class RemoveNodeCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(RemoveNodeCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Local File System */
    protected LocalFileSystemStorageManager fs;
    
    /** URI of node to be removed. */
    private VOSpaceURI uri;
    
    /** User name */
    private String userName;
    
    /** NodeTO node to be removed. */
    private NodeTO nodeTO;
    
    /**
     * Constructor.
     * @param node to be removed.
     */
    public RemoveNodeCommand (final NodeTO node) {
        log.debug("Into RemoveNodeCommand()");
        this.nodeTO = node;
        this.uri = node.getURI();
        this.userName = this.nodeTO.getUserTO().getName();
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        this.fs = new LocalFileSystemStorageManager();
    }
    
    @Override
    public void execute() throws InternalFaultException {
        log.debug("Into RemoveNodeCommand().execute()");
        
        try {
            
            iQueryManagerVospace.doRemoveNode(this.uri);                
            
            URI fsURI = getFileSystemURI(VOSConstants.FILESYSTEM_PATH + uri.getPath());        
            deleteTreeNode(fsURI);
            
            //Update quota            
            if (this.nodeTO.getType() == VOSpaceNodeType.CONTAINER_NODE) {                
                VospaceServiceImpl.doUpdateUserQuota(this.userName);
            } else {
                long size = NodeUtils.retrieveNodeSize(this.nodeTO);
                VospaceServiceImpl.doUpdateUserQuota(this.userName, size, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("InternalFaultException.Database");
            throw new InternalFaultException("RemoveNodeCommand.execute() -> ERROR!: " + e.getMessage());
        }
        log.debug("End of RemoveNodeCommand().execute()");
    }
    
    /**
     * Compose the FileSystem URI to remove the node.
     * @param path to generate the URI.
     * @return URI of then node
     */
    private URI getFileSystemURI (String path) {
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException e) {
            log.debug("URISyntaxException.FileSystem");
        }
        return uri;
    }
    
    /*
     * Recursive call to delete nodes from a VOSpaceURI
     */
    private void deleteTreeNode (URI uri) throws VOSpaceException {
        List<URI> childs = fs.listFiles(uri);
        boolean success = true;
        if (childs != null && !childs.isEmpty()) { //is a Directory
            for (URI uriChild : childs) {
                deleteTreeNode(uriChild); //recursive call
            }
            success = fs.removeBytes(uri);
        } else {
            success = fs.removeBytes(uri);
        }
        if (!success)
            throw new VOSpaceException("File " + uri + " could not be deleted.");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
