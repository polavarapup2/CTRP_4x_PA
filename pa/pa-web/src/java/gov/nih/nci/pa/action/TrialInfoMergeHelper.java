package gov.nih.nci.pa.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalEligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.dto.StudyIndldeWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAWebUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.RestClient;

/**
 * 
 * @author Purnima, Reshma
 *
 */
public class TrialInfoMergeHelper {

    private static final Logger LOG = Logger.getLogger(TrialInfoMergeHelper.class);
    /**
     * GET
     */
    private static final String GET = "GET";
    /**
     * POST
     */
    private static final String POST = "POST";
    /**
     * ERROR_MESSAGE
     */
    private static final String ERROR_MESSAGE = "Error in getting the response from microservices.";
    private RestClient client;

    /**
     * constructor
     */
    public TrialInfoMergeHelper() {
        super();
        this.client = new RestClient();
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

        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        AdditionalRegulatoryInfoDTO regulatoryDto = new AdditionalRegulatoryInfoDTO();
        try {
            String response = getHTTPResponseForStudyProtocol(studyProtocolId, nciId);
            if (response != null) {
                List<AdditionalRegulatoryInfoDTO> regulatoryDtoList = (List<AdditionalRegulatoryInfoDTO>) PAWebUtil
                    .unmarshallJSON(response, new TypeReference<List<AdditionalRegulatoryInfoDTO>>() { });
                if (regulatoryDtoList != null && regulatoryDtoList.size() == 1) {
                    regulatoryDto = regulatoryDtoList.get(0);
                }
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + studyProtocolId);
            throw new PAException(ERROR_MESSAGE + e.getMessage(), e);
        }
        if (regulatoryDto != null) {
            if (PAWebUtil.isValidBooleanString(regulatoryDto.getFda_regulated_drug())) {
                webDto.setFdaRegulatedDrug(regulatoryDto.getFda_regulated_drug());
            }
            if (PAWebUtil.isValidBooleanString(regulatoryDto.getFda_regulated_device())) {
                webDto.setFdaRegulatedDevice(regulatoryDto.getFda_regulated_device());
            }
            if (PAWebUtil.isValidBooleanString(regulatoryDto.getPed_postmarket_surv())) {
                webDto.setPedPostmarketSurv(regulatoryDto.getPed_postmarket_surv());
            }
            if (PAWebUtil.isValidBooleanString(regulatoryDto.getExported_from_us())) {
                webDto.setExportedFromUs(regulatoryDto.getExported_from_us());
            }
            if (PAWebUtil.isValidBooleanString(regulatoryDto.getPost_prior_to_approval())) {
                webDto.setPostPriorToApproval(regulatoryDto.getPost_prior_to_approval());
            }
            webDto.setLastUpdatedDate(regulatoryDto.getDate_updated());
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
                .convertToLong(studyProtocolIi));
        regulatoryDto.setNci_id(nciId);
        try {
            String postBody = PAWebUtil.marshallJSON(regulatoryDto);
            String response = client.sendHTTPRequest(PaEarPropertyReader
                    .getFdaaaDataClinicalTrialsUrl(), POST, postBody);
            regulatoryDto = (AdditionalRegulatoryInfoDTO) PAWebUtil
                    .unmarshallJSON(response, AdditionalRegulatoryInfoDTO.class);
        } catch (Exception e) {
            LOG.error(
                    "Error in updating additional Regulatory info in rest service for the study protocol id: "
                            + IiConverter.convertToString(studyProtocolIi), e);
            throw new PAException(
                    "Error in updating additional Regulatory info in rest service for the study protocol id: "
                            + IiConverter.convertToString(studyProtocolIi), e);
        }
        return regulatoryDto;
    }
    
