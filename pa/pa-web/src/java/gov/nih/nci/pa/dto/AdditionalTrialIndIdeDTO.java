package gov.nih.nci.pa.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author chandrasekaranp
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalTrialIndIdeDTO {
    @JsonProperty("study_protocol_id")
    private Long studyProtocolId;
    
    @JsonProperty("trial_indide_id")
    private Long trialIndIdeId;
    
    @JsonProperty("expanded_access_indicator")
    private String expandedAccessIndicator;
    
    @JsonProperty("expanded_access_nct_id")
    private String expandedAccessNctId;
    
    @JsonProperty("date_updated")
    private String dateUpdated;

    /**
     * Constructor 
     */
    public AdditionalTrialIndIdeDTO() {
        super();
    }

    /**
     * 
     * @param studyProtocolId the studyProtocolId
     * @param trialIndIdeId the trialIndIdeId
     * @param expandedAccessIndicator the expandedAccessIndicator
     * @param expandedAccessNctId the expandedAccessNctId
     */
    public AdditionalTrialIndIdeDTO(Long studyProtocolId, Long trialIndIdeId, String expandedAccessIndicator,
            String expandedAccessNctId) {
        super();
        this.studyProtocolId = studyProtocolId;
        this.trialIndIdeId = trialIndIdeId;
        this.expandedAccessIndicator = expandedAccessIndicator;
        this.expandedAccessNctId = expandedAccessNctId;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId the studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    
    /**
     * 
     * @return trialIndIdeId
     */
    public Long getTrialIndIdeId() {
        return trialIndIdeId;
    }

    /**
     * 
     * @param trialIndIdeId the trialIndIdeId
     */
    public void setTrialIndIdeId(Long trialIndIdeId) {
        this.trialIndIdeId = trialIndIdeId;
    }

    /**
     * 
     * @return expandedAccessIndicator
     */
    public String getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }

    /**
     * 
     * @param expandedAccessIndicator the expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(String expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }

    /**
     * 
     * @return expandedAccessNctId
     */
    public String getExpandedAccessNctId() {
        return expandedAccessNctId;
    }

    /**
     * 
     * @param expandedAccessNctId the expandedAccessNctId
     */
    public void setExpandedAccessNctId(String expandedAccessNctId) {
        this.expandedAccessNctId = expandedAccessNctId;
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
     * @return String the concatenated String of all the fields
     */
    @Override
    public String toString() {
        return "AdditionalTrialIndIdeDTO [studyProtocolId=" + studyProtocolId  + ", trialIndIdeId=" + trialIndIdeId
                + ", expandedAccessIndicator=" + expandedAccessIndicator + ", expandedAccessNctId="
                + expandedAccessNctId + ", dateUpdated=" + dateUpdated + "]";
    }

}
