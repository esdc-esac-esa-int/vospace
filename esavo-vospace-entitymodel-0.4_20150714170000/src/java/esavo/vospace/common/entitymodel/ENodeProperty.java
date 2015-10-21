package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class ENodeProperty extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -7651465276972270412L;
    
    /** Singleton */
    private static final ENodeProperty INSTANCE = new ENodeProperty("NODE_PROPERTY");
    
    /** Attribute */
    public final Attribute NODE_PROPERTY_ID = new Attribute("NODE_PROPERTY_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute VALUE = new Attribute("ARCHIVE_CLASS", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);

    protected ENodeProperty(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static ENodeProperty getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return NODE_PROPERTY_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return NODE_PROPERTY_ID;
    }

}
