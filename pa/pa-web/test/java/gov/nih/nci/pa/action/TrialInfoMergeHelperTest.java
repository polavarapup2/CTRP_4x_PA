package gov.nih.nci.pa.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAWebUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.RestClient;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

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

    @Test
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

}
