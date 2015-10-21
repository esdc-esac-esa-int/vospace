package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EViewParam;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ViewParamTO extends ResultBeanItem implements Serializable { 

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -9154904510641663781L;
    
    /**
     * Associated entity of this transfer object.
     */
    private EViewParam viewParam = VospaceModel.getInstance().VIEW_PARAM;
    
    /** Attribute */
    private final Attribute VIEW_PARAM_ID = viewParam.VIEW_PARAM_ID;
    
    /** Attribute */
    private VOSpaceURI URI;
    
    /** Attribute */
    private Attribute VALUE = viewParam.VALUE; 
    
    /** Attribute */
    private final Attribute VO_VIEW_ID = VospaceModel.getInstance().VO_VIEW.VO_VIEW_ID;
    
    /**
     * Default constructor
     */
    public ViewParamTO() {
        
    }

    /**
     * @return the ViewParamId
     */
    public final Integer getParamId() {
        return (Integer) super.getAttribute(VIEW_PARAM_ID);
    }

    /**
     * @return the name
     */
    public final VOSpaceURI getURI() {
        return this.URI;
    }

    /**
     * @return the value
     */
    public final String getValue() {
        return (String) super.getAttribute(VALUE);
    }

    /**
     * @param ViewParamId the ViewParamId to set
     */
    public final void setParamId(final Integer ViewParamId) {
        super.setAttribute(VIEW_PARAM_ID, ViewParamId);
    }

    /**
     * @param name the name to set
     */
    public final void setURI(final VOSpaceURI uri) {
        this.URI = uri;
    }

    /**
     * @param value the value to set
     */
    public final void setValue(final String value) {
        super.setAttribute(VALUE, value);
    }

    /**
     * @return the voViewId
     */
    public final Integer getViewId() {
        return (Integer) super.getAttribute(VO_VIEW_ID);
    }

    /**
     * @param voViewId the voViewId to set
     */
    public final void setViewId(final Integer voViewId) {
        super.setAttribute(VO_VIEW_ID, voViewId);;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((VIEW_PARAM_ID == null) ? 0 : VIEW_PARAM_ID.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ViewParamTO other = (ViewParamTO) obj;
        if (VIEW_PARAM_ID == null) {
            if (other.VIEW_PARAM_ID != null) {
                return false;
            }
        } else if (!VIEW_PARAM_ID.equals(other.VIEW_PARAM_ID)) {
            return false;
        }
        return true;
    }
}
