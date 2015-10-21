package esavo.vospace.common.model.transferobjects;

import java.util.ArrayList;
import java.util.List;

import esavo.vospace.common.entitymodel.EGroupNodeProperty;
import esavo.vospace.common.entitymodel.VospaceModel;

public class GroupNodePropertyTO extends NodePropertyTO {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 6109130210022912051L;
    
    /**
     * Associated entity of this transfer object.
     */
    @SuppressWarnings("unused")
    private EGroupNodeProperty groupNodeProperty = VospaceModel.getInstance().GROUP_NODE_PROPERTY;
    
    /** Attribute */
    protected List<GroupTO> groupTOList;
    
    /**
     * Constructor
     */
    public GroupNodePropertyTO () {}
    
    /**
     * @return the groupTOList
     */
    public final List<GroupTO> getGroupList() {
        if (this.groupTOList == null) {
            this.groupTOList = new ArrayList<GroupTO>();
        }
        return groupTOList;
    }

    /**
     * @param groupTOList the groupTOList to set
     */
    public final void setGroupList(List<GroupTO> groupTOList) {
        this.groupTOList = groupTOList;
    }

}
