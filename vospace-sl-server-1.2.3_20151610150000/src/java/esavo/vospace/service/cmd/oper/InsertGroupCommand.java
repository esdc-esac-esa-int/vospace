package esavo.vospace.service.cmd.oper;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertGroupCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertGroupCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Group to be inserted. */
    private GroupTO groupTO;
    
    /**
     * Constructor.
     * @param groupTO
     */
    public InsertGroupCommand(GroupTO group) {
        log.debug("Into InsertGroupCommand()");
        this.groupTO = group;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InsertGroupCommand.execute()");
        
        try {
            iQueryManagerVospace.doInsertGroup(this.groupTO, true);
        } catch (RemoteException e) {
        }
        
        log.debug("End of InsertGroupCommand.execute()");
    }

    @Override
    public Object getResult() {
        // TODO Auto-generated method stub
        return null;
    }

}
