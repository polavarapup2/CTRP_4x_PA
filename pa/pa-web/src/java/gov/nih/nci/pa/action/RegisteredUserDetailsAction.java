/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.action.RegisteredUserDetailsAction.RegisteredUserDetail;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.AccrualSubmissionAccessDTO;
import gov.nih.nci.pa.dto.AccrualSubmissionAccessWebDTO;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

/**
 * @author Denis G. Krylov
 * 
 */
public class RegisteredUserDetailsAction extends ActionSupport implements
        ScopedModelDriven<RegisteredUserDetail>, Preparable {

    /**
     * 
     */
    private static final long serialVersionUID = -455612586883087108L;

    private static final Logger LOG = Logger
            .getLogger(RegisteredUserDetailsAction.class);

    private RegisteredUserDetail model;

    private String scopeKey;

    private Long selectedUserId;

    private RegistryUserServiceLocal registryUserService;

    private PAServiceUtils paServiceUtils;

    private StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService;
    
    private ProtocolQueryServiceLocal protocolQueryService;

    @Override
    public void prepare() {
        this.registryUserService = PaRegistry.getRegistryUserService();
        this.protocolQueryService = PaRegistry.getProtocolQueryService();
        this.paServiceUtils = new PAServiceUtils();
        this.studySiteAccrualAccessService = PaRegistry
                .getStudySiteAccrualAccessService();
    }

    /**
     * @return res
     * @throws PAException
     *             exception
     */
    @Override
    public String execute() throws PAException {
        model.setUserList(sort(registryUserService.search(null)));
        model.setUser(null);
        return SUCCESS;
    }

    /**
     * Loads selected user's details.
     * 
     * @return String
     * @throws PAException
     *             PAException
     */
    public String query() throws PAException {
        if (selectedUserId != null) {
            model.setUser(registryUserService.getUserById(selectedUserId));
            populateModel();
        } else {
            model.setUser(null);
        }
        return SUCCESS;
    }
    
    /**
     * Data table paging.
     * 
     * @return String
     * @throws PAException
     *             PAException
     */
    public String paging() throws PAException {   
        return SUCCESS;
    }    

    private void populateModel() throws PAException {
        loadSiteAdminOrgs();
        loadAccrualAccessDTOs();
        loadTrialsOwned();
        loadTrialsSubmitted();
    }

    /**
     * 
     */
    private void loadSiteAdminOrgs() {
        try {
            OrganizationDTO orgDto = paServiceUtils
                    .getPOOrganizationEntity(IiConverter
                            .convertToPoOrganizationIi(String.valueOf(model
                                    .getUser().getAffiliatedOrganizationId())));
            if (orgDto != null) {
                model.setSiteAdminOrgs(Arrays.asList(orgDto));
            }
        } catch (Exception e) {
           LOG.error(e, e);
        }
    }

    private void loadTrialsSubmitted() throws PAException {        
        if (model.getUser().getCsmUser() != null) {
            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
            criteria.setSubmitter(model.getUser().getCsmUser().getLoginName());
            model.setTrialsSubmitted(ActionUtils
                    .sortTrialsByNciId(protocolQueryService
                            .getStudyProtocolByCriteria(criteria)));
        } else {
            model.setTrialsSubmitted(new ArrayList<StudyProtocolQueryDTO>());
        }
    }

    private void loadTrialsOwned() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setMyTrialsOnly(true);
        criteria.setUserId(model.getUser().getId());
        model.setTrialsOwned(ActionUtils.sortTrialsByNciId(protocolQueryService
                .getStudyProtocolByCriteria(criteria)));
    }

    /**
     * @throws PAException
     */
    private void loadAccrualAccessDTOs() throws PAException {
        model.getAccrualSubmissionAccess().clear();
        for (AccrualSubmissionAccessDTO dto : studySiteAccrualAccessService
                .getAccrualSubmissionAccess(model.getUser())) {
            AccrualSubmissionAccessWebDTO webDTO = new AccrualSubmissionAccessWebDTO();
            webDTO.setTrialId(dto.getTrialId());
            webDTO.setTrialNciId(dto.getTrialNciId());
            webDTO.setTrialTitle(dto.getTrialTitle());
            if (model.getAccrualSubmissionAccess().contains(webDTO)) {
                webDTO = model.getAccrualSubmissionAccess().get(
                        model.getAccrualSubmissionAccess().indexOf(webDTO));
            } else {
                model.getAccrualSubmissionAccess().add(webDTO);
            }
            OrganizationWebDTO orgWebDTO = new OrganizationWebDTO();
            orgWebDTO.setName(dto.getParticipatingSiteOrgName());
            orgWebDTO.setPoId(dto.getParticipatingSitePoOrgId());
            webDTO.getParticipatingSites().add(orgWebDTO);
        }
    }

    private List<RegistryUser> sort(List<RegistryUser> users) {
        Collections.sort(users, new Comparator<RegistryUser>() {
            @Override
            public int compare(RegistryUser u1, RegistryUser u2) {
                return StringUtils
                        .defaultString(u1.getLastName())
                        .toUpperCase()
                        .compareTo(
                                StringUtils.defaultString(u2.getLastName())
                                        .toUpperCase());
            }
        });
        return users;
    }

    /**
     * Registered user's data holder, session-scoped.
     * 
     * @author Denis G. Krylov
     * 
     */
    public static final class RegisteredUserDetail {

        private List<RegistryUser> userList = new ArrayList<RegistryUser>();
        private RegistryUser user;
        private List<OrganizationDTO> siteAdminOrgs = new ArrayList<OrganizationDTO>();
        private final List<AccrualSubmissionAccessWebDTO> accrualSubmissionAccess = 
                new ArrayList<AccrualSubmissionAccessWebDTO>();
        private List<StudyProtocolQueryDTO> trialsOwned = new ArrayList<StudyProtocolQueryDTO>();
        private List<StudyProtocolQueryDTO> trialsSubmitted = new ArrayList<StudyProtocolQueryDTO>();

        /**
         * @return the userList
         */
        public List<RegistryUser> getUserList() {
            return userList;
        }

        /**
         * @param userList
         *            the userList to set
         */
        public void setUserList(List<RegistryUser> userList) {
            this.userList = userList;
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
         * @return the siteAdminOrgs
         */
        public List<OrganizationDTO> getSiteAdminOrgs() {
            return siteAdminOrgs;
        }

        /**
         * @param siteAdminOrgs
         *            the siteAdminOrgs to set
         */
        public void setSiteAdminOrgs(List<OrganizationDTO> siteAdminOrgs) {
            this.siteAdminOrgs = siteAdminOrgs;
        }

        /**
         * @return the accrualSubmissionAccess
         */
        public List<AccrualSubmissionAccessWebDTO> getAccrualSubmissionAccess() {
            return accrualSubmissionAccess;
        }

        /**
         * @return the trialsOwned
         */
        public List<StudyProtocolQueryDTO> getTrialsOwned() {
            return trialsOwned;
        }

        /**
         * @param trialsOwned the trialsOwned to set
         */
        public void setTrialsOwned(List<StudyProtocolQueryDTO> trialsOwned) {
            this.trialsOwned = trialsOwned;
        }

        /**
         * @return the trialsSubmitted
         */
        public List<StudyProtocolQueryDTO> getTrialsSubmitted() {
            return trialsSubmitted;
        }

        /**
         * @param trialsSubmitted the trialsSubmitted to set
         */
        public void setTrialsSubmitted(List<StudyProtocolQueryDTO> trialsSubmitted) {
            this.trialsSubmitted = trialsSubmitted;
        }

    }

    @Override
    public RegisteredUserDetail getModel() {
        return model;
    }

    @Override
    public String getScopeKey() {
        return scopeKey;
    }

    @Override
    public void setModel(RegisteredUserDetail m) {
        model = m;
    }

    @Override
    public void setScopeKey(String key) {
        scopeKey = key;
    }

    /**
     * @return the selectedUserId
     */
    public Long getSelectedUserId() {
        return selectedUserId;
    }

    /**
     * @param selectedUserId
     *            the selectedUserId to set
     */
    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param studySiteAccrualAccessService the studySiteAccrualAccessService to set
     */
    public void setStudySiteAccrualAccessService(
            StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService) {
        this.studySiteAccrualAccessService = studySiteAccrualAccessService;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    
}
