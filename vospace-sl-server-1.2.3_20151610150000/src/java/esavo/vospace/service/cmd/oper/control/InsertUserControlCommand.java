package esavo.vospace.service.cmd.oper.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.cmd.oper.IVospaceCommand;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class InsertUserControlCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(InsertUserControlCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Group to be inserted. */
    private UserControlTO userTO;
    
    public InsertUserControlCommand(final UserControlTO user) {
        log.debug("Into InsertUserControlCommand()");
        this.userTO = user;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    public InsertUserControlCommand(final UserTO user) {        
        this.userTO = new UserControlTO();
        this.userTO.setName(user.getName());
        this.userTO.setSurname(user.getFullName());
        this.userTO.setRegisterDate(user.getCreationDate());
        this.userTO.setMail(user.getMail());
        
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into InsertUserControlCommand.execute()");
        
        if (this.userTO == null) {
            throw new VOSpaceException("Cannot insert new user null.");
        }
        
        try {
            iQueryManagerVospace.doInsertUserControlTO(this.userTO);
            
        } catch (Exception e) {
            throw new VOSpaceException("Impossible to insert the given user.");
        }
        
        log.debug("End of InsertUserControlCommand.execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
