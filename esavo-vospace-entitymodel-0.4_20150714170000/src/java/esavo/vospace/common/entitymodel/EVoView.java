package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EVoView extends AbstractEntity {

    /**
     * Auto-generated Serial version uid. 
     */
    private static final long serialVersionUID = -1786119132735876453L;
    
    /** Singleton */
    private static final EVoView INSTANCE = new EVoView("VO_VIEW");
    
    /** Attribute */
    public final Attribute VO_VIEW_ID = new Attribute("VO_VIEW_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
        
    /** Attribute */
    public Attribute ORIGINAL = new Attribute("ORIGINAL", this, LiteralType.BOOLEAN,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute VIEW_TYPE = new Attribute("VIEW_TYPE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);

    protected EVoView(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EVoView getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return VO_VIEW_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return VO_VIEW_ID;
    }

}
