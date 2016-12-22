package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;
/**
 * Exercises ManageStudyAlternateTitlesAction
 * @author ADas
 */
public class ManageStudyAlternateTitlesActionTest extends AbstractPaActionTest {
    ManageStudyAlternateTitlesAction action;
    
    @Before
    public void setUp() throws PAException {
        action = new ManageStudyAlternateTitlesAction();
        List<StudyAlternateTitleDTO> studyAlternateTitlesList = new ArrayList<StudyAlternateTitleDTO>();
        StudyAlternateTitleDTO studyAlternateTitleDTO = new StudyAlternateTitleDTO();
        studyAlternateTitleDTO.setAlternateTitle(StConverter.convertToSt("Test"));
        studyAlternateTitleDTO.setCategory(StConverter.convertToSt("Other"));
        studyAlternateTitlesList.add(studyAlternateTitleDTO);        
        getSession().setAttribute(Constants.STUDY_ALTERNATE_TITLES_LIST, studyAlternateTitlesList);        
    }
    
    @Test
    public void testAddStudyAlternateTitle() { 
        getRequest().setupAddParameter("alternateTitle", "Test1");
        getRequest().setupAddParameter("alternateTitleType", "Other");
        assertEquals("displayStudyAlternateTitles", action.addStudyAlternateTitle());
    }
    
    @Test
    public void testDeleteStudyAlternateTitle() {
        getRequest().setupAddParameter("uuid", "1");
        assertEquals("displayStudyAlternateTitles", action.deleteStudyAlternateTitle());
    }
    
    @Test
    public void testSaveStudyAlternateTitle() {
        getRequest().setupAddParameter("alternateTitle", "Test");
        getRequest().setupAddParameter("alternateTitleType", "Other");
        getRequest().setupAddParameter("uuid", "1");
        assertEquals("displayStudyAlternateTitles", action.saveStudyAlternateTitle());
    }
}
