package esavo.vospace.common.model.transferobjects;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.ECapabilityParam;
import esavo.vospace.common.entitymodel.VospaceModel;

import java.io.Serializable;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class CapabilityParamTO extends ResultBeanItem implements Serializable { 

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 8587328995715148538L;
    
    /**
     * Associated entity of this transfer object.
     */
    private ECapabilityParam capabilityParam = VospaceModel.getInstance().CAPABILITY_PARAM;
    
    /** Attribute. */
    private Attribute CAPABILITY_PARAM_ID = capabilityParam.CAPABILITY_PARAM_ID;
    
    /** Attribute. */
    private VOSpaceURI URI;
    
    /** Attribute. */
    private Attribute VALUE = capabilityParam.VALUE;
    
    /** Attribute */
    private Attribute VO_CAPABILITY_ID = VospaceModel.getInstance().VO_CAPABILITY.VO_CAPABILITY_ID;
    
    /**
     * Default constructor.
     */
    public CapabilityParamTO() {
        
    }

    public final Integer getParamId() {
        return (Integer) super.getAttribute(CAPABILITY_PARAM_ID);
    }

    public final VOSpaceURI getURI() {
        return this.URI;
    }

    public final String getValue() {
        return (String) super.getAttribute(VALUE);
    }

    public final void setParamId(final Integer capabilityParam) {
        super.setAttribute(CAPABILITY_PARAM_ID, capabilityParam);
    }

    public final void setURI(final VOSpaceURI uri) {
        this.URI = uri;
    }

    public final void setValue(final String value) {
        super.setAttribute(VALUE, value);
    }
    
    /**
     * @return the voCapabilityId
     */
    public final Integer getCapabilityId() {
        return (Integer) super.getAttribute(VO_CAPABILITY_ID);
    }

    /**
     * @param voCapabilityId the voCapabilityId to set
     */
    public final void setCapabilityId(final Integer voCapabilityId) {
        super.setAttribute(VO_CAPABILITY_ID, voCapabilityId);;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((CAPABILITY_PARAM_ID == null) ? 0 : CAPABILITY_PARAM_ID.hashCode());
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
        CapabilityParamTO other = (CapabilityParamTO) obj;
        if (CAPABILITY_PARAM_ID == null) {
            if (other.CAPABILITY_PARAM_ID != null) {
                return false;
            }
        } else if (!CAPABILITY_PARAM_ID.equals(other.CAPABILITY_PARAM_ID)) {
            return false;
        }
        return true;
    }

}
