package gov.nih.nci.pa.dto;

import java.util.Map;

/**
 * @author Vinodh
 */
public class AdditionalTrialInfo {

    private AdditionalDesignDetailsDTO designDetailsDTO;
    private AdditionalEligibilityCriteriaDTO eligibilityCriteriaDTO;
    private AdditionalRegulatoryInfoDTO regulatoryInfoDTO;
    private Map<String, AdditionalTrialIndIdeDTO> trialIndIdeDTOMap;


    public AdditionalDesignDetailsDTO getDesignDetailsDTO() {
        return designDetailsDTO;
    }

    public void setDesignDetailsDTO(AdditionalDesignDetailsDTO designDetailsDTO) {
        this.designDetailsDTO = designDetailsDTO;
    }

    public AdditionalEligibilityCriteriaDTO getEligibilityCriteriaDTO() {
        return eligibilityCriteriaDTO;
    }

    public void setEligibilityCriteriaDTO(AdditionalEligibilityCriteriaDTO eligibilityCriteriaDTO) {
        this.eligibilityCriteriaDTO = eligibilityCriteriaDTO;
    }

    public AdditionalRegulatoryInfoDTO getRegulatoryInfoDTO() {
        return regulatoryInfoDTO;
    }

    public void setRegulatoryInfoDTO(AdditionalRegulatoryInfoDTO regulatoryInfoDTO) {
        this.regulatoryInfoDTO = regulatoryInfoDTO;
    }

    public Map<String, AdditionalTrialIndIdeDTO> getTrialIndIdeDTOMap() {
        return trialIndIdeDTOMap;
    }

    public void setTrialIndIdeDTOMap(Map<String, AdditionalTrialIndIdeDTO> trialIndIdeDTOMap) {
        this.trialIndIdeDTOMap = trialIndIdeDTOMap;
    }
}
