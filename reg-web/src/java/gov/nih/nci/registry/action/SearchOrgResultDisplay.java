package gov.nih.nci.registry.action;

import java.io.Serializable;
import java.util.Map;

/**
 * Display helper class.
 * 
 * @author Harsha
 *
 */
public class SearchOrgResultDisplay implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String id;
    private Map<Long, String> families;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the zip
     */
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
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the families
     */
    public Map<Long, String> getFamilies() {
        return families;
    }
    /**
     * @param families the families to set
     */
    public void setFamilies(Map<Long, String> families) {
        this.families = families;
    }
}
