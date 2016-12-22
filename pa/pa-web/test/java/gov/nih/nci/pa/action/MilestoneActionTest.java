/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.MilestoneDTO;
import gov.nih.nci.pa.dto.MilestoneWebDTO;
import gov.nih.nci.pa.dto.MilestonesDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.DocumentWorkflowStatusService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.service.MockStudyProtocolService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Vrushali
 *
 */
public class MilestoneActionTest extends AbstractPaActionTest {
    MilestoneAction action;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    StudyProtocolServiceLocal studyProtocolService;
    DocumentWorkflowStatusService documentWorkflowStatusService;
    RegistryUserServiceLocal registryUserService;
    ProtocolQueryServiceLocal protocolQueryService;
    
    @Before
    public void prepare() throws PAException {
        action = new MilestoneAction();
        studyProtocolService = mock(StudyProtocolServiceLocal.class);
    }

    @After
    public void tearDown() {
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void testMilestoneProperty() {
        assertNull(action.getMilestone());
        action.setMilestone(new MilestoneWebDTO());
        assertNotNull(action.getMilestone());
    }

    @Test
    public void testMilestoneListProperty() {
        assertNull(action.getMilestoneList());
        action.setMilestoneList(new ArrayList<MilestoneWebDTO>());
        assertNotNull(action.getMilestoneList());
    }

    @Test
    public void testInitiliazeAddForm() throws PAException {
        action.initiliazeAddForm();
        assertNotNull(action.getMilestone());
        assertNotNull(action.getMilestone().getDate());
    }

    @Test
    public void testinitiliazeListForm() throws PAException {
        setUpAmendmentSearch();
        action.initiliazeListForm();
        assertNotNull("No milestone list", action.getMilestoneList());
        assertNotNull("No amendment map", action.getAmendmentMap());
        assertEquals("Wrong size of amendment map", 1, action.getAmendmentMap().size());
    }

    @Test
    public void testinitiliazeListFormAmend() throws PAException, TooManyResultsException {
        studyProtocolService = mock(StudyProtocolServiceLocal.class);
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
        action.setStudyProtocolService(studyProtocolService);
        Int n = new Int();
        n.setValue(1);
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        spDto.setPhaseCode(CdConverter.convertStringToCd(PhaseCode.NA.getCode()));
        StudyProtocolDTO spDTO1 = new StudyProtocolDTO();
        spDTO1.setIdentifier(IiConverter.convertToIi(2L));
        spDTO1.setOfficialTitle(StConverter.convertToSt("NCI-001-0002"));
        spDTO1.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
        spDTO1.setSubmissionNumber(IntConverter.convertToInt(1));
        List<StudyProtocolDTO> spList = new ArrayList<StudyProtocolDTO>();
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setIdentifier(IiConverter.convertToIi(1L));
        spDTO.setOfficialTitle(StConverter.convertToSt("NCI-001-0001"));
        n.setValue(1);
        spDTO.setSubmissionNumber(IntConverter.convertToInt(2));
        spList.add(spDTO1);
        when(studyProtocolService.getStudyProtocol(action.getSpIi())).thenReturn(spDTO1);
        when(studyProtocolService.search(any(StudyProtocolDTO.class), any(LimitOffset.class))).thenReturn(spList);
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        when(studyProtocolService.
                getActiveAndInActiveTrialsByspId(1L)).thenReturn(ids);
        when(studyProtocolService.getStudyProtocol(IiConverter.convertToIi(1L))).thenReturn(spDTO);
        action.initiliazeListForm();
        assertNotNull("No milestone list", action.getMilestoneList());
        assertNotNull("No amendment map", action.getAmendmentMap());
        assertEquals("Wrong size of amendment map", 2, action.getAmendmentMap().size());
    }
    @Test
    public void testLoadListFormWithRejectedStatus() throws PAException {
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
        action.initiliazeListForm();
        assertNotNull("No milestone list", action.getMilestoneList());
        assertNotNull("No amendment map", action.getAmendmentMap());
        assertEquals("Wrong size of amendment map", 0, action.getAmendmentMap().size());
    }

    @Test
    public void testAdd() throws PAException {
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06-19-2009");
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        setUpAmendmentSearch();
        String result = action.add();
        assertEquals("Wrong result from add action", "success", result);
    }

    @Test
    public void testDateCheckPastDate() throws PAException {
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06-19-2009");
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        setUpDateCheckForTodayOnMilestone();
        setUpAmendmentSearch();
        action.add();
        assertEquals("date does not match", action.getActionErrors().iterator().next());
    }

    @Test
    public void testDateCheckCurrentDate() throws PAException {
        Long now = DateTimeUtils.currentTimeMillis();
        DateTimeUtils.setCurrentMillisFixed(now);
        String clientDate = sdf.format(new Timestamp(now));
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate(clientDate);
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        setUpDateCheckForTodayOnMilestone();
        setUpAmendmentSearch();
        String result = action.add();
        assertEquals("Wrong result from add action", "success", result);
    }

    private void setUpAmendmentSearch() {
        action.setStudyProtocolService(new MockStudyProtocolService() {
            @Override
            public List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams) throws PAException {
                List<StudyProtocolDTO> result = new ArrayList<StudyProtocolDTO>();
                result.add(StudyProtocolConverter.convertFromDomainToDTO(list.get(0)));
                return result;
            }
        });
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
    }

