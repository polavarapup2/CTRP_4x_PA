package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockPaRegistryServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.util.MockPaRegistryServiceLocator;

public class NCISpecificInformationActionTest extends AbstractPaActionTest {

    NCISpecificInformationAction nciSpecificInformationAction;
    NCISpecificInformationWebDTO nciSpecificInformationWebDTO;
    FamilyDTO familyDto;
    
    @Before
    public void setUp() throws PAException {
        nciSpecificInformationAction = new NCISpecificInformationAction();
        nciSpecificInformationWebDTO = new NCISpecificInformationWebDTO();
        nciSpecificInformationWebDTO.setAccrualReportingMethodCode("");
        nciSpecificInformationAction.setNciSpecificInformationWebDTO(nciSpecificInformationWebDTO);

        familyDto = new FamilyDTO(1L);
        for (int i =0; i < 3; i++) {
            ProgramCodeDTO pgc = new ProgramCodeDTO();
            pgc.setActive(true);
            pgc.setId(i + 1L);
            pgc.setProgramName("Program Code " + i);
            pgc.setProgramCode("PG" + i);
            familyDto.getProgramCodes().add(pgc);
        }
        FamilyProgramCodeService familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
        when(familyProgramCodeService.getFamilyDTOByPoId(1L)).thenReturn(familyDto);
        
        
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
    }
    
    @Test
    public void testExecute() {
        String result = nciSpecificInformationAction.execute();
        assertEquals("success", result);
    }

    @Test
    public void testQuery() throws Exception {
    	PAServiceUtils svcUtils = mock(PAServiceUtils.class);
    	when(svcUtils.getDuplicateOrganizationIi(any(Ii.class))).thenReturn(IiConverter.convertToIi("1"));
    	Organization org = new Organization();
    	org.setId(1L);
    	when(svcUtils.getOrCreatePAOrganizationByIi(any(Ii.class))).thenReturn(org);
    	nciSpecificInformationAction.setPaServiceUtil(svcUtils);		
        String result = nciSpecificInformationAction.query();
        assertEquals("success", result);
    }

    @Test
    public void testUpdate() {
        String result = nciSpecificInformationAction.update();
        assertEquals("error", result);        
    }
    
    @Test
    public void testUpdate2() {
        nciSpecificInformationWebDTO.setProgramCodeText("PCT");
        nciSpecificInformationWebDTO.setCtroOverride(true);
        nciSpecificInformationWebDTO.setConsortiaTrialCategoryCode("CTCC");
        nciSpecificInformationWebDTO.setSummaryFourFundingCategoryCode("SFFCC");  
        nciSpecificInformationWebDTO.setAccrualReportingMethodCode("ARMC");
        nciSpecificInformationWebDTO.setCtroOverideFlagComments("This is a test comment");
        String result = nciSpecificInformationAction.update();
        assertEquals("success", result);
    }

    @Test(expected=Exception.class)
    public void testDisplayOrg() {
        String result = nciSpecificInformationAction.displayOrg();
        assertEquals("error", result);
    }
    
    @Test
    public void testDisplayOrg2() {
    	getRequest().setupAddParameter("orgId", "1");
        String result = nciSpecificInformationAction.displayOrg();
        SummaryFourSponsorsWebDTO dto = new SummaryFourSponsorsWebDTO();
        dto.setRowId("1");
        dto.setOrgId("1");
        dto.setOrgName("OrgName");
        assertEquals("displayOrgFld", result);
        getRequest().getSession().setAttribute("summary4Sponsors", Arrays.asList(dto));
        getRequest().setupAddParameter("orgId", "1");
        result = nciSpecificInformationAction.displayOrg();
        assertEquals("displayOrgFld", result);
    }

    
    @Test
    public void testDelete() {
    	getRequest().setupAddParameter("uuid", "1");
        List<SummaryFourSponsorsWebDTO> summary4SponsorsList = new ArrayList<SummaryFourSponsorsWebDTO>();
        SummaryFourSponsorsWebDTO webDto = new SummaryFourSponsorsWebDTO(); 
        webDto.setRowId("1");    
        summary4SponsorsList.add(webDto);
        webDto = new SummaryFourSponsorsWebDTO();
        webDto.setRowId("2");
        summary4SponsorsList.add(webDto);
        getSession().setAttribute("summary4Sponsors", summary4SponsorsList);
        String result = nciSpecificInformationAction.deleteSummaryFourOrg();
        assertEquals("displayOrgFld", result);
    }

    @Test
    public void testCoverage() {
        String result = nciSpecificInformationAction.lookup1();
        assertEquals("success", result);
        nciSpecificInformationAction.setChosenOrg("chosenOrg");
        assertEquals("chosenOrg", nciSpecificInformationAction.getChosenOrg());
        nciSpecificInformationAction.getNciSpecificInformationWebDTO();
    }


    @Test
    public void testLoadAndBindProgramCodes() throws Exception {
        StudyProtocolQueryDTO summary = (StudyProtocolQueryDTO) getSession().getAttribute(Constants.TRIAL_SUMMARY);
        summary.setLeadOrganizationPOId(1L); //National Cancer Institute
        NCISpecificInformationWebDTO gtdDTO = new NCISpecificInformationWebDTO();
        nciSpecificInformationAction.setNciSpecificInformationWebDTO(gtdDTO);
        nciSpecificInformationAction.loadProgramCodes();
        assertFalse(nciSpecificInformationAction.getProgramCodeList().isEmpty());
        assertTrue(gtdDTO.getProgramCodes().isEmpty());

        nciSpecificInformationAction.setProgramCodeIds(Arrays.asList(1L, 2L));
        nciSpecificInformationAction.bindProgramCodes();
        assertFalse(gtdDTO.getProgramCodes().isEmpty());

    }


    @Test
    public void testLoadAndSync() throws Exception {
        StudyProtocolQueryDTO summary = (StudyProtocolQueryDTO) getSession().getAttribute(Constants.TRIAL_SUMMARY);
        summary.setLeadOrganizationPOId(1L); //National Cancer Institute
        NCISpecificInformationWebDTO gtdDTO = new NCISpecificInformationWebDTO();
        nciSpecificInformationAction.setNciSpecificInformationWebDTO(gtdDTO);
        nciSpecificInformationAction.loadProgramCodes();
        assertFalse(nciSpecificInformationAction.getProgramCodeList().isEmpty());
        assertTrue(gtdDTO.getProgramCodes().isEmpty());
        assertTrue(CollectionUtils.isEmpty(nciSpecificInformationAction.getProgramCodeIds()));
        ProgramCodeDTO pgc = new ProgramCodeDTO();
        pgc.setActive(true);
        pgc.setId(1L);
        pgc.setProgramName("Program Code 1");
        pgc.setProgramCode("PG1");
        gtdDTO.setProgramCodes(Arrays.asList(pgc));

        nciSpecificInformationAction.syncProgramCodes();
        assertFalse(nciSpecificInformationAction.getProgramCodeIds().isEmpty());

    }

}
