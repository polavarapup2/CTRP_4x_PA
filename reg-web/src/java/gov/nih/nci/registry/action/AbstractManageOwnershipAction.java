package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.CacheUtils.Closure;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.util.SelectedRegistryUser;
import gov.nih.nci.registry.util.SelectedStudyProtocol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.sf.ehcache.Cache;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Base {@link com.opensymphony.xwork2.Action} class for ownership management
 * sub-classes.
 * 
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings({  "PMD.TooManyMethods" })
public abstract class AbstractManageOwnershipAction extends ActionSupport {

    private static final String CHECKED_VALUE = "checked";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger
            .getLogger(AbstractManageOwnershipAction.class);
    private static final String REG_USERS_LIST = "regUsersList";
    private static final String STUDY_PROTOCOLS_LIST = "studyProtocolsList";
    /**
     * Trial ownership records session param name
     */
    protected static final String TRIAL_OWNERSHIP_LIST = "trialOwnershipInfo";
    private static final String VIEW_RESULTS = "viewResults";
    private static final String SITE_NAME = "siteName";
    
    /**
     * Cache to keep the family collection in while people are working with it.
     */
    protected static final Cache FAMILY_CACHE = CacheUtils.getOrganizationFamilyCache();
    
    private List<SelectedStudyProtocol> studyProtocols = new ArrayList<SelectedStudyProtocol>();
    private List<SelectedRegistryUser> registryUsers = new ArrayList<SelectedRegistryUser>();
    private List<DisplayTrialOwnershipInformation> trialOwnershipInfo = 
            new ArrayList<DisplayTrialOwnershipInformation>();
    private static final String SUCCESS_MSG = "successMessage";
    private static final String FAILURE_MSG = "failureMessage";
    private String[] regUserIds = null;
    private String[] trialIds =  null;
    private String[] trialOwners =  null;
    private boolean owner;
    private boolean selected;
    private boolean checked = false;
    private String siteName;
    private Long trialId;
    private Long regUserId;
    

    /**
     * @return the trialIds
     */
    public String [] getTrialIds() {
        return trialIds;
    }

    /**
     * @param trialIds
     *            the trialIds to set
     */
    public void setTrialIds(String[] trialIds) {
        this.trialIds = trialIds;
    }

    /**
     * @return the isChecked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param isChecked
     *            the isChecked to set
     */
    public void setChecked(boolean isChecked) {
        this.checked = isChecked;
    }

    /**
     * @return the trialId
     */
    public Long getTrialId() {
        return trialId;
    }

    /**
     * @param trialId
     *            the trialId to set
     */
    public void setTrialId(Long trialId) {
        this.trialId = trialId;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param isSelected
     *            the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }

    /**
     * @return the regUserId
     */
    public Long getRegUserId() {
        return regUserId;
    }

    /**
     * @param regUserId
     *            the regUserId to set
     */
    public void setRegUserId(Long regUserId) {
        this.regUserId = regUserId;
    }

    /**
     * @return the isOwner
     */
    public boolean isOwner() {
        return owner;
    }

    /**
     * @param isOwner
     *            the isOwner to set
     */
    public void setOwner(boolean isOwner) {
        this.owner = isOwner;
    }

    /**
     * load initial view.
     * 
     * @return String the action result.
     * @throws PAException
     *             the pa exception.
     */
    public String search() throws PAException {
        performSearch();
        return VIEW_RESULTS;
    }
    
    /**
     * A function to wrap around FamilyHelper static function getAllRelatedOrgs so
     * it can use the cache.
     * @param siteId the id of the site
     * @return the list of organizations in the family,
     * @throws PAException When something goes wrong.
     */
    @SuppressWarnings("unchecked")
    protected List<Long> getAllRelatedOrgs(final Long siteId) throws PAException {
        List<Long> siblings = (List<Long>) CacheUtils.getFromCacheOrBackend(FAMILY_CACHE, siteId.toString(),
                new Closure() {
                    @Override
                    public Object execute() throws PAException {
                        List<Long> siblings = FamilyHelper.getAllRelatedOrgs(siteId);
                        if (siblings == null || siblings.size() == 0) {
                            //Can't happen, but just in case that changes later.
                            siblings = new ArrayList<Long>();
                            siblings.add(siteId);
                        }
                        return siblings;
                    }
                });
        return siblings;
    }

