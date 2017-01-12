package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceBean;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class TrialRegistrationValidatorAccrualDiseaseTermTest {

    TrialRegistrationValidator v;
    AccrualDiseaseTerminologyServiceRemote adtService;
    StudyProtocolDTO studyProtocolDTO;
    StringBuilder errorMsg;
    
    @Before
    public void setUp() {
        v = new TrialRegistrationValidator(null);
        adtService = mock(AccrualDiseaseTerminologyServiceRemote.class);
        v.setAccrualDiseaseTerminologyService(adtService);
        when(adtService.getValidCodeSystems()).thenReturn(
                Arrays.asList(new String[]{"SDC","ICD9","ICD10","ICD-O-3"}));
        studyProtocolDTO = new StudyProtocolDTO();
        errorMsg = new StringBuilder();
    }

    @Test
    public void newSubmissionNull() {
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals(0, errorMsg.length());
        assertEquals(AccrualDiseaseTerminologyServiceBean.DEFAULT_CODE_SYSTEM,
                StConverter.convertToString(studyProtocolDTO.getAccrualDiseaseCodeSystem()));
    }

    @Test
    public void newSubmissionValid() {
        studyProtocolDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("ICD10"));
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals(0, errorMsg.length());
        assertEquals("ICD10", StConverter.convertToString(studyProtocolDTO.getAccrualDiseaseCodeSystem()));
    }

    @Test
    public void newSubmissionInvalid() {
        studyProtocolDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("xyzzy"));
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals("Accrual disease code system xyzzy is invalid.", errorMsg.toString());
    }

    @Test
    public void updateNull() {
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        when(adtService.getCodeSystem(anyLong())).thenReturn("ICD-O-3");
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals(0, errorMsg.length());
        assertEquals("ICD-O-3", StConverter.convertToString(studyProtocolDTO.getAccrualDiseaseCodeSystem()));
    }

    @Test
    public void updateNochange() {
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        studyProtocolDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("ICD-O-3"));
        when(adtService.getCodeSystem(anyLong())).thenReturn("ICD-O-3");
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals(0, errorMsg.length());
        assertEquals("ICD-O-3", StConverter.convertToString(studyProtocolDTO.getAccrualDiseaseCodeSystem()));
    }

    @Test
    public void updateChangeValid() {
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        studyProtocolDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("ICD10"));
        when(adtService.getCodeSystem(anyLong())).thenReturn("ICD-O-3");
        when(adtService.canChangeCodeSystem(anyLong())).thenReturn(true);
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals(0, errorMsg.length());
        assertEquals("ICD10", StConverter.convertToString(studyProtocolDTO.getAccrualDiseaseCodeSystem()));
    }

    @Test
    public void updateChangeInvalid() {
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        studyProtocolDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("ICD10"));
        when(adtService.getCodeSystem(anyLong())).thenReturn("ICD-O-3");
        when(adtService.canChangeCodeSystem(anyLong())).thenReturn(false);
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals("Accrual disease code system for this trial can't be changed.", errorMsg.toString());
    }

    @Test
    public void updateInvalid() {
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        studyProtocolDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("xyzzy"));
        v.validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        assertEquals("Accrual disease code system xyzzy is invalid.", errorMsg.toString());
    }
}
