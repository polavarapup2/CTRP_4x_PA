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
package gov.nih.nci.pa.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.IdentifierType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import gov.nih.nci.pa.enums.StudyStatusCode;
import org.apache.commons.beanutils.PropertyUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

/**
 * @author Michael Visee
 */
public class StudyProtocolQueryCriteriaTest {
    private static final String ID = "identifier value";
    private StudyProtocolQueryCriteria sut = new StudyProtocolQueryCriteria();

    /**
     * Test the setIdentifier method for the CTEP case.
     */
    
    @Test
    public void testSetIdentifierAll() {
        sut.setCtepIdentifier(ID);
        sut.setDcpIdentifier(ID);
        sut.setNctNumber(ID);
        sut.setLeadOrganizationTrialIdentifier(ID);
        sut.setNciIdentifier(ID);
        sut.setOtherIdentifier(ID);

        sut.setIdentifierType("All");
        sut.setIdentifier(ID);

        assertEquals("Wrong identifier value", ID, sut.getAnyTypeIdentifier());
        assertNull(sut.getCtepIdentifier());
        assertNull(sut.getDcpIdentifier());
        assertNull(sut.getNctNumber());
        assertNull(sut.getLeadOrganizationTrialIdentifier());
        assertNull(sut.getNciIdentifier());
        assertNull(sut.getOtherIdentifier());
    }
    
    @Test
    public void testSetIdentifierCTEP() {
        sut.setIdentifierType(IdentifierType.CTEP.getCode());
        sut.setIdentifier(ID);
        assertEquals("Wrong identifier value", ID, sut.getCtepIdentifier());
    }

    /**
     * Test the setIdentifier method for the DCP case.
     */
    @Test
    public void testSetIdentifierDCP() {
        sut.setIdentifierType(IdentifierType.DCP.getCode());
        sut.setIdentifier(ID);
        assertEquals("Wrong identifier value", ID, sut.getDcpIdentifier());
    }

    /**
     * Test the setIdentifier method for the LEAD_ORG case.
     */
    @Test
    public void testSetIdentifierLEAD_ORG() {
        sut.setIdentifierType(IdentifierType.LEAD_ORG.getCode());
        sut.setIdentifier(ID);
        assertEquals("Wrong identifier value", ID, sut.getLeadOrganizationTrialIdentifier());
    }

    /**
     * Test the setIdentifier method for the NCI case.
     */
    @Test
    public void testSetIdentifierNCI() {
        sut.setIdentifierType(IdentifierType.NCI.getCode());
        sut.setIdentifier(ID);
        assertEquals("Wrong identifier value", ID, sut.getNciIdentifier());
    }

    /**
     * Test the setIdentifier method for the NCT case.
     */
    @Test
    public void testSetIdentifierNCT() {
        sut.setIdentifierType(IdentifierType.NCT.getCode());
        sut.setIdentifier(ID);
        assertEquals("Wrong identifier value", ID, sut.getNctNumber());
    }

    /**
     * Test the setIdentifier method for the OTHER_IDENTIFIER case.
     */
    @Test
    public void testSetIdentifierOTHER_IDENTIFIER() {
        sut.setIdentifierType(IdentifierType.OTHER_IDENTIFIER.getCode());
        sut.setIdentifier(ID);
        assertEquals("Wrong identifier value", ID, sut.getOtherIdentifier());
    }
    
