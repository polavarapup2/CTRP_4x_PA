package gov.nih.nci.registry.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.junit.Test;

public class ServletContextConfiguratorTest {

    @Test
    public void testContext() {
        ServletContextConfigurator conf = new ServletContextConfigurator();
        ServletContextEvent event = new ServletContextEvent(new MockContext());
        conf.contextInitialized(event);
        assertEquals("/registry", event.getServletContext().getAttribute("staticPath"));
        
        conf.contextDestroyed(event);
        //test the do nothing function does nothing.
        assertNotNull(event);
    }
    
    private class MockContext implements ServletContext {
        HashMap<String, Object> attribute = new HashMap<String, Object>();

        @Override
        public Object getAttribute(String key) {
            return attribute.get(key);
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return (new Vector<String>(attribute.keySet())).elements();
        }

        @Override
        public ServletContext getContext(String arg0) {
            return this;
        }

        @Override
        public String getInitParameter(String arg0) {
            return null;
        }

        @Override
        public Enumeration getInitParameterNames() {
            return null;
        }

        @Override
        public int getMajorVersion() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public String getMimeType(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int getMinorVersion() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public RequestDispatcher getNamedDispatcher(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getRealPath(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public URL getResource(String arg0) throws MalformedURLException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public InputStream getResourceAsStream(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Set getResourcePaths(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getServerInfo() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Servlet getServlet(String arg0) throws ServletException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getServletContextName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Enumeration getServletNames() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Enumeration getServlets() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void log(String arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void log(Exception arg0, String arg1) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void log(String arg0, Throwable arg1) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void removeAttribute(String key) {
            attribute.remove(key);
            
        }

        @Override
        public void setAttribute(String key, Object value) {
            attribute.put(key, value);
        }

        @Override
        public Dynamic addFilter(String arg0, String arg1) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Dynamic addFilter(String arg0, Filter arg1) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void addListener(String arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public <T extends EventListener> void addListener(T arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void addListener(Class<? extends EventListener> arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, String arg1) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void declareRoles(String... arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public ClassLoader getClassLoader() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getContextPath() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int getEffectiveMajorVersion() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getEffectiveMinorVersion() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public FilterRegistration getFilterRegistration(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public JspConfigDescriptor getJspConfigDescriptor() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public ServletRegistration getServletRegistration(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Map<String, ? extends ServletRegistration> getServletRegistrations() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public SessionCookieConfig getSessionCookieConfig() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean setInitParameter(String arg0, String arg1) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) {
            // TODO Auto-generated method stub
            
        }

        
    }
}
