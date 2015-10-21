package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EVoProtocol extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 672559900087340874L;
    
    /** Singleton */
    private static final EVoProtocol INSTANCE = new EVoProtocol("VO_PROTOCOL");
    
    /** Attribute */
    public final Attribute VO_PROTOCOL_ID = new Attribute("VO_PROTOCOL_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
        
    /** Attribute */
    public Attribute VIEW_TYPE = new Attribute("VIEW_TYPE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);

    protected EVoProtocol(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EVoProtocol getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return VO_PROTOCOL_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return VO_PROTOCOL_ID;
    }

}
