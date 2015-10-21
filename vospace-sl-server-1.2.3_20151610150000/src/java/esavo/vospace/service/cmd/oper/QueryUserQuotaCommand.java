package esavo.vospace.service.cmd.oper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class QueryUserQuotaCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryUserCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    private long quota;
    
    private UserTO user;
    
    public QueryUserQuotaCommand (UserTO user) {
        log.debug("Into QueryUserQuota()");
        this.user = user;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into QueryUserQuota().execute()");
        
        try {
            this.quota = 
                    this.iQueryManagerVospace.doQueryUserQuota(this.user.getName());
        } catch (Exception e) {
            throw new InternalFaultException("Error user [" + this.user 
                    + "] does not exist.");
        }
        
        log.debug("End of QueryUserQuota().execute()");   
    }

    @Override
    public Object getResult() {
        return this.quota;
    }

}
