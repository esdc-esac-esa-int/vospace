package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EStructuredDataNode extends EDataNode {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -7606291116582358437L;

    /** Singleton */
    private static final EStructuredDataNode INSTANCE = new EStructuredDataNode("STRUCTURED_DATA_NODE");
    
    protected EStructuredDataNode(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EStructuredDataNode getInstance() {
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
