/**
 * 
 */
package gov.nih.nci.registry.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.NotEmpty;

/**
 * @author Vrushali
 *
 */
public class ProprietaryTrialDTO extends BaseTrialDTO {
    private static final long serialVersionUID = -6255281337610196744L;
    private String sitePiIdentifier;
    private String sitePiName;
    private String siteOrganizationIdentifier;
    private String siteOrganizationName;
    private String localSiteIdentifier;
    private String siteStatusCode;
    private String siteStatusDate;
    private String dateOpenedforAccrual;
    private String dateClosedforAccrual;
    private String siteProgramCodeText;
    private List<SubmittedOrganizationDTO> participatingSitesList = new ArrayList<SubmittedOrganizationDTO>();
    
    /**
     * @return the sitePiIdentifier
     */
    @NotEmpty (message = "error.proprietary.submit.sitePI")
    public String getSitePiIdentifier() {
        return sitePiIdentifier;
    }
    /**
     * @param sitePiIdentifier the sitePiIdentifier to set
     */
    public void setSitePiIdentifier(String sitePiIdentifier) {
        this.sitePiIdentifier = sitePiIdentifier;
    }
    /**
     * @return the sitePiName
     */
    public String getSitePiName() {
        return sitePiName;
    }
    /**
     * @param sitePiName the sitePiName to set
     */
    public void setSitePiName(String sitePiName) {
        this.sitePiName = sitePiName;
    }
    /**
     * @return the siteOrganizationIdentifier
     */
    @NotEmpty (message = "error.proprietary.submit.siteOrg")
    public String getSiteOrganizationIdentifier() {
        return siteOrganizationIdentifier;
    }
    /**
     * @param siteOrganizationIdentifier the siteOrganizationIdentifier to set
     */
    public void setSiteOrganizationIdentifier(String siteOrganizationIdentifier) {
        this.siteOrganizationIdentifier = siteOrganizationIdentifier;
    }
    /**
     * @return the siteOrganizationName
     */
    public String getSiteOrganizationName() {
        return siteOrganizationName;
    }
    /**
     * @param siteOrganizationName the siteOrganizationName to set
     */
    public void setSiteOrganizationName(String siteOrganizationName) {
        this.siteOrganizationName = siteOrganizationName;
    }
    /**
     * @return the localSiteIdentifier
     */
    @NotEmpty (message = "error.proprietary.submit.siteLocalId")
    public String getLocalSiteIdentifier() {
        return localSiteIdentifier;
    }
    /**
     * @param localSiteIdentifier the localSiteIdentifier to set
     */
    public void setLocalSiteIdentifier(String localSiteIdentifier) {
        this.localSiteIdentifier = localSiteIdentifier;
    }
    /**
     * @return the siteStatusDate
     */
    @NotEmpty (message = "error.submit.statusDate")
    public String getSiteStatusDate() {
        return siteStatusDate;
    }
    /**
     * @param siteStatusDate the siteStatusDate to set
     */
    public void setSiteStatusDate(String siteStatusDate) {
        this.siteStatusDate = siteStatusDate;
    }
    /**
     * @return the dateOpenedforAccrual
     */
    public String getDateOpenedforAccrual() {
        return dateOpenedforAccrual;
    }
    /**
     * @param dateOpenedforAccrual the dateOpenedforAccrual to set
     */
    public void setDateOpenedforAccrual(String dateOpenedforAccrual) {
        this.dateOpenedforAccrual = dateOpenedforAccrual;
    }
    /**
     * @return the dateClosedforAccrual
     */
    public String getDateClosedforAccrual() {
        return dateClosedforAccrual;
    }
    /**
     * @param dateClosedforAccrual the dateClosedforAccrual to set
     */
    public void setDateClosedforAccrual(String dateClosedforAccrual) {
        this.dateClosedforAccrual = dateClosedforAccrual;
    }
    /**
     * @return the siteProgramCodeTxt
     */
    public String getSiteProgramCodeText() {
        return siteProgramCodeText;
    }
    /**
     * @param siteProgramCodeTxt the siteProgramCodeTxt to set
     */
    public void setSiteProgramCodeText(String siteProgramCodeTxt) {
        this.siteProgramCodeText = siteProgramCodeTxt;
    }
    /**
     * @return the siteStatusCode
     */
    @NotEmpty (message = "error.submit.statusCode")
    public String getSiteStatusCode() {
        return siteStatusCode;
    }
    /**
     * @param siteStatusCode the siteStatusCode to set
     */
    public void setSiteStatusCode(String siteStatusCode) {
        this.siteStatusCode = siteStatusCode;
    }
    
    /**
     * Gets the participating sites.
     * @return the participatingSites
     */
    public List<SubmittedOrganizationDTO> getParticipatingSitesList() {
        return participatingSitesList;
    }

    /**
     * Sets the participating sites.
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSitesList(List<SubmittedOrganizationDTO> participatingSites) {
        this.participatingSitesList = participatingSites;
    }
    
}
