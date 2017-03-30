package gov.nih.nci.pa.webservices.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Reshma
 *
 */
@SuppressWarnings({ "PMD.ExcessiveParameterList" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class CTGovImportLog {
    private String nciId;
    private String nctId;
    private String title;
    private String action;
    private String importStatus;
    private boolean needsReview;
    private boolean adminChanged;
    private boolean scientificChanged;
    private Long studyInboxId;
    private String userCreated;
    private Date dateCreated;
    /**
     * const
     */
    public CTGovImportLog() {
        super();
        // TODO Auto-generated constructor stub
    }
    // CHECKSTYLE:OFF
    /**
     * 
     * @param nciId nciId
     * @param nctId nctId
     * @param title title
     * @param action action
     * @param importStatus importStatus
     * @param needsReview needsReview
     * @param adminChanged adminChanged
     * @param scientificChanged scientificChanged
     * @param studyInboxId studyInboxId
     * @param userCreated userCreated
     * @param dateCreated dateCreated
     */
    public CTGovImportLog(String nciId, String nctId, String title,
            String action, String importStatus, boolean needsReview,
            boolean adminChanged, boolean scientificChanged, Long studyInboxId,
            String userCreated, Date dateCreated) {
        super();
        this.nciId = nciId;
        this.nctId = nctId;
        this.title = title;
        this.action = action;
        this.importStatus = importStatus;
        this.needsReview = needsReview;
        this.adminChanged = adminChanged;
        this.scientificChanged = scientificChanged;
        this.studyInboxId = studyInboxId;
        this.userCreated = userCreated;
        this.dateCreated = dateCreated;
    }
    // CHECKSTYLE:ON
    /**
     * 
     * @return nciId
     */
    public String getNciId() {
        return nciId;
    }
    /**
     * 
     * @param nciId nciId
     */
    public void setNciId(String nciId) {
        this.nciId = nciId;
    }
    /**
     * 
     * @return nctId
     */
    public String getNctId() {
        return nctId;
    }
    /**
     *  
     * @param nctId the nctId
     */
    public void setNctId(String nctId) {
        this.nctId = nctId;
    }   
    /**
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /**
     * 
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * 
     * @return action
     */
    public String getAction() {
        return action;
    }
    /**
     * 
     * @param action action
     */
    public void setAction(String action) {
        this.action = action;
    }
    /**
     * 
     * @return importStatus
     */
    public String getImportStatus() {
        return importStatus;
    }
    /**
     * 
     * @param importStatus importStatus
     */
    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }
    /**
     * 
     * @return needsReview
     */
    public boolean isNeedsReview() {
        return needsReview;
    }
    /**
     * 
     * @param needsReview needsReview
     */
    public void setNeedsReview(boolean needsReview) {
        this.needsReview = needsReview;
    }
    /**
     * 
     * @return adminChanged 
     */
    public boolean isAdminChanged() {
        return adminChanged;
    }
    /**
     * 
     * @param adminChanged adminChanged
     */
    public void setAdminChanged(boolean adminChanged) {
        this.adminChanged = adminChanged;
    }
    /**
     * 
     * @return scientificChanged
     */
    public boolean isScientificChanged() {
        return scientificChanged;
    }
    /**
     * 
     * @param scientificChanged scientificChanged
     */
    public void setScientificChanged(boolean scientificChanged) {
        this.scientificChanged = scientificChanged;
    }
    /**
     * 
     * @return studyInboxId
     */
    public Long getStudyInboxId() {
        return studyInboxId;
    }
    /**
     * 
     * @param studyInboxId studyInboxId
     */
    public void setStudyInboxId(Long studyInboxId) {
        this.studyInboxId = studyInboxId;
    }
    /**
     * 
     * @return userCreated
     */
    public String getUserCreated() {
        return userCreated;
    }
    /**
     * 
     * @param userCreated userCreated
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }
    /**
     * 
     * @return dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * 
     * @param dateCreated dateCreated
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}