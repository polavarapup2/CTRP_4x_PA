package gov.nih.nci.accrual.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Hugh Reinhart
 * @since Aug 4, 2012
 */
public class PatientListDto implements Serializable {

    private static final long serialVersionUID = -3855381571769457178L;

    private String identifier;
    private String assignedIdentifier;
    private Timestamp registrationDate;
    private String organizationName;
    private Timestamp dateLastUpdated;

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the assignedIdentifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @return the registrationDate
     */
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }
    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }
    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    /**
     * @return the dateLastUpdated
     */
    public Timestamp getDateLastUpdated() {
        return dateLastUpdated;
    }
    /**
     * @param dateLastUpdated the dateLastUpdated to set
     */
    public void setDateLastUpdated(Timestamp dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
}
