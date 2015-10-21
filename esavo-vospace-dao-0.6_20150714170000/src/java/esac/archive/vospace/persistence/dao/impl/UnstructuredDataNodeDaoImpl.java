package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IUnstructuredDataNodeDao;
import esac.archive.vospace.persistence.model.UnstructuredDataNode;
import esac.archive.vospace.persistence.util.DaoConstants;

public class UnstructuredDataNodeDaoImpl extends AbstractHibernateDAOImpl
        implements IUnstructuredDataNodeDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(UnstructuredDataNode.class);
    
    @Override
    public UnstructuredDataNode getEmptyUnstructuredDataNode() {
        return new UnstructuredDataNode();
    }
    
    @Override
    public Class<?> getPersistentClass() {
        return UnstructuredDataNode.class;
    }

    @Override
    public UnstructuredDataNode queryUnstructuredDataNodeById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("UnstructuredDataNodeImpl.queryUnstructuredDataNodeById (" + id
                    + "does not accept [null] as input");
        }
        /*UnstructuredDataNode structuredDataNode = getEmptyUnstructuredDataNode(); 
        structuredDataNode.setNodeOid(id);
        List<?> dataNodeList = this.findByExample(id);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(UnstructuredDataNode.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_ID, id));
        List<?> dataNodeList = executeCriteriaQuery(criteria);
        
        if ((dataNodeList == null) || (dataNodeList.size() == 0)) {
            return null;
        } else if (dataNodeList.size() > 1) {
            String errMsg = "More than 1 result found for UnstructuredDataNodeImpl.queryUnstructuredDataNodeById ("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (UnstructuredDataNode) dataNodeList.get(0);
    }

}
