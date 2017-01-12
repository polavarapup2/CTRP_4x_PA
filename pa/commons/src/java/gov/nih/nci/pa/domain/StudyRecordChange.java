package gov.nih.nci.pa.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.Length;

/**
 * @author Apratim K
 *
 */
@Entity
@Table(name = "STUDY_RECORD_CHANGE")
public class StudyRecordChange extends AbstractStudyEntity {
    
    private String changeType;
    /**
     * MAX_LENGTH
     */
    protected static final int MAX_LENGTH = 2000;

    private String actionTaken;
    private Timestamp actionCompletionDate;

    /**
     * @return changeType
     */
    @Column(name = "CHANGE_TYPE")
    @Length(max = MAX_LENGTH)
    public String getChangeType() {
        return changeType;
    }

    /**
     * @param changeType changeType
     */
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    
    /**
     * @return actionTaken actionTaken
     */
    @Column(name = "ACTION_TAKEN")
    @Length(max = MAX_LENGTH)
    public String getActionTaken() {
        return actionTaken;
    }

    /**
     * @param actionTaken actionTaken
     */
    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    /**
     * @return actionCompletionDate
     */
    @Column(name = "ACTION_COMPLETION_DATE")
    public Timestamp getActionCompletionDate() {
        return actionCompletionDate;
    }

    /**
     * @param actionCompletionDate actionCompletionDate
     */
    public void setActionCompletionDate(Timestamp actionCompletionDate) {
        this.actionCompletionDate = actionCompletionDate;
    }


}
