/**
 *
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudySiteStatusCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;

/**
 * Maintains history of overall status for the Site.
 *
 * @author Vrushali
 *
 */
@Entity
@Table(name = "STUDY_SITE_OVERALL_STATUS")
@org.hibernate.annotations.Table(appliesTo = "STUDY_SITE_OVERALL_STATUS", indexes = 
                                 {@Index(name = "study_site_overall_status_study_site_idx", 
                                  columnNames = { "STUDY_SITE_IDENTIFIER" }) })
public class StudySiteOverallStatus extends AbstractSiteEntity implements Auditable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private StudySiteStatusCode statusCode;
    private Timestamp statusDate;

    /**
     * @return the statusCode
     */
    @NotNull
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public StudySiteStatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(StudySiteStatusCode statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    @Column(name = "STATUS_DATE")
    @NotNull
    public Timestamp getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Timestamp statusDate) {
        this.statusDate = statusDate;
    }
}
