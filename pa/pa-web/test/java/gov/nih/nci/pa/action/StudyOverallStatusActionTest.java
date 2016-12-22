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
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.InOrder;

import com.mockrunner.mock.web.MockHttpServletRequest;
/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusActionTest extends AbstractPaActionTest {
    private ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class);
    private StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
    private StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private StudyCheckoutServiceLocal studyCheckoutService = mock(StudyCheckoutServiceLocal.class);
    private StudyOverallStatusAction sut;

    /**
     * Creates a real StudyOverallStatusAction and inject the mock services in it.
     * @return A real StudyOverallStatusAction with mock services injected.
     */
    private StudyOverallStatusAction createStudyOverallStatusAction() {
        StudyOverallStatusAction action = new StudyOverallStatusAction();
        setDependencies(action);
        return action;
    }

    /**
     * Creates a mock StudyOverallStatusAction and inject the mock services in it.
     * @return A mock StudyOverallStatusAction with mock services injected.
     */
    private StudyOverallStatusAction createStudyOverallStatusActionMock() {
        StudyOverallStatusAction action = mock(StudyOverallStatusAction.class);
        doCallRealMethod().when(action).setProtocolQueryService(protocolQueryService);
        doCallRealMethod().when(action).setStudyOverallStatusService(studyOverallStatusService);
        doCallRealMethod().when(action).setStudyProtocolService(studyProtocolService);
        doCallRealMethod().when(action).setServletRequest(ServletActionContext.getRequest());
        setDependencies(action);
        return action;
    }

    /**
     * Inject the mock services in the given StudyOverallStatusAction.
     * @param action The StudyOverallStatusAction to setup with mock services
     */
    private void setDependencies(StudyOverallStatusAction action) {
        action.setProtocolQueryService(protocolQueryService);
        action.setStudyOverallStatusService(studyOverallStatusService);
        action.setStudyProtocolService(studyProtocolService);
        action.setStudyCheckoutService(studyCheckoutService);
        action.setServletRequest(ServletActionContext.getRequest());
    }

    /**
     * test the execute method in the success case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testExecuteSuccess() throws PAException {
        sut = createStudyOverallStatusActionMock();
        doCallRealMethod().when(sut).execute();
        String result = sut.execute();
        assertEquals("Wrong result returned", "success", result);
        
        verify(sut).loadForm();
        
        verify(sut, never()).addActionError(anyString());
    }

    /**
     * test the execute method in the failure case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testExecuteFailure() throws PAException {
        sut = createStudyOverallStatusActionMock();
        doCallRealMethod().when(sut).execute();
        doThrow(new PAException("PAException")).when(sut).loadForm();
        
        String result = sut.execute();
        
        assertEquals("Wrong result returned", "success", result);
        verify(sut).loadForm();
        verify(sut).addActionError("PAException");
    }

    /**
     * Test the loadForm method with data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testLoadFormWithData() throws PAException {
        sut = createStudyOverallStatusAction();
        sut.prepareData();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        StudyProtocolDTO studyProtocolDTO = createStudyProtocolDTO();
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(studyProtocolDTO);
        StudyOverallStatusDTO studyOverallStatusDTO = createStudyOverallStatusDTO();
        when(studyOverallStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(studyOverallStatusDTO);

        sut.loadForm();

        assertEquals("Wrong startDate", TsConverter.convertToString(studyProtocolDTO.getStartDate()),
                     sut.getStartDate());
        assertEquals("Wrong primaryCompletionDate",
                     TsConverter.convertToString(studyProtocolDTO.getPrimaryCompletionDate()),
                     sut.getPrimaryCompletionDate());
        assertEquals("Wrong completionDate", TsConverter.convertToString(studyProtocolDTO.getCompletionDate()),
                     sut.getCompletionDate());
        assertEquals("Wrong startDateType", studyProtocolDTO.getStartDateTypeCode().getCode(), sut.getStartDateType());
        assertEquals("Wrong primaryCompletionDateType", studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode(),
                     sut.getPrimaryCompletionDateType());
        assertEquals("Wrong completionDateType", studyProtocolDTO.getCompletionDateTypeCode().getCode(),
                     sut.getCompletionDateType());
        assertEquals("Wrong status code", studyOverallStatusDTO.getStatusCode().getCode(), sut.getCurrentTrialStatus());
        assertEquals("Wrong status Date", TsConverter.convertToString(studyOverallStatusDTO.getStatusDate()),
                     sut.getStatusDate());
        assertEquals("Wrong status reason", StConverter.convertToString(studyOverallStatusDTO.getReasonText()),
                     sut.getStatusReason());
    }

    /**
     * Test the loadForm method with no data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testLoadFormNoData() throws PAException {
        sut = createStudyOverallStatusAction();
        sut.prepareData();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(null);
        when(studyOverallStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(null);
        
        sut.loadForm();
        
        assertNull("Wrong startDate", sut.getStartDate());
        assertNull("Wrong primaryCompletionDate", sut.getPrimaryCompletionDate());
        assertNull("Wrong completionDate", sut.getCompletionDate());
        assertNull("Wrong startDateType", sut.getStartDateType());
        assertNull("Wrong primaryCompletionDateType", sut.getPrimaryCompletionDateType());
        assertNull("Wrong completionDateType", sut.getCompletionDateType());
        assertNull("Wrong status code", sut.getCurrentTrialStatus());
        assertNull("Wrong status Date", sut.getStatusDate());
        assertNull("Wrong status reason", sut.getStatusReason());
    }
    
    private StudyProtocolDTO createStudyProtocolDTO() {
        StudyProtocolDTO dto = new StudyProtocolDTO();
        DateTime now = new DateTime();
        dto.setStartDate(TsConverter.convertToTs(now.minusDays(2).toDate()));
        dto.setPrimaryCompletionDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        dto.setCompletionDate(TsConverter.convertToTs(now.toDate()));
        dto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        dto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        dto.setCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        return dto;
    }
    
    private StudyOverallStatusDTO createStudyOverallStatusDTO(){
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        dto.setStatusDate(TsConverter.convertToTs(new DateTime().toDate()));
        dto.setReasonText(StConverter.convertToSt("reason"));
        return dto;
    }
    
    /**
     * Test the update method in the success case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdateSuccess() throws PAException {
        sut = createStudyOverallStatusActionMock();
        doCallRealMethod().when(sut).update();
        StudyOverallStatusDTO statusDto = new StudyOverallStatusDTO();
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        StudyProtocolDTO currentProtocolDto = new StudyProtocolDTO();
        DateTime now = new DateTime();
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(now.minusDays(2).toDate()));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        studyProtocolDTO.setCompletionDate(TsConverter.convertToTs(now.toDate()));
        currentProtocolDto.setStartDate(TsConverter.convertToTs(now.minusDays(2).toDate()));
        currentProtocolDto.setPrimaryCompletionDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        currentProtocolDto.setCompletionDate(TsConverter.convertToTs(now.toDate()));
        when(sut.getStudyOverallStatus()).thenReturn(statusDto);
        when(studyProtocolService
                .getStudyProtocol(statusDto.getStudyProtocolIdentifier())).thenReturn(currentProtocolDto);
        when(sut.hasActionErrors()).thenReturn(false);
        
        String result = sut.update();
        
        assertEquals("Wrong result returned", "success", result);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).clearErrorsAndMessages();
        inOrder.verify(sut).getStudyOverallStatus();
        inOrder.verify(sut).insertOrUpdateStudyOverallStatus(statusDto);
        inOrder.verify(sut).updateStudyProtocol();
        inOrder.verify(sut).loadForm();
        assertEquals("Wrong success message", Constants.UPDATE_MESSAGE,
                     ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
    }
    
    /**
     * Test the update method in the error case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdateError() throws PAException {
        sut = createStudyOverallStatusActionMock();
        doCallRealMethod().when(sut).update();
        StudyOverallStatusDTO statusDto = new StudyOverallStatusDTO();
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        StudyProtocolDTO currentProtocolDto = new StudyProtocolDTO();
        DateTime now = new DateTime();
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(now.minusDays(2).toDate()));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        studyProtocolDTO.setCompletionDate(TsConverter.convertToTs(now.toDate()));
        currentProtocolDto.setStartDate(TsConverter.convertToTs(now.minusDays(2).toDate()));
        currentProtocolDto.setPrimaryCompletionDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        currentProtocolDto.setCompletionDate(TsConverter.convertToTs(now.toDate()));
        when(studyProtocolService
                .getStudyProtocol(statusDto.getStudyProtocolIdentifier())).thenReturn(currentProtocolDto);
        when(sut.getStudyOverallStatus()).thenReturn(statusDto);
        when(sut.hasActionErrors()).thenReturn(true);
        
        String result = sut.update();
        
        assertEquals("Wrong result returned", "success", result);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).clearErrorsAndMessages();
        inOrder.verify(sut).getStudyOverallStatus();
        inOrder.verify(sut).insertOrUpdateStudyOverallStatus(statusDto);
        inOrder.verify(sut).updateStudyProtocol();
        inOrder.verify(sut, never()).loadForm();
        assertNull("Wrong success message", ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
    }
    
    /**
     * Test the update method in the exception case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdateException() throws PAException {
        sut = createStudyOverallStatusActionMock();
        doCallRealMethod().when(sut).update();
        StudyOverallStatusDTO statusDto = new StudyOverallStatusDTO();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        DateTime now = new DateTime();
        studyProtocolDTO.setIdentifier(spIi);
        StudyProtocolDTO currentProtocolDto = new StudyProtocolDTO();
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(now.minusDays(2).toDate()));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        studyProtocolDTO.setCompletionDate(TsConverter.convertToTs(now.toDate()));
        currentProtocolDto.setStartDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        currentProtocolDto.setPrimaryCompletionDate(TsConverter.convertToTs(now.minusDays(1).toDate()));
        
        currentProtocolDto.setCompletionDate(TsConverter.convertToTs(now.toDate()));
        when(studyOverallStatusService.isTrialStatusOrDateChanged(statusDto, spIi)).thenReturn(true);
        when(studyProtocolService
                .getStudyProtocol(statusDto.getStudyProtocolIdentifier())).thenReturn(currentProtocolDto);
        when(sut.getStudyOverallStatus()).thenReturn(statusDto);
        doThrow(new PAException("PAException")).when(sut).validateOverallStatus(statusDto);
        
        String result = sut.update();
        
        assertEquals("Wrong result returned", "success", result);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).clearErrorsAndMessages();
        inOrder.verify(sut).getStudyOverallStatus();
    }

    /**
     * Test the getStudyOverallStatus method.
     * @throws PAException 
     */
    @Test
    public void testGetStudyOverallStatus() throws PAException {
        sut = createStudyOverallStatusAction();
        sut.prepareData();
        sut.setStatusReason("reason");
        sut.setCurrentTrialStatus(StudyStatusCode.ACTIVE.getCode());
        String now = TsConverter.convertToString(TsConverter.convertToTs(new Date()));
        sut.setStatusDate(now);
        
        StudyOverallStatusDTO status = sut.getStudyOverallStatus();
        
        assertNotNull("No result returned", status);
        assertNotNull("status identifier is null", status.getIdentifier());
        assertEquals("Wrong status identifier", NullFlavor.NI, status.getIdentifier().getNullFlavor());
        assertNotNull("status reason is null", status.getReasonText());
        assertEquals("Wrong status reason", "reason", status.getReasonText().getValue());
        assertNotNull("status code is null", status.getStatusCode());
        assertEquals("Wrong status code", StudyStatusCode.ACTIVE.getCode(), status.getStatusCode().getCode());
        assertNotNull("status date is null", status.getStatusDate());
        assertEquals("Wrong status date", now, TsConverter.convertToString(status.getStatusDate()));
        assertEquals("Wrong study protocol Ii", IiConverter.convertToStudyProtocolIi(1L),
                     status.getStudyProtocolIdentifier());
    }

    /**
     * Test the validateOverallStatus method.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test(expected = PAException.class)
    public void testValidateOverallStatus() throws PAException {
        sut = createStudyOverallStatusActionMock();
        StudyOverallStatusDTO statusDto = createStudyOverallStatusDTO();
        doCallRealMethod().when(sut).prepareData();
        doCallRealMethod().when(sut).validateOverallStatus(statusDto);
        sut.prepareData();

        sut.validateOverallStatus(statusDto);

    }

    /**
     * Test the getStudyProtocolDates method.
     */
    @Test
    public void testGetStudyProtocolDates() {
        sut = createStudyOverallStatusAction();
        String startDate = TsConverter.convertToString(TsConverter.convertToTs(new Date()));
        sut.setStartDate(startDate);
        sut.setStartDateType(ActualAnticipatedTypeCode.ACTUAL.getCode());
        String primaryCompletionDate = TsConverter.convertToString(TsConverter.convertToTs(new Date()));
        sut.setPrimaryCompletionDate(primaryCompletionDate);
        sut.setPrimaryCompletionDateType(ActualAnticipatedTypeCode.ANTICIPATED.getCode());
        String completionDate = TsConverter.convertToString(TsConverter.convertToTs(new Date()));
        sut.setCompletionDate(completionDate);
        sut.setCompletionDateType(ActualAnticipatedTypeCode.ANTICIPATED.getCode());
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();

        sut.getStudyProtocolDates(studyProtocolDTO);

        assertNotNull("Start Date Type is null", studyProtocolDTO.getStartDateTypeCode());
        assertEquals("Wrong Start Date Type", ActualAnticipatedTypeCode.ACTUAL.getCode(), studyProtocolDTO
            .getStartDateTypeCode().getCode());
        assertNotNull("Start Date is null", studyProtocolDTO.getStartDate());
        assertEquals("Wrong Start Date", startDate, TsConverter.convertToString(studyProtocolDTO.getStartDate()));
        assertNotNull("Primary Completion Date Type is null", studyProtocolDTO.getPrimaryCompletionDateTypeCode());
        assertEquals("Wrong Primary Completion Date Type", ActualAnticipatedTypeCode.ANTICIPATED.getCode(),
                     studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode());
        assertNotNull("Primary Completion Date is null", studyProtocolDTO.getPrimaryCompletionDate());
        assertEquals("Wrong Primary Completion Date", primaryCompletionDate,
                     TsConverter.convertToString(studyProtocolDTO.getPrimaryCompletionDate()));
        
        assertNotNull("Completion Date Type is null", studyProtocolDTO.getCompletionDateTypeCode());
        assertEquals("Wrong Completion Date Type", ActualAnticipatedTypeCode.ANTICIPATED.getCode(),
                     studyProtocolDTO.getCompletionDateTypeCode().getCode());
        assertNotNull("Completion Date is null", studyProtocolDTO.getCompletionDate());
        assertEquals("Wrong Completion Date", completionDate,
                     TsConverter.convertToString(studyProtocolDTO.getCompletionDate()));
    }
    
    /**
     * Test the insertOrUpdateStudyOverallStatus in the update case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testInsertOrUpdateStudyOverallStatusUpdate() throws PAException {
        sut = createStudyOverallStatusAction();
        sut.prepareData();
        sut.setCurrentTrialStatus(StudyStatusCode.ACTIVE.getCode());
        String statusDate = TsConverter.convertToString(TsConverter.convertToTs(new Date()));
        sut.setStatusDate(statusDate);
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
        studyProtocolQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L)).thenReturn(studyProtocolQueryDTO);
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt(1));
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(studyProtocolDTO);
        StudyOverallStatusDTO existingStatus = new StudyOverallStatusDTO();
        Ii statusIi = IiConverter.convertToStudyOverallStatusIi(2L);
        existingStatus.setIdentifier(statusIi);
        when(studyOverallStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(existingStatus);
        StudyOverallStatusDTO statusDto = new StudyOverallStatusDTO();
        sut.setSosDto(statusDto);
        
        sut.insertOrUpdateStudyOverallStatus(statusDto);
        
        InOrder inOrder = inOrder(protocolQueryService, studyOverallStatusService, studyProtocolService);
        inOrder.verify(protocolQueryService).getTrialSummaryByStudyProtocolId(1L);
        inOrder.verify(studyProtocolService).getStudyProtocol(spIi);
        inOrder.verify(studyOverallStatusService).update(statusDto);
        assertEquals("Wrong status id", statusIi, statusDto.getIdentifier());
        assertEquals("Wrong study status code", StudyStatusCode.ACTIVE,studyProtocolQueryDTO.getStudyStatusCode());
        assertEquals("Wrong study status date", statusDate, 
                     TsConverter.convertToString(TsConverter.convertToTs(studyProtocolQueryDTO.getStudyStatusDate())));
        assertEquals("Wrong study protocol in session", studyProtocolQueryDTO, 
                     ServletActionContext.getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY));

    }
    
    /**
     * Test the insertOrUpdateStudyOverallStatus in the create case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testInsertOrUpdateStudyOverallStatusCreate() throws PAException {
        sut = createStudyOverallStatusAction();
        sut.prepareData();
        sut.setCurrentTrialStatus(StudyStatusCode.ACTIVE.getCode());
        String statusDate = TsConverter.convertToString(TsConverter.convertToTs(new Date()));
        sut.setStatusDate(statusDate);
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L)).thenReturn(studyProtocolQueryDTO);
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(studyProtocolDTO);
        StudyOverallStatusDTO statusDto = new StudyOverallStatusDTO();

        sut.insertOrUpdateStudyOverallStatus(statusDto);

        InOrder inOrder = inOrder(protocolQueryService, studyOverallStatusService, studyProtocolService);
        inOrder.verify(protocolQueryService).getTrialSummaryByStudyProtocolId(1L);
        inOrder.verify(studyProtocolService).getStudyProtocol(spIi);
        inOrder.verify(studyOverallStatusService).create(statusDto);
        assertEquals("Wrong study status code", StudyStatusCode.ACTIVE, studyProtocolQueryDTO.getStudyStatusCode());
        assertEquals("Wrong study status date", statusDate,
                     TsConverter.convertToString(TsConverter.convertToTs(studyProtocolQueryDTO.getStudyStatusDate())));
        assertEquals("Wrong study protocol in session", studyProtocolQueryDTO, ServletActionContext.getRequest()
            .getSession().getAttribute(Constants.TRIAL_SUMMARY));
    }

    /**
     * Test the updateStudyProtocol method in the success case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdateStudyProtocol() throws PAException {
        sut = createStudyOverallStatusActionMock();
        doCallRealMethod().when(sut).prepareData();
        doCallRealMethod().when(sut).updateStudyProtocol();
        sut.prepareData();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(studyProtocolDTO);

        sut.updateStudyProtocol();
        
        InOrder inOrder = inOrder(studyProtocolService, sut);
        inOrder.verify(studyProtocolService).getStudyProtocol(spIi);
        inOrder.verify(sut).getStudyProtocolDates(studyProtocolDTO);
        inOrder.verify(studyProtocolService).updateStudyProtocol(studyProtocolDTO);
    }

    /**
     * Test the updateStudyProtocol method in the eception case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdateStudyProtocolException() throws PAException {
        sut = createStudyOverallStatusActionMock();
        doCallRealMethod().when(sut).prepareData();
        doCallRealMethod().when(sut).updateStudyProtocol();
        sut.prepareData();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        when(studyProtocolService.getStudyProtocol(spIi)).thenThrow(new PAException("PAException"));

        sut.updateStudyProtocol();

        verify(studyProtocolService).getStudyProtocol(spIi);
        verify(sut).addActionError("PAException");
    }
    
    @Test
    public void testStudyprotocolWithPrepare() throws PAException {
        sut = new StudyOverallStatusAction();
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryService);
        when(PaRegistry.getStudyOverallStatusService()).thenReturn(studyOverallStatusService);
        when(PaRegistry.getStudyProtocolService()).thenReturn(studyProtocolService);
        sut.prepare();
        StudyProtocolDTO dto = new StudyProtocolDTO();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        dto.setIdentifier(spIi);
        sut.setStartDate("1/1/2013");
        sut.setStartDateType(ActualAnticipatedTypeCode.ACTUAL.toString());
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(dto);

        sut.updateStudyProtocol();

    }
    
    /**
     * Test the loadForm method with no data.
     * 
     * @throws PAException
     *             if an error occurs
     * @throws TooManyResultsException 
     */
    @Test
    public void runTransitionValidationAndInvokeSuAbstractorLogic()
            throws PAException, TooManyResultsException {
        sut = createStudyOverallStatusAction();
        sut.prepareData();
        reset(studyCheckoutService);
        
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(
                super.getSpDto());
        when(studyOverallStatusService.getCurrentByStudyProtocol(spIi))
                .thenReturn(super.getStudyOverallStatusDto());
        when(studyOverallStatusService.statusHistoryHasErrors(eq(getSpDto().getIdentifier()))).thenReturn(true);
        when(studyOverallStatusService.statusHistoryHasWarnings(eq(getSpDto().getIdentifier()))).thenReturn(true);
        
        final MockHttpServletRequest r = (MockHttpServletRequest) ServletActionContext.getRequest();
        r.setUserInRole(Constants.SUABSTRACTOR, true);
        sut.setPaServiceUtils(mock(PAServiceUtils.class));
        
        sut.runTransitionValidationAndInvokeSuAbstractorLogic(getSpDto());
        
        assertTrue(sut.getActionErrors().contains("trialStatus.warningsAndErrors"));
        assertTrue(sut.isDisplaySuAbstractorAutoCheckoutMessage());
        verify(studyCheckoutService).checkOut(eq(spIi),
                eq(CdConverter.convertToCd(CheckOutType.ADMINISTRATIVE)),
                eq(StConverter.convertToSt(r.getRemoteUser())));

    }
    
}