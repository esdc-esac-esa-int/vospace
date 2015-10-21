package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EUser;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class UserTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 3063150472114786480L;

    /**
     * Associated entity of this transfer object.
     */
    private EUser user = VospaceModel.getInstance().USER;
    
    /** Attribute */
    private final Attribute USER_ID = user.USER_ID;
    
    /** Attribute */
    private final Attribute NAME = user.NAME;
    
    /** Attribute */
    private final Attribute FULL_NAME = user.FULL_NAME;
    
    /** Attribute */
    private final Attribute MAIL = user.MAIL;
    
    /** Attribute */
    private Attribute QUOTA = user.QUOTA;
    
    /** Attribute */
    private Attribute QLIMIT = user.QLIMIT;
    
    /** Attribute */
    private Attribute CREATION_DATE = user.CREATION_DATE;
    
    /** Groups managed by me */
    private List<GroupTO> myGroups;
    
    /** Groups I'm memeber of */
    private List<GroupTO> isMember;
    
    /** Nodes shared with me */
    private List<NodePropertyTO> sharedWithMe;
    
    /**
     * Default constructor
     */
    public UserTO() {
        
    }

    /**
     * @return the UserId
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(USER_ID);
    }

    /**
     * @return the name
     */
    public final String getName() {
        return (String) super.getAttribute(NAME);
    }

    /**
     * @return the fullName
     */
    public final String getFullName() {
        return (String) super.getAttribute(FULL_NAME);
    }

    /**
     * @return the mail
     */
    public final String getMail() {
        return (String) super.getAttribute(MAIL);
    }

    /**
     * @return the Quota
     */
    public final Long getQuota() {
        return (Long) super.getAttribute(QUOTA);
    }
    
    /**
     * @return the Quota Limit
     */
    public final Long getQLimit() {
        return (Long) super.getAttribute(QLIMIT);
    }

    /**
     * @return the creationDate
     */
    public final Date getCreationDate() {
        return (Date) super.getAttribute(CREATION_DATE);
    }

    /**
     * @param userId the userId to set
     */
    public final void setId(final Integer userId) {
        super.setAttribute(USER_ID, userId);
    }

    /**
     * @param name the name to set
     */
    public final void setName(final String name) {
        super.setAttribute(NAME, name);
    }

    /**
     * @param fullName the fullName to set
     */
    public final void setFullName(final String fullName) {
        super.setAttribute(FULL_NAME, fullName);
    }

    /**
     * @param mail the mail to set
     */
    public final void setMail(final String mail) {
        super.setAttribute(MAIL, mail);
    }

    /**
     * @param quota the quota to set
     */
    public final void setQuota(final Long  quota) {
        super.setAttribute(QUOTA, quota);
    }
    
    /**
     * @param quota the quota limit to set
     */
    public final void setQLimit(final Long  qLimit) {
        super.setAttribute(QLIMIT, qLimit);
    }

    /**
     * @param creationDate the creationDate to set
     */
    public final void setCreationDate(final Date creationDate) {
        super.setAttribute(CREATION_DATE, creationDate);
    }
    
    /**
     * @return the myGroups
     */
    public final List<GroupTO> getMyGroups() {
        if (this.myGroups == null)
            this.myGroups = new ArrayList<GroupTO>();
        return myGroups;
    }

    /**
     * @return the isMember
     */
    public final List<GroupTO> getIsMember() {
        if (this.isMember == null)
            this.isMember = new ArrayList<GroupTO>();
        return isMember;
    }

    /**
     * @return the sharedWithMe
     */
    public final List<NodePropertyTO> getSharedWithMe() {
        if (this.sharedWithMe == null)
            this.sharedWithMe = new ArrayList<NodePropertyTO>();
        return sharedWithMe;
    }

    /**
     * @param myGroups the myGroups to set
     */
    public final void setMyGroups(List<GroupTO> myGroups) {
        this.myGroups = myGroups;
    }

    /**
     * @param isMember the isMember to set
     */
    public final void setIsMember(List<GroupTO> isMember) {
        this.isMember = isMember;
    }

    /**
     * @param sharedWithMe the sharedWithMe to set
     */
    public final void setSharedWithMe(List<NodePropertyTO> sharedWithMe) {
        this.sharedWithMe = sharedWithMe;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((USER_ID == null) ? 0 : USER_ID.hashCode());
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
        UserTO other = (UserTO) obj;
        if (USER_ID == null) {
            if (other.USER_ID != null) {
                return false;
            }
        } else if (!USER_ID.equals(other.USER_ID)) {
            return false;
        }
        return true;
    }
}
