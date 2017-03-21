package gov.nih.nci.pa.util;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import gov.nih.nci.pa.dto.AdditionalDesignDetailsDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;

public class PAWebUtilTest {

    @Test
    public void unmarshallJSONTest() {
        String actualString = "{\"study_protocol_id\":null,\"nci_id\":null,\"fda_regulated_drug\":\"true\","
                + "\"fda_regulated_device\":\"true\""
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
    
    @Test
    public void unmarshallJSONTrialIndIdeTest() {
        String jsonString = "[{\"study_protocol_id\":\"1234567\", \"trial_ide_ind_id\":\"12345\", \"expanded_access_indicator\":\"Yes\", "
                + "\"expanded_access_nct_id\":\"NCT12345678\"}, "
                + "{\"trial_ide_ind_id\":\"22345\", \"expanded_access_indicator\":\"Unknown\", "
                + "\"expanded_access_nct_id\":\"NCT22345678\"}, "
                + "{\"study_protocol_id\":2345646,\"nci_id\":null,\"fda_regulated_drug\":\"true\","
                        + "\"fda_regulated_device\":\"true\""
                        + ",\"post_prior_to_approval\":\"true\""
                        + ",\"ped_postmarket_surv\":\"true\",\"exported_from_us\":\"true\"}]";
        List<AdditionalTrialIndIdeDTO> trialIndIdeDtoList = (List<AdditionalTrialIndIdeDTO>) PAWebUtil.unmarshallJSON(
                jsonString, new TypeReference<List<AdditionalTrialIndIdeDTO>>() { });
        assertTrue(trialIndIdeDtoList != null && trialIndIdeDtoList.size() == 3);
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(0).getExpandedAccessIndicator(), "Yes"));
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(0).getExpandedAccessNctId(), "NCT12345678"));
        
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(1).getExpandedAccessIndicator(), "Unknown"));
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(1).getExpandedAccessNctId(), "NCT22345678"));
        
        assertTrue(trialIndIdeDtoList.get(2).getTrialIndIdeId() == null);
    }
    
    @Test
    public void unMarshallJsonDesignDetailsTest() {
        String jsonString = "[{\"model_description\":\"Desc1\",\"date_updated\":\"2017-03-20 17:17:14 -0400\",\"nci_id\":\"NCI-2014-00342\","
                + "\"no_masking\":\"true\",\"date_created\":\"2017-03-20 17:17:14 -0400\",\"study_protocol_id\":\"135258099\","
                + "\"id\":\"d0a9ba51bc6424a6f2a2\",\"masking_description\":\"no masking\"}]";
        List<AdditionalDesignDetailsDTO> dtoList = (List<AdditionalDesignDetailsDTO>) PAWebUtil.unmarshallJSON(
                jsonString, new TypeReference<List<AdditionalDesignDetailsDTO>>() { });
        assertTrue(dtoList != null && dtoList.size() == 1);
    }

}
