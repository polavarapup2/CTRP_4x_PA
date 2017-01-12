/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.NciDivisionProgramCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * @author Vrushali
 *
 */
@Entity
@Table (name = "STUDY_FUNDING_STAGE")
public class StudyFundingStage extends AbstractEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -4538577427187053537L;
    private String fundingMechanismCode;
    private String nihInstituteCode;
    private NciDivisionProgramCode nciDivisionProgramCode;
    private String serialNumber;
    private Double fundingPercent;
    private StudyProtocolStage studyProtocolStage;
    /**
     * @return the fundingMechanismCode
     */
    @Column (name = "FUNDING_MECHANISM_CODE")
    public String getFundingMechanismCode() {
        return fundingMechanismCode;
    }
    /**
     * @param fundingMechanismCode the fundingMechanismCode to set
     */
    public void setFundingMechanismCode(String fundingMechanismCode) {
        this.fundingMechanismCode = fundingMechanismCode;
    }
    /**
     * @return the nihInstituteCode
     */
    @Column (name = "NIH_INSTITUTE_CODE")
    public String getNihInstituteCode() {
        return nihInstituteCode;
    }
    /**
     * @param nihInstituteCode the nihInstituteCode to set
     */
    public void setNihInstituteCode(String nihInstituteCode) {
        this.nihInstituteCode = nihInstituteCode;
    }
    /**
     * @return the nciDivisionProgramCode
     */
    @Column (name = "NCI_DIVISION_PROGRAM_CODE")
    @Enumerated(EnumType.STRING)
    public NciDivisionProgramCode getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }
    /**
     * @param nciDivisionProgramCode the nciDivisionProgramCode to set
     */
    public void setNciDivisionProgramCode(
            NciDivisionProgramCode nciDivisionProgramCode) {
        this.nciDivisionProgramCode = nciDivisionProgramCode;
    }
    /**
     * @return the serialNumber
     */
    @Column (name = "SERIAL_NUMBER")
    public String getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    /**
     * @return the fundingPercent
     */
    @Column(name = "FUNDING_PERCENT")
    public Double getFundingPercent() {
        return fundingPercent;
    }
    /**
     * @param fundingPercent the fundingPercent to set
     */
    public void setFundingPercent(Double fundingPercent) {
        this.fundingPercent = fundingPercent;
    }
    /**
     * @param studyProtocolStage the studyProtocolStage to set
     */
    public void setStudyProtocolStage(StudyProtocolStage studyProtocolStage) {
        this.studyProtocolStage = studyProtocolStage;
    }
    /**
     * @return the tempStudyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_STAGE_IDENTIFIER", updatable = false)
    @NotNull
    public StudyProtocolStage getStudyProtocolStage() {
        return studyProtocolStage;
    }
    
}
