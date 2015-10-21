package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

public class ENode extends AbstractEntity {

    /**
     *  Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -196879096953491722L;
    
    /** Singleton. */
    private static final ENode INSTANCE = new ENode("NODE");
    
    /** Attribute. */
    public final Attribute NODE_ID = new Attribute("NODE_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected ENode(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static ENode getInstance() {
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
