/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.system.ApplicationSessionFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.util.ReflectionUtils;

import com.dumbster.smtp.SimpleSmtpServer;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author dkrylov
 * 
 */
public class AbstractEjbTestCase extends AbstractHibernateTestCase {

    public static final int CTGOV_API_MOCK_PORT = randomPort();
    public static final int SMTP_PORT = randomPort();
    public static final int TWITTER_MOCK_PORT = randomPort();
    public static final int GO_USA_GOV_MOCK_PORT = randomPort();
    public static final int CANCER_GOV_MOCK_PORT = randomPort();

    private EjbFactory ejbFactory;

    private ServiceLocator backupPaServiceLocator;
    private PoServiceLocator backupPoServiceLocator;
    private CSMUserUtil backupCsmService;
    private String backupUsername;

    private HttpServer server;

    protected SimpleSmtpServer smtp;

    @Before
    public final void setupEjbEnvironment() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, IOException,
            IllegalStateException, NamingException, URISyntaxException,
            CSConfigurationException, NoSuchFieldException, SecurityException {
        ejbFactory = new EjbFactory();
        configureJndi();
        configurePoMock();
        configureCSM();
        createCsmUsers();
        populatePaProperties();
        populateRegulatoryAuthorities();
        setCurrentUser();
        startNctApiMock();
        startSMTP();
        setUpPaEarProperties();
        CacheUtils.getPoResearchOrganizationByEntityIdentifierCache().removeAll();

    }

