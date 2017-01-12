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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseParent;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedMarker;
import gov.nih.nci.pa.domain.PlannedMarkerSyncWithCaDSR;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyAlternateTitle;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolFlag;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.domain.Tweet;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.enums.TweetStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceBean;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.AnatomicSiteComparator;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.StudySiteComparator;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 * @author NAmiruddin
 * 
 */
public class ProtocolQueryServiceIntegrationTest extends
        AbstractHibernateTestCase {

    /**
     * Have to mock results service since views not created in HSQLDB.
     */
    class MockResultsService implements ProtocolQueryResultsServiceLocal {
        @Override
        public List<StudyProtocolQueryDTO> getResults(List<Long> ids,
                boolean myTrialsOnly, Long userId,
                ProtocolQueryPerformanceHints... hints) throws PAException {
            List<StudyProtocolQueryDTO> result = new ArrayList<StudyProtocolQueryDTO>();
            for (Long id : ids) {
                Session session = PaHibernateUtil.getCurrentSession();
                StudyProtocol sp = (StudyProtocol) session.get(
                        StudyProtocol.class, id);
                StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
                dto.setStudyProtocolId(id);
                dto.setOfficialTitle(sp.getOfficialTitle());
                dto.setPhaseCode(sp.getPhaseCode());
                dto.setPhaseAdditionalQualifier(sp
                        .getPhaseAdditionalQualifierCode());
                dto.setProprietaryTrial(sp.getProprietaryTrialIndicator());
                result.add(dto);
            }
            return result;
        }

        @Override
        public List<StudyProtocolQueryDTO> getResultsLean(List<Long> ids) throws PAException {
            return getResults(ids, false, null);
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ProtocolQueryServiceBean bean = new ProtocolQueryServiceBean();
    private final ProtocolQueryServiceLocal localEjb = bean;
    private Long spId = null;
    private Long leadOrgId = null;
    private Long principalInvestigator = null;
    private Long diseaseId = null;
    private static int COUNT = 0;

    @Before
    public void setUp() throws Exception {
        AbstractMockitoTest mockitoTest = new AbstractMockitoTest();
        mockitoTest.setUp();

        bean.setProtocolQueryResultsService(new MockResultsService());
        bean.setRegistryUserService(new RegistryUserServiceBean());
        bean.setDataAccessService(new DataAccessServiceBean());
        createStudyProtocol("1", false, Boolean.FALSE, false, false, false,
                false, true);
    }

    @Test
    public void testEmptyCriteria() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("At least one criteria is required");

        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        localEjb.getStudyProtocolByCriteria(criteria);
    }

    @Test
    public void getStudyProtocolByCriteriaTest() throws Exception {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNciIdentifier("nci");
        criteria.setCtgovXmlRequiredIndicator("");
        List<StudyProtocolQueryDTO> results = localEjb
                .getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Title does not match.",
                results.get(0).getOfficialTitle(), "Cancer for kids");        
        criteria.setNciIdentifier(null);

        criteria.setStudyStatusCode("In Review");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());
        criteria.setStudyStatusCode(null);

        criteria.setStudyStatusCode("Active");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setStudyStatusCode(null);

        criteria.getDocumentWorkflowStatusCodes().add("Verification Pending");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());
        criteria.getDocumentWorkflowStatusCodes().clear();

        criteria.getDocumentWorkflowStatusCodes().add("Accepted");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertFalse(results.isEmpty());
        criteria.getDocumentWorkflowStatusCodes().clear();

        criteria.setLeadOrganizationTrialIdentifier("Local1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setLeadOrganizationTrialIdentifier(null);

        criteria.setOfficialTitle("Cancer");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());
        
        criteria.setOfficialTitle("caNc");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());
        
        criteria.setOfficialTitle("caNCer FOR");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());
        
        criteria.setOfficialTitle("Cancer FOR kidS");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());
        
        criteria.setOfficialTitle("tEeN");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());
        
        criteria.setOfficialTitle("for ADultS");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());
        
        criteria.setOfficialTitle("CaNceR for Child");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());
        
        criteria.setOfficialTitle(null);

        criteria.setPhaseCodes(Arrays.asList(new String[] { "I", "0" }));
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals(PhaseCode.I, results.get(0).getPhaseCode());
        criteria.setPhaseCode(null);

        criteria.setPhaseAdditionalQualifierCode("Pilot");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals(PhaseAdditionalQualifierCode.PILOT, results.get(0)
                .getPhaseAdditionalQualifier());
        criteria.setPhaseAdditionalQualifierCode(null);

        criteria.setPrimaryPurposeCode("Prevention");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setPrimaryPurposeCode(null);

        criteria.setNctNumber("NCT-1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setNctNumber(null);

        criteria.setCtepIdentifier("CTEP-1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setCtepIdentifier(null);

        criteria.setDcpIdentifier("DCP-1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setDcpIdentifier(null);

        criteria.setSubmissionType("O");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setSubmissionType(null);

        criteria.getStudyMilestone().add("Submission Received Date");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());
        criteria.setStudyMilestone(new ArrayList<String>());

        criteria.getStudyMilestone().add("Submission Acceptance Date");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setStudyMilestone(new ArrayList<String>());

        criteria.getLeadOrganizationIds().add(123L);
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());
        criteria.getLeadOrganizationIds().clear();

        criteria.getLeadOrganizationIds().add(leadOrgId);
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.getLeadOrganizationIds().clear();

        criteria.setPrincipalInvestigatorId("123");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());
        criteria.setPrincipalInvestigatorId(null);

        criteria.setPrincipalInvestigatorId(principalInvestigator.toString());
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setPrincipalInvestigatorId(null);

        criteria.setOtherIdentifier("OTHER-2");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());
        criteria.setOtherIdentifier(null);

        criteria.setOtherIdentifier("OTHER-1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setOtherIdentifier(null);

        criteria.setOtherIdentifier("OTHER-1");
        criteria.setInBoxProcessing(Boolean.TRUE);
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        criteria.setInBoxProcessing(null);

        createStudyProtocol("2", true, Boolean.FALSE, false, false, false,
                false, false);
        StudyProtocolQueryCriteria otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setNciIdentifier("NCI");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 2, results.size());

        otherCriteria.setExcludeRejectProtocol(Boolean.FALSE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 2, results.size());

        otherCriteria.setNciIdentifier(null);
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setExcludeRejectProtocol(Boolean.TRUE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());
        assertFalse(DocumentWorkflowStatusCode.REJECTED == results.get(0)
                .getDocumentWorkflowStatusCode());

        createStudyProtocol("3", false, Boolean.TRUE, false, false, false,
                false, false);
        createStudyProtocol("4", false, Boolean.TRUE, false, false, false,
                false, false);
        createStudyProtocol("5", false, Boolean.TRUE, false, false, false,
                false, false);

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setNciIdentifier("NCI");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 5, results.size());

        otherCriteria.setTrialCategory("N");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 2, results.size());
        for (StudyProtocolQueryDTO dto : results) {
            assertEquals("Complete Trial", dto.getTrialCategory());
        }

        otherCriteria.setTrialCategory("P");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 3, results.size());
        for (StudyProtocolQueryDTO dto : results) {
            assertEquals("Abbreviated Trial", dto.getTrialCategory());
        }

        StudyProtocol sp = createStudyProtocol("6", false, Boolean.FALSE,
                false, false, false, false, false);
        RegistryUser owner = sp.getStudyOwners().iterator().next();

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setUserId(owner.getId());
        otherCriteria.setUserLastCreated("user");
        otherCriteria.setMyTrialsOnly(Boolean.FALSE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 6, results.size());

        otherCriteria.setMyTrialsOnly(Boolean.TRUE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());

        createStudyProtocol("7", false, Boolean.FALSE, true, false, false,
                false, false);

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setHoldStatus("");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 7, results.size());

        otherCriteria.setHoldStatus(PAConstants.ON_HOLD);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());

        otherCriteria.setHoldStatus(PAConstants.NOT_ON_HOLD);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 6, results.size());

        createStudyProtocol("8", false, Boolean.FALSE, false, true, true,
                false, false);

        otherCriteria.setHoldStatus("");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 8, results.size());

        otherCriteria.setStudyLockedBy(true);
        otherCriteria.setUserLastCreated("user");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());

        createStudyProtocol("9", false, Boolean.FALSE, false, false, false,
                true, false);

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setSubmissionType("Original");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 8, results.size());

        otherCriteria.setSubmissionType("Amendment");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());

        createStudyProtocol("10", false, Boolean.FALSE, false, false, false,
                false, true);
        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setSubmissionType("Update");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 2, results.size());

        createStudyProtocol("11", false, Boolean.FALSE, false, false, false,
                false, true);
        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.getLeadOrganizationIds().add(leadOrgId);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());

        createStudyProtocol("12", false, Boolean.FALSE, false, false, false,
                false, true);
        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setSumm4FundingSourceId(leadOrgId);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());
        otherCriteria
                .setSumm4FundingSourceTypeCode(SummaryFourFundingCategoryCode.INSTITUTIONAL
                        .getCode());
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());
        otherCriteria
                .setSumm4FundingSourceTypeCode(SummaryFourFundingCategoryCode.NATIONAL
                        .getCode());
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 0, results.size());
        otherCriteria.setSumm4FundingSourceId(null);
        otherCriteria
                .setSumm4FundingSourceTypeCode(SummaryFourFundingCategoryCode.INSTITUTIONAL
                        .getCode());
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 12, results.size());
        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setPdqDiseases(Arrays.asList(diseaseId));
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());
        otherCriteria.setPdqDiseases(Arrays.asList(999L));
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 0, results.size());
    }

    @Test
    public void getTrialSummaryByStudyProtocolIdTest() throws Exception {
        StudyProtocolQueryDTO data = localEjb
                .getTrialSummaryByStudyProtocolId(spId);
        assertNotNull(data);
        assertEquals("Title does not match  ", data.getOfficialTitle(),
                "Cancer for kids");
        assertEquals("NCI Identifier does not match  ",
                data.getNciIdentifier(), "NCI-2009-00001");
    }

    @Test
    public void getStudyProtocolByOrgIdentifierTest() throws Exception {
        createStudyProtocol("2", false, Boolean.FALSE, false, false, false,
                false, false);
        List<StudyProtocol> results = localEjb
                .getStudyProtocolByOrgIdentifier(Long.valueOf(1));
        assertEquals(1, results.size());

        results = localEjb.getStudyProtocolByOrgIdentifier(Long.valueOf(2));
        assertEquals(1, results.size());
    }

    @Test(expected = PAException.class)
    public void nullParameter1() throws Exception {
        localEjb.getTrialSummaryByStudyProtocolId(null);
    }

    @Test(expected = PAException.class)
    public void nullParameter2() throws Exception {
        localEjb.getTrialSummaryByStudyProtocolId(Long.valueOf(1000));
    }

    public StudyProtocol createStudyProtocol(String orgId,
            boolean createRejected, Boolean isPropTrial, boolean onHold,
            boolean adminCheckout, boolean scientificCheckout,
            boolean amendment, boolean update) {
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        sp.setProprietaryTrialIndicator(isPropTrial);

        if (amendment) {
            sp.setSubmissionNumber(2);
            sp.setAmendmentNumber("2");
            sp.setAmendmentReasonCode(AmendmentReasonCode.ADMINISTRATIVE);
            sp.setAmendmentDate(new Timestamp(new Date().getTime()));
        } else {
            sp.setSubmissionNumber(1);
            sp.setAmendmentDate(null);
            sp.setAmendmentNumber(null);
        }

        Ii otherId = new Ii();
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        otherId.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        otherId.setExtension("OTHER-" + orgId);
        sp.getOtherIdentifiers().add(otherId);

        TestSchema.addUpdObject(sp);
        spId = sp.getId();

        Organization org = TestSchema.createOrganizationObj();
        org.setIdentifier(orgId);
        TestSchema.addUpdObject(org);
        leadOrgId = org.getId();

        Organization nctOrg = TestSchema.createOrganizationObj();
        nctOrg.setIdentifier(orgId);
        nctOrg.setName(PAConstants.CTGOV_ORG_NAME);
        TestSchema.addUpdObject(nctOrg);

        Organization dcpOrg = TestSchema.createOrganizationObj();
        dcpOrg.setIdentifier(orgId);
        dcpOrg.setName(PAConstants.DCP_ORG_NAME);
        TestSchema.addUpdObject(dcpOrg);

        Organization ctepOrg = TestSchema.createOrganizationObj();
        ctepOrg.setIdentifier(orgId);
        ctepOrg.setName(PAConstants.CTEP_ORG_NAME);
        TestSchema.addUpdObject(ctepOrg);

        Person p = TestSchema.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        principalInvestigator = p.getId();

        HealthCareFacility hcf = TestSchema.createHealthCareFacilityObj(org);
        TestSchema.addUpdObject(hcf);

        HealthCareProvider hcp = TestSchema.createHealthCareProviderObj(p, org);
        TestSchema.addUpdObject(hcp);

        Country c = TestSchema.createCountryObj();
        TestSchema.addUpdObject(c);

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(
                org, p);
        TestSchema.addUpdObject(crs);

        StudyContact sc = TestSchema.createStudyContactObj(sp, c, hcp, crs);
        TestSchema.addUpdObject(sc);
        sp.getStudyContacts().add(sc);

        StudyContact sc2 = TestSchema.createStudyContactObj(sp, c, hcp, crs);
        sc2.setRoleCode(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR);
        TestSchema.addUpdObject(sc2);
        sp.getStudyContacts().add(sc2);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(org);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setIdentifier("abc");
        TestSchema.addUpdObject(ro);

        ResearchOrganization dcpRo = new ResearchOrganization();
        dcpRo.setOrganization(dcpOrg);
        dcpRo.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        dcpRo.setIdentifier("abc");
        TestSchema.addUpdObject(dcpRo);

        ResearchOrganization nctRo = new ResearchOrganization();
        nctRo.setOrganization(nctOrg);
        nctRo.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        nctRo.setIdentifier("abc");
        TestSchema.addUpdObject(nctRo);

        ResearchOrganization ctepRo = new ResearchOrganization();
        ctepRo.setOrganization(ctepOrg);
        ctepRo.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ctepRo.setIdentifier("abc");
        TestSchema.addUpdObject(ctepRo);

        StudySite spc = TestSchema.createStudySiteObj(sp, hcf);
        spc.setLocalStudyProtocolIdentifier("Local" + orgId);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        sp.getStudySites().add(spc);

        StudySite spc2 = TestSchema.createStudySiteObj(sp, hcf);
        spc2.setLocalStudyProtocolIdentifier("NCT-" + orgId);
        spc2.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        spc2.setResearchOrganization(nctRo);
        TestSchema.addUpdObject(spc2);
        sp.getStudySites().add(spc2);

        StudySite spc3 = TestSchema.createStudySiteObj(sp, hcf);
        spc3.setLocalStudyProtocolIdentifier("DCP-" + orgId);
        spc3.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        spc3.setResearchOrganization(dcpRo);
        TestSchema.addUpdObject(spc3);
        sp.getStudySites().add(spc3);

        StudySite spc4 = TestSchema.createStudySiteObj(sp, hcf);
        spc4.setLocalStudyProtocolIdentifier("CTEP-" + orgId);
        spc4.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        spc4.setResearchOrganization(ctepRo);
        TestSchema.addUpdObject(spc4);
        sp.getStudySites().add(spc4);

        Date now = new Date();
        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(now.getTime()));
        sos.setCommentText("In Review");
        sos.setStudyProtocol(sp);
        TestSchema.addUpdObject(sos);
        sp.getStudyOverallStatuses().add(sos);

        now = new Date();
        StudyOverallStatus sos2 = new StudyOverallStatus();
        sos2.setStatusCode(StudyStatusCode.ACTIVE);
        sos2.setStatusDate(new Timestamp(now.getTime()));
        sos2.setCommentText("Active");
        sos2.setStudyProtocol(sp);
        TestSchema.addUpdObject(sos2);
        sp.getStudyOverallStatuses().add(sos2);

        // add Summ4 Fund Sponsor and Type
        StudyResourcing stdRes = new StudyResourcing();
        stdRes.setSummary4ReportedResourceIndicator(true);
        stdRes.setOrganizationIdentifier(org.getId().toString());
        stdRes.setTypeCode(SummaryFourFundingCategoryCode.INSTITUTIONAL);
        stdRes.setActiveIndicator(true);
        stdRes.setStudyProtocol(sp);
        TestSchema.addUpdObject(stdRes);
        sp.getStudyResourcings().add(stdRes);

        // add Disease/Condition
        StudyDisease stdDes = new StudyDisease();
        PDQDisease dis01 = TestSchema.createPdqDisease("Toe Cancer");
        TestSchema.addUpdObject(dis01);
        diseaseId = dis01.getId();
        stdDes.setDisease(dis01);
        stdDes.setStudyProtocol(sp);
        TestSchema.addUpdObject(stdDes);
        sp.getStudyDiseases().add(stdDes);

        // add PlannedActivity - Intervention
        Intervention inv = new Intervention();
        inv.setName("Chocolate Bar");
        inv.setDescriptionText("Oral intervention to improve morale");
        inv.setDateLastUpdated(new Date());
        inv.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        inv.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2000"));
        inv.setTypeCode(InterventionTypeCode.DRUG);
        TestSchema.addUpdObject(inv);
        PlannedActivity pa = new PlannedActivity();
        pa.setCategoryCode(ActivityCategoryCode.INTERVENTION);
        pa.setDateLastUpdated(new Date());
        pa.setIntervention(inv);
        pa.setLeadProductIndicator(true);
        pa.setStudyProtocol(sp);
        pa.setSubcategoryCode(ActivitySubcategoryCode.DIETARY_SUPPLEMENT
                .getCode());
        TestSchema.addUpdObject(pa);
        sp.getPlannedActivities().add(pa);

        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        dws.setCommentText("Pending");
        TestSchema.addUpdObject(dws);
        sp.getDocumentWorkflowStatuses().add(dws);
        TestSchema.addUpdObject(sp);

        DocumentWorkflowStatus dws2 = new DocumentWorkflowStatus();
        dws2.setStudyProtocol(sp);
        dws2.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        dws2.setCommentText("Accepted");
        TestSchema.addUpdObject(dws2);
        sp.getDocumentWorkflowStatuses().add(dws2);

        if (createRejected) {
            DocumentWorkflowStatus dws3 = new DocumentWorkflowStatus();
            dws3.setStudyProtocol(sp);
            dws3.setStatusCode(DocumentWorkflowStatusCode.REJECTED);
            dws3.setCommentText("Rejected");
            TestSchema.addUpdObject(dws3);
            sp.getDocumentWorkflowStatuses().add(dws3);
        }

        now = new Date();
        StudyMilestone milestone = new StudyMilestone();
        milestone.setStudyProtocol(sp);
        milestone.setMilestoneDate(new Timestamp(now.getTime()));
        milestone.setMilestoneCode(MilestoneCode.SUBMISSION_RECEIVED);
        TestSchema.addUpdObject(milestone);
        sp.getStudyMilestones().add(milestone);

        now = new Date();
        StudyMilestone milestone2 = new StudyMilestone();
        milestone2.setStudyProtocol(sp);
        milestone2.setMilestoneDate(new Timestamp(now.getTime()));
        milestone2.setMilestoneCode(MilestoneCode.SUBMISSION_ACCEPTED);
        TestSchema.addUpdObject(milestone2);
        sp.getStudyMilestones().add(milestone2);
        PaHibernateUtil
                .getCurrentSession()
                .createSQLQuery(
                        "update study_milestone set last=true where identifier="
                                + milestone2.getId()).executeUpdate();

        StudyInbox si = createStudyInboxobj(sp);
        TestSchema.addUpdObject(si);
        sp.getStudyInbox().add(si);

        StudyInbox si2 = createStudyInboxobj(sp);
        if (update) {
            si2.setCloseDate(null);
        }
        TestSchema.addUpdObject(si2);
        sp.getStudyInbox().add(si2);

        if (onHold) {
            now = new Date();
            StudyOnhold soh = new StudyOnhold();
            soh.setOnholdReasonCode(OnholdReasonCode.OTHER);
            soh.setOnholdReasonText("On hold.");
            soh.setOnholdDate(new Timestamp(now.getTime()));
            soh.setStudyProtocol(sp);
            TestSchema.addUpdObject(soh);
            sp.getStudyOnholds().add(soh);
        }
        if (adminCheckout) {
            StudyCheckout co = new StudyCheckout();
            co.setCheckOutDate(new Timestamp((new Date()).getTime()));
            co.setCheckOutType(CheckOutType.ADMINISTRATIVE);
            co.setUserIdentifier("user");
            co.setStudyProtocol(sp);
            TestSchema.addUpdObject(co);
            sp.getStudyCheckout().add(co);
        }
        if (scientificCheckout) {
            StudyCheckout co = new StudyCheckout();
            co.setCheckOutDate(new Timestamp((new Date()).getTime()));
            co.setCheckOutType(CheckOutType.SCIENTIFIC);
            co.setUserIdentifier("user");
            co.setStudyProtocol(sp);
            TestSchema.addUpdObject(co);
            sp.getStudyCheckout().add(co);
        }
        //add study alternate titles
        StudyAlternateTitle obj1 = new StudyAlternateTitle();
        obj1.setAlternateTitle("cancer for Teens");
        obj1.setCategory("Other");
        obj1.setStudyProtocol(sp);
        sp.getStudyAlternateTitles().add(obj1);
        
        StudyAlternateTitle obj2 = new StudyAlternateTitle();
        obj2.setAlternateTitle("cancer For children");
        obj2.setCategory("Spelling/Formatting Correction");
        obj2.setStudyProtocol(sp);
        sp.getStudyAlternateTitles().add(obj2);
        
        StudyAlternateTitle obj3 = new StudyAlternateTitle();
        obj3.setAlternateTitle("Cancer For adults");
        obj3.setCategory("Other");
        obj3.setStudyProtocol(sp);
        sp.getStudyAlternateTitles().add(obj3);
        TestSchema.addUpdObject(sp);
        return sp;
    }

    private static StudyInbox createStudyInboxobj(StudyProtocol sp) {
        StudyInbox create = new StudyInbox();
        Timestamp now = new Timestamp(new Date().getTime());
        create.setStudyProtocol(sp);
        create.setComments("idName");
        create.setOpenDate(now);
        create.setCloseDate(now);
        create.setUserLastUpdated(TestSchema.getUser());
        create.setDateLastUpdated(now);
        return create;
    }

    @Test
    public void getStudyProtocolQueryResultListByLeadOrganizationCountry()
            throws PAException {
        List<Long> data = createStudyProtocolList();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setCountryName("UKR");
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] { data.get(1) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByParticipatingSiteCountry()
            throws PAException {
        List<Long> data = createStudyProtocolList();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setCountryName("RUS");
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] { data.get(3) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByLeadOrganizationState()
            throws PAException {
        List<Long> data = createStudyProtocolList();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setStates(Arrays.asList(new String[] { "TX" }));
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(2, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] { data.get(3),
                data.get(4) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
        assertTrue(expectedResult.contains(result.get(1).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByTreatingOrtganizationState()
            throws PAException {
        List<Long> data = createStudyProtocolList();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setStates(Arrays.asList(new String[] { "MD", "CA" }));
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(4, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] { data.get(0),
                data.get(1), data.get(2), data.get(4) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
        assertTrue(expectedResult.contains(result.get(1).getId()));
        assertTrue(expectedResult.contains(result.get(2).getId()));
        assertTrue(expectedResult.contains(result.get(3).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByLeadOrganizationCity()
            throws PAException {
        List<Long> data = createStudyProtocolList();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setCity("Arlin");
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(3, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] { data.get(0),
                data.get(2), data.get(4) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
        assertTrue(expectedResult.contains(result.get(1).getId()));
        assertTrue(expectedResult.contains(result.get(2).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByPArticipatingSiteCity()
            throws PAException {
        List<Long> data = createStudyProtocolList();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setCity("ville");
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(4, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] { data.get(0),
                data.get(1), data.get(2), data.get(3) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
        assertTrue(expectedResult.contains(result.get(1).getId()));
        assertTrue(expectedResult.contains(result.get(2).getId()));
        assertTrue(expectedResult.contains(result.get(3).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByAllLocationParametrs()
            throws PAException {
        List<Long> data = createStudyProtocolList();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setCountryName("USA");
        criteria.setStates(Arrays.asList(new String[] { "MD", "TX" }));
        criteria.setCity("Balt");
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(2, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] { data.get(3),
                data.get(4) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
        assertTrue(expectedResult.contains(result.get(1).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListEmptyReturn() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setCity("notExistingCity");
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(0, result.size());
    }

    @Test
    public void getStudyProtocolQueryResultListByParticipatingSite()
            throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByParticipatingSite();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setParticipatingSiteIds(testBean.input);
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(2, result.size());
        List<Long> expectedResult = Arrays.asList(new Long[] {
                testBean.output.get(1), testBean.output.get(2) });
        assertTrue(expectedResult.contains(result.get(0).getId()));
        assertTrue(expectedResult.contains(result.get(1).getId()));
    }
    
    @Test
    public void getStudyProtocolQueryResultListByParticipatingSiteStatus()
            throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByParticipatingSite();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setSiteStatusCodes(Arrays.asList(RecruitmentStatusCode.ACTIVE));
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(4, result.size());
        
        criteria = new StudyProtocolQueryCriteria();
        criteria.setSiteStatusCodes(Arrays.asList(RecruitmentStatusCode.ENROLLING_BY_INVITATION));
        result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(0, result.size());

    }
    
    @Test
    public void getStudyProtocolQueryResultListByFlag() throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByParticipatingSite();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNotFlaggedWith(StudyFlagReasonCode.DO_NOT_SUBMIT_TWEETS);
        criteria.setSiteStatusCodes(Arrays.asList(RecruitmentStatusCode.ACTIVE));
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(3, result.size());

        criteria = new StudyProtocolQueryCriteria();
        criteria.setNotFlaggedWith(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES);
        criteria.setSiteStatusCodes(Arrays.asList(RecruitmentStatusCode.ACTIVE));
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());

    }
    
    @Test
    public void getStudyProtocolQueryResultListTweets() throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByParticipatingSite();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setHasTweets(true);
        criteria.setSiteStatusCodes(Arrays.asList(RecruitmentStatusCode.ACTIVE));
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());

        criteria = new StudyProtocolQueryCriteria();
        criteria.setHasTweets(false);
        criteria.setSiteStatusCodes(Arrays.asList(RecruitmentStatusCode.ACTIVE));
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(3, result.size());

    }

    @Test
    public void getStudyProtocolQueryResultListByAnatomicSites()
            throws PAException {
        TestBean testBean = createStudyProtocolListForSearchByAnatomicSites();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setSummary4AnatomicSites(testBean.input);
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(2, result.size());
        assertTrue(testBean.output.contains(result.get(0).getId()));
        assertTrue(testBean.output.contains(result.get(1).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByBioMarkers()
            throws PAException {
        TestBean testBean = createStudyProtocolListForSearchByBioMarkers();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setBioMarkerIds(testBean.input);
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(2, result.size());
        assertTrue(testBean.output.contains(result.get(0).getId()));
        assertTrue(testBean.output.contains(result.get(1).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByPDQDiseases()
            throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByPdqDisease();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setPdqDiseases(testBean.input);
        PDQDiseaseServiceLocal pdqDiseaseService = new PDQDiseaseServiceBean();
        bean.setPdqDiseaseService(pdqDiseaseService);
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(2, result.size());
        assertTrue(testBean.output.contains(result.get(0).getId()));
        assertTrue(testBean.output.contains(result.get(1).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByIntervention()
            throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByIntervention();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        List<Long> interventionIds = Arrays.asList(new Long[] {
                testBean.input.get(0), testBean.input.get(1) });
        criteria.setInterventionIds(interventionIds);
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        List<Long> output = Arrays.asList(new Long[] { testBean.output.get(0),
                testBean.output.get(1) });
        assertEquals(2, result.size());
        assertTrue(output.contains(result.get(0).getId()));
        assertTrue(output.contains(result.get(1).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByInterventionAlternativeNames()
            throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByIntervention();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        List<Long> interventionAlternateNameIds = Arrays.asList(new Long[] {
                testBean.input.get(2), testBean.input.get(3) });
        criteria.setInterventionAlternateNameIds(interventionAlternateNameIds);
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        List<Long> output = Arrays.asList(new Long[] { testBean.output.get(1),
                testBean.output.get(2) });
        assertEquals(2, result.size());
        assertTrue(output.contains(result.get(0).getId()));
        assertTrue(output.contains(result.get(1).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByInterventionAndInterventionAlternativeNames()
            throws PAException {
        TestBean<Long, Long> testBean = createStudyProtocolListForSearchByIntervention();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        List<Long> interventionIds = Arrays.asList(new Long[] {
                testBean.input.get(0), testBean.input.get(1) });
        criteria.setInterventionIds(interventionIds);
        List<Long> interventionAlternateNameIds = Arrays.asList(new Long[] {
                testBean.input.get(2), testBean.input.get(3) });
        criteria.setInterventionAlternateNameIds(interventionAlternateNameIds);
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(3, result.size());
        assertTrue(testBean.output.contains(result.get(0).getId()));
        assertTrue(testBean.output.contains(result.get(1).getId()));
        assertTrue(testBean.output.contains(result.get(2).getId()));
    }

    @Test
    public void getStudyProtocolQueryResultListByLeadOrganizationTrialIdentifier()
            throws PAException {
        TestBean<String, Long> testBean = createStudyProtocolListForSearchByLeadOrganizationTrialIdentifier();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType(StudySiteFunctionalCode.LEAD_ORGANIZATION
                .getCode());
        criteria.setIdentifier(testBean.input.get(0));
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        assertEquals(testBean.output.get(0), result.get(0).getId());
    }

    @Test
    public void getStudyProtocolQueryResultListByAllIdentifiers()
            throws PAException {
        StudyProtocol leadSp = createStudyProtocol();
        StudySite site = getLeadOrganizationStudySite(leadSp.getStudySites());
        site.setLocalStudyProtocolIdentifier("LEAD_ORG_ID");
        TestSchema.addUpdObject(site);

        StudyProtocol ctepSp = createStudyProtocol();
        StudySite site2 = createIdentifierAssignerStudySite(ctepSp,
                PAConstants.CTEP_ORG_NAME);
        site2.setLocalStudyProtocolIdentifier("CTEP_ID");
        TestSchema.addUpdObject(site2);

        StudyProtocol dcpSp = createStudyProtocol();
        StudySite site3 = createIdentifierAssignerStudySite(dcpSp,
                PAConstants.DCP_ORG_NAME);
        site3.setLocalStudyProtocolIdentifier("DCP_ID");
        TestSchema.addUpdObject(site3);

        StudyProtocol nctSp = createStudyProtocol();
        StudySite site4 = createIdentifierAssignerStudySite(nctSp,
                PAConstants.CTGOV_ORG_NAME);
        site4.setLocalStudyProtocolIdentifier("NCT_CTGOV_ID");
        TestSchema.addUpdObject(site4);

        StudyProtocol otherSp = createStudyProtocol();
        otherSp.getOtherIdentifiers().add(
                IiConverter.convertToOtherIdentifierIi("OTHER_ID"));
        TestSchema.addUpdObject(otherSp);

        StudyProtocol nciSp = createStudyProtocol();
        
        nciSp.getOtherIdentifiers().clear();
        PaHibernateUtil.getCurrentSession().flush();
        
        nciSp.getOtherIdentifiers().add(
                IiConverter.convertToAssignedIdentifierIi("NCI_ID"));
        TestSchema.addUpdObject(nciSp);

        // All 6 match.
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        criteria.setIdentifier("ID");
        List<StudyProtocol> result = localEjb
                .getStudyProtocolQueryResultList(criteria);
        assertEquals(6, result.size());
        assertTrue(result.contains(leadSp));
        assertTrue(result.contains(ctepSp));
        assertTrue(result.contains(dcpSp));
        assertTrue(result.contains(nctSp));
        assertTrue(result.contains(otherSp));
        assertTrue(result.contains(nciSp));

        // Lead
        criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        criteria.setIdentifier("LEAD_ORG_");
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        assertEquals(leadSp, result.get(0));

        // CTEP
        criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        criteria.setIdentifier("CTEP_ID");
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        assertEquals(ctepSp, result.get(0));

        // DCP
        criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        criteria.setIdentifier("DCP_ID");
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        assertEquals(dcpSp, result.get(0));

        // NCT
        criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        criteria.setIdentifier("NCT_CTGOV_ID");
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        assertEquals(nctSp, result.get(0));

        // OTHER
        criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        criteria.setIdentifier("OTHER_ID");
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        assertEquals(otherSp, result.get(0));

        // NCI
        criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        criteria.setIdentifier("NCI_ID");
        result = localEjb.getStudyProtocolQueryResultList(criteria);
        assertEquals(1, result.size());
        assertEquals(nciSp, result.get(0));

    }

    @Test
    public void getStudyProtocolByAgentNsc() throws PAException {
        StudyProtocol leadSp = createStudyProtocol();
        assertTrue(localEjb.getStudyProtocolByAgentNsc("xyzzy").isEmpty());

        Intervention intrv = createIntervention();
        intrv.setTypeCode(InterventionTypeCode.DRUG);
        TestSchema.addUpdObject(intrv);
        InterventionAlternateName ian = createInterventionAlternateName(intrv);
        ian.setName("xyzzy");
        ian.setNameTypeCode("NSC number");
        TestSchema.addUpdObject(ian);
        PlannedActivity pa = createPlannedActivity(intrv, leadSp);
        TestSchema.addUpdObject(pa);

        assertEquals(1, localEjb.getStudyProtocolByAgentNsc("xyzzy").size());
    }

    private List<Long> createStudyProtocolList() {
        List<Long> result = Arrays.asList(new Long[] { 0L, 0L, 0L, 0L, 0L });

        StudyProtocol studyProtocol1 = createStudyProtocol();
        result.set(0, studyProtocol1.getId());

        StudyProtocol studyProtocol2 = createStudyProtocol();
        Iterator<StudySite> it2 = studyProtocol2.getStudySites().iterator();
        StudySite leadStudySite2 = it2.next();
        leadStudySite2.getResearchOrganization().getOrganization()
                .setCountryName("UKR");
        leadStudySite2.getResearchOrganization().getOrganization().setState("");
        leadStudySite2.getResearchOrganization().getOrganization()
                .setCity("Kiev");
        TestSchema.addUpdObject(leadStudySite2.getResearchOrganization()
                .getOrganization());
        result.set(1, studyProtocol2.getId());

        StudyProtocol studyProtocol3 = createStudyProtocol();
        Iterator<StudySite> it3 = studyProtocol3.getStudySites().iterator();
        it3.next();

        StudySite treatingStudySite3 = it3.next();
        treatingStudySite3.getHealthCareFacility().getOrganization()
                .setCity("Poolsville");
        TestSchema.addUpdObject(treatingStudySite3.getHealthCareFacility()
                .getOrganization());
        result.set(2, studyProtocol3.getId());

        StudyProtocol studyProtocol4 = createStudyProtocol();
        Iterator<StudySite> it4 = studyProtocol4.getStudySites().iterator();
        StudySite leadStudySite4 = it4.next();
        leadStudySite4.getResearchOrganization().getOrganization()
                .setState("TX");
        leadStudySite4.getResearchOrganization().getOrganization()
                .setCity("Baltville");
        TestSchema.addUpdObject(leadStudySite4.getResearchOrganization()
                .getOrganization());

        StudySite treatingStudySite4 = it4.next();
        treatingStudySite4.getHealthCareFacility().getOrganization()
                .setCountryName("RUS");
        treatingStudySite4.getHealthCareFacility().getOrganization()
                .setState("");
        treatingStudySite4.getHealthCareFacility().getOrganization()
                .setCity("Moscow");
        TestSchema.addUpdObject(treatingStudySite4.getHealthCareFacility()
                .getOrganization());
        result.set(3, studyProtocol4.getId());

        StudyProtocol studyProtocol5 = createStudyProtocol();
        Iterator<StudySite> it5 = studyProtocol5.getStudySites().iterator();
        StudySite leadStudySite5 = it5.next();
        leadStudySite5.getResearchOrganization().getOrganization()
                .setState("TX");
        leadStudySite5.getResearchOrganization().getOrganization()
                .setCity("Arlinburg");
        TestSchema.addUpdObject(leadStudySite5.getResearchOrganization()
                .getOrganization());

        StudySite treatingStudySite5 = it5.next();
        treatingStudySite5.getHealthCareFacility().getOrganization()
                .setCity("Baltimore");
        TestSchema.addUpdObject(treatingStudySite5.getHealthCareFacility()
                .getOrganization());

        result.set(4, studyProtocol5.getId());

        return result;
    }

    private TestBean<Long, Long> createStudyProtocolListForSearchByParticipatingSite() {
        TestBean<Long, Long> result = new TestBean<Long, Long>();

        Organization organization = TestSchema.createOrganizationObj();
        TestSchema.addUpdObject(organization);
        result.input.add(organization.getId());

        StudyProtocol studyProtocol1 = createStudyProtocol();
        addFlag(studyProtocol1, StudyFlagReasonCode.DO_NOT_SUBMIT_TWEETS);
        addTweet(studyProtocol1, "My first tweet", TweetStatusCode.SENT);
        result.output.add(studyProtocol1.getId());

        StudyProtocol studyProtocol2 = createStudyProtocol();
        addFlag(studyProtocol2, StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES);
        Iterator<StudySite> it2 = studyProtocol2.getStudySites().iterator();
        it2.next();

        StudySite treatingStudySite2 = it2.next();
        treatingStudySite2.getHealthCareFacility()
                .setOrganization(organization);
        TestSchema.addUpdObject(treatingStudySite2.getHealthCareFacility());
        result.output.add(studyProtocol2.getId());

        StudyProtocol studyProtocol3 = createStudyProtocol();
        addFlag(studyProtocol3, StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES);
        Iterator<StudySite> it3 = studyProtocol3.getStudySites().iterator();
        it3.next();

        StudySite treatingStudySite3 = it3.next();
        treatingStudySite3.getHealthCareFacility()
                .setOrganization(organization);
        TestSchema.addUpdObject(treatingStudySite3.getHealthCareFacility());
        result.output.add(studyProtocol3.getId());

        StudyProtocol studyProtocol4 = createStudyProtocol();
        addFlag(studyProtocol4, StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES);
        result.output.add(studyProtocol4.getId());

        return result;
    }

    private void addTweet(StudyProtocol sp, String text,
            TweetStatusCode status) {
        Tweet t = new Tweet();
        t.setCreateDate(new Date());
        t.setSentDate(new Date());
        t.setStatus(status);
        t.setStudyProtocol(sp);
        t.setText(text);
        TestSchema.addUpdObject(t);
    }

    private void addFlag(StudyProtocol sp,
            StudyFlagReasonCode code) {
       StudyProtocolFlag f = new StudyProtocolFlag();
       f.setDateFlagged(new Date());
       f.setDeleted(false);
       f.setFlagReason(code);
       f.setStudyProtocol(sp);
       f.setFlaggingUser(TestSchema.user);
       TestSchema.addUpdObject(f);
    }

    private TestBean<Long, Long> createStudyProtocolListForSearchByAnatomicSites() {
        TestBean<Long, Long> result = new TestBean<Long, Long>();

        createStudyProtocol();

        StudyProtocol studyProtocol2 = createStudyProtocol();
        TestSchema.addUpdObject(studyProtocol2);
        result.input.add(studyProtocol2.getSummary4AnatomicSites().iterator()
                .next().getId());
        result.output.add(studyProtocol2.getId());

        createStudyProtocol();

        StudyProtocol studyProtocol4 = createStudyProtocol();
        AnatomicSite anatomicSite = createAnatomicSite("second");
        studyProtocol4.getSummary4AnatomicSites().add(anatomicSite);
        TestSchema.addUpdObject(studyProtocol4);
        result.input.add(anatomicSite.getId());
        result.output.add(studyProtocol4.getId());

        return result;
    }

    private TestBean<Long, Long> createStudyProtocolListForSearchByBioMarkers() {
        TestBean<Long, Long> result = new TestBean<Long, Long>();

        StudyProtocol studyProtocol1 = createStudyProtocol();
        TestSchema.addUpdObject(studyProtocol1);
        result.input.add(studyProtocol1.getPlannedActivities().iterator()
                .next().getId());
        result.output.add(studyProtocol1.getId());

        createStudyProtocol();

        StudyProtocol studyProtocol3 = createStudyProtocol();
        PlannedMarker plannedMarker = TestSchema.createPlannedMarker();
        plannedMarker.setStudyProtocol(studyProtocol3);
        TestSchema.addUpdObject(plannedMarker);
        TestSchema.addUpdObject(studyProtocol3);
        result.input.add(plannedMarker.getId());
        result.output.add(studyProtocol3.getId());

        createStudyProtocol();

        return result;
    }

    private TestBean<Long, Long> createStudyProtocolListForSearchByPdqDisease() {
        TestBean<Long, Long> result = new TestBean<Long, Long>();

        createStudyProtocol();

        StudyProtocol studyProtocol2 = createStudyProtocol();
        TestSchema.addUpdObject(studyProtocol2);
        PDQDisease pdqDisease2 = TestSchema.createPdqDisease("name2");
        TestSchema.addUpdObject(pdqDisease2);
        PDQDisease pdqDisease21 = TestSchema.createPdqDisease("name21");
        TestSchema.addUpdObject(pdqDisease21);
        PDQDiseaseParent parent = TestSchema.createPdqDiseaseParent(
                pdqDisease2, pdqDisease21);
        TestSchema.addUpdObject(parent);
        pdqDisease21.getDiseaseChildren().add(parent);
        pdqDisease2.getDiseaseParents().add(parent);
        TestSchema.addUpdObject(pdqDisease2);
        TestSchema.addUpdObject(pdqDisease21);
        StudyDisease studyDisease2 = TestSchema.createStudyDiseaseObj(
                studyProtocol2, pdqDisease2);
        studyProtocol2.getStudyDiseases().add(studyDisease2);
        TestSchema.addUpdObject(studyDisease2);
        TestSchema.addUpdObject(studyProtocol2);
        result.input.add(pdqDisease21.getId());
        result.output.add(studyProtocol2.getId());

        StudyProtocol studyProtocol3 = createStudyProtocol();
        TestSchema.addUpdObject(studyProtocol3);
        PDQDisease pdqDisease3 = TestSchema.createPdqDisease("name3");
        TestSchema.addUpdObject(pdqDisease3);
        StudyDisease studyDisease3 = TestSchema.createStudyDiseaseObj(
                studyProtocol3, pdqDisease3);
        studyProtocol3.getStudyDiseases().add(studyDisease3);
        TestSchema.addUpdObject(studyDisease3);
        TestSchema.addUpdObject(studyProtocol3);
        result.input.add(pdqDisease3.getId());
        result.output.add(studyProtocol3.getId());

        createStudyProtocol();

        return result;
    }

    private TestBean<Long, Long> createStudyProtocolListForSearchByIntervention() {
        TestBean<Long, Long> result = new TestBean<Long, Long>();

        createStudyProtocol();

        StudyProtocol studyProtocol1 = createStudyProtocol();
        studyProtocol1.getPlannedActivities().clear();
        Intervention intervention1 = createIntervention();
        TestSchema.addUpdObject(intervention1);
        PlannedActivity plannedActivity1 = createPlannedActivity(intervention1,
                studyProtocol1);
        TestSchema.addUpdObject(plannedActivity1);
        studyProtocol1.getPlannedActivities().add(plannedActivity1);
        TestSchema.addUpdObject(studyProtocol1);
        result.input.add(studyProtocol1.getPlannedActivities().iterator()
                .next().getIntervention().getId());
        result.output.add(studyProtocol1.getId());

        StudyProtocol studyProtocol2 = createStudyProtocol();
        studyProtocol2.getPlannedActivities().clear();
        Intervention intervention2 = createIntervention();
        InterventionAlternateName interventionAlternateName2 = createInterventionAlternateName(intervention2);
        intervention2.getInterventionAlternateNames().clear();
        intervention2.getInterventionAlternateNames().add(
                interventionAlternateName2);
        TestSchema.addUpdObject(intervention2);
        TestSchema.addUpdObject(interventionAlternateName2);
        PlannedActivity plannedActivity2 = createPlannedActivity(intervention2,
                studyProtocol2);
        TestSchema.addUpdObject(plannedActivity2);
        studyProtocol2.getPlannedActivities().add(plannedActivity2);
        TestSchema.addUpdObject(studyProtocol2);
        result.input.add(studyProtocol2.getPlannedActivities().iterator()
                .next().getIntervention().getId());
        result.input.add(studyProtocol2.getPlannedActivities().iterator()
                .next().getIntervention().getInterventionAlternateNames()
                .iterator().next().getId());
        result.output.add(studyProtocol2.getId());

        StudyProtocol studyProtocol3 = createStudyProtocol();
        studyProtocol3.getPlannedActivities().clear();
        Intervention intervention3 = createIntervention();
        InterventionAlternateName interventionAlternateName3 = createInterventionAlternateName(intervention3);
        intervention3.getInterventionAlternateNames().clear();
        intervention3.getInterventionAlternateNames().add(
                interventionAlternateName3);
        TestSchema.addUpdObject(intervention3);
        TestSchema.addUpdObject(interventionAlternateName3);
        PlannedActivity plannedActivity3 = createPlannedActivity(intervention3,
                studyProtocol3);
        TestSchema.addUpdObject(plannedActivity3);
        studyProtocol3.getPlannedActivities().add(plannedActivity3);
        TestSchema.addUpdObject(studyProtocol3);
        result.input.add(studyProtocol3.getPlannedActivities().iterator()
                .next().getIntervention().getInterventionAlternateNames()
                .iterator().next().getId());
        result.output.add(studyProtocol3.getId());

        createStudyProtocol();

        return result;
    }

    private TestBean<String, Long> createStudyProtocolListForSearchByLeadOrganizationTrialIdentifier() {
        TestBean<String, Long> result = new TestBean<String, Long>();

        StudyProtocol sp1 = createStudyProtocol();
        StudySite site = getLeadOrganizationStudySite(sp1.getStudySites());
        site.setLocalStudyProtocolIdentifier("ident1");
        TestSchema.addUpdObject(site);

        StudyProtocol sp2 = createStudyProtocol();
        StudySite site2 = getLeadOrganizationStudySite(sp2.getStudySites());
        site2.setLocalStudyProtocolIdentifier("ident2");
        TestSchema.addUpdObject(site2);
        result.input.add("ident2");
        result.output.add(sp2.getId());

        StudyProtocol sp3 = createStudyProtocol();
        StudySite site3 = getLeadOrganizationStudySite(sp3.getStudySites());
        site3.setLocalStudyProtocolIdentifier("ident3");
        TestSchema.addUpdObject(site3);

        return result;
    }

    private StudySite getLeadOrganizationStudySite(Set<StudySite> sites) {
        for (StudySite site : sites) {
            if (site.getFunctionalCode() == StudySiteFunctionalCode.LEAD_ORGANIZATION) {
                return site;
            }
        }
        return null;
    }

    private StudyProtocol createStudyProtocol() {
        COUNT++ ;
        StudyProtocol result = TestSchema.createStudyProtocolObj();
        SortedSet<StudySite> studySites = new TreeSet<StudySite>(
                new StudySiteComparator());
        StudySite leadOrganizationStudySite = createLeadOrganizationStudySite(result);
        leadOrganizationStudySite.setStudyProtocol(result);
        TestSchema.addUpdObject(leadOrganizationStudySite);
        StudySite treatingStudySite = createTreatingSiteStudySite(result);
        treatingStudySite.setStudyProtocol(result);
        TestSchema.addUpdObject(treatingStudySite);
        studySites.add(leadOrganizationStudySite);
        studySites.add(treatingStudySite);
        result.setStudySites(studySites);
        Set<AnatomicSite> summary4AnatomicSites = new TreeSet<AnatomicSite>(
                new AnatomicSiteComparator());
        summary4AnatomicSites.add(createAnatomicSite(result.getId() + "code"));
        result.setSummary4AnatomicSites(summary4AnatomicSites);
        PlannedMarker plannedMarker = TestSchema.createPlannedMarker();
        PlannedMarkerSyncWithCaDSR pmSync = TestSchema
                .createPlannedMarkerSyncWithCaDSRObj("name", COUNT);
        plannedMarker.setStudyProtocol(result);
        TestSchema.addUpdObject(pmSync);
        TestSchema.addUpdObject(plannedMarker);
        result.getPlannedActivities().add(plannedMarker);

        TestSchema.addUpdObject(result);
        return result;
    }

    private StudySite createLeadOrganizationStudySite(StudyProtocol sp) {
        StudySite result = TestSchema.createStudySiteObj(sp, null);

        result.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        result.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        result.setResearchOrganization(createResearchOrganization(null));
        List<StudySite> studySites = Arrays.asList(new StudySite[] { result });
        result.getResearchOrganization().setStudySites(studySites);
        TestSchema.addUpdObject(result.getResearchOrganization());
        TestSchema.addUpdObject(result);
        return result;
    }

    private StudySite createIdentifierAssignerStudySite(StudyProtocol sp,
            String orgName) {
        StudySite result = TestSchema.createStudySiteObj(sp, null);
        result.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        result.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        result.setResearchOrganization(createResearchOrganization(orgName));
        List<StudySite> studySites = Arrays.asList(new StudySite[] { result });
        result.getResearchOrganization().setStudySites(studySites);
        TestSchema.addUpdObject(result.getResearchOrganization());
        TestSchema.addUpdObject(result);
        return result;
    }

    private StudySite createTreatingSiteStudySite(StudyProtocol sp) {
        StudySite result = TestSchema.createStudySiteObj(sp,
                createHealthCareFacility());
        List<StudySite> studySites = Arrays.asList(new StudySite[] { result });
        result.getHealthCareFacility().setStudySites(studySites);
        TestSchema.addUpdObject(result.getHealthCareFacility());
        result.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        TestSchema.addUpdObject(result);
        
        StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
        ssas.setDateLastCreated(new Date());
        ssas.setDeleted(false);
        ssas.setStatusCode(RecruitmentStatusCode.ACTIVE);
        ssas.setStatusDate(new Timestamp(System.currentTimeMillis()));
        ssas.setStudySite(result);
        TestSchema.addUpdObject(ssas);
        result.getStudySiteAccrualStatuses().add(ssas);
        
        ssas = new StudySiteAccrualStatus();
        ssas.setDateLastCreated(new Date());
        ssas.setDeleted(false);
        ssas.setStatusCode(RecruitmentStatusCode.IN_REVIEW);
        ssas.setStatusDate(new Timestamp(System.currentTimeMillis()-DateUtils.MILLIS_IN_DAY));
        ssas.setStudySite(result);
        TestSchema.addUpdObject(ssas);
        result.getStudySiteAccrualStatuses().add(ssas);
        
        ssas = new StudySiteAccrualStatus();
        ssas.setDateLastCreated(new Date());
        ssas.setDeleted(true);
        ssas.setStatusCode(RecruitmentStatusCode.ENROLLING_BY_INVITATION);
        ssas.setStatusDate(new Timestamp(System.currentTimeMillis()+DateUtils.MILLIS_IN_DAY));
        ssas.setStudySite(result);
        TestSchema.addUpdObject(ssas);
        result.getStudySiteAccrualStatuses().add(ssas);

        
        return result;
    }

    private HealthCareFacility createHealthCareFacility() {
        Organization org = createOrganization(null);
        org.setCountryName("USA");
        org.setState("MD");
        org.setCity("Rockville");
        TestSchema.addUpdObject(org);
        HealthCareFacility result = TestSchema.createHealthCareFacilityObj(org);
        TestSchema.addUpdObject(result);
        return result;
    }

    private ResearchOrganization createResearchOrganization(String name) {
        ResearchOrganization result = new ResearchOrganization();
        Organization org = createOrganization(name);
        result.setOrganization(org);
        result.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        result.setIdentifier("abc" + org.getId());
        TestSchema.addUpdObject(result);
        return result;
    }

    private Organization createOrganization(String name) {
        Organization result = TestSchema.createOrganizationObj();
        result.setCountryName("USA");
        result.setState("VA");
        result.setCity("Arlington");
        if (name != null) {
            result.setName(name);
        }
        TestSchema.addUpdObject(result);
        return result;
    }

    private AnatomicSite createAnatomicSite(String preferredName) {
        AnatomicSite result = TestSchema.createAnatomicSiteObj(preferredName);
        TestSchema.addUpdObject(result);
        return result;
    }

    private Intervention createIntervention() {
        Intervention result = new Intervention();
        result.setName("Chocolate Bar");
        result.setDescriptionText("Oral intervention to improve morale");
        result.setDateLastUpdated(new Date());
        result.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        result.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2000"));
        result.setTypeCode(InterventionTypeCode.DRUG);
        return result;
    }

    private PlannedActivity createPlannedActivity(Intervention inv,
            StudyProtocol sp) {
        PlannedActivity result = new PlannedActivity();
        result.setCategoryCode(ActivityCategoryCode.INTERVENTION);
        result.setDateLastUpdated(new Date());
        result.setIntervention(inv);
        result.setLeadProductIndicator(true);
        result.setStudyProtocol(sp);
        result.setSubcategoryCode(ActivitySubcategoryCode.DIETARY_SUPPLEMENT
                .getCode());
        return result;
    }

    private InterventionAlternateName createInterventionAlternateName(
            Intervention inv) {
        InterventionAlternateName inresult = new InterventionAlternateName();
        inresult.setDateLastUpdated(new Date());
        inresult.setIntervention(inv);
        inresult.setName("Hershey");
        inresult.setStatusCode(ActiveInactiveCode.ACTIVE);
        inresult.setStatusDateRangeLow(ISOUtil
                .dateStringToTimestamp("1/1/2000"));
        inresult.setNameTypeCode("synonym");
        return inresult;
    }

    private static class TestBean<I, O> {
        List<I> input = new ArrayList<I>();
        List<O> output = new ArrayList<O>();
    }

}
