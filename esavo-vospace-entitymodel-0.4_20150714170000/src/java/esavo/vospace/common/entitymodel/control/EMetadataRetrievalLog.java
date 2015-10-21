package esavo.vospace.common.entitymodel.control;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * MetadataRetrievalLog Entity.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class EMetadataRetrievalLog extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 7347617189043817288L;

    /** SINGLETON */
    private static final EMetadataRetrievalLog INSTANCE = new EMetadataRetrievalLog("METADATA_RETRIEVAL_LOG");
    
    /** Attribute */
    public final Attribute LOG_ID = new Attribute("LOG_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    /*public Attribute OWNER_NAME = new Attribute("OWNER_NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);*/
    
    /** Attribute */
    public Attribute TYPE = new Attribute("TYPE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute START_TIMESTAMP = new Attribute("START_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute STOP_TIMESTAMP = new Attribute("STOP_TIMESTAMP", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EMetadataRetrievalLog(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EMetadataRetrievalLog getInstance() {
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
