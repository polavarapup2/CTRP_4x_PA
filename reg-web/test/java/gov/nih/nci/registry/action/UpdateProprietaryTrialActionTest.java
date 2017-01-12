/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Kalpana Guthikonda
 *
 */
public class UpdateProprietaryTrialActionTest extends AbstractRegWebTest {
    private UpdateProprietaryTrialAction action = new UpdateProprietaryTrialAction();
    
    /**
     * Initialization method.
     */
    @Before
    public void init() {
        action.prepare();
    }
    
    @Test
    public void testServletResponseProperty(){
        assertNull(action.getServletResponse());
        action.setServletResponse(null);
        assertNull(action.getServletResponse());
    }
    @Test
    public void testTrialActionProperty(){
       assertNull(action.getTrialAction());
       action.setTrialAction("trialAction");
       assertNotNull(action.getTrialAction());
    }
    @Test
    public void testTrialDTOProperty() {
        assertNull(action.getTrialDTO());
        action.setTrialDTO(new ProprietaryTrialDTO());
        assertNotNull(action.getTrialDTO());
    }
    @Test 
    public void testCancel() {
        assertEquals("redirect_to_search", action.cancel());
    }
    @Test
    public void testEdit() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        ProprietaryTrialDTO tDto = getMockProprietaryTrialDTO();
        List<SubmittedOrganizationDTO> paOrgList = new ArrayList<SubmittedOrganizationDTO>();
        SubmittedOrganizationDTO paOrgDto = new SubmittedOrganizationDTO();
        paOrgDto.setName("name");
        paOrgList.add(paOrgDto);
        tDto.setParticipatingSitesList(paOrgList);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("error", action.edit());
    }
    @SuppressWarnings({ "deprecation", "rawtypes" })
    @Test
    public void testView() throws NullifiedRoleException, PAException {

        MockHttpServletRequest request = (MockHttpServletRequest) ServletActionContext.getRequest();
        request.clearParameters();
        request.setupAddParameter("studyProtocolId", "1");
        
        addTrialUtilMock();
        action.view();
    }
    /**
     * @throws PAException
     * @throws NullifiedRoleException
     */
    private void addTrialUtilMock() throws PAException, NullifiedRoleException {
        TrialUtil mock = mock(TrialUtil.class);
        stubVoid(mock)
                .toAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation)
                            throws Throwable {
                        ProprietaryTrialDTO dto = (ProprietaryTrialDTO) invocation.getArguments()[1];
                        PropertyUtils.copyProperties(dto, getMockProprietaryTrialDTO());                       
                        return null;
                    }
                })
                .on()
                .getProprietaryTrialDTOFromDb(any(Ii.class),
                        any(ProprietaryTrialDTO.class));
        action.setUtil(mock);
        
        PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
        action.setPaServiceUtils(paServiceUtils);
    }
    @Test
    public void testReviewUpdate() throws URISyntaxException, NullifiedRoleException, PAException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setNctIdentifier(null);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("error", action.review());
        
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setNctIdentifier(null);
        action.getTrialDTO().setPhaseCode(null);
        action.getTrialDTO().setPrimaryPurposeCode(null);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("ProtocolDoc.doc");
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setOtherDocument(new File[] {f});
        action.setProtocolDocFileName("ProtocolDoc.doc");
        action.setOtherDocumentFileName(new String[] {"ProtocolDoc.doc"});
        request.setSession(session);
        ServletActionContext.setRequest(request);
        session.setAttribute(Constants.PARTICIPATING_SITES_LIST, action.getTrialDTO().getParticipatingSitesList());
        addTrialUtilMock();
        assertEquals("review", action.review());
    }
    @Test 
    public void testReviewWithParticipatingSite() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setTrialDTO(getMockProprietaryTrialDTO());
        List<SubmittedOrganizationDTO> paOrgList = new ArrayList<SubmittedOrganizationDTO>();
        SubmittedOrganizationDTO paOrgDto = new SubmittedOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgList.add(paOrgDto);
        paOrgDto = new SubmittedOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus(null);
        Date now = new Date();  
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");  
        String currentDate = df.format(now);
        paOrgDto.setRecruitmentStatusDate(currentDate);
        paOrgDto.setSiteLocalTrialIdentifier("Test");
        paOrgDto.setDateOpenedforAccrual(currentDate);
        paOrgDto.setDateClosedforAccrual(currentDate);
        paOrgList.add(paOrgDto);
        action.getTrialDTO().setParticipatingSitesList(paOrgList);
        assertEquals("error", action.review());
        
        paOrgList = new ArrayList<SubmittedOrganizationDTO>();
        paOrgDto = new SubmittedOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("Recruiting");
        paOrgDto.setRecruitmentStatusDate(null);
        paOrgDto.setSiteLocalTrialIdentifier(null);
        paOrgDto.setDateOpenedforAccrual(null);
        paOrgDto.setDateClosedforAccrual(null);
        paOrgList.add(paOrgDto);
        action.getTrialDTO().setParticipatingSitesList(paOrgList);
        assertEquals("error", action.review());
    }
    @Test
    public void testUpdate() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        ProprietaryTrialDTO tDto = getMockProprietaryTrialDTO();
        tDto.setIdentifier("1");
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        tDto = getMockProprietaryTrialDTO();
        tDto.setIdentifier("1");
        tDto.setDocDtos(getDocumentDtos());
        List<SubmittedOrganizationDTO> paOrgList = new ArrayList<SubmittedOrganizationDTO>();
        SubmittedOrganizationDTO paOrgDto = new SubmittedOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("Recruiting");
        Date now = new Date();  
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");  
        String currentDate = df.format(now);
        paOrgDto.setRecruitmentStatusDate(currentDate);
        paOrgDto.setSiteLocalTrialIdentifier("Test");
        paOrgDto.setDateOpenedforAccrual(currentDate);
        paOrgDto.setDateClosedforAccrual(currentDate);
        paOrgList.add(paOrgDto);
        tDto.setParticipatingSitesList(paOrgList);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
    }
}
