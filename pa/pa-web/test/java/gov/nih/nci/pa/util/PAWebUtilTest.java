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
        String expectedResult = "{\"fdaRegulatedDrug\":\"true\",\"fdaRegulatedDevice\":\"true\",\"postPriorToApproval\":\"true\",\"pedPostmarketSurv\":\"true\",\"exportedFromUs\":\"true\"}";
        AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        additionalRegInfoDTO.setExportedFromUs("true");
        additionalRegInfoDTO.setFdaRegulatedDevice("true");
        additionalRegInfoDTO.setFdaRegulatedDrug("true");
        additionalRegInfoDTO.setPedPostmarketSurv("true");
        additionalRegInfoDTO.setPostPriorToApproval("true");
        String jsonStr = PAWebUtil.marshallJSON(additionalRegInfoDTO);
        assertEquals(jsonStr, expectedResult);
    }

    @Test
    public void unmarshallJSONTest() {
        String actualString = "{\"fdaRegulatedDrug\":\"true\",\"fdaRegulatedDevice\":\"true\",\"postPriorToApproval\":\"true\",\"pedPostmarketSurv\":\"true\",\"exportedFromUs\":\"true\"}";
        AdditionalRegulatoryInfoDTO dto = (AdditionalRegulatoryInfoDTO) PAWebUtil
                .unmarshallJSON(actualString, AdditionalRegulatoryInfoDTO.class);
        assertTrue(Boolean.valueOf(dto.getExportedFromUs()));
        assertTrue(Boolean.valueOf(dto.getFdaRegulatedDevice()));
        assertTrue(Boolean.valueOf(dto.getFdaRegulatedDrug()));
        assertTrue(Boolean.valueOf(dto.getPedPostmarketSurv()));
        assertTrue(Boolean.valueOf(dto.getPostPriorToApproval()));
    }
}
