package esavo.vospace.common.model.transferobjects.control;

import java.io.Serializable;
import java.util.Date;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.VospaceModel;
import esavo.vospace.common.entitymodel.control.EAccessLog;

/**
 * AccessLog Transfer Object.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class AccessLogTO extends ResultBeanItem implements Serializable {    

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -778282874408817636L;

    /** Associated entity of this transfer object.     */
    private EAccessLog accessLog = VospaceModel.getInstance().ACCESS_LOG;
        
    /** Attribute. */
    protected Attribute LOG_ID = accessLog.LOG_ID;
    
    /** Attribute. */
    protected Attribute LOGIN_TIMESTAMP = accessLog.LOGIN_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute LOGOUT_TIMESTAMP = accessLog.LOGOUT_TIMESTAMP;
    
    /** Attribute. */
    protected Attribute QUOTA = accessLog.QUOTA;
    
    /** Attribute. */
    protected Attribute INTERNET_SITE = accessLog.INTERNET_SITE;
    
    /** Attribute. */
    protected Attribute ENVIRONMENT = accessLog.ENVIRONMENT;
    
    /** Attribute. */
    protected UserControlTO userTO;
    
    /**
     * Default constructor.
     */
    public AccessLogTO () {
        
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
        AccessLogTO other = (AccessLogTO) obj;
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
     * @return the lOGIN_TIMESTAMP
     */
    public final Date getLoginTimestamp() {
        return (Date) super.getAttribute(LOGIN_TIMESTAMP);
    }

    /**
     * @return the lOGOUT_TIMESTAMP
     */
    public final Date getLogouTimestamp() {
        return (Date) super.getAttribute(LOGOUT_TIMESTAMP);
    }

    /**
     * @return the qUOTA
     */
    public final Long getQuota() {
        return (Long) super.getAttribute(QUOTA);
    }

    /**
     * @return the iNTERNET_SITE
     */
    public final String getInternetSite() {
        return (String) super.getAttribute(INTERNET_SITE);
    }

    /**
     * @return the eNVIRONMENT
     */
    public final String getEnvironment() {
        return (String) super.getAttribute(ENVIRONMENT);
    }

    /**
     * @param lOGIN_TIMESTAMP the lOGIN_TIMESTAMP to set
     */
    public final void setLoginTimestamp(Date loginTimestamp) {
        super.setAttribute(LOGIN_TIMESTAMP, loginTimestamp);
    }

    /**
     * @param lOGOUT_TIMESTAMP the lOGOUT_TIMESTAMP to set
     */
    public final void setLogoutTimestamp(Date logoutTimestamp) {
        super.setAttribute(LOGOUT_TIMESTAMP, logoutTimestamp);
    }

    /**
     * @param qUOTA the qUOTA to set
     */
    public final void setQuota(Long quota) {
        super.setAttribute(QUOTA, quota);
    }

    /**
     * @param iNTERNET_SITE the iNTERNET_SITE to set
     */
    public final void setInternetSite(String internetSite) {
        super.setAttribute(INTERNET_SITE, internetSite);
    }

    /**
     * @param eNVIRONMENT the eNVIRONMENT to set
     */
    public final void setEnvironment(String environment) {
        super.setAttribute(ENVIRONMENT, environment);
    }
}
