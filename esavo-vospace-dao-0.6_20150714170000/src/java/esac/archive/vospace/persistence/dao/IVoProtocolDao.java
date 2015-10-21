/**
 * 
 */
package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.VoProtocol;
import esac.archive.vospace.persistence.model.VosType;

/**
 * Interface to define methods used by VoProtocol DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public interface IVoProtocolDao extends IHibernateDAO {

    /**
     * Returns an VoProtocols Class object, used by Session and Criteria object.
     * @return VoProtocols class
     */
    VoProtocol getEmptyVoProtocol();
    
    /**
     * Query VoProtocols by Id
     * @param id
     * @return VoProtocol if exists.
     */
    VoProtocol queryVoProtocolById (Integer id);
    
    /**
     * Query VoProtocols by name
     * @param name
     * @return list of VoProtocols if exists.
     */
    List < VoProtocol > queryVoProtocolByName (String name);
    
    /**
     * Query the list of VoProtocols by type. 
     * @param type
     * @return list of VoProtocols if exists.
     */
    List < VoProtocol > queryVoProtocolByType (VosType type);
}
