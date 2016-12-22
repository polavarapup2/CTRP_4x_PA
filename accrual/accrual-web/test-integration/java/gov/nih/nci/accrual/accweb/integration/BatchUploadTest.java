package gov.nih.nci.accrual.accweb.integration;

import gov.nih.nci.accrual.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.accrual.webservices.types.DiseaseCode;
import gov.nih.nci.accrual.webservices.types.Ethnicity;
import gov.nih.nci.accrual.webservices.types.Gender;
import gov.nih.nci.accrual.webservices.types.MethodOfPayment;
import gov.nih.nci.accrual.webservices.types.ObjectFactory;
import gov.nih.nci.accrual.webservices.types.Race;
import gov.nih.nci.accrual.webservices.types.StudySubject;
import gov.nih.nci.accrual.webservices.types.StudySubjects;
import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.test.integration.AbstractRestServiceTest;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Logger;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.xml.sax.SAXException;

import com.dumbster.smtp.SmtpMessage;

@SuppressWarnings("deprecation")
public class BatchUploadTest extends AbstractRestServiceTest {

    private static final int BATCH_PROCESSING_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 20000
            : 10000;

    public static final Logger LOG = Logger.getLogger(BatchUploadTest.class
            .getName());

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp("/sites/1");
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
    }

    @Test
    public void testNullifiedOrgHandlingPO8108() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        String batchFileStr = IOUtils
                .toString(
                        getClass().getResourceAsStream("/PO_8108_by_poid.txt"))
                .replaceAll("@nciid@", rConf.getNciTrialID())
                .replaceAll("@poid@", "6");
        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        FileUtils.writeStringToFile(batchFile, batchFileStr);

        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();
        verifyAccrualCollectionRecordHasNullifiedOrgErrorMessage("6");
        verifyEmailContainsNullifiedOrgError("6");
    }

    @Test
    public void testNullifiedOrgHandlingByCtepIDPO8108() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        String batchFileStr = IOUtils
                .toString(
                        getClass().getResourceAsStream("/PO_8108_by_poid.txt"))
                .replaceAll("@nciid@", rConf.getNciTrialID())
                .replaceAll("@poid@", "CTGOVDUPE");
        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        FileUtils.writeStringToFile(batchFile, batchFileStr);

        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();
        verifyAccrualCollectionRecordHasNullifiedOrgErrorMessage("CTGOVDUPE");
        verifyEmailContainsNullifiedOrgError("CTGOVDUPE");
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingPO8106() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        writeValidBatchFileButWithDuplicatePatients(rConf, batchFile);

        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();

        String error = getLatestAccrualCollectionMessage();
        verifyErrorMessageContainsDupePatientInfo(error);

        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        error = email.getBody();
        assertTrue(error.contains("CTRP processed your file successfully"));
        assertTrue(error.contains("Number of Subjects Registered: </b> 1"));
        verifyErrorMessageContainsDupePatientInfo(error);

        verifySubject(rConf, "SU002");

    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingAcrossSitesPO8110()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialRegistrationConfirmation rConf = setupTrialWithTwoSitesAndSu001Subject();

        File batchFile = writeBatchFileWithDuplicateSubjectOnAnotherSite(rConf);

        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();

        String error = getLatestAccrualCollectionMessage();
        verifySubjectOnOtherSiteError(error);

        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        error = email.getBody();
        assertTrue(error.contains("CTRP processed your file successfully"));
        assertTrue(error.contains("Number of Subjects Registered: </b> 1"));
        verifySubjectOnOtherSiteError(error);

        verifySubject(rConf, "SU002");
        String entityName = getAuditDetails();
        assertTrue(entityName.equals("BATCH_FILE"));
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testExistingSubjectAvoidsDuplicates() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();
        login();
        accessTrialScreen(rConf);

        // Add subject with lower case and ensure it is converted to upper case.
        final String subjectID = "su001";
        addSubject(subjectID);
        verifySubject(rConf, subjectID.toUpperCase());
        logoutUser();

        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        writeValidBatchFileWithNoDuplicates(rConf, batchFile);

        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();

        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String error = email.getBody();
        assertTrue(error.contains("CTRP processed your file successfully"));
        assertTrue(error.contains("Number of Subjects Registered: </b> 1"));

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

    @SuppressWarnings("rawtypes")
    @Test
    public void testAccrualCount() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialRegistrationConfirmation rConf = prepareNonInterventionalTrialForAccSub();
        login();
        accessTrialScreen(rConf);
        logoutUser();
        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        writeValidBatchFileWithAccrualCount(rConf, batchFile);
        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String error = email.getBody();
        LOG.info("Successfully connected to the database at " + error);
        assertTrue(error
                .contains("Accrual counts for the following Study Site(s) were updated successfully as follows:"));
        assertTrue(error
                .contains(" 3 - National Cancer Institute Division of Cancer Prevention - 12"));
        assertEquals(
                "12",
                new QueryRunner()
                        .query(connection,
                                "select accrual_count from study_site_subject_accrual_count where study_protocol_identifier = "
                                        + rConf.getPaTrialID(),
                                new ArrayHandler())[0]
                        + "");
    }

    @Override
    protected void logoutUser() {
        super.logoutUser();
        openAndWait("/accrual/logout.action");
    }

    /**
     * @param rConf
     */
    private void accessTrialScreen(TrialRegistrationConfirmation rConf) {
        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());

    }

    /**
     * @param subjectID
     */
    @SuppressWarnings("deprecation")
    private void addSubject(final String subjectID) {
        clickAndWait("xpath=//i[@class='fa-plus']");
        selenium.type("identifier", subjectID);
        selenium.type("birthDate", "01/1990");
        selenium.select("genderCode", "Male");
        selenium.select("raceCode", "White");
        selenium.select("ethnicCode", "Unknown");
        selenium.type("zip", "20171");
        selenium.type("registrationDate", "01/01/2015");
        selenium.type("xpath=//input[@name='patient.diseaseIdentifier']",
                "29491");
        selenium.select("organizationName",
                "label=National Cancer Institute Division of Cancer Prevention");
        clickAndWait("mainActionBtn");
    }

    /**
     * @param rConf
     * @param batchFile
     * @throws IOException
     */
    private void writeValidBatchFileWithNoDuplicates(
            TrialRegistrationConfirmation rConf, File batchFile)
            throws IOException {
        FileUtils
                .writeLines(
                        batchFile,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "COLLECTIONS," + rConf.getNciTrialID()
                                        + ",,,,,,,,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",sU001,77058,,193106,Female,Not Hispanic or Latino,,20110513,, 3 ,,,,,,,,,,B46.9,,",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",sU001,White" }));
    }

    /**
     * @param rConf
     * @param batchFile
     * @throws IOException
     */
    private void writeValidBatchFileWithAccrualCount(
            TrialRegistrationConfirmation rConf, File batchFile)
            throws IOException {
        FileUtils.writeLines(batchFile, "UTF-8", Arrays.asList(new String[] {
                "COLLECTIONS," + rConf.getNciTrialID() + ",,,,,,,,,",
                "ACCRUAL_COUNT," + rConf.getNciTrialID() + ", 3 , 12 " }));
    }

    /**
     * @return
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
    private TrialRegistrationConfirmation setupTrialWithTwoSitesAndSu001Subject()
            throws JAXBException, SAXException, SQLException,
            ClientProtocolException, ParseException, IOException,
            NumberFormatException, DatatypeConfigurationException,
            java.text.ParseException, UnsupportedEncodingException {
        // Register trial, add a site, and add a patient.
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));

        // Now add a second site.
        upd = readParticipatingSiteFromFile("/integration_ps_accruing_ctep_add.xml");
        response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long site2ID = Long.parseLong(EntityUtils.toString(
                response.getEntity(), "utf-8"));

        grantAccrualAccess("submitter-ci", siteID);
        grantAccrualAccess("submitter-ci", site2ID);

        // Add a subject that will serve as a potential duplicate
        StudySubjects subjects = new ObjectFactory().createStudySubjects();
        StudySubject subject = createSubject("SU001");
        subjects.getStudySubject().add(subject);
        submitSubjects(subjects, "/sites/" + siteID);
        return rConf;
    }

    /**
     * @param error
     */
    private void verifySubjectOnOtherSiteError(String error) {
        assertTrue(error
                .contains("The following lines contain Patient IDs already in use by Patients registered on a different site for "
                        + "this study with matching Gender, Date of Birth, Race, and Ethnicity. "
                        + "Patients were not processed:"));
        assertTrue(error
                .contains("Line 2: Patient ID SU001 is already registered at "
                        + "National Cancer Institute Division of Cancer Prevention, Site PO ID: "
                        + 3 + ", Site CTEP ID: DCP"));
    }

    /**
     * @param subjects
     * @param serviceURL
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ClientProtocolException
     */
    protected void submitSubjects(StudySubjects subjects, String serviceURL)
            throws JAXBException, UnsupportedEncodingException, IOException,
            ClientProtocolException {
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
        EntityUtils.consumeQuietly(response.getEntity());
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
        ss.getRace().add(Race.WHITE);
        ss.setEthnicity(Ethnicity.HISPANIC_OR_LATINO);
        ss.setGender(Gender.FEMALE);
        ss.setIdentifier(id);
        ss.setMethodOfPayment(MethodOfPayment.MEDICAID_AND_MEDICARE);
        gc.setTime(DateUtils.parseDate("01/01/2014",
                new String[] { "MM/dd/yyyy" }));
        ss.setRegistrationDate(DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(gc));
        ss.setZipCode("20171");

        return ss;
    }

    /**
     * @param error
     */
    private void verifyErrorMessageContainsDupePatientInfo(String error) {
        assertTrue(error
                .contains("The following lines contain duplicate subject data and were not processed into the system. "
                        + "Please remove the duplicate lines and resubmit the file"));
        assertTrue(error.contains("Line 2, Site ID: 3, Subject ID: SU001"));
        assertTrue(error.contains("Line 3, Site ID: 3, Subject ID: SU001"));
    }

    /**
     * @param rConf
     * @param batchFile
     * @throws IOException
     */
    private void writeValidBatchFileButWithDuplicatePatients(
            TrialRegistrationConfirmation rConf, File batchFile)
            throws IOException {
        FileUtils
                .writeLines(
                        batchFile,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "COLLECTIONS," + rConf.getNciTrialID()
                                        + ",,,,,,,,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU001,77058,,193106,Female,Not Hispanic or Latino,,20110513,, 3 ,,,,,,,,,,B46.9,,",
                                "PATIENTS, "
                                        + rConf.getNciTrialID()
                                        + ", SU001 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU002,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",
                                "",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU001,White",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU002,Asian" }));
    }

    /**
     * @return
     * @throws JAXBException
     * @throws SAXException
     * @throws SQLException
     * @throws ClientProtocolException
     * @throws ParseException
     * @throws IOException
     * @throws NumberFormatException
     */
    private TrialRegistrationConfirmation prepareTrialForAccrualSubmission()
            throws JAXBException, SAXException, SQLException,
            ClientProtocolException, ParseException, IOException,
            NumberFormatException {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);
        return rConf;
    }

    /**
     * @return
     * @throws JAXBException
     * @throws SAXException
     * @throws SQLException
     * @throws ClientProtocolException
     * @throws ParseException
     * @throws IOException
     * @throws NumberFormatException
     */
    private TrialRegistrationConfirmation prepareNonInterventionalTrialForAccSub()
            throws JAXBException, SAXException, SQLException,
            ClientProtocolException, ParseException, IOException,
            NumberFormatException {
        TrialRegistrationConfirmation rConf = register("/register_complete_noninterventional_minimal_data.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);
        return rConf;
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingZipUploadPO8106() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        File zip = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".zip");
        File batchFile1 = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString() + ".txt");
        File batchFile2 = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString() + ".txt");
        writeValidBatchFileButWithDuplicatePatients(rConf, batchFile1);
        writeInvalidBatchFileWithDuplicatePatients(rConf, batchFile2);

        FileOutputStream fos = new FileOutputStream(zip);
        ZipOutputStream zos = new ZipOutputStream(fos);
        addToZipFile(batchFile1, zos);
        addToZipFile(batchFile2, zos);
        zos.close();
        fos.close();

        restartEmailServer();
        submitBatchFile(zip);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(2);
        stopSMTP();

        assertEquals(2, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email1 = (SmtpMessage) emailIter.next();
        String msg1 = email1.getBody();
        SmtpMessage email2 = (SmtpMessage) emailIter.next();
        String msg2 = email2.getBody();

        String success = msg1;
        String error = msg2;
        if (msg2.contains("CTRP processed your file successfully")) {
            success = msg2;
            error = msg1;
        }

        assertTrue(success.contains("CTRP processed your file successfully"));
        assertTrue(success.contains("Number of Subjects Registered: </b> 1"));
        verifyErrorMessageContainsDupePatientInfo(success);

        assertTrue(error
                .contains("Unfortunately, the submission failed due to the following errors"));
        assertTrue(error
                .contains("Patient race code is not valid for patient ID SU002 at line 7"));
        verifyErrorMessageContainsDupePatientInfo(error);
        verifySubject(rConf, "SU002");

    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingAcrossSitesWithErrorsPO8110()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialRegistrationConfirmation rConf = setupTrialWithTwoSitesAndSu001Subject();

        File batchFile = writeInvalidBatchFileWithDuplicateSubjectOnAnotherSite(rConf);

        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();

        String error = getLatestAccrualCollectionMessage();
        verifySubjectOnOtherSiteError(error);

        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        error = email.getBody();
        assertTrue(error
                .contains("Unfortunately, the submission failed due to the following errors"));
        assertTrue(error
                .contains("Patient race code is not valid for patient ID SU002 at line 7"));
        verifySubjectOnOtherSiteError(error);

        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        assertFalse(selenium.isElementPresent("xpath=//a[text()='SU002']"));

    }

    /**
     * @param rConf
     * @return
     * @throws IOException
     */
    private File writeInvalidBatchFileWithDuplicateSubjectOnAnotherSite(
            TrialRegistrationConfirmation rConf) throws IOException {
        // Now submit a batch file with SU001 duplicate but on a different site.
        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        FileUtils
                .writeLines(
                        batchFile,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "COLLECTIONS," + rConf.getNciTrialID()
                                        + ",,,,,,,,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU001,77058,,201001,Female,Hispanic or Latino,,20110513,, 2 ,,,,,,,,,,B46.9,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU002,33908,,195306,Female,Not Hispanic or Latino,,20110930,,2,,,,,,,,,,B46.9,,",
                                "",
                                "PATIENT_RACES,"
                                        + rConf.getNciTrialID()
                                        + ",SU001,American Indian or Alaska Native",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU001,White",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU002,Black" }));
        return batchFile;
    }

    /**
     * @param rConf
     * @return
     * @throws IOException
     */
    private File writeBatchFileWithDuplicateSubjectOnAnotherSite(
            TrialRegistrationConfirmation rConf) throws IOException {
        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        FileUtils
                .writeLines(
                        batchFile,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "COLLECTIONS," + rConf.getNciTrialID()
                                        + ",,,,,,,,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU001,77058,,201001,Female,Hispanic or Latino,,20110513,, 2 ,,,,,,,,,,B46.9,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU002,33908,,195306,Female,Not Hispanic or Latino,,20110930,,2,,,,,,,,,,B46.9,,",
                                "",
                                "PATIENT_RACES,"
                                        + rConf.getNciTrialID()
                                        + ",SU001,American Indian or Alaska Native",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU001,White",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU002,Asian" }));
        return batchFile;
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingWithErrorsPO8106() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        writeInvalidBatchFileWithDuplicatePatients(rConf, batchFile);

        restartEmailServer();
        submitBatchFile(batchFile);
        pause(BATCH_PROCESSING_WAIT_TIME);
        waitForEmailsToArrive(1);
        stopSMTP();

        String error = getLatestAccrualCollectionMessage();
        verifyErrorMessageContainsDupePatientInfo(error);

        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        error = email.getBody();
        assertTrue(error
                .contains("Unfortunately, the submission failed due to the following errors"));
        assertTrue(error
                .contains("Patient race code is not valid for patient ID SU002 at line 7"));
        verifyErrorMessageContainsDupePatientInfo(error);

        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        assertFalse(selenium.isElementPresent("xpath=//a[text()='SU001']"));
        assertFalse(selenium.isElementPresent("xpath=//a[text()='SU002']"));

    }

    /**
     * @param rConf
     * @param batchFile
     * @throws IOException
     */
    private void writeInvalidBatchFileWithDuplicatePatients(
            TrialRegistrationConfirmation rConf, File batchFile)
            throws IOException {
        FileUtils
                .writeLines(
                        batchFile,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "COLLECTIONS," + rConf.getNciTrialID()
                                        + ",,,,,,,,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU001,77058,,193106,Female,Not Hispanic or Latino,,20110513,, 3 ,,,,,,,,,,B46.9,,",
                                "PATIENTS, "
                                        + rConf.getNciTrialID()
                                        + ", SU001 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",
                                "PATIENTS,"
                                        + rConf.getNciTrialID()
                                        + ",SU002,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",
                                "",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU001,Black",
                                "PATIENT_RACES," + rConf.getNciTrialID()
                                        + ",SU002,Black" }));
    }

    @SuppressWarnings("deprecation")
    private void verifySubject(TrialRegistrationConfirmation rConf,
            String subjectID) {
        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        moveElementIntoView(By.xpath("//a[text()='" + subjectID + "']"));
        clickAndWait("link=" + subjectID);
        clickAndWait("xpath=//i[@class='fa-pencil']");
        assertEquals(subjectID, selenium.getValue("id=identifier"));
    }

    @SuppressWarnings("rawtypes")
    private void verifyEmailContainsNullifiedOrgError(String orgID) {
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        verifyNullifiedOrgErrorMessage(email.getBody(), orgID);

    }

    private void verifyAccrualCollectionRecordHasNullifiedOrgErrorMessage(
            String orgID) throws SQLException {
        String error = getLatestAccrualCollectionMessage();
        verifyNullifiedOrgErrorMessage(error, orgID);
    }

    /**
     * @return
     * @throws SQLException
     */
    private String getLatestAccrualCollectionMessage() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String error = (String) runner
                .query(connection,
                        "select results from accrual_collections order by identifier desc limit 1",
                        new ArrayHandler())[0];
        return error;
    }

    private void verifyNullifiedOrgErrorMessage(String msg, String orgID) {
        assertTrue(msg
                .contains("The Registering Institution Code must be a valid PO or CTEP ID. Code: "
                        + orgID + "*"));
        assertTrue(msg
                .contains("*This organization's record has been nullified and merged with another organization."));
        assertTrue(msg.contains("The new organization is:"));
        assertTrue(msg.contains("Name: ClinicalTrials.gov"));
        assertTrue(msg.contains("PO ID: 1"));
        assertTrue(msg.contains("CTEP ID: N/A"));
    }

    @SuppressWarnings("deprecation")
    private void submitBatchFile(File batchFile) {
        login();
        clickAndWait("link=Batch Upload");
        String path = batchFile.toString();
        selenium.type("upload", path);
        clickAndWait("xpath=//button[normalize-space(text())='Submit']");
    }

    @SuppressWarnings("deprecation")
    private void login() {
        openAndWait("/accrual");
        selenium.type("j_username", "submitter-ci");
        selenium.type("j_password", "pass");
        clickAndWait("xpath=//i[@class='fa-arrow-circle-right']");
        clickAndWait("id=acceptDisclaimer");

    }

    private static void addToZipFile(File file, ZipOutputStream zos)
            throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    private String getAuditDetails() throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (String) runner.query(connection,
                "select entityname from auditlogrecord  "
                        + "where id = (select max(id) from auditlogrecord )",
                new ArrayHandler())[0];
    }

}
