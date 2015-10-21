package esavo.vospace.dl.querymanager.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Users;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

public class QueryUserListCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(QueryUserListCommand.class);
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** User list to be returned */
    private List<UserTO> userList;
    
    public QueryUserListCommand () {
        log.debug("Into QueryUserListCommand()");
        userList = new ArrayList<UserTO>();
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into QueryUserListCommand().execute()");

        List<Users> users = this.userDao.getUsers();
        
        // mapper
        for (Users user : users) {
            UserTO userTO = VospaceTransferObjectMapper.populateUserTO(user);
            this.userList.add(userTO);
        }
        log.debug("End of QueryUserListCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.userList;
    }

}
