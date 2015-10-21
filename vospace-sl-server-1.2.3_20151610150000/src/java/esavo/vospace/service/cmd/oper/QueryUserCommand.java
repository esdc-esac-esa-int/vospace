package esavo.vospace.service.cmd.oper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class QueryUserCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryUserCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Group manager */
    private UserTO user;
    
    /** Group name */
    private String name;
    
    public QueryUserCommand(final String userName) {
        log.debug("Into QueryUserCommand()");
        this.name = userName;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into QueryUserCommand().execute()");
        
        try {
            this.user = 
                    this.iQueryManagerVospace.doQueryUser(this.name);
        } catch (Exception e) {
            throw new InternalFaultException("Error user [" + this.name 
                    + "] does not exist.");
        }
        
        log.debug("End of QueryUserCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.user;
    }

}
