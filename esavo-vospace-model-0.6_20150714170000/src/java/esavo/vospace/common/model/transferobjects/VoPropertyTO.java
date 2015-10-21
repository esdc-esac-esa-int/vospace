package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EVoProperty;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class VoPropertyTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 7354171843324524405L;
    
    /**
     * Associated entity of this transfer object.
     */
    private final EVoProperty voProperty = VospaceModel.getInstance().VO_PROPERTY; 
    
    /** Attribute */
    private final Attribute VO_PROPERTY_ID = voProperty.VO_PROPERTY_ID;
    
    /** Attribute */
    private Attribute URI = voProperty.URI;
    
    /** Attribute */
    private Attribute DESCRIPTION = voProperty.DESCRIPTION;
    
    /** Attribute */
    private Attribute READ_ONLY = voProperty.READ_ONLY;
    
    /**
     * Default constructor
     */
    public VoPropertyTO() {
        
    }

    /**
     * @return the VoPropertyId
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(VO_PROPERTY_ID);
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return (String) super.getAttribute(DESCRIPTION);
    }

    /**
     * @return the readOnly
     */
    public final Boolean getReadOnly() {
        return (Boolean) super.getAttribute(READ_ONLY);
    }

    /**
     * @param voPropertyId the voPropertyId to set
     */
    public final void setId(final Integer voPropertyId) {
        super.setAttribute(VO_PROPERTY_ID, voPropertyId);
    }

    /**
     * @param description the description to set
     */
    public final void setDescription(final String description) {
        super.setAttribute(DESCRIPTION, description);
    }

    /**
     * @param ReadOnly the ReadOnly to set
     */
    public final void setReadOnly(final Boolean readOnly) {
        super.setAttribute(READ_ONLY, readOnly);
    }
    
    /**
     * @return the uRI
     */
    public final String getURI() {
        return (String) super.getAttribute(URI);
    }

    /**
     * @param uRI the uRI to set
     */
    public final void setURI(final String uri) {
        super.setAttribute(URI, uri);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((VO_PROPERTY_ID == null) ? 0 : VO_PROPERTY_ID.hashCode());
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
        VoPropertyTO other = (VoPropertyTO) obj;
        if (VO_PROPERTY_ID == null) {
            if (other.VO_PROPERTY_ID != null) {
                return false;
            }
        } else if (!VO_PROPERTY_ID.equals(other.VO_PROPERTY_ID)) {
            return false;
        }
        return true;
    }
}
