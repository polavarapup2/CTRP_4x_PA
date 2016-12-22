package gov.nih.nci.accrual.service.interceptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.security.Principal;

import javax.ejb.SessionContext;
import javax.interceptor.InvocationContext;

import org.junit.Test;

public class RemoteAuthorizationInterceptorTest {

	RemoteAuthorizationInterceptor interceptor = new RemoteAuthorizationInterceptor();
	
	@Test
	public void testPrepareReturnValue() throws Exception {
		SessionContext sessionContext = mock(SessionContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("JUnitUser");
        Field f = RemoteAuthorizationInterceptor.class.getDeclaredField("sessionContext");
        f.setAccessible(true);
        f.set(interceptor, sessionContext);
        when(sessionContext.getCallerPrincipal()).thenReturn(principal);
		interceptor.prepareReturnValue(mock(InvocationContext.class));
	}

}
