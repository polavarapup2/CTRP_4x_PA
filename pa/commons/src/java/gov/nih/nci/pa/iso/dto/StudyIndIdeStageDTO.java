/**
 *
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;

/**
 * @author Vrushali
 *
 */
public class StudyIndIdeStageDTO extends BaseDTO {

    /**
     *
     */
    private static final long serialVersionUID = -1676965808746172635L;
    private Cd expandedAccessStatusCode;
    private Bl expandedAccessIndicator;
    private Cd grantorCode;
    private Cd holderTypeCode;
    private Cd nihInstHolderCode;
    private Cd nciDivProgHolderCode;
    private St indldeNumber;
    private Cd indldeTypeCode;
    private Ii studyProtocolStageIi;
    private Bl exemptIndicator;
    /**
     * @return the expandedAccessStatusCode
     */
    public Cd getExpandedAccessStatusCode() {
        return expandedAccessStatusCode;
    }
    /**
     * @param expandedAccessStatusCode the expandedAccessStatusCode to set
     */
    public void setExpandedAccessStatusCode(Cd expandedAccessStatusCode) {
        this.expandedAccessStatusCode = expandedAccessStatusCode;
    }
    /**
     * @return the expandedAccessIndicator
     */
    public Bl getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }
    /**
     * @param expandedAccessIndicator the expandedAccessIndicator to set
     */
    public void setExpandedAccessIndicator(Bl expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }
    /**
     * @return the grantorCode
     */
    public Cd getGrantorCode() {
        return grantorCode;
    }
    /**
     * @param grantorCode the grantorCode to set
     */
    public void setGrantorCode(Cd grantorCode) {
        this.grantorCode = grantorCode;
    }
    /**
     * @return the holderTypeCode
     */
    public Cd getHolderTypeCode() {
        return holderTypeCode;
    }
    /**
     * @param holderTypeCode the holderTypeCode to set
     */
    public void setHolderTypeCode(Cd holderTypeCode) {
        this.holderTypeCode = holderTypeCode;
    }
    /**
     * @return the nihInstHolderCode
     */
    public Cd getNihInstHolderCode() {
        return nihInstHolderCode;
    }
    /**
     * @param nihInstHolderCode the nihInstHolderCode to set
     */
    public void setNihInstHolderCode(Cd nihInstHolderCode) {
        this.nihInstHolderCode = nihInstHolderCode;
    }
    /**
     * @return the nciDivProgHolderCode
     */
    public Cd getNciDivProgHolderCode() {
        return nciDivProgHolderCode;
    }
    /**
     * @param nciDivProgHolderCode the nciDivProgHolderCode to set
     */
    public void setNciDivProgHolderCode(Cd nciDivProgHolderCode) {
        this.nciDivProgHolderCode = nciDivProgHolderCode;
    }
    /**
     * @return the indldeNumber
     */
    public St getIndldeNumber() {
        return indldeNumber;
    }
    /**
     * @param indldeNumber the indldeNumber to set
     */
    public void setIndldeNumber(St indldeNumber) {
        this.indldeNumber = indldeNumber;
    }
    /**
     * @return the indldeTypeCode
     */
    public Cd getIndldeTypeCode() {
        return indldeTypeCode;
    }
    /**
     * @param indldeTypeCode the indldeTypeCode to set
     */
    public void setIndldeTypeCode(Cd indldeTypeCode) {
        this.indldeTypeCode = indldeTypeCode;
    }
    /**
     * @return the studyProtocolStageIi
     */
    public Ii getStudyProtocolStageIi() {
        return studyProtocolStageIi;
    }
    /**
     * @param studyProtocolStageIi the studyProtocolStageIi to set
     */
    public void setStudyProtocolStageIi(Ii studyProtocolStageIi) {
        this.studyProtocolStageIi = studyProtocolStageIi;
    }
    /**
     * @param exemptIndicator the exemptIndicator to set
     */
    public void setExemptIndicator(Bl exemptIndicator) {
        this.exemptIndicator = exemptIndicator;
    }
    /**
     * @return the exemptIndicator
     */
    public Bl getExemptIndicator() {
        return exemptIndicator;
    }


}
