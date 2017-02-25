package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAWebUtil;
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
     *  GET 
     */
    private static final String GET = "GET";
    
    private RestClient client = new RestClient(true);
    /**
     * 
     * @param studyProtocolIi studyProtocolIi
     * @param webDto webDto
     * @throws PAException PAException
     */
    public void mergeRegulatoryInfoRead(Ii studyProtocolIi,
            RegulatoryAuthorityWebDTO webDto) throws PAException {
        LOG.info("Getting Regulatory data info from new DB"
                + IiConverter.convertToString(studyProtocolIi));

        String studyProtocolId = IiConverter.convertToString(studyProtocolIi);
        AdditionalRegulatoryInfoDTO regulatoryDto = new AdditionalRegulatoryInfoDTO();
        try {
           // String response = client.sendHTTPRequest("", GET, null);
            String response = "{\"fdaRegulatedDrug\":\"true\",\"fdaRegulatedDevice\":\"true\""
                    + ",\"postPriorToApproval\":\"true\""
                    + ",\"pedPostmarketSurv\":\"true\",\"exportedFromUs\":\"true\"}";
            regulatoryDto = (AdditionalRegulatoryInfoDTO) PAWebUtil
                    .unmarshallJSON(response, AdditionalRegulatoryInfoDTO.class);
        } catch (Exception e) {
            LOG.error("Error in getting the response from microservices."
                    + studyProtocolId);
            throw new PAException(
                    "Error in getting the response from microservices."
                            + e.getMessage(), e);
        }

        webDto.setFdaRegulatedDrug(regulatoryDto.getFdaRegulatedDrug());
        webDto.setFdaRegulatedDevice(regulatoryDto.getFdaRegulatedDevice());
        webDto.setPedPostmarketSurv(regulatoryDto.getPedPostmarketSurv());
        webDto.setExportedFromUs(regulatoryDto.getExportedFromUs());
        webDto.setPostPriorToApproval(regulatoryDto.getPostPriorToApproval());

    }

    /**
     * public static void mergeRegulatoryInfoUpdate(Ii studyProtocolIi,
     * RegulatoryAuthorityWebDTO webDto) throws PAException {
     * LOG.info("Updating Regulatory data info to new DB" +
     * IiConverter.convertToString(studyProtocolIi)); RestClient client = new
     * RestClient(true); AdditionalRegulatoryInfoDTO regulatoryDto = new
     * AdditionalRegulatoryInfoDTO();
     * regulatoryDto.setExportedFromUs(webDto.getExportedFromUs());
     * regulatoryDto.setFdaRegulatedDevice(webDto.getFdaRegulatedDevice());
     * regulatoryDto.setFdaRegulatedDrug(webDto.getFdaRegulatedDrug());
     * regulatoryDto.setPedPostmarketSurv(webDto.getPedPostmarketSurv());
     * regulatoryDto.setPostPriorToApproval(webDto.getPostPriorToApproval());
     * try { String postBody = client.marshallJSON(regulatoryDto);
     * 
     * client.sendHTTPRequest("", POST, postBody); } catch (Exception e) {
     * LOG.error(
     * "Error in updating additional Regulatory info in rest service for the study protocol id: "
     * + IiConverter.convertToString(studyProtocolIi) ,e); throw new
     * PAException(
     * "Error in updating additional Regulatory info in rest service for the study protocol id: "
     * + IiConverter.convertToString(studyProtocolIi) ,e); }
     * 
     * }
     */

    /**
     * 
     * @return client
     */
    public RestClient getClient() {
        return client;
    }

    /**
     * 
     * @param client  the client
     */
    public void setClient(RestClient client) {
        this.client = client;
    }

}
