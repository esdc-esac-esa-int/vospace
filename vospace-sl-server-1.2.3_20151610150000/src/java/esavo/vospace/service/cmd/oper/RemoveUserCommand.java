package esavo.vospace.service.cmd.oper;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class RemoveUserCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(RemoveUserCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    //protected final String fsPath = VOSConstants.FILESYSTEM_PATH;
    
    private UserTO userTO;
    
    public RemoveUserCommand (final UserTO userTO) {
        log.debug("Into RemoveUserCommand()");
        this.userTO = userTO;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into RemoveUserCommand().execute()");
                        
        try {
            this.iQueryManagerVospace.doRemoveUser(this.userTO);
        } catch (RemoteException e) {
        }
        
        log.debug("End of RemoveUserCommand().execute()");
    }

    @Override
    public Object getResult() {
        // TODO Auto-generated method stub
        return null;
    }

}
