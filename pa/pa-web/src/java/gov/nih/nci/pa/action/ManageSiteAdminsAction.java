/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.action.ManageSiteAdminsAction.ManageSiteAdminsModel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

/**
 * @author Denis G. Krylov
 * 
 */
public class ManageSiteAdminsAction extends ActionSupport implements
        ScopedModelDriven<ManageSiteAdminsModel>, Preparable {

    private static final Logger LOG = Logger
            .getLogger(ManageSiteAdminsAction.class);

    private static final String SUCCESS_MSG = "successMessage";
    private static final String FAILURE_MSG = "failureMessage";

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ManageSiteAdminsModel model;

    private RegistryUserServiceLocal registryUserService;
    private PAOrganizationServiceRemote paOrganizationServiceRemote;
    private Long selectedOrganizationId;
    private String usersToAssign;
    private String usersToUnassign;

    private String scopeKey;

    @Override
    public ManageSiteAdminsModel getModel() {
        return model;
    }

    @Override
    public String getScopeKey() {
        return scopeKey;
    }

    @Override
    public void prepare() {
        setRegistryUserService(PaRegistry.getRegistryUserService());
        setPaOrganizationServiceRemote(PaRegistry.getPAOrganizationService());
    }

    @Override
    public void setModel(ManageSiteAdminsModel model) {
        this.model = model;
    }

    @Override
    public void setScopeKey(String key) {
        this.scopeKey = key;
    }

    @Override
    public String execute() throws PAException {
        model.setOrganizations(paOrganizationServiceRemote
                .getOrganizationsWithUserAffiliations());
        clearModel();
        return SUCCESS;
    }

    /**
     * 
     */
    private void clearModel() {
        model.getAdmins().clear();
        model.getMembers().clear();
    }

    /**
     * @return fwd
     * @throws PAException
     *             PAException
     */
    public String query() throws PAException {
        clearModel();
        if (selectedOrganizationId != null) {
            List<RegistryUser> users = registryUserService
                    .findByAffiliatedOrg(selectedOrganizationId);
            for (RegistryUser user : users) {
                if (UserOrgType.ADMIN == user.getAffiliatedOrgUserType()) {
                    model.getAdmins().add(user);
                } else {
                    model.getMembers().add(user);
                }
            }
        }
        return SUCCESS;
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String assign() throws PAException {
        Collection<Long> userIDs = new HashSet<Long>();
        for (String userIdStr : this.usersToAssign.split(";")) {
            userIDs.add(Long.parseLong(userIdStr));
        }
        return assign(userIDs);
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String unassign() throws PAException {
        Collection<Long> userIDs = new HashSet<Long>();
        for (String userIdStr : this.usersToUnassign.split(";")) {
            userIDs.add(Long.parseLong(userIdStr));
        }
        return unassign(userIDs);
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String assignAll() throws PAException {
        Collection<Long> userIDs = new HashSet<Long>();
        for (RegistryUser user : model.getMembers()) {
            userIDs.add(user.getId());
        }
        return assign(userIDs);
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String unassignAll() throws PAException {
        Collection<Long> userIDs = new HashSet<Long>();
        for (RegistryUser user : model.getAdmins()) {
            userIDs.add(user.getId());
        }
        return unassign(userIDs);
    }

    private String unassign(Collection<Long> userIDs) throws PAException {
        return changeUserOrgType(userIDs, UserOrgType.MEMBER,
                "manageSiteAdmins.usersUnassigned");

    }

    private String assign(Collection<Long> userIDs) throws PAException {
        return changeUserOrgType(userIDs, UserOrgType.ADMIN,
                "manageSiteAdmins.usersAssigned");

    }

    private String changeUserOrgType(Collection<Long> userIDs,
            UserOrgType userOrgType, String successMessageKey)
            throws PAException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        try {
            for (Long userID : userIDs) {
                registryUserService.changeUserOrgType(userID, userOrgType, "");
            }
            request.setAttribute(SUCCESS_MSG, getText(successMessageKey));
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(FAILURE_MSG, getText("manageSiteAdmins.error"));
        }
        return query();
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    public static final class ManageSiteAdminsModel implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private Collection<RegistryUser> admins = new TreeSet<RegistryUser>(
                new LastFirstNameComparator());
        private Collection<RegistryUser> members = new TreeSet<RegistryUser>(
                new LastFirstNameComparator());
        private List<Organization> organizations = new ArrayList<Organization>();

        /**
         * @return the admins
         */
        public Collection<RegistryUser> getAdmins() {
            return admins;
        }

        /**
         * @return the members
         */
        public Collection<RegistryUser> getMembers() {
            return members;
        }

        /**
         * @return the organizations
         */
        public List<Organization> getOrganizations() {
            return organizations;
        }

        /**
         * @param admins
         *            the admins to set
         */
        public void setAdmins(Collection<RegistryUser> admins) {
            this.admins = admins;
        }

        /**
         * @param members
         *            the members to set
         */
        public void setMembers(Collection<RegistryUser> members) {
            this.members = members;
        }

        /**
         * @param organizations
         *            the organizations to set
         */
        public void setOrganizations(List<Organization> organizations) {
            this.organizations = organizations;
        }

    }

    /**
     * @author Denis G. Krylov
     * 
     */
    private static final class LastFirstNameComparator implements
            Comparator<RegistryUser> {
        @Override
        public int compare(RegistryUser u1, RegistryUser u2) {
            return (StringUtils.defaultString(u1.getLastName()).concat(
                    StringUtils.defaultString(u1.getFirstName())).concat(u1
                    .getId().toString()))
                    .trim()
                    .toUpperCase()
                    .compareTo(
                            StringUtils
                                    .defaultString(u2.getLastName())
                                    .concat(StringUtils.defaultString(u2
                                            .getFirstName()))
                                    .concat(u2.getId().toString()).trim()
                                    .toUpperCase());
        }

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
     * @param paOrganizationServiceRemote
     *            the paOrganizationServiceRemote to set
     */
    public void setPaOrganizationServiceRemote(
            PAOrganizationServiceRemote paOrganizationServiceRemote) {
        this.paOrganizationServiceRemote = paOrganizationServiceRemote;
    }

    /**
     * @return the selectedOrganizationId
     */
    public Long getSelectedOrganizationId() {
        return selectedOrganizationId;
    }

    /**
     * @param selectedOrganizationId
     *            the selectedOrganizationId to set
     */
    public void setSelectedOrganizationId(Long selectedOrganizationId) {
        this.selectedOrganizationId = selectedOrganizationId;
    }

    /**
     * @return the usersToAssign
     */
    public String getUsersToAssign() {
        return usersToAssign;
    }

    /**
     * @param usersToAssign
     *            the usersToAssign to set
     */
    public void setUsersToAssign(String usersToAssign) {
        this.usersToAssign = usersToAssign;
    }

    /**
     * @return the usersToUnassign
     */
    public String getUsersToUnassign() {
        return usersToUnassign;
    }

    /**
     * @param usersToUnassign
     *            the usersToUnassign to set
     */
    public void setUsersToUnassign(String usersToUnassign) {
        this.usersToUnassign = usersToUnassign;
    }

}
