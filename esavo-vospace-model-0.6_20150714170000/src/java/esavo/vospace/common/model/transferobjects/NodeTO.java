package esavo.vospace.common.model.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.ENode;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * Node Transfer Object.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class NodeTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 3679861755570414803L;
    
    /**
     * Associated entity of this transfer object.
     */
    private ENode node = VospaceModel.getInstance().NODE; 
    
    /** Attribute. */
    protected final Attribute NODE_ID = node.NODE_ID;
    
    /** Attribute. */
    protected VOSpaceURI URI;
    
    /** Attribute. */
    /*protected final Attribute CONTAINER_NODE_ID = VospaceModel.getInstance().CONTAINER_NODE.NODE_ID;*/
    
    /** Attribute. */
    protected UserTO userTO;
    
    /** Attribute. */
    protected List<VoCapabilityTO> voCapabilityTOList;
    
    /** Attribute. */
    protected List<NodePropertyTO> nodePropertyTOList;
    
    /** Attribute. */
    protected ContainerNodeTO parent;
    
    /**
     * Default constructor.
     */
    public NodeTO () {
        
    }

    public final Integer getId() {
        return (Integer) super.getAttribute(NODE_ID);
    }

    public final VOSpaceURI getURI() {
        return this.URI;
    }

    public final void setId(final Integer nodeId) {
        super.setAttribute(NODE_ID, nodeId);;
    }

    public final void setUri(final VOSpaceURI uri) {
        this.URI = uri;
    }
    
    public UserTO getUserTO() {
		return userTO;
	}

	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}
    
    /**
     * @return the containerNodeId
     *//*
    public final Integer getContainerNodeId() {
        return (Integer) super.getAttribute(CONTAINER_NODE_ID);
    }

    *//**
     * @param containerNodeId the containerNodeId to set
     *//*
    public final void setContainerNodeId(final Integer containerNodeId) {
        super.setAttribute(CONTAINER_NODE_ID, containerNodeId);
    }*/

    /**
     * @return the voCapabilityTOList
     */
    public final List<VoCapabilityTO> getCapabilities() {
        if (this.voCapabilityTOList == null)
            this.voCapabilityTOList = new ArrayList<VoCapabilityTO>();
        return voCapabilityTOList;
    }

    /**
     * @return the nodePropertyTOList
     */
    public final List<NodePropertyTO> getNodeProperties() {
        if (this.nodePropertyTOList == null)
            this.nodePropertyTOList = new ArrayList<NodePropertyTO>();
        return nodePropertyTOList;
    }

    /**
     * @param voCapabilityTOList the voCapabilityTOList to set
     */
    public final void setCapabilities(List<VoCapabilityTO> voCapabilityTOList) {
        this.voCapabilityTOList = voCapabilityTOList;
    }

    /**
     * @param nodePropertyTOList the nodePropertyTOList to set
     */
    public final void setNodeProperties(List<NodePropertyTO> nodePropertyTOList) {
        this.nodePropertyTOList = nodePropertyTOList;
    }
    
    public VOSpaceNodeType getType() {
      return VOSpaceNodeType.UNKNOWN;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((NODE_ID == null) ? 0 : NODE_ID.hashCode());
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
        NodeTO other = (NodeTO) obj;
        if (NODE_ID == null) {
            if (other.NODE_ID != null) {
                return false;
            }
        } else if (!((Integer) super.getAttribute(NODE_ID))
                .equals((Integer)other.getAttribute(NODE_ID))) {
            return false;
        }
        return true;
    }

    /**
     * @return the parent
     */
    public final ContainerNodeTO getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public final void setParent(ContainerNodeTO parent) {
        this.parent = parent;
    }
}
