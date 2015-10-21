package esavo.vospace.service.cmd.oper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

/**
 * Vospace Service to get VoProtocols
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryProtocolsCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryProtocolsCommand.class);
        
    /** Type of View */
    private String type;
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Result of the command execution */
    private List<VoProtocolTO> protocolList;
    
    public QueryProtocolsCommand (final String type) {
        log.debug("Into QueryProtocolsCommand()");
        iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        protocolList = new ArrayList<VoProtocolTO>();
        this.type = type;
    }
    
    @Override
    public void execute() {
        
        log.debug("Into QueryProtocolsCommand().execute()");
        
        if (this.type == null || this.type.isEmpty()) {
            throw new IllegalArgumentException("QueryProtocolsCommand.execute() -> "
                    + "Input Type cannot be null.");
        }
        
        try {
            this.protocolList = iQueryManagerVospace.doQueryVoProtocols(this.type);            
        } catch (RemoteException e) {            
            e.printStackTrace();
        }
        
        log.debug("End of QueryProtocolsCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.protocolList;
    }

}
