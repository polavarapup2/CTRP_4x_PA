package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.ctrp.importtrials.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.webservices.converters.TrialRegisterationWebServiceDTOConverter;
import gov.nih.nci.pa.webservices.dto.NameDTO;
import gov.nih.nci.pa.webservices.dto.OrganizationDTO;
import gov.nih.nci.pa.webservices.dto.PersonDTO;
import gov.nih.nci.pa.webservices.dto.StudyProtocolWebServiceDTO;
import gov.nih.nci.pa.webservices.dto.StudySiteDTO;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma
 *
 */
public class TrialRegisterationWebServiceDTOConverterTest {
    private TrialRegisterationWebServiceDTOConverter converter = new TrialRegisterationWebServiceDTOConverter();
    
    @Before
    public void before() {
        //todo
    }
    
    private StudyProtocolWebServiceDTO setUpInterventionalStudyWSDTO() {
        InterventionalStudyProtocolDTO dto = new InterventionalStudyProtocolDTO();
        dto.setAcceptHealthyVolunteersIndicator(true);
        dto.setCompletionDate("December 2020");
        dto.setAcronym("acronym");
        dto.setPublicTitle("publicTitle");
        dto.setPublicDescription("publicDescription");
        dto.setCompletionDateTypeCode("Anticipated");
        dto.setDataMonitoringCommitteeAppointedIndicator(true);
        dto.setExpandedAccessIndicator(true);
        dto.setKeywordText("keywordText");
        dto.setNciId("NCI_2010_0001");
        dto.setOfficialTitle("officialTitle");
        dto.setAllocationCode("Randomized Controlled Trial");
        dto.setDesignConfigurationCode("Single Group");
        dto.setSecondaryIdentifiers(Arrays.asList((String) "OtherId"));
        dto.setBlindedRoleCode(Arrays.asList((String) "Subject"));
        return dto;
    }
    private StudyProtocolWebServiceDTO setUpNonInterventionalStudyWSDTO() {
        NonInterventionalStudyProtocolDTO dto = new NonInterventionalStudyProtocolDTO();
        dto.setAcceptHealthyVolunteersIndicator(true);
        dto.setCompletionDate("December 2020");
        dto.setAcronym("acronym");
        dto.setPublicTitle("publicTitle");
        dto.setPublicDescription("publicDescription");
        dto.setCompletionDateTypeCode("Anticipated");
        dto.setDataMonitoringCommitteeAppointedIndicator(true);
        dto.setExpandedAccessIndicator(true);
        dto.setKeywordText("keywordText");
        dto.setNciId("NCI_2010_0001");
        dto.setOfficialTitle("officialTitle");
        dto.setBiospecimenDescription("biospecimenDescription");
        dto.setBiospecimenRetentionCode("Samples With DNA");
        dto.setNumberOfGroups(1);
        dto.setStudyModelCode("Cohort");
        dto.setStartDate("June 01, 2017");
        dto.setTimePerspectiveCode("Prospective");
        dto.setSecondaryIdentifiers(Arrays.asList((String) "OtherId"));
        return dto;
    }
   
    private OrganizationDTO setupOrganizationDTOWS() {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setName("name");
        dto.setPostalAddress("unknown");
        dto.setTelecomAddress("unknown@gmail.com");
        return dto;
    }
    
    private StudySiteDTO setUpStudySiteDTO() {
        StudySiteDTO dto = new StudySiteDTO();
        dto.setLocalStudyProtocolIdentifier("Nct100000023");
        return dto;
    }
    
    private PersonDTO setUpPersonDto() {
        PersonDTO dto = new PersonDTO();
        NameDTO name = new NameDTO("first", "last", "middle", null, null);
        dto.setName(name);
        dto.setFullName("fullName");
        return dto;
    }
    
    @Test
    public void convertToIntStudyProtocol() throws ParseException {
        gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO inspdDTO = (gov
                .nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO) converter
                .convertToStudyProtocolDTO(setUpInterventionalStudyWSDTO(), new gov.nih.nci.pa.iso
                        .dto.InterventionalStudyProtocolDTO());
        assertTrue(inspdDTO != null);
        assertEquals(TsConverter.convertToString(inspdDTO.getCompletionDate()), "12/01/2020");
        assertEquals(StConverter.convertToString(inspdDTO.getPublicDescription()), "publicDescription");
        assertEquals(CdConverter.convertCdToString(inspdDTO.getAllocationCode()), "Randomized Controlled Trial");
    }
    
    @Test
    public void convertToNonIntStudyProtocol() throws ParseException {
        gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO inspdDTO = (gov.nih
                .nci.pa.iso.dto.NonInterventionalStudyProtocolDTO) converter
                .convertToStudyProtocolDTO(setUpNonInterventionalStudyWSDTO(), new gov
                        .nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO());
        assertTrue(inspdDTO != null);
        assertEquals(TsConverter.convertToString(inspdDTO.getCompletionDate()), "12/01/2020");
        assertEquals(StConverter.convertToString(inspdDTO.getPublicDescription()), "publicDescription");
        assertEquals(StConverter.convertToString(inspdDTO.getBiospecimenDescription()), "biospecimenDescription");
        assertEquals(CdConverter.convertCdToString(inspdDTO.getBiospecimenRetentionCode()), "Samples With DNA");
    }
    @Test
    public void convertToOrganizationDTOtest() {
        gov.nih.nci.services.organization.OrganizationDTO orgDto = converter
                .convertToOrganizationDTO(setupOrganizationDTOWS());
        assertTrue(orgDto != null);
        assertEquals(EnOnConverter.convertEnOnToString(orgDto.getName()), "name");
    }
    
    @Test
    public void convertToOrganizationDTOListTest() {
        List<gov.nih.nci.services.organization.OrganizationDTO> orgDtos = converter
                .convertToOrganizationDTOList(Arrays.asList((OrganizationDTO) setupOrganizationDTOWS()));
        assertTrue(orgDtos != null);
        assertEquals(EnOnConverter.convertEnOnToString(orgDtos.get(0).getName()), "name");
    }
    @Test
    public void convertToLeadOrgIDtest() {
        gov.nih.nci.pa.iso.dto.StudySiteDTO dto = converter.convertToLeadOrgID(setUpStudySiteDTO());
        assertTrue(dto != null);
        assertEquals(StConverter.convertToString(dto.getLocalStudyProtocolIdentifier()), "Nct100000023");
    }
    @Test
    public void convertToPersonDTOTest() {
        gov.nih.nci.services.person.PersonDTO dto = converter.convertToPersonDTO(setUpPersonDto());
        assertTrue(dto != null);
        assertEquals(EnPnConverter.convertToLastCommaFirstName(dto.getName()), "middle, first last");
    }
    
}
