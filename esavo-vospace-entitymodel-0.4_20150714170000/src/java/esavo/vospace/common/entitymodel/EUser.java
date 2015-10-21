package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EUser extends AbstractEntity {

    /**
     *  Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -7831434131723494474L;
    
    /** Singleton */
    private static final EUser INSTANCE = new EUser("USERS");
    
    /** Attribute */
    public final Attribute USER_ID = new Attribute("USER_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute NAME = new Attribute("NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute FULL_NAME = new Attribute("FULL_NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute MAIL = new Attribute("MAIL", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute CREATION_DATE = new Attribute("CREATION_DATE", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute QUOTA = new Attribute("QUOTA", this, LiteralType.LONG,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute QLIMIT = new Attribute("QLIMIT", this, LiteralType.LONG,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EUser(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EUser getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return NAME;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return NAME;
    }

}
