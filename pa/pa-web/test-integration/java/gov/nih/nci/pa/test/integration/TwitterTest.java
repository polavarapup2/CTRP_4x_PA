package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.domain.Keystore;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.TweetStatusCode;
import gov.nih.nci.pa.test.integration.support.Batch;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.TimeoutException;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Assert;
import org.junit.Test;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Denis G. Krylov
 * 
 */
@Batch(number = 3)
public class TwitterTest extends AbstractTrialStatusTest {

    private static final int TWEET_WAIT_TIME_SECONDS = SystemUtils.IS_OS_LINUX ? 60
            : 30;
    private static final int TWEET_STATUS_RECHECK_WAIT_TIME = 5000;
    private static final int ON_OFF_SWITCH_RECHECK_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 10000
            : 5000;
    private static final int FIRST_RUN_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 45000
            : 5000;
    private static final int SCHEDULING_CHANGES_PICK_UP_TIME = SystemUtils.IS_OS_LINUX ? 20000
            : 10000;
    private static final int CANCER_GOV_TIMEOUT = 60000;

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        log("Setting up for a Twitter test case");

        setPaProperty("twitter.queue.process.schedule", "0/3 * * * * ?");
        setPaProperty("twitter.trials.scan.schedule", "0/2 * * * * ?");
        setPaProperty("twitter.enabled", "false");
        Thread.sleep(SCHEDULING_CHANGES_PICK_UP_TIME);

        new File(SystemUtils.USER_HOME, "pa-keystore.pkcs12").delete();
        setupTwitterAccounts();
        setupGoUsaGovAccount();
        clearUp();

