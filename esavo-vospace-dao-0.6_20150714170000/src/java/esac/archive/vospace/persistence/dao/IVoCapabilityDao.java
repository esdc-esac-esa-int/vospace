/**
 * 
 */
package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.VoCapability;

/**
 * Interface to define methods used by VoCapability DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public interface IVoCapabilityDao extends IHibernateDAO {

    /**
     * Returns an VoCapabilities Class object, used by Session and Criteria object.
     * @return VoCapabilities class
     */
    VoCapability getEmptyVoCapability();
    
    /**
     * Query VoCapabilities by Id.
     * @param id
     * @return Capability if exists.
     */
    VoCapability queryVoCapabilityById (Integer id);
    
    /**
     * Query VoCapabilities by name.
     * @param name of capability
     * @return Capability
     */
    VoCapability queryVoCapabilityByUri (String name);
}
