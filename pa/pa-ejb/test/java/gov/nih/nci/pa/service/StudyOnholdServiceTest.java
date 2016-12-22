/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
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
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.convert.StudyOnholdConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.exceptions.base.MockitoException;

/**
 * @author hreinhart
 *
 */
public class StudyOnholdServiceTest extends AbstractHibernateTestCase {
    
    private static final String ONHOLD_NOTIF_START_DATE = "01/02/2000";
    private final DocumentWorkflowStatusServiceLocal documentWorkflowStatusService = mock(DocumentWorkflowStatusServiceLocal.class);
    private final ProtocolQueryServiceLocal protocolQueryServiceBean = mock(ProtocolQueryServiceLocal.class);
    private final LookUpTableServiceRemote lookUpTableServiceRemote = mock(LookUpTableServiceRemote.class);
    private final MailManagerServiceLocal mailManagerServiceLocal = mock(MailManagerServiceLocal.class);
    private final StudyMilestoneServicelocal studyMilestoneServicelocal = mock(StudyMilestoneServicelocal.class);
    
    private StudyProtocol onholdStudy;
    private StudyOnhold onholdRecord;
    private final StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
    
    private static final OnholdReasonCode[] reasonsToSkipReminders = new OnholdReasonCode[] {            
            OnholdReasonCode.INVALID_GRANT, OnholdReasonCode.OTHER,
            OnholdReasonCode.PENDING_CTRP_REVIEW,
            OnholdReasonCode.PENDING_DISEASE_CUR,
            OnholdReasonCode.PENDING_INTERVENTION_CUR,
            OnholdReasonCode.PENDING_ORG_CUR,
            OnholdReasonCode.PENDING_PERSON_CUR};
    
    private Timestamp today;
    
