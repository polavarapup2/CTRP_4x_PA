package gov.nih.nci.pa.service.util;

import static java.lang.String.format;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.MessageLog;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.OrganizationSynchronizationServiceLocal;
import gov.nih.nci.pa.service.correlation.PersonSynchronizationServiceLocal;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoJndiServiceLocator;
import gov.nih.nci.services.SubscriberUpdateMessage;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

/**
 * @author Hugh Reinhart
 * @author Paul Cowan
 * @since Mar 27, 2014
 */
/**
 * @author paul
 *
 */
@Singleton
@Startup
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
public class PoJmsMessageReceiver implements MessageListener, ExceptionListener, Runnable {

    private static final Logger LOG = Logger.getLogger(PoJmsMessageReceiver.class);
    
    /**
     * Startup delay
     */
    public static final long STARTUP_DELAY_MS = 15000;
    
    /**
     * Reconnect delay
     */
    public static final long RECONNECT_DELAY_MS = 5000;

    @EJB
    private OrganizationSynchronizationServiceLocal orgRemote;

    @EJB
    private PersonSynchronizationServiceLocal perRemote;

    @EJB
    private FamilySynchronizationServiceLocal familyLocal;

    private Thread connectThread;

    private String factoryName = "jms/PORemoteConnectionFactory";

    private String topicName = "jms/topic/POTopic";
    private String subscriptionName = "PAApp";
    private long startupDelay = STARTUP_DELAY_MS;
    private long reconnectDelay = RECONNECT_DELAY_MS;

    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private TopicSubscriber topicSubscriber;

    private final Object lockObject = new Object();
    
    private boolean connected = false;
    private boolean stopFlag = false;
    private boolean running = false;
    private String statusMessage;

    /**
     * Start the connection thread
     */
    @PostConstruct
    public synchronized void init() {
        LOG.info("Starting Singleton PO JMS subscriber...");
        
        PaHibernateUtil.getHibernateHelper();
        
        if (!running) {
            connectThread = new Thread(this);
            connectThread.setName("Thread-" + this.getClass().getSimpleName());
            connectThread.setDaemon(true);
            connectThread.start();
        }
    }

    @Override
    public void run() {
        boolean interrupted = false;
        try {
            running = true;
            LOG.info(format("Connect thread will start after an inital delay of %s ms: status=%s", 
                    startupDelay, statusMessage));
            try {
                Thread.sleep(startupDelay);
            } catch (Exception e) {
                LOG.debug(e);
            }
            while (true) {
                try {
                    disconnect();
                    if (stopFlag || interrupted) {
                        break;
                    }
                    connect();
                    if (isConnected()) {
                        synchronized (lockObject) {
                            try {
                                lockObject.wait(); // block until we are
                                                   // signaled to reconnect by
                                                   // exception handler
                            } catch (InterruptedException e) {
                                LOG.debug("Connect thread was interrupted");
                                interrupted = true;
                            }
                        }
                    } else {
                        LOG.info(format("Delaying reconnection attempt for %s ms", reconnectDelay));
                        Thread.sleep(reconnectDelay);
                    }
                } catch (Exception e) {
                    LOG.error("Encountered unexpected exception in connect thread", e);
                    try {
                        // this prevents rapid looping if we encounter an unexpected error
                        LOG.info(format("Delaying reconnection attempt for %s ms", reconnectDelay));
                        Thread.sleep(reconnectDelay);
                    } catch (Exception e2) {
                        LOG.debug(e2);
                    }
                }
            }
        } finally {
            running = false;
            LOG.info(format("Connect thread exiting: stopFlag=%s, interrupted=%s, status=%s", 
                    stopFlag, interrupted, statusMessage));
        }
    }

    /**
     * Disconnect and stop the connection thread
     */
    public void stop() {
        stopFlag = true;
        synchronized (lockObject) {
            lockObject.notifyAll();
        }
    }

    /**
     * @return connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @return running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @return statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @param msg statusMessage
     */
    private void setStatusMessage(String msg) {
        statusMessage = msg;
    }

    /**
     * @return topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName topicName
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    
    /**
     * @return subscriptionName
     */
    public String getSubscriptionName() {
        return subscriptionName;
    }

