package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.dto.PACorrelationDTO;
import gov.nih.nci.pa.service.PAException;


/**
 * 
 * @author NAmiruddin
 *
 * @param <PADTO> dtoPa
 */
public interface PABaseCorrelationService <PADTO extends PACorrelationDTO> {
    
    /**
     * @param dto dto
     * @return created object
     * @throws PAException exception
     */
    Long create(PADTO dto) throws PAException;    
    

}
