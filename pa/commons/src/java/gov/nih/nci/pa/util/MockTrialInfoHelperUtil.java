package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalDesignDetailsDTO;
import gov.nih.nci.pa.dto.AdditionalEligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinodh on 4/3/17.
 */
public class MockTrialInfoHelperUtil extends TrialInfoHelperUtil {

    @Override
    public AdditionalRegulatoryInfoDTO retrieveRegulatoryInfo(Ii studyProtocolIi, String nciId) throws PAException {
        AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("true");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("2017-03-01 23:30:38 -0500");
        additionalRegInfoDTO.setStudy_protocol_id(IiConverter.convertToString(studyProtocolIi));
        additionalRegInfoDTO.setNci_id(nciId);
        return additionalRegInfoDTO;
    }

    @Override
    public AdditionalRegulatoryInfoDTO mergeRegulatoryInfoUpdate(Ii studyProtocolIi, String nciId,
                     AdditionalRegulatoryInfoDTO regulatoryDto) throws PAException {
        return regulatoryDto;
    }

    @Override
    public AdditionalEligibilityCriteriaDTO retrieveEligibilityCriteria(Ii studyProtocolIi, String nciId)
            throws PAException {
        AdditionalEligibilityCriteriaDTO dto = new AdditionalEligibilityCriteriaDTO();
        dto.setDateUpdated("2017-03-01 23:30:38 -0500");
        dto.setGender("true");
        dto.setGenderEligibilityDescription("Description");
        dto.setNciId(nciId);
        dto.setStudyProtocolId(IiConverter.convertToString(studyProtocolIi));
        return dto;
    }

    @Override
    public AdditionalEligibilityCriteriaDTO mergeEligibilityCriteriaUpdate(Ii studyProtocolIi, String nciId,
                       AdditionalEligibilityCriteriaDTO eligibilityDto) throws PAException {
        return eligibilityDto;
    }

    @Override
    public AdditionalTrialIndIdeDTO retrieveTrialIndIdeById(Ii studyProtocolIi, String trialIndldeID)
            throws PAException {
        AdditionalTrialIndIdeDTO addTrialIndIdeDto = new AdditionalTrialIndIdeDTO();
        addTrialIndIdeDto.setStudyProtocolId(IiConverter.convertToString(studyProtocolIi));
        addTrialIndIdeDto.setTrialIndIdeId(trialIndldeID);
        addTrialIndIdeDto.setExpandedAccessIndicator("Yes");
        addTrialIndIdeDto.setExpandedAccessNctId("NCT12345678");
        return addTrialIndIdeDto;
    }

    @Override
    public List<AdditionalTrialIndIdeDTO> retrieveAllTrialIndIdeByStudyId(Ii studyProtocolIi) throws PAException {
        List<AdditionalTrialIndIdeDTO> lst = new ArrayList<>();
        lst.add(retrieveTrialIndIdeById(studyProtocolIi, "1"));
        return lst;
    }

    @Override
    public AdditionalTrialIndIdeDTO mergeTrialIndIdeInfoUpdate(Ii studyProtocolIi,
                     AdditionalTrialIndIdeDTO trialIndIdeDto) throws PAException {
        return trialIndIdeDto;
    }

    @Override
    public void deleteTrialIndIdeInfo(String msId) throws PAException {
        //do nothing
    }

    @Override
    public AdditionalDesignDetailsDTO retrieveDesignDetails(Ii studyProtocolIi, String nciId) throws PAException {
        AdditionalDesignDetailsDTO dto = new AdditionalDesignDetailsDTO();
        dto.setDateUpdated("2017-03-01 23:30:38 -0500");
        dto.setMaskingDescription("maskingDescription");
        dto.setModelDescription("modelDescription");
        dto.setNciId(nciId);
        dto.setNoMasking("True");
        dto.setStudyProtocolId(IiConverter.convertToString(studyProtocolIi));
        return dto;
    }

    @Override
    public AdditionalDesignDetailsDTO mergeDesignDetailsUpdate(Ii studyProtocolIi, String nciId,
                     AdditionalDesignDetailsDTO designDetailsDto) throws PAException {
        return designDetailsDto;
    }
}
