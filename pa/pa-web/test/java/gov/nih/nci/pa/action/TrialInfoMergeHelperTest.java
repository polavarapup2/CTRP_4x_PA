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
        additionalRegInfoDTO.setExportedFromUs("true");
        additionalRegInfoDTO.setFdaRegulatedDevice("true");
        additionalRegInfoDTO.setFdaRegulatedDrug("true");
        additionalRegInfoDTO.setPedPostmarketSurv("true");
        additionalRegInfoDTO.setPostPriorToApproval("true");
        when(client.sendHTTPRequest("", "GET", null)).thenReturn(
                PAWebUtil.marshallJSON(additionalRegInfoDTO));

    }

    @Test
    public void mergeRegulatoryInfoReadTest() throws PAException {

        helper.mergeRegulatoryInfoRead(
                IiConverter.convertToIi(studyprotocolId), webDto);
        assertEquals(webDto.getFdaRegulatedDevice(),
                additionalRegInfoDTO.getFdaRegulatedDevice());
        assertEquals(webDto.getExportedFromUs(),
                additionalRegInfoDTO.getExportedFromUs());
        assertEquals(webDto.getFdaRegulatedDrug(),
                additionalRegInfoDTO.getFdaRegulatedDrug());
        assertEquals(webDto.getPedPostmarketSurv(),
                additionalRegInfoDTO.getPedPostmarketSurv());
        assertEquals(webDto.getPostPriorToApproval(),
                additionalRegInfoDTO.getPostPriorToApproval());

    }

}
