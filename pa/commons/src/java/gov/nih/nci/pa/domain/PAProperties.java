package gov.nih.nci.pa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Look up table for PA Properties.
 *
 * @author Kalpana Guthikonda
 * @since 12/09/2008
 */

@Entity
@Table(name = "PA_PROPERTIES")
public class PAProperties implements Serializable {
    private static final long serialVersionUID = 1143701320723960374L;
    private Long identifier;
    private String name;
    private String value;

    /**
     * Get the identifier of the object.
     * @return the identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getIdentifier() {
        return this.identifier;
    }

    /**
     * set identifier.
     * @param identifier identifier
     */
    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }
    /**
     * @return name
     */
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    /**
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return value
     */
    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    /**
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
