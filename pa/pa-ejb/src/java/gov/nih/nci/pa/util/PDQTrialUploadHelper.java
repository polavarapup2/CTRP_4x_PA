package gov.nih.nci.pa.util;

import java.io.File;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @author Kalpana Guthikonda
 *
 */
public class PDQTrialUploadHelper implements Serializable {
    private static final long serialVersionUID = 3960604567380860990L;
    private File uploadFile;
    private String username;
    private String email;
    private MessageFormat htmlBody;
    private MessageFormat item;
    private MessageFormat line; 

    /**
     * @return uploadFile
     */
    public File getUploadFile() {
        return uploadFile;
    }

    /**
     * @param uploadFile set the uploadFile
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username set the username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email set the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return htmlBody
     */
    public MessageFormat getHtmlBody() {
        return htmlBody;
    }

    /**
     * @param htmlBody set the htmlBody
     */
    public void setHtmlBody(MessageFormat htmlBody) {
        this.htmlBody = htmlBody;
    }

    /**
     * @return item
     */
    public MessageFormat getItem() {
        return item;
    }

    /**
     * @param item set the item
     */
    public void setItem(MessageFormat item) {
        this.item = item;
    }

    /**
     * @return line
     */
    public MessageFormat getLine() {
        return line;
    }

    /**
     * @param line set the line
     */
    public void setLine(MessageFormat line) {
        this.line = line;
    }
}
