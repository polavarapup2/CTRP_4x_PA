package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import gov.nih.nci.pa.service.correlation.OrganizationSynchronizationServiceLocal;
import gov.nih.nci.pa.service.correlation.PersonSynchronizationServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PoJndiServiceLocator;

import java.lang.reflect.Field;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class PoJmsMessageReceiverTest extends AbstractHibernateTestCase {
    
    @Mock
    private Context mockContext;
    
    @Mock
    private TopicConnectionFactory mockConnectionFactory;
    
    @Mock
    private Topic mockTopic;
    
    @Mock
    private TopicConnection mockConnection;
    
    @Mock
    private TopicSession mockSession;
    
    @Mock
    private TopicSubscriber mockSubscriber;
    
    @Mock
    private OrganizationSynchronizationServiceLocal mockOrgService;
    
    @Mock
    private PersonSynchronizationServiceLocal mockPersonService;
    
    @Mock
    private FamilySynchronizationServiceLocal mockFamService;
    
    private PoJmsMessageReceiver receiver;
    
    @Before
    public void setup() throws Exception {
        Field propsField = PaEarPropertyReader.class.getDeclaredField("PROPS");
        propsField.setAccessible(true);
        Properties earProps = (Properties) propsField.get(null);
        earProps.setProperty("po.topic.clientId", "clientId");
        earProps.setProperty("po.topic.userName", "userName");
        earProps.setProperty("po.topic.password", "password");
        earProps.setProperty("po.server.name", "poServer");
        
        Field ctxField = PoJndiServiceLocator.class.getDeclaredField("ctx");
        ctxField.setAccessible(true);
        ctxField.set(null, mockContext);

        when(mockContext.lookup(anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String name = (String) invocation.getArguments()[0];
                if (name.toLowerCase().contains("factory"))
                    return mockConnectionFactory;
                else
                    return mockTopic;
            }
        });
        
        when(mockConnectionFactory.createTopicConnection(anyString(), anyString())).thenReturn(mockConnection);
        when(mockConnection.createTopicSession(anyBoolean(), anyInt())).thenReturn(mockSession);
        when(mockSession.createDurableSubscriber(any(Topic.class), anyString())).thenReturn(mockSubscriber);
        
        receiver = new PoJmsMessageReceiver();
        receiver.setReconnectDelay(100);
        receiver.setStartupDelay(100);
        
        Whitebox.setInternalState(receiver, "orgRemote" , mockOrgService);
        Whitebox.setInternalState(receiver, "perRemote" , mockPersonService);
        Whitebox.setInternalState(receiver, "familyLocal" , mockFamService);
    }
    
    @After
    public void cleanup() throws Exception {
        receiver.stop();
        waitForStop(receiver);
        Mockito.reset(mockConnection, mockConnectionFactory, mockContext, mockSession, mockSubscriber, mockTopic);
    }
    
    @Test
    public void testStartAndStop() throws Exception {
        receiver.init();
        waitForConnect(receiver);
        
        verify(mockSubscriber).setMessageListener(receiver);
        verify(mockConnection).start();
        verify(mockConnectionFactory).createTopicConnection("userName", "password");
        assertEquals("Connected", receiver.getStatusMessage());
        
        receiver.stop();
        waitForStop(receiver);
        
        verify(mockConnection).close();
        assertEquals("Disconnected", receiver.getStatusMessage());
    }
    
    @Test
    public void testAlreadyConnected() throws Exception {
        boolean connected = receiver.connect();
        waitForConnect(receiver);
        
        assertTrue(connected);
        
        boolean connectedAgain = receiver.connect();
        assertTrue(connectedAgain);
        
        assertEquals("Connected", receiver.getStatusMessage());
    }
    
    @Test
    public void testAlreadyStarted() throws Exception {
        receiver.init();
        waitForConnect(receiver);
        
        receiver.init();
        
        assertEquals("Connected", receiver.getStatusMessage());
        verify(mockSubscriber).setMessageListener(receiver);
        verify(mockConnection).start();
    }
    
    @Test
    public void testTopicSubscriberCloseException() throws Exception {
        doThrow(JMSException.class).when(mockSubscriber).close();
        
        boolean connected = receiver.connect();
        
        assertTrue(connected);
        verify(mockSubscriber).setMessageListener(receiver);
        verify(mockConnection).start();
        
        receiver.disconnect();
        
        verify(mockSubscriber).close();
        verify(mockConnection).close();
    }

    @Test
    public void testTopicConnectionCloseException() throws Exception {
        doThrow(JMSException.class).when(mockConnection).close();
        
        boolean connected = receiver.connect();
        
        assertTrue(connected);
        verify(mockSubscriber).setMessageListener(receiver);
        verify(mockConnection).start();
        
        receiver.disconnect();
        
        verify(mockSession).close();
        verify(mockConnection).close();
    }
    
    @Test
    public void testTopicSessionCloseException() throws Exception {
        doThrow(JMSException.class).when(mockSession).close();
        
        boolean connected = receiver.connect();
        
        assertTrue(connected);
        verify(mockSubscriber).setMessageListener(receiver);
        verify(mockConnection).start();
        
        receiver.disconnect();
        
        verify(mockSession).close();
        verify(mockConnection).close();
    }

    @Test
    public void testReconnectOnException() throws Exception {
        receiver.init();
        waitForConnect(receiver);
        
        receiver.onException(new JMSException("TEST"));
        
        Thread.sleep(500);
        
        assertEquals("Connected", receiver.getStatusMessage());
        verify(mockConnection, times(1)).close();
        verify(mockSubscriber, atLeast(2)).setMessageListener(receiver);
        verify(mockConnection, atLeast(2)).start();
    }

    @Test
    public void testDisconnectAfterError() throws Exception {
        receiver.connect();
        receiver.onException(new JMSException("TEST"));
        receiver.disconnect();
        
        assertEquals("Disconnected", receiver.getStatusMessage());
        verify(mockSubscriber).setMessageListener(receiver);
        verify(mockConnection).start();
        verify(mockConnection).close();
    }
    
    @Test
    public void testReconnectImmediately() throws Exception {
        when(mockContext.lookup(anyString())).thenThrow(new NamingException("TEST"));
        receiver.init();
        Thread.sleep(500);
        
        assertEquals("javax.naming.NamingException: TEST", receiver.getStatusMessage());
        verify(mockContext, atLeast(2)).lookup(anyString());
        verify(mockConnection, times(0)).close();
        verify(mockSubscriber, times(0)).setMessageListener(receiver);
        verify(mockConnection, times(0)).start();
    }
    
    public static void waitForConnect(PoJmsMessageReceiver receiver) throws Exception {
        int n = 0;
        while(!receiver.isConnected() && n++ < 100) {
            Thread.sleep(100);
        }
        if (!receiver.isConnected()) {
            throw new IllegalStateException("Timed out waiting for listener to connect");
        }
    }
    
    public static void waitForStop(PoJmsMessageReceiver receiver) throws Exception {
        int n = 0;
        while(receiver.isRunning() && n++ < 100) {
            Thread.sleep(100);
        }
        if (receiver.isRunning()) {
            throw new IllegalStateException("Timed out waiting for receiver stop");
        }
    }


}
