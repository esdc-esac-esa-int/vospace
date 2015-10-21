package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.ProtocolParam;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public interface IProtocolParamDao extends IHibernateDAO {

    /**
     * Retrives an empty ProtocolParam.
     * @return empty ProtocolParam object.
     */
    ProtocolParam getEmptyProtocolParam();
    
    /**
     * Retrieves a ProtocolParam by id. 
     * @param id Identifier of the ProtocolParam.
     * @return ProtocolParam if exists.
     */
    ProtocolParam queryProtocolParamById(Integer id);
}