    private void performSearch() throws PAException {        
        final HttpSession session = ServletActionContext.getRequest()
                .getSession();
        session.removeAttribute(AbstractManageOwnershipAction.REG_USERS_LIST);
        session.removeAttribute(AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST);
        session.removeAttribute(CHECKED_VALUE);
        checked = false;

        try {            
            RegistryUser loggedInUser = getRegistryUser();
            Long affiliatedOrgId = loggedInUser.getAffiliatedOrganizationId();
            ServletActionContext.getRequest().getSession()
                                       .setAttribute(SITE_NAME, loggedInUser.getAffiliateOrg());
            
            getOrgMembers(affiliatedOrgId);
            getOrgTrials(affiliatedOrgId);
            getAssignedTrials(affiliatedOrgId);
        } catch (PAException e) {
            LOG.error(e.getMessage());
            throw new PAException(e);
        }
    }
    
    /**
     * @return RegistryUser
     * @throws PAException PAException
     */
    protected RegistryUser getRegistryUser() throws PAException {
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        return PaRegistry.getRegistryUserService().getUser(loginName);
    }    

    private void getOrgMembers(Long affiliatedOrgId) throws PAException {
        RegistryUser criteria = new RegistryUser();
        List<Long> siblings = getAllRelatedOrgs(affiliatedOrgId);
        Set<RegistryUser> regUsers = new HashSet<RegistryUser>();
        for (Long orgId : siblings) {
            criteria.setAffiliatedOrganizationId(orgId);
            regUsers.addAll(PaRegistry.getRegistryUserService().search(criteria));
        }
        registryUsers.clear();
        for (RegistryUser user : regUsers) {
            if (user.getCsmUser() != null) {
                SelectedRegistryUser selectedRegUser = new SelectedRegistryUser();
                selectedRegUser.setRegistryUser(user);
                registryUsers.add(selectedRegUser);
            }
        }
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(AbstractManageOwnershipAction.REG_USERS_LIST,
                        registryUsers);
    }

    private void getOrgTrials(Long affiliatedOrgId) throws PAException {
        List<StudyProtocol> trials = getStudyProtocols(affiliatedOrgId);
        studyProtocols.clear();
        for (StudyProtocol sp : trials) {
            SelectedStudyProtocol selectedStudyProtocol = new SelectedStudyProtocol();
            selectedStudyProtocol.setStudyProtocol(sp);
            selectedStudyProtocol.setNciIdentifier(PADomainUtils
                    .getAssignedIdentifierExtension(sp));
            selectedStudyProtocol
                    .setLeadOrgId(PADomainUtils.getLeadOrgSpId(sp));
            studyProtocols.add(selectedStudyProtocol);
        }
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(
                        AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST,
                        studyProtocols);
    }
    
    

    /**
     * @param affiliatedOrgId
     *            affiliatedOrgId
     * @return List<StudyProtocol>
     * @throws PAException
     *             PAException
     */
    public abstract List<StudyProtocol> getStudyProtocols(Long affiliatedOrgId)
            throws PAException;
    /**
     * Get the current trail assignments for the persons of the org
     * @param affiliatedOrgId affiliateOrgId
     * @throws PAException PAException
     */
    public abstract void getAssignedTrials(Long affiliatedOrgId) throws PAException;

