/*
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate, market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights  set out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, caBIG Participant shall have no right of accounting or right of payment from You or
 * Your sub licensees for the rights granted under this License.   This  License  is  granted  at no  charge  to You.
 * Your downloading, copying, modifying, displaying, distributing or use of caBIG Software constitutes acceptance  of
 * all of the terms and conditions of this Agreement.  If You do not agree to such terms and conditions,  You have
 * no right to download,  copy,  modify, display, distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any,  must include the following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs, You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * caBIG SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package gov.nih.nci.pa.service.util;


import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO.ErrorMessageTypeEnum;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.OutcomeMeasureTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingService.Method;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceCachingDecorator;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.exception.PAValidationException.Level;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * service bean for validating the Abstraction.
 *
 * @author Kalpana Guthikonda
 * @since 11/27/2008
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class AbstractionCompletionServiceBean implements AbstractionCompletionServiceLocal {

    private static final String NO_GROUPS_COHORTS_EXISTS_FOR_THE_TRIAL = "No Groups/Cohorts exists for the trial.";
    private static final String SELECT_GROUPS_COHORTS_HINT = "Select Groups/Cohorts from "
            + "Non-Interventional Trial Design "
            + "under Scientific Data menu.";
    private static final String NO_ARM_EXISTS_FOR_THE_TRIAL = "No Arm exists for the trial.";
    private static final String ARM_TYPE_REQUIRED = "Arm Type is required: ";
    private static final String SELECT_ARM_UNDER_SCIENTIFIC_DATA_MENU = "Select Arm under Scientific Data menu.";
    private static final String SELECT = "Select ";
    private static final String INTERVENTIONAL_STUDY_PROTOCOL = "InterventionalStudyProtocol";
    private static final String NON_INTERVENTIONAL_STUDY_PROTOCOL = "NonInterventionalStudyProtocol";
    private static final String SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU = 
            "Select Participating Sites from Administrative Data menu.";
    private static final String SELECT_INT_TRIAL_DESIGN_DETAILS_MSG = "Select Design Details from Interventional Trial"
            + " Design under Scientific Data menu.";
    private static final String SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG = "Select Design Details from "
            + "Non-Interventional Trial"
            + " Design under Scientific Data menu.";
    private static final String SELECT_TRIAL_DESCRIPTION = "Select Trial Description from Scientific Data menu.";
    private static final String SELECT_TRIAL_DETAILS = "Select General Trial Details from Administrative Data menu.";
    private static final String SELECT_TRIAL_STATUS = "Select Trial Status from Administrative Data menu.";
    private static final String SELECT_TRIAL_STATUS_HISTORY = "Select Trial Status from Administrative Data menu,"
            + " then click History.";
    private static final String YES = "Yes";
    private static final String NO = "No";

    private CorrelationUtils correlationUtils = new CorrelationUtils();
    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    @EJB
    private ArmServiceLocal armService;
    @EJB
    private DocumentServiceLocal documentService;
    @EJB
    private InterventionServiceLocal interventionService;
    @EJB
    private OrganizationCorrelationServiceRemote organizationCorrelationService;
    @EJB
    private PlannedActivityServiceLocal plannedActivityService;
    @EJB
    private PlannedMarkerServiceLocal plannedMarkerService;
    @EJB
    private RegulatoryInformationServiceLocal regulatoryInformationService;
    @EJB
    private StudyContactServiceLocal studyContactService;
    @EJB
    private StudyDiseaseServiceLocal studyDiseaseService;
    @EJB
    private StudyIndldeServiceLocal studyIndldeService;
    @EJB
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;
    @EJB
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;   
    @EJB
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService;
    @EJB
    private StudyResourcingServiceLocal studyResourcingService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private StudySiteContactServiceLocal studySiteContactService;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AbstractionCompletionDTO> validateAbstractionCompletion(Ii studyProtocolIi) throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null");
        }
        AbstractionMessageCollection messages = new AbstractionMessageCollection();

        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);        
        
        enforceBlindingSchemaRules(studyProtocolDTO, messages);
        
        if (isPropTrial(studyProtocolDTO)) {
            abstractionCompletionRuleForProprietary(studyProtocolDTO, messages);
        } else {
            enforceIdentifierLength(studyProtocolDTO, messages);
            enforceGeneralTrialDetails(studyProtocolDTO, messages);
            enforceNCISpecificInfo(studyProtocolDTO, messages);
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                enforceRegulatoryInfo(studyProtocolIi, messages);
            }
            enforceIRBInfo(studyProtocolDTO, messages);
            enforceTrialINDIDE(studyProtocolDTO, messages);
            enforceTrialStatus(studyProtocolDTO, messages);
            enforceRecruitmentStatus(studyProtocolIi, messages);

            List<DocumentDTO> isoList = documentService.getDocumentsByStudyProtocol(studyProtocolIi);
            String protocolDoc = null;
            String irbDoc = null;
            if ((CollectionUtils.isNotEmpty(isoList))) {
                for (DocumentDTO dto : isoList) {
                    if (dto.getTypeCode().getCode().equalsIgnoreCase(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode())) {
                        protocolDoc = dto.getTypeCode().getCode().toString();
                    } else if (dto.getTypeCode().getCode()
                        .equalsIgnoreCase(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode())) {
                        irbDoc = dto.getTypeCode().getCode().toString();
                    }
                }
            }
            enforceDocument(protocolDoc, irbDoc, messages);

            if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase(INTERVENTIONAL_STUDY_PROTOCOL)) {
                InterventionalStudyProtocolDTO ispDTO = (InterventionalStudyProtocolDTO) studyProtocolDTO;             
                enforceInterventional(ispDTO, messages);
                final Integer numOfArms = ispDTO.getNumberOfInterventionGroups().getValue();
                if (numOfArms != null) {
                    List<ArmDTO> aList = armService.getByStudyProtocol(studyProtocolIi);
                    if (aList.size() != numOfArms
                            && !(numOfArms == 1 && aList.isEmpty())) {
                        // CHECKSTYLE:OFF
                        messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG,
                                          "Number of interventional trial arm records must be the same"
                                                  + " as Number of Arms assigned in Interventional Trial Design.", 
                                                  ErrorMessageTypeEnum.SCIENTIFIC, 12);
                    }
                }
            } else if (studyProtocolDTO.getStudyProtocolType().getValue()
                .equalsIgnoreCase(NON_INTERVENTIONAL_STUDY_PROTOCOL)) {
                NonInterventionalStudyProtocolDTO ospDTO = new NonInterventionalStudyProtocolDTO();
                ospDTO = studyProtocolService.getNonInterventionalStudyProtocol(studyProtocolIi);
                enforeNonInterventional(ospDTO, messages);
                enforceNonInterventionalGroups(studyProtocolIi, messages,
                        ospDTO);
            }
            enforceTrialDescriptionDetails(studyProtocolDTO, messages);
            enforceOutcomeMeasure(studyProtocolIi, messages);
            enforceInterventions(studyProtocolDTO, messages);
            enforceTreatingSite(studyProtocolIi, messages);
            enforceStudyContactNullification(studyProtocolIi, messages);
            enforceStudySiteNullification(studyProtocolIi, messages);
            enforceStudySiteContactNullification(studyProtocolIi, messages);
            enforceArmGroup(studyProtocolIi, studyProtocolDTO, messages);
            enforceTrialFunding(studyProtocolDTO, messages);
            enforceDisease(studyProtocolDTO, messages);
            enforceArmOrGroupAssociationToIntervention(studyProtocolDTO, messages);
            enforceEligibility(studyProtocolDTO, messages);
            enforceCollaborator(studyProtocolIi, messages);
            enforceSummary4OrgNullification(studyProtocolIi, messages);
            enforcePlannedMarkerStatus(studyProtocolIi, messages);
        }
        return messages.getMessages();
    }

    /**
     * @param studyProtocolDTO
     * @return
     */
    private boolean isPropTrial(StudyProtocolDTO studyProtocolDTO) {
        return !ISOUtil.isBlNull(studyProtocolDTO.getProprietaryTrialIndicator())
                && BlConverter.convertToBoolean(studyProtocolDTO.getProprietaryTrialIndicator());
    }

    void enforceBlindingSchemaRules(StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) {
        if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
            InterventionalStudyProtocolDTO ispDTO = (InterventionalStudyProtocolDTO) studyProtocolDTO;
            enforceBlindingSchemaRules(ispDTO, messages);
        }
    }

    /**
     * @param ispDTO
     */
    private void enforceBlindingSchemaRules(
            InterventionalStudyProtocolDTO ispDTO,
            AbstractionMessageCollection messages) {
        int totBlindCodes = 0;
        if (ISOUtil.isDSetNotEmpty(ispDTO.getBlindedRoleCode())) {
            totBlindCodes = ispDTO.getBlindedRoleCode().getItem().size();
        }
        checkBlindingSchemaCode(ispDTO, totBlindCodes, messages);
    }

    private void checkBlindingSchemaCode(InterventionalStudyProtocolDTO ispDTO, // NOPMD
            int totBlindCodes, AbstractionMessageCollection messages) {
        if (ispDTO.getBlindingSchemaCode() != null) {
            if (BlindingSchemaCode.OPEN.getCode().equals(
                    ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes > 0) {
                addBlindingSchemaMessage(ispDTO, messages,
                        "Open Blinding Schema code cannot have any Blinded codes.");
            }
            if (BlindingSchemaCode.SINGLE_BLIND.getCode().equals(
                    ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes > 1) {
                addBlindingSchemaMessage(ispDTO, messages,
                        "Only one masking role must be specified for \"Single Blind\" masking.");
            }
            if (BlindingSchemaCode.SINGLE_BLIND.getCode().equals(
                    ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes < 1) {
                addBlindingSchemaMessage(ispDTO, messages,
                        "Single Blinding Schema code must have 1 Blinded code.");
            }
            if (BlindingSchemaCode.DOUBLE_BLIND.getCode().equals(
                    ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes < 2) {
                addBlindingSchemaMessage(ispDTO, messages,
                        "At least two masking roles must be specified for \"Double Blind\" masking.");
            }
        }

    }

    private void addBlindingSchemaMessage(
            InterventionalStudyProtocolDTO ispDTO,
            AbstractionMessageCollection messages, String msg) {
        if (isPropTrial(ispDTO)) {
            messages.addWarning(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, msg, 12);
        } else {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, msg,
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
    }

    /**
     * @param studyProtocolIi
     * @param messages
     * @param ospDTO
     * @throws PAException
     */
    private void enforceNonInterventionalGroups(Ii studyProtocolIi,
            AbstractionMessageCollection messages,
            NonInterventionalStudyProtocolDTO ospDTO) throws PAException {
        final Integer groups = ospDTO.getNumberOfGroups().getValue();
        final int interventions = getNumberOfInterventions(studyProtocolIi);
        if (groups != null) {
            List<ArmDTO> aList = armService.getByStudyProtocol(studyProtocolIi);
            // PO-5852: If a study is single-group (i.e. number of
            // Groups/Cohorts = 1) and Group/Cohort (detail) is not present on
            // study but there exists one/none intervention for the study,
            // abstraction validation will not show any error.
            if (Integer.valueOf(1).equals(groups) && aList.isEmpty()
                    && interventions <= 1) {
                return;
            }
            // If a study is multi-group (i.e. number of Groups/Cohorts > 1) and
            // if for each Group/Cohort (detail) is not present on study.
            // Abstraction-validation will show error as follow: "Number of
            // non-interventional study group records must be the same as the
            // Number of Groups assigned in Non-Interventional Study Design." 
            if (aList.size() != groups && groups > 1) {
                messages.addError(
                        SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG,
                        "Number of non-interventional study group/cohort records must be the same"
                                + " as the Number of Groups/Cohorts assigned in Non-Interventional Study Design.",
                        ErrorMessageTypeEnum.SCIENTIFIC, 12);
            }
        }
    }
    
    private int getNumberOfInterventions(Ii studyProtocolIi) throws PAException {
        int cnt = 0;
        List<PlannedActivityDTO> paList = plannedActivityService
                .getByStudyProtocol(studyProtocolIi);
        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                cnt++;
            }
        }
        return cnt;
    }

    private void abstractionCompletionRuleForProprietary(StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) throws PAException {

        Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
        enforceIdentifierLength(studyProtocolDTO, messages);
        enforceGeneralTrialDetails(studyProtocolDTO, messages);    
        enforceTrialStatusTransitionsValidation(studyProtocolDTO, messages);
        enforceInterventions(studyProtocolDTO, messages);
        enforceStudySiteNullification(studyProtocolIi, messages);
        enforceStudySiteContactNullification(studyProtocolIi, messages);
        enforceTrialFunding(studyProtocolDTO, messages);
        enforceDisease(studyProtocolDTO, messages);
        enforceSummary4OrgNullification(studyProtocolIi, messages);
        enforcePlannedMarkerStatus(studyProtocolIi, messages);
        enforceStudySiteRuleForProprietary(studyProtocolIi, messages);
        if (studyProtocolDTO.getPhaseCode().getCode() == null) {
            messages.addError(SELECT_TRIAL_DETAILS, "Trial Phase must be Entered", ErrorMessageTypeEnum.ADMIN, 1);
        }
        if (studyProtocolDTO.getPrimaryPurposeCode().getCode() == null) {
            messages.addError(SELECT_TRIAL_DETAILS, "Primary Purpose must be Entered", ErrorMessageTypeEnum.ADMIN, 1);
        }
        List<DocumentDTO> isoList = documentService.getDocumentsByStudyProtocol(studyProtocolIi);
        String protocolDoc = null;
        for (DocumentDTO dto : isoList) {
            if (dto.getTypeCode().getCode().equalsIgnoreCase(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode())) {
                protocolDoc = dto.getTypeCode().getCode().toString();
            }
        }
        PAServiceUtils paServiceUtils = new PAServiceUtils();
        StudySiteDTO nctDto = new StudySiteDTO();
        nctDto.setStudyProtocolIdentifier(studyProtocolIi);
        nctDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        String poOrgId = organizationCorrelationService
            .getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
        nctDto.setResearchOrganizationIi(organizationCorrelationService
            .getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
        List<StudySiteDTO> studySites = paServiceUtils.getStudySite(nctDto, true);
        StudySiteDTO studySite = PAUtil.getFirstObj(studySites);
        if (protocolDoc == null && studySite == null) {
            messages.addError("Select Trial Related Documents from Administrative Data menu. "
                                      + " or Select General Trial Details from Administrative Data menu.",
                              "Either one of NCT number or Proprietary Template document is mandatory", 
                              ErrorMessageTypeEnum.ADMIN, 10);
        }
    }

    /**
     * @param abstractionList
     * @param studyProtocolIi
     * @throws PAException
     */
    private void enforceStudySiteRuleForProprietary(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);
        if (spList == null || spList.isEmpty()) {
            return;
        }
        // treating site for the study
        for (StudySiteDTO spartDto : spList) {
            List<StudySiteContactDTO> spContactDtos = new StudySiteContactServiceCachingDecorator(
                    studySiteContactService).getByStudySite(spartDto
                    .getIdentifier());
            boolean piFound = false;
            for (StudySiteContactDTO spContactDto : spContactDtos) {
                String contactRoleCode = spContactDto.getRoleCode().getCode();
                if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)
                        || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)) {
                    piFound = true;
                }
            } // for
            Organization orgBo = getPoOrg(spartDto);
            if (!piFound) {
                // Error Message ID Does Not Match Participating Site PO ID#
                messages.addError(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU, "Participating site # "
                        + orgBo.getIdentifier() + " Must have an Investigator", ErrorMessageTypeEnum.ADMIN, 8);

            }
            // No investigator duplicates must exist on the same treating site for the same trial.
            if (piFound && hasDuplicate(getPIForTreatingSite(spContactDtos))) {
                messages.addError("Select Participating Sites from " + " Administrative Data menu.",
                                  "Treating site can not have duplicate investigator.", ErrorMessageTypeEnum.ADMIN, 8);
                break;
            }
        }
        // No participating site duplicates playing same role must exist on the same trial
        if (hasDuplicate(getTreatingSiteOrg(spList))) {
            messages.addError(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU,
                              "Trial cannot have duplicate Treating Site.", ErrorMessageTypeEnum.ADMIN, 8);
        }
    }

    private void enforceStudyContactNullification(Ii studyProtocolIi,
            AbstractionMessageCollection messages) throws PAException {

        List<StudyContactDTO> studyContactDtos = studyContactService.getByStudyProtocol(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(studyContactDtos)) {
            for (StudyContactDTO studyContactDTO : studyContactDtos) {

                if (StructuralRoleStatusCode.NULLIFIED.getCode().equalsIgnoreCase(
                        studyContactDTO.getStatusCode().getCode())) {
                    if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studyContactDTO.getRoleCode().getCode())) {
                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Principal Investigator status has been set to nullified, "
                                                    + "Please select another Principal Investigator", 1);
                    }
                    if (StudyContactRoleCode.CENTRAL_CONTACT.getCode().equalsIgnoreCase(
                            studyContactDTO.getRoleCode().getCode())) {
                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Central Contact status has been set to nullified, "
                                                    + "Please select another Central contact", 1);
                    }
                    if (StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studyContactDTO.getRoleCode().getCode())) {

                       messages.addWarning(SELECT_TRIAL_DETAILS,
                                "Responsible Party Study Principal Investigator status has been set to nullified, "
                                        + "Please select another Responsible Party Study Principal Investigator", 1);
                    }
                    if (StudyContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studyContactDTO.getRoleCode().getCode())) {

                       messages.addWarning(SELECT_TRIAL_DETAILS,
                                "Responsible Party Sponsor-Investigator status has been set to nullified, "
                                        + "Please select another Responsible Party Sponsor-Investigator", 1);
                    }
                }
            }
        }
    }

    private void enforceStudySiteNullification(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<StudySiteDTO> studySiteDtos = studySiteService.getByStudyProtocol(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(studySiteDtos)) {
            for (StudySiteDTO studySiteDTO : studySiteDtos) {
                if (StructuralRoleStatusCode.NULLIFIED.getCode().equalsIgnoreCase(
                        studySiteDTO.getStatusCode().getCode())) {

                    if (StudySiteFunctionalCode.FUNDING_SOURCE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning("Select Collaborators from Administrative Data menu.",
                                            "Funding Source status has been set to nullified, "
                                                    + "Please select another Funding Source", 9);
                    }
                    if (StudySiteFunctionalCode.AGENT_SOURCE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning("Select Collaborators from Administrative Data menu.",
                                            "Agent Source status has been set to nullified, "
                                                    + "Please select another Agent Source", 9);
                    }
                    if (StudySiteFunctionalCode.LABORATORY.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {
                        messages.addWarning("Select Collaborators from Administrative Data menu.",
                                            "Laboratory status has been set to nullified, "
                                                    + "Please select another Laboratory", 9);
                    }
                    if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Lead Organization status has been set to nullified, "
                                                    + "Please select another Lead Organization", 1);
                    }
                    if (StudySiteFunctionalCode.RESPONSIBLE_PARTY_SPONSOR.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Responsible Party Sponsor status has been set to nullified, "
                                                    + "Please select another Responsible Party Sponsor", 1);
                    }
                    if (StudySiteFunctionalCode.SPONSOR.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Sponsor status has been set to nullified, "
                                                    + "Please select another Sponsor", 1);
                    }
                    if (StudySiteFunctionalCode.TREATING_SITE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU,
                                            "Participating Site status has been set to nullified, "
                                                    + "Please select another Participating Site", 8);
                    }
                    if (StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning("Select Human Subject Safety under Regulatory"
                                                    + " Information from Administrative Data menu.",
                                            "Board status has been set to nullified, " + "Please select another Board",
                                            3);
                    }
                }
            }
        }
    }

    private void enforceStudySiteContactNullification(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<StudySiteContactDTO> studySiteContactDtos = new StudySiteContactServiceCachingDecorator(
                studySiteContactService).getByStudyProtocol(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(studySiteContactDtos)) {
            for (StudySiteContactDTO studySiteContactDTO : studySiteContactDtos) {

                if (StructuralRoleStatusCode.NULLIFIED.getCode().equalsIgnoreCase(
                        studySiteContactDTO.getStatusCode().getCode())) {

                    if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {
                       messages.addWarning(
                                "Select Contact tab under Participating Sites from Administrative Data menu.",
                                "Primary Contact status has been set to nullified, "
                                        + "Please select another Primary Contact", 8);
                    }

                    if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {
                       messages.addWarning(
                                "Select Investigators tab under Participating sites from Administrative Data menu.",
                                "Investigator status has been set to nullified, "
                                        + "Please select another Investigator", 8);
                    }

                    if (StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {

                        messages.addError(SELECT_TRIAL_DETAILS,
                                "Responsible Party Sponsor Contact status has been set to nullified, "
                                        + "Please select another Responsible Party Sponsor Contact", 
                                        ErrorMessageTypeEnum.ADMIN, 1);
                    }
                    if (StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {
                       messages.addWarning(
                                "Select Investigators tab under Participating sites from Administrative Data menu.",
                                "Sub Investigator status has been set to nullified, "
                                        + "Please select another Sub Investigator", 8);
                    }
                }
            }
        }

    }

    private void enforceDisease(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages)
            throws PAException {
        boolean ctgovxmlIndicator = false;
        Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
        List<StudyDiseaseDTO> sdDtos = studyDiseaseService.getByStudyProtocol(studyProtocolIi);
        for (StudyDiseaseDTO sdDto : sdDtos) {
            if (sdDto.getCtGovXmlIndicator() != null && sdDto.getCtGovXmlIndicator().getValue()) {
                ctgovxmlIndicator = true;
                break;
            }
        }
        if (CollectionUtils.isEmpty(sdDtos)) {
            messages.addError("Select Disease/Condition from Scientific Data Menu",
                              "A trial must have at least one disease/condition", ErrorMessageTypeEnum.SCIENTIFIC, 15);
        }
        // not a proprietary trial and the studyprotocol is set to ctgov = true
        // and there are no diseases with xml inclusion indicator set to true
        if ((ISOUtil.isBlNull(studyProtocolDTO.getProprietaryTrialIndicator()) || !BlConverter
                .convertToBoolean(studyProtocolDTO.getProprietaryTrialIndicator()))
                && (!ISOUtil.isBlNull(studyProtocolDTO.getCtgovXmlRequiredIndicator()) && BlConverter
                        .convertToBoolean(studyProtocolDTO.getCtgovXmlRequiredIndicator())) && !ctgovxmlIndicator) {
            messages.addError("Select Disease/Condition from Scientific Data Menu",
                              "Abstraction cannot be valid if trial has no diseases with ctgov xml indicator = 'yes'", 
                              ErrorMessageTypeEnum.SCIENTIFIC, 15);
        }
    }

    private void enforceTrialFunding(StudyProtocolDTO spDto, AbstractionMessageCollection messages)
            throws PAException {
        List<StudyResourcingDTO> srList = studyResourcingService.getStudyResourcingByStudyProtocol(spDto.getIdentifier());

        if (!(srList.isEmpty())) {
            for (int i = 0; i < srList.size(); i++) {
                int j = 0;
                if (srList.size() > 1 && i != 0
                        && (!Boolean.FALSE.equals(BlConverter.convertToBoolean(srList.get(j).getActiveIndicator())))
                        && (!Boolean.FALSE.equals(BlConverter.convertToBoolean(srList.get(i).getActiveIndicator())))
                        && srList.get(j).getFundingMechanismCode().getCode().toString()
                                 .equalsIgnoreCase(srList.get(i).getFundingMechanismCode().getCode().toString())
                        && srList.get(j).getNihInstitutionCode().getCode().toString()
                                 .equalsIgnoreCase(srList.get(i).getNihInstitutionCode().getCode().toString())
                        && srList.get(j).getNciDivisionProgramCode().getCode().toString()
                                 .equalsIgnoreCase(srList.get(i).getNciDivisionProgramCode().getCode().toString())
                        && srList.get(j).getSerialNumber().getValue().toString()
                                 .equalsIgnoreCase(srList.get(i).getSerialNumber().getValue().toString())) {
                    messages.addError("Select Trial Funding from Administrative Data menu.",
                                      "Trial should not have Duplicate grants.", ErrorMessageTypeEnum.ADMIN, 7);
                    if (i != srList.size()) {
                        j++;
                    }
                }
            }
        }

        
        PAException ex = studyResourcingService.validateNoException(
                Method.ABSTRACTION_VALIDATION,
                BlConverter.convertToBoolean(spDto.getNciGrant()),
                IiConverter.convertToString(spDto.getIdentifier()), null, null);
        if (ex instanceof PAValidationException) {
            PAValidationException e = (PAValidationException) ex;
            if (e.getLevel() == Level.WARN) {
                messages.addWarning(
                        "Select Trial Funding from Administrative Data menu.",
                        e.getMessage(), 7);
            } else {
                messages.addError(
                        "Select Trial Funding from Administrative Data menu.",
                        e.getMessage(), ErrorMessageTypeEnum.ADMIN, 7);
            }
        } else if (ex instanceof PAException) {
            messages.addError(
                    "Select Trial Funding from Administrative Data menu.",
                    ex.getMessage(), ErrorMessageTypeEnum.ADMIN, 7);
        }
         
       
    }

    void enforceArmGroup(Ii studyProtocolIi, StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) throws PAException {
        List<ArmDTO> dtos = armService.getByStudyProtocol(studyProtocolIi);        
        if (dtos.isEmpty()) {
            if (studyProtocolDTO.getStudyProtocolType().getValue()
                    .equalsIgnoreCase(INTERVENTIONAL_STUDY_PROTOCOL)) {
                InterventionalStudyProtocolDTO ispDTO = studyProtocolService
                        .getInterventionalStudyProtocol(studyProtocolIi);
                final Integer arms = ispDTO.getNumberOfInterventionGroups().getValue();
                if (!(Integer.valueOf(1).equals(arms))) {
                    messages.addError(SELECT_ARM_UNDER_SCIENTIFIC_DATA_MENU,
                            NO_ARM_EXISTS_FOR_THE_TRIAL,
                            ErrorMessageTypeEnum.SCIENTIFIC, 19);
                } else {
                    messages.addWarning(
                            SELECT_ARM_UNDER_SCIENTIFIC_DATA_MENU,
                            NO_ARM_EXISTS_FOR_THE_TRIAL, 19);
                }
            } else if (studyProtocolDTO.getStudyProtocolType().getValue()
                    .equalsIgnoreCase(NON_INTERVENTIONAL_STUDY_PROTOCOL)) {
                NonInterventionalStudyProtocolDTO ospDTO = studyProtocolService
                        .getNonInterventionalStudyProtocol(studyProtocolIi);
                final Integer groups = ospDTO.getNumberOfGroups().getValue();
                final int interventions = getNumberOfInterventions(studyProtocolIi);
                if (!(Integer.valueOf(1).equals(groups) && interventions <= 1)) {
                    messages.addError(
                            SELECT_GROUPS_COHORTS_HINT,
                            NO_GROUPS_COHORTS_EXISTS_FOR_THE_TRIAL,
                            ErrorMessageTypeEnum.SCIENTIFIC, 19);
                } else {
                    messages.addWarning(
                            SELECT_GROUPS_COHORTS_HINT,
                            NO_GROUPS_COHORTS_EXISTS_FOR_THE_TRIAL, 19);
                }
            }
        } else {
            for (ArmDTO dto : dtos) {
                if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase(INTERVENTIONAL_STUDY_PROTOCOL)
                        && (dto.getTypeCode() == null || StringUtils.isEmpty(dto.getTypeCode().getCode()))) {
                    messages.addError("Select Arms from the Scientific Data menu and specify an Arm Type.",
                            ARM_TYPE_REQUIRED + dto.getName().getValue(), ErrorMessageTypeEnum.SCIENTIFIC, 19);
                }
                if (PAUtil.isGreaterThan(dto.getName(), PAAttributeMaxLen.ARM_NAME)) {
                    messages.addError("Select Arm/Group under Scientific Data menu.",
                            dto.getName().getValue() + "  must not be more than 62 characters  ",
                            ErrorMessageTypeEnum.SCIENTIFIC, 19);
                }
            }
        }
    }
    
    private void enforceTrialStatusTransitionsValidation(
            StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) throws PAException {
        if (studyOverallStatusService.statusHistoryHasErrors(studyProtocolDTO
                .getIdentifier())) {
            messages.addError(SELECT_TRIAL_STATUS_HISTORY,
                    "Trial status transition errors were found.",
                    ErrorMessageTypeEnum.ADMIN, 6);
        }
        if (studyOverallStatusService.statusHistoryHasWarnings(studyProtocolDTO
                .getIdentifier())) {
            messages.addWarning(SELECT_TRIAL_STATUS_HISTORY,
                    "Trial status transition warnings were found.", 6);
        }
    }

    void enforceTrialStatus(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages) // NOPMD
            throws PAException {
        StudyOverallStatusDTO sos = studyOverallStatusService.getCurrentByStudyProtocol(studyProtocolDTO
            .getIdentifier());
        if (sos == null) {
            messages.addError(SELECT_TRIAL_STATUS,
                              "No Trial Status exists for the trial.", ErrorMessageTypeEnum.ADMIN, 6);
        }
        if (studyProtocolDTO.getStartDate() == null || studyProtocolDTO.getStartDate().getValue() == null) {
            messages.addError(SELECT_TRIAL_STATUS,
                              "StartDate must be Entered.", ErrorMessageTypeEnum.ADMIN, 6);
        }
        if (studyProtocolDTO.getStartDateTypeCode() == null
                || studyProtocolDTO.getStartDateTypeCode().getCode() == null) {
            messages.addError(SELECT_TRIAL_STATUS,
                              "StartDateType must be Entered.", ErrorMessageTypeEnum.ADMIN, 6);
        }
        // don't validate primary completion date if it is non interventional
        // trial
        // and CTGovXmlRequired is false.
        if (PAUtil.isPrimaryCompletionDateRequired(studyProtocolDTO)) {
            if (ISOUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())
                    && ActualAnticipatedTypeCode.NA != CdConverter
                            .convertCdToEnum(ActualAnticipatedTypeCode.class,
                                    studyProtocolDTO
                                            .getPrimaryCompletionDateTypeCode())) {
                messages.addError(SELECT_TRIAL_STATUS,
                        "PrimaryCompletionDate must be Entered.",
                        ErrorMessageTypeEnum.ADMIN, 6);
            }
            if (ISOUtil.isCdNull(studyProtocolDTO
                    .getPrimaryCompletionDateTypeCode())) {
                messages.addError(SELECT_TRIAL_STATUS,
                        "PrimaryCompletionDateType must be Entered.",
                        ErrorMessageTypeEnum.ADMIN, 6);
            }
        }     
        if (ActualAnticipatedTypeCode.NA == CdConverter.convertCdToEnum(
                ActualAnticipatedTypeCode.class,
                studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                && StringUtils.isBlank(paServiceUtil.getCtepOrDcpId(
                        IiConverter.convertToLong(studyProtocolDTO
                                .getIdentifier()),
                        PAConstants.DCP_IDENTIFIER_TYPE))) {
            messages.addError(
                    SELECT_TRIAL_STATUS,
                    "Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'.",
                    ErrorMessageTypeEnum.ADMIN, 6);
        }
        if (ActualAnticipatedTypeCode.NA == CdConverter.convertCdToEnum(
                ActualAnticipatedTypeCode.class,
                studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                && !ISOUtil.isTsNull(studyProtocolDTO
                        .getPrimaryCompletionDate())) {
            messages.addError(SELECT_TRIAL_STATUS,
                    "When the Primary Completion Date Type is set to 'N/A', "
                            + "the Primary Completion Date must be null.",
                    ErrorMessageTypeEnum.ADMIN, 6);
        }
        if (sos != null) {
            for (String error : studyOverallStatusService
                    .validateTrialStatusAndDates(studyProtocolDTO, sos)) {
                messages.addError(SELECT_TRIAL_STATUS, error,
                        ErrorMessageTypeEnum.ADMIN, 6);
            }
        }
        enforceTrialStatusTransitionsValidation(studyProtocolDTO, messages);
    }

    private void enforceTrialINDIDE(StudyProtocolDTO studyProtocolDto, AbstractionMessageCollection messages)
            throws PAException {
        List<StudyIndldeDTO> siList = studyIndldeService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        Boolean ctGovIndicator = BlConverter.convertToBoolean(studyProtocolDto.getCtgovXmlRequiredIndicator());
        if (!(siList.isEmpty()) && BooleanUtils.isTrue(ctGovIndicator)) {
            checkDuplicateINDIDE(siList, messages);
            // if IND is is there for Trial Oversight Authority Country =USA
            // then Trial Oversight Authority Organization Name shld be FDA if not throw err
            // get the country and check if its usa if so then check if Org name is FDA if not throw err
            if (paServiceUtil.containsNonExemptInds(siList)) {
                StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = studyRegulatoryAuthorityService
                    .getCurrentByStudyProtocol(studyProtocolDto.getIdentifier());
                if (sraFromDatabaseDTO != null) {
                    Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
                    RegulatoryAuthority regAuth = regulatoryInformationService.get(sraId);
                    if (!("USA".equals(regAuth.getCountry().getAlpha3()) && "Food and Drug Administration"
                        .equalsIgnoreCase(regAuth.getAuthorityName()))) {
                        messages.addError("Select Regulatory under Regulatory Information from Administrative "
                                                  + "Data menu.",
                                          "For IND protocols, Oversight Authorities  must include United States: "
                                                  + "Food and Drug Administration.", ErrorMessageTypeEnum.ADMIN, 3);
                    }
                }
                if (isCorrelationRuleRequired(studyProtocolDto)) {
                    messages.addError("Select Regulatory under Regulatory Information from Administrative "
                            + "Data menu.",
                            "FDA Regulated Intervention Indicator Should be Yes to add Trial IND IDE records.", 
                            ErrorMessageTypeEnum.ADMIN, 3);
                }
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @return
     */
    private boolean isCorrelationRuleRequired(StudyProtocolDTO studyProtocolDTO) {
        Boolean ctGovIndicator = BlConverter.convertToBoolean(studyProtocolDTO.getCtgovXmlRequiredIndicator());
        return BooleanUtils.isTrue(ctGovIndicator) && (studyProtocolDTO.getIdentifier() != null
                && studyProtocolDTO.getFdaRegulatedIndicator() != null)
                && (studyProtocolDTO.getFdaRegulatedIndicator().getValue() != null)
                && (!Boolean.valueOf(studyProtocolDTO.getFdaRegulatedIndicator().getValue()));
    }

    private void checkDuplicateINDIDE(List<StudyIndldeDTO> siList, AbstractionMessageCollection messages) {
        for (int i = 0; i < siList.size(); i++) {
            int j = 0;
            if (siList.size() > 1
                    && i != 0
                    && siList.get(j).getGrantorCode().getCode().toString()
                        .equalsIgnoreCase(siList.get(i).getGrantorCode().getCode().toString())
                    && siList.get(j).getHolderTypeCode().getCode().toString()
                        .equalsIgnoreCase(siList.get(i).getHolderTypeCode().getCode().toString())
                    && siList.get(j).getIndldeNumber().getValue().toString()
                        .equalsIgnoreCase(siList.get(i).getIndldeNumber().getValue().toString())
                    && siList.get(j).getIndldeTypeCode().getCode().toString()
                        .equalsIgnoreCase(siList.get(i).getIndldeTypeCode().getCode().toString())) {
                messages.addError("Select Trial IND/IDE under Regulatory Information from Administrative "
                        + "Data menu.", "Trial IND/IDE should not have Duplicate values.", ErrorMessageTypeEnum.ADMIN,
                        5);
                if (i != siList.size()) {
                    j++;
                }
            }
        }
    }

    private void enforceRegulatoryInfo(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {

        List<StudyRegulatoryAuthorityDTO> sraDTOList = studyRegulatoryAuthorityService
            .getByStudyProtocol(studyProtocolIi);
        StudyRegulatoryAuthorityDTO sraDTO = null;
        if (!sraDTOList.isEmpty()) {
            sraDTO = sraDTOList.get(0);
        }
        if (sraDTO == null) {
            messages.addError("Select Regulatory under Regulatory Information" + " from Administrative Data menu.",
                              "Regulatory Information fields must be Entered.", ErrorMessageTypeEnum.ADMIN, 3);
        }
        // Display error in abstraction validation if section 801 indicator = yes,
        // delayed posting indicator is yes and trial does not include Intervention with type Device
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (YES.equalsIgnoreCase(convertBLToString(spDTO.getSection801Indicator()))
                && YES.equalsIgnoreCase(convertBLToString(spDTO.getDelayedpostingIndicator()))
                && !isDeviceFound(studyProtocolIi)) {
            messages.addError("Select Regulatory under Regulatory Information" + " from Administrative Data menu.",
                              "Delay posting indicator can only be set to \'yes\' "
                                      + " if study includes at least one intervention with type \'device\'.", 
                                      ErrorMessageTypeEnum.ADMIN, 3);
        }

    }

    private void enforceIRBInfo(StudyProtocolDTO spDto, AbstractionMessageCollection messages) throws PAException {

        Boolean reviewBoardIndicator = spDto.getReviewBoardApprovalRequiredIndicator().getValue();

        if (reviewBoardIndicator == null) {
            messages
                .addError("Select Human Subject Safety under Regulatory Information"
                                  + " from Administrative Data menu.",
                          "Review Board Approval Status is missing, Please complete Human Subject Review information.", 
                          ErrorMessageTypeEnum.ADMIN, 4);
        }
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(spDto.getIdentifier(), srDTO);

        StudyOverallStatusDTO sos = studyOverallStatusService.getCurrentByStudyProtocol(spDto.getIdentifier());
        if (sos != null && !spList.isEmpty()) {
            StudySiteDTO studySite = spList.get(0);
            if (StudyStatusCode.IN_REVIEW.getCode().equalsIgnoreCase(sos.getStatusCode().getCode())
                    && !studySite.getReviewBoardApprovalStatusCode().getCode()
                        .equals(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode())) {
                messages.addWarning("Select Human Subject Safety under Regulatory Information",
                                    "Data inconsistency: \'Submitted, pending\' value (Review Board Approval Status) "
                                            + "is only valid for the current trial status \'In-Review\'.", 4);

            }
            if (StudyStatusCode.WITHDRAWN.getCode().equalsIgnoreCase(sos.getStatusCode().getCode())
                    && !studySite.getReviewBoardApprovalStatusCode().getCode()
                        .equals(ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode())) {
                messages.addWarning("Select Human Subject Safety under Regulatory Information",
                                    "Data inconsistency: \'Submitted, denied\' value (Review Board Approval Status) is "
                                            + "only valid for the current trial status \'WithDrawn\'.", 4);
            }
        }

        // spList Empty => No Study Oversight Committee.
        // Display warning if Study is recruiting && reviewBoardindicator is false =>
        // Board Approval Status = Submission Not Required.
        StudyOverallStatusDTO sosDTO = studyOverallStatusService
                .getCurrentByStudyProtocol(spDto.getIdentifier());
        RecruitmentStatusCode recruitmentStatusCode = RecruitmentStatusCode
                .getByStatusCode(StudyStatusCode
                        .getByCode(sosDTO.getStatusCode()
                                .getCode()));
        if (spList.isEmpty() && BooleanUtils.isFalse(reviewBoardIndicator) && recruitmentStatusCode.isRecruiting()) {
            messages.addWarning("Select a different review board status",
                                "Data inconsistency. Review Board Approval Status cannot be 'Not required'"
                                        + " for an interventional study that is recruiting patients", 3);
        }
    }

    /**
     * Enforce the recruitment status rules.
     * @param studyProtocolIi The study protocil Ii
     * @param messages The messages object collecting errors and warnings
     * @throws PAException if an error occurs.
     */
    void enforceRecruitmentStatus(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {

        StudyOverallStatusDTO rsDto = studyOverallStatusService.getCurrentByStudyProtocol(studyProtocolIi);
        RecruitmentStatusCode recruitmentStatus = RecruitmentStatusCode
                .getByStatusCode(StudyStatusCode
                        .getByCode(rsDto.getStatusCode()
                                .getCode()));        
        boolean studySiteRecruiting = isStudySiteRecruiting(studyProtocolIi);

        if (recruitmentStatus.isRecruiting() && !studySiteRecruiting) {
            String errorMsg = "Data inconsistency: At least one location needs to be recruiting if the overall "
                    + "recruitment status is '%s'";
            messages.addError(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU,
                              String.format(errorMsg, recruitmentStatus.getCode()), ErrorMessageTypeEnum.ADMIN, 8);
        }
        boolean isInReviewOrApproved = recruitmentStatus == RecruitmentStatusCode.IN_REVIEW
                || recruitmentStatus == RecruitmentStatusCode.APPROVED;

        if (isInReviewOrApproved && studySiteRecruiting) {
            String errorMsg = "Data inconsistency. No site can recruit patients if the overall"
                    + " recruitment status is '%s'";
            messages.addWarning(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU,
                                String.format(errorMsg, recruitmentStatus.getCode()), 8);
        }

        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);

        if (isInReviewOrApproved && studyProtocolDTO.getStartDate().getValue().before(new Date())) {
            String errorMsg = "Data inconsistency. Study Start Date cannot be in the past if the overall "
                    + "recruitment status is '%s'";
            messages.addWarning("Select study start date.", String.format(errorMsg, recruitmentStatus.getCode()), 6);
        }
    }

    /**
     * Test if a site is recruiting.
     * @param studyProtocolIi The study protocil Ii
     * @return true if a site of the given study is recruiting.
     * @throws PAException if an error occurs.
     */
    boolean isStudySiteRecruiting(Ii studyProtocolIi) throws PAException {
        boolean studySiteRecruiting = false;
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> studySites = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);

        for (StudySiteDTO studySite : studySites) {
            StudySiteAccrualStatusDTO lastestStudySiteAccrualStatusDTO = studySiteAccrualStatusService
                .getCurrentStudySiteAccrualStatusByStudySite(studySite.getIdentifier());

            if (lastestStudySiteAccrualStatusDTO != null) {
                String latestStatusCode = lastestStudySiteAccrualStatusDTO.getStatusCode().getCode();
                RecruitmentStatusCode accrualStatus = RecruitmentStatusCode.getByCode(latestStatusCode);
                if (accrualStatus.isRecruiting()) {
                    return true;
                }
            }
        }
        return studySiteRecruiting;
    }

    private void enforceTreatingSite(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);
        if (spList == null || spList.isEmpty()) {
            messages.addError(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU,
                              "No Participating Sites exists for the trial.", ErrorMessageTypeEnum.ADMIN, 8);
            return;
        }
        // check if central contact exits for the study
        boolean centralContactDefined = isCentralContactDefined(studyProtocolIi);

        for (StudySiteDTO spartDto : spList) {
            List<StudySiteContactDTO> spContactDtos = new StudySiteContactServiceCachingDecorator(
                    studySiteContactService).getByStudySite(spartDto
                    .getIdentifier());
            boolean piFound = false;
            boolean contactFound = false;
            for (StudySiteContactDTO spContactDto : spContactDtos) {
                String contactRoleCode = spContactDto.getRoleCode().getCode();
                if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)
                        || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)) {
                    piFound = true;
                } else if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equalsIgnoreCase(contactRoleCode)) {
                    contactFound = true;
                }

            }
            Organization orgBo = getPoOrg(spartDto);
            if (!piFound) {
                // Error Message ID Does Not Match Participating Site PO ID#
                messages.addError(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU, "Participating site # "
                        + orgBo.getIdentifier() + " Must have an Investigator", ErrorMessageTypeEnum.ADMIN, 8);

            }
            // No investigator duplicates must exist on the same treating site for the same trial.
            if (piFound && hasDuplicate(getPIForTreatingSite(spContactDtos))) {
                messages.addError("Select Participating Sites from " + " Administrative Data menu.",
                                  "Treating site can not have duplicate investigator.", ErrorMessageTypeEnum.ADMIN, 8);
                break;
            }
            // abstraction validation rule for participating site contact and
            // central contact
            if (!contactFound && !centralContactDefined) {
                messages.addError(
                        "Either select General Trial Details from Administrative Data menu"
                                + " to provide a Central Contact for the trial or select Participating Sites "
                                + "from Administrative Data menu to"
                                + " provide Participating Site Contact information.",
                        "Either Participating Site Contact information"
                                + " or the trial's Central Contact information is mandatory. "
                                + "Complete either Central Contact for the trial"
                                + " or Participating Site Contact information for each site.",
                        ErrorMessageTypeEnum.ADMIN, 1);
            }

        }
        // No participating site duplicates playing same role must exist on the same trial
        if (hasDuplicate(getTreatingSiteOrg(spList))) {
            messages.addError(SELECT_PARTICIPATING_SITES_FROM_ADMINISTRATIVE_DATA_MENU,
                              "Trial cannot have duplicate Treating Site.", ErrorMessageTypeEnum.ADMIN, 8);
        }

    }

    private boolean isCentralContactDefined(Ii studyProtocolIi) throws PAException {
        boolean ccDefined = false;
        List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolIi);
        if (scDtos != null && !scDtos.isEmpty()) {
            for (StudyContactDTO studyContactDTO : scDtos) {
                if (StudyContactRoleCode.CENTRAL_CONTACT.getCode().equalsIgnoreCase(
                        studyContactDTO.getRoleCode().getCode())) {
                    ccDefined = true;
                }
            }
        }
        return ccDefined;
    }

    private void enforceCollaborator(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
        for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudySiteDTO searchCode = new StudySiteDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, criteriaList);
        List<String> newspList = new ArrayList<String>();
        for (StudySiteDTO spdto : spList) {
            newspList.add(spdto.getFunctionalCode().getCode() + spdto.getResearchOrganizationIi().getExtension());
        }
        if (hasDuplicate(newspList)) {
            messages.addError("Select Collaborators from Administrative Data menu.",
                              "Trial can not have a duplicate collaborator playing the same role.", 
                              ErrorMessageTypeEnum.ADMIN, 9);
        }
    }

    /**
     * @param spartDto
     * @return
     * @throws PAException
     */
    private Organization getPoOrg(StudySiteDTO spartDto) throws PAException {
        return correlationUtils.getPAOrganizationByIi(spartDto.getHealthcareFacilityIi());
    }

    private List<String> getPIForTreatingSite(List<StudySiteContactDTO> spContactDtos) {
        List<String> piList = new ArrayList<String>();
        for (StudySiteContactDTO dto : spContactDtos) {
            if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(dto.getRoleCode().getCode())
                    || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode()
                        .equalsIgnoreCase(dto.getRoleCode().getCode())) {
                if (dto.getClinicalResearchStaffIi() != null) {
                    piList.add(dto.getClinicalResearchStaffIi().getExtension());
                }
                if (dto.getHealthCareProviderIi() != null) {
                    piList.add(dto.getHealthCareProviderIi().getExtension());
                }
            }
        }
        return piList;
    }

    private List<String> getTreatingSiteOrg(List<StudySiteDTO> spartList) {
        List<String> treatingSiteList = new ArrayList<String>();
        for (StudySiteDTO spdto : spartList) {
            treatingSiteList.add(spdto.getHealthcareFacilityIi().getExtension());
        }
        return treatingSiteList;
    }

    private <T> boolean hasDuplicate(Collection<T> list) {
        Set<T> set = new HashSet<T>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        boolean dup = false;
        for (T each : list) {
            if (!set.add(each)) {
                dup = true;
            }
        }
        return dup;
    }

    private void enforceInterventions(StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) throws PAException {
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolDTO.getIdentifier());
        boolean interventionsList = false;
        boolean isNonInterventional = studyProtocolDTO.getStudyProtocolType() != null
                && NON_INTERVENTIONAL_STUDY_PROTOCOL
                        .equalsIgnoreCase(studyProtocolDTO
                                .getStudyProtocolType().getValue());
        for (PlannedActivityDTO pa : paList) {
            if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                .convertCdToString(pa.getCategoryCode())))
                    || ActivityCategoryCode.SUBSTANCE_ADMINISTRATION.equals(ActivityCategoryCode.getByCode(CdConverter
                        .convertCdToString(pa.getCategoryCode())))
                    || ActivityCategoryCode.PLANNED_PROCEDURE.equals(ActivityCategoryCode.getByCode(CdConverter
                        .convertCdToString(pa.getCategoryCode())))) {
                interventionsList = true;
                // validation rules for inactive interventions
                InterventionDTO iDto = interventionService.get(pa.getInterventionIdentifier());
                if (ActiveInactiveCode.INACTIVE.getCode().equalsIgnoreCase(iDto.getStatusCode().getCode())) {
                    messages.addWarning("Select Interventions from Scientific Data menu.", "Intervention '"
                            + iDto.getName().getValue() + "' status has been set to inactive"
                            + ", Please select another Intervention.", 18);
                }

            }
        }
        if (!interventionsList && !isNonInterventional) {
            messages.addError(
                    "Select Interventions from Scientific Data menu.",
                    "No Interventions exists for the trial.",
                    ErrorMessageTypeEnum.SCIENTIFIC, 18);
        } else if (!interventionsList && isNonInterventional) {
            messages.addWarning(
                    "Select Interventions from Scientific Data menu.",
                    "Warning: There are no interventions in this study.", 18);
        } 
    }

    private void enforceOutcomeMeasure(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        List<StudyOutcomeMeasureDTO> somList = studyOutcomeMeasureService.getByStudyProtocol(studyProtocolIi);
        boolean isPrimayFound = false;
        for (StudyOutcomeMeasureDTO somDto : somList) {
            if (!ISOUtil.isCdNull(somDto.getTypeCode()) 
                    && somDto.getTypeCode().getCode().equalsIgnoreCase(OutcomeMeasureTypeCode.PRIMARY.getCode())) {
                isPrimayFound = true;
                break;
            }
        }
        if (!isPrimayFound) {
            messages.addError(
                    "Select Outcome Measure from Interventional/Non-Interventional under Scientific Data menu.",
                    "Trial must include at least one PRIMARY outcome measure.",
                    ErrorMessageTypeEnum.SCIENTIFIC, 13);
        }
    }

    private void enforceGeneralTrialDetails(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages) {
        if (!PAUtil.checkAssignedIdentifierExists(studyProtocolDTO)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "NCI Trial Identifier must be Entered", ErrorMessageTypeEnum.ADMIN, 1);
        }
        if (studyProtocolDTO.getOfficialTitle().getValue() == null) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Official Title must be Entered", ErrorMessageTypeEnum.ADMIN, 1);
        } else if (PAUtil.isGreaterThan(studyProtocolDTO.getOfficialTitle(), PAAttributeMaxLen.LEN_4000)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Official Title cannot be more than 4000 chracters ", ErrorMessageTypeEnum.ADMIN, 1);
        }
        if (PAUtil.isGreaterThan(studyProtocolDTO.getAcronym(), PAAttributeMaxLen.ACRONYM)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Acronym must not be more than 14 characters ", ErrorMessageTypeEnum.ADMIN, 1);
        }
        if (PAUtil.isGreaterThan(studyProtocolDTO.getScientificDescription(), PAAttributeMaxLen.LEN_32000)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Detailed Description must not be more than 32000 characters ", 
                              ErrorMessageTypeEnum.ADMIN, 1);
        }
        if (PAUtil.isGreaterThan(studyProtocolDTO.getKeywordText(), PAAttributeMaxLen.KEYWORD)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Keywords must not be more than 4000 characters ", ErrorMessageTypeEnum.ADMIN, 1);
        }
    }

    private void enforceTrialDescriptionDetails(StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) throws PAException {
        if (studyProtocolDTO.getPublicTitle().getValue() == null) {
            messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Title must be Entered", ErrorMessageTypeEnum.SCIENTIFIC,
                    11);
        } else {
            if (!PAUtil.isWithinRange(studyProtocolDTO.getPublicTitle(), PAAttributeMaxLen.LEN_18,
                                      PAAttributeMaxLen.LEN_300)) {
                messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Title must be between 18 and 300 characters ", 
                        ErrorMessageTypeEnum.SCIENTIFIC, 11);
            }
            if  (!hasUniqueBriefTitle(studyProtocolDTO)) {
                messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Title must be unique.", 
                        ErrorMessageTypeEnum.SCIENTIFIC, 11);
            }
        }
        if (studyProtocolDTO.getPublicDescription().getValue() == null) {
            messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Summary must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 11);
        } else {
            if (PAUtil.isGreaterThan(studyProtocolDTO.getPublicDescription(), PAAttributeMaxLen.LEN_5000)) {
                messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Summary must not be more than 5000 characters ", 
                        ErrorMessageTypeEnum.SCIENTIFIC, 11);
            }
        }
    }

    private boolean hasUniqueBriefTitle(StudyProtocolDTO studyProtocolDTO) throws PAException {
        List<Long> protocolIdList = studyProtocolService.getNonRejectedByPublicTitle(StConverter
                .convertToString(studyProtocolDTO.getPublicTitle()));
        for (Long id : protocolIdList) {
            if (id != IiConverter.convertToLong(studyProtocolDTO.getIdentifier()).longValue()) {
               return false;
            }
        }
        return true;
    }
    private void enforceNCISpecificInfo(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages) {
        if (studyProtocolDTO.getAccrualReportingMethodCode().getCode() == null) {
            messages.addError("Select NCI Specific Information from Administrative Data menu.",
                              "Reporting Data Set Method must be Entered", ErrorMessageTypeEnum.ADMIN, 2);
        }
    }

    private void enforceDocument(String protocolDoc, String irbDoc, AbstractionMessageCollection messages) {
        if (protocolDoc == null) {
            messages.addError("Select Trial Related Documents from Administrative Data menu.",
                              "Protocol_Document is required", ErrorMessageTypeEnum.ADMIN, 10);
        }
        if (irbDoc == null) {
            messages.addError("Select Trial Related Documents from Administrative Data menu.",
                              "IRB_Approval_Document is required", ErrorMessageTypeEnum.ADMIN, 10);
        }
    }

    private void enforeNonInterventional(NonInterventionalStudyProtocolDTO nonIntDTO, //NOPMD
            AbstractionMessageCollection messages) {
        if (nonIntDTO.getPrimaryPurposeCode().getCode() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Primary Purpose must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (ISOUtil.isCdNull(nonIntDTO.getStudySubtypeCode())) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Non-interventional Study Type must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (nonIntDTO.getStudyModelCode().getCode() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Study Model must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        } else {
            if (nonIntDTO.getStudyModelCode().getCode().equalsIgnoreCase("Other")
                    && nonIntDTO.getStudyModelOtherText() == null) {
                messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "If Study Model is 'Other', please describe", 
                        ErrorMessageTypeEnum.SCIENTIFIC, 12);
            }
        }

        if (nonIntDTO.getTimePerspectiveCode().getCode() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Time Perspective must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        } else {
            if (nonIntDTO.getTimePerspectiveCode().getCode().equalsIgnoreCase("Other")
                    && nonIntDTO.getTimePerspectiveOtherText() == null) {
                messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG,
                        "If Time Perspective is 'Other', " + "please describe",
                        ErrorMessageTypeEnum.SCIENTIFIC, 12);
            }
        }  
        if (nonIntDTO.getNumberOfGroups().getValue() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Number of Groups/Cohorts must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (nonIntDTO.getTargetAccrualNumber().getLow().getValue() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Target Enrollment must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
    }

    private void enforceInterventional(InterventionalStudyProtocolDTO ispDTO, AbstractionMessageCollection messages) {
        if (ispDTO.getPrimaryPurposeCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Primary Purpose must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }

        if (ispDTO.getPhaseCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Trial Phase must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (ispDTO.getDesignConfigurationCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Intervention Model must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (ispDTO.getNumberOfInterventionGroups().getValue() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Number of Arms must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (ispDTO.getBlindingSchemaCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Masking must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (ispDTO.getAllocationCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Allocation must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
        if (ispDTO.getTargetAccrualNumber().getLow().getValue() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Target Enrollment must be Entered", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 12);
        }
    }
    
    private void enforceArmOrGroupAssociationToIntervention(
            StudyProtocolDTO sp, AbstractionMessageCollection messages)
            throws PAException {
        if (sp.getStudyProtocolType().getValue()
                .equalsIgnoreCase(INTERVENTIONAL_STUDY_PROTOCOL)) {
            enforceArmsInterventionAssociations(sp, messages);
        } else {
            enforceGroupsInterventionsAssociations(sp, messages);
        }
    }

    @SuppressWarnings("PMD.NPathComplexity")
    private void enforceGroupsInterventionsAssociations(StudyProtocolDTO sp,
            AbstractionMessageCollection messages) throws PAException {
        Ii studyProtocolIi = sp.getIdentifier();
        final String menuName = "Groups/Cohorts";
        NonInterventionalStudyProtocolDTO ospDTO = studyProtocolService
                .getNonInterventionalStudyProtocol(studyProtocolIi);
        final Integer groups = ospDTO.getNumberOfGroups().getValue();
        final int interventions = getNumberOfInterventions(studyProtocolIi);
        List<ArmDTO> arms = armService.getByStudyProtocol(studyProtocolIi);
        
        if ((groups != null && groups < 2) || interventions == 0 || CollectionUtils.isEmpty(arms)) {
            return;
        }
        
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
        HashMap<String, String> intervention = new HashMap<String, String>();
        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                List<ArmDTO> armDtos = armService.getByPlannedActivity(pa.getIdentifier());
                if (armDtos == null || armDtos.isEmpty()) {                    
                    messages.addError(
                            SELECT
                                    + menuName
                                    + " from Scientific Data menu and associated Intervention.", 
                            "Intervention(s) must be associated with at least"
                                    + " one Group/Cohort",
                            ErrorMessageTypeEnum.SCIENTIFIC, 19);
                }
                for (ArmDTO armDTO : armDtos) {
                    intervention.put(armDTO.getName().getValue(), armDTO.getName().getValue());
                }
            }
        }
    }

    @SuppressWarnings("PMD.NPathComplexity")
    private void enforceArmsInterventionAssociations(StudyProtocolDTO sp, AbstractionMessageCollection messages)
            throws PAException {
        Ii studyProtocolIi = sp.getIdentifier();       
        InterventionalStudyProtocolDTO ispDTO = (InterventionalStudyProtocolDTO) sp;
        final Integer numberOfArms = ispDTO.getNumberOfInterventionGroups().getValue();        
        final String menuName = "Arm";
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
        List<ArmDTO> arms = armService.getByStudyProtocol(studyProtocolIi);
        HashMap<String, String> intervention = new HashMap<String, String>();
        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                List<ArmDTO> armDtos = armService.getByPlannedActivity(pa.getIdentifier());
                if ((armDtos == null || armDtos.isEmpty())
                        && !(Integer.valueOf(1).equals(numberOfArms) && arms.isEmpty())) {
                    messages.addError(
                            SELECT
                                    + menuName
                                    + " from Scientific Data menu and associated Intervention.",
                            "Every intervention in interventional trial must be associated with at least"
                                    + " one arm in interventional trial",
                            ErrorMessageTypeEnum.SCIENTIFIC, 19);
                }
                for (ArmDTO armDTO : armDtos) {
                    intervention.put(armDTO.getName().getValue(), armDTO.getName().getValue());
                }
            }
        }        
        for (ArmDTO armDTO : arms) {
            if (ArmTypeCode.NO_INTERVENTION.getCode().equals(armDTO.getTypeCode().getCode())) {
                continue;
            }
            if (!intervention.containsKey(armDTO.getName().getValue())) {
                messages.addError(
                        SELECT
                                + menuName
                                + " from Scientific Data menu and associated Interventional.",
                        "Arm " + armDTO.getName().getValue()
                                + " does not have any Intervention associated",
                        ErrorMessageTypeEnum.SCIENTIFIC, 19);
            }
        }
    }

    void enforceEligibility(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = plannedActivityService
            .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolDTO.getIdentifier());

        if (paECs == null || paECs.isEmpty()) {
            messages.addError("Select Eligibilty Criteria from specific Interventional/Non-Interventional"
                    + " under Scientific Data menu.", " Does not have any Eligibilty Criteria", 
                    ErrorMessageTypeEnum.SCIENTIFIC, 14);
            return;
        }
        boolean otherCriteriaExist = false;
        for (PlannedEligibilityCriterionDTO paEC : paECs) {
            if (ActivityCategoryCode.OTHER.getCode().equals(paEC.getCategoryCode().getCode())) {
                otherCriteriaExist = true;
            }
        } // for loop
        if (!otherCriteriaExist) {
            messages.addError(
                    "Select Eligibilty Criteria from specific Interventional/Non-Interventional under Scientific "
                            + "Data menu and Add Other Criteria.",
                    " Minimum one Other criteria must be added ",
                    ErrorMessageTypeEnum.SCIENTIFIC, 14);

        }
        if (studyProtocolDTO instanceof NonInterventionalStudyProtocolDTO) {
            NonInterventionalStudyProtocolDTO nonIntDTO = (NonInterventionalStudyProtocolDTO) studyProtocolDTO;
            if (ISOUtil.isCdNull(nonIntDTO.getSamplingMethodCode())) {
                messages.addError(
                        "Select Eligibilty Criteria from specific Interventional/Non-Interventional under Scientific "
                                + "Data menu.",
                        " Sampling Method is required ",
                        ErrorMessageTypeEnum.SCIENTIFIC, 14);
            }
            if (ISOUtil.isStNull(nonIntDTO.getStudyPopulationDescription())) {
                messages.addError(
                        "Select Eligibilty Criteria from specific Interventional/Non-Interventional under Scientific "
                                + "Data menu.",
                        " Study Population Description is required ",
                        ErrorMessageTypeEnum.SCIENTIFIC, 14);
            }
        }
    }

    private void enforceIdentifierLength(StudyProtocolDTO spDto, AbstractionMessageCollection messages)
            throws PAException {
        List<StudySiteDTO> sParts = new ArrayList<StudySiteDTO>();
        StudySiteDTO spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        sParts.add(spartDTO);
        spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        sParts.add(spartDTO);
        List<StudySiteDTO> dtos = studySiteService.getByStudyProtocol(spDto.getIdentifier(), sParts);
        for (StudySiteDTO dto : dtos) {
            if (PAUtil.isGreaterThan(dto.getLocalStudyProtocolIdentifier(), PAAttributeMaxLen.LEN_30)) {
                if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equals(dto.getFunctionalCode().getCode())) {
                    messages.addError(SELECT_TRIAL_DETAILS,
                                      "Lead Organization Trial Identifier  cannot be more than 30 characters", 
                                      ErrorMessageTypeEnum.ADMIN, 1);
                } else if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getCode().equals(dto.getFunctionalCode()
                                                                                            .getCode())) {
                	StructuralRole srRO = correlationUtils.getStructuralRoleByIi(dto.getResearchOrganizationIi());
                    if (srRO instanceof ResearchOrganization
                            && StringUtils.equals(PAConstants.CTGOV_ORG_NAME,
                                    ((ResearchOrganization) srRO).getOrganization()
                                            .getName())) {
                        messages.addError(SELECT_TRIAL_DETAILS,
                                "NCT Number cannot be more than 30 characters", ErrorMessageTypeEnum.ADMIN, 1);                    	
                    } else if (srRO instanceof ResearchOrganization
                            && StringUtils.equals(PAConstants.CTEP_ORG_NAME,
                                    ((ResearchOrganization) srRO).getOrganization()
                                            .getName())) {
                        messages.addError(SELECT_TRIAL_DETAILS,
                                "CTEP Number cannot be more than 30 characters", ErrorMessageTypeEnum.ADMIN, 1);  
                    } else if (srRO instanceof ResearchOrganization
                            && StringUtils.equals(PAConstants.DCP_ORG_NAME,
                                    ((ResearchOrganization) srRO).getOrganization()
                                            .getName())) {
                        messages.addError(SELECT_TRIAL_DETAILS,
                                "DCP Number cannot be more than 30 characters", ErrorMessageTypeEnum.ADMIN, 1);  
                    }
                }

            }
        }
    }

    /**
     * @param studyProtocolIi
     * @param abstractionWarnList
     * @throws PAException on err
     */
    private void enforceSummary4OrgNullification(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<StudyResourcingDTO> studyResourcingList = studyResourcingService.getSummary4ReportedResourcing(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(studyResourcingList)) {
        	for (StudyResourcingDTO studyResourcingDTO : studyResourcingList) {
        		if (!ISOUtil.isIiNull(studyResourcingDTO.getOrganizationIdentifier())) {
        			Long paOrgId = IiConverter.convertToLong(studyResourcingDTO.getOrganizationIdentifier());
        			Organization org = correlationUtils.getPAOrganizationByIi(IiConverter.convertToPaOrganizationIi(paOrgId));
        			if (org != null && EntityStatusCode.NULLIFIED.getCode().equals(org.getStatusCode().getCode())) {
        				messages.addWarning("Select NCI Specific Information from Administrative Data menu.",
        						" Data Table 4 Funding Sponsor status has been set to nullified, "
        								+ "Please select another Data Table 4 Funding Sponsor", 2);
        			}
        		}
        	}
        }
    }

    /**
     * Checks for the planned markers with the pending status.
     * @param studyProtocolIi the ii of the study protocol
     * @param abstractionWarningList the current list of warnings
     * @throws PAException on error
     */
    private void enforcePlannedMarkerStatus(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<PlannedMarkerDTO> plannedMarkers = plannedMarkerService.getByStudyProtocol(studyProtocolIi);
        for (PlannedMarkerDTO marker : plannedMarkers) {
            String statusCode = marker.getStatusCode().getCode();
            if (ActiveInactivePendingCode.getByCode(statusCode) == ActiveInactivePendingCode.PENDING) {
                messages.addWarning("At least one pending biomarker exists on the trial.", "Use Marker menu-option.",
                        17);
                break;
            }
        }

    }

    private boolean isDeviceFound(Ii studyProtocolIi) throws PAException {
        boolean found = false;
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
        for (PlannedActivityDTO pa : paList) {
            if (pa.getCategoryCode() != null
                    && ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                            .convertCdToString(pa.getCategoryCode()))) && pa.getSubcategoryCode() != null
                    && pa.getSubcategoryCode().getCode() != null
                    && InterventionTypeCode.DEVICE.getCode().equalsIgnoreCase(pa.getSubcategoryCode().getCode())) {
                found = true;
                break;
            }
        }
        return found;
    }

    private static String convertBLToString(Bl bl) {
        if (bl == null) {
            return null;
        }
        return BooleanUtils.isTrue(bl.getValue()) ? YES : NO;
    }

    /**
     * Sets the CorrelationUtils Service.
     * @param corUtils The service to set
     */
    public void setCorrelationUtils(CorrelationUtils corUtils) {
        this.correlationUtils = corUtils;
    }

    /**
     * @return the paServiceUtil
     */
    public PAServiceUtils getPaServiceUtil() {
        return paServiceUtil;
    }

    /**
     * @param paServiceUtil the paServiceUtil to set
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }

    /**
     * @param armService the armService to set
     */
    public void setArmService(ArmServiceLocal armService) {
        this.armService = armService;
    }

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }

    /**
     * @param interventionService the interventionService to set
     */
    public void setInterventionService(InterventionServiceLocal interventionService) {
        this.interventionService = interventionService;
    }

    /**
     * @param organizationCorrelationService the organizationCorrelationService to set
     */
    public void setOrganizationCorrelationService(OrganizationCorrelationServiceRemote organizationCorrelationService) {
        this.organizationCorrelationService = organizationCorrelationService;
    }

    /**
     * @param plannedActivityService the plannedActivityService to set
     */
    public void setPlannedActivityService(PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }

    /**
     * @param plannedMarkerService the plannedMarkerService to set
     */
    public void setPlannedMarkerService(PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }

    /**
     * @param regulatoryInformationService the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(RegulatoryInformationServiceLocal regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactServiceLocal studyContactService) {
        this.studyContactService = studyContactService;
    }

    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    
    /**
     * @param regulatoryAuthorityService the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(StudyRegulatoryAuthorityServiceLocal regulatoryAuthorityService) {
        this.studyRegulatoryAuthorityService = regulatoryAuthorityService;
    }

    /**
     * @param studyResourcingService the studyResourcingService to set
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResourcingService) {
        this.studyResourcingService = studyResourcingService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

}
