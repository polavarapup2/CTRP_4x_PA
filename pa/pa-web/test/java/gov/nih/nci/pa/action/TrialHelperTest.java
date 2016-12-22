/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockCorrelationUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialHelperTest extends AbstractPaActionTest {

    TrialHelper trialHelper;
    Ii id = IiConverter.convertToIi(1L);
    StudyContactServiceLocal studyContactSvc = mock(StudyContactServiceLocal.class);
    @Before
    public void setup() throws Exception {
        PAServiceUtils svcUtils = mock(PAServiceUtils.class);
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn("NCT-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.CTEP_IDENTIFIER_TYPE))).thenReturn("CTEP-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.DCP_IDENTIFIER_TYPE))).thenReturn("DCP-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn("NCI-1");

        trialHelper = new TrialHelper();
        trialHelper.setCorrelationUtils(new MockCorrelationUtils());
        trialHelper.setPaServiceUtils(svcUtils);
        trialHelper.setOrgService(orgSvc);
        trialHelper.setLookupTableService(lookupSvc);

        List<StudyAlternateTitleDTO> studyAlternateTitlesList = new ArrayList<StudyAlternateTitleDTO>();
        StudyAlternateTitleDTO studyAlternateTitleDTO = new StudyAlternateTitleDTO();
        studyAlternateTitleDTO.setAlternateTitle(StConverter.convertToSt("Test"));
        studyAlternateTitleDTO.setCategory(StConverter.convertToSt("Other"));
        studyAlternateTitlesList.add(studyAlternateTitleDTO);        
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        getSession().setAttribute(Constants.STUDY_ALTERNATE_TITLES_LIST, studyAlternateTitlesList);
    }

    @Test
    public void testGetTrialDTO() throws Exception {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getStudyProtocolService()).thenReturn(spSvc);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        when(PaRegistry.getStudyResourcingService()).thenReturn(studyResourcingSvc);
        StudyContactDTO dto = createStudyContactMock();
        when(PaRegistry.getStudySiteService()).thenReturn(studySiteSvc);
        StudyProtocolDTO spDTO = createStudyProtocol();
        StudyProtocolQueryDTO spqDto = new StudyProtocolQueryDTO();
        spqDto.setStudyProtocolId(1L);
        spqDto.setPiId(1L);
        List<StudySiteDTO> spDtos = new ArrayList<StudySiteDTO>();
        StudySiteDTO siteDto = new StudySiteDTO();
        siteDto.setIdentifier(id);
        siteDto.setResearchOrganizationIi(id);
        spDtos.add(siteDto);
        when(spSvc.getStudyProtocol(id)).thenReturn(spDTO);
        when(protocolQueryServiceLocal.getTrialSummaryByStudyProtocolId(1L)).thenReturn(spqDto);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(spDtos);
        when(studyContactSvc.getResponsiblePartyContact(any(Ii.class))).thenReturn(dto);
        assertNotNull(trialHelper.getTrialDTO(IiConverter.convertToIi(1L), "Abstraction"));
        List<StudyResourcingDTO> srDTOList = createStudyResourcingDto();
        when(studyResourcingSvc.getSummary4ReportedResourcing(any(Ii.class))).thenReturn(srDTOList);
        assertNotNull(trialHelper.getTrialDTO(IiConverter.convertToIi(1L), "Validation"));
    }

    @Test
    public void testShouldRssOwnTrialYes() throws PAException {
        Organization rssOrg = new Organization();
        rssOrg.setName("American College of Surgeons Oncology Trials Group");
        when(orgSvc.getOrganizationByFunctionRole((Ii)anyObject(), (Cd)anyObject())).thenReturn(rssOrg);
        assertTrue(trialHelper.shouldRssOwnTrial(spId));
    }

    @Test
    public void testShouldRssOwnTrialNo() throws PAException {
        Organization rssOrg = new Organization();
        rssOrg.setName("Non Rss Org.");
        when(orgSvc.getOrganizationByFunctionRole((Ii)anyObject(), (Cd)anyObject())).thenReturn(rssOrg);
        assertFalse(trialHelper.shouldRssOwnTrial(spId));
    }

    @Test
    public void testSaveTrial() throws Exception {
        GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setOfficialTitle("Test");
        gtdDTO.setAcronym("test");
        gtdDTO.setKeywordText("key");
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setLocalProtocolIdentifier("1");
        gtdDTO.setNctIdentifier("test");
        gtdDTO.setSubmissionNumber(1);
        gtdDTO.setProprietarytrialindicator("false");
        trialHelper.saveTrial(IiConverter.convertToIi(1L),gtdDTO ,"Abstraction");
        gtdDTO.setSponsorIdentifier("1");
        gtdDTO.setCtGovXmlRequired(true);
        gtdDTO.setPiIdentifier("1");
        List<SummaryFourSponsorsWebDTO> webDtoList = new ArrayList<SummaryFourSponsorsWebDTO>();
        SummaryFourSponsorsWebDTO dto = new SummaryFourSponsorsWebDTO();
        dto.setRowId("1");
        dto.setOrgId("1");
        dto.setOrgName("OrgName");
        webDtoList.add(dto);
        gtdDTO.setSummaryFourOrgIdentifiers(webDtoList);
        gtdDTO.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.INSTITUTIONAL.getName());
        gtdDTO.setResponsiblePartyType("sponsor");
        trialHelper.saveTrial(IiConverter.convertToIi(1L),gtdDTO ,"Validation");
        gtdDTO.setSummaryFourFundingCategoryCode(null);
        trialHelper.saveTrial(IiConverter.convertToIi(1L),gtdDTO ,"Validation");
    }

    @Test
    public void testCreateOrUpdateCentralContact() throws Exception {
        GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setOfficialTitle("Test");
        gtdDTO.setAcronym("test");
        gtdDTO.setKeywordText("key");
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setLocalProtocolIdentifier("1");
        gtdDTO.setNctIdentifier("test");
        trialHelper.createOrUpdateCentralContact(IiConverter.convertToIi(1L),gtdDTO);

    }

    @Test
    public void testCreateStudyContactObj() throws Exception {
        GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setOfficialTitle("Test");
        gtdDTO.setAcronym("test");
        gtdDTO.setKeywordText("key");
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setLocalProtocolIdentifier("1");
        gtdDTO.setNctIdentifier("test");
        gtdDTO.setCentralContactIdentifier("2");
        gtdDTO.setCentralContactPhone("123456789");
        assertNotNull(trialHelper.createStudyContactObj(IiConverter.convertToIi("1"), new StudyContactDTO(), gtdDTO));
    }
    
    private StudyProtocolDTO createStudyProtocol() {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setIdentifier(id);
        spDTO.setOfficialTitle(StConverter.convertToSt("Title"));
        spDTO.setAcronym(StConverter.convertToSt("test"));
        spDTO.setKeywordText(StConverter.convertToSt("KeyWordText"));
        spDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.I));
        spDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode.PILOT));
        spDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(PrimaryPurposeAdditionalQualifierCode.ANCILLARY));
        spDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeAdditionalQualifierCode.OTHER));
        spDTO.setPrimaryPurposeOtherText(StConverter.convertToSt("Other Text"));
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
        spDTO.setSubmissionNumber(new Int());
        spDTO.setAmendmentNumber(StConverter.convertToSt("1"));
        spDTO.setProgramCodeText(StConverter.convertToSt("test"));
        return spDTO;
    }
    private DSet<Tel> setupScDto() throws URISyntaxException {
         DSet<Tel> telAd = new DSet<Tel>();
         Set<Tel> telSet = new HashSet<Tel>();
         TelEmail email = new TelEmail();
         email.setValue(new URI("mailto:X"));
         telSet.add(email);
         TelPhone phone = new TelPhone();
         phone.setValue(new URI("tel:111-222-3333"));
         telSet.add(phone);
         TelUrl url = new TelUrl();
         url.setValue(new URI("http://ctrp.com"));
         telSet.add(url);
         telAd.setItem(telSet);
         return telAd;
    }
    
    private StudyContactDTO createStudyContactMock() throws Exception {
        when(PaRegistry.getStudyContactService()).thenReturn(studyContactSvc);
        List<StudyContactDTO> srDtos = new ArrayList<StudyContactDTO>();
        StudyContactDTO dto = new StudyContactDTO();
        dto.setIdentifier(id);
        dto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        dto.setTelecomAddresses(setupScDto());
        srDtos.add(dto);
        when(studyContactSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(srDtos);
        return dto;
    }
    private List<StudyResourcingDTO> createStudyResourcingDto() throws Exception {
        List<StudyResourcingDTO> srDTOList = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = new StudyResourcingDTO();
        dto.setIdentifier(id);
        dto.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL));
        dto.setOrganizationIdentifier(id);
        srDTOList.add(dto);
        return srDTOList;
    }
}