    private void setUpDateCheckForTodayOnMilestone() throws PAException {
        StudyMilestoneServicelocal svcMil = mock(StudyMilestoneServicelocal.class);
        action.setStudyMilestoneService(svcMil);
        when(svcMil.create(any(StudyMilestoneDTO.class))).thenAnswer(new Answer<StudyMilestoneDTO>() {
            @Override
            public StudyMilestoneDTO answer(InvocationOnMock invocation) throws Throwable {
                Object args[] = invocation.getArguments();
                StudyMilestoneDTO smDto = (StudyMilestoneDTO) args[0];
                if (!sdf.format(new Date()).equals(sdf.format(smDto.getMilestoneDate().getValue()))) {
                    throw new PAException("date does not match");
                }
                return smDto;
            }
          });
    }
    
    @Test
    public void unrejectTrialTest() throws PAException {
        studyProtocolService = mock(StudyProtocolServiceLocal.class);
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
        action.setStudyProtocolService(studyProtocolService);
        
        documentWorkflowStatusService = mock(DocumentWorkflowStatusService.class);
        action.setDocumentWorkflowStatusService(documentWorkflowStatusService);
        
        registryUserService = mock(RegistryUserServiceLocal.class);
        action.setRegistryUserService(registryUserService);
        
        protocolQueryService = mock(ProtocolQueryServiceLocal.class);
        action.setProtocolQueryService(protocolQueryService);
        ServletActionContext.getRequest().getSession().setAttribute("isSuAbstractor", true);
        User csmUser = new User();
        csmUser.setLoginName("loginName");
        CSMUserUtil csmUserService = mock(CSMUserService.class);
        CSMUserService.setInstance(csmUserService);
        when(CSMUserService.getInstance().getCSMUser(any(String.class))).thenReturn(csmUser);
        RegistryUser ru = new RegistryUser();
        ru.setFirstName("firstName");
        ru.setLastName("lastName");
        when(registryUserService.getUser(any(String.class))).thenReturn(ru);
        
        action.setUnRejectReason("Test Code");
        MilestoneDTO mdto = new MilestoneDTO();
        mdto.setMilestone(MilestoneCode.SUBMISSION_REJECTED);
        mdto.setMilestoneDate(new Date());
        MilestonesDTO milestonesDto = new MilestonesDTO();
        milestonesDto.setStudyMilestone(mdto);
        
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.REJECTED);
        dto.setMilestones(milestonesDto);
        dto.setNciIdentifier("NCI-001-0001");
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(any(Long.class))).thenReturn(dto);
        
        
        StudyProtocolDTO spDTO1 = new StudyProtocolDTO();
        spDTO1.setIdentifier(IiConverter.convertToIi(2L));
        spDTO1.setOfficialTitle(StConverter.convertToSt("NCI-001-0002"));
        spDTO1.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
        spDTO1.setSubmissionNumber(IntConverter.convertToInt(1));
        List<StudyProtocolDTO> spList = new ArrayList<StudyProtocolDTO>();
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setIdentifier(IiConverter.convertToIi(1L));
        spDTO.setOfficialTitle(StConverter.convertToSt("NCI-001-0001"));
        spDTO.setSubmissionNumber(IntConverter.convertToInt(2));
        spList.add(spDTO1);
        when(studyProtocolService.getStudyProtocol(action.getSpIi())).thenReturn(spDTO1);
        assertEquals("success", action.unrejectTrial()); 
        assertEquals("NCI-001-0001 has been restored to previously active version.", ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
    }
}
