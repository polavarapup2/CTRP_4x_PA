package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.dto.StudyIndldeWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAWebUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.RestClient;

public class TrialInfoMergeHelperTest {
    private Long studyprotocolId = 1L;
    private TrialInfoMergeHelper helper = new TrialInfoMergeHelper();
    private AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
    private RegulatoryAuthorityWebDTO webDto = new RegulatoryAuthorityWebDTO();
    private RestClient client = mock(RestClient.class);
    private String url;
    @Before
    public void setUp() throws PAException {
       url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl();
    }

/*    @Test
    public void mergeRegulatoryInfoReadTest() throws PAException, IOException {
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("true");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("2017-03-01 23:30:38 -0500");
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/1", "GET", null)).thenReturn(
                PAWebUtil.marshallJSON(additionalRegInfoDTO));
        helper.mergeRegulatoryInfoRead(
                IiConverter.convertToIi(studyprotocolId), webDto);
        assertEquals(webDto.getFdaRegulatedDevice(),
                additionalRegInfoDTO.getFda_regulated_device());
        assertEquals(webDto.getExportedFromUs(),
                additionalRegInfoDTO.getExported_from_us());
        assertEquals(webDto.getFdaRegulatedDrug(),
                additionalRegInfoDTO.getFda_regulated_drug());
        assertEquals(webDto.getPedPostmarketSurv(),
                additionalRegInfoDTO.getPed_postmarket_surv());
        assertEquals(webDto.getPostPriorToApproval(),
                additionalRegInfoDTO.getPost_prior_to_approval());
        assertEquals(webDto.getLastUpdatedDate(), additionalRegInfoDTO.getDate_updated());

    }
    @Test
    public void mergeRegulatoryInfoUpdateTest() throws PAException, IOException {
        helper.setClient(client);
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("false");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("2017-03-01 23:30:38 -0500");
        additionalRegInfoDTO.setStudy_protocol_id(1L);
        additionalRegInfoDTO.setNci_id("NCI-2000-12222");
        String reponse ="{\"study_protocol_id\":1,\"nci_id\":\"NCI-2000-12222\",\"fda_regulated_drug\":\"false\","
                + "\"fda_regulated_device\":\"true\",\"post_prior_to_approval\":\"true\",\"ped_postmarket_surv\":\"true\","
                + "\"exported_from_us\":\"true\",\"date_updated\":\"2017-03-01 23:30:38 -0500\"}";
        when(client.sendHTTPRequest(url, "POST", 
                PAWebUtil.marshallJSON(additionalRegInfoDTO))).thenReturn(reponse);
        webDto.setFdaRegulatedDevice("true");
        webDto.setFdaRegulatedDrug("false");
        webDto.setExportedFromUs("true");
        webDto.setPedPostmarketSurv("true");
        webDto.setPostPriorToApproval("true");
        webDto.setLastUpdatedDate("2017-03-01 23:30:38 -0500");
        helper.mergeRegulatoryInfoUpdate(IiConverter.convertToIi(studyprotocolId), "NCI-2000-12222", webDto);
        assertEquals(webDto.getFdaRegulatedDevice(),
                additionalRegInfoDTO.getFda_regulated_device());
        assertEquals(webDto.getExportedFromUs(),
                additionalRegInfoDTO.getExported_from_us());
        assertEquals(webDto.getFdaRegulatedDrug(),
                additionalRegInfoDTO.getFda_regulated_drug());
        assertEquals(webDto.getPedPostmarketSurv(),
                additionalRegInfoDTO.getPed_postmarket_surv());
        assertEquals(webDto.getPostPriorToApproval(),
                additionalRegInfoDTO.getPost_prior_to_approval());
        assertEquals(webDto.getLastUpdatedDate(), additionalRegInfoDTO.getDate_updated());
    }
    
    
   // @Test
    public void mergeRegulatoryInfoRead1Test() throws PAException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        helper.mergeRegulatoryInfoRead(IiConverter.convertToIi(11111L), webDto);
        assertEquals(webDto.getFdaRegulatedDevice(),
                additionalRegInfoDTO.getFda_regulated_device());
    }
    
    //@Test
    public void mergeRegulatoryInfoWrite1Test() throws PAException {
        RestClient client1 = new RestClient();
        helper.setClient(client1);
        webDto.setFdaRegulatedDevice("true");
        webDto.setFdaRegulatedDrug("true");
        webDto.setExportedFromUs("true");
        webDto.setPedPostmarketSurv("true");
        webDto.setPostPriorToApproval("true");
        webDto.setLastUpdatedDate("2017-03-01 23:30:38 -0500");
        helper.mergeRegulatoryInfoUpdate(IiConverter.convertToIi(169939706L), "NCI-2014-02277", webDto);
    }
    
    @Test
    public void noDataFromMicroServiceTest() throws PAException {
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/1", "GET", null)).thenReturn("");
        helper.mergeRegulatoryInfoRead(
                IiConverter.convertToIi(studyprotocolId), webDto);
        assertNull(webDto.getFdaRegulatedDevice());
        assertNull(webDto.getExportedFromUs());
        assertNull(webDto.getFdaRegulatedDrug());
        assertNull(webDto.getPedPostmarketSurv());
        assertNull(webDto.getPostPriorToApproval());
        assertNull(webDto.getLastUpdatedDate());
    }
    
    @Test(expected=PAException.class)
    public void microServiceUnavailableTest() throws PAException {
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/1", "GET", null)).thenThrow(new PAException("Error: Unable to get response from Rest server @ " + url));
        helper.mergeRegulatoryInfoRead(
                IiConverter.convertToIi(studyprotocolId), webDto);
    }
    
    @Test
    public void invalidFieldValuesFromMicroserviceTest() throws PAException {
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/1", "GET", null)).thenReturn("{\"study_protocol_id\":\"169939706\",\"nci_id\":\"NCI-2014-02277\",\"fda_regulated_drug\":\"truesed\",\"fda_regulated_device\":\"trueed\"}");
        helper.mergeRegulatoryInfoRead(
                IiConverter.convertToIi(studyprotocolId), webDto);
        assertNull(webDto.getFdaRegulatedDrug());
        assertNull(webDto.getFdaRegulatedDevice());
    }
    
    @Test
    public void incompatibleJsonFromMicroServiceTest() throws PAException {
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/1", "GET", null)).thenReturn("{\"study\":\"169939706\",\"nciid\":\"NCI-2014-02277\"}");
        helper.mergeRegulatoryInfoRead(
                IiConverter.convertToIi(studyprotocolId), webDto);
        assertNull(webDto.getFdaRegulatedDrug());
        assertNull(webDto.getFdaRegulatedDevice());
        assertNull(webDto.getPedPostmarketSurv());
        assertNull(webDto.getPostPriorToApproval());
        assertNull(webDto.getLastUpdatedDate());
    }
    
    @Test
    public void mergeTrialIndIdeInfoReadTest() throws PAException, IOException {
        StudyIndldeWebDTO studyIndldeWebDTO = new StudyIndldeWebDTO();
        studyIndldeWebDTO.setStudyProtocolIi(studyprotocolId + "");
        studyIndldeWebDTO.setId("123");
        
        AdditionalTrialIndIdeDTO addTrialIndIdeDto = new AdditionalTrialIndIdeDTO();
        addTrialIndIdeDto.setStudyProtocolId(Long.valueOf(studyIndldeWebDTO.getStudyProtocolIi()));
        addTrialIndIdeDto.setTrialIndIdeId(Long.valueOf(studyIndldeWebDTO.getId()));
        addTrialIndIdeDto.setExpandedAccessIndicator("Yes");
        addTrialIndIdeDto.setExpandedAccessNctId("NCT12345678");
        
        String responseStr = PAWebUtil.marshallJSON(addTrialIndIdeDto);
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/123", "GET", null)).thenReturn(responseStr);
        helper.mergeTrialIndIdeInfoRead(IiConverter.convertToIi(studyprotocolId), studyIndldeWebDTO);
        assertEquals(studyIndldeWebDTO.getStudyProtocolIi(),
                addTrialIndIdeDto.getStudyProtocolId() + "");
        assertEquals(studyIndldeWebDTO.getId(),
                addTrialIndIdeDto.getTrialIndIdeId() + "");
        assertEquals(studyIndldeWebDTO.getExpandedAccessIndicator(),
                addTrialIndIdeDto.getExpandedAccessIndicator());
        assertEquals(studyIndldeWebDTO.getExpandedAccessNctId(),
                addTrialIndIdeDto.getExpandedAccessNctId());
    }
    
    @Test
    public void mergeStudyProtocolTrialIndIdeInfoReadTest() throws IOException, PAException {
        String jsonStr = "[{\"study_protocol_id\":\"1\",\"trial_indide_id\":\"123\","
                + "\"expanded_access_indicator\":\"Yes\",\"expanded_access_nct_id\":\"NCT12345678\"},"
                +"{\"study_protocol_id\":\"1\",\"trial_indide_id\":\"223\","
                        + "\"expanded_access_indicator\":\"No\",\"expanded_access_nct_id\":\"NCT22345678\"}]";
        
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/1", "GET", null)).thenReturn(jsonStr);
        StudyIndldeWebDTO studyIndldeWebDTO = new StudyIndldeWebDTO();
        studyIndldeWebDTO.setStudyProtocolIi(studyprotocolId + "");
        studyIndldeWebDTO.setId("123");
        
        StudyIndldeWebDTO studyIndldeWebDTO1 = new StudyIndldeWebDTO();
        studyIndldeWebDTO1.setStudyProtocolIi(studyprotocolId + "");
        studyIndldeWebDTO1.setId("223");
        List<StudyIndldeWebDTO> studyIndideList = new ArrayList<StudyIndldeWebDTO>();
        studyIndideList.add(studyIndldeWebDTO);
        studyIndideList.add(studyIndldeWebDTO1);
        helper.mergeStudyProtocolTrialIndIdeInfoRead(IiConverter.convertToIi(studyprotocolId), studyIndideList);
        
        assertTrue(StringUtils.equals(studyIndideList.get(0).getId(), 123 + ""));
        assertTrue(StringUtils.equals(studyIndideList.get(0).getExpandedAccessIndicator(), "Yes"));
        assertTrue(StringUtils.equals(studyIndideList.get(0).getExpandedAccessNctId(), "NCT12345678"));
        
        assertTrue(StringUtils.equals(studyIndideList.get(1).getId(), 223 + ""));
        assertTrue(StringUtils.equals(studyIndideList.get(1).getExpandedAccessIndicator(), "No"));
        assertTrue(StringUtils.equals(studyIndideList.get(1).getExpandedAccessNctId(), "NCT22345678"));
    }
    
    @Test
    public void mergeTrialIndIdeInfoUpdateTest() throws PAException {
        helper.setClient(client);
        String jsonStr = "{\"study_protocol_id\":1,\"trial_indide_id\":123,"
                + "\"expanded_access_indicator\":\"Yes\",\"expanded_access_nct_id\":\"NCT12345678\",\"date_updated\":\"03/12/2017\"}";
        when(client.sendHTTPRequest(url, "POST", jsonStr)).thenReturn(jsonStr);
        StudyIndldeWebDTO webDTO = new StudyIndldeWebDTO();
        webDTO.setStudyProtocolIi(studyprotocolId + "");
        webDTO.setId("123");
        webDTO.setExpandedAccessIndicator("Yes");
        webDTO.setExpandedAccessNctId("NCT12345678");
        webDTO.setDateUpdated("03/12/2017");
        AdditionalTrialIndIdeDTO trialIndIdeDto = helper.mergeTrialIndIdeInfoUpdate(
                IiConverter.convertToIi(studyprotocolId), webDTO);
        assertEquals(webDTO.getId(), trialIndIdeDto.getTrialIndIdeId() + "");
        assertEquals(webDTO.getStudyProtocolIi(), trialIndIdeDto.getStudyProtocolId() + "");
        assertEquals(webDTO.getExpandedAccessNctId(), trialIndIdeDto.getExpandedAccessNctId());
        assertEquals(webDTO.getExpandedAccessIndicator(), trialIndIdeDto.getExpandedAccessIndicator());
    }*/
    
    @Test(expected=PAException.class)
    public void microServiceUnavailableTrialIndIdeTest() throws PAException {
        helper.setClient(client);
        when(client.sendHTTPRequest(url + "/null", "GET", null)).thenThrow(new PAException("Error: Unable to get response from Rest server @ " + url));
        helper.mergeTrialIndIdeInfoRead(IiConverter.convertToIi(studyprotocolId), new StudyIndldeWebDTO());
    }

}
