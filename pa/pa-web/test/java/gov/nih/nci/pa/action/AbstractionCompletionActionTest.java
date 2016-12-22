/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO.ErrorMessageTypeEnum;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorOptions;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
/**
 * @author Michael Visee
 *
 */
public class AbstractionCompletionActionTest {

    private AbstractionCompletionServiceLocal abstractionCompletionService =
            mock(AbstractionCompletionServiceLocal.class);
    private CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService = mock(CTGovXmlGeneratorServiceLocal.class);
    private TSRReportGeneratorServiceRemote tsrReportGeneratorService = mock(TSRReportGeneratorServiceRemote.class);
    private AbstractionCompletionAction sut;

    private AbstractionCompletionAction createAbstractionCompletionAction() {
        AbstractionCompletionAction action = new AbstractionCompletionAction();
        action.prepare();
        setDependencies(action);
        return action;
    }

    private void setDependencies(AbstractionCompletionAction action) {
        action.setAbstractionCompletionService(abstractionCompletionService);
        action.setCtGovXmlGeneratorService(ctGovXmlGeneratorService);
        action.setTsrReportGeneratorService(tsrReportGeneratorService);
    }

    /**
     * Initialization method.
     */
    @Before
    public void setUp() {
        AbstractPaActionTest.initActionContext();
    }

    /**
     * Clean out the action context to ensure one test does not impact another.
     */
    @After
    public void tearDown() {
        ActionContext.setContext(null);
    }

    /**
     * Test The query method in the successful case.
     * @throws PAException if an error occurs.
     */
    @Test
    public void testQuerySuccess() throws PAException {
        sut = createAbstractionCompletionAction();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.STUDY_PROTOCOL_II, spIi);
        List<AbstractionCompletionDTO> abstractionList = createAbstractionList("error", 
                ErrorMessageTypeEnum.ADMIN);        
        when(abstractionCompletionService.validateAbstractionCompletion(spIi)).thenReturn(abstractionList);
        String result = sut.query();
        assertEquals("Wrong result returned", "success", result);
        assertEquals("Wrong abstraction list", abstractionList, sut.getAbstractionList());
        assertTrue("Wrong error indicator", sut.isAbstractionError());
        assertNull("Unexpected failure message",
                   ServletActionContext.getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        abstractionList = createAbstractionList("error", ErrorMessageTypeEnum.SCIENTIFIC);
        when(abstractionCompletionService.validateAbstractionCompletion(spIi)).thenReturn(abstractionList);
        result = sut.query();
    }

    /**
     * Test The query method in the successful case.
     * @throws PAException if an error occurs.
     */
    @Test
    public void testQueryFailure() throws PAException {
        sut = createAbstractionCompletionAction();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.STUDY_PROTOCOL_II, spIi);
        when(abstractionCompletionService.validateAbstractionCompletion(spIi)).thenThrow(new NullPointerException());
        String result = sut.query();
        assertEquals("Wrong result returned", "success", result);
        assertNull("Wrong abstraction list", sut.getAbstractionList());
        assertFalse("Wrong error indicator", sut.isAbstractionError());
        assertEquals("Unexpected failure message",
                     "An error happened during abstraction: java.lang.NullPointerException", ServletActionContext
                         .getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        verify(abstractionCompletionService).validateAbstractionCompletion(spIi);
    }

    /**
     * Test the generateXML method with no study protocol id.
     */
    @Test
    public void testGenerateXMLNoStudy() {
        sut = createAbstractionCompletionAction();
        String result = sut.generateXML();
        assertEquals("Wrong result returned from generateXML", "displayXML", result);
    }

