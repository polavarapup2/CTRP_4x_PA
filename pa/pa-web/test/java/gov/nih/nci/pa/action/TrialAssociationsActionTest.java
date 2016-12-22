package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.iso.dto.StudyProtocolAssociationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.util.Constants;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class TrialAssociationsActionTest extends AbstractPaActionTest {
    TrialAssociationsAction action;
    private StudyProtocolService studyProtocolService;
    private Ii id = IiConverter.convertToIi(1L);
    @Before
    public void setUp() throws PAException {
        action =  new TrialAssociationsAction();
        studyProtocolService = mock(StudyProtocolService.class);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, id);
    } 
    
    @Test
    public void testQuery() throws PAException {
        action.prepare();
        List<StudyProtocolAssociationDTO> dtos= new ArrayList<StudyProtocolAssociationDTO>();
        StudyProtocolAssociationDTO dto = new StudyProtocolAssociationDTO();
        dto.setIdentifier(id);
        dto.setStudyIdentifier(StConverter.convertToSt("NCI-110-1111"));
        when(studyProtocolService.getTrialAssociations(IiConverter
                        .convertToLong(id))).thenReturn(dtos);
        assertEquals("success", action.query());
        }
    
    @Test
    public void testQueryError() throws PAException {
        assertEquals("error", action.query());
    }
    
    @Test
    public void testCreateValidationError() {
        assertEquals("input", action.create());
        assertTrue(action.hasFieldErrors());
        assertTrue(action.getFieldErrors().get(
                ("trialAssociation.studyIdentifier")).contains("error.trialAssociation.studyIdentifier"));
        assertTrue(action.getFieldErrors().get(
                ("trialAssociation.identifierType")).contains("error.trialAssociation.identifierType"));
    }
    
    @Test
    public void testCreate() {
       action.prepare();
       StudyProtocolAssociationDTO trialAssociation = new StudyProtocolAssociationDTO();
       trialAssociation.setIdentifier(id);
       trialAssociation.setStudyIdentifier(StConverter.convertToSt("NCI-110-1111"));
       trialAssociation.setIdentifierType(CdConverter.convertToCd(IdentifierType.NCI));
       action.setTrialAssociation(trialAssociation);
       assertEquals("success", action.create());
    }
    
    @Test
    public void testEdit() {
       action.prepare();
       assertEquals("input", action.edit());
    }
    
    @Test
    public void testUpdateAssociationValidationError() {
       action.prepare();
       assertEquals("input", action.updateAssociation());
       
    }
    
    @Test
    public void testUpdateAssociation() {
       action.prepare();
       StudyProtocolAssociationDTO trialAssociation = new StudyProtocolAssociationDTO();
       trialAssociation.setIdentifier(id);
       trialAssociation.setStudyIdentifier(StConverter.convertToSt("NCI-110-1111"));
       trialAssociation.setIdentifierType(CdConverter.convertToCd(IdentifierType.NCI));
       action.setTrialAssociation(trialAssociation);
       assertEquals("success", action.updateAssociation());
       
    }
    
    @Test
    public void testAssociateWithTria() {
       action.prepare();
       assertEquals("success", action.associateWithTrial());
    }
    
    @Test
    public void testDelete() {
      action.prepare();
      assertEquals("success", action.delete());
    }
}
