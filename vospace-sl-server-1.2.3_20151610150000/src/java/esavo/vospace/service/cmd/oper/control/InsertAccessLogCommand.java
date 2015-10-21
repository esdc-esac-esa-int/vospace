package esavo.vospace.service.cmd.oper.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.oper.IVospaceCommand;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertAccessLogCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertAccessLogCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** AccessLog to be inserted. */
    private AccessLogTO accessLogTO;
    
    public InsertAccessLogCommand(final AccessLogTO log) {
        this.accessLogTO = log;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InsertAccessLogCommand.execute()");
        
        if (this.accessLogTO == null) {
            throw new VOSpaceException("Cannot insert new access log null.");
        }
        
        try {
            iQueryManagerVospace.doInsertAccessLog(this.accessLogTO);
            
        } catch (Exception e) {
            throw new VOSpaceException("Impossible to insert the given user.");
        }
    }

    @Override
    public Object getResult() {
        return null;
    }

}
