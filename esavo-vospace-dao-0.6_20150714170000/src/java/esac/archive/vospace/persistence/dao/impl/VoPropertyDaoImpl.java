/**
 * 
 */
package esac.archive.vospace.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IVoPropertyDao;
import esac.archive.vospace.persistence.model.Node;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VosType;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class VoPropertyDaoImpl extends AbstractHibernateDAOImpl implements
        IVoPropertyDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(VoPropertyDaoImpl.class);
    
    @Override
    public final Class<VoProperty> getPersistentClass() {
        return VoProperty.class;
    }
    
    @Override
    public VoProperty getEmptyVoProperty() {
        return new VoProperty();
    }

    @Override
    public VoProperty queryVoPropertyById(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("VoPropertyDaoImpl.queryVoPropertyById (" + id
                    + "does not accept [null] as input");
        }
        /*VoProperty voProperty = getEmptyVoProperty();
        voProperty.setVoPropertyOid(id);
        
        List<?> voPropertiesList = this.findByExample(voProperty);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(VoProperty.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VOPROPERTY_ID, id));
        List<?> voPropertiesList = executeCriteriaQuery(criteria);
        
        if ((voPropertiesList == null) || (voPropertiesList.size() == 0)) {
            return null;
        } else if (voPropertiesList.size() > 1) {
            String errMsg = "More than 1 result found for VoPropertyDaoImpl.queryVoPropertyById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (VoProperty) voPropertiesList.get(0);
    }

    @Override
    public VoProperty queryVoPropertyByUri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("VoPropertyDaoImpl.queryVoPropertyByName (" + uri
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(VoProperty.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VOPROPERTIES_NAME, uri));
        
        List<?> voPropertiesList = this.executeCriteriaQuery(criteria);
        if ((voPropertiesList == null) || (voPropertiesList.size() == 0)) {
            return null;
        } else if (voPropertiesList.size() > 1) {
            String errMsg = "More than 1 result found for VoPropertyDaoImpl.queryVoPropertyByUri("
                    + uri + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        
        return (VoProperty) voPropertiesList.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VoProperty> queryVoPropertyByType(VosType type) {
        if (type == null) {
            throw new IllegalArgumentException("VoPropertyDaoImpl.queryPropertyByType (" + type
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(VoProperty.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_PROPERTIES_TYPE, type));
        
        List<?> voPropertiesList = this.executeCriteriaQuery(criteria);
        if ((voPropertiesList == null) || (voPropertiesList.size() == 0)) {
            return null;
        }
        return (List<VoProperty>) voPropertiesList;
    }
    
    @Override
    public List<VoProperty> queryPropertiesUsed() {
        
        /*String query = "SELECT vo_property.uri FROM vos.vo_property " +
        "INNER JOIN vos.node_property ON "
        + "vo_property.vo_property_oid = node_property.property_id "
        + "ORDER BY vo_property.uri;";*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Node.class)
                .createCriteria("nodePropertySet")
                .createAlias("voProperty", "p")
                .setProjection(Projections.groupProperty("p.voPropertyOid"));
        
        @SuppressWarnings("unchecked")
        List<Integer> voPropertiesURIList = 
                (List<Integer>) this.executeCriteriaQuery(criteria);
        List<VoProperty> voPropertiesList = new ArrayList<VoProperty>();
        if ((voPropertiesURIList == null) || (voPropertiesURIList.size() == 0)) {
            return null;
        } else {
            for (Integer id : voPropertiesURIList) {
                voPropertiesList.add(queryVoPropertyById(id));
            }
        }
        return (List<VoProperty>) voPropertiesList;
    }

}
