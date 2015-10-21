package esavo.vospace.common.entitymodel.control;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * TransferLog Entity.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class ETransferLog extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 3984728083808124807L;

    /** SINGLETON */
    private static final ETransferLog INSTANCE = new ETransferLog("TRANSFER_LOG");
    
    /** Attribute */
    public final Attribute LOG_ID = new Attribute("LOG_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    /*public Attribute USER_NAME = new Attribute("USER_NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);*/
    
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
    public Attribute PROTOCOL_PARAM = new Attribute("PROTOCOL_PARAM", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute VIEW_PARAM = new Attribute("VIEW_PARAM", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute TOTAL_SIZE = new Attribute("TOTAL_SIZE", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected ETransferLog(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static ETransferLog getInstance() {
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
