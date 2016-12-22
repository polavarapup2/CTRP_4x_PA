package gov.nih.nci.registry.rest.exception;

/**
 * @author Hugh Reinhart
 * @since Mar 4, 2013
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = -6867895673025025126L;

    /**
     * @param message the message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
