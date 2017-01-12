package gov.nih.nci.accrual.accweb.dto.util;

import gov.nih.nci.pa.domain.RegistryUser;

/**
 * webDTO to hold RegistryUser fields.
 * @author Kalpana Guthikonda
 * @since 3/28/2014
 *
 */
@SuppressWarnings({ "PMD.TooManyFields" })
public class RegistryUserWebDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    private String affiliateOrg;
    private Long csmUserId;
    private String prsOrgName;
    private String emailAddress;
    private Long affiliatedOrganizationId;
    private Boolean enableEmails;

    /**
    *
    * @param registryUser registryUser
    */
    public RegistryUserWebDTO(RegistryUser registryUser) {
        id = registryUser.getId();
        if (registryUser.getCsmUser() != null) {
            csmUserId = registryUser.getCsmUser().getUserId();
        }
       firstName = registryUser.getFirstName();
       lastName =  registryUser.getLastName();
       middleName =  registryUser.getMiddleName();
       addressLine = registryUser.getAddressLine();
       city = registryUser.getCity();
       state = registryUser.getState();
       postalCode = registryUser.getPostalCode();
       country = registryUser.getCountry();
       phone = registryUser.getPhone();
       affiliateOrg = registryUser.getAffiliateOrg();
       prsOrgName = registryUser.getPrsOrgName();
       enableEmails = registryUser.getEnableEmails();
       emailAddress = registryUser.getEmailAddress();
       affiliatedOrganizationId = registryUser.getAffiliatedOrganizationId();
   }

    /** .
     *  Default Constructor
     */
    public RegistryUserWebDTO() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }
    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * @return the addressLine
     */
    public String getAddressLine() {
        return addressLine;
    }
    /**
     * @param addressLine the addressLine to set
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }
    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the affiliateOrg
     */
    public String getAffiliateOrg() {
        return affiliateOrg;
    }
    /**
     * @param affiliateOrg the affiliateOrg to set
     */
    public void setAffiliateOrg(String affiliateOrg) {
        this.affiliateOrg = affiliateOrg;
    }
    /**
     * @return the csmUserId
     */
    public Long getCsmUserId() {
        return csmUserId;
    }
    /**
     * @param csmUserId the csmUserId to set
     */
    public void setCsmUserId(Long csmUserId) {
        this.csmUserId = csmUserId;
    }

    /**
     * @return the prsOrgName
     */
    public String getPrsOrgName() {
        return prsOrgName;
    }

    /**
     * @param prsOrgName the prsOrgName to set
     */
    public void setPrsOrgName(String prsOrgName) {
        this.prsOrgName = prsOrgName;
    }

    /**
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the affiliatedOrganizationId
     */
     public Long getAffiliatedOrganizationId() {
       return affiliatedOrganizationId;
     }

    /**
     * @param affiliatedOrganizationId the affiliatedOrganizationId to set
     */
     public void setAffiliatedOrganizationId(Long affiliatedOrganizationId) {
        this.affiliatedOrganizationId = affiliatedOrganizationId;
     }

    /**
     * @return the enableEmails
     */
    public Boolean getEnableEmails() {
        return enableEmails;
    }

    /**
     * @param enableEmails the enableEmails to set
     */
    public void setEnableEmails(Boolean enableEmails) {
        this.enableEmails = enableEmails;
    }

}
