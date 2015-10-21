package esavo.vospace.service.cmd.oper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

public class QueryNodeSharedByGroup implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryNodeSharedByGroup.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
        
    /** Node List */
    private List<NodeTO> nodeList;
    
    /** User name */
    private String userName;
    
    /** Property uri */
    private String propertyUri;
    
    /**
     * Constructor.
     * @param user
     * @param propertyUri
     */
    public QueryNodeSharedByGroup(final String user, final String propertyUri) {
        log.debug("Into QueryNodeSharedByGroup()");
        this.userName = user;
        this.propertyUri = propertyUri;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into QueryNodeSharedByGroup().execute()");
        
        try {
            this.nodeList = this.iQueryManagerVospace.doQueryGroupsNodePropertyCommand(
                    this.userName, this.propertyUri);
            
        } catch (Exception e) {
            throw new InternalFaultException("Error nodes does not exist.");
        }
        
        log.debug("End of QueryNodeSharedByGroup().execute()");
    }

    @Override
    public Object getResult() {
        return this.nodeList;
    }

}
