/**
 * caBIG Open Source Software License
 */
package gov.nih.nci.registry.dto;

import java.io.Serializable;


/**
 * 
 * @author Reshma.Koganti
 *
 */
public class TrialVerificationDataWebDTO implements Serializable {
    private static final long serialVersionUID = 4631799276433167679L;
    private String verificationMethod;
    private String id;
    private String studyProtocolId;
    private String updatedDate;
    private String userLastUpdated;
    
    /**
     * 
     * @return verificationMethod verificationMethod
     */
    public String getVerificationMethod() {
        return verificationMethod;
    }
    /**
     * 
     * @param verificationMethod verificationMethod
     */
    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }
    /**
     * 
     * @return id id
     */
    public String getId() {
        return id;
    }
    /**
     * 
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 
     * @return studyProtocolId studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }
    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    /**
     * 
     * @return updatedDate updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }
    /**
     * 
     * @param updatedDate updatedDate
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
    /**
     * 
     * @return userLastUpdated userLastUpdated
     */
    public String getUserLastUpdated() {
        return userLastUpdated;
    }
    /**
     * 
     * @param userLastUpdated userLastUpdated
     */
    public void setUserLastUpdated(String userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }
    
    

}
