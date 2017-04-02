package gov.nih.nci.pa.util;

import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTO;
import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTOs;
import gov.nih.nci.pa.service.PAException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
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
    private static final String ERROR = "Error in importing ctgov xml with NCT number";
    private ImportRestClient client;

    /**
     * 
     * const
     */
    public CTGovImportMergeHelper() {
        super();
        this.client = new ImportRestClient();
    }

    /**
     * 
     * @param nctID
     *            the nctID
     * @return TrialRegistrationConfirmationDTOs
     * @throws PAException
     *             PAException
     */
    public TrialRegistrationConfirmationDTOs updateNctId(String nctID)
            throws PAException {
        TrialRegistrationConfirmationDTOs dtos = null;
        try {
            String response = client.sendHTTPRequest(
                    PaEarPropertyReader.getCtrpImportCtApiUrl() + "/" + nctID,
                    PUT, null);
            if (response != null) {
                dtos = CommonsUtil.unmarshallXML(response,
                        TrialRegistrationConfirmationDTOs.class);
            }
        } catch (Exception e) {
            LOG.error(ERROR + nctID, e);
            throw new PAException(ERROR + nctID, e);
        }
        return dtos;
    }

    /**
     * 
     * @param nctID
     *            the nctID
     * @return TrialRegistrationConfirmationDTO
     * @throws PAException
     *             PAException
     */
    public TrialRegistrationConfirmationDTO insertNctId(String nctID)
            throws PAException {
        TrialRegistrationConfirmationDTO dto = new TrialRegistrationConfirmationDTO();
        try {
            String response = client.sendHTTPRequest(
                    PaEarPropertyReader.getCtrpImportCtApiUrl() + "/" + nctID,
                    POST, null);
            if (response != null) {
                dto = CommonsUtil.unmarshallXML(response,
                        TrialRegistrationConfirmationDTO.class);
            }
        } catch (Exception e) {
            LOG.error(ERROR + nctID, e);
            throw new PAException(ERROR + nctID, e);
        }
        return dto;
    }

    /**
     * 
     * @param jsonString
     *            the jsonString
     * @param <T>
     *            the <T>
     * @param valueTypeRef
     *            the valueTypeRef
     * @return the Object for the JSONString
     */
    public static <T> T unmarshallJSON(String jsonString,
            TypeReference<T> valueTypeRef) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // JSON from String to Object
            return (mapper.readValue(jsonString, valueTypeRef));

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 
     * @return client
     */
    public ImportRestClient getClient() {
        return client;
    }

    /**
     * 
     * @param client
     *            the client
     */
    public void setClient(ImportRestClient client) {
        this.client = client;
    }

}
