/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;

import java.util.ArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;

/**
 * Class that encapsulates validation rules for participating sites.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class ParticipatingSiteValidator implements Validateable {

    private final SubmittedOrganizationDTO siteDTO;

    private final ValidationAware errorReporter;

    private final TextProvider textProvider;

    private final PAServiceUtils paServiceUtil;

    private final StatusTransitionService statusTransitionService;

    private static final Logger LOG = Logger
            .getLogger(ParticipatingSiteValidator.class);

    /**
     * @param siteDTO
     *            siteDTO
     * @param errorReporter
     *            errorReporter
     * @param textProvider
     *            textProvider
     * @param paServiceUtil
     *            paServiceUtil
     * @param statusTransitionService
     *            statusTransitionService
     */
    public ParticipatingSiteValidator(SubmittedOrganizationDTO siteDTO,
            ValidationAware errorReporter, TextProvider textProvider,
            PAServiceUtils paServiceUtil,
            StatusTransitionService statusTransitionService) {
        this.siteDTO = siteDTO;
        this.errorReporter = errorReporter;
        this.textProvider = textProvider;
        this.paServiceUtil = paServiceUtil;
        this.statusTransitionService = statusTransitionService;
    }

    @Override
    public void validate() {
        enforceBusinessRulesForProprietary();
    }

    // The following validation methods have been copied as-is from
    // gov.nih.nci.pa.action.ParticipatingOrganizationsAction and adjusted for
    // use here.
    // The original validation methods in ParticipatingOrganizationsAction are
    // hard-wired
    // into the action class and are not easily reusable; hence the copy &
    // paste.
    // This needs to be re-visited at a later point to see how we could extract
    // the validation
    // into common functionality and stay DRY. TO DO. Sorry.
    private void enforceBusinessRulesForProprietary() {
        checkInvestigatorStatus();
        enforcePartialRulesForProp1();
    }

    private void checkInvestigatorStatus() {
        if (siteDTO.getInvestigatorId() != null) {
            Ii investigatorIi = IiConverter.convertToPoPersonIi(siteDTO
                    .getInvestigatorId().toString());
            if (paServiceUtil.getPoPersonEntity(investigatorIi) == null) {
                errorReporter.addFieldError("investigator",
                        textProvider.getText("error.nullifiedInvestigator"));
            }
        }

    }

    private void enforcePartialRulesForProp1() {
        checkFieldError(
                StringUtils.isEmpty(siteDTO.getSiteLocalTrialIdentifier()),
                "localIdentifier", "error.siteLocalTrialIdentifier.required");
        checkFieldError(siteDTO.getInvestigatorId() == null, "investigator",
                "error.selectedPersId.required");

        if (CollectionUtils.isEmpty(siteDTO.getStatusHistory())) {
            checkFieldError(
                    StringUtils.isEmpty(siteDTO.getRecruitmentStatus()),
                    "statusCode",
                    "error.participatingOrganizations.recruitmentStatus");
            if (!PAUtil.isValidDate(siteDTO.getRecruitmentStatusDate())) {
                errorReporter.addFieldError("statusDate",
                        "A valid Recruitment Status Date is required");
            } else if (PAUtil.isDateCurrentOrPast(siteDTO
                    .getRecruitmentStatusDate())) {
                errorReporter.addFieldError("statusDate",
                        textProvider.getText("error.submit.invalidStatusDate"));
            }
        } else {
            try {
                final ArrayList<StatusDto> validatedList = new ArrayList<StatusDto>(
                        siteDTO.getStatusHistory());
                statusTransitionService.validateStatusHistory(
                        AppName.REGISTRATION, TrialType.ABBREVIATED,
                        TransitionFor.SITE_STATUS, validatedList);
                if (CollectionUtils.exists(validatedList, new Predicate() {
                    @Override
                    public boolean evaluate(Object arg0) {
                        StatusDto s = (StatusDto) arg0;
                        return s.hasErrorOfType(ErrorType.ERROR);
                    }
                })) {
                    errorReporter
                            .addFieldError("statusDate",
                                    "Recruitment status history for this site has errors; please see below");
                }
            } catch (PAException e) {
                LOG.error(e, e);
                errorReporter.addFieldError("statusDate",
                        "Unable to validate recruitment status history for this site: "
                                + e.getMessage());
            }
        }
    }

    // NOPMD
    private void checkFieldError(boolean condition, String fieldName,
            String textKey) {
        if (condition) {
            errorReporter.addFieldError(fieldName,
                    textProvider.getText(textKey));
        }
    }
}
