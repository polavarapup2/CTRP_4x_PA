package gov.nih.nci.registry.rest.domain;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StudyProtocolXmlLeanTest {

    @Test
    public void constructorNullTest() {
        List<StudyProtocolXmlLean> jaxbList = StudyProtocolXmlLean.getList(null);
        assertEquals(0, jaxbList.size());
    }

    @Test
    public void constructorEmptyListTest() {
        List<StudyProtocolQueryDTO> dtos = new ArrayList<StudyProtocolQueryDTO>();
        List<StudyProtocolXmlLean> jaxbList = StudyProtocolXmlLean.getList(dtos);
        assertEquals(0, jaxbList.size());
    }

    @Test
    public void constructorNciIdentifierTest() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        List<StudyProtocolQueryDTO> dtos = new ArrayList<StudyProtocolQueryDTO>();
        dtos.add(dto);
        List<StudyProtocolXmlLean> jaxbList = StudyProtocolXmlLean.getList(dtos);
        assertEquals(0, jaxbList.size());
        dto.setNciIdentifier("NCI-0000-00000");
        jaxbList = StudyProtocolXmlLean.getList(dtos);
        assertEquals(1, jaxbList.size());
    }

    @Test
    public void constructorDWFTest() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-0000-00000");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        List<StudyProtocolQueryDTO> dtos = new ArrayList<StudyProtocolQueryDTO>();
        dtos.add(dto);
        List<StudyProtocolXmlLean> jaxbList = StudyProtocolXmlLean.getList(dtos);
        assertEquals(0, jaxbList.size());
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        jaxbList = StudyProtocolXmlLean.getList(dtos);
        assertEquals(1, jaxbList.size());
    }
}
