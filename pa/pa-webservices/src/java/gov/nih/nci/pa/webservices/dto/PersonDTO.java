package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by chandrasekaranp on 3/22/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDTO {

    private String fullName;
    private NameDTO name;
    private String telecomAddress;
    private String postalAddress;
    private String phone;
    /**
     * const
     */
    public PersonDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param fullName fullName
     * @param name name
     * @param telecomAddress telecomAddress
     * @param postalAddress postalAddress
     * @param phone phone
     */
    public PersonDTO(String fullName, NameDTO name, String telecomAddress,
            String postalAddress, String phone) {
        super();
        this.fullName = fullName;
        this.name = name;
        this.telecomAddress = telecomAddress;
        this.postalAddress = postalAddress;
        this.phone = phone;
    }
    /**
     * 
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * 
     * @param fullName the fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * 
     * @return name
     */
    public NameDTO getName() {
        return name;
    }
    /**
     * 
     * @param name the name
     */
    public void setName(NameDTO name) {
        this.name = name;
    }
    /**
     * 
     * @return telecomAddress
     */
    public String getTelecomAddress() {
        return telecomAddress;
    }
    /**
     * 
     * @param telecomAddress the telecomAddress
     */
    public void setTelecomAddress(String telecomAddress) {
        this.telecomAddress = telecomAddress;
    }
    /**
     * 
     * @return postalAddress
     */
    public String getPostalAddress() {
        return postalAddress;
    }
    /**
     * 
     * @param postalAddress the postalAddress
     */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    /**
     * 
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * 
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * set the  values
     */
    public void setUnknownPersonDTO() {
        setTelecomAddress("replacewithrealemail@nih.gov");
        setPostalAddress("Unknown");
    }

}
