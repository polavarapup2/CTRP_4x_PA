/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ts;

/**
 * @author Vrushali
 *
 */
public class StudySiteOverallStatusDTO extends SiteDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Cd statusCode;
    private Ts statusDate;
    /**
     * @return the statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    public Ts getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Ts statusDate) {
        this.statusDate = statusDate;
    }
    
}