        setPaProperty("twitter.enabled", "true");
        // Wait for PA to pick up changes to scheduling
        log("Wait for PA to pick up changes to scheduling...");
        Thread.sleep(ON_OFF_SWITCH_RECHECK_WAIT_TIME);
        log("Wait over");

    }

    /**
     * @throws SQLException
     */
    private void clearUp() throws SQLException {
        deactivateAllTrials();
        clearTweetQueue();
    }

    private void clearTweetQueue() throws SQLException {
        new QueryRunner().update(connection, "delete from tweets");

    }

    private void setupGoUsaGovAccount() throws SQLException {
        String key = getProperty("go.usa.gov.key");
        String consumerSecretEncrypted = encrypt(key);
        updateAccountPassword("go.usa.gov", consumerSecretEncrypted);

    }

    private void setupTwitterAccounts() throws SQLException {
        String consumerSecret = getProperty("twitter.consumer.secret");
        String consumerSecretEncrypted = encrypt(consumerSecret);
        updateAccountPassword("twitter.default.consumerkey",
                consumerSecretEncrypted);

        String tokenSecret = getProperty("twitter.token.secret");
        String tokenSecretEncrypted = encrypt(tokenSecret);
        updateAccountPassword("twitter.default.accesstoken",
                tokenSecretEncrypted);
    }

    private String getProperty(String name) {
        return System.getProperty(name,
                System.getenv(name.toUpperCase().replace('.', '_')));
    }

    private void updateAccountPassword(String name, String password)
            throws SQLException {
        new QueryRunner().update(connection,
                "update accounts set encrypted_password='" + password
                        + "' where account_name='" + name + "'");

    }

    private String encrypt(String secret) {
        openAndWait("/pa/public-key.action");
        Keystore ks = new Keystore();
        byte[] encryptedBytes = encrypt(secret, ks.getKeypair().getPublic());
        String hex = Hex.encodeHexString(encryptedBytes).toUpperCase();
        return hex;
    }

    /**
     * Encrypt the plain text using public key.
     * 
     * @param text
     *            : original plain text
     * @param key
     *            :The public key
     * @return Encrypted text
     * @throws java.lang.Exception
     */
    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    @Override
    public void tearDown() throws Exception {
        setPaProperty("twitter.enabled", "false");
        super.tearDown();

    }

    @Test
    public void testTweetCompleteTrialDifferentStatuses() throws Exception {
        setUpForFirstRun();

        testTweetCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ENROLLING_BY_INVITATION");
        testTweetCompleteTrial("ABSTRACTION_VERIFIED_NORESPONSE",
                "ENROLLING_BY_INVITATION");
        testTweetCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE", "ACTIVE");
        testTweetCompleteTrial("ABSTRACTION_VERIFIED_NORESPONSE", "ACTIVE");

    }

    @Test
    public void testTweetIndustrialTrialDifferentStatuses() throws Exception {
        setUpForFirstRun();

        testTweetIndustrialTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ENROLLING_BY_INVITATION");
        testTweetIndustrialTrial("ABSTRACTION_VERIFIED_NORESPONSE",
                "ENROLLING_BY_INVITATION");
        testTweetIndustrialTrial("ABSTRACTION_VERIFIED_RESPONSE", "ACTIVE");
        testTweetIndustrialTrial("ABSTRACTION_VERIFIED_NORESPONSE", "ACTIVE");

    }

    @Test
    public void testNoTweetingWhenDisabled() throws Exception {
        setUpForFirstRun();
        // Set up eligible trial.
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", new Modifier() {
                    public void modify(TrialInfo trial) throws Exception {
                        setPaProperty("twitter.enabled", "false");
                    }
                });

        verifyTweetDoesNotGetQueued(trial);

    }

    @Test
    public void testNoTweetsForAmendments() throws Exception {
        setUpForFirstRun();
        // Set up eligible trial.
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", new Modifier() {
                    public void modify(TrialInfo trial) throws Exception {
                        new QueryRunner()
                                .update(connection,
                                        "update study_protocol set submission_number=2, amendment_date=now() where identifier="
                                                + trial.id);
                    }
                });

        verifyTweetDoesNotGetQueued(trial);

    }

    @Test
    public void testNoTweetsForTrialsWithNoNCTId() throws Exception {
        setUpForFirstRun();
        // Set up eligible trial.
        final Modifier modifier = new Modifier() {
            public void modify(TrialInfo trial) throws Exception {
                new QueryRunner().update(connection,
                        "delete from study_site where study_protocol_identifier="
                                + trial.id
                                + " and local_sp_indentifier like 'NCT%'");
            }
        };
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", modifier);
        verifyTweetDoesNotGetQueued(trial);
        trial = setupIndustrialTrial("ABSTRACTION_VERIFIED_RESPONSE", "ACTIVE",
                modifier);
        verifyTweetDoesNotGetQueued(trial);

    }

    @Test
    public void testNoTweetsOnFirstRun() throws Exception {
        final Modifier modifier = new Modifier() {
            public void modify(TrialInfo trial) throws Exception {
            }
        };
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", modifier);
        verifyTweetDoesNotGetQueued(trial);
        waitForCanceledTweetToQueue(trial);

    }

    @Test
    public void testNoTweetsForTrialsBecomeEligibleWhenTweetingDisabled()
            throws Exception {
        log("Setting up for first run...");
        setUpForFirstRun();
        final Modifier modifier = new Modifier() {
            public void modify(TrialInfo trial) throws Exception {
            }
        };
        log("Disabling tweeting...");
        setPaProperty("twitter.enabled", "false");
        Thread.sleep(FIRST_RUN_WAIT_TIME * 2);
        log("Creating a trial...");
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", modifier);
        pause(1000);
        log("Enabling tweeting...");
        setPaProperty("twitter.enabled", "true");
        verifyTweetDoesNotGetQueued(trial);
        waitForCanceledTweetToQueue(trial);

    }

    @Test
    public void testNoTweetsForTrialsNotInCancerGov() throws Exception {
        setUpForFirstRun();
        // Set up eligible trial.
        final Modifier m = new Modifier() {
            public void modify(TrialInfo trial) throws Exception {
                new QueryRunner().update(connection,
                        "delete from study_site where study_protocol_identifier="
                                + trial.id
                                + " and local_sp_indentifier like 'NCT%'");
                addNctIdentifier(trial, "NCT23904823908490238490");
            }
        };
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", m);
        verifyTweetDoesNotGetQueued(trial);
        trial = setupIndustrialTrial("ABSTRACTION_VERIFIED_RESPONSE", "ACTIVE",
                m);
        verifyTweetDoesNotGetQueued(trial);

    }

    @Test
    public void testNoTweetsForTrialsAlreadyTweetedAbout() throws Exception {
        setUpForFirstRun();
        // Set up eligible trial.
        final Modifier m = new Modifier() {
            public void modify(TrialInfo trial) throws Exception {
                createCancelledTweet(trial.id);
            }
        };
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", m);
        verifyTweetDoesNotGetQueued(trial);
        trial = setupIndustrialTrial("ABSTRACTION_VERIFIED_RESPONSE", "ACTIVE",
                m);
        verifyTweetDoesNotGetQueued(trial);

    }

    @Test
    public void testNoTweetsForFlaggedTrials() throws Exception {
        setUpForFirstRun();
        // Set up eligible trial.
        final Modifier m = new Modifier() {
            public void modify(TrialInfo trial) throws Exception {
                new QueryRunner()
                        .update(connection,
                                "INSERT INTO study_protocol_flags (identifier,study_protocol_id,flag_reason,date_flagged,flagging_user_id,deleted) VALUES "
                                        + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),"
                                        + ""
                                        + trial.id
                                        + ",'DO_NOT_SUBMIT_TWEETS'"
                                        + ",now(),"
                                        + trial.csmUserID + ",false)");
            }
        };
        TrialInfo trial = setupCompleteTrial("ABSTRACTION_VERIFIED_RESPONSE",
                "ACTIVE", m);
        verifyTweetDoesNotGetQueued(trial);

        trial = setupIndustrialTrial("ABSTRACTION_VERIFIED_RESPONSE", "ACTIVE",
                m);
        verifyTweetDoesNotGetQueued(trial);

    }

    /**
     * @param trial
     * @throws SQLException
     * @throws InterruptedException
     */
    private void verifyTweetDoesNotGetQueued(TrialInfo trial)
            throws SQLException, InterruptedException {
        try {
            // wait for tweet to show up.
            waitForNonCanceledTweetToQueue(trial);
            Assert.fail("Tweet was not supposed to show up.");
        } catch (TimeoutException e) {
        }
    }

    /**
     * @throws SQLException
     * @throws InterruptedException
     */
    private void setUpForFirstRun() throws SQLException, InterruptedException {
        // This will avoid 'first run'.
        createCancelledTweet(null);
        log("Waiting after creating a cancelled tweet...");
        Thread.sleep(FIRST_RUN_WAIT_TIME);
    }

    @Test
    public void testNoTweetsForCompleteTrialWithWrongStatuses()
            throws Exception {
        setUpForFirstRun();

        for (DocumentWorkflowStatusCode dws : EnumSet.of(
                DocumentWorkflowStatusCode.ABSTRACTED,
                DocumentWorkflowStatusCode.VERIFICATION_PENDING)) {
            for (StudyStatusCode ssc : (EnumSet.of(StudyStatusCode.APPROVED,
                    StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL))) {
                testNoTweetForCompleteTrial(dws.name(), ssc.name());
            }
        }

    }

    @Test
    public void testNoTweetsForIndustrialTrialWithWrongStatuses()
            throws Exception {
        setUpForFirstRun();

        for (DocumentWorkflowStatusCode dws : (EnumSet.of(
                DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED,
                DocumentWorkflowStatusCode.ACCEPTED))) {
            for (RecruitmentStatusCode ssc : (EnumSet.of(
                    RecruitmentStatusCode.APPROVED,
                    RecruitmentStatusCode.IN_REVIEW))) {
                testNoTweetForIndustrialTrial(dws.name(), ssc.name());
            }
        }

    }

    private void testNoTweetForCompleteTrial(String processingStatus,
            String studyStatus) throws Exception {
        // Set up eligible trial.
        TrialInfo trial = setupCompleteTrial(processingStatus, studyStatus);

        verifyTweetDoesNotGetQueued(trial);

    }

    private void testNoTweetForIndustrialTrial(String processingStatus,
            String siteStatus) throws Exception {
        // Set up eligible trial.
        TrialInfo trial = setupIndustrialTrial(processingStatus, siteStatus);
        verifyTweetDoesNotGetQueued(trial);

    }

    private void testTweetIndustrialTrial(String processingStatus,
            String siteStatus) throws Exception {

        setupTweetTemplates();

        // Set up eligible trial.
        TrialInfo trial = setupIndustrialTrial(processingStatus, siteStatus);

        // wait for tweet to show up.
        Number tweetID = waitForNonCanceledTweetToQueue(trial);
        Tweet tweet = waitForTweetToSend(tweetID);
        System.out.println("Tweeted: " + tweet);

        // Verify tweet
        verifyTweet(
                tweet,
                "^A new NCI-sponsored #anuscancer #coloncancer study is now accepting patients\\. http://.+$");

        // Finally, ensure the tweet actually showed up in Twitter. Use Twitter
        // API for this.
        verifyTweetInTwitter("^A new NCI-sponsored #anuscancer #coloncancer study is now accepting patients\\. http(s)?://t\\.co/\\w+$");

    }

    private void testTweetCompleteTrial(String processingStatus,
            String studyStatus) throws Exception {

        setupTweetTemplates();

        // Set up eligible trial.
        TrialInfo trial = setupCompleteTrial(processingStatus, studyStatus);

        // wait for tweet to show up.
        Number tweetID = waitForNonCanceledTweetToQueue(trial);
        Tweet tweet = waitForTweetToSend(tweetID);
        System.out.println("Tweeted: " + tweet);

        // Verify tweet
        verifyTweet(
                tweet,
                "^A new NCI-sponsored #cancer study is now accepting patients\\. http://.+$");

        // Finally, ensure the tweet actually showed up in Twitter. Use Twitter
        // API for this.
        verifyTweetInTwitter("^A new NCI-sponsored #cancer study is now accepting patients\\. http(s)?://t\\.co/\\w+$");

    }

    /**
     * @throws SQLException
     */
    protected void setupTweetTemplates() throws SQLException {
        setPaProperty("twitter.trials.industrial.basetext",
                "A new NCI-sponsored {hashtags} study is now accepting patients. {url}?&"
                        + RandomUtils.nextInt(999));
        setPaProperty("twitter.trials.complete.basetext",
                "A new NCI-sponsored {hashtags} study is now accepting patients. {url}?&"
                        + RandomUtils.nextInt(999));
    }

    /**
     * @param tweet
     * @throws ClientProtocolException
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     */
    private void verifyTweet(Tweet tweet, String tweetTextRegexp)
            throws ClientProtocolException, IOException, InterruptedException,
            SQLException {
        assertEquals("SENT", tweet.status);
        assertNull(tweet.errors);
        assertTrue(Math.abs(tweet.sentDate.getTime() - new Date().getTime()) < DateUtils.MILLIS_IN_SECOND * 30);
        assertTrue(tweet.text.matches(tweetTextRegexp));

        // Verify the shortened link in fact opens.
        String url = tweet.text.replaceFirst("^.*?http://", "http://");
        verifyURLOpens(url);

        // wait a bit and ensure tweet's status is still SENT. Due to a bug,
        // code was trying to submit the same tweet twice.
        Thread.sleep(TWEET_STATUS_RECHECK_WAIT_TIME);
        assertEquals("SENT", getTweetById(tweet.tweetID).status);
    }

    private TrialInfo setupCompleteTrial(String processingStatus,
            String studyStatus) throws Exception {
        return setupCompleteTrial(processingStatus, studyStatus,
                new Modifier() {
                    @Override
                    public void modify(TrialInfo trial) throws Exception {
                    }
                });
    }

    private TrialInfo setupIndustrialTrial(String processingStatus,
            String siteStatus) throws Exception {
        return setupIndustrialTrial(processingStatus, siteStatus,
                new Modifier() {
                    @Override
                    public void modify(TrialInfo trial) throws Exception {
                        new QueryRunner()
                                .update(connection,
                                        "INSERT INTO study_anatomic_site  VALUES "
                                                + "("
                                                + trial.id
                                                + ", (select identifier from anatomic_sites where code='Anus')), "
                                                + "("
                                                + trial.id
                                                + ", (select identifier from anatomic_sites where code='Colon')), "
                                                + "("
                                                + trial.id
                                                + ", (select identifier from anatomic_sites where code='Multiple'))");

                    }
                });
    }

    /**
     * @param studyStatus
     * @param processingStatus
     * @return
     * @throws Exception
     */
    private TrialInfo setupCompleteTrial(String processingStatus,
            String studyStatus, Modifier m) throws Exception {
        final String nctID = "NCT01822756";
        deactivateTrialByNctId(nctID);

        TrialInfo trial = createAcceptedTrial(false);
        new QueryRunner().update(connection,
                "update study_protocol set status_code='INACTIVE' where identifier="
                        + trial.id);
        addDWS(trial, processingStatus);
        addSOS(trial, studyStatus);
        addNctIdentifier(trial, nctID);
        m.modify(trial);
        new QueryRunner().update(connection,
                "update study_protocol set status_code='ACTIVE' where identifier="
                        + trial.id);
        return trial;
    }

    private TrialInfo setupIndustrialTrial(String processingStatus,
            String siteStatus, Modifier m) throws Exception {
        final String nctID = "NCT01822756";
        deactivateTrialByNctId(nctID);

        TrialInfo trial = createAcceptedTrial(true);
        new QueryRunner().update(connection,
                "update study_protocol set status_code='INACTIVE' where identifier="
                        + trial.id);
        addDWS(trial, processingStatus);
        addParticipatingSite(trial, "National Cancer Institute", siteStatus);
        addNctIdentifier(trial, nctID);
        m.modify(trial);
        new QueryRunner().update(connection,
                "update study_protocol set status_code='ACTIVE' where identifier="
                        + trial.id);
        return trial;
    }

    private void verifyTweetInTwitter(String tweetTextRegexp)
            throws SQLException, TwitterException, ClientProtocolException,
            IOException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(
                        getAccountByName("twitter.default.consumerkey").username)
                .setOAuthConsumerSecret(
                        getAccountByName("twitter.default.consumerkey").unencryptedPassword)
                .setOAuthAccessToken(
                        getAccountByName("twitter.default.accesstoken").username)
                .setOAuthAccessTokenSecret(
                        getAccountByName("twitter.default.accesstoken").unencryptedPassword);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        User user = twitter.showUser("ncictrpdevteam");
        Status status = user.getStatus();
        assertTrue(status.getText().matches(tweetTextRegexp));

        String url = status.getText().replaceFirst("^.*?http(s)?://",
                "http$1://");
        verifyURLOpens(url);
    }

    @SuppressWarnings("deprecation")
    private void verifyURLOpens(String url) throws ClientProtocolException,
            IOException {
        System.out.println("Verifying URL: " + url);
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,
                CANCER_GOV_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, CANCER_GOV_TIMEOUT);
        final HttpClient httpClient = new DefaultHttpClient(httpParams);
        HttpGet req = new HttpGet(url);
        req.addHeader("Accept", "text/html");
        HttpResponse response = httpClient.execute(req);
        final int statusCode = response.getStatusLine().getStatusCode();
        LOG.info("Status code: " + statusCode);
        assertEquals(200, statusCode);

    }

    private Tweet waitForTweetToSend(Number tweetID) throws SQLException,
            InterruptedException, TimeoutException {
        final long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < DateUtils.MILLIS_IN_SECOND
                * TWEET_WAIT_TIME_SECONDS) {
            Tweet t = getTweetById(tweetID);
            if (TweetStatusCode.SENT.name().equals(t.status)) {
                return t;
            }
            Thread.sleep(1000);
        }
        throw new TimeoutException("Tweet never sent: " + getTweetById(tweetID));
    }

    @SuppressWarnings("deprecation")
    private Number waitForNonCanceledTweetToQueue(TrialInfo trial)
            throws SQLException, InterruptedException, TimeoutException {
        final String statusList = "'PENDING', 'SENT', 'FAILED'";
        return waitForTweetToQueue(trial, statusList);
    }

    @SuppressWarnings("deprecation")
    private Number waitForCanceledTweetToQueue(TrialInfo trial)
            throws SQLException, InterruptedException, TimeoutException {
        final String statusList = "'CANCELED'";
        return waitForTweetToQueue(trial, statusList);
    }

    /**
     * @param trial
     * @param statusList
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    private Number waitForTweetToQueue(final TrialInfo trial,
            final String statusList) throws SQLException, InterruptedException,
            TimeoutException {
        final QueryRunner runner = new QueryRunner();
        final long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < DateUtils.MILLIS_IN_SECOND
                * TWEET_WAIT_TIME_SECONDS) {
            final Object[] array = runner.query(connection,
                    "select identifier from tweets where study_protocol_identifier="
                            + trial.id + " and status in (" + statusList
                            + ") limit 1", new ArrayHandler());
            if (array != null && array.length > 0) {
                Number tweetID = (Number) array[0];
                log("Tweet showed up for protocol "
                        + ToStringBuilder.reflectionToString(trial) + ": "
                        + tweetID);
                return tweetID;
            }
            Thread.sleep(1000);
        }
        final String msg = "Tweet never showed up for protocol: "
                + ToStringBuilder.reflectionToString(trial);
        log(msg);
        throw new TimeoutException(msg);
    }

    private void createCancelledTweet(Number trialID) throws SQLException {
        String sql = "INSERT INTO tweets (identifier,tweet_text,status,study_protocol_identifier,create_date,sent_date,account_name,errors) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),"
                + "'Cancelled',"
                + "'CANCELED'" + "," + trialID + "," + "now(),null,null,null)";
        new QueryRunner().update(connection, sql);

    }

    private static interface Modifier {
        void modify(TrialInfo trial) throws Exception;
    }

}
