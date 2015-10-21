package esavo.vospace.common.model.transferobjects.control;

import java.io.Serializable;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.VospaceModel;
import esavo.vospace.common.entitymodel.control.EGroupControl;

/**
 * GroupControl Transfer Object.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class GroupControlTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 3094103721077893618L;

    /** Associated entity of this transfer object.     */
    private EGroupControl groupControl = VospaceModel.getInstance().GROUP_CONTROL;
    
    /** Attribute. */
    protected Attribute LOG_ID = groupControl.LOG_ID;
    
    /** Attribute. */
    protected UserControlTO userTO;
    
    /**
     * Default constructor.
     */
    public GroupControlTO () {
        
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
        GroupControlTO other = (GroupControlTO) obj;
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
}
