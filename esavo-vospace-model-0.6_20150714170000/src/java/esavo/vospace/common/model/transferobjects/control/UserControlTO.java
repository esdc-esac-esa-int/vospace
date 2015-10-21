package esavo.vospace.common.model.transferobjects.control;

import java.io.Serializable;
import java.util.Date;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.modules.common.resultbean.ResultBeanItem;
import esavo.vospace.common.entitymodel.VospaceModel;
import esavo.vospace.common.entitymodel.control.EUserControl;

/**
 * UserControl Transfer Object.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class UserControlTO extends ResultBeanItem implements Serializable {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -6634764810399583824L;

    /** Associated entity of this transfer object.     */
    private EUserControl userControl = VospaceModel.getInstance().USER_CONTROL;
        
    /** Attribute. */
    protected Attribute LOG_ID = userControl.LOG_ID;
    
    /** Attribute. */
    protected Attribute NAME = userControl.NAME;
    
    /** Attribute. */
    protected Attribute REGISTER_DATE = userControl.REGISTER_DATE;
    
    /** Attribute. */
    protected Attribute SURNAME = userControl.SURNAME;
    
    /** Attribute. */
    protected Attribute INSTITUTE = userControl.INSTITUTE;
    
    /** Attribute. */
    protected Attribute ADDRESS_1 = userControl.ADDRESS_1;
    
    /** Attribute. */
    protected Attribute ADDRESS_2 = userControl.ADDRESS_2;
    
    /** Attribute. */
    protected Attribute TOWN = userControl.TOWN;
    
    /** Attribute. */
    protected Attribute STATE = userControl.STATE;
    
    /** Attribute. */
    protected Attribute COUNTRY = userControl.COUNTRY;
    
    /** Attribute. */
    protected Attribute MAIL = userControl.MAIL;
    
    /** Attribute. */
    protected Attribute FAX = userControl.FAX;
    
    /** Attribute. */
    protected Attribute PHONE = userControl.PHONE;
    
    /** Attribute. */
    protected Attribute ZIP_CODE = userControl.ZIP_CODE;
    
    /**
     * Default constructor.
     */
    public UserControlTO () {
        
    }
    
    /**
     * @return the lOG_ID
     */
    public final Integer getId() {
        return (Integer) super.getAttribute(LOG_ID);
    }
    
    /**
     * @param lOG_ID the lOG_ID to set
     */
    public final void setId(Integer logId) {
        super.setAttribute(LOG_ID, logId);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((LOG_ID == null) ? 0 : LOG_ID.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserControlTO other = (UserControlTO) obj;
        if (LOG_ID == null) {
            if (other.LOG_ID != null) {
                return false;
            }
        } else if (!LOG_ID.equals(other.LOG_ID)) {
            return false;
        }
        return true;
    }

    /**
     * @return the nAME
     */
    public final String getName() {
        return (String) super.getAttribute(NAME);
    }

    /**
     * @return the rEGISTER_DATE
     */
    public final Date getRegisterDate() {
        return (Date) super.getAttribute(REGISTER_DATE);
    }

    /**
     * @return the sURNAME
     */
    public final String getSurname() {
        return (String) super.getAttribute(SURNAME);
    }

    /**
     * @return the iNSTITUTE
     */
    public final String getInstitute() {
        return (String) super.getAttribute(INSTITUTE);
    }

    /**
     * @return the aDDRESS_1
     */
    public final String getAddress1() {
        return (String) super.getAttribute(ADDRESS_1);
    }

    /**
     * @return the aDDRESS_2
     */
    public final String getAddress2() {
        return (String) super.getAttribute(ADDRESS_2);
    }

    /**
     * @return the tOWN
     */
    public final String getTown() {
        return (String) super.getAttribute(TOWN);
    }

    /**
     * @return the sTATE
     */
    public final String getState() {
        return (String) super.getAttribute(STATE);
    }

    /**
     * @return the cOUNTRY
     */
    public final String getCountry() {
        return (String) super.getAttribute(COUNTRY);
    }

    /**
     * @return the mAIL
     */
    public final String getMail() {
        return (String) super.getAttribute(MAIL);
    }

    /**
     * @return the fAX
     */
    public final String getFax() {
        return (String) super.getAttribute(FAX);
    }

    /**
     * @return the pHONE
     */
    public final Integer getPhone() {
        return (Integer) super.getAttribute(PHONE);
    }

    /**
     * @param nAME the nAME to set
     */
    public final void setName(String name) {
        super.setAttribute(NAME, name);
    }

    /**
     * @param rEGISTER_DATE the rEGISTER_DATE to set
     */
    public final void setRegisterDate(Date registerDate) {
        super.setAttribute(REGISTER_DATE, registerDate);
    }

    /**
     * @param sURNAME the sURNAME to set
     */
    public final void setSurname(String surname) {
        super.setAttribute(SURNAME, surname);
    }

    /**
     * @param iNSTITUTE the iNSTITUTE to set
     */
    public final void setInstitute(String institute) {
        super.setAttribute(INSTITUTE, institute);
    }

    /**
     * @param aDDRESS_1 the aDDRESS_1 to set
     */
    public final void setAddress1(String address1) {
        super.setAttribute(ADDRESS_1, address1);
    }

    /**
     * @param aDDRESS_2 the aDDRESS_2 to set
     */
    public final void setAddress2(String address2) {
        super.setAttribute(ADDRESS_2, address2);
    }

    /**
     * @param tOWN the tOWN to set
     */
    public final void setTown(String town) {
        super.setAttribute(TOWN, town);
    }

    /**
     * @param sTATE the sTATE to set
     */
    public final void setState(String state) {
        super.setAttribute(STATE, state);
    }

    /**
     * @param cOUNTRY the cOUNTRY to set
     */
    public final void setCountry(String country) {
        super.setAttribute(COUNTRY, country);
    }

    /**
     * @param mAIL the mAIL to set
     */
    public final void setMail(String mail) {
        super.setAttribute(MAIL, mail);
    }

    /**
     * @param fAX the fAX to set
     */
    public final void setFax(String fax) {
        super.setAttribute(FAX, fax);
    }

    /**
     * @param pHONE the pHONE to set
     */
    public final void setPhone(Integer phone) {
        super.setAttribute(PHONE, phone);
    }

    /**
     * @return the zIP_CODE
     */
    public final Integer getZipCode() {
        return (Integer) super.getAttribute(ZIP_CODE);
    }

    /**
     * @param zIP_CODE the zIP_CODE to set
     */
    public final void setZipCode(Integer zipCode) {
        super.setAttribute(ZIP_CODE, zipCode);
    }
}
