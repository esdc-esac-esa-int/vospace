package esac.archive.vospace.persistence.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import esac.archive.absi.persistence.dao.impl.AbstractHibernateDAOImpl;
import esac.archive.vospace.persistence.dao.IViewParamDao;
import esac.archive.vospace.persistence.model.ViewParam;
import esac.archive.vospace.persistence.util.DaoConstants;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ViewParamDaoImpl extends AbstractHibernateDAOImpl implements
        IViewParamDao {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(ViewParamDaoImpl.class);
    
    @Override
    public ViewParam getEmptyViewParam() {
        return new ViewParam();
    }

    @Override
    public Class<ViewParam> getPersistentClass() {
        return ViewParam.class;
    }

    @Override
    public ViewParam queryViewParamById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ViewParamDaoImpl.queryViewParamById (" + id
                    + "does not accept [null] as input");
        }
        /*ViewParam viewParam = getEmptyViewParam();
        viewParam.setViewParamOid(id);        
        List<?> viewParamList = this.findByExample(viewParam);*/
        
        DetachedCriteria criteria = DetachedCriteria.forClass(ViewParam.class);
        criteria.add(Restrictions.eq(DaoConstants.DB_VIEW_PARAM_ID, id));
        List<?> viewParamList = executeCriteriaQuery(criteria);
        
        if ((viewParamList == null) || (viewParamList.size() == 0)) {
            return null;
        } else if (viewParamList.size() > 1) {
            String errMsg = "More than 1 result found for ViewParamDaoImpl.queryViewParamById("
                    + id + ")";
            log.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }
        
        return (ViewParam) viewParamList.get(0);
    }

}
