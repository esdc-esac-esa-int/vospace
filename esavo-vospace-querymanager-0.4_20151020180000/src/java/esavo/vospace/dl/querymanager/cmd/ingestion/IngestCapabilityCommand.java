package esavo.vospace.dl.querymanager.cmd.ingestion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.model.CapabilityParam;
import esac.archive.vospace.persistence.model.VoCapability;
import esavo.vospace.common.model.transferobjects.CapabilityParamTO;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.dl.querymanager.cmd.VospaceQueryManagerCommandFactory;
import esavo.vospace.exceptions.VOSpaceException;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class IngestCapabilityCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestCapabilityCommand.class);
    
    /** Vospace Vo Capability DAO. */
    private IVoCapabilityDao voCapabilityDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoCapabilityDAO();
    
    /** Transfer object to be ingested. */
    private VoCapabilityTO voCapabilityTO;
    
    /** Ingested persistent object. */
    private VoCapability voCapability;
    
    /** Overwrite information flag */
    private Boolean overwriteData;
    
    /**
     * Constructor
     * @param voCapabilityTO
     * @param voCapability
     * @param overwrite
     */
    public IngestCapabilityCommand (final VoCapabilityTO voCapabilityTO, 
            final Boolean overwrite) {
        this.voCapabilityTO = voCapabilityTO;
        this.overwriteData = overwrite;
    }
    
    @SuppressWarnings("unused")
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into IngestVoCapabilityCommand().execute()");
        
        if (this.voCapabilityTO == null) {
            throw new IllegalArgumentException("IngestVoCapabilityCommand.execute() -> "
                    + "Input VoCapability cannot be null.");
        }
        
        this.voCapability = this.voCapabilityDao.queryVoCapabilityById(this.voCapabilityTO.getId());
        if ((this.voCapability != null) && !this.overwriteData) {
            throw new UnsupportedOperationException("VoCapability: [" + this.voCapabilityTO.getId()
                    + "] has already been ingested");
        } else if (this.voCapability == null) {
            this.voCapability = this.voCapabilityDao.getEmptyVoCapability();
        }
        
        this.voCapability.setUri(this.voCapabilityTO.getURI().toString());
        this.voCapability.setEndPoint(this.voCapabilityTO.getEndPoint().toString());
        
        this.voCapabilityDao.insertOrUpdate(this.voCapability);
        
        //Insert VoCapabilityParams after inserting voCapability
        Set<CapabilityParam> capabilityParamSet = ingestVoCapabilityParams(
                this.voCapabilityTO.getParams(), this.voCapability, this.overwriteData);
                
        log.debug("End of IngestVoCapabilityCommand.execute()");
    }
    
    /**
     * Ingest a Set of VoCapabilityParam
     * @param capabilityParamTOList
     * @param voCapability
     * @param overwrite
     * @return Set of VoCapabilityParam persisten objects inserted.
     * @throws ArchiveNestedException
     * @throws VOSpaceException 
     */
    private Set<CapabilityParam> ingestVoCapabilityParams (final List<CapabilityParamTO> capabilityParamTOList,
            VoCapability voCapability, final boolean overwrite) throws ArchiveNestedException {
        if (capabilityParamTOList == null) {
            return null;
        }
        Set<CapabilityParam> capabilityParamSet = new HashSet<CapabilityParam>();
        for (CapabilityParamTO capabilityParamTO : capabilityParamTOList) {
            CapabilityParam capabilityParam = ingestVoCapabilityParam(capabilityParamTO, voCapability, overwrite);
            capabilityParamSet.add(capabilityParam);
        }
        return capabilityParamSet;
    }
    
    /**
     * Ingest a VoCapabilityParam
     * @param capabilityParamTO
     * @param voCapability
     * @param overwrite
     * @return CapabilityParam persisten object inserted.
     * @throws VOSpaceException 
     * @throws ArchiveNestedException
     */
    private CapabilityParam ingestVoCapabilityParam (final CapabilityParamTO capabilityParamTO,
            VoCapability voCapability, final boolean overwrite) throws ArchiveNestedException {
            
        IQueryManagerCommand ingestVoCapabilityParam = VospaceQueryManagerCommandFactory
                .createVoCapabilityParamCommand(capabilityParamTO, voCapability, overwrite);
        ingestVoCapabilityParam.execute();
        return (CapabilityParam) ingestVoCapabilityParam.getResult();
    }

    @Override
    public Object getResult() {
        return this.voCapability;
    }

}
