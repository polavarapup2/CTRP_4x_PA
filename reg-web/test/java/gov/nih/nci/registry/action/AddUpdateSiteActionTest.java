package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.opensymphony.xwork2.ActionSupport;

import javax.servlet.http.HttpSession;

/**
 * @author Denis G. Krylov
 * 
 */
public class AddUpdateSiteActionTest extends AbstractRegWebTest {
    private AddUpdateSiteAction action;

    private PAServiceUtils paServiceUtils;
    private TrialUtil trialUtil;
    private RegistryUserServiceLocal registryUserServiceLocal;
    private ParticipatingSiteServiceLocal participatingSiteServiceLocal;
    private StudySiteContactServiceLocal studySiteContactServiceLocal;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;
    private ProtocolQueryServiceLocal protocolQueryServiceLocal;
    private OrganizationEntityServiceRemote organizationEntityServiceRemote;
    private StatusTransitionService statusTransitionService;
    private FamilyProgramCodeService familyProgramCodeService;

    private SubmittedOrganizationDTO existentSiteDTO;
    private ClinicalResearchStaffDTO researchStaffDTO;
    private HealthCareProviderDTO healthCareProviderDTO;
    private FamilyDTO familyDTO;
    private Set<ProgramCodeDTO> programCodes;
    RegistryUser user;
    StudyProtocolDTO studyDTO;
    List<ProgramCodeDTO> masterPgcList;

