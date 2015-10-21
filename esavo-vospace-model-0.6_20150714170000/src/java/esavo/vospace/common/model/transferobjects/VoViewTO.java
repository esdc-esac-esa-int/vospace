package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EVoView;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class VoViewTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 4410638801820664321L;

    /**
     * Associated entity of this transfer object.
     */
    private EVoView voView = VospaceModel.getInstance().VO_VIEW;
    
    /** Attribute */
    private final Attribute VO_VIEW_ID = voView.VO_VIEW_ID;
    
    /** Attribute */
    private VOSpaceURI URI;
    
    /** Attribute */
    private Attribute ORIGINAL = voView.ORIGINAL;
    
    /** Attribute */
    private List<ViewParamTO> viewParamTOList;
    
    /**
     * Default constructor
     */
    public VoViewTO() {
        
    }

    /**
     * @return the VoViewId
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(VO_VIEW_ID);
    }

    /**
     * @return the ur
     */
    public final VOSpaceURI getURI() {
        return this.URI;
    }

    /**
     * @return the original
     */
    public final Boolean getOriginal() {
        Boolean originalValue = (Boolean) super.getAttribute(ORIGINAL);
        if (originalValue == null) {
            setOriginal((Boolean)true);
        }
        return (Boolean) super.getAttribute(ORIGINAL);
    }

    /**
     * @param VoViewId the VoViewId to set
     */
    public final void setId(final Integer voViewId) {
        super.setAttribute(VO_VIEW_ID, voViewId);
    }

    /**
     * @param uri the uri to set
     */
    public final void setURI(final VOSpaceURI uri) {
        this.URI = uri;;
    }

    /**
     * @param original the original to set
     */
    public final void setOriginal(final Boolean original) {
        super.setAttribute(ORIGINAL, original);
    }
    
    /**
     * @return the viewParamTOList
     */
    public final List<ViewParamTO> getParams() {
        if (this.viewParamTOList == null)
            this.viewParamTOList = new ArrayList<ViewParamTO>();
        return viewParamTOList;
    }

    /**
     * @param viewParamTOList the viewParamTOList to set
     */
    public final void setParams(List<ViewParamTO> viewParamTOList) {
        this.viewParamTOList = viewParamTOList;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((VO_VIEW_ID == null) ? 0 : VO_VIEW_ID.hashCode());
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
        VoViewTO other = (VoViewTO) obj;
        if (VO_VIEW_ID == null) {
            if (other.VO_VIEW_ID != null) {
                return false;
            }
        } else if (!VO_VIEW_ID.equals(other.VO_VIEW_ID)) {
            return false;
        }
        return true;
    }    
}
