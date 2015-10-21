package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IProtocolParamDao;
import esac.archive.vospace.persistence.model.ProtocolParam;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ProtocolParamDaoImpl extends AbstractHibernateDAOImpl implements
        IProtocolParamDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(ProtocolParamDaoImpl.class);
    
    @Override
    public ProtocolParam getEmptyProtocolParam() {
        return new ProtocolParam();
    }

    @Override
    public Class<ProtocolParam> getPersistentClass() {
        return ProtocolParam.class;
    }

    @Override
    public ProtocolParam queryProtocolParamById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ProtocolParamDaoImpl.queryProtocolParamById (" + id
                    + "does not accept [null] as input");
        }
        /*ProtocolParam protocolParam = getEmptyProtocolParam();
        protocolParam.setProtocolParamOid(id);        
        List<?> protocolParamList = this.findByExample(protocolParam);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(ProtocolParam.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_PROTOCOL_PARAM_ID, id));
        List<?> protocolParamList = executeCriteriaQuery(criteria);
        
        if ((protocolParamList == null) || (protocolParamList.size() == 0)) {
            return null;
        } else if (protocolParamList.size() > 1) {
            String errMsg = "More than 1 result found for ProtocolParamDaoImpl.queryProtocolParamById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        
        return (ProtocolParam) protocolParamList.get(0);
    }

}
