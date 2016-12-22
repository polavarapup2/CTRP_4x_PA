/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.service.MockCorrelationUtils;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;

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
public class CollaboratorsActionTest extends AbstractPaActionTest {
    private CollaboratorsAction action;
    private OrganizationCorrelationServiceRemote ocService;
    private StudySiteServiceLocal sPartService;
    @Before
    public void prepare() throws Exception {
        action = new CollaboratorsAction();
        action.prepare();
        action.setCorrelationUtils(new MockCorrelationUtils());
        ocService = mock(OrganizationCorrelationServiceRemote.class);
        sPartService = mock(StudySiteServiceLocal.class);
        StudySiteDTO sp = new StudySiteDTO();
        when(ocService.createResearchOrganizationCorrelations(any(String.class))).thenReturn(1L);
        when(sPartService.create(any(StudySiteDTO.class))).thenReturn(sp);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
     }
    @Test
    public void testCountryRegDTOProperty(){
        assertNull(action.getCountryRegDTO());
        action.setCountryRegDTO(new ArrayList<CountryRegAuthorityDTO>());
        assertNotNull(action.getCountryRegDTO());
    }
    @Test
    public void testOrganizationListProperty(){
        assertNull(action.getOrganizationList());
        action.setOrganizationList(new ArrayList<PaOrganizationDTO>());
        assertNotNull(action.getOrganizationList());
    }
    @Test
    public void testSelectedOrgDTOProperty(){
        assertNull(action.getSelectedOrgDTO());
        action.setSelectedOrgDTO(new OrganizationDTO());
        assertNotNull(action.getSelectedOrgDTO());
    }
    @Test
    public void testCbValueProperty(){
        assertNull(action.getCbValue());
        action.setCbValue(1L);
        assertNotNull(action.getCbValue());
    }
    @Test
    public void testFunctionalCodeProperty(){
        assertNull(action.getFunctionalCode());
        action.setFunctionalCode("functionalCode");
        assertNotNull(action.getFunctionalCode());
    }
    @Test
    public void testCurrentActionProperty(){
        assertNull(action.getCurrentAction());
        action.setCurrentAction("currentAction");
        assertNotNull(action.getCurrentAction());
    }
    @Test
    public void testOrgFromPOProperty(){
        assertNotNull(action.getOrgFromPO());
        action.setOrgFromPO(null);
        assertNull(action.getOrgFromPO());
    }
    @Test
    public void testStatusCodeProperty(){
        assertNull(action.getStatusCode());
        action.setStatusCode("statusCode");
        assertNotNull(action.getStatusCode());
    }
    @Test
    public void testExecute() throws Exception{
        assertEquals("success",action.execute());
    }
    @Test
    public void testCreate() throws Exception {
        assertEquals("create",action.create());
    }
    @Test
    public void testDelete() throws Exception {
        action.setObjectsToDelete(new String[] {"1"});
        assertEquals("delete",action.delete());       
    }
    @Test
    public void testDisplayOrg() throws Exception {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgId", "1");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("displayJsp",action.displayOrg());        
    }
    @Test
    public void testEdit() throws Exception {
        action.setCbValue(1L);
        assertEquals("edit",action.edit());
    }
    @Test
    public void testNodecorlookup() throws Exception {
        assertEquals("lookup", action.nodecorlookup());
    }
    @Test
    public void  testFacilityUpdateNull() throws Exception {
        assertEquals("success", action.facilityUpdate());
        assertNotNull(action.getActionErrors());
    }
    @Test
    public void  testFacilityUpdate() throws Exception {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ParticipatingOrganizationsTabWebDTO tab =new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(1L);
        sess.setAttribute("participatingOrganizationsTabs", tab);
        action.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE.getCode());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("facilitySave", action.facilityUpdate());        
    }
    @Test
    public void  testFacilityUpdateE() throws Exception {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ParticipatingOrganizationsTabWebDTO tab =new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(1L);
        sess.setAttribute("participatingOrganizationsTabs", tab);
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("edit", action.facilityUpdate());
        assertNotNull(action.getActionErrors());
    }
    @Test
    public void  testFacilitySaveNull() throws Exception {
        assertEquals("create", action.facilitySave());
        assertNotNull(action.getActionErrors());
    }
    
    @Test
    public void  testFacilitySave() throws Exception {
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ParticipatingOrganizationsTabWebDTO tab =new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(1L);
        Organization facilityOrg = new Organization();
        facilityOrg.setIdentifier("1");
        tab.setFacilityOrganization(facilityOrg);
        sess.setAttribute("participatingOrganizationsTabs", tab);        
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("create", action.facilitySave());
        action.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode());
        assertEquals("facilitySave", action.facilitySave());
    }
}
