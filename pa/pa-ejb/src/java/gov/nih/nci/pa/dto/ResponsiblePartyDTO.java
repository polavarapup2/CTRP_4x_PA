/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.Serializable;

/**
 * Holds responsible party information.
 * 
 * @author Denis G. Krylov
 * 
 */
public class ResponsiblePartyDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ResponsiblePartyType type;
    private PersonDTO investigator;
    private String title;
    private OrganizationDTO affiliation;

    /**
     * @author Denis G. Krylov
     * 
     */
    public static enum ResponsiblePartyType {
        /**
         * 
         */
        PRINCIPAL_INVESTIGATOR("pi"), /**
         * 
         */
        SPONSOR_INVESTIGATOR("si"), /**
         * 
         */
        SPONSOR("sponsor");

        private String code;

        private ResponsiblePartyType(String code) {
            this.code = code;
        }

        /**
         * @return the code
         */
        public String getCode() {
            return code;
        }

        /**
         * @param code
         *            String
         * @return ResponsiblePartyType
         */
        public static ResponsiblePartyType getByCode(String code) {
            for (ResponsiblePartyType type : values()) {
                if (type.getCode().equalsIgnoreCase(code)) {
                    return type;
                }
            }
            return null;
        }

    }

    /**
     * @return the type
     */
    public ResponsiblePartyType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(ResponsiblePartyType type) {
        this.type = type;
    }

    /**
     * @return the investigator
     */
    public PersonDTO getInvestigator() {
        return investigator;
    }

    /**
     * @param investigator
     *            the investigator to set
     */
    public void setInvestigator(PersonDTO investigator) {
        this.investigator = investigator;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the affiliation
     */
    public OrganizationDTO getAffiliation() {
        return affiliation;
    }

    /**
     * @param affiliation
     *            the affiliation to set
     */
    public void setAffiliation(OrganizationDTO affiliation) {
        this.affiliation = affiliation;
    }

}
