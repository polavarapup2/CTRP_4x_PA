package gov.nih.nci.registry.action;

import static gov.nih.nci.pa.enums.StudyStatusCode.ACTIVE;
import static gov.nih.nci.pa.enums.StudyStatusCode.APPROVED;
import static gov.nih.nci.pa.enums.StudyStatusCode.ENROLLING_BY_INVITATION;
import static gov.nih.nci.pa.enums.StudyStatusCode.IN_REVIEW;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Program codes
 * @author lalit-sb
 *
 */

@SuppressWarnings({  "PMD.TooManyMethods" })
public class ProgramCodesAction extends ActionSupport implements Preparable, ServletRequestAware, ServletResponseAware {
    
    private static final long serialVersionUID = 4866651110688880068L;    
    
    private static final Logger LOG = Logger
            .getLogger(ProgramCodesAction.class);

    private static final StudyStatusCode[] ACTIVE_PROTOCOL_STATUSES = new StudyStatusCode[]{ACTIVE, APPROVED,
            IN_REVIEW, ENROLLING_BY_INVITATION,
            TEMPORARILY_CLOSED_TO_ACCRUAL,
            TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION};
    
    private static final String UTF_8 = "UTF-8";
    
    // to address PMD error : The String literal "data" appears 5 times in this file
    private static final String DATA = "data";   
    
    private static final String IS_PROGRAM_CODE_ADMIN = "programCodeAdmin";
    private static final String FAMILY_DTO_KEY = "family_dto";
    
    private List<FamilyDTO> familyDTOs = new ArrayList<>();    
    private FamilyDTO selectedFamilyDTO;

    private StudyProtocolServiceLocal studyProtocolService;
    private FamilyProgramCodeService familyProgramCodeService;
    private ProtocolQueryServiceLocal protocolQueryService;

    private RegistryUserService registryUserService;    

    private LookUpTableServiceRemote lookUpTableServiceRemote;    
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private Long poId;
    private Long selectedDTOId;
    private Long programCodeIdSelectedForDeletion;

    /**
     * execute
     * @throws PAException paexception
     * @throws ParseException parseexception
     * @return success value
     */
    @Override
    public String execute() throws PAException, ParseException {   
        request = ServletActionContext.getRequest();
        
        RegistryUser registryUser = registryUserService.getUser(request.getRemoteUser());  
        if (request.getSession().getAttribute(IS_PROGRAM_CODE_ADMIN) == null) {
            request.getSession().setAttribute(IS_PROGRAM_CODE_ADMIN, request
                    .isUserInRole(Constants.PROGRAM_CODE_ADMINISTRATOR));
        }

        List<OrgFamilyDTO> affiliatedFamilies = new ArrayList<OrgFamilyDTO>();
        if ((Boolean) request.getSession().getAttribute(IS_PROGRAM_CODE_ADMIN)) {
            affiliatedFamilies = getAllFamiliesDto();
        } else {
            affiliatedFamilies = FamilyHelper.getByOrgId(registryUser.getAffiliatedOrganizationId());
        }
        for (OrgFamilyDTO orgFamilyDTO : affiliatedFamilies) {            
            findOrCreateFamilyAndAddToList(orgFamilyDTO);
        }

        pickDefaultFamily();

        return SUCCESS;
    }

    private void pickDefaultFamily() {
        if (familyDTOs.isEmpty()) {
            return;
        }
        FamilyDTO family = (FamilyDTO) ServletActionContext.getRequest().getSession().getAttribute(FAMILY_DTO_KEY);
        Long familyId = selectedDTOId != null ? selectedDTOId : (family != null ? family.getId() : null);
        if (familyId != null) {
            for (FamilyDTO familyDTO : familyDTOs) {
                if (familyDTO.getId().equals(familyId)) {
                    setSelectedFamilyDTO(familyDTO);
                }
            }
        } else {
            setSelectedFamilyDTO(familyDTOs.get(0));
        }
    }

