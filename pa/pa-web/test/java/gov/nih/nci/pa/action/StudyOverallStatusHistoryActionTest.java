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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;
import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.audit.AuditType;
import com.google.gson.Gson;
/**
 * @author Michael Visee
 */
public class StudyOverallStatusHistoryActionTest extends AbstractPaActionTest {

    private StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
    private StudyProtocolServiceLocal studyProtocolServiceLocal = mock(StudyProtocolServiceLocal.class);
    private AuditTrailServiceLocal auditTrailService = mock(AuditTrailServiceLocal.class);
    
    private StudyOverallStatusHistoryAction sut;


    /**
     * Creates a real StudyOverallStatusHistoryAction and inject the mock services in it.
     * @return A real StudyOverallStatusHistoryAction with mock services injected.
     */
    private StudyOverallStatusHistoryAction createStudyOverallStatusHistoryAction() {
        StudyOverallStatusHistoryAction action = new StudyOverallStatusHistoryAction();
        setDependencies(action);
        return action;
    }

    /**
     * Inject the mock services in the given StudyOverallStatusHistoryAction.
     * @param action The StudyOverallStatusHistoryAction to setup with mock services
     */
    private void setDependencies(StudyOverallStatusHistoryAction action) {
        action.setStudyOverallStatusService(studyOverallStatusService);
        action.setStudyProtocolServiceLocal(studyProtocolServiceLocal);
        action.setAuditTrailService(auditTrailService);
        
    }
    
    @Test
    public void testSort() throws PAException {
        sut = createStudyOverallStatusHistoryAction();
        Set<AuditLogDetail> details = new LinkedHashSet<>();
        
        AuditLogDetail d1 = new AuditLogDetail(null, "deleted", "true", "false");
        details.add(d1);
        AuditLogDetail d2 = new AuditLogDetail(null, "additionalComments", "", "");
        details.add(d2);
        AuditLogDetail d3 = new AuditLogDetail(null, "reason", "", "");
        details.add(d3);
        
        Collection<AuditLogDetail> sorted = sut.sort(details);
        Iterator<AuditLogDetail> it = sorted.iterator();
        assertEquals(d2, it.next());
        assertEquals(d1, it.next());
        assertEquals(d3, it.next());
        
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testGetAuditTrail() throws PAException, JSONException,
            NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException, IOException {
        sut = createStudyOverallStatusHistoryAction();
        sut.setStatusId(1L);

        final Date today = DateUtils.truncate(new Date(), Calendar.DATE);
        final Date yday = DateUtils.truncate(DateUtils.addDays(new Date(), -1),
                Calendar.DATE);
        AuditLogRecord r = new AuditLogRecord(AuditType.INSERT,
                "StudyOverallStatus", 1L, "username", today);

        AuditLogDetail commentText = new AuditLogDetail(r, "commentText",
                "Comment Before", "Comment After");
        AuditLogDetail additionalComments = new AuditLogDetail(r,
                "additionalComments", "Comment Before", "Comment After");
        AuditLogDetail statusCode = new AuditLogDetail(r, "statusCode",
                "IN_REVIEW", "APPROVED");
        AuditLogDetail statusDate = new AuditLogDetail(r, "statusDate",
                yday.toString(), today.toString());
        AuditLogDetail deleted = new AuditLogDetail(r, "deleted", "false",
                "true");

        r.getDetails().add(commentText);
        r.getDetails().add(additionalComments);
        r.getDetails().add(statusCode);
        r.getDetails().add(statusDate);
        r.getDetails().add(deleted);

        when(
                auditTrailService.getAuditTrail(eq(StudyOverallStatus.class),
                        eq(IiConverter.convertToIi(1L)))).thenReturn(
                Arrays.asList(r));

        StreamResult result = sut.getAuditTrail();
        final Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);
        InputStream is = (InputStream) field.get(result);
        String json = IOUtils.toString(is);
        Gson gson = new Gson();
        final Map fromJson = (Map) gson.fromJson(json, Object.class);
        List data = (List) fromJson.get("data");
        List record = (List) data.get(0);

        assertEquals(DateFormatUtils.format(today, "MM/dd/yyyy HH:mm"),
                record.get(0));
        assertEquals(CsmUserUtil.getGridIdentityUsername("username"),
                record.get(1));
        assertEquals("Insert", record.get(2));
        assertEquals(
                "<table><thead><tr><th>Attribute</th><th>Old Value</th><th>New Value</th></tr></thead>"
                        + "<tr><td>Comments</td><td>Comment Before</td><td>Comment After</td></tr>"
                        + "<tr><td>Why Study Stopped</td><td>Comment Before</td><td>Comment After</td></tr>"
                        + "<tr><td>Deleted</td><td>false</td><td>true</td></tr>"
                        + "<tr><td>Status</td><td>In Review</td><td>Approved</td></tr>"
                        + "<tr><td>Status Date</td><td>"
                        + yday
                        + "</td><td>"
                        + today + "</td></tr></table>", record.get(3));

    }

