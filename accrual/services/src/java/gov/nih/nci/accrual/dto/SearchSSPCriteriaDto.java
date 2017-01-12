package gov.nih.nci.accrual.dto;

import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Hugh Reinhart
 * @since Jun 28, 2012
 */
public class SearchSSPCriteriaDto implements Serializable {

    private static final long serialVersionUID = -4863535194750868706L;

    private List<Long> studySiteIds = new ArrayList<Long>();
    private FunctionalRoleStatusCode  studySubjectStatusCode;
    private String studySubjectAssignedIdentifier;
    private String patientBirthDate;

    /**
     * @return the studySiteIds
     */
    public List<Long> getStudySiteIds() {
        return studySiteIds;
    }
    /**
     * @param studySiteIds the studySiteIds to set
     */
    public void setStudySiteIds(List<Long> studySiteIds) {
        this.studySiteIds = studySiteIds;
    }
    /**
     * @return the studySubjectStatusCode
     */
    public FunctionalRoleStatusCode getStudySubjectStatusCode() {
        return studySubjectStatusCode;
    }
    /**
     * @param studySubjectStatusCode the studySubjectStatusCode to set
     */
    public void setStudySubjectStatusCode(FunctionalRoleStatusCode studySubjectStatusCode) {
        this.studySubjectStatusCode = studySubjectStatusCode;
    }
    /**
     * @return the studySubjectAssignedIdentifier
     */
    public String getStudySubjectAssignedIdentifier() {
        return studySubjectAssignedIdentifier;
    }
    /**
     * @param studySubjectAssignedIdentifier the studySubjectAssignedIdentifier to set
     */
    public void setStudySubjectAssignedIdentifier(String studySubjectAssignedIdentifier) {
        this.studySubjectAssignedIdentifier = studySubjectAssignedIdentifier;
    }
    /**
     * @return the patientBirthDate
     */
    public String getPatientBirthDate() {
        return patientBirthDate;
    }
    /**
     * @param patientBirthDate the patientBirthDate to set
     */
    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }
}
