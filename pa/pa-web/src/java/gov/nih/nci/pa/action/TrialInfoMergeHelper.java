package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.PAWebUtil;
import gov.nih.nci.pa.util.PaRegistry;
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
    private RestClient client;
    private LookUpTableServiceRemote lookupTableService;

    /**
     * constructor
     */
    public TrialInfoMergeHelper() {
        super();
        this.client = new RestClient();
        this.lookupTableService = PaRegistry.getLookUpTableService();
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
            String url = lookupTableService
                    .getPropertyValue("data-clinicaltrials-api")
                    + "/"
                    + studyProtocolId;

            String response = client.sendHTTPRequest(url, GET, null);
            if (response != null) {
                regulatoryDto = (AdditionalRegulatoryInfoDTO) PAWebUtil
                    .unmarshallJSON(response, AdditionalRegulatoryInfoDTO.class);
            }
        } catch (Exception e) {
            LOG.error("Error in getting the response from microservices."
                    + studyProtocolId);
            throw new PAException(
                    "Error in getting the response from microservices."
                            + e.getMessage(), e);
        }
        if (regulatoryDto != null) {
            webDto.setFdaRegulatedDrug(regulatoryDto.getFda_regulated_drug());
            webDto.setFdaRegulatedDevice(regulatoryDto.getFda_regulated_device());
            webDto.setPedPostmarketSurv(regulatoryDto.getPed_postmarket_surv());
            webDto.setExportedFromUs(regulatoryDto.getExported_from_us());
            webDto.setPostPriorToApproval(regulatoryDto.getPost_prior_to_approval());
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
            String response = client.sendHTTPRequest(lookupTableService
                    .getPropertyValue("data-clinicaltrials-api"), POST,
                    postBody);
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

    /**
     * @return lookup table service
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookupTableService;
    }

    /**
     * @param lookupTableService1
     *            - lookupTableService
     */
    public void setLookUpTableService(
            LookUpTableServiceRemote lookupTableService1) {
        this.lookupTableService = lookupTableService1;
    }

}
