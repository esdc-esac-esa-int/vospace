package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.ICapabilityParamDao;
import esac.archive.vospace.persistence.model.CapabilityParam;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class CapabilityParamDaoImpl extends AbstractHibernateDAOImpl implements
        ICapabilityParamDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(CapabilityParamDaoImpl.class);
    
    @Override
    public CapabilityParam getEmptyCapabilityParam() {
        return new CapabilityParam();
    }

    @Override
    public Class<CapabilityParam> getPersistentClass() {
        return CapabilityParam.class;
    }

    @Override
    public CapabilityParam queryCapabilityParamById(Integer id) {        
        if (id == null) {
            throw new IllegalArgumentException("CapabilityParamDaoImpl.queryCapabilityParamById (" + id
                    + "does not accept [null] as input");
        }
        /*CapabilityParam capabilityParam = getEmptyCapabilityParam();
        capabilityParam.setCapabilityParamOid(id);
        
        List<?> capabilityParamList = this.findByExample(capabilityParam);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(CapabilityParamDaoImpl.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VOCAPABILITY_PARAM_ID, id));
        List<?> capabilityParamList = executeCriteriaQuery(criteria);
        
        if ((capabilityParamList == null) || (capabilityParamList.size() == 0)) {
            return null;
        } else if (capabilityParamList.size() > 1) {
            String errMsg = "More than 1 result found for CapabilityParamDaoImpl.queryCapabilityParamById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        
        return (CapabilityParam) capabilityParamList.get(0);
    }

}
