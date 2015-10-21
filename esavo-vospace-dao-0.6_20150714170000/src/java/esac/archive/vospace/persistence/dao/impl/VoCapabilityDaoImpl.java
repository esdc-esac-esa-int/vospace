package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IVoCapabilityDao;
import esac.archive.vospace.persistence.model.VoCapability;
import esac.archive.vospace.persistence.util.DaoConstants;

public class VoCapabilityDaoImpl extends AbstractHibernateDAOImpl implements
        IVoCapabilityDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(VoCapability.class);
    
    @Override
    public VoCapability getEmptyVoCapability() {
        return new VoCapability();
    }
    
    @Override
    public Class<VoCapability> getPersistentClass() {
        return VoCapability.class;
    }

    @Override
    public VoCapability queryVoCapabilityById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("VoCapabilityDaoImpl.queryVoCapabilitiesById (" + id
                    + "does not accept [null] as input");
        }
        /*VoCapability voCapability = getEmptyVoCapability();
        voCapability.setVoCapabilityOid(id);        
        List<?> capabilitiesList = this.findByExample(voCapability);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(VoCapability.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VOCAPABILITY_ID, id));
        List<?> capabilitiesList = executeCriteriaQuery(criteria);
        
        if ((capabilitiesList == null) || (capabilitiesList.size() == 0)) {
            return null;
        } else if (capabilitiesList.size() > 1) {
            String errMsg = "More than 1 result found for VoCapabilityDaoImpl.queryVoCapabilitiesById ("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (VoCapability) capabilitiesList.get(0);
    }

    @Override
    public VoCapability queryVoCapabilityByUri(final String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("VoCapabilityDaoImpl.queryVoCapabilitiesByName (" + uri
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(VoCapability.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VOCAPABILITIES_NAME, uri));
        
        List<?> capabilitiesList = this.executeCriteriaQuery(criteria);
        if ((capabilitiesList == null) || (capabilitiesList.size() == 0)) {
            return null;
        } else if (capabilitiesList.size() > 1) {
            String errMsg = "More than 1 result found for VoCapabilityDaoImpl.queryVoCapabilitiesByUri ("
                    + uri + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (VoCapability) capabilitiesList.get(0);
    }

}