    /**
     * Test the execute method.
     * @throws PAException if an error occurs.
     */
    @Test
    public void testExecute() throws PAException {
        sut = createStudyOverallStatusHistoryAction();
        sut.setStudyProtocolId(1L);
        Ii spIi = IiConverter.convertToStudyProtocolIi(sut.getStudyProtocolId());
        List<StudyOverallStatusDTO> statuses = createStatuses();
        when(studyOverallStatusService.getByStudyProtocol(spIi)).thenReturn(statuses);
        String result = sut.execute();
        assertEquals("Wrong result returned", "success", result);
        List<StudyOverallStatusWebDTO> webDTOs = sut.getOverallStatusList();
        assertNotNull("No list returned", webDTOs);
        assertEquals("Wrong result size", 1, webDTOs.size());
        StudyOverallStatusDTO status = statuses.get(0);
        StudyOverallStatusWebDTO webDTO = webDTOs.get(0);
        assertEquals("Wrong status code", StudyStatusCode.IN_REVIEW.getCode(), webDTO.getStatusCode());
        assertEquals("Wrong status date", TsConverter.convertToString(status.getStatusDate()), webDTO.getStatusDate());
        assertEquals("Wrong status reason", StConverter.convertToString(status.getReasonText()), webDTO.getReason());
    }

    private List<StudyOverallStatusDTO> createStatuses() {
        List<StudyOverallStatusDTO> statuses = new ArrayList<StudyOverallStatusDTO>();
        StudyOverallStatusDTO status = new StudyOverallStatusDTO();
        status.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        status.setStatusDate(TsConverter.convertToTs(new Date()));
        status.setReasonText(StConverter.convertToSt("reason"));
        status.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
        statuses.add(status);
        return statuses;
    }
    
    @Test
    public void testDelete() throws PAException {
        ServletActionContext.getRequest().getSession().setAttribute(Constants.IS_SU_ABSTRACTOR, true);
        sut = createStudyOverallStatusHistoryAction();
        sut.setStudyProtocolId(1L);
        sut.setStatusId(1L);
        Ii spIi = IiConverter.convertToStudyProtocolIi(sut.getStudyProtocolId());
        List<StudyOverallStatusDTO> statuses = createStatuses();
        when(studyOverallStatusService.getByStudyProtocol(spIi)).thenReturn(statuses);
        when(studyOverallStatusService.get(any(Ii.class))).thenReturn(statuses.get(0));
        String result = sut.delete();
        assertEquals("success", result);
        assertTrue(sut.isChangesMadeFlag());
        assertTrue(sut.getOverallStatusList().size() > 0);
        assertTrue(sut.getOverallStatusList().get(0).isEditable());
        assertFalse(sut.getOverallStatusList().get(0).isSystemCreated());
        assertEquals("Record Deleted", ServletActionContext.getRequest().getAttribute("successMessage"));
        
    }
   
    
    @Test
    public void testSave() throws PAException {
        ServletActionContext.getRequest().getSession().setAttribute(Constants.IS_SU_ABSTRACTOR, true);
    	ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getStudyOverallStatusService()).thenReturn(studyOverallStatusService);
        when(PaRegistry.getStudyProtocolService()).thenReturn(studyProtocolServiceLocal);
        sut = new StudyOverallStatusHistoryAction();
        sut.prepare();
        sut.setStatusId(1L);
        sut.setStudyProtocolId(1L);
        Ii spIi = IiConverter.convertToStudyProtocolIi(sut.getStudyProtocolId());
        List<StudyOverallStatusDTO> statuses = createStatuses();
        when(studyOverallStatusService.getByStudyProtocol(spIi)).thenReturn(statuses);
        when(studyOverallStatusService.getByStudyProtocolWithTransitionValidations(spIi)).thenReturn(statuses);
        when(studyOverallStatusService.get(spIi)).thenReturn(statuses.get(0));
        String result = sut.save();
        assertEquals("success", result);
        assertEquals("studyOverallStatus.edit.invalidDate", ServletActionContext.getRequest().getAttribute("failureMessage"));
        
        sut.setStatusDate("12/10/2012");
        sut.setReason("reasonUpdate");
        sut.setStatusCode("Active");
        StudyOverallStatusDTO dto = statuses.get(0);
        dto.setIdentifier(spIi);
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setIdentifier(spIi);
        when(studyOverallStatusService
                .getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dto);
        when(studyProtocolServiceLocal.getStudyProtocol(any(Ii.class))).thenReturn(studyProtocolDTO);
        result = sut.save();
        assertEquals("success", result);
        assertEquals("Active", sut.getOverallStatusList().get(0).getStatusCode());
        assertEquals("reasonUpdate", sut.getOverallStatusList().get(0).getReason());
    }
}
