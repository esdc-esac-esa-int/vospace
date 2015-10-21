package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EVoProtocol;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class VoProtocolTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 7542420434390685134L;

    /**
     * Associated entity of this transfer object.
     */
    private EVoProtocol voProtocol = VospaceModel.getInstance().VO_PROTOCOL;
    
    /** Attribute */
    private final Attribute VO_PROTOCOL_ID = voProtocol.VO_PROTOCOL_ID;
    
    /** Attribute */
    private VOSpaceURI URI;
    
    /** Attribute */
    private VOSpaceURI END_POINT;
    
    /** Attribute */
    private List<ProtocolParamTO> protocolParamTOList;
    
    /**
     * Default constructor
     */
    public VoProtocolTO() {
        
    }

    /**
     * @return the VoProtocolId
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(VO_PROTOCOL_ID);
    }

    /**
     * @return the uri
     */
    public final VOSpaceURI getURI() {
        return this.URI;
    }

    /**
     * @return the endPoint
     */
    public final VOSpaceURI getEndPoint() {
        return this.END_POINT;
    }

    /**
     * @param voProtocolId the voProtocolId to set
     */
    public final void setId(final Integer voProtocolId) {
        super.setAttribute(VO_PROTOCOL_ID, voProtocolId);
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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((VO_PROTOCOL_ID == null) ? 0 : VO_PROTOCOL_ID.hashCode());
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
        VoProtocolTO other = (VoProtocolTO) obj;
        if (VO_PROTOCOL_ID == null) {
            if (other.VO_PROTOCOL_ID != null) {
                return false;
            }
        } else if (!VO_PROTOCOL_ID.equals(other.VO_PROTOCOL_ID)) {
            return false;
        }
        return true;
    }

    /**
     * @return the protocolParamTOList
     */
    public final List<ProtocolParamTO> getParams() {
        if (this.protocolParamTOList == null)
            this.protocolParamTOList = new ArrayList<ProtocolParamTO>();
        return protocolParamTOList;
    }

    /**
     * @param protocolParamTOList the protocolParamTOList to set
     */
    public final void setParams(
            List<ProtocolParamTO> protocolParamTOList) {
        this.protocolParamTOList = protocolParamTOList;
    }
    
}
