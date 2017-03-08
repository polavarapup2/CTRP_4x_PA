package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalEligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAWebUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.RestClient;

import org.apache.log4j.Logger;

/**
 * 
 * @author Purnima, Reshma
 *
 */
public class TrialInfoMergeHelper {

    private static final Logger LOG = Logger
            .getLogger(TrialInfoMergeHelper.class);
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
     * @param webDto
     *            webDto
     * @throws PAException
     *             PAException
     */
    public void mergeRegulatoryInfoRead(Ii studyProtocolIi,
            RegulatoryAuthorityWebDTO webDto) throws PAException {
        LOG.info("Getting Regulatory data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));

        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        AdditionalRegulatoryInfoDTO regulatoryDto = new AdditionalRegulatoryInfoDTO();
        try {
            String response = getHTTPResponse(studyProtocolId);
            if (response != null) {
                regulatoryDto = (AdditionalRegulatoryInfoDTO) PAWebUtil
                    .unmarshallJSON(response, AdditionalRegulatoryInfoDTO.class);
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
     * @return String response
     * @throws PAException exception
     */
    public String getHTTPResponse(String studyProtocolId) throws PAException {
        String url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl()
                + "/"
                + studyProtocolId;
        return (client.sendHTTPRequest(url, GET, null));
    }
    /**
     * 
     * @param studyProtocolIi the studyProtocolIi
     * @param webDto the webDto
     * @throws PAException the exception
     */
    public void mergeEligibilityCriteriaRead(Ii studyProtocolIi,
            ISDesignDetailsWebDTO webDto) throws PAException {
        LOG.info("Getting Eligibility Criteria data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));
        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        AdditionalEligibilityCriteriaDTO eligibilityDto = new AdditionalEligibilityCriteriaDTO();
        try {
            String response = getHTTPResponse(studyProtocolId);
            if (response != null) {
                eligibilityDto = (AdditionalEligibilityCriteriaDTO) PAWebUtil
                    .unmarshallJSON(response, AdditionalEligibilityCriteriaDTO.class);
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
