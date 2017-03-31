package gov.nih.nci.pa.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalDesignDetailsDTO;
import gov.nih.nci.pa.dto.AdditionalEligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.dto.StudyIndldeWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

/**
 * 
 * @author Purnima, Reshma
 *
 */
public class TrialInfoMergeHelper {

    private static final Logger LOG = Logger.getLogger(TrialInfoMergeHelper.class);

    private TrialInfoHelperUtil trialInfoHelperUtil = new TrialInfoHelperUtil();

    /**
     * constructor
     */
    public TrialInfoMergeHelper() {
        super();
    }

    /**
     * 
     * @param studyProtocolIi
     *            studyProtocolIi
     * @param nciId the nciId
     * @param webDto
     *            webDto
     * @throws PAException
     *             PAException
     */
    public void mergeRegulatoryInfoRead(Ii studyProtocolIi, String nciId,
            RegulatoryAuthorityWebDTO webDto) throws PAException {
        LOG.info("Getting Regulatory data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));

        AdditionalRegulatoryInfoDTO regulatoryDto =
                trialInfoHelperUtil.retrieveRegulatoryInfo(studyProtocolIi, nciId);

        if (regulatoryDto != null) {
            if (PAJsonUtil.isValidBooleanString(regulatoryDto.getFda_regulated_drug())) {
                webDto.setFdaRegulatedDrug(regulatoryDto.getFda_regulated_drug());
            }
            if (PAJsonUtil.isValidBooleanString(regulatoryDto.getFda_regulated_device())) {
                webDto.setFdaRegulatedDevice(regulatoryDto.getFda_regulated_device());
            }
            if (PAJsonUtil.isValidBooleanString(regulatoryDto.getPed_postmarket_surv())) {
                webDto.setPedPostmarketSurv(regulatoryDto.getPed_postmarket_surv());
            }
            if (PAJsonUtil.isValidBooleanString(regulatoryDto.getExported_from_us())) {
                webDto.setExportedFromUs(regulatoryDto.getExported_from_us());
            }
            if (PAJsonUtil.isValidBooleanString(regulatoryDto.getPost_prior_to_approval())) {
                webDto.setPostPriorToApproval(regulatoryDto.getPost_prior_to_approval());
            }
            webDto.setLastUpdatedDate(regulatoryDto.getDate_updated());
            webDto.setId(regulatoryDto.getId());
        }
    }

    /**
     * 
     * @param studyProtocolIi
     *            the studyProtocolIi
     * @param nciId
     *            the nciId
     * @param webDto
     *            the webDto
     * @return AdditionalRegulatoryInfoDTO
     * @throws PAException
     *             PAException
     */
    public AdditionalRegulatoryInfoDTO mergeRegulatoryInfoUpdate(
            Ii studyProtocolIi, String nciId, RegulatoryAuthorityWebDTO webDto)
            throws PAException {
        LOG.info("Updating Regulatory data info to new DB"
                + IiConverter.convertToString(studyProtocolIi));
        AdditionalRegulatoryInfoDTO regulatoryDto = new AdditionalRegulatoryInfoDTO();
        regulatoryDto.setExported_from_us(webDto.getExportedFromUs());
        regulatoryDto.setFda_regulated_device(webDto.getFdaRegulatedDevice());
        regulatoryDto.setFda_regulated_drug(webDto.getFdaRegulatedDrug());
        regulatoryDto.setPed_postmarket_surv(webDto.getPedPostmarketSurv());
        regulatoryDto
                .setPost_prior_to_approval(webDto.getPostPriorToApproval());
        regulatoryDto.setDate_updated(webDto.getLastUpdatedDate());
        regulatoryDto.setStudy_protocol_id(IiConverter
                .convertToString(studyProtocolIi));
        regulatoryDto.setNci_id(nciId);
        regulatoryDto.setId(webDto.getId());

        return trialInfoHelperUtil.mergeRegulatoryInfoUpdate(studyProtocolIi, nciId, regulatoryDto);
    }


    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param nciId nciId
     * @return webDto
     * @throws PAException the exception
     */
    public ISDesignDetailsWebDTO mergeEligibilityCriteriaRead(Ii studyProtocolIi, String nciId) throws PAException {
        LOG.info("Getting Eligibility Criteria data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));

        ISDesignDetailsWebDTO webDto = new ISDesignDetailsWebDTO();

        AdditionalEligibilityCriteriaDTO eligibilityDto =
                trialInfoHelperUtil.retrieveEligibilityCriteria(studyProtocolIi, nciId);

        if (eligibilityDto != null) {
            if (PAJsonUtil.isValidBooleanString(eligibilityDto.getGender())) {
                webDto.setGender(eligibilityDto.getGender());
            }
            webDto.setGenderEligibilityDescription(eligibilityDto.getGenderEligibilityDescription());
            webDto.setLastUpdatedDate(eligibilityDto.getDateUpdated());
            webDto.setId(eligibilityDto.getId());
        }
        return webDto;
    }
    
    /**
     * 
     * @param studyProtocolIi studyProtocolIi
     * @param nciId nciId
     * @param webDto webDto
     * @return eligibilityDto 
     * @throws PAException PAException
     */
    public AdditionalEligibilityCriteriaDTO mergeEligibilityCriteriaUpdate(
            Ii studyProtocolIi, String nciId, ISDesignDetailsWebDTO webDto) throws PAException {
        LOG.info("Updating Eligibility criteria to new DB"
                + IiConverter.convertToString(studyProtocolIi));
        AdditionalEligibilityCriteriaDTO eligibilityDto = new AdditionalEligibilityCriteriaDTO();
        eligibilityDto.setGender(webDto.getGender());
        eligibilityDto.setGenderEligibilityDescription(webDto.getGenderEligibilityDescription());
        eligibilityDto.setStudyProtocolId(IiConverter.convertToString(studyProtocolIi));
        eligibilityDto.setNciId(nciId);
        eligibilityDto.setDateUpdated(webDto.getLastUpdatedDate());
        eligibilityDto.setId(webDto.getId());

        return  trialInfoHelperUtil.mergeEligibilityCriteriaUpdate(studyProtocolIi, nciId, eligibilityDto);
    }
    
    
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param webDto the webDto
     * @throws PAException exception
     */
    public void mergeTrialIndIdeInfoRead(Ii studyProtocolIi, StudyIndldeWebDTO webDto) throws PAException {
        LOG.info("Getting additional Trial IND/IDE info for IND/IDE id: " + webDto.getId());

        AdditionalTrialIndIdeDTO trialIndIdeDto =
                trialInfoHelperUtil.retrieveTrialIndIdeById(studyProtocolIi, webDto.getId());

        if (trialIndIdeDto != null) {
            webDto.setExpandedAccessIndicator(trialIndIdeDto.getExpandedAccessIndicator());
            webDto.setExpandedAccessNctId(trialIndIdeDto.getExpandedAccessNctId());
            webDto.setMsId(trialIndIdeDto.getId());
        }
    }
    
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param studyIndideList the studyIndideList 
     * @throws PAException exception
     */
    public void mergeStudyProtocolTrialIndIdeInfoRead(Ii studyProtocolIi, List<StudyIndldeWebDTO> studyIndideList) 
            throws PAException {
        String studyProtocolIdStr = IiConverter.convertToString(studyProtocolIi);
        LOG.info("Getting additional Trial IND/IDE info for all the IND/IDE records of study protocol id: " 
                        + studyProtocolIdStr);
        List<AdditionalTrialIndIdeDTO> trialIndIdeDtoList =
                trialInfoHelperUtil.retrieveAllTrialIndIdeByStudyId(studyProtocolIi);

        if (trialIndIdeDtoList != null && !trialIndIdeDtoList.isEmpty()) {
            for (StudyIndldeWebDTO studyIndide : studyIndideList) {
                String studyIndIdeId = studyIndide.getId();
                for (AdditionalTrialIndIdeDTO trialDto : trialIndIdeDtoList) {
                    if (trialDto.getTrialIndIdeId() != null 
                            && StringUtils.equals(studyIndIdeId, trialDto.getTrialIndIdeId() + "")) {
                        studyIndide.setExpandedAccessIndicator(trialDto.getExpandedAccessIndicator());
                        studyIndide.setExpandedAccessNctId(trialDto.getExpandedAccessNctId());
                        studyIndide.setMsId(trialDto.getId());
                        break;
                    }
                }
            }
        }
    }

    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param webDto the webDto
     * @return AdditionalTrialIndIdeDTO
     * @throws PAException exception
     */
    public AdditionalTrialIndIdeDTO mergeTrialIndIdeInfoUpdate(Ii studyProtocolIi, 
            StudyIndldeWebDTO webDto) throws PAException {
        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        LOG.info("Updating Trial IND/IDE info to new DB for :"
                + studyProtocolId + ", Trial IND/IDE id: " + webDto.getId());
        AdditionalTrialIndIdeDTO trialIndIdeDto = new AdditionalTrialIndIdeDTO();
        trialIndIdeDto.setTrialIndIdeId(webDto.getId());
        trialIndIdeDto.setExpandedAccessIndicator(webDto.getExpandedAccessIndicator());
        trialIndIdeDto.setExpandedAccessNctId(webDto.getExpandedAccessNctId());
        trialIndIdeDto.setDateUpdated(webDto.getDateUpdated());
        trialIndIdeDto.setStudyProtocolId(studyProtocolId);
        trialIndIdeDto.setId(webDto.getMsId());

        return trialInfoHelperUtil.mergeTrialIndIdeInfoUpdate(studyProtocolIi, trialIndIdeDto);
    }
    /**
     * 
     * @param msId msId
     * @throws PAException the PAException
     */
    public void deleteTrialIndIdeInfo(String msId) throws PAException {
        trialInfoHelperUtil.deleteTrialIndIdeInfo(msId);
    }
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param webDTO webDTO
     * @param nciId nciId
     * @throws PAException the exception
     */
    public void mergeDesignDetailsRead(Ii studyProtocolIi, 
            ISDesignDetailsWebDTO webDTO, String nciId) throws PAException {
        LOG.info("Getting Design Details data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));

        AdditionalDesignDetailsDTO designDetailsDto =
                trialInfoHelperUtil.retrieveDesignDetails(studyProtocolIi, nciId);

        if (designDetailsDto != null) {
            webDTO.setMaskingDescription(designDetailsDto.getMaskingDescription());
            webDTO.setModelDescription(designDetailsDto.getModelDescription());
            webDTO.setNoMasking(designDetailsDto.getNoMasking());
            webDTO.setLastUpdatedDate(designDetailsDto.getDateUpdated());
            webDTO.setId(designDetailsDto.getId());
        }
    }
    
    /**
     * 
     * @param studyProtocolIi studyProtocolIi
     * @param nciId nciId
     * @param webDto webDto
     * @return eligibilityDto 
     * @throws PAException PAException
     */
    public AdditionalDesignDetailsDTO mergeDesignDetailsUpdate(
            Ii studyProtocolIi, String nciId, ISDesignDetailsWebDTO webDto) throws PAException {
        LOG.info("Updating Design Details to new DB"
                + IiConverter.convertToString(studyProtocolIi));
        AdditionalDesignDetailsDTO designDetailsDto = new AdditionalDesignDetailsDTO();
        designDetailsDto.setMaskingDescription(webDto.getMaskingDescription());
        designDetailsDto.setModelDescription(webDto.getModelDescription());
        designDetailsDto.setNoMasking(webDto.getNoMasking());
        designDetailsDto.setStudyProtocolId(IiConverter.convertToString(studyProtocolIi));
        designDetailsDto.setNciId(nciId);
        designDetailsDto.setDateUpdated(webDto.getLastUpdatedDate());
        designDetailsDto.setId(webDto.getId());

        return trialInfoHelperUtil.mergeDesignDetailsUpdate(studyProtocolIi, nciId, designDetailsDto);
    }

    /**
     *
     * @return TrialInfoHelperUtil
     */
    public TrialInfoHelperUtil getTrialInfoHelperUtil() {
        return trialInfoHelperUtil;
    }

    /**
     *
     * @param trialInfoHelperUtil
     *            the TrialInfoHelperUtil
     */
    public void setTrialInfoHelperUtil(TrialInfoHelperUtil trialInfoHelperUtil) {
        this.trialInfoHelperUtil = trialInfoHelperUtil;
    }
}
