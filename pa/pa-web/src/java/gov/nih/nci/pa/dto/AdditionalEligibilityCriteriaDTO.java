package gov.nih.nci.pa.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalEligibilityCriteriaDTO {
    @JsonProperty("gender_based")
    private String gender;
    @JsonProperty("gender_description")
    private String genderEligibilityDescription;
    @JsonProperty("date_updated")
    private String dateUpdated;
    @JsonProperty("study_protocol_id")
    private String studyProtocolId;
    @JsonProperty("nci_id")
    private String nciId;
    @JsonProperty("id")
    private String id;
    
    /**
     * constructor
     */
    public AdditionalEligibilityCriteriaDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param gender gender
     * @param genderEligibilityDescription the genderEligibilityDescription
     */
    public AdditionalEligibilityCriteriaDTO(String gender,
            String genderEligibilityDescription) {
        super();
        this.gender = gender;
        this.genderEligibilityDescription = genderEligibilityDescription;
    }

    /**
     * 
     * @return gender the gender
     */
    public String getGender() {
        return gender;
    }
    /**
     * 
     * @param gender 
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return genderEligibilityDescription 
     */
    public String getGenderEligibilityDescription() {
        return genderEligibilityDescription;
    }

    /**
     * 
     * @param genderEligibilityDescription the genderEligibilityDescription
     */
    public void setGenderEligibilityDescription(
            String genderEligibilityDescription) {
        this.genderEligibilityDescription = genderEligibilityDescription;
    }
    /**
     * 
     * @return dateUpdated
     */
    public String getDateUpdated() {
        return dateUpdated;
    }
    /**
     * 
     * @param dateUpdated the dateUpdated
     */
    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
    /**
     * 
     * @return studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }
    /**
     * 
     * @param studyProtocolId the studyProtocolId
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    /**
     * 
     * @return nciId
     */
    public String getNciId() {
        return nciId;
    }
    /**
     * 
     * @param nciId the nciId
     */
    public void setNciId(String nciId) {
        this.nciId = nciId;
    }
    /**
     * 
     * @return id
     */
    public String getId() {
        return id;
    }
    /**
     * 
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return String value
     */
    @Override
    public String toString() {
        return "AdditionalEligibilityCriteriaDTO [gender=" + gender
                + ", genderEligibilityDescription="
                + genderEligibilityDescription + ", dateUpdated=" + dateUpdated
                + ", studyProtocolId=" + studyProtocolId + ", nciId=" + nciId
                + "]";
    }


}
