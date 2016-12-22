/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.StudyProcessingErrorDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProcessingErrorService;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test ResultsReportingActionsTakenAction Test
 * @author gunnikrishnan
 * 
 */
public class ResultsReportingActionsTakenActionTest extends AbstractPaActionTest {

    @Override
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());
    }

    
    @Test
    public void testAllLatestResultsReportingActions() throws PAException {
        ResultsReportingActionsTakenAction action = getAction();
        assertEquals("success", action.view());
        final List spes = action.getStudyProcessingErrors();
        assertNotNull(spes);
        assertEquals(2, spes.size());
    }
    
    @Test
    public void testResultsReportingActionsByStudy() throws PAException {
        ResultsReportingActionsTakenAction action = getAction();
        action.setStudyProtocolId(1l);
        assertEquals("success", action.view());
        final List spes = action.getStudyProcessingErrors();
        assertNotNull(spes);
        assertEquals(1, spes.size());
    }
        
    @Test
    public void testActionsTakenUpdate() throws PAException, IOException {
        ResultsReportingActionsTakenAction action = getAction();
        StudyProcessingErrorDTO dto = new StudyProcessingErrorDTO();
        dto.setIdentifier(1l);
        action.setStudyProcessingErrDto(dto);
        assertEquals("ajaxResponse", action.updateSpeAjax());
        String result = IOUtils.toString(action.getAjaxResponseStream()); 
        assertEquals("success", result);
    }
    
    @Test
    public void testActionsTakenUpdateNonExistingSPE() throws PAException, IOException {
        ResultsReportingActionsTakenAction action = getAction();
        StudyProcessingErrorDTO dto = new StudyProcessingErrorDTO();
        dto.setIdentifier(2l);
        action.setStudyProcessingErrDto(dto);
        assertEquals("ajaxResponse", action.updateSpeAjax());
        String result = IOUtils.toString(action.getAjaxResponseStream()); 
        assertEquals("StudyProcessingError with Id = 2 not found in DB, refresh the page and try again", result);
    }

    private ResultsReportingActionsTakenAction getAction() throws PAException {
        ResultsReportingActionsTakenAction action = new ResultsReportingActionsTakenAction();
        action.prepare();
        action.setStudyProcessingErrorService(getStudyProcessingErrorMock());
        return action;
    }

   
    private StudyProcessingErrorService getStudyProcessingErrorMock() throws PAException {
        final StudyProcessingErrorService mock = mock(StudyProcessingErrorService.class);
        StudyProcessingErrorDTO dto1 = new StudyProcessingErrorDTO();
        StudyProtocolQueryDTO dto2 = new StudyProtocolQueryDTO();
        when(mock.getLatestStudyProcessingErrors())
                .thenReturn(new ArrayList(Arrays.asList(dto1, dto2)));
        
        when(mock.getStudyProcessingErrorByStudy(1L))
                .thenReturn(new ArrayList(Arrays.asList(dto1)));
                
        when(mock.getStudyProcessingError(1L))
                .thenReturn(new StudyProcessingErrorDTO());
        when(mock.getStudyProcessingError(2L))
                .thenReturn(null);
        return mock;
    }

}