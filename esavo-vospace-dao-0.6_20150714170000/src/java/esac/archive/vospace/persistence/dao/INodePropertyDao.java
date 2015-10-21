package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.NodeProperty;
import esac.archive.vospace.persistence.model.VoProperty;

/**
 * Represents the association between Node and Property.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public interface INodePropertyDao extends IHibernateDAO {

    /**
     * Returns an NodeProperty Class object, used by Session and Criteria object.
     * @return NodeProperty class
     */
    NodeProperty getEmptyNodeProperty();
    
    /**
     * Query NodePropertyby Id
     * @param nodeId
     * @return
     */
    NodeProperty queryNodePropertyById(Integer nodeId);
    
    /**
     * Query the list of node properties by VoProperty
     * @param voProperty
     * @return List<NodeProperty> if exists
     */
    List<NodeProperty> queryNodePropertyByVoProperty(VoProperty voPropertyId);
    
}