    @Before
    public void before() throws PAException {
        paServiceUtils = mock(PAServiceUtils.class);
        trialUtil = mock(TrialUtil.class);
        registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
        participatingSiteServiceLocal = mock(ParticipatingSiteServiceLocal.class);
        studySiteContactServiceLocal = mock(StudySiteContactServiceLocal.class);
        studyProtocolServiceLocal = mock(StudyProtocolServiceLocal.class);
        protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
        statusTransitionService = mock(StatusTransitionService.class);
        familyProgramCodeService = mock(FamilyProgramCodeService.class);

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
       
        researchStaffDTO = new ClinicalResearchStaffDTO();
        healthCareProviderDTO = new HealthCareProviderDTO();
        when(paServiceUtils.getPoPersonEntity(any(Ii.class))).thenReturn(new PersonDTO());        
        when(paServiceUtils.getPoHcfIi("1")).thenReturn(
                IiConverter.convertToPoHealthCareFacilityIi("1"));
        when(
                paServiceUtils.getCrsDTO(IiConverter.convertToPoPersonIi("1"),
                        "1")).thenReturn(researchStaffDTO);
        when(
                paServiceUtils.getHcpDTO(any(String.class), any(Ii.class),
                        any(String.class))).thenReturn(healthCareProviderDTO);

        user = getRegistryUser();
        when(registryUserServiceLocal.getUser(any(String.class))).thenReturn(
                user);

        existentSiteDTO = getExistentSiteDTO();
        when(trialUtil.getSubmittedOrganizationDTO(eq(studySiteDTO)))
                .thenReturn(existentSiteDTO);

        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteDTO();
        participatingSiteDTO
                .setIdentifier(IiConverter.convertToStudySiteIi(1L));
        participatingSiteDTO.setSiteOrgPoId("1");
        when(
                participatingSiteServiceLocal.updateStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class))).thenReturn(
                participatingSiteDTO);
        when(
                participatingSiteServiceLocal
                        .updateStudySiteParticipantWithStatusHistory(
                                any(StudySiteDTO.class),
                                any(StudySiteAccrualStatusDTO.class),
                                any(Collection.class))).thenReturn(
                participatingSiteDTO);
        when(
                participatingSiteServiceLocal.createStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class), any(Ii.class)))
                .thenReturn(participatingSiteDTO);
        when(
                participatingSiteServiceLocal.createStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class), any(Collection.class), any(Ii.class)))
                .thenReturn(participatingSiteDTO);
        when(
                participatingSiteServiceLocal.getParticipatingSite(
                        any(Ii.class), any(String.class))).thenReturn(
                studySiteDTO);
        when(
                participatingSiteServiceLocal.getParticipatingSite(
                        any(Ii.class))).thenReturn(
                                participatingSiteDTO);   
        studyDTO = setupSpDto();
        when(
                studyProtocolServiceLocal.getStudyProtocol(eq(IiConverter
                        .convertToStudyProtocolIi(1L)))).thenReturn(studyDTO);
        StudyProtocolQueryDTO queryDTO = new StudyProtocolQueryDTO();
        when(protocolQueryServiceLocal.getTrialSummaryByStudyProtocolId(1L)).thenReturn(
                queryDTO);

        organizationEntityServiceRemote = new MockOrganizationEntityService();
        familyDTO = new FamilyDTO(1L);
        programCodes = new HashSet<>();
        familyDTO.setProgramCodes(programCodes);

        programCodes.add(createProgramCode(1L, "PG1", "ProgramCode1"));
        programCodes.add(createProgramCode(2L, "PG2", "ProgramCode2"));
        programCodes.add(createProgramCode(3L, "PG3", "ProgramCode3"));
        programCodes.add(createProgramCode(4L, "PG4", "ProgramCode4"));

        when(familyProgramCodeService.getFamilyDTOByPoId(1L)).thenReturn(familyDTO);

    }


    /**
     * @return
     */
    private RegistryUser getRegistryUser() {
        RegistryUser user = new RegistryUser();
        user.setId(1L);
        user.setAffiliateOrg("NCI");
        user.setAffiliatedOrganizationId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        return user;
    }

    /**
     * 
     */
    private SubmittedOrganizationDTO getExistentSiteDTO() {
        SubmittedOrganizationDTO dto = new SubmittedOrganizationDTO();
        dto.setDateClosedforAccrual("01/31/2012");
        dto.setDateOpenedforAccrual("01/01/2012");
        dto.setId("1");
        dto.setInvestigator("John Doe");
        dto.setInvestigatorId(1L);
        dto.setName("NCI");
        dto.setNameInvestigator("John Doe");
        dto.setProgramCode("PCODE");
        dto.setRecruitmentStatus("Active");
        dto.setRecruitmentStatusDate("01/15/2012");
        dto.setSiteLocalTrialIdentifier("LOCAL_ID");
        dto.setTargetAccrualNumber("1000");
        return dto;
    }

    /**
     * 
     */
    private void prepareAction() {
        action = new AddUpdateSiteAction();
        action.setTrialUtil(trialUtil);
        action.setPaServiceUtil(paServiceUtils);
        action.setRegistryUserService(registryUserServiceLocal);
        action.setParticipatingSiteService(participatingSiteServiceLocal);
        action.setStudySiteContactService(studySiteContactServiceLocal);
        action.setStudyProtocolService(studyProtocolServiceLocal);
        action.setProtocolQueryService(protocolQueryServiceLocal);
        action.setOrganizationService(organizationEntityServiceRemote);
        action.setStudyProtocolId("1");
        action.setStatusTransitionService(statusTransitionService);
        action.setFamilyProgramCodeService(familyProgramCodeService);
        action.setServletRequest(ServletActionContext.getRequest());
    }

    /**
     * @throws PAException
     * @throws NullifiedEntityException 
     * 
     */
    @Test
    public void testPopulateSiteDTO() throws PAException, NullifiedEntityException {
        prepareAction();
        action.populateSiteDTO();
        assertEquals(existentSiteDTO, action.getSiteDTO());
    }

    @Test
    public void testView() throws PAException {
        prepareAction();
        String fwd = action.view();
        HttpSession session = ServletActionContext.getRequest().getSession();

        assertEquals(existentSiteDTO, action.getSiteDTO());
        assertEquals(ActionSupport.SUCCESS, fwd);

        assertEquals("true", String.valueOf(session.getAttribute("CANCER_TRIAL")));
        assertEquals(
                existentSiteDTO, session.getAttribute(TrialUtil.SESSION_TRIAL_SITE_ATTRIBUTE));
    }


    @Test
    public void testViewNonCancerTrial() throws PAException {
        prepareAction();
        user.setAffiliatedOrganizationId(12L);
        String fwd = action.view();

        assertEquals(ActionSupport.SUCCESS, fwd);
        HttpSession session = ServletActionContext.getRequest().getSession();
        assertEquals("false", String.valueOf(session.getAttribute("CANCER_TRIAL")));
        assertNull(session.getAttribute("FAMILY_ID"));
        assertTrue(((List) session.getAttribute("PGC_MASTER_LIST")).isEmpty());
        assertTrue(((List) session.getAttribute("PGC_ID_LIST")).isEmpty());
        assertEquals(
                existentSiteDTO,
                session.getAttribute(TrialUtil.SESSION_TRIAL_SITE_ATTRIBUTE));
    }

    @Test
    public void testSuccessfulUpdateSite() throws PAException {
        prepareAction();

        action.setSiteDTO(existentSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.SUCCESS, fwd);
        assertEquals(
                action.getText("add.site.updateSuccess"),
                ServletActionContext.getRequest().getSession()
                        .getAttribute(AddUpdateSiteAction.SUCCESS_MESSAGE_KEY));
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());

        ArgumentCaptor<StudySiteDTO> ssDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<StudySiteAccrualStatusDTO> accDTO = ArgumentCaptor
                .forClass(StudySiteAccrualStatusDTO.class);
        ArgumentCaptor<Collection> hist = ArgumentCaptor
                .forClass(Collection.class);

        verify(participatingSiteServiceLocal, times(1))
                .updateStudySiteParticipantWithStatusHistory(ssDTO.capture(), accDTO.capture(), hist.capture());
        verify(participatingSiteServiceLocal, times(1))
                .addStudySiteInvestigator(
                        IiConverter.convertToStudySiteIi(1L),
                        researchStaffDTO,
                        healthCareProviderDTO,
                        null,
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR
                                .getCode());
        assertEquals(IiConverter.convertToStudySiteIi(1L), ssDTO.getValue()
                .getIdentifier());
        assertEquals(IiConverter.convertToStudyProtocolIi(1L), ssDTO.getValue()
                .getStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("LOCAL_ID"), ssDTO.getValue()
                .getLocalStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("PCODE"), ssDTO.getValue()
                .getProgramCodeText());

        assertEquals(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE),
                accDTO.getValue().getStatusCode());
        assertEquals(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("01/15/2012")), accDTO.getValue()
                .getStatusDate());
    }

    @Test
    public void testSuccessfulAddSite() throws PAException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);
        newSiteDTO.setId(null);

        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.SUCCESS, fwd);
        assertEquals(
                action.getText("add.site.success"),
                ServletActionContext.getRequest().getSession()
                        .getAttribute(AddUpdateSiteAction.SUCCESS_MESSAGE_KEY));
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());

        ArgumentCaptor<StudySiteDTO> ssDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<StudySiteAccrualStatusDTO> accDTO = ArgumentCaptor
                .forClass(StudySiteAccrualStatusDTO.class);
        ArgumentCaptor<Ii> ii = ArgumentCaptor.forClass(Ii.class);
        ArgumentCaptor<Collection> statusHistory = ArgumentCaptor.forClass(Collection.class);

        verify(participatingSiteServiceLocal, times(1))
                .createStudySiteParticipant(ssDTO.capture(), accDTO.capture(),
                        statusHistory.capture(), ii.capture());
        verify(participatingSiteServiceLocal, times(1))
                .addStudySiteInvestigator(
                        IiConverter.convertToStudySiteIi(1L),
                        researchStaffDTO,
                        healthCareProviderDTO,
                        null,
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR
                                .getCode());
        assertNull(ssDTO.getValue().getIdentifier().getExtension());
        assertEquals(IiConverter.convertToStudyProtocolIi(1L), ssDTO.getValue()
                .getStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("LOCAL_ID"), ssDTO.getValue()
                .getLocalStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("PCODE"), ssDTO.getValue()
                .getProgramCodeText());

        assertEquals(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE),
                accDTO.getValue().getStatusCode());
        assertEquals(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("01/15/2012")), accDTO.getValue()
                .getStatusDate());
    }

    @Test
    public void testAddSiteValidationErrors_enforcePartialRulesForProp1()
            throws PAException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);

        newSiteDTO.setId(null);
        newSiteDTO.setSiteLocalTrialIdentifier("");
        newSiteDTO.setInvestigatorId(null);
        newSiteDTO.setRecruitmentStatus("");
        newSiteDTO.setRecruitmentStatusDate("01/01/100");

        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        Map<String, List<String>> fieldErrs = action.getFieldErrors();
        assertEquals(fieldErrs.get("localIdentifier"),
                Arrays.asList("error.siteLocalTrialIdentifier.required"));
        assertEquals(fieldErrs.get("investigator"),
                Arrays.asList("error.selectedPersId.required"));
        assertEquals(
                fieldErrs.get("statusCode"),
                Arrays.asList("error.participatingOrganizations.recruitmentStatus"));
        assertEquals(fieldErrs.get("statusDate"),
                Arrays.asList("A valid Recruitment Status Date is required"));

    }
  
    
    @Test
    public void testAddSiteValidationErrors_nullifiedInvestigator()
            throws PAException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);
        newSiteDTO.setId(null);

        when(paServiceUtils.getPoPersonEntity(any(Ii.class))).thenReturn(null);
        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        Map<String, List<String>> fieldErrs = action.getFieldErrors();
        assertEquals(fieldErrs.get("investigator"),
                Arrays.asList("error.nullifiedInvestigator"));

    }

    @Test
    public void testAddSiteWithProgramCodes() throws Exception {
        prepareAction();
        //Given that a study exists with no previous program code association
        prepareProgramCodes();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);
        newSiteDTO.setId(null);

        action.setSiteDTO(newSiteDTO);

        //When the user selects PG1, PG2 and PG3 from screen
        action.setProgramCode("1,2,3");
        String fwd = action.save();
        assertEquals(ActionSupport.SUCCESS, fwd);
        assertEquals(
                action.getText("add.site.success"),
                ServletActionContext.getRequest().getSession()
                        .getAttribute(AddUpdateSiteAction.SUCCESS_MESSAGE_KEY));
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());

        //Then PG1, PG2 and PG3 must be assigned to study

        verify(studyProtocolServiceLocal, times(1)).assignProgramCodesToTrials(
                Arrays.asList(1L), 1L, Arrays.asList(new ProgramCodeDTO(1L, "PG1"),
                new ProgramCodeDTO(2L, "PG2"),
                new ProgramCodeDTO(3L, "PG3"))
        );

    }


    @Test
    public void testUpdateSiteWithProgramCodes() throws PAException {

        prepareAction();

        prepareProgramCodes();
        //Given a study exists with program codes PG1 and PG2
        studyDTO.setProgramCodes(Arrays.asList(masterPgcList.get(0), masterPgcList.get(1)));


        action.setSiteDTO(existentSiteDTO);
        //And user de-select PG2 and selects PG3
        action.setProgramCode("1,3");
        String fwd = action.save();
        assertEquals(ActionSupport.SUCCESS, fwd);
        assertEquals(
                action.getText("add.site.updateSuccess"),
                ServletActionContext.getRequest().getSession()
                        .getAttribute(AddUpdateSiteAction.SUCCESS_MESSAGE_KEY));
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());

        //Then PG2 is unassigned
        verify(studyProtocolServiceLocal, times(1)).unassignProgramCodesFromTrials(
                Arrays.asList(1L), Arrays.asList(new ProgramCodeDTO(2L, "PG2"))
        );

        //And PG3 is assigned
        verify(studyProtocolServiceLocal, times(1)).assignProgramCodesToTrials(
                Arrays.asList(1L), 1L, Arrays.asList(new ProgramCodeDTO(3L, "PG3"))
        );


    }

    private void prepareProgramCodes() {
        masterPgcList = new ArrayList<ProgramCodeDTO>();
        masterPgcList.add(createProgramCode(1L, "PG1", "Program Code1"));
        masterPgcList.add(createProgramCode(2L, "PG2", "Program Code2"));
        masterPgcList.add(createProgramCode(3L, "PG3", "Program Code3"));
        masterPgcList.add(createProgramCode(4L, "PG4", "Program Code4"));
        List<Long> selectedPgcIdList = new ArrayList<Long>();
        selectedPgcIdList.add(1L);
        selectedPgcIdList.add(2L);
        FamilyDTO f = new FamilyDTO(1L);
        f.setProgramCodes(new HashSet<ProgramCodeDTO>(masterPgcList));
        f.setId(1L);
        Map<ProgramCodeDTO, FamilyDTO> pgcFamilyIndex = new LinkedHashMap<ProgramCodeDTO, FamilyDTO>();
        for (ProgramCodeDTO p : f.getProgramCodes()) {
            pgcFamilyIndex.put(p, f);
        }
        ServletActionContext
                .getRequest()
                .getSession().setAttribute("PGC_MASTER_LIST", masterPgcList);
        ServletActionContext
                .getRequest()
                .getSession().setAttribute("PGC_ID_LIST", selectedPgcIdList);
        ServletActionContext
                .getRequest()
                .getSession().setAttribute("PGC_FAMILY_INDEX", pgcFamilyIndex);
        ServletActionContext
                .getRequest()
                .getSession().setAttribute("FAMILY_ID", 1L);
        ServletActionContext
                .getRequest()
                .getSession().setAttribute("isSiteAdmin", true);

    }


}
