/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.service.MockCorrelationUtils;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

public class TrialValidationActionTest extends AbstractPaActionTest {

    private final TrialValidationAction trialValidationAction = new TrialValidationAction();
    RegistryUserService regUserSvc = mock(RegistryUserService.class);
    FamilyProgramCodeService familyProgramCodeService = mock(FamilyProgramCodeService.class);
    RegistryUser regUser = new RegistryUser();
    FamilyDTO familyDto;


    /**
     * Initialization method.
     *
     * @throws PAException
     */
    @Before
    public void setup() throws PAException {
        trialValidationAction.prepare();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        PAServiceUtils svcUtils = mock(PAServiceUtils.class);
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn("NCT-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.CTEP_IDENTIFIER_TYPE))).thenReturn("CTEP-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.DCP_IDENTIFIER_TYPE))).thenReturn("DCP-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn("NCI-1");

        TrialHelper trialHelper = new TrialHelper();
        trialHelper.setCorrelationUtils(new MockCorrelationUtils());
        trialHelper.setPaServiceUtils(svcUtils);
        trialHelper.setLookupTableService(lookupSvc);
        trialHelper.setOrgService(orgSvc);

        regUser.setId(1L);
        when(regUserSvc.getUser(anyString())).thenReturn(regUser);

        familyDto = new FamilyDTO(1L);
        for (int i =0; i < 3; i++) {
            ProgramCodeDTO pgc = new ProgramCodeDTO();
            pgc.setActive(true);
            pgc.setId(i + 1L);
            pgc.setProgramName("Program Code " + i);
            pgc.setProgramCode("PG" + i);
            familyDto.getProgramCodes().add(pgc);
        }

        when(familyProgramCodeService.getFamilyDTOByPoId(1L)).thenReturn(familyDto);

        trialValidationAction.setRegistryUserService(regUserSvc);
        trialValidationAction.setFamilyProgramCodeService(familyProgramCodeService);
        trialValidationAction.setTrialHelper(trialHelper);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#query()}.
     */
    @Test
    public void testQuery() {
        assertEquals("edit", trialValidationAction.query());
    }
    

    
    @Test
    public void displayLeadOrganization() {
        getRequest().setupAddParameter("orgId", "undefined");
        assertEquals("display_org", trialValidationAction.displayLeadOrganization());
        getRequest().setupAddParameter("orgId", "1");
        assertEquals("display_lead_org", trialValidationAction.displayLeadOrganization());
    }

    @Test
    public void testDisplayNullLeadPI() {
        getRequest().setupAddParameter("persId", "undefined");
        assertEquals("display_lead_prinicipal_inv", trialValidationAction.displayLeadPrincipalInvestigator());
        assertNull(trialValidationAction.getGtdDTO().getPiIdentifier());
    }

    @Test
    public void testDisplayLeadPI() {
        getRequest().setupAddParameter("persId", "1");
        assertEquals("display_lead_prinicipal_inv", trialValidationAction.displayLeadPrincipalInvestigator());
        assertEquals("1", trialValidationAction.getGtdDTO().getPiIdentifier());
    }

    @Test
    public void testDisplaySelectedSponsorUndefined() {
        getRequest().setupAddParameter("orgId", "undefined");
        assertEquals("display_selected_sponsor", trialValidationAction.displaySelectedSponsor());
    }

    @Test
    public void testDisplaySum4Undefined() {
        getRequest().setupAddParameter("orgId", "undefined");
        assertEquals("display_summary4funding_sponsor", trialValidationAction.displaySummary4FundingSponsor());
    }

    @Test
    public void testDisplaySum4() {
        getRequest().setupAddParameter("orgId", "1");
        assertEquals("display_summary4funding_sponsor", trialValidationAction.displaySummary4FundingSponsor());
        assertEquals("1", trialValidationAction.getGtdDTO().getSummaryFourOrgIdentifiers().get(0).getOrgId());
        getRequest().getSession().setAttribute("summary4Sponsors", 
        		Arrays.asList(trialValidationAction.getGtdDTO().getSummaryFourOrgIdentifiers().get(0)));
        getRequest().setupAddParameter("orgId", "1");
        assertEquals("display_summary4funding_sponsor", trialValidationAction.displaySummary4FundingSponsor());
    }

    @Test
    public void testDisplayCentralContactFailure() {
        getRequest().setupAddParameter("persId", "1");
        assertEquals("central_contact", trialValidationAction.displayCentralContact());
    }

    @Test
    public void testGetOrgContactsFailure() {
        getRequest().setupAddParameter("orgContactIdentifier", "1");
        assertEquals("display_org_contacts", trialValidationAction.getOrganizationContacts());
    }

    @Test
    public void testGetOrgContacts() {
        OrganizationalContactCorrelationServiceRemote orgContactSvc = mock(OrganizationalContactCorrelationServiceRemote.class);
        when(orgContactSvc.search((OrganizationalContactDTO) anyObject())).thenReturn(new ArrayList());
        trialValidationAction.setOrganizationalContactCorrelationService(orgContactSvc);
        getRequest().setupAddParameter("orgContactIdentifier", "1");
        assertEquals("display_org_contacts", trialValidationAction.getOrganizationContacts());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#update()}.
     * @throws PAException 
     */
    @Test
    public void testUpdate() throws PAException {
        trialValidationAction.getGtdDTO().setLeadOrganizationIdentifier("1");
        assertEquals("edit", trialValidationAction.update());
        GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.update());
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCtGovXmlRequired(true);
        gtdDTO.setPrimaryPurposeCode("Other");
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.update());
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCtGovXmlRequired(true);
        gtdDTO.setPrimaryPurposeCode("Other");
        gtdDTO.setPrimaryPurposeAdditionalQualifierCode("Other");
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.update());
    }

    @Test
    public void testAcceptOriginal() throws PAException {
        GeneralTrialDesignWebDTO gtdDTO = trialValidationAction.getGtdDTO();
        gtdDTO.setSubmissionNumber(1);
        gtdDTO.setPhaseCode(PhaseCode.I.getCode());
        gtdDTO.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION.getCode());
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        gtdDTO.setOfficialTitle("Test");
        gtdDTO.setAcronym("test");
        gtdDTO.setKeywordText("key");
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setLocalProtocolIdentifier("1");
        gtdDTO.setNctIdentifier("test");
        gtdDTO.setSubmissionNumber(1);
        gtdDTO.setProprietarytrialindicator("false");

        Organization rssOrg = new Organization();
        rssOrg.setName("American College of Surgeons Oncology Trials Group");
        when(orgSvc.getOrganizationByFunctionRole((Ii) anyObject(), (Cd) anyObject())).thenReturn(rssOrg);

        assertEquals("edit", trialValidationAction.accept());

        verify(regUserSvc).assignOwnership(regUser.getId(), 1L);
    }

    @Test
    public void testAcceptOriginalNoRss() throws PAException {
        GeneralTrialDesignWebDTO gtdDTO = trialValidationAction.getGtdDTO();
        gtdDTO.setSubmissionNumber(1);
        gtdDTO.setPhaseCode(PhaseCode.I.getCode());
        gtdDTO.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION.getCode());
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        gtdDTO.setOfficialTitle("Test");
        gtdDTO.setAcronym("test");
        gtdDTO.setKeywordText("key");
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setLocalProtocolIdentifier("1");
        gtdDTO.setNctIdentifier("test");
        gtdDTO.setSubmissionNumber(1);
        gtdDTO.setProprietarytrialindicator("false");
        assertEquals("edit", trialValidationAction.accept());

        verify(regUserSvc, never()).assignOwnership(regUser.getId(), 1L);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#reject()}.
     * @throws PAException 
     */
    @Test
    public void testReject() throws PAException {
        trialValidationAction.getGtdDTO().setLeadOrganizationIdentifier("1");
        assertEquals("edit", trialValidationAction.reject());
        GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        gtdDTO.setCommentText("rejectcommentText");
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.reject());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#rejectReason()}.
     */
    @Test
    public void testRejectReason() {
        GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCommentText("rejectcommentText");
        gtdDTO.setRejectionReasonCode("Other");
        trialValidationAction.setGtdDTO(gtdDTO);
        getRequest().getSession().setAttribute("submissionNumber", 1);
        assertEquals("edit", trialValidationAction.rejectReason());
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        gtdDTO.setCommentText("rejectcommentText");
        gtdDTO.setRejectionReasonCode("Other");
        trialValidationAction.setGtdDTO(gtdDTO);
        getRequest().getSession().setAttribute("submissionNumber", 2);
        assertEquals("protocol_view", trialValidationAction.rejectReason());
        gtdDTO = new GeneralTrialDesignWebDTO();
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("rejectReason", trialValidationAction.rejectReason());
    }

    @Test
    public void testEnforceBusinessRules() throws PAException {
        // reject for proprietary trial where phase,purpose and NCT is null
        GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setCommentText("rejectcommentText");
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        trialValidationAction.setGtdDTO(gtdDTO);
        getRequest().getSession().setAttribute("submissionNumber", 1);
        assertEquals("edit", trialValidationAction.reject());
        assertFalse(trialValidationAction.getFieldErrors().containsKey("gtdDTO.phaseCode"));
        // reject for non-proprietary trial where phase,purpose and NCT is null
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCommentText("rejectcommentText");
        trialValidationAction.setGtdDTO(gtdDTO);
        getRequest().getSession().setAttribute("submissionNumber", 1);
        assertEquals("edit", trialValidationAction.reject());
        assertTrue(trialValidationAction.getFieldErrors().containsKey("gtdDTO.phaseCode"));
        // accept for non-proprietary trial where phase,purpose and NCT is null
        gtdDTO = new GeneralTrialDesignWebDTO();
        trialValidationAction.setGtdDTO(gtdDTO);
        trialValidationAction.getGtdDTO().setSubmissionNumber(1);
        assertEquals("edit", trialValidationAction.accept());
        assertTrue(trialValidationAction.getFieldErrors().containsKey("gtdDTO.phaseCode"));
    }

    @Test
    public void testLoadAndBindProgramCodes() throws Exception {
        GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setLeadOrganizationIdentifier("1");
        trialValidationAction.setGtdDTO(gtdDTO);
        trialValidationAction.loadProgramCodes();
        assertFalse(trialValidationAction.getProgramCodeList().isEmpty());
        assertTrue(gtdDTO.getProgramCodes().isEmpty());

        trialValidationAction.setProgramCodeIds(Arrays.asList(1L, 2L));
        trialValidationAction.bindProgramCodes();
        assertFalse(gtdDTO.getProgramCodes().isEmpty());

    }


    @Test
    public void testLoadAndSync() throws Exception {
        GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setLeadOrganizationIdentifier("1");
        trialValidationAction.setGtdDTO(gtdDTO);
        trialValidationAction.loadProgramCodes();
        assertFalse(trialValidationAction.getProgramCodeList().isEmpty());
        assertTrue(gtdDTO.getProgramCodes().isEmpty());
        assertTrue(CollectionUtils.isEmpty(trialValidationAction.getProgramCodeIds()));
        ProgramCodeDTO pgc = new ProgramCodeDTO();
        pgc.setActive(true);
        pgc.setId(1L);
        pgc.setProgramName("Program Code 1");
        pgc.setProgramCode("PG1");
        gtdDTO.setProgramCodes(Arrays.asList(pgc));

        trialValidationAction.syncProgramCodes();
        assertFalse(trialValidationAction.getProgramCodeIds().isEmpty());

    }
}
