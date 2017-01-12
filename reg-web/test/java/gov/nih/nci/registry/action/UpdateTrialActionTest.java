/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class UpdateTrialActionTest extends AbstractRegWebTest {
    private final UpdateTrialAction action = new UpdateTrialAction();
    /**
     * Initialization method.
     */
    @Before
    public void init() {
        action.prepare();
        action.setServletRequest(ServletActionContext.getRequest());
    }
    @Test
    public void testTrialActionProperty(){
       assertNull(action.getTrialAction());
       action.setTrialAction("trialAction");
       assertNotNull(action.getTrialAction());
    }
    @Test
    public void testStudyProtocolIdProperty(){
       assertNull(action.getStudyProtocolId ());
       action.setStudyProtocolId("studyProtocolId");
       assertNotNull(action.getStudyProtocolId());
    }
    @Test
    public void testPropertyIRBDoc() {
        assertNull(action.getIrbApproval());
        action.setIrbApproval(new File("irbApprovalFileName.doc"));
        assertNotNull(action.getIrbApproval());

    }
    @Test
    public void testProtocolFileNameProperty(){
        assertNull(action.getIrbApprovalFileName());
        action.setIrbApprovalFileName("irbApprovalFileName");
        assertNotNull(action.getIrbApprovalFileName());
    }
    @Test
    public void testTrialDTOProperty() {
        assertNotNull(action.getTrialDTO());
        action.setTrialDTO(null);
        assertNull(action.getTrialDTO());
    }
    @Test
    public void testCollaboratorsListProperty(){
        assertNotNull(action.getCollaborators());
        action.setCollaborators(null);
        assertNull(action.getCollaborators());
    }
    @Test
    public void testParticipatingSitesListProperty() {
        assertNotNull(action.getParticipatingSitesList());
        action.setParticipatingSitesList(null);
        assertNull(action.getParticipatingSitesList());
    }
    @Test
    public void testIndIdeUpdateDtosProperty() {
        assertNotNull(action.getIndIdeUpdateDtos());
        action.setIndIdeUpdateDtos(null);
        assertNull(action.getIndIdeUpdateDtos());
    }
    @Test
    public void testIndIdeAddDtosProperty() {
        assertNotNull(action.getIndIdeAddDtos());
        action.setIndIdeAddDtos(null);
        assertNull(action.getIndIdeAddDtos());
    }
    @Test
    public void testFundingAddDtosProperty() {
        assertNotNull(action.getFundingAddDtos());
        action.setFundingAddDtos(null);
        assertNull(action.getFundingAddDtos());
    }
    @Test
    public void testFundingDtosProperty() {
        assertNotNull(action.getFundingDtos());
        action.setFundingDtos(null);
        assertNull(action.getFundingDtos());
    }
    @Test
    public void testProgramcodenihselectedvalueProperty() {
        assertNull(action.getProgramcodenihselectedvalue());
        action.setProgramcodenihselectedvalue("programcodenihselectedvalue");
        assertNotNull(action.getProgramcodenihselectedvalue());
    }
    @Test
    public void testProgramcodenciselectedvalueProperty() {
        assertNull(action.getProgramcodenciselectedvalue());
        action.setProgramcodenciselectedvalue("programcodenciselectedvalue");
        assertNotNull(action.getProgramcodenciselectedvalue());
    }
    @Test
    public void testPaOrganizationDTOProperty() {
        assertNull(action.getPaOrganizationDTO());
        action.setPaOrganizationDTO(new PaOrganizationDTO());
        assertNotNull(action.getPaOrganizationDTO());
    }

    @Test
    public void testTrialFundingDTOProperty() {
        assertNull(action.getTrialFundingDTO());
        action.setTrialFundingDTO(new TrialFundingWebDTO());
        assertNotNull(action.getTrialFundingDTO());
    }
    @Test
    public void testTrialIndIdeDTOProperty() {
        assertNull(action.getTrialIndIdeDTO());
        action.setTrialIndIdeDTO(new TrialIndIdeDTO());
        assertNotNull(action.getTrialIndIdeDTO());
    }
    @Test
    public void testIndIdeUpdateDtosLenProperty() {
        assertEquals(0, action.getIndIdeUpdateDtosLen());
        action.setIndIdeUpdateDtosLen(2);
        assertEquals(2, action.getIndIdeUpdateDtosLen());
    }
    @Test
    public void testCancle() {
        assertEquals("redirect_to_search", action.cancel());
    }
    @Test
    public void testEdit() throws PAException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("edit", action.edit());
        tDto.setFundingDtos(getfundingDtos());
        tDto.setIndIdeDtos(getIndDtos());
        tDto.setFundingAddDtos(getfundingDtos());
        tDto.setIndIdeAddDtos(getIndDtos());
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgList.add(paOrgDto);
        tDto.setCollaborators(paOrgList);
        tDto.setParticipatingSites(paOrgList);
        tDto.setSelectedRegAuth("selectedRegAuth");
        tDto.setLst("lst");
        List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();
        regIdAuthOrgList.add(new RegulatoryAuthOrgDTO());
        tDto.setRegIdAuthOrgList(regIdAuthOrgList);
        List<TrialIndIdeDTO> indIdeUpdateDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIde("IND");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setNciDivProgHolder("nciDivProgHolder");
        indIdeDto.setExpandedAccess("Yes");
        indIdeDto.setExpandedAccessType("expandedAccessStatus");
        indIdeDto.setStudyProtocolId("1");
        indIdeDto.setProgramCode("programCode");
        indIdeDto.setIndIdeId("1");
        indIdeUpdateDtos.add(indIdeDto);
        tDto.setIndIdeUpdateDtos(indIdeUpdateDtos);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("edit", action.edit());
    }
    @Test
    public void testView() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("error", action.view());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        action.setStudyProtocolId(null);
        request.setupAddParameter("studyProtocolId", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.view();
    }
    @Test
    public void testReviewUpdate() throws URISyntaxException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setTrialDTO(getMockTrialDTO());
        action.getTrialDTO().setLst("");
        action.getTrialDTO().setSelectedRegAuth("");
        assertEquals("error", action.reviewUpdate());

        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute(Constants.COUNTRY_LIST, new ArrayList<CountryRegAuthorityDTO>());
        session.setAttribute(Constants.REG_AUTH_LIST, new ArrayList<RegulatoryAuthOrgDTO>());
        session.setAttribute(Constants.GRANT_ADD_LIST, new ArrayList<TrialFundingWebDTO>());
        session.setAttribute(Constants.INDIDE_ADD_LIST, new ArrayList<TrialIndIdeDTO>());
       
        
        action.setTrialDTO(getMockTrialDTO());
        action.getTrialDTO().setDataMonitoringCommitteeAppointedIndicator("dataMonitoringIndicator");
        action.getTrialDTO().setDelayedPostingIndicator("delayedPostingIndicator");
        action.getTrialDTO().setFdaRegulatoryInformationIndicator("fdaRegulatedInterventionIndicator");
        action.getTrialDTO().setSection801Indicator("section801Indicator");
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setIrbApprovalFileName("ProtocolDoc.doc");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("ProtocolDoc.doc");
        File f = new File(fileUrl.toURI());
        action.setIrbApproval(f);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("review", action.reviewUpdate());

        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute(Constants.COUNTRY_LIST, new ArrayList<CountryRegAuthorityDTO>());
        session.setAttribute(Constants.REG_AUTH_LIST, new ArrayList<RegulatoryAuthOrgDTO>());
        session.setAttribute(Constants.GRANT_ADD_LIST, new ArrayList<TrialFundingWebDTO>());
        session.setAttribute(Constants.INDIDE_ADD_LIST, new ArrayList<TrialIndIdeDTO>());
        request.setSession(session);
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setIrbApprovalFileName("ProtocolDoc.doc");
        action.setIrbApproval(new File("ProtocolDoc.doc"));
        ServletActionContext.setRequest(request);
        action.setTrialDTO(getMockTrialDTO());
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateNoAccrualDiseaseCodeSystem() throws URISyntaxException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute(Constants.COUNTRY_LIST, new ArrayList<CountryRegAuthorityDTO>());
        session.setAttribute(Constants.REG_AUTH_LIST, new ArrayList<RegulatoryAuthOrgDTO>());
        session.setAttribute(Constants.GRANT_ADD_LIST, new ArrayList<TrialFundingWebDTO>());
        session.setAttribute(Constants.INDIDE_ADD_LIST, new ArrayList<TrialIndIdeDTO>());
        action.setTrialDTO(getMockTrialDTO());
        action.getTrialDTO().setDataMonitoringCommitteeAppointedIndicator("dataMonitoringIndicator");
        action.getTrialDTO().setDelayedPostingIndicator("delayedPostingIndicator");
        action.getTrialDTO().setFdaRegulatoryInformationIndicator("fdaRegulatedInterventionIndicator");
        action.getTrialDTO().setSection801Indicator("section801Indicator");
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setIrbApprovalFileName("ProtocolDoc.doc");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("ProtocolDoc.doc");
        File f = new File(fileUrl.toURI());
        action.setIrbApproval(f);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("review", action.reviewUpdate());

        action.getTrialDTO().setAccrualDiseaseCodeSystem(null);
        assertEquals("error", action.reviewUpdate());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.accrualDiseaseCodeSystem"));
    }
    @Test
    public void testReviewUpdateWithCollaborators() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setFunctionalRole("functionalRole");
        paOrgList.add(paOrgDto);
        paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setFunctionalRole(null);
        paOrgList.add(paOrgDto);
        action.setCollaborators(paOrgList);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateWithParticipatingSite() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);

        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgList.add(paOrgDto);
        paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus(null);
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgList.add(paOrgDto);
        action.setParticipatingSitesList(paOrgList);
        session.setAttribute(Constants.PARTICIPATING_SITES_LIST, paOrgList);
        assertEquals("error", action.reviewUpdate());

        paOrgList = new ArrayList<PaOrganizationDTO>();
        paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate(null);
        paOrgList.add(paOrgDto);
        action.setParticipatingSitesList(paOrgList);
        session.setAttribute(Constants.PARTICIPATING_SITES_LIST, paOrgList);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateWithFundingDtos() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);

        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<TrialFundingWebDTO> fundingDtos = getfundingDtos();
        TrialFundingWebDTO dto = new TrialFundingWebDTO();
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());

        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("fundingMechanismCode");
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());

        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("fundingMechanismCode");
        dto.setNciDivisionProgramCode("nciDivisionProgramCode");
        dto.setStudyProtocolId("1");
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());

        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("fundingMechanismCode");
        dto.setNciDivisionProgramCode("nciDivisionProgramCode");
        dto.setNihInstitutionCode("nihInstitutionCode");
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateWithIndIdeDtos() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<TrialIndIdeDTO> indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDtos.add(new TrialIndIdeDTO());
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());

        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("holderType");
        indIdeDto.setExpandedAccess("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());

        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testUpdate() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setIdentifier("1");
        
        List<String> programCodesList = new ArrayList<String>();
        programCodesList.add("PG1");
        programCodesList.add("PG2");
        tDto.setProgramCodesList(programCodesList);
        
        session.setAttribute("trialDTO", tDto);
        
        
        
        List<ProgramCodeDTO> allProgramCodeList = new ArrayList<ProgramCodeDTO>();
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setId(1l);
        programCodeDTO.setProgramCode("PG1");
        programCodeDTO.setProgramName("Program Code 1");
        programCodeDTO.setActive(true);
        allProgramCodeList.add(programCodeDTO);
        
        programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setId(2l);
        programCodeDTO.setProgramCode("PG2");
        programCodeDTO.setProgramName("Program Code 2");
        programCodeDTO.setActive(true);
        allProgramCodeList.add(programCodeDTO);
        
        programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setId(3l);
        programCodeDTO.setProgramCode("PG3");
        programCodeDTO.setProgramName("Program Code 3");
        programCodeDTO.setActive(true);
        allProgramCodeList.add(programCodeDTO);
        
        programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setId(4l);
        programCodeDTO.setProgramCode("PG4");
        programCodeDTO.setProgramName("Program Code 4");
        programCodeDTO.setActive(true);
        allProgramCodeList.add(programCodeDTO);
        
        
        
        session.setAttribute(Constants.PROGRAM_CODES_LIST, allProgramCodeList);
        
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        
       
        assertEquals("redirect_to_search", action.update());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        tDto = getMockTrialDTO();
        tDto.setIdentifier("1");
        tDto.setDocDtos(getDocumentDtos());
        tDto.setResponsiblePartyType(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);
        tDto.setResponsiblePersonName("responsiblePersonName");
        tDto.setResponsiblePersonIdentifier("12");
        tDto.setResponsibleGenericContactName("responsibleGenericContactName");
        tDto.setIndIdeDtos(getIndDtos());
        tDto.setFundingAddDtos(getfundingDtos());
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("redirect_to_search", action.update());
    }
    @Test
    public void testUpdateWithSumm4AndGrants() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId(UUID.randomUUID().toString());
        summarySp.setOrgId("1");
        summarySp.setOrgName("summaryFourOrgName");
        tDto.getSummaryFourOrgIdentifiers().add(summarySp);
        tDto.setIdentifier("3");
        tDto.setFundingDtos(getfundingDtos());
        tDto.setIndIdeDtos(getIndDtos());
        tDto.setFundingAddDtos(getfundingDtos());
        tDto.setIndIdeAddDtos(getIndDtos());
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("redirect_to_search", action.update());
    }
    @Test
    public void testUpdateWithIndIde() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        List<TrialIndIdeDTO> indIdeUpdateDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIde("IND");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setNciDivProgHolder("nciDivProgHolder");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDto.setExpandedAccess("expandedAccessStatus");
        indIdeDto.setIndIdeId("1");
        indIdeUpdateDtos.add(indIdeDto);
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIdeId("2");
        indIdeDto.setIndIde("IDE");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setNihInstHolder("nihInstHolder");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDto.setExpandedAccess("expandedAccessStatus");
        indIdeUpdateDtos.add(indIdeDto);
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIdeId("2");
        indIdeDto.setIndIde("IDE");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setNihInstHolder("nihInstHolder");
        indIdeDto.setExpandedAccessType("No");
        indIdeDto.setExpandedAccess("");
        indIdeUpdateDtos.add(indIdeDto);
        tDto.setIndIdeUpdateDtos(indIdeUpdateDtos );
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("redirect_to_search", action.update());
    }
    @Test
    public void testUpdateWithCollaborator() {
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setFunctionalRole("functionalRole");
        paOrgDto.setId("1");
        paOrgList.add(paOrgDto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setCollaborators(paOrgList);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("redirect_to_search", action.update());
    }
    
    @Test
    public void testUpdateWithParticipatingSite() {
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgDto.setId("1");
        paOrgList.add(paOrgDto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setParticipatingSites(paOrgList);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());
        assertEquals("redirect_to_search", action.update());
    }
    
    @Test
    public void testUpdateProgramCode() throws PAException {
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgDto.setId("1");
        paOrgDto.setProgramCode("ProgramCodeFromUI");
        paOrgList.add(paOrgDto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setParticipatingSites(paOrgList);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setServletRequest(request);
        action.setInitialStatusHistory(action.getTrialDTO().getStatusHistory());  
        
        List<StudySiteDTO> ssList = new ArrayList<StudySiteDTO>();
        StudySiteDTO ssDto = new StudySiteDTO();
        ssDto.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        ssList.add(ssDto);
        when(action.getStudySiteToUpdate(paOrgList)).thenReturn(ssList);
        action.update();
        assertNotSame(ssList.get(0).getProgramCodeText(),paOrgList.get(0).getProgramCode());
        assertEquals(ssList.get(0).getIdentifier().getExtension(),paOrgList.get(0).getId());
    }

    @Test 
    public void validateSummaryFourInfoValidationPassed() {
        TrialDTO trial = getMockTrialDTO();
        action.setTrialDTO(trial);
        assertTrue(action.validateSummaryFourInfo());
    }
    
    @Test 
    public void  validateSummaryFourInfoIncorrectSummaryFourFundingCategoryCode() {
        TrialDTO trial = getMockTrialDTO();
        trial.setSummaryFourFundingCategoryCode(null);
        action.setTrialDTO(trial);
        assertFalse(action. validateSummaryFourInfo());
    }  
    
    
    @Test
    public void validateTrialNotUpdatableFieldsFailed() throws PAException, IOException {
        UpdateTrialAction action = mock(UpdateTrialAction.class);
        doCallRealMethod().when(action).validateTrial();
        when(action.validateSummaryFourInfo()).thenReturn(false);
        String result = action.validateTrial();
        assertEquals("Wrong error message returned",
                     "This trial cannot be updated at this time. Please contact us at ncictro@mail.nih.gov for further assistance regarding this trial. Please include the NCI Trial Identifier in your email.",
                     result);
    }

    @Test
    public void validateTrialBusinessRulesFailed() throws PAException, IOException {
        UpdateTrialAction action = mock(UpdateTrialAction.class);
        doCallRealMethod().when(action).validateTrial();
        when(action.validateSummaryFourInfo()).thenReturn(true);
        when(action.hasFieldErrors()).thenReturn(true);
        String result = action.validateTrial();
        assertEquals("Wrong error message returned",
                     "The form has errors and could not be submitted, please check the fields highlighted below as well as any general trial errors, if displayed.",
                     result);
    }

    @Test
    public void validateTrial() throws PAException, IOException {
        UpdateTrialAction action = mock(UpdateTrialAction.class);
        doCallRealMethod().when(action).validateTrial();
        when(action.validateSummaryFourInfo()).thenReturn(true);
        when(action.hasFieldErrors()).thenReturn(true);

        action.validateTrial();

        InOrder inOrder = inOrder(action);
        inOrder.verify(action).clearErrorsAndMessages();
        inOrder.verify(action).validateSummaryFourInfo();
        inOrder.verify(action).enforceBusinessRules();
        inOrder.verify(action).hasFieldErrors();
    }
    
    @Test
    public void testGetFlattenedRemainingFieldErrors() {
        UpdateTrialAction action = new UpdateTrialAction();
        action.prepare();
        action.clearFieldErrors();
        action.addFieldError("field1", "error1");
        action.addFieldError("field2", "error2");
        
        Collection<String> c = action.getFlattenedRemainingFieldErrors();
        assertEquals(2, c.size());
        Iterator<String> iterator = action.getFlattenedRemainingFieldErrors().iterator();
        assertEquals("error1", iterator.next());
        assertEquals("error2", iterator.next());
        
        action.getFieldErrors().get("field1");
        c = action.getFlattenedRemainingFieldErrors();
        assertEquals(1, c.size());
        iterator = action.getFlattenedRemainingFieldErrors().iterator();        
        assertEquals("error2", iterator.next());
                
    }
    
     
}
