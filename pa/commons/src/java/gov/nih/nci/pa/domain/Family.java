/**
 * 
 */
package gov.nih.nci.pa.domain;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.nih.nci.pa.enums.ActiveInactiveCode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * @author dkrylov
 * 
 */
@Entity
@Table(name = "family")
public class Family implements PersistentObject, Auditable {

    private static final long serialVersionUID = 1234567890L;

    private Long id;
    private Long poId;
    private Date reportingPeriodEnd;
    private Integer reportingPeriodLength;
    private Set<ProgramCode> programCodes = new TreeSet<>();

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
     * @return the poId
     */
    @NotNull
    @Column(name = "po_id")
    @Searchable
    public Long getPoId() {
        return poId;
    }

    /**
     * @param poId
     *            the poId to set
     */
    public void setPoId(Long poId) {
        this.poId = poId;
    }

    /**
     * @return the reportingPeriodEnd
     */
    @NotNull
    @Column(name = "rep_period_end")
    @Searchable
    public Date getReportingPeriodEnd() {
        return reportingPeriodEnd;
    }

    /**
     * @param reportingPeriodEnd
     *            the reportingPeriodEnd to set
     */
    public void setReportingPeriodEnd(Date reportingPeriodEnd) {
        this.reportingPeriodEnd = reportingPeriodEnd;
    }

    /**
     * @return the reportingPeriodLength
     */
    @NotNull    
    @Column(name = "rep_period_len_months")
    @Searchable
    public Integer getReportingPeriodLength() {
        return reportingPeriodLength;
    }

    /**
     * @param reportingPeriodLength
     *            the reportingPeriodLength to set
     */
    public void setReportingPeriodLength(Integer reportingPeriodLength) {
        this.reportingPeriodLength = reportingPeriodLength;
    }

    /**
     * @return the programCodes
     */
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    @Sort(type = SortType.NATURAL)
    public Set<ProgramCode> getProgramCodes() {
        return programCodes;
    }

    /**
     * @param programCodes
     *            the programCodes to set
     */
    public void setProgramCodes(Set<ProgramCode> programCodes) {
        this.programCodes = programCodes;
    }

    /**
     * Will return a matching active program code from family
     *
     * @param pgCode the code of ProgramCode
     * @return ProgramCode
     */
    public ProgramCode findActiveProgramCodeByCode(String pgCode) {
        for (ProgramCode pg : programCodes) {
            if (pg.getStatusCode() == ActiveInactiveCode.ACTIVE && pg.getProgramCode().equals(pgCode)) {
                return pg;
            }
        }
        return null;
    }
}
