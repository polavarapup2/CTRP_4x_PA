/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.dto.UserWebDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;


/**
 * @author Vrushali
 *
 */
public class RegisterUserActionTest extends AbstractRegWebTest {
    private RegisterUserAction action;
    private final RegistryUserService regUserSvc = mock(RegistryUserService.class);
    private final CSMUserService csmSvc = mock(CSMUserService.class);

    private final RegistryUser regUser = new RegistryUser();
    private final RegistryUserWebDTO regDto = new RegistryUserWebDTO();

    private final UserWebDTO userDto = new UserWebDTO();


    @Before
    public void setup() throws PAException, IOException {
        User csmUser = new User();

        when(csmSvc.getCSMUserById(anyLong())).thenReturn(csmUser);
        when(csmSvc.getCSMUser(anyString())).thenReturn(csmUser);
        when(csmSvc.createCSMUser((RegistryUser)anyObject(), anyString(), anyString())).thenReturn(csmUser);

        CSMUserService.setInstance(csmSvc);

        action = new RegisterUserAction();
        action.prepare();

        Map<String, String> idps = new HashMap<String, String>();
        idps.put("dorian", "/O=caBIG/OU=Dorian");
       

        regUser.setCsmUser(csmUser);
        regUser.setAffiliatedOrganizationId(1L);
        regUser.setReportGroups("DataTable4");

        when(regUserSvc.getUser(anyString())).thenReturn(regUser);
        when(regUserSvc.getUserById(anyLong())).thenReturn(regUser);

        action.setRegistryUserService(regUserSvc);

        regDto.setEmailAddress("sample@example.com");
        regDto.setState("MD");
        regDto.setCountry("United States");
        regDto.setFirstName("Jo");
        regDto.setLastName("Smith");
        regDto.setAffiliateOrg("MyOrg");
        regDto.setPhone("111-222-3333");
        regDto.setAddressLine("street");
        regDto.setCity("city");
        regDto.setPostalCode("12345");
        regDto.setAffiliatedOrganizationId(1L);
        regDto.setId(1L);
        action.setRegistryUserWebDTO(regDto);

        userDto.setUsername("myuser");
        userDto.setPassword("test");
        action.setUserWebDTO(userDto);
    }

    @Test
    public void testUserWebDTO() {
        assertEquals(userDto, action.getUserWebDTO());
        assertEquals("myuser", userDto.getDisplayUsername());
        assertEquals("test", userDto.getPassword());
    }


    @Test
    public void testRegistryUserWebDTO() {
        assertEquals(regDto, action.getRegistryUserWebDTO());
    }

    @Test
    public void testRequestAccount() {
        assertEquals(Constants.CREATE_ACCOUNT, action.requestAccount());
    }

    @Test
    public void testRequestAccountException() throws PAException {
        when(regUserSvc.getUser(anyString())).thenThrow(new PAException());
        assertEquals(Constants.APPLICATION_ERROR, action.requestAccount());
    }

    @Test
    public void testRequestAccountNoAffOrg() throws PAException {
        regUser.setAffiliatedOrganizationId(null);
        assertEquals(Constants.CREATE_ACCOUNT, action.requestAccount());
    }

    @Test
    public void testVerifyEmailExistingRegUser() {
        // this causes an error because regUser has to be null
        assertEquals(Constants.CREATE_ACCOUNT, action.verifyEmail());
    }

    @Test
    public void testVerifyEmailAllGood() throws PAException {
        when(regUserSvc.getUser(anyString())).thenReturn(null);
        assertEquals(Constants.CREATE_ACCOUNT, action.verifyEmail());
    }

    @Test
    public void testVerifyEmailNoEmail() throws PAException {
        regDto.setEmailAddress("");
        assertEquals(Constants.REGISTER_USER_ERROR, action.verifyEmail());
        assertTrue(action.getFieldErrors().keySet().contains("registryUserWebDTO.emailAddress"));
    }

    @Test
    public void testVerifyEmailInvalidEmail() throws PAException {
        regDto.setEmailAddress("Oops!");
        assertEquals(Constants.REGISTER_USER_ERROR, action.verifyEmail());
        assertTrue(action.getFieldErrors().keySet().contains("registryUserWebDTO.emailAddress"));
    }

