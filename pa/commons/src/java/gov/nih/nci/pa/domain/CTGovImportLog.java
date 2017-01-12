package gov.nih.nci.pa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
/**
 * 
 * @author Monish
 * 
 */
@Entity
@Table(name = "CTGOVIMPORT_LOG")
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public class CTGovImportLog implements PersistentObject {

    /**
     * NO PENDING/PERFORMED ACKNOWLEDGEMENT
     */
    public static final String NO_ACKNOWLEDGEMENT = "No";
    /**
     * PENDING/PERFORMED ADMIN ACKNOWLEDGEMENT
     */
    public static final String ADMIN_ACKNOWLEDGMENT = "Admin";
    /**
     * PENDING/PERFORMED SCIENTIFIC ACKNOWLEDGEMENT
     */
    public static final String SCIENTIFIC_ACKNOWLEDGEMENT = "Scientific";
    /**
     * PENDING/PERFORMED ADMIN AND SCIENTIFIC ACKNOWLEDGEMENT
     */
    public static final String ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT = "Admin & Scientific";
    private static final long serialVersionUID = 2827128893597594641L;
    private Long id;
    private String nciID;
    private String nctID;
    private String title;
    private String action;
    private String userCreated;
    private Date dateCreated;
    private String importStatus;
    private Boolean reviewRequired;
    private Boolean admin;
    private Boolean scientific;
    private StudyInbox studyInbox;
    private String ackPending = "";
    private String ackPerformed = "";
    /**
     * 
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return NCI ID
     */
    @Column(name = "NCI_ID")
    public String getNciID() {
        return nciID;
    }

    /**
     * 
     * @param nciID
     *            set the NCI ID
     */
    public void setNciID(String nciID) {
        this.nciID = nciID;
    }

    /**
     * 
     * @return NCT ID
     */
    @Column(name = "NCT_ID")
    public String getNctID() {
        return nctID;
    }

    /**
     * 
     * @param nctID
     *            NCT ID to set
     */
    public void setNctID(String nctID) {
        this.nctID = nctID;
    }

    /**
     * 
     * @return trial title
     */
    @Column(name = "TRIAL_TITLE")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *            trial title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return action performed (create or update)
     */
    @Column(name = "ACTION_PERFORMED")
    public String getAction() {
        return action;
    }

    /**
     * 
     * @param action
     *            set the action performed.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 
     * @return userCreated
     */
    @Column(name = "USER_CREATED")
    public String getUserCreated() {
        return userCreated;
    }

    /**
     * @return displayable review indicator
     */
    @Transient
    public String getDisplayableReviewIndicator() {
        String displayableReviewIndicator = "No";
        if (!reviewRequired && !admin && !scientific) {
            displayableReviewIndicator = "No";
        } else if (reviewRequired && !admin && !scientific) { 
            displayableReviewIndicator = "Yes";
        } else if (admin && !scientific) {
            displayableReviewIndicator = "Yes, Admin";
        } else if (!admin && scientific) {
            displayableReviewIndicator = "Yes, Scientific";
        } else if (admin && scientific) {
            displayableReviewIndicator = "Yes, Admin & Scientific";
        }
        return displayableReviewIndicator;
    }
    
    /**
     * 
     * @param userCreated
     *            set user who performed the import
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * 
     * @return date created
     */
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * 
     * @param dateCreated
     *            set date created
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * 
     * @return import status
     */
    @Column(name = "IMPORT_STATUS")
    public String getImportStatus() {
        return importStatus;
    }

    /**
     * 
     * @param importStatus
     *            set the status of import
     */
    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    /**
     * @return the reviewRequired
     */
    @Column(name = "review_required")
    public Boolean getReviewRequired() {
        return reviewRequired;
    }

    /**
     * @param reviewRequired the reviewRequired to set
     */
    public void setReviewRequired(Boolean reviewRequired) {
        this.reviewRequired = reviewRequired;
    }

    /**
     * @return the admin
     */
    @Column(name = "admin")
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * @return the scientific
     */
    @Column(name = "scientific")
    public Boolean getScientific() {
        return scientific;
    }

    /**
     * @param scientific the scientific to set
     */
    public void setScientific(Boolean scientific) {
        this.scientific = scientific;
    }
    
    /**
     * @return the studyInbox
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_inbox_id")
    public StudyInbox getStudyInbox() {
        return studyInbox;
    }
    
    /**
     * @param studyInbox the studyInbox to set
     */
    public void setStudyInbox(StudyInbox studyInbox) {
        this.studyInbox = studyInbox;
    }
    /**
     * @return the pending acknowledgment.
     */
    @Transient
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public String getAckPending() {
        if (studyInbox != null) {
            if (studyInbox.getCloseDate() == null) {
                if (Boolean.TRUE.equals(studyInbox.getAdmin()) 
                        && studyInbox.getAdminCloseDate() == null 
                        && (studyInbox.getScientific() == null || !studyInbox.getScientific())) {
                    ackPending = ADMIN_ACKNOWLEDGMENT;
                } else if (Boolean.TRUE.equals(studyInbox.getScientific()) 
                        && studyInbox.getScientificCloseDate() == null 
                        && (studyInbox.getAdmin() == null || !studyInbox.getAdmin())) {
                    ackPending = SCIENTIFIC_ACKNOWLEDGEMENT;                    
                } else if (Boolean.TRUE.equals(studyInbox.getAdmin()) 
                        && Boolean.TRUE.equals(studyInbox.getScientific()) 
                        && studyInbox.getAdminCloseDate() == null && studyInbox.getScientificCloseDate() == null) {
                    ackPending = ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT;
                }
            } else {
                ackPending = NO_ACKNOWLEDGEMENT;
            }
        }
        return ackPending;
    }
    
    /**
     * @return AckPendingAtTimeOfImport
     */
    @Transient
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public String getAckPendingAtTimeOfImport() {
        if (Boolean.TRUE.equals(getAdmin())
                && !Boolean.TRUE.equals(getScientific())) {
            return ADMIN_ACKNOWLEDGMENT;
        } else if (!Boolean.TRUE.equals(getAdmin())
                && Boolean.TRUE.equals(getScientific())) {
            return SCIENTIFIC_ACKNOWLEDGEMENT;
        } else if (Boolean.TRUE.equals(getAdmin())
                && Boolean.TRUE.equals(getScientific())) {
            return ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT;
        } else {
            return NO_ACKNOWLEDGEMENT;
        }

    }

    /**
     * @return the performed acknowledgment
     */
    @Transient
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public String getAckPerformed() {
        if (studyInbox != null) {
            if (studyInbox.getAdminCloseDate() != null 
                    && studyInbox.getScientificCloseDate() == null) {
                ackPerformed = ADMIN_ACKNOWLEDGMENT;
            } else if (studyInbox.getScientificCloseDate() != null 
                    && studyInbox.getAdminCloseDate() == null) {
                ackPerformed = SCIENTIFIC_ACKNOWLEDGEMENT;                
            } else if (studyInbox.getAdminCloseDate() != null 
                    && studyInbox.getScientificCloseDate() != null) {
                ackPerformed = ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT;
            } else if (studyInbox.getAdminCloseDate() == null 
                    && studyInbox.getScientificCloseDate() == null 
                    && (Boolean.TRUE.equals(studyInbox.getAdmin()) 
                            || Boolean.TRUE.equals(studyInbox.getScientific()))) {
                ackPerformed = NO_ACKNOWLEDGEMENT;
            }
        }
        return ackPerformed;
    }

    /**
     * @param ackPending pending acknowledgment to set
     */
    public void setAckPending(String ackPending) {
        this.ackPending = ackPending;
    }

    /**
     * @param ackPerformed performed acknowledgment to set
     */
    public void setAckPerformed(String ackPerformed) {
        this.ackPerformed = ackPerformed;
    }
}

