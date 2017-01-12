/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.Keystore;
import gov.nih.nci.pa.enums.ExternalSystemCode;
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
import java.net.URL;
import java.net.URLDecoder;
import java.security.PublicKey;
import java.util.UUID;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.hibernate.Session;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author dkrylov
 * 
 */
public class URLShortenerBeanTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    URLShortenerBeanLocal bean;

    private GoUsaGovMock mock;

    @Before
    public void init() throws Exception {
        bean = getEjbBean(URLShortenerBeanLocal.class);
        TestSchema.primeData();
        startGoUsaGovMock();
        setupGoUsaGovMockAccounts();

    }

    @Test
    public void shortenURL() throws PAException, IOException {
        URL url = new URL(
                "http://clinicaltrials.gov/show/NCT01916083?resultsxml=true");
        URL shortened = bean.shorten(url);

        assertTrue(shortened.toString().matches("^http://go.usa.gov/\\w+$"));
    }

    @Test
    public void checkTimeout() throws PAException, IOException {
        URL url = new URL(
                "http://clinicaltrials.gov/show/NCT01916083?resultsxml=true");
        mock.setSimulateNetworkTimeout(true);
        assertNull(bean.shorten(url));

    }

    private void setupGoUsaGovMockAccounts() throws NoSuchFieldException,
            SecurityException, Exception {
        Session s = PaHibernateUtil.getCurrentSession();
        s.createQuery("delete from Account where externalSystem=:sys")
                .setParameter("sys", ExternalSystemCode.GO_USA_GOV)
                .executeUpdate();

        File keystoreFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString());
        keystoreFile.deleteOnExit();

        setFinalStatic(Keystore.class.getDeclaredField("KEYSTORE_FILE"),
                keystoreFile);

        Keystore ks = new Keystore();
        Account account = new Account();
        String secret = "2904820398423908";
        byte[] encryptedBytes = encrypt(secret, ks.getKeypair().getPublic());
        String encryptedPasswordInHexUpper = Hex
                .encodeHexString(encryptedBytes).toUpperCase();
        account.setEncryptedPassword(encryptedPasswordInHexUpper);
        account.setAccountName("go.usa.gov");
        account.setExternalSystem(ExternalSystemCode.GO_USA_GOV);
        account.setUsername("nci-ctrp");
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
        stopGoUsaGovMock();
    }

    private void startGoUsaGovMock() throws IOException {
        mock = new GoUsaGovMock();
    }

    /**
     * 
     */
    private void stopGoUsaGovMock() {
        try {
            mock.stop(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final class GoUsaGovMock {

        private HttpServer server;
        private String lastShortURL;
        private boolean simulateNetworkTimeout;

        public GoUsaGovMock() throws IOException {
            server = HttpServer.create(new InetSocketAddress(
                    GO_USA_GOV_MOCK_PORT), 0);
            server.createContext("/api", new HttpHandler() {
                @SuppressWarnings("deprecation")
                @Override
                public void handle(HttpExchange t) throws IOException {
                    try {
                        String uri = t.getRequestURI().toString();
                        if (uri.startsWith("/api/shorten.json")
                                && "GET".equalsIgnoreCase(t.getRequestMethod())) {
                            System.out
                                    .println("Go.Usa.Gov Mock received a shorten URL request: "
                                            + uri);
                            String login = URLDecoder.decode(uri.replaceFirst(
                                    "^.*?login=", "")
                                    .replaceFirst("\\&.+$", ""));
                            String apiKey = uri.replaceFirst("^.*?apiKey=", "")
                                    .replaceFirst("&longUrl.*$", "");
                            String url = URLDecoder.decode(uri.replaceFirst(
                                    "^.*?longUrl=", "").replaceFirst(
                                    "&longUrl.*$", ""));
                            if (!(login.equals("nci-ctrp") && apiKey
                                    .equals("2904820398423908"))) {
                                t.sendResponseHeaders(401, 0);
                                OutputStream os = t.getResponseBody();
                                os.close();
                            } else if (simulateNetworkTimeout) {
                                OutputStream os = t.getResponseBody();
                                os.write(32);
                                Thread.sleep(5000);
                                os.close();
                            } else {
                                buildSuccessfulStatusUpdateResponse(t, url);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }

                }

                /**
                 * @param t
                 * @param newStatus
                 * @throws IOException
                 * @throws JSONException
                 */
                private void buildSuccessfulStatusUpdateResponse(
                        HttpExchange t, String url) throws IOException,
                        JSONException {
                    String shortURL = "http://go.usa.gov/"
                            + RandomStringUtils.randomAlphanumeric(5);
                    t.sendResponseHeaders(200, 0);
                    OutputStream os = t.getResponseBody();
                    os.write(("{\"response\":{\"0\":[{\"status_code\":\"200\",\"status_txt\":\"OK\"}],\"data\":{\"entry\":[{\"short_url\":\""
                            + shortURL + "\",\"long_url\":\"" + url + "\"}]}}}")
                            .toString().getBytes());
                    os.flush();
                    os.close();

                    lastShortURL = shortURL;
                    System.out.println("Long URL: " + url);
                    System.out.println("Short URL: " + shortURL);

                }
            });
            server.setExecutor(null); // creates a default executor
            server.start();

        }

        public void stop(int i) {
            server.stop(i);
        }

        /**
         * @return the lastTweet
         */
        public String getLastShortURL() {
            return lastShortURL;
        }

        /**
         * @param simulateNetworkTimeout
         *            the simulateNetworkTimeout to set
         */
        public void setSimulateNetworkTimeout(boolean simulateNetworkTimeout) {
            this.simulateNetworkTimeout = simulateNetworkTimeout;
        }

    }

}
