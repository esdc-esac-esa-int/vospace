package esavo.vospace.service.cmd.oper.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.oper.IVospaceCommand;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertMetadataRetrievalLogCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertMetadataRetrievalLogCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** TO to be inserted. */
    private MetadataRetrievalLogTO metadataLogTO;
    
    @SuppressWarnings("unused")
    private Boolean overwrite;
    
    public InsertMetadataRetrievalLogCommand (final MetadataRetrievalLogTO log, final Boolean overwrite) {
        //log.debug("Into InsertMetadataRetrievalLogCommand()");
        this.metadataLogTO = log;
        this.overwrite = overwrite;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        
        log.debug("Into InsertMetadataRetrievalLogCommand.execute()");
        
        if (this.metadataLogTO == null) {
            throw new VOSpaceException("Cannot insert new metadata retrieval log null.");
        }
        
        try {
            iQueryManagerVospace.doInsertMetadataRetrievalLogTO(this.metadataLogTO);
            
        } catch (Exception e) {
            throw new VOSpaceException("Impossible to insert the given log.");
        }
        
        log.debug("End of InsertMetadataRetrievalLogCommand.execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
