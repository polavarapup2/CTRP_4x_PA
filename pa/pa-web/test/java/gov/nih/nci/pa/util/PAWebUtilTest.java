package gov.nih.nci.pa.util;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

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
        String jsonString = "[{ \"study_protocol_id\":1234567, \"trial_indide_id\":12345, \"expanded_access_indicator\":\"Yes\", "
                + "\"expanded_access_nct_id\":\"NCT12345678\"}, "
                + "{\"trial_indide_id\":\"22345\", \"expanded_access_indicator\":\"Unknown\", "
                + "\"expanded_access_nct_id\":\"NCT22345678\"}, "
                + "{\"study_protocol_id\":2345646,\"nci_id\":null,\"fda_regulated_drug\":\"true\","
                        + "\"fda_regulated_device\":\"true\""
                        + ",\"post_prior_to_approval\":\"true\""
                        + ",\"ped_postmarket_surv\":\"true\",\"exported_from_us\":\"true\"}]";
        List<AdditionalTrialIndIdeDTO> trialIndIdeDtoList = (List<AdditionalTrialIndIdeDTO>) PAWebUtil.unmarshallJSON(
                jsonString, new TypeReference<List<AdditionalTrialIndIdeDTO>>() { });
        assertTrue(trialIndIdeDtoList != null && trialIndIdeDtoList.size() == 3);
        assertTrue(trialIndIdeDtoList.get(0).getTrialIndIdeId() == 12345);
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(0).getExpandedAccessIndicator(), "Yes"));
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(0).getExpandedAccessNctId(), "NCT12345678"));
        
        assertTrue(trialIndIdeDtoList.get(1).getTrialIndIdeId() == 22345);
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(1).getExpandedAccessIndicator(), "Unknown"));
        assertTrue(StringUtils.equals(trialIndIdeDtoList.get(1).getExpandedAccessNctId(), "NCT22345678"));
        
        assertTrue(trialIndIdeDtoList.get(2).getTrialIndIdeId() == null);
    }

}
