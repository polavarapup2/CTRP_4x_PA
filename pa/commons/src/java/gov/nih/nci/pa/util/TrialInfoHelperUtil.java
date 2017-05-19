package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalDesignDetailsDTO;
import gov.nih.nci.pa.dto.AdditionalEligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Purnima, Reshma
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class TrialInfoHelperUtil {

    private static final Logger LOG = Logger.getLogger(TrialInfoHelperUtil.class);
    /**
     * GET
     */
    private static final String GET = "GET";
    /**
     * POST
     */
    private static final String POST = "POST";
    /**
     * PUT
     */
    private static final String PUT = "PUT";

    /**
     * DELETE
     */
    private static final String DELETE = "DELETE";
    /**
     * ERROR_MESSAGE
     */
    private static final String ERROR_MESSAGE = "Error in getting regulatory information: ";
    private RestClient client;

    /**
     * constructor
     */
    public TrialInfoHelperUtil() {
        super();
        this.client = new RestClient();
    }

    /**
     * @param studyProtocolIi studyProtocolIi
     * @param nciId nciId
     * @return AdditionalRegulatoryInfoDTO
     * @throws PAException PAException
     */
    public AdditionalRegulatoryInfoDTO retrieveRegulatoryInfo(Ii studyProtocolIi, String nciId) throws PAException {
        return retrieveRegulatoryInfo(IiConverter.convertToString(studyProtocolIi), nciId);
    }
    
    /**
     * @param studyProtocolId studyProtocolId
     * @param nciId nciId
     * @return AdditionalRegulatoryInfoDTO
     * @throws PAException PAException
     */
    public AdditionalRegulatoryInfoDTO retrieveRegulatoryInfo(String studyProtocolId, String nciId) throws PAException {
        LOG.info(String.format("Getting Trial %s RegulatoryInfo for %s", studyProtocolId, nciId));

        AdditionalRegulatoryInfoDTO regulatoryDto = new AdditionalRegulatoryInfoDTO();
        try {
            String response = getHTTPResponseForStudyProtocol(studyProtocolId, nciId);
            if (response != null) {
                List<AdditionalRegulatoryInfoDTO> regulatoryDtoList = (List<AdditionalRegulatoryInfoDTO>) PAJsonUtil
                    .unmarshallJSON(response, new TypeReference<List<AdditionalRegulatoryInfoDTO>>() { });
                if (regulatoryDtoList != null && regulatoryDtoList.size() == 1) {
                    regulatoryDto = regulatoryDtoList.get(0);
                }
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + studyProtocolId, e);
            throw new PAException(ERROR_MESSAGE + studyProtocolId, e);
        }

        return regulatoryDto;
    }

    /**
     *
     * @param studyProtocolIi
     *            the studyProtocolIi
     * @param nciId
     *            the nciId
     * @param regulatoryDto
     *            the AdditionalRegulatoryInfoDTO
     * @return AdditionalRegulatoryInfoDTO
     * @throws PAException
     *             PAException
     */
    public AdditionalRegulatoryInfoDTO mergeRegulatoryInfoUpdate(
            Ii studyProtocolIi, String nciId, AdditionalRegulatoryInfoDTO regulatoryDto)
            throws PAException {
        return mergeRegulatoryInfoUpdate(IiConverter.convertToString(studyProtocolIi), nciId, regulatoryDto);
    }
    
    /**
    *
    * @param studyProtocolId
    *            the studyProtocolId
    * @param nciId
    *            the nciId
    * @param regulatoryDto
    *            the AdditionalRegulatoryInfoDTO
    * @return AdditionalRegulatoryInfoDTO
    * @throws PAException
    *             PAException
    */
   public AdditionalRegulatoryInfoDTO mergeRegulatoryInfoUpdate(
           String studyProtocolId, String nciId, AdditionalRegulatoryInfoDTO regulatoryDto)
           throws PAException {
       LOG.info(String.format("Updating Trial %s RegulatoryInfo service for %s", studyProtocolId, nciId));
       
       if (StringUtils.isEmpty(regulatoryDto.getNci_id())) {
           regulatoryDto.setNci_id(nciId);
       }

       AdditionalRegulatoryInfoDTO currRegulatoryDto = regulatoryDto;
       try {
           String postBody = PAJsonUtil.marshallJSON(regulatoryDto);
           String response = "";
           if (regulatoryDto.getId() == null) {
               response = client.sendHTTPRequest(PaEarPropertyReader
                       .getFdaaaDataClinicalTrialsUrl(), POST, postBody);
           } else {
               response = client.sendHTTPRequest(PaEarPropertyReader
                       .getFdaaaDataClinicalTrialsUrl() + "/" + regulatoryDto.getId(), PUT, postBody);
           }
           if (response != null) {
               currRegulatoryDto = (AdditionalRegulatoryInfoDTO) PAJsonUtil
                       .unmarshallJSON(response, AdditionalRegulatoryInfoDTO.class);
           }
       } catch (Exception e) {
           LOG.error("Error in updating additional Regulatory info for the study protocol id: " 
                   + studyProtocolId, e);
           throw new PAException("Error in updating additional Regulatory info for the study protocol id: " 
                   + studyProtocolId, e);
       }
       return currRegulatoryDto;
   }


    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param nciId nciId
     * @return AdditionalEligibilityCriteriaDTO
     * @throws PAException the exception
     */
    public AdditionalEligibilityCriteriaDTO retrieveEligibilityCriteria(Ii studyProtocolIi, String nciId)
            throws PAException {
        LOG.info("Getting Eligibility Criteria data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));
        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        AdditionalEligibilityCriteriaDTO eligibilityDto = new AdditionalEligibilityCriteriaDTO();
        try {
            String response = getHTTPResponseForStudyProtocol(studyProtocolId, nciId);
            
            if (response != null) {
                List<AdditionalEligibilityCriteriaDTO> eligibilityDtoList = 
                        (List<AdditionalEligibilityCriteriaDTO>) PAJsonUtil
                    .unmarshallJSON(response, new TypeReference<List<AdditionalEligibilityCriteriaDTO>>() { });
                if (eligibilityDtoList != null && eligibilityDtoList.size() == 1) {
                    eligibilityDto = eligibilityDtoList.get(0);
                }
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + studyProtocolId);
            throw new PAException(ERROR_MESSAGE + e.getMessage(), e);
        }
        return eligibilityDto;
    }

    /**
     *
     * @param studyProtocolIi studyProtocolIi
     * @param nciId nciId
     * @param eligibilityDto AdditionalEligibilityCriteriaDTO
     * @return eligibilityDto
     * @throws PAException PAException
     */
    public AdditionalEligibilityCriteriaDTO mergeEligibilityCriteriaUpdate(
            Ii studyProtocolIi, String nciId, AdditionalEligibilityCriteriaDTO eligibilityDto) throws PAException {
        LOG.info("Updating Eligibility criteria to new DB"
                + IiConverter.convertToString(studyProtocolIi));
        AdditionalEligibilityCriteriaDTO currEligibilityDto = eligibilityDto;
        try {
            String postBody = PAJsonUtil.marshallJSON(eligibilityDto);
            String response = "";
            if (eligibilityDto.getId() == null) {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getFdaaaDataClinicalTrialsUrl(), POST, postBody);
            } else {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getFdaaaDataClinicalTrialsUrl() + "/" + eligibilityDto.getId(), PUT, postBody);
            }
            currEligibilityDto = (AdditionalEligibilityCriteriaDTO) PAJsonUtil
                    .unmarshallJSON(response, AdditionalEligibilityCriteriaDTO.class);
        } catch (Exception e) {
            LOG.error(
                    "Error in updating additional Eligibility Criteria for the study protocol id: "
                            + IiConverter.convertToString(studyProtocolIi), e);
            throw new PAException(
                    "Error in updating additional Eligibility Criteria for the study protocol id: "
                            + IiConverter.convertToString(studyProtocolIi), e);
        }
        return currEligibilityDto;
    }
    
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param trialIndldeID the trial Indlde ID String
     * @return AdditionalTrialIndIdeDTO
     * @throws PAException exception
     */
    public AdditionalTrialIndIdeDTO retrieveTrialIndIdeById(Ii studyProtocolIi, String trialIndldeID)
            throws PAException {
        LOG.info("Getting additional Trial IND/IDE info by IND/IDE id: " + trialIndldeID);
        AdditionalTrialIndIdeDTO trialIndIdeDto = new AdditionalTrialIndIdeDTO();
        try {
            String response = getHTTPResponseForStudyWithTrialInd(IiConverter
                    .convertToString(studyProtocolIi), trialIndldeID);
            if (response != null) {
                List<AdditionalTrialIndIdeDTO> trialIndIdeDtoList = (List<AdditionalTrialIndIdeDTO>) PAJsonUtil
                        .unmarshallJSON(response, new TypeReference<List<AdditionalTrialIndIdeDTO>>() { });
                    if (trialIndIdeDtoList != null && trialIndIdeDtoList.size() == 1) {
                        trialIndIdeDto = trialIndIdeDtoList.get(0);
                    }
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + " for Trial IND/IDE id : " + trialIndldeID);
            throw new PAException(ERROR_MESSAGE + " for Trial IND/IDE id : " + trialIndldeID + e.getMessage(), e);
        }

        return trialIndIdeDto;
    }
    
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @return List<AdditionalTrialIndIdeDTO>
     * @throws PAException exception
     */
    public List<AdditionalTrialIndIdeDTO> retrieveAllTrialIndIdeByStudyId(Ii studyProtocolIi)
            throws PAException {
        String studyProtocolIdStr = IiConverter.convertToString(studyProtocolIi);
        LOG.info("Getting additional Trial IND/IDE info for all the IND/IDE records of study protocol id: " 
                        + studyProtocolIdStr);
        List<AdditionalTrialIndIdeDTO> trialIndIdeDtoList = new ArrayList<AdditionalTrialIndIdeDTO>();
        try {
            String response = getHTTPResponseWithSPID(studyProtocolIdStr);
            if (response != null) {
                trialIndIdeDtoList = (List<AdditionalTrialIndIdeDTO>) PAJsonUtil.unmarshallJSON(
                        response, new TypeReference<List<AdditionalTrialIndIdeDTO>>() { });
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + " for Study Protocol id : " + studyProtocolIdStr);
            throw new PAException(ERROR_MESSAGE + " for Study Protocol id : " + studyProtocolIdStr + e.getMessage(), e);
        }

        return trialIndIdeDtoList;
    }

    /**
     *
     * @param studyProtocolIi the studyProtocolIi
     * @param trialIndIdeDto the AdditionalTrialIndIdeDTO
     * @return AdditionalTrialIndIdeDTO
     * @throws PAException exception
     */
    public AdditionalTrialIndIdeDTO mergeTrialIndIdeInfoUpdate(Ii studyProtocolIi,
                             AdditionalTrialIndIdeDTO trialIndIdeDto) throws PAException {
        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        LOG.info("Updating Trial IND/IDE info to new DB for :"
                + studyProtocolId + ", Trial IND/IDE id: " + trialIndIdeDto.getTrialIndIdeId());
        AdditionalTrialIndIdeDTO currTrialIndIdeDto = trialIndIdeDto;
        try {
            String postBody = PAJsonUtil.marshallJSON(trialIndIdeDto);
            String response = "";
            if (trialIndIdeDto.getId() == null) {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getFdaaaDataClinicalTrialsUrl(), POST, postBody);
            } else {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getFdaaaDataClinicalTrialsUrl() + "/" + trialIndIdeDto.getId(), PUT, postBody);
            }

            currTrialIndIdeDto = (AdditionalTrialIndIdeDTO) PAJsonUtil
                    .unmarshallJSON(response, AdditionalTrialIndIdeDTO.class);
        } catch (Exception e) {
            LOG.error(
                 "Error in updating additional Trial IND/IDE info for the study protocol id: "
                       + studyProtocolId, e);
            throw new PAException(
                    "Error in updating additional Trial IND/IDE info for the study protocol id: "
                            + studyProtocolId, e);
        }
        return currTrialIndIdeDto;
    }

    /**
     * 
     * @param msId msId
     * @throws PAException the PAException
     */
    public void deleteTrialIndIdeInfo(String msId) throws PAException {
       
        LOG.info("Deleting Trial IND/IDE info in the new DB for :" + msId);
        try {
            client.sendHTTPRequest(PaEarPropertyReader
                .getFdaaaDataClinicalTrialsUrl() + "/" + msId, DELETE, null);
        } catch (Exception e) {
            LOG.error(
                 "Error in deleting additional Trial IND/IDE info for the study protocol id: "
                      + msId, e);
            throw new PAException(
                    "Error in deleting additional Trial IND/IDE info for the study protocol id: "
                            + msId, e);
        }
    }
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param nciId nciId
     * @return AdditionalDesignDetailsDTO
     * @throws PAException the exception
     */
    public AdditionalDesignDetailsDTO retrieveDesignDetails(Ii studyProtocolIi, String nciId) throws PAException {
        LOG.info("Getting Design Details data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));
        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        AdditionalDesignDetailsDTO designDetailsDto = new AdditionalDesignDetailsDTO();
        try {
            String response = getHTTPResponseForStudyProtocol(studyProtocolId, nciId);
            
            if (response != null) {
                List<AdditionalDesignDetailsDTO> designDetailsDtoList = 
                        (List<AdditionalDesignDetailsDTO>) PAJsonUtil
                    .unmarshallJSON(response, new TypeReference<List<AdditionalDesignDetailsDTO>>() { });
                if (designDetailsDtoList != null && designDetailsDtoList.size() == 1) {
                    designDetailsDto = designDetailsDtoList.get(0);
                }
            }
        } catch (Exception e) {
            LOG.error(ERROR_MESSAGE + studyProtocolId);
            throw new PAException(ERROR_MESSAGE + e.getMessage(), e);
        }

        return  designDetailsDto;
    }

    /**
     *
     * @param studyProtocolIi studyProtocolIi
     * @param nciId nciId
     * @param designDetailsDto AdditionalDesignDetailsDTO
     * @return AdditionalDesignDetailsDTO
     * @throws PAException PAException
     */
    public AdditionalDesignDetailsDTO mergeDesignDetailsUpdate(
            Ii studyProtocolIi, String nciId, AdditionalDesignDetailsDTO designDetailsDto) throws PAException {
        LOG.info("Updating Design Details to new DB"
                + IiConverter.convertToString(studyProtocolIi));
        AdditionalDesignDetailsDTO currDesignDetailsDto = designDetailsDto;
        try {
            String postBody = PAJsonUtil.marshallJSON(designDetailsDto);
            String response = "";
            if (designDetailsDto.getId() == null) {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getFdaaaDataClinicalTrialsUrl(), POST, postBody);
            } else {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getFdaaaDataClinicalTrialsUrl() + "/" + designDetailsDto.getId(), PUT, postBody);
            }
            currDesignDetailsDto = (AdditionalDesignDetailsDTO) PAJsonUtil
                    .unmarshallJSON(response, AdditionalDesignDetailsDTO.class);
        } catch (Exception e) {
            LOG.error(
                    "Error in updating Design Details in for the study protocol id: "
                            + IiConverter.convertToString(studyProtocolIi), e);
            throw new PAException(
                    "Error in updating Design Details in for the study protocol id: "
                            + IiConverter.convertToString(studyProtocolIi), e);
        }
        return currDesignDetailsDto;
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
     * @param studyProtocolId the studyProtocolId
     * @param trialINDId the trialINDId
     * @return String response
     * @throws PAException exception
     */
    public String getHTTPResponseForStudyWithTrialInd(String studyProtocolId, String trialINDId) throws PAException {
        String url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl()
                + "?study_protocol_id=" + studyProtocolId + "&trial_ide_ind_id=" + trialINDId;
        return (client.sendHTTPRequest(url, GET, null));
    }


    /**
     *
     * @param studyProtocolId the studyProtocolId
     * @return String response
     * @throws PAException exception
     */
    public String getHTTPResponseWithSPID(String studyProtocolId) throws PAException {
        String url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl()
                + "?study_protocol_id=" + studyProtocolId;
        return (client.sendHTTPRequest(url, GET, null));
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
