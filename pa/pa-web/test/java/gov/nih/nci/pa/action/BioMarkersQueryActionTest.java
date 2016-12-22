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
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.PermissibleValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.module.sitemesh.tapestry.Util;

/**
 * @author Gaurav Gupta
 */
public class BioMarkersQueryActionTest extends AbstractPaActionTest {
    private BioMarkersQueryAction bioMarkersQueryAction;
    StudyProtocolService studyProtocolService = mock(StudyProtocolService.class);
    PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    PlannedMarkerSyncWithCaDSRServiceLocal permissibleService = mock(PlannedMarkerSyncWithCaDSRServiceLocal.class);
    LookUpTableServiceRemote lookUpTableSrv = mock(LookUpTableServiceRemote.class);
    @Before
    public void setUp() throws Exception {
        bioMarkersQueryAction = new BioMarkersQueryAction();
        bioMarkersQueryAction.prepare();
        bioMarkersQueryAction.setStudyProtocolService(studyProtocolService);
        bioMarkersQueryAction.setPlannedMarkerService(plannedMarkerService);
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setName("Marker #1");
        webDTO.setMeaning("Marker #1");
        webDTO.setStatus("PENDING");
        webDTO.setId(1L);
        List<PlannedMarkerWebDTO> plannedMarkerWebDTOs = new ArrayList<PlannedMarkerWebDTO>();
        plannedMarkerWebDTOs.add(webDTO);
        CSMUserService.setInstance(new MockCSMUserService());
        bioMarkersQueryAction.setPlannedMarkerList(plannedMarkerWebDTOs);
        
    }

