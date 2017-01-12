/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author hreinhart
 * 
 */
public class StudyOverallStatusServiceTest extends AbstractEjbTestCase {
    private StudyOverallStatusBeanLocal bean;
    private Ii spIi;
    private StudyProtocolServiceLocal studyProtocolService;
    private StudyRecruitmentStatusBeanLocal studyRecruitmentStatusBeanLocal;

    /**
     * Initialization method.
     */
    @Before
    public void setUp() {
        bean = (StudyOverallStatusServiceBean) getEjbBean(StudyOverallStatusServiceBean.class);
        studyProtocolService = (StudyProtocolServiceLocal) getEjbBean(StudyProtocolBeanLocal.class);
        studyRecruitmentStatusBeanLocal = (StudyRecruitmentStatusBeanLocal) getEjbBean(StudyRecruitmentStatusBeanLocal.class);
        TestSchema.primeData();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds
                .get(0));
        StudySourceInterceptor.STUDY_SOURCE_CONTEXT.remove();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void create() throws Exception {
        TestSchema.addAbstractedWorkflowStatus(IiConverter.convertToLong(spIi));

        StudyOverallStatusDTO dto = bean.getCurrentByStudyProtocol(spIi);
        try {
            bean.create(dto);
            fail("StudyOverallStatus objects cannot be modified.");
        } catch (PAException e) {
            // expected behavior
        }

        // Following tests assume current status is ACTIVE.
        assertTrue(StudyStatusCode.ACTIVE.getCode().equals(
                dto.getStatusCode().getCode()));
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.CLOSED_TO_ACCRUAL));
        dto.setStatusDate(TsConverter.convertToTs(TestSchema.TOMMORROW));
        dto.setStudyProtocolIdentifier(spIi);
        bean.create(dto);

        StudyOverallStatusDTO result = bean.getCurrentByStudyProtocol(spIi);
        assertNotNull(IiConverter.convertToLong(result.getIdentifier()));
        assertEquals(result.getStatusCode().getCode(), dto.getStatusCode()
                .getCode());
        assertEquals(TsConverter.convertToTimestamp(result.getStatusDate()),
                TsConverter.convertToTimestamp(dto.getStatusDate()));
        assertEquals(IiConverter.convertToLong(spIi),
                IiConverter.convertToLong(result.getStudyProtocolIdentifier()));
    }

    @Test
    public void update() throws Exception {
        StudyOverallStatusDTO currentStatus = bean
                .getCurrentByStudyProtocol(spIi);
        currentStatus.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.IN_REVIEW));
        currentStatus.setReasonText(StConverter
                .convertToSt("Trial submitted with incorrect overall status."));
        currentStatus.setStatusDate(TsConverter.convertToTs(TestSchema.TODAY));

        StudyOverallStatusDTO updatedStatus = bean.update(currentStatus);
        assertEquals(
                CdConverter.convertCdToString(currentStatus.getStatusCode()),
                CdConverter.convertCdToString(updatedStatus.getStatusCode()));
        assertEquals(
                StConverter.convertToString(currentStatus.getReasonText()),
                StConverter.convertToString(updatedStatus.getReasonText()));
        assertEquals(
                TsConverter.convertToTimestamp(currentStatus.getStatusDate()),
                TsConverter.convertToTimestamp(updatedStatus.getStatusDate()));
    }

    private InterventionalStudyProtocol createStudyProtocol()
            throws PAException {
        InterventionalStudyProtocol sp = createInterventionalProtocol();
        Ii spId = IiConverter.convertToStudyProtocolIi(sp.getId());
        StudyOverallStatus inReview = createStudyOverallStatusobj(sp);
        inReview.setStatusCode(StudyStatusCode.IN_REVIEW);
        bean.create(Converters.get(StudyOverallStatusConverter.class)
                .convertFromDomainToDto(inReview));
        assertEquals(bean.getByStudyProtocol(spId).size(), 1);

        return sp;
    }

    /**
     * @return
     */
    private InterventionalStudyProtocol createInterventionalProtocol() {
        InterventionalStudyProtocol sp = new InterventionalStudyProtocol();
        sp = (InterventionalStudyProtocol) TestSchema
                .createStudyProtocolObj(sp);
        sp = TestSchema.createInterventionalStudyProtocolObj(sp);
        TestSchema.addUpdObject(sp);

        DocumentWorkflowStatus docWorkflow = TestSchema
                .createDocumentWorkflowStatus(sp);
        TestSchema.addUpdObject(docWorkflow);
        return sp;
    }

    @Test
    public void getByProtocolTest() throws Exception {
        List<StudyOverallStatusDTO> statusList = bean.getByStudyProtocol(spIi);
        assertEquals(2, statusList.size());

        StudyOverallStatusDTO dto = bean.getCurrentByStudyProtocol(spIi);
        assertEquals(
                IiConverter.convertToLong(statusList.get(1).getIdentifier()),
                (IiConverter.convertToLong(dto.getIdentifier())));
    }

    @Test
    public void getCurrentByStudyProtocol() throws Exception {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.APPROVED);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        s.saveOrUpdate(sos);

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        s.saveOrUpdate(sos);

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        sos.setStatusDate(new Timestamp(date.getTime()));
        sos.setDeleted(true);
        s.saveOrUpdate(sos);

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -2).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        assertEquals(
                StudyStatusCode.ACTIVE.getCode(),
                bean.getCurrentByStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(spNew.getId()))
                        .getStatusCode().getCode());
        assertEquals(
                DateUtils.addDays(date, -1),
                bean.getCurrentByStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(spNew.getId()))
                        .getStatusDate().getValue());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createTest() throws Exception {
        // simulate creating new protocol using registry
        StudyProtocol spNew = TestSchema.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("1/1/1999")));
        dto.setReasonText(StConverter.convertToSt("Test"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        Ii initialIi = null;
        dto.setIdentifier(initialIi);
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));

        StudyOverallStatusDTO resultDto = bean.create(dto);
        assertFalse(ISOUtil.isIiNull(resultDto.getIdentifier()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createDoesNotCreateNewRecord() throws Exception {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();
        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        dto.setStatusDate(TsConverter.convertToTs(date));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));

        StudyOverallStatusDTO current = bean.create(dto);
        s.flush();

        // merely updating a comment should not lead to creation of a new status
        // record because status code & date stay the same
        StudyOverallStatusDTO newStatus = new StudyOverallStatusDTO();
        newStatus.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.IN_REVIEW));
        newStatus.setStatusDate(TsConverter.convertToTs(date));
        newStatus.setAdditionalComments(StConverter.convertToSt("An update"));
        newStatus.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew
                .getId()));
        StudyOverallStatusDTO afterCreate = bean.create(newStatus);

        assertEquals(current.getIdentifier(), afterCreate.getIdentifier());
        assertEquals(
                "An update",
                bean.getCurrentByStudyProtocol(
                        IiConverter.convertToIi(spNew.getId()))
                        .getAdditionalComments().getValue());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void nullInDateTest() throws Exception {
        StudyProtocol spNew = TestSchema.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(null);
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        dto.setReasonText(StConverter.convertToSt("Trial with Reason Text"));
        dto.setIdentifier(null);
        try {
            bean.create(dto);
            fail("PAException should have been thrown for null in status date.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setStatusDate(TsConverter.convertToTs(null));
        try {
            bean.create(dto);
            fail("PAException should have been thrown for Ts null in status date.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("1/1/2000")));
        dto = bean.create(dto);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
    }

    @Test
    public void iiRootTest() throws Exception {
        List<StudyOverallStatusDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        StudyOverallStatusDTO dto = dtoList.get(0);
        assertEquals(dto.getIdentifier().getRoot(),
                IiConverter.STUDY_OVERALL_STATUS_ROOT);
        assertTrue(StringUtils.isNotEmpty(dto.getIdentifier()
                .getIdentifierName()));
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(),
                IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createWithReasonTextTest() throws Exception {
        // simulate creating new protocol using registry
        StudyProtocol spNew = TestSchema.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("1/1/1999")));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        Ii initialIi = null;
        dto.setIdentifier(initialIi);
        try {
            bean.create(dto);
        } catch (PAException e) {
            assertTrue(StringUtils
                    .startsWith(e.getMessage(),
                            "Validation Exception A reason must be entered when the study status "));
        }
        dto.setReasonText(StConverter.convertToSt(RandomStringUtils
                .random(2001)));
        try {
            bean.create(dto);
        } catch (PAException e) {
            assertTrue(StringUtils
                    .startsWith(e.getMessage(),
                            "Validation Exception Reason must be less than 2000 characters."));
        }
        dto.setReasonText(StConverter.convertToSt(RandomStringUtils
                .random(2000)));
        bean.create(dto);
    }

    @Test
    public void validateWithBadStatusCode() throws Exception {
        TestSchema.addAbstractedWorkflowStatus(IiConverter.convertToLong(spIi));
        StudyProtocolDTO spDto = studyProtocolService.getStudyProtocol(spIi);
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertStringToCd("bad status code"));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("1/1/1999")));
        dto.setReasonText(StConverter.convertToSt(RandomStringUtils
                .random(2001)));
        dto.setStudyProtocolIdentifier(spIi);
        dto.setIdentifier(null);
        try {
            bean.validate(dto, spDto);
        } catch (PAException e) {
            assertTrue(e
                    .getMessage()
                    .startsWith(
                            "Validation Exception Invalid new study status: 'bad status code'."));
        }
    }

    private StudyOverallStatus createStudyOverallStatusobj(StudyProtocol sp) {
        StudyOverallStatus create = new StudyOverallStatus();
        Timestamp now = new Timestamp(DateUtils.addDays(new Date(), -1)
                .getTime());
        create.setStudyProtocol(sp);
        create.setStatusCode(StudyStatusCode.ACTIVE);
        create.setStatusDate(now);
        create.setUserLastUpdated(TestSchema.getUser());
        create.setDateLastUpdated(now);
        return create;
    }

    /**
     * test the createStudyRecruitmentStatus method.
     */
    @Test
    public void testCreateStudyRecruitmentStatus() {
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        StudyOverallStatus bo = new StudyOverallStatus();
        bo.setStatusCode(StudyStatusCode.ACTIVE);
        bo.setStatusDate(PAUtil.dateStringToTimestamp("1/1/2001"));
        bo.setStudyProtocol(sp);
        bo.setCommentText(null);
        StudyRecruitmentStatus srs = bean.createStudyRecruitmentStatus(bo);
        assertNotNull("No result returned", srs);
        assertEquals("Wrong status code", RecruitmentStatusCode.ACTIVE,
                srs.getStatusCode());
        assertEquals("Wrong status date",
                PAUtil.dateStringToTimestamp("1/1/2001"), bo.getStatusDate());
        assertEquals("Wrong strudy protocol", sp, bo.getStudyProtocol());
    }

    @Test
    public void delete() throws Exception {
        // simulate creating new protocol using registry
        StudyProtocol spNew = createStudyProtocol();
        final Ii studyID = IiConverter.convertToStudyProtocolIi(spNew.getId());

        // ACTIVE
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(new Date()));
        dto.setReasonText(StConverter.convertToSt("Test"));
        dto.setStudyProtocolIdentifier(studyID);
        StudyOverallStatusDTO active = bean.create(dto);

        // Current recrutiment status is also ACTIVE
        assertEquals(
                StudyStatusCode.ACTIVE.getCode(),
                studyRecruitmentStatusBeanLocal
                        .getCurrentByStudyProtocol(studyID).getStatusCode()
                        .getCode());

        bean.delete(active.getIdentifier());
        assertNull(bean.get(active.getIdentifier()));

        assertEquals(
                StudyStatusCode.IN_REVIEW.getCode(),
                studyRecruitmentStatusBeanLocal
                        .getCurrentByStudyProtocol(studyID).getStatusCode()
                        .getCode());
    }

    /**
     * test the createStudyRecruitmentStatus method.
     * 
     * @throws PAException
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testRelaxedValidation() throws PAException {
        // simulate creating new protocol using registry
        final Date date = new Date();
        StudyProtocol spNew = createStudyProtocol();

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.WITHDRAWN));
        dto.setStatusDate(TsConverter.convertToTs(date));
        dto.setReasonText(StConverter.convertToSt("Test"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));

        StudyOverallStatusDTO resultDto = bean.create(dto);
        assertFalse(ISOUtil.isIiNull(resultDto.getIdentifier()));

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(DateUtils.addDays(date, 1)));
        dto.setReasonText(StConverter.convertToSt("Test"));
        final Ii studyID = IiConverter.convertToStudyProtocolIi(spNew.getId());
        dto.setStudyProtocolIdentifier(studyID);

        bean.create(dto);

    }

    @Test
    public void testGetStatusHistoryByProtocol() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL);
        sos.setStatusDate(new Timestamp(date.getTime()));
        sos.setAdditionalComments("Additional comment 1");
        sos.setCommentText("Why stopped 1");
        sos.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        sos.setDateLastUpdated(new Date());
        s.saveOrUpdate(sos);

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        sos.setStatusDate(new Timestamp(date.getTime()));
        sos.setAdditionalComments("Additional comment 2");
        sos.setCommentText("Why stopped 2");
        sos.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        sos.setDateLastUpdated(new Date());
        s.saveOrUpdate(sos);

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        sos.setAdditionalComments("Additional comment 3");
        sos.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        sos.setDateLastUpdated(new Date());
        s.saveOrUpdate(sos);

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -2).getTime()));
        sos.setAdditionalComments("Additional comment 4");
        sos.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        sos.setDateLastUpdated(new Date());
        sos.setDeleted(true);
        s.saveOrUpdate(sos);

        s.flush();

        List<StatusDto> hist = bean.getStatusHistoryByProtocol(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(3, hist.size());

        assertEquals(StudyStatusCode.ACTIVE.name(), hist.get(0).getStatusCode());
        assertEquals(new Timestamp(DateUtils.addDays(date, -1).getTime()), hist
                .get(0).getStatusDate());
        assertEquals("Additional comment 3", hist.get(0).getComments());
        assertTrue(StringUtils.isBlank(hist.get(0).getReason()));
        assertNotNull(hist.get(0).getId());

        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.name(), hist
                .get(1).getStatusCode());
        assertEquals(new Timestamp(date.getTime()), hist.get(1).getStatusDate());
        assertEquals("Additional comment 1", hist.get(1).getComments());
        assertEquals("Why stopped 1", hist.get(1).getReason());
        assertNotNull(hist.get(1).getId());

        assertEquals(
                StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION
                        .name(),
                hist.get(2).getStatusCode());
        assertEquals(new Timestamp(date.getTime()), hist.get(2).getStatusDate());
        assertEquals("Additional comment 2", hist.get(2).getComments());
        assertEquals("Why stopped 2", hist.get(2).getReason());
        assertNotNull(hist.get(2).getId());

        // Additionally, ensure the current status is properly determined as
        // well by other bean methods.
        StudyOverallStatusDTO current = bean
                .getCurrentByStudyProtocol(IiConverter
                        .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(
                StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                CdConverter.convertCdToEnum(StudyStatusCode.class,
                        current.getStatusCode()));

        // Additionally, ensure getByProtocol methods sorts in the same order as
        // history.
        List<StudyOverallStatusDTO> list = bean.getByStudyProtocol(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(3, list.size());
        assertEquals(StudyStatusCode.ACTIVE, CdConverter.convertCdToEnum(
                StudyStatusCode.class, list.get(0).getStatusCode()));
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL,
                CdConverter.convertCdToEnum(StudyStatusCode.class, list.get(1)
                        .getStatusCode()));
        assertEquals(
                StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                CdConverter.convertCdToEnum(StudyStatusCode.class, list.get(2)
                        .getStatusCode()));

    }

    @Test
    public void testStatusHistoryHasWarningsErrors() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -3).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        assertFalse(bean.statusHistoryHasWarnings(IiConverter
                .convertToStudyProtocolIi(spNew.getId())));
        assertFalse(bean.statusHistoryHasErrors(IiConverter
                .convertToStudyProtocolIi(spNew.getId())));

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -2).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        // In Review to Active should produce a missing status warning.
        assertTrue(bean.statusHistoryHasWarnings(IiConverter
                .convertToStudyProtocolIi(spNew.getId())));
        assertFalse(bean.statusHistoryHasErrors(IiConverter
                .convertToStudyProtocolIi(spNew.getId())));

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.COMPLETE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        // Active to Complete should produce an warning due to invalid status
        // transition.
        assertTrue(bean.statusHistoryHasWarnings(IiConverter
                .convertToStudyProtocolIi(spNew.getId())));
        assertFalse(bean.statusHistoryHasErrors(IiConverter
                .convertToStudyProtocolIi(spNew.getId())));

    }

    @Test
    public void getByStudyProtocolWithTransitionValidations()
            throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -3).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        List<StudyOverallStatusDTO> hist = bean
                .getByStudyProtocolWithTransitionValidations(IiConverter
                        .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(1, hist.size());
        assertTrue(StringUtils.isBlank(hist.get(0).getErrors().getValue()));
        assertTrue(StringUtils.isBlank(hist.get(0).getWarnings().getValue()));

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -2).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        // In Review to Active should produce a missing status warning.
        hist = bean.getByStudyProtocolWithTransitionValidations(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(2, hist.size());
        assertTrue(StringUtils.isBlank(hist.get(0).getErrors().getValue()));
        assertTrue(StringUtils.isBlank(hist.get(0).getWarnings().getValue()));
        assertTrue(StringUtils.isBlank(hist.get(1).getErrors().getValue()));
        assertEquals("Interim status [APPROVED] is missing. ", hist.get(1)
                .getWarnings().getValue());

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.COMPLETE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        // Active to Complete should produce an error due to invalid status
        // transition.
        hist = bean.getByStudyProtocolWithTransitionValidations(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(3, hist.size());
        assertTrue(StringUtils.isBlank(hist.get(0).getErrors().getValue()));
        assertTrue(StringUtils.isBlank(hist.get(0).getWarnings().getValue()));
        assertTrue(StringUtils.isBlank(hist.get(1).getErrors().getValue()));
        assertEquals("Interim status [APPROVED] is missing. ", hist.get(1)
                .getWarnings().getValue());
        assertEquals(
                "Interim status [CLOSED TO ACCRUAL] is missing. Interim status [CLOSED TO ACCRUAL AND INTERVENTION] is missing. ",
                hist.get(2).getWarnings().getValue());

        assertTrue(StringUtils.isBlank(hist.get(2).getErrors().getValue()));
    }

    @Test
    public void softDelete() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -3).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        List<StudyOverallStatusDTO> hist = bean
                .getByStudyProtocolWithTransitionValidations(IiConverter
                        .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(1, hist.size());

        StudyOverallStatusDTO dto = hist.get(0);
        try {
            bean.softDelete(dto);
            fail();
        } catch (PAException e) {
            assertEquals("A comment is required when deleting a status",
                    e.getMessage());
        }

        dto.setAdditionalComments(StConverter.convertToSt("I am deleted"));
        bean.softDelete(dto);
        s.flush();
        s.refresh(sos);
        assertTrue(sos.getDeleted());
        assertEquals("I am deleted", sos.getAdditionalComments());
        assertTrue(DateUtils.isSameDay(new Date(), sos.getDateLastUpdated()));
        assertEquals(
                CSMUserService.getInstance().getCSMUser(
                        UsernameHolder.getUser()), sos.getUserLastUpdated());
        assertEquals(
                0,
                bean.getByStudyProtocolWithTransitionValidations(
                        IiConverter.convertToStudyProtocolIi(spNew.getId()))
                        .size());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void getDeletedByStudyProtocol() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -3).getTime()));
        s.saveOrUpdate(sos);
        s.flush();

        List<StudyOverallStatusDTO> hist = bean
                .getDeletedByStudyProtocol(IiConverter
                        .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(0, hist.size());

        StudyOverallStatusDTO dto = bean.get(IiConverter.convertToIi(sos
                .getId()));
        dto.setAdditionalComments(StConverter.convertToSt("I am deleted"));
        bean.softDelete(dto);
        s.flush();
        s.refresh(sos);

        hist = bean.getDeletedByStudyProtocol(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(1, hist.size());
        assertEquals(sos.getId().toString(), hist.get(0).getIdentifier()
                .getExtension());
        assertTrue(BlConverter.convertToBool(hist.get(0).getDeleted()));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void createStatusHistory() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();
        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        List<StudyOverallStatusDTO> hist = new ArrayList<>();

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        dto.setStatusDate(TsConverter.convertToTs(date));
        hist.add(dto);

        dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        dto.setStatusDate(TsConverter.convertToTs(date));
        dto.setReasonText(StConverter.convertToSt("Test2"));
        hist.add(dto);

        bean.createStatusHistory(IiConverter.convertToIi(spNew.getId()), hist);

        hist = bean.getByStudyProtocolWithTransitionValidations(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(2, hist.size());

        assertEquals(StudyStatusCode.IN_REVIEW.getCode(), hist.get(0)
                .getStatusCode().getCode());
        assertEquals(date, hist.get(0).getStatusDate().getValue());
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode(),
                hist.get(1).getStatusCode().getCode());
        assertEquals(date, hist.get(1).getStatusDate().getValue());
        assertEquals(
                StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode(),
                bean.getCurrentByStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(spNew.getId()))
                        .getStatusCode().getCode());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void updateStatusHistory() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -5).getTime()));
        sos.setAdditionalComments("Additional comment 4");
        sos.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        sos.setDateLastUpdated(new Date());
        s.saveOrUpdate(sos);

        sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -2).getTime()));
        sos.setAdditionalComments("Additional comment 3");
        sos.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        sos.setDateLastUpdated(new Date());
        s.saveOrUpdate(sos);
        s.flush();

        List<StudyOverallStatusDTO> hist = bean.getByStudyProtocol(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));

        StudyOverallStatusDTO inReview = hist.get(0);
        StudyOverallStatusDTO active = hist.get(1);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.APPROVED));
        dto.setStatusDate(TsConverter.convertToTs(DateUtils.addDays(date, -3)));
        dto.setDeleted(BlConverter.convertToBl(true));
        hist.add(1, dto);

        active.setDeleted(BlConverter.convertToBl(true));
        active.setAdditionalComments(StConverter.convertToSt("Deleted"));

        inReview.setStatusDate(TsConverter.convertToTs(DateUtils.addDays(date,
                -30)));
        inReview.setAdditionalComments(StConverter.convertToSt("Updated"));

        StudyOverallStatusDTO closed = new StudyOverallStatusDTO();
        closed.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.CLOSED_TO_ACCRUAL));
        closed.setStatusDate(TsConverter.convertToTs(date));
        hist.add(closed);

        bean.updateStatusHistory(
                IiConverter.convertToStudyProtocolIi(spNew.getId()), hist);

        hist = bean.getByStudyProtocolWithTransitionValidations(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(2, hist.size());

        assertEquals(StudyStatusCode.IN_REVIEW.getCode(), hist.get(0)
                .getStatusCode().getCode());
        assertEquals(DateUtils.addDays(date, -30), hist.get(0).getStatusDate()
                .getValue());
        assertEquals("Updated", hist.get(0).getAdditionalComments().getValue());

        assertEquals(StudyStatusCode.CLOSED_TO_ACCRUAL.getCode(), hist.get(1)
                .getStatusCode().getCode());
        assertEquals(date, hist.get(1).getStatusDate().getValue());

        hist = bean.getDeletedByStudyProtocol(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        assertEquals(1, hist.size());
        assertEquals(active.getIdentifier().getExtension(), hist.get(0)
                .getIdentifier().getExtension());
        assertTrue(BlConverter.convertToBool(hist.get(0).getDeleted()));

    }
 // commented as part of PO-9862
    /*@SuppressWarnings("deprecation")
    @Test
    public void closeOpenSitesIfNeeded() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        s.saveOrUpdate(sos);

        StudySite site = TestSchema.createParticipatingSite(spNew);
        s.flush();
        assertEquals(1, site.getStudySiteAccrualStatuses().size());

        StudyOverallStatusDTO closed = new StudyOverallStatusDTO();
        closed.setStatusCode(CdConverter.convertToCd(StudyStatusCode.COMPLETE));
        closed.setStatusDate(TsConverter.convertToTs(DateUtils.addDays(date, 2)));
        closed.setStudyProtocolIdentifier(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));

        StudySourceInterceptor.STUDY_SOURCE_CONTEXT
                .set(StudySourceCode.GRID_SERVICE);
        bean.create(closed);
        s.flush();

        s.refresh(site);
        assertEquals(2, site.getStudySiteAccrualStatuses().size());
        assertEquals(RecruitmentStatusCode.COMPLETED, site
                .getStudySiteAccrualStatuses().get(1).getStatusCode());
     

    }*/
    
    
    /*@SuppressWarnings("deprecation")
    @Test
    public void checkIfSitesNotClosedForLaterDate() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -3).getTime()));
        s.saveOrUpdate(sos);

        StudySite site = TestSchema.createParticipatingSite(spNew);
       
       
        s.flush();
        assertEquals(1, site.getStudySiteAccrualStatuses().size());

        StudyOverallStatusDTO closed = new StudyOverallStatusDTO();
        closed.setStatusCode(CdConverter.convertToCd(StudyStatusCode.COMPLETE));
        //stuy status code is still earlier date than site status date
        closed.setStatusDate(TsConverter.convertToTs(DateUtils.addDays(date, -2)));
        closed.setStudyProtocolIdentifier(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));

        StudySourceInterceptor.STUDY_SOURCE_CONTEXT
                .set(StudySourceCode.GRID_SERVICE);
        bean.create(closed);
        s.flush();

        s.refresh(site);
        
        //check if this site should not be closed
        assertEquals(1, site.getStudySiteAccrualStatuses().size());
        assertEquals(RecruitmentStatusCode.ACTIVE, site
                .getStudySiteAccrualStatuses().get(0).getStatusCode());
     

    }*/
    
    /*@SuppressWarnings("deprecation")
    @Test
    public void checkIfSitesNotClosedIfClosureInHistory() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -3).getTime()));
        s.saveOrUpdate(sos);

        //add closure status in site history
        StudySite site = TestSchema.createParticipatingSiteWithClosureInHistory(spNew);
       
       
        s.flush();
        assertEquals(2, site.getStudySiteAccrualStatuses().size());

        StudyOverallStatusDTO closed = new StudyOverallStatusDTO();
        closed.setStatusCode(CdConverter.convertToCd(StudyStatusCode.COMPLETE));
        
        closed.setStatusDate(TsConverter.convertToTs(DateUtils.addDays(date, +2)));
        closed.setStudyProtocolIdentifier(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));

        StudySourceInterceptor.STUDY_SOURCE_CONTEXT
                .set(StudySourceCode.GRID_SERVICE);
        bean.create(closed);
        s.flush();

        s.refresh(site);
        
        //check if this site should not be closed
        assertEquals(2, site.getStudySiteAccrualStatuses().size());
        assertEquals(RecruitmentStatusCode.ACTIVE, site
                .getStudySiteAccrualStatuses().get(0).getStatusCode());
     

    }
*/
    /*@SuppressWarnings("deprecation")
    @Test
    public void closeOpenSitesIfNeededProprietaryProtocol() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        s.saveOrUpdate(sos);

        StudySite site = TestSchema.createParticipatingSite(spNew);
        s.flush();
        assertEquals(1, site.getStudySiteAccrualStatuses().size());

        StudyOverallStatusDTO closed = new StudyOverallStatusDTO();
        closed.setStatusCode(CdConverter.convertToCd(StudyStatusCode.COMPLETE));
        closed.setStatusDate(TsConverter.convertToTs(date));
        closed.setStudyProtocolIdentifier(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));

        StudySourceInterceptor.STUDY_SOURCE_CONTEXT
                .set(StudySourceCode.GRID_SERVICE);

        spNew.setProprietaryTrialIndicator(true);
        s.saveOrUpdate(spNew);
        s.flush();

        bean.create(closed);
        s.flush();

        s.refresh(site);
        assertEquals(1, site.getStudySiteAccrualStatuses().size());
        assertEquals(RecruitmentStatusCode.ACTIVE, site
                .getStudySiteAccrualStatuses().get(0).getStatusCode());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void closeOpenSitesIfNeededNewStatusIsNotClosed() throws PAException {
        // new protocol with no statuses initially.
        final Session s = PaHibernateUtil.getCurrentSession();

        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStudyProtocol(spNew);
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(new Timestamp(DateUtils.addDays(date, -1).getTime()));
        s.saveOrUpdate(sos);

        StudySite site = TestSchema.createParticipatingSite(spNew);
        s.flush();
        assertEquals(1, site.getStudySiteAccrualStatuses().size());

        StudyOverallStatusDTO closed = new StudyOverallStatusDTO();
        closed.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        closed.setStatusDate(TsConverter.convertToTs(date));
        closed.setStudyProtocolIdentifier(IiConverter
                .convertToStudyProtocolIi(spNew.getId()));
        closed.setReasonText(StConverter.convertToSt("test"));

        StudySourceInterceptor.STUDY_SOURCE_CONTEXT
                .set(StudySourceCode.GRID_SERVICE);

        bean.create(closed);
        s.flush();

        s.refresh(site);
        assertEquals(1, site.getStudySiteAccrualStatuses().size());
        assertEquals(RecruitmentStatusCode.ACTIVE, site
                .getStudySiteAccrualStatuses().get(0).getStatusCode());

    }*/
 // commented as part of PO-9862
    @SuppressWarnings("deprecation")
    @Test
    public void createStatusHistoryValidations() throws PAException {
        // new protocol with no statuses initially.
        final Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        List<StudyOverallStatusDTO> hist = new ArrayList<>();

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter
                .convertToCd(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        dto.setStatusDate(TsConverter.convertToTs(date));
        hist.add(dto);
        try {
            bean.createStatusHistory(IiConverter.convertToIi(spNew.getId()),
                    hist);
            fail();
        } catch (PAException e) {
            assertEquals(
                    "Validation Exception A reason must be entered when the study status is set to Temporarily Closed to Accrual and Intervention.",
                    e.getMessage());
        }
        hist.clear();

        dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        dto.setStatusDate(TsConverter.convertToTs(date));
        hist.add(dto);

        dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.APPROVED));
        dto.setStatusDate(TsConverter.convertToTs(DateUtils.addDays(date, -1)));
        hist.add(dto);

        try {
            bean.createStatusHistory(IiConverter.convertToIi(spNew.getId()),
                    hist);
            fail();
        } catch (PAException e) {
            assertEquals(
                    "New current status date should be bigger/same as old date.",
                    e.getMessage());
        }
        hist.clear();

    }

    @SuppressWarnings("deprecation")
    @Test
    public void insert() throws PAException {
        // new protocol with no statuses initially.
        final Date today = DateUtils
                .truncate(new Date(), Calendar.DAY_OF_MONTH);
        final Date tomorrow = DateUtils.addDays(today, 1);

        InterventionalStudyProtocol spNew = createInterventionalProtocol();

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(tomorrow));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        try {
            bean.insert(dto);
            fail();
        } catch (PAException e) {
            assertEquals("Study status date cannot be in future.",
                    e.getMessage());
        }

        dto.setStatusDate(TsConverter.convertToTs(today));
        bean.insert(dto);

        assertEquals(
                StudyStatusCode.ACTIVE.getCode(),
                bean.getCurrentByStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(spNew.getId()))
                        .getStatusCode().getCode());
        assertEquals(
                today,
                bean.getCurrentByStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(spNew.getId()))
                        .getStatusDate().getValue());

    }

}
