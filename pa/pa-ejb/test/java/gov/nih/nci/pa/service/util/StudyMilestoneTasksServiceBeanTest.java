/**
 *
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

/**
 * @author Michael Visee
 * 
 */
public class StudyMilestoneTasksServiceBeanTest {

    private LookUpTableServiceRemote lookUpTableService = mock(LookUpTableServiceRemote.class);
    private MailManagerServiceLocal mailManagerService = mock(MailManagerServiceLocal.class);
    private StudyMilestoneServicelocal studyMilestoneService = mock(StudyMilestoneServicelocal.class);
    private StudyMilestoneTasksServiceLocal studyMilestoneTasksService = mock(StudyMilestoneTasksServiceLocal.class);
    private StudyMilestoneTasksServiceBean sut;

    /**
     * Creates a real StudyMilestoneTasksServiceBean and inject the mock services in it.
     * @return A real StudyMilestoneTasksServiceBean with mock services injected.
     */
    private StudyMilestoneTasksServiceBean createStudyMilestoneTasksServiceBean() {
        StudyMilestoneTasksServiceBean studyMilestoneTasksService = new StudyMilestoneTasksServiceBean();
        setDependencies(studyMilestoneTasksService);
        return studyMilestoneTasksService;
    }

    /**
     * Creates a mock StudyMilestoneTasksServiceBean and inject the mock services in it.
     * @return A mock StudyMilestoneTasksServiceBean with mock services injected.
     */
    private StudyMilestoneTasksServiceBean createStudyMilestoneTasksServiceBeanMock() {
        StudyMilestoneTasksServiceBean service = mock(StudyMilestoneTasksServiceBean.class);
        doCallRealMethod().when(service).setLookUpTableService(lookUpTableService);
        doCallRealMethod().when(service).setMailManagerService(mailManagerService);
        doCallRealMethod().when(service).setStudyMilestoneService(studyMilestoneService);
        doCallRealMethod().when(service).setStudyMilestoneTasksService(studyMilestoneTasksService);
        setDependencies(service);
        return service;
    }

    /**
     * Inject the mock services in the given StudyMilestoneTasksServiceBean.
     * @param service The StudyMilestoneTasksServiceBean to setup with mock services
     */
    private void setDependencies(StudyMilestoneTasksServiceBean service) {
        service.setLookUpTableService(lookUpTableService);
        service.setMailManagerService(mailManagerService);
        service.setStudyMilestoneService(studyMilestoneService);
        service.setStudyMilestoneTasksService(studyMilestoneTasksService);
        
        final PAServiceUtils mock = mock(PAServiceUtils.class);
        when(mock.getTrialNciId(any(Long.class))).thenReturn("NCI-2001-1111111111");
        service.setPaServiceUtils(mock);
    }

    /**
     * Test the performTask method
     * @throws Exception 
     */
    @Test
    public void testPerformTask() throws Exception {
        sut = createStudyMilestoneTasksServiceBeanMock();
        doCallRealMethod().when(sut).performTask();
        DateTime threshold = new DateTime();
        when(sut.getOverdueDate(any(DateTime.class))).thenReturn(threshold);
        Set<StudyMilestone> milestones = new HashSet<StudyMilestone>();
        when(studyMilestoneTasksService.getTrialSummarySentMilestones(threshold)).thenReturn(milestones);
        StudyMilestoneTaskMessageCollection errors = new StudyMilestoneTaskMessageCollection();
        when(sut.createMilestones(milestones)).thenReturn(errors);

        sut.performTask();

        InOrder inOrder = inOrder(sut, studyMilestoneTasksService);
        inOrder.verify(sut).getOverdueDate(any(DateTime.class));
        inOrder.verify(studyMilestoneTasksService).getTrialSummarySentMilestones(threshold);
        inOrder.verify(sut).createMilestones(milestones);
        inOrder.verify(sut).sendFailureNotification(errors);
    }

    /**
     * Test the createMilestones method with a valid case
     * @throws PAException if an error occurs.
     */
    @Test
    public void testCreateMilestonesValid() throws PAException {
        DateTime before = new DateTime();
        sut = createStudyMilestoneTasksServiceBean();
        Set<StudyMilestone> milestones = new HashSet<StudyMilestone>();
        milestones.add(StudyMilestoneTaskMessageTest.createMilestone(0L, 1L));

        StudyMilestoneTaskMessageCollection errors = sut.createMilestones(milestones);

        assertTrue("Error collection should be empty", errors.isEmpty());

        ArgumentCaptor<StudyMilestoneDTO> captor = ArgumentCaptor.forClass(StudyMilestoneDTO.class);
        verify(studyMilestoneTasksService).createMilestone(captor.capture());
        assertMilestoneDTO(captor.getValue(), 0L, before);
    }

