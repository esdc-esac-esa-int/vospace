package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IVoProtocolDao;
import esac.archive.vospace.persistence.model.VoProtocol;
import esac.archive.vospace.persistence.model.VosType;
import esac.archive.vospace.persistence.util.DaoConstants;

public class VoProtocolDaoImpl extends AbstractHibernateDAOImpl implements
        IVoProtocolDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(VoProtocol.class);
    
    @Override
    public VoProtocol getEmptyVoProtocol() {
        return new VoProtocol();
    }
    
    @Override
    public Class<VoProtocol> getPersistentClass() {
        return VoProtocol.class;
    }

    @Override
    public VoProtocol queryVoProtocolById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("VoProtocolDaoImpl.queryVoProtocolsById (" + id
                    + "does not accept [null] as input");
        }
        /*VoProtocol voProtocol = getEmptyVoProtocol();
        voProtocol.setVoProtocolOid(id);        
        List<?> voProtocolList = this.findByExample(voProtocol);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(VoProtocol.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_PROTOCOL_ID, id));
        List<?> voProtocolList = executeCriteriaQuery(criteria);
        
        if ((voProtocolList == null) || (voProtocolList.size() == 0)) {
            return null;
        } else if (voProtocolList.size() > 1) {
            String errMsg = "More than 1 result found for VoProtocolDaoImpl.queryVoProtocolsById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (VoProtocol) voProtocolList.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VoProtocol> queryVoProtocolByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("VoProtocolDaoImpl.queryVoProtocolsByName (" + name
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(VoProtocol.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VOPROTOCOL_NAME, name));
        
        List<?> voProtocolList = this.executeCriteriaQuery(criteria);
        if ((voProtocolList == null) || (voProtocolList.size() == 0)) {
            return null;
        }
        return (List<VoProtocol>) voProtocolList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VoProtocol> queryVoProtocolByType(VosType type) {
        if (type == null) {
            throw new IllegalArgumentException("VoProtocolDaoImpl.queryVoProtocolByType (" + type
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(VoProtocol.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_PROTOCOLS_TYPE, type));
        
        List<?> voProtocolList = this.executeCriteriaQuery(criteria);
        if ((voProtocolList == null) || (voProtocolList.size() == 0)) {
            return null;
        }
        return (List<VoProtocol>) voProtocolList;
    }

}
