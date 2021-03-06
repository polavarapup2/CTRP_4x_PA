/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAJsonUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.RestClient;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TrialInfoHelperUtil;
import gov.nih.nci.pa.util.TrialInfoMergeHelper;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class RegulatoryInformationActionTest extends AbstractPaActionTest {

    private static RegulatoryInformationAction regulatoryInformationAction;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;
    private RestClient client = mock(RestClient.class);
    private TrialInfoMergeHelper helper = new TrialInfoMergeHelper();
    private TrialInfoHelperUtil helperUtil = new TrialInfoHelperUtil();
    private Ii id = IiConverter.convertToIi(1L);
    List<Long> identifiersList = new ArrayList<Long>();
    private AdditionalRegulatoryInfoDTO additionalRegInfoDTO = new AdditionalRegulatoryInfoDTO();
    Map<Long, String> identifierMap = new HashMap<Long, String>();
    private List<AdditionalRegulatoryInfoDTO> regulatoryDtoList = new ArrayList<AdditionalRegulatoryInfoDTO>();
    
    @Before
    public void setUp() throws PAException, IOException {
        regulatoryInformationAction = new RegulatoryInformationAction();
        regulatoryInformationAction.setLst(null);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, id);
        studyProtocolServiceLocal =  mock(StudyProtocolServiceLocal.class);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolServiceLocal);
        Long studyprotocolId = IiConverter.convertToLong(id);
        identifiersList.add(studyprotocolId);
        identifierMap.put(1L, "NCI-1000-0000");
        when(PaRegistry.getStudyProtocolService().getTrialNciId(identifiersList)).thenReturn(identifierMap);
        regulatoryInformationAction.setHelper(helper);
        additionalRegInfoDTO.setExported_from_us("true");
        additionalRegInfoDTO.setFda_regulated_device("true");
        additionalRegInfoDTO.setFda_regulated_drug("true");
        additionalRegInfoDTO.setPed_postmarket_surv("true");
        additionalRegInfoDTO.setPost_prior_to_approval("true");
        additionalRegInfoDTO.setDate_updated("1234455");
        regulatoryDtoList.add(additionalRegInfoDTO);
        String url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl();
        when(client.sendHTTPRequest(url +"?study_protocol_id=1&nci_id=\" + \"NCI-1000-0000\"", "GET", null)).thenReturn(
                PAJsonUtil.marshallJSON(regulatoryDtoList));
        when(client.sendHTTPRequest(url, "POST", PAJsonUtil.marshallJSON(additionalRegInfoDTO))).thenReturn("");
        helperUtil.setClient(client);
        helper.setTrialInfoHelperUtil(helperUtil);
    }
    
    @Test
    public void testUpdate() throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        spDTO.setStartDate(TsConverter.convertToTs(new Date()));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setPedPostmarketSurv("true");
        webDTO.setPostPriorToApproval("true");
        webDTO.setDataMonitoringIndicator("true");
        webDTO.setSection801Indicator("true");
        webDTO.setDelayedPostingIndicator("true");
        webDTO.setFdaRegulatedInterventionIndicator("true");
        regulatoryInformationAction.setWebDTO(webDTO);

        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(false));
        String result = regulatoryInformationAction.update();
        assertEquals("success", result);
        assertTrue(Boolean.valueOf(webDTO.getDataMonitoringIndicator()));
    }
    
    @Test
    public void testUpdateNegativeChecks() throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setSection801Indicator(null);
        spDTO.setFdaRegulatedIndicator(null);
        spDTO.setDelayedpostingIndicator(null);
        spDTO.setDataMonitoringCommitteeAppointedIndicator(null);
        spDTO.setStartDate(TsConverter.convertToTs(new Date()));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setPedPostmarketSurv("true");
        webDTO.setPostPriorToApproval("true");
        webDTO.setDataMonitoringIndicator(null);
        webDTO.setSection801Indicator(null);
        webDTO.setDelayedPostingIndicator(null);
        webDTO.setFdaRegulatedInterventionIndicator(null);
        regulatoryInformationAction.setWebDTO(webDTO);

        String result = regulatoryInformationAction.update();
        assertEquals("success", result);
        assertTrue(StringUtils.isEmpty(webDTO.getFdaRegulatedInterventionIndicator()));
        assertTrue(StringUtils.isEmpty(webDTO.getSection801Indicator()));
        assertTrue(StringUtils.isEmpty(webDTO.getDelayedPostingIndicator()));
        assertTrue(StringUtils.isEmpty(webDTO.getDataMonitoringIndicator()));
    }
    
    @Test
    public void updateException() throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        spDTO.setStartDate(TsConverter.convertToTs(new Date()));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
        webDTO.setFdaRegulatedDevice("true");
        webDTO.setFdaRegulatedDrug("true");
        webDTO.setExportedFromUs("true");
        webDTO.setPedPostmarketSurv("true");
        webDTO.setPostPriorToApproval("true");
        webDTO.setDataMonitoringIndicator("true");
        webDTO.setSection801Indicator("true");
        webDTO.setDelayedPostingIndicator("true");
        webDTO.setFdaRegulatedInterventionIndicator("true");
        regulatoryInformationAction.setWebDTO(webDTO);
        
        when (PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO)).thenThrow(
                new PAException("Error in updating Study Protocol."));
        regulatoryInformationAction.update();
        assertNotNull(getRequest().getAttribute("failureMessage"));
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
        spDTO.setStartDate(TsConverter.convertToTs(new Date()));
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
        spDTO.setStartDate(TsConverter.convertToTs(new Date()));
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(id)).thenReturn(spDTO);
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
       // assertEquals("Studies a U.S. FDA-regulated Drug Product is required field", 
       ///      regulatoryInformationAction.getFieldErrors().get("webDTO.fdaRegulatedDrug").get(0));
      //  assertEquals("Studies a U.S. FDA-regulated Device Product is required field", 
      //          regulatoryInformationAction.getFieldErrors().get("webDTO.fdaRegulatedDevice").get(0));
        
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
    
    @Test
    public void updateTestExceptionMicroService() throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        spDTO.setSection801Indicator(BlConverter.convertToBl(true));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        spDTO.setStartDate(TsConverter.convertToTs(new Date()));
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
        RestClient client1 = new RestClient();
        helperUtil.setClient(client1);
        String result = regulatoryInformationAction.update();
        assertEquals("success", result);
        assertNotNull(getRequest().getAttribute("failureMessage"));
       // assertTrue(StringUtils.containsIgnoreCase(getRequest().getAttribute("failureMessage")
       //         .toString(), "Error: Unable to get response from Rest server"));
    }
}
