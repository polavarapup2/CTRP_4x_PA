/**
 * caBIG Open Source Software License
 */
package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
@Entity
@Table(name = "TRIAL_DATA_VERIFICATION")
public class TrialDataVerification extends AbstractStudyEntity {
    private static final long serialVersionUID = 1L;
    /** Maximum length of  column length . */
    private static final int DEFAULT_COLUMN_LENGTH = 200;
    private String verificationMethod;
    /**
     * 
     * @return verificationMethod the verificationMethod
     */
    @Column(name = "verification_method", length = DEFAULT_COLUMN_LENGTH, nullable = false)
    @NotNull
    public String getVerificationMethod() {
        return verificationMethod;
    }
    /**
     * 
     * @param verificationMethod verificationMethod
     */
    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }
    
}
