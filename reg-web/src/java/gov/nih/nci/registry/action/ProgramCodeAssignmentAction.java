package gov.nih.nci.registry.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static gov.nih.nci.pa.enums.StudyStatusCode.ACTIVE;
import static gov.nih.nci.pa.enums.StudyStatusCode.APPROVED;
import static gov.nih.nci.pa.enums.StudyStatusCode.ENROLLING_BY_INVITATION;
import static gov.nih.nci.pa.enums.StudyStatusCode.IN_REVIEW;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;

/**
 * To manage program code assignments.
 * For details refer to PO-9192 (attachment PPT) page 10
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.TooManyMethods", "PMD.TooManyFields" })
public class ProgramCodeAssignmentAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 4866645110688822061L;
    private static final String UTF_8 = "UTF-8";
    private static final Long NONE_PROGRAM_CODE = -1L;
    private static final Logger LOG = Logger.getLogger(ProgramCodeAssignmentAction.class);
    private static final String IS_SITE_ADMIN = "isSiteAdmin";
    private static final String ERROR_MSG_KEY = "msg";
    private static final String AJAX_RETURN_SUCCESS_KEY = "status";
    private static final String FAMILY_DTO_KEY = "family_dto";

    private static final StudyStatusCode[] ACTIVE_PROTOCOL_STATUSES = new StudyStatusCode[]{ACTIVE, APPROVED,
            IN_REVIEW, ENROLLING_BY_INVITATION,
            TEMPORARILY_CLOSED_TO_ACCRUAL,
            TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION};


    private FamilyProgramCodeService familyProgramCodeService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private RegistryUserServiceLocal registryUserService;
    private ParticipatingOrgServiceLocal participatingOrgService;
    private StudyProtocolServiceLocal studyProtocolService;
    private PAOrganizationServiceRemote paOrganizationService;

    private List<OrgFamilyDTO> affiliatedFamilies;
    private Long familyPoId;
    private FamilyDTO familyDto;
    private Long studyProtocolId;
    private Long programCodeId;
    private Integer reportingPeriodLength;
    private Date reportingPeriodEndDate;

    //the following are used by assign, unassign, replace operations
    private String studyProtocolIdListParam;
    private String pgcParam;
    private String pgcListParam;

    /**
     *  Will return the studyProtocolId
     * @return the studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * Will set the studyProtocolId
     * @param studyProtocolId the studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * Gets affiliatedFamilies
     * @return the affiliatedFamilies
     */
    public List<OrgFamilyDTO> getAffiliatedFamilies() {
        return affiliatedFamilies;
    }

    /**
     * Sets affiliatedFamilies
     * @param affiliatedFamilies  the affiliatedFamilies
     */
    public void setAffiliatedFamilies(List<OrgFamilyDTO> affiliatedFamilies) {
        this.affiliatedFamilies = affiliatedFamilies;
    }

    /**
     * Sets familyProgramCodeService
     * @param familyProgramCodeService  the familyProgramCodeService
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }

    /**
     * Sets the organization service
     * @param paOrganizationService  - the organization service in PA
     */
    public void setPaOrganizationService(PAOrganizationServiceRemote paOrganizationService) {
        this.paOrganizationService = paOrganizationService;
    }

    /**
     * Will populate the affiliatedFamilies
     * @param orgId the orgd
     * @throws PAException - is thrown on any backend exception
     */
    private void loadAffiliatedFamilies(Long orgId) throws PAException {
        List<OrgFamilyDTO> families = isProgramCodeAdmin() ? fetchAllFamilies() : FamilyHelper.getByOrgId(orgId);
        setAffiliatedFamilies(families);
    }

    /**
     * Checks if the logged-in user is a program code administrator
     * @return true if program code admin, false otherwise
     */
    public boolean isProgramCodeAdmin() {
        return ServletActionContext.getRequest()
                .isUserInRole(Constants.PROGRAM_CODE_ADMINISTRATOR);
    }

    /**
     * Gets familyPoId
     * @return  familyPoId - the familyPOId
     */
    public Long getFamilyPoId() {
        return familyPoId;
    }

    /**
     * Sets familyPoId
     * @param familyPoId - the familyPoId
     */
    public void setFamilyPoId(Long familyPoId) {
        this.familyPoId = familyPoId;
    }


    /**
     * The FamilyDto
     * @return the familyDto
     */
    public FamilyDTO getFamilyDto() {
        return familyDto;
    }

    /**
     * Sets the FamilyDto
     * @param familyDto the familyDto
     */
    public void setFamilyDto(FamilyDTO familyDto) {
        this.familyDto = familyDto;
    }

    /**
     * A commaseperated list of study ids
     * @return  studyids
     */
    public String getStudyProtocolIdListParam() {
        return studyProtocolIdListParam;
    }

    /**
     * Set the list of studyids
     * @param studyProtocolIdListParam the commaseperated list of studyIds
     */
    public void setStudyProtocolIdListParam(String studyProtocolIdListParam) {
        this.studyProtocolIdListParam = studyProtocolIdListParam;
    }

    /**
     * The program code
     * @return the program code
     */
    public String getPgcParam() {
        return pgcParam;
    }

    /**
     * Sets the program code
     * @param pgcParam - the program code
     */
    public void setPgcParam(String pgcParam) {
        this.pgcParam = pgcParam;
    }

    /**
     * Sets the list of program code
     * @return the commaseperated list of program codes
     */
    public String getPgcListParam() {
        return pgcListParam;
    }

    /**
     * The program code list
     * @param pgcListParam - the program codes comma seperated.
     */
    public void setPgcListParam(String pgcListParam) {
        this.pgcListParam = pgcListParam;
    }

    /**
     * @return ProtocolQueryServiceLocal
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }


    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * Will return the participatingOrgService
     * @return the participatingOrgService
     */
    public ParticipatingOrgServiceLocal getParticipatingOrgService() {
        return participatingOrgService;
    }

    /**
     * Will set the participatingOrgService
     * @param participatingOrgService  the participatingOrgService
     */
    public void setParticipatingOrgService(ParticipatingOrgServiceLocal participatingOrgService) {
        this.participatingOrgService = participatingOrgService;
    }

    /**
     * Will return the study protocol service
     * @return the studyProtocolService
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * Will set the study protocol service
     * @param studyProtocolService the studyProtocolService
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * Gets the program code id
     * @return - program code id
     */
    public Long getProgramCodeId() {
        return programCodeId;
    }

    /**
     * Sets the program code id
     * @param programCodeId - program code id
     */
    public void setProgramCodeId(Long programCodeId) {
        this.programCodeId = programCodeId;
    }

    /**
     * The reporting period length
     * @return - the period length
     */
    public Integer getReportingPeriodLength() {
        return reportingPeriodLength;
    }

    /**
     * The reporting period length
     * @param reportingPeriodLength   the length
     */
    public void setReportingPeriodLength(Integer reportingPeriodLength) {
        this.reportingPeriodLength = reportingPeriodLength;
    }

    /**
     * The end date
     * @return   the end date
     */
    public Date getReportingPeriodEndDate() {
        return reportingPeriodEndDate;
    }

    /**
     * The end date
     * @param reportingPeriodEndDate the end date
     */
    public void setReportingPeriodEndDate(Date reportingPeriodEndDate) {
        this.reportingPeriodEndDate = reportingPeriodEndDate;
    }

    /**
     * Will initialize the action
     */
    @Override
    public void prepare() {
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
        protocolQueryService = PaRegistry.getCachingProtocolQueryService();
        registryUserService = PaRegistry.getRegistryUserService();
        participatingOrgService = PaRegistry.getParticipatingOrgService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        paOrganizationService = PaRegistry.getPAOrganizationService();
    }

    /**
     * The RegistryUserServiceLocal
     * @return    the registryUserService
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * Sets the RegistryUserServiceLocal
     * @param registryUserService the RegistryUserServiceLocal
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * Will set the basic view action
     * @return - the view name
     */
    @Override
    public String execute() {
       return showDefaultViewPage();
    }

    /**
     * Will be invoked when user changes the family in dropdown.
     * @return - the view name
     */
    public String changeFamily() {
       return showDefaultViewPage();
    }

    /**
     *  Will return the trial data to be shown on UI
     * @return  StreamResult - the json object array
     * @throws UnsupportedEncodingException - for encoding issues
     */
    public StreamResult findTrials() throws UnsupportedEncodingException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();

        boolean updated = false;
        boolean lengthChanged = false;
        boolean endDateChanged = false;

        try {

            if (familyPoId != null) {

                loadFamily();

                Integer length = reportingPeriodLength != null
                        ? reportingPeriodLength : familyDto.getReportingPeriodLength();
                Date endDate = reportingPeriodEndDate != null
                        ? reportingPeriodEndDate : familyDto.getReportingPeriodEndDate();
                Date startDate = DateUtils.addMonths(endDate , -1 * length);

                if (!length.equals(familyDto.getReportingPeriodLength())) {
                    familyDto.setReportingPeriodLength(length);
                    lengthChanged = true;
                }
                if (!isSameDay(endDate, familyDto.getReportingPeriodEndDate())) {
                    familyDto.setReportingPeriodEndDate(endDate);
                    endDateChanged = true;
                }
                if (lengthChanged || endDateChanged) {
                    familyProgramCodeService.update(familyDto);
                    updated = true;
                }

                populateTrials(startDate, endDate, arr);
            }

        } catch (PAException pae) {
            LOG.error("Error finding the trials", pae);
        }

        root.put("updated", updated);
        root.put("lengthChanged", lengthChanged);
        root.put("endDateChanged", endDateChanged);
        root.put("data", arr);

        return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
    }

    /**
     *  Will unassign program code from trial
     * @return  StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult unassignProgramCode() throws IOException {
        try {
            loadFamily();
            ProgramCodeDTO programCode = familyDto.findProgramCodeDTOByCode(pgcParam);
            studyProtocolService.unAssignProgramCode(studyProtocolId, programCode);
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "REMOVED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("unassignProgramCode - studyProtocol:"
                    + getStudyProtocolId()
                    + "programCode:" + getPgcParam());
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }


    /**
     *  Will assign program code from trial
     * @return  StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult assignProgramCode() throws IOException {
        try {
            loadFamily();
            ProgramCodeDTO programCode = familyDto.findProgramCodeDTOByCode(pgcParam);
            studyProtocolService.assignProgramCodesToTrials(Arrays.asList(studyProtocolId),
                    familyPoId, Arrays.asList(programCode));
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "ADDED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("assignProgramCode - studyProtocol:"
                    + getStudyProtocolId()
                    + "programCode:" + getPgcParam());
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }


    /**
     *  Will assign program codes to the trials
     * @return  StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult assignProgramCodesToTrials() throws IOException {

        try {
            //studies
             List<Long> studyProtocolIds = new ArrayList<Long>();
            for (String trialId : getStudyProtocolIdListParam().split(",")) {
                studyProtocolIds.add(Long.parseLong(trialId));
            }
            //program codes
            loadFamily();
            List<ProgramCodeDTO> programCodes = new ArrayList<ProgramCodeDTO>();
            for (String code: getPgcListParam().split(",")) {
                ProgramCodeDTO programCode = familyDto.findProgramCodeDTOByCode(code);
                programCodes.add(programCode);
            }

            //assign
            studyProtocolService.assignProgramCodesToTrials(studyProtocolIds, familyPoId, programCodes);
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "ADDED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("assignProgramCodesToTrials - studyProtocolIds:"
                    + getStudyProtocolIdListParam()
                    + "programCodes:" + getPgcListParam());
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }


    /**
     * Will unassign program codes from the trials
     *
     * @return StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult unassignProgramCodesFromTrials() throws IOException {

        try {
            List<Long> studyProtocolIds = new ArrayList<Long>();
            for (String trialId : getStudyProtocolIdListParam().split(",")) {
                studyProtocolIds.add(Long.parseLong(trialId));
            }
            //program codes
            loadFamily();
            List<ProgramCodeDTO> programCodes = new ArrayList<ProgramCodeDTO>();
            for (String code: getPgcListParam().split(",")) {
                ProgramCodeDTO programCode = familyDto.findProgramCodeDTOByCode(code);
                programCodes.add(programCode);
            }

            studyProtocolService.unassignProgramCodesFromTrials(studyProtocolIds, programCodes);
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "REMOVED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("unassignProgramCodesFromTrials - studyProtocolIds:"
                    + getStudyProtocolIdListParam()
                    + "programCodes:" + getPgcListParam());
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }

    /**
     * Will replace program codes on selected trials
     *
     * @return StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult replaceProgramCodesInTrials() throws IOException {
        try {
            List<Long> studyProtocolIds = new ArrayList<Long>();
            for (String trialId : getStudyProtocolIdListParam().split(",")) {
                studyProtocolIds.add(Long.parseLong(trialId));
            }
            //program codes
            loadFamily();
            List<ProgramCodeDTO> programCodes = new ArrayList<ProgramCodeDTO>();
            for (String code: getPgcListParam().split(",")) {
                ProgramCodeDTO programCode = familyDto.findProgramCodeDTOByCode(code);
                programCodes.add(programCode);
            }
            ProgramCodeDTO programCode = familyDto.findProgramCodeDTOByCode(pgcParam);
            studyProtocolService.replaceProgramCodesOnTrials(studyProtocolIds, getFamilyPoId(), programCode,
                    programCodes);
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "REPLACED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("replaceProgramCodesInTrials - studyProtocolIds:"
                    + getStudyProtocolIdListParam()
                    + ", programCodesFrom:" + getPgcParam()
                    + ", programCodesTo:" + getPgcListParam());
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }

    /**
     * Will return the sites and investigators
     *
     * @return json having sites and investigators
     * @throws IOException - when error
     */
    public StreamResult participation() throws IOException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);

        try {
            if (familyPoId != null && studyProtocolId != null) {
                List<ParticipatingOrgDTO> treatingSites = participatingOrgService.getTreatingSites(studyProtocolId);
                List<Long> associatedOrgIds = FamilyHelper.getRelatedOrgsInFamily(familyPoId);
                for (ParticipatingOrgDTO site : treatingSites) {
                    if (associatedOrgIds.contains(Long.valueOf(site.getPoId()))) {
                        JSONObject json = new JSONObject();
                        json.put("site", site.getName());

                        List<PaPersonDTO> allInvestigators = new ArrayList<PaPersonDTO>();
                        allInvestigators.addAll(site.getPrincipalInvestigators());
                        allInvestigators.addAll(site.getSubInvestigators());
                        StringBuilder sb = new StringBuilder();
                        for (PaPersonDTO person : allInvestigators) {
                            sb.append(sb.length() > 0 ? "; " : "");
                            sb.append(person.getLastName());
                            sb.append(", ");
                            sb.append(person.getFirstName());
                        }
                        json.put("investigator", sb.toString());
                        arr.put(json);
                    }
                }
            }

            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("error while checking my participation", pae);
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }

    /**
     * Will reutrn the PA organization id, given the PO id of the organization
     * @param poOrgIds - list of PO organization ID
     * @return List of PA organization ID
     * @throws PAException - on error
     */
    private List<Long> fetchOrganizationIds(List<Long> poOrgIds) throws PAException {
        List<Long> orgIds = new ArrayList<Long>();
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            for (Long id : poOrgIds) {
                Organization org = new Organization();
                org.setIdentifier(id.toString());
                Organization loaded = paOrganizationService.getOrganizationByIndetifers(org);
                if (loaded != null) {
                    orgIds.add(loaded.getId());
                }
            }
        }
        return orgIds;
    }

    /*
     * Tells if trial is already associated with a program code in family
     */
    private boolean hasMatchingProgramCodes(List<Long> programCodeIds,
                                            List<Long> familyProgramCodeIds,
                                            StudyProtocolQueryDTO trial) {

        //Note:- this method is invoked only if NONE is in the options

        if (CollectionUtils.isEmpty(trial.getProgramCodes())) {
            return true;
        }

        //check if valid for None option (ie not associated to others)
        boolean validForNone = true;
        for (ProgramCodeDTO pgc : trial.getProgramCodes()) {
            if (familyProgramCodeIds.contains(pgc.getId())) {
                validForNone = false;
            }
        }
        //If only None is present in options, return that
        if (programCodeIds.size() == 1) {
            return validForNone;
        }

        //check if other options are valid
        boolean validForOthers = false;
        for (ProgramCodeDTO pgc : trial.getProgramCodes()) {
            if (programCodeIds.contains(pgc.getId())) {
               validForOthers = true;
            }
        }

        return validForNone || validForOthers;
    }

    private void populateTrials(Date startDate, Date endDate, JSONArray arr) throws PAException {
        LOG.debug("populating trials [familyPOId : " + familyPoId + "]");

        //process program codes in filter
        List<Long> programCodeIds = new ArrayList<Long>();
        if (StringUtils.isNotEmpty(pgcListParam)) {
            for (String p :  StringUtils.split(pgcListParam, ",")) {
                programCodeIds.add(Long.parseLong(p));
            }
        }

        List<Long> familyProgramCodeIds = new ArrayList<Long>();
        for (ProgramCodeDTO pgc : familyDto.getProgramCodes()) {
            familyProgramCodeIds.add(pgc.getId());
        }

        StudyProtocolQueryCriteria spQueryCriteria = new StudyProtocolQueryCriteria();


        spQueryCriteria.populateReportingPeriodStatusCriterion(startDate, endDate, ACTIVE_PROTOCOL_STATUSES);
        spQueryCriteria.setExcludeRejectProtocol(true);
        spQueryCriteria.setExcludeTerminatedTrials(true);
        List<Long> orgPOIds = FamilyHelper.getRelatedOrgsInFamily(familyPoId);
        List<Long> orgIds = fetchOrganizationIds(orgPOIds);
        spQueryCriteria.getParticipatingSiteIds().addAll(orgIds);

        boolean hasNone = programCodeIds.contains(NONE_PROGRAM_CODE);
        if (!hasNone) {
            spQueryCriteria.getProgramCodeIds().addAll(programCodeIds);
        }


        List<StudyProtocolQueryDTO> trials = protocolQueryService.getStudyProtocolByCriteria(spQueryCriteria,
                SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO);
        for (StudyProtocolQueryDTO trial : trials) {
            if (hasNone && !hasMatchingProgramCodes(programCodeIds, familyProgramCodeIds, trial)) {
                continue;
            }

            JSONObject o = new JSONObject();
            o.put("studyProtocolId", trial.getStudyProtocolId());
            o.put("nciIdentifier", trial.getNciIdentifier());
            o.put("title", trial.getOfficialTitle());
            o.put("identifiers", trial.getAllIdentifiersAsString());
            o.put("leadOrganizationName", trial.getLeadOrganizationName());
            o.put("piFullName", StringUtils.equals("null", trial.getPiFullName()) ? ""
                    : StringUtils.trimToEmpty(trial.getPiFullName()));
            o.put("trialStatus", trial.getStudyStatusCode().getCode());
            o.put("DT_RowId", "trial_" + trial.getStudyProtocolId());

            JSONArray pgcArr = new JSONArray();
            for (ProgramCodeDTO pg : trial.getProgramCodesAsOrderedList()) {
                JSONObject pgcObj = new JSONObject();
                pgcObj.put("id", pg.getId());
                pgcObj.put("code", pg.getProgramCode());
                pgcObj.put("name", pg.getProgramName());
                pgcArr.put(pgcObj);
            }
            o.put("programCodes", pgcArr);
            arr.put(o);
        }

    }

    /**
     * Will load data and return the view name
     * @return - the view name
     */
    private String showDefaultViewPage()  {

        String returnPage = ERROR;

        try {

            if (isSiteAdmin()) {
                Long affiliagedOrgId = getAffiliatedOrganizationId();
                loadAffiliatedFamilies(affiliagedOrgId);
                selectDefaultFamilyPoId();
                loadFamily();
                if (familyDto != null) {
                    reportingPeriodLength = familyDto.getReportingPeriodLength();
                    reportingPeriodEndDate = familyDto.getReportingPeriodEndDate();
                }
                returnPage = SUCCESS;
            }

        } catch (PAException e) {
            LOG.error("Error while loading data", e);
        }

        return returnPage;

    }

    /**
     * Will select the default family PO ID for non-program code Admins
     */
    private void selectDefaultFamilyPoId() {
        if (!isProgramCodeAdmin() && CollectionUtils.isNotEmpty(affiliatedFamilies)) {
           familyPoId = affiliatedFamilies.get(0).getId();
        }
    }

    /**
     * Loads the family from database
     */
    /**
     * Loads the family from database
     */
    private void loadFamily() {
        FamilyDTO sessionFamilyDto = (FamilyDTO)  ServletActionContext.getRequest()
                .getSession()
                .getAttribute(FAMILY_DTO_KEY);

        Long poId = familyPoId != null ? familyPoId : (sessionFamilyDto != null ? sessionFamilyDto.getPoId() : null);
        if (poId == null) {
            return;
        }

        familyDto = familyProgramCodeService.getFamilyDTOByPoId(poId);

        if (CollectionUtils.isNotEmpty(affiliatedFamilies)) {
            for (OrgFamilyDTO of : affiliatedFamilies) {
                if (of.getId().equals(poId)) {
                    familyDto.setName(of.getName());
                }
            }
        }
        ServletActionContext.getRequest().getSession().setAttribute(FAMILY_DTO_KEY, familyDto);
    }

    /**
     * Will return the affiliated site from logged-in user.
     * @return  affiliatedOrganizationId
     * @throws PAException on error
     */
    private Long getAffiliatedOrganizationId() throws PAException {
        HttpServletRequest request = ServletActionContext.getRequest();
        RegistryUser registryUser = registryUserService.getUser(request.getRemoteUser());
        return registryUser.getAffiliatedOrganizationId();
    }

    /**
     * Will check if user session have siteAdmin attribute.
     * @return true for site administrator
     */
    private boolean isSiteAdmin() {
        Object value =  ServletActionContext.getRequest().getSession().getAttribute(IS_SITE_ADMIN);
        return value != null && (boolean) value;
    }


    /**
     * Get all family DTOs
     * @return List<OrgFamilyDTO> List<OrgFamilyDTO>
     * @throws PAException
     */
    private List<OrgFamilyDTO> fetchAllFamilies() throws PAException {
        List<OrgFamilyDTO> families = FamilyHelper.getAllFamilies();
        Collections.sort(families, new Comparator<OrgFamilyDTO>() {
            @Override
            public int compare(OrgFamilyDTO o1, OrgFamilyDTO o2) {
                return StringUtils
                        .defaultString(o1.getName())
                        .compareTo(
                                StringUtils.defaultString(o2.getName()));
            }
        });
        return families;
    }


    private boolean isSameDay(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return DateUtils.isSameDay(d1, d2);
    }

}
