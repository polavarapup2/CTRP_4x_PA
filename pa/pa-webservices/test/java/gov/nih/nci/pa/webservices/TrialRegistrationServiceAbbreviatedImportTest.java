/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class TrialRegistrationServiceAbbreviatedImportTest extends AbstractMockitoTest {
    
    static {
        try {
            final ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
            appender.setThreshold(Priority.INFO);
            Logger.getRootLogger().addAppender(
                    appender);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private TrialRegistrationService service;

    @SuppressWarnings("unchecked")
    @Before
    public void before() throws PAException {
        service = new TrialRegistrationService();

        CTGovSyncServiceLocal ctgovService = mock(CTGovSyncServiceLocal.class);
        when(ctgovService.getAdaptedCtGovStudyByNctId(eq("NCT123456")))
                .thenReturn(null);
        when(ctgovService.getAdaptedCtGovStudyByNctId(eq("NCT290384")))
                .thenReturn(new CTGovStudyAdapter(null));

        when(ctgovService.getAdaptedCtGovStudyByNctId(eq("NCT11111111111")))
                .thenReturn(new CTGovStudyAdapter(null));
        when(ctgovService.importTrial(eq("NCT11111111111"))).thenReturn(
                "NCI-2014-00999");

        when(ctgovService.getAdaptedCtGovStudyByNctId(eq("NCT66666666")))
                .thenReturn(new CTGovStudyAdapter(null));
        when(ctgovService.importTrial(eq("NCT66666666"))).thenThrow(
                PAException.class);

        when(PaRegistry.getInstance().getServiceLocator().getCTGovSyncService())
                .thenReturn(ctgovService);

        when(
                PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
                        eq("NCT290384"))).thenReturn(
                Arrays.asList((StudyProtocolDTO) spDto));
        
        PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
        when(paServiceUtils.getTrialNciId(any(Long.class))).thenReturn("NCI-2014-00999");
        service.setPaServiceUtils(paServiceUtils);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.TrialRegistrationService#registerAbbreviatedTrial(java.lang.String)}
     * .
     */
    @Test
    public final void testBlankNctError() {
        Response r = service.registerAbbreviatedTrial("");
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("Please provide an ClinicalTrials.gov Identifier value.",
                r.getEntity());
    }

    @Test
    public final void testInvalidNctError() {
        Response r = service.registerAbbreviatedTrial("NCT1234$");
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("Provided ClinicalTrials.gov Identifer is invalid.",
                r.getEntity());
    }

    @Test
    public final void testNctNotFound() {
        Response r = service.registerAbbreviatedTrial("NCT123456");
        assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
        assertEquals(
                "A study with the given identifier is not found in ClinicalTrials.gov.",
                r.getEntity());
    }

    @Test
    public final void testNctAlreadyImported() {
        Response r = service.registerAbbreviatedTrial("NCT290384");
        assertEquals(Status.PRECONDITION_FAILED.getStatusCode(), r.getStatus());
        assertEquals(
                "A study with the given identifier already exists in CTRP.",
                r.getEntity());
    }

    @Test
    public final void testFailedImport() {
        Response r = service.registerAbbreviatedTrial("NCT66666666");
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSuccessfulImport() {
        Response r = service.registerAbbreviatedTrial("NCT11111111111");
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        JAXBElement<TrialRegistrationConfirmation> el = (JAXBElement<TrialRegistrationConfirmation>) r
                .getEntity();
        TrialRegistrationConfirmation conf = el.getValue();
        assertEquals("NCI-2014-00999", conf.getNciTrialID());
        assertEquals(spDto.getIdentifier().getExtension(), conf.getPaTrialID()
                + "");
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.TrialRegistrationService#getContext(java.lang.Class)}
     * .
     */
    @Test
    public final void testGetContext() {
        assertNotNull(service.getContext(TrialRegistrationConfirmation.class));
    }

}
