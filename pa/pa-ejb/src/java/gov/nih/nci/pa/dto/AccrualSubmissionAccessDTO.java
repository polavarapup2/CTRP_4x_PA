/**
 * 
 */
package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 * 
 */
public class AccrualSubmissionAccessDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7352786058763010690L;
    
    private Long trialId;
    private String trialNciId;
    private String trialTitle;
    private String participatingSiteOrgName;
    private String participatingSitePoOrgId;
    private Long studySiteId;
    /**
     * @return the trialId
     */
    public Long getTrialId() {
        return trialId;
    }
    /**
     * @param trialId the trialId to set
     */
    public void setTrialId(Long trialId) {
        this.trialId = trialId;
    }
    /**
     * @return the trialNciId
     */
    public String getTrialNciId() {
        return trialNciId;
    }
    /**
     * @param trialNciId the trialNciId to set
     */
    public void setTrialNciId(String trialNciId) {
        this.trialNciId = trialNciId;
    }
    /**
     * @return the trialTitle
     */
    public String getTrialTitle() {
        return trialTitle;
    }
    /**
     * @param trialTitle the trialTitle to set
     */
    public void setTrialTitle(String trialTitle) {
        this.trialTitle = trialTitle;
    }
    /**
     * @return the participatingSiteOrgName
     */
    public String getParticipatingSiteOrgName() {
        return participatingSiteOrgName;
    }
    /**
     * @param participatingSiteOrgName the participatingSiteOrgName to set
     */
    public void setParticipatingSiteOrgName(String participatingSiteOrgName) {
        this.participatingSiteOrgName = participatingSiteOrgName;
    }
    /**
     * @return the participatingSitePoOrgId
     */
    public String getParticipatingSitePoOrgId() {
        return participatingSitePoOrgId;
    }
    /**
     * @param participatingSitePoOrgId the participatingSitePoOrgId to set
     */
    public void setParticipatingSitePoOrgId(String participatingSitePoOrgId) {
        this.participatingSitePoOrgId = participatingSitePoOrgId;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccrualSubmissionAccessDTO [trialId=").append(trialId)
                .append(", trialNciId=").append(trialNciId)
                .append(", trialTitle=").append(trialTitle)
                .append(", participatingSiteOrgName=")
                .append(participatingSiteOrgName)
                .append(", participatingSitePoOrgId=")
                .append(participatingSitePoOrgId).append("]");
        return builder.toString();
    }
    /**
     * @return the studySiteId
     */
    public Long getStudySiteId() {
        return studySiteId;
    }
    /**
     * @param studySiteId the studySiteId to set
     */
    public void setStudySiteId(Long studySiteId) {
        this.studySiteId = studySiteId;
    }
    

}
