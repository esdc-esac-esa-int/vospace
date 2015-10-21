package esavo.vospace.common.model.transferobjects;

import java.util.ArrayList;
import java.util.List;

import esavo.vospace.common.entitymodel.EUserNodeProperty;
import esavo.vospace.common.entitymodel.VospaceModel;

public class UserNodePropertyTO extends NodePropertyTO {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 3448045233134488311L;
    
    /**
     * Associated entity of this transfer object.
     */
    @SuppressWarnings("unused")
    private EUserNodeProperty userNodeProperty = VospaceModel.getInstance().USER_NODE_PROPERTY;

    /** Attribute */
    protected List<UserTO> userTOList;
    
    /**
     * Constructor
     */
    public UserNodePropertyTO () {}
    
    /**
     * @return the userTOList
     */
    public final List<UserTO> getUserList() {
        if (this.userTOList == null) {
            this.userTOList = new ArrayList<UserTO>();
        }
        return userTOList;
    }

    /**
     * @param userTOList the userTOList to set
     */
    public final void setUserList(List<UserTO> userTOList) {
        this.userTOList = userTOList;
    }
}
