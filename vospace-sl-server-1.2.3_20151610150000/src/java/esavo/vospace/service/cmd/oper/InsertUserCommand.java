package esavo.vospace.service.cmd.oper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertUserCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertUserCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Group to be inserted. */
    private UserTO userTO;
    
    public InsertUserCommand(final UserTO user) {
        log.debug("Into InsertUserCommand()");
        this.userTO = user;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InsertUserCommand.execute()");
        
        if (this.userTO == null) {
            throw new VOSpaceException("Cannot insert new user null.");
        }
        
        try {
            iQueryManagerVospace.doInsertUser(this.userTO);
            
        } catch (Exception e) {
            log.debug("Impossible to insert the given user.");
            //throw new VOSpaceException("Impossible to insert the given user.");
        }
        
        log.debug("End of InsertGroupCommand.execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
