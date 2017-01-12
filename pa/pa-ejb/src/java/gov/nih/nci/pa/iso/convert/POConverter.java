package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.service.PAException;

/**
 * 
 * @author NAmiruddin
 *
 */
public class POConverter {

    private static OrganizationalContactConverter oc = new OrganizationalContactConverter();
    
    /**
     * @param clazz class
     * @param <TYPE> the converter type to get
     * @return converter
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    public static <TYPE extends AbstractPoConverter> TYPE get(Class<TYPE> clazz)  throws PAException {
        if (clazz.equals(OrganizationalContactConverter.class)) {
            return (TYPE) oc;
        }
        throw new PAException("Converter needs to be added to gov.nih.nci.pa.iso.convert.po.PoConverters.  ");
    }
    
   
}    

