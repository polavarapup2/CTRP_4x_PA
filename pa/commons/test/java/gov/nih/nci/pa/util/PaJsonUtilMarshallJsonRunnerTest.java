package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import gov.nih.nci.pa.dto.AdditionalDesignDetailsDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;

@RunWith(Parameterized.class)
public class PaJsonUtilMarshallJsonRunnerTest {
    private Object obj;
    private String expectedResult;
    
    @Before
    public void setUp() {
    }
    
    public PaJsonUtilMarshallJsonRunnerTest(Object obj, String expectedResult) {
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
        String result3 ="{\"id\":\"12\",\"nci_id\":\"NCI-2014-00342\",\"study_protocol_id\":\"135258099\",\"date_updated\":\"2017-03-20 17:17:14 -0400\","
                + "\"model_description\":\"Desc1\",\"masking_description\":\"no masking\",\"no_masking\":\"true\"}";
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
        
        AdditionalDesignDetailsDTO  addDesignDetailsDto = new AdditionalDesignDetailsDTO();
        addDesignDetailsDto.setId("12");
        addDesignDetailsDto.setMaskingDescription("no masking");
        addDesignDetailsDto.setModelDescription("Desc1");
        addDesignDetailsDto.setDateUpdated("2017-03-20 17:17:14 -0400");
        addDesignDetailsDto.setNciId("NCI-2014-00342");
        addDesignDetailsDto.setNoMasking("true");
        addDesignDetailsDto.setStudyProtocolId("135258099");
        
        return Arrays.asList(new Object[][] {
            { additionalRegInfoDTO, result },
            { addTrialIndIdeDto, result2 },
            { addDesignDetailsDto, result3}
        });
    }

    @Test
    public void marshallTest() throws IOException {
        String jsonStr = PAJsonUtil.marshallJSON(obj);
        assertEquals(jsonStr, expectedResult);
    }

}
