package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EVoProperty extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -2418758742873346582L;
    
    /** Singleton */
    private static final EVoProperty INSTANCE = new EVoProperty("VO_PROPERTY");
    
    /** Attribute */
    public final Attribute VO_PROPERTY_ID = new Attribute("VO_PROPERTY_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute URI = new Attribute("URI", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute DESCRIPTION = new Attribute("DESCRIPTION", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute READ_ONLY = new Attribute("READ_ONLY", this, LiteralType.BOOLEAN,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute VIEW_TYPE = new Attribute("VIEW_TYPE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);

    
    protected EVoProperty(String inputEntityName) {
        super(inputEntityName);
    }

    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EVoProperty getInstance() {
        return INSTANCE;
    }
    
    @Override
    public Attribute getOrderByAttribute() {
        return VO_PROPERTY_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return VO_PROPERTY_ID;
    }

}
