package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String prefix;
    private String suffix;
    /**
     * const
     */
    public NameDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param firstName firstName
     * @param middleName middleName
     * @param lastName lastName
     * @param prefix prefix
     * @param suffix suffix
     */
    public NameDTO(String firstName, String middleName, String lastName, String prefix, String suffix) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.prefix = prefix;
        this.suffix = suffix;
    }
    /**
     * 
     * @return firstName
     * 
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * 
     * @param firstName the firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * 
     * @param lastName lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * 
     * @return middleName
     */
    public String getMiddleName() {
        return middleName;
    }
    /**
     * 
     * @param middleName middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * 
     * @return prefix
     */
    public String getPrefix() {
        return prefix;
    }
    /**
     * 
     * @param prefix the prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    /**
     * 
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }
    /**
     * 
     * @param suffix suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
