package gov.nih.nci.pa.service;

import java.util.List;
import javax.ejb.Local;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@Local
public interface PlannedMarkerSynonymsServiceLocal extends PlannedMarkerSynonymsServiceRemote {
    /**
     * 
     * @param pmSyncId
     *            pmSyncId
     * @param synonym
     *            synonym
     * @param statusCode
     *            statusCode
     */
    void insertValues(Long pmSyncId, List<String> synonym, String statusCode);
    /**
     * Gets the List of all Integer values.
     * 
     * @return the list of all Integer values
     * @throws PAException
     *             on error.
     * @param id
     *            id
     */
    List<Number> getIdentifierBySyncId(Long id) throws PAException;
    
    /**
     * Gets the List
     * 
     * @return the list of all Integer values
     * @throws PAException
     *             on error.
     * @param id
     *            id
     */
      List<String> getAltNamesBySyncId(Long id) throws PAException;
     
     /**
      * 
      * @param oldSynonyms oldSynonyms
      * @param synonyms synonyms
      * @param statusCode statusCode
      * @param  pmId pmId
      * @throws PAException PAException
      */
     void insertAndUpdateLogic(List<String> oldSynonyms, 
               List<String> synonyms, String statusCode, Long pmId) throws PAException;
     
     /**
      * deletes PlannedMarkerSynonyms values based on Pmsyncid.
      * @param pmSyncId pmSyncId
      */
     void deleteBySyncId(Long pmSyncId);
     
     /**
      * updates the Planned Marker Synonyms with status
      * 
      * @param identifier identifier
      * @param status status
      * @throws PAException PAException
      */
     void updateStatusByPMSynID(Long identifier, String status) throws PAException;
}
