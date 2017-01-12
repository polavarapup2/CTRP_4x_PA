package gov.nih.nci.pa.service.util;

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

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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

import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since Mar 27, 2014
 */
@Singleton
@Startup
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
public class PoJmsMessageReceiver {

    private static final Logger LOG = Logger.getLogger(PoJmsMessageReceiver.class);
    private static final long JMS_TIMEOUT = 300000L;

    @EJB
    private OrganizationSynchronizationServiceLocal orgRemote;
    
    @EJB
    private PersonSynchronizationServiceLocal perRemote;
    
    @EJB
    private FamilySynchronizationServiceLocal familyLocal;

    private volatile Thread jmsThread;

    @PostConstruct
    void init() {
        LOG.info("Starting Singleton PO JMS subscriber...");
        try {
            PaHibernateUtil.getHibernateHelper();
            jmsThread = new Thread(new JMSClient());
            jmsThread.start();
        } catch (Exception e) {
            LOG.error("Exception starting PO JMS subscriber thread.", e);
        }
    }

    @PreDestroy
    void cleanUp() {
        if (jmsThread != null && jmsThread.isAlive()) {
            Thread tmp = jmsThread;
            jmsThread = null;
            tmp.interrupt();
        }
    }

    /**
     * The JMS Client thread.
     */
    private class JMSClient implements Runnable {
        private TopicConnection topicConnection = null;

        @Override
        public void run() {
            try {
                while (jmsThread != null) {
                    if (!isConnectionOpen()) {
                        connectionStart();
                    }
                    Thread.sleep(JMS_TIMEOUT);
                } 
            } catch (InterruptedException e) {
                LOG.info("Shutting down PO JMS subscriber thread...");
            } catch (Exception e) {
                LOG.error(e);
            } finally {
                if (topicConnection != null) {
                    try {
                        LOG.info("Closing topic...");
                        topicConnection.close();
                    } catch (JMSException e) {
                        LOG.error("Exception closing PO JMS topic.", e);
                    }
                }
            }
            
        }

        private boolean isConnectionOpen() {
            if (topicConnection != null) {
                try {
                    topicConnection.start();
                    return true;
                } catch (javax.jms.IllegalStateException e) {
                    LOG.info(e);
                } catch (Exception e) {
                    LOG.error(e);
                }
                try {
                    topicConnection.close();
                } catch (Exception e) {
                    LOG.error(e);
                }
            }
            return false;
        }

        private void connectionStart() {
            try {
                String user = PaEarPropertyReader.getLookUpServerPoJmsPricipal();
                String pass = PaEarPropertyReader.getLookUpServerPoJmsCredentials();
                String clientId = PaEarPropertyReader.getLookUpServerPoJmsClientId();
                Context context = PoJndiServiceLocator.getContext();
                TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context
                        .lookup("jms/PORemoteConnectionFactory");
                Topic topic = (Topic) context.lookup("jms/topic/POTopic");
                topicConnection = connectionFactory
                        .createTopicConnection(user, pass);
                topicConnection.setClientID(clientId);
                TopicSession topicSession = topicConnection.createTopicSession(false,
                        Session.AUTO_ACKNOWLEDGE);
                TopicSubscriber topicSubscriber = topicSession.createDurableSubscriber(
                        topic, "PAApp");
                topicSubscriber.setMessageListener(new MessageListener() {

                    @Override
                    public void onMessage(Message message) {
                        processMessage(message);
                    }
                });
                topicConnection.start();
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }

    private void processMessage(Message message) {
        ObjectMessage msg = null;
        Long msgId = null;
        PaHibernateUtil.getHibernateHelper().openAndBindSession();
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                SubscriberUpdateMessage updateMessage = (SubscriberUpdateMessage) msg.getObject();
                String identifierName = updateMessage.getId().getIdentifierName();
                try {
                    LOG.info("PoJmsMessageReceiver processMessage() got the Identifier to be processed "
                            + updateMessage.getId().getExtension());
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
                        familyLocal.synchronizeFamilyOrganizationRelationship(IiConverter.
                                convertToLong(updateMessage.getId()));
                    }
                    updateExceptionAuditMessageLog(msgId, "Processed", null, true);
                } catch (PAException paex) {
                    LOG.error("PoJmsMessageReceiver processMessage() method threw an PAException ", paex);
                    updateExceptionAuditMessageLog(msgId, "Failed", " PAException -" + paex.getMessage(), false);
                } catch (Exception e) {
                    LOG.error("PoJmsMessageReceiver processMessage() method threw an Exception ", e);
                    updateExceptionAuditMessageLog(msgId, "Failed", " Generic exception -" + e.getMessage(), false);
                }
            }
        } catch (JMSException e) {
            LOG.error("PoJmsMessageReceiver processMessage() method threw an JMSException ", e);
            updateExceptionAuditMessageLog(msgId, "Failed", " JMSException-" + e.getMessage(), false);
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
