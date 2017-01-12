/**
 * 
 */
package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * @author dkrylov
 * 
 */
@Entity
@Table(name = "email_attachment")
public class EmailAttachment implements PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String filename;
    private byte[] data;

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
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     *            the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data; // NOPMD
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(byte[] data) { // NOPMD
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
