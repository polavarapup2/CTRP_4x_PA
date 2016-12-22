package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.TweetStatusCode;
import gov.nih.nci.pa.util.CommonsConstant;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * Tweets queue.
 * 
 * @author dkrylov
 * 
 */
@Entity
@Table(name = "tweets")
public class Tweet implements Serializable {
    private static final long serialVersionUID = 1234567890L;

    private Long id;
    private String text;
    private TweetStatusCode status;
    private StudyProtocol studyProtocol;
    private Date createDate;
    private Date sentDate;
    private String accountName;
    private String errors;

    /**
     * set id.
     * 
     * @param id
     *            id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * 
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    @Searchable
    public Long getId() {
        return this.id;
    }

    /**
     * @return studyProtocol
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @Searchable(nested = true)
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }

    /**
     * @param studyProtocol
     *            studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }

    /**
     * @return the text
     */
    @Column(name = "tweet_text")
    @Length(max = CommonsConstant.TWEET_LENGTH)
    @NotNull
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the status
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    public TweetStatusCode getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(TweetStatusCode status) {
        this.status = status;
    }

    /**
     * @return the createDate
     */
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the sentDate
     */
    @Column(name = "sent_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSentDate() {
        return sentDate;
    }

    /**
     * @param sentDate
     *            the sentDate to set
     */
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    /**
     * @return the accountName
     */
    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the errors
     */
    @Column(name = "errors")
    public String getErrors() {
        return errors;
    }

    /**
     * @param errors
     *            the errors to set
     */
    public void setErrors(String errors) {
        this.errors = errors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Tweet [id=" + id + ", text=" + text + "]";
    }

}