    /**
     * Test the cleanupIds method.
     */
    @Test
    public void cleanupIds() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(2L);
        ids.add(1L);
        ids.add(null);
        List<Long> result = sut.cleanupIds(ids);
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 2, result.size());
        assertEquals("Wrong result(0)", 1L, result.get(0).longValue());
        assertEquals("Wrong result(1)", 2L, result.get(1).longValue());
    }
    
    /**
     * Test the cleanupNames method.
     */
    @Test
    public void cleanupNamess() {
        List<String> names = new ArrayList<String>();
        names.add("name1");
        names.add("name2");
        names.add("name1");
        names.add("   ");
        names.add(null);
        List<String> result = sut.cleanupNames(names);
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 2, result.size());
        assertEquals("Wrong result(0)", "name1", result.get(0));
        assertEquals("Wrong result(1)", "name2", result.get(1));
    }

    @Test
    public void testToString() {
        assertFalse(sut.toString().contains("reportingPeriodStatusCriterion"));
        sut.populateReportingPeriodStatusCriterion(new Date(), new Date(),
                StudyStatusCode.ACTIVE, StudyStatusCode.IN_REVIEW);
        assertTrue(sut.toString().contains("reportingPeriodStatusCriterion"));
        assertFalse(sut.toString().contains("programCodeIds"));
        sut.getProgramCodeIds().add(5L);
        assertTrue(sut.toString().contains("programCodeIds"));
    }
    
    @Test
    public void testGetUniqueCriteriaKey() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        Date date = new Date();
        Collection<String> usedKeys = new HashSet<String>();
        StudyProtocolQueryCriteria c1 = new StudyProtocolQueryCriteria();
        StudyProtocolQueryCriteria c2 = new StudyProtocolQueryCriteria();
        assertEquals(c1.getUniqueCriteriaKey(), c2.getUniqueCriteriaKey());

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "studyProtocolId", 10L);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "studyProtocolId", 10L);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "nciIdentifier", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "nciIdentifier", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "dcpIdentifier", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "dcpIdentifier", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "ctepIdentifier", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "ctepIdentifier", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "nctNumber", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "nctNumber", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "officialTitle", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "officialTitle", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "otherIdentifier", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "otherIdentifier", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));

        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "leadOrganizationTrialIdentifier",
                "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "leadOrganizationTrialIdentifier",
                "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "phaseAdditionalQualifierCode",
                "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "phaseAdditionalQualifierCode",
                "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "studyStatusCode", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "studyStatusCode", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "principalInvestigatorId",
                "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "principalInvestigatorId",
                "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils
                .setSimpleProperty(c1, "primaryPurposeCode", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils
                .setSimpleProperty(c2, "primaryPurposeCode", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "identifierType", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "identifierType", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "organizationType", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "organizationType", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "userLastCreated", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "userLastCreated", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "excludeRejectProtocol",
                Boolean.TRUE);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "excludeRejectProtocol",
                Boolean.TRUE);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        // for Registry trial search
        PropertyUtils.setSimpleProperty(c1, "myTrialsOnly", Boolean.TRUE);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "myTrialsOnly", Boolean.TRUE);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "holdStatus", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "holdStatus", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "studyMilestone", Arrays.asList("TEST STRING"));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "studyMilestone", Arrays.asList("TEST STRING"));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "submissionType", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "submissionType", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        // for Inbox Processing
        PropertyUtils.setSimpleProperty(c1, "inBoxProcessing", Boolean.TRUE);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "inBoxProcessing", Boolean.TRUE);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "studyLockedBy", Boolean.TRUE);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "studyLockedBy", Boolean.TRUE);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "trialCategory", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "trialCategory", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());
        
        PropertyUtils.setSimpleProperty(c1, "ctepDcpCategory", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "ctepDcpCategory", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());        

        PropertyUtils.setSimpleProperty(c1, "userId", 10L);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "userId", 10L);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "ctgovXmlRequiredIndicator",
                "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "ctgovXmlRequiredIndicator",
                "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "summ4FundingSourceId", 10L);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "summ4FundingSourceId", 10L);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "summ4FundingSourceTypeCode",
                "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "summ4FundingSourceTypeCode",
                "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "countryName", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "countryName", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "city", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "city", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "bioMarkerIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "bioMarkerIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "bioMarkerNames",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "bioMarkerNames",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "documentWorkflowStatusCodes",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "documentWorkflowStatusCodes",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "interventionIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "interventionIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "interventionAlternateNameIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "interventionAlternateNameIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "interventionTypes",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "interventionTypes",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "leadOrganizationIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "leadOrganizationIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "participatingSiteIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "participatingSiteIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "pdqDiseases",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "pdqDiseases",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "phaseCodes",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "phaseCodes",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "states",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "states",
                Arrays.asList(new String[] { "S1", "S2" }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "summary4AnatomicSites",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "summary4AnatomicSites",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "familyId", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "familyId", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "participatingSiteFamilyId",
                "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "participatingSiteFamilyId",
                "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());
        
        PropertyUtils.setSimpleProperty(c1, "anyTypeIdentifier", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "anyTypeIdentifier", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());
        
        PropertyUtils.setSimpleProperty(c1, "submitter", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "submitter", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());        
        
        PropertyUtils.setSimpleProperty(c1, "onholdReasons", Arrays.asList("TEST STRING"));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "onholdReasons", Arrays.asList("TEST STRING"));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "processingPriority", Arrays.asList("TEST STRING"));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "processingPriority", Arrays.asList("TEST STRING"));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "milestoneFilters", Arrays.asList("TEST STRING"));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "milestoneFilters", Arrays.asList("TEST STRING"));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "submitterAffiliateOrgId", "TEST STRING");
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "submitterAffiliateOrgId", "TEST STRING");
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());    
        
        PropertyUtils.setSimpleProperty(c1, "submittedOnOrAfter", date);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "submittedOnOrAfter", date);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());
        
        PropertyUtils.setSimpleProperty(c1, "submittedOnOrBefore", date);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "submittedOnOrBefore", date);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());
        
        PropertyUtils.setSimpleProperty(c1, "checkedOut", true);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "checkedOut", true);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());  
        
        PropertyUtils.setSimpleProperty(c1, "holdRecordExists", true);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "holdRecordExists", true);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        PropertyUtils.setSimpleProperty(c1, "programCodeIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        PropertyUtils.setSimpleProperty(c2, "programCodeIds",
                Arrays.asList(new Long[] { 1L, 2L }));
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());

        Date startDate = new Date();
        Date endDate = new Date();
        c1.populateReportingPeriodStatusCriterion(startDate, endDate, StudyStatusCode.ACTIVE,
                StudyStatusCode.IN_REVIEW);
        assertFalse(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        c2.populateReportingPeriodStatusCriterion(startDate, endDate, StudyStatusCode.ACTIVE,
                StudyStatusCode.IN_REVIEW);
        assertTrue(c1.getUniqueCriteriaKey().equals(c2.getUniqueCriteriaKey()));
        assertFalse(usedKeys.contains(c1.getUniqueCriteriaKey()));
        usedKeys.add(c1.getUniqueCriteriaKey());
    }
    
}
