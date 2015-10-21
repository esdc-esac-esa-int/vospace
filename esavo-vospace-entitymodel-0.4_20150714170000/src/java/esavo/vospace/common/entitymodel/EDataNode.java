package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class EDataNode extends ENode {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -4460602260052175176L;
    
    /** Singleton. */
    private static final EDataNode INSTANCE = new EDataNode("DATA_NODE"); 
    
    /** Attribute */
    public Attribute BUSY = new Attribute("BUSY", this, LiteralType.BOOLEAN,
            ConstantsUnits.DIMENSIONLESS, true);

    protected EDataNode(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EDataNode getInstance() {
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
        return BUSY;
    }

}
