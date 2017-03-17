package gov.nih.nci.pa.webservices.dto;

import java.util.List;
/**
 * 
 * @author Reshma
 *
 */

public class InterventionalSPWebServiceDTO extends StudyProtocolWebServiceDTO {
    private String allocationCode;
    private List<String> blindedRoleCode;
    private String blindingSchemaCode;
    private String designConfigurationCode;
    private Integer numberOfInterventionGroups;
    private String studyClassificationCode;
    
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
     * @return blindingSchemaCode
     */
    public String getBlindingSchemaCode() {
        return blindingSchemaCode;
    }
    /**
     * 
     * @param blindingSchemaCode the blindingSchemaCode
     */
    public void setBlindingSchemaCode(String blindingSchemaCode) {
        this.blindingSchemaCode = blindingSchemaCode;
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
