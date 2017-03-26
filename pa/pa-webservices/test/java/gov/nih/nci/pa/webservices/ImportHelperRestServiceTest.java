package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaRegistry;

/**
 * 
 * @author Reshma
 *
 */
public class ImportHelperRestServiceTest extends AbstractMockitoTest {
    private ImportHelperRestService service = new ImportHelperRestService();
    @Test
    public void getStudyProtocolIdentityTest() throws PAException {

        when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
                        eq("NCT290384"))).thenReturn(
                Arrays.asList((StudyProtocolDTO) spDto));
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(queryDTOList);
        Response r = service.getStudyProtocolIdentity("NCT290384");
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
    }
}
