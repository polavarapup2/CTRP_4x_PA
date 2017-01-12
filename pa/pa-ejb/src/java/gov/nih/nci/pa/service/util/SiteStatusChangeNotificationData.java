/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Just a container for data.
 * 
 * @author dkrylov
 * 
 */
public final class SiteStatusChangeNotificationData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final Ii studyProtocolID;

    private final StudyOverallStatusDTO previousTrialStatus;

    private final StudyOverallStatusDTO newTrialStatus;

    private List<SiteData> siteData = new ArrayList<>();

    /**
     * @param studyProtocolID
     *            studyProtocolID
     * @param previousTrialStatus
     *            previousTrialStatus
     * @param newTrialStatus
     *            newTrialStatus
     */
    public SiteStatusChangeNotificationData(Ii studyProtocolID,
            StudyOverallStatusDTO previousTrialStatus,
            StudyOverallStatusDTO newTrialStatus) {
        this.studyProtocolID = studyProtocolID;
        this.previousTrialStatus = previousTrialStatus;
        this.newTrialStatus = newTrialStatus;
    }

    /**
     * @author dkrylov
     * 
     */
    public static final class SiteData implements Serializable, Comparable<SiteData> {

        private final String name;
        private final RecruitmentStatusCode previousTrialStatus;
        private final RecruitmentStatusCode newStatus;
        private final String errors;
        private String previousTrialStatusDate;
        private static final long serialVersionUID = 1L;

        /**
         * @param name
         *            name
         * @param previousTrialStatus
         *            spreviousTrialStatus
         * @param newStatus
         *            newStatus
         * @param errors
         *            errors
         */
        public SiteData(String name, RecruitmentStatusCode previousTrialStatus,
                RecruitmentStatusCode newStatus, String errors) {
            this.name = name;
            this.previousTrialStatus = previousTrialStatus;
            this.newStatus = newStatus;
            this.errors = errors;
        }

        /**
         * 
         */
      

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the previousTrialStatus
         */
        public RecruitmentStatusCode getPreviousTrialStatus() {
            return previousTrialStatus;
        }

        /**
         * @return the newStatus
         */
        public RecruitmentStatusCode getNewStatus() {
            return newStatus;
        }

        /**
         * @return the errors
         */
        public String getErrors() {
            return errors;
        }

        /** 
         * @return previousTrialStatusDate
         */
        public String getPreviousTrialStatusDate() {
            return previousTrialStatusDate;
        }

        /**
         * @param previousTrialStatusDate previousTrialStatusDate
         */
        public void setPreviousTrialStatusDate(String previousTrialStatusDate) {
            this.previousTrialStatusDate = previousTrialStatusDate;
        }

        @Override
        public int compareTo(SiteData o) {
            return StringUtils.defaultString(name).compareTo(
                    StringUtils.defaultString(o.name));
        }

    }

    /**
     * @return the studyProtocolID
     */
    public Ii getStudyProtocolID() {
        return studyProtocolID;
    }

    /**
     * @return the previousTrialStatus
     */
    public StudyOverallStatusDTO getPreviousTrialStatus() {
        return previousTrialStatus;
    }

    /**
     * @return the siteData
     */
    public List<SiteData> getSiteData() {
        return siteData;
    }

    /**
     * @param siteData
     *            the siteData to set
     */
    public void setSiteData(List<SiteData> siteData) {
        this.siteData = siteData;
    }

    /**
     * @return the newTrialStatus
     */
    public StudyOverallStatusDTO getNewTrialStatus() {
        return newTrialStatus;
    }

}
