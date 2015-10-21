package esavo.vospace.common.entitymodel;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * Group Entity.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public final class EGroup extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = 9113888442294279380L;
    
    /** SINGLETON */
    private static final EGroup INSTANCE = new EGroup("GROUP");
    
    /** Attribute */
    public final Attribute GROUP_ID = new Attribute("GROUP_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute GROUP_NAME = new Attribute("NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute OWNER_NAME = new Attribute("OWNER_NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute DESCRIPTION = new Attribute("DESCRIPTION", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute CREATION_DATE = new Attribute("CREATION_DATE", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EGroup(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EGroup getInstance() {
        return INSTANCE;
    }

    @Override
    public Attribute getOrderByAttribute() {
        return GROUP_ID;
    }

    @Override
    public Attribute getPrimaryAttribute() {
        return GROUP_ID;
    }

}
