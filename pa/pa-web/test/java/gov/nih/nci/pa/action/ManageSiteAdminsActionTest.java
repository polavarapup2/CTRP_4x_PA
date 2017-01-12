package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.Constants;

import java.util.Arrays;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

public class ManageSiteAdminsActionTest extends AbstractPaActionTest {
    ManageSiteAdminsAction action;
    Organization org;
    RegistryUser user;

    RegistryUserServiceLocal regSvcMock;

    @Before
    public void setUp() throws PAException {
        action = new ManageSiteAdminsAction();
        MockHttpServletRequest request = getRequest();
        MockHttpSession session = getSession();
        getSession().setAttribute(Constants.IS_ABSTRACTOR, Boolean.TRUE);
        request.setSession(session);
        ServletActionContext.setRequest(getRequest());

        org = new Organization();
        org.setId(1L);
        org.setName("NCI");

        user = new RegistryUser();
        user.setAffiliatedOrgUserType(UserOrgType.PENDING_ADMIN);
        user.setId(1L);

        PAOrganizationServiceRemote paOrgSvcMock = mock(PAOrganizationServiceRemote.class);
        when(paOrgSvcMock.getOrganizationsWithUserAffiliations()).thenReturn(
                Arrays.asList(org));
        action.setPaOrganizationServiceRemote(paOrgSvcMock);

        regSvcMock = mock(RegistryUserServiceLocal.class);
        when(regSvcMock.findByAffiliatedOrg(1L))
                .thenReturn(Arrays.asList(user));
        action.setRegistryUserService(regSvcMock);

        action.setModel(new ManageSiteAdminsAction.ManageSiteAdminsModel());
        action.setScopeKey("key");

    }

    @Test
    public void execute() throws PAException {
        String s = action.execute();
        assertEquals("success", s);
        assertEquals(1, action.getModel().getOrganizations().size());
        assertEquals(org, action.getModel().getOrganizations().get(0));
        assertEquals(0, action.getModel().getAdmins().size());
        assertEquals(0, action.getModel().getMembers().size());
    }

    @Test
    public void query() throws PAException {
        action.setSelectedOrganizationId(1L);
        String s = action.query();
        assertEquals("success", s);
        assertEquals(0, action.getModel().getAdmins().size());
        assertEquals(1, action.getModel().getMembers().size());
    }

    @Test
    public void assign() throws PAException {
        action.setSelectedOrganizationId(1L);
        action.setUsersToAssign("1");
        String s = action.assign();
        assertEquals("success", s);
        verify(regSvcMock).changeUserOrgType(1L, UserOrgType.ADMIN, "");
        reset(regSvcMock);

    }

    @Test
    public void assignAll() throws PAException {
        action.setSelectedOrganizationId(1L);
        action.getModel().getMembers().clear();
        action.getModel().getMembers().add(user);
        String s = action.assignAll();
        assertEquals("success", s);
        verify(regSvcMock).changeUserOrgType(1L, UserOrgType.ADMIN, "");
        reset(regSvcMock);

    }

    @Test
    public void unassignAll() throws PAException {
        action.setSelectedOrganizationId(1L);
        action.getModel().getAdmins().clear();
        action.getModel().getAdmins().add(user);
        String s = action.unassignAll();
        assertEquals("success", s);
        verify(regSvcMock).changeUserOrgType(1L, UserOrgType.MEMBER, "");
        reset(regSvcMock);

    }

    @Test
    public void unassign() throws PAException {
        action.setSelectedOrganizationId(1L);
        action.setUsersToUnassign("1");
        String s = action.unassign();
        assertEquals("success", s);
        verify(regSvcMock).changeUserOrgType(1L, UserOrgType.MEMBER, "");
        reset(regSvcMock);

    }

}
