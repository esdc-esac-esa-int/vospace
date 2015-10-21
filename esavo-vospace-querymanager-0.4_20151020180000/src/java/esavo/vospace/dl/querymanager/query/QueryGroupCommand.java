package esavo.vospace.dl.querymanager.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.dao.IUsersDao;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.Users;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.dl.querymanager.mapper.VospaceTransferObjectMapper;

/**
 * Query the Group by its name and User owner or the list of groups belonging to a user.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class QueryGroupCommand implements IQueryManagerCommand {

    /** Commons-logging variable. */
    private static Log log = LogFactory.getLog(QueryGroupCommand.class);
    
    /** Vospace User DAO. */
    private IGroupsDao groupDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getGroupsDAO();
    
    /** Vospace User DAO. */
    private IUsersDao userDao = ((IVospaceDaoService) DAOServiceProvider.getDaoService())
            .getUsersDAO();
    
    /** Transfer object to be ingested. */
    private List<GroupTO> groupTOList;
    
    /** Ingested persistent object. */
    private List<Groups> groupList;
    
    /** Group name to retrieve the group. */
    private String groupName;
    
    /** Group owner name */
    private String ownerName;
        
    public QueryGroupCommand(final String ownerName, final String groupName) {
        log.debug("Into QueryGroupCommand()");
        this.groupName = groupName;
        this.ownerName = ownerName;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into QueryGroupCommand().execute()");
        
        if (this.ownerName == null) {
            throw new IllegalArgumentException("QueryGroupCommand.execute() -> "
                    + "Input Group owner cannot be null.");
        }
        
        Users persistentOwner = this.userDao.queryUserByName(this.ownerName);
        if (persistentOwner == null) {
            throw new IllegalArgumentException("QueryGroupCommand.execute() -> "
                    + "The owner with Name [" + this.ownerName + "] does not exists.");
        }
        
        if (this.groupName == null) {
            //retrieve all Groups belonging to Manager
            this.groupList = this.groupDao.queryGroupByOwner(persistentOwner);
        } else {
            Groups group = this.groupDao.queryGroupByOwnerAndName(persistentOwner, this.groupName);
            this.groupList = new ArrayList<Groups>();
            this.groupList.add(group);
        }
        
        if (this.groupName != null && this.groupList == null) {
            throw new UnsupportedOperationException("QueryGroupCommand.execute() -> "
                    + "The group with Name [" + this.groupName + "] does not exists.");
        }
        
        if (this.groupList != null && !this.groupList.isEmpty()) {
            this.groupTOList = VospaceTransferObjectMapper.populateGroupTOList(this.groupList);
        } else 
            this.groupTOList = new ArrayList<GroupTO>();
        
        log.debug("End of QueryGroupCommand().execute()");
    }

    @Override
    public Object getResult() {
        return this.groupTOList;
    }

}
