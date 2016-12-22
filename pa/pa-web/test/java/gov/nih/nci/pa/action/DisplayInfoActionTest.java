/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.service.MockCorrelationUtils;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class DisplayInfoActionTest extends AbstractPaActionTest {
    DisplayInfoAction action;
    @Before
    public void prepare() throws Exception {
        action = new DisplayInfoAction();
        action.prepare();
        action.setCorrelationUtils(new MockCorrelationUtils());
        StudyProtocolQueryDTO ts = new StudyProtocolQueryDTO();
        ts.setStudyProtocolId(1L);
        ts.getLastCreated().setUserLastCreated("userName");
        HttpSession sess = new MockHttpSession();
        sess.setAttribute(Constants.TRIAL_SUMMARY, ts);
     }
    @Test
    public void testPersWebDTOProperty(){
        assertNotNull(action.getWebDTO());
        action.setWebDTO(null);
        assertNull(action.getWebDTO());

    }
    @Test
    public void testQuery() {
        assertEquals("success",action.query());
    }
    //@Test
    public void testQueryPiInfo(){
        assertEquals("success",action.queryPiInfo());
    }
    @Test
    public void testQueryException() {
        StudyProtocolQueryDTO ts = new StudyProtocolQueryDTO();
        ts.setStudyProtocolId(1L);
        ts.getLastCreated().setUserLastCreated("exceptionName");
        HttpSession sess = new MockHttpSession();
        sess.setAttribute(Constants.TRIAL_SUMMARY, ts);
        assertEquals("success",action.query());
    }
   
}
