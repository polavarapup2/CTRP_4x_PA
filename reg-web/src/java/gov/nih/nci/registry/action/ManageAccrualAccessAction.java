package gov.nih.nci.registry.action;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentByTrialDTO;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentHistoryDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyServiceLocal;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.action.ManageAccrualAccessAction.AccrualAccessModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

/**
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class ManageAccrualAccessAction extends ActionSupport implements
        ScopedModelDriven<AccrualAccessModel>, Preparable {

  

    private static final Logger LOG = Logger
            .getLogger(ManageAccrualAccessAction.class);
    
    private static final String SUCCESS_MSG = "successMessage";
    static final String FAILURE_MSG = "failureMessage";

    private AccrualAccessModel model;

    private String scopeKey;

    private String trialCategory;
    
    private String comments;
    
    private String trialsToAssign;
    
    private String trialsToUnassign;

    private RegistryUser currentUser;

    private StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService;

    private ProtocolQueryServiceLocal protocolQueryService;

    private RegistryUserServiceLocal registryUserService;

    private PAOrganizationServiceRemote paOrganizationService;

    private FamilyServiceLocal familyService;

    private Long userId;

    private Long ofUserId;

    private String families;

    private Organization organization;
    
    private String assignmentType; 

    private static final long serialVersionUID = 1L;

    private static final String BY_TRIAL = "byTrial";
    
    private static final String HISTORY = "history";

    @Override
    public String execute() throws PAException {
        Long orgId = currentUser.getAffiliatedOrganizationId();
        Organization criteria = new Organization();
        criteria.setIdentifier(orgId.toString());
        setOrganization(paOrganizationService.getOrganizationByIndetifers(criteria));
        List<OrgFamilyDTO> famList = FamilyHelper.getByOrgId(orgId);
        setFamilies("NONE");
        boolean bFirst = true;
        for (OrgFamilyDTO fam : famList) {
            if (bFirst) {
                setFamilies("");
                bFirst = false;
            } else {
                setFamilies(getFamilies() + " | "); 
            }
            setFamilies(getFamilies() + fam.getName()); 
        }
        model.setTrialCategory(TrialCategory.ALL);
        model.setUsers(sort(registryUserService.findByAffiliatedOrg(orgId)));
        model.setUser(null);
        populateFamilyDd();

        loadTrials();

        return SUCCESS;
    }

    private List<RegistryUser> sort(List<RegistryUser> users) {
        Collections.sort(users, new Comparator<RegistryUser>() {
            @Override
            public int compare(RegistryUser u1, RegistryUser u2) {
                return StringUtils.defaultString(u1.getLastName()).compareTo(
                        StringUtils.defaultString(u2.getLastName()));
            }
        });
        return users;
    }

    private void populateFamilyDd() throws PAException {
        List<RegistryUser> result = new ArrayList<RegistryUser>();
        List<Long> orgList = FamilyHelper.getAllRelatedOrgs(currentUser.getAffiliatedOrganizationId());
        for (Long org : orgList) {
            for (RegistryUser user : registryUserService.findByAffiliatedOrg(org)) {
                result.add(user);
            }
        }
        model.setOfUsers(sort(result));
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String change() throws PAException {
        model.setUser(registryUserService.getUserById(userId));
        model.setTrialCategory(TrialCategory.valueOf(trialCategory));
        model.setAssignmentType(assignmentType);
        loadTrials();
        return SUCCESS;
    }

    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignUnAssignSASubmitter() throws PAException {
        String msg = "";
        RegistryUser rUser = registryUserService.getUserById(userId);
        if (rUser == null) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG, 
                    getText("manage.accrual.access.user.not.found.error"));
            return SUCCESS;
        }
        if (!rUser.getSiteAccrualSubmitter()) {
            getAllTrialsForSiteAccrualSubmitter();
            assignAll(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE);
            rUser.setSiteAccrualSubmitter(true);
            msg = "As a Site Submitter, " + rUser.getFirstName() + " " + rUser.getLastName() 
                + ", who is affiliated with "
                + rUser.getAffiliateOrg() 
                + ", will be able to submit accrual for "
                + rUser.getAffiliateOrg() + " as it participates on any Institutional or Externally "
                + "Peer Reviewed trial lead by an organization that belongs to the same family of "
                + "organizations to which " + rUser.getAffiliateOrg() + " belongs to."
                + "\n" + rUser.getFirstName() + " " + rUser.getLastName() + " can also submit accrual for "
                + rUser.getAffiliateOrg() + " as it participates on any Industrial trial.";
        } else {
            if (!rUser.getFamilyAccrualSubmitter()) {
                getAllTrialsForSiteAccrualSubmitter(); 
                unassignAll(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE);
            }
            rUser.setSiteAccrualSubmitter(false);
            msg = rUser.getFirstName() + " " + rUser.getLastName() + " can not submit accrual for "
                    + rUser.getAffiliateOrg() 
                    + " Institutional, Externally Peer Reviewed, and Industrial trials where "
                    + rUser.getAffiliateOrg() + " is a lead organization or participating site";
        }
        registryUserService.updateUser(rUser);
        model.setUsers(sort(registryUserService.findByAffiliatedOrg(currentUser
                .getAffiliatedOrganizationId())));
        model.setUser(registryUserService.getUserById(userId));
        ServletActionContext.getRequest().setAttribute(SUCCESS_MSG, msg);
        return SUCCESS;
    }

    /**
     * Assign or UnAssign the registry user as Org Family Accrual Submitter.
     * @return action result
     * @throws PAException exception
     */
    public String assignUnAssignOFSubmitter() throws PAException {
        RegistryUser rUser = registryUserService.getUserById(getOfUserId());
        if (rUser == null) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG, 
                    getText("manage.accrual.access.user.not.found.error"));
            return SUCCESS;
        }
        String msg = "";
        if (rUser.getFamilyAccrualSubmitter()) {
           try {
               familyService.unassignAllAccrualAccess(registryUserService.getUserById(ofUserId), currentUser);
           } catch (PAException e) {
               ServletActionContext.getRequest().setAttribute(FAILURE_MSG, e.getMessage());
               return SUCCESS;
           }
           model.setUsers(sort(registryUserService.findByAffiliatedOrg(currentUser.getAffiliatedOrganizationId())));
           msg = rUser.getFirstName() + " " + rUser.getLastName() + " will no longer be able to submit accrual";
        } else {
            List<OrgFamilyDTO> famList = FamilyHelper.getByOrgId(ofUserId);
            StringBuffer famString = new StringBuffer();
            boolean bFirst = true;
            for (OrgFamilyDTO fam : famList) {
                if (bFirst) {
                    bFirst = false;
                } else {
                    famString.append(" | "); 
                }
                famString.append(fam.getName()); 
            }
            try {
                familyService.assignFamilyAccrualAccess(registryUserService.getUserById(ofUserId), currentUser, null);
            } catch (PAException e) {
                ServletActionContext.getRequest().setAttribute(FAILURE_MSG, e.getMessage());
                return SUCCESS;
            }
            msg = "As a family submitter, " + rUser.getFirstName() + " " + rUser.getLastName() 
                  + ", who is affiliated with "
                  + getOrganization().getName() + ", will be able to submit accruals for all organizations"
                  + " participating on any Institutional or Externally Peer Reviewed trial lead by an organization"
                  + " that belongs to the same family of  organizations to which "
                  + getOrganization().getName() + " belongs to." + "\n" 
                  + rUser.getFirstName() + " " + rUser.getLastName() + " can also submit accrual for any organization "
                  + "participating on any Industrial trial as long as that organization belongs to the same family "
                  + "of organizations to which " + getOrganization().getName() + " belongs to." + "\n" 
                  + "Please allow time for the assignments to be processed by the CTRP system. If a large number of "
                  + "trials are being assigned, this operation may take up to 30 minutes to complete. You may "
                  + "leave this page or exit the application; the operation will continue to run in the background "
                  + "until all assignments are made.";
        }
        populateFamilyDd();
        ServletActionContext.getRequest().setAttribute(SUCCESS_MSG, msg);
        return SUCCESS;
    }

    private void getAllTrialsForSiteAccrualSubmitter() throws PAException {
        model.setUser(registryUserService.getUserById(userId));
        model.setTrialCategory(TrialCategory.ALL);
        loadTrials();
    }

    /**
     * Assign all trials setting the source to "Admin Provided".
     * @return String
     * @throws PAException PAException
     */
    public String assignAll() throws PAException {
        return assignAll(AccrualAccessSourceCode.REG_ADMIN_PROVIDED);
    }

    private String assignAll(AccrualAccessSourceCode source) throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (Collection<StudyProtocolHolder> trialList : model
                .getUnassignedTrials().values()) {
            for (StudyProtocolHolder trialHolder : trialList) {
                if (trialHolder.isSelectable()) {
                    trialIDs.add(trialHolder.getProtocolDTO()
                            .getStudyProtocolId());
                }
            }
        }
        return assign(trialIDs, source);
    }

    /**
     * Remove assignment for all trials setting the source to "Admin Provided".
     * @return String
     * @throws PAException PAException
     */
    public String unassignAll() throws PAException {
        return unassignAll(AccrualAccessSourceCode.REG_ADMIN_PROVIDED);
    }

    private String unassignAll(AccrualAccessSourceCode source) throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (Collection<StudyProtocolQueryDTO> trialList : model
                .getAssignedTrials().values()) {
            for (StudyProtocolQueryDTO trial : trialList) {
               trialIDs.add(trial.getStudyProtocolId());
            }
        }
        return unassign(trialIDs, source);
    }

    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignSelected() throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (String trialIdStr : this.trialsToAssign.split(";")) {
            trialIDs.add(Long.parseLong(trialIdStr));
        }
        return assign(trialIDs, AccrualAccessSourceCode.REG_ADMIN_PROVIDED);
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String unassignSelected() throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (String trialIdStr : this.trialsToUnassign.split(";")) {
            trialIDs.add(Long.parseLong(trialIdStr));
        }
        return unassign(trialIDs, AccrualAccessSourceCode.REG_ADMIN_PROVIDED);
    }
    

    private String assign(Collection<Long> trialIDs, AccrualAccessSourceCode source) throws PAException {
        try {
            studySiteAccrualAccessService.assignTrialLevelAccrualAccessNoTransaction(
                    model.getUser(), source, trialIDs, comments, currentUser);
            ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                    getText("manage.accrual.access.trialsAssigned"));
        } catch (PAException e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    getText("manage.accrual.access.error"));
        }
        return change();
    }

    private String unassign(Collection<Long> trialIDs, AccrualAccessSourceCode source) throws PAException {
        try {
            studySiteAccrualAccessService.unassignTrialLevelAccrualAccessNoTransaction(
                    model.getUser(), source, trialIDs, comments, currentUser);
            ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                    getText("manage.accrual.access.trialsUnassigned"));
        } catch (PAException e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    getText("manage.accrual.access.error"));
        }
        return change();
    }

    private void loadTrials() throws PAException {
        model.getAssignedTrials().clear();
        model.getUnassignedTrials().clear();
        if (currentUser.getAffiliatedOrganizationId() != null
                && model.getUser() != null) {
            List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
            // determine lead org's internal ID
            Organization org = findCurrentUsersOrg();
            loadInstitutionalPeerReviewedTrials(list, org);
            loadIndustrialTrials(list, org);
            filterOutNationalAndRejectedTrials(list);
            filterOutCTEPAndDCPTrials(list);
            loadTrialsIntoModel(list);
        }
    }

    private void filterOutCTEPAndDCPTrials(List<StudyProtocolQueryDTO> list) {
        CollectionUtils.filter(list, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                StudyProtocolQueryDTO trial = (StudyProtocolQueryDTO) arg0;
                boolean isNotCtepDcpTrial = StringUtils.isBlank(trial.getCtepId()) 
                        && StringUtils.isBlank(trial.getDcpId());                
                return isNotCtepDcpTrial;
            }
        });
    }

    /**
     * @param list
     * @throws PAException
     */
    private void loadTrialsIntoModel(List<StudyProtocolQueryDTO> list)
            throws PAException {
        List<Long> trialIDs = studySiteAccrualAccessService
                .getActiveTrialLevelAccrualAccess(model.getUser());
        Collections.sort(list, new Comparator<StudyProtocolQueryDTO>() {
            public int compare(StudyProtocolQueryDTO o1, StudyProtocolQueryDTO o2) {
                return ObjectUtils.compare(o2.getNciIdentifier(), o1.getNciIdentifier());
            }
        });
        for (StudyProtocolQueryDTO trial : list) {
            if (trialIDs.contains(trial.getStudyProtocolId())) {
                addToAssigned(model.getAssignedTrials(), trial);
            } else {
                addToUnassigned(model.getUnassignedTrials(), trial);
            }
        }
    }

    void filterOutNationalAndRejectedTrials(List<StudyProtocolQueryDTO> list) {
        CollectionUtils.filter(list, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                StudyProtocolQueryDTO trial = (StudyProtocolQueryDTO) arg0;
                boolean isNotNational = StringUtils.isNotBlank(trial
                        .getSummary4FundingSponsorType())
                        && !SummaryFourFundingCategoryCode.NATIONAL.name()
                                .equals(trial
                                        .getSummary4FundingSponsorType());
                boolean isNotRejected = !DocumentWorkflowStatusCode.REJECTED.equals(
                        trial.getDocumentWorkflowStatusCode());
                return isNotNational && isNotRejected;
            }
        });
    }

    /**
     * @param list
     * @param org
     * @throws PAException
     */
    private void loadIndustrialTrials(List<StudyProtocolQueryDTO> list,
            Organization org) throws PAException {
        if (TrialCategory.ALL == model.trialCategory
                || TrialCategory.INDUSTRIAL == model.trialCategory) {
            StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
            queryCriteria.setOrganizationType("Participating Site");
            queryCriteria
                    .setParticipatingSiteIds(Arrays.asList(org.getId()));
            queryCriteria.setTrialCategory("p");
            list.addAll(protocolQueryService
                    .getStudyProtocolByCriteria(queryCriteria));
        }
    }

    /**
     * @param list
     * @param org
     * @throws PAException
     */
    private void loadInstitutionalPeerReviewedTrials(
            List<StudyProtocolQueryDTO> list, Organization org)
            throws PAException {
        if (TrialCategory.ALL == model.trialCategory
                || TrialCategory.INSTITUTIONAL_PEER_REVIEWED == model.trialCategory) {
            StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
            queryCriteria
                    .setLeadOrganizationIds(Arrays.asList(org.getId()));
            queryCriteria.setTrialCategory("n");
            list.addAll(protocolQueryService
                    .getStudyProtocolByCriteria(queryCriteria));
        }
    }

    /**
     * @return
     * @throws PAException
     */
    private Organization findCurrentUsersOrg() throws PAException {
        Organization orgCriteria = new Organization();
        orgCriteria.setIdentifier(currentUser.getAffiliatedOrganizationId()
                .toString());
        Organization org = paOrganizationService
                .getOrganizationByIndetifers(orgCriteria);
        return org;
    }

    private void addToAssigned(Map<String, List<StudyProtocolQueryDTO>> map,
            StudyProtocolQueryDTO trial) {
        final String summary4FundingSponsorType = trial.isProprietaryTrial() ? SummaryFourFundingCategoryCode.INDUSTRIAL
                .name() : trial.getSummary4FundingSponsorType();
        if (StringUtils.isNotBlank(summary4FundingSponsorType)) {
            String code = SummaryFourFundingCategoryCode.valueOf(
                    summary4FundingSponsorType).getCode();
            List<StudyProtocolQueryDTO> list = map.get(code);
            if (list == null) {
                list = new ArrayList<StudyProtocolQueryDTO>();
                map.put(code, list);
            }
            list.add(trial);
        }
    }
    
    private void addToUnassigned(Map<String, List<StudyProtocolHolder>> map,
            StudyProtocolQueryDTO trial) {
        boolean selectable = trial.getDocumentWorkflowStatusCode()
                .isEligibleForAccrual();
        final String summary4FundingSponsorType = trial.isProprietaryTrial() ? SummaryFourFundingCategoryCode.INDUSTRIAL
                .name() : trial.getSummary4FundingSponsorType();
        if (StringUtils.isNotBlank(summary4FundingSponsorType)) {
            String code = SummaryFourFundingCategoryCode.valueOf(
                    summary4FundingSponsorType).getCode();
            List<StudyProtocolHolder> list = map.get(code);
            if (list == null) {
                list = new ArrayList<StudyProtocolHolder>();
                map.put(code, list);
            }
            list.add(new StudyProtocolHolder(trial, selectable));
        }
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignmentHistory() throws PAException {
        Collection<Long> trialIds = familyService.getSiteAccrualTrials(currentUser.getAffiliatedOrganizationId());
        model.setHistory(studySiteAccrualAccessService.getAccrualAccessAssignmentHistory(trialIds));
        return HISTORY;
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignmentByTrial() throws PAException {
        model.getByTrial().clear();
        Collection<Long> trialIds = familyService.getSiteAccrualTrials(currentUser.getAffiliatedOrganizationId());
        List<AccrualAccessAssignmentByTrialDTO> list = studySiteAccrualAccessService
                .getAccrualAccessAssignmentByTrial(trialIds);
        for (AccrualAccessAssignmentByTrialDTO dto : list) {
            SummaryFourFundingCategoryCode code = dto.getCategoryCode();
            List<AccrualAccessAssignmentByTrialDTO> trials = model.getByTrial()
                    .get(code);
            if (trials == null) {
                trials = new ArrayList<AccrualAccessAssignmentByTrialDTO>();
                model.getByTrial().put(code, trials);
            }
            trials.add(dto);
        }
        return BY_TRIAL;
    }
    

    
    /**
     * @return String
     *  
     */
    public String historyPaging() {        
        return HISTORY;
    }
    
    /**
     * @return String
     *  
     */
    public String byTrialPaging() {        
        return BY_TRIAL;
    }
    


    /**
     * @return Collection
     */
    public Collection<TrialCategory> getTrialCategoryList() {
        return Arrays.asList(TrialCategory.values());
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    public static final class AccrualAccessModel implements Serializable {

        private TrialCategory trialCategory;

        private List<RegistryUser> users = new ArrayList<RegistryUser>();

        private List<RegistryUser> ofUsers = new ArrayList<RegistryUser>();

        private RegistryUser user = new RegistryUser();

        private Map<String, List<StudyProtocolQueryDTO>> assignedTrials = 
                new TreeMap<String, List<StudyProtocolQueryDTO>>();

        private Map<String, List<StudyProtocolHolder>> unassignedTrials = 
                new TreeMap<String, List<StudyProtocolHolder>>();
        
        private List<AccrualAccessAssignmentHistoryDTO> history = new ArrayList<AccrualAccessAssignmentHistoryDTO>();
        
        private Map<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>> byTrial = 
                new TreeMap<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>>();
        
        private String assignmentType;

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * @return the trialCategory
         */
        public TrialCategory getTrialCategory() {
            return trialCategory;
        }

        /**
         * @param trialCategory
         *            the trialCategory to set
         */
        public void setTrialCategory(TrialCategory trialCategory) {
            this.trialCategory = trialCategory;
        }

        /**
         * @return the users
         */
        public List<RegistryUser> getUsers() {
            return users;
        }

        /**
         * @param users
         *            the users to set
         */
        public void setUsers(List<RegistryUser> users) {
            this.users = users;
        }

        /**
         * @return the user
         */
        public RegistryUser getUser() {
            return user;
        }

        /**
         * @param user
         *            the user to set
         */
        public void setUser(RegistryUser user) {
            this.user = user;
        }

        /**
         * @return the assignedTrials
         */
        public Map<String, List<StudyProtocolQueryDTO>> getAssignedTrials() {
            return assignedTrials;
        }

        /**
         * @param assignedTrials
         *            the assignedTrials to set
         */
        public void setAssignedTrials(
                Map<String, List<StudyProtocolQueryDTO>> assignedTrials) {
            this.assignedTrials = assignedTrials;
        }

        /**
         * @return the unassignedTrials
         */
        public Map<String, List<StudyProtocolHolder>> getUnassignedTrials() {
            return unassignedTrials;
        }

        /**
         * @param unassignedTrials
         *            the unassignedTrials to set
         */
        public void setUnassignedTrials(
                Map<String, List<StudyProtocolHolder>> unassignedTrials) {
            this.unassignedTrials = unassignedTrials;
        }
        
        /**
         * @return boolean
         */
        public boolean isHasAssignableTrials() {
            for (Collection<StudyProtocolHolder> trialList : getUnassignedTrials()
                    .values()) {
                for (StudyProtocolHolder trialHolder : trialList) {
                    if (trialHolder.isSelectable()) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * @return the history
         */
        public List<AccrualAccessAssignmentHistoryDTO> getHistory() {
            return history;
        }

        /**
         * @param history the history to set
         */
        public void setHistory(List<AccrualAccessAssignmentHistoryDTO> history) {
            this.history = history;
        }

        /**
         * @return the byTrial
         */
        public Map<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>> getByTrial() {
            return byTrial;
        }

        /**
         * @param byTrial the byTrial to set
         */
        public void setByTrial(
                Map<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>> byTrial) {
            this.byTrial = byTrial;
        }

      /**
         * @return the ofUsers
         */
        public List<RegistryUser> getOfUsers() {
            return ofUsers;
        }

        /**
         * @param ofUsers the ofUsers to set
         */
        public void setOfUsers(List<RegistryUser> ofUsers) {
            this.ofUsers = ofUsers;
        }
        
        /**
         * @return assignmentType
         */
        public String getAssignmentType() {
            return assignmentType;
        }

        /**
         * @param assignmentType the assignmentType to set
         */
        public void setAssignmentType(String assignmentType) {
            this.assignmentType = assignmentType;
        }
    }
    
    /**
     * @author Denis G. Krylov
     *
     */
    public static final class StudyProtocolHolder implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private StudyProtocolQueryDTO protocolDTO;
        private boolean selectable = true;
        private AccrualAccessSourceCode accrualAccessSource;
        
        
        /**
         * @param protocolDTO protocolDTO
         * @param selectable selectable
         */
        public StudyProtocolHolder(StudyProtocolQueryDTO protocolDTO,
                boolean selectable) {
            this.protocolDTO = protocolDTO;
            this.selectable = selectable;
        }
        /**
         * @return the protocolDTO
         */
        public StudyProtocolQueryDTO getProtocolDTO() {
            return protocolDTO;
        }
        /**
         * @param protocolDTO the protocolDTO to set
         */
        public void setProtocolDTO(StudyProtocolQueryDTO protocolDTO) {
            this.protocolDTO = protocolDTO;
        }
        /**
         * @return the selectable
         */
        public boolean isSelectable() {
            return selectable;
        }
        /**
         * @param selectable the selectable to set
         */
        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }
        /**
         * @return the accrualAccessSource
         */
        public AccrualAccessSourceCode getAccrualAccessSource() {
            return accrualAccessSource;
        }
        /**
         * @param accrualAccessSource the accrualAccessSource to set
         */
        public void setAccrualAccessSource(AccrualAccessSourceCode accrualAccessSource) {
            this.accrualAccessSource = accrualAccessSource;
        }
        
        
        
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    public static enum TrialCategory implements CodedEnum<String> {
        /**
         * 
         */
        ALL("All"),
        /**
         * 
         */
        INSTITUTIONAL_PEER_REVIEWED("Institutional/Externally Peer Reviewed"),
        /**
         * 
         */
        INDUSTRIAL("Industrial");

        private String code;

        /**
         * Constructor for StatusCode.
         * 
         * @param code
         */
        private TrialCategory(String code) {
            this.code = code;
            register(this);
        }

        /**
         * @return code coded value of enum
         */
        @Override
        public String getCode() {
            return code;
        }

        /**
         * @return String DisplayName
         */
        @Override
        public String getDisplayName() {
            return sentenceCasedName(this);
        }

        /**
         * @return String display name
         */
        public String getName() {
            return name();
        }

        /**
         * @param code
         *            code
         * @return StatusCode
         */
        public static ActiveInactiveCode getByCode(String code) {
            return getByClassAndCode(ActiveInactiveCode.class, code);
        }

        /**
         * construct a array of display names for Abstracted Status coded Enum.
         * 
         * @return String[] display names for Abstracted Status Code
         */
        public static String[] getDisplayNames() {
            ActiveInactiveCode[] absStatusCodes = ActiveInactiveCode.values();
            String[] codedNames = new String[absStatusCodes.length];
            for (int i = 0; i < absStatusCodes.length; i++) {
                codedNames[i] = absStatusCodes[i].getCode();
            }
            return codedNames;
        }

    }

    @Override
    public AccrualAccessModel getModel() {
        return model;
    }

    @Override
    public void prepare() throws PAException {
        setRegistryUserService(PaRegistry.getRegistryUserService());
        setStudySiteAccrualAccessService(PaRegistry
                .getStudySiteAccrualAccessService());
        setProtocolQueryService(PaRegistry.getProtocolQueryService());
        setPaOrganizationService(PaRegistry.getPAOrganizationService());
        setFamilyService(PaRegistry.getFamilyService());
        currentUser = registryUserService.getUser(UsernameHolder.getUser());
    }

    @Override
    public String getScopeKey() {
        return scopeKey;
    }

    @Override
    public void setModel(AccrualAccessModel arg0) {
        this.model = arg0;
    }

    @Override
    public void setScopeKey(String arg0) {
        this.scopeKey = arg0;
    }

    /**
     * @param studySiteAccrualAccessService
     *            the studySiteAccrualAccessService to set
     */
    public void setStudySiteAccrualAccessService(
            StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService) {
        this.studySiteAccrualAccessService = studySiteAccrualAccessService;
    }

    /**
     * @return the trialCategory
     */
    public String getTrialCategory() {
        return trialCategory;
    }

    /**
     * @param trialCategory
     *            the trialCategory to set
     */
    public void setTrialCategory(String trialCategory) {
        this.trialCategory = trialCategory;
    }

    /**
     * @param registryUserService
     *            the registryUserService to set
     */
    public void setRegistryUserService(
            RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param familyService the familyService to set
     */
    public void setFamilyService(FamilyServiceLocal familyService) {
        this.familyService = familyService;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @param protocolQueryService
     *            the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param paOrganizationService
     *            the paOrganizationService to set
     */
    public void setPaOrganizationService(
            PAOrganizationServiceRemote paOrganizationService) {
        this.paOrganizationService = paOrganizationService;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the studiesToAssign
     */
    public String getTrialsToAssign() {
        return trialsToAssign;
    }

    /**
     * @param studiesToAssign the studiesToAssign to set
     */
    public void setTrialsToAssign(String studiesToAssign) {
        this.trialsToAssign = studiesToAssign;
    }

    /**
     * @return the trialsToUnassign
     */
    public String getTrialsToUnassign() {
        return trialsToUnassign;
    }

    /**
     * @param trialsToUnassign the trialsToUnassign to set
     */
    public void setTrialsToUnassign(String trialsToUnassign) {
        this.trialsToUnassign = trialsToUnassign;
    }

    /**
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * @return the families
     */
    public String getFamilies() {
        return families;
    }

    /**
     * @param families the families to set
     */
    public void setFamilies(String families) {
        this.families = families;
    }

    /**
     * @return the ofUserId
     */
    public Long getOfUserId() {
        return ofUserId;
    }

    /**
     * @param ofUserId the ofUserId to set
     */
    public void setOfUserId(Long ofUserId) {
        this.ofUserId = ofUserId;
    }
    
    /**
     * @return the assignmentType
     */
    public String getAssignmentType() {
        return assignmentType;
    }

    /**
     * @param assignmentType the assignmentType to set
     */
    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }
}
