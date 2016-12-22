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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialHistoryWebDTO;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyInboxSectionCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.AuditTrailCode;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;
import com.opensymphony.xwork2.Action;

/**
 * @author Anupama Sharma
 */
public class TrialHistoryActionTest extends AbstractPaActionTest {
    private TrialHistoryAction trialHistory;

    private final StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private final DocumentServiceLocal documentService = mock(DocumentServiceLocal.class);
    private final DocumentWorkflowStatusServiceLocal documentWorkflowStatusServiceLocal = mock(DocumentWorkflowStatusServiceLocal.class);
    private final StudyInboxServiceLocal studyInboxService = mock(StudyInboxServiceLocal.class);
    private final ServiceLocator serviceLoc = mock(ServiceLocator.class);

    @Before
    public void setup() throws Exception {
        trialHistory = new TrialHistoryAction();
        trialHistory.setNciID("1");
        trialHistory.prepare();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        setupMocks();
     }

    @Test
    public void testLoadListForm() throws Exception {
        // select from main menu
        trialHistory.loadListForm();

    }
    @Test
    public void testLoadEditForm() throws Exception {
    	trialHistory.setCurrentAction("create");
    	trialHistory.setSelectedRowIdentifier("1");
        // select from main menu
        trialHistory.loadEditForm();
        
        trialHistory.setCurrentAction("edit");
    	trialHistory.setSelectedRowIdentifier("1");
    	DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
    	dto.setIdentifier(IiConverter.convertToIi(1L));
        when(documentWorkflowStatusServiceLocal.getInitialStatus(any(Ii.class))).thenReturn(dto);
        trialHistory.loadEditForm();

    }
    @Test
    public void testOpen() throws Exception {
    	trialHistory.setDocii("1");
    	trialHistory.setStudyProtocolii("1");
    	trialHistory.setServletResponse(getResponse());
    	assertEquals("none",trialHistory.open());
    }

    @Test
    public void testUpdate() throws Exception {
    	trialHistory.setDocii("1");
    	trialHistory.setStudyProtocolii("1");
    	trialHistory.setServletResponse(getResponse());
    	TrialHistoryWebDTO webDTO = new TrialHistoryWebDTO();
    	trialHistory.setTrialHistoryWbDto(webDTO);
    	assertEquals("edit",trialHistory.update());
    }

    @Test
    public void testUpdateNoFieldErrors() throws Exception {
        PaRegistry.getInstance().setServiceLocator(serviceLoc);

    	trialHistory.setDocii("1");
    	trialHistory.setStudyProtocolii("1");
    	trialHistory.setServletResponse(getResponse());
    	TrialHistoryWebDTO webDTO = new TrialHistoryWebDTO();
    	webDTO.setAmendmentDate("12/21/2009");
    	webDTO.setIdentifier("1");
    	webDTO.setAmendmentReasonCode(AmendmentReasonCode.ADMINISTRATIVE.getCode());
    	trialHistory.setTrialHistoryWbDto(webDTO);
    	assertEquals("list",trialHistory.update());
    }

    @Test
    public void updateTest() throws Exception {
        PaRegistry.getInstance().setServiceLocator(serviceLoc);
        assertEquals(AbstractListEditAction.AR_LIST, trialHistory.execute());

        assertEquals(4, trialHistory.getTrialHistoryWebDTO().size());
    }

    @Test
    public void testTrialUpdate() throws PAException, TooManyResultsException {
        PaRegistry.getInstance().setServiceLocator(serviceLoc);
        assertEquals("list", trialHistory.execute());
    }

