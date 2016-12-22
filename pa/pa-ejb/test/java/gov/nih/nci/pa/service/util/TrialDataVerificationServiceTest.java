package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertTrue;


import java.util.List;
import org.junit.Before;
import org.junit.Test;
import gov.nih.nci.pa.iso.dto.TrialVerificationDataDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialDataVerificationBeanLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;;

/**
 * 
 * @author Reshma.Koganti
 *
 */
public class TrialDataVerificationServiceTest extends AbstractHibernateTestCase {
    private TrialDataVerificationBeanLocal bean;
    private Long studyProtocolId;
    
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());
        bean = new TrialDataVerificationBeanLocal();
        studyProtocolId = 1L;
        TestSchema.addTrialVerificationData(studyProtocolId);
    }
    
    @Test
    public void getDataByStudyProtocolIdTest() throws PAException {
        List<TrialVerificationDataDTO> list = bean.getDataByStudyProtocolId(studyProtocolId);
        assertTrue(list.size() > 0);
    }
    
}
