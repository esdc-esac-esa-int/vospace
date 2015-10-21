package esavo.vospace.service.cmd.oper.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.oper.IVospaceCommand;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertDataMngLogCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertDataMngLogCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** TO to be inserted. */
    private DataMngLogTO dataMngLogTO;
    
    public InsertDataMngLogCommand (final DataMngLogTO log) {
        this.dataMngLogTO = log;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        
        log.debug("Into IngestDataMngLogCommand.execute()");
        
        if (this.dataMngLogTO == null) {
            throw new VOSpaceException("Cannot insert new data management log null.");
        }
        
        try {
            iQueryManagerVospace.doInsertDataMngLog(this.dataMngLogTO);
            
        } catch (Exception e) {
            throw new VOSpaceException("Impossible to insert the given log.");
        }
        
        log.debug("End of IngestDataMngLogCommand.execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
