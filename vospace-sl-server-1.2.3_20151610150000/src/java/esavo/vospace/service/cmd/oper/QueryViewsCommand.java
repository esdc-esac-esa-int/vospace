package esavo.vospace.service.cmd.oper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

/**
 * Vospace Service to get VoViews
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryViewsCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryViewsCommand.class);
    
    /** Type of View */
    private String type;
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** Result of the command execution */
    private List<VoViewTO> viewList;
    /**
     * Constructor.
     */
    public QueryViewsCommand (final String type) {
        log.debug("Into QueryViewsCommand()");
        iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
        this.type = type;
        viewList = new ArrayList<VoViewTO>();
    }
    
    @Override
    public void execute() {
        
        log.debug("Into QueryViewsCommand().execute()");
        
        if (this.type == null || this.type.isEmpty()) {
            throw new IllegalArgumentException("QueryViewsCommand.execute() -> "
                    + "Input Type cannot be null.");
        }
        
        try {
            this.viewList = iQueryManagerVospace.doQueryVoViews(this.type);            
        } catch (RemoteException e) {            
            e.printStackTrace();
        }
        
        log.debug("End of QueryViewsCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.viewList;
    }

}
