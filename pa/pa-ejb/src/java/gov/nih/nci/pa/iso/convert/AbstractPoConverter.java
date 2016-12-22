package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.dto.PACorrelationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.CorrelationDto;
/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 * @param <BO> domain object
 * @param <DTOPO> dto
 * @param <DTOPA> dtoPa
 */

public abstract class AbstractPoConverter <DTOPA extends PACorrelationDTO , 
    DTOPO extends CorrelationDto,  
    BO extends StructuralRole > {
    
    /**
     * @param dto dto
     * @return domain object
     * @throws PAException exception
     */
    public abstract BO convertFromPODtoToPADto(DTOPO dto) throws PAException;
    /**
     * @param dto dto object
     * @return dto
     * @throws PAException exception
     */
    public abstract CorrelationDto convertFromPADtoToPoDto(DTOPA dto) throws PAException;
    
    /**
     * 
     * @param dto dto obj
     * @param per person object
     * @param org organization object
     * @return dto
     * @throws PAException on error
     */
    public abstract BO convertToDomain(DTOPA dto ,  Organization org , Person per) throws PAException;

    
}
