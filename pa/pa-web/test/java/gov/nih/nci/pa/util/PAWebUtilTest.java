package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

public class PAWebUtilTest {
    @Test
    public void marshallJSONTest() throws JsonGenerationException, JsonMappingException, IOException {
        String expectedResult = "{\"study_protocol_id\":null,\"nci_id\":null,\"fda_regulated_drug\":\"true\",\"fda_regulated_device\":\"true\""
                + ",\"post_prior_to_approval\":\"true\""
                + ",\"ped_postmarket_surv\":\"true\",\"exported_from_us\":\"true\",\"date_updated\":\"1234455\"}";
        AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("true");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("1234455");
        String jsonStr = PAWebUtil.marshallJSON(additionalRegInfoDTO);
        assertEquals(jsonStr, expectedResult);
    }

    @Test
    public void unmarshallJSONTest() {
        String actualString = "{\"study_protocol_id\":null,\"nci_id\":null,\"fda_regulated_drug\":\"true\",\"fda_regulated_device\":\"true\""
                + ",\"post_prior_to_approval\":\"true\""
                + ",\"ped_postmarket_surv\":\"true\",\"exported_from_us\":\"true\",\"date_updated\":\"1234455\"}";
        AdditionalRegulatoryInfoDTO dto = (AdditionalRegulatoryInfoDTO) PAWebUtil
                .unmarshallJSON(actualString, AdditionalRegulatoryInfoDTO.class);
        assertTrue(Boolean.valueOf(dto.getExported_from_us()));
        assertTrue(Boolean.valueOf(dto.getFda_regulated_device()));
        assertTrue(Boolean.valueOf(dto.getFda_regulated_drug()));
        assertTrue(Boolean.valueOf(dto.getPed_postmarket_surv()));
        assertTrue(Boolean.valueOf(dto.getPost_prior_to_approval()));
    }
}
