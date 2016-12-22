package gov.nih.nci.pa.action;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Hugh Reinhart
 * @since Mar 27, 2012
 */
public abstract class AbstractPopUpPoAction extends ActionSupport {
    private static final long serialVersionUID = 1779207439808550008L;
    
    private String poId;
    private String ctepId;
    
    private List<Country> countryList = new ArrayList<Country>();
    private String cityName;
    private String countryName;
    private String email;
    private String stateName;
    private String telephone;
    private String zipCode;

    /**
     * Populate the countries drop down.
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    protected void getCountriesList() throws PAException {
        countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = PaRegistry.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * Convert alpha 3 country code to name.
     * @param code alpha 3 code
     * @return country name
     */
    protected String getCountryNameUsingCode(String code) {
        for (Country c : countryList) {
            if (c.getAlpha3().toString().equals(code)) {
                return c.getName();
            }
        }
        return null;
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }
    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the poId
     */
    public String getPoId() {
        return poId;
    }

    /**
     * @param poId the poId to set
     */
    public void setPoId(String poId) {
        this.poId = poId;
    }

    /**
     * @return the ctepId
     */
    public String getCtepId() {
        return ctepId;
    }

    /**
     * @param ctepId the ctepId to set
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }
}
