package gov.nih.nci.pa.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * DTO that describes eligibility criteria of a trial in human-readable format.
 *
 * @author kkanchinadam
 */
public class EligibilityCriteriaDTO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String acceptsHealthyVolunteers;
    private String gender;
    private String minimumAge;
    private String maximumAge;
    private String sampleMethodCode;
    private String studyPopulationDescription;
    private final List<String> otherCriteria = new ArrayList<String>();
    private final List<String> inclusionCriteria = new ArrayList<String>();
    private final List<String> exclusionCriteria = new ArrayList<String>();

    /**
     * @return the acceptsHealthyVolunteers
     */
    public String getAcceptsHealthyVolunteers() {
        return acceptsHealthyVolunteers;
    }

    /**
     * @param acceptsHealthyVolunteers the acceptsHealthyVolunteers to set
     */
    public void setAcceptsHealthyVolunteers(String acceptsHealthyVolunteers) {
        this.acceptsHealthyVolunteers = acceptsHealthyVolunteers;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the minimumAge
     */
    public String getMinimumAge() {
        return minimumAge;
    }

    /**
     * @param minimumAge the minimumAge to set
     */
    public void setMinimumAge(String minimumAge) {
        this.minimumAge = minimumAge;
    }

    /**
     * @return the maximumAge
     */
    public String getMaximumAge() {
        return maximumAge;
    }

    /**
     * @param maximumAge the maximumAge to set
     */
    public void setMaximumAge(String maximumAge) {
        this.maximumAge = maximumAge;
    }

    /**
     * @return the otherCriteria
     */
    public List<String> getOtherCriteria() {
        return otherCriteria;
    }

    /**
     * @return the inclusionCriteria
     */
    public List<String> getInclusionCriteria() {
        return inclusionCriteria;
    }

    /**
     * @return the exclusionCriteria
     */
    public List<String> getExclusionCriteria() {
        return exclusionCriteria;
    }

    /**
     * @return the sampleMethodCode
     */
    public String getSampleMethodCode() {
        return sampleMethodCode;
    }

    /**
     * @param sampleMethodCode the sampleMethodCode to set
     */
    public void setSampleMethodCode(String sampleMethodCode) {
        this.sampleMethodCode = sampleMethodCode;
    }

    /**
     * @return the studyPopulationDescription
     */
    public String getStudyPopulationDescription() {
        return studyPopulationDescription;
    }

    /**
     * @param studyPopulationDescription the studyPopulationDescription to set
     */
    public void setStudyPopulationDescription(String studyPopulationDescription) {
        this.studyPopulationDescription = studyPopulationDescription;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */    
    @Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {       
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {       
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
