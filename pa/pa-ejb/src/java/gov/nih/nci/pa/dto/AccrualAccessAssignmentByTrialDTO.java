/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Denis G. Krylov
 *
 */
public class AccrualAccessAssignmentByTrialDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private SummaryFourFundingCategoryCode categoryCode;
    
    private String trialNciId;
    
    private String trialTitle;
    
    private Set<String> accrualSubmitters = new TreeSet<String>();

    /**
     * @return the categoryCode
     */
    public SummaryFourFundingCategoryCode getCategoryCode() {
        return categoryCode;
    }

    /**
     * @param categoryCode the categoryCode to set
     */
    public void setCategoryCode(SummaryFourFundingCategoryCode categoryCode) {
        this.categoryCode = categoryCode;
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
     * @return the accrualSubmitters
     */
    public Set<String> getAccrualSubmitters() {
        return accrualSubmitters;
    }

    /**
     * @param accrualSubmitters the accrualSubmitters to set
     */
    public void setAccrualSubmitters(Set<String> accrualSubmitters) {
        this.accrualSubmitters = accrualSubmitters;
    }
    
}
