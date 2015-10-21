package esavo.vospace.dl.querymanager.cmd.controls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.controls.TransferLog;
import esac.archive.vospace.persistence.model.controls.TransferOperType;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.control.TransferLogTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

/**
 * Command which will ingest an TransferLog into the Control database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class IngestTransferLogCommand implements IQueryManagerCommand {
    
    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestTransferLogCommand.class);
    
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
    
    private TransferLogTO logTO;
    
    private TransferLogTO result;
    
    /**
     * Constructor.
     */
    public IngestTransferLogCommand (TransferLogTO logTO) {
        log.debug("Into IngestTransferLogCommand()");
        this.logTO = logTO;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into IngestTransferLogCommand().execute()");
        
        if (this.logTO == null) {
            throw new IllegalArgumentException("IngestTransferLogCommand.execute() -> "
                    + "Input logTO cannot be null.");
        }
        
        User user = IDaoService.getUserControlDao().getUserControlByUname(this.logTO.getUserControlTO().getName());
        if (user == null) {
            throw new UnsupportedOperationException("TransferLog without User owner cannot be inserted.");
        }
        
        TransferLog sLog = IDaoService.getTransferLogDao().getEmptyTransferLog();
        sLog.setOperationType(TransferOperType.toType(this.logTO.getOperType()));
        sLog.setProtocolParam(this.logTO.getProtocolParam());
        sLog.setViewParam(this.logTO.getViewParam());
        sLog.setStartTimestamp(this.logTO.getStartTimestamp());
        sLog.setStopTimestamp(this.logTO.getStopTimestamp());
        sLog.setTotalSize(this.logTO.getTotalSize());
        sLog.setUser(user);
        
        IDaoService.getTransferLogDao().insertOrUpdate(sLog);
        
        this.result = VospaceTransferObjectMapper.populateTransferTO(sLog);
        
        log.debug("End of IngestTransferLogCommand().execute()");

    }

    @Override
    public Object getResult() {
        return this.result;
    }

}
