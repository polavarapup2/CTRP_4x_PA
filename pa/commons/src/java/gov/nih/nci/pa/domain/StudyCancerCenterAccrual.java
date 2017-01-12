/**
 * 
 */
package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * @author chandrasekaravr
 * 
 */
@Entity
@Table(name = "study_cancer_center_accrual")
public class StudyCancerCenterAccrual implements PersistentObject, Auditable {

    private static final long serialVersionUID = 13181511956432378L;
    
    private Long id;
    private StudyProtocol studyProtocol;
    private Family family;
    private Integer targetedAccrual;
    
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
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    @Searchable
    public Long getId() {
        return this.id;
    }
    
    /**
     * @return the studyProtocol
     */
    @OneToOne
    @JoinColumn(name = "study_protocol_id")
    @Searchable(nested = true)
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }

    /**
     * @param studyProtocol
     *            the StudyProtocol to set
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }

    /**
     * @return the family
     */
    @ManyToOne
    @JoinColumn(name = "family_id")
    @Searchable(nested = true)
    public Family getFamily() {
        return family;
    }

    /**
     * @param family
     *            the family to set
     */
    public void setFamily(Family family) {
        this.family = family;
    }

    /**
     * @return the targetedAccrual
     */
    @Column(name = "targeted_accrual")
    @NotNull
    public Integer getTargetedAccrual() {
        return targetedAccrual;
    }

    /**
     * @param targetedAccrual the targetedAccrual to set
     */
    public void setTargetedAccrual(Integer targetedAccrual) {
        this.targetedAccrual = targetedAccrual;
    }
}
