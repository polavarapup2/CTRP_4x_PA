package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.AdditionalEligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAWebUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EligibilityCriteriaActionTest extends AbstractPaActionTest {

    EligibilityCriteriaAction eligibilityCriteriaAction;
    StudyProtocolQueryDTO spDTO;  
    ISDesignDetailsWebDTO dto;
    private AdditionalEligibilityCriteriaDTO additionalDTO = new AdditionalEligibilityCriteriaDTO();
    private String url;
    private RestClient client = mock(RestClient.class);
    private TrialInfoMergeHelper helper = new TrialInfoMergeHelper();
    private List<AdditionalEligibilityCriteriaDTO> eligibilityDtoList = new ArrayList<AdditionalEligibilityCriteriaDTO>();
    @Before
    public void setUp() throws PAException, IOException{
        eligibilityCriteriaAction = new EligibilityCriteriaAction();
       
        spDTO = new StudyProtocolQueryDTO();
        spDTO.setStudyProtocolType("NonInterventionalStudyProtocol");
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        getSession().setAttribute(Constants.TRIAL_SUMMARY, spDTO);
        
        dto = new ISDesignDetailsWebDTO();
        eligibilityCriteriaAction.setWebDTO(dto);
        url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl();
        additionalDTO.setGender("Yes");
        additionalDTO.setGenderEligibilityDescription("Desc1");
        additionalDTO.setStudyProtocolId("1");
        additionalDTO.setNciId("NCI-2000-12222");
        eligibilityDtoList.add(additionalDTO);
        
        String responseStr = PAWebUtil.marshallJSON(eligibilityDtoList);
        when(client.sendHTTPRequest(url + "?study_protocol_id=1&nci_id=NCI-2000-12222", "GET", null)).thenReturn(responseStr);
        when(client.sendHTTPRequest(url, "POST", PAWebUtil.marshallJSON(additionalDTO))).thenReturn("");
        when(client.sendHTTPRequest(url + "/1", "PUT",  PAWebUtil.marshallJSON(additionalDTO))).thenReturn("");
        helper.setClient(client);    
        eligibilityCriteriaAction.setHelper(helper);
    }
    
    @Test
    public void testQuery() {
        String result = eligibilityCriteriaAction.query();
        assertEquals("eligibility", result);
    }

    @Test
    public void testSaveWithErrors() {
        UnitsCode unit = UnitsCode.getByCode("Years");
        double retMaxVal = unit.getMinuteMultiplicationFactor() * Double.valueOf(1);
        assertNotNull(retMaxVal);
        eligibilityCriteriaAction.save();
        assertTrue(eligibilityCriteriaAction.hasFieldErrors());
    }
    @Test
    public void testSave() {
    	eligibilityCriteriaAction.setStudyPopulationDescription("test");
    	eligibilityCriteriaAction.setSamplingMethodCode("test");
    	eligibilityCriteriaAction.setAcceptHealthyVolunteersIndicator("false");
    	eligibilityCriteriaAction.setEligibleGenderCode("male");
    	eligibilityCriteriaAction.setEligibleGenderCodeId("1");
    	eligibilityCriteriaAction.setMaximumValue("45");
    	eligibilityCriteriaAction.setMaxValueUnit("Years");
    	eligibilityCriteriaAction.setMinimumValue("23");
    	eligibilityCriteriaAction.setMinValueUnit("Years");
    	eligibilityCriteriaAction.setValueUnit("10");
    	eligibilityCriteriaAction.setValueId("1");
    	eligibilityCriteriaAction.setGender("Yes");
    	eligibilityCriteriaAction.setGenderEligibilityDescription("Desc1");
    	assertEquals("eligibility",eligibilityCriteriaAction.save());
    }
    @Test
    public void testInput() {
        String result = eligibilityCriteriaAction.input();
        assertEquals("eligibilityAdd", result);
    }
    @Test
    public void testCreate() {
    	ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    	webDTO.setStructuredType("Structured");
    	webDTO.setDisplayOrder("1");
    	webDTO.setTextDescription("test");
    	webDTO.setCriterionName("test");
    	webDTO.setOperator("=");
    	webDTO.setValueIntegerMax("4");
    	webDTO.setValueIntegerMin("1");
    	webDTO.setValueText("test");
    	webDTO.setLabTestNameValueText("test lab name");
    	webDTO.setUnit("test unit");
    	webDTO.setId("1");
    	eligibilityCriteriaAction.setWebDTO(webDTO);
    	List<ISDesignDetailsWebDTO> webDTOList = new ArrayList<ISDesignDetailsWebDTO>();
    	webDTOList.add(webDTO);
    	eligibilityCriteriaAction.setEligibilityList(webDTOList);    	
        String result = eligibilityCriteriaAction.create();
        assertEquals("eligibility", result);
    }
    @Test
    public void testCreateWithError() {
        String result = eligibilityCriteriaAction.create();
        assertEquals("eligibilityAdd", result);
    }

    @Test
    public void testEdit() {
        eligibilityCriteriaAction.setId(1L);
        String result = eligibilityCriteriaAction.edit();
        assertEquals("eligibilityAdd", result);
    }

    @Test
    public void testUpdateWithErrors() {
        eligibilityCriteriaAction.update();
        assertTrue(eligibilityCriteriaAction.hasFieldErrors());
    }
    @Test
    public void testUpdate() {
    	ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    	webDTO.setStructuredType("Structured");
    	webDTO.setDisplayOrder("3");
    	webDTO.setTextDescription("test");
    	webDTO.setCriterionName("test");
    	webDTO.setOperator("=");
    	webDTO.setValueIntegerMax("4");
    	webDTO.setValueIntegerMin("1");
    	webDTO.setValueText("test");
    	eligibilityCriteriaAction.setWebDTO(webDTO);
    	assertEquals("eligibility",eligibilityCriteriaAction.update());
    }

    @Test
    public void testDelete() {
        eligibilityCriteriaAction.setId(null);
        eligibilityCriteriaAction.setObjectsToDelete(new String[] {"1"});
        String result =  eligibilityCriteriaAction.delete();
        assertEquals("eligibility", result);
    }
    @Test(expected=Exception.class)
	public void testDisplaySelectedTypeDoseUnitOfMeasurement() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "UnitOfMeasurement");
		getRequest().setupAddParameter("divName", "loadUOM");
		eligibilityCriteriaAction.displaySelectedType();
	}
    @Test
    public void testReOrder() {
    	ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    	webDTO.setStructuredType("Structured");
    	webDTO.setDisplayOrder("3");
    	webDTO.setTextDescription("test");
    	webDTO.setCriterionName("test");
    	webDTO.setOperator("=");
    	webDTO.setValueIntegerMax("4");
    	webDTO.setValueIntegerMin("1");
    	webDTO.setValueText("test");
    	webDTO.setId("1");
    	ISDesignDetailsWebDTO webDTO1 = new ISDesignDetailsWebDTO();
    	webDTO1.setStructuredType("Structured");
    	webDTO1.setDisplayOrder("5");
    	webDTO1.setTextDescription("test");
    	webDTO1.setCriterionName("test");
    	webDTO1.setOperator("=");
    	webDTO1.setValueIntegerMax("4");
    	webDTO1.setValueIntegerMin("1");
    	webDTO1.setValueText("test");
    	webDTO1.setId("2");
    	List<ISDesignDetailsWebDTO> webDTOList = new ArrayList<ISDesignDetailsWebDTO>();
    	webDTOList.add(webDTO);
    	webDTOList.add(webDTO1);
    	eligibilityCriteriaAction.setEligibilityList(webDTOList);
    	assertEquals("eligibility",eligibilityCriteriaAction.reOrder());
    }
}