    /**
     * Test method generateXML method in the success case.
     * @throws PAException if an error occurs.
     */
    @Test
    public void testGenerateXML() throws PAException {
        sut = createAbstractionCompletionAction();
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setupAddParameter("studyProtocolId", "1");
        sut.setStudyProtocolId(1L);
        sut.setServletResponse(ServletActionContext.getResponse());
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi)).thenReturn("xmlData");
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS)).thenReturn("xmlData");
        String result = sut.generateXML();
        assertEquals("none", result);
        HttpServletResponse response = ServletActionContext.getResponse();
        assertEquals("Wrong content type", "application/xml", response.getContentType());
        assertEquals("", "UTF-8", response.getCharacterEncoding());
        verify(ctGovXmlGeneratorService).generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
    }

    /**
     * Test method generateXML method in the failure case.
     * @throws PAException if an error occurs.
     */
    @Test
    public void testGenerateXMLException() throws PAException {
        sut = createAbstractionCompletionAction();
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setupAddParameter("studyProtocolId", "1");
        sut.setStudyProtocolId(1L);
        sut.setServletResponse(ServletActionContext.getResponse());
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi)).thenThrow(new NullPointerException());
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS)).thenThrow(new NullPointerException());
        String result = sut.generateXML();
        assertEquals("displayXML", result);
        verify(ctGovXmlGeneratorService).generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
    }

    /**
     * Test the viewTSR method in the success case.
     * @throws IOException if an error occurs
     * @throws PAException if an error occurs
     */
    @Test
    public void testViewTSR() throws IOException, PAException {
        sut = createAbstractionCompletionAction();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.STUDY_PROTOCOL_II, spIi);
        sut.setServletResponse(ServletActionContext.getResponse());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write("rtfData".getBytes());
        when(tsrReportGeneratorService.generateRtfTsrReport(spIi)).thenReturn(output);
        String fileNameDateStr = DateFormatUtils.format(new Date(), PAConstants.TSR_DATE_FORMAT);
        String result = sut.viewTSR();
        assertEquals("none", result);
        MockHttpServletResponse response = (MockHttpServletResponse) ServletActionContext.getResponse();
        assertEquals("Wrong content type", "application/rtf", response.getContentType());
        assertEquals("Wrong Pragma header", "public", response.getHeader("Pragma"));        
        assertEquals("Wrong Cache-Control header", "max-age=0", response.getHeader("Cache-Control"));
        String contentDisposition = response.getHeader("Content-Disposition");
        assertTrue(contentDisposition.matches(".*" + fileNameDateStr + ".*"));
        verify(tsrReportGeneratorService).generateRtfTsrReport(spIi);
    }

    /**
     * Test the viewTSR method in the failure case.
     * @throws IOException if an error occurs
     * @throws PAException if an error occurs
     */
    @Test
    public void testViewTSRException() throws IOException, PAException {
        sut = createAbstractionCompletionAction();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.STUDY_PROTOCOL_II, spIi);
        sut.setServletResponse(ServletActionContext.getResponse());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write("rtfData".getBytes());
        when(tsrReportGeneratorService.generateRtfTsrReport(spIi)).thenThrow(new NullPointerException());
        String result = sut.viewTSR();
        assertEquals("success", result);
        verify(tsrReportGeneratorService).generateRtfTsrReport(spIi);
    }

    /**
     * Test the displayReportingXML method.
     */
    @Test
    public void testDisplayReportingXML() {
        sut = createAbstractionCompletionAction();
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setupAddParameter("studyProtocolId", "1");
        assertEquals("displayXML", sut.displayReportingXML());
        assertEquals("Wrong protocolIdForXmlGeneration request attribute", "1", ServletActionContext.getRequest()
            .getAttribute("protocolIdForXmlGeneration"));
    }

    /**
     * Test the errorExists with an error.
     */
    @Test
    public void testerrorExistsTrue() {
        sut = createAbstractionCompletionAction();
        sut.setAbstractionList(createAbstractionList("error", null));
        assertTrue("Wrong result of errorExists", sut.errorExists());        
    }

    /**
     * Test the errorExists with a warning.
     */
    @Test
    public void testerrorExistsFalse() {
        sut = createAbstractionCompletionAction();
        sut.setAbstractionList(createAbstractionList("warning", null));
        assertFalse("Wrong result of errorExists", sut.errorExists());
    }

    /**
     * Creates an abstraction list with one element of the given type.
     * @param errorType The type
     * @param errorMessageType error message type
     * @return an abstraction list with one element of the given type
     */
    private List<AbstractionCompletionDTO> createAbstractionList(String errorType, ErrorMessageTypeEnum errorMessageType) {
        List<AbstractionCompletionDTO> list = new ArrayList<AbstractionCompletionDTO>();
        AbstractionCompletionDTO dto = new AbstractionCompletionDTO();
        dto.setErrorType(errorType);
        if (errorMessageType != null) {
            dto.setErrorMessageType(errorMessageType);
        }
        list.add(dto);
        return list;
    }
    
    /**
     * Creates an abstraction list with warnings.
     */
    @Test
    public void testgetWarnings() {
        sut = createAbstractionCompletionAction();
        sut.setAbstractionList(createAbstractionList("warning", null));
        List<AbstractionCompletionDTO> absWarnings = sut.getWarnings();
        assertTrue("true", absWarnings.size()>0);
    }
    
    
    /**
     * Test the getWarnings method .
     */
    @Test
    public void testgetNoWarnings() {
        sut = createAbstractionCompletionAction();
        sut.setAbstractionList(createAbstractionList("error", null));
        List<AbstractionCompletionDTO> absWarnings = sut.getWarnings();
        assertFalse("true", absWarnings.size()>0);
    }
}
