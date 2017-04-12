package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.Lov;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.webservices.dto.AgeDTO;
import gov.nih.nci.pa.webservices.dto.CTGovImportLogWebService;
import gov.nih.nci.pa.webservices.dto.ProtocolSnapshotDTO;
import gov.nih.nci.pa.webservices.dto.StudyProtocolIdentityDTO;
import gov.nih.nci.pa.webservices.dto.StudyProtocolWebServiceDTO;
import gov.nih.nci.pa.webservices.dto.TrialRegistrationDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma
 *
 */
public class ImportHelperRestServiceTest extends AbstractHibernateTestCase {
    private ImportHelperRestService service = new ImportHelperRestService();
    private TrialRegistrationServiceLocal trialRegistrationServiceLocal;
    private CTGovSyncServiceLocal ctGovSyncServiceLocal = mock(CTGovSyncServiceLocal.class);
    private ProtocolQueryServiceLocal protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
    private InterventionalStudyProtocolDTO spDto;
    private Ii spId;
    private List<StudyProtocolQueryDTO> queryDTOList = new ArrayList<StudyProtocolQueryDTO>();
    private StudyProtocolServiceLocal spSvc = mock(StudyProtocolServiceLocal.class);
    Ii studyId = null;
    private ArmServiceLocal armSvc = mock(ArmServiceLocal.class);
    private List<ArmDTO> armDtoList = new ArrayList<ArmDTO>();
    private PlannedActivityServiceLocal plannedActSvc = mock(PlannedActivityServiceLocal.class);
    private List<PlannedEligibilityCriterionDTO> plannedEcDtoList;
    private LookUpTableServiceRemote lookupSvc = mock(LookUpTableServiceRemote.class);
    private StudyInboxServiceLocal inboxSvs = mock(StudyInboxServiceLocal.class);
    private MailManagerServiceLocal mailManagerSvc = mock(MailManagerServiceLocal.class);
    @Before
    public void before() throws PAException {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        studyId = IiConverter.convertToIi(1L);
        spId = new Ii();
        spId.setExtension("1");
        trialRegistrationServiceLocal = mock(TrialRegistrationServiceLocal.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getInstance().getServiceLocator()
                        .getTrialRegistrationService()).thenReturn(
                trialRegistrationServiceLocal);
        when(PaRegistry.getCTGovSyncService()).thenReturn(ctGovSyncServiceLocal);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        when(PaRegistry.getStudyProtocolService()).thenReturn(spSvc);
        setupSpDto();
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0001");
        dto.setNctIdentifier("NCI20120001");
        dto.setStudyStatusCode(StudyStatusCode.COMPLETE);
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0001");
        dto.setStudyProtocolId(1L);
        dto.setLeadOrganizationPOId(1L);
        
        queryDTOList.add(dto);
        
        dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0002");
        dto.setNctIdentifier("NCI20120002");
        dto.setStudyStatusCode(StudyStatusCode.IN_REVIEW);
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0001");
        dto.setStudyProtocolId(2L);
        dto.setLeadOrganizationPOId(2L);
        
        queryDTOList.add(dto);
        setupArmDto();
        when(PaRegistry.getArmService()).thenReturn(armSvc);
        when(armSvc.getByStudyProtocol(any(Ii.class))).thenReturn(armDtoList);
        setupPlEcDto();
        when(PaRegistry.getPlannedActivityService()).thenReturn(plannedActSvc);
        when(plannedActSvc.getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class))).thenReturn(plannedEcDtoList);
        when(paRegSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        when(lookupSvc.getPropertyValue("ctgov.sync.fields_of_interest"))
        .thenReturn("studyProtocol.publicDescription;studyProtocol.scientificDescription;studyProtocol.keywordText;eligibilityCriteria;arms");
        when(PaRegistry.getMailManagerService()).thenReturn(mailManagerSvc);
    }
    private void setupArmDto() {
        ArmDTO armDto = new ArmDTO();
        armDto.setName(StConverter.convertToSt("Bname"));
        armDto.setDescriptionText(StConverter.convertToSt("some description"));
        armDto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.EXPERIMENTAL));
        armDto.setIdentifier(studyId);
        armDtoList = new ArrayList<ArmDTO>();
        armDtoList.add(armDto);
        armDto = new ArmDTO();
        armDto.setName(StConverter.convertToSt("Aname"));
        armDto.setDescriptionText(StConverter.convertToSt("some description"));
        armDto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.EXPERIMENTAL));
        armDto.setIdentifier(IiConverter.convertToIi(2L));
        armDtoList.add(armDto);
    }
    private void setupPlEcDto() {
        plannedEcDtoList = new ArrayList<PlannedEligibilityCriterionDTO>();
        PlannedEligibilityCriterionDTO plannedECDto = new PlannedEligibilityCriterionDTO();
        Ivl<Pq> ivlPq = new Ivl<Pq>();
        Pq pq = new Pq();
        pq.setValue(BigDecimal.valueOf(1L));
        pq.setPrecision(Integer.valueOf("1"));
        pq.setUnit("years");
        ivlPq.setLow(pq);
        ivlPq.setHigh(pq);
        plannedECDto.setValue(ivlPq);
        plannedECDto.setDisplayOrder(IntConverter.convertToInt(1));
        plannedECDto.setCriterionName(StConverter.convertToSt("GENDER"));
        plannedECDto.setEligibleGenderCode(CdConverter.convertStringToCd("M"));
        plannedECDto.setTextDescription(StConverter.convertToSt("some description"));
        plannedECDto.setOperator(StConverter.convertToSt("+"));
        plannedECDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.COURSE));
        plannedEcDtoList.add(plannedECDto);

        plannedECDto = new PlannedEligibilityCriterionDTO();
        plannedECDto.setCriterionName(StConverter.convertToSt("AGE"));
        ivlPq = new Ivl<Pq>();
        pq = new Pq();
        pq.setValue(BigDecimal.valueOf(1L));
        pq.setUnit("some unit");
        pq.setPrecision(Integer.valueOf("1"));
        ivlPq.setLow(pq);
        pq = new Pq();
        pq.setValue(BigDecimal.valueOf(999L));
        ivlPq.setHigh(pq);
        plannedECDto.setValue(ivlPq);
        plannedECDto.setDisplayOrder(IntConverter.convertToInt(2));
        plannedECDto.setTextDescription(StConverter.convertToSt("some description"));
        plannedECDto.setInclusionIndicator(BlConverter.convertToBl(true));
        plannedECDto.setOperator(StConverter.convertToSt("+"));
        plannedECDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.COURSE));
        plannedEcDtoList.add(plannedECDto);
    }
    @Test
    public void getStudyProtocolIdentityTest() throws PAException {
        ClinicalStudy clinicalStudy = new ClinicalStudy();
        clinicalStudy.setOverallStatus("Completed");
        CTGovStudyAdapter study = new CTGovStudyAdapter(clinicalStudy);
        when(PaRegistry.getCTGovSyncService()).thenReturn(ctGovSyncServiceLocal);
        when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
                        eq("NCT290384"))).thenReturn(
                Arrays.asList((StudyProtocolDTO) spDto));
        when(ctGovSyncServiceLocal
                    .getAdaptedCtGovStudyByNctId(eq("NCT290384"))).thenReturn(study);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(queryDTOList);
        Response r = service.getStudyProtocolIdentity("NCT290384");
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
    }
    @Test
    public void getStudyProtocolIdentityTitleTest() throws PAException {
        ClinicalStudy clinicalStudy = new ClinicalStudy();
        clinicalStudy.setOverallStatus("Completed");
        clinicalStudy.setOfficialTitle("official Title");
        CTGovStudyAdapter study = new CTGovStudyAdapter(clinicalStudy);
        when(PaRegistry.getCTGovSyncService()).thenReturn(ctGovSyncServiceLocal);
        when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
                        eq("NCT290384"))).thenReturn(
                Arrays.asList((StudyProtocolDTO) spDto));
        when(ctGovSyncServiceLocal
                    .getAdaptedCtGovStudyByNctId(eq("NCT290384"))).thenReturn(study);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        queryDTOList.get(0).setOfficialTitle("official Title");
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(null).thenReturn(queryDTOList);
        Response r = service.getStudyProtocolIdentity("NCT290384");
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
    }
    @Test
    public void getSPIdentityNotFoundTest() throws PAException {
        ClinicalStudy clinicalStudy = new ClinicalStudy();
        clinicalStudy.setOverallStatus("Completed");
        clinicalStudy.setOfficialTitle("official Title");
        CTGovStudyAdapter study = new CTGovStudyAdapter(clinicalStudy);
        when(PaRegistry.getCTGovSyncService()).thenReturn(ctGovSyncServiceLocal);
        when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
                        eq("NCT290384"))).thenReturn(
                Arrays.asList((StudyProtocolDTO) spDto));
        when(ctGovSyncServiceLocal
                    .getAdaptedCtGovStudyByNctId(eq("NCT290384"))).thenReturn(study);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        queryDTOList.get(0).setOfficialTitle("official Title");
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(null).thenReturn(null);
        Response r = service.getStudyProtocolIdentity("NCT290384");
       assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
    }
    @Test
    public void getStudyProtocolIdentityTitleErrorTest() throws PAException {
        ClinicalStudy clinicalStudy = new ClinicalStudy();
        clinicalStudy.setOverallStatus("Completed");
        clinicalStudy.setOfficialTitle("official Title");
        CTGovStudyAdapter study = new CTGovStudyAdapter(clinicalStudy);
        when(PaRegistry.getCTGovSyncService()).thenReturn(ctGovSyncServiceLocal);
        when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
                        eq("NCT290384"))).thenReturn(
                Arrays.asList((StudyProtocolDTO) spDto));
        when(ctGovSyncServiceLocal
                    .getAdaptedCtGovStudyByNctId(eq("NCT290384"))).thenReturn(study);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        queryDTOList.get(0).setOfficialTitle("official Title");
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class))).thenThrow(new PAException("error"));
        Response r = service.getStudyProtocolIdentity("NCT290384");
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("error", r.getEntity().toString());
    }

   @Test
   public void createImportLogEntryTest() throws IOException {
       CTGovImportLogWebService log = new CTGovImportLogWebService();
       log.setAction("update");
       log.setNciId("NCI-1000-11111");
       log.setNctId("NCT290384");
       log.setTitle("Title1");
       log.setImportStatus("Success");
       log.setUserCreated("kogantir");
       log.setNeedsReview(true);
       log.setAdminChanged(true);
       log.setScientificChanged(true);
       log.setStudyInboxId(1L);
       Response r = service.createImportLogEntry(log);
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
   }
   
   @Test
   public void createImportLogEntryTestException() throws IOException, PAException {
       CTGovImportLogWebService log = new CTGovImportLogWebService();
       log.setAction("update");
       log.setNciId("NCI-1000-11111");
       log.setNctId("NCT290384");
       log.setTitle("Title1");
       log.setImportStatus("Success");
       log.setUserCreated("kogantir");
       log.setNeedsReview(true);
       log.setAdminChanged(true);
       log.setScientificChanged(true);
       log.setStudyInboxId(1L);
       
       doThrow(new TrialDataException("error")).when(
               ctGovSyncServiceLocal).createImportLogEntry(
       any(String.class), any(String.class), any(String.class),
       any(String.class), any(String.class),
       any(String.class), any(boolean.class),
       any(boolean.class), any(boolean.class), any(StudyInboxDTO.class));
       Response r = service.createImportLogEntry(log);
       assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
       assertEquals("error", r.getEntity().toString());
   }
   
   @Test
   public void createImportLogEntryNotFoundTest() throws IOException {
       CTGovImportLogWebService log = new CTGovImportLogWebService();
       log.setAction("update");
       log.setNciId("NCI-1000-11111");
       log.setNctId("NCT290384");
       log.setTitle("Title1");
       log.setImportStatus("Success");
       log.setUserCreated("kogantir");
       log.setNeedsReview(true);
       log.setAdminChanged(true);
       log.setScientificChanged(true);
       Response r = service.createImportLogEntry(log);
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
   }
   
   
   @Test
   public void updateTrialRegisterationTest() throws PAException, IOException {
       TrialRegistrationDTO trialRegistrationDTO = new TrialRegistrationDTO();
       trialRegistrationDTO.setNctID("NCT290384");
       gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO webDto = new gov
               .nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO();
       webDto.setStudyProtocolId("1");
       webDto.setOfficialTitle("officialTitle");
       webDto.setExpandedAccessIndicator(true);
       webDto.setAllocationCode("Randomized Controlled Trial");
       webDto.setDesignConfigurationCode("Single Group");
       trialRegistrationDTO.setStudyProtocolDTO(webDto);
       when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
               eq("NCT290384"))).thenReturn(
       Arrays.asList((StudyProtocolDTO) spDto));
       Response r = service.updateTrialRegisteration(trialRegistrationDTO);
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
       assertTrue(r.getEntity() instanceof StudyProtocolIdentityDTO);
   }
   
   @Test
   public void updateTrialRegisterationBadRequestTest() throws PAException, IOException {
       TrialRegistrationDTO trialRegistrationDTO = new TrialRegistrationDTO();
       trialRegistrationDTO.setNctID("NCT290384");
       gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO webDto = new gov
               .nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO();
       webDto.setStudyProtocolId("1");
       webDto.setOfficialTitle("officialTitle");
       webDto.setExpandedAccessIndicator(true);
       webDto.setAllocationCode("Randomized Controlled Trial");
       webDto.setDesignConfigurationCode("Single Group");
       trialRegistrationDTO.setStudyProtocolDTO(webDto);
       when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
               eq("NCT290384"))).thenThrow(new PAException("error"));
       
       Response r = service.updateTrialRegisteration(trialRegistrationDTO);
       assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
       assertEquals(r.getEntity(), "error");
   }
   
   @Test
   public void createTrialRegisterationTest() throws PAException, IOException {
       TrialRegistrationDTO trialRegistrationDTO = new TrialRegistrationDTO();
       trialRegistrationDTO.setNctID("NCT290384");
       gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO webDto = new gov
               .nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO();
       webDto.setOfficialTitle("officialTitle");
       webDto.setExpandedAccessIndicator(true);
       webDto.setAllocationCode("Randomized Controlled Trial");
       webDto.setDesignConfigurationCode("Single Group");
       trialRegistrationDTO.setStudyProtocolDTO(webDto);
       when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
               eq("NCT290384"))).thenReturn(
       Arrays.asList((StudyProtocolDTO) spDto));
       Response r = service.createTrialRegisteration(trialRegistrationDTO);
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
       assertTrue(r.getEntity() instanceof StudyProtocolIdentityDTO);
   }
   @Test
   public void createTrialRegisterationErrorTest() throws PAException, IOException {
       TrialRegistrationDTO trialRegistrationDTO = new TrialRegistrationDTO();
       trialRegistrationDTO.setNctID("NCT290384");
       gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO webDto = new gov
               .nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO();
      
       webDto.setOfficialTitle("officialTitle");
       webDto.setExpandedAccessIndicator(true);
       webDto.setAllocationCode("Randomized Controlled Trial");
       webDto.setDesignConfigurationCode("Single Group");
       trialRegistrationDTO.setStudyProtocolDTO(webDto);
       when(PaRegistry.getStudyProtocolService().getStudyProtocol(any(Ii.class))).thenThrow(new PAException("error"));
       
       Response r = service.createTrialRegisteration(trialRegistrationDTO);
       assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
       assertEquals(r.getEntity(), "error");
   }
   @Test
   public void getIndustrialConsortiaTrialsWithNCTIdsTest() throws PAException {
       populateCTGovImportLogs();
       StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
       dto.setNciIdentifier("NCI-2012-0004");
       dto.setNctIdentifier("NCI20120004");
      
       dto.setStudyStatusCode(StudyStatusCode.COMPLETE);
       dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0004");
       dto.setStudyProtocolId(1L);
       dto.setLeadOrganizationPOId(1L);
       queryDTOList.add(dto);
       when(protocolQueryServiceLocal
                       .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
               .thenReturn(queryDTOList);
       Response r = service.getIndustrialConsortiaTrialsWithNCTIds();
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
       tear();
   }
   @Test
   public void getIndConsortiaTrialsWithNCTIdsErrorTest() throws PAException {
       populateCTGovImportLogs();
       StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
       dto.setNciIdentifier("NCI-2012-0004");
       dto.setNctIdentifier("NCI20120004");
      
       dto.setStudyStatusCode(StudyStatusCode.COMPLETE);
       dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0004");
       dto.setStudyProtocolId(1L);
       dto.setLeadOrganizationPOId(1L);
       queryDTOList.add(dto);
       when(protocolQueryServiceLocal
                       .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
               .thenThrow(new PAException("error"));
       Response r = service.getIndustrialConsortiaTrialsWithNCTIds();
       assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
       assertEquals(r.getEntity(), "error");
       tear();
   }
   @Test
   public void getIndConsortiaTrialsWithNCTIdsNullTest() throws PAException {
       when(protocolQueryServiceLocal
                       .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
               .thenReturn(queryDTOList);
       Response r = service.getIndustrialConsortiaTrialsWithNCTIds();
       assertEquals(Status.OK.getStatusCode(), r.getStatus());
       assertTrue(r.getEntity() != null);
       tear();
   }
   /**
    * Populates entries in ctgovimport_log table for NCT-2012-0001 and NCT-2012-0003.
    */
   private void populateCTGovImportLogs() {
       Calendar calendar = Calendar.getInstance();
       
       Session s = PaHibernateUtil.getCurrentSession();
       SQLQuery query = s.createSQLQuery(
               "insert into ctgovimport_log (identifier, nci_id, nct_id, trial_title, action_performed, " 
       + "import_status, user_created, date_created,review_required,admin,scientific) " 
       + "values (:id, :nci_id, :nct_id, :title, :action, :status, :user, :date, :review, :admin, :scientific)");
       
       calendar.add(Calendar.MONTH, -2);
       query.setText("id", "1");
       query.setText("nci_id", "NCI-2012-0001");
       query.setText("nct_id", "NCI20120001");
       query.setText("title", "Test");
       query.setText("action", "UPDATE");
       query.setText("status", "Success");
       query.setText("user", "User");
       query.setTimestamp("date", calendar.getTime());
       query.setText("review", "true");
       query.setText("admin", "true");
       query.setText("scientific", "true");
       query.executeUpdate();
       s.flush();
       
       calendar.add(Calendar.MONTH, 2);
       query.setText("id", "2");
       query.setText("nci_id", "NCI-2012-0004");
       query.setText("nct_id", "NCI20120004");
       query.setText("title", "Test");
       query.setText("action", "UPDATE");
       query.setText("status", "Success");
       query.setText("user", "User");
       query.setTimestamp("date", calendar.getTime());
       query.setText("review", null);
       query.setText("admin", null);
       query.setText("scientific", null);
       query.executeUpdate();
       s.flush();
   }
   /**
    * Removes the entries in ctgovimport_log table
    */
   public void tear() {
       Session s = PaHibernateUtil.getCurrentSession();
       SQLQuery query = s.createSQLQuery("delete from ctgovimport_log");
       query.executeUpdate();
   }
   
   
    @Test
    public void extractStudyProtocolDTOTest() {
        String jsonStr ="{" + 
                "    \"type\": \"gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO\"," + 
                "    \"studyProtocolId\": \"130686\"," + 
                "    \"nciId\": \"NCI-2017-00334\"," + 
                "    \"acronym\": null," + 
                "    \"publicDescription\": \"\\n      RATIONALE: Drugs used in chemotherapy, such as melphalan, work in different ways to stop the\\n      growth of tumor cells, either by killing the cells or by stopping them from dividing. Giving\\n      melphalan directly into the arteries around the tumor may kill more tumor cells. It is not\\n      yet known whether hepatic arterial infusion with melphalan is more effective than standard\\n      therapy in treating liver metastases due to melanoma.\\n\\n      PURPOSE: This randomized phase III trial is studying hepatic arterial infusion with\\n      melphalan to see how well it works compared to standard therapy in treating patients with\\n      unresectable liver metastases due to melanoma.\\n    \"," + 
                "    \"publicTitle\": \"Hepatic Arterial Infusion With Melphalan Compared With Standard Therapy in Treating Patients With Unresectable Liver Metastases Due to Melanoma\"," + 
                "    \"scientificDescription\": \"\\n      OBJECTIVES:\\n\\n      Primary\\n\\n        -  Compare the hepatic progression-free survival of patients with unresectable liver\\n           metastases secondary to ocular or cutaneous melanoma treated with percutaneous isolated\\n           hepatic arterial perfusion (PHP) with melphalan with subsequent venous hemofiltration\\n           vs the best alternative standard treatment.\\n\\n      Secondary\\n\\n        -  Determine the response rate and duration of response in patients treated with melphalan\\n           PHP.\\n\\n        -  Determine the patterns of recurrence in patients treated with melphalan PHP.\\n\\n        -  Compare the overall survival of patients treated with these regimens.\\n\\n        -  Compare the safety and tolerability of these regimens in these patients.\\n\\n        -  Determine the pharmacokinetics of melphalan after PHP.\\n\\n      OUTLINE: This is a multicenter study. Patients are stratified according to site of disease\\n      (ocular vs cutaneous). Patients are randomized to 1 of 2 treatment arms.\\n\\n        -  Arm I: Patients undergo an isolated hepatic arterial infusion of melphalan over 30\\n           minutes on day 1. Treatment repeats every 4 weeks for 4 courses in the absence of\\n           disease progression or unacceptable toxicity. Patients with complete or partial\\n           response undergo 2 additional courses in the absence of ongoing or increasing toxicity.\\n\\n        -  Arm II: Patients receive the best alternative therapy comprising supportive care,\\n           systemic or regional chemotherapy, hepatic artery (chemo)-embolization, or any other\\n           appropriate therapy at the National Cancer Institute or therapy at the discretion of\\n           their physician. Patients may cross over to arm I if they have evidence of disease\\n           progression.\\n\\n      Blood samples are collected periodically for pharmacokinetic analysis of melphalan.\\n\\n      After completion of study treatment, patients are followed periodically for 4 years and then\\n      annually for survival.\\n\\n      PROJECTED ACCRUAL: A total of 92 patients will be accrued for this study.\\n    \"," + 
                "    \"keywordText\": \"liver metastases, extraocular extension melanoma, stage IV melanoma, recurrent melanoma, recurrent intraocular melanoma, metastatic intraocular melanoma, iris melanoma, ciliary body and choroid melanoma, medium/large size\"," + 
                "    \"officialTitle\": \"A Random-Assignment Study of Hepatic Arterial Infusion of Melphalan With Venous Filtration Via Peripheral Hepatic Perfusion (PHP) (Delcath System) Versus Best Alternative Care for Ocular and Cutaneous Melanoma Metastatic to the Liver\"," + 
                "    \"startDateTypeCode\": \"Actual\"," + 
                "    \"primaryCompletionDateTypeCode\": \"Actual\"," + 
                "    \"completionDateTypeCode\": \"Actual\"," + 
                "    \"startDate\": \"February 2006\"," + 
                "    \"primaryCompletionDate\": \"August 2012\"," + 
                "    \"completionDate\": \"August 2012\"," + 
                "    \"targetAccrualNumber\": 93," + 
                "    \"expandedAccessIndicator\": false," + 
                "    \"phaseCode\": \"III\"," + 
                "    \"recordVerificationDate\": \"October 2013\"," + 
                "    \"acceptHealthyVolunteersIndicator\": false," + 
                "    \"dataMonitoringCommitteeAppointedIndicator\": null," + 
                "    \"primaryPurposeCode\": \"Other\"," + 
                "    \"primaryPurposeAdditionalQualifierCode\": \"Other\"," + 
                "    \"primaryPurposeOtherText\": \"Not provided by ClinicalTrials.gov\"," + 
                "    \"userLastCreated\": \"ClinicalTrials.gov Import\"," + 
                "    \"secondaryIdentifiers\": [" + 
                "      \"NCI-06-C-0088\"," + 
                "      \"NCI-P6701\"" + 
                "    ]," + 
                "    \"studySource\": \"ClinicalTrials.gov\"," + 
                "    \"allocationCode\": \"Randomized Controlled Trial\"," + 
                "    \"blindedRoleCode\": null," + 
                "    \"designConfigurationCode\": \"Cross-over\"," + 
                "    \"numberOfInterventionGroups\": 2," + 
                "    \"studyClassificationCode\": null" + 
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            // JSON from String to Object
            StudyProtocolWebServiceDTO dto =  mapper.readValue(jsonStr, StudyProtocolWebServiceDTO.class);
           // dto.getAcronym();
            System.out.println("Hello World");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void needsReviewTest() {
        Set<gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO> dtos = new HashSet<gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO>();

        gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO dto = new gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO();
        dto.setIdentifier(1L);
        dto.setCategoryCode("AGE");
        dtos.add(dto);
        gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO dto21 = new gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO();
        dto21.setIdentifier(2L);
        dto21.setCategoryCode("AGE");
        dtos.add(dto21);
//        PlannedEligibilityCriterionDTO dto22 = new PlannedEligibilityCriterionDTO();
//        dto22.setIdentifier(3L);
//        dto22.setCategoryCode("AGE");
//        dtos.add(dto22);

        Set<gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO> dtos1 = new HashSet<gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO>();
        gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO dto2 = new gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO();
        dto2.setIdentifier(2L);
        dto2.setCategoryCode("AGE");
        dtos1.add(dto2);
        gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO dto1 = new gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO();
        dto1.setIdentifier(1L);
        dto1.setCategoryCode("AGE");
        dtos1.add(dto1);
      

        boolean eligibilityChange = Objects.deepEquals(dtos, dtos1);
        assertTrue(eligibilityChange);
    }
    private void setupSpDto() {
        spDto = new InterventionalStudyProtocolDTO();
        spDto.setPublicTitle(StConverter.convertToSt("title"));
        spDto.setAcronym(StConverter.convertToSt("acronym"));
        spDto.setOfficialTitle(StConverter.convertToSt("off title"));
        spDto.setIdentifier(spId);
        spDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
        spDto.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDto.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
        spDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        spDto.setSection801Indicator(BlConverter.convertToBl(true));
        spDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        spDto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(true));
        spDto.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode.ABBREVIATED));
        spDto.setStartDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setStartDateTypeCode(CdConverter.convertStringToCd("Actual"));
        spDto.setPrimaryCompletionDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd("Anticipated"));
        spDto.setCompletionDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setCompletionDateTypeCode(CdConverter.convertStringToCd("Anticipated"));
        spDto.setPublicDescription(StConverter.convertToSt("public description"));
        spDto.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDto.setPublicTitle(StConverter.convertToSt("public title"));
        spDto.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(true));
        spDto.setPrimaryPurposeCode(CdConverter.convertStringToCd("TREATMENT"));
        
        spDto.setPhaseCode(CdConverter.convertToCd((Lov)null));
        spDto.setDesignConfigurationCode(CdConverter.convertToCd((Lov)null)); 
        spDto.setNumberOfInterventionGroups(IntConverter.convertToInt((Integer)null));
        spDto.setAllocationCode(CdConverter.convertToCd((Lov)null)) ;
        spDto.setScientificDescription(StConverter.convertToSt("scientificDesc"));
        spDto.setKeywordText(StConverter.convertToSt("keywordText"));
        final Ivl<Int> targetAccrualNumber = new Ivl<Int>();
        targetAccrualNumber.setLow(IntConverter.convertToInt((Integer)null));
        spDto.setTargetAccrualNumber(targetAccrualNumber);


        DSet<Ii> secondaryIdentifiers = new DSet<Ii>();
        Ii assignedId = new Ii();
        assignedId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        assignedId.setExtension("NCI_2010_0001");
        Set<Ii> iis = new HashSet<Ii>();
        iis.add(assignedId);
        
        Ii otherId = new Ii();
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        otherId.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        otherId.setExtension("OtherId");
        iis.add(otherId);
        
        otherId = new Ii();
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        otherId.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        otherId.setExtension("AAAAId");
        iis.add(otherId);
        
        secondaryIdentifiers.setItem(iis);
        
        spDto.setSecondaryIdentifiers(secondaryIdentifiers);
    }
    
    @Test
    public void getProtocolSnapshotWithSInboxIDTest() throws PAException {
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(
                any(Ii.class))).thenReturn(spDto);
        List<StudyInboxDTO> inboxEntries = new ArrayList<StudyInboxDTO>();
        StudyInboxDTO dto = new StudyInboxDTO();
        dto.setIdentifier(studyId);
        inboxEntries.add(dto);
        when(PaRegistry.getStudyInboxService()).thenReturn(inboxSvs);
        when(inboxSvs.getOpenInboxEntries(any(Ii.class))).thenReturn(inboxEntries);
        Response r = service.getProtocolSnapshotWithSInboxID(IiConverter.convertToString(studyId));
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        assertTrue(r.getEntity() != null);
        assertTrue(r.getEntity() instanceof ProtocolSnapshotDTO);
    }
    
    @Test
    public void getProtocolSnapshotWithSInboxIDErrorTest() throws PAException {
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(
                any(Ii.class))).thenReturn(spDto);
        List<StudyInboxDTO> inboxEntries = new ArrayList<StudyInboxDTO>();
        StudyInboxDTO dto = new StudyInboxDTO();
        dto.setIdentifier(studyId);
        inboxEntries.add(dto);
        when(PaRegistry.getStudyInboxService()).thenReturn(inboxSvs);
        when(inboxSvs.getOpenInboxEntries(any(Ii.class))).thenThrow(new PAException("error"));
        Response r = service.getProtocolSnapshotWithSInboxID(IiConverter.convertToString(studyId));
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertTrue(r.getEntity() != null);
        assertEquals(r.getEntity(), "error");
    }
    
    
    @Test
    public void getProtocolSnapshotWithNoSInboxIDTest() throws PAException {
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(
                any(Ii.class))).thenReturn(spDto);
        when(PaRegistry.getStudyInboxService()).thenReturn(inboxSvs);
        when(inboxSvs.getOpenInboxEntries(any(Ii.class))).thenReturn(new ArrayList<StudyInboxDTO>());
        Response r = service.getProtocolSnapshotWithSInboxID(IiConverter.convertToString(studyId));
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        assertTrue(r.getEntity() != null);
        assertTrue(r.getEntity() instanceof ProtocolSnapshotDTO);
    }
    
    @Test 
    public void postProcessingCallTest() throws PAException {
        ProtocolSnapshotDTO before = setUpProtocolSnapshotDTO();
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(
                any(Ii.class))).thenReturn(spDto);
        List<StudyInboxDTO> inboxEntries = new ArrayList<StudyInboxDTO>();
        StudyInboxDTO dto = new StudyInboxDTO();
        dto.setIdentifier(studyId);
        inboxEntries.add(dto);
        when(PaRegistry.getStudyInboxService()).thenReturn(inboxSvs);
        when(inboxSvs.getOpenInboxEntries(any(Ii.class))).thenReturn(inboxEntries);
        Response r = service.postProcessingCall("1", before);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        assertTrue(r.getEntity() != null);
    }
    @Test 
    public void postProcessingCallExceptionTest() throws PAException {
        ProtocolSnapshotDTO before = setUpProtocolSnapshotDTO();
        when(PaRegistry.getStudyProtocolService().getStudyProtocol(
                any(Ii.class))).thenReturn(spDto);

        when(PaRegistry.getStudyInboxService()).thenReturn(inboxSvs);
        when(inboxSvs.getOpenInboxEntries(any(Ii.class))).thenThrow(new PAException("error"));
        Response r = service.postProcessingCall("1", before);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertTrue(r.getEntity() != null);
        assertEquals(r.getEntity(), "error");
    }
    private ProtocolSnapshotDTO setUpProtocolSnapshotDTO() {
        ProtocolSnapshotDTO dto = new ProtocolSnapshotDTO();
        dto.setKeywordText("keywordText");
        dto.setPublicDescription("public description");
        dto.setScientificDescription("scientificDesc");
        dto.setLastChangedDate("2017-01-01");
        dto.setStudyInboxId("1");
        gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO eligiDto = new gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO();
        eligiDto.setInclusionIndicator(true);
        eligiDto.setCategoryCode("Course");
        eligiDto.setDisplayOrder(1);
        eligiDto.setEligibleGenderCode("M");
        eligiDto.setIdentifier(1L);
        eligiDto.setCriterionName("GENDER");
        eligiDto.setOperator("+");
        eligiDto.setTextDescription("some description");
        AgeDTO age1 = new AgeDTO("years", BigDecimal.valueOf(1L), Integer.valueOf("1"));
        eligiDto.setMaxValue(age1);
        eligiDto.setMinValue(age1);
        List <gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO> eligibilityList = new ArrayList<gov
                .nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO>();
        eligibilityList.add(eligiDto);
        dto.setEligibilityList(eligibilityList);
        gov.nih.nci.pa.webservices.dto.ArmDTO arm = new gov.nih.nci.pa.webservices.dto.ArmDTO();
        arm.setName("Bname");
        arm.setDescriptionText("some description");
        arm.setId(1L);
        arm.setTypeCode("Experimental");
        List <gov.nih.nci.pa.webservices.dto.ArmDTO> armList = new ArrayList<gov.nih.nci.pa.webservices.dto.ArmDTO>();
        armList.add(arm);
        arm = new gov.nih.nci.pa.webservices.dto.ArmDTO();
        arm.setName("Aname");
        arm.setDescriptionText("some description");
        arm.setId(2L);
        arm.setTypeCode("Experimental");
        armList.add(arm);
        dto.setArmList(armList);
        return dto;
    }
   
    @Test
    public void emailSyncJobImportLogsTest() throws PAException {
        List<CTGovImportLog> logEntries = new ArrayList<CTGovImportLog>();
        CTGovImportLog log = new CTGovImportLog();
        log.setAdmin(true);
        log.setAction("update");
        log.setDateCreated(new Date());
        log.setId(1L);
        log.setNciID("NCI-2017-00001");
        log.setNctID("NCT10000");
        log.setReviewRequired(true);
        logEntries.add(log);
        when(ctGovSyncServiceLocal.getLogEntries(any(CTGovImportLogSearchCriteria.class))).thenReturn(logEntries);
        Response r = service.emailSyncJobImportLogs(new Date());
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        assertTrue(r.getEntity() != null);
        assertEquals(r.getEntity(), "Success");
    }
    @Test
    public void emailSyncJobImportLogsErrorTest() throws PAException {
        List<CTGovImportLog> logEntries = new ArrayList<CTGovImportLog>();
        CTGovImportLog log = new CTGovImportLog();
        log.setAdmin(true);
        log.setAction("update");
        log.setDateCreated(new Date());
        log.setId(1L);
        log.setNciID("NCI-2017-00001");
        log.setNctID("NCT10000");
        log.setReviewRequired(true);
        logEntries.add(log);
        when(ctGovSyncServiceLocal.getLogEntries(any(CTGovImportLogSearchCriteria.class))).thenReturn(logEntries);
        doThrow(new TrialDataException("error")).when(mailManagerSvc).sendCTGovSyncStatusSummaryMail(logEntries);
        Response r = service.emailSyncJobImportLogs(new Date());
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertTrue(r.getEntity() != null);
        assertEquals(r.getEntity(), "error");
    }
}
