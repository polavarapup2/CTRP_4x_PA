package gov.nih.nci.pa.service;


/**
 * PA Exception for Invalid Password.
 * 
 * @author oweisms
 */
public class PAInvalidPasswordException extends PAException {

    private static final long serialVersionUID = 8351693095420333041L;

    /**
     * no argument constructor.
     */
    public PAInvalidPasswordException() {
        super();
    }

    /**
     * String constructor.
     * @param message message
     */
    public PAInvalidPasswordException(String message) {
        super(message);
    }

    /**
     * String and Throwable constructor.
     * @param message message
     * @param t t
     */
    public PAInvalidPasswordException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * 
     * @param t t
     */
    public PAInvalidPasswordException(Throwable t) {
        super(t);
    }
    
}
