/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class TrialBatchDataValidatorTest extends AbstractHibernateTestCase {
    private final TrialBatchDataValidator validator = new TrialBatchDataValidator();
    private StudyProtocolBatchDTO  dto = new StudyProtocolBatchDTO ();
    
 
    
    @Test
    public void testValidateBatchForObservational() {
        dto = new StudyProtocolBatchDTO ();
        assertNotNull(validator.validateBatchDTO(dto));
        dto.setTrialType("Observational");
        assertEquals("Observational Trial not supported.\n",validator.validateBatchDTO(dto));
    }
    @Test
    public void testValidateBatchDTOForOrginalSubmission() {
        //without summ4
        dto = getBatchDto();

        assertEquals("" ,validator.validateBatchDTO(dto));
        //withSumm4
        dto = getBatchDto();
        getBatchSumm4Info(dto);
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testValidateBatchDTOInvalidOversight() {
        // missing country
        dto = getBatchDto();
        dto.setOversightAuthorityCountry("");
        assertEquals("Oversight Authority Country is required. \n", validator.validateBatchDTO(dto));
        // missing org
        dto = getBatchDto();
        dto.setOversightOrgName(" ");
        assertEquals("Oversight Authority Organization Name is required. \n", validator.validateBatchDTO(dto));
        // invalid country
        dto = getBatchDto();
        dto.setOversightAuthorityCountry("xyzzy");
        assertEquals("Oversight Authority Country is invalid. \n", validator.validateBatchDTO(dto));
        // invalid org
        dto = getBatchDto();
        dto.setOversightOrgName("xyzzy");
        assertEquals("Oversight Authority Organization is invalid. \n", validator.validateBatchDTO(dto));
    }

    @Test
    public void testValidateBatchDTOForOptionalPrimaryCompletionDate() {
        dto = getBatchDto();
        dto.setPrimaryCompletionDateType(null);
        dto.setPrimaryCompletionDate(null);
        assertNotNull(validator.validateBatchDTO(dto));
        
        dto.setTrialType("NonInterventional");
        dto.setCtGovXmlIndicator(false);
        assertEquals("" ,validator.validateBatchDTO(dto));
    }
    
    @Test
    public void testValidateBatchDTOForOrginalSubmissionWithGrants() {
         //with grants
        dto = getBatchDto();
        getBatchGrants(dto);
        assertNotNull(validator.validateBatchDTO(dto));
        dto = getBatchDto();
        getBatchMultipleGrants(dto);
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testValidateBatchDTOForOrginalSubmissionWithIndIde() {
        dto = getBatchDto();
        getBatchIndIde(dto);
        assertNotNull(validator.validateBatchDTO(dto));
        //with mutiple inds
        dto = getBatchDto();
        getBatchMultipleIndIde(dto);
        assertNotNull(validator.validateBatchDTO(dto));

    }
    @Test
    public void testValidateBatchDTOForUpdateSubmission() {
        StudyProtocolBatchDTO  dto = new StudyProtocolBatchDTO ();
        //update
        dto = getBatchDto();
        dto.setSubmissionType("U");
        dto.setNciTrialIdentifier("NCI-2009-00001");
        dto.setSponsorContactType("Generic");
        dto.setResponsibleGenericContactName("responsibleGenericContactName");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testAmendment() {
        //test amendment
        dto = getBatchDto();
        dto.setNciTrialIdentifier("NCI-2009-00001");
        dto.setSubmissionType("A");
        dto.setAmendmentDate("12/22/2009");
        dto.setAmendmentNumber("amendmentNumber");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @ Test
    public void testInvalidDoctype() {
        //test all err like invalid doc type
        dto = getBatchDto();
        dto.setProtcolDocumentFileName("protcolDocumentFileName");
        dto.setIrbApprovalDocumentFileName("irbApprovalDocumentFileName");
        dto.setParticipatinSiteDocumentFileName("participatinSiteDocumentFileName");
        dto.setInformedConsentDocumentFileName("informedConsentDocumentFileName");
        dto.setOtherTrialRelDocumentFileName("otherTrialRelDocumentFileName");
        dto.setProtocolHighlightDocFileName("protocolHighlightDocFileName");
        dto.setChangeRequestDocFileName("changeRequestDocFileName");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testInvalidDatesAndInvalidEmailAndState(){
        dto = getBatchDto();
        dto.setPrimaryCompletionDate("primaryCompletionDate");
        dto.setStudyStartDate("studyStartDate");
        dto.setCurrentTrialStatusDate("currentTrialStatusDate");
        dto.setCurrentTrialStatus("currentTrialStatus");
        dto.setTrialType("trialType");
        dto.setLeadOrgState("");
        dto.setSponsorState("State");
        dto.setPiState("State");
        dto.setSponsorContactEmail("sponsorContactEmail");
        dto.setLeadOrgEmail("leadOrgEmail");
        dto.setPiEmail("piEmail");
        dto.setSponsorEmail("sponsorEmail");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testMissingFeilds() {
        dto = getBatchDto();
        dto.setResponsibleParty("responsibleParty");
        dto.setSponsorContactType("");
        dto.setLocalProtocolIdentifier("");
        dto.setProtcolDocumentFileName("");
        dto.setIrbApprovalDocumentFileName("");
        dto.setPrimaryCompletionDateType("primaryCompletionDateType");
        dto.setStudyStartDateType("studyStartDateType");
        dto.setPhase("Other");
        dto.setPhaseAdditionalQualifierCode("");
        dto.setPrimaryPurpose("Other");
        dto.setPrimaryPurposeAdditionalQualifierCode("");
        dto.setTitle("");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testMissingGrantFeilds() {
        dto = getBatchDto();
        dto.setResponsibleParty("");
        dto.setPrimaryPurpose("primaryPurpose");
        getBatchMultipleGrants(dto);
        dto.setNihGrantInstituteCode("AD");
        assertNotNull(validator.validateBatchDTO(dto));
        dto = getBatchDto();
        getBatchMultipleGrants(dto);
        dto.setNihGrantInstituteCode("AD;");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testMissingIndFeilds() {
        dto = getBatchDto();
        getBatchMultipleIndIde(dto);
        dto.setIndGrantor("Grantor1");
        assertNotNull(validator.validateBatchDTO(dto));
        dto = getBatchDto();
        getBatchMultipleIndIde(dto);
        dto.setIndNCIDivision(null);
        dto.setIndNIHInstitution(";value");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testMissingAmendmentFeilds(){
        dto = getBatchDto();
        dto.setSubmissionType("A");
        dto.setChangeRequestDocFileName("");
        dto.setCurrentTrialStatus("In Review");
        assertNotNull(validator.validateBatchDTO(dto));
    }
    @Test
    public void testMissingUpdateFeilds(){
        dto = getBatchDto();
        dto.setSubmissionType("U");
        dto.setCurrentTrialStatus("currentTrialStatus");
        dto.setSponsorContactType("Generic");
        dto.setSponsorContactEmail("");
        assertNotNull(validator.validateBatchDTO(dto));
        dto = getBatchDto();
        dto.setSubmissionType("U");
        dto.setCurrentTrialStatus("currentTrialStatus");
        dto.setSponsorContactType("Generic");
        dto.setSponsorContactEmail("sponsorContactEmail");
        assertNotNull(validator.validateBatchDTO(dto));

    }
    @Test
    public void testValidateOversightInfo() {
        dto = getBatchDto();
        dto.setFdaRegulatoryInformationIndicator("");
        assertNotNull(validator.validateBatchDTO(dto));
        dto.setFdaRegulatoryInformationIndicator("No");
        assertNotNull(validator.validateBatchDTO(dto));
        dto.setFdaRegulatoryInformationIndicator("Yes");
        dto.setSection801Indicator("");
        assertNotNull(validator.validateBatchDTO(dto));
        dto.setSection801Indicator("yes");
        dto.setDelayedPostingIndicator("");
        assertNotNull(validator.validateBatchDTO(dto));
    }

}