    /**
     * Exception rule.
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
 
    /**
     * Initialization of database.
     * @throws PAException 
     * @throws ParseException 
     */
    @Before
    public void setUp() throws PAException, ParseException {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        
        Session session = PaHibernateUtil.getCurrentSession();
        onholdRecord = (StudyOnhold) session.get(StudyOnhold.class, TestSchema.studyOnholdIds.get(0));
        onholdStudy = onholdRecord.getStudyProtocol();
        
        studyProtocolQueryDTO.setStudyProtocolId(onholdStudy.getId());
        studyProtocolQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ON_HOLD);        
        when(protocolQueryServiceBean.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class))).
            thenReturn(Arrays.asList(studyProtocolQueryDTO));
        
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.reminder.reasons")))
                .thenReturn("SUBMISSION_INCOM,SUBMISSION_INCOM_MISSING_DOCS");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.deadline")))
                .thenReturn("21");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.startdate")))
                .thenReturn(ONHOLD_NOTIF_START_DATE);        
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.reminder.frequency")))
                .thenReturn("3");        
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.reminder.logentry")))
                .thenReturn("${date}_${emails}");        
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.termination.logentry")))
                .thenReturn("${date}");
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("trial.onhold.termination.comment")))
                .thenReturn("trial.onhold.termination.comment");    
        
        when(
                lookUpTableServiceRemote
                        .getPropertyValue(eq("studyonhold.reason_category.mapping")))
                .thenReturn("SUBMISSION_INCOM=Submitter\nSUBMISSION_INCOM_MISSING_DOCS=Submitter\nINVALID_GRANT=Submitter\nPENDING_CTRP_REVIEW=CTRP\nPENDING_DISEASE_CUR=CTRP\nPENDING_PERSON_CUR=CTRP\nPENDING_ORG_CUR=CTRP\nPENDING_INTERVENTION_CUR=CTRP\nOTHER=CTRP");        
        
        today =  new Timestamp(DateUtils.parseDate("03/23/2012", new String[] {"MM/dd/yyyy"}).getTime());
        
    }
    
    /**
     * Creates a new StudyOnholdBeanLocal with its dependencies.
     * @return The new StudyOnholdBeanLocal with its dependencies.
     */
    private StudyOnholdBeanLocal createStudyOnholdBeanLocal() {
        StudyOnholdBeanLocal service = new StudyOnholdBeanLocal();
        setDependencies(service);
        return service;
    }

    /**
     * Creates a new StudyOnholdBeanLocal mock with its dependencies.
     * @return The new StudyOnholdBeanLocal with its dependencies.
     */
    private StudyOnholdBeanLocal createStudyOnholdBeanLocalMock() {
        StudyOnholdBeanLocal service = mock(StudyOnholdBeanLocal.class);
        doCallRealMethod().when(service).setDocumentWorkflowStatusService(documentWorkflowStatusService);
        doCallRealMethod().when(service).setLookUpTableServiceRemote(lookUpTableServiceRemote);
        doCallRealMethod().when(service).setProtocolQueryServiceLocal(protocolQueryServiceBean);
        doCallRealMethod().when(service).setMailManagerSerivceLocal(mailManagerServiceLocal);
        doCallRealMethod().when(service).setStudyMilestoneService(studyMilestoneServicelocal);
        try {
			doCallRealMethod().when(service).processOnHoldTrials();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        when(service.getTodaysDate()).thenReturn(today);
        setDependencies(service);
        return service;
    }

    /**
     * Sets the dependencies of the given service.
     */
    private void setDependencies(StudyOnholdBeanLocal service) {
        service.setDocumentWorkflowStatusService(documentWorkflowStatusService);
        service.setLookUpTableServiceRemote(lookUpTableServiceRemote);
        service.setProtocolQueryServiceLocal(protocolQueryServiceBean);
        service.setMailManagerSerivceLocal(mailManagerServiceLocal);
        service.setStudyMilestoneService(studyMilestoneServicelocal);
    }
    
    /**
     * Test the isOnhold method when the study is on hold.
     * @throws PAException in case of error
     */
    @Test
    public void testisOnholdTrue() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).isOnhold(spIi);
        List<StudyOnholdDTO> onholdList = createOnholdList(true);
        when(sut.getByStudyProtocol(spIi)).thenReturn(onholdList);
        Bl result = sut.isOnhold(spIi);
        assertTrue("Wrong result", result.getValue());
    }
    
    /**
     * Test the isOnhold method when the study is not on hold.
     * @throws PAException in case of error
     */
    @Test
    public void testisOnholdFalse() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).isOnhold(spIi);
        List<StudyOnholdDTO> onholdList = createOnholdList(false);
        when(sut.getByStudyProtocol(spIi)).thenReturn(onholdList);
        Bl result = sut.isOnhold(spIi);
        assertFalse("Wrong result", result.getValue());
    }
    
    private List<StudyOnholdDTO> createOnholdList(boolean onhold) {
        List<StudyOnholdDTO> onholdList = new ArrayList<StudyOnholdDTO>();
        onholdList.add(createStudyOnholdDTO(onhold));
        return onholdList;
    }
    
    private StudyOnholdDTO createStudyOnholdDTO(boolean onhold) {
        StudyOnholdDTO dto = new StudyOnholdDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(1)));
        Timestamp low = new Timestamp(new DateTime().getMillis());
        Timestamp high = (onhold) ? null : low;
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(low, high));
        dto.setOnholdReasonCode(CdConverter.convertStringToCd(OnholdReasonCode.SUBMISSION_INCOM.getCode()));
        return dto;
    }
    
  
    
    /**
     * Test the create method for the on-hold to off-hold transition.
     * @throws PAException in case of error
     */
    @Test
    public void testCreateOnToOff() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        StudyOnholdDTO onholdDto = createStudyOnholdDTO(false);
        doCallRealMethod().when(sut).create(onholdDto);
        StudyOnhold onholdBo = new StudyOnholdConverter().convertFromDtoToDomain(onholdDto);
        when(sut.convertFromDtoToDomain(onholdDto)).thenReturn(onholdBo);
        when(sut.getDocumentWorkflowStatus(onholdDto.getStudyProtocolIdentifier())).thenReturn(DocumentWorkflowStatusCode.SUBMITTED);
        when(sut.isOnhold(onholdDto.getStudyProtocolIdentifier())).thenReturn(BlConverter.convertToBl(true));
        sut.create(onholdDto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).getDocumentWorkflowStatus(onholdDto.getStudyProtocolIdentifier());
        inOrder.verify(sut).isOnhold(onholdDto.getStudyProtocolIdentifier());
        inOrder.verify(sut).statusRulesForCreation(false, true, false);
        assertNull("Wrong previous status", onholdDto.getPreviousStatusCode());
        inOrder.verify(sut, never()).createDocumentWorkflowStatus(any(Ii.class), any(DocumentWorkflowStatusCode.class));
    }
    
    /**
     * Test the create method for the off-hold to on-hold transition.
     * @throws PAException in case of error
     */
    @Test
    public void testCreateOffToOn() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        StudyOnholdDTO onholdDto = createStudyOnholdDTO(true);
        doCallRealMethod().when(sut).create(onholdDto);
        StudyOnhold onholdBo = new StudyOnholdConverter().convertFromDtoToDomain(onholdDto);
        when(sut.convertFromDtoToDomain(onholdDto)).thenReturn(onholdBo);
        when(sut.getDocumentWorkflowStatus(onholdDto.getStudyProtocolIdentifier())).thenReturn(DocumentWorkflowStatusCode.SUBMITTED);
        when(sut.isOnhold(onholdDto.getStudyProtocolIdentifier())).thenReturn(BlConverter.convertToBl(false));
        sut.create(onholdDto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).getDocumentWorkflowStatus(onholdDto.getStudyProtocolIdentifier());
        inOrder.verify(sut).isOnhold(onholdDto.getStudyProtocolIdentifier());
        inOrder.verify(sut).statusRulesForCreation(false, false, true);
        assertEquals("Wrong previous status", DocumentWorkflowStatusCode.SUBMITTED, CdConverter.convertCdToEnum(DocumentWorkflowStatusCode.class, onholdDto.getPreviousStatusCode()));
        inOrder.verify(sut).createDocumentWorkflowStatus(onholdDto.getStudyProtocolIdentifier(), DocumentWorkflowStatusCode.ON_HOLD);
    }
    
    /**
     * Test the create method for the off-hold to off-hold trasition.
     * @throws PAException in case of error
     */
    @Test
    public void testCreateOffToOff() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        StudyOnholdDTO onholdDto = createStudyOnholdDTO(false);
        doCallRealMethod().when(sut).create(onholdDto);
        StudyOnhold onholdBo = new StudyOnholdConverter().convertFromDtoToDomain(onholdDto);
        when(sut.convertFromDtoToDomain(onholdDto)).thenReturn(onholdBo);
        when(sut.getDocumentWorkflowStatus(onholdDto.getStudyProtocolIdentifier()))
            .thenReturn(DocumentWorkflowStatusCode.ON_HOLD);
        when(sut.isOnhold(onholdDto.getStudyProtocolIdentifier())).thenReturn(BlConverter.convertToBl(false));
        sut.create(onholdDto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).getDocumentWorkflowStatus(onholdDto.getStudyProtocolIdentifier());
        inOrder.verify(sut).isOnhold(onholdDto.getStudyProtocolIdentifier());
        inOrder.verify(sut).statusRulesForCreation(true, false, false);
        assertNull("Wrong previous status", onholdDto.getPreviousStatusCode());
        inOrder.verify(sut, never()).createDocumentWorkflowStatus(any(Ii.class), any(DocumentWorkflowStatusCode.class));
    }
    
    /**
     * Test the validationForCreation method without dto.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationNoDto() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("No StudyOnholdDTO provided.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        sut.validationForCreation(null);
    }
    
    /**
     * Test the validationForCreation method without study protocol Ii.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationNoStudyPrototocolIi() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Study Protocol is required.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        sut.validationForCreation(new StudyOnholdDTO());
    }
    
    /**
     * Test the validationForCreation method without dto.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationOther() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        StudyOnholdDTO dto = createStudyOnholdDTO(false);
        doCallRealMethod().when(sut).validationForCreation(dto);
        sut.validationForCreation(dto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).setTimeIfToday(dto);
        inOrder.verify(sut).reasonRules(dto);
        inOrder.verify(sut).dateRules(dto);
    }
    
    /**
     * Test the getDocumentWorkflowStatus method.
     * @throws PAException in case of error
     */
    @Test
    public void testgetDocumentWorkflowStatus() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        when(documentWorkflowStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(dto);
        DocumentWorkflowStatusCode result = sut.getDocumentWorkflowStatus(spIi);
        assertEquals("Wrong status returned", DocumentWorkflowStatusCode.SUBMITTED, result);
    }
    
    /**
     * Test the update method for an off-hold to off-hold transition.
     * @throws PAException in case of error
     */
    @Test
    public void testUpdateOffToOff() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        when(sut.getTypeArgument()).thenReturn(StudyOnhold.class);
        StudyOnholdDTO dto = createStudyOnholdDTO(false);
        Ii onholdIi = IiConverter.convertToStudyOnHoldIi(TestSchema.studyOnholdIds.get(0));
        dto.setIdentifier(onholdIi);
        doCallRealMethod().when(sut).update(dto);
        StudyOnholdDTO existingDto = createStudyOnholdDTO(false);
        existingDto.setIdentifier(onholdIi);
        when(sut.get(onholdIi)).thenReturn(existingDto);
        when(sut.getDocumentWorkflowStatus(dto.getStudyProtocolIdentifier()))
            .thenReturn(DocumentWorkflowStatusCode.SUBMITTED);
        when(sut.isOnhold(dto.getStudyProtocolIdentifier())).thenReturn(BlConverter.convertToBl(false));
        sut.update(dto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).validationForUpdate(dto);
        inOrder.verify(sut).get(onholdIi);
        inOrder.verify(sut).setTimeIfToday(existingDto);
        inOrder.verify(sut).dateRules(existingDto);
        inOrder.verify(sut).statusRulesForUpdate(false, false, false);
        inOrder.verify(sut, never()).createDocumentWorkflowStatus(any(Ii.class), any(DocumentWorkflowStatusCode.class));
    }
    
    /**
     * Test the update method for an on-hold to off-hold transition.
     * @throws PAException in case of error
     */
    @Test
    public void testUpdateOnToOff() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        when(sut.getTypeArgument()).thenReturn(StudyOnhold.class);
        StudyOnholdDTO dto = createStudyOnholdDTO(false);
        Ii onholdIi = IiConverter.convertToStudyOnHoldIi(TestSchema.studyOnholdIds.get(0));
        dto.setIdentifier(onholdIi);
        doCallRealMethod().when(sut).update(dto);
        StudyOnholdDTO existingDto = createStudyOnholdDTO(true);
        existingDto.setIdentifier(onholdIi);
        existingDto.setPreviousStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        when(sut.get(onholdIi)).thenReturn(existingDto);
        when(sut.getDocumentWorkflowStatus(dto.getStudyProtocolIdentifier()))
            .thenReturn(DocumentWorkflowStatusCode.ON_HOLD);
        when(sut.isOnhold(dto.getStudyProtocolIdentifier())).thenReturn(BlConverter.convertToBl(true));
        sut.update(dto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).validationForUpdate(dto);
        inOrder.verify(sut).get(onholdIi);
        inOrder.verify(sut).setTimeIfToday(existingDto);
        inOrder.verify(sut).dateRules(existingDto);
        inOrder.verify(sut).statusRulesForUpdate(true, true, false);
        inOrder.verify(sut).createDocumentWorkflowStatus(dto.getStudyProtocolIdentifier(),
                                                         DocumentWorkflowStatusCode.SUBMITTED);

    }
    
    /**
     * Test the update method for an off-hold to off-hold transition.
     * @throws PAException in case of error
     */
    @Test
    public void testUpdateError() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("On Hold record does not exist.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        StudyOnholdDTO dto = createStudyOnholdDTO(false);
        Ii onholdIi = IiConverter.convertToStudyOnHoldIi(1L);
        dto.setIdentifier(onholdIi);
        doCallRealMethod().when(sut).update(dto);
        sut.update(dto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).validationForUpdate(dto);
        inOrder.verify(sut).get(onholdIi);
    }
    
    /**
     * Test the validationForUpdate method without dto.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForUpdateNoDto() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("No StudyOnholdDTO provided.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        sut.validationForUpdate(null);
    }
    
    /**
     * Test the validationForUpdate method without identifier.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForUpdateNoIdentifier() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Identifier is required.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        sut.validationForUpdate(new StudyOnholdDTO());
    }
    
    /**
     * Test the validationForUpdate method success.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForUpdateSuccess() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        dto.setIdentifier(IiConverter.convertToStudyOnHoldIi(1L));
        sut.validationForUpdate(dto);
    }
    
    /**
     * Test the setTimeIfToday method for today.
     */
    @Test
    public void testSetTimeIfTodayToday() {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        DateTime now = new DateTime();
        Timestamp ts = new Timestamp(now.getMillis());
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(ts,ts));
        sut.setTimeIfToday(dto);
        DateMidnight before = now.toDateMidnight();
        DateMidnight after = before.plusDays(1);
        DateTime low = new DateTime(IvlConverter.convertTs().convertLow(dto.getOnholdDate()));
        assertTrue (before.isBefore(low) && low.isBefore(after));
        DateTime high = new DateTime(IvlConverter.convertTs().convertHigh(dto.getOnholdDate()));
        assertTrue (before.isBefore(high) && high.isBefore(after));
    }
    
    /**
     * Test the setTimeIfToday method for another day.
     */
    @Test
    public void testSetTimeIfTodayOther() {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        DateTime now = new DateTime().minusDays(3);
        Timestamp ts = new Timestamp(now.getMillis());
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(ts, ts));
        sut.setTimeIfToday(dto);
        assertEquals(ts, IvlConverter.convertTs().convertLow(dto.getOnholdDate()));
        assertEquals(ts, IvlConverter.convertTs().convertHigh(dto.getOnholdDate()));
    }

    /**
     * Test the reasonRules method with no reason code.
     * @throws PAException in case of error
     */
    @Test
    public void testReasonRulesError() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("The On-hold reason code is a required field.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        sut.reasonRules(new StudyOnholdDTO());
    }

    /**
     * Test the reasonRules method with a reason code.
     * @throws PAException in case of error
     */
    @Test
    public void testReasonRulesSuccess() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        dto.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.SUBMISSION_INCOM));
        sut.reasonRules(dto);
    }

    /**
     * Test the dateRules method with no low timestamp.
     * @throws PAException in case of error
     */
    @Test
    public void testDateRulesNoLow() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("On-hold date is required.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, null));
        sut.dateRules(dto);
    }
    
    /**
     * Test the dateRules method with low in the future.
     * @throws PAException in case of error
     */
    @Test
    public void testDateRulesLowFuture() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("On-hold date must be today's date.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        Timestamp low = new Timestamp(new DateTime().plusDays(1).getMillis());
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(low, null));
        sut.dateRules(dto);
    }
    
    /**
     * Test the dateRules method with valid low and no high.
     * @throws PAException in case of error
     */
    @Test
    public void testDateRulesNoHigh() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        Timestamp low = new Timestamp(new DateTime().getMillis());
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(low, null));
        sut.dateRules(dto);
    }
    
    /**
     * Test the dateRules method with valid low and high in the future.
     * @throws PAException in case of error
     */
    @Test
    public void testDateRulesHighFuture() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Off-hold dates must be only past or current dates.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        Timestamp low = new Timestamp(new DateTime().getMillis());
        Timestamp high = new Timestamp(new DateTime().plusDays(1).getMillis());
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(low, high));
        sut.dateRules(dto);
    }
    
    /**
     * Test the dateRules method with valid low and high in reverse.
     * @throws PAException in case of error
     */
    @Test
    public void testDateRulesReverse() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Off-hold date must be bigger than on-hold date for the same on-hold record.");
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        Timestamp low = new Timestamp(new DateTime().minusHours(1).getMillis());
        Timestamp high = new Timestamp(new DateTime().minusHours(2).getMillis());
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(low, high));
        // skip test if running early in day, test fails
        if (DateUtils.isSameDay(low, new Date())) {
            sut.dateRules(dto);
        }
    }
    
    /**
     * Test the dateRules method with valid dates.
     * @throws PAException in case of error
     */
    @Test
    public void testDateRulesValid() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        StudyOnholdDTO dto = new StudyOnholdDTO();
        Timestamp low = new Timestamp(new DateTime().minusHours(2).getMillis());
        Timestamp high = new Timestamp(new DateTime().minusHours(1).getMillis());
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(low, high));
        // skip test if running early in day, test fails
        if (DateUtils.isSameDay(low, new Date())) {
            sut.dateRules(dto);
        }
    }
    
    /**
     * Test the createDocumentWorkflowStatus method.
     * @throws PAException in case of error
     */
    @Test
    public void testCreateDocumentWorkflowStatus() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocalMock();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        DocumentWorkflowStatusCode status = DocumentWorkflowStatusCode.SUBMITTED;
        doCallRealMethod().when(sut).createDocumentWorkflowStatus(spIi, status);
        sut.createDocumentWorkflowStatus(spIi, status);
        ArgumentCaptor<DocumentWorkflowStatusDTO> captor = ArgumentCaptor.forClass(DocumentWorkflowStatusDTO.class);
        verify(documentWorkflowStatusService).create(captor.capture());
        DocumentWorkflowStatusDTO dto = captor.getValue();
        assertEquals("Wrong study protocol Ii", spIi, dto.getStudyProtocolIdentifier());
        assertEquals("Wrong status", status,
                     CdConverter.convertCdToEnum(DocumentWorkflowStatusCode.class, dto.getStatusCode()));
    }

    /**
     * Test the getByStudyProtocol method.
     * @throws PAException in case of error
     */
    @Test
    public void testGetByStudyProtocol() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
        dwsDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));;
        when(documentWorkflowStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(dwsDto);
        int originalCount = sut.getByStudyProtocol(spIi).size();
        StudyOnholdDTO x = new StudyOnholdDTO();
        Timestamp ts = new Timestamp(new DateTime().getMillis());
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(ts, ts));
        x.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.PENDING_ORG_CUR));
        x.setOnholdReasonText(StConverter.convertToSt("reason"));
        x.setStudyProtocolIdentifier(spIi);
        sut.create(x);
        assertEquals(originalCount + 1, sut.getByStudyProtocol(spIi).size());
    }
    
    @Test
    public void testNoOnHoldProcessing() throws PAException, Exception {
        reset(mailManagerServiceLocal, studyMilestoneServicelocal);
        StudyOnholdBeanLocal studyOnholdBeanLocal = createStudyOnholdBeanLocalMock(); 
        Session session = PaHibernateUtil.getCurrentSession();
        
        // too early to send a reminder.
        onholdRecord.setOnholdDate(new Timestamp(DateUtils.addDays(today, -2).getTime()));
        onholdRecord.setOffholdDate(null);
        session.update(onholdRecord);
        session.flush();
        session.evict(onholdRecord);                
        
        studyOnholdBeanLocal.processOnHoldTrials();        
        verifyNoProcessingHappened(session);
        
        // reminder is due, but on-hold reason is not suitable
        for (OnholdReasonCode code : reasonsToSkipReminders) {
            onholdRecord = (StudyOnhold) session.get(StudyOnhold.class, onholdRecord.getId());
            onholdRecord.setOnholdDate(new Timestamp(DateUtils.addDays(today, -3).getTime()));
            onholdRecord.setOffholdDate(null);
            onholdRecord.setOnholdReasonCode(code);
            session.update(onholdRecord);            
            session.flush();       
            session.evict(onholdRecord);
            
            studyOnholdBeanLocal.processOnHoldTrials();        
            verifyNoProcessingHappened(session);            
        }
        
        // reminder due, reason ok, but hold is closed.
        onholdRecord = (StudyOnhold) session.get(StudyOnhold.class, onholdRecord.getId());
        onholdRecord.setOnholdDate(new Timestamp(DateUtils.addDays(today, -3).getTime()));
        onholdRecord.setOffholdDate(new Timestamp(today.getTime()));
        onholdRecord.setOnholdReasonCode(OnholdReasonCode.SUBMISSION_INCOM);
        session.update(onholdRecord);            
        session.flush();       
        session.evict(onholdRecord);
        
        studyOnholdBeanLocal.processOnHoldTrials();        
        verifyNoProcessingHappened(session);
        
        // bad data: onhold date is after today's date.
        onholdRecord = (StudyOnhold) session.get(StudyOnhold.class, onholdRecord.getId());
        onholdRecord.setOnholdDate(new Timestamp(DateUtils.addDays(today, +3).getTime()));
        onholdRecord.setOffholdDate(null);
        onholdRecord.setOnholdReasonCode(OnholdReasonCode.SUBMISSION_INCOM);
        session.update(onholdRecord);            
        session.flush();       
        session.evict(onholdRecord);
        
        studyOnholdBeanLocal.processOnHoldTrials();        
        verifyNoProcessingHappened(session);            
        
        
    }
    
    @Test
    public void testOnHoldReminder() throws PAException, Exception {
        reset(mailManagerServiceLocal, studyMilestoneServicelocal);
        StudyOnholdBeanLocal studyOnholdBeanLocal = createStudyOnholdBeanLocalMock();
        Session session = PaHibernateUtil.getCurrentSession();

        // reminder is due.
        onholdRecord.setOnholdDate(new Timestamp(DateUtils.addDays(today, -3)
                .getTime()));
        onholdRecord.setOffholdDate(null);
        onholdRecord.setProcessingLog(null);
        session.update(onholdRecord);
        session.flush();

        when(
                mailManagerServiceLocal.sendOnHoldReminder(any(Long.class),
                        any(StudyOnhold.class), any(Date.class))).thenReturn(
                Arrays.asList("denis.krylov@semanticbits.com"));
        studyOnholdBeanLocal.processOnHoldTrials();
        verify(mailManagerServiceLocal, times(1)).sendOnHoldReminder(
                eq(onholdStudy.getId()), eq(onholdRecord),
                eq(DateUtils.addDays(onholdRecord.getOnholdDate(), 21)));
        assertEquals("03/23/2012_denis.krylov@semanticbits.com",
                onholdRecord.getProcessingLog());

    }
    
    @Test
    public void testOnHoldEqualsStartDate() throws PAException, ParseException, Exception {
        reset(mailManagerServiceLocal, studyMilestoneServicelocal);
        StudyOnholdBeanLocal studyOnholdBeanLocal = createStudyOnholdBeanLocalMock();
        Session session = PaHibernateUtil.getCurrentSession();

        onholdRecord.setOnholdDate(new Timestamp(DateUtils.parseDate(ONHOLD_NOTIF_START_DATE,new String[] {"MM/dd/yyyy"})
                .getTime()));
        onholdRecord.setOffholdDate(null);
        onholdRecord.setProcessingLog(null);
        session.update(onholdRecord);
        session.flush();

        ArgumentCaptor<StudyMilestoneDTO> milestoneCaptor = ArgumentCaptor
                .forClass(StudyMilestoneDTO.class);
        ArgumentCaptor<StudyOnholdDTO> holdCaptor = ArgumentCaptor
                .forClass(StudyOnholdDTO.class);

        studyOnholdBeanLocal.processOnHoldTrials();
        verify(mailManagerServiceLocal, times(1))
                .sendSubmissionTerminationEmail(eq(onholdStudy.getId()));
        verify(studyMilestoneServicelocal).create(milestoneCaptor.capture());
        verify(studyOnholdBeanLocal).update(holdCaptor.capture());

        StudyMilestoneDTO milestone = milestoneCaptor.getValue();
        assertEquals("trial.onhold.termination.comment", milestone
                .getCommentText().getValue());
        assertEquals("unspecifieduser", milestone.getCreator().getValue());
        assertEquals(onholdStudy.getId().toString(), milestone
                .getStudyProtocolIdentifier().getExtension());
        assertEquals(MilestoneCode.SUBMISSION_TERMINATED.getCode(), milestone
                .getMilestoneCode().getCode());
        assertTrue(DateUtils.isSameDay(today,
                TsConverter.convertToTimestamp(milestone.getCreationDate())));
        assertTrue(DateUtils.isSameDay(today,
                TsConverter.convertToTimestamp(milestone.getMilestoneDate())));

        StudyOnholdDTO holdDTO = holdCaptor.getValue();
        assertEquals("03/23/2012", holdDTO.getProcessingLog().getValue());
        assertTrue(DateUtils.isSameDay(today, IvlConverter.convertTs()
                .convertHigh(holdDTO.getOnholdDate())));  
    }

    @Test
    public void testOnHoldBeforeStartDate() throws PAException, ParseException, Exception {
        reset(mailManagerServiceLocal, studyMilestoneServicelocal);
        StudyOnholdBeanLocal studyOnholdBeanLocal = createStudyOnholdBeanLocalMock();
        Session session = PaHibernateUtil.getCurrentSession();

        onholdRecord.setOnholdDate(new Timestamp(DateUtils.parseDate(
                "01/01/2000", new String[] { "MM/dd/yyyy" }).getTime()));
        onholdRecord.setOffholdDate(null);
        onholdRecord.setProcessingLog(null);
        session.update(onholdRecord);
        session.flush();

        ArgumentCaptor<StudyMilestoneDTO> milestoneCaptor = ArgumentCaptor
                .forClass(StudyMilestoneDTO.class);
        ArgumentCaptor<StudyOnholdDTO> holdCaptor = ArgumentCaptor
                .forClass(StudyOnholdDTO.class);

        studyOnholdBeanLocal.processOnHoldTrials();

        verifyZeroInteractions(mailManagerServiceLocal,
                studyMilestoneServicelocal);

        try {
            milestoneCaptor.getValue();
            Assert.fail();
        } catch (MockitoException e) {
        }

        try {
            holdCaptor.getValue();
            Assert.fail();
        } catch (MockitoException e) {
        }
    }
    
    @Test
    public void testOnHoldStartDateWrong() throws PAException, ParseException, Exception {
        reset(mailManagerServiceLocal, studyMilestoneServicelocal);
        StudyOnholdBeanLocal studyOnholdBeanLocal = createStudyOnholdBeanLocalMock();
        Session session = PaHibernateUtil.getCurrentSession();

        onholdRecord.setOnholdDate(new Timestamp(DateUtils.parseDate(
                "01/01/2000", new String[] { "MM/dd/yyyy" }).getTime()));
        onholdRecord.setOffholdDate(null);
        onholdRecord.setProcessingLog(null);
        session.update(onholdRecord);
        session.flush();

        try {
            when(
                    lookUpTableServiceRemote
                            .getPropertyValue(eq("trial.onhold.startdate")))
                    .thenReturn("");
            studyOnholdBeanLocal.processOnHoldTrials();
            verifyZeroInteractions(mailManagerServiceLocal,
                    studyMilestoneServicelocal);            
        } finally {
            when(
                    lookUpTableServiceRemote
                            .getPropertyValue(eq("trial.onhold.startdate")))
                    .thenReturn(ONHOLD_NOTIF_START_DATE);
        }

    }
    
    
    @Test
    public void testSubmissionTermination() throws PAException, Exception {
        reset(mailManagerServiceLocal, studyMilestoneServicelocal);
        StudyOnholdBeanLocal studyOnholdBeanLocal = createStudyOnholdBeanLocalMock();
        Session session = PaHibernateUtil.getCurrentSession();

        onholdRecord.setOnholdDate(new Timestamp(DateUtils.addMinutes(
                DateUtils.addDays(today, -21), -1).getTime()));
        onholdRecord.setOffholdDate(null);
        onholdRecord.setProcessingLog(null);
        session.update(onholdRecord);
        session.flush();

        ArgumentCaptor<StudyMilestoneDTO> milestoneCaptor = ArgumentCaptor
                .forClass(StudyMilestoneDTO.class);
        ArgumentCaptor<StudyOnholdDTO> holdCaptor = ArgumentCaptor
                .forClass(StudyOnholdDTO.class);

        studyOnholdBeanLocal.processOnHoldTrials();
        verify(mailManagerServiceLocal, times(1))
                .sendSubmissionTerminationEmail(eq(onholdStudy.getId()));
        verify(studyMilestoneServicelocal).create(milestoneCaptor.capture());
        verify(studyOnholdBeanLocal).update(holdCaptor.capture());

        StudyMilestoneDTO milestone = milestoneCaptor.getValue();
        assertEquals("trial.onhold.termination.comment", milestone
                .getCommentText().getValue());
        assertEquals("unspecifieduser", milestone.getCreator().getValue());
        assertEquals(onholdStudy.getId().toString(), milestone
                .getStudyProtocolIdentifier().getExtension());
        assertEquals(MilestoneCode.SUBMISSION_TERMINATED.getCode(), milestone
                .getMilestoneCode().getCode());
        assertTrue(DateUtils.isSameDay(today,
                TsConverter.convertToTimestamp(milestone.getCreationDate())));
        assertTrue(DateUtils.isSameDay(today,
                TsConverter.convertToTimestamp(milestone.getMilestoneDate())));

        StudyOnholdDTO holdDTO = holdCaptor.getValue();
        assertEquals("03/23/2012", holdDTO.getProcessingLog().getValue());
        assertTrue(DateUtils.isSameDay(today, IvlConverter.convertTs()
                .convertHigh(holdDTO.getOnholdDate())));

    }    

    /**
     * @param session
     * @throws HibernateException
     */
    private void verifyNoProcessingHappened(Session session)
            throws HibernateException {
        verifyZeroInteractions(mailManagerServiceLocal, studyMilestoneServicelocal);        
        StudyOnhold newOnHold = (StudyOnhold) session.get(StudyOnhold.class, onholdRecord.getId());
        assertEquals(onholdRecord.getDateLastCreated(), newOnHold.getDateLastCreated());
        assertEquals(onholdRecord.getDateLastUpdated(), newOnHold.getDateLastUpdated());
        assertEquals(onholdRecord.getOffholdDate(), newOnHold.getOffholdDate());        
        assertEquals(onholdRecord.getOnholdReasonCode(), newOnHold.getOnholdReasonCode());
        assertEquals(onholdRecord.getOnholdReasonText(), newOnHold.getOnholdReasonText());
        assertEquals(onholdRecord.getPreviousStatusCode(), newOnHold.getPreviousStatusCode());
        assertEquals(onholdRecord.getProcessingLog(), newOnHold.getProcessingLog());
    }
    
    /**
     * Test if reason category value automatically populated to Submitter
     * @throws PAException in case of error
     */
    @Test
    public void testReasonCategoryisSetToSubmitter() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
        dwsDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));;
        when(documentWorkflowStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(dwsDto);
        StudyOnholdDTO studyOnholdDTO = new StudyOnholdDTO();
        Timestamp ts = new Timestamp(new DateTime().getMillis());
        studyOnholdDTO.setOnholdDate(IvlConverter.convertTs().convertToIvl(ts, ts));
        studyOnholdDTO.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.SUBMISSION_INCOM));
        studyOnholdDTO.setOnholdReasonText(StConverter.convertToSt("reason"));
        studyOnholdDTO.setStudyProtocolIdentifier(spIi);
        sut.create(studyOnholdDTO);
    
       List<StudyOnholdDTO> onholdDtoList =sut.getByStudyProtocol(spIi);
       
       assertTrue(onholdDtoList.size() > 0);
       assertTrue(onholdDtoList.get(0).getOnHoldCategory().getValue().equals("Submitter"));
      
    }    

    /**
     * Test if reason category value automatically populated to CTRP
     * @throws PAException in case of error
     */
    @Test
    public void testReasonCategoryisSetToCTRP() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
        dwsDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));;
        when(documentWorkflowStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(dwsDto);
        StudyOnholdDTO studyOnholdDTO = new StudyOnholdDTO();
        Timestamp ts = new Timestamp(new DateTime().getMillis());
        studyOnholdDTO.setOnholdDate(IvlConverter.convertTs().convertToIvl(ts, ts));
        studyOnholdDTO.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.PENDING_CTRP_REVIEW));
        studyOnholdDTO.setOnholdReasonText(StConverter.convertToSt("reason"));
        studyOnholdDTO.setStudyProtocolIdentifier(spIi);
        sut.create(studyOnholdDTO);
    
       List<StudyOnholdDTO> onholdDtoList =sut.getByStudyProtocol(spIi);
       
       assertTrue(onholdDtoList.size() > 0);
       assertTrue(onholdDtoList.get(0).getOnHoldCategory().getValue().equals("CTRP"));
      
    } 
    
    /**
     * Test if reason category value does automatically populated 
     * where reason code is other and this value is updated to what user has set
     * @throws PAException in case of error
     */
    @Test
    public void testReasonCategoryisUpdated() throws PAException {
        StudyOnholdBeanLocal sut = createStudyOnholdBeanLocal();
        DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
        dwsDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));;
        when(documentWorkflowStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(dwsDto);
        StudyOnholdDTO studyOnholdDTO = new StudyOnholdDTO();
        Timestamp ts = new Timestamp(new DateTime().getMillis());
        studyOnholdDTO.setOnholdDate(IvlConverter.convertTs().convertToIvl(ts, ts));
        studyOnholdDTO.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.OTHER));
        studyOnholdDTO.setOnHoldCategory(StConverter.convertToSt("reasonCategory"));
        studyOnholdDTO.setOnholdReasonText(StConverter.convertToSt("reason"));
        studyOnholdDTO.setStudyProtocolIdentifier(spIi);
        sut.create(studyOnholdDTO);
    
       List<StudyOnholdDTO> onholdDtoList =sut.getByStudyProtocol(spIi);
       
       assertTrue(onholdDtoList.size() > 0);
       assertTrue(onholdDtoList.get(0).getOnHoldCategory().getValue().equals("reasonCategory"));
      
    } 
   
}
