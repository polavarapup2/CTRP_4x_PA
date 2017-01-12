/**
 * 
 */
package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.ParticipatingSiteUpdate;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.Sites;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author dkrylov
 * 
 */
public final class ParticipatingSiteServiceTest extends AbstractRestServiceTest {

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp("/trials/nci/NCI-2014-00496/sites");
        deactivateAllTrials();
        setupFamilies();
    }

    @Test
    public void testAddCancerCenterSiteWithProgramCodes() throws Exception {
        final String xmlFile = "/integration_ps_add_cancer_center.xml";
        TrialRegistrationConfirmation rConf = register("/integration_register_cancer_center_with_program_codes.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());

        TrialInfo trial = new TrialInfo();
        trial.id = rConf.getPaTrialID();
        assignProgramCode(trial, 2, "PG4");
        assertEquals(
                Arrays.asList(new String[] { "PG1", "PG3", "PG4", "PG6" }),
                getTrialProgramCodes(trial));

        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        verifySite(rConf, siteID, upd);

    }

    @Test
    public void testAddNonCancerCenterSiteWithProgramCodes() throws Exception {
        final String xmlFile = "/integration_ps_add_non_cancer_center.xml";
        TrialRegistrationConfirmation rConf = register("/integration_register_cancer_center_with_program_codes.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());

        assertEquals(Arrays.asList(new String[] { "PG1", "PG6" }),
                getTrialProgramCodes(rConf.getPaTrialID()));

        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        verifySite(rConf, siteID, upd);

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
    public void testAddSiteWithMinimalInfo() throws Exception {
        final String xmlFile = "/integration_ps_add_minimum_info.xml";
        addSite(xmlFile);
    }
    
    
    @Test
    public void testDuplicatePS() throws Exception {
        final String xmlFile = "/integration_ps_add.xml";
        
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        verifySite(rConf, siteID, upd);
        upd = readParticipatingSiteFromFile(xmlFile);
        response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(500, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        
    }

    @Test
    public void testAddSiteWithPrimaryContact() throws Exception {
        final String xmlFile = "/integration_ps_with_pc_add.xml";
        addSite(xmlFile);
    }

    @Test
    public void testAddSiteWithPrimaryContactConflictingDesignation()
            throws Exception {
        final String xmlFile = "/integration_ps_with_pc_conflicting_designation.xml";
        addSite(xmlFile);
    }

    @Test
    public void testAddSiteWithExistingPersonAsPrimaryContact()
            throws Exception {
        final String xmlFile = "/integration_ps_with_pc_as_existing_person_add.xml";
        addSite(xmlFile);
    }

    @Test
    public void testAddSite() throws Exception {
        final String xmlFile = "/integration_ps_add.xml";
        addSite(xmlFile);
    }

    @Test
    public void testAddSiteWithSubInvestigator() throws Exception {
        final String xmlFile = "/integration_ps_sub_investigator_add.xml";
        addSite(xmlFile);
    }

    @Test
    public void testAddSiteWithNoPrimaryContact() throws Exception {
        final String xmlFile = "/integration_ps_without_primary_contact_add.xml";
        addSite(xmlFile);
    }

    @Test
    public void testAddSiteSchemaViolation() throws Exception {
        final String xmlFile = "/integration_ps_schema_violation_add.xml";

        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
    }

    @Test
    public void testAddSiteBizValidation() throws Exception {
        final String xmlFile = "/integration_ps_biz_validation_add.xml";

        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("nci", rConf.getNciTrialID() + "", upd);
        assertEquals(500, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("Date Opened for Accrual must be null for In Review"));
    }

    @Test
    public void testGetSites() throws Exception {
        final String xmlFile = "/integration_ps_add.xml";

        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        EntityUtils.consumeQuietly(response.getEntity());

        String url = baseURL + "/trials/nci/" + rConf.getNciTrialID()
                + "/sites";
        LOG.info("Hitting " + url);
        HttpGet req = new HttpGet(url);
        req.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(req);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        
        verifyWebServiceAccessLogEntry(url, req,
                new StringEntity(""), response);

        JAXBContext jaxbContext = JAXBContext.newInstance(Sites.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgXMLStr = EntityUtils.toString(response.getEntity(), "utf-8");
        JAXBElement<Sites> jaxbEle = (JAXBElement<Sites>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(orgXMLStr)),
                        Sites.class);
        Sites sites = jaxbEle.getValue();
        assertEquals(1, sites.getSite().size());
        ParticipatingSite site = sites.getSite().get(0);
        assertEquals(upd.getLocalTrialIdentifier(),
                site.getLocalTrialIdentifier());
        assertEquals(upd.getProgramCode(), site.getProgramCode());
        assertTrue(DateUtils.isSameDay(upd.getClosedForAccrual()
                .toGregorianCalendar(), site.getClosedForAccrual()
                .toGregorianCalendar()));
        assertTrue(DateUtils.isSameDay(upd.getOpenedForAccrual()
                .toGregorianCalendar(), site.getOpenedForAccrual()
                .toGregorianCalendar()));
        assertEquals(upd.getRecruitmentStatus(), site.getRecruitmentStatus());
        assertTrue(DateUtils.isSameDay(upd.getRecruitmentStatusDate()
                .toGregorianCalendar(), site.getRecruitmentStatusDate()
                .toGregorianCalendar()));
        assertEquals(upd.getTargetAccrualNumber(),
                site.getTargetAccrualNumber());
        assertEquals(upd.getInvestigator().get(0).getPerson()
                .getExistingPerson().getPoID(), site.getInvestigator().get(0)
                .getPerson().getExistingPerson().getPoID());
        assertEquals(upd.getInvestigator().get(0).getRole(), site
                .getInvestigator().get(0).getRole());
        assertEquals(upd.getInvestigator().get(0).isPrimaryContact(), site
                .getInvestigator().get(0).isPrimaryContact());
        assertEquals(upd.getOrganization().getExistingOrganization().getPoID(),
                site.getOrganization().getExistingOrganization().getPoID());

    }

    @Test
    public void testUpdateSiteBySiteID() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update.xml");
        response = updateSite(siteID, upd);
        verifyUpdateSiteResponse(response, siteID);
        verifySiteUpdate(siteID, upd);

    }

    @Test
    public void testUpdateSiteWithDifferentPrimaryContact() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update_with_primary_contact.xml");
        response = updateSite(siteID, upd);
        verifyUpdateSiteResponse(response, siteID);
        verifySiteUpdate(siteID, upd);

    }

    @Test
    public void testUpdateSiteWithDifferentPrimaryContactExistingPerson()
            throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update_with_primary_contact_existing_person.xml");
        response = updateSite(siteID, upd);
        verifyUpdateSiteResponse(response, siteID);
        verifySiteUpdate(siteID, upd);

    }

    @Test
    public void testUpdateSiteWithDuplicateSiteStatus() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update_duplicateSite.xml");
        response = updateSite(siteID, upd);
        verifyUpdateSiteResponse(response, siteID);
        verifySiteUpdate(siteID, upd);
        assertTrue(getSiteValue(siteID).contains("Deleted because another  record with the same site status"));
        
    }
    
    private String getSiteValue(long siteID) throws SQLException {
            QueryRunner runner = new QueryRunner();
            String sql = "select comments from study_site_accrual_status where study_site_identifier =" +siteID + 
              " and deleted=TRUE and status_code='ADMINISTRATIVELY_COMPLETE'";
            return (String) runner.query(connection, sql,new ArrayHandler())[0];
    }
    
    @Test
    public void testUpdateSiteWithMinimalInfo() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update_min.xml");
        response = updateSite(siteID, upd);
        verifyUpdateSiteResponse(response, siteID);
        verifySiteUpdate(siteID, upd);

    }

    @Test
    public void testUpdateSiteSchemaViolation() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update_bad_schema.xml");
        response = updateSite(siteID, upd);
        assertEquals(400, getReponseCode(response));

    }

   @Test
    public void testUpdateSiteByPoId() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));

        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update.xml");
        response = updateSiteByPoId(rConf, 5, upd);
        verifyUpdateSiteResponse(response, siteID);
        verifySiteUpdate(siteID, upd);

    } 

    @Test
    public void testUpdateSiteByCtepID() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite newSite = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "",
                newSite);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));

        ParticipatingSiteUpdate upd = readParticipatingSiteUpdateFromFile("/integration_ps_update.xml");
        response = updateSiteByCtepId(rConf, "CCR", upd);
        verifyUpdateSiteResponse(response, siteID);
        verifySiteUpdate(siteID, upd);

    }
    
    @Test
    public void testAddSiteWithInvalidDate() throws Exception {
        final String xmlFile = "/integration_ps_phone_validation_add.xml";
        
        
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        long beforeCount =getParticipatingSiteCount(rConf.getPaTrialID()); 
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("nci", rConf.getNciTrialID() + "", upd);
        assertEquals(500, getReponseCode(response));
        assertTrue(EntityUtils.toString(response.getEntity(), "utf-8")
                .contains("Invalid phone number: 70-035556666 format for USA or CANADA is xxx-xxx-xxxxextxxxx"));
        long afterCount =getParticipatingSiteCount(rConf.getPaTrialID());
        
        //check if this site is not added in database
        assertTrue(beforeCount ==afterCount);
    }

    private HttpResponse updateSiteByPoId(TrialRegistrationConfirmation rConf,
            int poID, ParticipatingSiteUpdate o) throws JAXBException,
            ClientProtocolException, IOException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<ParticipatingSiteUpdate>(new QName(
                "gov.nih.nci.pa.webservices.types", "ParticipatingSiteUpdate"),
                ParticipatingSiteUpdate.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = putEntityAndReturnResponse(entity,
                "/trials/nci/" + rConf.getNciTrialID() + "/sites/po/" + poID,
                TEXT_PLAIN, APPLICATION_XML);
        return response;
    }

    private HttpResponse updateSiteByCtepId(
            TrialRegistrationConfirmation rConf, String ctepID,
            ParticipatingSiteUpdate o) throws JAXBException,
            ClientProtocolException, IOException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<ParticipatingSiteUpdate>(new QName(
                "gov.nih.nci.pa.webservices.types", "ParticipatingSiteUpdate"),
                ParticipatingSiteUpdate.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = putEntityAndReturnResponse(entity,
                "/trials/nci/" + rConf.getNciTrialID() + "/sites/ctep/"
                        + ctepID, TEXT_PLAIN, APPLICATION_XML);
        return response;
    }

    /**
     * @param response
     * @param siteID
     * @throws NumberFormatException
     * @throws IOException
     * @throws ParseException
     */
    private void verifyUpdateSiteResponse(HttpResponse response, long siteID)
            throws NumberFormatException, IOException, ParseException {
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertEquals(siteID, Long.parseLong(EntityUtils.toString(
                response.getEntity(), "utf-8")));
    }

    /**
     * @param xmlFile
     * @throws JAXBException
     * @throws SAXException
     * @throws SQLException
     * @throws ClientProtocolException
     * @throws ParseException
     * @throws IOException
     * @throws NumberFormatException
     */
    private long addSite(final String xmlFile) throws JAXBException,
            SAXException, SQLException, ClientProtocolException,
            ParseException, IOException, NumberFormatException {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);
        ParticipatingSite upd = readParticipatingSiteFromFile(xmlFile);
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        verifySite(rConf, siteID, upd);
        return siteID;
    }

    private void verifySite(TrialRegistrationConfirmation rConf, long siteID,
            ParticipatingSite ps) throws SQLException {

        assertEquals(getLastParticipatingSiteId().toString(), siteID + "");
        clickAndWait("link=Participating Sites");
        clickAndWait("xpath=//table[@id='row']/tbody/tr[1]//td[7]//img");

        if (ps.getOrganization().getExistingOrganization() != null) {
            assertEquals(ps.getOrganization().getExistingOrganization()
                    .getPoID().toString(),
                    selenium.getValue("id=editOrg.identifier"));
        } else {
            assertEquals(ps.getOrganization().getNewOrganization().getName(),
                    selenium.getValue("id=editOrg.name"));
        }

        verifyBaseSiteInfo(ps);
        verifyLegacyProgramCode(ps, rConf);

    }

    @SuppressWarnings("deprecation")
    private void verifyLegacyProgramCode(ParticipatingSite ps,
            TrialRegistrationConfirmation rConf) throws SQLException {
        clickLinkAndWait("NCI Specific Information");
        for (String code : getTrialProgramCodes(rConf.getPaTrialID())) {
            assertTrue(s
                    .isElementPresent("xpath=//li[@class='select2-selection__choice' and normalize-space(text())='"
                            + code + "']"));
        }
    }

    private void verifySiteUpdate(long siteID, ParticipatingSiteUpdate ps)
            throws SQLException {

        clickAndWait("link=Participating Sites");
        clickAndWait("xpath=//table[@id='row']/tbody/tr[1]//td[7]//img");
        verifyBaseSiteInfo(ps);

    }

    /**
     * @param ps
     */
    @SuppressWarnings("deprecation")
    private void verifyBaseSiteInfo(BaseParticipatingSite ps) {
        assertEquals(ps.getLocalTrialIdentifier(),
                selenium.getValue("id=siteLocalTrialIdentifier"));
        assertEquals(ps.getRecruitmentStatus().value(),
                selenium.getValue("id=recStatus"));
        assertEquals(DateFormatUtils.format(ps.getRecruitmentStatusDate()
                .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=recStatusDate"));
        assertEquals(StringUtils.defaultString(ps.getProgramCode()),
                selenium.getValue("id=programCode"));
        assertEquals(ps.getTargetAccrualNumber() != null ? ps
                .getTargetAccrualNumber().toString() : "",
                selenium.getValue("id=targetAccrualNumber"));
        assertEquals(
                ps.getOpenedForAccrual() != null ? DateFormatUtils.format(ps
                        .getOpenedForAccrual().toGregorianCalendar(),
                        "MM/dd/yyyy") : "",
                selenium.getValue("id=participatingOrganizationsedit_dateOpenedForAccrual"));
        assertEquals(
                ps.getClosedForAccrual() != null ? DateFormatUtils.format(ps
                        .getClosedForAccrual().toGregorianCalendar(),
                        "MM/dd/yyyy") : "",
                selenium.getValue("id=participatingOrganizationsedit_dateClosedForAccrual"));

        selenium.click("link=Investigators");
        final Person person = ps.getInvestigator().get(0).getPerson();
        if (person.getExistingPerson() != null) {
            assertEquals(
                    person.getExistingPerson().getPoID().toString(),
                    selenium.getText("xpath=//div[@id='saveAndShowContacts']//table[@id='row']//tr[1]//td[1]/a"));
            assertEquals(
                    ps.getInvestigator().get(0).getRole(),
                    selenium.getText("xpath=//div[@id='saveAndShowContacts']//table[@id='row']//tr[1]//td[4]"));
        } else {
            assertEquals(
                    person.getNewPerson().getFirstName(),
                    selenium.getText("xpath=//div[@id='saveAndShowContacts']//table[@id='row']//tr[1]//td[3]"));
            assertEquals(
                    person.getNewPerson().getLastName(),
                    selenium.getText("xpath=//div[@id='saveAndShowContacts']//table[@id='row']//tr[1]//td[2]"));
        }

        selenium.click("link=Contact");
        if (ps.getInvestigator().get(0).isPrimaryContact()
                && ps.getPrimaryContact() == null) {
            assertEquals(person.getExistingPerson().getPoID().toString(),
                    selenium.getValue("id=personContactWebDTO.selectedPersId"));
        } else {
            if (ps.getPrimaryContact() == null) {
                assertEquals(
                        "",
                        selenium.getValue("id=personContactWebDTO.selectedPersId"));
            } else {
                if (ps.getPrimaryContact().getPerson().getNewPerson() != null) {
                    assertEquals(ps.getPrimaryContact().getPerson()
                            .getNewPerson().getFirstName(),
                            s.getValue("personContactWebDTO.firstName"));
                    assertEquals(ps.getPrimaryContact().getPerson()
                            .getNewPerson().getLastName(),
                            s.getValue("personContactWebDTO.lastName"));
                } else {
                    assertEquals(
                            ps.getPrimaryContact().getPerson()
                                    .getExistingPerson().getPoID().toString(),
                            selenium.getValue("id=personContactWebDTO.selectedPersId"));
                }
                assertEquals(ps.getPrimaryContact().getContactDetails()
                        .getContent().get(0).getValue(),
                        s.getValue("personContactWebDTO.email"));
                assertEquals(ps.getPrimaryContact().getContactDetails()
                        .getContent().size() > 1 ? ps.getPrimaryContact()
                        .getContactDetails().getContent().get(1).getValue()
                        : "", s.getValue("personContactWebDTO.telephone"));

            }
        }
    }

    private Number getLastParticipatingSiteId() throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner
                .query(connection,
                        "select identifier from study_site order by identifier desc limit 1",
                        new ArrayHandler())[0];
    }
    
    private Long getParticipatingSiteCount(long studyId) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Long) runner
                .query(connection,
                        "select count(*) from study_site where study_protocol_identifier="+studyId,
                        new ArrayHandler())[0];
    }

    @SuppressWarnings("unchecked")
    protected HttpResponse updateSite(long siteID, ParticipatingSiteUpdate o)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<ParticipatingSiteUpdate>(new QName(
                "gov.nih.nci.pa.webservices.types", "ParticipatingSiteUpdate"),
                ParticipatingSiteUpdate.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = putEntityAndReturnResponse(entity, "/sites/"
                + siteID, TEXT_PLAIN, APPLICATION_XML);
        return response;

    }

    @SuppressWarnings("unchecked")
    protected ParticipatingSiteUpdate readParticipatingSiteUpdateFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        ParticipatingSiteUpdate o = ((JAXBElement<ParticipatingSiteUpdate>) u
                .unmarshal(url)).getValue();
        return o;
    }

}
