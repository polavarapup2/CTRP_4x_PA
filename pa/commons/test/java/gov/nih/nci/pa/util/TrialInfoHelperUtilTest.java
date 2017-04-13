package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalDesignDetailsDTO;
import gov.nih.nci.pa.dto.AdditionalEligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma
 *
 */
public class TrialInfoHelperUtilTest {
    private TrialInfoHelperUtil helper = new TrialInfoHelperUtil();
    private RestClient client = mock(RestClient.class);
    private String url;
    private Ii studyId;
    private Long studyprotocolId = 1L;
    @Before
    public void setUp() throws PAException {
       url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl();
       studyId = IiConverter.convertToIi(studyprotocolId);
       helper.setClient(client);
    }
    
    @Test
    public void retrieveRegulatoryInfoTest() throws PAException, IOException {
        AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        List<AdditionalRegulatoryInfoDTO> regulatoryDtoList = new ArrayList<AdditionalRegulatoryInfoDTO>();
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("true");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("2017-03-01 23:30:38 -0500");
        additionalRegInfoDTO.setStudy_protocol_id("1");
        additionalRegInfoDTO.setNci_id("NCI-2000-12222");
        regulatoryDtoList.add(additionalRegInfoDTO);
        when(client.sendHTTPRequest(url + "?study_protocol_id=1&nci_id=" + "NCI-2000-12222", "GET", null)).thenReturn(
                PAJsonUtil.marshallJSON(regulatoryDtoList));
        AdditionalRegulatoryInfoDTO dto = helper.retrieveRegulatoryInfo(studyId, "NCI-2000-12222");
        assertEquals(dto.getFda_regulated_device(),
                additionalRegInfoDTO.getFda_regulated_device());
        assertEquals(dto.getExported_from_us(),
                additionalRegInfoDTO.getExported_from_us());
        assertEquals(dto.getFda_regulated_drug(),
                additionalRegInfoDTO.getFda_regulated_drug());
        assertEquals(dto.getPed_postmarket_surv(),
                additionalRegInfoDTO.getPed_postmarket_surv());
        assertEquals(dto.getPost_prior_to_approval(),
                additionalRegInfoDTO.getPost_prior_to_approval());
        assertEquals(dto.getDate_updated(), additionalRegInfoDTO.getDate_updated());
    }

