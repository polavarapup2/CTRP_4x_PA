package gov.nih.nci.registry.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

/**
 * The Class TrialUtil.
 *
 * @author vrushali
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength"
    , "PMD.CollapsibleIfStatements", "PMD.AvoidDeeplyNestedIfStmts" })
public class TrialUtil extends TrialConvertUtils {

    private CorrelationUtilsRemote correlationUtils;
    /**
     * Session Attribute of Trial DTO.
     */
    public static final String SESSION_TRIAL_ATTRIBUTE = "trialDTO";
    
    /**
     * Session Attribute of Site DTO.
     */
    public static final String SESSION_TRIAL_SITE_ATTRIBUTE = "siteDTO";
    /**
     * Holds the message for the Wait dialog.
     */
    public static final String SESSION_WAIT_MESSAGE_ATTRIBUTE = "waitDialogMsg";

    /**
     * Default constructor.
     */
    public TrialUtil() {
        super();
        correlationUtils = new CorrelationUtils();
    }

    /**
     * Copy.
     *
     * @param spDTO sdto
     * @param trialDTO gdto
     */
    private void copy(StudyProtocolDTO spDTO, TrialDTO trialDTO) { //NOPMD
        trialDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        trialDTO.setAssignedIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
        trialDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        trialDTO.setPhaseAdditionalQualifier(spDTO.getPhaseAdditionalQualifierCode().getCode());
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeAdditionalQualifierCode(spDTO.getPrimaryPurposeAdditionalQualifierCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        trialDTO.setTrialType(spDTO instanceof NonInterventionalStudyProtocolDTO ? PAConstants.NON_INTERVENTIONAL
                : PAConstants.INTERVENTIONAL);
        trialDTO.setIdentifier(spDTO.getIdentifier().getExtension());
        trialDTO.setStartDate(TsConverter.convertToString(spDTO.getStartDate()));
        trialDTO.setStartDateType(spDTO.getStartDateTypeCode().getCode());
        trialDTO.setPrimaryCompletionDate(TsConverter.convertToString(spDTO.getPrimaryCompletionDate()));
        trialDTO.setPrimaryCompletionDateType(spDTO.getPrimaryCompletionDateTypeCode().getCode());
        trialDTO.setCompletionDate(TsConverter.convertToString(spDTO.getCompletionDate()));
        trialDTO.setCompletionDateType(spDTO.getCompletionDateTypeCode().getCode());
        trialDTO.setProgramCodeText(StConverter.convertToString(spDTO.getProgramCodeText()));
        trialDTO.setSubmissionNumber(IntConverter.convertToString(spDTO.getSubmissionNumber()));
        trialDTO.setXmlRequired(!ISOUtil.isBlNull(spDTO.getCtgovXmlRequiredIndicator())
                && spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue());
        trialDTO.setNciGrant(BlConverter.convertToBoolean(spDTO.getNciGrant()));
        if (spDTO.getSecondaryIdentifiers() != null && spDTO.getSecondaryIdentifiers().getItem() != null) {
            List<Ii> listIi = new ArrayList<Ii>();
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (!IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    listIi.add(ii);
                }
            }
            trialDTO.setSecondaryIdentifierList(listIi);
        }
        if (spDTO.getSecondaryPurposes() != null) {
            trialDTO.setSecondaryPurposes(DSetConverter.convertDSetStToList(spDTO.getSecondaryPurposes()));            
        }
        trialDTO.setSecondaryPurposeOtherText(spDTO.getSecondaryPurposeOtherText().getValue());
        trialDTO.setAccrualDiseaseCodeSystem(StConverter.convertToString(spDTO.getAccrualDiseaseCodeSystem()));
        copyNonInterventionalTrialFields(spDTO, trialDTO);
    }

    /**
     * @param spDTO study protocol DTO
     * @param trialDTO trial DTO
     */
    private void copyStudyAlternateTitles(StudyProtocolDTO spDTO, BaseTrialDTO trialDTO) {
        Set<StudyAlternateTitleDTO> studyAlternateTitles = spDTO.getStudyAlternateTitles();
        if (CollectionUtils.isNotEmpty(studyAlternateTitles)) {
            Set<StudyAlternateTitleDTO> studyAlternateTitleDTOs = new TreeSet<StudyAlternateTitleDTO>(
                    studyAlternateTitles);           
            trialDTO.setStudyAlternateTitles(studyAlternateTitleDTOs);
        }
    }
    
    /**
     * @param spDTO
     * @param trialDTO
     */
    private void copyNonInterventionalTrialFields(StudyProtocolDTO spDTO,
            BaseTrialDTO trialDTO) {
        if (spDTO instanceof NonInterventionalStudyProtocolDTO) {
            NonInterventionalStudyProtocolDTO nonIntDTO = (NonInterventionalStudyProtocolDTO) spDTO;
            trialDTO.setStudyModelCode(CdConverter.convertCdToString(nonIntDTO
                    .getStudyModelCode()));
            trialDTO.setStudyModelOtherText(StConverter
                    .convertToString(nonIntDTO.getStudyModelOtherText()));
            trialDTO.setTimePerspectiveCode(CdConverter
                    .convertCdToString(nonIntDTO.getTimePerspectiveCode()));
            trialDTO.setTimePerspectiveOtherText(StConverter
                    .convertToString(nonIntDTO.getTimePerspectiveOtherText()));
            trialDTO.setStudySubtypeCode(CdConverter
                    .convertCdToString(nonIntDTO.getStudySubtypeCode()));
        }
    }

    /**
     * Copy.
     *
     * @param spqDTO sdto
     * @param trialDTO gdto
     */
    private void copy(StudyProtocolQueryDTO spqDTO, TrialDTO trialDTO) {
        trialDTO.setStudyProtocolId(spqDTO.getStudyProtocolId().toString());
        trialDTO.setLeadOrgTrialIdentifier(spqDTO.getLocalStudyProtocolIdentifier());
        trialDTO.setStatusCode(spqDTO.getStudyStatusCode().getCode());
        trialDTO.setStatusDate(PAUtil.normalizeDateString(spqDTO.getStudyStatusDate().toString()));
    }

    /**
     * Copy lo.
     *
     * @param o o
     * @param trialDTO gdto
     */
    private void copyLO(Organization o, BaseTrialDTO trialDTO) {
        trialDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        trialDTO.setLeadOrganizationName(o.getName());
    }

    /**
     * Copy pi.
     *
     * @param p p
     * @param trialDTO dto
     */
    private void copyPI(Person p, TrialDTO trialDTO) {
        trialDTO.setPiIdentifier(p.getIdentifier());
        trialDTO.setPiName(p.getFullName());
    }

    /**
     * Copy responsible party.
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     * @throws NullifiedRoleException the nullified role exception
     */
    private void copyResponsibleParty(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException,
            NullifiedRoleException {
        
        StudyContactDTO scDto = PaRegistry.getStudyContactService().getResponsiblePartyContact(studyProtocolIi);
        if (scDto != null) {
            String type = StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR
                    .equals(CdConverter.convertCdToEnum(
                            StudyContactRoleCode.class, scDto.getRoleCode())) ? TrialDTO.RESPONSIBLE_PARTY_TYPE_PI
                    : TrialDTO.RESPONSIBLE_PARTY_TYPE_SI;
            trialDTO.setResponsiblePartyType(type);
            trialDTO.setResponsiblePersonTitle(StConverter.convertToString(scDto
                    .getTitle()));

            Ii crsId = scDto.getClinicalResearchStaffIi();
            if (!ISOUtil.isIiNull(crsId)) {
                final ClinicalResearchStaff crs = (ClinicalResearchStaff) new gov.nih.nci.pa.util.CorrelationUtils()
                        .getStructuralRole(crsId, ClinicalResearchStaff.class);
                Organization o = crs.getOrganization();
                Person p = crs.getPerson();
                trialDTO.setResponsiblePersonAffiliationOrgName(o.getName());
                trialDTO.setResponsiblePersonAffiliationOrgId(o.getIdentifier());
                trialDTO.setResponsiblePersonName(p.getFullName());
                trialDTO.setResponsiblePersonIdentifier(p.getIdentifier());
            }
        } else {                   
            if (getPaServiceUtil().isResponsiblePartySponsor(studyProtocolIi)) {
                trialDTO.setResponsiblePartyType(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);                
            }
        }
       
    }

    /**
     * Copy sponsor.
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copySponsor(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            Organization o = getCorrelationUtils().getPAOrganizationByIi(spart.getResearchOrganizationIi());
            trialDTO.setSponsorName(o.getName());
            trialDTO.setSponsorIdentifier(o.getIdentifier());
        }
    }

    /**
     * Copy nct nummber.
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copyNctNummber(Ii studyProtocolIi, BaseTrialDTO trialDTO) throws PAException {
        String nctNumber = getPaServiceUtil().getStudyIdentifier(studyProtocolIi, PAConstants.NCT_IDENTIFIER_TYPE);
        if (nctNumber != null) {
            trialDTO.setNctIdentifier(nctNumber);
        }
    }

    /**
     * Copy summary four.
     *
     * @param srDTO sdto
     * @param trialDTO tdto
     * @throws PAException ex
     */
    private void copySummaryFour(List<StudyResourcingDTO> srDTO, BaseTrialDTO trialDTO) throws PAException {
        if (srDTO == null) {
            return;
        }
        if (CollectionUtils.isNotEmpty(srDTO) && srDTO.get(0).getTypeCode() != null) {
            trialDTO.setSummaryFourFundingCategoryCode(srDTO.get(0).getTypeCode().getCode());
        }
        if (CollectionUtils.isNotEmpty(srDTO)) {
            for (StudyResourcingDTO dto : srDTO) {
                 if (dto.getOrganizationIdentifier() != null
                         && StringUtils.isNotEmpty(dto.getOrganizationIdentifier().getExtension())) {
                    Organization o = getCorrelationUtils().getPAOrganizationByIi(dto.getOrganizationIdentifier());
                    SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
                    summarySp.setRowId(UUID.randomUUID().toString());
                    summarySp.setOrgId(o.getIdentifier());
                    summarySp.setOrgName(o.getName());
                    if (!trialDTO.getSummaryFourOrgIdentifiers().contains(summarySp)) {
                        trialDTO.getSummaryFourOrgIdentifiers().add(summarySp);
                    }
                 }
            }
        }
    }

    /**
     * Copy indide list.
     *
     * @param studyIndldeDTOList iiDto
     * @param trialDTO dto
     *
     * @throws PAException ex
     */
    private void copyINDIDEList(List<StudyIndldeDTO> studyIndldeDTOList, TrialDTO trialDTO) throws PAException {
        if (studyIndldeDTOList == null) {
            return;
        }
        List<TrialIndIdeDTO> indList = new ArrayList<TrialIndIdeDTO>();
        for (StudyIndldeDTO isoDto : studyIndldeDTOList) {
            indList.add(new TrialIndIdeDTO(isoDto));
        }
        trialDTO.setIndIdeDtos(indList);
    }

    /**
     * Copy grant list.
     *
     * @param isoGrantlist iso
     * @param trialDTO dto
     */
    private void copyGrantList(List<StudyResourcingDTO> isoGrantlist, TrialDTO trialDTO) {
        if (isoGrantlist == null) {
            return;
        }
        List<TrialFundingWebDTO> grantList = new ArrayList<TrialFundingWebDTO>();
        TrialFundingWebDTO webDto = null;
        for (StudyResourcingDTO isoDto : isoGrantlist) {
            webDto = new TrialFundingWebDTO(isoDto);
            webDto.setRowId(UUID.randomUUID().toString());
            grantList.add(webDto);
        }
        trialDTO.setFundingDtos(grantList);
    }

    /**
     * Gets the trial dto from db.
     *
     * @param studyProtocolIi Ii
     * @param trialDTO TrialDTO
     *
     * @throws PAException ex
     * @throws NullifiedRoleException e
     */
    public void getTrialDTOFromDb(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException, NullifiedRoleException {
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        StudyProtocolQueryDTO spqDto = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                Long.valueOf(studyProtocolIi.getExtension()));
        copy(spDTO, trialDTO);
        copyStudyAlternateTitles(spDTO, trialDTO);
        copy(spqDto, trialDTO);
        copyLO(getCorrelationUtils().getPAOrganizationByIi(
                IiConverter.convertToPaOrganizationIi(spqDto.getLeadOrganizationId())), trialDTO);
        copyPI(getCorrelationUtils().getPAPersonByIi(IiConverter.convertToPaPersonIi(spqDto.getPiId())), trialDTO);
        copyResponsibleParty(studyProtocolIi, trialDTO);
        copySponsor(studyProtocolIi, trialDTO);
        copyNctNummber(studyProtocolIi, trialDTO);
        copySummaryFour(PaRegistry.getStudyResourcingService().getSummary4ReportedResourcing(studyProtocolIi),
                trialDTO);
        // Copy IND's
        List<StudyIndldeDTO> studyIndldeDTOList = PaRegistry.getStudyIndldeService()
                .getByStudyProtocol(studyProtocolIi);
        if (!(studyIndldeDTOList.isEmpty())) {
            copyINDIDEList(studyIndldeDTOList, trialDTO);
        }
        // query the study grants
        List<StudyResourcingDTO> isoList = PaRegistry.getStudyResourcingService().
                getActiveStudyResourcingByStudyProtocol(
                studyProtocolIi);
        if (!(isoList.isEmpty())) {
            copyGrantList(isoList, trialDTO);
        }
        
        trialDTO.setStatusHistory(PaRegistry.getStudyOverallStatusService()
                .getStatusHistoryByProtocol(studyProtocolIi));
        
        copyRegulatoryInformation(studyProtocolIi, trialDTO);
        copyCollaborators(studyProtocolIi, trialDTO);
        copyParticipatingSites(studyProtocolIi, trialDTO);
        // Copy IND's to update list
        trialDTO.setIndIdeUpdateDtos(trialDTO.getIndIdeDtos());
        copyDcpIdentifier(studyProtocolIi, trialDTO);
        copyCtepIdentifier(studyProtocolIi, trialDTO);
        copyProgramCodes(trialDTO, spDTO);
    }
    
    private void copyProgramCodes(TrialDTO trialDTO, StudyProtocolDTO studyProtocolDTO) {
        if (!CollectionUtils.isEmpty(studyProtocolDTO.getProgramCodes())) {
            List<String> programCodesList = new ArrayList<String>();
            for (ProgramCodeDTO programCodeDTO :studyProtocolDTO.getProgramCodes()) {
                programCodesList.add(programCodeDTO.getProgramCode());
            }
            trialDTO.setProgramCodesList(programCodesList);
        }
        
    }

    /**
     * Copy collaborators.
     *
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO the trial dto
     *
     * @throws PAException the PA exception
     */
    private void copyCollaborators(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
        for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudySiteDTO searchCode = new StudySiteDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        List<PaOrganizationDTO> collaboratorsList = new ArrayList<PaOrganizationDTO>();
        List<StudySiteDTO> spList = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, criteriaList);
        for (StudySiteDTO sp : spList) {
            Organization orgBo = getCorrelationUtils().getPAOrganizationByIi(sp.getResearchOrganizationIi());
            PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
            orgWebDTO.setId(IiConverter.convertToString(sp.getIdentifier()));
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            orgWebDTO.setFunctionalRole(sp.getFunctionalCode().getCode());
            orgWebDTO.setStatus(sp.getStatusCode().getCode());
            collaboratorsList.add(orgWebDTO);
        }
        trialDTO.setCollaborators(collaboratorsList);

    }

    /**
     * Copy participating sites.
     *
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO the trial dto
     * @throws PAException the PA exception
     */
    private void copyParticipatingSites(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        List<PaOrganizationDTO> participatingSitesList = new ArrayList<PaOrganizationDTO>();
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE.getCode()));
        List<StudySiteDTO> spList = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, srDTO);
        for (StudySiteDTO sp : spList) {
            StudySiteAccrualStatusDTO ssas = PaRegistry.getStudySiteAccrualStatusService()
                    .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
            Organization orgBo = getCorrelationUtils().getPAOrganizationByIi(sp.getHealthcareFacilityIi());
            PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
            orgWebDTO.setId(IiConverter.convertToString(sp.getIdentifier()));
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setProgramCode(StConverter.convertToString(sp.getProgramCodeText()));
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            if (ssas == null || ssas.getStatusCode() == null || ssas.getStatusDate() == null) {
                orgWebDTO.setRecruitmentStatus("unknown");
                orgWebDTO.setRecruitmentStatusDate("unknown");
            } else {
                orgWebDTO.setRecruitmentStatus(CdConverter.convertCdToString(ssas.getStatusCode()));
                orgWebDTO.setRecruitmentStatusDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                        ssas.getStatusDate()).toString()));
            }
            participatingSitesList.add(orgWebDTO);
        }
        trialDTO.setParticipatingSites(participatingSitesList);
    }

    /**
     * Copy participating sites for proprietary trial.
     *
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO the trial dto
     *
     * @throws PAException the PA exception
     */
    public void copyParticipatingSites(Ii studyProtocolIi, ProprietaryTrialDTO trialDTO) throws PAException {
        List<SubmittedOrganizationDTO> organizationList = new ArrayList<SubmittedOrganizationDTO>();
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE.getCode()));
        List<StudySiteDTO> spList = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, srDTO);
        for (StudySiteDTO sp : spList) {
            SubmittedOrganizationDTO orgWebDTO = getSubmittedOrganizationDTO(sp);
            organizationList.add(orgWebDTO);
        }
        trialDTO.setParticipatingSitesList(organizationList);
    }
    

    /**
     * Converts {@link StudySiteDTO} into a {@link SubmittedOrganizationDTO}.
     * @param sp StudySiteDTO
     * @return SubmittedOrganizationDTO SubmittedOrganizationDTO
     * @throws PAException PAException 
     */
    public SubmittedOrganizationDTO getSubmittedOrganizationDTO(StudySiteDTO sp)
            throws PAException {
        StudySiteAccrualStatusDTO ssas = PaRegistry
                .getStudySiteAccrualStatusService()
                .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
        Organization orgBo = getCorrelationUtils().getPAOrganizationByIi(
                sp.getHealthcareFacilityIi());
        SubmittedOrganizationDTO orgWebDTO = new SubmittedOrganizationDTO(sp,
                ssas, PaRegistry.getStudySiteAccrualStatusService()
                        .getStatusHistory(
                                sp.getIdentifier()), orgBo);
        List<PaPersonDTO> principalInvresults = PaRegistry
                .getPAHealthCareProviderService().getPersonsByStudySiteId(
                        Long.valueOf(sp.getIdentifier().getExtension()
                                .toString()),
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR
                                .getName());
        if (!principalInvresults.isEmpty()) {
            for (PaPersonDTO per : principalInvresults) {
                orgWebDTO.setInvestigator(per.getFullName() != null ? per
                        .getFullName() : "");
                orgWebDTO.setInvestigatorId(per.getPaPersonId());
            }
        }
        orgWebDTO.setNameInvestigator(orgWebDTO.getName() + " - "
                + orgWebDTO.getInvestigator());
        return orgWebDTO;
    }

    /**
     * Copy regulatory information.
     *
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO the trial dto
     * @throws PAException the PA exception
     */
    private void copyRegulatoryInformation(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        RegulatoryInformationServiceLocal regInfoSvc = PaRegistry.getRegulatoryInformationService();
        //trialDTO.setCountryList(regInfoSvc.getDistinctCountryNames());
        trialDTO.setCountryList(regInfoSvc.getDistinctCountryNamesStartWithUSA());
        StudyRegulatoryAuthorityServiceLocal studyRegAuthSvc = PaRegistry.getStudyRegulatoryAuthorityService();
        StudyRegulatoryAuthorityDTO authorityDTO = studyRegAuthSvc.getCurrentByStudyProtocol(studyProtocolIi);
        if (authorityDTO != null) { // load values from database
            setRegulatoryIndicatorInfo(studyProtocolIi, trialDTO);
            setRegulatoryAuthorityInfo(studyProtocolIi, trialDTO);
            setOversgtInfo(trialDTO);
        }
    }

    private void setRegulatoryAuthorityInfo(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        RegulatoryInformationServiceLocal regInfoSvc = PaRegistry.getRegulatoryInformationService();
        StudyRegulatoryAuthorityServiceLocal studyRegAuthSvc = PaRegistry.getStudyRegulatoryAuthorityService();
        StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = studyRegAuthSvc.getCurrentByStudyProtocol(studyProtocolIi);
        if (sraFromDatabaseDTO != null) {
            Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
            List<Long> regInfo = regInfoSvc.getRegulatoryAuthorityInfo(sraId);
            trialDTO.setLst(regInfo.get(1).toString());
            // set selected the name of the regulatory authority chosen
            trialDTO.setRegIdAuthOrgList(regInfoSvc.getRegulatoryAuthorityNameId(Long
                    .valueOf(regInfo.get(1).toString())));
            trialDTO.setSelectedRegAuth(regInfo.get(0).toString());
        }
    }

    private void setRegulatoryIndicatorInfo(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (spDTO.getSection801Indicator().getValue() != null) {
            trialDTO.setSection801Indicator(BlConverter.convertBlToYesNoString(spDTO.getSection801Indicator()));
        }
        if (spDTO.getFdaRegulatedIndicator().getValue() != null) {
            trialDTO.setFdaRegulatoryInformationIndicator(BlConverter.convertBlToYesNoString(spDTO
                    .getFdaRegulatedIndicator()));
        }
        if (spDTO.getDelayedpostingIndicator().getValue() != null) {
            trialDTO.setDelayedPostingIndicator(BlConverter.convertBlToYesNoString(spDTO.getDelayedpostingIndicator()));
        }
        if (spDTO.getDataMonitoringCommitteeAppointedIndicator().getValue() != null) {
            trialDTO.setDataMonitoringCommitteeAppointedIndicator((BlConverter.convertBlToYesNoString(spDTO
                    .getDataMonitoringCommitteeAppointedIndicator())));
        }
    }


    /**
     * Copy Status data from the source to the destination Trial.
     *
     * @param copyTo the destination Trial to be updated
     * @param copyFrom the source Trial
     */
    public void copyStatusInformation(TrialDTO copyTo, TrialDTO copyFrom) {
        copyTo.setStatusCode(copyFrom.getStatusCode());
        copyTo.setStatusDate(copyFrom.getStatusDate());
        copyTo.setStartDate(copyFrom.getStartDate());
        copyTo.setStartDateType(copyFrom.getStartDateType());
        copyTo.setPrimaryCompletionDate(copyFrom.getPrimaryCompletionDate());
        copyTo.setPrimaryCompletionDateType(copyFrom.getPrimaryCompletionDateType());
        copyTo.setCompletionDate(copyFrom.getCompletionDate());
        copyTo.setCompletionDateType(copyFrom.getCompletionDateType());
        copyTo.setReason(copyFrom.getReason());
        copyTo.setStatusHistory(copyFrom.getStatusHistory());
    }

    /**
     * updates the studyprocol dto with the trial details and status information.
     *
     * @param trialDTO TrialDTO
     * @param spdto StudyProtocolDTO
     * @throws PAException on error
     */
    public void updateStudyProtcolDTO(StudyProtocolDTO spdto, TrialDTO trialDTO) throws PAException {
        convertToStudyProtocolDTO(trialDTO, spdto);
    }

    /**
     * Gets the study reg auth.
     *
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO trialDTO
     * @return the study reg auth
     * @throws PAException the PA exception
     */
    public StudyRegulatoryAuthorityDTO getStudyRegAuth(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {

        StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = null;
        StudyRegulatoryAuthorityDTO sraDTO = new StudyRegulatoryAuthorityDTO();
        if (!ISOUtil.isIiNull(studyProtocolIi)) {
            sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService().getCurrentByStudyProtocol(
                    studyProtocolIi);
            sraDTO.setStudyProtocolIdentifier(studyProtocolIi);
        }
        if (StringUtils.isEmpty(trialDTO.getSelectedRegAuth())) {
            return sraFromDatabaseDTO;
        }
        if (sraFromDatabaseDTO == null) {
            sraDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(trialDTO.getSelectedRegAuth()));
            return sraDTO;
        }
        sraFromDatabaseDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(trialDTO.getSelectedRegAuth()));
        return sraFromDatabaseDTO;
    }
    /**
     * @param trialDTO trialDTO
     */
    @SuppressWarnings({ "PMD" })
    public void populateRegulatoryListStartWithUSA(TrialDTO trialDTO) {
        try {
            trialDTO.setCountryList(PaRegistry.getRegulatoryInformationService().getDistinctCountryNamesStartWithUSA());
            if (NumberUtils.isNumber(trialDTO.getLst())) {
                trialDTO.setRegIdAuthOrgList(PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                        Long.valueOf(trialDTO.getLst())));
            }
        } catch (PAException e) {
            // do nothing
        }
    }
    /**
     * Copy dcp nummber.
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copyDcpIdentifier(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        String dcpId = getPaServiceUtil().getStudyIdentifier(studyProtocolIi, PAConstants.DCP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(dcpId)) {
            trialDTO.setDcpIdentifier(dcpId);
        }
    }

    /**
     * Copy dcp nummber.
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copyCtepIdentifier(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        String ctepId = getPaServiceUtil().getStudyIdentifier(studyProtocolIi, PAConstants.CTEP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(ctepId)) {
            trialDTO.setCtepIdentifier(ctepId);
        }
    }

    /**
     *
     * @param tempStudyProtocolId ii
     * @return trialDTO
     * @throws NullifiedRoleException on err
     * @throws PAException on err
     */
    @SuppressWarnings("deprecation")
    public BaseTrialDTO getTrialDTOForPartiallySumbissionById(String tempStudyProtocolId)
            throws NullifiedRoleException, PAException {
        BaseTrialDTO trialDTO = convertToTrialDTO(PaRegistry
                .getStudyProtocolStageService().get(
                        IiConverter.convertToIi(tempStudyProtocolId)));
        List<StudyFundingStageDTO> fundingIsoDtos = PaRegistry.getStudyProtocolStageService()
                .getGrantsByStudyProtocolStage(IiConverter.convertToIi(trialDTO.getStudyProtocolId()));
        List<TrialFundingWebDTO> webDTOs = new ArrayList<TrialFundingWebDTO>();
        for (StudyFundingStageDTO fundingDto : fundingIsoDtos) {
            webDTOs.add(convertToTrialFundingWebDTO(fundingDto));
        }
        trialDTO.setFundingDtos(webDTOs);
        List<TrialIndIdeDTO> webIndDtos = new ArrayList<TrialIndIdeDTO>();
        List<StudyIndIdeStageDTO> indIdeIsoDtos = PaRegistry.getStudyProtocolStageService()
                .getIndIdesByStudyProtocolStage(IiConverter.convertToIi(trialDTO.getStudyProtocolId()));
        for (StudyIndIdeStageDTO isoIndDto : indIdeIsoDtos) {
            webIndDtos.add(convertToTrialIndIdeDTO(isoIndDto));
        }
        trialDTO.setIndIdeDtos(webIndDtos);
        if (StringUtils.isEmpty(trialDTO.getPropritaryTrialIndicator())
                || trialDTO.getPropritaryTrialIndicator().equalsIgnoreCase(CommonsConstant.NO)) {
            populateRegulatoryListStartWithUSA((TrialDTO) trialDTO);
        }
        populateStageTrialDocuments(trialDTO);
        return trialDTO;
    }

    /**
     * @param trialDTO BaseTrialDTO
     * @throws PAException PAException
     */
    public void populateStageTrialDocuments(BaseTrialDTO trialDTO)
            throws PAException {
        // populate doc
        List<DocumentDTO> docDTOs = PaRegistry.getStudyProtocolStageService().getDocumentsByStudyProtocolStage(
                IiConverter.convertToIi(trialDTO.getStudyProtocolId()));
        List<TrialDocumentWebDTO> webDocList = new ArrayList<TrialDocumentWebDTO>();
        for (DocumentDTO docDTO : docDTOs) {
            webDocList.add(new TrialDocumentWebDTO(docDTO));
        }
        trialDTO.setDocDtos(webDocList);
    }

    /**
     * @param trialDTO
     *            BaseTrialDTO
     * @throws PAException
     *             PAException
     * @return List<TrialDocumentWebDTO>
     */
    @SuppressWarnings("deprecation")
    public List<TrialDocumentWebDTO> getTrialDocuments(BaseTrialDTO trialDTO)
            throws PAException {
        // populate doc
        List<DocumentDTO> docDTOs = PaRegistry.getDocumentService()
                .getDocumentsByStudyProtocol(
                        IiConverter.convertToIi(trialDTO.getStudyProtocolId()));
        List<TrialDocumentWebDTO> webDocList = new ArrayList<TrialDocumentWebDTO>();
        for (DocumentDTO docDTO : docDTOs) {
            webDocList.add(new TrialDocumentWebDTO(docDTO));
        }
        return webDocList;
    }
    
    
    /**
     * @param trialDTO dto
     * @return dto
     * @throws PAException on err
     */
    public BaseTrialDTO saveDraft(BaseTrialDTO trialDTO) throws PAException {
        validateLeadOrganization(trialDTO);
        List<StudyFundingStageDTO> fundingDTOS = getDraftGrant(trialDTO);
        List<StudyIndIdeStageDTO> indDTOS = getDraftIndIde(trialDTO);
        return saveDraft(trialDTO, fundingDTOS, indDTOS);
    }

    private BaseTrialDTO saveDraft(BaseTrialDTO trialDTO, List<StudyFundingStageDTO> fundingDTOS,
            List<StudyIndIdeStageDTO> indDTOS) throws PAException {
        StudyProtocolStageDTO spStageDto = convertToStudyProtocolStageDTO(trialDTO);
        List<DocumentDTO> docDTOS = convertToISODocumentList(trialDTO.getDocDtos());
        Ii tempStudyProtocolIi = null;
        if (StringUtils.isNotEmpty(trialDTO.getStudyProtocolId())) {
            StudyProtocolStageDTO dto = PaRegistry.getStudyProtocolStageService().update(spStageDto, fundingDTOS,
                    indDTOS, docDTOS);
            tempStudyProtocolIi = dto.getIdentifier();
        } else {
            tempStudyProtocolIi = PaRegistry.getStudyProtocolStageService().create(spStageDto, fundingDTOS, indDTOS,
                    docDTOS);
        }
        if (trialDTO instanceof TrialDTO) {
            setOversgtInfo((TrialDTO) trialDTO);
        }
        trialDTO.setStudyProtocolId(tempStudyProtocolIi.getExtension());
        return trialDTO;
    }

    @SuppressWarnings("unchecked")
    private List<StudyIndIdeStageDTO> getDraftIndIde(BaseTrialDTO trialDTO) {
        List<TrialIndIdeDTO> indList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.INDIDE_LIST);
        List<StudyIndIdeStageDTO> indDTOS = new ArrayList<StudyIndIdeStageDTO>();
        if (CollectionUtils.isNotEmpty(indList)) {
            for (TrialIndIdeDTO indDto : indList) {
                indDTOS.add(convertToStudyIndIdeStage(indDto));
            }
        }
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeList");
        if (indList != null) {
            trialDTO.setIndIdeDtos(indList);
        }
        return indDTOS;
    }

    @SuppressWarnings("unchecked")
    private List<StudyFundingStageDTO> getDraftGrant(BaseTrialDTO trialDTO) {
        List<TrialFundingWebDTO> grantList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.GRANT_LIST);
        List<StudyFundingStageDTO> fundingDTOS = new ArrayList<StudyFundingStageDTO>();
        if (CollectionUtils.isNotEmpty(grantList)) {
            for (TrialFundingWebDTO fundingDto : grantList) {
                fundingDTOS.add(convertToStudyFundingStage(fundingDto));
            }
        }
        ServletActionContext.getRequest().getSession().removeAttribute("grantList");
        if (grantList != null) {
            trialDTO.setFundingDtos(grantList);
        }
        return fundingDTOS;
    }

    private void validateLeadOrganization(BaseTrialDTO trialDTO) throws PAException {
        StringBuffer errMsg = new StringBuffer();
        if (StringUtils.isEmpty(trialDTO.getLeadOrgTrialIdentifier())) {
            errMsg.append("Lead Organization Trial Identifier is required.");
        }
        if (StringUtils.length(trialDTO
                .getLeadOrgTrialIdentifier()) > PAAttributeMaxLen.LEN_30) {
            errMsg.append("Lead Organization Trial Identifier  cannot be more than 30 characters");
        }
        if (StringUtils.isEmpty(trialDTO.getLeadOrganizationIdentifier())) {
            errMsg.append("Lead Organization is required.");
        }
        if (errMsg.length() > 1) {
            throw new PAException(errMsg.toString());
        }
    }

    /**
     *
     * @param trialDTO dto
     * @throws PAException on err
     */
    public void setOversgtInfo(TrialDTO trialDTO) throws PAException {
        if (trialDTO.getSelectedRegAuth() != null) {
            String orgName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
                    Long.valueOf(trialDTO.getSelectedRegAuth()), "RegulatoryAuthority");
            trialDTO.setTrialOversgtAuthOrgName(orgName);
        }
        if (trialDTO.getLst() != null) {
            String countryName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
                    Long.valueOf(trialDTO.getLst()), "Country");
            trialDTO.setTrialOversgtAuthCountryName(countryName);
        }
    }

    /**
     * Gets the Proprietary trial dto from db.
     *
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO the trial dto
     * @throws PAException the PA exception
     * @throws NullifiedRoleException the nullified role exception
     */
    public void getProprietaryTrialDTOFromDb(Ii studyProtocolIi, ProprietaryTrialDTO trialDTO) throws PAException,
            NullifiedRoleException {
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        StudyProtocolQueryDTO spqDto = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                Long.valueOf(studyProtocolIi.getExtension()));        
        trialDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        trialDTO.setAssignedIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
        trialDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        trialDTO.setPhaseAdditionalQualifier(spDTO.getPhaseAdditionalQualifierCode().getCode());
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeAdditionalQualifierCode(spDTO.getPrimaryPurposeAdditionalQualifierCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        trialDTO.setTrialType(spDTO instanceof NonInterventionalStudyProtocolDTO ? PAConstants.NON_INTERVENTIONAL
                : PAConstants.INTERVENTIONAL);
        trialDTO.setIdentifier(spDTO.getIdentifier().getExtension());
        trialDTO.setStudyProtocolId(spqDto.getStudyProtocolId().toString());
        trialDTO.setLeadOrgTrialIdentifier(spqDto.getLocalStudyProtocolIdentifier());
        trialDTO.setConsortiaTrialCategoryCode(CdConverter.convertCdToString(spDTO.getConsortiaTrialCategoryCode()));
        copyLO(getCorrelationUtils().getPAOrganizationByIi(
                IiConverter.convertToPaOrganizationIi(spqDto.getLeadOrganizationId())), trialDTO);
        copyNctNummber(studyProtocolIi, trialDTO);
        copySummaryFour(PaRegistry.getStudyResourcingService().getSummary4ReportedResourcing(studyProtocolIi),
                trialDTO);
        copyParticipatingSites(studyProtocolIi, trialDTO);
        if (spDTO.getSecondaryPurposes() != null) {
            trialDTO.setSecondaryPurposes(DSetConverter.convertDSetStToList(spDTO.getSecondaryPurposes()));            
        }   
        copyStudyAlternateTitles(spDTO, trialDTO);
        trialDTO.setSecondaryPurposeOtherText(spDTO.getSecondaryPurposeOtherText().getValue());
        trialDTO.setNciGrant(BlConverter.convertToBoolean(spDTO.getNciGrant()));
        copyNonInterventionalTrialFields(spDTO, trialDTO);
    }

    /**
     * Gets the study site to update.
     *
     * @param ps the ps
     * @return the study site to update
     * @throws PAException the PA exception
     */
    public List<StudySiteDTO> getStudySiteToUpdate(List<SubmittedOrganizationDTO> ps) throws PAException {
        List<StudySiteDTO> ssDTO = new ArrayList<StudySiteDTO>();
        for (SubmittedOrganizationDTO dto : ps) {
            StudySiteDTO sp = PaRegistry.getStudySiteService().get(IiConverter.convertToIi(dto.getId()));
            sp.setProgramCodeText(StConverter.convertToSt(dto.getProgramCode()));
            sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt(dto.getSiteLocalTrialIdentifier()));
            if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                    && StringUtils.isNotEmpty(dto.getDateClosedforAccrual())) {
                sp.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(),
                        dto.getDateClosedforAccrual()));
            }
            if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                    && StringUtils.isEmpty(dto.getDateClosedforAccrual())) {
                sp.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(), null));
            }
            ssDTO.add(sp);
        }
        return ssDTO;
    }

    /**
     * @param trialDTO2 trialDTO
     */
    public void removeAssignedIdentifierFromSecondaryIds(TrialDTO trialDTO2) {
        for (Iterator<Ii> iter = trialDTO2.getSecondaryIdentifierList().iterator(); iter.hasNext();) {
            Ii ii = iter.next();
            if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                iter.remove();
            } else {
                ii.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
            }
        }
    }
    
    
    /**
     * assign program codes to studyProtocolDTO
     * @param trialDTO trialDTO
     * @param studyProtocolDTO studyProtocolDTO
     */
    public void assignProgramCodes(TrialDTO trialDTO, StudyProtocolDTO studyProtocolDTO) {
        
        List<ProgramCodeDTO> allProgramCodes = (List<ProgramCodeDTO>) 
                ServletActionContext.getRequest().
                getSession().getAttribute(Constants.PROGRAM_CODES_LIST);
        List<ProgramCodeDTO> studyProgramCodeList = new ArrayList<ProgramCodeDTO>();
        
      //if program code are set then fetch corresponding DTO
        if (!CollectionUtils.isEmpty(trialDTO.getProgramCodesList())) {
            if (ServletActionContext.getRequest().
                    getSession().getAttribute(Constants.PROGRAM_CODES_LIST) != null) {
      
          
            
            if (!CollectionUtils.isEmpty(allProgramCodes)) {
                
                //get old family id
                for (ProgramCodeDTO programCodeDTO : allProgramCodes) {
                    if (trialDTO.getProgramCodesList().contains(
                            programCodeDTO.getProgramCode())) {
                        studyProgramCodeList.add(programCodeDTO);
                    }
                }
            }
            
            
            if (!CollectionUtils.isEmpty(studyProgramCodeList)) {
                studyProtocolDTO.setProgramCodes(studyProgramCodeList);
            }
        }
        }  
        
        
        
        
    }
    
    /**
     * * There can be other program codes that belongs to different family 
     * and already associated to trial.We are not showing such program codes
     * in update/amend screen.But when such update/amendment is saved
     * we need to retain those program codes
     * following logic is doing that
     * @param oldProgramCodesList oldProgramCodesList
     * @param studyProtocolDTO studyProtocolDTO
     */
    public void assignAdditionalProgramCodes(List<ProgramCodeDTO> oldProgramCodesList,
            StudyProtocolDTO studyProtocolDTO)  {
        if (!CollectionUtils.isEmpty(oldProgramCodesList)) {
            
            List<ProgramCodeDTO> allProgramCodes = (List<ProgramCodeDTO>) 
                    ServletActionContext.getRequest().
                    getSession().getAttribute(Constants.PROGRAM_CODES_LIST);
            List<Long> familyProgramCodesList = new ArrayList<Long>();
                 
                 List<ProgramCodeDTO> additionalProgramCodesList = new ArrayList<ProgramCodeDTO>();
                 
                 if (!CollectionUtils.isEmpty(allProgramCodes)) {
                     for (ProgramCodeDTO programCodeDTO : allProgramCodes) {
                         familyProgramCodesList.add(programCodeDTO.getId());
                     }
                 }
                 
                 for (ProgramCodeDTO programCodeDTO :oldProgramCodesList) {
                     if (!familyProgramCodesList.contains(programCodeDTO.getId())) {
                         additionalProgramCodesList.add(programCodeDTO);
                     }
                 }
                 if (!CollectionUtils.isEmpty(additionalProgramCodesList)
                      && studyProtocolDTO.getProgramCodes() != null) {
                     studyProtocolDTO.getProgramCodes().addAll(additionalProgramCodesList);
                 }
                 
             }
    }

    /**
     * @return the correlationUtils
     */
    public CorrelationUtilsRemote getCorrelationUtils() {
        return correlationUtils;
    }

    /**
     * @param correlationUtils the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

    
    
}
