/**
 * 
 */
package gov.nih.nci.registry.dto;

import java.io.Serializable;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Pattern;

/**
 * @author Vrushali
 *
 */
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = -8248219647277396357L;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String email;
    private String phone;
    private String tty;
    private String fax;
    private String url;
    /**
     * @return the city
     */
    @NotEmpty(message = "(fieldName) City is required.\n")
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
     * @return the country
     */
    @NotEmpty(message = "(fieldName) Country is required.\n")
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
     * @return the email
     */
    @NotEmpty(message = "(fieldName) Email Address is required.\n")
    @Email(message = "(fieldName) Email Address is invalid. \n")
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
     * @return the streetAddress
     */
    @NotEmpty(message = "(fieldName) Street Address is required.\n")
    public String getStreetAddress() {
        return streetAddress;
    }
    /**
     * @param streetAddress the streetAddress to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    /**
     * @return the zip
     */
    @NotEmpty(message = "(fieldName) Zip is required.\n")
    public String getZip() {
        return zip;
    }
    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }
    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
     * @return the tty
     */
    public String getTty() {
        return tty;
    }
    /**
     * @param tty the tty to set
     */
    public void setTty(String tty) {
        this.tty = tty;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the phone
     */
    @Pattern(regex = "^([\\w\\s\\-\\.\\+\\(\\)])*$" , message = "(fieldName) Phone is invalid.\n")
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
