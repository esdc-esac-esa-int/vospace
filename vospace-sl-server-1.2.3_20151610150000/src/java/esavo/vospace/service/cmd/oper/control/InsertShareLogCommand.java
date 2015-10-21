package esavo.vospace.service.cmd.oper.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.oper.IVospaceCommand;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertShareLogCommand implements IVospaceCommand {
    
    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertShareLogCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** AccessLog to be inserted. */
    private ShareLogTO shareLogTO;
        
    public InsertShareLogCommand (final ShareLogTO log) {
        this.shareLogTO = log;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InsertAccessLogCommand.execute()");
        
        if (this.shareLogTO == null) {
            throw new VOSpaceException("Cannot insert new share log null.");
        }
        
        try {
            iQueryManagerVospace.doInsertShareLogTO(this.shareLogTO);
            
        } catch (Exception e) {
            throw new VOSpaceException("Impossible to insert the given user.");
        }
    }

    @Override
    public Object getResult() {
        return null;
    }

}
