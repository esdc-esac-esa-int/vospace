package esavo.vospace.dl.querymanager.query;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IVoProtocolDao;
import esac.archive.vospace.persistence.model.VoProtocol;
import esac.archive.vospace.persistence.model.VosType;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

/**
 * Retrieve the list of VoProtocols of a given type.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryVoProtocolCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryVoProtocolCommand.class);
    
    /** Vospace VoProtocol DAO. */
    private IVoProtocolDao voProtocolsDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoProtocolDAO();
    
    /** Type of the persistent VoProtocol */
    private VosType type;
    
    /** List of persistent VoProtocols */
    private List<VoProtocol> voProtocolList;
    
    private List<VoProtocolTO> voProtocolTOList;
    
    /**
     * Constructor
     * @param type
     */
    public QueryVoProtocolCommand(final String type) {
        log.debug("Into QueryProtocolsCommand()");
        this.type = VosType.valueOf(type);
    }
    
    @Override
    public void execute() {
        log.debug("Into QueryNodeCommand().execute()");
        
        if (this.type == null) {
            throw new IllegalArgumentException("QueryProtocolsCommand.execute() -> "
                    + "Input type cannot be null.");
        }
        
        this.voProtocolList = this.voProtocolsDao.queryVoProtocolByType(this.type);
        
        this.voProtocolTOList = 
                VospaceTransferObjectMapper.populateVoProtocolTOList(this.voProtocolList);
        
        log.debug("End of QueryNodeCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.voProtocolTOList;
    }

}
