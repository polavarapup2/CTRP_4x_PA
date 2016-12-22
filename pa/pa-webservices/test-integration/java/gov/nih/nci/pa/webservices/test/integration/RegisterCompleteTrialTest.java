package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.bind.JAXBException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author Denis G. Krylov
 * 
 */
public class RegisterCompleteTrialTest extends AbstractRestServiceTest {

    public void setUp() throws Exception {
        super.setUp("/trials/complete");
        setupFamilies();
        deactivateAllTrials();
    }

    @Test
    public void testRegisterCancerCenterTrialWithProgramCodes()
            throws Exception {
        TrialInfo trial = registerAndVerify("/integration_register_cancer_center_with_program_codes.xml");
        // PG1;PGXYZ; PG6 ;PG7 -- PGXYZ is non existent; PG7 is inactive. Thus,
        // only PG1 and PG6 must be assigned.
        assertEquals(getTrialProgramCodes(trial),
                Arrays.asList(new String[] { "PG1", "PG6" }));
    }

    @Test
    public void testRegisterNonCancerCenterTrialWithProgramCodes()
            throws Exception {
        TrialInfo trial = registerAndVerify("/integration_register_non_cancer_center_with_program_codes.xml");
        // PG1;PGXYZ; PG6 ;PG7 -- PGXYZ is non existent; PG7 is inactive. Thus,
        // only PG1 and PG6 must be assigned.
        assertEquals(getTrialProgramCodes(trial),
                Arrays.asList(new String[] {}));
        assertEquals(
                "The following program code value was submitted but not recorded: PG1;PGXYZ;PG6;PG7."
                        + " Starting in version 4.3.1, CTRP no longer records program codes for trials"
                        + " lead by a non designated cancer center organization.",
                getTrialField(trial, "comments"));
    }

    @Test
    public void testPrimaryCompletionDateAsNAForDcpTrials() throws Exception {
        String uuid = RandomStringUtils.randomAlphabetic(12);
        System.out.println(uuid);
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
        reg.setLeadOrgTrialID(uuid);
        reg.setDcpIdentifier(uuid);

        // N/A date type cannot be used for Trial Start Date or Completion Date.
        reg.getTrialStartDate().setType("N/A");
        HttpResponse response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(500, "Start date cannot have type of 'N/A'",
                response);
        reg.getTrialStartDate().setType("Actual");

        reg.getCompletionDate().setType("N/A");
        response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(500,
                "Completion date cannot have type of 'N/A'", response);
        reg.getCompletionDate().setType("Anticipated");

        // If PCD is specified, it cannot be N/A.
        reg.getPrimaryCompletionDate().getValue().setType("N/A");
        response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(400,
                "When the Primary Completion Date Type is set to 'N/A', "
                        + "the Primary Completion Date must be null. ",
                response);

        // Only DCP trials can have N/A PCD.
        reg.setDcpIdentifier(null);
        reg.getPrimaryCompletionDate().getValue().setValue(null);
        reg.getPrimaryCompletionDate().getValue().setType("N/A");
        reg.getPrimaryCompletionDate().setNil(true);
        response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(
                400,
                "Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'",
                response);

        // Finally, register a DCP trial with N/A PCD.
        reg.setDcpIdentifier(uuid);
        reg.getPrimaryCompletionDate().getValue().setValue(null);
        reg.getPrimaryCompletionDate().getValue().setType("N/A");
        reg.getPrimaryCompletionDate().setNil(true);
        TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
        logInFindAndAcceptTrial(conf);
        verifyTrialStatus(reg, conf);

    }

    @Test
    public void testInvalidCredentials() throws Exception {
        super.testInvalidCredentials();
    }

    @Test
    public void testValidCredentialsButNoRole() throws Exception {
        super.testValidCredentialsButNoRole();
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        registerAndVerify("/integration_register_complete_success_no_dcp.xml");
    }

    @Test
    public void testRegisterNonCtGovIgnoreSections() throws Exception {
        registerAndVerify("/integration_register_complete_non_ctgov_but_with_data.xml");
    }

    @Test
    public void testRegisterNonCtGov() throws Exception {
        registerAndVerify("/integration_register_complete_nonCtGov_success.xml");
    }

    @Test
    public void testRegisterNonInterventional() throws Exception {
        registerAndVerify("/integration_register_complete_nonInt_success.xml");
    }

    @Test
    public void testRegisterWithNewOrgsAndPersons() throws Exception {
        registerAndVerify("/integration_register_complete_new_orgs_persons_success.xml");
    }

    @Test
    public void testRegisterWithOrgsAndPersonsIdentifiedByCtepID()
            throws Exception {
        registerAndVerify("/integration_register_complete_orgs_persons_ctepid.xml");
    }

    @Test
    public void testRegisterResponsiblePartyPI() throws Exception {
        registerAndVerify("/integration_register_complete_resp_party_pi.xml");
    }

