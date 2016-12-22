/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ValidationAware;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.TooManyFields" })
public final class AddUpdateSiteHelper {

    private final PAServiceUtils paServiceUtil;

    private final SubmittedOrganizationDTO siteDTO;

    private final ParticipatingSiteServiceLocal participatingSiteService;

    private final StudyProtocolServiceLocal studyProtocolService;

    private final RegistryUser registryUser;

    private final RegistryUserServiceLocal registryUserService;

    private final StudySiteContactServiceLocal studySiteContactService;

    private final ValidationAware validationAware;

    private final String studyProtocolId;
    
    private final String orgPoId;

    private final List<Long> finalProgramCodeIds = new ArrayList<Long>();
    private final List<Long> initialProgramCodeIds = new ArrayList<Long>();
    private final Map<ProgramCodeDTO, FamilyDTO> programCodeFamilyIndex = new HashMap<ProgramCodeDTO, FamilyDTO>();

    /**
     * @param paServiceUtil
     *            paServiceUtil
     * @param siteDTO
     *            siteDTO
     * @param participatingSiteService
     *            participatingSiteService
     * @param studyProtocolService
     *            studyProtocolService
     * @param registryUser
     *            registryUser
     * @param registryUserService
     *            registryUserService
     * @param studySiteContactService
     *            studySiteContactService
     * @param validationAware
     *            validationAware
     */
    public AddUpdateSiteHelper( // NOPMD           
            PAServiceUtils paServiceUtil, SubmittedOrganizationDTO siteDTO,
            ParticipatingSiteServiceLocal participatingSiteService,
            StudyProtocolServiceLocal studyProtocolService,
            RegistryUser registryUser,
            RegistryUserServiceLocal registryUserService,
            StudySiteContactServiceLocal studySiteContactService,
            ValidationAware validationAware, String studyProtocolId, String orgPoId) {
        this.paServiceUtil = paServiceUtil;
        this.siteDTO = siteDTO;
        this.participatingSiteService = participatingSiteService;
        this.studyProtocolService = studyProtocolService;
        this.registryUser = registryUser;
        this.registryUserService = registryUserService;
        this.studySiteContactService = studySiteContactService;
        this.validationAware = validationAware;
        this.studyProtocolId = studyProtocolId;
        this.orgPoId = orgPoId;
    }

    public void addSite() throws PAException {
        Ii poHealthFacilID = getHealthcareFacilityID();
        StudySiteDTO studySiteDTO = getStudySite();
        StudySiteAccrualStatusDTO accrualStatusDTO = getStudySiteAccrualStatus();
        Ii studySiteID = null; // IiConverter.convertToStudySiteIi(studySiteIdentifier);

        try {
            studySiteID = participatingSiteService.createStudySiteParticipant(
                    studySiteDTO, accrualStatusDTO, siteDTO.getStatusHistory(), poHealthFacilID)
                    .getIdentifier();
        } catch (DuplicateParticipatingSiteException e) {
            validationAware.addFieldError("organizationName", e.getMessage());
            throw new PAException(e);
        }
        StudyProtocolDTO spDTO = loadStudyProtocolDTO();
        updateProgramCodes(spDTO);
        addInvestigator(spDTO, studySiteID);
        createSiteRecordOwnership(studySiteID, registryUser);
    }
    
    public void updateSite() throws PAException {
        StudySiteDTO studySiteDTO = getStudySite();
        StudySiteAccrualStatusDTO accrualStatusDTO = getStudySiteAccrualStatus();

        Ii studySiteID = participatingSiteService.updateStudySiteParticipantWithStatusHistory(
                studySiteDTO, accrualStatusDTO, siteDTO.getStatusHistory())
                .getIdentifier();
        clearInvestigatorsForPropTrialSite(studySiteID);
        StudyProtocolDTO spDTO = loadStudyProtocolDTO();
        updateProgramCodes(spDTO);
        addInvestigator(spDTO, studySiteID);
    }

