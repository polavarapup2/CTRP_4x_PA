/**
 * 
 */
package gov.nih.nci.pa.service.util.report;

import gov.nih.nci.pa.iso.dto.StudyProtocolAssociationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 *
 */
public final class TSRReportAssociatedTrial implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2526079069590848986L;
    
    /**
     * @param association StudyProtocolAssociationDTO
     */
    public TSRReportAssociatedTrial(StudyProtocolAssociationDTO association) {
        setIdentifierType(CdConverter.convertCdToString(association
                .getIdentifierType()));
        setOfficialTitle(StConverter.convertToString(association
                .getOfficialTitle()));
        setTrialIdentifier(StConverter.convertToString(association
                .getStudyIdentifier()));
        setTrialSubType(CdConverter.convertCdToString(association
                .getStudySubtypeCode()));
        setTrialType(CdConverter.convertCdToString(association
                .getStudyProtocolType()));
    }
    
    private String trialIdentifier;
    private String identifierType;
    private String trialType;
    private String trialSubType;
    private String officialTitle;
    /**
     * @return the trialIdentifier
     */
    public String getTrialIdentifier() {
        return trialIdentifier;
    }
    /**
     * @param trialIdentifier the trialIdentifier to set
     */
    public void setTrialIdentifier(String trialIdentifier) {
        this.trialIdentifier = trialIdentifier;
    }
    /**
     * @return the identifierType
     */
    public String getIdentifierType() {
        return identifierType;
    }
    /**
     * @param identifierType the identifierType to set
     */
    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }
    /**
     * @return the trialType
     */
    public String getTrialType() {
        return trialType;
    }
    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }
    /**
     * @return the trialSubType
     */
    public String getTrialSubType() {
        return trialSubType;
    }
    /**
     * @param trialSubType the trialSubType to set
     */
    public void setTrialSubType(String trialSubType) {
        this.trialSubType = trialSubType;
    }
    /**
     * @return the officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    

}
