/**
 * 
 */
package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.VoProperty;
import esac.archive.vospace.persistence.model.VosType;

/**
 * Interface to define methods used by VoProperty DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public interface IVoPropertyDao extends IHibernateDAO {
    
    /**
     * Returns an VoProperties Class object, used by Session and Criteria object.
     * @return VoProperties class
     */
    VoProperty getEmptyVoProperty();
    
    /**
     * Query VoProperties by Id.
     * @param id of VoProperty
     * @return VoProperties if found.
     */
    VoProperty queryVoPropertyById (Integer id);
    
    /**
     * Query VoProperties by name.
     * @param uri of VoProperty
     * @return VoProperty if found.
     */
    VoProperty queryVoPropertyByUri (String uri);
    
    /**
     * Query the list of properties by type
     * @param type of the property
     * @return List<VoProperty>
     */
    List<VoProperty> queryVoPropertyByType(VosType type);
    
    /**
     * Query the list of properties used by nodes.
     * @return List<VoProperty>
     */
    List<VoProperty> queryPropertiesUsed();
}
