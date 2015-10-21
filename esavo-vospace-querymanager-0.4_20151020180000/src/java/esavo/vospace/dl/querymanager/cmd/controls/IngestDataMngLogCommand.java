package esavo.vospace.dl.querymanager.cmd.controls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.controls.DataMngLog;
import esac.archive.vospace.persistence.model.controls.DataMngType;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.control.DataMngLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;

/**
 * Command which will ingest an DataMngLog into the Control database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class IngestDataMngLogCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestDataMngLogCommand.class);
    
    
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
    
    /**  */
    private DataMngLogTO logTO;
    
    /**
     * Constructor.
     */
    public IngestDataMngLogCommand (DataMngLogTO logTO) {
        log.debug("Into IngestDataMngLogCommand()");
        this.logTO = logTO;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into IngestDataMngLogCommand().execute()");
        
        if (this.logTO == null) {
            throw new IllegalArgumentException("IngestDataMngLogCommand.execute() -> "
                    + "Input logTO cannot be null.");
        }
        
        UserControlTO userControl = this.logTO.getUserTO();
        String name = "";
        if (userControl != null) {
            name = userControl.getName();
        }
        
        User user = this.IDaoService.getUserControlDao().getUserControlByUname(name);
        if (user == null) {
            throw new UnsupportedOperationException("DataMngLog without User owner cannot be inserted.");
        }
        
        DataMngLog logInput = this.IDaoService.getDataMngLogDao().getEmptyDataManagementLog();        
        logInput.setNodeSource(this.logTO.getNodeSource());
        logInput.setNodeTarget(this.logTO.getNodeTarget());
        logInput.setStartTimestamp(this.logTO.getStartTimestamp());
        logInput.setStopTimestamp(this.logTO.getStopTimestamp());
        logInput.setOperationType(DataMngType.toType(this.logTO.getOperType()));
        logInput.setSize(this.logTO.getSize());
        logInput.setUser(user);
        
        this.IDaoService.getDataMngLogDao().insertOrUpdate(logInput);
        
        log.debug("End of IngestDataMngLogCommand().execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
