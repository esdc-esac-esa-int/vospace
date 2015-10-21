package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EVoCapability;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class VoCapabilityTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -235628698088685817L;
    
    /**
     * Associated entity of this transfer object.
     */
    private final EVoCapability voCapability = VospaceModel.getInstance().VO_CAPABILITY;
    
    /** Attribute */
    private final Attribute VO_CAPABILITY_ID = voCapability.VO_CAPABILITY_ID;
    
    /** Attribute */
    private VOSpaceURI URI;
    
    /** Attribute */
    private VOSpaceURI END_POINT;
    
    /** Attribute */
    private List<CapabilityParamTO> capabilityParamTOList;
    
    /**
     * Default constructor
     */
    public VoCapabilityTO() {
        
    }

    /**
     * @return the VoCapabilityId
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(VO_CAPABILITY_ID);
    }

    /**
     * @return the uri
     */
    public final VOSpaceURI getURI() {
        return this.URI;
    }

    /**
     * @return the EndPoint
     */
    public final VOSpaceURI getEndPoint() {
        return this.END_POINT;
    }

    /**
     * @param VoCapabilityId the VoCapabilityId to set
     */
    public final void setCapabilityId(final Integer voCapabilityId) {
        super.setAttribute(VO_CAPABILITY_ID, voCapabilityId);
    }

    /**
     * @param uri the uri to set
     */
    public final void setURI(final VOSpaceURI uri) {
        this.URI = uri;
    }

    /**
     * @param endPoint the endPoint to set
     */
    public final void setEndPoint(final VOSpaceURI endPoint) {
        this.END_POINT = endPoint;
    }

    /**
     * @return the capabilityParamTOList
     */
    public final List<CapabilityParamTO> getParams() {
        if (this.capabilityParamTOList == null)
            this.capabilityParamTOList = new ArrayList<CapabilityParamTO>();
        return capabilityParamTOList;
    }

    /**
     * @param capabilityParamTOList the capabilityParamTOList to set
     */
    public final void setParams(
            List<CapabilityParamTO> capabilityParamTOList) {
        this.capabilityParamTOList = capabilityParamTOList;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((VO_CAPABILITY_ID == null) ? 0 : VO_CAPABILITY_ID.hashCode());
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
        VoCapabilityTO other = (VoCapabilityTO) obj;
        if (VO_CAPABILITY_ID == null) {
            if (other.VO_CAPABILITY_ID != null) {
                return false;
            }
        } else if (!VO_CAPABILITY_ID.equals(other.VO_CAPABILITY_ID)) {
            return false;
        }
        return true;
    }

}