    private void updateProgramCodes(StudyProtocolDTO spDTO) throws PAException {

        List<Long> studyIds = Arrays.asList(Long.parseLong(studyProtocolId));
        Map<Long, ProgramCodeDTO> pgcIndex = new HashMap<Long, ProgramCodeDTO>();
        for (ProgramCodeDTO pgc : programCodeFamilyIndex.keySet()) {
            pgcIndex.put(pgc.getId(), pgc);
        }

        List<ProgramCodeDTO> programCodeDTOs = spDTO.getProgramCodes();
        if (CollectionUtils.isEmpty(programCodeDTOs)) {

            //add everything selected to study
            //group program codes by family to reduce the number of service calls
            Map<Long , List<ProgramCodeDTO>> familyGroupIndex = new HashMap<Long, List<ProgramCodeDTO>>();
            for(Long pgcId : finalProgramCodeIds) {
                ProgramCodeDTO pgc = pgcIndex.get(pgcId);
                Long familyPoId = programCodeFamilyIndex.get(pgc).getPoId();
                if (!familyGroupIndex.containsKey(familyPoId)) {
                    familyGroupIndex.put(familyPoId, new ArrayList<ProgramCodeDTO>());
                }
                familyGroupIndex.get(familyPoId).add(pgc);
            }
            for (Map.Entry<Long, List<ProgramCodeDTO>> entry : familyGroupIndex.entrySet())  {
                if (CollectionUtils.isNotEmpty(entry.getValue())) {
                    studyProtocolService.assignProgramCodesToTrials(studyIds, entry.getKey(), entry.getValue());
                }
            }



        } else {
            List<Long> toRemove = ListUtils.subtract(initialProgramCodeIds, finalProgramCodeIds);
            if (CollectionUtils.isNotEmpty(toRemove)) {
                List<ProgramCodeDTO> programCodes = new ArrayList<ProgramCodeDTO>();
                for (Long pgcId : toRemove) {
                    ProgramCodeDTO pgc = pgcIndex.get(pgcId);
                    programCodes.add(pgc);
                }
                studyProtocolService.unassignProgramCodesFromTrials(studyIds, programCodes);
            }

            List<Long> toAdd = ListUtils.subtract(finalProgramCodeIds, initialProgramCodeIds);
            if (CollectionUtils.isNotEmpty(toAdd)) {
                //group program codes by family to reduce the number of service calls
                Map<Long , List<ProgramCodeDTO>> familyGroupIndex = new HashMap<Long, List<ProgramCodeDTO>>();
                for(Long pgcId : toAdd) {
                    ProgramCodeDTO pgc = pgcIndex.get(pgcId);
                    Long familyPoId = programCodeFamilyIndex.get(pgc).getPoId();
                    if (!familyGroupIndex.containsKey(familyPoId)) {
                        familyGroupIndex.put(familyPoId, new ArrayList<ProgramCodeDTO>());
                    }
                    familyGroupIndex.get(familyPoId).add(pgc);
                }
                for (Map.Entry<Long, List<ProgramCodeDTO>> entry : familyGroupIndex.entrySet())  {
                    if (CollectionUtils.isNotEmpty(entry.getValue())) {
                        studyProtocolService.assignProgramCodesToTrials(studyIds, entry.getKey(), entry.getValue());
                    }
                }
            }
        }






    }
    
    private void clearInvestigatorsForPropTrialSite(Ii ssIi) throws PAException {
        List<StudySiteContactDTO> ssContDtoList = studySiteContactService
                .getByStudySite(ssIi);
        for (StudySiteContactDTO item : ssContDtoList) {
            studySiteContactService.delete(item.getIdentifier());
        }
    }

    /**
     * @return
     * @throws PAException
     */
    private Ii getHealthcareFacilityID() throws PAException {
        return paServiceUtil.getPoHcfIi(orgPoId);        
    }

