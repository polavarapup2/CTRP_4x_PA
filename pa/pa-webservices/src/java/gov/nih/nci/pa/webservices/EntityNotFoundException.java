/**
 * 
 */
package gov.nih.nci.pa.webservices;

/**
 * @author dkrylov
 * 
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message String
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

}
