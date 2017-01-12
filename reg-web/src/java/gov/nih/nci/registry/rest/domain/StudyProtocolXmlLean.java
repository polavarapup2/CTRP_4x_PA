package gov.nih.nci.registry.rest.domain;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since Feb 11, 2013
 */
@XmlRootElement(name = "studyProtocol")
@XmlAccessorType(XmlAccessType.PROPERTY)
public final class StudyProtocolXmlLean implements Comparable<StudyProtocolXmlLean> {
    private static final Logger LOG = Logger.getLogger(StudyProtocolXmlLean.class);

    private static final Set<DocumentWorkflowStatusCode> DISPLAY_CODES =
            EnumSet.of(DocumentWorkflowStatusCode.VERIFICATION_PENDING,
                       DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                       DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);

    private String nciIdentifier;
    private String officialTitle;
    private String phaseName;
    private String leadOrganizationName;
    private String piFullName;
    private StudyStatusCode studyStatusCode;
    private String nctIdentifier;
    private DocumentWorkflowStatusCode documentWorkflowStatusCode;
    private Date documentWorkflowStatusDate;

    /**
     * Generate a list of StudyProtocolXmlLean from search result list.
     * @param searchResultDtoList the search results
     * @return the list
     */
    public static List<StudyProtocolXmlLean> getList(List<StudyProtocolQueryDTO> searchResultDtoList) {
        List<StudyProtocolXmlLean> result = new ArrayList<StudyProtocolXmlLean>();
        if (searchResultDtoList != null) {
            for (StudyProtocolQueryDTO item : searchResultDtoList) {
                if (StringUtils.isNotEmpty(item.getNciIdentifier()) 
                        && DISPLAY_CODES.contains(item.getDocumentWorkflowStatusCode())) {
                    StudyProtocolXmlLean xmlObj = new StudyProtocolXmlLean(item);
                    result.add(xmlObj);
                }
            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     * 
     */
    public StudyProtocolXmlLean() {
        // required for to prevent JAXBMarshalException: IllegalAnnotationsException
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(StudyProtocolXmlLean that) {
        return nciIdentifier.compareTo(that.nciIdentifier);
    }

    /**
     * @param searchResultDto the dto from results service
     */
    public StudyProtocolXmlLean(StudyProtocolQueryDTO searchResultDto) {
        try {
            BeanUtils.copyProperties(this, searchResultDto);
       } catch (Exception e) {
            LOG.error(e);
        }
    }

    /**
     * @return the nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     * @param nciIdentifier the nciIdentifier to set
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
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
    /**
     * @return the leadOrganizationName
     */
    public String getLeadOrganizationName() {
        return leadOrganizationName;
    }
    /**
     * @param leadOrganizationName the leadOrganizationName to set
     */
    public void setLeadOrganizationName(String leadOrganizationName) {
        this.leadOrganizationName = leadOrganizationName;
    }
    /**
     * @return the piFullName
     */
    public String getPiFullName() {
        return piFullName;
    }
    /**
     * @param piFullName the piFullName to set
     */
    public void setPiFullName(String piFullName) {
        this.piFullName = piFullName;
    }
    /**
     * @return the phaseName
     */
    public String getPhaseName() {
        return phaseName;
    }
    /**
     * @param phaseName the phaseName to set
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
    /**
     * @return the studyStatusCode
     */
    public StudyStatusCode getStudyStatusCode() {
        return studyStatusCode;
    }

    /**
     * @param studyStatusCode the studyStatusCode to set
     */
    public void setStudyStatusCode(StudyStatusCode studyStatusCode) {
        this.studyStatusCode = studyStatusCode;
    }

    /**
     * @return the nctIdentifier
     */
    public String getNctIdentifier() {
        return nctIdentifier;
    }
    /**
     * @param nctIdentifier the nctIdentifier to set
     */
    public void setNctIdentifier(String nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
    }

    /**
     * @return documentWorkflowStatusCode
     */
    public DocumentWorkflowStatusCode getDocumentWorkflowStatusCode() {
        return documentWorkflowStatusCode;
    }

    /**
     * @param documentWorkflowStatusCode documentWorkflowStatusCode
     */
    public void setDocumentWorkflowStatusCode(
            DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        this.documentWorkflowStatusCode = documentWorkflowStatusCode;
    }

    /**
     * @return DocumentWorkflowStatusDate
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getDocumentWorkflowStatusDate() {
        return documentWorkflowStatusDate;
    }

    /**
     * @param documentWorkflowStatusDate documentWorkflowStatusDate
     */
    public void setDocumentWorkflowStatusDate(Date documentWorkflowStatusDate) {
       this.documentWorkflowStatusDate = documentWorkflowStatusDate;
    }
}
