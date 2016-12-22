/**
 * 
 */
package gov.nih.nci.pa.webservices;

/**
 * @author dkrylov
 * 
 */
public class PoEntityCannotBeCreatedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message String
     */
    public PoEntityCannotBeCreatedException(String message) {
        super(message);
    }

}