    @Test(expected=PAException.class)
    public void retrieveRegulatoryInfoExceptionTest() throws PAException, IOException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.retrieveRegulatoryInfo(studyId, "NCI-2000-12222");
    }
    
    @Test
    public void mergeRegulatoryInfoUpdateTest() throws PAException, IOException {
        AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("false");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("2017-03-01 23:30:38 -0500");
        additionalRegInfoDTO.setStudy_protocol_id("1");
        additionalRegInfoDTO.setNci_id("NCI-2000-12222");
        String reponse ="{\"study_protocol_id\":1,\"nci_id\":\"NCI-2000-12222\",\"fda_regulated_drug\":\"false\","
                + "\"fda_regulated_device\":\"true\",\"post_prior_to_approval\":\"true\",\"ped_postmarket_surv\":\"true\","
                + "\"exported_from_us\":\"true\",\"date_updated\":\"2017-03-01 23:30:38 -0500\"}";
        when(client.sendHTTPRequest(url, "POST", 
                PAJsonUtil.marshallJSON(additionalRegInfoDTO))).thenReturn(reponse);
        AdditionalRegulatoryInfoDTO dto = helper.mergeRegulatoryInfoUpdate(studyId, "NCI-2000-12222", additionalRegInfoDTO);
        assertEquals(dto.getFda_regulated_device(),
                additionalRegInfoDTO.getFda_regulated_device());
        assertEquals(dto.getExported_from_us(),
                additionalRegInfoDTO.getExported_from_us());
        assertEquals(dto.getFda_regulated_drug(),
                additionalRegInfoDTO.getFda_regulated_drug());
        assertEquals(dto.getPed_postmarket_surv(),
                additionalRegInfoDTO.getPed_postmarket_surv());
        assertEquals(dto.getPost_prior_to_approval(),
                additionalRegInfoDTO.getPost_prior_to_approval());
        assertEquals(dto.getDate_updated(), additionalRegInfoDTO.getDate_updated());
    }
    
    @Test(expected=PAException.class)
    public void mergeRegulatoryInfoUpdateErrorTest() throws PAException, IOException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.mergeRegulatoryInfoUpdate(studyId, "NCI-2000-12222", new AdditionalRegulatoryInfoDTO());
    }
    
    @Test
    public void retrieveEligibilityCriteriaTest() throws PAException, IOException {
        List<AdditionalEligibilityCriteriaDTO> eligibilityDtoList = new ArrayList<AdditionalEligibilityCriteriaDTO>();
        AdditionalEligibilityCriteriaDTO dto = new AdditionalEligibilityCriteriaDTO();
        dto.setDateUpdated("2017-03-01 23:30:38 -0500");
        dto.setGender("true");
        dto.setGenderEligibilityDescription("Description");
        dto.setNciId("NCI-2000-12222");
        dto.setStudyProtocolId("1");
        eligibilityDtoList.add(dto);
        when(client.sendHTTPRequest(url + "?study_protocol_id=1&nci_id=NCI-2000-12222", "GET", null)).thenReturn(
                PAJsonUtil.marshallJSON(eligibilityDtoList));
        AdditionalEligibilityCriteriaDTO returnDto = helper.retrieveEligibilityCriteria(studyId, "NCI-2000-12222");
        assertEquals(returnDto.getGender(),
                dto.getGender());
        assertEquals(returnDto.getGenderEligibilityDescription(),
                dto.getGenderEligibilityDescription());
        assertEquals(returnDto.getDateUpdated(),
                dto.getDateUpdated());
    }
    
    @Test(expected=PAException.class)
    public void retrieveEligibilityCriteriaErrorTest() throws PAException, IOException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.retrieveEligibilityCriteria(studyId, "NCI-2000-12222");
    }
    @Test
    public void mergeEligibilityCriteriaUpdateTest() throws PAException, IOException {
        AdditionalEligibilityCriteriaDTO dto = new AdditionalEligibilityCriteriaDTO();
        dto.setDateUpdated("2017-03-01 23:30:38 -0500");
        dto.setGender("true");
        dto.setGenderEligibilityDescription("Description");
        dto.setNciId("NCI-2000-12222");
        dto.setStudyProtocolId("1");
        String reponse ="{\"gender_based\":\"true\",\"gender_description\":\"Description\","
                + "\"date_updated\":\"2017-03-01 23:30:38 -0500\",\"study_protocol_id\":1,"
                + "\"nci_id\":\"NCI-2000-12222\"}";
        when(client.sendHTTPRequest(url, "POST", 
                PAJsonUtil.marshallJSON(dto))).thenReturn(reponse);
        AdditionalEligibilityCriteriaDTO returnDto = helper.mergeEligibilityCriteriaUpdate(studyId, "NCI-2000-12222", dto);
        assertEquals(returnDto.getGender(),
                dto.getGender());
        assertEquals(returnDto.getGenderEligibilityDescription(),
                "Description");
    }
    
    @Test(expected=PAException.class)
    public void mergeEligibilityCriteriaErrorUpdate() throws PAException, IOException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.mergeEligibilityCriteriaUpdate(studyId, "NCI-2000-12222", new AdditionalEligibilityCriteriaDTO());
    }
    
    @Test
    public void retrieveTrialIndIdeByIdTest() throws IOException, PAException {
        List<AdditionalTrialIndIdeDTO> trialIndIdeDtoList = new ArrayList<AdditionalTrialIndIdeDTO>();
        AdditionalTrialIndIdeDTO addTrialIndIdeDto = new AdditionalTrialIndIdeDTO();
        addTrialIndIdeDto.setStudyProtocolId("1");
        addTrialIndIdeDto.setTrialIndIdeId("123");
        addTrialIndIdeDto.setExpandedAccessIndicator("Yes");
        addTrialIndIdeDto.setExpandedAccessNctId("NCT12345678");
        trialIndIdeDtoList.add(addTrialIndIdeDto);
        String responseStr = PAJsonUtil.marshallJSON(trialIndIdeDtoList);
      
        when(client.sendHTTPRequest(url + "?study_protocol_id=1&trial_ide_ind_id=123", "GET", null)).thenReturn(responseStr);
        AdditionalTrialIndIdeDTO dto = helper.retrieveTrialIndIdeById(studyId, "123");
        assertEquals(dto.getStudyProtocolId(),
                addTrialIndIdeDto.getStudyProtocolId());
        assertEquals(dto.getTrialIndIdeId(),
                addTrialIndIdeDto.getTrialIndIdeId());
        assertEquals(dto.getExpandedAccessIndicator(),
                addTrialIndIdeDto.getExpandedAccessIndicator());
        assertEquals(dto.getExpandedAccessNctId(),
                addTrialIndIdeDto.getExpandedAccessNctId());
    }

    @Test(expected=PAException.class)
    public void retrieveTrialIndIdeByIdErrorTest() throws PAException, IOException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.retrieveTrialIndIdeById(studyId, "123");
    }
    
    @Test
    public void retrieveAllTrialIndIdeByStudyIdTest() throws PAException {
        String jsonStr = "[{\"study_protocol_id\":\"1\",\"trial_ide_ind_id\":\"123\","
                + "\"expanded_access_indicator\":\"Yes\",\"expanded_access_nct_id\":\"NCT12345678\"},"
                +"{\"study_protocol_id\":\"1\",\"trial_ide_ind_id\":\"223\","
                        + "\"expanded_access_indicator\":\"No\",\"expanded_access_nct_id\":\"NCT22345678\"}]";

        when(client.sendHTTPRequest(url + "?study_protocol_id=1", "GET", null)).thenReturn(jsonStr);

        List<AdditionalTrialIndIdeDTO> dtos = helper.retrieveAllTrialIndIdeByStudyId(studyId);
        
        assertTrue(StringUtils.equals(dtos.get(0).getTrialIndIdeId(),"123"));
        assertTrue(StringUtils.equals(dtos.get(0).getExpandedAccessIndicator(), "Yes"));
        assertTrue(StringUtils.equals(dtos.get(0).getExpandedAccessNctId(), "NCT12345678"));
        
        assertTrue(StringUtils.equals(dtos.get(1).getTrialIndIdeId(),"223"));
        assertTrue(StringUtils.equals(dtos.get(1).getExpandedAccessIndicator(), "No"));
        assertTrue(StringUtils.equals(dtos.get(1).getExpandedAccessNctId(), "NCT22345678"));
    }
    @Test(expected=PAException.class)
    public void retrieveAllTrialIndIdeByErrorStudyIdTest() throws PAException, IOException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.retrieveAllTrialIndIdeByStudyId(studyId);
    }
    
    @Test
    public void mergeTrialIndIdeInfoUpdateTest() throws PAException {
        String jsonStr = "{\"id\":null,\"study_protocol_id\":\"1\",\"trial_ide_ind_id\":\"123\",\"expanded_access_indicator\":\"Yes\",\"expanded_access_nct_id\":\"NCT12345678\",\"date_updated\":null}";
        AdditionalTrialIndIdeDTO addTrialIndIdeDto = new AdditionalTrialIndIdeDTO();
        addTrialIndIdeDto.setStudyProtocolId("1");
        addTrialIndIdeDto.setTrialIndIdeId("123");
        addTrialIndIdeDto.setExpandedAccessIndicator("Yes");
        addTrialIndIdeDto.setExpandedAccessNctId("NCT12345678");
        when(client.sendHTTPRequest(url, "POST", jsonStr)).thenReturn(jsonStr);
       
        AdditionalTrialIndIdeDTO dto = helper.mergeTrialIndIdeInfoUpdate(studyId, addTrialIndIdeDto);
        
        assertEquals(dto.getStudyProtocolId(), "1");
        assertEquals(dto.getTrialIndIdeId(), "123");
    }
    @Test(expected=PAException.class)
    public void mergeTrialIndIdeInfoUpdateTestError() throws PAException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.mergeTrialIndIdeInfoUpdate(studyId, new AdditionalTrialIndIdeDTO());
        
    }
    
    @Test(expected=PAException.class)
    public void deleteTrialIndIdeInfoTest() throws PAException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.deleteTrialIndIdeInfo("1");
    }
    
    @Test
    public void retrieveDesignDetailsTest() throws PAException, IOException {
        List<AdditionalDesignDetailsDTO> designDetailsDtoList = new ArrayList<AdditionalDesignDetailsDTO>();
        AdditionalDesignDetailsDTO dto = new AdditionalDesignDetailsDTO();
        dto.setDateUpdated("2017-03-01 23:30:38 -0500");
        dto.setMaskingDescription("maskingDescription");
        dto.setModelDescription("modelDescription");
        dto.setNciId("NCI-2000-12222");
        dto.setNoMasking("True");
        dto.setStudyProtocolId("1");
        designDetailsDtoList.add(dto);
        
        when(client.sendHTTPRequest(url + "?study_protocol_id=1&nci_id=NCI-2000-12222", "GET", null)).thenReturn(
                PAJsonUtil.marshallJSON(designDetailsDtoList));
       
        AdditionalDesignDetailsDTO returnDTO = helper.retrieveDesignDetails(studyId, "NCI-2000-12222");
        assertEquals(returnDTO.getMaskingDescription(),
                dto.getMaskingDescription());
        assertEquals(returnDTO.getModelDescription(),
                dto.getModelDescription());
        assertEquals(returnDTO.getNoMasking(),
                dto.getNoMasking());
    }
    
    @Test(expected=PAException.class)
    public void retrieveDesignDetailsErrorTest() throws PAException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.retrieveDesignDetails(studyId, "NCI-2000-12222");
    }
    
    @Test 
    public void mergeDesignDetailsUpdateTest() throws PAException, IOException {
        AdditionalDesignDetailsDTO dto = new AdditionalDesignDetailsDTO();
        dto.setDateUpdated("2017-03-01 23:30:38 -0500");
        dto.setMaskingDescription("Desc1");
        dto.setModelDescription("ModelDesc1");
        dto.setNciId("NCI-2000-12222");
        dto.setNoMasking("True");
        dto.setStudyProtocolId("1");

        String reponse ="{\"id\":null,\"nci_id\":\"NCI-2000-12222\",\"study_protocol_id\":\"1\",\"date_updated\":null,"
                + "\"model_description\":\"ModelDesc1\",\"masking_description\":\"Desc1\",\"no_masking\":\"True\"}";
        when(client.sendHTTPRequest(url, "POST", 
                PAJsonUtil.marshallJSON(dto))).thenReturn(reponse);
        
        AdditionalDesignDetailsDTO returndto = helper.mergeDesignDetailsUpdate(studyId, "NCI-2000-12222", dto);
        assertEquals(returndto.getMaskingDescription(),
                dto.getMaskingDescription());
        assertEquals(returndto.getModelDescription(),
                dto.getModelDescription());
    }
    @Test(expected=PAException.class)
    public void mergeDesignDetailsUpdateErrorTest() throws PAException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.mergeDesignDetailsUpdate(studyId, "NCI-2000-12222", new AdditionalDesignDetailsDTO());
    }
}
