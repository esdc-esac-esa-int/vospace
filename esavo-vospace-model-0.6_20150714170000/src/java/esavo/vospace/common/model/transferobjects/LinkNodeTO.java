package esavo.vospace.common.model.transferobjects;

import esavo.vospace.common.entitymodel.ELinkNode;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class LinkNodeTO extends NodeTO {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -7377852759652564874L;
    
    /**
     * Associated entity of this transfer object.
     */
    private ELinkNode linkNode = VospaceModel.getInstance().LINK_NODE;
    
    /** Attribute */
    private VOSpaceURI target;
    
    /**
     * Default constructor.
     */
    public LinkNodeTO() {
        
    }

    /**
     * @return the target
     */
    public final VOSpaceURI getTarget() {
        return this.target;
    }

    /**
     * @param target the target to set
     */
    public final void setTarget(final VOSpaceURI target) {
        this.target = target;
    }
    
    @Override
    public VOSpaceNodeType getType() {
      return VOSpaceNodeType.LINK_NODE;
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
        LinkNodeTO other = (LinkNodeTO) obj;
        if (NODE_ID == null) {
            if (other.NODE_ID != null) {
                return false;
            }
        } else if (!NODE_ID.equals(other.NODE_ID)) {
            return false;
        }
        return true;
    }

}
