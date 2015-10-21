package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.CapabilityParam;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public interface ICapabilityParamDao extends IHibernateDAO {

    /**
     * Retrieves an empty CapabilityParam
     * @return CapabilityParam empty object
     */
    CapabilityParam getEmptyCapabilityParam();
    
    /**
     * Query CapabilityParam by id.
     * @param id Identifier of CapabilityParam.
     * @return CapabilityParam if exists.
     */
    CapabilityParam queryCapabilityParamById(Integer id);
}
