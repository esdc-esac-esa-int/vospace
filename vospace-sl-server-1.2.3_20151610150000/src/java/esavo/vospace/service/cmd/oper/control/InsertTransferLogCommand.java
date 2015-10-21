package esavo.vospace.service.cmd.oper.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.oper.IVospaceCommand;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertTransferLogCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertTransferLogCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** TO to be inserted. */
    private TransferLogTO transferLogTO;
    
    public InsertTransferLogCommand(final TransferLogTO log) {
        //log.debug("Into InsertTransferLogCommand()");
        this.transferLogTO = log;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        
        log.debug("Into InsertTransferLogCommand.execute()");
        
        if (this.transferLogTO == null) {
            throw new VOSpaceException("Cannot insert new transfer log null.");
        }
        
        try {
            iQueryManagerVospace.doInsertTransferLogTO(this.transferLogTO);
            
        } catch (Exception e) {
            throw new VOSpaceException("Impossible to insert the given log.");
        }
        
        log.debug("End of InsertTransferLogCommand.execute()");

    }

    @Override
    public Object getResult() {
        return null;
    }

}
