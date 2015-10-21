package esavo.vospace.dl.querymanager.query;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VosType;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

/**
 * 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryVoPropertyCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryVoProtocolCommand.class);
    
    /** Vospace VoProtocol DAO. */
    private IVoPropertyDao voPropertyDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoPropertyDAO();
    
    /** Type of the persistent VoProperty */
    private VosType type;
    
    /** List of persistent VoProtocols */
    private List<VoProperty> voPropertyList;
    
    private List<VoPropertyTO> voPropertyTOList;
    
    public QueryVoPropertyCommand(final String type) {
        log.debug("Into QueryVoPropertyCommand()");
        this.type = VosType.valueOf(type);
    }
    
    @Override
    public void execute() {
        log.debug("Into QueryVoPropertyCommand().execute()");
        
        if (this.type == null) {
            throw new IllegalArgumentException("QueryVoPropertyCommand.execute() -> "
                    + "Input type cannot be null.");
        }
        
        if (this.type == VosType.ACCEPT || this.type == VosType.PROVIDE) {
            
            this.voPropertyList = this.voPropertyDao.queryVoPropertyByType(this.type);
            
        } else if (this.type == VosType.CONTAIN) {
            
            this.voPropertyList = this.voPropertyDao.queryPropertiesUsed();
            
        }
        
        this.voPropertyTOList = 
                VospaceTransferObjectMapper.populateVoPropertyTOList(this.voPropertyList);
        
        log.debug("End of QueryVoPropertyCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.voPropertyTOList;
    }

}
