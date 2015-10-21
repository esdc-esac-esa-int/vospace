package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EProtocolParam;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ProtocolParamTO extends ResultBeanItem implements Serializable {  

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -3083798684369254551L;
    
    /**
     * Associated entity of this transfer object.
     */
    private EProtocolParam protocolParam = VospaceModel.getInstance().PROTOCOL_PARAM;
    
    /** Attribute */
    private final Attribute PROTOCOL_PARAM_ID = protocolParam.PROTOCOL_PARAM_ID;
    
    /** Attribute */
    private VOSpaceURI URI;
    
    /** Attribute */
    private Attribute VALUE = protocolParam.VALUE;
    
    /** Attribute */
    private final Attribute VO_PROTOCOL_ID = VospaceModel.getInstance().VO_PROTOCOL.VO_PROTOCOL_ID;
    
    /**
     * Default constructor
     */
    public ProtocolParamTO() {
        
    }

    /**
     * @return the ProtocolParamId
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(PROTOCOL_PARAM_ID);
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
     * @param ProtocolParamId the ProtocolParamId to set
     */
    public final void setId(final Integer ProtocolParamId) {
        super.setAttribute(PROTOCOL_PARAM_ID, ProtocolParamId);
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
     * @return the voProtocolId
     */
    public final Integer getProtocolId() {
        return (Integer) super.getAttribute(VO_PROTOCOL_ID);
    }

    /**
     * @param voProtocolId the voProtocolId to set
     */
    public final void setProtocolId(final Integer voProtocolId) {
        super.setAttribute(VO_PROTOCOL_ID, voProtocolId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((PROTOCOL_PARAM_ID == null) ? 0 : PROTOCOL_PARAM_ID.hashCode());
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
        ProtocolParamTO other = (ProtocolParamTO) obj;
        if (PROTOCOL_PARAM_ID == null) {
            if (other.PROTOCOL_PARAM_ID != null) {
                return false;
            }
        } else if (!PROTOCOL_PARAM_ID.equals(other.PROTOCOL_PARAM_ID)) {
            return false;
        }
        return true;
    }
}
