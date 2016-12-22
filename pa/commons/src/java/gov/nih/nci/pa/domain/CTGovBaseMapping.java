package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * 
 * @author Denis G. Krylov
 * 
 */
@MappedSuperclass
public class CTGovBaseMapping implements PersistentObject {

    private static final long serialVersionUID = 2827128893597594641L;
    private Long id;
    private String role;
    private String cdrId;
    private String poId;
    private String ctepId;

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
     * @return the role
     */
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the cdrId
     */
    @Column(name = "cdr_id")
    public String getCdrId() {
        return cdrId;
    }

    /**
     * @param cdrId
     *            the cdrId to set
     */
    public void setCdrId(String cdrId) {
        this.cdrId = cdrId;
    }

    /**
     * @return the poId
     */
    @Column(name = "po_id")
    public String getPoId() {
        return poId;
    }

    /**
     * @param poId
     *            the poId to set
     */
    public void setPoId(String poId) {
        this.poId = poId;
    }

    /**
     * @return the ctepId
     */
    @Column(name = "ctep_id")
    public String getCtepId() {
        return ctepId;
    }

    /**
     * @param ctepId
     *            the ctepId to set
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }

}
