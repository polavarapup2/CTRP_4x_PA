/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class RegulatoryInformationActionTest extends AbstractPaActionTest {

    private static RegulatoryInformationAction regulatoryInformationAction;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;

    private Ii id = IiConverter.convertToIi(1L);
    @Before
    public void setUp(){
        regulatoryInformationAction = new RegulatoryInformationAction();
        regulatoryInformationAction.setLst(null);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, id);
        studyProtocolServiceLocal =  mock(StudyProtocolServiceLocal.class);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolServiceLocal);

    }
    
    @Test
    public void testUpdate() throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setPedPostmarketSurv("true");
        webDTO.setPostPriorToApproval("true");
        webDTO.setDataMonitoringIndicator("true");
        webDTO.setFdaRegulatedInterventionIndicator("true");
        webDTO.setSection801Indicator("true");
        webDTO.setDelayedPostingIndicator("true");
        regulatoryInformationAction.setWebDTO(webDTO);
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(false));
        String result = regulatoryInformationAction.update();
        assertEquals("success", result);
        assertTrue(Boolean.valueOf(webDTO.getDataMonitoringIndicator()));
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.RegulatoryInformationAction#query()}.
     * @throws PAException 
     */
    @Test(expected=Exception.class)
    public void testQueryException() {
        String result = regulatoryInformationAction.query();
        assertEquals("success", result);
    }
    
    @Test
    public void testQuery() throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        regulatoryInformationAction.setLst("1");
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        String result = regulatoryInformationAction.query();
        assertEquals("success", result);
    }

    
    @Test
    public void testUpdateException() throws PAException {
        RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
     
        webDTO.setFdaRegulatedDevice("");
        webDTO.setFdaRegulatedDrug("");
        webDTO.setExportedFromUs("");
        regulatoryInformationAction.setWebDTO(webDTO);
        
        StudyProtocolDTO spDTO = new NonInterventionalStudyProtocolDTO();
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
        assertEquals("Studies a U.S. FDA-regulated Drug Product is required field", 
             regulatoryInformationAction.getFieldErrors().get("webDTO.fdaRegulatedDrug").get(0));
        assertEquals("Studies a U.S. FDA-regulated Device Product is required field", 
                regulatoryInformationAction.getFieldErrors().get("webDTO.fdaRegulatedDevice").get(0));
        assertEquals("Product Exported from the U.S is required field", 
                regulatoryInformationAction.getFieldErrors().get("webDTO.exportedFromUs").get(0));
        
        webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setDelayedPostingIndicator("true");
        webDTO.setPostPriorToApproval("");
        regulatoryInformationAction.setWebDTO(webDTO);
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
        assertEquals("Post Prior to U.S. FDA Approval or Clearance is required field", 
                regulatoryInformationAction.getFieldErrors().get("webDTO.postPriorToApproval").get(0));
       
        webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setDelayedPostingIndicator("true");
        webDTO.setPostPriorToApproval("true");
        webDTO.setPedPostmarketSurv("");
        regulatoryInformationAction.setWebDTO(webDTO);
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
        assertEquals("Pediatric Post-market Surveillance is required field", 
                regulatoryInformationAction.getFieldErrors().get("webDTO.pedPostmarketSurv").get(0)); 
        
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(false));
        webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setDelayedPostingIndicator("true");
        webDTO.setPostPriorToApproval("");
        webDTO.setFdaRegulatedInterventionIndicator("");
        regulatoryInformationAction.setWebDTO(webDTO);
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
        assertEquals("FDA Regulated Intervention Indicator is required field", 
                regulatoryInformationAction.getFieldErrors().get("webDTO.fdaRegulatedInterventionIndicator").get(0)); 
        
        webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setDelayedPostingIndicator("true");
        webDTO.setPostPriorToApproval("true");
        webDTO.setFdaRegulatedInterventionIndicator("true");
        webDTO.setSection801Indicator("true");
        regulatoryInformationAction.setWebDTO(webDTO);
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
        assertEquals("Section 801 Indicator should be No for Non-interventional trials", 
                regulatoryInformationAction.getFieldErrors().get("webDTO.section801Indicator").get(0)); 
        
    }
    
    
}
