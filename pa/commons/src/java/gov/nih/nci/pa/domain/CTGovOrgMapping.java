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
@Table(name = "ctgov_org_map")
@org.hibernate.annotations.Entity(mutable = false)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CTGovOrgMapping extends CTGovBaseMapping {

    private static final long serialVersionUID = 2827128893597594641L;
    private Organization ctgovOrg;
    private Organization pdqOrg;

    /**
     * @author Denis G. Krylov
     * 
     */
    @Embeddable
    public static class Organization implements Serializable {

        private String name, city, state, country;

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the city
         */
        public String getCity() {
            return city;
        }

        /**
         * @param city
         *            the city to set
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * @return the state
         */
        public String getState() {
            return state;
        }

        /**
         * @param state
         *            the state to set
         */
        public void setState(String state) {
            this.state = state;
        }

        /**
         * @return the country
         */
        public String getCountry() {
            return country;
        }

        /**
         * @param country
         *            the country to set
         */
        public void setCountry(String country) {
            this.country = country;
        }

    }

    /**
     * @return the ctgovOrg
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ctgov_name")),
            @AttributeOverride(name = "city", column = @Column(name = "ctgov_city")),
            @AttributeOverride(name = "state", column = @Column(name = "ctgov_state")),
            @AttributeOverride(name = "country", column = @Column(name = "ctgov_country")) })
    public Organization getCtgovOrg() {
        return ctgovOrg;
    }

    /**
     * @param ctgovOrg
     *            the ctgovOrg to set
     */
    public void setCtgovOrg(Organization ctgovOrg) {
        this.ctgovOrg = ctgovOrg;
    }

    /**
     * @return the pdqOrg
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "pdq_name")),
            @AttributeOverride(name = "city", column = @Column(name = "pdq_city")),
            @AttributeOverride(name = "state", column = @Column(name = "pdq_state")),
            @AttributeOverride(name = "country", column = @Column(name = "pdq_country")) })
    public Organization getPdqOrg() {
        return pdqOrg;
    }

    /**
     * @param pdqOrg
     *            the pdqOrg to set
     */
    public void setPdqOrg(Organization pdqOrg) {
        this.pdqOrg = pdqOrg;
    }

}