    @Test
    public void testRegisterResponsiblePartySI() throws Exception {
        registerAndVerify("/integration_register_complete_resp_party_si.xml");
    }

    @Test
    public void testRegisterSchemaValidation() throws Exception {
        verifyFailureToRegister(
                "/integration_register_complete_schema_violation.xml", 400,
                "cvc-enumeration-valid");
    }

    @Test
    public void testRegisterBusinessValidation() throws Exception {
        verifyFailureToRegister(
                "/integration_register_complete_validation_error.xml", 400,
                "Validation Exception");
    }

    @Test
    public void testRegisterMinimalData() throws Exception {
        registerAndVerify("/integration_register_complete_minimal_dataset.xml");
    }

    @Test
    public void testInvalidRegistrationTransactionRollback() throws Exception {
        final QueryRunner runner = new QueryRunner();
        try {
            runner.update(
                    connection,
                    "update pa_properties set name = '_trial.register.body' where name = 'trial.register.body'");
            for (int i = 0; i <= 3; i++) {
                int countBefore = ((Number) runner.query(connection,
                        "select count(*) from study_protocol",
                        new ArrayHandler())[0]).intValue();
                verifyFailureToRegister(
                        "/integration_register_complete_invalid_startdate_dataset.xml",
                        500,
                        "PA_PROPERTIES does not have entry for  trial.register.body");
                int countAfter = ((Number) runner.query(connection,
                        "select count(*) from study_protocol",
                        new ArrayHandler())[0]).intValue();
                assertEquals(
                        i
                                + ") Trial registration ran non-transactionally and left junk in the database!!!",
                        countBefore, countAfter);
            }
        } finally {
            runner.update(
                    connection,
                    "update pa_properties set name = 'trial.register.body' where name = '_trial.register.body'");
        }
    }

    @Test
    public void testRegisterFundedByNciGrant() throws Exception {
        registerAndVerify("/integration_register_complete_nci_grant.xml");
    }

    @Override
    protected void verifyTrialStatus(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) {
        super.verifyTrialStatus(reg, conf); // To change body of overridden
                                            // methods use File | Settings |
                                            // File Templates.
    }

    @Test
    public void testRegistrationWithoutTrialOwnerElement_PO9091()
            throws Exception {
        registerAndVerify("/integration_register_complete_no_owner.xml");
    }

    private void verifyFailureToRegister(String file, int code,
            String expectedErrMsg) throws JAXBException, SAXException,
            UnsupportedEncodingException, ClientProtocolException, IOException, SQLException {
        HttpResponse response = submitRegistrationAndReturnResponse(file);
        verifyResponseHasFailure(code, expectedErrMsg, response);
    }

    private TrialInfo registerAndVerify(String file) throws SQLException,
            ClientProtocolException, ParseException, IOException,
            JAXBException, SAXException {
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(file);
        deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
        restartEmailServer();
        TrialRegistrationConfirmation conf = registerTrialFromFile(file);
        verifyRegistration(conf, reg);
        TrialInfo trial = new TrialInfo();
        trial.id = conf.getPaTrialID();
        if (reg.isClinicalTrialsDotGovXmlRequired()) {
            assertEquals("false",
                    getTrialField(trial, "DELAYED_POSTING_INDICATOR")
                            .toString());
            assertEquals(3, server.getReceivedEmailSize());
            Iterator<SmtpMessage> emailIter = server.getReceivedEmail();
            for (int i = 0; emailIter.hasNext(); i++) {
                SmtpMessage email = (SmtpMessage) emailIter.next();
                String body = email.getBody();
                String subject = email.getHeaderValue("Subject");
                System.out.println(body);
                switch (i) {
                case 0:
                    verifyCreateBodyDSPWarning(body);
                    verifyCreateSubjectDSPWarning(subject);
                    break;
                case 1:
                    verifyCreateBodyDSPCTRO(body);
                    verifyCreateSubjectDSPCTRO(subject);
                    break;
                default:
                    break;
                }
            }
        }
        return trial;
    }

    /**
     * @param subject
     */
    private void verifyCreateSubjectDSPWarning(final String subject) {
        assertFalse(subject.contains("<table>"));
        assertTrue(subject.contains("NCI CTRP: Trial RECORD CREATED for"));
    }

    /**
     * @param body
     */
    private void verifyCreateBodyDSPWarning(final String body) {
        assertTrue(body
                .contains("WARNING:</b> The trial submitted has a Delayed Posting Indicator value of \"Yes\""));
    }

    /**
     * @param subject
     */
    private void verifyCreateSubjectDSPCTRO(final String subject) {
        assertFalse(subject.contains("<table>"));
        assertTrue(subject.contains("Delayed Posting Indicator set to \"Yes\""));
    }

    /**
     * @param body
     */
    private void verifyCreateBodyDSPCTRO(final String body) {
        assertTrue(body
                .contains("Dear CTRO Staff,</p><p>The trial below was submitted with the value for the Delayed Posting Indicator set to \"Yes\":"));
    }

}
