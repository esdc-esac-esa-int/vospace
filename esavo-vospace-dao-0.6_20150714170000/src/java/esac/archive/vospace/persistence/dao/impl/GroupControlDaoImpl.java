package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IGroupControlDao;
import esac.archive.vospace.persistence.model.controls.Group;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * Implementation of Group Dao interface.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class GroupControlDaoImpl extends AbstractHibernateDAOImpl implements
        IGroupControlDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(GroupControlDaoImpl.class);
    
    @Override
    public Group getEmptyGroupControl() {
        return new Group();
    }
    
    @Override
    public Class<Group> getPersistentClass() {
        return Group.class;
    }

    @Override
    public Group getGroupControlById(Integer groupId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Group.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_CONTROLS_GROUP_ID, groupId));
        List<?> logList = executeCriteriaQuery(criteria);
        
        if ((logList == null) || (logList.size() == 0)) {
            return null;
        } else if (logList.size() > 1) {
            String errMsg = "More than 1 result found for GroupControlDaoImpl.getGroupControlById ("
                    + groupId + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (Group) logList.get(0);
    }
    
}