    /**
     * set if user is owner or not.
     * 
     * @throws PAException
     *             the pa exception
     */
    public void setRegUser() throws PAException {
        if (regUserId != null) {
            SelectedRegistryUser regUser = getRegUser(regUserId);
            if (regUser != null) {
                regUser.setSelected(owner);
                ServletActionContext
                        .getRequest()
                        .getSession()
                        .setAttribute(
                                AbstractManageOwnershipAction.REG_USERS_LIST,
                                registryUsers);
            }
        }
    }

    @SuppressWarnings("unchecked")    // NOPMD
    private SelectedRegistryUser getRegUser(Long rUserId) {
        SelectedRegistryUser regUser = null;
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext
                .getRequest().getSession()
                .getAttribute(AbstractManageOwnershipAction.REG_USERS_LIST);
        for (SelectedRegistryUser srUser : registryUsers) {
            if (rUserId.equals(srUser.getRegistryUser().getId())) {
                regUser = srUser;
            }
        }
        return regUser;
    }

    /**
     * Sets email preference.
     * 
     * @throws PAException
     *             the pa exception
     */
    public void updateEmailPref() throws PAException {
        if (trialId != null && regUserId != null) {
            updateEmailPreference(trialId, regUserId);
        } else if (siteName != null) {
            updateEmailPreferenceForOrg();
        }
    }    

    @SuppressWarnings("unchecked")
    private void updateEmailPreferenceForOrg() throws PAException {
        trialOwnershipInfo = (List<DisplayTrialOwnershipInformation>) ServletActionContext.getRequest().getSession()
                .getAttribute(TRIAL_OWNERSHIP_LIST);
        for (Iterator iterator = trialOwnershipInfo.iterator(); iterator.hasNext();) {
            DisplayTrialOwnershipInformation ownership = (DisplayTrialOwnershipInformation) iterator.next();
            PaRegistry.getRegistryUserService().setEmailNotificationsPreference(Long.parseLong(ownership.getUserId()),
                    Long.parseLong(ownership.getTrialId()), selected);
        }
    }

    private void updateEmailPreference(Long tId, Long userId) throws PAException {
        PaRegistry.getRegistryUserService().setEmailNotificationsPreference(userId, tId, selected);
    }
    
     /**
     * view results from session..
     * 
     * @return String
     */
    @SuppressWarnings("unchecked")
    public String view() {
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext
                .getRequest().getSession()
                .getAttribute(AbstractManageOwnershipAction.REG_USERS_LIST);
        studyProtocols = (List<SelectedStudyProtocol>) ServletActionContext
                .getRequest()
                .getSession()
                .getAttribute(
                        AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST);
        
        siteName = (String) ServletActionContext.getRequest().getSession()
                    .getAttribute(SITE_NAME);
        trialOwnershipInfo = (List<DisplayTrialOwnershipInformation>) ServletActionContext.getRequest().getSession()
                                .getAttribute(TRIAL_OWNERSHIP_LIST);
        if (ServletActionContext.getRequest().getSession().getAttribute(CHECKED_VALUE) != null) {
            checked = (Boolean) ServletActionContext.getRequest().getSession().getAttribute(CHECKED_VALUE); 
        }
        return VIEW_RESULTS;
    }

