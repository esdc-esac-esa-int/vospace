package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.INodePropertyDao;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.util.DaoConstants;

public class NodePropertyDaoImpl extends AbstractHibernateDAOImpl implements INodePropertyDao {

    /**
     * Commons-logging variable.
     */
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(NodePropertyDaoImpl.class);
    
    @Override
    public Class<NodeProperty> getPersistentClass() {
        return NodeProperty.class;
    }

    @Override
    public NodeProperty getEmptyNodeProperty() {
        return new NodeProperty();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<NodeProperty> queryNodePropertyByVoProperty(VoProperty voProperty) {
        if (voProperty == null) {
            throw new IllegalArgumentException("NodePropertyDaoImpl."
                    + "queryNodePropertyByPropertyId (" + voProperty
                    + "does not accept [null] as input");
        }

        
        DetachedCriteria criteria = DetachedCriteria.forClass(NodeProperty.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VOPROPERTY, voProperty));
        List<?> auxList = this.executeCriteriaQuery(criteria);
        if ((auxList == null) || (auxList.size() == 0)) {
            return null;
        }
        
        return (List<NodeProperty>) auxList;
    }

    @Override
    public NodeProperty queryNodePropertyById(Integer nodePropertyId) {
        if (nodePropertyId == null) {
            throw new IllegalArgumentException("NodePropertyDaoImpl."
                    + "queryNodePropertyById (" + nodePropertyId
                    + "does not accept [null] as input");
        }
        
        DetachedCriteria criteria = DetachedCriteria.forClass(NodeProperty.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_NODE_PROPERTIES_ID, nodePropertyId));
        
        List<?> nodePropertyList = executeCriteriaQuery(criteria);        
        if ((nodePropertyList == null) || (nodePropertyList.size() == 0)) {
            return null;
        }
        
        // This query retrieves one result by row in user_node_property | group_node_property
        // so that's why we show only the first element of the list
        return (NodeProperty) nodePropertyList.get(0);
    }
    
    
}
