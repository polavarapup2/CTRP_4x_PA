/**
 * 
 */
package gov.nih.nci.pa.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis G. Krylov
 * 
 */
public class AccrualSubmissionAccessWebDTO implements Serializable { //NOPMD

    /**
     * 
     */
    private static final long serialVersionUID = 2761690244780289607L;

    private Long trialId;
    private String trialNciId;
    private String trialTitle;
    private List<OrganizationWebDTO> participatingSites = new ArrayList<OrganizationWebDTO>();

    /**
     * @return the trialId
     */
    public Long getTrialId() {
        return trialId;
    }

    /**
     * @param trialId
     *            the trialId to set
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
     * @param trialNciId
     *            the trialNciId to set
     */
    public void setTrialNciId(String trialNciId) {
        this.trialNciId = trialNciId;
    }

    /**
     * @return the participatingSites
     */
    public List<OrganizationWebDTO> getParticipatingSites() {
        return participatingSites;
    }

    /**
     * @param participatingSites
     *            the participatingSites to set
     */
    public void setParticipatingSites(
            List<OrganizationWebDTO> participatingSites) {
        this.participatingSites = participatingSites;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((trialId == null) ? 0 : trialId.hashCode());
        result = prime * result
                + ((trialNciId == null) ? 0 : trialNciId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) { // NOPMD
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AccrualSubmissionAccessWebDTO)) {
            return false;
        }
        AccrualSubmissionAccessWebDTO other = (AccrualSubmissionAccessWebDTO) obj;
        if (trialId == null) {
            if (other.trialId != null) {
                return false;
            }
        } else if (!trialId.equals(other.trialId)) {
            return false;
        }
        if (trialNciId == null) {
            if (other.trialNciId != null) {
                return false;
            }
        } else if (!trialNciId.equals(other.trialNciId)) {
            return false;
        }
        return true;
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

}
