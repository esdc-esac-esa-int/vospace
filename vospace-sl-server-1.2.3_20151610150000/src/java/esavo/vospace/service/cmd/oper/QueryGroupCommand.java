package esavo.vospace.service.cmd.oper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class QueryGroupCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryGroupCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
        
    /** Group */
    private List<GroupTO> groupList;
    
    /** Group name */
    private String groupName;
    
    /** Manager name */
    private String managerName;
        
    /**
     * Constructor.
     * @param userName
     * @param name
     */
    public QueryGroupCommand (final String userName, final String name) {
        log.debug("Into QueryGroupCommand()");
        this.managerName = userName;
        this.groupName = name;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws InternalFaultException {
        log.debug("Into QueryGroupCommand().execute()");
        
        try {
            groupList = 
                    this.iQueryManagerVospace.doQueryGroup(this.managerName, this.groupName);
            
        } catch (Exception e) {
            throw new InternalFaultException("Error group does not exist.");
        }
        
        log.debug("End of QueryGroupCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.groupList;
    }

}
