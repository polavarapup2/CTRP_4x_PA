/**
 * 
 */
package gov.nih.nci.pa.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.action.ManageUserGroupsAction;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.dispatcher.StreamResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

/**
 * @author dkrylov
 * 
 */
public class ManageUserGroupsActionTest extends AbstractPaActionTest {

    ManageUserGroupsAction action;

    @Before
    public void setUp() throws CSObjectNotFoundException {
        action = new ManageUserGroupsAction();
        action.setServletResponse(getResponse());

        User user = new User();
        user.setUserId(1L);
        user.setLoginName("curator");

        UserProvisioningManager upm = mock(UserProvisioningManager.class);
        when(upm.getUserById(eq("1"))).thenReturn(user);
        action.setUpm(upm);

    }

    @Test
    public final void testExecute() {
        assertEquals("input", action.execute());
    }

    @Test
    public final void testSaveRoles() throws IOException,
            IllegalArgumentException, IllegalAccessException,
            SecurityException, NoSuchFieldException, CSTransactionException {
        action.setUserID(1L);
        action.setNewGroups("SecurityAdmin;Curator");
        final StreamResult result = (StreamResult) action.saveRoles();
        final Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);
        assertEquals("", IOUtils.toString((InputStream) field.get(result)));

        verify(action.getUpm())
                .assignGroupsToUser(eq("1"), eq(new String[] {}));
        verify(action.getUpm()).assignUserToGroup(eq("curator"),
                eq("SecurityAdmin"));
        verify(action.getUpm()).assignUserToGroup(eq("curator"), eq("Curator"));
    }

}