    private void startSMTP() {
        SimpleSmtpServer server = new SimpleSmtpServer(SMTP_PORT);
        Thread t = new Thread(server);
        t.start();

        // Block until the server socket is created
        synchronized (server) {
            try {
                server.wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        smtp = server;

    }

    private void setUpPaEarProperties() throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        File tempDir;
        tempDir = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        tempDir.mkdirs();
        Field field = PaEarPropertyReader.class.getDeclaredField("PROPS");
        field.setAccessible(true);
        Properties earProps = (Properties) field.get(null);
        earProps.setProperty("doc.upload.path", tempDir.getAbsolutePath());
    }

    private void startNctApiMock() throws IOException {
        server = HttpServer.create(new InetSocketAddress(CTGOV_API_MOCK_PORT),
                0);
        server.createContext("/ctgov", new HttpHandler() {
            @Override
            public void handle(HttpExchange t) throws IOException {
                try {
                    String uri = t.getRequestURI().toString();
                    String nctid = uri.substring(uri.lastIndexOf("?") + 1);
                    if ("NCT404".equalsIgnoreCase(nctid)) {
                        t.sendResponseHeaders(404, 0);
                    } else if ("NCT500".equalsIgnoreCase(nctid)) {
                        t.sendResponseHeaders(500, 0);
                    } else {
                        String xml = IOUtils.toString(this.getClass()
                                .getResourceAsStream("/" + nctid + ".xml"));
                        t.sendResponseHeaders(200, 0);
                        OutputStream os = t.getResponseBody();
                        os.write(xml.getBytes("UTF-8"));
                        os.flush();
                        os.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }
        });
        server.setExecutor(null); // creates a default executor
        server.start();

    }

    private void populateRegulatoryAuthorities() {
        final Session s = PaHibernateUtil.getCurrentSession();
        Country c = new Country();
        c.setAlpha2("US");
        c.setAlpha3("USA");
        c.setName("United States");
        s.save(c);

        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setAuthorityName("Food and Drug Administration");
        ra.setCountry(c);
        s.save(ra);

        RegulatoryAuthority iIDMC = new RegulatoryAuthority();
        iIDMC.setAuthorityName("IDMC");
        iIDMC.setCountry(c);
        s.save(iIDMC);

        s.flush();
    }

    /**
     * @throws HibernateException
     * @throws IOException
     */
    private void populatePaProperties() throws HibernateException, IOException {
        addPaProperty(
                "rss.leadOrgs",
                "American College of Surgeons Oncology Trials Group,Cancer and Leukemia Group B,Eastern Cooperative Oncology Group,Gynecologic Oncology Group,National Cancer Institute of Canada Clinical Trials Group,North Central Cancer Treatment Group,National Surgical Adjuvant Breast and Bowel Project,Radiation Therapy Oncology Group,Southwest Oncology Group,Children's Oncology Group,American College of Radiology Imaging Network,European Organization for Research and Treatment of Cancer,Alliance for Clinical Trials in Oncology,ECOG-ACRIN Cancer Research Group,Imaging and Radiation Oncology Core Group,NCIC Clinical Trials Group,NRG Oncology,SWOG");
        addPaProperty("smtp", "localhost");
        addPaProperty("smtp.port", SMTP_PORT + "");
        addPaProperty("smtp.auth.username", "");
        addPaProperty("log.email.address", "log@example.com");
        addPaProperty("fromaddress", "from@example.com");

        addPaProperty("ctgov.api.getByNct", "http://localhost:"
                + CTGOV_API_MOCK_PORT + "/ctgov?${nctid}");
        addPaProperty("ctgov.sync.import_persons", "true");
        addPaProperty("ctgov.sync.import_orgs", "true");
        addPaProperty("ctgov.sync.fields_of_interest",
                "studyProtocol.scientificDescription;eligibilityCriteria");
        addPaProperty(
                "ctgov.sync.fields_of_interest.key_to_label_mapping",
                "studyProtocol.scientificDescription=Detailed Description\r\neligibilityCriteria=Eligibility Criteria");
        addPaProperty("ctgov.sync.fields_of_interest.key_to_sect_mapping",
                "studyProtocol.scientificDescription=Scientific\r\neligibilityCriteria=Admin");
        addPaProperty("proprietarytrial.register.subject", "");
        addPaProperty("proprietarytrial.register.body", "");
        addPaProperty("dcp.identifier.row", "");
        addPaProperty("ctep.identifier.row", "");
        addPaProperty("other.identifiers.row", "");
        addPaProperty("ctep.ccr.learOrgIds", "NCICCR");
        addPaProperty("allowed.regulatory.authorities.no.country.name", "IDMC");
        addPaProperty("ctgov.upload.error.regex",
                "Study Number \\d+ \\(([^\\)]*)\\)\\s*ERROR:([^\\n]*)");
        addPaProperty("imap.server", ServerSetupTest.IMAP.getLocalHostAddress());
        addPaProperty("imap.port",
                String.valueOf(ServerSetupTest.IMAPS.getPort()));
        addPaProperty("imap.folder", "INBOX");
        addPaProperty("ctgov.upload.errorEmail.subject",
                "PRS Protocol Upload Notification");
        addPaProperty("ctgov.upload.errorEmail.account",
                "CTGovUploadErrorEmailAccount");
        addPaProperty(
                "closed_industrial_trial_statuses",
                "CLOSED_TO_ACCRUAL, CLOSED_TO_ACCRUAL_AND_INTERVENTION, ADMINISTRATIVELY_COMPLETE, COMPLETE");

        String statusRulesStr = IOUtils.toString(getClass().getClassLoader()
                .getResourceAsStream("statusvalidations.json"));
        addPaProperty("status.rules", statusRulesStr);

        addPaProperty("twitter.enabled", "true");
        addPaProperty("twitter.api.url", "http://localhost:"
                + TWITTER_MOCK_PORT + "/1.1/");
        addPaProperty("twitter.account", "twitter.default");
        addPaProperty("twitter.timeout.read", "2000");

        addPaProperty(
                "go.usa.gov.api.shorten.url",
                "http://localhost:"
                        + GO_USA_GOV_MOCK_PORT
                        + "/api/shorten.json?login={user_name}&apiKey={api_key}&longUrl={longUrl}");
        addPaProperty("go.usa.gov.api.timeout", "3000");
        addPaProperty("go.usa.gov.api.shorten.json.path",
                "response.data.entry.[0].short_url");

        addPaProperty("twitter.trials.complete.basetext",
                "A new NCI-sponsored {hashtags} study is now accepting patients. {url}");
        addPaProperty("twitter.trials.industrial.basetext",
                "A new NCI-sponsored {hashtags} study is now accepting patients. {url}");
        addPaProperty("twitter.trials.url", "http://localhost:"
                + CANCER_GOV_MOCK_PORT + "/clinicaltrials/{nctid}");
        
        addPaProperty("abstraction.script.mailTo", "sample@example.com");
        addPaProperty("participating.site.not.closed.email.subject", "email subject");
        addPaProperty("participating.site.not.closed.email.body", "email body");
        
        addPaProperty("ctrp.job.failure.body", "Dear Sir or Madam,<br /><br />This is to notify that CTRP nightly job <strong>${jobName}</strong> "
        		+ "failed its processing at <strong>${timeStamp}</strong> with the following error message:" +
        		"<br /><br /><pre>${stackTrace}</pre>"
        		+ "<br /><br />Please consult the server logs for more details.");
        
        addPaProperty("ctrp.job.failure.subject", "CTRP nightly job failure: ${jobName}");
        addPaProperty("ctrp.job.failure.recipients", "to@example.com");        
        
    }

    protected void addPaProperty(String name, String value) {
        final Session s = PaHibernateUtil.getCurrentSession();
        s.createQuery("delete from PAProperties where name='" + name + "'")
                .executeUpdate();
        PAProperties prop = new PAProperties();
        prop.setName(name);
        prop.setValue(value);
        s.save(prop);
        s.flush();
    }

    /**
     * 
     */
    private void setCurrentUser() {
        this.backupUsername = UsernameHolder.getUser();
        UsernameHolder
                .setUserCaseSensitive("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");
    }

    @After
    public final void shutdown() {
        stopNctMockServer();
        stopSMTP();
        CSMUserService.setInstance(backupCsmService);
        PaRegistry.getInstance().setServiceLocator(backupPaServiceLocator);
        PoRegistry.getInstance().setPoServiceLocator(backupPoServiceLocator);
        UsernameHolder.setUserCaseSensitive(UsernameHolder.ANONYMOUS_USERNAME
                .equals(backupUsername) ? null : backupUsername);
    }

    /**
     * 
     */
    protected void stopNctMockServer() {
        try {
            server.stop(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void stopSMTP() {
        try {
            smtp.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCsmUsers() throws CSConfigurationException {
        SessionFactory sf = ApplicationSessionFactory.getSessionFactory("pa");
        Session s = sf.openSession();

        Application app = new Application();
        app.setApplicationName("pa");
        app.setUpdateDate(new Date());
        s.saveOrUpdate(app);

        createCsmGroup(app, s, "Abstractor");
        createCsmGroup(app, s, "Submitter");
        createCsmGroup(app, s, "SuAbstractor");
        createCsmGroup(app, s, "ScientificAbstractor");
        createCsmGroup(app, s, "AdminAbstractor");

        createCsmUser(s, "Super", "Abstractor",
                "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");

        s.flush();
        s.close();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void createCsmUser(Session s, String first, String last,
            String login) {
        User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        user.setLoginName(login);
        user.setGroups(new HashSet(s.createCriteria(Group.class).list()));
        user.setUpdateDate(new Date());
        s.saveOrUpdate(user);
    }

    private void createCsmGroup(Application app, Session s, String name) {
        Group g = new Group();
        g.setApplication(app);
        g.setGroupName(name);
        s.saveOrUpdate(g);
    }

    private void configureCSM() throws IOException, URISyntaxException {

        this.backupCsmService = CSMUserService.getInstance();

        CSMUserService.setInstance(new CSMUserService());

        File csmConf = (new File(getClass().getResource(
                "/ApplicationSecurityConfig.xml").toURI()));
        System.setProperty("gov.nih.nci.security.configFile",
                csmConf.getAbsolutePath());
        String hibernateConfig = IOUtils.toString(getClass()
                .getResourceAsStream("/csm.hibernate.cfg.xml"));
        File hibernateConfigFile = new File("csm.hibernate.cfg.xml");
        FileUtils.writeStringToFile(hibernateConfigFile, hibernateConfig);
        hibernateConfigFile.deleteOnExit();

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final <T> T getEjbBean(Class<T> clazz) {
        for (Object ejb : ejbFactory.getEjbs()) {
            if (ejb.getClass().equals(clazz)) {
                return (T) ejb;
            }
        }
        throw new RuntimeException("EJB of type " + clazz + " is not found");
    }

    private void configurePoMock() {
        this.backupPoServiceLocator = PoRegistry.getInstance()
                .getPoServiceLocator();

        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());

        MockOrganizationEntityService.reset(1, true);
        MockPersonEntityService.reset();

    }

    private void configureJndi() throws IllegalStateException, NamingException {

        this.backupPaServiceLocator = PaRegistry.getInstance()
                .getServiceLocator();

        JndiServiceLocator loc = new JndiServiceLocator();
        Field ctx = ReflectionUtils.findField(JndiServiceLocator.class, "ctx");
        ReflectionUtils.makeAccessible(ctx);
        ReflectionUtils.setField(ctx, loc, buildJndiContext());
        PaRegistry.getInstance().setServiceLocator(loc);

    }

    private Context buildJndiContext() throws IllegalStateException,
            NamingException {
        SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder
                .emptyActivatedContextBuilder();
        for (Object ejb : ejbFactory.getEjbs()) {
            storeEjbInContext(ejb, contextBuilder);
        }
        return contextBuilder.createInitialContextFactory(new Hashtable<>())
                .getInitialContext(new Hashtable<>());
    }

    @SuppressWarnings("rawtypes")
    private void storeEjbInContext(Object ejb,
            SimpleNamingContextBuilder contextBuilder) {
        Class localOrRemoteInterface = getEjbImplementedInterface(ejb
                .getClass());
        String jndiURL = "java:global/pa/pa-ejb/"
                + ejb.getClass().getSimpleName() + "!"
                + localOrRemoteInterface.getName();
        contextBuilder.bind(jndiURL, ejb);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Class getEjbImplementedInterface(Class ejbClass) {
        for (Type type : ejbClass.getGenericInterfaces()) {
            Class interfaceClass = (Class) type;
            if (interfaceClass.isAnnotationPresent(Local.class)
                    || interfaceClass.isAnnotationPresent(Remote.class)) {
                return interfaceClass;
            }
        }
        throw new RuntimeException(
                "Unable to determine which interface, Local or Remote, this EJB implements: "
                        + ejbClass);
    }

    /**
     * @return
     */
    private static int randomPort() {
        int port;
        while (isPortInUse((port = (int) (1024 + Math.random() * 64500)))) {
            System.out.println("Port " + port + " in use; trying another...");
        }
        return port;
    }

    private static boolean isPortInUse(final int port) {
        try {
            new ServerSocket(port).close();
        } catch (IOException e) {
            return true;
        }
        return false;
    }
}
