/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Denis G. Krylov
 * 
 */
class AbstractGeneralTrialDesignAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final String FALSE = "FALSE";

    /**
     * GeneralTrialDesignWebDTO.
     */
    //CHECKSTYLE:OFF
    protected GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    
    
    protected void validateResponsibleParty() {
        String type = getGtdDTO().getResponsiblePartyType();
        if (StringUtils.isBlank(type)) {
            checkForMandatoryResponsibleParty();
        } else if ("pi".equals(type) || "si".equals(type)) {
            validateRespPartyInvestigator();
        } else if ("sponsor".equals(type)) {
            validateCtGovXmlRequiredFields();
        }
    }

    protected void validateRespPartyInvestigator() {
        String type = gtdDTO.getResponsiblePartyType();
        if (StringUtils.isBlank(gtdDTO.getResponsiblePersonIdentifier())) {
            addFieldError("responsiblePersonName",
                    ("Please select an Investigator"));
            if ("pi".equals(type)
                    && StringUtils.isBlank(gtdDTO.getPiIdentifier())) {
                addFieldError("LeadPINotSelected",
                        ("Please select a Principal Investigator"));
            }
        }
        if (StringUtils.isBlank(gtdDTO.getResponsiblePersonAffiliationOrgId())) {
            addFieldError("responsiblePersonAffiliationOrgName",
                    ("Please select an affiliation"));
        }
        if (StringUtils.isBlank(gtdDTO.getResponsiblePersonTitle())) {
            addFieldError("responsiblePersonTitle",
                    ("Please specify a title"));
        }
    }

    protected void checkForMandatoryResponsibleParty() {
        if ((isNotProprietary() && gtdDTO.isCtGovXmlRequired())) {
            addFieldError("gtdDTO.responsiblePartyType",
                    ("Please select a Responsible Party"));
        }
    }
    
    protected void validateCtGovXmlRequiredFields() {
        if (StringUtils.isEmpty(gtdDTO.getSponsorIdentifier())) {
            addFieldError("gtdDTO.sponsorName", ("Sponsor must be entered"));
        }       
    }

    
    protected boolean isNotProprietary() {
        return gtdDTO.getProprietarytrialindicator() == null
                || gtdDTO.getProprietarytrialindicator().equalsIgnoreCase(FALSE);
    }
    
    

    /**
     * 
     * @return gtdDTO
     */
    public GeneralTrialDesignWebDTO getGtdDTO() {
        return gtdDTO;
    }

    /**
     * 
     * @param gtdDTO
     *            gtdDTO
     */
    public void setGtdDTO(GeneralTrialDesignWebDTO gtdDTO) {
        this.gtdDTO = gtdDTO;
    }

}
