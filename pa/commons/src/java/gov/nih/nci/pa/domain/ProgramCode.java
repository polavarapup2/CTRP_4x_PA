/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactiveCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * @author dkrylov
 * 
 */
@Entity
@Table(name = "program_code")
public class ProgramCode implements PersistentObject, Auditable,
        Comparable<ProgramCode> {

    private static final long serialVersionUID = 1234567890L;

    private Long id;
    private String programCode;
    private String programName;
    private ActiveInactiveCode statusCode;
    private Family family;

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
     * @return the programCode
     */
    @NotNull
    @Length(min = 1)
    @Column(name = "program_code")
    @Searchable
    public String getProgramCode() {
        return programCode;
    }

    /**
     * @param programCode
     *            the programCode to set
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * @return the programName
     */
    @Column(name = "program_name")
    @Searchable
    public String getProgramName() {
        return programName;
    }

    /**
     * @param programName
     *            the programName to set
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * @return the statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    @NotNull
    @Searchable
    public ActiveInactiveCode getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(ActiveInactiveCode statusCode) {
        this.statusCode = statusCode;
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

    @Override
    public int compareTo(ProgramCode other) {
        return stringForComparison().compareTo(other.stringForComparison());
    }

    private String stringForComparison() {
        return (family != null ? family.getId() : "") + "_" + getProgramCode()
                + "_" + getStatusCode() + "_" + getId();
    }

}
