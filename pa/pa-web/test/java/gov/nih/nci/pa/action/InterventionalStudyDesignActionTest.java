package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.OutcomeMeasureWebDTO;
import gov.nih.nci.pa.enums.OutcomeMeasureTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class InterventionalStudyDesignActionTest extends AbstractPaActionTest {
    InterventionalStudyDesignAction action ;
    private final String TEN_CHARACTERS = "abcdefghij";
    @Before
    public void prepare(){
        action = new InterventionalStudyDesignAction();
    }
    @Test
    public void testWebDTOProperty(){
        assertNotNull(action.getWebDTO());
        action.setWebDTO(null);
        assertNull(action.getWebDTO());
    }
    @Test
    public void testSubjectProperty(){
     assertNull(action.getSubject());
     action.setSubject("subject");
     assertNotNull(action.getSubject());
     assertFalse(action.isSubjectChecked());
     action.setSubject("Subject");
     assertTrue(action.isSubjectChecked());
    }
    @Test
    public void testInvestigatorProperty(){
        assertNull(action.getInvestigator());
        action.setInvestigator("investigator");
        assertNotNull(action.getInvestigator());
        assertFalse(action.isInvestigatorChecked());
        action.setInvestigator("Investigator");
        assertTrue(action.isInvestigatorChecked());
    }
    @Test
    public void testCaregiverProperty(){
        assertNull(action.getCaregiver());
        action.setCaregiver("caregiver");
        assertNotNull(action.getCaregiver());
        assertFalse(action.isCaregiverChecked());
        action.setCaregiver("Caregiver");
        assertTrue(action.isCaregiverChecked());
    }
    @Test
    public void testOutcomesassessorProperty(){
     assertNull(action.getOutcomesassessor());
     action.setOutcomesassessor("outcomesassessor");
     assertNotNull(action.getOutcomesassessor());
     assertFalse(action.isOutcomesAssessorChecked());
     action.setOutcomesassessor("Outcomes Assessor");
     assertTrue(action.isOutcomesAssessorChecked());
    }
    @Test
    public void testoutcomeListProperty(){
        assertNull(action.getOutcomeList());
        action.setOutcomeList(new ArrayList<ISDesignDetailsWebDTO>());
        assertNotNull(action.getOutcomeList());
    }
    @Test
    public void testIdProperty(){
        assertNull(action.getId());
        action.setId(1L);
        assertNotNull(action.getId());
    }
    @Test
    public void testPageProperty(){
        assertNull(action.getPage());
        action.setPage("page");
        assertNotNull(action.getPage());
    }
    @Test
    public void testDetailsQuery(){
     getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
     assertEquals("details",action.detailsQuery());
    }
    @Test
    public void testOutcomeinput(){
     assertEquals("outcomeAdd", action.outcomeinput());
    }
    @Test
    public void testOutcomeQueryWithResult(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        assertEquals("outcome", action.outcomeQuery());
    }
    @Test
    public void testOutcomeQueryNoResult(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
        assertEquals("outcome", action.outcomeQuery());
    }
    @Test
    public void testOutcomeedit(){
        action.setId(1L);
        assertEquals("outcomeAdd", action.outcomeedit());
    }
    @Test
    public void testOutcomecreateErr(){
        assertEquals("outcomeAdd",action.outcomecreate());
    }
    @Test
    public void testOutcomecreateErrTooLong() {
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        OutcomeMeasureWebDTO omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        StringBuilder longString = new StringBuilder();
        while (longString.length() < InterventionalStudyDesignAction.MAXIMUM_CHAR_OUTCOME_NAME ) {
            longString.append(TEN_CHARACTERS);
        }
        webDTO.getOutcomeMeasure().setName(longString.toString());
        webDTO.getOutcomeMeasure().setDescription("Description");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        assertEquals("outcomeAdd",action.outcomecreate());

        webDTO = new ISDesignDetailsWebDTO();
        omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.getOutcomeMeasure().setName("Name");
        longString = new StringBuilder();
        while (longString.length() < InterventionalStudyDesignAction.MAXIMUM_CHAR_OUTCOME_DESC ) {
            longString.append(TEN_CHARACTERS);
        }
        webDTO.getOutcomeMeasure().setDescription(longString.toString());
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        assertEquals("outcomeAdd",action.outcomecreate());

        webDTO = new ISDesignDetailsWebDTO();
        omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setDescription("Description");
        longString = new StringBuilder();
        while (longString.length() < InterventionalStudyDesignAction.MAXIMUM_CHAR_OUTCOME_DESC ) {
            longString.append(TEN_CHARACTERS);
        }
        webDTO.getOutcomeMeasure().setTimeFrame(longString.toString());
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        assertEquals("outcomeAdd",action.outcomecreate());
    }
    @Test
    public void testOutcomecreate() {
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        OutcomeMeasureWebDTO omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setTypeCode(OutcomeMeasureTypeCode.PRIMARY.getCode());
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setDescription("Description");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        assertEquals("outcome",action.outcomecreate());
    }
    @Test
    public void testUpdateErr() throws PAException {
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        OutcomeMeasureWebDTO omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.setPrimaryPurposeCode("");
        webDTO.setPhaseCode("");
        webDTO.setDesignConfigurationCode("");
        webDTO.setNumberOfInterventionGroups("1ds");
        webDTO.setBlindingSchemaCode("");
        webDTO.setAllocationCode("");
        webDTO.setMinimumTargetAccrualNumber("minimumTargetAccrualNumber");
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);

        assertEquals("details",action.update());
    }
    @Test
    public void testUpdateErrOther() throws PAException {
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        OutcomeMeasureWebDTO omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.setPrimaryPurposeCode("Other");
        webDTO.setPhaseCode("NA");
        webDTO.setDesignConfigurationCode("");
        webDTO.setNumberOfInterventionGroups("");
        webDTO.setBlindingSchemaCode("");
        webDTO.setAllocationCode("");
        webDTO.setMinimumTargetAccrualNumber("-1");
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        assertEquals("details",action.update());

        webDTO = new ISDesignDetailsWebDTO();
        omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.setPrimaryPurposeCode("Other");
        webDTO.setPrimaryPurposeAdditionalQualifierCode("Other");
        webDTO.setPhaseCode("NA");
        webDTO.setDesignConfigurationCode("");
        webDTO.setNumberOfInterventionGroups("");
        webDTO.setBlindingSchemaCode("");
        webDTO.setAllocationCode("");
        webDTO.setMinimumTargetAccrualNumber("");
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);

        assertEquals("details",action.update());

    }
    @Test
    public void testUpdate() throws PAException{
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        OutcomeMeasureWebDTO omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION.getDisplayName());
        webDTO.setPhaseCode(PhaseCode.I.getDisplayName());
        webDTO.setDesignConfigurationCode("designConfigurationCode");
        webDTO.setNumberOfInterventionGroups("1");
        webDTO.setBlindingSchemaCode("blindingSchemaCode");
        webDTO.setAllocationCode("allocationCode");
        webDTO.setMinimumTargetAccrualNumber("1");
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        action.setInvestigator("Investigator");
        action.setOutcomesassessor("Outcomes Assessor");
        action.setCaregiver("Caregiver");
        action.setSubject("Subject");
        assertEquals("details",action.update());

    }
    @Test
    public void testUpdateS() throws PAException{
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        OutcomeMeasureWebDTO omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION.getDisplayName());
        webDTO.setPhaseCode(PhaseCode.I.getDisplayName());
        webDTO.setDesignConfigurationCode("designConfigurationCode");
        webDTO.setNumberOfInterventionGroups("1");
        webDTO.setBlindingSchemaCode("Open");
        webDTO.setAllocationCode("allocationCode");
        webDTO.setMinimumTargetAccrualNumber("1");
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        action.setInvestigator("FALSE");
        action.setOutcomesassessor("FALSE");
        action.setCaregiver("FALSE");
        action.setSubject("FALSE");
        assertEquals("details",action.update());

    }

    @Test
    public void testOutcomedelete(){
        action.setObjectsToDelete(new String[] {"1"});
     assertEquals("outcome", action.outcomedelete());
    }

    @Test
    public void testOutcomeUpdateErr(){
       assertEquals("outcomeAdd", action.outcomeupdate());
    }

    @Test
    public void testDetailsQueryException(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(9L));
        assertEquals("details",action.detailsQuery());
    }
    @Test
    public void testUpdateException() throws PAException{
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        OutcomeMeasureWebDTO omDto = new OutcomeMeasureWebDTO();
        webDTO.setOutcomeMeasure(omDto);
        webDTO.getOutcomeMeasure().setPrimaryIndicator(true);
        webDTO.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION.getDisplayName());
        webDTO.setPhaseCode(PhaseCode.I.getDisplayName());
        webDTO.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT.getDisplayName());
        webDTO.setDesignConfigurationCode("designConfigurationCode");
        webDTO.setNumberOfInterventionGroups("1");
        webDTO.setBlindingSchemaCode("blindingSchemaCode");
        webDTO.setAllocationCode("allocationCode");
        webDTO.setMinimumTargetAccrualNumber("1");
        webDTO.getOutcomeMeasure().setName("Name");
        webDTO.getOutcomeMeasure().setTimeFrame("designConfigurationCode");
        webDTO.getOutcomeMeasure().setSafetyIndicator(true);
        action.setWebDTO(webDTO);
        action.setInvestigator("FALSE");
        action.setOutcomesassessor("FALSE");
        action.setCaregiver("FALSE");
        action.setSubject("FALSE");
        assertEquals("details",action.update());

    }
    
    @Test
    public void testOutcomeOrder() throws PAException {
        StudyOutcomeMeasureServiceLocal mock = mock(StudyOutcomeMeasureServiceLocal.class);
        action.setStudyOutcomeMeasureService(mock);
        action.setOrderString("3;2;1");
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II,
                IiConverter.convertToIi(1L));
        action.outcomeOrder();
        verify(mock).reorderOutcomes(IiConverter.convertToIi(1L),
                Arrays.asList(new String[] { "3", "2", "1" }));

    }
}