    /**
     * 
     * @param studyProtocolId the studyProtocolId
     * @param nciId the nciId
     * @return String response
     * @throws PAException exception
     */
    public String getHTTPResponseForStudyProtocol(String studyProtocolId, String nciId) throws PAException {
        String url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl()
                + "?study_protocol_id=" + studyProtocolId + "&nci_id=" + nciId;
        return (client.sendHTTPRequest(url, GET, null));
    }
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param nciId the nciId
     * @param webDto the webDto
     * @throws PAException the exception
     */
    public void mergeEligibilityCriteriaRead(Ii studyProtocolIi, String nciId,
            ISDesignDetailsWebDTO webDto) throws PAException {
        LOG.info("Getting Eligibility Criteria data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));
        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        AdditionalEligibilityCriteriaDTO eligibilityDto = new AdditionalEligibilityCriteriaDTO();
        try {
            String response = getHTTPResponseForStudyProtocol(studyProtocolId, nciId);
            if (response != null) {
                List<AdditionalEligibilityCriteriaDTO> eligDtoList = 
                        (List<AdditionalEligibilityCriteriaDTO>) PAWebUtil.unmarshallJSON(response, 
                                new TypeReference<List<AdditionalEligibilityCriteriaDTO>>() { });
                if (eligDtoList != null && eligDtoList.size() == 1) {
                    eligibilityDto = eligDtoList.get(0);
                }
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + studyProtocolId);
            throw new PAException(ERROR_MESSAGE + e.getMessage(), e);
        }
        if (eligibilityDto != null) {
            if (PAWebUtil.isValidBooleanString(eligibilityDto.getGender())) {
                webDto.setGender(eligibilityDto.getGender());
            }
            webDto.setGenderEligibilityDescription(eligibilityDto.getGenderEligibilityDescription());
            webDto.setLastUpdatedDate(eligibilityDto.getDateUpdated());
        }
        
    }
    
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param webDto the webDto
     * @throws PAException exception
     */
    public void mergeTrialIndIdeInfoRead(Ii studyProtocolIi, StudyIndldeWebDTO webDto) throws PAException {
        LOG.info("Getting additional Trial IND/IDE info for IND/IDE id: " + webDto.getId());
        AdditionalTrialIndIdeDTO trialIndIdeDto = new AdditionalTrialIndIdeDTO();
        /*try {
            String response = getHTTPResponse(webDto.getId());
            if (response != null) {
                trialIndIdeDto = (AdditionalTrialIndIdeDTO) PAWebUtil.unmarshallJSON(
                        response, AdditionalTrialIndIdeDTO.class);
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + " for Trial IND/IDE id : " + webDto.getId());
            throw new PAException(ERROR_MESSAGE + " for Trial IND/IDE id : " + webDto.getId() + e.getMessage(), e);
        }*/
        if (trialIndIdeDto != null) {
            webDto.setExpandedAccessIndicator(trialIndIdeDto.getExpandedAccessIndicator());
            webDto.setExpandedAccessNctId(trialIndIdeDto.getExpandedAccessNctId());
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
        List<AdditionalTrialIndIdeDTO> trialIndIdeDtoList = new ArrayList<AdditionalTrialIndIdeDTO>();
        /*try {
            String response = getHTTPResponse(studyProtocolIdStr);
            if (response != null) {
                trialIndIdeDtoList = (List<AdditionalTrialIndIdeDTO>) PAWebUtil.unmarshallJSON(
                        response, new TypeReference<List<AdditionalTrialIndIdeDTO>>() { });
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + " for Study Protocol id : " + studyProtocolIdStr);
            throw new PAException(ERROR_MESSAGE + " for Study Protocol id : " + studyProtocolIdStr + e.getMessage(), e);
        }*/
        if (trialIndIdeDtoList != null && !trialIndIdeDtoList.isEmpty()) {
            for (StudyIndldeWebDTO studyIndide : studyIndideList) {
                String studyIndIdeId = studyIndide.getId();
                for (AdditionalTrialIndIdeDTO trialDto : trialIndIdeDtoList) {
                    if (trialDto.getTrialIndIdeId() != null 
                            && StringUtils.equals(studyIndIdeId, trialDto.getTrialIndIdeId() + "")) {
                        studyIndide.setExpandedAccessIndicator(trialDto.getExpandedAccessIndicator());
                        studyIndide.setExpandedAccessNctId(trialDto.getExpandedAccessNctId());
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
        Long studyProtocolIdL = IiConverter.convertToLong(studyProtocolIi);
        LOG.info("Updating Trial IND/IDE info to new DB for :"
                + studyProtocolIdL + ", Trial IND/IDE id: " + webDto.getId());
        AdditionalTrialIndIdeDTO trialIndIdeDto = new AdditionalTrialIndIdeDTO();
        trialIndIdeDto.setTrialIndIdeId(Long.valueOf(webDto.getId()));
        trialIndIdeDto.setExpandedAccessIndicator(webDto.getExpandedAccessIndicator());
        trialIndIdeDto.setExpandedAccessNctId(webDto.getExpandedAccessNctId());
        trialIndIdeDto.setDateUpdated(webDto.getDateUpdated());
        trialIndIdeDto.setStudyProtocolId(studyProtocolIdL);
        try {
            String postBody = PAWebUtil.marshallJSON(trialIndIdeDto);
            String response = client.sendHTTPRequest(PaEarPropertyReader
                    .getFdaaaDataClinicalTrialsUrl(), POST, postBody);
            trialIndIdeDto = (AdditionalTrialIndIdeDTO) PAWebUtil
                    .unmarshallJSON(response, AdditionalTrialIndIdeDTO.class);
        } catch (Exception e) {
            LOG.error(
                    "Error in updating additional Trial IND/IDE info in rest service for the study protocol id: "
                            + studyProtocolIdL, e);
            throw new PAException(
                    "Error in updating additional Trial IND/IDE info in rest service for the study protocol id: "
                            + studyProtocolIdL, e);
        }
        return trialIndIdeDto;
    }

    /**
     * 
     * @return client
     */
    public RestClient getClient() {
        return client;
    }

    /**
     * 
     * @param client
     *            the client
     */
    public void setClient(RestClient client) {
        this.client = client;
    }
}
