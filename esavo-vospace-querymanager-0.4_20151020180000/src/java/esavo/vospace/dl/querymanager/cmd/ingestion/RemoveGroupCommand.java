package esavo.vospace.dl.querymanager.cmd.ingestion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.Users;

/**
 * Command to remove a group by its manager and name.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014 *
 */
public class RemoveGroupCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(RemoveGroupCommand.class);
    
    /** Vospace Groups DAO. */
    private IGroupsDao groupsDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getGroupsDAO();
    
    /** Vospace Users DAO. */
    private IUsersDao usersDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** Group name */
    private String groupName;
    
    /** Group manager */
    private String manager;
    
    /**
     * Constructor
     * @param manager
     * @param groupName
     */
    public RemoveGroupCommand(final String groupName, final String managerName) {
        log.debug("Into RemoveGroupCommand()");
        this.groupName = groupName;
        this.manager = managerName;
    }
    
    @Override
    public void execute() {
        log.debug("Into RemoveGroupCommand().execute()");
        
        if (this.groupName == null || this.manager == null) {
            throw new IllegalArgumentException("RemoveGroupCommand.execute() -> "
                    + "Input values cannot be null.");
        }
        
        Users user = this.usersDao.queryUserByName(this.manager);
        if (user == null) {
            throw new UnsupportedOperationException("Group manager for name ["
                    + this.manager + "] does not exist");
        }
        
        Groups persistentGroup = this.groupsDao.queryGroupByOwnerAndName(user, this.groupName);
        
        if (persistentGroup != null) {
            deletePersistentGroup(persistentGroup);
        } else {
            throw new UnsupportedOperationException("Group for User ["
                    + this.manager + "] and Group Name [" + this.groupName + "] does not exist");
        }
        
        log.debug("End of RemoveGroupCommand().execute()");
    }
    
    /*
     * Remove group and if related associations.
     */
    private void deletePersistentGroup(final Groups group) {
        
        if (group != null) {
            
            this.groupsDao.delete(group);
        }
    }

    @Override
    public Object getResult() {
        return null;
    }

}
