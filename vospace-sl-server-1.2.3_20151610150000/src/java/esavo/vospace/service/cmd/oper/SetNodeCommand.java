package esavo.vospace.service.cmd.oper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.constants.ClientConstants;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.exceptions.QuotaExceededException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;
import esavo.vospace.service.utils.access.UserUtils;
import esavo.vospace.service.utils.nodes.NodeUtils;

/**
 * Update the Properties of a Node.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class SetNodeCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(SetNodeCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Result of the command execution */
    private NodeTO nodeTO;
    
    private NodeTO resultNodeTO;
    
    /** Whether to update or not the length property and user quota */
    private boolean updateLength;
    
    private long oldLength;
    
    public SetNodeCommand (NodeTO nodeTO) {
        log.debug("Into SetNodeCommand()");
        this.nodeTO = nodeTO;
        this.updateLength = false;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    /**
     * Constructor to update only the Lenght property of a node
     * @param nodeTO
     * @param oldLength
     */
    public SetNodeCommand (NodeTO nodeTO, Long oldLength) {
        log.debug("Into SetNodeCommand()");
        this.nodeTO = nodeTO;
        this.updateLength = true;
        this.oldLength = oldLength;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws InternalFaultException {
        log.debug("Into SetNodeCommand().execute()");
        
        try {            
            
            // Updating the length property of a node
            if (this.updateLength){                
                long newLength = NodeUtils.retrieveNodeSize(this.nodeTO);
                long sizeToUpdate = 0;
                if (newLength > oldLength) { //increment -> check quota limit
                    sizeToUpdate = newLength - oldLength;
                    Long quota = UserUtils.getCurrentContextUser().getQuota();
                    Long qlimit = this.nodeTO.getUserTO().getQLimit();
                    if (qlimit < (quota + sizeToUpdate)) {
                        log.debug("Quota limit " + qlimit + " exceeded: Current quota =" + quota);
                        throw new QuotaExceededException(ClientConstants.QUOTA_EXCEEDED);
                    }
                }
            }
            
            this.resultNodeTO = this.iQueryManagerVospace.doUpdateNodeProperties(this.nodeTO);
            
            if (this.updateLength) { //update user quota
                //TODO this quota update can be optimized just updating the sizeToUpdate calculated in the IF before
                Long quota = iQueryManagerVospace.doQueryUserQuota(UserUtils.getCurrentContextUser().getName());
                iQueryManagerVospace.doUpdateUserQuota(UserUtils.getCurrentContextUser().getName(), quota);
            }
                
        } catch (Exception e) {
            log.debug("InternalFaultException.Database " + e.getMessage());
            e.printStackTrace();
            throw new InternalFaultException("InternalFaultException.Database ");
        }
        
        log.debug("End of SetNodeCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.resultNodeTO;
    }

}
