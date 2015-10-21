package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;

public class EGroupNodeProperty extends ENodeProperty {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -2346186011680772109L;

    /** Singleton. */
    private static final EGroupNodeProperty INSTANCE =  new EGroupNodeProperty("GROUP_NODE_PROPERTY");
    
    protected EGroupNodeProperty(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EGroupNodeProperty getInstance() {
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
