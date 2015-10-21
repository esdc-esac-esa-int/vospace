package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.EGroup;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class GroupTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -6269857861383250342L;
    
    /**
     * Associated entity of this transfer object.
     */
    private EGroup group = VospaceModel.getInstance().GROUP;
    
    /** Attribute. */
    private Attribute GROUP_ID = group.GROUP_ID;
    
    /** Attribute. */
    protected UserTO managerTO;
    
    /** Attribute. */
    private Attribute NAME = group.GROUP_NAME;
    
    /** Attribute. */
    private Attribute DESCRIPTION = group.DESCRIPTION;
    
    /** Attribute */
    private Attribute CREATION_DATE = group.CREATION_DATE;
    
    /** Attribute. */
    protected List<UserTO> members;
    
    /** Attribute. */
    protected List<NodePropertyTO> nodePropertyTOList;
    
    /**
     * Default constructor.
     */
    public GroupTO() {
        
    }

    /**
     * @return the Name
     */
    public final String getName() {
        return (String) super.getAttribute(NAME);
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return (String) super.getAttribute(DESCRIPTION);
    }
    
    /**
     * @return the creationDate
     */
    public final Date getCreationDate() {
        return (Date) super.getAttribute(CREATION_DATE);
    }
    
    public UserTO getManagerTO() {
		return managerTO;
	}

	public void setManagerTO(UserTO managerTO) {
		this.managerTO = managerTO;
	}

    /**
     * @param name the name to set
     */
    public final void setName(final String name) {
        super.setAttribute(NAME, name);
    }

    /**
     * @param description the description to set
     */
    public final void setDescription(final String description) {
        super.setAttribute(DESCRIPTION, description);
    }

    /**
     * @return the userGroupTOList
     */
    public final List<UserTO> getMembers() {
        if (this.members == null) {
            this.members = new ArrayList<UserTO>();
        }
        return members;
    }
    
    /**
     * @param userList the userGroupTOList to set
     */
    public final void setMembers(List<UserTO> userList) {
        this.members = userList;
    }
    
    /**
     * @return the nodePropertyTOList
     */
    public final List<NodePropertyTO> getNodePropertyTOList() {
        if (nodePropertyTOList == null)
            this.nodePropertyTOList = new ArrayList<NodePropertyTO>();
        return nodePropertyTOList;
    }

    /**
     * @param nodePropertyTOList the nodePropertyTOList to set
     */
    public final void setNodePropertyTOList(List<NodePropertyTO> nodePropertyTOList) {
        this.nodePropertyTOList = nodePropertyTOList;
    }
    
    /**
     * @param creationDate the creationDate to set
     */
    public final void setCreationDate(final Date creationDate) {
        super.setAttribute(CREATION_DATE, creationDate);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((GROUP_ID == null) ? 0 : GROUP_ID.hashCode());
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
        GroupTO other = (GroupTO) obj;
        if (GROUP_ID == null) {
            if (other.GROUP_ID != null) {
                return false;
            }
        } else if (!GROUP_ID.equals(other.GROUP_ID)) {
            return false;
        }
        return true;
    }

}
