package esavo.vospace.service.cmd.oper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

/**
 * Command to remove a group from the database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class RemoveGroupCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(RemoveGroupCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    private GroupTO groupTO;
    
    public RemoveGroupCommand (final GroupTO groupTO) {
        log.debug("Into RemoveGroupCommand()");
        this.groupTO = groupTO;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws VOSpaceException {
        log.debug("Into RemoveGroupCommand.execute()");
        
        if (this.groupTO == null) {
            throw new VOSpaceException("Group null cannot be created.");
        }
        
        String name = this.groupTO.getName();
        if (name.isEmpty()) {
            throw new VOSpaceException("Group name does not exist.");
        }
        
        UserTO manager = this.groupTO.getManagerTO();
        if (manager == null) {
            throw new VOSpaceException("Group manager cannot be null.");
        }
        
        String uname = manager.getName();
        
        try {
            iQueryManagerVospace.doRemoveGroup(name, uname);
        } catch (Exception e) {
            throw new VOSpaceException("Group with Name [" + name  + "] cannot be deleted.");
        }
        
        log.debug("End of RemoveGroupCommand.execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
