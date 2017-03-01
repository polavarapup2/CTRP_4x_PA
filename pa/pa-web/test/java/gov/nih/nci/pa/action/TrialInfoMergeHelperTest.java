package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAWebUtil;
import gov.nih.nci.pa.util.RestClient;

import org.junit.Before;
import org.junit.Test;

public class TrialInfoMergeHelperTest {
    private Long studyprotocolId = 1L;
    private TrialInfoMergeHelper helper = new TrialInfoMergeHelper();
    private AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
    private RegulatoryAuthorityWebDTO webDto = new RegulatoryAuthorityWebDTO();
    private RestClient client = mock(RestClient.class);

    @Before
    public void setUp() throws Exception {
        helper.setClient(client);
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("true");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("1234455");
        when(client.sendHTTPRequest("", "GET", null)).thenReturn(
                PAWebUtil.marshallJSON(additionalRegInfoDTO));

    }

    @Test
    public void mergeRegulatoryInfoReadTest() throws PAException {

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

}
