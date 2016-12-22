package gov.nih.nci.accrual.dto.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * @author Hugh Reinhart
 * @since Aug 2, 2012
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class SubjectAccrualKey {

    private Long studySiteId;
    private String assignedIdentifier;

    /**
     * @param studySiteId the study site id
     * @param assignedIdentifier the site specific patient identifier
     */
    public SubjectAccrualKey(Long studySiteId, String assignedIdentifier) {
        this.studySiteId = studySiteId;
        this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @param studySiteId the study site id
     * @param assignedIdentifier the site specific patient identifier
     */
    public SubjectAccrualKey(Ii studySiteId, St assignedIdentifier) {
        this.studySiteId = IiConverter.convertToLong(studySiteId);
        this.assignedIdentifier = StConverter.convertToString(assignedIdentifier);
    }
    /**
     * @return the studySiteId
     */
    public Long getStudySiteId() {
        return studySiteId;
    }
    /**
     * @param studySiteId the studySiteId to set
     */
    public void setStudySiteId(Long studySiteId) {
        this.studySiteId = studySiteId;
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (assignedIdentifier == null ? 0 : assignedIdentifier.hashCode());
        result = prime * result + (studySiteId == null ? 0 : studySiteId.hashCode());
        return result;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SubjectAccrualKey other = (SubjectAccrualKey) obj;
        if (assignedIdentifier == null) {
            if (other.assignedIdentifier != null) {
                return false;
            }
        } else if (!assignedIdentifier.equals(other.assignedIdentifier)) {
            return false;
        }
        if (studySiteId == null) {
            if (other.studySiteId != null) {
                return false;
            }
        } else if (!studySiteId.equals(other.studySiteId)) {
            return false;
        }
        return true;
    }
}
