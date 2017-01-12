/**
 *
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.iso21090.Ii;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @author Vrushali
 *
 */
public class PAContactDTO implements Serializable {
    private static final long serialVersionUID = -8882819211753249799L;
    private Ii srIdentifier = null; // structural role
    private Ii organizationIdentifier; // org
    private Ii personIdentifier; // person
    private Ii studyProtocolIdentifier;
    private String email;
    private String phone;
    private String title;
    private String fullName;

    /**
     * @return the srIdentifier
     */
    public Ii getSrIdentifier() {
        return srIdentifier;
    }
    /**
     * @param srIdentifier the srIdentifier to set
     */
    public void setSrIdentifier(Ii srIdentifier) {
        this.srIdentifier = srIdentifier;
    }
    /**
     * @return the organizationIdentifier
     */
    public Ii getOrganizationIdentifier() {
        return organizationIdentifier;
    }
    /**
     * @param organizationIdentifier the organizationIdentifier to set
     */
    public void setOrganizationIdentifier(Ii organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
    }
    /**
     * @return the personIdentifier
     */
    public Ii getPersonIdentifier() {
        return personIdentifier;
    }
    /**
     * @param personIdentifier the personIdentifier to set
     */
    public void setPersonIdentifier(Ii personIdentifier) {
        this.personIdentifier = personIdentifier;
    }
    /**
     * @return the studyProtocolIdentifier
     */
    public Ii getStudyProtocolIdentifier() {
        return studyProtocolIdentifier;
    }
    /**
     * @param studyProtocolIdentifier the studyProtocolIdentifier to set
     */
    public void setStudyProtocolIdentifier(Ii studyProtocolIdentifier) {
        this.studyProtocolIdentifier = studyProtocolIdentifier;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     *
     * @return s
     */
    public String getResponsiblePartyContactName() {
        String respPartyContactName = "";
        if (StringUtils.isNotEmpty(this.fullName)) {
            respPartyContactName = this.fullName;
        }
        if (StringUtils.isNotEmpty(this.title)) {
            respPartyContactName = this.title;
        }
        return respPartyContactName;
    }
}
