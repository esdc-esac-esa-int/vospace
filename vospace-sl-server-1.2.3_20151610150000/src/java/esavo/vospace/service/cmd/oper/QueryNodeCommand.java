package esavo.vospace.service.cmd.oper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.exceptions.InternalFaultException;
import esavo.vospace.ifsd.model.actions.IQueryManagerVospace;
import esavo.vospace.service.rmi.RMIVospaceClient;

/**
 * Query Node according to the given criteria.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryNodeCommand implements IVospaceCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryNodeCommand.class);
    
    /** RMI access to QueryManager */
    private IQueryManagerVospace iQueryManagerVospace;
    
    /** URI Target */
    private VOSpaceURI uriTarget;
    
    /** Detail Param */
    @SuppressWarnings("unused")
    private String detailParam;
    
    /** URI Param */
    @SuppressWarnings("unused")
    private VOSpaceURI uriParam;
    
    /** Limit Param */
    @SuppressWarnings("unused")
    private String limitParam;
    
    /** Full details */
    private Boolean fullDetails;
    
    /** Node Transfer Object */
    private NodeTO nodeTO;
    
    /**
     * Constructor.
     * @param detail
     * @param uriParam
     * @param limit
     */
    public QueryNodeCommand (final VOSpaceURI uri, final String detail, 
            final VOSpaceURI uriParam, final String limit) {
        log.debug("Into GetNodeCommand()");
        this.uriTarget =  uri;
        this.detailParam = detail;
        this.uriParam = uriParam;
        this.limitParam = limit;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    public QueryNodeCommand (final VOSpaceURI uri, final boolean fullDetails) {
        log.debug("Into GetNodeCommand()");
        this.uriTarget =  uri;
        this.fullDetails = fullDetails;
        this.iQueryManagerVospace = RMIVospaceClient.getQueryManagerService();
    }
    
    @Override
    public void execute() throws InternalFaultException {
        log.debug("Into QueryNodeCommand().execute()");
        
        if (this.fullDetails != null) {
            executeGetRequest(this.fullDetails);
        } else {
            executeGetRequest(true);
        }
                
        log.debug("End of QueryNodeCommand().execute()");
    }
    
    private void executeGetRequest(boolean fullDetails) throws InternalFaultException {
        try {
            this.nodeTO = this.iQueryManagerVospace.doQueryNode(this.uriTarget, true);
        } catch (Exception e) {
            throw new InternalFaultException("Error node does not exist.");
        }
    }

    @Override
    public Object getResult() {
        return this.nodeTO;
    }

}
