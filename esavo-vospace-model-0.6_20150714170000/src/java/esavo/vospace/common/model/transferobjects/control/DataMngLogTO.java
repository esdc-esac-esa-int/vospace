package esavo.vospace.common.model.transferobjects.control;

import java.io.Serializable;
import java.util.Date;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.VospaceModel;
import esavo.vospace.common.entitymodel.control.EDataMngLog;

/**
 * DataManagerLog Transfer Object.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class DataMngLogTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 6288350933353514389L;

    /** Associated entity of this transfer object.     */
    private EDataMngLog dataMngLog =  VospaceModel.getInstance().DATA_MNG_LOG;
    
    /** Attribute. */
    protected Attribute LOG_ID = dataMngLog.LOG_ID;
    
    /** Attribute. */
    protected Attribute START_TIMESTAMP = dataMngLog.START_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute STOP_TIMESTAMP = dataMngLog.STOP_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute NODE_SOURCE = dataMngLog.NODE_SOURCE;
    
    /** Attribute. */
    protected Attribute NODE_TARGET = dataMngLog.NODE_TARGET;
    
    /** Attribute. */
    protected Attribute OPER_TYPE = dataMngLog.OPER_TYPE;
    
    /** Attribute. */
    protected Attribute SIZE = dataMngLog.SIZE;
    
    /** Attribute. */
    protected UserControlTO userTO;
    
    /**
     * Default constructor.
     */
    public DataMngLogTO () {
        
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
    public final UserControlTO getUserTO() {
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
    public final void setUserTO(UserControlTO userTO) {
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
        DataMngLogTO other = (DataMngLogTO) obj;
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
     * @return the nODE_SOURCE
     */
    public final String getNodeSource() {
        return (String) super.getAttribute(NODE_SOURCE);
    }

    /**
     * @return the nODE_TARGET
     */
    public final String getNodeTarget() {
        return (String) super.getAttribute(NODE_TARGET);
    }

    /**
     * @return the oPER_TYPE
     */
    public final String getOperType() {
        return (String) super.getAttribute(OPER_TYPE);
    }

    /**
     * @return the sIZE
     */
    public final Long getSize() {
        return (Long) super.getAttribute(SIZE);
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
     * @param nODE_SOURCE the nODE_SOURCE to set
     */
    public final void setNodeSource(String nodeSource) {
        super.setAttribute(NODE_SOURCE, nodeSource);
    }

    /**
     * @param nODE_TARGET the nODE_TARGET to set
     */
    public final void setNodeTarget(String nodeTarget) {
        super.setAttribute(NODE_TARGET, nodeTarget);
    }

    /**
     * @param oPER_TYPE the oPER_TYPE to set
     */
    public final void setOperType(String operTYpe) {
        super.setAttribute(OPER_TYPE, operTYpe);
    }

    /**
     * @param sIZE the sIZE to set
     */
    public final void setSize(Long size) {
        super.setAttribute(SIZE, size);
    }
    
}
