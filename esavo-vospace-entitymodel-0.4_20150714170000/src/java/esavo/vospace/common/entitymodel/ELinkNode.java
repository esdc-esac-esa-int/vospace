package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class ELinkNode extends ENode {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 3017915156136934213L;
    
    /** Singleton. */
    private static final ELinkNode INSTANCE =  new ELinkNode("LINK_NODE");
    
    protected ELinkNode(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static ELinkNode getInstance() {
        return INSTANCE;
    }
    
    /**
     * Returns primary attribute.
     * @return Primary attribute.
     * */
    @Override
    public Attribute getPrimaryAttribute() {
        return NODE_ID;
    }

    /**
     * Returns default order by attribute.
     * @return OrderBy attribute
     */
    @Override
    public Attribute getOrderByAttribute() {
        return NODE_ID;
    }
    
}
