/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.Tweet;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.TweetStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author dkrylov
 * 
 */
public class TrialTweetingBeanTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    TrialTweetingBean bean;

    private CancerGov mock;

    private StudyProtocolQueryDTO protocolDTO = new StudyProtocolQueryDTO();

    @SuppressWarnings("rawtypes")
    @Before
    public void init() throws Exception {
        bean = getEjbBean(TrialTweetingBean.class);
        TestSchema.primeData();
        startCancerGovMock();

        URLShortenerServiceLocal urlShort = mock(URLShortenerServiceLocal.class);
        when(urlShort.shorten(any(URL.class))).thenReturn(
                new URL("http://1.usa.gov/1xVvdmH"));
        bean.setUrlShortenerService(urlShort);

        ProtocolQueryServiceLocal queryBean = mock(ProtocolQueryServiceLocal.class);
        when(
                queryBean.getStudyProtocolByCriteria(
                        any(StudyProtocolQueryCriteria.class),
                        (ProtocolQueryPerformanceHints[]) anyVararg())).then(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation)
                            throws Throwable {
                        List<StudyProtocolQueryDTO> list = new ArrayList<>();
                        StudyProtocolQueryCriteria criteria = (StudyProtocolQueryCriteria) invocation
                                .getArguments()[0];
                        if ("n".equalsIgnoreCase(criteria.getTrialCategory())) {
                            list.add(protocolDTO);
                        }
                        return list;
                    }
                });
        bean.setProtocolQueryService(queryBean);

        protocolDTO
                .setStudyProtocolId(TestSchema.studyProtocols.get(0).getId());
        protocolDTO.setProprietaryTrial(false);
        protocolDTO.setNctIdentifier("NCT123456789");

    }

    @Test
    public void tweetingDisabled() throws PAException, IOException {
        addPaProperty("twitter.enabled", "false");
        bean.processTrials();
        assertEquals(
                ((Number) PaHibernateUtil.getCurrentSession()
                        .createCriteria(Tweet.class)
                        .setProjection(Projections.rowCount()).uniqueResult())
                        .intValue(),
                0);
    }

    @Test
    public void firstRun() throws PAException, IOException {
        deleteAllTweetsAndRemoveBodySites();
        bean.processTrials();
        assertEquals(
                ((Number) PaHibernateUtil.getCurrentSession()
                        .createCriteria(Tweet.class)
                        .setProjection(Projections.rowCount()).uniqueResult())
                        .intValue(),
                1);

        Tweet t = (Tweet) PaHibernateUtil.getCurrentSession()
                .createCriteria(Tweet.class).setMaxResults(1).uniqueResult();
        assertEquals(TestSchema.studyProtocols.get(0).getId(), t
                .getStudyProtocol().getId());
        assertEquals(TweetStatusCode.CANCELED, t.getStatus());
    }

    @Test
    public void enablingAfterBeingDisabledForAWhile() throws PAException,
            IOException {

        deleteAllTweetsAndRemoveBodySites();
        createCancelledTweet();

        addPaProperty("twitter.enabled", "false");
        bean.processTrials();
        addPaProperty("twitter.enabled", "true");
        bean.processTrials();

        assertEquals(
                ((Number) PaHibernateUtil.getCurrentSession()
                        .createCriteria(Tweet.class)
                        .setProjection(Projections.rowCount()).uniqueResult())
                        .intValue(),
                2);

        Tweet t = (Tweet) PaHibernateUtil
                .getCurrentSession()
                .createCriteria(Tweet.class)
                .add(Restrictions.eq("studyProtocol",
                        (TestSchema.studyProtocols.get(0)))).setMaxResults(1)
                .uniqueResult();
        assertEquals(TestSchema.studyProtocols.get(0).getId(), t
                .getStudyProtocol().getId());
        assertEquals(TweetStatusCode.CANCELED, t.getStatus());
    }

    /**
     * @throws HibernateException
     */
    private void deleteAllTweetsAndRemoveBodySites() throws HibernateException {
        final Session s = PaHibernateUtil.getCurrentSession();
        s.createQuery("delete from Tweet").executeUpdate();

        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class,
                TestSchema.studyProtocols.get(0).getId());
        sp.getSummary4AnatomicSites().clear();
        s.save(sp);
        s.flush();
    }

    @Test
    public void doNotTweetIfNotInCancerGov() throws PAException, IOException {
        deleteAllTweetsAndRemoveBodySites();
        createCancelledTweet();

        mock.setRespondWithFailureOnNextCall(true);
        bean.processTrials();
        Tweet newTweet = (Tweet) PaHibernateUtil
                .getCurrentSession()
                .createCriteria(Tweet.class)
                .add(Restrictions.eq("studyProtocol",
                        (TestSchema.studyProtocols.get(0)))).setMaxResults(1)
                .uniqueResult();
        assertNull(newTweet);
    }

    @Test
    public void noTweetsIfUnableToShortenURL() throws PAException, IOException {
        deleteAllTweetsAndRemoveBodySites();
        createCancelledTweet();

        URLShortenerServiceLocal urlShort = mock(URLShortenerServiceLocal.class);
        when(urlShort.shorten(any(URL.class))).thenReturn(null);
        bean.setUrlShortenerService(urlShort);

        bean.processTrials();
        Tweet newTweet = (Tweet) PaHibernateUtil
                .getCurrentSession()
                .createCriteria(Tweet.class)
                .add(Restrictions.eq("studyProtocol",
                        (TestSchema.studyProtocols.get(0)))).setMaxResults(1)
                .uniqueResult();
        assertNull(newTweet);
    }

    @Test
    public void tweetProtocolNoBodySites() throws PAException, IOException {
        deleteAllTweetsAndRemoveBodySites();
        createCancelledTweet();

        bean.processTrials();

        Tweet t = (Tweet) PaHibernateUtil
                .getCurrentSession()
                .createCriteria(Tweet.class)
                .add(Restrictions.eq("studyProtocol",
                        (TestSchema.studyProtocols.get(0)))).setMaxResults(1)
                .uniqueResult();
        assertNotNull(t);
        assertNull(t.getSentDate());
        assertTrue(DateUtils.isSameDay(t.getCreateDate(), new Date()));
        assertEquals(TweetStatusCode.PENDING, t.getStatus());
        assertEquals(
                "A new NCI-sponsored #cancer study is now accepting patients. http://1.usa.gov/1xVvdmH",
                t.getText());
        assertEquals("twitter.default", t.getAccountName());
    }

    @Test
    public void tweetProtocolSingleBodySite() throws PAException, IOException {

        final Session s = PaHibernateUtil.getCurrentSession();

        deleteAllTweetsAndRemoveBodySites();
        createCancelledTweet();

        AnatomicSite site = TestSchema.createAnatomicSiteObj("Anus",
                "anuscancer,analcancer");
        TestSchema.addUpdObject(site);
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class,
                TestSchema.studyProtocols.get(0).getId());
        sp.getSummary4AnatomicSites().add(site);
        s.save(sp);
        s.flush();

        bean.processTrials();

        Tweet t = (Tweet) s
                .createCriteria(Tweet.class)
                .add(Restrictions.eq("studyProtocol",
                        (TestSchema.studyProtocols.get(0)))).uniqueResult();
        assertNotNull(t);
        assertNull(t.getSentDate());
        assertTrue(DateUtils.isSameDay(t.getCreateDate(), new Date()));
        assertEquals(TweetStatusCode.PENDING, t.getStatus());
        assertEquals(
                "A new NCI-sponsored #anuscancer #analcancer study is now accepting patients. http://1.usa.gov/1xVvdmH",
                t.getText());
        assertEquals("twitter.default", t.getAccountName());
    }

    @Test
    public void tweetProtocolSingleManyBodySitesAnd140Limit()
            throws PAException, IOException {

        final Session s = PaHibernateUtil.getCurrentSession();

        deleteAllTweetsAndRemoveBodySites();
        createCancelledTweet();

        AnatomicSite site1 = TestSchema.createAnatomicSiteObj(
                "Non-Hodgkin's Lymphoma",
                " nonhodgkinslymphoma, nonhodgkins, nonhodgkin"); // nonhodgkin
                                                                  // is
                                                                  // not going
                                                                  // to
                                                                  // fit into
                                                                  // the
                                                                  // tweet
        TestSchema.addUpdObject(site1);
        AnatomicSite site2 = TestSchema.createAnatomicSiteObj("Anus",
                "anuscancer ,analcancer, a3");
        TestSchema.addUpdObject(site2);

        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class,
                TestSchema.studyProtocols.get(0).getId());
        sp.getSummary4AnatomicSites().add(site1);
        sp.getSummary4AnatomicSites().add(site2);
        s.save(sp);
        s.flush();

        bean.processTrials();

        Tweet t = (Tweet) s
                .createCriteria(Tweet.class)
                .add(Restrictions.eq("studyProtocol",
                        (TestSchema.studyProtocols.get(0)))).uniqueResult();
        assertNotNull(t);
        assertNull(t.getSentDate());
        assertTrue(DateUtils.isSameDay(t.getCreateDate(), new Date()));
        assertEquals(TweetStatusCode.PENDING, t.getStatus());
        assertEquals(
                "A new NCI-sponsored #anuscancer #nonhodgkinslymphoma #analcancer #nonhodgkins #a3 study "
                        + "is now accepting patients. http://1.usa.gov/1xVvdmH",
                t.getText());
        assertEquals("twitter.default", t.getAccountName());
    }

    /**
     * @throws HibernateException
     */
    private Tweet createCancelledTweet() throws HibernateException {
        Tweet t = new Tweet();
        t.setText(StringUtils.EMPTY);
        t.setStatus(TweetStatusCode.CANCELED);
        t.setStudyProtocol(TestSchema.createStudyProtocolObj());
        t.setCreateDate(new Date());
        PaHibernateUtil.getCurrentSession().save(t);
        return t;
    }

    @After
    public void done() throws Exception {
        stopCancerGovMock();
    }

    private void startCancerGovMock() throws IOException {
        mock = new CancerGov();
    }

    /**
     * 
     */
    private void stopCancerGovMock() {
        try {
            mock.stop(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final class CancerGov {

        private HttpServer server;
        private boolean respondWithFailureOnNextCall;

        public CancerGov() throws IOException {
            server = HttpServer.create(new InetSocketAddress(
                    CANCER_GOV_MOCK_PORT), 0);
            server.createContext("/clinicaltrials", new HttpHandler() {
                @SuppressWarnings("deprecation")
                @Override
                public void handle(HttpExchange t) throws IOException {
                    try {
                        String uri = t.getRequestURI().toString();
                        System.out
                                .println("Cancer.gov Mock received a request: "
                                        + uri);
                        if ((uri.startsWith("/clinicaltrials/NCT") && "GET"
                                .equalsIgnoreCase(t.getRequestMethod()))
                                && !respondWithFailureOnNextCall) {
                            buildSuccessfulStatusUpdateResponse(t);
                        } else {
                            t.sendResponseHeaders(404, 0);
                            OutputStream os = t.getResponseBody();
                            os.write("not found".getBytes());
                            os.flush();
                            os.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    } finally {
                        respondWithFailureOnNextCall = false;
                    }

                }

                /**
                 * @param t
                 * @param newStatus
                 * @throws IOException
                 * @throws JSONException
                 */
                private void buildSuccessfulStatusUpdateResponse(HttpExchange t)
                        throws IOException, JSONException {

                    t.sendResponseHeaders(200, 0);
                    OutputStream os = t.getResponseBody();
                    os.write(" ".getBytes());
                    os.flush();
                    os.close();

                }
            });
            server.setExecutor(null); // creates a default executor
            server.start();

        }

        public void stop(int i) {
            server.stop(i);
        }

        /**
         * @param respondWithFailureOnNextCall
         *            the respondWithFailureOnNextCall to set
         */
        public void setRespondWithFailureOnNextCall(
                boolean respondWithFailureOnNextCall) {
            this.respondWithFailureOnNextCall = respondWithFailureOnNextCall;
        }

    }

}
