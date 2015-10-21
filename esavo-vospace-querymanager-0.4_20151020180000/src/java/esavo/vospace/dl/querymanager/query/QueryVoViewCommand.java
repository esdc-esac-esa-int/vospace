package esavo.vospace.dl.querymanager.query;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IVoViewDao;
import esac.archive.vospace.persistence.model.VoView;
import esac.archive.vospace.persistence.model.VosType;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

public class QueryVoViewCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryVoViewCommand.class);
    
    /** Vospace VoProtocol DAO. */
    private IVoViewDao voViewDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getVoViewDAO();
    
    /** Type of the persistent VoView */
    private VosType type;
    
    /** List of persistent VoProtocols */
    private List<VoView> voViewList;
    
    /** List of VoView Transfer Object */
    private List<VoViewTO> voViewTOList;
    
    /**
     * Constructor
     * @param type
     */
    public QueryVoViewCommand(final String type) {
        log.debug("Into QueryVoViewCommand()");
        this.type = VosType.valueOf(type);
    }
    
    @Override
    public void execute() {
        log.debug("Into QueryVoViewCommand().execute()");
        
        if (this.type == null) {
            throw new IllegalArgumentException("QueryVoViewCommand.execute() -> "
                    + "Input type cannot be null.");
        }
        
        this.voViewList = this.voViewDao.queryVoViewByType(this.type);
        
        this.voViewTOList = 
                VospaceTransferObjectMapper.populateTypeVoViewTOList(this.voViewList, this.type);
        
        log.debug("End of QueryVoViewCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.voViewTOList;
    }

}
