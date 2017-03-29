package gov.nih.nci.pa.webservices.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by chandrasekaranp on 3/21/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyOverallStatusDTO {

    private String statusCode;
    private Date statusDate;
    private String reasonText;
    /**
     * const
     */
    public StudyOverallStatusDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param statusCode statusCode
     * @param statusDate  statusDate
     * @param reasonText reasonText
     */
    public StudyOverallStatusDTO(String statusCode, Date statusDate,
            String reasonText) {
        super();
        this.statusCode = statusCode;
        this.statusDate = statusDate;
        this.reasonText = reasonText;
    }
    /**
     * 
     * @return statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode the statusCode
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * 
     * @return statusDate
     */
    public Date getStatusDate() {
        return statusDate;
    }
    /**
     * 
     * @param statusDate the statusDate
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
    /**
     * 
     * @return reasonText
     */
    public String getReasonText() {
        return reasonText;
    }
    /**
     * 
     * @param reasonText the reasonText
     */
    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

}
