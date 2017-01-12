package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AccrualChangeCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since Jul 3, 2012
 */
@Entity
@Table(name = "ACCRUAL_COLLECTIONS")
public class AccrualCollections extends AbstractEntity {

    private static final long serialVersionUID = 4897356017387688389L;

    private BatchFile batchFile;
    private boolean passedValidation;
    private AccrualChangeCode changeCode;
    private String results;
    private String nciNumber;
    private Integer totalImports;

    /**
     * @return the batchFile
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_file_identifier", updatable = false)
    @NotNull
    public BatchFile getBatchFile() {
        return batchFile;
    }

    /**
     * @param batchFile the batchFile to set
     */
    public void setBatchFile(BatchFile batchFile) {
        this.batchFile = batchFile;
    }

    /**
     * @return the passedValidation
     */
    @Column(name = "passed_validation")
    @NotNull
    public boolean isPassedValidation() {
        return passedValidation;
    }

    /**
     * @param passedValidation the passedValidation to set
     */
    public void setPassedValidation(boolean passedValidation) {
        this.passedValidation = passedValidation;
    }

    /**
     * @return the changeCode
     */
    @Column(name = "change_code")
    @Enumerated(EnumType.STRING)
    public AccrualChangeCode getChangeCode() {
        return changeCode;
    }

    /**
     * @param changeCode the changeCode to set
     */
    public void setChangeCode(AccrualChangeCode changeCode) {
        this.changeCode = changeCode;
    }

    /**
     * @return the results
     */
    @Column(name = "results")
    public String getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(String results) {
        this.results = results;
    }

    /**
     * @return the nciNumber
     */
    @Column(name = "nci_number")
    public String getNciNumber() {
        return nciNumber;
    }

    /**
     * @param nciNumber the nciNumber to set
     */
    public void setNciNumber(String nciNumber) {
        this.nciNumber = nciNumber;
    }

    /**
     * @return the totalImports
     */
    @Column(name = "total_imports")
    public Integer getTotalImports() {
        return totalImports;
    }

    /**
     * @param totalImports the totalImports to set
     */
    public void setTotalImports(Integer totalImports) {
        this.totalImports = totalImports;
    }
}
