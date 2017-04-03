package gov.nih.nci.pa.dto;

import java.util.Map;

/**
 *
 * @author Vinodh
 */
public class AdditionalTrialInfo {

    private AdditionalDesignDetailsDTO designDetailsDTO;
    private AdditionalEligibilityCriteriaDTO eligibilityCriteriaDTO;
    private AdditionalRegulatoryInfoDTO regulatoryInfoDTO;
    private Map<String, AdditionalTrialIndIdeDTO> trialIndIdeDTOMap;

    /**
     *
     * @return designDetailsDTO AdditionalDesignDetailsDTO
     */
    public AdditionalDesignDetailsDTO getDesignDetailsDTO() {
        return designDetailsDTO;
    }

    /**
     * set AdditionalDesignDetailsDTO
     * @param designDetailsDTO AdditionalDesignDetailsDTO
     */
    public void setDesignDetailsDTO(AdditionalDesignDetailsDTO designDetailsDTO) {
        this.designDetailsDTO = designDetailsDTO;
    }

    /**
     *
     * @return eligibilityCriteriaDTO EligibilityCriteriaDTO
     */
    public AdditionalEligibilityCriteriaDTO getEligibilityCriteriaDTO() {
        return eligibilityCriteriaDTO;
    }

    /**
     *
     * @param eligibilityCriteriaDTO AdditionalEligibilityCriteriaDTO
     */
    public void setEligibilityCriteriaDTO(AdditionalEligibilityCriteriaDTO eligibilityCriteriaDTO) {
        this.eligibilityCriteriaDTO = eligibilityCriteriaDTO;
    }

    /**
     *
     * @return regulatoryInfoDTO AdditionalRegulatoryInfoDTO
     */
    public AdditionalRegulatoryInfoDTO getRegulatoryInfoDTO() {
        return regulatoryInfoDTO;
    }

    /**
     *
     * @param regulatoryInfoDTO AdditionalRegulatoryInfoDTO
     */
    public void setRegulatoryInfoDTO(AdditionalRegulatoryInfoDTO regulatoryInfoDTO) {
        this.regulatoryInfoDTO = regulatoryInfoDTO;
    }

    /**
     *
     * Map of trial IND/IDE number to DTO
     * @return trialIndIdeDTOMap Map<String, AdditionalTrialIndIdeDTO>
     */
    public Map<String, AdditionalTrialIndIdeDTO> getTrialIndIdeDTOMap() {
        return trialIndIdeDTOMap;
    }

    /**
     *
     * @param trialIndIdeDTOMap Map<String, AdditionalTrialIndIdeDTO>
     */
    public void setTrialIndIdeDTOMap(Map<String, AdditionalTrialIndIdeDTO> trialIndIdeDTOMap) {
        this.trialIndIdeDTOMap = trialIndIdeDTOMap;
    }
}
