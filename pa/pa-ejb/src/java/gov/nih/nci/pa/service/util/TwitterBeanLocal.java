package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.Tweet;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.enums.TweetStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.IOException;
import java.util.Date;
import java.util.EnumSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Denis G. Krylov
 * 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public class TwitterBeanLocal implements TwitterServiceLocal {

    private static final int SPAM = 226;

    private static final int DUPLICATE_STATUS = 187;

    private static final Logger LOG = Logger.getLogger("Twitter");

    @EJB
    private LookUpTableServiceRemote lookUpTableService;

    /**
     * @param lookUpTableService
     *            the lookUpTableService to set
     */
    public void setLookUpTableService(
            LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void processQueue() throws PAException, IOException {
        if (!isTweetingEnabled() || !isTweetingConfigured()) {
            return;
        }
        LOG.info("Tweeting enabled; kicking off...");
        final Session s = PaHibernateUtil.getCurrentSession();
        Tweet tweet;
        while ((tweet = getNextTweetToTweet()) != null) {
            LOG.info("We have a tweet to send: " + tweet);
            try {
                tweet(tweet);
                markAsProcessed(tweet);
            } catch (TwitterException e) {
                LOG.warn(e.getMessage());
                tweet.setErrors(e.getMessage());
                s.save(tweet);
                // If there is a problem with the tweet's text, mark it as
                // FAILED and move on to the next one.
                // Otherwise, try again later.
                if (ArrayUtils.contains(new int[] {DUPLICATE_STATUS, SPAM},
                        e.getErrorCode())) {
                    LOG.info("Twitter says this tweet is a dupe or spam. Marking as FAILED.");
                    tweet.setStatus(TweetStatusCode.FAILED);
                    s.save(tweet);
                } else {
                    LOG.info("Trying again later.");
                    break;
                }
            }
            s.flush();
        }
    }

    private void markAsProcessed(final Tweet tweet) {
        tweet.setSentDate(new Date());
        tweet.setStatus(TweetStatusCode.SENT);
        PaHibernateUtil.getCurrentSession().save(tweet);
    }

    private void tweet(Tweet tweet) throws PAException, TwitterException {
        final ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(getConsumerKeyAndSecret().getUsername())
                .setOAuthConsumerSecret(
                        getConsumerKeyAndSecret().getDecryptedPassword())
                .setOAuthAccessToken(getAccessTokenAndSecret().getUsername())
                .setOAuthAccessTokenSecret(
                        getAccessTokenAndSecret().getDecryptedPassword())
                .setHttpRetryCount(1)
                .setHttpReadTimeout(
                        Integer.parseInt(lookUpTableService
                                .getPropertyValue("twitter.timeout.read")))
                .setRestBaseURL(
                        lookUpTableService.getPropertyValue("twitter.api.url"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        twitter.updateStatus(tweet.getText());
        LOG.info("Just tweeted: " + tweet.getText());

    }

    private boolean isTweetingConfigured() throws PAException {
        return StringUtils.isNotBlank(getConsumerKeyAndSecret()
                .getEncryptedPassword())
                && StringUtils.isNotBlank(getAccessTokenAndSecret()
                        .getEncryptedPassword());
    }

    private Account getConsumerKeyAndSecret() throws PAException {
        return getTwitterCredentialsAccount(".consumerkey");
    }

    private Account getAccessTokenAndSecret() throws PAException {
        return getTwitterCredentialsAccount(".accesstoken");
    }

    private Account getTwitterCredentialsAccount(String postfix)
            throws PAException {
        return (Account) PaHibernateUtil
                .getCurrentSession()
                .createQuery(
                        "from Account where accountName=:name and externalSystem=:system")
                .setMaxResults(1)
                .setString(
                        "name",
                        lookUpTableService.getPropertyValue("twitter.account")
                                + postfix)
                .setParameter("system", ExternalSystemCode.TWITTER)
                .uniqueResult();
    }

    private Tweet getNextTweetToTweet() {
        Session s = PaHibernateUtil.getCurrentSession();
        Query q = s
                .createQuery(
                        "from Tweet where status in (:statuses) and sentDate is null order by createDate, id")
                .setMaxResults(1)
                .setCacheable(false)
                .setParameterList("statuses",
                        EnumSet.of(TweetStatusCode.PENDING));
        return (Tweet) q.uniqueResult();
    }

    /**
     * @return
     * @throws PAException
     */
    private boolean isTweetingEnabled() throws PAException {
        return Boolean.valueOf(lookUpTableService
                .getPropertyValue("twitter.enabled"));
    }

}
