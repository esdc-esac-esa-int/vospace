package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IVoViewDao;
import esac.archive.vospace.persistence.model.VoView;
import esac.archive.vospace.persistence.model.VosType;
import esac.archive.vospace.persistence.util.DaoConstants;

public class VoViewDaoImpl extends AbstractHibernateDAOImpl implements
        IVoViewDao {
    
    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(VoView.class);

    @Override
    public VoView getEmptyVoView() {
        return new VoView();
    }
    
    @Override
    public Class<VoView> getPersistentClass() {
        return VoView.class;
    }

    @Override
    public VoView queryVoViewById(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("VoViewDaoImpl.queryVoViewById (" + id
                    + "does not accept [null] as input");
        }
        /*VoView voView = getEmptyVoView();
        voView.setVoViewOid(id);
        List<?> voViewList = this.findByExample(voView);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(VoView.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VIEW_ID, id));        
        List<?> voViewList = this.executeCriteriaQuery(criteria);
        
        if ((voViewList == null) || (voViewList.size() == 0)) {
            return null;
        } else if (voViewList.size() > 1) {
            String errMsg = "More than 1 result found for VoViewDaoImpl.queryVoViewById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (VoView) voViewList.get(0);
    }
    
    @Override
    public VoView queryVoViewByUri(final String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("VoViewDaoImpl.queryVoViewByUri (" + uri
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(VoView.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VIEW_URI, uri));
        
        List<?> voViewList = this.executeCriteriaQuery(criteria);
        if ((voViewList == null) || (voViewList.size() == 0)) {
            return null;
        } else if (voViewList.size() > 1) {
            String errMsg = "More than 1 result found for VoViewDaoImpl.queryVoViewByUri("
                    + uri + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return (VoView) voViewList.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VoView> queryVoViewByType(VosType type) {
        if (type == null) {
            throw new IllegalArgumentException("VoViewDaoImpl.queryVoViewByType (" + type
                    + "does not accept [null] as input");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(VoView.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VIEW_TYPE, type));
        
        List<?> voViewList = this.executeCriteriaQuery(criteria);
        if ((voViewList == null) || (voViewList.size() == 0)) {
            return null;
        }
        return (List<VoView>) voViewList;
    }

}
