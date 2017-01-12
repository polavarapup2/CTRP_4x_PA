package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class RegisterAbbreviatedTrialTest extends AbstractRestServiceTest {

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp("/trials/abbreviated/");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInvalidCredentials() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials(
                "nonexistentuserfortesting", "nonexistentuserfortesting");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + "/trials/abbreviated/NCT01721876";

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(401, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    @Test
    public void testValidCredentialsButNoRole() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials("curator",
                "pass");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + "/trials/abbreviated/NCT01721876";

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(403, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    @Test
    public void testNctValidation() throws Exception {
        String url = baseURL + "/trials/abbreviated/%20";
        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertEquals(400, getReponseCode(response));
        assertEquals("Please provide an ClinicalTrials.gov Identifier value.",
                IOUtils.toString(response.getEntity().getContent()));

        url = baseURL + "/trials/abbreviated/NCT_0124232";
        req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(req);
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertEquals(400, getReponseCode(response));
        assertEquals("Provided ClinicalTrials.gov Identifer is invalid.",
                IOUtils.toString(response.getEntity().getContent()));

        url = baseURL + "/trials/abbreviated/NCT2834908239048";
        req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(req);
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertEquals(404, getReponseCode(response));
        assertEquals(
                "A study with the given identifier is not found in ClinicalTrials.gov.",
                IOUtils.toString(response.getEntity().getContent()));

        String nctID = "NCT01920308";
        deactivateTrialByNctId(nctID);
        importByNctIdViaService(nctID);
        url = baseURL + "/trials/abbreviated/" + nctID;
        req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(req);
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertEquals(412, getReponseCode(response));
        assertEquals(
                "A study with the given identifier already exists in CTRP.",
                IOUtils.toString(response.getEntity().getContent()));

    }

    @Test
    public void testImportAbbreviatedSuccess() throws Exception {
        importAndVerify("NCT01721876");
    }

    private void importAndVerify(String nctID) throws SQLException,
            ClientProtocolException, ParseException, IOException, JAXBException {
        loginAsSuperAbstractor();

        deactivateTrialByNctId(nctID);
        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", nctID);
        selenium.select("id=identifierType", "NCT");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("Nothing found to display"));

        importByNctIdViaService(nctID);

        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", nctID);
        selenium.select("id=identifierType", "NCT");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        assertTrue(selenium.getText("id=displaySubmitterLink").contains(
                "ClinicalTrials.gov Import"));
        assertTrue(selenium.getText("id=td_CTGOV_value").contains(nctID));

        clickAndWait("id=ctGovImportLogMenuOption");
        selenium.type("id=nctIdentifier", nctID);
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("One item found."));
        assertEquals(nctID,
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[2]/a")
                        .trim());
        assertEquals("Success",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[7]")
                        .trim());
        assertEquals("New Trial",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[4]/a")
                        .trim());

    }

    private void importByNctIdViaService(String nctID)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        String url = baseURL + "/trials/abbreviated/" + nctID;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);

        HttpEntity resEntity = response.getEntity();
        TrialRegistrationConfirmation conf = unmarshalTrialRegistrationConfirmation(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertTrue(StringUtils.isNotBlank(conf.getNciTrialID()));
        assertNotNull(conf.getPaTrialID());
        assertEquals(getTrialIdByNct(nctID).longValue(), conf.getPaTrialID());

    }
    @Test
    public void testImportTrial() throws Exception {
        importAndVerify("NCT01033123");
        assertEquals(
                "false",
                new QueryRunner()
                        .query(connection,
                                "select expd_access_indidicator from study_protocol where nct_id='NCT01033123'",
                                new ArrayHandler())[0]
                        + "");
        
    }

    @Test
    public void testImportTrialExpandedAccessYes() throws Exception {
        importAndVerify("NCT00338442");
        assertEquals(
                "true",
                new QueryRunner()
                        .query(connection,
                                "select expd_access_indidicator from study_protocol where nct_id='NCT00338442'",
                                new ArrayHandler())[0]
                        + "");

    }

}
