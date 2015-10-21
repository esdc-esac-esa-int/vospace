package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EContainerNode extends ENode {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -4438025067343145101L;
    
    /** Singleton. */
    private static final EContainerNode INSTANCE = new EContainerNode("CONTAINER_NODE");
    
    protected EContainerNode(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EContainerNode getInstance() {
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
