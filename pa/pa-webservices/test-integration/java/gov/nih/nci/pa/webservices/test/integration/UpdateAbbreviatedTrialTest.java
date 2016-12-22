package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.webservices.types.AbbreviatedTrialUpdate;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Denis G. Krylov
 * 
 */
public class UpdateAbbreviatedTrialTest extends AbstractRestServiceTest {

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp("/trials/abbreviated");
        deactivateAllTrials();
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
    public void testSchemaValidation() throws Exception {
        HttpResponse response = updateTrialFromFile("pa", "1",
                "/integration_update_abbr_schema_violation.xml");
        assertEquals(400, getReponseCode(response));

    }

    @Test
    public void testUpdate() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyUpdate(upd, uConf);
    }
    
    @Test
    public void testUpdateDoesNotResetCtroOverride() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        enableCtroOverride(rConf.getPaTrialID());

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);

        clickAndWait("link=NCI Specific Information");
        assertFalse(selenium.isTextPresent("Send XML to ClinicalTrials.gov?:"));
    }
    
    @Test
    public void testEmptyUpdate() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr_empty.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        clickAndWait("link=Trial History");
        clickAndWait("id=updatesId");
        assertTrue(selenium.isTextPresent("Nothing found to display"));
    }

    @Test
    public void testUpdateProperDocHandling() throws Exception {
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        makeAbbreviated(rConf);

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyTrialDocuments(upd, uConf);

        verifyDocument(reg.getParticipatingSitesDocument(),
                "Participating sites");
        verifyDocument(reg.getProtocolDocument(), "Protocol Document");
        verifyDocument(reg.getOtherDocument().get(0), "Other");
        // IRB & Consent from original registration should be gone.
        try {
            verifyDocument(reg.getIrbApprovalDocument(),
                    "IRB Approval Document");
            fail();
        } catch (Exception e) {

        }
        try {
            verifyDocument(reg.getInformedConsentDocument(),
                    "Informed Consent Document");
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void testUpdateRestrictedToOwners() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        removeOwners(rConf);

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody
                .contains("Updates to the trial can only be submitted by either an owner of the trial"
                        + " or a lead organization admin"));
    }
    
    @Test
    public void testUpdateRestrictedToAbbreviatedTrials() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
      
        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody
                .contains("Only abbreviated trials can be updated by this operation"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateExistingNctIDNotModified()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException, ClientProtocolException, ParseException,
            SQLException, IOException {

        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        clickAndWait("link=General Trial Details");
        selenium.select("id=otherIdentifierType", "label="
                + "ClinicalTrials.gov Identifier");
        selenium.type("id=otherIdentifierOrg", "NCT3049580349");
        clickAndWait("id=otherIdbtnid");

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        clickAndWait("link=Trial Identification");
        assertEquals(
                "NCT3049580349",
                getTrialIdentificationTableCellValue("ClinicalTrials.gov Identifier"));

    }

    @Test
    public void testUpdateByNciID() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("nci",
                rConf.getNciTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyUpdate(upd, uConf);
    }

    @Test
    public void testUpdateByCtepID() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        clickAndWait("link=General Trial Details");
        selenium.select("id=otherIdentifierType", "label=" + "CTEP Identifier");
        selenium.type("id=otherIdentifierOrg", "CTEP00001");
        clickAndWait("id=otherIdbtnid");
        makeAbbreviated(rConf);

        AbbreviatedTrialUpdate upd = readAbbreviatedTrialUpdateFromFile("/integration_update_abbr.xml");
        HttpResponse response = updateTrialFromJAXBElement("ctep", "CTEP00001",
                upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyUpdate(upd, uConf);
    }

    private void verifyUpdate(AbbreviatedTrialUpdate upd,
            TrialRegistrationConfirmation conf) {
        verifyTrialIdentification(upd, conf);
        verifyTrialHistory(upd, conf);
        verifyTrialDocuments(upd, conf);
    }

    private void verifyTrialDocuments(AbbreviatedTrialUpdate reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Related Documents");
        verifyDocument(reg.getIrbApprovalDocument(), "IRB Approval Document");
        verifyDocument(reg.getInformedConsentDocument(),
                "Informed Consent Document");
        verifyDocument(reg.getOtherDocument().isEmpty() ? null : reg
                .getOtherDocument().get(0), "Other");

    }

    private void verifyTrialIdentification(AbbreviatedTrialUpdate reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Identification");

        if (reg.getClinicalTrialsDotGovTrialID() != null) {
            assertEquals(
                    reg.getClinicalTrialsDotGovTrialID(),
                    getTrialIdentificationTableCellValue("ClinicalTrials.gov Identifier"));
        }

    }

    private void verifyTrialHistory(AbbreviatedTrialUpdate upd,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial History");
        clickAndWait("id=updatesId");
        assertTrue(selenium.isTextPresent("One item found"));
        assertTrue(selenium
                .isTextPresent("ClinicalTrials.gov Identifier was added"));
        assertTrue(selenium
                .isTextPresent("IRB Approval Document Document was uploaded"));
        assertTrue(selenium
                .isTextPresent("Informed Consent Document Document was uploaded"));
        assertTrue(selenium.isTextPresent("Other Document was uploaded"));
        clickAndWaitAjax("link=Acknowledge");
    }

    @SuppressWarnings("unchecked")
    protected HttpResponse updateTrialFromJAXBElement(String idType,
            String trialID, AbbreviatedTrialUpdate o)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<AbbreviatedTrialUpdate>(new QName(
                "gov.nih.nci.pa.webservices.types", "AbbreviatedTrialUpdate"),
                AbbreviatedTrialUpdate.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = submitEntityAndReturnResponse(entity,
                serviceURL + "/" + idType + "/" + trialID);
        return response;

    }
   

    @SuppressWarnings("unchecked")
    private AbbreviatedTrialUpdate readAbbreviatedTrialUpdateFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        AbbreviatedTrialUpdate o = ((JAXBElement<AbbreviatedTrialUpdate>) u
                .unmarshal(url)).getValue();
        return o;
    }

}
