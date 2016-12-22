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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO.ErrorMessageTypeEnum;
import gov.nih.nci.pa.dto.LastCreatedDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceBean;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.FamilyServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerBeanLocal;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceBean;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceBean;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author hreinhart
 *
 */
public class StudyMilestoneServiceTest extends AbstractHibernateTestCase {

    private static final MilestoneCode[] UP_TO_READY_FOR_TSR = new MilestoneCode[]{MilestoneCode.SUBMISSION_RECEIVED,
        MilestoneCode.SUBMISSION_ACCEPTED, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE,
        MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, MilestoneCode.ADMINISTRATIVE_READY_FOR_QC,
        MilestoneCode.ADMINISTRATIVE_QC_START, MilestoneCode.ADMINISTRATIVE_QC_COMPLETE,
        MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE,
        MilestoneCode.SCIENTIFIC_READY_FOR_QC, MilestoneCode.SCIENTIFIC_QC_START,
        MilestoneCode.SCIENTIFIC_QC_COMPLETE};

    class TestMilestoneService extends StudyMilestoneBeanLocal {
        @Override
        public StudyMilestoneDTO create(StudyMilestoneDTO dto) throws PAException {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                throw new PAException("Error in StudyMilestoneServiceTest.StudyMilestoneDTO.create()");
            }
            return super.create(dto);
        }
    }

    private final StudyMilestoneBeanLocal bean = new StudyMilestoneServiceTest.TestMilestoneService();
    private final AbstractionCompletionServiceBean abstractionCompletionSerivce = new AbstractionCompletionServiceBean();
    private final DocumentWorkflowStatusBeanLocal dws = new DocumentWorkflowStatusBeanLocal();
    private final MailManagerBeanLocal mailSrc = new MailManagerBeanLocal();
    private final StudyInboxServiceLocal sis = new StudyInboxServiceBean();
    private final StudyOnholdServiceBean ohs = new StudyOnholdServiceBean();
    private final StudyProtocolServiceLocal sps = new StudyProtocolServiceBean();
    private final FamilyServiceLocal familySvc = mock(FamilyServiceLocal.class);
    
    private final RegistryUserServiceLocal registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
    private final TSRReportGeneratorServiceRemote tsrReportGeneratorServiceRemote = mock(TSRReportGeneratorServiceRemote.class);
    private final ProtocolQueryServiceLocal protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
    private final DocumentServiceLocal documentServiceLocal = mock(DocumentServiceLocal.class);
    private final LookUpTableServiceRemote lookUpTableServiceRemote = mock(LookUpTableServiceRemote.class);

    private Ii spIi;
    private Ii spAmendIi;
    private Ii spIndustrialIi;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());

        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.deadline")))
                .thenReturn("21");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.reminder.reasons")))
                .thenReturn("SUBMISSION_INCOM,SUBMISSION_INCOM_MISSING_DOCS");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("rejection.body")))
                .thenReturn("test");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("rejection.subject")))
                .thenReturn("test");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.amend.reject.body")))
                .thenReturn("test");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.amend.reject.subject")))
                .thenReturn("test");
        
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("studyonhold.reason_category.mapping")))
                .thenReturn("SUBMISSION_INCOM=Submitter\nSUBMISSION_INCOM_MISSING_DOCS=Submitter\nINVALID_GRANT=Submitter\nPENDING_CTRP_REVIEW=CTRP\nPENDING_DISEASE_CUR=CTRP\nPENDING_PERSON_CUR=CTRP\nPENDING_ORG_CUR=CTRP\nPENDING_INTERVENTION_CUR=CTRP\nOTHER=CTRP");
        
        
        ohs.setLookUpTableServiceRemote(lookUpTableServiceRemote);
        bean.setAbstractionCompletionService(abstractionCompletionSerivce);
        bean.setDocumentWorkflowStatusService(dws);
        mailSrc.setDocWrkflStatusSrv(dws);
        mailSrc.setLookUpTableService(lookUpTableServiceRemote);
        mailSrc.setProtocolQueryService(protocolQueryServiceLocal);
        mailSrc.setRegistryUserService(registryUserServiceLocal);
        bean.setMailManagerService(mailSrc);
        bean.setStudyInboxService(sis);
        bean.setStudyOnholdService(ohs);
        bean.setStudyProtocolService(sps);
        bean.setTsrReportGeneratorService(tsrReportGeneratorServiceRemote);
        bean.setProtocolQueryService(protocolQueryServiceLocal);
        bean.setDocumentService(documentServiceLocal);

        mailSrc.setProtocolQueryService(new ProtocolQueryServiceBean());
        bean.setValidateAbstractions(false);

        TestSchema.primeData();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        spAmendIi = TestSchema.createAmendStudyProtocol();
        spIndustrialIi = TestSchema.createAmendSpIndustrial();
        ohs.setDocumentWorkflowStatusService(dws);
        TrialRegistrationServiceLocal trialRegistrationService = new MockTrialRegistrationService();
        bean.setTrialRegistrationService(trialRegistrationService);
        
        
        OrganizationCorrelationServiceBean.resetCache();
        OrganizationCorrelationServiceBean ocsr = mock(OrganizationCorrelationServiceBean.class);
        when(ocsr.getPoResearchOrganizationByEntityIdentifier(any(Ii.class))).thenAnswer(new Answer<Ii>() {
            @Override
            public Ii answer(InvocationOnMock invocation) throws Throwable {
                Ii ii = (Ii) invocation.getArguments()[0];
                return IiConverter.convertToPoResearchOrganizationIi(ii.getExtension());
            }
        });
        when(ocsr.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE)).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {               
                return "abc";
            }
        });
        
        ServiceLocator paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        when(paSvcLoc.getOrganizationCorrelationService()).thenReturn(ocsr);
        when(paSvcLoc.getStudySiteService()).thenReturn(new StudySiteBeanLocal());
    }

    private void compareDataAttributes(StudyMilestoneDTO dto1, StudyMilestoneDTO dto2) throws Exception {
        StudyMilestone bo1 = bean.convertFromDtoToDomain(dto1);
        StudyMilestone bo2 = bean.convertFromDtoToDomain(dto2);
        assertEquals(bo1.getCommentText(), bo2.getCommentText());
        assertEquals(bo1.getMilestoneCode().getCode(), bo2.getMilestoneCode().getCode());
        assertEquals(bo1.getMilestoneDate(), bo2.getMilestoneDate());
        assertEquals(bo1.getStudyProtocol().getId(), bo2.getStudyProtocol().getId());
    }

    @Test
    public void getTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        assertFalse(ISOUtil.isIiNull(ii));
        StudyMilestoneDTO resultDto = bean.get(ii);
        compareDataAttributes(dtoList.get(0), resultDto);
    }

    @Test
    public void updateTest() throws Exception {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("The update() method in the StudyMilestoneService has been disabled.");

        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);

        StudyMilestoneDTO dto = dtoList.get(0);
        dto.setCommentText(StConverter.convertToSt("new comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_ACCEPTED));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        bean.update(dto);
    }

    @Test
    public void createTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        dtoList = bean.getByStudyProtocol(spIi);
        assertEquals(oldSize + 4, dtoList.size());
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setCommentText(null);
        dto.setStudyProtocolIdentifier(spAmendIi);
        bean.create(dto);
        PaHibernateUtil.getCurrentSession().flush();
    }
    
    
    @Test
    public void createTestScientificQCComplete() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        
        bean.setValidateAbstractions(true);
        List<AbstractionCompletionDTO> errorList = new ArrayList<AbstractionCompletionDTO>();
        AbstractionCompletionDTO dtoabs = new AbstractionCompletionDTO();
        dtoabs.setErrorMessageType(ErrorMessageTypeEnum.ADMIN);
        dtoabs.setErrorType("Error");
        errorList.add(dtoabs);
        AbstractionCompletionServiceBean abstractionCompletionSerivce = mock(AbstractionCompletionServiceBean.class);
        bean.setAbstractionCompletionService(abstractionCompletionSerivce);
        when(abstractionCompletionSerivce.validateAbstractionCompletion(spIi)).thenReturn(errorList);
        StudyMilestoneDTO dto =  bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        assertEquals("Scientific QC Completed Date", dto.getMilestoneCode().getCode());
        
        assertEquals(StConverter.convertToString(dto.getErrorMessage()), "The milestone \"Ready for "
           + "Trial Summary Report Date\" can only be recorded if the abstraction is valid. "
           + " There is a problem with the current abstraction.  Select Abstraction "
           + "Validation under Completion menu to view details.");
        
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        assertTrue(dtoList.size() > 0);
        assertEquals(CdConverter.convertCdToEnum(MilestoneCode.class,
               dtoList.get(dtoList.size() - 1).getMilestoneCode()), MilestoneCode.SCIENTIFIC_QC_COMPLETE);
        
        PaHibernateUtil.getCurrentSession().flush();
    }
    
    
    @Test
    public void createTestScientificReady() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        StudyMilestoneDTO dto =  bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        assertEquals("Scientific QC Completed Date", dto.getMilestoneCode().getCode());
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        assertTrue(dtoList.size() > 0);
        assertEquals(CdConverter.convertCdToEnum(MilestoneCode.class,
               dtoList.get(dtoList.size() - 1).getMilestoneCode()), MilestoneCode.READY_FOR_TSR);
        PaHibernateUtil.getCurrentSession().flush();
    }
    
    
    @Test
    public void createTestIndustrialTrial() throws Exception {
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SUBMISSION_REACTIVATED));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTOIndustrial(MilestoneCode.ADMINISTRATIVE_QC_START));
        StudyMilestoneDTO dto = bean.create(getMilestoneDTOIndustrial(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        assertEquals("Administrative QC Completed Date", dto.getMilestoneCode().getCode());
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIndustrialIi);
        dtoList.size();
        assertTrue(dtoList.size() > 0);
        assertEquals(CdConverter.convertCdToEnum(MilestoneCode.class,
               dtoList.get(dtoList.size() - 1).getMilestoneCode()), MilestoneCode.TRIAL_SUMMARY_REPORT);
        assertEquals(CdConverter.convertCdToEnum(MilestoneCode.class,
               dtoList.get(dtoList.size() - 2).getMilestoneCode()), MilestoneCode.READY_FOR_TSR);
        PaHibernateUtil.getCurrentSession().flush();
    }

    private StudyMilestoneDTO getMilestoneDTO(MilestoneCode mileCode) {
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(mileCode));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        return dto;
    }

    private StudyMilestoneDTO getMilestoneDTOIndustrial(MilestoneCode mileCode) {
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(mileCode));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIndustrialIi);
        return dto;
    }
    @Test
    public void deleteTest() throws Exception {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("The delete() method in the StudyMilestoneService has been disabled.");
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        bean.delete(dtoList.get(0).getIdentifier());
    }

    @Test
    public void iiRootTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        StudyMilestoneDTO dto = dtoList.get(0);
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @Test
    public void checkRequiredDataRulesMissingStudyProtocol() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setStudyProtocolIdentifier(null);
        checkMilestoneFailure(dto, "Check the Ii value; null found.  ");
    }

    @Test
    public void checkRequiredDataRulesMissingCode() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setMilestoneCode(null);
        checkMilestoneFailure(dto, "Milestone code is required.");
    }

    @Test
    public void checkDateRulesMissingDate() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setMilestoneDate(null);
        checkMilestoneFailure(dto, "Milestone date is required.");
    }

    @Test
    public void checkDateRulesFutureDate() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() + 86400000L)));
        checkMilestoneFailure(dto, "Milestone dates may not be in the future.");
    }

    @Test
    public void checkDateRulesPastLastMilestone() throws Exception {
        StudyMilestoneDTO dto1 = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        bean.create(dto1);
        StudyMilestoneDTO dto2 = getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED);
        dto2.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() - 86400000L)));
        String msg = "A milestone cannot predate existing milestones. The prior milestone date is {0}.";
        Timestamp lastDate = TsConverter.convertToTimestamp(dto1.getMilestoneDate());
        String lastMileStoneDate = PAUtil.normalizeDateStringWithTime(lastDate.toString());
        String message = MessageFormat.format(msg, lastMileStoneDate);
        checkMilestoneFailure(dto2, message);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
    }

    @Test
    public void checkLateRejectionRulesMissingComment() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE);
        dto.setCommentText(null);
        checkMilestoneFailure(dto, "Milestone Comment is required.");
    }

    @Test
    public void checkReadyForTSRMilestone() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.READY_FOR_TSR);
        checkMilestoneFailure(dto, "\"Ready for Trial Summary Report Date\" can not be created at this stage.");
    }



    @Test
    public void checkOnHoldRules() throws Exception {
        MailManagerServiceLocal mailManagerServiceLocal = mock(MailManagerServiceLocal.class);
        ohs.setMailManagerSerivceLocal(mailManagerServiceLocal);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        // set on-hold and dwf status
        StudyOnholdDTO ohDto = new StudyOnholdDTO();
        ohDto.setOnholdDate(IvlConverter.convertTs().convertToIvl(PAUtil.today(), null));
        ohDto.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.PENDING_ORG_CUR));
        ohDto.setStudyProtocolIdentifier(spIi);
        ohs.create(ohDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        String msg = "The milestone \"Administrative QC Completed Date\" cannot be recorded if there is an active"
                + " on-hold record.";
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, msg);

        // however, SUBMISSION_TERMINATED & SUBMISSION_REACTIVATED cannot be recorded while On-Hold.
        checkMilestoneFailure((MilestoneCode.SUBMISSION_TERMINATED));
        checkMilestoneFailure((MilestoneCode.SUBMISSION_REACTIVATED));

        // take off-hold
        List<StudyOnholdDTO> ohList = ohs.getByStudyProtocol(spIi);
        for (StudyOnholdDTO oh : ohList) {
            if (IvlConverter.convertTs().convertHigh(oh.getOnholdDate()) == null) {
                oh.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, PAUtil.today()));
                ohs.update(oh);
            }
        }
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
    }

    @Test
    public void checkInboxRules() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        // create inbox record
        StudyInboxDTO inboxDto = new StudyInboxDTO();
        inboxDto.setStudyProtocolIdentifier(spIi);
        inboxDto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(getDate(0), null));
        inboxDto = sis.create(inboxDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        String msg = "The milestone \"Administrative QC Completed Date\" cannot be recorded if there is an active Inbox record.";
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, msg);
        inboxDto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(getDate(0), getDate(0)));
        sis.update(inboxDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
    }



    @Test
    public void checkUniquenessRules() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        bean.create(dto);
        String pat = "The milestone \"Submission Received Date\" must be unique.  It was previously recorded on {0}.";
        String date = PAUtil.normalizeDateString(TsConverter.convertToTimestamp(dto.getMilestoneDate()).toString());
        String msg = MessageFormat.format(pat, date);
        checkMilestoneFailure(MilestoneCode.SUBMISSION_RECEIVED, msg);
    }

    @Test
    public void checkScientificProcessingStartDate() throws PAException {
        String canNotReachMsg = "\"Scientific Processing Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific Processing Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, canNotReachMsg);
    }

    @Test
    public void checkScientificProcessingCompletedDate() throws PAException {
        String canNotReachMsg = "\"Scientific Processing Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific Processing Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, canNotReachMsg);
    }

    @Test
    public void checkScientificReadyForQCDate() throws PAException {
        String canNotReachMsg = "\"Ready for Scientific QC Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Ready for Scientific QC Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
    }

    @Test
    public void checkScientificQCStartDate() throws PAException {
        String canNotReachMsg = "\"Scientific QC Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific QC Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
    }

    @Test
    public void checkScientificQCCompletedDate() throws PAException {
        String canNotReachMsg = "\"Scientific QC Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific QC Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeProcessingStartDate() throws PAException {
        String canNotReachMsg = "\"Administrative Processing Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative Processing Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeProcessingCompletedDate() throws PAException {
        String canNotReachMsg = "\"Administrative Processing Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative Processing Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeReadyForQCDate() throws PAException {
        String canNotReachMsg = "\"Ready for Administrative QC Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Ready for Administrative QC Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeQCStartDate() throws PAException {
        String canNotReachMsg = "\"Administrative QC Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative QC Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeQCCompletedDate() throws PAException {
        String canNotReachMsg = "\"Administrative QC Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative QC Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
    }


    @Test
    public void checkDocumentWorkflowStatusRules() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        for (DocumentWorkflowStatusDTO dto : dws.getByStudyProtocol(spIi)) {
            dws.delete(dto.getIdentifier());
        }
        String msg = "The processing status must be 'Accepted', 'Abstracted', 'Verification Pending', "
                + "'Abstraction Verified Response', or 'Abstraction Verified No Response' when entering the milestone "
                + "'Administrative Processing Start Date'.  The current processing status is null.";
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, msg);
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        dws.create(dwfDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
    }

    @Test
    public void checkAbstractionsRules() throws Exception {
        createMilestones(UP_TO_READY_FOR_TSR);
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_REPORT);
        bean.setValidateAbstractions(true);
        bean.setAbstractionCompletionService(null);
        String msg = "Error injecting reference to AbstractionCompletionService.";
        checkMilestoneFailure(dto, msg);
    }

    @Test
    public void checkTrialSummaryFeedbackPrerequisiteOK() throws PAException {
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
    }

    //@Test
    public void checkTrialSummaryFeedbackPrerequisiteMissing() throws PAException {
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        String msg = "\"Trial Summary Report Sent Date\" is a prerequisite to "
                + "\"Submitter Trial Summary Report Feedback Date\".";
        checkMilestoneFailure(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, msg);
    }

    @Test
    public void checkLateRejectionPrerequisiteMissing() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        String msg = "\"Submission Acceptance Date\" is a prerequisite to \"Late Rejection Date\".";
        checkMilestoneFailure(MilestoneCode.LATE_REJECTION_DATE, msg);
    }

    /**
     * @return
     */
    private DocumentWorkflowStatusDTO getDocWrkStatusDTO() {
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        dwfDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        dwfDto.setStudyProtocolIdentifier(spIi);
        return dwfDto;
    }

    @Test
    public void checkTSRSentMail() throws PAException {
        Ii studyProtocolIi = TestSchema.nonPropTrialData();
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_REPORT);
        dto.setStudyProtocolIdentifier(studyProtocolIi);
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTED));
        dwfDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        dwfDto.setStudyProtocolIdentifier(studyProtocolIi);
        dws.create(dwfDto);
        mailSrc.setLookUpTableService(new LookUpTableServiceBean());
        TSRReportGeneratorServiceRemote tsrBean = new TSRReportGeneratorServiceBean();
        mailSrc.setTsrReportGeneratorService(tsrBean);
        String msg = "Trial Summary Report Date' could not be recorded as sending the TSR report to the submitter  failed.";
        checkMilestoneFailure(dto, msg);
    }

    @Test
    public void attachTSRToTrialDocs() throws PAException, IOException {
        final StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_REPORT);

        StudyProtocolQueryDTO queryDTO = new StudyProtocolQueryDTO();
        queryDTO.setAmendmentNumber("10");
        queryDTO.setNciIdentifier("NCI_ID");
        when(protocolQueryServiceLocal.getTrialSummaryByStudyProtocolId(anyLong())).thenReturn(queryDTO);

        ByteArrayOutputStream tsr = new ByteArrayOutputStream();
        tsr.write("TSR RTF".getBytes());
        when(tsrReportGeneratorServiceRemote.generateRtfTsrReport(dto.getStudyProtocolIdentifier())).thenReturn(tsr);

        when(documentServiceLocal.create(any(DocumentDTO.class))).thenAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation)
                            throws Throwable {
                        DocumentDTO docDTO = (DocumentDTO) invocation
                                .getArguments()[0];
                        assertEquals(dto.getStudyProtocolIdentifier(),
                                docDTO.getStudyProtocolIdentifier());
                        assertEquals(
                                CdConverter.convertToCd(DocumentTypeCode.TSR),
                                docDTO.getTypeCode());
                        assertEquals(
                                "TSR RTF",
                                new String(docDTO.getText().getData()));
                        assertEquals(
                                "TSR_NCI_ID_"
                                        + DateFormatUtils
                                                .format(new Date(),
                                                        PAConstants.TSR_DATE_FORMAT)
                                        + "_A10.rtf", docDTO.getFileName()
                                        .getValue());
                        return null;
                    }
                });

        bean.attachTSRToTrialDocs(dto);
        verify(documentServiceLocal, times(1)).create(any(DocumentDTO.class));
    }

    private void checkMilestoneFailure(MilestoneCode milestone, String message) {
        try {
            bean.create(getMilestoneDTO(milestone));
            fail();
        } catch (PAException e) {
            assertEquals(message, e.getMessage());
        }
    }

    private void checkMilestoneFailure(MilestoneCode milestone) {
        try {
            bean.create(getMilestoneDTO(milestone));
            fail();
        } catch (PAException e) {
            // OK
        }
    }


    private void checkMilestoneFailure(StudyMilestoneDTO milestoneDTO, String message) {
        try {
            bean.create(milestoneDTO);
            fail();
        } catch (PAException e) {
            assertEquals(message, e.getMessage());
        }
    }

    private void addAbstractedWorkflowStatus() throws PAException {
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTED));
        dws.create(dwfDto);
    }

    @Test
    public void createDocumentWorkflowStatusForRejectedTest() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkDWS(1, DocumentWorkflowStatusCode.SUBMITTED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REJECTED));
        checkDWS(2, DocumentWorkflowStatusCode.REJECTED);
    }

    @Test
    public void createDocumentWorkflowStatusForTerminated() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkDWS(1, DocumentWorkflowStatusCode.SUBMITTED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkDWS(2, DocumentWorkflowStatusCode.ACCEPTED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        checkDWS(3, DocumentWorkflowStatusCode.SUBMISSION_TERMINATED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkDWS(4, DocumentWorkflowStatusCode.ACCEPTED);
    }

    @Test
    public void createTerminatedFailureTest() throws Exception {
        deleteWorkflowStatusesAndMilestones();

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMITTED);

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REJECTED));
        checkCurrentDWS(DocumentWorkflowStatusCode.REJECTED);
        checkMilestoneFailure(
                MilestoneCode.SUBMISSION_TERMINATED,
                "The processing status must be 'Submitted', 'Amendment Submitted', 'Accepted', 'Abstracted', 'Verification Pending', 'Abstraction Verified Response', 'Abstraction Verified No Response', or 'On-Hold' when entering the milestone 'Submission Terminated Date'.  The current processing status is 'Rejected'.");

    }

    @Test
    public void createTerminatedFollowedByActivationOnlyTest() throws Exception {
        deleteWorkflowStatusesAndMilestones();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMITTED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMISSION_TERMINATED);

        for (MilestoneCode code : MilestoneCode.values()) {
            if (code != MilestoneCode.SUBMISSION_REACTIVATED) {
                checkMilestoneFailure(code);
            }
        }

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMITTED);

    }

    @Test
    public void createReactivatedOnlyIfTerminated() throws Exception {
        deleteWorkflowStatusesAndMilestones();

        // First test against different milestones.
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMISSION_TERMINATED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));

        // First test against different DWS, without varying milestones
        deleteWorkflowStatusesAndMilestones();
        Session session = PaHibernateUtil.getCurrentSession();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMISSION_TERMINATED);

        for (DocumentWorkflowStatusCode code : DocumentWorkflowStatusCode
                .values()) {
            if (code != DocumentWorkflowStatusCode.SUBMISSION_TERMINATED) {
                DocumentWorkflowStatusDTO dwsDTO =  dws.getLatestOffholdStatus(spIi);
                dwsDTO.setStatusCode(CdConverter.convertToCd(code));
                dws.update(dwsDTO);
                checkMilestoneFailure(MilestoneCode.SUBMISSION_REACTIVATED);
            }
        }

    }


    /**
     * @throws PAException
     * @throws HibernateException
     */
    private void deleteWorkflowStatusesAndMilestones() throws PAException,
            HibernateException {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        for (DocumentWorkflowStatusDTO dto : dwsList) {
            dws.delete(dto.getIdentifier());
        }
        Session session = PaHibernateUtil.getCurrentSession();
        session.createQuery(
                "delete from " + StudyMilestone.class.getName()
                        + " sm where sm.studyProtocol.id="
                        + spIi.getExtension()).executeUpdate();
        session.flush();
    }



    @Test
    public void createTerminatedAnywhereExceptRejected() throws Exception {
        deleteWorkflowStatusesAndMilestones();

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMITTED);

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkCurrentDWS(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ACCEPTED);

        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ABSTRACTED);
        checkCurrentDWS(DocumentWorkflowStatusCode.ABSTRACTED);

        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        checkCurrentDWS(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);

        bean.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        checkTerminationAndReactivation(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        checkCurrentDWS(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);

    }

    /**
     * @throws PAException
     */
    private void checkTerminationAndReactivation(DocumentWorkflowStatusCode afterReactivationCode) throws PAException {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        checkCurrentDWS(DocumentWorkflowStatusCode.SUBMISSION_TERMINATED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        checkCurrentDWS(afterReactivationCode);
    }



    @Test
    public void createDocumentWorkflowStatusesUntilAbstracted() throws Exception {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(0, dwsList.size());
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkDWS(1, DocumentWorkflowStatusCode.SUBMITTED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkDWS(2, DocumentWorkflowStatusCode.ACCEPTED);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkDWS(2, DocumentWorkflowStatusCode.ACCEPTED);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkDWS(3, DocumentWorkflowStatusCode.ABSTRACTED);
    }

    @Test
    public void createDocumentWorkflowStatusesWithNOResponse() throws Exception {
        addAbstractedWorkflowStatus();
        checkDWS(1, DocumentWorkflowStatusCode.ABSTRACTED);
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        checkDWS(2, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        bean.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        checkDWS(2, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        bean.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        bean.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        checkDWS(3, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
    }

    @Test
    public void createDocumentWorkflowStatusesWithResponse() throws Exception {
        addAbstractedWorkflowStatus();
        checkDWS(1, DocumentWorkflowStatusCode.ABSTRACTED);
        bean.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        checkDWS(1, DocumentWorkflowStatusCode.ABSTRACTED);
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        checkDWS(2, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
    }

    private void checkDWS(int expectedSize, DocumentWorkflowStatusCode expectedStatus) throws PAException {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        assertEquals("Wrong size of Document workflow status list", expectedSize, dwsList.size());
        assertEquals("Wrong Document workflow status", expectedStatus, getCurrentDocumentWorkflowStatus());
    }

    private void checkCurrentDWS(DocumentWorkflowStatusCode expectedStatus) throws PAException {
        assertEquals("Wrong Document workflow status", expectedStatus, getCurrentDocumentWorkflowStatus());
    }


    private DocumentWorkflowStatusCode getCurrentDocumentWorkflowStatus() throws PAException {
        DocumentWorkflowStatusDTO dtoDwf = dws.getCurrentByStudyProtocol(spIi);
        return (dtoDwf != null) ? DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dtoDwf
            .getStatusCode())) : null;
    }

    @Test
    public void updateRecordVerificationDateTest() throws Exception {
        addAbstractedWorkflowStatus();
        // set initial date
        StudyProtocolDTO sp = sps.getStudyProtocol(spIi);
        sp.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        sps.updateStudyProtocol(sp);

        DocumentWorkflowStatusDTO dwfsDTO = getDocWrkStatusDTO();
        dwfsDTO.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        // milestone triggers change of date
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        sp = sps.getStudyProtocol(spIi);
        String recordVerificationDate = TsConverter.convertToString(sp.getRecordVerificationDate());
        assertTrue(ISOUtil.normalizeDateString(getDate(0).toString()).equals(recordVerificationDate));

    }

    @Test
    public void searchNotFound() throws TooManyResultsException, PAException {
        StudyMilestoneDTO studyMilestone = new StudyMilestoneDTO();
        studyMilestone.setStudyProtocolIdentifier(spIi);
        studyMilestone.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        LimitOffset limitOffset = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudyMilestoneDTO> milestones = bean.search(studyMilestone, limitOffset);
        assertEquals("There should only be one milestone present for TRIAL_SUMMARY_FEEDBACK", 0, milestones.size());
    }

    @Test
    public void searchFound() throws TooManyResultsException, PAException {
        StudyMilestoneDTO studyMilestone = new StudyMilestoneDTO();
        studyMilestone.setStudyProtocolIdentifier(spIi);
        studyMilestone.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_REPORT));
        LimitOffset limitOffset = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudyMilestoneDTO> milestones = bean.search(studyMilestone, limitOffset);
        assertEquals("There should only be one milestone present for TRIAL_SUMMARY_REPORT", 3, milestones.size());
        StudyMilestoneDTO milestone = milestones.get(0);
        assertEquals(milestone.getMilestoneCode(), studyMilestone.getMilestoneCode());
        assertEquals(milestone.getStudyProtocolIdentifier(), studyMilestone.getStudyProtocolIdentifier());
    }

    private Timestamp getDate(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * Creates the milestones specified in the given array
     */
    private void createMilestones(MilestoneCode[] milestones) throws PAException {
        for (MilestoneCode code : milestones) {
            bean.create(getMilestoneDTO(code));
        }
    }

    @Test
    public void testResetProcessingPriority() throws Exception {
        Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update study_protocol set processing_priority=1 where identifier="
                        + spIi.getExtension()).executeUpdate();
        PaHibernateUtil.getCurrentSession().flush();
        bean.setMailManagerService(mock(MailManagerBeanLocal.class));

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_REPORT));

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        assertEquals(
                2,
                ((StudyProtocol) session.get(StudyProtocol.class,
                        new Long(spIi.getExtension()))).getProcessingPriority().intValue());
    }

    @Test
    public void testProtocolQueryServiceBean_populateMilestoneHistory()
            throws Exception {
        Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "delete from study_milestone where study_protocol_identifier="
                        + spIi.getExtension()).executeUpdate();

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        List<StudyProtocolQueryDTO> trials = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO queryDTO = new StudyProtocolQueryDTO();
        queryDTO.setStudyProtocolId(new Long(spIi.getExtension()));
        trials.add(queryDTO);
        new ProtocolQueryServiceBean().populateMilestoneHistory(trials);

        assertEquals(3, queryDTO.getMilestoneHistory().size());
        assertEquals(MilestoneCode.SUBMISSION_RECEIVED, queryDTO
                .getMilestoneHistory().get(0).getMilestone());
        assertEquals(MilestoneCode.SUBMISSION_TERMINATED, queryDTO
                .getMilestoneHistory().get(1).getMilestone());
        assertEquals(MilestoneCode.SUBMISSION_REACTIVATED, queryDTO
                .getMilestoneHistory().get(2).getMilestone());
        
        try {
            bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REACTIVATED));
            fail();
        } catch (PAException ex) {
            assertTrue(ex.getMessage().contains("The processing status must be 'Submission Terminated' when entering the milestone 'Submission Reactivated Date'."));
        }

        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_TERMINATED));
        
        try {
            bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
            fail();
        } catch (PAException ex) {
            assertTrue(ex.getMessage().contains("'Submission Terminated Date' milestone can only be followed by 'Submission Reactivated Date' milestone."));
        }
    }

    
    @Test
    public void testStudyMilestoneServiceBean_documentSetter() {
        //This is here to test the setters and getters, a boring but necessary part for 100% code coverage.
        bean.setDocumentService(documentServiceLocal);
        assertTrue(documentServiceLocal == bean.getDocumentService());
        PAServiceUtils paServiceUtils = new PAServiceUtils(), tempUtil;
        assertTrue(null != bean.getPaServiceUtils());
        assertTrue(paServiceUtils != bean.getPaServiceUtils());
        tempUtil = bean.getPaServiceUtils();
        bean.setPaServiceUtils(paServiceUtils);
        assertTrue(paServiceUtils == bean.getPaServiceUtils());
        bean.setPaServiceUtils(tempUtil);
        TrialRegistrationServiceLocal trialRegistrationService = new TrialRegistrationBeanLocal();
        bean.setTrialRegistrationService(trialRegistrationService);
        assertTrue(trialRegistrationService == bean.getTrialRegistrationService());
        bean.setProtocolQueryService(protocolQueryServiceLocal);
        assertTrue(protocolQueryServiceLocal == bean.getProtocolQueryService());
        bean.setTsrReportGeneratorService(tsrReportGeneratorServiceRemote);
        assertTrue(bean.getTsrReportGeneratorService() instanceof TSRReportGeneratorServiceLocal);
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testStudyMilestoneServiceBean_testLateRejectoion() throws PAException {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE));
        
        //test amendments too
        spIi = TestSchema.createAmendStudyProtocol();
        when(protocolQueryServiceLocal.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(spIi)))
            .thenReturn(new StudyProtocolQueryDTO() {
                
                @Override
                public Long getStudyProtocolId() {
                    return 2L;
                }
                @Override
                public String getOfficialTitle() {
                    return "test";
                }
                public String getLocalStudyProtocolIdentifier() {
                    return "test";
                }
                public String getLeadOrganizationName() {
                    return "test";
                }
                public String getNciIdentifier() {
                    return "test";
                }
                public LastCreatedDTO getLastCreated() {
                    LastCreatedDTO date = new LastCreatedDTO();
                    date.setUserLastCreated("test");
                    date.setUserLastDisplayName("test");
                    date.setDateLastCreated(new Date());
                    return date;
                }
            });
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE));
        
        //cleanup.
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
    }
    
    @Test
    public void testStudyMilestoneServiceBean_testSearch() {
        try {
            bean.search(null, new LimitOffset(0, 0));
            fail();
        } catch (PAException ex) {
            assertTrue("Error message does not match expected value for null search paramater.", ex.getMessage().contains("StudyMilestoneDTO should not be null") );
        } catch (TooManyResultsException e) {
            fail();
        }
    }
    
    @Test
    public void testdeleteMilestoneByStatusAndSpid() throws PAException {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REJECTED));
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        assertEquals(5, dtoList.size());
        bean.deleteMilestoneByCodeAndStudy(MilestoneCode.SUBMISSION_REJECTED, spIi);
        dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        assertEquals(4, dtoList.size());
    }
    
    @Test
    public void testUpdateMilestoneCodeCommentWithDateAndUser() throws PAException {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        assertEquals(4, dtoList.size());
        bean.updateMilestoneCodeCommentWithDateAndUser(dtoList.get(3), "Test code", "FullName");
        getCommentByMilestoneIdAndSPID(IiConverter.convertToLong(spIi), IiConverter.convertToLong(dtoList.get(3).getIdentifier()));
        dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(StConverter.convertToString(dtoList.get(3).getCommentText()).contains("comment " + TsConverter
               .convertToTimestamp(dtoList.get(3).getMilestoneDate())+ " \n\nTest code ")); 
    }
    
    @Test
    public void testUpdateMilestoneCodeCommentWithDateAndUserNull() throws PAException {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        assertEquals(4, dtoList.size());
        dtoList.get(3).setCommentText(null);
        bean.updateMilestoneCodeCommentWithDateAndUser(dtoList.get(3), "Test code", "FullName");
        getCommentByMilestoneIdAndSPID(IiConverter.convertToLong(spIi), IiConverter.convertToLong(dtoList.get(3).getIdentifier()));
        dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(StConverter.convertToString(dtoList.get(3).getCommentText()).contains("Test code ")); 
    }
    
    /**
     * @param nciID
     * @param session
     * @return
     * @throws HibernateException
     */
    private String getCommentByMilestoneIdAndSPID(Long spId, Long milestoneid)
            throws HibernateException {
        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();
        return (String) session.createSQLQuery(
                "select comment_text from study_milestone where study_protocol_identifier=" +spId + "and identifier =" + milestoneid).uniqueResult();
    }

}
