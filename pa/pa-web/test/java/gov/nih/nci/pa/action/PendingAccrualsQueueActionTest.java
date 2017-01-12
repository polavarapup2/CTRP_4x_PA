package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import gov.nih.nci.pa.domain.PatientStage;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PendingPatientAccrualsServiceLocal;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 */
public class PendingAccrualsQueueActionTest extends AbstractPaActionTest {
    private PendingAccrualsQueueAction patientAction;
    private PendingPatientAccrualsServiceLocal patientServiceLocal;
    
    /**
     * Initialization method.
     * @throws PAException if an error occurs
     */
    @Before
    public void setUp() throws PAException {
    	patientAction = new PendingAccrualsQueueAction();
    	patientServiceLocal = mock(PendingPatientAccrualsServiceLocal.class);
    	patientAction.setPatientService(patientServiceLocal);
    	when(patientServiceLocal.getAllPatientsStage(any(String.class))).thenReturn(new ArrayList<PatientStage>());
        patientAction.prepare();        
    }
    
    @Test
    public void testAction() throws Exception {
    	patientAction.getPendingAccruals();
        assertEquals("success", patientAction.execute());
        patientAction.setObjectsToDelete(new String[] {"1","2"});
        assertEquals("success", patientAction.delete());
        patientAction.setIdentifier("1");
        patientAction.deleteObject(1L);
    }
}
