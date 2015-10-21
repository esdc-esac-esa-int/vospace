package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EProtocolParam extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 7668985233724790727L;
    
    /** SINGLETON */
    private static final EProtocolParam INSTANCE = new EProtocolParam("PROTOCOL_PARAM");
    
    /** Attribute */
    public final Attribute PROTOCOL_PARAM_ID = new Attribute("PROTOCOL_PARAM_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
        
    /** Attribute */
    public Attribute VALUE = new Attribute("VALUE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EProtocolParam(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EProtocolParam getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return PROTOCOL_PARAM_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return PROTOCOL_PARAM_ID;
    }

}