    /**
     * @param subscriptionName subscriptionName
     */
    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }
    
    /**
     * @return factoryName
     */
    public String getFactoryName() {
        return factoryName;
    }

    /**
     * @param factoryName factoryName
     */
    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    /**
     * @return the startupDelay
     */
    public long getStartupDelay() {
        return startupDelay;
    }

    /**
     * @param startupDelay the startupDelay to set
     */
    public void setStartupDelay(long startupDelay) {
        this.startupDelay = startupDelay;
    }

    /**
     * @return the reconnectDelay
     */
    public long getReconnectDelay() {
        return reconnectDelay;
    }

    /**
     * @param reconnectDelay the reconnectDelay to set
     */
    public void setReconnectDelay(long reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }

    /**
     * Connect to JMS
     * @return success
     */
    public synchronized boolean connect() {
        if (isConnected()) {
            LOG.warn("Attempting to connect when already connected!");
            return true;
        }

        setStatusMessage("Connecting");

        String clientId = null;
        String user = null;
        String pass = null;
        String serverName = null;

        try {
            clientId = PaEarPropertyReader.getLookUpServerPoJmsClientId();
            user = PaEarPropertyReader.getLookUpServerPoJmsPricipal();
            pass = PaEarPropertyReader.getLookUpServerPoJmsCredentials();
            serverName = PaEarPropertyReader.getPoServerName();
        } catch (PAException e) {
            setStatusMessage(e.toString());
            LOG.info(
                format("Failed to connect to PO JMS Topic %s at %s as Client %s with Subscription %s using Factory %s", 
                        topicName, serverName, clientId, subscriptionName, factoryName), e);
            return false;
        }

        LOG.info(format("Connecting to PO JMS Topic %s at %s as Client %s with Subscription %s using Factory %s", 
                topicName, serverName, clientId, subscriptionName, factoryName));

        Context context = null;

        try {
            context = PoJndiServiceLocator.getContext();
            if (context == null) {
                throw new NamingException("Failed to obtain JNDI Context from PoJndiServiceLocator");
            }

            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup(factoryName);
            fixConnectionFactoryHostName(connectionFactory);

            Topic topic = (Topic) context.lookup(topicName);

            topicConnection = connectionFactory.createTopicConnection(user, pass);
            topicConnection.setClientID(clientId);
            topicConnection.setExceptionListener(this);

            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topicSubscriber = topicSession.createDurableSubscriber(topic, subscriptionName);
            topicSubscriber.setMessageListener(this);
            topicConnection.start();

            connected = true;
            setStatusMessage("Connected");

            LOG.info(format("Connected to PO JMS Topic %s at %s as Client %s with Subscription %s using Factory %s", 
                    topicName, serverName, clientId, subscriptionName, factoryName));
            return true;
        } catch (NamingException e) {
            setStatusMessage(e.toString());
            LOG.info(
                format("Failed to connect to PO JMS Topic %s at %s as Client %s with Subscription %s using Factory %s", 
                    topicName, serverName, clientId, subscriptionName, factoryName), e);
            return false;
        } catch (JMSException e) {
            setStatusMessage(e.toString());
            LOG.info(
                format("Failed to connect to PO JMS Topic %s at %s as Client %s with Subscription %s using Factory %s", 
                    topicName, serverName, clientId, subscriptionName, factoryName), e);
            return false;
        }
        
        // JNDI Context is shared in PA, don't close it
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void fixConnectionFactoryHostName(TopicConnectionFactory connectionFactory) {
        try {
            Field serverLocatorField = ReflectionUtils.findField(connectionFactory.getClass(), "serverLocator");
            if (serverLocatorField == null) {
                LOG.info("Skipping JBoss ConnectionFactory host name fix");
                return;
            }

            ReflectionUtils.makeAccessible(serverLocatorField);
            Object serverLocator = serverLocatorField.get(connectionFactory);

            Field initialConnectorsField = ReflectionUtils.findField(serverLocator.getClass(), "initialConnectors");
            ReflectionUtils.makeAccessible(initialConnectorsField);
            Object initialConnectors = initialConnectorsField.get(serverLocator);

            Object transportConfiguration = Array.get(initialConnectors, 0);
            Field paramsField = ReflectionUtils.findField(transportConfiguration.getClass(), "params");

            ReflectionUtils.makeAccessible(paramsField);
            Map params = (Map) paramsField.get(transportConfiguration);

            params.put("host", PaEarPropertyReader.getPoServerName());

            LOG.info(format("Applied JBoss ConnectionFactory host name fix: host=%s, port=%s", 
                    params.get("host"), params.get("port")));
        } catch (Exception e) {
            LOG.error("Failed to apply JBoss ConnectionFactory host name fix", e);
        }
    }

    /**
     * Disconnect from JMS
     */
    public synchronized void disconnect() {
        if (topicSubscriber != null || topicSession != null || topicConnection != null) {
            LOG.info(format("Disconnecting from PO JMS Topic %s", topicName));
        }

        // attempting close or unsubscribe on a
        // disconnected connection causes a long pause

        if (topicSubscriber != null) {
            try {
                if (connected) {
                    topicSubscriber.close();
                }
            } catch (Exception e) {
                LOG.warn(format("TopicSubscriber close failed"), e);
            } finally {
                topicSubscriber = null;
            }
        }

        if (topicSession != null) {
            try {
                if (connected) {
                    topicSession.close();
                }
            } catch (Exception e) {
                LOG.warn(format("TopicSession close failed"), e);
            } finally {
                topicSession = null;
            }
        }

        if (topicConnection != null) {
            try {
                topicConnection.close();
            } catch (Exception e) {
                LOG.warn(format("TopicConnection close failed"), e);
            } finally {
                topicConnection = null;
            }

            setStatusMessage("Disconnected");
        }

        connected = false;
    }

    @Override
    public void onException(JMSException e) {
        LOG.error("Caught JMS connection exception, will reconnect...", e);

        connected = false;
        setStatusMessage(e.getMessage());

        synchronized (lockObject) {
            lockObject.notifyAll();
        }
    }

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof ObjectMessage)) {
            LOG.info(format("PoJmsMessageReceiver processMessage() ignoring message of type %s: %s", 
                    message.getClass().getName(), message));
            return;
        }

        PaHibernateUtil.getHibernateHelper().openAndBindSession();

        ObjectMessage msg = null;
        SubscriberUpdateMessage updateMessage = null;
        String identifierName = null;
        Long msgId = null;

        try {
            msg = (ObjectMessage) message;
            updateMessage = (SubscriberUpdateMessage) msg.getObject();
            identifierName = updateMessage.getId().getIdentifierName();

            LOG.info(format("PoJmsMessageReceiver processMessage() got the Identifier to be processed %s",
                    updateMessage.getId().getExtension()));

            msgId = createAuditMessageLog(updateMessage.getId());

            if (identifierName.equals(IiConverter.ORG_IDENTIFIER_NAME)) {
                orgRemote.synchronizeOrganization(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME)) {
                orgRemote.synchronizeHealthCareFacility(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME)) {
                orgRemote.synchronizeOversightCommittee(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.RESEARCH_ORG_IDENTIFIER_NAME)) {
                orgRemote.synchronizeResearchOrganization(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.PERSON_IDENTIFIER_NAME)) {
                perRemote.synchronizePerson(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME)) {
                perRemote.synchronizeClinicalResearchStaff(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME)) {
                perRemote.synchronizeHealthCareProvider(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME)) {
                perRemote.synchronizeOrganizationalContact(updateMessage.getId());
            }
            if (identifierName.equals(IiConverter.PO_FAMILY_ORG_REL_IDENTIFIER_NAME)) {
                familyLocal.synchronizeFamilyOrganizationRelationship(IiConverter.convertToLong(updateMessage.getId()));
            }

            updateExceptionAuditMessageLog(msgId, "Processed", null, true);
        } catch (PAException e) {
            LOG.error("PoJmsMessageReceiver processMessage() method threw a PAException ", e);
            updateExceptionAuditMessageLog(msgId, "Failed", " PAException -" + e.getMessage(), false);
        } catch (JMSException e) {
            LOG.error("PoJmsMessageReceiver processMessage() method threw a JMSException", e);
            updateExceptionAuditMessageLog(msgId, "Failed", " JMSException-" + e.getMessage(), false);
        } catch (Exception e) {
            LOG.error("PoJmsMessageReceiver processMessage() method threw an unexpected Exception ", e);
            updateExceptionAuditMessageLog(msgId, "Failed", " Generic exception -" + e.getMessage(), false);
        } finally {
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
        }
    }

    private Long createAuditMessageLog(Ii identifier) throws PAException {
        org.hibernate.Session session = PaHibernateUtil.getCurrentSession();
        MessageLog log = new MessageLog();
        log.setAssignedIdentifier(identifier.getExtension());
        log.setDateCreated(new Date());
        log.setEntityName(identifier.getIdentifierName());
        log.setMessageAction("Received");
        log.setResult(Boolean.TRUE);
        session.save(log);
        return log.getId();
    }

    private void updateExceptionAuditMessageLog(Long id, String msgAction, String errorMessage, boolean result) {
        org.hibernate.Session session = PaHibernateUtil.getCurrentSession();
        MessageLog msg = (MessageLog) session.get(MessageLog.class, id);
        msg.setMessageAction(msgAction);
        msg.setResult(Boolean.valueOf(result));
        msg.setExceptionMessage(errorMessage);
        session.update(msg);
    }
}
