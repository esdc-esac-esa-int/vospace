package esavo.vospace.common.model.transferobjects.control;

import java.io.Serializable;
import java.util.Date;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.VospaceModel;
import esavo.vospace.common.entitymodel.control.EMetadataRetrievalLog;

/**
 * MetadataRetrievalLog Transfer Object.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class MetadataRetrievalLogTO extends ResultBeanItem implements
        Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -8037403318827301367L;

    /** Associated entity of this transfer object.     */
    private EMetadataRetrievalLog metadataRetrievalLog = VospaceModel.getInstance().METADATA_RETRIEVAL_LOG;
        
    /** Attribute. */
    protected Attribute LOG_ID = metadataRetrievalLog.LOG_ID;
    
    /** Attribute. */
    /*protected Attribute OWNER_NAME = metadataRetrievalLog.OWNER_NAME;*/
    
    /** Attribute. */
    protected Attribute START_TIMESTAMP = metadataRetrievalLog.START_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute STOP_TIMESTAMP = metadataRetrievalLog.STOP_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute TYPE = metadataRetrievalLog.TYPE;
    
    /** Attribute. */
    protected UserControlTO userTO;
    
    /**
     * Default constructor.
     */
    public MetadataRetrievalLogTO () {
        
    }
    
    /**
     * @return the lOG_ID
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(LOG_ID);
    }

    /**
     * @return the userTO
     */
    public final UserControlTO getUserControlTO() {
        return userTO;
    }

    /**
     * @param lOG_ID the lOG_ID to set
     */
    public final void setId(Integer logId) {
        super.setAttribute(LOG_ID, logId);
    }

    /**
     * @param userTO the userTO to set
     */
    public final void setUserControlTO(UserControlTO userTO) {
        this.userTO = userTO;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((LOG_ID == null) ? 0 : LOG_ID.hashCode());
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
        MetadataRetrievalLogTO other = (MetadataRetrievalLogTO) obj;
        if (LOG_ID == null) {
            if (other.LOG_ID != null) {
                return false;
            }
        } else if (!LOG_ID.equals(other.LOG_ID)) {
            return false;
        }
        return true;
    }

    /**
     * @return the oWNER_NAME
     *//*
    public final String getOwnerName() {
        return (String) super.getAttribute(OWNER_NAME);
    }*/

    /**
     * @return the sTART_TIMESTAMP
     */
    public final Date getStartTimestamp() {
        return (Date) super.getAttribute(START_TIMESTAMP);
    }

    /**
     * @return the sTOP_TIMESTAMP
     */
    public final Date getStopTimestamp() {
        return (Date) super.getAttribute(STOP_TIMESTAMP);
    }

    /**
     * @return the tYPE
     */
    public final String getType() {
        return (String) super.getAttribute(TYPE);
    }

    /**
     * @param oWNER_NAME the oWNER_NAME to set
     *//*
    public final void setOwnerName(String ownerName) {
        super.setAttribute(OWNER_NAME, ownerName);
    }*/

    /**
     * @param sTART_TIMESTAMP the sTART_TIMESTAMP to set
     */
    public final void setStartTimestamp(Date startTimestamp) {
        super.setAttribute(START_TIMESTAMP, startTimestamp);
    }

    /**
     * @param sTOP_TIMESTAMP the sTOP_TIMESTAMP to set
     */
    public final void setStopTimestamp(Date stopTimestamp) {
        super.setAttribute(STOP_TIMESTAMP, stopTimestamp);
    }

    /**
     * @param tYPE the tYPE to set
     */
    public final void setType(String type) {
        super.setAttribute(TYPE, type);
    }
}
