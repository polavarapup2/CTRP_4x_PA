package gov.nih.nci.pa.util;

import java.util.List;

import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTO;
import gov.nih.nci.pa.service.PAException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;


/**
 * 
 * @author Reshma
 *
 */
public class CTGovImportMergeHelper {
    private static final Logger LOG = Logger
            .getLogger(CTGovImportMergeHelper.class);

    /**
     * POST
     */
    private static final String POST = "POST";
    /**
     * PUT
     */
    private static final String PUT = "PUT";
    /**
     * ERROR
     */
    private static final String ERROR = "Error in importing ctgov xml with NCT number ";
    private RestClient client;

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
     * @param nctID
     *            the nctID
     * @param currentUser currentUser
     * @return TrialRegistrationConfirmationDTOs
     * @throws PAException
     *             PAException
     */
    public List<TrialRegistrationConfirmationDTO> updateNctId(String nctID, String currentUser)
            throws PAException {
        List<TrialRegistrationConfirmationDTO> dtos = null;
        try {
            String response = client.sendHTTPRequest(
                    PaEarPropertyReader.getCtrpImportCtApiUrl() + "/" + nctID,
                    PUT, currentUser);
            if (response != null) {
                dtos = (List<TrialRegistrationConfirmationDTO>) PAJsonUtil.unmarshallJSON(
                        response, new TypeReference<List<TrialRegistrationConfirmationDTO>>() { });
            }
        } catch (Exception e) {
            LOG.error(ERROR + nctID, e);
            throw new PAException(e.getMessage(), e);
        }
        return dtos;
    }

    /**
     * 
     * @param nctID
     *            the nctID
     * @param currentUser currentUser
     * @return TrialRegistrationConfirmationDTO
     * @throws PAException
     *             PAException
     */
    public TrialRegistrationConfirmationDTO insertNctId(String nctID, String currentUser)
            throws PAException {
        TrialRegistrationConfirmationDTO dto = new TrialRegistrationConfirmationDTO();
        try {
            String response = client.sendHTTPRequest(
                    PaEarPropertyReader.getCtrpImportCtApiUrl() + "/" + nctID,
                    POST, currentUser);
            if (response != null) {
                dto = PAJsonUtil.unmarshallJSON(response,
                        TrialRegistrationConfirmationDTO.class);
            }
        } catch (Exception e) {
            LOG.error(ERROR + nctID, e);
            throw new PAException(e.getMessage(), e);
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
