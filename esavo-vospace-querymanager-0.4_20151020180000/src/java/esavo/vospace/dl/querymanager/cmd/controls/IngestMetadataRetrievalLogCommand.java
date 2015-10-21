package esavo.vospace.dl.querymanager.cmd.controls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.controls.MetadataRetrievalLog;
import esac.archive.vospace.persistence.model.controls.MetadataRetrievalType;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.control.MetadataRetrievalLogTO;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;

/**
 * Command which will ingest an MetadataRetrievalLog into the Control database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class IngestMetadataRetrievalLogCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestMetadataRetrievalLogCommand.class);
    
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
    
    private MetadataRetrievalLogTO logTO;
    
    /**
     * Constructor.
     */
    public IngestMetadataRetrievalLogCommand (MetadataRetrievalLogTO logTO) {
        log.debug("Into IngestMetadataRetrievalLogCommand()");
        this.logTO = logTO;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into IngestMetadataRetrievalLogCommand().execute()");        
        
        if (this.logTO == null) {
            throw new IllegalArgumentException("IngestMetadataRetrievalLogCommand.execute() -> "
                    + "Input logTO cannot be null.");
        }
        
        UserControlTO userControl = this.logTO.getUserControlTO();
        String name = "";
        if (userControl != null) {
            name = userControl.getName();
        }
        
        User user = IDaoService.getUserControlDao().getUserControlByUname(name);
        if (user == null) {
            throw new UnsupportedOperationException("MetadataRetrievalLog without User owner cannot be inserted.");
        }
        
        MetadataRetrievalLog mrLog = IDaoService.getMetadataRetrievalLogDao().getEmptyMetadataRetrievalLog();
        mrLog.setStartTimestamp(this.logTO.getStartTimestamp());
        mrLog.setStopTimestamp(this.logTO.getStopTimestamp());
        mrLog.setOperationType(MetadataRetrievalType.toType(this.logTO.getType()));
        mrLog.setUser(user);
        
        IDaoService.getMetadataRetrievalLogDao().insertOrUpdate(mrLog);
        
        log.debug("End of IngestMetadataRetrievalLogCommand().execute()");
    }  

    @Override
    public Object getResult() {
        return null;
    }

}