    @Test
    public void testExecute() throws PAException {
        Map<Long, String> identifierMap = new HashMap<Long, String>();
        List<Long> identifiersList = new ArrayList<Long>();
        identifierMap.put(123456L, "NCI-2012-00260");
        identifierMap.put(123457L, "NCI-2012-00261");
        identifiersList.add(123456L);
        identifiersList.add(123457L);
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setLongName(StConverter.convertToSt("Marker #1"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.PENDING));
        dto.setUserLastCreated(StConverter.convertToSt("1"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(123457L));
        List<PlannedMarkerDTO> dtos = new ArrayList<PlannedMarkerDTO>();
        dtos.add(dto);
        Map<Long, String> statusMap = new HashMap<Long, String>();
        statusMap.put(123456L, "REJECTED");
        statusMap.put(123457L, "VERIFICATION_PENDING");
        when(plannedMarkerService.getPlannedMarkers()).thenReturn(dtos);
        when(studyProtocolService.getTrialNciId((List<Long>) any(Util.class))).thenReturn(identifierMap);
        when(studyProtocolService.getTrialProcessingStatus((List<Long>) any(Util.class))).thenReturn(statusMap);
        assertEquals(bioMarkersQueryAction.execute(), "success");
        assertNotNull(bioMarkersQueryAction.getPlannedMarkerList());
        assertEquals(1, bioMarkersQueryAction.getPlannedMarkerList().size());
        statusMap.clear();
        statusMap.put(123456L, "REJECTED");
        statusMap.put(123457L, "SUBMISSION_TERMINATED");
        when(plannedMarkerService.getPlannedMarkers()).thenReturn(dtos);
        when(studyProtocolService.getTrialNciId((List<Long>) any(Util.class))).thenReturn(identifierMap);
        when(studyProtocolService.getTrialProcessingStatus((List<Long>) any(Util.class))).thenReturn(statusMap);
        assertEquals(bioMarkersQueryAction.execute(), "success");
        assertTrue(bioMarkersQueryAction.getPlannedMarkerList().isEmpty());
        assertEquals(0, bioMarkersQueryAction.getPlannedMarkerList().size());
    }

    @Test
    public void testEdit() throws PAException{
        bioMarkersQueryAction.setSelectedRowIdentifier("1");
        assertEquals(bioMarkersQueryAction.edit(), "edit");
    }

    @Test
    public void testSendQuestion() throws PAException{
        bioMarkersQueryAction.setSelectedRowIdentifier("1");
        assertEquals(bioMarkersQueryAction.sendQuestion(), "question");
    }

    @Test
    public void testSendQuestionMail() throws PAException{
        assertEquals(bioMarkersQueryAction.sendQuestionMail(), "success");
    }

    @Test
    public void testUpdate() throws PAException {
        PlannedMarkerDTO marker = new PlannedMarkerDTO();
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setName("Marker #1");
        webDTO.setMeaning("Marker #1");
        webDTO.setStatus("PENDING");
        webDTO.setId(1L);
        marker.setName(StConverter.convertToSt("Marker #1"));
        marker.setLongName(StConverter.convertToSt("Marker #1"));
        marker.setStudyProtocolIdentifier(IiConverter.convertToIi(123456L));
        marker.setIdentifier(IiConverter.convertToIi(1L));
        bioMarkersQueryAction.setPlannedMarker(webDTO);
        when(plannedMarkerService.get(IiConverter.convertToIi(1L))).thenReturn(marker);
        assertEquals(bioMarkersQueryAction.update(), "success");
    }

    @Test
    public void testAccept() throws Exception {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        bioMarkersQueryAction.setCaDsrId("1");
        bioMarkersQueryAction.setSelectedRowIdentifier("1");
        getSession().setAttribute(Constants.LOGGED_USER_NAME, "login1");
        EnumeratedValueDomain vd = new EnumeratedValueDomain();
        vd.setId("1");
        DataElement de = new DataElement();
        de.setValueDomain(vd);
        List<Object> deResults = new ArrayList<Object>();
        deResults.add(de);
        ApplicationService appService = mock(ApplicationService.class);
        when(appService.search(eq(DataElement.class), any(DataElement.class))).thenReturn(deResults);
        PermissibleValue pv = new PermissibleValue();
        pv.setValue("N-Cadherin");
        ValueMeaning vm = new ValueMeaning();
        vm.setLongName("N-Cadherin");
        vm.setDescription("cadherin");
        vm.setPublicID(2578250L);
        pv.setValueMeaning(vm);
        ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
        vdpv.setPermissibleValue(pv);
        vdpv.setId("1");
        List<Object> results = new ArrayList<Object>();
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(results);
        bioMarkersQueryAction.setLookUpTableService(lookUpTableSrv);
        when(lookUpTableSrv.getPropertyValue("CDE_PUBLIC_ID")).thenReturn("5473");
        when(lookUpTableSrv.getPropertyValue("Latest_Version_Indicator")).thenReturn("Yes");
        when(lookUpTableSrv.getPropertyValue("CDE_Version")).thenReturn("9.0");
        bioMarkersQueryAction.setAppService(appService);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        assertEquals(bioMarkersQueryAction.accept(), "success");
        bioMarkersQueryAction.setPermissibleService(permissibleService);
        List<Number> idList = new ArrayList<Number>();
        idList.add(1);
        when(permissibleService.getIdentifierByCadsrId(Long.parseLong("1"))).thenReturn(idList);
        bioMarkersQueryAction.setAppService(appService);
        assertEquals(bioMarkersQueryAction.accept(), "success");
    }
    

    @Test
    public void testSearch() throws PAException{
        Map<Long, String> identifierMap = new HashMap<Long, String>();
        List<Long> identifiersList = new ArrayList<Long>();
        List<PlannedMarkerDTO> markers = new ArrayList<PlannedMarkerDTO>();
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("name"));
        dto.setLongName(StConverter.convertToSt("LongName"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(123L));
        markers.add(dto);
        identifierMap.put(123456L, "NCI-2012-00260");
        identifierMap.put(123457L, "NCI-2012-00261");
        identifiersList.add(123456L);
        identifiersList.add(123457L);
        Map<Long, String> statusMap = new HashMap<Long, String>();
        statusMap.put(123456L, "REJECTED");
        statusMap.put(123457L, "VERIFICATION_PENDING");
        when(plannedMarkerService.getPendingPlannedMarkersShortNameAndNCIId("NCI", "NCI-2012-00260")).thenReturn(markers);
        when(studyProtocolService.getTrialNciId(identifiersList)).thenReturn(identifierMap);
        when(studyProtocolService.getTrialProcessingStatus(identifiersList)).thenReturn(statusMap);
        assertEquals(bioMarkersQueryAction.search(), "success");
        assertNotNull(bioMarkersQueryAction.getPlannedMarkerList());   
    }

    @Test
    public void testSearchElse() throws PAException{
        Map<Long, String> identifierMap = new HashMap<Long, String>();
        List<Long> identifiersList = new ArrayList<Long>();
        List<PlannedMarkerDTO> markers = new ArrayList<PlannedMarkerDTO>();
        bioMarkersQueryAction.setTrialId("NCI-2012-00260");
        bioMarkersQueryAction.setMarkerName("Marker #1");
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setLongName(StConverter.convertToSt("Marker #1"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(123456L));
        PlannedMarkerDTO dto1 = new PlannedMarkerDTO();
        dto1.setIdentifier(IiConverter.convertToIi(2L));
        dto1.setName(StConverter.convertToSt("Marker #2"));
        dto1.setLongName(StConverter.convertToSt("Marker #2"));
        dto1.setStudyProtocolIdentifier(IiConverter.convertToIi(123457L));
        dto1.setDateLastCreated(TsConverter.convertToTs(new Date()));
        markers.add(dto);
        markers.add(dto1);
        identifierMap.put(123456L, "NCI-2012-00260");
        identifierMap.put(123457L, "NCI-2012-00261");
        identifiersList.add(123456L);
        identifiersList.add(123457L);
        Map<Long, String> statusMap = new HashMap<Long, String>();
        statusMap.put(123456L, "PENDING");
        when(plannedMarkerService.getPendingPlannedMarkersShortNameAndNCIId("Marker #1", "NCI-2012-00260")).thenReturn(markers);
        when(studyProtocolService.getTrialNciId(identifiersList)).thenReturn(identifierMap);
        when(studyProtocolService.getTrialProcessingStatus(identifiersList)).thenReturn(statusMap);
        assertEquals(bioMarkersQueryAction.search(), "success");
        assertNotNull(bioMarkersQueryAction.getPlannedMarkerList());   
    }
    
    @Test
    public void testSaveFile() throws PAException {
        String result = bioMarkersQueryAction.saveFile();
        assertEquals("none",result);
        HttpServletResponse response = ServletActionContext.getResponse();
        assertEquals("application/octet-stream", response.getContentType());
        assertEquals("ISO-8859-1", response.getCharacterEncoding());
    }

}
