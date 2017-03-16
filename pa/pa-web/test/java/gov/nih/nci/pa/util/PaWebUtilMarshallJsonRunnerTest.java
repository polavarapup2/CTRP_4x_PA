package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;

@RunWith(Parameterized.class)
public class PaWebUtilMarshallJsonRunnerTest {
    private Object obj;
    private String expectedResult;
    
    @Before
    public void setUp() {
    }
    
    public PaWebUtilMarshallJsonRunnerTest(Object obj, String expectedResult) {
        this.obj = obj;
        this.expectedResult = expectedResult;
    }
    
    @Parameterized.Parameters
    public static Collection collectionTestData() {
        String result = "{\"study_protocol_id\":null,\"nci_id\":null,\"fda_regulated_drug\":\"true\","
                + "\"fda_regulated_device\":\"true\""
                + ",\"post_prior_to_approval\":\"true\""
                + ",\"ped_postmarket_surv\":\"true\",\"exported_from_us\":\"true\",\"date_updated\":\"1234455\",\"id\":\"1\"}";
        
        String result2 = "{\"id\":\"1\",\"study_protocol_id\":\"1\",\"trial_ide_ind_id\":\"123\",\"expanded_access_indicator\":\"Yes\","
                + "\"expanded_access_nct_id\":\"NCT12345678\",\"date_updated\":null}";
        AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("true");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("1234455");
        additionalRegInfoDTO.setId("1");
        
        AdditionalTrialIndIdeDTO addTrialIndIdeDto = new AdditionalTrialIndIdeDTO();
        addTrialIndIdeDto.setStudyProtocolId("1");
        addTrialIndIdeDto.setTrialIndIdeId("123");
        addTrialIndIdeDto.setExpandedAccessIndicator("Yes");
        addTrialIndIdeDto.setExpandedAccessNctId("NCT12345678");
        addTrialIndIdeDto.setId("1");
        return Arrays.asList(new Object[][] {
            { additionalRegInfoDTO, result },
            { addTrialIndIdeDto, result2 }
        });
    }

    @Test
    public void marshallTest() throws IOException {
        String jsonStr = PAWebUtil.marshallJSON(obj);
        assertEquals(jsonStr, expectedResult);
    }

}
