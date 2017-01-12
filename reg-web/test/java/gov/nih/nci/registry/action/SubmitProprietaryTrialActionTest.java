/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.lov.ConsortiaTrialCategoryCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class SubmitProprietaryTrialActionTest extends AbstractRegWebTest {
    private static final String FILE_NAME = "ProtocolDoc.doc";
    private final SubmitProprietaryTrialAction action = new SubmitProprietaryTrialAction();
    
    private CTGovSyncServiceLocal ctGovSyncService = mock(CTGovSyncServiceLocal.class);
    private StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    
    @Override
    @Before
    public void setUpServices() {        
        super.setUpServices();
        action.setCtGovSyncService(ctGovSyncService);
        action.setStudyProtocolService(studyProtocolService);
    }
    
    @Test
    public void testProperty() {
        assertNull(action.getSum4FundingCatCode());
        action.setSum4FundingCatCode("sum4FundingCatCode");
        assertNotNull(action.getSum4FundingCatCode());
    }
    @Test
    public void testPropertyTrialDTO() {
        assertNull(action.getTrialDTO());
        action.setTrialDTO(new ProprietaryTrialDTO());
        assertNotNull(action.getTrialDTO());

    }
    @Test
    public void testPropertyProtocolDoc() {
        assertNull(action.getProtocolDoc());
        action.setProtocolDoc(new File(FILE_NAME));
        assertNotNull(action.getProtocolDoc());

    }
    @Test
    public void testProtocolFileNameProperty(){
        assertNull(action.getProtocolDocFileName());
        action.setProtocolDocFileName("protocolDocFileName");
        assertNotNull(action.getProtocolDocFileName());
    }
    @Test
    public void testOtherDocProperty(){
        assertTrue(action.getOtherDocument().length==0);
        action.setOtherDocument(new File[] {new File(FILE_NAME)});
        assertEquals(new File(FILE_NAME), action.getOtherDocument()[0]);
    }
    @Test
    public void testOtherFileNameProperty(){
        assertTrue(action.getOtherDocumentFileName().length==0);
        action.setOtherDocumentFileName(new String[] {"otherDocFileName"});
        assertEquals("otherDocFileName", action.getOtherDocumentFileName()[0]);
    }
    @Test
    public void testTrialActionProperty(){
       assertNotNull(action.getTrialAction());
       action.setTrialAction(null);
       assertNull(action.getTrialAction());
    }
    @Test
    public void testServletResponseProperty(){
        assertNull(action.getServletResponse());
        action.setServletResponse(null);
        assertNull(action.getServletResponse());
    }
    @Test
    public void testSelectedTrialTypeProperty() {
        assertNotNull(action.getSelectedTrialType());
        action.setSelectedTrialType(null);
        assertNull(action.getSelectedTrialType());
    }
    @Test
    public void testSelectTypeOfTrial() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success", action.selectTypeOfTrial());
    }
    @Test
    public void testExcute(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search",action.execute());
        action.setSum4FundingCatCode("sum4FundingCatCode");
        assertEquals("success", action.execute());
    }
    @Test
    public void testEdit() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        assertEquals("edit", action.edit());
    }
    @Test
    public void testCancle() {
        assertEquals("redirect_to_search", action.cancel());
    }
    @Test
    public void testReview() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setOtherDocument(new File[] {f});
        action.setProtocolDocFileName(FILE_NAME);
        action.setOtherDocumentFileName(new String[] {FILE_NAME});
        assertEquals("review", action.review());
    }
    @Test
    public void testReviewFileNotFound() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        File f = new File(FILE_NAME);
        action.setProtocolDoc(f);
        action.setOtherDocument(new File[] {f});
        action.setProtocolDocFileName(FILE_NAME);
        action.setOtherDocumentFileName(new String[] {FILE_NAME});
        assertEquals("error", action.review());
    }
    @Test
    public void testReviewNoDoc() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        assertEquals("review", action.review());
    }
    @Test
    public void testReviewMissingPhasePurpose() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setPhaseCode(null);
        action.getTrialDTO().setPrimaryPurposeCode(null);
        assertEquals("review", action.review());
    }
    @Test
    public void testReviewSiteRecStatus() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Active");
        assertEquals("error", action.review());
        assertTrue(action.getActionErrors().contains("Date Opened for Accrual must be a valid date for Active. "));
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Active");
        action.getTrialDTO().setDateOpenedforAccrual("12/09/2009");
        assertEquals("review", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Administratively Complete");
        action.getTrialDTO().setDateOpenedforAccrual("11/09/2009");
        action.getTrialDTO().setDateClosedforAccrual("12/09/2009");
        assertEquals("review", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Administratively Complete");
        action.getTrialDTO().setDateOpenedforAccrual("11/09/2009");
        action.getTrialDTO().setDateClosedforAccrual("10/09/2009");
        assertEquals("error", action.review());
        assertTrue(action.getActionErrors().contains("Date Closed for Accrual must be same or bigger  than Date Opened for Accrual. "));
    }
    
    @Test
    public void testReviewInvalidDocAndMissingFiled() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setLocalSiteIdentifier(null);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setOtherDocument(new File[] {f});
        action.setOtherDocumentFileName(new String[] {"filename.zip"});
        assertEquals("error", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setNctIdentifier(null);
        action.getTrialDTO().setPhaseCode(null);
        assertEquals("error", action.review());

    }
    @Test
    public void testReviewLongLeadOrgTrialIdentifier() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setLeadOrgTrialIdentifier("LeadOrganizationTrialIdentifer12");  
        assertEquals("error", action.review());

    }
    @Test
    public void testReviewShortLeadOrgTrialIdentifier() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setLeadOrgTrialIdentifier("LeadOrganizationTrialIdentifer");  
        assertEquals("review", action.review());

    }
    @Test
    public void testCreateWithNoDTO() {
        assertEquals("error", action.create());
    }
    @Test
    public void testCreate() {
        HttpSession sess = new MockHttpSession();
        ProprietaryTrialDTO tDto = getMockProprietaryTrialDTO();
        tDto.setDocDtos(getDocumentDtos());
        sess.setAttribute("trialDTO", tDto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertNull(tDto.getStudySource());
        assertEquals("review", action.create());
        assertEquals(StudySourceCode.REGISTRY, tDto.getStudySource());

        tDto.setSummaryFourFundingCategoryCode("summaryFourFundingCategoryCode");
        SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId(UUID.randomUUID().toString());
        summarySp.setOrgId("2");
        summarySp.setOrgName("summaryFourOrgName");
        tDto.getSummaryFourOrgIdentifiers().add(summarySp);
        tDto.setOfficialTitle("testthrowException");
        sess.setAttribute("trialDTO", tDto);
        request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("error", action.create());
    }
    
    @Test
    public void testSumary4Sponsor() {
        SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId(UUID.randomUUID().toString());
        summarySp.setOrgId("2");
        summarySp.setOrgName("summaryFourOrgName");
        
        SummaryFourSponsorsWebDTO sum4 = new SummaryFourSponsorsWebDTO();
        assertFalse(sum4.hashCode() == summarySp.hashCode());
        sum4.setOrgId("2");
        assertEquals(sum4.hashCode(), summarySp.hashCode());
        assertTrue(summarySp.equals(sum4));
        assertEquals("summaryFourOrgName", summarySp.getOrgName());
        assertFalse(summarySp.getOrgName().equals(sum4.getOrgName()));
    }
    
    @Test
    public void searchByNct() throws PAException {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);

        when(ctGovSyncService.getAdaptedCtGovStudyByNctId("NCT01861054"))
                .thenReturn(new CTGovStudyAdapter(null));
        when(studyProtocolService.getStudyProtocolsByNctId("NCT01861054"))
                .thenReturn(new ArrayList<StudyProtocolDTO>());

        action.setNctID("NCT01861054");
        action.clearActionErrors();

        assertEquals("redirect_to_nct_import", action.searchByNct());
    }
    
    @Test
    public void searchByNctInvalidNCT() throws PAException {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);

        action.setNctID("  ");
        action.clearActionErrors();
        assertEquals("input_nct", action.searchByNct());
        assertTrue(action.getActionErrors().contains(
                "Please provide an ClinicalTrials.gov Identifier value."));
        
        action.setNctID("NCT1111111%");
        action.clearActionErrors();
        assertEquals("input_nct", action.searchByNct());
        assertTrue(action.getActionErrors().contains(
                "Provided ClinicalTrials.gov Identifer is invalid."));
    }
    
    @Test
    public void searchByNctNotExistInCtGov() throws PAException {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);

        when(ctGovSyncService.getAdaptedCtGovStudyByNctId("NCT01861054"))
                .thenReturn(null);       
        action.setNctID("NCT01861054");
        action.clearActionErrors();

        assertEquals("input_nct", action.searchByNct());
        assertTrue(action.getActionErrors().contains(
                "A study with the given identifier is not found in ClinicalTrials.gov."));
    }
    
    @Test
    public void searchByNctAlreadyInCTRP() throws PAException {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);

        when(ctGovSyncService.getAdaptedCtGovStudyByNctId("NCT01861054"))
                .thenReturn(new CTGovStudyAdapter(null));
        when(studyProtocolService.getStudyProtocolsByNctId("NCT01861054"))
                .thenReturn(Arrays.asList(new StudyProtocolDTO()));
        action.setNctID("NCT01861054");
        action.clearActionErrors();

        assertEquals("input_nct", action.searchByNct());
        assertTrue(action
                .getActionErrors()
                .contains(
                        "A study with the given identifier already exists in CTRP."
                        + " To find this trial in CTRP, go to the Search Trials page."));
    }
    
    @Test
    public void checkSumary4() {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        ProprietaryTrialDTO trial = getMockProprietaryTrialDTO();
        trial.setSummaryFourFundingCategoryCode(null);
        action.setTrialDTO(trial);
        
        action.checkSummary4Funding();
        Map<String, List<String>> errs = action.getFieldErrors();
        
        assertTrue(errs.containsKey("trialDTO.summaryFourFundingCategoryCode"));
        
        trial = getMockProprietaryTrialDTO();
        trial.setSummaryFourOrgIdentifiers(null);
        action.setTrialDTO(trial);
        
        action.checkSummary4Funding();
        errs = action.getFieldErrors();
        
        assertTrue(errs.containsKey("summary4FundingSponsor"));
        
    }
    
}
