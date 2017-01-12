/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;

/**
 * @author Denis G. Krylov
 * 
 */
public class StudyProtocolAssociationDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5910678594987854479L;

    private St studyIdentifier;

    private Cd identifierType;

    private Ii studyProtocolA;
    private Ii studyProtocolB;

    private Cd studyProtocolType;
    private Cd studySubtypeCode;
    private St officialTitle;

    /**
     * @return the studyIdentifier
     */
    public St getStudyIdentifier() {
        return studyIdentifier;
    }

    /**
     * @param studyIdentifier
     *            the studyIdentifier to set
     */
    public void setStudyIdentifier(St studyIdentifier) {
        this.studyIdentifier = studyIdentifier;
    }

    /**
     * @return the identifierType
     */
    public Cd getIdentifierType() {
        return identifierType;
    }

    /**
     * @param identifierType
     *            the identifierType to set
     */
    public void setIdentifierType(Cd identifierType) {
        this.identifierType = identifierType;
    }

    /**
     * @return the studyProtocolA
     */
    public Ii getStudyProtocolA() {
        return studyProtocolA;
    }

    /**
     * @param studyProtocolA
     *            the studyProtocolA to set
     */
    public void setStudyProtocolA(Ii studyProtocolA) {
        this.studyProtocolA = studyProtocolA;
    }

    /**
     * @return the studyProtocolB
     */
    public Ii getStudyProtocolB() {
        return studyProtocolB;
    }

    /**
     * @param studyProtocolB
     *            the studyProtocolB to set
     */
    public void setStudyProtocolB(Ii studyProtocolB) {
        this.studyProtocolB = studyProtocolB;
    }

    /**
     * @return the studyProtocolType
     */
    public Cd getStudyProtocolType() {
        return studyProtocolType;
    }

    /**
     * @param studyProtocolType
     *            the studyProtocolType to set
     */
    public void setStudyProtocolType(Cd studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }

    /**
     * @return the studySubtypeCode
     */
    public Cd getStudySubtypeCode() {
        return studySubtypeCode;
    }

    /**
     * @param studySubtypeCode
     *            the studySubtypeCode to set
     */
    public void setStudySubtypeCode(Cd studySubtypeCode) {
        this.studySubtypeCode = studySubtypeCode;
    }

    /**
     * @return the officialTitle
     */
    public St getOfficialTitle() {
        return officialTitle;
    }

    /**
     * @param officialTitle
     *            the officialTitle to set
     */
    public void setOfficialTitle(St officialTitle) {
        this.officialTitle = officialTitle;
    }

}
