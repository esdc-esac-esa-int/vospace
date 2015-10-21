package esavo.vospace.service.cmd.oper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class QueryUserControlCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryUserControlCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** user control */
    private UserControlTO user;
    
    /** Group name */
    private String uname;
    
    public QueryUserControlCommand(final String userName) {
        log.debug("Into QueryUserControlCommand()");
        this.uname = userName;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into QueryUserControlCommand().execute()");
        
        try {
            this.user = 
                    this.iQueryManagerVospace.doQueryUserControlTO(this.uname);
        } catch (Exception e) {
            throw new InternalFaultException("Error user [" + this.uname 
                    + "] does not exist." + e.getMessage());
        }
        
        log.debug("End of QueryUserControlCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.user;
    }

}
