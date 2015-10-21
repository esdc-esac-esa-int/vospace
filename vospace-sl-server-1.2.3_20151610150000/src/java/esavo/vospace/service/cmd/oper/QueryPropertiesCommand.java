package esavo.vospace.service.cmd.oper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

/**
 * Vospace Service to get VoProperties
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class QueryPropertiesCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryPropertiesCommand.class);
        
    /** Type of View */
    private String type;
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Result of the command execution */
    private List<VoPropertyTO> propertyList;
    
    public QueryPropertiesCommand(final String type) {
        log.debug("Into QueryPropertiesCommand()");
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        this.propertyList = new ArrayList<VoPropertyTO>();
        this.type = type;
    }
    
    @Override
    public void execute() {
        log.debug("Into QueryPropertiesCommand().execute()");
        
        if (this.type == null || this.type.isEmpty()) {
            throw new IllegalArgumentException("QueryPropertiesCommand.execute() -> "
                    + "Input Type cannot be null.");
        }
        
        try {
            this.propertyList = iQueryManagerVospace.doQueryVoProperties(this.type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
        log.debug("End of QueryPropertiesCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.propertyList;
    }

}
