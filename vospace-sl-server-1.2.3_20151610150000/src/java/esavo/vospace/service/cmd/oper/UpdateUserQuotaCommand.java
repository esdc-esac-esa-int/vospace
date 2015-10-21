package esavo.vospace.service.cmd.oper;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class UpdateUserQuotaCommand implements IVospaceCommand {

    /* Commons-logging variable. */
    private static Log log = LogFactory.getLog(UpdateUserQuotaCommand.class);
    
    /* RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /* User name */
    private final String userName;
    
    /* Size to update the quota */
    private Long size;
    
    /* True to add, otherwise false */
    private Boolean add;
    
    /* Determines a partial quota update */
    private Boolean auto;
    
    /* User result after updating its quota*/
    private UserTO result;
    
    /**
     * Contructor.
     * @param userName
     * @param size
     * @param add
     */
    public UpdateUserQuotaCommand (String userName, Long size, Boolean add) {
        log.debug("Into UpdateUserQuota()");
        this.userName = userName;
        this.size = size;
        this.add = add;
        this.auto = false;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    /**
     * Constructor.
     * @param userName
     */
    public UpdateUserQuotaCommand (String userName) {
        log.debug("Into UpdateUserQuota()");
        this.userName = userName;
        this.auto = true;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into UpdateUserQuota().execute()");
        
        try {
            if (auto) {
                long quota = iQueryManagerVospace.doQueryUserQuota(userName);
                this.result = iQueryManagerVospace.doUpdateUserQuota(this.userName, quota);            
            } else {
                this.result = iQueryManagerVospace.doUpdateUserQuota(this.userName, size, this.add);
            }        
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
        log.debug("End of UpdateUserQuota().execute()");
    }

    @Override
    public Object getResult() {
        return this.result;
    }

}
