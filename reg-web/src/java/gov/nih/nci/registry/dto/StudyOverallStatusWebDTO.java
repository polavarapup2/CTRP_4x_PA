/**
 *
 */
package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;

/**
 * DTO class for displaying study status .
 *
 * @author Bala Nair
 */
public class StudyOverallStatusWebDTO implements Serializable {
    private static final long serialVersionUID = -7501476168426334890L;
    private String statusCode;
    private String statusDate;
    private String reason;

    /**
     * @param dto The iso dto object.
     */
    public StudyOverallStatusWebDTO(StudyOverallStatusDTO dto) {
        super();
        this.statusCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode()).getDisplayName();
        this.statusDate = PAUtil.normalizeDateString(
                TsConverter.convertToTimestamp(dto.getStatusDate()).toString());
        this.reason = StConverter.convertToString(dto.getReasonText());
    }
    
    /** .
     *  Default Constructor
     */
    public StudyOverallStatusWebDTO() {
        super();
    }
    
    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }
    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }
    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
