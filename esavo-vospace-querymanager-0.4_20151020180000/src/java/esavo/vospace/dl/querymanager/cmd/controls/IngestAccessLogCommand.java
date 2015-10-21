package esavo.vospace.dl.querymanager.cmd.controls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.controls.AccessLog;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.control.AccessLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

/**
 * Command which will ingest an AccessLog into the Control database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class IngestAccessLogCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestAccessLogCommand.class);
    
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
    
    private AccessLogTO logTO;
    
    private AccessLogTO result;
    
    public IngestAccessLogCommand (AccessLogTO logTO) {
        log.debug("Into IngestAccessLogCommand()");
        this.logTO = logTO;
    }
    
    @Override
    public void execute() {
        
        log.debug("Into IngestAccessLogCommand().execute()");
        
        if (this.logTO == null) {
            throw new IllegalArgumentException("IngestAccessLogCommand.execute() -> "
                    + "Input logTO cannot be null.");
        }
        
        UserControlTO userControl = this.logTO.getUserControlTO();
        String name = "";
        if (userControl != null) {
            name = userControl.getName();
        }
        
        User user = IDaoService.getUserControlDao().getUserControlByUname(name);
        if (user == null) {
            throw new UnsupportedOperationException("AccessLog without User owner cannot be inserted."); 
        }
        
        AccessLog alog = IDaoService.getAccessLogDao().getEmptyAccessLog();
                
        alog.setEnvironment(this.logTO.getEnvironment());
        alog.setInternetsite(this.logTO.getInternetSite());
        alog.setLoginTimestamp(this.logTO.getLoginTimestamp());
        //alog.setLogoutTimestamp(this.logTO.getLogouTimestamp());
        alog.setQuota(this.logTO.getQuota());
        alog.setUser(user);
        
        IDaoService.getAccessLogDao().insertOrUpdate(alog);
        
        this.result = VospaceTransferObjectMapper.populateAccessLogTO(alog);
        
        log.debug("Into IngestAccessLogCommand().execute()");

    }

    @Override
    public Object getResult() {
        return this.result;
    }

}
