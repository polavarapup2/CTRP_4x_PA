/**
 *
 */
package gov.nih.nci.registry.action;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.TrialUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * @author Vrushali
 *
 */
public class AmendmentTrialActionTest extends AbstractRegWebTest {
    private static final String FILE_NAME = "ProtocolDoc.doc";
    
    private final AmendmentTrialAction trialAction = new AmendmentTrialAction();
    
    @Before
    public void init() {
        trialAction.prepare();
        trialAction.setServletRequest(ServletActionContext.getRequest());
    }
    
    private void setTrialDTOInSession(TrialDTO dto) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("trialDTO", dto);
        trialAction.setInitialStatusHistory(dto.getStatusHistory());
    }

    @Test
    public void testView() throws Exception {
        trialAction.setStudyProtocolId(1L);
        trialAction.view();
    }

    @Test
    public void testViewWithIdInRequest() throws Exception {
        MockHttpServletRequest request = (MockHttpServletRequest)ServletActionContext.getRequest();
        request.setupAddParameter("studyProtocolId", "1");
        trialAction.view();
    }
    
    @Test
    public void testEdit() throws Exception {
        setTrialDTOInSession(getMockTrialDTO());
        assertEquals("edit", trialAction.edit());
    }
    
    @Test
    public void testReview() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        assertEquals("review", trialAction.review());
    }
    
    @Test
    public void testReviewMissingAccrualDiseaseCodeSystem() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        assertEquals("review", trialAction.review());

        trialAction.getTrialDTO().setAccrualDiseaseCodeSystem(null);
        assertEquals("error", trialAction.review());
        assertTrue(trialAction.getFieldErrors().containsKey("trialDTO.accrualDiseaseCodeSystem"));
    }
    
    @Test
    public void testReviewMissingProtocolDoc() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        assertEquals("error", trialAction.review());
        assertEquals("Protocol Document is required", trialAction.getFieldErrors().get("trialDTO.protocolDocFileName").get(0));
    }
    
    @Test
    public void testReviewMissingIRBDoc() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        
        assertEquals("error", trialAction.review());
        assertEquals("IRB Approval Document is required", trialAction.getFieldErrors().get("trialDTO.irbApprovalFileName").get(0));
    }
    
    @Test
    public void testReviewMissingChangeMemoAndProtocolHighlightedDoc() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        
        assertEquals("error", trialAction.review());
        assertEquals("At least one is required: Change Memo Document or Protocol Highlighted Document", trialAction
                .getFieldErrors().get("trialDTO.changeMemoDocFileName").get(0));
        assertEquals("At least one is required: Change Memo Document or Protocol Highlighted Document", trialAction
                .getFieldErrors().get("trialDTO.protocolHighlightDocumentFileName").get(0));
    }
    
    @Test
    public void testReviewMissingChangeMemoDocument() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setProtocolHighlightDocument(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setProtocolHighlightDocumentFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        assertEquals("review", trialAction.review());
    }
    
    @Test
    public void testReviewMissingProtocolHighlightDocument() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        assertEquals("review", trialAction.review());
    }
    
    @Test
    public void testReviewWithAllDoc() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        trialAction.setInformedConsentDocument(f);
        trialAction.setParticipatingSites(f);
        trialAction.setProtocolHighlightDocument(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setInformedConsentDocumentFileName(FILE_NAME);
        trialAction.setParticipatingSitesFileName(FILE_NAME);
        trialAction.setProtocolHighlightDocumentFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        assertEquals("review", trialAction.review());
    }
    
    @Test
    public void testReviewWithError() throws Exception{
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setContactEmail("dhh");
        dto.setContactPhone("");
        dto.setStartDate("startDate");
        dto.setAmendmentDate("01/20/2010");
        dto.setPrimaryCompletionDate("completionDate");
        dto.setStatusDate("statusDate");
        dto.setResponsiblePartyType("");
        dto.setPrimaryPurposeCode("Other");
        dto.setStatusCode("Administratively Complete");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setProtocolDocFileName(FILE_NAME);
        fileUrl = ClassLoader.getSystemClassLoader().getResource("test.txt");
        f = new File(fileUrl.toURI());
        trialAction.setIrbApproval(f);
        trialAction.setIrbApprovalFileName("test.txt");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    
    @Test
    public void testReviewWithAllDatesEmpty() throws Exception{
        TrialDTO dto = getMockTrialDTO();
        dto.setIdentifier("2");
        dto.setStartDate("");
        dto.setStatusDate("");
        dto.setAmendmentDate("");
        dto.setPrimaryCompletionDate("");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    
    
    
    @Test
    public void testReviewWithStatusCodeChangedToAdComplete() throws Exception{
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("01/20/2009");
        dto.setStatusCode("Administratively Complete");
        dto.setPrimaryCompletionDateType("Anticipated");
        dto.setPrimaryCompletionDate("01/20/2009");
        dto.setStartDateType("Anticipated");
        dto.setStartDate("01/20/2008");
        dto.setReason("reason");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }

    @Test
    public void testReviewWithGrants() throws Exception{
        trialAction.setTrialDTO(getMockTrialDTO());

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        //set grant in session
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        assertEquals("review", trialAction.review());
    }
    
    @Test
    public void testReviewWithIndIde() throws URISyntaxException{
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
        //set indide list in session
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        assertEquals("review", trialAction.review());

    }
    
    @Test
    public void testReviewWhenRespPartyIsSponsor() throws URISyntaxException{
        TrialDTO dto = getMockTrialDTO();
        dto.setResponsiblePartyType(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);
        dto.setResponsiblePersonIdentifier("2");
        dto.setResponsiblePersonName("responsiblePersonName");
        trialAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        assertEquals("review", trialAction.review());
    }
    
    @Test
    public void testReviewWithErrorHavingGrantsAndInd() throws Exception{
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("");
        dto.setPrimaryPurposeCode("");
        dto.setContactEmail("dhh");
        dto.setContactPhone("");
        dto.setFundingDtos(getfundingDtos());
        dto.setIndIdeDtos(getIndDtos());
        trialAction.setTrialDTO(dto);

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);

        assertEquals("error", trialAction.review());
    }
    
    @Test
    public void testCancel() {
        assertEquals("redirect_to_search", trialAction.cancel());
    }
    
    @Test
    public void testAmendWhenRespPartyIsPI(){
        setTrialDTOInSession(getMockTrialDTO());
        assertEquals("redirect_to_search", trialAction.amend());
    }
    
    @Test
    public void testAmendWhenRespPartyIsSponsor(){
        TrialDTO dto = getMockTrialDTO();
        dto.setResponsiblePartyType(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);
        dto.setResponsiblePersonIdentifier("2");
        dto.setResponsiblePersonName("responsiblePersonName");
        dto.setDocDtos(getDocumentDtos());
        setTrialDTOInSession(dto);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    
    @Test
    public void testAmendWithSummaryFour(){
        TrialDTO dto = getMockTrialDTO();
        dto.setSummaryFourFundingCategoryCode("National");
        SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId(UUID.randomUUID().toString());
        summarySp.setOrgId("2");
        summarySp.setOrgName("SummaryFourOrgName");
        dto.getSummaryFourOrgIdentifiers().add(summarySp);
        setTrialDTOInSession(dto);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    
    @Test
    public void testAmendWithGrants(){
        TrialDTO dto = getMockTrialDTO();
        dto.setFundingDtos(getfundingDtos());
        setTrialDTOInSession(dto);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    
    @Test
    public void testAmendWithIndIde(){
        TrialDTO dto = getMockTrialDTO();
        dto.setIndIdeDtos(getIndDtos());
        setTrialDTOInSession(dto);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    
    @Test
    public void testAmendWhenPhaseAndPurposeOther(){
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setPhaseAdditionalQualifier("phaseOtherText");
        dto.setPrimaryPurposeCode("Other");
        dto.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.ANCILLARY.getCode());
        setTrialDTOInSession(dto);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    
    @Test
    public void testAmendWithException(){
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setPhaseAdditionalQualifier("phaseOtherText");
        dto.setPrimaryPurposeCode("Other");
        dto.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.CORRELATIVE.getCode());
        dto.setOfficialTitle("testthrowException");
        setTrialDTOInSession(dto);
        assertEquals("error", trialAction.amend());
    }
    
    @Test
    public void testAmendWhenNoDTO(){
        assertEquals("error", trialAction.amend());
    }
    
    @Test
    public void testTrialDTOProperty(){
        assertNull(trialAction.getTrialDTO());
        trialAction.setTrialDTO(getMockTrialDTO());
        assertNotNull(trialAction.getTrialDTO());
    }
    
    @Test
    public void testTrialActionProperty(){
        trialAction.setTrialAction(null);
        assertNull(trialAction.getTrialAction());
        trialAction.setTrialAction("trialAction");
        assertNotNull(trialAction.getTrialAction());
    }
    
    @Test
    public void testStudyProtocolIdProperty(){
        assertNull(trialAction.getStudyProtocolId());
        trialAction.setStudyProtocolId(1L);
        assertNotNull(trialAction.getStudyProtocolId());
    }
    
    @Test
    public void testServletResponseProperty(){
        assertNull(trialAction.getServletResponse());
        trialAction.setServletResponse(null);
        assertNull(trialAction.getServletResponse());
    }
    
    @Test
    public void testProtocolDocProperty(){
        assertNull(trialAction.getProtocolDoc());
        trialAction.setProtocolDoc(new File(FILE_NAME));
        assertNotNull(trialAction.getProtocolDoc());
    }
    
    @Test
    public void testIRBDocProperty(){
        assertNull(trialAction.getIrbApproval());
        trialAction.setIrbApproval(new File(FILE_NAME));
        assertNotNull(trialAction.getIrbApproval());
    }
    
    @Test
    public void testInformedConsentDocProperty(){
        assertNull(trialAction.getInformedConsentDocument());
        trialAction.setInformedConsentDocument(new File(FILE_NAME));
        assertNotNull(trialAction.getInformedConsentDocument());
    }
    
    @Test
    public void testParticipatingSiteDocProperty(){
        assertNull(trialAction.getParticipatingSites());
        trialAction.setParticipatingSites(new File(FILE_NAME));
        assertNotNull(trialAction.getParticipatingSites());
    }
    
    @Test
    public void testChangeMemoDocProperty(){
        assertNull(trialAction.getChangeMemoDoc());
        trialAction.setChangeMemoDoc(new File(FILE_NAME));
        assertNotNull(trialAction.getChangeMemoDoc());
    }
    
    @Test
    public void testProtocolHighlightDocProperty(){
        assertNull(trialAction.getProtocolHighlightDocument());
        trialAction.setProtocolHighlightDocument(new File(FILE_NAME));
        assertNotNull(trialAction.getProtocolHighlightDocument());
    }

    @Test
    public void testProtocolFileNameProperty(){
        assertNull(trialAction.getProtocolDocFileName());
        trialAction.setProtocolDocFileName("protocolDocFileName");
        assertNotNull(trialAction.getProtocolDocFileName());
    }
    
    @Test
    public void testIRBFileNameProperty(){
        assertNull(trialAction.getIrbApprovalFileName());
        trialAction.setIrbApprovalFileName("irbApprovalFileName");
        assertNotNull(trialAction.getIrbApprovalFileName());
    }
    
    @Test
    public void testInformedConsentFileNameProperty(){
        assertNull(trialAction.getInformedConsentDocumentFileName());
        trialAction.setInformedConsentDocumentFileName("informedConsentDocumentFileName");
        assertNotNull(trialAction.getInformedConsentDocumentFileName());
    }
    
    @Test
    public void testParticipatingSiteFileNameProperty(){
        assertNull(trialAction.getParticipatingSitesFileName());
        trialAction.setParticipatingSitesFileName("participatingSitesFileName");
        assertNotNull(trialAction.getParticipatingSitesFileName());
    }
    
    @Test
    public void testChangeMemoFileNameProperty(){
        assertNull(trialAction.getChangeMemoDocFileName());
        trialAction.setChangeMemoDocFileName("changeMemoDocFileName");
        assertNotNull(trialAction.getChangeMemoDocFileName());
    }
    
    @Test
    public void testProtocolHighlightFileNameProperty(){
        assertNull(trialAction.getProtocolHighlightDocumentFileName());
        trialAction.setProtocolHighlightDocumentFileName("protocolHighlight");
        assertNotNull(trialAction.getProtocolHighlightDocumentFileName());
    }
    
    @Test
    public void testValidateTrialDates(){
        TrialDTO dto = getMockTrialDTO();
        trialAction.setTrialDTO(dto);
        trialAction.setPageFrom("amendTrial");
        assertEquals("error", trialAction.review());
    }

    @Test
    public void testTrialDatesEmpty(){
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("");
        dto.setStatusDate("");
        dto.setPrimaryCompletionDate("");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    
    @Test
    public void testValidateTrialDatesRule18Pass() {
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("02/22/2009");
        trialAction.setTrialDTO(dto);
        assertEquals("review", trialAction.review());
    }
    
    @Test
    public void testValidateTrialDatesRule18Fail(){
        TrialDTO dto = getMockTrialDTO();
        dto.getStatusHistory().iterator().next().setStatusDate(getTomorrowAsDate());
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
        assertTrue(trialAction.getFieldErrors().containsKey("trialDTO.statusDate"));
    }
    
    @Test
    public void testInValidStatusTransition () {
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Approved");
        dto.setPrimaryCompletionDateType("Actual");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
        assertNotNull(trialAction.getActionErrors());
    }
    
    @Test
    public void testStatusToAdComplete() throws URISyntaxException {
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Administratively Complete");
        dto.setReason("reason");
        dto.setPrimaryCompletionDateType("Actual");
        dto.setPrimaryCompletionDate("01/10/2008");
        trialAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);

        assertEquals("error", trialAction.review());
        assertNotNull(trialAction.getActionErrors());
    }
    
   
    
    @Test
    public void testAmdendFutureDate() throws URISyntaxException {
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);
        TrialDTO dto = getMockTrialDTO();
        dto.setAmendmentDate(nextDate);
        trialAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);

        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        assertEquals("error", trialAction.review());
    }
    
    @Test
    public void testReviewWithDSPChange() throws Exception {
        TrialDTO dto = getMockTrialDTO();
        dto.setFdaRegulatoryInformationIndicator("Yes");
        dto.setSection801Indicator("Yes");
        dto.setDelayedPostingIndicator("Yes");
        trialAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        trialAction.setInformedConsentDocument(f);
        trialAction.setParticipatingSites(f);
        trialAction.setProtocolHighlightDocument(f);
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setInformedConsentDocumentFileName(FILE_NAME);
        trialAction.setParticipatingSitesFileName(FILE_NAME);
        trialAction.setProtocolHighlightDocumentFileName(FILE_NAME);
        trialAction.setPageFrom("amendTrial");
       
        HttpSession session = ServletActionContext.getRequest().getSession();
        assertEquals("review", trialAction.review());
        TrialDTO resultDto = (TrialDTO) session.getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE);
        assertEquals(null, resultDto.getDelayedPostingIndicator());
    }

}
