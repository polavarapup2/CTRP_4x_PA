package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.exception.PAValidationException;

import java.util.Map;


/**
 * StudyCancerCenterAccrualService
 * @author chandrasekaravr
 */
public interface StudyCancerCenterAccrualService {    
    
    /**
     * Returns the study cancer center accruals for a given family id
     * @param familyPoId family id 
     * @return Map<Long, Integer> - map of trial id to its target accrual
     * @throws PAValidationException if program code already exists
     */
    Map<Long, Integer> getStudyCancerCenterAccrualsByFamily(Long familyPoId)
            throws PAValidationException;
   
    /**
     * saves or updates Study Cancer Center Accrual in db
     * @param familyPOId family PO id
     * @param studyProtocolId study Protocol id
     * @param targetAccrual - target accrual count
     * @throws PAValidationException if program code already exists
     */
    void saveOrUpdateStudyCancerCenterAccrual(Long familyPOId,
            Long studyProtocolId, Integer targetAccrual)
            throws PAValidationException;

}
