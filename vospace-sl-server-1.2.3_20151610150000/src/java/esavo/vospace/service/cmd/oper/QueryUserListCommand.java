package esavo.vospace.service.cmd.oper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class QueryUserListCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryUserListCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** List of users */
    public List<UserTO> userList;
    
    /**
     * Constructor.
     */
    public QueryUserListCommand() {
        log.debug("Into QueryUserListCommand()");
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into QueryUserListCommand().execute()");
        
        try {
            this.userList = 
                    this.iQueryManagerVospace.doQueryUserList();
        } catch (Exception e) {
            throw new InternalFaultException("Error retrieving users "
                    + "from the system.");
        }
        
        log.debug("End of QueryUserListCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.userList;
    }

}
