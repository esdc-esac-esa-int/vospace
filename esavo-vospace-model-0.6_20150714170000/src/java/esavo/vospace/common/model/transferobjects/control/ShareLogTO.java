package esavo.vospace.common.model.transferobjects.control;

import java.io.Serializable;
import java.util.Date;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.VospaceModel;
import esavo.vospace.common.entitymodel.control.EShareLog;

/**
 * ShareLog Transfer Object.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class ShareLogTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 78955170093614511L;

    /** Associated entity of this transfer object.     */
    private EShareLog shareLog = VospaceModel.getInstance().SHARE_LOG;
        
    /** Attribute. */
    protected Attribute LOG_ID = shareLog.LOG_ID;
    
    /** Attribute. */
    /*protected Attribute OWNER_NAME = shareLog.OWNER_NAME;*/
    
    /** Attribute. */
    protected Attribute OPERATION_TIMESTAMP = shareLog.OPERATION_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute END_TIMESTAMP = shareLog.END_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute NODE = shareLog.NODE;
    
    /** Attribute. */
    protected UserControlTO userTO;
    
    /**
     * Default constructor.
     */
    public ShareLogTO () {
        
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
        ShareLogTO other = (ShareLogTO) obj;
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
     */
    /*public final String getOwnerName() {
        return (String) super.getAttribute(OWNER_NAME);
    }*/

    /**
     * @return the oPERATION_TIMESTAMP
     */
    public final Date getOperationTimestamp() {
        return (Date) super.getAttribute(OPERATION_TIMESTAMP);
    }

    /**
     * @return the eND_TIMESTAMP
     */
    public final Date getEndTimestamp() {
        return (Date) super.getAttribute(END_TIMESTAMP);
    }

    /**
     * @return the nODE
     */
    public final String getNode() {
        return (String) super.getAttribute(NODE);
    }

    /**
     * @param oWNER_NAME the oWNER_NAME to set
     */
    /*public final void setOwnerName(String ownerName) {
        super.setAttribute(OWNER_NAME, ownerName);
    }*/

    /**
     * @param oPERATION_TIMESTAMP the oPERATION_TIMESTAMP to set
     */
    public final void setOperationTimestamp(Date operationTimestamp) {
        super.setAttribute(OPERATION_TIMESTAMP, operationTimestamp);
    }

    /**
     * @param eND_TIMESTAMP the eND_TIMESTAMP to set
     */
    public final void setEndTimestamp(Date endTimestamp) {
        super.setAttribute(END_TIMESTAMP, endTimestamp);
    }

    /**
     * @param nODE the nODE to set
     */
    public final void setNode(String node) {
        super.setAttribute(NODE, node);
    }
}
