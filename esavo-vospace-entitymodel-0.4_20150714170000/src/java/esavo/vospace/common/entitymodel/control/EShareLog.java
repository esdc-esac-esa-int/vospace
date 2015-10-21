package esavo.vospace.common.entitymodel.control;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

public class EShareLog extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 5151691118427805104L;

    /** SINGLETON */
    private static final EShareLog INSTANCE = new EShareLog("SHARE_LOG");
    
    /** Attribute */
    public final Attribute LOG_ID = new Attribute("LOG_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    /*public Attribute OWNER_NAME = new Attribute("OWNER_NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);*/
    
    /** Attribute */
    public Attribute OPERATION_TIMESTAMP = new Attribute("OPERATION_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute END_TIMESTAMP = new Attribute("END_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute NODE = new Attribute("NODE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute ROLE_TYPE = new Attribute("ROLE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute SHARE_GROUP = new Attribute("SHARE_GROUP", this, LiteralType.BOOLEAN,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute SHARE_USER = new Attribute("SHARE_USER", this, LiteralType.BOOLEAN,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EShareLog(String inputEntityName) {
        super(inputEntityName);
    }

    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EShareLog getInstance() {
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
