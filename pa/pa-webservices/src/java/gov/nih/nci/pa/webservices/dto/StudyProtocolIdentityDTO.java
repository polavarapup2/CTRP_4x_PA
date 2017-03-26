package gov.nih.nci.pa.webservices.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyProtocolIdentityDTO {
    private String nciId;
    private String studyProtocolId;
    private String nctId;
    private String userLastCreated;
    private String trialType;
    private String currentUser;
    private boolean updateIndicator;
    private List<String> secondaryIdentifiers;
    private boolean proprietaryTrial;
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
     * @return nctId
     */
    public String getNctId() {
        return nctId;
    }
    /**
     * 
     * @param nctId the nctId
     */
    public void setNctId(String nctId) {
        this.nctId = nctId;
    }
    /**
     * 
     * @return userLastCreated 
     */
    public String getUserLastCreated() {
        return userLastCreated;
    }
    /**
     * 
     * @param userLastCreated the userLastCreated
     */
    public void setUserLastCreated(String userLastCreated) {
        this.userLastCreated = userLastCreated;
    }
    /**
     * 
     * @return trialType
     */
    public String getTrialType() {
        return trialType;
    }
    /**
     * 
     * @param trialType the trialType
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }
    /**
     * 
     * @return currentUser
     */
    public String getCurrentUser() {
        return currentUser;
    }
    /**
     * 
     * @param currentUser the currentUser
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    /**
     * 
     * @return updateIndicator
     */
    public boolean isUpdateIndicator() {
        return updateIndicator;
    }
    /**
     * 
     * @param updateIndicator the updateIndicator
     */
    public void setUpdateIndicator(boolean updateIndicator) {
        this.updateIndicator = updateIndicator;
    }
    /**
     * 
     * @return secondaryIdentifiers
     */
    public List<String> getSecondaryIdentifiers() {
        return secondaryIdentifiers;
    }
    /**
     * 
     * @param secondaryIdentifiers the secondaryIdentifiers
     */
    public void setSecondaryIdentifiers(List<String> secondaryIdentifiers) {
        this.secondaryIdentifiers = secondaryIdentifiers;
    }
    /**
     * 
     * @return proprietaryTrial
     */
    public boolean isProprietaryTrial() {
        return proprietaryTrial;
    }
    /**
     * 
     * @param proprietaryTrial the proprietaryTrial
     */
    public void setProprietaryTrial(boolean proprietaryTrial) {
        this.proprietaryTrial = proprietaryTrial;
    }
    
    
    
}
