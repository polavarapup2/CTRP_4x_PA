package gov.nih.nci.ctrp.importtrials.dto;


import gov.nih.nci.pa.webservices.dto.StudyProtocolWebServiceDTO;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterventionalStudyProtocolDTO extends StudyProtocolWebServiceDTO {
    private String allocationCode;
    private List<String> blindedRoleCode;
    private String designConfigurationCode;
    private Integer numberOfInterventionGroups;
    private String studyClassificationCode;
    
    /**
     * const
     */
    public InterventionalStudyProtocolDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param allocationCode allocationCode
     * @param blindedRoleCode blindedRoleCode
     * @param designConfigurationCode designConfigurationCode
     * @param numberOfInterventionGroups numberOfInterventionGroups
     * @param studyClassificationCode studyClassificationCode
     */
    public InterventionalStudyProtocolDTO(String allocationCode,
            List<String> blindedRoleCode, String designConfigurationCode,
            Integer numberOfInterventionGroups, String studyClassificationCode) {
        super();
        this.allocationCode = allocationCode;
        this.blindedRoleCode = blindedRoleCode;
        this.designConfigurationCode = designConfigurationCode;
        this.numberOfInterventionGroups = numberOfInterventionGroups;
        this.studyClassificationCode = studyClassificationCode;
    }
    /**
     * 
     * @return allocationCode
     */
    public String getAllocationCode() {
        return allocationCode;
    }
    /**
     * 
     * @param allocationCode the allocationCode
     */
    public void setAllocationCode(String allocationCode) {
        this.allocationCode = allocationCode;
    }
    /**
     * 
     * @return blindedRoleCode
     */
    public List<String> getBlindedRoleCode() {
        return blindedRoleCode;
    }
    /**
     * 
     * @param blindedRoleCode the blindedRoleCode
     */
    public void setBlindedRoleCode(List<String> blindedRoleCode) {
        this.blindedRoleCode = blindedRoleCode;
    }

    /**
     * 
     * @return designConfigurationCode
     */
    public String getDesignConfigurationCode() {
        return designConfigurationCode;
    }
    /**
     * 
     * @param designConfigurationCode the designConfigurationCode
     */
    public void setDesignConfigurationCode(String designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
    }
    /**
     * 
     * @return numberOfInterventionGroups
     */
    public Integer getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }
    /**
     * 
     * @param numberOfInterventionGroups the numberOfInterventionGroups
     */
    public void setNumberOfInterventionGroups(Integer numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
    }
    /**
     * 
     * @return studyClassificationCode
     */
    public String getStudyClassificationCode() {
        return studyClassificationCode;
    }
    /**
     * 
     * @param studyClassificationCode the studyClassificationCode
     */
    public void setStudyClassificationCode(String studyClassificationCode) {
        this.studyClassificationCode = studyClassificationCode;
    }
    
    

}
