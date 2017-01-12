package gov.nih.nci.pa.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author Denis G. Krylov
 * 
 */
@Entity
@Table(name = "ctgov_person_map")
@org.hibernate.annotations.Entity(mutable = false)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CTGovPersonMapping extends CTGovBaseMapping {

    private static final long serialVersionUID = 2827128893597594641L;

    private Person ctgovPerson;
    private Person pdqPerson;

    /**
     * @author Denis G. Krylov
     * 
     */
    @Embeddable
    public static class Person implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private String firstName, middleName, lastName, fullName;

        /**
         * @return the firstName
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * @param firstName
         *            the firstName to set
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * @return the middleName
         */
        public String getMiddleName() {
            return middleName;
        }

        /**
         * @param middleName
         *            the middleName to set
         */
        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        /**
         * @return the lastName
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * @param lastName
         *            the lastName to set
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        /**
         * @return the fullName
         */
        public String getFullName() {
            return fullName;
        }

        /**
         * @param fullName
         *            the fullName to set
         */
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

    }

    /**
     * @return the ctgovPerson
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "ctgov_firstname")),
            @AttributeOverride(name = "middleName", column = @Column(name = "ctgov_middlename")),
            @AttributeOverride(name = "lastName", column = @Column(name = "ctgov_lastname")),
            @AttributeOverride(name = "fullName", column = @Column(name = "ctgov_fullname")) })
    public Person getCtgovPerson() {
        return ctgovPerson;
    }

    /**
     * @param ctgovPerson
     *            the ctgovPerson to set
     */
    public void setCtgovPerson(Person ctgovPerson) {
        this.ctgovPerson = ctgovPerson;
    }

    /**
     * @return the pdqPerson
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "pdq_firstname")),
            @AttributeOverride(name = "middleName", column = @Column(name = "pdq_middlename")),
            @AttributeOverride(name = "lastName", column = @Column(name = "pdq_lastname")),
            @AttributeOverride(name = "fullName", column = @Column(name = "pdq_fullname")) })
    public Person getPdqPerson() {
        return pdqPerson;
    }

    /**
     * @param pdqPerson
     *            the pdqPerson to set
     */
    public void setPdqPerson(Person pdqPerson) {
        this.pdqPerson = pdqPerson;
    }

}
