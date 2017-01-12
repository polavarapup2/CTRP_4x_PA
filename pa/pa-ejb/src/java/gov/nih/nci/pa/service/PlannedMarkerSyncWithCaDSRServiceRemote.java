package gov.nih.nci.pa.service;


import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;

import java.util.List;

import javax.ejb.Local;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@Local
public interface PlannedMarkerSyncWithCaDSRServiceRemote {
    /**
     *  
     * @param valuesList the CaDSR list
     * @throws PAException on error.
     */
    void syncTableWithCaDSR(List<CaDSRDTO> valuesList) throws PAException;
    /**
     * Gets the List of all PlannedMarkerSyncWithCaDSRDTO values. 
     * @return the list of all PlannedMarkerSyncWithCaDSRDTO values
     * @throws PAException on error.
     * @param id id
     */
    List<PlannedMarkerSyncWithCaDSRDTO> getValuesById(Long id) throws PAException;
    /**
     * Gets the List of all Integer values. 
     * @return the list of all Integer values
     * @throws PAException on error.
     * @param id id
     */
    List<Number> getIdentifierByCadsrId(Long id) throws PAException;
    /**
     * Gets the List of all name values. 
     * @return the list of all name values
     * @throws PAException on error.
     * @param name name
     */
    List<Number> getPendingIdentifierByCadsrName(String name) throws PAException;
    
    /**
     * Updated the values
     * @param caDSRId caDSRId
     * @param name name
     * @param meaning meaning 
     * @param description description
     * @param statusCode statusCode
     */
    void updateValueByName(Long caDSRId, String name, String meaning, String description, String statusCode);
    /**
     * Updated the values
     *
     * @param name name
     * @param id id
     * 
     */
    void updateValueById(String name, Long id);
    
    /**
     * Gets the List of all PlannedMarkerSyncWithCaDSRDTO values.
     * 
     * @return the list of all PlannedMarkerSyncWithCaDSRDTO values
     * @throws PAException
     *             on error.
     * @param name name
     */
    List<PlannedMarkerSyncWithCaDSRDTO> getValuesByName(String name) throws PAException;
    
    /**
     * 
     * @param caDSRId caDSRId
     * @param name name
     * @param meaning meaning
     * @param description description
     * @param statusCode statusCode
     * @param ntTermId ntTermId
     * @param pvValue pvValue
     */
    @SuppressWarnings({ "PMD.ExcessiveParameterList" })
    void insertValues(Long caDSRId, String name, String meaning,
            String description, String ntTermId, String pvValue, String statusCode);
}
