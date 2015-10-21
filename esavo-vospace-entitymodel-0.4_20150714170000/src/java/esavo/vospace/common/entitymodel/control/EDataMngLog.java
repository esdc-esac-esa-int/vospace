package esavo.vospace.common.entitymodel.control;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * DataMngLog Entity. 
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class EDataMngLog extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 1267236571024553850L;

    /** SINGLETON */
    private static final EDataMngLog INSTANCE = new EDataMngLog("DATA_MNG_LOG");
    
    /** Attribute */
    public final Attribute LOG_ID = new Attribute("LOG_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute START_TIMESTAMP = new Attribute("START_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute STOP_TIMESTAMP = new Attribute("STOP_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute OPER_TYPE = new Attribute("OPER_TYPE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute NODE_SOURCE = new Attribute("NODE_SOURCE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute NODE_TARGET = new Attribute("NODE_TARGET", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute SIZE = new Attribute("SIZE", this, LiteralType.LONG,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EDataMngLog(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EDataMngLog getInstance() {
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
