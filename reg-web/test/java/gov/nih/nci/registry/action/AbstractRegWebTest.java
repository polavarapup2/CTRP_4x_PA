/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.test.util.MockPoServiceLocator;
import gov.nih.nci.registry.test.util.RegistrationMockServiceLocator;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.TextProviderSupport;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 * @author Vrushali
 *
 */
public abstract class AbstractRegWebTest {
    private final static int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

    /**
     * Creates the action context with a mock request.
     */
    public static void initActionContext() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();

        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));

        assertNotNull(ActionContext.getContext());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        ServletActionContext.setRequest(request);

        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
    }
    
    protected void setTextProvider(ActionSupport action) {
        try {
            Field field = ActionSupport.class.getDeclaredField("textProvider");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField
                    .setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(
                    action,
                    new TextProviderSupport(ResourceBundle
                            .getBundle("ApplicationResources"), action));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set up services.
     */
    @Before
    public void setUpServices() {
        PaRegistry.getInstance().setServiceLocator(new RegistrationMockServiceLocator());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
    }
    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockrequest() {
        initActionContext();
    }

    /**
     * Clean out the action context to ensure one test does not impact another.
     */
    @After
    public void cleanUpActionContext() {
        ActionContext.setContext(null);
    }

    protected TrialDTO getMockTrialDTO()  {
        TrialDTO trialDTO = new TrialDTO();
        trialDTO.setAmendmentDate("01/20/2009");
        trialDTO.setLocalAmendmentNumber("localAmendmentNumber");
        trialDTO.setAssignedIdentifier("assignedIdentifier");
        trialDTO.setLeadOrgTrialIdentifier("localProtocolIdentifier");
        trialDTO.setOfficialTitle("officialTitle");
        trialDTO.setPhaseCode("II");
        trialDTO.setPrimaryPurposeCode("TREATMENT");
        trialDTO.setTrialType("Interventional");
        trialDTO.setLeadOrganizationIdentifier("1");
        trialDTO.setPiIdentifier("2");
        trialDTO.setSponsorIdentifier("3");
        trialDTO.setResponsiblePartyType(TrialDTO.RESPONSIBLE_PARTY_TYPE_PI);
        trialDTO.setResponsiblePersonTitle("PI");
        trialDTO.setResponsiblePersonIdentifier("1");
        trialDTO.setResponsiblePersonAffiliationOrgId("1");
        trialDTO.setIdentifier("1");
        trialDTO.setContactEmail("contactEmail@mail.com");
        trialDTO.setContactPhone("contact Phone ");
        
        try {
            StatusDto status = new StatusDto();
            status.setReason("");
            status.setStatusCode(StudyStatusCode.ACTIVE.name());
            status.setStatusDate(DateUtils.parseDate("01/20/2008",
                    new String[] { "MM/dd/yyyy" }));
            trialDTO.setStatusHistory(Arrays.asList(status));
            ServletActionContext.getRequest().getSession()
                    .setAttribute("statusHistoryList", Arrays.asList(status));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        trialDTO.setPrimaryCompletionDateType("Anticipated");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String futureDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

        trialDTO.setPrimaryCompletionDate(futureDate);
        trialDTO.setStartDateType("Actual");
        trialDTO.setStartDate("01/20/2008");
       
        SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId(UUID.randomUUID().toString());
        summarySp.setOrgId("1");
        summarySp.setOrgName("SummaryFourOrgName");
        trialDTO.getSummaryFourOrgIdentifiers().add(summarySp);
        trialDTO.setSummaryFourFundingCategoryCode("summaryFourFundingCategoryCode");
        trialDTO.setNctIdentifier("nctIdentifier");
        trialDTO.setLeadOrganizationName("leadOrganizationName");
        trialDTO.setPiName("piName");
        trialDTO.setSponsorName("sponsorName");
        trialDTO.setLst("1");
        trialDTO.setSelectedRegAuth("1");
        trialDTO.setXmlRequired(true);
        trialDTO.setNciGrant(Boolean.TRUE);
        trialDTO.setAccrualDiseaseCodeSystem("SDC");
        return trialDTO;
    }
    protected List<TrialFundingWebDTO> getfundingDtos(){
        TrialFundingWebDTO grantDto = new TrialFundingWebDTO();
        grantDto.setFundingMechanismCode("B09");
        grantDto.setNihInstitutionCode("AG");
        grantDto.setNciDivisionProgramCode("CCR");
        grantDto.setSerialNumber("123456");
        grantDto.setFundingPercent("20.5");
        grantDto.setId("1");
        grantDto.setRowId("1");
        List<TrialFundingWebDTO> fundingDtos = new ArrayList<TrialFundingWebDTO>();
        fundingDtos.add(grantDto);
        return fundingDtos;
    }
    protected List<TrialDocumentWebDTO> getDocumentDtos(){
        TrialDocumentWebDTO dto = new TrialDocumentWebDTO();
        dto.setFileName("fileName");
        dto.setTypeCode(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode());
        byte[] content = new byte[10];;
        dto.setText(content);
        List<TrialDocumentWebDTO> returnList =  new ArrayList<TrialDocumentWebDTO>();
        returnList.add(dto);
        return returnList;
    }
    protected List<TrialIndIdeDTO> getIndDtos() {
        List<TrialIndIdeDTO> indDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indDto = new TrialIndIdeDTO();
        indDto.setIndIde("IND");
        indDto.setNumber("Ind no");
        indDto.setGrantor("CDER");
        indDto.setHolderType("Investigator");
        indDto.setExpandedAccess("false");
        indDto.setRowId("1");
        indDto.setIndIdeId("1");
        indDto.setExpandedAccessType("expandedAccessType");
        indDto.setProgramCode("programCode");
        indDto.setRowId("1");
        indDto.setIndIdeId("1");
        indDtos.add(indDto);

        indDto = new TrialIndIdeDTO();
        indDto.setIndIde("IDE");
        indDto.setNumber("Ide no");
        indDto.setGrantor("CDER");
        indDto.setHolderType("NIH");
        indDto.setExpandedAccess("yes");
        indDto.setExpandedAccessType(ExpandedAccessStatusCode.AVAILABLE.getCode());
        indDto.setProgramCode(NihInstituteCode.NEI.getCode());
        indDto.setRowId("1");
        indDto.setIndIdeId("1");
        indDtos.add(indDto);

        indDto = new TrialIndIdeDTO();
        indDto.setIndIde("IDE");
        indDto.setNumber("Ide no");
        indDto.setGrantor("CDER");
        indDto.setHolderType("NCI");
        indDto.setExpandedAccess("yes");
        indDto.setExpandedAccessType(ExpandedAccessStatusCode.AVAILABLE.getCode());
        indDto.setProgramCode(NciDivisionProgramCode.CCR.getCode());
        indDto.setRowId("1");
        indDto.setIndIdeId("1");
        indDtos.add(indDto);
        return indDtos;
    }
    protected void deleteCreatedFolder() throws PAException{
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StringBuffer sbFolderPath = new StringBuffer(folderPath);
        File uploadedFolder = new File(sbFolderPath.toString());
        if (!uploadedFolder.exists()) {
            return;
          }
          String[] childrenFolder = uploadedFolder.list();
          for (int i = 0; i < childrenFolder.length; i++) {
            File currentFolder = new File(sbFolderPath + File.separator + childrenFolder[i]);
            if (!currentFolder.isDirectory()) { // skip ., .., other directories, etc.
              continue;
            }
            if (currentFolder.getName().startsWith("orgName")) { // name match
                System.out.println("removing " + currentFolder.getPath());
                if (!deleteDir(currentFolder)) {
                    System.err.println("Couldn't remove " + currentFolder.getPath());
                }
                }
            }
    }
    /** Deletes all files and sub directories under dir.
     Returns true if all deletions were successful.
     If a deletion fails, the method stops attempting to delete and returns false.
     **/
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }
    protected ProprietaryTrialDTO getMockProprietaryTrialDTO() {
        ProprietaryTrialDTO trialDTO = new ProprietaryTrialDTO();
        trialDTO.setAssignedIdentifier("assignedIdentifier");
        trialDTO.setOfficialTitle("officialTitle");
        trialDTO.setPhaseCode("II");
        trialDTO.setPrimaryPurposeCode("TREATMENT");
        trialDTO.setTrialType("Interventional");
        trialDTO.setLeadOrganizationIdentifier("1");
        trialDTO.setLeadOrganizationName("leadOrganizationName");
        trialDTO.setLeadOrgTrialIdentifier("leadOrgTrialIdentifier");
        trialDTO.setIdentifier("1");
        trialDTO.setStudyProtocolId("1");
        SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId(UUID.randomUUID().toString());
        summarySp.setOrgId("1");
        summarySp.setOrgName("SummaryFourOrgName");
        trialDTO.getSummaryFourOrgIdentifiers().add(summarySp);
        trialDTO.setNctIdentifier("nctIdentifier");

        trialDTO.setSiteStatusCode("In Review");
        trialDTO.setSiteStatusDate("12/09/2009");
        trialDTO.setSiteOrganizationIdentifier("siteOrganizationIdentifier");
        trialDTO.setSiteOrganizationName("siteOrganizationName");
        trialDTO.setSitePiIdentifier("sitePiIdentifier");
        trialDTO.setSitePiName("sitePiName");
        trialDTO.setSiteProgramCodeText("siteProgramCodeTxt");
        trialDTO.setLocalSiteIdentifier("localSiteIdentifier");
        trialDTO.setSummaryFourFundingCategoryCode("summaryFourFundingCategoryCode");
        
        List<SubmittedOrganizationDTO> paOrgList = new ArrayList<SubmittedOrganizationDTO>();
        SubmittedOrganizationDTO paOrgDto = new SubmittedOrganizationDTO();
        paOrgDto.setName("SITE01");
        paOrgDto.setRecruitmentStatus(RecruitmentStatusCode.ACTIVE.getCode());
        paOrgDto.setRecruitmentStatusDate("01/01/2012");
        paOrgDto.setSiteLocalTrialIdentifier("SITE01");
        paOrgList.add(paOrgDto);
        
        paOrgDto = new SubmittedOrganizationDTO();
        paOrgDto.setName("SITE02");
        paOrgDto.setRecruitmentStatus(RecruitmentStatusCode.ACTIVE.getCode());
        paOrgDto.setSiteLocalTrialIdentifier("SITE02");
        Date now = new Date();  
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");  
        String currentDate = df.format(now);
        paOrgDto.setRecruitmentStatusDate(currentDate);
        paOrgDto.setSiteLocalTrialIdentifier("SITE02");
        paOrgDto.setDateOpenedforAccrual(currentDate);
        paOrgDto.setDateClosedforAccrual(currentDate);
        paOrgList.add(paOrgDto);
        trialDTO.setParticipatingSitesList(paOrgList);        
        trialDTO.setNciGrant(false);
        trialDTO.setAccrualDiseaseCodeSystem("SDC");
        return trialDTO;
    }
    /**
     *
     * @return StudyProtocolBatchDTO
     */
    protected StudyProtocolBatchDTO getBatchDto() {
        StudyProtocolBatchDTO  dto = new StudyProtocolBatchDTO ();
        dto.setTrialType("Interventional");
        dto.setProtcolDocumentFileName("protcolDocumentFileName.doc");
        dto.setIrbApprovalDocumentFileName("irbApprovalDocumentFileName.doc");
        dto.setParticipatinSiteDocumentFileName("participatinSiteDocumentFileName.doc");
        dto.setInformedConsentDocumentFileName("informedConsentDocumentFileName.doc");
        dto.setOtherTrialRelDocumentFileName("otherTrialRelDocumentFileName.doc");
        dto.setProtocolHighlightDocFileName("protocolHighlightDocFileName.doc");
        dto.setChangeRequestDocFileName("changeRequestDocFileName.doc");
        dto.setSubmissionType("O");
        dto.setLeadOrgName("leadOrgName");
        dto.setLeadOrgStreetAddress("leadOrgStreetAddress");
        dto.setLeadOrgCity("leadOrgCity");
        dto.setLeadOrgState("MD");
        dto.setLeadOrgZip("leadOrgZip");
        dto.setLeadOrgCountry("USA");
        dto.setLeadOrgEmail("leadOrgE@mail.co");
        dto.setLeadOrgPhone("leadOrgPhone");
        dto.setSponsorOrgName("sponsorOrgName");
        dto.setSponsorStreetAddress("sponsorStreetAddress");
        dto.setSponsorCity("sponsorCity");
        dto.setSponsorCountry("JPN");
        dto.setSponsorZip("sponsorZip");
        dto.setSponsorEmail("sponsorE@mail.co");
        dto.setSponsorPhone("sponsorPhone");
        dto.setPiFirstName("piFirstName");
        dto.setPiLastName("piLastName");
        dto.setPiStreetAddress("piStreetAddress");
        dto.setPiCity("piCity");
        dto.setPiState("Sta");
        dto.setPiZip("piZip");
        dto.setPiCountry("AUS");
        dto.setPiEmail("piE@mail.co");
        dto.setPiPhone("piPhone");
        dto.setResponsibleParty(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);
        dto.setSponsorContactType("Personal");
        dto.setSponsorContactFName("sponsorContactFName");
        dto.setSponsorContactLName("sponsorContactLName");
        dto.setSponsorContactStreetAddress("sponsorContactStreetAddress");
        dto.setSponsorContactCity("sponsorContactCity");
        dto.setSponsorContactState("OC");
        dto.setSponsorContactCountry("CAN");
        dto.setSponsorContactZip("sponsorContactZip");
        dto.setSponsorContactEmail("sponsorContactE@mail.co");
        dto.setLocalProtocolIdentifier("localProtocolIdentifier");
        dto.setTitle("title");
        dto.setCurrentTrialStatus("Approved");
        dto.setCurrentTrialStatusDate("12/10/2009");
        dto.setStudyStartDate("12/12/2009");
        dto.setStudyStartDateType("Anticipated");
        dto.setPrimaryCompletionDate("12/12/2009");
        dto.setPrimaryCompletionDateType("Anticipated");
        dto.setPhase("II");
        dto.setPrimaryPurpose("Other");
        dto.setPrimaryPurposeAdditionalQualifierCode("primaryPurposeOtherValueSp");
        dto.setDataMonitoringCommitteeAppointedIndicator("No");
        dto.setFdaRegulatoryInformationIndicator("Yes");
        dto.setSection801Indicator("YES");
        dto.setDelayedPostingIndicator("NO");
        dto.setOversightAuthorityCountry("United States");
        dto.setOversightOrgName("Federal Government");
        dto.setCtGovXmlIndicator(true);
        dto.setNciGrant(Boolean.TRUE);
        return dto;
    }
    /**
     * @param dto
     */
    protected void getBatchSumm4Info(StudyProtocolBatchDTO dto) {
        dto.setSumm4OrgName("summ4OrgName");
        dto.setSumm4OrgStreetAddress("summ4OrgStreetAddress");
        dto.setSumm4City("summ4City");
        dto.setSumm4State("MD");
        dto.setSumm4Country("USA");
        dto.setSumm4Email("summ4E@mail.co");
        dto.setSumm4Phone("summ4Phone");
        dto.setSumm4Zip("summ4Zip");
    }
    /**
     * @param dto
     */
    protected void getBatchIndIde(StudyProtocolBatchDTO dto) {
        dto.setIndExpandedAccessStatus("Available");
        dto.setIndGrantor("CDRH");
        dto.setIndHasExpandedAccess("YES");
        dto.setIndHolderType("Industry");
        dto.setIndNCIDivision("NCI");
        dto.setIndNIHInstitution("NIH");
        dto.setIndNumber("indNumber");
        dto.setIndType("IDE");
    }
    /**
     * @param dto
     */
    protected void getBatchMultipleIndIde(StudyProtocolBatchDTO dto) {
        dto.setIndExpandedAccessStatus("indExpandedAccessStatus1;indExpandedAccessStatus2");
        dto.setIndGrantor("indGrantor1;indGrantor2");
        dto.setIndHasExpandedAccess("indHasExpandedAccess1;indHasExpandedAccess2");
        dto.setIndHolderType("indHolderType1;indHolderType2");
        dto.setIndNCIDivision("indNCIDivision1;indNCIDivision2");
        dto.setIndNIHInstitution("indNIHInstitution1;indNIHInstitution2");
        dto.setIndNumber("indNumber1;indNumber2");
        dto.setIndType("indType1;indType2");
    }
    /**
     * @param dto
     */
    protected void getBatchGrants(StudyProtocolBatchDTO dto) {
        dto.setNihGrantFundingMechanism("nihGrantFundingMechanism");
        dto.setNihGrantInstituteCode("nihGrantInstituteCode");
        dto.setNihGrantNCIDivisionCode("nihGrantNCIDivisionCode");
        dto.setNihGrantSrNumber("12345");
        dto.setNihGrantFundingPct("100");
    }
    /**
     * @param dto
     */
    protected void getBatchMultipleGrants(StudyProtocolBatchDTO dto) {
        dto.setNihGrantFundingMechanism("nihGrantFundingMechanism1;nihGrantFundingMechanism2");
        dto.setNihGrantInstituteCode("nihGrantInstituteCode1;nihGrantInstituteCode2");
        dto.setNihGrantNCIDivisionCode("nihGrantNCIDivisionCode1;nihGrantNCIDivisionCode2");
        dto.setNihGrantSrNumber("SrNum1;SrNum2");
        dto.setNihGrantFundingPct("50;50");
    }
    /**
    *
    * @param <T> t
    * @param obj o
    */
    protected  <T> void addUpdObject(T obj) {
       Session session = PaHibernateUtil.getCurrentSession();
       Transaction transaction = session.beginTransaction();
       session.saveOrUpdate(obj);
       transaction.commit();
   }

   /**
    *
    * @param <T> t
    * @param oList o
    */
   protected  <T> void addUpdObjects(ArrayList<T> oList) {
       for (T obj : oList) {
           addUpdObject(obj);
       }
   }

   /**
    *
    */
   protected void primeData() {
       Organization org = new Organization();
       org.setName("Mayo University");
       org.setIdentifier("1");
       User abstractor = new User();
       abstractor.setLoginName("abstractor");
       org.setUserLastUpdated(abstractor);
       java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
       org.setDateLastUpdated(now);
       org.setStatusCode(EntityStatusCode.PENDING);
       addUpdObject(org);
       HealthCareFacility hfc = new HealthCareFacility();
       hfc.setOrganization(org);
       hfc.setIdentifier("1");
       hfc.setStatusCode(StructuralRoleStatusCode.PENDING);
       addUpdObject(hfc);


       ResearchOrganization rOrg = new ResearchOrganization();
       rOrg.setOrganization(org);
       rOrg.setStatusCode(StructuralRoleStatusCode.ACTIVE);
       rOrg.setIdentifier("1");
       addUpdObject(rOrg);
       OrganizationalContact orgContact = new OrganizationalContact();
       orgContact.setIdentifier("1");
       orgContact.setOrganization(org);
       orgContact.setStatusCode(StructuralRoleStatusCode.ACTIVE);
       addUpdObject(orgContact);

       Person per = new Person();
       per.setFirstName("firstName");
       per.setLastName("lastName");
       per.setIdentifier("1");
       per.setStatusCode(EntityStatusCode.PENDING);
       addUpdObject(per);
       per = new Person();
       per.setFirstName("firstNameOne");
       per.setLastName("lastNameTwo");
       per.setIdentifier("2");
       per.setStatusCode(EntityStatusCode.PENDING);
       addUpdObject(per);

       Country country = new Country();
       country.setAlpha2("ZZ");
       country.setAlpha3("ZZZ");
       country.setName("Zanzibar");
       country.setNumeric("67");
       addUpdObject(country);
       PaHibernateUtil.getCurrentSession().clear();
   }

   protected String getTomorrowDate() {

       Date tomorrow = getTomorrowAsDate();
       return PAUtil.normalizeDateString(tomorrow.toString());
   }

    /**
     * @return
     */
    protected Date getTomorrowAsDate() {
        // get a calendar instance, which defaults to "now"
        Calendar calendar = Calendar.getInstance();

        // get a date to represent "today"
        Date today = calendar.getTime();
        System.out.println("today:    " + today);

        // add one day to the date/calendar
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        // now get "tomorrow"
        Date tomorrow = calendar.getTime();
        return tomorrow;
    }

   protected StudyProtocolDTO setupSpDto() {
       Ii spId = new Ii();
       spId.setExtension("1");

       StudyProtocolDTO spDto = new StudyProtocolDTO();
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
       spDto.setPublicDescription(StConverter.convertToSt("public description"));
       spDto.setDelayedpostingIndicator(BlConverter.convertToBl(true));
       spDto.setPublicTitle(StConverter.convertToSt("public title"));
       spDto.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(true));
       spDto.setNciGrant(BlConverter.convertToBl(true));


       DSet<Ii> secondaryIdentifiers = new DSet<Ii>();
       Ii assignedId = new Ii();
       assignedId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
       assignedId.setExtension("NCI_2010_0001");
       Set<Ii> iis = new HashSet<Ii>();
       iis.add(assignedId);
       secondaryIdentifiers.setItem(iis);
       spDto.setSecondaryIdentifiers(secondaryIdentifiers);

       return spDto;
   }


    protected ProgramCodeDTO createProgramCode(Long id, String code, String name) {
        ProgramCodeDTO pg1 = new ProgramCodeDTO();
        pg1.setProgramCode(code);
        pg1.setProgramName(name);
        pg1.setActive(true);
        pg1.setId(id);
        return pg1;
    }
}
