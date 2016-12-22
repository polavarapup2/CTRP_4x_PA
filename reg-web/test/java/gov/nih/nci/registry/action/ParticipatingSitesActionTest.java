package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

public class ParticipatingSitesActionTest extends AbstractRegWebTest {
    private ParticipatingSitesAction action;

    /**
     * Initialization.
     * @throws Exception in case of error
     */
    @Before
    public void setup() throws Exception {
        Organization org = new Organization();
        org.setIdentifier("1");
        org.setCity("city");
        org.setCountryName("countryName");
        org.setName("name");
        org.setPostalCode("postalCode");

        Person person = new Person();
        person.setIdentifier("1");
        person.setFirstName("firstName");
        person.setLastName("lastName");

        PAContactDTO contactDTO = new PAContactDTO();
        contactDTO.setFullName("Contact User");

        CorrelationUtilsRemote correlationUtils = mock(CorrelationUtilsRemote.class);
        when(correlationUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(org);

        action = new ParticipatingSitesAction();
        action.setCorrelationUtils(correlationUtils);
        action.prepare();

        MockHttpSession session = new MockHttpSession();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("firstName");
        request.setSession(session);

        ServletActionContext.setRequest(request);
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setupAddParameter("studyProtocolId", "1");

        HttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
    }

    @Test
    public void testExecute(){
        try {            
            assertEquals("success", action.execute());
        } catch (PAException e) {
            e.printStackTrace();
        }
    }

}