    /**
     * assign ownership.
     * 
     * @return String
     * @throws PAException
     * @throws PAException
     *             the pa exception
     */
    public String assignOwnership() throws PAException {
        try {
            if (regUserIds != null && trialIds != null) {
                List<Long> selectedUserIds = new ArrayList<Long>();
                Set<Long> selectedTrialIds = new HashSet<Long>();
                
                for (int i = 0; i < trialIds.length; i++) {
                    selectedTrialIds.add(Long.parseLong(trialIds[i]));
                }
                for (int i = 0; i < regUserIds.length; i++) {
                    selectedUserIds.add(Long.parseLong(regUserIds[i]));
                }
                List<Long> toBeRemoved = new ArrayList<Long>();
                toBeRemoved.add(Long.valueOf(0));
                selectedTrialIds.removeAll(toBeRemoved);
                selectedUserIds.removeAll(toBeRemoved);
                
                updateOwnership(selectedUserIds, selectedTrialIds, true, true);

                ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                        assignSuccessMsg());
            } else {
                ServletActionContext
                        .getRequest()
                        .setAttribute(
                                FAILURE_MSG,
                                "Please select at least one organizational member and one trial to assign ownership");
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    e.getMessage());
            throw new PAException(e);
        }
        return search();
    }

    /**
     * @return String
     */
    public abstract String assignSuccessMsg();

    /**
     * @return String
     */
    public abstract String unassignSuccessMsg();

    /**
     * Unassign ownership.
     * 
     * @return String
     * @throws PAException
     * @throws PAException
     *             the pa exception
     */
    public String unassignOwnership() throws PAException {
        try {
            if (trialOwners != null) {
                Long tId;
                Long userId;
                String [] ids;
                for (int i = 0; i < trialOwners.length; i++) {
                    ids = trialOwners[i].split("_");
                    tId = Long.parseLong(ids[0]);
                    userId = Long.parseLong(ids[1]);
                    if (tId != 0 && userId != 0) {
                        List<Long> user = new ArrayList<Long>();
                        user.add(userId);
                        Set<Long> trial = new HashSet<Long>();
                        trial.add(tId);
                        updateOwnership(user, trial, false, false);
                    }
                }
                ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                        unassignSuccessMsg());
            } else {
                ServletActionContext
                        .getRequest()
                        .setAttribute(
                                FAILURE_MSG,
                                "Please select at least one trial-owner assignment to unassign ownership");
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    e.getMessage());
            throw new PAException(e);
        }

        return search();
    }

    /**
     * Updates ownership.
     * 
     * @param registryUserID
     *            userId
     * @param trialID
     *            tId
     * @param assign
     *            assign
     * @param enableEmailNotifications enableEmailNotifications
     * @throws PAException
     *             PAException
     */
    public abstract void updateOwnership(List<Long> registryUserID, Set<Long> trialID,
            boolean assign, boolean enableEmailNotifications)
            throws PAException;

    /**
     * @return the registryUsers.
     */
    public List<SelectedRegistryUser> getRegistryUsers() {
        return registryUsers;
    }

    /**
     * @param registryUsers
     *            the registryUsers to set
     */
    public void setRegistryUsers(List<SelectedRegistryUser> registryUsers) {
        this.registryUsers = registryUsers;
    }

    /**
     * @return the studyProtocols
     */
    public List<SelectedStudyProtocol> getStudyProtocols() {
        return studyProtocols;
    }

    /**
     * @param studyProtocols
     *            the studyProtocols to set
     */
    public void setStudyProtocols(List<SelectedStudyProtocol> studyProtocols) {
        this.studyProtocols = studyProtocols;
    }
    
    /**
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }


    /**
     * @return the trialOwnershipInfo
     */
    public List<DisplayTrialOwnershipInformation> getTrialOwnershipInfo() {
        return trialOwnershipInfo;
    }

    /**
     * @param trialOwnershipInfo the trialOwnershipInfo to set
     */
    public void setTrialOwnershipInfo(
            List<DisplayTrialOwnershipInformation> trialOwnershipInfo) {
        this.trialOwnershipInfo = trialOwnershipInfo;
    }

    /**
     * @return the regUserIds
     */
    public String[] getRegUserIds() {
        return regUserIds;
    }

    /**
     * @param regUserIds the regUserIds to set
     */
    public void setRegUserIds(String[] regUserIds) {
        this.regUserIds = regUserIds;
    }

    /**
     * @return the trialOwnsers 
     */
    public String[] getTrialOwners() {
        return trialOwners;
    }

    /**
     * @param trialOwners the trialOwener to set
     */
    public void setTrialOwners(String[] trialOwners) {
        this.trialOwners = trialOwners;
    }

    
    
}