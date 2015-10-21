package esavo.vospace.common.entitymodel.control;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * AccessLog Entity.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class EAccessLog extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -1260459418984187161L;
    
    /** SINGLETON */
    private static final EAccessLog INSTANCE = new EAccessLog("ACCESS_LOG");
    
    /** Attribute */
    public final Attribute LOG_ID = new Attribute("LOG_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute LOGIN_TIMESTAMP = new Attribute("LOGIN_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute LOGOUT_TIMESTAMP = new Attribute("LOGOUT_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute QUOTA = new Attribute("QUOTA", this, LiteralType.LONG,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute INTERNET_SITE = new Attribute("INTERNET_SITE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute ENVIRONMENT = new Attribute("ENVIRONMENT", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EAccessLog(String inputEntityName) {
        super(inputEntityName);
    }

    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EAccessLog getInstance() {
        return INSTANCE;
    }
    
    @Override
    public Attribute getOrderByAttribute() {
        return LOG_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return LOG_ID;
    }

}
