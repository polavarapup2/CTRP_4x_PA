package gov.nih.nci.registry.action;

import gov.nih.nci.pa.lov.ConsortiaTrialCategoryCode;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * Base class for proprietary trial management action classes.
 * 
 * @author Denis G. Krylov
 * @see
 * 
 */
public class AbstractBaseProprietaryTrialAction extends ManageFileAction
        implements ServletResponseAware {

    /**
     * 
     */
    private static final long serialVersionUID = 3244347854526316932L;
    /**
     * Trial DTO.
     */
    private ProprietaryTrialDTO trialDTO;

    // CHECKSTYLE:ON
   

    private String trialAction;

    /**
     * Gets the trial dto.
     * 
     * @return the trialDTO
     */
    public final ProprietaryTrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * Sets the trial dto.
     * 
     * @param trialDTO
     *            the trialDTO to set
     */
    public final void setTrialDTO(ProprietaryTrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }

  
    /**
     * Gets the trial action.
     * 
     * @return the trialAction
     */
    public final String getTrialAction() {
        return trialAction;
    }

    /**
     * Sets the trial action.
     * 
     * @param trialAction
     *            the trialAction to set
     */
    public final void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * checkSummary4Funding.
     */
    protected void checkSummary4Funding() {
        if (!StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && CollectionUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifiers())) {
            addFieldError("summary4FundingSponsor",
                    "Select the Data Table 4 Funding Sponsor");
        }
        if (StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && CollectionUtils.isNotEmpty(trialDTO.getSummaryFourOrgIdentifiers())) {
            addFieldError("trialDTO.summaryFourFundingCategoryCode",
                    "Select the Trial Submission Category");
        }
    }

    
    /**
     * @return ConsortiaTrialCategoryValueMap
     */
    public final Map<String, String> getConsortiaTrialCategoryValueMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String code : ConsortiaTrialCategoryCode.getDisplayNames()) {
            map.put(code, "No - " + code);
        }
        return map;
    }
    
    @Override
    public final TrialType getTrialTypeHandledByThisClass() {     
        return TrialType.ABBREVIATED;
    }
    
    @Override
    public boolean isOpenSitesWarningRequired() {     
        return false;
    }

}