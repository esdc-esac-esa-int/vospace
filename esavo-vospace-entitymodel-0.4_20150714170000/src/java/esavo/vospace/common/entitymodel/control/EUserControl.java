package esavo.vospace.common.entitymodel.control;

import esac.archive.absi.interfaces.common.entitymodel.AbstractEntity;
import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.entitymodel.ConstantsUnits;
import esac.archive.absi.interfaces.common.entitymodel.LiteralType;

/**
 * UserControl Entity.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class EUserControl extends AbstractEntity {

    /**
     * Auto-generated Serial version uid.
     */
    private static final long serialVersionUID = -2265092333166658954L;

    /** SINGLETON */
    private static final EUserControl INSTANCE = new EUserControl("USER_CONTROL");
    
    /** Attribute */
    public final Attribute LOG_ID = new Attribute("LOG_ID", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute NAME = new Attribute("NAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute SURNAME = new Attribute("SURNAME", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute INSTITUTE = new Attribute("INSTITUTE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute ADDRESS_1 = new Attribute("ADDRESS_1", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute ADDRESS_2 = new Attribute("ADDRESS_2", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute TOWN = new Attribute("TOWN", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute STATE = new Attribute("STATE", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute COUNTRY = new Attribute("COUNTRY", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute MAIL = new Attribute("MAIL", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute FAX = new Attribute("FAX", this, LiteralType.STRING,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute PHONE = new Attribute("PHONE", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute ZIP_CODE = new Attribute("ZIP_CODE", this, LiteralType.INTEGER,
            ConstantsUnits.DIMENSIONLESS, true);
    
    /** Attribute */
    public Attribute REGISTER_DATE = new Attribute("REGISTER_DATE", this, LiteralType.DATE,
            ConstantsUnits.DIMENSIONLESS, true);
    
    protected EUserControl(String inputEntityName) {
        super(inputEntityName);
    }
    
    /**
     * Making the class singleton.
     * @return Static instance
     */
    public static EUserControl getInstance() {
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
