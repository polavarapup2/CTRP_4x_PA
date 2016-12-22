/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.Keystore;
import gov.nih.nci.pa.domain.Tweet;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.enums.TweetStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author dkrylov
 * 
 */
public class TwitterBeanTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    TwitterBeanLocal bean;

    private TwitterMock twitter;

    @Before
    public void init() throws Exception {
        bean = getEjbBean(TwitterBeanLocal.class);
        TestSchema.primeData();
        startTwitterMock();
        setupTwitterAccounts();

    }

    private void setupTwitterAccounts() throws NoSuchFieldException,
            SecurityException, Exception {
        Session s = PaHibernateUtil.getCurrentSession();
        s.createQuery("delete from Account where externalSystem=:sys")
                .setParameter("sys", ExternalSystemCode.TWITTER)
                .executeUpdate();

        File keystoreFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString());
        keystoreFile.deleteOnExit();

        setFinalStatic(Keystore.class.getDeclaredField("KEYSTORE_FILE"),
                keystoreFile);

        Keystore ks = new Keystore();
        Account account = new Account();
        String secret = RandomStringUtils.randomAlphanumeric(8);
        byte[] encryptedBytes = encrypt(secret, ks.getKeypair().getPublic());
        String encryptedPasswordInHexUpper = Hex
                .encodeHexString(encryptedBytes).toUpperCase();
        account.setEncryptedPassword(encryptedPasswordInHexUpper);
        account.setAccountName("twitter.default.consumerkey");
        account.setExternalSystem(ExternalSystemCode.TWITTER);
        account.setUsername("Iwl54IThlIunRdrMr9A5wyr2z");
        s.save(account);

        account = new Account();
        account.setEncryptedPassword(encryptedPasswordInHexUpper);
        account.setAccountName("twitter.default.accesstoken");
        account.setExternalSystem(ExternalSystemCode.TWITTER);
        account.setUsername("3389288896-BlHfFTnlL1nZtLRhwYHftcJaSi5Ag1J76l96ALz");
        s.save(account);
        s.flush();

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
    private static byte[] encrypt(String text, PublicKey key) {
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

    private static void setFinalStatic(Field field, Object newValue)
            throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    @After
    public void done() throws Exception {
        stopTwitterMock();
    }

    @Test
    public void testTweetingDisabled() throws PAException, IOException {
        Session s = PaHibernateUtil.getCurrentSession();
        addPaProperty("twitter.enabled", "false");
        Tweet tweet = createPendingTweet();
        bean.processQueue();

        assertEquals(TweetStatusCode.PENDING,
                ((Tweet) s.get(Tweet.class, tweet.getId())).getStatus());
        assertNull(twitter.getLastTweet());
    }

    @Test
    public void testNetworkFailure() throws PAException, IOException {
        Session s = PaHibernateUtil.getCurrentSession();
        addPaProperty("twitter.enabled", "true");

        Tweet tweet = createPendingTweet();
        twitter.setSimulateNetworkTimeout(true);
        bean.processQueue();
        assertEquals(TweetStatusCode.PENDING,
                ((Tweet) s.get(Tweet.class, tweet.getId())).getStatus());
        assertNull(twitter.getLastTweet());
        assertEquals("Unexpected end of file from server", tweet.getErrors()
                .replaceAll("\\s+", " "));

    }

    @Test
    public void testQueueProcessedInProperOrder() throws PAException,
            IOException {
        Session s = PaHibernateUtil.getCurrentSession();
        addPaProperty("twitter.enabled", "true");

        Tweet t1 = createPendingTweet("Last");

        Tweet t2 = createPendingTweet("First");
        t2.setCreateDate(DateUtils.addDays(new Date(), -2));
        s.save(t2);

        Tweet t3 = createPendingTweet("Middle");
        t3.setCreateDate(DateUtils.addDays(new Date(), -1));
        s.save(t3);
        s.flush();
        s.clear();

        bean.processQueue();
        bean.processQueue();

        assertEquals("First", twitter.getTweets().get(0));
        assertEquals("Middle", twitter.getTweets().get(1));
        assertEquals("Last", twitter.getTweets().get(2));
        assertEquals(3, twitter.getTweets().size());
    }

    @Test
    public void testEnsureOnlyPendingTweetsPickedUp() throws PAException,
            IOException {
        Session s = PaHibernateUtil.getCurrentSession();
        addPaProperty("twitter.enabled", "true");

        Tweet sent = createPendingTweet();
        sent.setStatus(TweetStatusCode.SENT);
        s.save(sent);

        Tweet failed = createPendingTweet();
        failed.setStatus(TweetStatusCode.FAILED);
        s.save(failed);

        Tweet cancelled = createPendingTweet();
        cancelled.setStatus(TweetStatusCode.CANCELED);
        s.save(cancelled);
        s.flush();

        bean.processQueue();
        assertNull(twitter.getLastTweet());
    }

    @Test
    public void testTweeting() throws PAException, IOException {
        Session s = PaHibernateUtil.getCurrentSession();
        addPaProperty("twitter.enabled", "true");

        Tweet successfulTweet = createPendingTweet();
        Tweet dupe = createPendingTweet();
        Tweet spam = createPendingTweet("Click here to purchase!");
        bean.processQueue();
        s.flush();
        s.refresh(successfulTweet);
        assertEquals(TweetStatusCode.SENT, successfulTweet.getStatus());
        assertTrue(DateUtils.isSameDay(new Date(),
                successfulTweet.getSentDate()));
        assertNull(successfulTweet.getErrors());
        assertEquals("This is my #first tweet!", twitter.getLastTweet());

        // Immediately tweeting the same thing should get rejected as a
        // duplicate.
        s.refresh(dupe);
        assertEquals(TweetStatusCode.FAILED, dupe.getStatus());
        assertNull(dupe.getSentDate());
        assertEquals(
                "403:The request is understood, but it has been refused. "
                        + "An accompanying error message will explain why. "
                        + "This code is used when requests are being denied due to update limits"
                        + " (https://support.twitter.com/articles/15364-about-twitter-limits-update-api-dm-and-following). message - Duplicate status code - 187 ",
                dupe.getErrors().replaceAll("\\s+", " "));

        // Spam
        s.refresh(spam);
        assertEquals(TweetStatusCode.FAILED, spam.getStatus());
        assertNull(spam.getSentDate());
        assertEquals(
                "403:The request is understood, but it has been refused. "
                        + "An accompanying error message will explain why. "
                        + "This code is used when requests are being denied due to update limits"
                        + " (https://support.twitter.com/articles/15364-about-twitter-limits-update-api-dm-and-following). message - Spam code - 226 ",
                spam.getErrors().replaceAll("\\s+", " "));

    }

    private Tweet createPendingTweet() {
        final String status = "This is my #first tweet!";
        return createPendingTweet(status);
    }

    /**
     * @param status
     * @return
     * @throws HibernateException
     */
    private Tweet createPendingTweet(final String status)
            throws HibernateException {
        Session s = PaHibernateUtil.getCurrentSession();
        Tweet t = new Tweet();
        t.setAccountName("twitter.default");
        t.setCreateDate(new Date());
        t.setStatus(TweetStatusCode.PENDING);
        t.setStudyProtocol(TestSchema.studyProtocols.get(0));
        t.setText(status);
        s.save(t);
        s.flush();
        return t;
    }

    private void startTwitterMock() throws IOException {
        twitter = new TwitterMock();
    }

    /**
     * 
     */
    private void stopTwitterMock() {
        try {
            twitter.stop(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final class TwitterMock {

        private HttpServer twitter;
        private String lastTweet;
        private List<String> tweets = new ArrayList<>();
        private boolean simulateNetworkTimeout;

        public TwitterMock() throws IOException {
            twitter = HttpServer.create(
                    new InetSocketAddress(TWITTER_MOCK_PORT), 0);
            twitter.createContext("/1.1", new HttpHandler() {
                @SuppressWarnings("deprecation")
                @Override
                public void handle(HttpExchange t) throws IOException {
                    try {
                        String uri = t.getRequestURI().toString();
                        if ("/1.1/statuses/update.json".equals(uri)
                                && "POST".equalsIgnoreCase(t.getRequestMethod())) {
                            String post = IOUtils.toString(t.getRequestBody());
                            String newStatus = URLDecoder.decode(post
                                    .replaceFirst("status=", "").replaceFirst(
                                            "&.*$", ""));
                            System.out
                                    .println("Twitter Mock received a POST to update status: "
                                            + newStatus);
                            if (simulateNetworkTimeout) {
                                OutputStream os = t.getResponseBody();
                                os.write(32);
                                Thread.sleep(10000);
                            } else if (lastTweet != null
                                    && lastTweet.equalsIgnoreCase(newStatus)) {
                                buildDuplicateStatusResponse(t, newStatus);
                            } else if (newStatus
                                    .equalsIgnoreCase("Click here to purchase!")) {
                                buildSpamResponse(t, newStatus);
                            } else {
                                buildSuccessfulStatusUpdateResponse(t,
                                        newStatus);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }

                }

                private void buildDuplicateStatusResponse(HttpExchange t,
                        String newStatus) throws IOException, JSONException {
                    final String msg = "Duplicate status";
                    final int code = 187;
                    buildErrorResponse(t, msg, code);

                }

                private void buildSpamResponse(HttpExchange t, String newStatus)
                        throws IOException, JSONException {
                    final String msg = "Spam";
                    final int code = 226;
                    buildErrorResponse(t, msg, code);

                }

                /**
                 * @param t
                 * @param msg
                 * @param code
                 * @throws IOException
                 * @throws JSONException
                 */
                private void buildErrorResponse(HttpExchange t,
                        final String msg, final int code) throws IOException,
                        JSONException {
                    t.sendResponseHeaders(403, 0);
                    // Build a response JSON
                    JSONObject root = new JSONObject();
                    JSONArray errors = new JSONArray();
                    JSONObject error = new JSONObject();

                    error.put("message", msg);

                    error.put("code", code);
                    errors.put(error);
                    root.put("errors", errors);

                    OutputStream os = t.getResponseBody();
                    os.write(root.toString().getBytes());
                    os.flush();
                    os.close();
                }

                /**
                 * @param t
                 * @param newStatus
                 * @throws IOException
                 * @throws JSONException
                 */
                private void buildSuccessfulStatusUpdateResponse(
                        HttpExchange t, String newStatus) throws IOException,
                        JSONException {
                    t.sendResponseHeaders(200, 0);
                    // Build a response JSON
                    JSONObject root = new JSONObject();
                    root.put("created_at", new Date());
                    final long tweetID = RandomUtils.nextLong();
                    root.put("id_str", tweetID + "");
                    root.put("text", newStatus);
                    root.put("id", tweetID);

                    OutputStream os = t.getResponseBody();
                    os.write(root.toString().getBytes());
                    os.flush();
                    os.close();

                    lastTweet = newStatus;
                    tweets.add(newStatus);
                }
            });
            twitter.setExecutor(null); // creates a default executor
            twitter.start();

        }

        public void stop(int i) {
            twitter.stop(i);
        }

        /**
         * @return the lastTweet
         */
        public String getLastTweet() {
            return lastTweet;
        }

        /**
         * @param simulateNetworkTimeout
         *            the simulateNetworkTimeout to set
         */
        public void setSimulateNetworkTimeout(boolean simulateNetworkTimeout) {
            this.simulateNetworkTimeout = simulateNetworkTimeout;
        }

        /**
         * @return the tweets
         */
        public List<String> getTweets() {
            return tweets;
        }

    }

}
