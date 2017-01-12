/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.StudyObjectiveTypeCode;
import gov.nih.nci.pa.iso.dto.StudyObjectiveDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialDescriptionActionTest extends AbstractPaActionTest {

	TrialDescriptionAction trialDescriptionAction;
	Ii id = IiConverter.convertToIi(1L);
	StudyObjectiveServiceLocal service;
	StudyProtocolServiceLocal studyProtocolService;
	@Before 
	public void setUp() throws PAException {
		trialDescriptionAction =  new TrialDescriptionAction();	
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, id);
		 
	} 

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialDescriptionAction#query()}.
	 * @throws PAException 
	 */
    @Test
    public void testQuery() throws PAException {
        createMocks();
        List<StudyObjectiveDTO> studyObjList = new ArrayList<StudyObjectiveDTO>();
        StudyObjectiveDTO dto = new StudyObjectiveDTO();
        dto.setIdentifier(id);
        dto.setTypeCode(CdConverter.convertToCd(StudyObjectiveTypeCode.PRIMARY));
        dto.setStudyProtocolIdentifier(id);
        dto.setDescription(StConverter.convertToSt("Description"));
        StudyObjectiveDTO dto1 = new StudyObjectiveDTO();
        dto1.setIdentifier(IiConverter.convertToIi(2L));
        dto1.setTypeCode(CdConverter.convertToCd(StudyObjectiveTypeCode.SECONDARY));
        dto1.setStudyProtocolIdentifier(IiConverter.convertToIi(2L));
        dto1.setDescription(StConverter.convertToSt("Description1"));
        StudyObjectiveDTO dto2 = new StudyObjectiveDTO();
        dto2.setIdentifier(IiConverter.convertToIi(3L));
        dto2.setTypeCode(CdConverter.convertToCd(StudyObjectiveTypeCode.TERNARY));
        dto2.setStudyProtocolIdentifier(IiConverter.convertToIi(3L));
        dto2.setDescription(StConverter.convertToSt("Description2"));
        studyObjList.add(dto);
        studyObjList.add(dto1);
        studyObjList.add(dto2);
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setIdentifier(id);
        spDTO.setPublicTitle(StConverter.convertToSt("title"));
        spDTO.setPublicDescription(StConverter.convertToSt("Description"));
        spDTO.setScientificDescription(StConverter.convertToSt("ScientificDescription"));
        when(studyProtocolService.getStudyProtocol(id)).thenReturn(spDTO);
        when(service.getByStudyProtocol(id)).thenReturn(studyObjList);
        assertEquals("edit", trialDescriptionAction.query());
        assertEquals("Description", trialDescriptionAction.getPrimary());
        assertEquals("1", trialDescriptionAction.getStudyObjectiveIip());
    }

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialDescriptionAction#update()}.
	 */
    @Test
    public void testUpdate() {
        trialDescriptionAction.setStudyObjectiveIip("objective");
        trialDescriptionAction.setSecondary("Secondary");
        trialDescriptionAction.setStudyObjectiveIis("studyObjectiveIis");
        trialDescriptionAction.setStudyObjectiveIit("studyObjectiveIit");
        assertEquals("edit", trialDescriptionAction.update());
        assertEquals("objective", trialDescriptionAction.getStudyObjectiveIip());
    }
	
    private void createMocks() throws PAException {
        service = mock(StudyObjectiveServiceLocal.class);
        studyProtocolService = mock(StudyProtocolServiceLocal.class);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getStudyObjectiveService()).thenReturn(service);
        when(PaRegistry.getStudyProtocolService()).thenReturn(studyProtocolService);
    }

}
