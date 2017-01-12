/**
 *
 */
package gov.nih.nci.registry.decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.test.util.RegistrationMockServiceLocator;

import java.util.Calendar;

import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockPageContext;

/**
 * @author Vrushali
 *
 */
public class RegistryDisplayTagDecoratorTest {
    private static final int currentListIndex = 1;
    private static final int currentViewIndex = 1;
    RegistryDisplayTagDecorator decorator  ;
    @Before
    public void setup() {
        decorator= new RegistryDisplayTagDecorator();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("userLastCreated");
        PageContext context = new MockPageContext(null, request, new MockHttpServletResponse());
        decorator.init(context, null, null);
        PaRegistry.getInstance().setServiceLocator(new RegistrationMockServiceLocator());
    }
    @Test
    public void testGetStudyStatusDate() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setStudyStatusDate(null);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("",decorator.getStudyStatusDate());
        dto = new StudyProtocolQueryDTO();
        dto.setStudyStatusDate(Calendar.getInstance().getTime());
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertNotNull(decorator.getStudyStatusDate());
    }

    @Test
    public void testGetAmend() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setStudyProtocolId(1L);
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        dto.setStudyStatusCode(StudyStatusCode.ACTIVE);
        dto.setSearcherTrialOwner(true);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Amend", decorator.getAmend());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setProprietaryTrial(true);
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        dto.setStudyStatusCode(StudyStatusCode.ACTIVE);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("", decorator.getAmend());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("muserLastCreated");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        dto.setStudyStatusCode(StudyStatusCode.APPROVED);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("", decorator.getAmend());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        dto.setStudyStatusCode(StudyStatusCode.WITHDRAWN);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("", decorator.getAmend());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        dto.setStudyStatusCode(StudyStatusCode.COMPLETE);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("", decorator.getAmend());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        dto.setStudyStatusCode(StudyStatusCode.ADMINISTRATIVELY_COMPLETE);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("", decorator.getAmend());
    }

    @Test
    public void testGetDocumentWorkflowStatusDate() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusDate(null);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("",decorator.getDocumentWorkflowStatusDate());
        dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusDate(Calendar.getInstance().getTime());
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertNotNull(decorator.getDocumentWorkflowStatusDate());
    }
    @Test
    public void testGetDocumentWorkflowStatusCode() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setStudyProtocolId(1L);
        dto.setSearcherTrialOwner(true);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals(DocumentWorkflowStatusCode.ABSTRACTED,decorator.getDocumentWorkflowStatusCode());

        dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser(null);
        PageContext context = new MockPageContext(null, request, new MockHttpServletResponse());
        decorator.init(context, null, null);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertNull(decorator.getDocumentWorkflowStatusCode());
    }
    @Test
    public void testGetExpandedAccessIndicator() {
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("No", decorator.getExpandedAccessIndicator());
        dto = new StudyIndldeDTO();
        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Yes", decorator.getExpandedAccessIndicator());
        dto = new StudyIndldeDTO();
        dto.setExpandedAccessIndicator(null);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("No", decorator.getExpandedAccessIndicator());
    }
    @Test
    public void testGetInstProgramCode() {
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.INDUSTRY));
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertNull(decorator.getInstProgramCode());
        dto = new StudyIndldeDTO();
        dto.setHolderTypeCode(null);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertNull(decorator.getInstProgramCode());
        dto = new StudyIndldeDTO();
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NIH));
        dto.setNihInstHolderCode(CdConverter.convertToCd(NihInstituteCode.CSR));
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals(NihInstituteCode.CSR.getCode(), decorator.getInstProgramCode());
        dto = new StudyIndldeDTO();
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NIH));
        dto.setNihInstHolderCode(null);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertNull(decorator.getInstProgramCode());

        dto = new StudyIndldeDTO();
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NCI));
        dto.setNciDivProgHolderCode(CdConverter.convertToCd(NciDivisionProgramCode.CCR));
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals(NciDivisionProgramCode.CCR.getCode(), decorator.getInstProgramCode());
        dto = new StudyIndldeDTO();
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NCI));
        dto.setNciDivProgHolderCode(null);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertNull(decorator.getInstProgramCode());
    }
    @Test
    public void testGetProprietaryTypeCode() {
        TrialDocumentWebDTO dto = new TrialDocumentWebDTO();
        dto.setTypeCode("Protocol Document");
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Proprietary Template", decorator.getProprietaryTypeCode());
        dto = new TrialDocumentWebDTO();
        dto.setTypeCode("Type Code");
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Type Code", decorator.getProprietaryTypeCode());
    }
    @Test
    public void testGetCompletePartialSubmission() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setStudyProtocolId(1L);
        dto.setNciIdentifier("");
        dto.setSearcherTrialOwner(true);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Complete",decorator.getCompletePartialSubmission());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("muserLastCreated");
        dto.setNciIdentifier("");
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("",decorator.getCompletePartialSubmission());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("muserLastCreated");
        dto.setNciIdentifier("NCI-2010-00001");
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("",decorator.getCompletePartialSubmission());
    }
    @Test
    public void testGetDeletePartialSubmission() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setNciIdentifier("");
        dto.setStudyProtocolId(1L);
        dto.setSearcherTrialOwner(true);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Delete",decorator.getDeletePartialSubmission());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("muserLastCreated");
        dto.setNciIdentifier("");
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("",decorator.getDeletePartialSubmission());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("muserLastCreated");
        dto.setNciIdentifier("NCI-2010-00001");
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("",decorator.getDeletePartialSubmission());
    }

    @Test
    public void testGetTrialCategory() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("userLastCreated");
        dto.setProprietaryTrial(true);
        dto.setNciIdentifier("");
        dto.setStudyProtocolId(1L);
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Abbreviated", decorator.getTrialCategory());

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setUserLastCreated("muserLastCreated");
        dto.setProprietaryTrial(false);
        dto.setNciIdentifier("");
        decorator.initRow(dto, currentViewIndex, currentListIndex);
        assertEquals("Complete", decorator.getTrialCategory());
    }

}
