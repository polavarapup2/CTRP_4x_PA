/**
 * 
 */
package gov.nih.nci.accrual.webservices;

import gov.nih.nci.accrual.webservices.types.BatchFile;
import gov.nih.nci.accrual.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.accrual.webservices.types.DiseaseCode;
import gov.nih.nci.accrual.webservices.types.Ethnicity;
import gov.nih.nci.accrual.webservices.types.Gender;
import gov.nih.nci.accrual.webservices.types.MethodOfPayment;
import gov.nih.nci.accrual.webservices.types.ObjectFactory;
import gov.nih.nci.accrual.webservices.types.Race;
import gov.nih.nci.accrual.webservices.types.StudySubject;
import gov.nih.nci.accrual.webservices.types.StudySubjects;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.test.integration.AbstractRestServiceTest;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.xml.sax.SAXException;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author dkrylov
 * 
 */
public class AccrualRestServiceTest extends AbstractRestServiceTest {

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp("/sites/1");
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        deactivateAllTrials();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#submitStudySubjects(long, gov.nih.nci.accrual.webservices.types.StudySubjects)}
     * .
     * 
     * @throws IOException
     * @throws SQLException
     * @throws SAXException
     * @throws JAXBException
     * @throws ParseException
     * @throws ClientProtocolException
     * @throws DatatypeConfigurationException
     * @throws java.text.ParseException
     */
    @Test
    public final void testSubmitStudySubjects() throws ClientProtocolException,
            ParseException, JAXBException, SAXException, SQLException,
            IOException, DatatypeConfigurationException,
            java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        addSiteSubjectsAndVerify(rConf);
    }

    @Test
    public final void testUpdateAccrualCountRequriesAccrualAccess()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));

        String serviceURL = "/sites/" + siteID + "/count";
        String accrualCount = "4234";

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        StringEntity entity = new StringEntity(accrualCount);
        String url = baseURL + serviceURL;
        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", TEXT_PLAIN);
        req.setEntity(entity);

        response = httpClient.execute(req);
        assertEquals(500, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("User does not have accrual access to site"));

    }

    @Test
    public final void testUpdateAccrualCountSiteNotFound()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        String serviceURL = "/sites/2390478534/count";
        String accrualCount = "4234";

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        StringEntity entity = new StringEntity(accrualCount);
        String url = baseURL + serviceURL;
        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", TEXT_PLAIN);
        req.setEntity(entity);

        HttpResponse response = httpClient.execute(req);
        assertEquals(404, getReponseCode(response));
        assertTrue(EntityUtils
                .toString(response.getEntity(), "utf-8")
                .contains(
                        "Participating site with PA ID 2390478534 is not found."));

    }

    @Test
    public final void testUpdateAccrualCount() throws ClientProtocolException,
            ParseException, JAXBException, SAXException, SQLException,
            IOException, DatatypeConfigurationException,
            java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        String serviceURL = "/sites/" + siteID + "/count";
        String accrualCount = "4234";

        submitAccrualCountAndVerify(rConf, serviceURL, accrualCount);

    }

    @Test
    public final void testUpdateAccrualCountBySitePoId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        String serviceURL = "/trials/nci/" + rConf.getNciTrialID()
                + "/sites/po/3/count";
        String accrualCount = "15234";

        submitAccrualCountAndVerify(rConf, serviceURL, accrualCount);

    }

    @Test
    public final void testUpdateAccrualCountWithSameValueAndEnsureTimestamp()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException,
            InterruptedException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        String serviceURL = "/trials/nci/" + rConf.getNciTrialID()
                + "/sites/po/3/count";
        String accrualCount = "15234";

        Date ts1 = submitAccrualCountAndVerify(rConf, serviceURL, accrualCount);
        Thread.sleep(60000);
        logoutUser();
        Date ts2 = submitAccrualCountAndVerify(rConf, serviceURL, accrualCount);

        assertTrue(ts2.after(ts1));

    }

    @Test
    public final void testUpdateAccrualCountBySiteCtepId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        String serviceURL = "/trials/pa/" + rConf.getPaTrialID()
                + "/sites/ctep/DCP/count";
        String accrualCount = "152349";

        submitAccrualCountAndVerify(rConf, serviceURL, accrualCount);

    }

    /**
     * @param rConf
     * @param siteID
     * @param serviceURL
     * @param accrualCount
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ClientProtocolException
     * @throws SQLException
     * @throws java.text.ParseException
     */
    private Date submitAccrualCountAndVerify(
            TrialRegistrationConfirmation rConf, String serviceURL,
            String accrualCount) throws UnsupportedEncodingException,
            IOException, ClientProtocolException, SQLException,
            java.text.ParseException {
        HttpResponse response;
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        StringEntity entity = new StringEntity(accrualCount);
        String url = baseURL + serviceURL;
        LOG.info("Hitting " + url);
        LOG.info("Payload: " + accrualCount);

        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", TEXT_PLAIN);
        req.setEntity(entity);

        response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        EntityUtils.consumeQuietly(response.getEntity());

        return verifyAccrualCount(rConf, accrualCount);
    }

    @SuppressWarnings("deprecation")
    private Date verifyAccrualCount(TrialRegistrationConfirmation rConf,
            String accrualCount) throws SQLException, java.text.ParseException {
        removeDcpAndCtepIdFromTrial(rConf);
        login();
        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        assertEquals(
                accrualCount,
                selenium.getValue("xpath=//table[@id='row']/tbody/tr[1]/td[3]/input"));
        String tstampStr = s.getText(
                "xpath=//table[@id='row']/tbody/tr[1]/td[4]").trim();
        return DateUtils.parseDate(tstampStr,
                new String[] { "MM/dd/yyyy HH:mm" });

    }

    @Test
    public final void testDeleteStudySubject() throws ClientProtocolException,
            ParseException, JAXBException, SAXException, SQLException,
            IOException, DatatypeConfigurationException,
            java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long siteID = addSiteSubjectsAndVerify(rConf);

        deleteAndVerify(rConf, "/sites/" + siteID + "/subjects/SU001");
    }

    @Test
    public final void testDeleteStudySubjectNotFound()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long siteID = addSiteSubjectsAndVerify(rConf);

        final String path = "/sites/" + siteID + "/subjects/SU002";
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";

        String url = baseURL + path;
        LOG.info("Hitting " + url);
        HttpDelete req = new HttpDelete(url);
        req.addHeader("Accept", TEXT_PLAIN);
        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(404, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("Study Subject with ID of SU002 is not found"));
    }

    @Test
    public final void testDeleteStudySubjectRequiresAccrualAccess()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long siteID = addSiteSubjectsAndVerify(rConf);

        removeAccrualAccess(siteID);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";

        String url = baseURL + "/sites/" + siteID + "/subjects/SU001";
        LOG.info("Hitting " + url);
        HttpDelete req = new HttpDelete(url);
        req.addHeader("Accept", TEXT_PLAIN);
        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(500, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("User does not have accrual access to site"));
    }

    @Test
    public final void testDeleteStudySubjectSiteNotFound()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        final String path = "/sites/2390478534/subjects/SU001";
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";

        String url = baseURL + path;
        LOG.info("Hitting " + url);
        HttpDelete req = new HttpDelete(url);
        req.addHeader("Accept", TEXT_PLAIN);
        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(404, getReponseCode(response));
        assertTrue(EntityUtils
                .toString(response.getEntity(), "utf-8")
                .contains(
                        "Participating site with PA ID 2390478534 is not found."));
    }

    @Test
    public final void testDeleteStudySubjectPoId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long siteID = addSiteSubjectsAndVerify(rConf);

        deleteAndVerify(rConf, "/trials/nci/" + rConf.getNciTrialID()
                + "/sites/po/3/subjects/SU001");
    }

    @Test
    public final void testDeleteStudySubjectCtepId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long siteID = addSiteSubjectsAndVerify(rConf);

        deleteAndVerify(rConf, "/trials/pa/" + rConf.getPaTrialID()
                + "/sites/ctep/DCP/subjects/SU001");
    }

    private void deleteAndVerify(TrialRegistrationConfirmation rConf,
            String serviceURL) throws ClientProtocolException, IOException {
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";

        String url = baseURL + serviceURL;
        LOG.info("Hitting " + url);
        HttpDelete req = new HttpDelete(url);
        req.addHeader("Accept", TEXT_PLAIN);
        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        EntityUtils.consumeQuietly(response.getEntity());

        verifySubjectDeleted(rConf);
    }

    /**
     * @param rConf
     * @throws JAXBException
     * @throws SAXException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     * @throws SQLException
     * @throws NumberFormatException
     * @throws DatatypeConfigurationException
     * @throws ParseException
     */
    private long addSiteSubjectsAndVerify(TrialRegistrationConfirmation rConf)
            throws JAXBException, SAXException, ClientProtocolException,
            IOException, ParseException, SQLException, NumberFormatException,
            DatatypeConfigurationException, java.text.ParseException {
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("su001");
        subjects.getStudySubject().add(subject);

        submitAndVerify(rConf, siteID, subjects, "/sites/" + siteID);
        return siteID;
    }

    @Test
    public final void testSubmitStudySubjectsAvoidsDuplicates()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long siteID = addSiteSubjectsAndVerify(rConf);

        logoutUser();

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("Su001");
        subject.setZipCode("99999");
        subjects.getStudySubject().add(subject);
        submitAndVerify(rConf, siteID, subjects, "/sites/" + siteID);

        assertEquals(
                "1",
                new QueryRunner()
                        .query(connection,
                                "select count(*) from study_subject where study_protocol_identifier="
                                        + rConf.getPaTrialID()
                                        + " and status_code='ACTIVE' and upper(assigned_identifier)='SU001'",
                                new ArrayHandler())[0]
                        + "");

    }

    @Test
    public final void testSubmitStudySubjectsRotateAllEnums()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        subjects.getStudySubject().addAll(rotateSubjects());

        submitAndVerify(rConf, siteID, subjects, "/sites/" + siteID);
    }

    private Collection<StudySubject> rotateSubjects()
            throws DatatypeConfigurationException, java.text.ParseException {
        Collection<StudySubject> list = new ArrayList<StudySubject>();
        int counter = 1;

        for (Gender gender : Gender.values()) {
            StudySubject ss = createSubject("SU" + counter++);
            ss.setGender(gender);
            list.add(ss);
        }

        for (Race race : Race.values()) {
            StudySubject ss = createSubject("SU" + counter++);
            ss.getRace().clear();
            ss.getRace().add(race);
            list.add(ss);
        }

        for (Ethnicity e : Ethnicity.values()) {
            StudySubject ss = createSubject("SU" + counter++);
            ss.setEthnicity(e);
            list.add(ss);
        }

        for (MethodOfPayment e : MethodOfPayment.values()) {
            StudySubject ss = createSubject("SU" + counter++);
            ss.setMethodOfPayment(e);
            list.add(ss);
        }

        return list;
    }

    @Test
    public final void testSubmitStudySubjectsICDO3()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);
        changeDiseaseTerm(rConf, "ICD-O-3");

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");

        DiseaseCode dc = new DiseaseCode();
        dc.setCodeSystem("ICD-O-3");
        dc.setValue("C41");
        subject.setDisease(dc);
        dc = new DiseaseCode();
        dc.setCodeSystem("ICD-O-3");
        dc.setValue("C41.0");
        subject.setSiteDisease(dc);

        subjects.getStudySubject().add(subject);

        submitAndVerify(rConf, siteID, subjects, "/sites/" + siteID);
    }

    @Test
    public final void testSubmitStudySubjectsWithLegacyCtepDiseaseCode()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);
        changeDiseaseTerm(rConf, "Legacy Codes - CTEP");

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");

        DiseaseCode dc = new DiseaseCode();
        dc.setCodeSystem("Legacy Codes - CTEP");
        dc.setValue("10028566");
        subject.setDisease(dc);

        subjects.getStudySubject().add(subject);

        submitAndVerify(rConf, siteID, subjects, "/sites/" + siteID);
    }

    private void changeDiseaseTerm(TrialRegistrationConfirmation rConf,
            String string) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set accrual_disease_code_system='"
                + string + "' where identifier=" + rConf.getPaTrialID();
        runner.update(connection, sql);

    }

    private void removeAccrualAccess(long siteID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "delete from study_site_accrual_access where study_site_identifier="
                + siteID;
        runner.update(connection, sql);

    }

    @Test
    public final void testSubmitStudySubjectsSchemaValidation()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subject.getRace().clear();
        subjects.getStudySubject().add(subject);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        String xml = marshall(subjects);
        StringEntity entity = new StringEntity(xml);
        String url = baseURL + serviceURL;

        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        response = httpClient.execute(req);
        assertEquals(400, getReponseCode(response));
    }

    @Test
    public final void testSubmitStudySubjectsBizValidation()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subject.getDisease().setValue("nonexsitent");
        subjects.getStudySubject().add(subject);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        String xml = marshall(subjects);
        StringEntity entity = new StringEntity(xml);
        String url = baseURL + "/sites/" + siteID;

        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        response = httpClient.execute(req);
        assertEquals(400, getReponseCode(response));
    }

    @Test
    public final void testSubmitStudySubjectsSiteNotFound()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subject.getDisease().setValue("nonexsitent");
        subjects.getStudySubject().add(subject);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        String xml = marshall(subjects);
        StringEntity entity = new StringEntity(xml);
        String url = baseURL + "/sites/2384092384";

        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        HttpResponse response = httpClient.execute(req);
        assertEquals(404, getReponseCode(response));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void testSuperAbstractorHasAccrualAccessToDcpTrials()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        testSuperAbstractorHasAccrualAccessToTrialsWithID("DCP Identifier");

    }

    @SuppressWarnings("deprecation")
    @Test
    public final void testSuperAbstractorHasAccrualAccessToCtepTrials()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        testSuperAbstractorHasAccrualAccessToTrialsWithID("CTEP Identifier");

    }

    /**
     * @param trialIdType
     * @throws JAXBException
     * @throws SAXException
     * @throws SQLException
     * @throws ClientProtocolException
     * @throws ParseException
     * @throws IOException
     * @throws NumberFormatException
     * @throws DatatypeConfigurationException
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    private void testSuperAbstractorHasAccrualAccessToTrialsWithID(
            final String trialIdType) throws JAXBException, SAXException,
            SQLException, ClientProtocolException, ParseException, IOException,
            NumberFormatException, DatatypeConfigurationException,
            java.text.ParseException, UnsupportedEncodingException {
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subjects.getStudySubject().add(subject);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        String xml = marshall(subjects);
        StringEntity entity = new StringEntity(xml);
        String url = baseURL + "/sites/" + siteID;

        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        // Create new Submitter
        String loginName = RandomStringUtils.randomAlphabetic(12).toLowerCase();
        Number userID = createCSMUser(loginName);
        createRegistryUser(userID);
        assignUserToGroup(userID, "Submitter");

        // All calls must authenticate as the newly created user.
        Credentials credentials = new UsernamePasswordCredentials(loginName,
                "pass");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);

        // Bare submitter can't access this site.
        response = httpClient.execute(req);
        assertEquals(500, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("User does not have accrual access to site"));

        // Merely making the user Super Abstractor is not enough, either.
        assignUserToGroup(userID, "SuAbstractor");
        response = httpClient.execute(req);
        assertEquals(500, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("User does not have accrual access to site"));

        // Adding a DCP ID to the trial must let Super Abstractor submit
        // accruals on its sites.
        String identifier = RandomStringUtils.randomAlphabetic(12);
        findAndSelectTrial(rConf);
        clickAndWait("link=General Trial Details");
        s.select("otherIdentifierType", trialIdType);
        s.type("otherIdentifierOrg", identifier);
        clickAndWait("id=otherIdbtnid");
        waitForTextToAppear(By.className("confirm_msg"),
                "Identifier added to the trial", 15);
        submitAndVerify(rConf, siteID, subjects, "/sites/" + siteID);
    }

    @Test
    public final void testSubmitStudySubjectsNoAccrualAccess()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subjects.getStudySubject().add(subject);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        String xml = marshall(subjects);
        StringEntity entity = new StringEntity(xml);
        String url = baseURL + "/sites/" + siteID;

        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        response = httpClient.execute(req);
        assertEquals(500, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("User does not have accrual access to site"));
    }

    @Test
    public final void testSubmitStudySubjectsPoId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subjects.getStudySubject().add(subject);

        submitAndVerify(rConf, siteID, subjects,
                "/trials/nci/" + rConf.getNciTrialID() + "/sites/po/3");
    }

    @Test
    public final void testSubmitStudySubjectsCtepId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subjects.getStudySubject().add(subject);

        submitAndVerify(rConf, siteID, subjects,
                "/trials/nci/" + rConf.getNciTrialID() + "/sites/ctep/DCP");
    }

    @Test
    public final void testSubmitStudySubjectsDiseaseError()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001", "ICD9", "141.0");
        subjects.getStudySubject().add(subject);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        String xml = marshall(subjects);
        StringEntity entity = new StringEntity(xml);

        String url = baseURL + "/sites/" + siteID;
        LOG.info("Hitting " + url);
        LOG.info("Payload: " + xml);
        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(400, getReponseCode(response));
        assertTrue(EntityUtils
                .toString(response.getEntity(), "utf-8")
                .contains(
                        "The subject's disease's coding system ICD9 is different from the one used on the trial: ICD10"));
        subjects.getStudySubject().clear();
        subject = createSubject("SU001", "ICD9", "1v99.00");
        subjects.getStudySubject().add(subject);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        xml = marshall(subjects);
        entity = new StringEntity(xml);

        url = baseURL + "/sites/" + siteID;
        LOG.info("Hitting " + url);
        LOG.info("Payload: " + xml);
        req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(400, getReponseCode(response));
        assertTrue(EntityUtils
                .toString(response.getEntity(), "utf-8")
                .contains(
                        "Disease code does not exist for given Disease code System."));
    }

    public final void testSubmitEmptyBatchFile() throws Exception {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services/batch";
        String xml = marshall(new BatchFile());
        StringEntity entity = new StringEntity(xml);
        HttpPost req = new HttpPost(baseURL);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        HttpResponse response = httpClient.execute(req);
        assertEquals(400, getReponseCode(response));

    }

    @Test
    public final void testSubmitBatchFile() throws Exception {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";

        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset_accrual.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add_accrual.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services/batch";

        File file = new File(this.getClass()
                .getResource("/CDUS_Complete_Rest.txt").toURI());

        String data = FileUtils.readFileToString(file);
        String modifiedData = data.replaceAll("\\{nciId\\}",
                rConf.getNciTrialID());

        BatchFile batchFile = new BatchFile();
        batchFile.setValue(modifiedData.getBytes());
        String xml = marshall(batchFile);
        StringEntity entity = new StringEntity(xml);
        HttpPost req = new HttpPost(baseURL);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        response = httpClient.execute(req);
        assertEquals(200, getReponseCode(response));

        waitForEmailsToArrive(3);
        Iterator emailIter = server.getReceivedEmail();

        // skip trial registration emails
        emailIter.next();
        emailIter.next();

        // check if submission is sucessful
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll(">\\s+", ">");

        assertEquals(
                "NCI CTRP: Accrual SUBMISSION SUCCESSFUL for "
                        + rConf.getNciTrialID(), subject);
        assert body.contains("The CTRP processed your file successfully.");

        // check if records added in database
        long count = getRecordCount(rConf.getPaTrialID());

        assert count == 2;

    }

    @Test
    public final void testSubmitBatchFileWithZip() throws Exception {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";

        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset_accrual.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add_accrual.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services/batch";

        File file = new File(this.getClass()
                .getResource("/CDUS_Complete_Rest.txt").toURI());

        String data = FileUtils.readFileToString(file);
        String modifiedData = data.replaceAll("\\{nciId\\}",
                rConf.getNciTrialID());

        // zip this file in memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry("CDUS_Complete_Rest.zip");
        entry.setSize(modifiedData.getBytes().length);
        zos.putNextEntry(entry);
        zos.write(modifiedData.getBytes());
        zos.closeEntry();
        zos.close();

        byte[] zipData = baos.toByteArray();
        baos.close();

        BatchFile batchFile = new BatchFile();
        batchFile.setValue(zipData);
        String xml = marshall(batchFile);
        StringEntity entity = new StringEntity(xml);
        HttpPost req = new HttpPost(baseURL);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        response = httpClient.execute(req);
        assertEquals(200, getReponseCode(response));

        waitForEmailsToArrive(3);
        Iterator emailIter = server.getReceivedEmail();

        // skip trial registration emails
        emailIter.next();
        emailIter.next();

        // check if submission is sucessful
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll(">\\s+", ">");

        assertEquals(
                "NCI CTRP: Accrual SUBMISSION SUCCESSFUL for "
                        + rConf.getNciTrialID(), subject);
        assert body.contains("The CTRP processed your file successfully.");

        // check if records added in database
        long count = getRecordCount(rConf.getPaTrialID());

        assert count == 2;

    }

    @Test
    public final void testSubmitStudySubjectsDcpId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");

        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");

        String dcpString = "dcp001";
        Integer site_dcpID = insertDcpID(rConf, dcpString);
        LOG.info("site_dcpID : " + site_dcpID);

        HttpResponse response = addSite("pa", "" + rConf.getPaTrialID(), upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        LOG.info("siteID : " + siteID);
        grantAccrualAccess("submitter-ci", siteID);

        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subjects.getStudySubject().add(subject);

        submitAndVerify(rConf, siteID, subjects, "/trials/dcp/" + dcpString
                + "/sites/ctep/DCP");
    }

    @Test
    public final void testDeleteStudySubjectDcpId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long siteID = addSiteSubjectsAndVerify(rConf);
        String dcpId = "dcp001";
        Integer site_dcpID = insertDcpID(rConf, dcpId);
        deleteAndVerify(rConf, "/trials/dcp/" + dcpId
                + "/sites/ctep/DCP/subjects/SU001");
    }

    @Test
    public final void testUpdateAccrualCountBySiteDcpId()
            throws ClientProtocolException, ParseException, JAXBException,
            SAXException, SQLException, IOException,
            DatatypeConfigurationException, java.text.ParseException {

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");

        String dcpId = "dcp001";
        Integer site_dcpID = insertDcpID(rConf, dcpId);
        LOG.info("site_dcpID : " + site_dcpID);

        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));

        grantAccrualAccess("submitter-ci", siteID);
        LOG.info("siteID : " + siteID);

        String serviceURL = "/trials/dcp/" + dcpId + "/sites/ctep/DCP/count";
        String accrualCount = "1566";

        submitAccrualCountAndVerify(rConf, serviceURL, accrualCount);
    }

    private long getRecordCount(long trialId) throws SQLException {

        String sql = "select count(*) from study_subject where study_protocol_identifier ="
                + trialId;

        long recordCount = 0;
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection, sql,
                new ArrayListHandler());
        for (Object[] row : results) {
            recordCount = (long) row[0];

        }
        return recordCount;
    }

    private String marshall(BatchFile batchFile) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<BatchFile>(new QName(
                "gov.nih.nci.accrual.webservices.types", "batchFile"),
                BatchFile.class, batchFile), out);
        return out.toString();
    }

    private Integer insertDcpID(TrialRegistrationConfirmation rConf, String dcp)
            throws SQLException {
        String orgName = "National Cancer Institute Division of Cancer Prevention";

        String sql = "INSERT INTO study_site( identifier, functional_code, local_sp_indentifier, review_board_approval_number, review_board_approval_date, "
                + " review_board_approval_status_code, target_accrual_number, study_protocol_identifier, healthcare_facility_identifier,"
                + " research_organization_identifier, oversight_committee_identifier,             status_code, "
                + "status_date_range_low, date_last_created, date_last_updated,             "
                + "status_date_range_high, review_board_organizational_affiliation,             "
                + "program_code_text, accrual_date_range_low, accrual_date_range_high,         "
                + "    user_last_created_id, user_last_updated_id)    VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'IDENTIFIER_ASSIGNER', "
                + "'"
                + dcp
                + "' , NULL, current_timestamp, NULL,             null, "
                + rConf.getPaTrialID()
                + ", null ,"
                + getOrgIdByName(orgName)
                + ","
                + "NULL, 'PENDING' , current_timestamp , current_timestamp, current_timestamp ,             "
                + "current_timestamp , null,'', current_timestamp  , current_timestamp , NULL, NULL);";

        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);

        Integer ssId = (Integer) runner
                .query(connection,
                        "select identifier from study_site order by identifier desc limit 1",
                        new ArrayHandler())[0];
        return ssId;
    }

    private void submitAndVerify(TrialRegistrationConfirmation rConf,
            long siteID, StudySubjects subjects, String serviceURL)
            throws JAXBException, ClientProtocolException, IOException,
            SQLException {
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        String xml = marshall(subjects);
        StringEntity entity = new StringEntity(xml);
        String url = baseURL + serviceURL;
        LOG.info("Hitting " + url);
        LOG.info("Payload: " + xml);

        HttpPut req = new HttpPut(url);
        req.addHeader("Accept", TEXT_PLAIN);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(entity);

        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        EntityUtils.consumeQuietly(response.getEntity());

        verifySubjectsOnTrial(rConf, siteID, subjects);
    }

    private void removeDcpAndCtepIdFromTrial(TrialRegistrationConfirmation rConf)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(
                connection,
                "delete from study_site where functional_code::text = 'IDENTIFIER_ASSIGNER'::text AND "
                        + "(research_organization_identifier = "
                        + getOrgIdByName("National Cancer Institute Division of Cancer Prevention")
                        + " ) "
                        + "AND study_protocol_identifier = "
                        + rConf.getPaTrialID());
        runner.update(
                connection,
                "delete from study_site where functional_code::text = 'IDENTIFIER_ASSIGNER'::text AND "
                        + "(research_organization_identifier = "
                        + getOrgIdByName("Cancer Therapy Evaluation Program")
                        + " ) "
                        + "AND study_protocol_identifier = "
                        + rConf.getPaTrialID());

    }

    private void verifySubjectsOnTrial(TrialRegistrationConfirmation rConf,
            long siteID, StudySubjects subjects) throws SQLException {
        removeDcpAndCtepIdFromTrial(rConf);
        login();
        for (StudySubject ss : subjects.getStudySubject()) {
            clickAndWait("link=Trial Search");
            clickAndWait("link=" + rConf.getNciTrialID());
            selenium.select("xpath=//select[@name='row_length']", "100");
            verifySubject(ss, siteID);
        }
    }

    private void verifySubjectDeleted(TrialRegistrationConfirmation rConf) {
        logoutUser();
        login();
        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        assertTrue(selenium.isTextPresent("Nothing found to display."));
    }

    private void verifySubject(StudySubject ss, long siteID)
            throws SQLException {
        moveElementIntoView(By.xpath("//a[text()='"
                + ss.getIdentifier().toUpperCase() + "']"));
        clickAndWait("link=" + ss.getIdentifier().toUpperCase());
        clickAndWait("xpath=//i[@class='fa-pencil']");
        assertEquals(ss.getIdentifier().toUpperCase(),
                selenium.getValue("id=identifier"));
        assertEquals(DateFormatUtils.format(ss.getBirthDate()
                .toGregorianCalendar(), "MM/yyyy"),
                selenium.getValue("id=birthDate"));
        assertEquals(ss.getGender().value(), selenium.getValue("id=genderCode"));
        assertEquals(ss.getRace().get(0).value(),
                selenium.getValue("id=raceCode"));
        assertEquals(ss.getEthnicity().value(),
                selenium.getValue("id=ethnicCode"));
        assertEquals(getCountryId(ss.getCountry().value()),
                selenium.getValue("id=countryIdentifier"));
        assertEquals(ss.getZipCode(), selenium.getValue("id=zip"));
        assertEquals(DateFormatUtils.format(ss.getRegistrationDate()
                .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=registrationDate"));
        assertEquals(PaymentMethodCode.valueOf(ss.getMethodOfPayment().value())
                .getCode(), selenium.getValue("id=paymentMethodCode"));
        assertEquals(
                getDiseaseId(ss.getDisease()),
                selenium.getValue("id=patientsupdate_patient_diseaseIdentifier"));
        if (ss.getSiteDisease() != null) {
            assertEquals(
                    getDiseaseId(ss.getSiteDisease()),
                    selenium.getValue("id=patientsupdate_patient_siteDiseaseIdentifier"));
        }
        assertEquals(siteID + "", selenium.getValue("id=organizationName"));

    }

    private String getDiseaseId(DiseaseCode disease) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return ""
                + runner.query(connection,
                        "select identifier from accrual_disease where code_system='"
                                + disease.getCodeSystem()
                                + "' and disease_code='" + disease.getValue()
                                + "' limit 1", new ArrayHandler())[0];
    }

    private String getCountryId(String value) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return ""
                + runner.query(connection,
                        "select identifier from country where alpha3='" + value
                                + "' limit 1", new ArrayHandler())[0];
    }

    private void login() {
        openAndWait("/accrual");
        selenium.type("j_username", "submitter-ci");
        selenium.type("j_password", "pass");
        clickAndWait("xpath=//i[@class='fa-arrow-circle-right']");
        clickAndWait("id=acceptDisclaimer");

    }

    private String marshall(StudySubjects subjects) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<StudySubjects>(new QName(
                "gov.nih.nci.accrual.webservices.types", "studySubjects"),
                StudySubjects.class, subjects), out);
        return out.toString();
    }

    private StudySubject createSubject(String id)
            throws DatatypeConfigurationException, java.text.ParseException {
        StudySubject ss = new StudySubject();

        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(DateUtils.parseDate("01/01/2010",
                new String[] { "MM/dd/yyyy" }));
        ss.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(
                gc));

        ss.setCountry(CountryISO31661Alpha3Code.USA);

        final DiseaseCode disease = new DiseaseCode();
        disease.setCodeSystem("ICD10");
        disease.setValue("B46.9");
        ss.setDisease(disease);

        ss.getRace().add(Race.AMERICAN_INDIAN_OR_ALASKA_NATIVE);
        ss.setEthnicity(Ethnicity.UNKNOWN);
        ss.setGender(Gender.UNSPECIFIED);
        ss.setIdentifier(id);
        ss.setMethodOfPayment(MethodOfPayment.MEDICAID_AND_MEDICARE);
        gc.setTime(DateUtils.parseDate("01/01/2014",
                new String[] { "MM/dd/yyyy" }));
        ss.setRegistrationDate(DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(gc));
        ss.setZipCode("20171");

        return ss;
    }

    private StudySubject createSubject(String id, String diseaseCode,
            String diseaseValue) throws DatatypeConfigurationException,
            java.text.ParseException {
        StudySubject ss = createSubject(id);
        final DiseaseCode disease = new DiseaseCode();
        disease.setCodeSystem(diseaseCode);
        disease.setValue(diseaseValue);
        ss.setDisease(disease);
        return ss;
    }

    @Override
    protected void logoutUser() {
        super.logoutUser();
        openAndWait("/accrual/logout.action");
    }

}
