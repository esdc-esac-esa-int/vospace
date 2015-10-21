package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IStructuredDataNodeDao;
import esac.archive.vospace.persistence.model.StructuredDataNode;
import esac.archive.vospace.persistence.util.DaoConstants;

public class StructuredDataNodeDaoImpl extends AbstractHibernateDAOImpl implements
        IStructuredDataNodeDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(StructuredDataNode.class);
    
    @Override
    public StructuredDataNode getEmptyStructuredDataNode() {
        return new StructuredDataNode();
    }
    
    @Override
    public Class<?> getPersistentClass() {
        return StructuredDataNode.class;
    }

    @Override
    public StructuredDataNode queryStructuredDataNodeById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("StructuredDataNodeImpl.queryStructuredDataNodeById (" + id
                    + "does not accept [null] as input");
        }
        
        /*StructuredDataNode structuredDataNode = getEmptyStructuredDataNode();
        structuredDataNode.setNodeOid(id);        
        List<?> dataNodeList = this.findByExample(structuredDataNode);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(StructuredDataNode.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_ID, id));
        List<?> dataNodeList = executeCriteriaQuery(criteria);
        
        if ((dataNodeList == null) || (dataNodeList.size() == 0)) {
            return null;
        } else if (dataNodeList.size() > 1) {
            String errMsg = "More than 1 result found for StructuredDataNodeImpl.queryStructuredDataNodeById ("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (StructuredDataNode) dataNodeList.get(0);
    }

}
