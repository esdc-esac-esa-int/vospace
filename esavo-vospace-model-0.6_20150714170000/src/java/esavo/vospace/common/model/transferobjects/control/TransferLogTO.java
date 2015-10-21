package esavo.vospace.common.model.transferobjects.control;

import java.io.Serializable;
import java.util.Date;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.VospaceModel;
import esavo.vospace.common.entitymodel.control.ETransferLog;

public class TransferLogTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 3829311021859167534L;

    /** Associated entity of this transfer object.     */
    private ETransferLog transferLog = VospaceModel.getInstance().TRANSFER_LOG;
        
    /** Attribute. */
    protected Attribute LOG_ID = transferLog.LOG_ID;
    
    /** Attribute. */
    /*protected Attribute USER_NAME = transferLog.USER_NAME;*/
    
    /** Attribute. */
    protected Attribute START_TIMESTAMP = transferLog.START_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute STOP_TIMESTAMP = transferLog.STOP_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute PROTOCOL_PARAM = transferLog.PROTOCOL_PARAM;
    
    /** Attribute. */
    protected Attribute VIEW_PARAM = transferLog.VIEW_PARAM;
    
    /** Attribute. */
    protected Attribute OPER_TYPE = transferLog.OPER_TYPE;
    
    /** Attribute. */
    protected Attribute TOTAL_SIZE = transferLog.TOTAL_SIZE;
    
    /** Attribute. */
    protected UserControlTO userTO;
    
    /**
     * Default constructor.
     */
    public TransferLogTO () {
        
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
        TransferLogTO other = (TransferLogTO) obj;
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
     * @return the pROTOCOL_PARAM
     */
    public final String getProtocolParam() {
        return (String) super.getAttribute(PROTOCOL_PARAM);
    }

    /**
     * @return the vIEW_PARAM
     */
    public final String getViewParam() {
        return (String) super.getAttribute(VIEW_PARAM);
    }

    /**
     * @return the oPER_TYPE
     */
    public final String getOperType() {
        return (String) super.getAttribute(OPER_TYPE);
    }

    /**
     * @return the tOTAL_SIZE
     */
    public final Long getTotalSize() {
        return (Long) super.getAttribute(TOTAL_SIZE);
    }

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
     * @param pROTOCOL_PARAM the pROTOCOL_PARAM to set
     */
    public final void setProtocolParam(String protocolParam) {
        super.setAttribute(PROTOCOL_PARAM, protocolParam);
    }

    /**
     * @param vIEW_PARAM the vIEW_PARAM to set
     */
    public final void setViewParam(String viewParam) {
        super.setAttribute(VIEW_PARAM, viewParam);
    }

    /**
     * @param oPER_TYPE the oPER_TYPE to set
     */
    public final void setOperType(String operType) {
        super.setAttribute(OPER_TYPE, operType);
    }

    /**
     * @param tOTAL_SIZE the tOTAL_SIZE to set
     */
    public final void setTotalSize(Long totalSize) {
        super.setAttribute(TOTAL_SIZE, totalSize);
    }
}
