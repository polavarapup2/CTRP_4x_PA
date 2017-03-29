package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Created by chandrasekaranp on 3/21/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudySiteDTO {

    /*private String healthcareFacilityIi;
    private String researchOrganizationIi;
    private String oversightCommitteeIi;*/

    private String localStudyProtocolIdentifier;
    /*private String reviewBoardApprovalNumber;
    private Date reviewBoardApprovalDate;
    private Integer targetAccrualNumber;
    private String reviewBoardOrganizationalAffiliation;
    private String programCodeText;
    private Date accrualDateRangeMin;
    private Date accrualDateRangeMax;*/
    /**
     * 
     * @param localStudyProtocolIdentifier localStudyProtocolIdentifier
     */
    public StudySiteDTO(String localStudyProtocolIdentifier) {
        super();
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
    /**
     * const
     */
    public StudySiteDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @return localStudyProtocolIdentifier
     */

    public String getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }
    /**
     * 
     * @param localStudyProtocolIdentifier the localStudyProtocolIdentifier
     */
    public void setLocalStudyProtocolIdentifier(String localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
}
