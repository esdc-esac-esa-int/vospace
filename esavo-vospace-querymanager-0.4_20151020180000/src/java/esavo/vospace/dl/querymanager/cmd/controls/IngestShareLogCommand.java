package esavo.vospace.dl.querymanager.cmd.controls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.controls.ShareLog;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.control.ShareLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;

/**
 * Command which will ingest an ShareLog into the Control database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class IngestShareLogCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestShareLogCommand.class);
    
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
        
    private ShareLogTO logTO;
    
    /**
     * Constructor.
     */
    public IngestShareLogCommand (ShareLogTO logTO) {
        log.debug("Into IngestShareLogCommand()");
        this.logTO = logTO;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into IngestShareLogCommand().execute()");
        
        if (this.logTO == null) {
            throw new IllegalArgumentException("IngestShareLogCommand.execute() -> "
                    + "Input logTO cannot be null.");
        }
        
        UserControlTO userControl = this.logTO.getUserControlTO();
        String name = "";
        if (userControl != null) {
            name = userControl.getName();
        }
        
        User user = IDaoService.getUserControlDao().getUserControlByUname(name);
        if (user == null) {
            throw new UnsupportedOperationException("DataMngLog without User owner cannot be inserted.");
        }
        
        ShareLog sLog = IDaoService.getShareLogDao().getEmptyShareLog();
        sLog.setNode(this.logTO.getNode());
        sLog.setOperationTimestamp(this.logTO.getOperationTimestamp());
        sLog.setEndTimestamp(this.logTO.getEndTimestamp());
        sLog.setUser(user);
        
        IDaoService.getShareLogDao().insertOrUpdate(sLog);
        
        log.debug("End of IngestShareLogCommand().execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
