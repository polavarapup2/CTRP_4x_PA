package gov.nih.nci.pa.util;

/**
 * Exception thrown by the NCItTermLookUp class
 * @author gopal
 *
 */
public class LEXEVSLookupException extends Exception {
    /**
     * Constructor
     * @param message message
     * @param e exception
     */
    public LEXEVSLookupException(String message, Exception e) {
        super(message, e);
    }
}