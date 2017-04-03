package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**
 * Created by chandrasekaranp on 3/24/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsiblePartyDTO {

    private String type;
    private OrganizationDTO affiliation;
    private PersonDTO investigator;
    private String title;
    
    /**
     * cons
     */
    public ResponsiblePartyDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param type type
     * @param affiliation affiliation
     * @param investigator investigator
     * @param title title
     */
    public ResponsiblePartyDTO(String type, OrganizationDTO affiliation,
            PersonDTO investigator, String title) {
        super();
        this.type = type;
        this.affiliation = affiliation;
        this.investigator = investigator;
        this.title = title;
    }
    /**
     * 
     * @return type
     */
    public String getType() {
        return type;
    }
    /**
     * 
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * 
     * @return affiliation
     */
    public OrganizationDTO getAffiliation() {
        return affiliation;
    }
    
    /**
     * 
     * @param affiliation the affiliation
     */
    public void setAffiliation(OrganizationDTO affiliation) {
        this.affiliation = affiliation;
    }
    /**
     * 
     * @return investigator
     */
    public PersonDTO getInvestigator() {
        return investigator;
    }
    /**
     * 
     * @param investigator the investigator
     */
    public void setInvestigator(PersonDTO investigator) {
        this.investigator = investigator;
    }
    /**
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /**
     * 
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
