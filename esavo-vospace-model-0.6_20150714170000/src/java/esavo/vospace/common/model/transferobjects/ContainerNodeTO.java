package esavo.vospace.common.model.transferobjects;

import java.util.ArrayList;
import java.util.List;

import esavo.vospace.common.entitymodel.EContainerNode;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ContainerNodeTO extends DataNodeTO {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 4937859675494606210L;
    
    /**
     * Associated entity of this transfer object.
     */
    @SuppressWarnings("unused")
    private EContainerNode containerNode = VospaceModel.getInstance().CONTAINER_NODE;
        
    /** Attribute */
    protected List<NodeTO> nodeTOList;
    
    /**
     * Default constructor.
     */
    public ContainerNodeTO() {
        
    }

    /**
     * @return the nodeTOList
     */
    public final List<NodeTO> getNodeList() {
        if (this.nodeTOList == null) {
            this.nodeTOList = new ArrayList<NodeTO>();
        }
        return nodeTOList;
    }

    /**
     * @param nodeTOList the nodeTOList to set
     */
    public final void setNodeList(List<NodeTO> nodeTOList) {
        this.nodeTOList = nodeTOList;
    }
    
    @Override
    public VOSpaceNodeType getType() {
      return VOSpaceNodeType.CONTAINER_NODE;
    }

}
