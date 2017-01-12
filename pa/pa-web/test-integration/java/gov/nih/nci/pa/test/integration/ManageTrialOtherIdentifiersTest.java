/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;

@Batch(number = 1)
public class ManageTrialOtherIdentifiersTest extends OtherIdentifiersRelatedTest {

    /**
     * Tests logging in as abstractor.
     * 
     * @throws Exception
     *             on error
     */
    @Test
    public void testAddIdentifiers() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        goToGTDScreen(trial);

        verifyStudySiteAssignedIdentifier(trial,
                "ClinicalTrials.gov Identifier", UUID.randomUUID().toString());
        verifyStudySiteAssignedIdentifier(trial, "CTEP Identifier", UUID
                .randomUUID().toString());
        verifyStudySiteAssignedIdentifier(trial, "DCP Identifier", UUID
                .randomUUID().toString());
        verifyStudySiteAssignedIdentifier(trial, "CCR Identifier", UUID
                .randomUUID().toString());        
        verifyOtherIdentifier(trial, "Duplicate NCI Identifier", UUID
                .randomUUID().toString());
        verifyOtherIdentifier(trial, "Obsolete ClinicalTrials.gov Identifier",
                UUID.randomUUID().toString());
        verifyOtherIdentifier(trial, "Other Identifier", UUID.randomUUID()
                .toString());

    }

    @Test
    public void testEditIdentifiers() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        goToGTDScreen(trial);

        verifyEditLeadOrgIdentifier(trial, UUID.randomUUID().toString());
        verifyEditStudySiteAssignedIdentifier(trial,
                "ClinicalTrials.gov Identifier", UUID.randomUUID().toString());
        verifyEditStudySiteAssignedIdentifier(trial, "CTEP Identifier", UUID
                .randomUUID().toString());
        verifyEditStudySiteAssignedIdentifier(trial, "DCP Identifier", UUID
                .randomUUID().toString());
        verifyEditStudySiteAssignedIdentifier(trial, "CCR Identifier", UUID
                .randomUUID().toString());
        verifyEditOtherIdentifier(trial, "Duplicate NCI Identifier", UUID
                .randomUUID().toString());
        verifyEditOtherIdentifier(trial,
                "Obsolete ClinicalTrials.gov Identifier", UUID.randomUUID()
                        .toString());
        verifyEditOtherIdentifier(trial, "Other Identifier", UUID.randomUUID()
                .toString());

       
    }

    @Test
    public void testValidation() throws Exception {
        TrialInfo trial1 = createAcceptedTrial();
        TrialInfo trial2 = createAcceptedTrial();
        goToGTDScreen(trial2);

        verifyEditIdentifierFailure(
                trial2,
                "Lead Organization Trial ID",
                trial1.leadOrgID,
                "Duplicate Trial Submission: A trial exists in the system with the same Lead Organization Trial "
                        + "Identifier for the selected Lead Organization");

        String uuid = UUID.randomUUID().toString();
        addIdentifier("Other Identifier", uuid);
        addIdentifierWithFailure("Other Identifier", uuid,
                "An identifier with the given type and value already exists");
        addIdentifierWithFailure("Duplicate NCI Identifier", trial2.nciID,
                "Duplicate NCI Identifier cannot match NCI Identifier of this trial");

        // Test duplicate NCT ID
        logoutUser();
        goToGTDScreen(trial1);
        addIdentifier("ClinicalTrials.gov Identifier", uuid);
        logoutUser();
        goToGTDScreen(trial2);
        addIdentifierWithFailure("ClinicalTrials.gov Identifier", uuid,
                "The NCT Trial Identifier provided is tied to another trial in CTRP system");

        // Test Obsolete NCT ID cannot match the current one
        uuid = UUID.randomUUID().toString();
        addIdentifier("ClinicalTrials.gov Identifier", uuid);
        addIdentifierWithFailure(
                "Obsolete ClinicalTrials.gov Identifier",
                uuid,
                "Obsolete ClinicalTrials.gov Identifier cannot match the current ClinicalTrials.gov Identifier of this trial");

       
    }
    
    @SuppressWarnings({ "resource", "deprecation" })
    @Test
    public void testRemovalOfNctIdByDirectGETIsPrevented() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        goToGTDScreen(trial);

        final String nctID = UUID.randomUUID().toString();
        verifyStudySiteAssignedIdentifier(trial,
                "ClinicalTrials.gov Identifier", nctID);

        final String directURL = url
                + "/pa/protected/studyProtocolremoveNctId.action?studyProtocolId="
                + trial.id + "&hash=" + UUID.randomUUID();
        selenium.open(directURL);
        assertEquals("PA: Error", selenium.getTitle());
        
    }

    @Test
    public void testRemoveNctIdFromRejectedTrial() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        goToGTDScreen(trial);

        final String nctID = UUID.randomUUID().toString();
        final String ctepID = UUID.randomUUID().toString();
        final String dcpID = UUID.randomUUID().toString();
        
        verifyStudySiteAssignedIdentifier(trial, "CTEP Identifier", ctepID);
        verifyStudySiteAssignedIdentifier(trial, "DCP Identifier", dcpID);
        verifyStudySiteAssignedIdentifier(trial,
                "ClinicalTrials.gov Identifier", nctID);
        
        clickAndWait("link=Trial Identification");
        assertFalse(selenium.isElementPresent("id=removeNctIdIcon"));
        
        addDWS(trial, "REJECTED");
        addMilestone(trial, "LATE_REJECTION_DATE");
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("id=removeNctIdIcon"));
        
        // First, make sure Cancel button closes the dialog and the identifier
        // is NOT deleted.
        selenium.click("id=removeNctIdIcon");
        assertTrue(selenium.isVisible("id=confirmNctIdDialog"));
        assertTrue(selenium
                .isVisible("xpath=//p[text()='Please confirm you want to remove ClinicalTrials.gov Identifier from this trial']"));
        assertTrue(selenium
                .isVisible("xpath=//span[text()='Confirm ClinicalTrials.gov ID Removal']"));        
        selenium.click("xpath=//button/span[text()='Cancel']");
        assertFalse(selenium.isVisible("id=confirmNctIdDialog"));
        assertTrue(selenium.isElementPresent("id=removeNctIdIcon"));
        verifyStudySiteIdentifierAssignerInDb(trial, nctID);
        
        // Now actually delete.        
        selenium.click("id=removeNctIdIcon");
        assertTrue(selenium.isVisible("id=confirmNctIdDialog"));
        selenium.click("xpath=//button/span[text()='Confirm']");       
        assertFalse(selenium.isVisible("id=confirmNctIdDialog"));
        assertFalse(selenium.isVisible("id=removeNctIdIcon"));
        pause(5000);
        assertFalse(selenium.isVisible("id=td_CTGOV_value"));
        assertFalse(selenium.isTextPresent(nctID));
        Object[] results = getIdentifierAssignerStudySiteId(trial, nctID);
        assertNull(results);
        
        // reload page and make sure there is no NCT ID, but CTEP and DCP are in place.
        clickAndWait("link=Trial Identification");
        assertFalse(selenium.isTextPresent(nctID));
        assertFalse(selenium.isTextPresent("ClinicalTrials.gov Identifier"));
        verifyStudySiteIdentifierAssignerInDb(trial, dcpID);
        verifyStudySiteIdentifierAssignerInDb(trial, ctepID);
        assertTrue(selenium.isTextPresent(dcpID));
        assertTrue(selenium.isTextPresent(ctepID));

    }

    private void verifyEditIdentifierFailure(TrialInfo trial, String type,
            String newValue, String expectedErrorMsg) throws SQLException {
        editIdentifierAndExpectMessage("Lead Organization Trial ID",
                trial.leadOrgID, newValue, expectedErrorMsg);
        verifyNoLeadOrgAssignerInDb(trial, newValue);
        verifyLeadOrgAssignerInDb(trial, trial.leadOrgID);
    }

    private void verifyEditLeadOrgIdentifier(TrialInfo trial, String newValue)
            throws SQLException {
        editIdentifier("Lead Organization Trial ID", trial.leadOrgID, newValue);
        verifyNoLeadOrgAssignerInDb(trial, trial.leadOrgID);
        verifyLeadOrgAssignerInDb(trial, newValue);
    }

    @Test
    public void testDeleteIdentifiers() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        goToGTDScreen(trial);

        verifyDeleteStudySiteAssignedIdentifier(trial,
                "ClinicalTrials.gov Identifier", UUID.randomUUID().toString());
        verifyDeleteStudySiteAssignedIdentifier(trial, "CTEP Identifier", UUID
                .randomUUID().toString());
        verifyDeleteStudySiteAssignedIdentifier(trial, "DCP Identifier", UUID
                .randomUUID().toString());
        verifyDeleteStudySiteAssignedIdentifier(trial, "CCR Identifier", UUID
                .randomUUID().toString());
        verifyDeleteOtherIdentifier(trial, "Duplicate NCI Identifier", UUID
                .randomUUID().toString());
        verifyDeleteOtherIdentifier(trial,
                "Obsolete ClinicalTrials.gov Identifier", UUID.randomUUID()
                        .toString());
        verifyDeleteOtherIdentifier(trial, "Other Identifier", UUID
                .randomUUID().toString());

       
    }

    private void verifyOtherIdentifier(TrialInfo trial, String type,
            String value) throws SQLException {
        addIdentifier(type, value);
        verifyIdentifiersTableHasRow(type, value);
        verifyOtherIdentifierAssignerInDb(trial, value);
    }

    private void verifyDeleteOtherIdentifier(TrialInfo trial, String type,
            String value) throws SQLException {
        verifyOtherIdentifier(trial, type, value);
        deleteIdentifier(type, value);
        verifyNoOtherIdentifierAssignerInDb(trial, value);
    }

    private void verifyEditOtherIdentifier(TrialInfo trial, String type,
            String value) throws SQLException {
        verifyOtherIdentifier(trial, type, value);

        final String newValue = UUID.randomUUID().toString();
        editIdentifier(type, value, newValue);
        verifyNoOtherIdentifierAssignerInDb(trial, value);
        verifyOtherIdentifierAssignerInDb(trial, newValue);
    }

    private void verifyDeleteStudySiteAssignedIdentifier(TrialInfo trial,
            String type, String value) throws SQLException {
        verifyStudySiteAssignedIdentifier(trial, type, value);
        deleteIdentifier(type, value);
        verifyNoStudySiteIdentifierAssignerInDb(trial, value);
    }

    private void verifyEditStudySiteAssignedIdentifier(TrialInfo trial,
            String type, String value) throws SQLException {
        verifyStudySiteAssignedIdentifier(trial, type, value);
        final String newValue = UUID.randomUUID().toString();
        editIdentifier(type, value, newValue);
        verifyNoStudySiteIdentifierAssignerInDb(trial, value);
        verifyStudySiteIdentifierAssignerInDb(trial, newValue);
    }

    /**
     * @param type
     * @param value
     */
    private void editIdentifier(String type, String value, String newValue) {
        final String message = "New identifier value saved";
        editIdentifierAndExpectMessage(type, value, newValue, message);
    }

    /**
     * @param type
     * @param value
     * @param newValue
     * @param message
     */
    private void editIdentifierAndExpectMessage(String type, String value,
            String newValue, final String message) {
        int rowNum = findIdentifierRowIndex(type, value);
        selenium.click("id=otherIdEditBtn_" + rowNum);
        selenium.type("id=identifier_" + rowNum, newValue);
        selenium.click("id=otherIdSaveBtn_" + rowNum);
        try {
            waitForTextToAppear(By.className("confirm_msg"), message,
                    WAIT_FOR_ELEMENT_TIMEOUT);
        } catch (TimeoutException e) {
            waitForTextToAppear(By.className("error_msg"), message,
                    WAIT_FOR_ELEMENT_TIMEOUT);
        }
    }

    /**
     * @param type
     * @param value
     */
    private void deleteIdentifier(String type, String value) {
        int rowNum = findIdentifierRowIndex(type, value);
        //selenium.chooseOkOnNextConfirmation();
        //selenium.click("id=otherIdDeleteBtn_" + rowNum);
        ((JavascriptExecutor) driver).executeScript("deleteOtherIdentifierRow('"+rowNum+"');");
        waitForTextToAppear(By.className("confirm_msg"),
                "Identifier deleted from the trial", WAIT_FOR_ELEMENT_TIMEOUT);
    }

    private int findIdentifierRowIndex(String type, String value) {
        int counter = 0;
        while (counter < 10) {
            counter++;
            if (selenium.isElementPresent("id=identifierTypeDiv_" + counter)) {
                if (StringUtils.trim(
                        selenium.getText("id=identifierTypeDiv_" + counter))
                        .contains(type)
                        && StringUtils
                                .trim(selenium.getText("id=identifierDiv_"
                                        + counter)).equals(value)) {
                    return counter;
                }
            }
        }
        throw new RuntimeException(
                "Unable to determine in which table row the identifier is: "
                        + value);
    }

    /**
     * @param type
     * @param value
     */
    private void addIdentifierWithFailure(String type, String value, String msg) {
        selenium.select("id=otherIdentifierType", "label=" + type);
        selenium.type("id=otherIdentifierOrg", value);
        clickAndWait("id=otherIdbtnid");
        waitForTextToAppear(By.className("error_msg"), msg,
                WAIT_FOR_ELEMENT_TIMEOUT);
    }

    private void verifyLeadOrgAssignerInDb(TrialInfo trial, String value)
            throws SQLException {
        Object[] results = getStudySiteIdByValueAndCode(trial, value,
                "LEAD_ORGANIZATION");
        assertNotNull(results);
        assertTrue(results[0] instanceof Number);
    }

    private void verifyNoLeadOrgAssignerInDb(TrialInfo trial, String value)
            throws SQLException {
        Object[] results = getStudySiteIdByValueAndCode(trial, value,
                "LEAD_ORGANIZATION");
        assertNull(results);
    }

    private void verifyNoStudySiteIdentifierAssignerInDb(TrialInfo trial,
            String value) throws SQLException {
        Object[] results = getIdentifierAssignerStudySiteId(trial, value);
        assertNull(results);
    }

    private void verifyOtherIdentifierAssignerInDb(TrialInfo trial, String value)
            throws SQLException {
        Object[] results = getStudyOtherIdentifierProtocolID(trial, value);
        assertNotNull(results);
        assertTrue(results[0] instanceof Number);
    }

    private void verifyNoOtherIdentifierAssignerInDb(TrialInfo trial,
            String value) throws SQLException {
        Object[] results = getStudyOtherIdentifierProtocolID(trial, value);
        assertNull(results);

    }

    /**
     * @param trial
     * @param value
     * @return
     * @throws SQLException
     */
    private Object[] getStudyOtherIdentifierProtocolID(TrialInfo trial,
            String value) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql = "select study_protocol_id from study_otheridentifiers ss where ss.study_protocol_id="
                + trial.id
                + " and ss.root='2.16.840.1.113883.19' and ss.extension='"
                + value + "'";
        Object[] results = r.query(connection, sql, new ArrayHandler());
        return results;
    }
}