    /**
     * Get all family DTOs
     * @return List<OrgFamilyDTO> List<OrgFamilyDTO>
     * @throws PAException
     */
    private List<OrgFamilyDTO> getAllFamiliesDto() throws PAException {
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
    
    /**
     * Will load the family
     */
    private void loadFamily() {
        selectedFamilyDTO = poId == null ? null : familyProgramCodeService.getFamilyDTOByPoId(poId);
    }
    
    /**
     * Gets program codes for family 
     * @throws UnsupportedEncodingException Unsupported encoding exception
     * @return JSON String
     */
    
    public StreamResult fetchProgramCodesForFamily() throws UnsupportedEncodingException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put(DATA, arr);
        populateProgramCodes(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
    }
    
    private void populateProgramCodes(JSONArray arr) {
        LOG.debug("populating program codes for [familyPOId : " + poId + "]");
        loadFamily();
        if (selectedFamilyDTO != null) {
            for (ProgramCodeDTO programCodeDTO : selectedFamilyDTO.getProgramCodes()) {
                JSONObject o = new JSONObject();
                o.put("programCodeId", programCodeDTO.getId());
                o.put("programName", programCodeDTO.getProgramName());
                o.put("programCode", programCodeDTO.getProgramCode());
                o.put("isActive", programCodeDTO.isActive());
                arr.put(o);
             }
        }

      }
    
    /**
     * Creates a new program code 
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult createProgramCode() throws IOException {
        try {
            LOG.debug("Creating and adding a new program code for [familyPOId : " + poId + "]");
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put(DATA, arr);
            addProgramCode();
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }
    
    private void addProgramCode() throws PAValidationException {
        loadFamily();
        String programCode = request.getParameter("newProgramCode"); 
        String programName = request.getParameter("newProgramName");
        // create and add a new active program code to the family
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setProgramCode(programCode);
        programCodeDTO.setProgramName(programName);
        programCodeDTO.setActive(true);
        familyProgramCodeService.createProgramCode(selectedFamilyDTO, programCodeDTO);
      }
    
    
    private void findOrCreateFamilyAndAddToList(OrgFamilyDTO orgFamilyDTO) throws PAException, ParseException {
        poId = orgFamilyDTO.getId();
        loadFamily();
        if (selectedFamilyDTO == null) {
            String defaultDate = lookUpTableServiceRemote.
                    getPropertyValue("programcodes.reporting.default.end_date");
            String defaultLength = lookUpTableServiceRemote.
                    getPropertyValue("programcodes.reporting.default.length");            
            Date effectiveDate = new SimpleDateFormat(PAUtil.DATE_FORMAT, 
                    Locale.getDefault()).parse(defaultDate);
            FamilyDTO newFamilyDTO = familyProgramCodeService.create(
                    new FamilyDTO(orgFamilyDTO.getId(), effectiveDate, 
                    Integer.parseInt(defaultLength)));
            newFamilyDTO.setName(orgFamilyDTO.getName());    
            familyDTOs.add(newFamilyDTO);
        } else {
            selectedFamilyDTO.setName(orgFamilyDTO.getName());
            familyDTOs.add(selectedFamilyDTO);
        }
    }
    
    private StreamResult handleExceptionDuringAjax(Exception e)
            throws IOException {
        LOG.error(e, e);
        response.addHeader("msg", e.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        return null;
    }
    
    /**
     * Update program code 
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult updateProgramCode() throws IOException {
        try {
            LOG.debug("Updating program code for [familyPOId : " + poId + "]");
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put(DATA, arr);
            editProgramCode();
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }
    
    private void editProgramCode() throws PAValidationException {
        loadFamily();
        
        String currentProgramCodeValue = request.getParameter("currentProgramCode");
        String currentProgramCodeId = request.getParameter("currentProgramCodeId");
        ProgramCodeDTO currentProgramCodeDTO = new ProgramCodeDTO();
        currentProgramCodeDTO.setId(Long.parseLong(currentProgramCodeId));
        currentProgramCodeDTO.setProgramCode(currentProgramCodeValue);
        
        String updatedProgramCode = request.getParameter("updatedProgramCode");
        String updatedProgramName = request.getParameter("updatedProgramName");
        ProgramCodeDTO updatedProgramCodeDTO = new ProgramCodeDTO();
        updatedProgramCodeDTO.setProgramCode(updatedProgramCode);
        updatedProgramCodeDTO.setProgramName(updatedProgramName);
        
        familyProgramCodeService.updateProgramCode(selectedFamilyDTO, currentProgramCodeDTO, updatedProgramCodeDTO);
      }
    
    /**
     * Finds whether program code is associated with a trial
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult isProgramCodeAssignedToATrial() throws IOException {
        try {
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put(DATA, arr);
            arr.put(isProgramCodeAssignedToATrial(programCodeIdSelectedForDeletion));
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }

    /**
     * Tells if program code is associated with any trial.
     * @param programCodeId - the program code id
     * @return true or false
     */
    private boolean isProgramCodeAssignedToATrial(Long programCodeId) {
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setId(programCodeId);
        return familyProgramCodeService.isProgramCodeAssociatedWithATrial(programCodeDTO);
    }
    
    /**
     * Delete program code from db
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult deleteProgramCode() throws IOException {
        try {
            LOG.debug("Deleting program code for [familyPOId : " + poId + "]");
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put(DATA, arr);
            loadFamily();
            ProgramCodeDTO currentProgramCodeDTO = new ProgramCodeDTO();
            currentProgramCodeDTO.setId(programCodeIdSelectedForDeletion);
            
            familyProgramCodeService.deleteProgramCode(selectedFamilyDTO, currentProgramCodeDTO);
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }
    
    /**
     * Inactivate program code from db
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult inactivateProgramCode() throws IOException {
        try {

            LOG.debug("Inactivating program code for [familyPOId : " + poId + ", programCodeId : "
                    + programCodeIdSelectedForDeletion + "]");

            //check remove program codes from active trials
            List<StudyProtocolQueryDTO> list = findMatchingStudies();
            if (CollectionUtils.isNotEmpty(list)) {
                List<Long> studyIds = new ArrayList<Long>();
                for (StudyProtocolQueryDTO sp : list) {
                    studyIds.add(sp.getStudyProtocolId());
                }
                studyProtocolService.unassignProgramCodesFromTrials(studyIds,
                        Arrays.asList(selectedFamilyDTO.findProgramCodeDTO(programCodeIdSelectedForDeletion)));
            }


            ProgramCodeDTO currentProgramCodeDTO = new ProgramCodeDTO();
            currentProgramCodeDTO.setId(programCodeIdSelectedForDeletion);

            //check if the program code is associated with other trials.
            if (isProgramCodeAssignedToATrial(programCodeIdSelectedForDeletion)) {
                familyProgramCodeService.inactivateProgramCode(currentProgramCodeDTO);
            } else {
                familyProgramCodeService.deleteProgramCode(selectedFamilyDTO, currentProgramCodeDTO);
            }


            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put(DATA, arr);
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }
    
    /**
     * Gets study protocols associated to a program code
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult getStudyProtocolsAssociatedToAProgramCode() throws IOException {
        try {
            LOG.debug("Deleting program code for [familyPOId : " + poId + "]");
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put(DATA, arr);
            populateStudyProtocolDTOs(arr);
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }

    /**
     * Will load study protocols matching the program code and is within reporting period of family
     * @return   list of protocols
     */
    private List<StudyProtocolQueryDTO> findMatchingStudies() {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        try {
            LOG.debug("Finding active trials for  [familyPOId : " + poId + ", programCodeId: "
                    + programCodeIdSelectedForDeletion +  "]");

            //finds the protocols that were active any time during the reporting period start and end dates
           loadFamily();

            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
            criteria.setExcludeRejectProtocol(true);
            criteria.setExcludeTerminatedTrials(true);
            criteria.getProgramCodeIds().add(programCodeIdSelectedForDeletion);
            criteria.populateReportingPeriodStatusCriterion(selectedFamilyDTO.findStartDate(),
                    selectedFamilyDTO.getReportingPeriodEndDate(), ACTIVE_PROTOCOL_STATUSES);


            list =  protocolQueryService.getStudyProtocolByCriteria(criteria,
                    SKIP_LAST_UPDATER_INFO, SKIP_ALTERNATE_TITLES, SKIP_OTHER_IDENTIFIERS);
        } catch (PAException e) {
            LOG.error("Unable to find protocols", e);
        }
        return list;
    }

    private void populateStudyProtocolDTOs(JSONArray array) {


        List<StudyProtocolQueryDTO> list = findMatchingStudies();

        for (StudyProtocolQueryDTO dto : list) {
            JSONObject object = new JSONObject();
            object.put("nciIdentifier", dto.getNciIdentifier());
            object.put("leadOrganizationName", dto.getLeadOrganizationName());
            object.put("statusCode", dto.getStudyStatusCode().getCode());
            object.put("principalInvestigatorName", dto.getPiFullName());
            object.put("title", dto.getOfficialTitle());
            List<String> trialProgramCodes = new ArrayList<String>();
            for (ProgramCodeDTO programCodeDTo : dto.getProgramCodes()) {
                trialProgramCodes.add(programCodeDTo.getProgramCode());
            }
            object.put("trialProgramCodes", trialProgramCodes.toArray());
            array.put(object);
        }
        
    }
    
    /**
     * Does bean injections
     * @throws Exception on exception
     */
    @Override
    public void prepare() throws Exception {
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
        protocolQueryService = PaRegistry.getCachingProtocolQueryService();
        registryUserService = PaRegistry.getRegistryUserService();
        lookUpTableServiceRemote = PaRegistry.getLookUpTableService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
    }
    
    /**
     * get all family dtos
     * @return familyDTO list
     */
    public List<FamilyDTO> getFamilyDTOs() {
        return familyDTOs;
    }

    /**
     * sets list of family dto's
     * @param familyDTOs list
     */
    public void setFamilyDTOs(List<FamilyDTO> familyDTOs) {
        this.familyDTOs = familyDTOs;
    }
    
    /**
     * family dto
     * @return family DTO
     */
    public FamilyDTO getSelectedFamilyDTO() {
        return selectedFamilyDTO;
    }

    /**
     * sets the selected DTO
     * @param selectedFamilyDTO selected family
     */
    public void setSelectedFamilyDTO(FamilyDTO selectedFamilyDTO) {
        this.selectedFamilyDTO = selectedFamilyDTO;
        ServletActionContext.getRequest().getSession().setAttribute(FAMILY_DTO_KEY, selectedFamilyDTO);
    }
    
    /**
     * @param resp
     *            the servletResponse to set
     */
    @Override
    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;        
    }

