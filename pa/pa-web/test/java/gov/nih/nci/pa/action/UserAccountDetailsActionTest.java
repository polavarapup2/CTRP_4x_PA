package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.MockPoOrganizationEntityService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Action;

public class UserAccountDetailsActionTest extends AbstractPaActionTest {
    
    private UserAccountDetailsAction action;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        action = new UserAccountDetailsAction();
        PoServiceLocator poServiceLocator = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);
        CSMUserService.setInstance(new MockCSMUserService());
        when(PoRegistry.getOrganizationEntityService()).thenReturn(new MockPoOrganizationEntityService());
        action.prepare();
        UsernameHolder.setUser("user3@mail.nih.gov");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExecute() throws PAException {
        action.execute();
        assertNotNull(action.getUser());
        assertEquals("user3@mail.nih.gov",action.getUser().getLoginName());
        assertTrue(action.getUser().getUserId().equals(3L));
        assertEquals("FirstName",action.getFirstName());
        assertEquals("LastName",action.getLastName());
        assertEquals("Organization",action.getOrganization());
        assertEquals("1231231234",action.getPhoneNumber());
        assertEquals("user1@mail.nih.gov",action.getEmailId());
    }
    
    
    @Test
    public void testSave() throws PAException {

        action.execute();
        
        assertTrue(action.getUser().getUserId().equals(3L));
        assertEquals("user3@mail.nih.gov",action.getUser().getLoginName());
        assertEquals("FirstName",action.getUser().getFirstName());
        assertEquals("LastName",action.getUser().getLastName());
        assertEquals("Organization",action.getUser().getOrganization());
        assertEquals("1231231234",action.getUser().getPhoneNumber());
        assertEquals("user1@mail.nih.gov",action.getUser().getEmailId());
        
        action.setFirstName("John");
        action.setLastName("Doe");
        action.setOrganization("NCI");
        action.setEmailId("john.doe@nci.ann");
        action.setPhoneNumber("1112221234");
        
        action.save();
        
        assertNotNull(action.getUser());
        assertTrue(action.getUser().getUserId().equals(3L));
        assertEquals("user3@mail.nih.gov",action.getUser().getLoginName());
        assertEquals("John",action.getUser().getFirstName());
        assertEquals("Doe",action.getUser().getLastName());
        assertEquals("NCI",action.getUser().getOrganization());
        assertEquals("1112221234",action.getUser().getPhoneNumber());
        assertEquals("john.doe@nci.ann",action.getUser().getEmailId());
        assertEquals(Constants.UPDATE_MESSAGE, ServletActionContext
                .getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testGetUserName() throws PAException{
        action.execute();
        action.getUser().setLoginName("");
        assertNull(action.getUserName());
        action.getUser().setLoginName(null);
        assertNull(action.getUserName());
        action.getUser().setLoginName("/O=caBIG/OU=caGrid/OU=Training/OU=National Cancer Institute/CN=monishd");
        assertEquals("monishd", action.getUserName());
    }

    @Test
    public void testUpdateOrgName() throws Exception {
        getRequest().setupAddParameter("orgId", "1");
        assertEquals(Action.SUCCESS, action.updateOrgName());
        assertEquals("OrgName", action.getOrganization());
        OrganizationEntityServiceRemote organizationEntityService = mock(OrganizationEntityServiceRemote.class);
        action.setOrganizationEntityService(organizationEntityService);
        when(organizationEntityService.
                getOrganization(IiConverter.convertToPoOrganizationIi("1L"))).thenReturn(null);
        assertEquals(Action.SUCCESS, action.updateOrgName());
        assertTrue(action.getActionErrors().size() > 0);
    }
}