    @Test
    public void testActivateNoToken() {        
        assertEquals("activation", action.activate());
        assertTrue(action.getActionMessages().contains("Invalid URL."));
    }

   

    @Test
    public void testActivate() {
        assertEquals("activation", action.activate());        
    }

    @Test
    public void testShowMyAccount() {
        assertEquals(Constants.MY_ACCOUNT, action.showMyAccount());
    }

    @Test
    public void testShowMyAccountAndLoadAdminUser() {
        regUser.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        assertEquals(Constants.MY_ACCOUNT, action.showMyAccount());
    }

    @Test
    public void testCreateAccount() {
        assertEquals("confirmation", action.createAccount());
    }

    @Test
    public void testCreateAccountWithAdmin() {
        regDto.setRequestAdminAccess(true);
        assertEquals("confirmation", action.createAccount());
    }

    @Test
    public void testCreateAccountLdapAccountButNoCSM() throws PAException {
        when(csmSvc.getCSMUser(anyString())).thenReturn(null);
        assertEquals("confirmation", action.createAccount());
        verify(csmSvc).createCSMUser((RegistryUser)anyObject(), anyString(), anyString());
    }

    @Test
    public void testCreateAccountNoLdapAccount() throws PAException {
        userDto.setUsername("");
        assertEquals("confirmation", action.createAccount());
    }

    @Test
    public void testCreateAccountMissingField() {
        regDto.setState(null);
        assertEquals(Constants.CREATE_ACCOUNT, action.createAccount());
        assertTrue(action.getFieldErrors().keySet().contains("registryUserWebDTO.state"));
    }

    @Test
    public void testCreateAccountInvalidUSState() {
        regDto.setState("None");
        assertEquals(Constants.CREATE_ACCOUNT, action.createAccount());
        assertTrue(action.getFieldErrors().keySet().contains("registryUserWebDTO.state"));
    }

    @Test
    public void testCreateAccountInvalidNonUSState() {
        regDto.setCountry("France");
        assertEquals(Constants.CREATE_ACCOUNT, action.createAccount());
        assertTrue(action.getFieldErrors().keySet().contains("registryUserWebDTO.state"));
    }

    @Test
    public void testCreateAccountMissingOrg() {
        regDto.setAffiliatedOrganizationId(null);
        assertEquals(Constants.CREATE_ACCOUNT, action.createAccount());
        assertTrue(action.getFieldErrors().keySet().contains("registryUserWebDTO.affiliateOrg"));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        MockHttpServletRequest req = (MockHttpServletRequest) ServletActionContext.getRequest();
        req.setRemoteUser("RegUser");
        assertNull(req.getSession().getAttribute("regUserWebDto"));
        assertNotNull(regUserSvc.getUserById(1L).getReportGroups());
        action.getRegistryUserWebDTO().setAffiliatedOrganizationId(2L);
        assertEquals("logout", action.updateAccount());
        assertNull(regUserSvc.getUserById(1L).getReportGroups());
    }

    @Test
    public void testUpdateAccountAdminRequest() {
        regDto.setRequestAdminAccess(true);
        assertEquals("confirmation", action.updateAccount());
    }

    @Test
    public void testUpdateAccountNoRegUser() throws PAException {
        when(regUserSvc.getUserById(anyLong())).thenReturn(null);
        assertEquals("confirmation", action.updateAccount());
    }

    @Test
    public void testUpdateAccountException() throws PAException {
        when(regUserSvc.getUserById(anyLong())).thenThrow(new PAException());
        assertEquals(Constants.APPLICATION_ERROR, action.updateAccount());
    }

    @Test
    public void testUpdateAccountFieldError() {
        regDto.setCountry("France");
        MockHttpServletRequest req = (MockHttpServletRequest) ServletActionContext.getRequest();
        req.setRemoteUser("RegUser");
        assertEquals(Constants.MY_ACCOUNT_ERROR, action.updateAccount());
        assertEquals(req.getAttribute("userName"), "RegUser");
    }

    @Test
    public void testLoadAdminUser() {
        assertEquals("loadAdminList", action.loadAdminUsers());
    }

    @Test
    public void testViewAdminUser() {
        assertEquals("viewAdminUser", action.viewAdminUsers());
    }

}
