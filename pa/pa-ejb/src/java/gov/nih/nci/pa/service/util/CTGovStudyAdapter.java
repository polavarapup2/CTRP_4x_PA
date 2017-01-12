/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.ctgov.InterventionStruct;

import org.apache.commons.lang.StringUtils;

/**
 * An adapter for the {@link ClinicalStudy} class. Its purpose is to simplify
 * access to the study information contained in a "raw" instance of
 * {@link ClinicalStudy} class, i.e. to avoid having for example UI code to
 * navigate through CT.Gov XML tree structure.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class CTGovStudyAdapter {

    private final ClinicalStudy study;

    /**
     * @param study
     *            study
     */
    public CTGovStudyAdapter(ClinicalStudy study) {
        this.study = study;
    }

    /**
     * @return nct id
     */
    public String getNctId() {
        return study.getIdInfo() != null ? study.getIdInfo().getNctId() : "";
    }

    /**
     * @return status
     */
    public String getStatus() {
        return study.getOverallStatus();
    }

    /**
     * @return title
     */
    public String getTitle() {
        return StringUtils.isNotBlank(study.getOfficialTitle()) ? study
                .getOfficialTitle() : study.getBriefTitle();
    }

    /**
     * @return list of conditions
     */
    public String getConditions() {
        return StringUtils.join(study.getCondition(), ", ");
    }

    /**
     * @return list of interventions
     */
    public String getInterventions() {
        StringBuilder sb = new StringBuilder();
        for (InterventionStruct i : study.getIntervention()) {
            sb.append(StringUtils.defaultString(i.getInterventionType()));
            sb.append(": ");
            sb.append(StringUtils.defaultString(i.getInterventionName()));
            sb.append("; ");
        }
        return sb.toString().replaceFirst("; $", "");
    }

    /**
     * @return type
     */
    public String getStudyCategory() {
        return study.getSponsors() != null
                && study.getSponsors().getLeadSponsor() != null ? study
                .getSponsors().getLeadSponsor().getAgencyClass() : "";
    }

}
