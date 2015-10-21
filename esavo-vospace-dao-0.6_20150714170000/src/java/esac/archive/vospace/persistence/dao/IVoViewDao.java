/**
 * 
 */
package esac.archive.vospace.persistence.dao;

import java.util.List;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.VoView;
import esac.archive.vospace.persistence.model.VosType;

/**
 * Interface to define methods used by VoView DAO.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public interface IVoViewDao extends IHibernateDAO {

    /**
     * Returns an VoView Class object, used by Session and Criteria object.
     * @return VoView class
     */
    VoView getEmptyVoView();
    
    /**
     * Query VoView by Id
     * @param id
     * @return VoView if exists.
     */
    VoView queryVoViewById (Integer id);
    
    /**
     * Query VoView by uri
     * @param uri
     * @return VoView if exists.
     */
    VoView queryVoViewByUri (String uri);
    
    /**
     * Query VoView by type
     * @param type ACCEPT or PROVIDE
     * @return list of VoView
     */    
    List < VoView > queryVoViewByType (VosType type);
}
