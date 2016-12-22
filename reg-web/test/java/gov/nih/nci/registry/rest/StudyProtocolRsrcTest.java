package gov.nih.nci.registry.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.registry.rest.domain.StudyProtocolXmlLean;
import gov.nih.nci.registry.rest.exception.BadRequestException;
import gov.nih.nci.registry.rest.exception.BadRequestExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StudyProtocolRsrcTest {
    ProtocolQueryServiceLocal pcs;
    StudyProtocolQueryDTO resultDto;

    /**
     * Set up services.
     */
    @Before
    public void setUpServices() throws Exception {
        resultDto = new StudyProtocolQueryDTO();
        resultDto.setNciIdentifier("nciIdentifier");
        resultDto.setOfficialTitle("officialTitls");
        resultDto.setPhaseName("phaseName");
        resultDto.setLeadOrganizationName("leadOrganizationName");
        resultDto.setPiFullName("piFullName");
        resultDto.setStudyStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        resultDto.setNctIdentifier("nctIdentifier");
        resultDto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        List<StudyProtocolQueryDTO> result = new ArrayList<StudyProtocolQueryDTO>();
        result.add(resultDto);
        
        
      
        pcs = mock(ProtocolQueryServiceLocal.class);
        when(pcs.getStudyProtocolByAgentNsc(anyString())).thenReturn(result);
        
        when(
                pcs
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenAnswer(new Answer<List<StudyProtocolQueryDTO>>() {
                    @Override
                    public List<StudyProtocolQueryDTO> answer(InvocationOnMock invocation) throws Throwable {
                        Object[] arguments = invocation.getArguments();
                        if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                            StudyProtocolQueryCriteria studyProtocolQueryCriteria  = (StudyProtocolQueryCriteria) arguments[0];
                            
                          List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
                            
                            if (studyProtocolQueryCriteria.getStudyProtocolId()!=null ){
                                
                                StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO(); 
                                studyProtocolQueryDTO = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTO.setStudyProtocolId(1l);
                                studyProtocolQueryDTO.setOfficialTitle("officialTitls");
                                studyProtocolQueryDTO.setPhaseName("phaseName1");
                                studyProtocolQueryDTO.setLeadOrganizationName("leadOrganizationNameForPaId");
                                studyProtocolQueryDTO.setPiFullName("piFullName1");
                                studyProtocolQueryDTO.setStudyStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
                                studyProtocolQueryDTO.setDocumentWorkflowStatusDate(new Date());
                                list.add(studyProtocolQueryDTO);
                               
                            }
                            else if (studyProtocolQueryCriteria.getIdentifierType().equals(IdentifierType.NCI.getCode()) &&
                                    studyProtocolQueryCriteria.getNciIdentifier().equals("nci123")) {
                                StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO(); 
                                studyProtocolQueryDTO = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTO.setStudyProtocolId(1l);
                                studyProtocolQueryDTO.setOfficialTitle("officialTitls");
                                studyProtocolQueryDTO.setPhaseName("phaseName");
                                studyProtocolQueryDTO.setLeadOrganizationName("leadOrganizationNameForNciId");
                                studyProtocolQueryDTO.setPiFullName("piFullName");
                                studyProtocolQueryDTO.setStudyStatusCode(StudyStatusCode.APPROVED);
                                studyProtocolQueryDTO.setDocumentWorkflowStatusDate(new Date());
                                list.add(studyProtocolQueryDTO);
                            }
                            else if (studyProtocolQueryCriteria.getIdentifierType().equals(IdentifierType.CTEP.getCode()) &&
                                    studyProtocolQueryCriteria.getCtepIdentifier().equals("ctep123")) {
                                StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO(); 
                                studyProtocolQueryDTO = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTO.setStudyProtocolId(1l);
                                studyProtocolQueryDTO.setOfficialTitle("officialTitls");
                                studyProtocolQueryDTO.setPhaseName("phaseName");
                                studyProtocolQueryDTO.setLeadOrganizationName("leadOrganizationNameForCtepId");
                                studyProtocolQueryDTO.setPiFullName("piFullName");
                                studyProtocolQueryDTO.setStudyStatusCode(StudyStatusCode.APPROVED);
                                studyProtocolQueryDTO.setDocumentWorkflowStatusDate(new Date());
                                list.add(studyProtocolQueryDTO);
                            }
                            else if (studyProtocolQueryCriteria.getIdentifierType().equals(IdentifierType.DCP.getCode()) &&
                                    studyProtocolQueryCriteria.getDcpIdentifier().equals("dcp123")) {
                                StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO(); 
                                studyProtocolQueryDTO = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTO.setStudyProtocolId(1l);
                                studyProtocolQueryDTO.setOfficialTitle("officialTitls");
                                studyProtocolQueryDTO.setPhaseName("phaseName");
                                studyProtocolQueryDTO.setLeadOrganizationName("leadOrganizationNameForDcpId");
                                studyProtocolQueryDTO.setPiFullName("piFullName");
                                studyProtocolQueryDTO.setStudyStatusCode(StudyStatusCode.APPROVED);
                                studyProtocolQueryDTO.setDocumentWorkflowStatusDate(new Date());
                                list.add(studyProtocolQueryDTO);
                            }
                            else if (studyProtocolQueryCriteria.getIdentifierType().equals(IdentifierType.NCT.getCode()) &&
                                    studyProtocolQueryCriteria.getNctNumber().equals("nct123")) {
                                StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO(); 
                                studyProtocolQueryDTO = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTO.setStudyProtocolId(1l);
                                studyProtocolQueryDTO.setOfficialTitle("officialTitls");
                                studyProtocolQueryDTO.setPhaseName("phaseName");
                                studyProtocolQueryDTO.setLeadOrganizationName("leadOrganizationNameForNctId");
                                studyProtocolQueryDTO.setPiFullName("piFullName");
                                studyProtocolQueryDTO.setStudyStatusCode(StudyStatusCode.APPROVED);
                                studyProtocolQueryDTO.setDocumentWorkflowStatusDate(new Date());
                                list.add(studyProtocolQueryDTO);
                            }
                          
                           return list;
                        }
                       return null;
                    } 
                });
        
        
        ServiceLocator sl = mock(ServiceLocator.class);
        when(sl.getProtocolQueryService()).thenReturn(pcs);
        PaRegistry.getInstance().setServiceLocator(sl);
    }

    @Test
    public void getStudiesXMLFound() throws Exception {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response resp = rsrc.getStudiesXML("123456");
        assertEquals(200, resp.getStatus());
    }

    @Test(expected = BadRequestException.class)
    public void getStudiesXMLNullTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        rsrc.getStudiesXML(null);
    }

    @Test(expected = BadRequestException.class)
    public void getStudiesXMLEmptyTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        rsrc.getStudiesXML("");
    }

    @Test(expected = BadRequestException.class)
    public void getStudiesXMLAlphaTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        rsrc.getStudiesXML("a");
    }
    
    @Test
    public void getStudiesXMLExceptionTest() {
        try {
            StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
            rsrc.getStudiesXML("a");
        } catch (BadRequestException bre) {
            BadRequestExceptionHandler breh = new BadRequestExceptionHandler();
            Response test = breh.toResponse(bre);
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), test.getStatus());
        }
    }

    @Test
    public void getStudiesXMLInternalServerErrorTest() throws Exception {
        when(pcs.getStudyProtocolByAgentNsc(anyString())).thenThrow(new RuntimeException());
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response resp = rsrc.getStudiesXML("123456");
        assertEquals(500, resp.getStatus());
    }
    
    @Test
    public void getByPaIdTest() throws Exception {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response response = rsrc.getStudiesXMLById("pa", "1");
        assertEquals(200, response.getStatus());
        
        GenericEntity<?>genericEntity = (GenericEntity)response.getEntity();
        List<StudyProtocolXmlLean> list = (ArrayList<StudyProtocolXmlLean>)genericEntity.getEntity();
        
        assertEquals(1,list.size());
        StudyProtocolXmlLean studyProtocolXmlLean = list.get(0);
        assertEquals("leadOrganizationNameForPaId" ,studyProtocolXmlLean.getLeadOrganizationName());
    }
    
    @Test
    public void getByNciIdTest() throws Exception {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response response = rsrc.getStudiesXMLById("nci", "nci123");
        assertEquals(200, response.getStatus());
        
        GenericEntity<?>genericEntity = (GenericEntity)response.getEntity();
        List<StudyProtocolXmlLean> list = (ArrayList<StudyProtocolXmlLean>)genericEntity.getEntity();
        
        assertEquals(1,list.size());
        StudyProtocolXmlLean studyProtocolXmlLean = list.get(0);
        assertEquals("leadOrganizationNameForNciId" ,studyProtocolXmlLean.getLeadOrganizationName());
    }
    
    @Test
    public void getByCtepIdTest() throws Exception {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response response = rsrc.getStudiesXMLById("ctep", "ctep123");
        assertEquals(200, response.getStatus());
        
        GenericEntity<?>genericEntity = (GenericEntity)response.getEntity();
        List<StudyProtocolXmlLean> list = (ArrayList<StudyProtocolXmlLean>)genericEntity.getEntity();
        
        assertEquals(1,list.size());
        StudyProtocolXmlLean studyProtocolXmlLean = list.get(0);
        assertEquals("leadOrganizationNameForCtepId" ,studyProtocolXmlLean.getLeadOrganizationName());
    }
    
    @Test
    public void getByDcpIdTest() throws Exception {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response response = rsrc.getStudiesXMLById("dcp", "dcp123");
        assertEquals(200, response.getStatus());
        
        GenericEntity<?>genericEntity = (GenericEntity)response.getEntity();
        List<StudyProtocolXmlLean> list = (ArrayList<StudyProtocolXmlLean>)genericEntity.getEntity();
        
        assertEquals(1,list.size());
        StudyProtocolXmlLean studyProtocolXmlLean = list.get(0);
        assertEquals("leadOrganizationNameForDcpId" ,studyProtocolXmlLean.getLeadOrganizationName());
    }
    
    @Test
    public void getByNctIdTest() throws Exception {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response response = rsrc.getStudiesXMLById("nct", "nct123");
        assertEquals(200, response.getStatus());
        
        GenericEntity<?>genericEntity = (GenericEntity)response.getEntity();
        List<StudyProtocolXmlLean> list = (ArrayList<StudyProtocolXmlLean>)genericEntity.getEntity();
        
        assertEquals(1,list.size());
        StudyProtocolXmlLean studyProtocolXmlLean = list.get(0);
        assertEquals("leadOrganizationNameForNctId" ,studyProtocolXmlLean.getLeadOrganizationName());
    }
    
    @Test
    public void getByInvalidIdTest() throws Exception {
        try {
            StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
            rsrc.getStudiesXMLById("test", "test");
        } catch (BadRequestException bre) {
            BadRequestExceptionHandler breh = new BadRequestExceptionHandler();
            Response test = breh.toResponse(bre);
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), test.getStatus());
        }
    }
    
   
}
