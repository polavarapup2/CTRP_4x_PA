package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.ctrp.importtrials.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.webservices.converters.TrialRegisterationWebServiceDTOConverter;
import gov.nih.nci.pa.webservices.dto.AgeDTO;
import gov.nih.nci.pa.webservices.dto.ArmDTO;
import gov.nih.nci.pa.webservices.dto.DocumentDTO;
import gov.nih.nci.pa.webservices.dto.NameDTO;
import gov.nih.nci.pa.webservices.dto.OrganizationDTO;
import gov.nih.nci.pa.webservices.dto.PersonDTO;
import gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.webservices.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.webservices.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.webservices.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.webservices.dto.StudyProtocolWebServiceDTO;
import gov.nih.nci.pa.webservices.dto.StudySiteDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    
    private ResponsiblePartyDTO setUpRespPartyDto() {
        ResponsiblePartyDTO dto = new ResponsiblePartyDTO();
        dto.setTitle("title");
        dto.setAffiliation(setupOrganizationDTOWS());
        dto.setInvestigator(setUpPersonDto());
        dto.setType("Sponsor");
        return dto;
    }
    
    private StudyOverallStatusDTO setUpOverallStatusDTO() {
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setReasonText("reasonText");
        dto.setStatusCode("In Review");
        dto.setStatusDate(new Date());
        return dto;
    }
    private DocumentDTO setUpDocumentDTO() {
        DocumentDTO dto = new DocumentDTO();
        dto.setFileName("fileName");
        dto.setText("text");
        dto.setActiveIndicator(true);
        dto.setTypeCode("protocolDoc");
        return dto;
    }
    private List<ArmDTO> setupArms() {
        List<ArmDTO> dtos = new ArrayList<ArmDTO>();
        ArmDTO dto = new ArmDTO();
        dto.setDescriptionText("descriptionText");
        dto.setId(1L);
        dto.setName("nameA");
        dto.setTypeCode("Experimental");
        dtos.add(dto);
        dto = new ArmDTO();
        dto.setDescriptionText("descriptionText1");
        dto.setId(1L);
        dto.setName("nameB");
        dto.setTypeCode("Active Comparator");
        dtos.add(dto);
        return dtos;
    }
    private List<StudyOutcomeMeasureDTO> setUpOutComes() {
        List<StudyOutcomeMeasureDTO> dtos = new ArrayList<StudyOutcomeMeasureDTO>();
        StudyOutcomeMeasureDTO dto = new StudyOutcomeMeasureDTO();
        dto.setDescription("description");
        dto.setDisplayOrder(1);
        dto.setName("nameA");
        dto.setPrimaryIndicator(true);
        dto.setSafetyIndicator(true);
        dto.setTypeCode("Primary");
        dtos.add(dto);
        dto = new StudyOutcomeMeasureDTO();
        dto.setDescription("description1");
        dto.setDisplayOrder(2);
        dto.setName("nameB");
        dto.setPrimaryIndicator(true);
        dto.setSafetyIndicator(true);
        dto.setTypeCode("Secondary");
        dtos.add(dto);
        return dtos;
    }
    private List<PlannedEligibilityCriterionDTO> setUpPlannedEligibility() {
        List<PlannedEligibilityCriterionDTO> dtos = new ArrayList<PlannedEligibilityCriterionDTO>();
        PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
        dto.setCategoryCode("Course");
        dto.setCdePublicIdentifier(1L);
        dto.setIdentifier(1L);
        dto.setEligibleGenderCode("M");
        dto.setDisplayOrder(1);
        dto.setTextDescription("textDescription");
        dto.setInclusionIndicator(false);
        dto.setOperator("+");
        AgeDTO ageDto = new AgeDTO("Years", BigDecimal.valueOf(1L), 1);
        dto.setMaxValue(ageDto);
        dto.setMinValue(ageDto);
        dtos.add(dto);
        dto = new PlannedEligibilityCriterionDTO();
        dto.setCategoryCode("Eligibility Criterion");
        dto.setCdePublicIdentifier(2L);
        dto.setIdentifier(2L);
        dto.setEligibleGenderCode("F");
        dto.setDisplayOrder(1);
        dto.setTextDescription("textDescription1");
        dto.setInclusionIndicator(false);
        dto.setOperator("-");
        ageDto = new AgeDTO("Years", BigDecimal.valueOf(2L), 2);
        dto.setMaxValue(ageDto);
        dto.setMinValue(ageDto);
        dtos.add(dto);
        return dtos;
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
    @Test
    public void convertToPartyDTOTest() {
        gov.nih.nci.pa.dto.ResponsiblePartyDTO dto = converter.convertToPartyDTO(setUpRespPartyDto());
        assertTrue(dto != null);
        assertEquals(EnOnConverter.convertEnOnToString(dto.getAffiliation().getName()), "name");
        assertEquals(EnPnConverter.convertToLastCommaFirstName(dto.getInvestigator().getName()), "middle, first last");
        assertEquals(dto.getType().getCode(), "sponsor");
    }
    
    @Test
    public void convertToOverallStatusDTO() {
        gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO dto = converter.convertToOverallStatusDTO(setUpOverallStatusDTO());
        assertTrue(dto != null);
        assertEquals(dto.getStatusCode().getCode(), "In Review");
        assertEquals(StConverter.convertToString(dto.getReasonText()), "reasonText");
    }
    @Test
    public void convertToDocumentTest() throws IOException {
        gov.nih.nci.pa.iso.dto.DocumentDTO dto = converter.convertToDocument(setUpDocumentDTO());
        assertTrue(dto != null);
        assertEquals(new String(EdConverter.convertToByte(dto.getText())),"text");
        assertEquals(dto.getTypeCode().getCode(), "protocolDoc");
    }
    @Test
    public void convertToArmsDTOListTest() {
        List<gov.nih.nci.pa.iso.dto.ArmDTO> dtos = converter.convertToArmsDTOList(setupArms());
        assertTrue(dtos != null);
        assertTrue(dtos.size() == 2);
        assertEquals(StConverter.convertToString(dtos.get(0).getName()),"nameA");
        assertEquals(dtos.get(0).getTypeCode().getCode(), "Experimental");
        assertEquals(StConverter.convertToString(dtos.get(1).getName()),"nameB");
        assertEquals(dtos.get(1).getTypeCode().getCode(), "Active Comparator");
    }
    
    @Test
    public void convertTOOutcomesDTOListTest() {
        List<gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO> dtos = converter
                .convertTOOutcomesDTOList(setUpOutComes());
        assertTrue(dtos != null);
        assertTrue(dtos.size() == 2);
        assertEquals(StConverter.convertToString(dtos.get(0).getName()),"nameA");
        assertEquals(dtos.get(0).getTypeCode().getCode(), "Primary");
        assertEquals(StConverter.convertToString(dtos.get(1).getName()),"nameB");
        assertEquals(dtos.get(1).getTypeCode().getCode(), "Secondary");
    }
    @Test
    public void convertToEligibilityDTOListTest() {
        List<gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO> dtos = converter
                .convertToEligibilityDTOList(setUpPlannedEligibility());
        assertTrue(dtos != null);
        assertTrue(dtos.size() == 2);
        assertEquals(dtos.get(0).getValue().getHigh().getPrecision().toString(),"1");
        assertEquals(dtos.get(1).getValue().getHigh().getPrecision().toString(),"2");
    }
    
    @Test
    public void convertSPSecondaryIdsTest() throws ParseException {
        gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO secIdsSP = new gov.nih.nci.pa.iso
        .dto.InterventionalStudyProtocolDTO();
        Set<Ii> secondaryIds = new LinkedHashSet<Ii>();
        secondaryIds.add(IiConverter.convertToOtherIdentifierIi("NCI-2000-000110"));
        secIdsSP.setSecondaryIdentifiers(DSetConverter
                .convertIiSetToDset(secondaryIds));
        gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO inspdDTO = (gov
                .nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO) converter
                .convertToStudyProtocolDTO(setUpInterventionalStudyWSDTO(), secIdsSP);
        assertTrue(inspdDTO != null);
        assertEquals(DSetConverter
                .convertDsetToIiSet(inspdDTO.getSecondaryIdentifiers()).iterator().next().getExtension().toString(),
                "NCI-2000-000110");
        assertEquals(StConverter.convertToString(inspdDTO.getPublicDescription()), "publicDescription");
        assertEquals(CdConverter.convertCdToString(inspdDTO.getAllocationCode()), "Randomized Controlled Trial");
    }
}
