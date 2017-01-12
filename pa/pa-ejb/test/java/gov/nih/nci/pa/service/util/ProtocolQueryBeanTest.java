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

import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_READY_FOR_QC;
import static gov.nih.nci.pa.enums.MilestoneCode.READY_FOR_TSR;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_READY_FOR_QC;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_ACCEPTED;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_RECEIVED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 * @author dkrylov
 * 
 */
public class ProtocolQueryBeanTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    ProtocolQueryServiceBean bean;

    @Before
    public void init() throws Exception {
        bean = (ProtocolQueryServiceBean) getEjbBean(ProtocolQueryServiceBean.class);
        TestSchema.primeData();

        StudyProtocol sp = TestSchema.studyProtocols.get(0);

        // DWS
        DocumentWorkflowStatus submitted = TestSchema
                .createDocumentWorkflowStatus(sp);
        submitted.setStatusDateRangeLow(new Timestamp(System
                .currentTimeMillis() - DateUtils.MILLIS_PER_DAY));
        submitted.setStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        final Session s = PaHibernateUtil.getCurrentSession();
        s.save(submitted);
        DocumentWorkflowStatus accepted = TestSchema
                .createDocumentWorkflowStatus(sp);
        s.save(accepted);

        s.flush();

    }

    /**
     * tests if tree traversal is correct
     * 
     * @throws PAException
     */
    @Test
    public void searchByNciId() throws PAException {
        StudyProtocolQueryCriteria crit = new StudyProtocolQueryCriteria();
        crit.setIdentifierType("NCI");
        crit.setIdentifier("NCI-2009-00001");
        Assert.assertEquals(1, bean.getStudyProtocolByCriteria(crit).size());

    }

    /**
     * tests if tree traversal is correct
     * 
     * @throws PAException
     */
    @Test
    public void testDWS() throws PAException {
        StudyProtocolQueryDTO dto = findProtocol();
        assertEquals("NCI-2009-00001", dto.getNciIdentifier());
        assertEquals(DocumentWorkflowStatusCode.ACCEPTED,
                dto.getDocumentWorkflowStatusCode());
        assertEquals(DocumentWorkflowStatusCode.SUBMITTED,
                dto.getPreviousDocumentWorkflowStatusCode());

    }

    /**
     * tests if tree traversal is correct
     *
     * @throws PAException
     */
    @Test
    public void testSOS() throws PAException {
        StudyProtocol sp = TestSchema.studyProtocols.get(0);

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.ENROLLING_BY_INVITATION);
        sos.setStatusDate(TestSchema.TOMMORROW);
        sos.setStudyProtocol(sp);
        sos.setDeleted(true);
        TestSchema.addUpdObject(sos);

        StudyProtocolQueryDTO dto = findProtocol();
        assertEquals(StudyStatusCode.ACTIVE, dto.getStudyStatusCode());

        sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.ENROLLING_BY_INVITATION);
        sos.setStatusDate(TestSchema.TOMMORROW);
        sos.setStudyProtocol(sp);
        sos.setDeleted(false);
        TestSchema.addUpdObject(sos);

        dto = findProtocol();
        assertEquals(StudyStatusCode.ENROLLING_BY_INVITATION,
                dto.getStudyStatusCode());

    }


    /**
     * tests if tree traversal is correct
     *
     * @throws PAException
     */
    @Test
    public void testRetrieveStudyProtocolsWithProgramCodes() throws PAException {
        //Given few studies in database having cancer in official title, and program code association
        StudyProtocolQueryCriteria crit = new StudyProtocolQueryCriteria();
        crit.setOfficialTitle("cancer");

        //When I load it
        List<StudyProtocolQueryDTO>  list = bean.getStudyProtocolByCriteria(crit);

        //then program codes should not be loaded.
        assertNotNull(list);
        for(StudyProtocolQueryDTO dto : list) {
            assertTrue(dto.getProgramCodes().isEmpty());
        }

        //When I query with program codes
        list = bean.getStudyProtocolByCriteria(crit,
                ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS,
                ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES );

        //Then I must get program codes in the result.
        assertNotNull(list);
        for(StudyProtocolQueryDTO dto : list) {
            assertFalse(dto.getProgramCodes().isEmpty());
            assertEquals(2, dto.getProgramCodes().size());
            assertEquals(TestSchema.theProgramCodes.get(0).getId(), dto.getProgramCodes().get(0).getId()) ;
        }

    }

    /**
     *  tests if filter by program code is returning results properly
     */
    @Test
    public void testFilterByProgramCodeIds() throws PAException {

        //When I filter by program codes that are not yet associated with any study
        StudyProtocolQueryCriteria crit = new StudyProtocolQueryCriteria();
        crit.getProgramCodeIds().add(TestSchema.theProgramCodes.get(4).getId());
        crit.getProgramCodeIds().add(TestSchema.theProgramCodes.get(5).getId());

        List<StudyProtocolQueryDTO>  list = bean.getStudyProtocolByCriteria(crit,
                ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES);
        //Then I should see no result
        assertTrue(CollectionUtils.isEmpty(list));

        //when I ask for program code that is associated to the, to the filter list above
        crit.getProgramCodeIds().add(TestSchema.theProgramCodes.get(0).getId());
        list = bean.getStudyProtocolByCriteria(crit,
                ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES);
        //Then I should get the protocols (at least 2) back
        assertTrue(CollectionUtils.isNotEmpty(list));
        assertEquals(2, list.size());
        for (StudyProtocolQueryDTO sp : list) {
            List<Long> pgIds = new ArrayList<Long>();
            for(ProgramCodeDTO pc : sp.getProgramCodes()) {
                pgIds.add(pc.getId());
            }
            assertTrue(pgIds.contains(TestSchema.theProgramCodes.get(0).getId()));
        }

        for (StudyProtocolQueryDTO sp : list) {
            List<Long> pgIds = new ArrayList<Long>();
            for(ProgramCodeDTO pc : sp.getProgramCodesAsOrderedList()) {
                pgIds.add(pc.getId());
            }
            assertTrue(pgIds.contains(TestSchema.theProgramCodes.get(0).getId()));
        }

    }


    /**
     * tests if tree traversal is correct
     *
     * @throws PAException
     */
    @Test
    public void testSearchProtocolsByReportingPeriodAndStatus() throws PAException {

        // When there exist no study with TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION status code
        List<StudyProtocolQueryDTO> studyProtocolQueryDTOs = findProtocolsWithinReportingPeriodHavingStatus(
                TestSchema.YESTERDAY, TestSchema.TOMMORROW);
        //then I must not get any data
        assertEquals(0, studyProtocolQueryDTOs.size());

        //when I update a study, with TEMPORARILY_CLOSED_TO_ACCRUAL
        StudyProtocol sp = TestSchema.studyProtocols.get(0);

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL);
        sos.setStatusDate(TestSchema.TODAY);
        sos.setStudyProtocol(sp);
        sos.setDeleted(true);
        TestSchema.addUpdObject(sos);

        //then also I should not get any maches for TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION
        studyProtocolQueryDTOs = findProtocolsWithinReportingPeriodHavingStatus(
                TestSchema.YESTERDAY, TestSchema.TOMMORROW);
        //then I must not get any data
        assertEquals(0, studyProtocolQueryDTOs.size());


        //When I update a study with TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION
        sp = TestSchema.studyProtocols.get(1);
        sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        sos.setStatusDate(TestSchema.TODAY);
        sos.setStudyProtocol(sp);
        sos.setDeleted(false);
        TestSchema.addUpdObject(sos);

        //then I must see the correct study
        studyProtocolQueryDTOs = findProtocolsWithinReportingPeriodHavingStatus(
                TestSchema.YESTERDAY, TestSchema.TOMMORROW);
        assertEquals(1, studyProtocolQueryDTOs.size());
        assertEquals(sp.getId(), studyProtocolQueryDTOs.get(0).getStudyProtocolId());

        //But, when I search with out of range dates
        studyProtocolQueryDTOs = findProtocolsWithinReportingPeriodHavingStatus(
                TestSchema.TOMMORROW, TestSchema.DAY_AFTER_TOMMORROW);
        //then I should still see results.
        assertEquals(1, studyProtocolQueryDTOs.size());
        assertEquals(sp.getId(), studyProtocolQueryDTOs.get(0).getStudyProtocolId());

        //when I delete the status TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION on a known study
        sos.setDeleted(true);
        TestSchema.addUpdObject(sos);

        //And I search  TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION
        studyProtocolQueryDTOs = findProtocolsWithinReportingPeriodHavingStatus(
                TestSchema.YESTERDAY, TestSchema.TOMMORROW);

        //That study (which is the only study) should not be present in search
        assertEquals(0, studyProtocolQueryDTOs.size());

    }

    /**
     * tests if tree traversal is correct
     * 
     * @throws PAException
     */
    @Test
    public void testMilestones() throws PAException {
        StudyProtocol sp = TestSchema.studyProtocols.get(0);

        createMilestoneHistory(sp, SUBMISSION_RECEIVED, SUBMISSION_ACCEPTED,
                ADMINISTRATIVE_PROCESSING_START_DATE,
                ADMINISTRATIVE_PROCESSING_COMPLETED_DATE);

        StudyProtocolQueryDTO dto = findProtocol();
        assertEquals(ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, dto
                .getMilestones().getActiveMilestone().getMilestone());
        assertEquals(ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, dto
                .getMilestones().getLastMilestone().getMilestone());
        assertEquals(ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, dto
                .getMilestones().getAdminMilestone().getMilestone());
        assertNull(dto.getMilestones().getScientificMilestone().getMilestone());
        assertNull(dto.getMilestones().getStudyMilestone().getMilestone());

        createMilestoneHistory(sp, SUBMISSION_RECEIVED, SUBMISSION_ACCEPTED,
                ADMINISTRATIVE_PROCESSING_START_DATE,
                ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                ADMINISTRATIVE_READY_FOR_QC, SCIENTIFIC_PROCESSING_START_DATE,
                SCIENTIFIC_PROCESSING_COMPLETED_DATE, SCIENTIFIC_READY_FOR_QC);

        dto = findProtocol();
        assertEquals(SCIENTIFIC_READY_FOR_QC, dto.getMilestones()
                .getActiveMilestone().getMilestone());
        assertEquals(SCIENTIFIC_READY_FOR_QC, dto.getMilestones()
                .getLastMilestone().getMilestone());
        assertEquals(ADMINISTRATIVE_READY_FOR_QC, dto.getMilestones()
                .getAdminMilestone().getMilestone());
        assertEquals(SCIENTIFIC_READY_FOR_QC, dto.getMilestones()
                .getScientificMilestone().getMilestone());
        assertNull(dto.getMilestones().getStudyMilestone().getMilestone());

        createMilestoneHistory(sp, SUBMISSION_RECEIVED, SUBMISSION_ACCEPTED,
                ADMINISTRATIVE_PROCESSING_START_DATE,
                ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                ADMINISTRATIVE_READY_FOR_QC, SCIENTIFIC_PROCESSING_START_DATE,
                SCIENTIFIC_PROCESSING_COMPLETED_DATE, SCIENTIFIC_READY_FOR_QC,
                READY_FOR_TSR);

        dto = findProtocol();
        assertEquals(READY_FOR_TSR, dto.getMilestones().getActiveMilestone()
                .getMilestone());
        assertEquals(READY_FOR_TSR, dto.getMilestones().getLastMilestone()
                .getMilestone());
        assertEquals(READY_FOR_TSR, dto.getMilestones().getStudyMilestone()
                .getMilestone());

    }

    private void createMilestoneHistory(StudyProtocol sp,
            MilestoneCode... milestones) {
        final Session s = PaHibernateUtil.getCurrentSession();
        s.createQuery(
                "delete from StudyMilestone sm where sm.studyProtocol.id="
                        + sp.getId()).executeUpdate();
        int days = -milestones.length;
        for (MilestoneCode code : milestones) {
            days++;
            StudyMilestone adminStart = TestSchema.createStudyMilestoneObj(
                    code.name(), sp);
            adminStart.setMilestoneCode(code);
            adminStart.setMilestoneDate(new Timestamp(System
                    .currentTimeMillis() + DateUtils.MILLIS_PER_DAY * days));
            s.save(adminStart);
        }

        s.flush();
    }

    /**
     * @return
     * @throws PAException
     */
    private StudyProtocolQueryDTO findProtocol() throws PAException {
        StudyProtocolQueryCriteria crit = new StudyProtocolQueryCriteria();
        crit.setIdentifierType("NCI");
        crit.setIdentifier("NCI-2009-00001");

        StudyProtocolQueryDTO dto = bean.getStudyProtocolByCriteria(crit)
                .get(0);
        return dto;
    }


    /**
     * @return
     * @throws PAException
     */
    private  List<StudyProtocolQueryDTO>  findProtocolsWithinReportingPeriodHavingStatus(Date from, Date to) throws PAException {
        StudyProtocolQueryCriteria crit = new StudyProtocolQueryCriteria();
        crit.populateReportingPeriodStatusCriterion(from, to,
                StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        List<StudyProtocolQueryDTO> dtoList =  bean.getStudyProtocolByCriteria(crit);
        return dtoList;
    }
}