    private void setupMocks() throws PAException, TooManyResultsException {
        trialHistory.setDocumentService(documentService);
        trialHistory.setStudyInboxService(studyInboxService);
        trialHistory.setStudyProtocolService(studyProtocolService);
        trialHistory.setDocumentWorkflowStatusServiceLocal(documentWorkflowStatusServiceLocal);
        
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        spDto.setPhaseCode(CdConverter.convertStringToCd(PhaseCode.NA.getCode()));
        spDto.setSubmissionNumber(IntConverter.convertToInt(1));
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(spDto);

        List<StudyProtocolDTO> trialUpdates = new ArrayList<StudyProtocolDTO>();
        spDto = new StudyProtocolDTO();
        spDto.setOfficialTitle(StConverter.convertToSt("NCI-2010-001"));
        spDto.setIdentifier(IiConverter.convertToIi(1L));
        spDto.setSubmissionNumber(IntConverter.convertToInt("1"));
        spDto.setAmendmentNumber(StConverter.convertToSt("11"));
        spDto.setAmendmentReasonCode(CdConverter.convertStringToCd("ReasonCode1"));
        spDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.I_II));
        trialUpdates.add(spDto);

        spDto = new StudyProtocolDTO();
        spDto.setOfficialTitle(StConverter.convertToSt("NCI-2010-001"));
        spDto.setIdentifier(IiConverter.convertToIi(1L));
        spDto.setSubmissionNumber(IntConverter.convertToInt("2"));
        spDto.setAmendmentNumber(StConverter.convertToSt("12"));
        spDto.setAmendmentReasonCode(CdConverter.convertStringToCd("ReasonCode2"));
        spDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.I_II));
        trialUpdates.add(spDto);
        when(studyProtocolService.search(any(StudyProtocolDTO.class), any(LimitOffset.class))).thenReturn(trialUpdates);

        List<DocumentDTO> docs = new ArrayList<DocumentDTO>();
        DocumentDTO docDto = new DocumentDTO();
        docDto.setStudyProtocolIdentifier(spDto.getIdentifier());
        docDto.setIdentifier(IiConverter.convertToIi("docII"));
        docDto.setFileName(StConverter.convertToSt("file name"));
        docDto.setTypeCode(CdConverter.convertStringToCd("doc code"));
        docDto.setText(EdConverter.convertToEd("Document Text".getBytes()));
        docs.add(docDto);

        when(documentService.get(any(Ii.class))).thenReturn(docDto);
        when(documentService.getDocumentsByStudyProtocol(any(Ii.class))).thenReturn(docs);
        when(documentService.
                getOriginalDocumentsByStudyProtocol(any(Ii.class))).thenReturn(docs);

        StudyInboxDTO siDto = new StudyInboxDTO();
        Ivl<Ts> ivlTs = new Ivl<Ts>();
        ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
        siDto.setInboxDateRange(ivlTs);
        when(studyInboxService.get(any(Ii.class))).thenReturn(siDto);
        when(studyInboxService.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyInboxDTO>());
        when(studyInboxService.update(any(StudyInboxDTO.class))).thenThrow(new PAException("ERROR!!!"));
        when(serviceLoc.getStudyInboxService()).thenReturn(studyInboxService);

        ProtocolQueryServiceLocal protocolQSvc = mock(ProtocolQueryServiceLocal.class);
        when(protocolQSvc.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
            .thenReturn(new ArrayList<StudyProtocolQueryDTO>());

        when(serviceLoc.getProtocolQueryService()).thenReturn(protocolQSvc);

    }

    @Test
    public void testGetters() {
        trialHistory.setDocFileName("docname");
        assertEquals("docname", trialHistory.getDocFileName());

        trialHistory.getServletResponse();
        trialHistory.setDocii("docII");
        assertEquals("docII", trialHistory.getDocii());
    }

    @Test
    public void testAcceptUpdate() throws PAException {
        HttpServletRequest httpReq = mock(HttpServletRequest.class);        
        when(httpReq.getParameter("studyInboxId")).thenReturn("123");
        when(httpReq.getParameter("updateType")).thenReturn("Both");
        when(httpReq.getSession()).thenReturn(getSession());

        ServletActionContext.setRequest(httpReq);

        PaRegistry.getInstance().setServiceLocator(serviceLoc);

        assertEquals("list", trialHistory.acceptUpdate());
        verify(studyInboxService).acknowledge(eq(IiConverter.convertToIi(123L)), eq(StudyInboxSectionCode.BOTH));
    }
    
    
    @Test(expected=UnsupportedOperationException.class)
    public void testDeleteObject() throws PAException {
        trialHistory.deleteObject(1l);
    }
    
    @Test
    public void testView() throws Exception {
    	trialHistory.clearActionErrors();
    	trialHistory.setAuditTrailCode(AuditTrailCode.MARKERS);
        assertEquals(TrialHistoryAction.AR_LIST, trialHistory.view());
        assertNotNull(trialHistory.getAuditTrail());
        assertEquals(1, trialHistory.getAuditTrail().size());
        AuditLogDetail detail = trialHistory.getAuditTrail().iterator().next();
        assertEquals("PLANNED_MARKER", detail.getRecord().getEntityName());
        assertEquals("name", detail.getAttribute());
        assertEquals("name", detail.getNewValue());
    	trialHistory.clearActionErrors();
        trialHistory.setAuditTrailCode(AuditTrailCode.NCI_SPECIFIC_INFORMATION);
        List<StudyInboxDTO> inboxEntries = new ArrayList<StudyInboxDTO>();
        StudyInboxDTO dto = new StudyInboxDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setComments(StConverter.convertToSt("comments"));
        Ivl<Ts> ivlTs = new Ivl<Ts>();
        ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
        dto.setInboxDateRange(ivlTs);
        dto.setAdmin(BlConverter.convertToBl(true));
        dto.setScientific(BlConverter.convertToBl(false));
        dto.setScientificCloseDate(TsConverter.convertToTs(new Date()));
        dto.setAdminCloseDate(TsConverter.convertToTs(new Date()));
        inboxEntries.add(dto);
        when(studyInboxService.getOpenInboxEntries(any(Ii.class))).thenReturn(inboxEntries);
        assertEquals(TrialHistoryAction.AR_LIST, trialHistory.view());
        assertNotNull(trialHistory.getAuditTrail());
        assertEquals(1, trialHistory.getAuditTrail().size());
        detail = trialHistory.getAuditTrail().iterator().next();
        assertEquals("STUDY_RESOURCING", detail.getRecord().getEntityName());
        assertEquals("programCodeText", detail.getAttribute());
        assertEquals("programCode", detail.getNewValue());
    }
}
