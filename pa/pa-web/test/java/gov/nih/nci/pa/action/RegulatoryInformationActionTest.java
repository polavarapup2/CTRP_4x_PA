/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class RegulatoryInformationActionTest extends AbstractPaActionTest {

    private static RegulatoryInformationAction regulatoryInformationAction;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityServiceLocal;
    private RegulatoryInformationServiceLocal regRemote;
    private Ii id = IiConverter.convertToIi(1L);
    @Before
    public void setUp(){
        regulatoryInformationAction = new RegulatoryInformationAction();
        regulatoryInformationAction.setLst(null);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, id);
        studyProtocolServiceLocal =  mock(StudyProtocolServiceLocal.class);
        studyRegulatoryAuthorityServiceLocal = mock(StudyRegulatoryAuthorityServiceLocal.class);
        regRemote = mock(RegulatoryInformationServiceLocal.class);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolServiceLocal);
        when(PaRegistry.getStudyRegulatoryAuthorityService()).thenReturn(studyRegulatoryAuthorityServiceLocal);
        when(PaRegistry.getRegulatoryInformationService()).thenReturn(regRemote);
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.RegulatoryInformationAction#update()}.
     * @throws PAException 
     */
    @Test(expected=Exception.class)
    public void testUpdateException() throws PAException {
        regulatoryInformationAction.setLst("");
        regulatoryInformationAction.setSelectedRegAuth("");
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(false));
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
        assertEquals("Section 801 Indicator should be No for Non-interventional trials", 
             regulatoryInformationAction.getFieldErrors().get("webDTO.section801Indicator"));
    }

    @Test
    public void testUpdate() throws PAException {
        regulatoryInformationAction.setLst("1");
        regulatoryInformationAction.setSelectedRegAuth("1");
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        String result = regulatoryInformationAction.update();
        assertEquals("success", result);
        RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setDataMonitoringIndicator("false");
        webDTO.setFdaRegulatedInterventionIndicator("false");
        webDTO.setSection801Indicator("false");
        webDTO.setDelayedPostingIndicator("false");
        regulatoryInformationAction.setWebDTO(webDTO);
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        result = regulatoryInformationAction.update();
        assertEquals("success", result);
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
        StudyRegulatoryAuthorityDTO authorityDTO = new StudyRegulatoryAuthorityDTO();
        authorityDTO.setIdentifier(id);
        authorityDTO.setRegulatoryAuthorityIdentifier(id);
        List<Long> list = new ArrayList<Long>();
        list.add(3L);
        list.add(4L);
        regulatoryInformationAction.setSelectedRegAuth("2");
        regulatoryInformationAction.setLst("1");
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        when(PaRegistry.getStudyRegulatoryAuthorityService()
             .getCurrentByStudyProtocol(id)).thenReturn(authorityDTO);
        when(PaRegistry.getStudyRegulatoryAuthorityService()
             .getCurrentByStudyProtocol(id)).thenReturn(authorityDTO);
        when(PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityInfo(1L)).thenReturn(list);
        String result = regulatoryInformationAction.query();
        assertEquals("success", result);
        assertEquals("3", regulatoryInformationAction.getSelectedRegAuth());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.RegulatoryInformationAction#getRegAuthoritiesList()}.
     */
    @Test
    public void testGetRegAuthoritiesList() {
        regulatoryInformationAction.getRegAuthoritiesList();
        assertNotNull(regulatoryInformationAction.getRegIdAuthOrgList());
    }
    
    @Test
    public void testifGetRegAuthoritiesList() {
        getRequest().setupAddParameter("countryid", "1");
        regulatoryInformationAction.getRegAuthoritiesList();
        assertNotNull(regulatoryInformationAction.getRegIdAuthOrgList());
    }

}
