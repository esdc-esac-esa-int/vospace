package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.ENodeProperty;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class NodePropertyTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -7576280591965228271L;

    /**
     * Associated entity of this transfer object.
     */
    private ENodeProperty nodeProperty = VospaceModel.getInstance().NODE_PROPERTY;
    
    /** Attribute */
    private Attribute NODE_PROPERTY_ID = nodeProperty.NODE_PROPERTY_ID;
    
    /** Attribute */
    protected VoPropertyTO voPropertyTO;
    
    /** Attribute */
    protected NodeTO nodeTO;
    
    /** Attribute */
    private Attribute VALUE = nodeProperty.VALUE;
    
    /** Boolean **/
    private Boolean nill;
    
    /**
     * Default constructor.
     */
    public NodePropertyTO() {
        this.nill = false;
    }

    /**
     * @return the value
     */
    public final String getValue() {
        return (String) super.getAttribute(VALUE);
    }

    /**
     * @param value the value to set
     */
    public final void setValue(final String value) {
        super.setAttribute(VALUE, value);
    }
    
    /*
    *//**
     * @return the voPropertyId
     *//*
    public final Integer getVoPropertyId() {
        return (Integer) super.getAttribute(VO_PROPERTY_ID);
    }

    *//**
     * @param voPropertyId the voPropertyId to set
     *//*
    public final void setVoPropertyId(final Integer voPropertyId) {
        super.setAttribute(VO_PROPERTY_ID, voPropertyId);;
    }*/
    
    /**
     * @return the VoPropertyUri
     *//*
    public final VOSpaceURI getURI() {
        return this.VO_PROPERTY_URI;
    }

    *//**
     * @param VoPropertyUri the VoPropertyUri to set
     *//*
    public final void setURI(final VOSpaceURI uri) {
        this.VO_PROPERTY_URI = uri;
    }*/
    
    /**
     * @return the nill
     */
    public final Boolean getNill() {
        return this.nill;
    }

    /**
     * @param nill the nill to set
     */
    public final void setNill(Boolean nill) {
        this.nill = nill;
    }

    /**
     * @return the voPropertyTO
     */
    public final VoPropertyTO getVoPropertyTO() {
        return voPropertyTO;
    }

    /**
     * @param voPropertyTO the voPropertyTO to set
     */
    public final void setVoPropertyTO(VoPropertyTO voPropertyTO) {
        this.voPropertyTO = voPropertyTO;
    }
    
    /**
     * @return the nodeTO
     */
    public final NodeTO getNodeTO() {
        return nodeTO;
    }

    /**
     * @param nodeTO the nodeTO to set
     */
    public final void setNodeTO(NodeTO nodeTO) {
        this.nodeTO = nodeTO;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((NODE_PROPERTY_ID == null) ? 0 : NODE_PROPERTY_ID.hashCode());
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
        NodePropertyTO other = (NodePropertyTO) obj;
        if (NODE_PROPERTY_ID == null) {
            if (other.NODE_PROPERTY_ID != null) {
                return false;
            }
        } else if (!((Integer)super.getAttribute(NODE_PROPERTY_ID))
                .equals((Integer)other.getAttribute(NODE_PROPERTY_ID))) {
            return false;
        }
        return true;
    }    
}
