package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;

public final class EUserNodeProperty extends ENodeProperty {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 2174700203752506977L;
    
    /** Singleton. */
    private static final EUserNodeProperty INSTANCE =  new EUserNodeProperty("USER_NODE_PROPERTY");
    
    protected EUserNodeProperty(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EUserNodeProperty getInstance() {
        return INSTANCE;
    }
    
    /**
     * Returns primary attribute.
     * @return Primary attribute.
     * */
    @Override
    public Attribute getPrimaryAttribute() {
        return NODE_PROPERTY_ID;
    }

    /**
     * Returns default order by attribute.
     * @return OrderBy attribute
     */
    @Override
    public Attribute getOrderByAttribute() {
        return NODE_PROPERTY_ID;
    }
}