    /**
     * Check that the given milestoneDTO is the new milestone created automatically
     * @param milestoneDTO The dto to check
     * @param studyProtocolId The expected study protocol id
     */
    private void assertMilestoneDTO(StudyMilestoneDTO milestoneDTO, Long studyProtocolId, DateTime before) {
        String expectedComment = "Milestone auto-set based on non-response within 5 days";
        assertEquals("Wrong comment", expectedComment, StConverter.convertToString(milestoneDTO.getCommentText()));
        String expectedCode = MilestoneCode.INITIAL_ABSTRACTION_VERIFY.getCode();
        assertEquals("Wrong milestone code", expectedCode,
                     CdConverter.convertCdToString(milestoneDTO.getMilestoneCode()));
        DateTime creationDate = new DateTime(TsConverter.convertToTimestamp(milestoneDTO.getMilestoneDate()));
        assertFalse("Creation date in the past", creationDate.isBefore(before));
        assertFalse("Creation date in the future", creationDate.isAfterNow());
        Ii expectedSpIi = IiConverter.convertToStudyProtocolIi(studyProtocolId);
        assertEquals("Wrong study protocol Ii", expectedSpIi, milestoneDTO.getStudyProtocolIdentifier());
    }

    /**
     * Test the createMilestones method with an invalid case
     * @throws PAException if an error occurs.
     */
    @Test
    public void testCreateMilestonesInvalid() throws PAException {
        DateTime before = new DateTime();
        sut = createStudyMilestoneTasksServiceBean();
        Set<StudyMilestone> milestones = new HashSet<StudyMilestone>();
        milestones.add(StudyMilestoneTaskMessageTest.createMilestone(0L, 1L));
        doThrow(new PAException("PAException")).when(studyMilestoneTasksService)
            .createMilestone(any(StudyMilestoneDTO.class));
        StudyMilestoneTaskMessageCollection errors = sut.createMilestones(milestones);

        assertFalse("Error collection should not be empty", errors.isEmpty());
        String expectedBody = "An error occurred while running the Abstraction Verified No Response Script.\n"
                + "The trial ID is 1 and the error is NCI-2001-1111111111: PAException\n";
        assertEquals("Wrong error message body", expectedBody, errors.getSummary());

        ArgumentCaptor<StudyMilestoneDTO> captor = ArgumentCaptor.forClass(StudyMilestoneDTO.class);
        verify(studyMilestoneTasksService).createMilestone(captor.capture());
        assertMilestoneDTO(captor.getValue(), 0L, before);
    }
    
    /**
     * Test the createMilestone method in the valid case.
     * @throws PAException if an error occurs.
     */
    @Test
    public void testCreateMilestoneValid() throws PAException {
        sut = createStudyMilestoneTasksServiceBean();
        StudyMilestoneDTO milestoneDTO = new StudyMilestoneDTO();
        when(studyMilestoneService.create(milestoneDTO)).thenReturn(milestoneDTO);
        sut.createMilestone(milestoneDTO);
        verify(studyMilestoneService).create(milestoneDTO);
    }
    
    /**
     * Test the createMilestone method in the invalid case.
     * @throws PAException if an error occurs.
     */
    @Test
    public void testCreateMilestoneInvalid() throws PAException {
        sut = createStudyMilestoneTasksServiceBean();
        StudyMilestoneDTO milestoneDTO = new StudyMilestoneDTO();
        PAException exception = new PAException("PAException");
        when(studyMilestoneService.create(milestoneDTO)).thenThrow(exception);
        try {
            sut.createMilestone(milestoneDTO);
            fail("createMilestone should have failed");
        } catch (Exception e) {
            assertEquals("Wrong exception thrown", exception, e);
        } 
        verify(studyMilestoneService).create(milestoneDTO);
    }

    /**
     * test the sendFailureNotification method with an empty error collection
     * @throws PAException if an error occurs.
     */
    @Test
    public void testsendFailureNotificationEmpty() throws PAException {
        sut = createStudyMilestoneTasksServiceBean();
        StudyMilestoneTaskMessageCollection errors = new StudyMilestoneTaskMessageCollection();
        sut.sendFailureNotification(errors);
        verify(lookUpTableService, never()).getPropertyValue(anyString());
        verify(mailManagerService, never()).sendMailWithAttachment(anyString(), anyString(), anyString(),
                                                                   any(File[].class));
    }

    /**
     * test the sendFailureNotification method with an non empty error collection
     * @throws PAException if an error occurs.
     */
    @Test
    public void testsendFailureNotificationNotEmpty() throws PAException {
        sut = createStudyMilestoneTasksServiceBean();
        StudyMilestoneTaskMessageCollection errors = new StudyMilestoneTaskMessageCollection();
        errors.add(StudyMilestoneTaskMessageTest.createMilestone(0L, 1L), "message1");
        String mailBody = errors.getSummary();
        when(lookUpTableService.getPropertyValue("abstraction.script.subject")).thenReturn("mailSubject");
        when(lookUpTableService.getPropertyValue("abstraction.script.mailTo")).thenReturn("mailTo");
        sut.sendFailureNotification(errors);
        verify(lookUpTableService).getPropertyValue("abstraction.script.subject");
        verify(lookUpTableService).getPropertyValue("abstraction.script.mailTo");
        verify(mailManagerService).sendMailWithAttachment("mailTo", "mailSubject", mailBody, null);
    }

}
