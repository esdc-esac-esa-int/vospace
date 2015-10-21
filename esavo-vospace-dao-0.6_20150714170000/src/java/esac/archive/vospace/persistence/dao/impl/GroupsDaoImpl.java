/**
 * 
 */
package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IGroupsDao;
import esac.archive.vospace.persistence.model.Groups;
import esac.archive.vospace.persistence.model.Users;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class GroupsDaoImpl extends AbstractHibernateDAOImpl implements
        IGroupsDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(GroupsDaoImpl.class);
    
    @Override
    public final Class<Groups> getPersistentClass() {
        return Groups.class;
    }
    
    @Override
    public final Groups getEmptyGroup() {
        return new Groups();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Groups> queryGroupByOwner(Users manager) {
        if (manager == null) {
            throw new IllegalArgumentException("GroupsDaoImpl.queryGroupsByOwner (" + manager
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Groups.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_GROUPS_MANAGER, manager));
        
        List<?> groupList = this.executeCriteriaQuery(criteria);
        if ((groupList == null) || (groupList.size() == 0)) {
            return null;
        }
        
        return (List<Groups>) groupList;
    }

    @Override
    public Groups queryGroupByOwnerAndName(final Users manager, final String groupName) {
        if (groupName == null || manager == null) {
            throw new IllegalArgumentException("GroupsDaoImpl.queryGroupByOwnerAndName (" + groupName
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Groups.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_GROUPS_NAME, groupName));
        criteria.add(Restrictions.eq(DaoConstants.DB_GROUPS_MANAGER, manager));
        
        List<?> groupList = this.executeCriteriaQuery(criteria);
        if ((groupList == null) || (groupList.size() == 0)) {
            return null;
        } else if (groupList.size() > 1) {
            String errMsg = "More than 1 result found for GroupsDaoImpl.queryGroupByOwnerAndName("
                    + groupName + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (Groups) groupList.get(0);
    }

    @Override
    public Groups queryGroupById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("GroupsDaoImpl.queryGroupById (" + id
                    + "does not accept [null] as input");
        }
        /*Groups group = getEmptyGroup();
        group.setGroupOid(id);        
        List<?> groupList = this.findByExample(group);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Groups.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_GROUPS_ID, id));
        List<?> groupList = executeCriteriaQuery(criteria);
        
        if ((groupList == null) || (groupList.size() == 0)) {
            return null;
        } else if (groupList.size() > 1) {
            String errMsg = "More than 1 result found for GroupsDaoImpl.queryGroupById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (Groups) groupList.get(0);
    }

}
