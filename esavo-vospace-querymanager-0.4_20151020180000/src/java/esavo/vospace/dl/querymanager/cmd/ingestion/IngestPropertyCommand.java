package esavo.vospace.dl.querymanager.cmd.ingestion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.VoProperty;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;

/**
 * Command which will ingest a VoProperty into the database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class IngestPropertyCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestPropertyCommand.class);
    
    /** Vospace Node DAO. */
    private IVoPropertyDao voPropertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    /** Transfer object to be ingested. */
    private VoPropertyTO voPropertyTO;
    
    /** Ingested persistent object. */
    private VoProperty voProperty;
    
    /** Overwrite information flag */
    private Boolean overwriteData;
    
    public IngestPropertyCommand(final VoPropertyTO voPropertyTO, 
            final Boolean overwrite) {
        this.voPropertyTO = voPropertyTO;
        this.overwriteData = overwrite;
    }
    
    @Override
    public void execute() {
        
        log.debug("Into IngestPropertyCommand().execute()");
        
        if (this.voPropertyTO == null) {
            throw new IllegalArgumentException("IngestPropertyCommand.execute() -> "
                    + "Input VoProperty cannot be null.");
        }
        
        // Does this VoProperty already exists??
        this.voProperty = this.voPropertyDao.queryVoPropertyById(this.voPropertyTO.getId());
        if ((this.voProperty != null) && !overwriteData) {
            throw new UnsupportedOperationException("LinkNode: [" + this.voPropertyTO.getId()
                    + "] has already been ingested");
        } else if (this.voProperty == null) {
            this.voProperty = this.voPropertyDao.getEmptyVoProperty();
        }
        
        //this.voProperty.setViewType(this.voPropertyTO.get); // view_type
        this.voProperty.setUri(this.voPropertyTO.getURI().toString());
        this.voProperty.setReadonly(this.voPropertyTO.getReadOnly());
        this.voProperty.setDescription(this.voPropertyTO.getDescription());
        
        this.voPropertyDao.insertOrUpdate(this.voProperty);        
        
        log.debug("End of IngestPropertyCommand.execute()");

    }

    @Override
    public Object getResult() {
        return this.voProperty;
    }

}
