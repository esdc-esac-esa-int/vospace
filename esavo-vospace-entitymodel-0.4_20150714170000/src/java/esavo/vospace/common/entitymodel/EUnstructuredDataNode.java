package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EUnstructuredDataNode extends EDataNode {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 6200314665140307312L;

    /** Singleton */
    private static final EUnstructuredDataNode INSTANCE = new EUnstructuredDataNode("STRUCTURED_DATA_NODE");
    
    protected EUnstructuredDataNode(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EUnstructuredDataNode getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return NODE_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return NODE_ID;
    }

}
