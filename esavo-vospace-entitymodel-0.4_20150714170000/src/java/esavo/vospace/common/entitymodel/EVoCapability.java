package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EVoCapability extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 3763453857533181699L;

    /** Singleton. */
    private static final EVoCapability INSTANCE = new EVoCapability("VO_CAPABILITY");    
    
    /** Attribute. */
    public final Attribute VO_CAPABILITY_ID = new Attribute("VO_CAPABILITY_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);    
    
    protected EVoCapability(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EVoCapability getInstance() {
        return INSTANCE;
    }
    
    @Override
    public Attribute getOrderByAttribute() {
        return VO_CAPABILITY_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return VO_CAPABILITY_ID;
    }

}
