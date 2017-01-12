package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;

/**
 * @author Hugh Reinhart
 * @since Dec 14, 2012
 */
public class AccrualDiseaseDTO extends BaseDTO {
    private static final long serialVersionUID = 4886400360482462676L;

    private Ii diseaseCode;
    private St preferredName;
    private St displayName;

    /**
     * @return the diseaseCode
     */
    public Ii getDiseaseCode() {
        return diseaseCode;
    }
    /**
     * @param diseaseCode the diseaseCode to set
     */
    public void setDiseaseCode(Ii diseaseCode) {
        this.diseaseCode = diseaseCode;
    }
    /**
     * @return the preferredName
     */
    public St getPreferredName() {
        return preferredName;
    }
    /**
     * @param preferredName the preferredName to set
     */
    public void setPreferredName(St preferredName) {
        this.preferredName = preferredName;
    }
    /**
     * @return the displayName
     */
    public St getDisplayName() {
        return displayName;
    }
    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(St displayName) {
        this.displayName = displayName;
    }
}
