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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceBean;
import gov.nih.nci.pa.service.StudyIndldeBeanLocal;
import gov.nih.nci.pa.service.StudyMilestoneBeanLocal;
import gov.nih.nci.pa.service.StudyOnholdBeanLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityBeanLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author Michael Visee
 */
public class StudyMilestoneTasksServiceBeanIntegrationTest extends AbstractHibernateTestCase {
    /**
     * Test method the perform task method
     * @throws Exception if an error occurs
     */
    @Test
    public void testPerformTaskFull() throws Exception {
        AbstractMockitoTest mockitoTest = new AbstractMockitoTest();
        mockitoTest.setUp();
        StudyMilestoneBeanLocal result = new StudyMilestoneBeanLocal();
        StudyMilestoneTasksServiceBean taskBean = new StudyMilestoneTasksServiceBean();
        taskBean.setStudyMilestoneService(result);
        taskBean.setStudyMilestoneTasksService(taskBean);
        result.setStudyOnholdService(new StudyOnholdBeanLocal());
        result.setStudyInboxService(new StudyInboxServiceBean());
        AbstractionCompletionServiceBean abstractionBean = new AbstractionCompletionServiceBean();
        abstractionBean.setPlannedMarkerService(mockitoTest.getPlannedMarkerSvc());
        result.setAbstractionCompletionService(abstractionBean);
        abstractionBean.setStudyProtocolService(new StudyProtocolBeanLocal());
        abstractionBean.setStudySiteService(new StudySiteBeanLocal());
        abstractionBean.setStudyRegulatoryAuthorityService(new StudyRegulatoryAuthorityBeanLocal());
        abstractionBean.setStudyOverallStatusService(new StudyOverallStatusBeanLocal());
        abstractionBean.setStudyIndldeService(new StudyIndldeBeanLocal());
        abstractionBean.setRegulatoryInformationService(new RegulatoryInformationBean());
        StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = mock(StudyOutcomeMeasureServiceLocal.class);
        abstractionBean.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        StudyOutcomeMeasureDTO outcomeDto = new StudyOutcomeMeasureDTO();
        outcomeDto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        when(studyOutcomeMeasureService.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(outcomeDto));
        DocumentServiceLocal documentServiceLocal = mock(DocumentServiceLocal.class);
        abstractionBean.setDocumentService(documentServiceLocal);
        when(documentServiceLocal.getDocumentsByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<DocumentDTO>());
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PlannedActivityServiceLocal plannedActivityService = mock(PlannedActivityServiceLocal.class);
        PlannedActivityDTO plannedDTO = new PlannedActivityDTO();
        plannedDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        plannedDTO.setIdentifier(IiConverter.convertToIi("1"));
        abstractionBean.setPlannedActivityService(plannedActivityService);
        InterventionServiceLocal interventionSvc = mock(InterventionServiceLocal.class);
        abstractionBean.setInterventionService(interventionSvc);
        InterventionDTO interventionDto = new InterventionDTO();
        interventionDto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        when(interventionSvc.get(any(Ii.class))).thenReturn(interventionDto);
        when(plannedActivityService.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(plannedDTO));
        DocumentWorkflowStatusServiceLocal mockDocWrkBean = mock(DocumentWorkflowStatusServiceLocal.class);
        DocumentWorkflowStatusDTO dw = new DocumentWorkflowStatusDTO();
        dw.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.VERIFICATION_PENDING));
        when(mockDocWrkBean.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dw);
        when(paRegSvcLoc.getDocumentWorkflowStatusService()).thenReturn(mockDocWrkBean);
        OrganizationCorrelationServiceRemote orgCorrBean = mock(OrganizationCorrelationServiceRemote.class);
        when(paRegSvcLoc.getOrganizationCorrelationService()).thenReturn(orgCorrBean);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        StudyContactServiceLocal studyContactService = mock(StudyContactServiceLocal.class);
        abstractionBean.setStudyContactService(studyContactService);
        when(studyContactService.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyContactDTO>());
        StudySiteContactServiceLocal studySiteContactService = mock(StudySiteContactServiceLocal.class);
        abstractionBean.setStudySiteContactService(studySiteContactService);
        when(studySiteContactService.getByStudyProtocol(any(Ii.class)))
            .thenReturn(new ArrayList<StudySiteContactDTO>());
        ArmServiceLocal armService = mock(ArmServiceLocal.class);
        abstractionBean.setArmService(armService);
        ArmDTO armDto = new ArmDTO();
        armDto.setName(StConverter.convertToSt("arm Name"));
        armDto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.NO_INTERVENTION));
        when(armService.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(armDto));
        abstractionBean.setStudyResourcingService(mock(StudyResourcingServiceLocal.class));
        abstractionBean.setStudyDiseaseService(mock(StudyDiseaseServiceLocal.class));
        StudyRecruitmentStatusServiceLocal mockStudyRecruitBean = mock(StudyRecruitmentStatusServiceLocal.class);
        StudyRecruitmentStatusDTO recruitDto = new StudyRecruitmentStatusDTO();
        recruitDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.IN_REVIEW));
        when(mockStudyRecruitBean.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(recruitDto);
        when(mockStudyRecruitBean.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(recruitDto));        
        when(paRegSvcLoc.getLookUpTableService()).thenReturn(mockitoTest.getLookupSvc());
        when(mockitoTest.getLookupSvc().getPropertyValue(anyString())).thenReturn("");
        taskBean.performTask();
    }
}
