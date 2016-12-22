package gov.nih.nci.pa.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fiveamsolutions.nci.commons.audit.Auditable;

/**
 * @author Kalpana Guthikonda
 * @since Aug 29, 2013
 *
 */
@Entity
@Table(name = "patient_stage")
@SuppressWarnings("PMD.TooManyFields")
public class PatientStage extends AbstractEntity implements Auditable {

    private static final long serialVersionUID = -9074135750212657095L;
    private String raceCode;  
    private String sexCode;
    private String ethnicCode;
    private Timestamp birthDate;
    private String countryCode;
    private String zip;
    private String studyIdentifier;
    private Long studyProtocolIdentifier;
    private String assignedIdentifier;
    private String paymentMethodCode;
    private String studySite; 
    private String diseaseCode;
    private String siteDiseaseCode; 
    private Timestamp registrationDate;
    private String registrationGroupId; 
    private Integer accrualCount; 
    private String fileName;
    private String submissionStatus;
    private String orgName;
    private String ctepId;
    private String dcpId;
    
    /**
     * @return the raceCode
     */
    @Column(name = "race_code")
    public String getRaceCode() {
        return raceCode;
    }
    /**
     * @param raceCode the raceCode to set
     */
    public void setRaceCode(String raceCode) {
        this.raceCode = raceCode;
    }
    /**
     * @return the sexCode
     */
    @Column(name = "sex_code")
    public String getSexCode() {
        return sexCode;
    }
    /**
     * @param sexCode the sexCode to set
     */
    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }
    /**
     * @return the ethnicCode
     */
    @Column(name = "ethnic_code")
    public String getEthnicCode() {
        return ethnicCode;
    }
    /**
     * @param ethnicCode the ethnicCode to set
     */
    public void setEthnicCode(String ethnicCode) {
        this.ethnicCode = ethnicCode;
    }
    /**
     * @return the birthDate
     */
    @Column(name = "birth_date")
    public Timestamp getBirthDate() {
        return birthDate;
    }
    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * @return the countryCode
     */
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }
    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @return the zip
     */
    @Column(name = "zip")
    public String getZip() {
        return zip;
    }
    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    /**
     * @return the studyIdentifier
     */
    @Column(name = "study_identifier")
    public String getStudyIdentifier() {
        return studyIdentifier;
    }
    /**
     * @param studyIdentifier the studyIdentifier to set
     */
    public void setStudyIdentifier(String studyIdentifier) {
        this.studyIdentifier = studyIdentifier;
    }
    /**
     * @return the studyProtocolIdentifier
     */
    @Column(name = "study_protocol_identifier")
    public Long getStudyProtocolIdentifier() {
        return studyProtocolIdentifier;
    }
    /**
     * @param studyProtocolIdentifier the studyProtocolIdentifier to set
     */
    public void setStudyProtocolIdentifier(Long studyProtocolIdentifier) {
        this.studyProtocolIdentifier = studyProtocolIdentifier;
    }
    /**
     * @return the assignedIdentifier
     */
    @Column(name = "assigned_identifier")
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @return the paymentMethodCode
     */
    @Column(name = "payment_method_code")
    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }
    /**
     * @param paymentMethodCode the paymentMethodCode to set
     */
    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }
    /**
     * @return the studySite
     */
    @Column(name = "study_site")
    public String getStudySite() {
        return studySite;
    }
    /**
     * @param studySite the studySite to set
     */
    public void setStudySite(String studySite) {
        this.studySite = studySite;
    }
    /**
     * @return the diseaseCode
     */
    @Column(name = "disease_code")
    public String getDiseaseCode() {
        return diseaseCode;
    }
    /**
     * @param diseaseCode the diseaseCode to set
     */
    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }
    /**
     * @return the siteDiseaseCode
     */
    @Column(name = "site_disease_code")
    public String getSiteDiseaseCode() {
        return siteDiseaseCode;
    }
    /**
     * @param siteDiseaseCode the siteDiseaseCode to set
     */
    public void setSiteDiseaseCode(String siteDiseaseCode) {
        this.siteDiseaseCode = siteDiseaseCode;
    }
    /**
     * @return the registrationDate
     */
    @Column(name = "registration_date")
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }
    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    /**
     * @return the registrationGroupId
     */
    @Column(name = "registration_group_id")
    public String getRegistrationGroupId() {
        return registrationGroupId;
    }
    /**
     * @param registrationGroupId the registrationGroupId to set
     */
    public void setRegistrationGroupId(String registrationGroupId) {
        this.registrationGroupId = registrationGroupId;
    }
    /**
     * @return the accrualCount
     */
    @Column(name = "accrual_count")
    public Integer getAccrualCount() {
        return accrualCount;
    }
    /**
     * @param accrualCount the accrualCount to set
     */
    public void setAccrualCount(Integer accrualCount) {
        this.accrualCount = accrualCount;
    }
    /**
     * @return the fileName
     */
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the submissionStatus
     */
    @Column(name = "submission_status")
    public String getSubmissionStatus() {
        return submissionStatus;
    }
    /**
     * @param submissionStatus the submissionStatus to set
     */
    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }
    /**
     * @return the orgName
     */
    @Transient
    public String getOrgName() {
        return orgName;
    }
    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    /**
     * @return the ctepId
     */
    @Transient
    public String getCtepId() {
        return ctepId;
    }
    /**
     * @param ctepId the ctepId to set
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }
    /**
     * @return the dcpId
     */
    @Transient
    public String getDcpId() {
        return dcpId;
    }
    /**
     * @param dcpId the dcpId to set
     */
    public void setDcpId(String dcpId) {
        this.dcpId = dcpId;
    } 
}
