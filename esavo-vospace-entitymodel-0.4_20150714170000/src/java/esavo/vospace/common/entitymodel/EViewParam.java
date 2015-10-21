package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EViewParam extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 7668985233724790727L;
    
    /** Singleton */
    private static final EViewParam INSTANCE = new EViewParam("VIEW_PARAM");
    
    /** Attribute */
    public final Attribute VIEW_PARAM_ID = new Attribute("VIEW_PARAM_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute NAME = new Attribute("NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute VALUE = new Attribute("VALUE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EViewParam(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EViewParam getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return NAME;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return VIEW_PARAM_ID;
    }

}
