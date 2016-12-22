/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test CTGovXMLAction
 * @author bpickeral
 *
 */
public class CTGovXMLActionTest extends AbstractRegWebTest {

    private static final String TEST_STRING = "test xml string";
    private final CTGovXmlGeneratorServiceLocal ctService = mock(CTGovXmlGeneratorServiceLocal.class);
    private final StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private final ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class);
    private final RegistryUserService regUserService = mock(RegistryUserService.class);

    private final StudyProtocolQueryDTO spqDto = new StudyProtocolQueryDTO();

    private final CTGovXMLAction action = new CTGovXMLAction();

    @Before
    public void setup() throws PAException {
        action.prepare();

        DocumentWorkflowStatusCode dwfs = DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE;

        List<StudyProtocolQueryDTO> spqDtoList = new ArrayList<StudyProtocolQueryDTO>();
        spqDto.setShowSendXml(true);
        spqDto.setProprietaryTrial(false);
        spqDto.setDocumentWorkflowStatusCode(dwfs);
        spqDto.setSearcherTrialOwner(true);
        spqDto.setCtgovXmlRequiredIndicator(true);
        spqDto.setNciIdentifier("NCI-2009-00001");
        spqDto.setOfficialTitle("title");
        spqDtoList.add(spqDto);

        when(protocolQueryService.getStudyProtocolByCriteria((StudyProtocolQueryCriteria) anyObject())).thenReturn(spqDtoList);

        StudyProtocolDTO sp = new StudyProtocolDTO();
        sp.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(sp);

        action.setProtocolQueryService(protocolQueryService);
        action.setStudyProtocolService(studyProtocolService);
        action.setCtService(ctService);

        action.setId("test id");
        action.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        when(ctService.generateCTGovXml((Ii) anyObject())).thenReturn(TEST_STRING);

        action.setRegistryUserService(regUserService);
        when(regUserService.getUser(anyString())).thenReturn(new RegistryUser());
        when(regUserService.isTrialOwner(anyLong(), anyLong())).thenReturn(true);

    }

    /**
     * Test retrieveCtGovXML streams the generated xml as a file.
     * @throws PAException on PA error
     * @throws IOException on I/O error
     */
    @Test
    public void testRetrieveCtGovXML() throws IOException {
        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        BufferedReader br = new BufferedReader(new InputStreamReader(action.getXmlFile()));
        assertTrue(br.readLine().contains(TEST_STRING));
    }

    @Test
    public void testNotOwner() throws IOException, PAException {
        when(regUserService.isTrialOwner(anyLong(), anyLong())).thenReturn(false);

        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        String xml = readerToString(new BufferedReader(new InputStreamReader(action.getXmlFile())));

        assertTrue(xml.contains("Authorization failed. User does not have ownership of the trial."));
    }

    @Test
    public void testNonCtGov() throws IOException {
        spqDto.setCtgovXmlRequiredIndicator(false);
        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        String xml = readerToString(new BufferedReader(new InputStreamReader(action.getXmlFile())));

        assertTrue(xml.contains("This trial cannot be uploaded to PRS."));
    }

    @Test
    public void testAbbreviated() throws IOException {
        spqDto.setProprietaryTrial(true);
        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        String xml = readerToString(new BufferedReader(new InputStreamReader(action.getXmlFile())));

        assertTrue(xml.contains("Abbreviated trials are not eligible for XML Export."));
    }

    @Test
    public void testNotAbstracted() throws IOException {
        spqDto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        String xml = readerToString(new BufferedReader(new InputStreamReader(action.getXmlFile())));

        assertTrue(xml.contains("This trial is in review in CTRP by the CTRO and is not eligible to be uploaded to PRS at this time."));
    }

    @Test
    public void testSpError() throws PAException, IOException {
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenThrow(new PAException("exception"));
        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        String xml = readerToString(new BufferedReader(new InputStreamReader(action.getXmlFile())));

        assertTrue(xml.contains("No match found based on the NCI ID that was provided."));
    }

    @Test
    public void testCtServiceError() throws PAException, IOException {
        when(ctService.generateCTGovXml(any(Ii.class))).thenThrow(new PAException("exception"));
        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        String xml = readerToString(new BufferedReader(new InputStreamReader(action.getXmlFile())));

        assertTrue(xml.contains("An error occurred while retrieving the document for CT "));
    }

    private String readerToString(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while(line != null) {
            sb.append(line);
            line = br.readLine();
        }
        return sb.toString();
    }

}
