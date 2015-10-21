package esavo.vospace.dl.querymanager.cmd.ingestion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.ICapabilityParamDao;
import esac.archive.vospace.persistence.model.CapabilityParam;
import esac.archive.vospace.persistence.model.VoCapability;
import esavo.vospace.common.model.transferobjects.CapabilityParamTO;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestVoCapabilityParamCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestVoCapabilityParamCommand.class);
    
    /** Vospace Vo Capability Param DAO. */
    private ICapabilityParamDao capabilityParamDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getCapabilityParamDao();
    
    /** Transfer object to be ingested. */
    private CapabilityParamTO capabilityParamTO;
    
    /** Ingested persistent object. */
    private CapabilityParam capabilityParam;
    
    /** Persistent parent of this object. */
    private VoCapability voCapability;
    
    /** Overwrite information flag */
    private Boolean overwriteData;
    
    public IngestVoCapabilityParamCommand (final CapabilityParamTO capabilityParamTO,
            final VoCapability voCapability, final Boolean overwrite) {
        this.capabilityParamTO = capabilityParamTO;
        this.voCapability = voCapability;
        this.overwriteData = overwrite;
    }
    
    @Override
    public void execute() {
        log.debug("Into IngestVoCapabilityParamCommand().execute()");
        
        if (this.capabilityParamTO == null) {
            throw new IllegalArgumentException("IngestVoCapabilityParamCommand.execute() -> "
                    + "Input VoCapabilityParam cannot be null.");
        }
        
        // Does this VoCapabilityParam already exists??
        this.capabilityParam = this.capabilityParamDao.queryCapabilityParamById(
                this.capabilityParamTO.getParamId());
        if ((this.capabilityParam != null) && !this.overwriteData) {
            throw new UnsupportedOperationException("CapabilityParam: [" + 
                    this.capabilityParam.getCapabilityParamOid() + "] has already been ingested");
        } else if (this.capabilityParam == null) {
            this.capabilityParam = this.capabilityParamDao.getEmptyCapabilityParam();
        }
        
        this.capabilityParam.setName(this.capabilityParamTO.getURI().toString());
        this.capabilityParam.setValue(this.capabilityParamTO.getValue());
        this.capabilityParam.setVoCapability(this.voCapability); //Reference to its parent
        
        this.capabilityParamDao.insertOrUpdate(this.capabilityParam);
        
        log.debug("End of IngestVoCapabilityParamCommand.execute()");

    }

    @Override
    public Object getResult() {
        return this.capabilityParam;
    }

}
