package gov.nih.nci.accrual.accweb.integration;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.test.integration.AbstractRestServiceTest;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.SystemUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.xml.sax.SAXException;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class SearchTrialTest extends AbstractRestServiceTest {
     @Override
     public void setUp() throws Exception {
         super.setUp("/sites/2");
         baseURL = "http://" + TestProperties.getServerHostname() + ":"
         + TestProperties.getServerPort() + "/services";
         
     }
     
     
     /** User affiliated to Org 3 - NCI Family
      * User has Family Submitter access
      * Trial's lead org is Org1- NCI Family
      * Trial's Participating Site PS is Org4 - NCI Family
      * User should be able to submit accruals
     **/
     @Test
     public void testUserSameaffiliatedOrgFamilySubmitter() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
             // PhantomJS keeps crashing on Linux CI box. No idea why at the
             // moment.
             return;
         }
        
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();
        setFamilySubmitter(true);
        login();
        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        final String subjectID = "su001";
        addSubject(subjectID);
        verifySubject(rConf, subjectID.toUpperCase());
        setFamilySubmitter(false);
     }
     
     
     /** User affiliated to Org 3 - NCI Family
      * User has Family Submitter access
      * Trial's lead org is Org9- Case Comprehensive Cancer Center Family
      * Trial's Participating Site PS is Org10 - Case Comprehensive Cancer Center
      * User should Not be able to submit accruals 
     **/
     @Test
     public void testUserdifferentaffiliatedOrgFamilySubmitter() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
             // PhantomJS keeps crashing on Linux CI box. No idea why at the
             // moment.
             return;
         }
        TrialRegistrationConfirmation rConf1 = register("/integration_register_complete_Family2_LeadOrg.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_new_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf1.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        TrialInfo info = new TrialInfo();
        info.id = rConf1.getPaTrialID();
        addDWS(info,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.name());
        
        setFamilySubmitter(true);
        login();
        clickAndWait("link=Trial Search");
        assertFalse(selenium.isElementPresent("link=" + rConf1.getNciTrialID()));
        setFamilySubmitter(false);
     }
     
     /** User affiliated to Org 3 - NCI Family
      * User has Family Submitter access
      * Trial's lead org is Org1-  NCI Family
      * Trial's Participating Site PS is Org10 - Case Comprehensive Cancer Center
      * User should be able to see the trial.. But as the PS is not user's affiliated family.
      *  User should not see any organizations list in the add subject page
     **/
     @Test
     public void testUserSameaffiliatedOrgAndLeadOrg() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
             // PhantomJS keeps crashing on Linux CI box. No idea why at the
             // moment.
             return;
         }
        
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_nonctgov_minimal_data.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_new_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
       
        TrialInfo info = new TrialInfo();
        info.id = rConf.getPaTrialID();
        addDWS(info,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.name());
        
        setFamilySubmitter(true);
        login();
        clickAndWait("link=Trial Search");
        assertTrue(selenium.isElementPresent("link=" + rConf.getNciTrialID()));
        clickAndWait("link=" + rConf.getNciTrialID());
        final String subjectID = "su001";
        clickAndWait("xpath=//i[@class='fa-plus']");
        selenium.type("identifier", subjectID);
        int numOptions = selenium.getXpathCount("//*[@id='organizationName']/option").intValue();
        assertTrue(numOptions==1);
        setFamilySubmitter(false);
     }
     
     
     /** User affiliated to Org 3 - NCI Family
      * User has Family Submitter access
      * Trial's lead org is Org9- Case Comprehensive Cancer Center Family
      * Trial's Participating Site PS is Org1 - NCI Family
      * User should Not be able to submit accruals 
     **/
     @Test
     public void testUserDifferentaffiliatedOrgAndLeadOrg() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
             // PhantomJS keeps crashing on Linux CI box. No idea why at the
             // moment.
             return;
         }
        
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_Family2_LeadOrg.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        TrialInfo info = new TrialInfo();
        info.id = rConf.getPaTrialID();
        addDWS(info,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.name());
        
        setFamilySubmitter(true);
        login();
        clickAndWait("link=Trial Search");
        assertFalse(selenium.isElementPresent("link=" + rConf.getNciTrialID()));
        setFamilySubmitter(false);
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
         TrialRegistrationConfirmation rConf = register("/integration_register_complete_nonctgov_minimal_data.xml");
         ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_family1.xml");
         HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
         assertEquals(200, getReponseCode(response));
         TrialInfo info = new TrialInfo();
         info.id = rConf.getPaTrialID();
         addDWS(info,
                 DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.name());
         return rConf;
     }
     
     @SuppressWarnings("deprecation")
     private void login() {
         openAndWait("/accrual");
         selenium.type("j_username", "submitter-ci");
         selenium.type("j_password", "pass");        
         clickAndWait("xpath=//i[@class='fa-arrow-circle-right']");
         clickAndWait("id=acceptDisclaimer");
     }
     
     protected void setFamilySubmitter(boolean value) throws SQLException {
         QueryRunner runner = new QueryRunner();
         String sql = "update registry_user set family_accrual_submitter= "+ value
                                 + " where first_name='Submitter'";
         runner.update(connection, sql);
     }
     
     /** User affiliated to Org 4 - NCI Family
      * User has site Submitter access
      * Trial's lead org is Org1- NCI Family
      * Trial's Participating Site PS is Org4 - NCI Family
      * User should be able to submit accruals for Org4
     **/
     @Test
     public void testUserSameaffiliatedOrgSiteSubmitter() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
             // PhantomJS keeps crashing on Linux CI box. No idea why at the
             // moment.
             return;
         }
        
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();
        setSiteSubmitter(true);
        login();
        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        final String subjectID = "su001";
        addSubject(subjectID);
        verifySubject(rConf, subjectID.toUpperCase());
        setSiteSubmitter(false);
     }
     
     
     
     /** User affiliated to Org 4 - NCI Family
      * User has site Submitter access
      * Trial's lead org is Org9- Case Comprehensive Cancer Center Family
      * Trial's Participating Site PS is Org4 - NCI Family
      * User should not be able to submit accruals for Org4
     **/
     @Test
     public void testUserdifferentaffiliatedOrgSiteSubmitter() throws Exception {
         if(isPhantomJS() && SystemUtils.IS_OS_LINUX) {
             // PhantomJS keeps crashing on Linux CI box. No idea why at the
             // moment.
             return; 
         }
         TrialRegistrationConfirmation rConf1 = register("/integration_register_complete_Family2_LeadOrg.xml");
         ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_family1.xml");
         HttpResponse response = addSite("pa", rConf1.getPaTrialID() + "", upd);
         assertEquals(200, getReponseCode(response));
         TrialInfo info = new TrialInfo();
         info.id = rConf1.getPaTrialID();
         addDWS(info,
               DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.name());
       
         setSiteSubmitter(true);
         login();
         clickAndWait("link=Trial Search");
         assertFalse(selenium.isElementPresent("link=" + rConf1.getNciTrialID()));
         setSiteSubmitter(false);
     }
     
     protected void setSiteSubmitter(boolean value) throws SQLException {
         QueryRunner runner = new QueryRunner();
         String sql = "update registry_user set site_accrual_submitter= "+ value
                                 + " where first_name='Submitter'";
         runner.update(connection, sql);
     }
}
