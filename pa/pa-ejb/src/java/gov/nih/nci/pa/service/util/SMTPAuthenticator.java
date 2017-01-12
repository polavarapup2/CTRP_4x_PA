package gov.nih.nci.pa.service.util;

import javax.mail.PasswordAuthentication;

/**
 * 
 * @author lalit-sb
 *
 */
public class SMTPAuthenticator extends javax.mail.Authenticator {
    
    /**
     * username
     */
    private String userName;
    
    /**
     * password
     */
    private String password;
    
    /**
     * Intializes the authenticator with username and password
     * @param userName 
     *          user name
     * @param password
     *          password
     */
    public SMTPAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    /**
     * Returns password authentication
     * @return PasswordAuthentication
     *                    
     */
    public PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(userName, password);
    }
}