    /**
     * @param req
     *            the servletRequest to set
     */
    @Override
    public void setServletRequest(HttpServletRequest req) {
        this.request = req;        
    }    
    
    /**
     * Gets PO ID
     * @return poId poId
     */
    public Long getPoId() {
        return poId;
    }

    /**
     * sets po id
     * @param poId poId
     */
    public void setPoId(Long poId) {
        this.poId = poId;
    }
    
    /**
     * family program code
     * @return familyProgramCodeService family program code
     */
    public FamilyProgramCodeService getFamilyProgramCodeService() {
        return familyProgramCodeService;
    }

    /**
     * sets family program code 
     * @param familyProgramCodeService family program code service
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }
    
    /**
     * selected dto id
     * @return selectedDTO id
     */
    public Long getSelectedDTOId() {
        return selectedDTOId;
    }

    /**
     * sets dto id for selected family
     * @param selectedDTOId selected dto id
     */
    public void setSelectedDTOId(Long selectedDTOId) {
        this.selectedDTOId = selectedDTOId;
    }

    /**
     * The program code being deleted/deactivated
     * @return - the program code Id
     */
    public Long getProgramCodeIdSelectedForDeletion() {
        return programCodeIdSelectedForDeletion;
    }

    /**
     * The program code that is being deleted
     * @param programCodeIdSelectedForDeletion - the program code id
     */
    public void setProgramCodeIdSelectedForDeletion(Long programCodeIdSelectedForDeletion) {
        this.programCodeIdSelectedForDeletion = programCodeIdSelectedForDeletion;
    }

    /**
     * returns registry user service 
     * @return registryUserService user service
     */
    public RegistryUserService getRegistryUserService() {
        return registryUserService;
    }

    /**
     * Sets registry user service
     * @param registryUserService registry user service
     */
    public void setRegistryUserService(RegistryUserService registryUserService) {
        this.registryUserService = registryUserService;
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

}
