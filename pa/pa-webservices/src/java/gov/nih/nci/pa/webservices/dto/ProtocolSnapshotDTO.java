package gov.nih.nci.pa.webservices.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProtocolSnapshotDTO {
    private List<PlannedEligibilityCriterionDTO> eligibilityList;
    private List<ArmDTO> armList;
    private String publicDescription;
    private String scientificDescription;
    private String keywordText;
    private String studyInboxId;
    private String lastChangedDate;
    /**
     * const
     */
    public ProtocolSnapshotDTO() {
        super();
       
    }
    /**
     * 
     * @param eligibilityList eligibilityList
     * @param armList armList
     * @param publicDescription publicDescription
     * @param scientificDescription scientificDescription
     * @param keywordText keywordText
     * @param studyInboxId studyInboxId
     * @param lastChangedDate lastChangedDate
     */
    public ProtocolSnapshotDTO(
            List<PlannedEligibilityCriterionDTO> eligibilityList,
            List<ArmDTO> armList, String publicDescription,
            String scientificDescription, String keywordText,
            String studyInboxId, String lastChangedDate) {
        super();
        this.eligibilityList = eligibilityList;
        this.armList = armList;
        this.publicDescription = publicDescription;
        this.scientificDescription = scientificDescription;
        this.keywordText = keywordText;
        this.studyInboxId = studyInboxId;
        this.lastChangedDate = lastChangedDate;
    }
    /**
     * @return the eligibilityList
     */
    public List<PlannedEligibilityCriterionDTO> getEligibilityList() {
        return eligibilityList;
    }
    /**
     * @param eligibilityList the eligibilityList to set
     */
    public void setEligibilityList(
            List<PlannedEligibilityCriterionDTO> eligibilityList) {
        this.eligibilityList = eligibilityList;
    }
    /**
     * @return the armList
     */
    public List<ArmDTO> getArmList() {
        return armList;
    }
    /**
     * @param armList the armList to set
     */
    public void setArmList(List<ArmDTO> armList) {
        this.armList = armList;
    }
    /**
     * @return the publicDescription
     */
    public String getPublicDescription() {
        return publicDescription;
    }
    /**
     * @param publicDescription the publicDescription to set
     */
    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }
    /**
     * @return the scientificDescription
     */
    public String getScientificDescription() {
        return scientificDescription;
    }
    /**
     * @param scientificDescription the scientificDescription to set
     */
    public void setScientificDescription(String scientificDescription) {
        this.scientificDescription = scientificDescription;
    }
    /**
     * @return the keywordText
     */
    public String getKeywordText() {
        return keywordText;
    }
    /**
     * @param keywordText the keywordText to set
     */
    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }
    /**
     * @return the studyInboxId
     */
    public String getStudyInboxId() {
        return studyInboxId;
    }
    /**
     * @param studyInboxId the studyInboxId to set
     */
    public void setStudyInboxId(String studyInboxId) {
        this.studyInboxId = studyInboxId;
    }
    /**
     * @return the lastChangedDate
     */
    public String getLastChangedDate() {
        return lastChangedDate;
    }
    /**
     * @param lastChangedDate the lastChangedDate to set
     */
    public void setLastChangedDate(String lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }
    
}
