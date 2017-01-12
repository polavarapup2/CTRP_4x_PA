/**
 *
 */
package gov.nih.nci.registry.interceptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.action.AbstractRegWebTest;
import gov.nih.nci.registry.service.MockLookUpTableService;

/**
 * @author vpoluri
 *
 */
public class ReportViewerInterceptorTest extends AbstractRegWebTest {
    private ReportViewerInterceptor interceptor;

    private ActionInvocation actionInvocation;
    private ActionContext invocationContext;
    private Map<String, Object> params;
    private ValueStack stack;
    private ValueStackFactory stackFactory;
    private Map<String, Object> session;

    @Before
    public void setup() throws Exception {
        interceptor = new ReportViewerInterceptor();
        setupProperties();
        initInvocation();
    }

    private void setupProperties() throws PAException {
        interceptor.setLookupTableService(new MockLookUpTableService());

    }

    private void initInvocation() throws Exception {
        actionInvocation = mock(ActionInvocation.class);
        invocationContext = mock(ActionContext.class);
        params = new HashMap<String, Object>();

        session = new HashMap<String, Object>();
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();
        stackFactory = container.getInstance(ValueStackFactory.class);
        stack = stackFactory.createValueStack();
        stack.push(params);

        when(actionInvocation.getInvocationContext()).thenReturn(invocationContext);
        when(actionInvocation.getInvocationContext().getParameters()).thenReturn(params);
        when(actionInvocation.getInvocationContext().getSession()).thenReturn(session);
        when(actionInvocation.getStack()).thenReturn(stack);
        when(actionInvocation.invoke()).thenReturn("viewResults");
    }

    @Test
    public void testNoAdminNoReportAccess() throws Exception {
        session.put("isReportsAllowed", "flase");
        session.put("isSiteAdmin", false);
        assertEquals("noAccess", interceptor.intercept(actionInvocation));

    }

    @Test
    public void testAdminNoReportAccess() throws Exception {
        session.put("isSiteAdmin", true);
        session.put("isReportsAllowed", "flase");
        assertEquals("noAccess", interceptor.intercept(actionInvocation));
    }

    @Test
    public void testReportAccessNoAdmin() throws Exception {
        session.put("isReportsAllowed", "true");
        session.put("isSiteAdmin", false);
        assertEquals("noAccess", interceptor.intercept(actionInvocation));
    }

    @Test
    public void testAdminReportAccess() throws Exception {
        session.put("isReportsAllowed", "true");
        session.put("isSiteAdmin", true);
        assertEquals("viewResults", interceptor.intercept(actionInvocation));
    }

    @Test
    public void testNoSession() throws Exception {
        session.put("isSiteAdmin", true);
        assertEquals("viewResults", interceptor.intercept(actionInvocation));
    }
}