    private void createSiteRecordOwnership(Ii ssIi, RegistryUser registryUser)
            throws PAException {
        registryUserService.assignSiteOwnership(registryUser.getId(),
                IiConverter.convertToLong(ssIi));
    }

    /**
     * @param studySiteID
     * @throws PAException
     */
    private void addInvestigator(StudyProtocolDTO spDTO, Ii studySiteID) throws PAException {
        Ii investigatorIi = IiConverter.convertToPoPersonIi(siteDTO
                .getInvestigatorId().toString());
        addInvestigator(spDTO, studySiteID, investigatorIi,
                StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode(),
                participatingSiteService.getParticipatingSite(studySiteID)
                        .getSiteOrgPoId());
    }

    @SuppressWarnings("deprecation")
    private StudySiteDTO getStudySite() {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO
                .setIdentifier(StringUtils.isNotBlank(siteDTO.getId()) ? IiConverter
                        .convertToStudySiteIi(Long.parseLong(siteDTO.getId()))
                        : IiConverter.convertToIi((Long) null));
        studySiteDTO.setStatusCode(CdConverter
                .convertToCd(FunctionalRoleStatusCode.PENDING));
        studySiteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(
                new Timestamp(new Date().getTime()), null));
        studySiteDTO
                .setStudyProtocolIdentifier(IiConverter
                        .convertToStudyProtocolIi(Long
                                .parseLong(studyProtocolId)));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter
                .convertToSt(siteDTO.getSiteLocalTrialIdentifier()));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt(siteDTO
                .getProgramCode()));
        if (StringUtils.isNotEmpty(siteDTO.getDateClosedforAccrual())
                && StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())) {
            studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(siteDTO.getDateOpenedforAccrual(),
                            siteDTO.getDateClosedforAccrual()));
        }
        if (StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())
                && StringUtils.isEmpty(siteDTO.getDateClosedforAccrual())) {
            studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(siteDTO.getDateOpenedforAccrual(), null));
        }
        return studySiteDTO;
    }

    @SuppressWarnings("deprecation")
    private StudySiteAccrualStatusDTO getStudySiteAccrualStatus() {
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode
                .getByCode(siteDTO.getRecruitmentStatus())));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp(siteDTO.getRecruitmentStatusDate())));
        return ssas;
    }
    
    private void addInvestigator(StudyProtocolDTO spDTO, Ii ssIi, Ii investigatorIi, String role,
            String poOrgId) throws PAException {
        ClinicalResearchStaffDTO crsDTO = paServiceUtil.getCrsDTO(
                investigatorIi, poOrgId);
        HealthCareProviderDTO hcpDTO = paServiceUtil.getHcpDTO(spDTO
                .getStudyProtocolType().getValue(), investigatorIi, poOrgId);
        participatingSiteService.addStudySiteInvestigator(ssIi, crsDTO, hcpDTO,
                null, role);
    }

    private StudyProtocolDTO loadStudyProtocolDTO() throws PAException {
       return studyProtocolService
               .getStudyProtocol(IiConverter.convertToStudyProtocolIi(Long.parseLong(studyProtocolId)));
    }

    /**
     * Add to the initial program code ids
     *
     * @param id - a program code id
     */
    public void addToInitialProgramCodeIds(Long id) {
        if (initialProgramCodeIds.indexOf(id) < 0) {
            initialProgramCodeIds.add(id);
        }
    }

    /**
     * Add to the finally selected program code ids
     *
     * @param id - a program code id;
     */
    public void addToFinalProgramCodeIds(Long id) {
        if (finalProgramCodeIds.indexOf(id) < 0) {
            finalProgramCodeIds.add(id);
        }
    }

    /**
     * Add to family program code index
     *
     * @param map - a map indexing family and program codes
     */
    public void addAllToFamilyProgramCodeIndex(Map<ProgramCodeDTO, FamilyDTO> map) {
        programCodeFamilyIndex.putAll(map);
    }
}
