package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationDTO {

    private String name;
    private String telecomAddress;
    private String postalAddress;
    /**
     * consr
     */
    public OrganizationDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param name name
     * @param telecomAddress telecomAddress
     * @param postalAddress postalAddress
     */
    public OrganizationDTO(String name, String telecomAddress,
            String postalAddress) {
        super();
        this.name = name;
        this.telecomAddress = telecomAddress;
        this.postalAddress = postalAddress;
    }
    /**
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @param name the name
     */
    public void setName(String name) {
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
     * @param orgName the orgName
     */
    public void getUnknownOrganizationDTO(String orgName) {
        setName(orgName);
        setTelecomAddress("replacewithrealemail@nih.gov");
        setPostalAddress("Unknown");
    }
}