package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudyProtocolIdentityDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAJsonUtil;
import gov.nih.nci.pa.util.RestClient;

import org.apache.log4j.Logger;

/**
 * 
 * @author Reshma
 *
 */
public class CTGovImportMergeHelper {
    private static final Logger LOG = Logger.getLogger(CTGovImportMergeHelper.class);
   
    /**
     * POST
     */
    private static final String POST = "POST";
    /**
     * PUT
     */
    private static final String PUT = "PUT";
    private RestClient client;
    //private String url = "http://<host>:3200/api/v1/ctrp_import_ct_api/importtrial";
    
    /**
     * 
     * const
     */
    public CTGovImportMergeHelper() {
        super();
        this.client = new RestClient();
    }
    /**
     * 
     * @param dto the dto
     * @return StudyProtocolIdentityDTO
     * @throws PAException PAException
     */
    public StudyProtocolIdentityDTO insertOrUpdateNctId(StudyProtocolIdentityDTO dto) throws PAException {
        try {
            //String postBody = PAJsonUtil.marshallJSON(dto);
            String response = "";
            if (dto.getNciId() != null) {
                response = client.sendHTTPRequest("", POST, null);
            } else {
                response = client.sendHTTPRequest("", PUT, null);
            }
            if (response != null) {
                dto = (StudyProtocolIdentityDTO) PAJsonUtil
                    .unmarshallJSON(response, StudyProtocolIdentityDTO.class);
            }
        } catch (Exception e) {
            LOG.error(
                    "Error in importing ctgov xml with NCT number"
                            + dto.getNctId(), e);
            throw new PAException(
                    "Error in importing ctgov xml with NCT number"
                            + dto.getNctId(), e);
        }
        return dto;
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
