package esac.archive.vospace.persistence.dao;

import esac.archive.absi.persistence.dao.IHibernateDAO;
import esac.archive.vospace.persistence.model.ViewParam;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public interface IViewParamDao extends IHibernateDAO {

    /**
     * Retrieves an empty ViewParam.
     * @return ViewParam empty object.
     */
    ViewParam getEmptyViewParam();
    
    /**
     * Query an empty ViewParam by id.
     * @param id Identifier of ViewParam
     * @return ViewParam if exist.
     */
    ViewParam queryViewParamById(Integer id);
}
