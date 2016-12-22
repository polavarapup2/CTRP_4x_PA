/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.OpOutcomeCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * @author dkrylov
 * 
 */
@Entity
@Table(name = "email_log")
public class EmailLog implements PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date dateSent;
    private OpOutcomeCode outcome;
    private String errors;
    private String sender;
    private String recipient;
    private String cc;
    private String bcc;
    private String subject;
    private String body;

    private List<EmailAttachment> attachments = new ArrayList<>();

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
     * @return the dateSent
     */
    @Column(name = "date_sent")
    public Date getDateSent() {
        return dateSent;
    }

    /**
     * @param dateSent
     *            the dateSent to set
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * @return the outcome
     */
    @Enumerated(EnumType.STRING)
    public OpOutcomeCode getOutcome() {
        return outcome;
    }

    /**
     * @param outcome
     *            the outcome to set
     */
    public void setOutcome(OpOutcomeCode outcome) {
        this.outcome = outcome;
    }

    /**
     * @return the errors
     */
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

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * @param recipient
     *            the recipient to set
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return the cc
     */
    public String getCc() {
        return cc;
    }

    /**
     * @param cc
     *            the cc to set
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * @return the bcc
     */
    public String getBcc() {
        return bcc;
    }

    /**
     * @param bcc
     *            the bcc to set
     */
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body
     *            the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @return the attachments
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id")
    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments
     *            the attachments to set
     */
    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

}
