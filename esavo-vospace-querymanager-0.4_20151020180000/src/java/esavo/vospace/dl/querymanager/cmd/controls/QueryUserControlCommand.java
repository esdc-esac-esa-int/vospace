package esavo.vospace.dl.querymanager.cmd.controls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

public class QueryUserControlCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryUserControlCommand.class);
        
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
    
    private UserControlTO userControlTO;
    
    private String uname;
    
    /**
     * Constructor.
     */
    public QueryUserControlCommand (String userName) {
        log.debug("Into QueryUserControlCommand()");
        
        this.uname = userName;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into QueryUserControlCommand().execute()");
        
        if (this.uname == null || this.uname.isEmpty()) {
            throw new IllegalArgumentException("QueryUserControlCommand.execute() -> "
                    + "Input User name cannot be null.");
        }
        
        User user = IDaoService.getUserControlDao().getUserControlByUname(this.uname);
        if (user == null) {
            throw new UnsupportedOperationException("UserControl does not exist.");
        }
        
        this.userControlTO = VospaceTransferObjectMapper.populateUserControlTO(user);
        
        log.debug("Into QueryUserControlCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.userControlTO;
    }

}
