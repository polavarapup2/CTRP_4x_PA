package gov.nih.nci.pa.service; // NOPMD

import static gov.nih.nci.pa.service.AbstractBaseIsoService.ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SCIENTIFIC_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUPER_ABSTRACTOR_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Prop trial Management Bean for registering and updating the protocol.
 * @author Naveen Amiruddin
 * @since 05/24/2010
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@RolesAllowed({ SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE, ABSTRACTOR_ROLE,
    SCIENTIFIC_ABSTRACTOR_ROLE, SUPER_ABSTRACTOR_ROLE })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProprietaryTrialManagementBeanLocal extends AbstractTrialRegistrationBean
    implements ProprietaryTrialManagementServiceLocal {

    private static final String VALIDATION_EXCEPTION = "Validation Exception ";
    private static PAServiceUtils paServiceUtils = new PAServiceUtils();

    @Resource private SessionContext ctx;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private MailManagerServiceLocal mailManagerSerivceLocal;
    @EJB
    private DocumentWorkflowStatusServiceLocal docWrkFlowStatusService;
    @EJB
    private StudyInboxServiceLocal studyInboxServiceLocal;
    @EJB
    private DocumentServiceLocal documentService;
    @EJB
    private StudyMilestoneServicelocal studyMilestoneService;
    @EJB
    private RegistryUserServiceLocal userServiceLocal;
    @EJB
    private ProtocolQueryServiceLocal protocolQueryServiceLocal;    
    @EJB
    private StudyResourcingServiceLocal studyResourcingServiceLocal;
    
    private CSMUserUtil csmUserService = new CSMUserService();

    /**
     * update a proprietary trial.
     * @param studyProtocolDTO study protocol dto
     * @param leadOrganizationDTO lead organization dto
     * @param summary4OrganizationDTO summary 4 organization dto
     * @param leadOrganizationIdentifier lead organization identifier
     * @param nctIdentifier nct Identifier
     * @param summary4TypeCode summary 4 type code
     * @param documentDTOs list of dtos
     * @param studySiteDTOs list of study site dtos
     * @param studySiteAccrualDTOs list of study site Accrual status
     * @throws PAException on error
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @SuppressWarnings("PMD.ExcessiveParameterList")
    // CHECKSTYLE:OFF More than 7 Parameters
    public void update(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO,
            OrganizationDTO summary4OrganizationDTO, St leadOrganizationIdentifier, St nctIdentifier,
            Cd summary4TypeCode, List<DocumentDTO> documentDTOs, List<StudySiteDTO> studySiteDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs) throws PAException {
        // CHECKSTYLE:ON
        if (studyProtocolDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol DTO is null");
        }
        if (ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol DTO identifier is null");
        }
        try {
            StudyProtocolDTO spDto = studyProtocolService.getStudyProtocol(studyProtocolDTO.getIdentifier());
            
            // The following 3 later are used for change tracking (inbox).
            StudyProtocolQueryDTO originalDTO = protocolQueryServiceLocal
                    .getTrialSummaryByStudyProtocolId(IiConverter
                            .convertToLong(spDto.getIdentifier()));
            List<StudyResourcingDTO> originalSummary4 = studyResourcingServiceLocal
                    .getSummary4ReportedResourcing(studyProtocolDTO
                            .getIdentifier());
            StudySiteDTO srDTO = new StudySiteDTO();
            srDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE.getCode()));
            List<StudySiteDTO> originalSites = studySiteService
                    .getByStudyProtocol(spDto.getIdentifier(), srDTO);
            
            // remember original study site recruitment statuses because they
            // are separate from study sites.
            List<StudySiteAccrualStatusDTO> originalAccrualStatuses = new ArrayList<StudySiteAccrualStatusDTO>();
            for (StudySiteDTO site : originalSites) {
                originalAccrualStatuses.add(studySiteAccrualStatusService
                        .getCurrentStudySiteAccrualStatusByStudySite(site
                                .getIdentifier()));
            }
            
            final Bl proprietaryTrialIndicator = spDto.getProprietaryTrialIndicator();
            if (Boolean.FALSE.equals(proprietaryTrialIndicator.getValue())) {
                throw new PAException(
                        VALIDATION_EXCEPTION
                                + "Only abbreviated trials can be updated by this operation");
            }
            studyProtocolDTO.setProprietaryTrialIndicator(proprietaryTrialIndicator);
            validate(studyProtocolDTO, leadOrganizationDTO, leadOrganizationIdentifier, nctIdentifier, documentDTOs,
                    studySiteDTOs, studySiteAccrualDTOs);
            /*StudyResourcingDTO summary4StudyResourcingDTO = new StudyResourcingDTO();
            summary4StudyResourcingDTO.setTypeCode(summary4TypeCode);
            TrialRegistrationValidator validator = new TrialRegistrationValidator(ctx);
            validator.validateSummary4SponsorAndCategory(studyProtocolDTO, summary4OrganizationDTO,
                                                            summary4StudyResourcingDTO);*/
            // the validation are done, proceed to update
            Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
            spDto.setOfficialTitle(studyProtocolDTO.getOfficialTitle());
            spDto.setPrimaryPurposeCode(studyProtocolDTO.getPrimaryPurposeCode());
            setPrimaryPurposeCode(studyProtocolDTO, spDto);
            spDto.setPhaseCode(studyProtocolDTO.getPhaseCode());
            setPhaseAdditionalQualifier(studyProtocolDTO, spDto);
            setNonInterventionalTrialFields(studyProtocolDTO, spDto);
            if (spDto instanceof NonInterventionalStudyProtocolDTO) {
                studyProtocolService.updateNonInterventionalStudyProtocol((NonInterventionalStudyProtocolDTO) spDto);
            } else {
                studyProtocolService.updateStudyProtocol(spDto);
            }
            
            if (leadOrganizationDTO != null
                    && !ISOUtil.isStNull(leadOrganizationIdentifier)) {
                updateLeadOrganization(
                        paServiceUtils.findOrCreateEntity(leadOrganizationDTO),
                        leadOrganizationIdentifier, studyProtocolIi);
            }
            
            updateNctIdentifier(nctIdentifier, studyProtocolIi);
            /*StudyResourcingDTO srDto = null;
            if (!ISOUtil.isCdNull(summary4TypeCode)) {
                srDto = new StudyResourcingDTO();
                srDto.setTypeCode(summary4TypeCode);
            }
            paServiceUtils.manageSummaryFour(studyProtocolIi,
                    (summary4OrganizationDTO != null ? paServiceUtils.findOrCreateEntity(summary4OrganizationDTO)
                            : null), srDto);*/
            for (StudySiteDTO ssDto : studySiteDTOs) {
                StudySiteDTO studySiteDto = studySiteService.get(ssDto.getIdentifier());
                studySiteDto.setProgramCodeText(ssDto.getProgramCodeText());
                studySiteDto.setLocalStudyProtocolIdentifier(ssDto.getLocalStudyProtocolIdentifier());
                studySiteDto.setAccrualDateRange(ssDto.getAccrualDateRange());
                studySiteService.update(studySiteDto);
            }

            for (StudySiteAccrualStatusDTO ssasDto : studySiteAccrualDTOs) {
                ssasDto.setIdentifier(null);
                studySiteAccrualStatusService.createStudySiteAccrualStatus(ssasDto);
            }
            List<DocumentDTO> existingDocs = documentService
                    .getDocumentsByStudyProtocol(studyProtocolDTO
                            .getIdentifier());
            for (DocumentDTO doc : existingDocs) {
                if (!isAmong(doc, documentDTOs)) {
                    documentService.forceDelete(doc.getIdentifier());
                    PaHibernateUtil.getCurrentSession().flush();
                } else {
                    removeFromCollectionByID(documentDTOs, doc.getIdentifier());
                }
            }
            List<DocumentDTO> savedDocs = paServiceUtils.createOrUpdate(
                    documentDTOs, IiConverter.convertToDocumentIi(null),
                    studyProtocolDTO.getIdentifier());
            String updatesList = studyInboxServiceLocal.create(documentDTOs, existingDocs,
                    studyProtocolIi, originalDTO, originalSummary4,
                    originalSites, originalAccrualStatuses, savedDocs);
            studyProtocolService
                .updatePendingTrialAssociationsToActive(IiConverter
                    .convertToLong(studyProtocolIi));
            if (StringUtils.isNotEmpty(updatesList)) {
                mailManagerSerivceLocal.sendUpdateNotificationMail(studyProtocolIi,
                        updatesList);
            }
            StudyMilestoneDTO smDto = studyMilestoneService.getCurrentByStudyProtocol(studyProtocolIi);
            List<StudyInboxDTO> inbox = studyInboxServiceLocal.getByStudyProtocol(studyProtocolIi);
            if (StringUtils.isNotEmpty(updatesList)) {
               sendTSRXML(studyProtocolDTO.getIdentifier(), smDto.getMilestoneCode(), inbox);
            }
        } catch (Exception e) {
            throw new PAException(e.getMessage(), e);
        }
    }

    private void removeFromCollectionByID(List<DocumentDTO> documentDTOs,
            Ii identifier) {
        for (DocumentDTO doc : documentDTOs) {
            if (!ISOUtil.isIiNull(doc.getIdentifier())
                    && !ISOUtil.isIiNull(identifier)
                    && doc.getIdentifier().getExtension()
                            .equals(identifier.getExtension())) {

                documentDTOs.remove(doc);
                return;
            }
        }
    }

    private boolean isAmong(DocumentDTO doc, List<DocumentDTO> documentDTOs) {
        for (DocumentDTO dto : documentDTOs) {
            if (!ISOUtil.isIiNull(doc.getIdentifier())
                    && !ISOUtil.isIiNull(dto.getIdentifier())
                    && doc.getIdentifier().getExtension()
                            .equals(dto.getIdentifier().getExtension())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    private void updateLeadOrganization(OrganizationDTO leadOrg, St leadOrganizationIdentifier, Ii studyProtocolIi)
            throws PAException {
        StudySiteDTO ssCriteriaDTO = new StudySiteDTO();
        ssCriteriaDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        ssCriteriaDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<StudySiteDTO> studySiteDtos = paServiceUtils.getStudySite(ssCriteriaDTO, true);
        StudySiteDTO studySiteDTO = PAUtil.getFirstObj(studySiteDtos);
        if (studySiteDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Lead organization not found for Study Protocol "
                    + studyProtocolIi.getExtension());
        }
        studySiteDTO.setResearchOrganizationIi(IiConverter.convertToIi(PaRegistry.getOrganizationCorrelationService()
                .createResearchOrganizationCorrelations(leadOrg.getIdentifier().getExtension())));
        studySiteDTO.setLocalStudyProtocolIdentifier(leadOrganizationIdentifier);        
        studySiteService.update(studySiteDTO);
    }

    private void updateNctIdentifier(St nctIdentifier, Ii studyProtocolIi) throws PAException {
        StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
        nctIdentifierDTO.setLocalStudyProtocolIdentifier(nctIdentifier);
        nctIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
        nctIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        String poOrgId =
                PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                        PAConstants.NCT_IDENTIFIER_TYPE);
        nctIdentifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService()
                .getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
        paServiceUtils.manageStudyIdentifiers(nctIdentifierDTO);
    }

    @SuppressWarnings({"PMD.ExcessiveParameterList" })
    private void validate(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO,
            St leadOrganizationIdentifier, St nctIdentifier, List<DocumentDTO> documentDTOs,
            List<StudySiteDTO> studySiteDTOs, List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        notNullCheck(studyProtocolDTO, leadOrganizationDTO, leadOrganizationIdentifier, nctIdentifier, errorMsg);
        validateOwner(studyProtocolDTO, errorMsg);
        if (ISOUtil.isStNull(nctIdentifier)
                && CollectionUtils.isEmpty(documentService.getByStudyProtocol(studyProtocolDTO.getIdentifier()))) {
            errorMsg.append("ClinicalTrials.gov Identifier is required as there are no Documents");
        }
        if (CollectionUtils.isNotEmpty(studySiteDTOs)) {
            for (StudySiteDTO studySiteDto : studySiteDTOs) {
                if (ISOUtil.isIiNull(studySiteDto.getStudyProtocolIdentifier())) {
                    errorMsg.append("Study Protocol Identifier  from Study Site cannot be null ");
                }
                if (ISOUtil.isIiNull(studySiteDto.getIdentifier())) {
                    errorMsg.append("Study Site Identifier cannot be null ");
                }
            }
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
        validateNctAndProtocolDoc(nctIdentifier, documentDTOs, studyProtocolDTO.getIdentifier(), errorMsg);
        validateStudySites(studySiteDTOs, studySiteAccrualDTOs, errorMsg);
        validateDocWrkStatus(studyProtocolDTO, errorMsg);
        validateDocuments(documentDTOs, errorMsg);
        validateRecuritmentStatusDateRules(studySiteDTOs, studySiteAccrualDTOs,
                errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void validateRecuritmentStatusDateRules(
            List<StudySiteDTO> studySiteDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs,
            StringBuffer errorMsg) {
        for (StudySiteDTO site : studySiteDTOs) {
            if (ISOUtil.isStNull((site.getLocalStudyProtocolIdentifier()))) {
                errorMsg.append("Participating sites cannot have an empty Local Trial Identifier ");
            }
            if (ISOUtil.isIiNull((site.getIdentifier()))) {
                errorMsg.append("Participating sites must specify an existing identifer, "
                        + "because this is an update operation ");
            }
            // find corresponding accrual status
            for (StudySiteAccrualStatusDTO accrualDTO : studySiteAccrualDTOs) {
                if (!ISOUtil.isIiNull(accrualDTO.getStudySiteIi())
                        && !ISOUtil.isIiNull(site.getIdentifier())
                        && StringUtils.equals(accrualDTO.getStudySiteIi()
                                .getExtension(), site.getIdentifier()
                                .getExtension())) {
                    errorMsg.append(paServiceUtils
                            .validateRecuritmentStatusDateRule(accrualDTO, site));
                }
            }
        }
    }

    private void validateNctAndProtocolDoc(St nctIdentifier,
            List<DocumentDTO> documentDTOs, Ii studyProtocolIi, StringBuffer errorMsg) {
        if (ISOUtil.isStNull(nctIdentifier)) {
            boolean hasProtocolDoc = false;
            for (DocumentDTO doc : documentDTOs) {
                if (doc.getTypeCode() != null
                        && DocumentTypeCode.PROTOCOL_DOCUMENT.getCode().equals(
                                doc.getTypeCode().getCode())) {
                    hasProtocolDoc = true;
                }
            }
            if (!hasProtocolDoc) {
                errorMsg.append("Provide either ClinicalTrials.gov Identifier or Protocol Trial Template ");
            }
        }
        if (nctIdentifier != null && StringUtils.isNotEmpty(nctIdentifier.getValue())) {
            String nctValidationResultString = 
                    getPAServiceUtils().validateNCTIdentifier(nctIdentifier.getValue(), studyProtocolIi);
            if (StringUtils.isNotEmpty(nctValidationResultString)) {
                errorMsg.append(nctValidationResultString);
            }       
        }
    }

    /**
     * @param documentDTOs
     * @param errorMsg
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void validateDocuments(List<DocumentDTO> documentDTOs, StringBuffer errorMsg) {        
        String docErrors = paServiceUtils
                .checkDocumentListForValidFileTypes(documentDTOs);
        errorMsg.append(docErrors);        
        for (DocumentDTO docDto : documentDTOs) {
            if (!ISOUtil.isIiNull(docDto.getIdentifier())
                    && (!paServiceUtils.isIiExistInPA(docDto.getIdentifier()))) {
                errorMsg.append("Document id " + docDto.getIdentifier().getExtension() + " does not exist.");
            }
        }
        for (Iterator<DocumentDTO> iter = documentDTOs.listIterator(); iter.hasNext();) {
            DocumentDTO docDto = iter.next();
            if (ISOUtil.isEdNull(docDto.getText()) && ISOUtil.isIiNull(docDto.getIdentifier())) {
                iter.remove();
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @param errorMsg
     * @throws PAException
     */
    private void validateDocWrkStatus(StudyProtocolDTO studyProtocolDTO, StringBuffer errorMsg) throws PAException {
        DocumentWorkflowStatusDTO isoDocWrkStatus =
                docWrkFlowStatusService.getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
        String dwfs = isoDocWrkStatus.getStatusCode().getCode();
        if (dwfs.equals(DocumentWorkflowStatusCode.SUBMITTED.getCode())
                || dwfs.equals(DocumentWorkflowStatusCode.REJECTED.getCode())) {
            errorMsg.append("Only Trials with processing status Accepted or Abstracted or  "
                    + " Abstraction Verified No Response or   Abstraction Verified No Response can be Updated.");
        }
    }

    /**
     * @param studySiteDTOs
     * @param studySiteAccrualDTOs
     * @param errorMsg
     * @throws PAException
     */
    private void validateStudySites(List<StudySiteDTO> studySiteDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs, StringBuffer errorMsg) throws PAException {
        Map<String, StudySiteDTO> studySiteMap = new HashMap<String, StudySiteDTO>();
        for (StudySiteDTO ssDto : studySiteDTOs) {
            if (ISOUtil.isIiNull(ssDto.getIdentifier())) {
                errorMsg.append(" Study Site identifier cannot be null");
                continue;
            }
            StudySiteDTO studySiteDto = studySiteService.get(ssDto.getIdentifier());
            if (studySiteDto == null) {
                errorMsg.append(" Study site identifier not found for ").append(ssDto.getIdentifier().getExtension());
            }
            studySiteMap.put(ssDto.getIdentifier().getExtension(), studySiteDto);
        }
        for (StudySiteAccrualStatusDTO ssasDto : studySiteAccrualDTOs) {
            if (ISOUtil.isIiNull(ssasDto.getStudySiteIi())) {
                errorMsg.append(" Study Site Accrual Status identifier cannot be null");
                continue;
            }
            if (studySiteMap.get(ssasDto.getStudySiteIi().getExtension()) == null) {
                errorMsg.append(" Study site identifier not found in Study Site Accrual Status DTO ").append(
                        ssasDto.getStudySiteIi().getExtension());
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @param leadOrganizationDTO
     * @param leadOrganizationIdentifier
     * @param nctIdentifier
     * @param errorMsg
     * @throws PAException
     */
    private void notNullCheck(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO, // NOPMD
            St leadOrganizationIdentifier, St nctIdentifier, StringBuffer errorMsg) throws PAException {
        if (studyProtocolDTO == null) {
            errorMsg.append("Study Protocol DTO cannot be null , ");
        }
        if (leadOrganizationDTO == null && !ISOUtil.isStNull(leadOrganizationIdentifier)) {
            errorMsg.append("Lead Organization DTO cannot be null , ");
        }
        if (ISOUtil.isStNull(leadOrganizationIdentifier) && leadOrganizationDTO != null) {
            errorMsg.append("Lead Organization identifier cannot be null , ");
        }
        if (studyProtocolDTO != null) {
            if (ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
                errorMsg.append("Study Protocol Identifier cannot be null ");
            }
            if (ISOUtil.isStNull(studyProtocolDTO.getOfficialTitle())) {
                errorMsg.append("Official Title cannot be null ");
            }
            if (ISOUtil.isStNull(nctIdentifier)) {
                if (ISOUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode())) {
                    errorMsg.append("Purpose cannot be null ");
                }
                if (ISOUtil.isCdNull(studyProtocolDTO.getPhaseCode())) {
                    errorMsg.append("Phase cannot be null ");
                }
            }
        } else {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
    }

    /**
     * @param studyProtocolDTO
     * @param errorMsg
     * @throws PAException
     */
    private void validateOwner(StudyProtocolDTO studyProtocolDTO, StringBuffer errorMsg) throws PAException {
        String loginName = "";
        if (!ISOUtil.isStNull(studyProtocolDTO.getUserLastCreated())) {
            loginName = studyProtocolDTO.getUserLastCreated().getValue();            
            User user = csmUserService.getCSMUser(loginName);
            if (user == null) {
                errorMsg.append("Submitter " + loginName + " does not exist. Please do self register in CTRP.");
            }
        } else {
            errorMsg.append("Submitter is required.");
        }
        if (StringUtils.isNotEmpty(loginName)
                && !userServiceLocal.hasTrialAccess(loginName,
                        Long.parseLong(studyProtocolDTO.getIdentifier().getExtension()))) {
            errorMsg.append("Updates to the trial can only be submitted by either an owner of the trial"
                    + " or a lead organization admin.\n");
        }
    }


    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
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
    public void setStudySiteAccrualStatusService(
            StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }


    /**
     * @param mailManagerSerivceLocal the mailManagerSerivceLocal to set
     */
    public void setMailManagerSerivceLocal(
            MailManagerServiceLocal mailManagerSerivceLocal) {
        this.mailManagerSerivceLocal = mailManagerSerivceLocal;
    }


    /**
     * @param docWrkFlowStatusService the docWrkFlowStatusService to set
     */
    public void setDocWrkFlowStatusService(
            DocumentWorkflowStatusServiceLocal docWrkFlowStatusService) {
        this.docWrkFlowStatusService = docWrkFlowStatusService;
    }


    /**
     * @param studyInboxServiceLocal the studyInboxServiceLocal to set
     */
    public void setStudyInboxServiceLocal(
            StudyInboxServiceLocal studyInboxServiceLocal) {
        this.studyInboxServiceLocal = studyInboxServiceLocal;
    }


    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }


    /**
     * @param studyMilestoneService the studyMilestoneService to set
     */
    public void setStudyMilestoneService(
            StudyMilestoneServicelocal studyMilestoneService) {
        this.studyMilestoneService = studyMilestoneService;
    }


    /**
     * @param userServiceLocal the userServiceLocal to set
     */
    public void setUserServiceLocal(RegistryUserServiceLocal userServiceLocal) {
        this.userServiceLocal = userServiceLocal;
    }


    /**
     * @param userService the userService to set
     */
    public void setCsmUserService(CSMUserUtil userService) {
        this.csmUserService = userService;
    }

    /**
     * @return the protocolQueryServiceLocal
     */
    public ProtocolQueryServiceLocal getProtocolQueryServiceLocal() {
        return protocolQueryServiceLocal;
    }

    /**
     * @param protocolQueryServiceLocal the protocolQueryServiceLocal to set
     */
    public void setProtocolQueryServiceLocal(
            ProtocolQueryServiceLocal protocolQueryServiceLocal) {
        this.protocolQueryServiceLocal = protocolQueryServiceLocal;
    }

    /**
     * @return the studyResourcingServiceLocal
     */
    public StudyResourcingServiceLocal getStudyResourcingServiceLocal() {
        return studyResourcingServiceLocal;
    }

    /**
     * @param studyResourcingServiceLocal the studyResourcingServiceLocal to set
     */
    public void setStudyResourcingServiceLocal(
            StudyResourcingServiceLocal studyResourcingServiceLocal) {
        this.studyResourcingServiceLocal = studyResourcingServiceLocal;
    }
}
