package gov.nih.nci.registry.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.rest.jasper.JasperServerRestClient;
import gov.nih.nci.registry.util.ReportViewerCriteria;

/**
 * @author vpoluri
 *
 */
public class ReportViewerAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ReportViewerAction.class);
    private List<RegistryUser> registryUsers = new ArrayList<RegistryUser>();
    private ReportViewerCriteria criteria = new ReportViewerCriteria();
    private List<String> affiliatedOrgAdmins = new ArrayList<String>();
    private static final String REG_USERS_LIST = "regUsersList";
    private static final String VIEW_RESULTS = "viewResults";
    private static final String REPORT_LIST = "reportList";
    private static final String REPORT_ROLE_LIST = "reportRoleList";

    private MailManagerServiceLocal mailManagerService;
    private LookUpTableServiceRemote lookupTableService;
    private RegistryUserServiceLocal registryUserService;
    private JasperServerRestClient jasperRestClient;

    @Override
    public void prepare() {
        lookupTableService = PaRegistry.getLookUpTableService();
        registryUserService = PaRegistry.getRegistryUserService();
        mailManagerService = PaRegistry.getMailManagerService();

        try {
            String baseURL = getPropertyValue("jasper.base.user.rest.url");
            String allowTrusted = getPropertyValue("regweb.jasper.allow.allssl");
            boolean allowTrustedSites = false;
            try {
                allowTrustedSites = Boolean.parseBoolean(allowTrusted);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
            jasperRestClient = new JasperServerRestClient(baseURL, allowTrustedSites);
        } catch (Exception e) {
            LOG.log(Priority.DEBUG, "Error while retreving jasper properties : " + e.getMessage());
        }
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
        fetchReportDetails();
        return VIEW_RESULTS;
    }

    /**
     * view results from session..
     * 
     * @return String
     */
    @SuppressWarnings("unchecked")
    public String view() {
        registryUsers = (List<RegistryUser>) ServletActionContext.getRequest().getSession()
                .getAttribute(ReportViewerAction.REG_USERS_LIST);

        return VIEW_RESULTS;
    }

    private List<RegistryUser> sortBasedOnFirstName(List<RegistryUser> userList) {

        Comparator<RegistryUser> userComp = new Comparator<RegistryUser>() {
            @Override
            public int compare(RegistryUser o1, RegistryUser o2) {
                return o1.getFirstName().toUpperCase().compareTo(o2.getFirstName().toUpperCase());
            }
        };

        Collections.sort(userList, userComp);

        return userList;
    }

    private void performSearch() throws PAException {
        String loginName = null;
        registryUsers = new ArrayList<RegistryUser>();
        try {
            loginName = ServletActionContext.getRequest().getRemoteUser();
            RegistryUser loggedInUser = getRegistryUserService().getUser(loginName);

            Long orgId = loggedInUser.getAffiliatedOrganizationId();

            FamilyHelper familyHelper = new FamilyHelper();
            List<Long> allOrgs = familyHelper.getAllRelatedOrgs(orgId);

            for (Long iOrgId : allOrgs) {

                RegistryUser regUserCri = new RegistryUser();
                regUserCri.setAffiliatedOrganizationId(iOrgId);
                regUserCri.setFirstName(criteria.getFirstName());
                regUserCri.setLastName(criteria.getLastName());
                regUserCri.setEmailAddress(criteria.getEmailAddress());

                List<RegistryUser> regUsers = getRegistryUserService().search(regUserCri);

                /*
                 * PO-9174: Remove registry user from list if doesn't have CSM
                 * user
                 */
                Iterator<RegistryUser> regUserItr = regUsers.iterator();
                while (regUserItr.hasNext()) {
                    RegistryUser regUserLcl = regUserItr.next();
                    if (regUserLcl.getCsmUser() == null) {
                        regUserItr.remove();
                    }
                }

                registryUsers.addAll(sortBasedOnFirstName(regUsers));
            }

            ServletActionContext.getRequest().getSession().setAttribute(ReportViewerAction.REG_USERS_LIST,
                    registryUsers);

        } catch (PAException e) {
            LOG.error(e.getMessage());
            throw new PAException(e);
        }
    }

    /**
     * set LookupTableService
     * 
     * @param service
     *            lookup service
     */
    public void setLookUpTableService(LookUpTableServiceRemote service) {
        lookupTableService = service;
    }

    /**
     * @return lookup table service
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookupTableService;
    }

    /**
     * 
     * @param name
     *            get property value
     * @return property value
     * @throws PAException
     *             throws PAException
     */
    public String getPropertyValue(String name) throws PAException {
        String retString = "";

        try {

            LookUpTableServiceRemote lookupBean = getLookUpTableService();
            retString = lookupBean.getPropertyValue(name);

        } catch (Exception ex) {
            LOG.log(Priority.ERROR, ex.getMessage());
            throw new PAException(ex);
        }
        return retString;
    }

    /**
     * 
     */
    private void fetchReportDetails() {

        HashMap<String, String> reportGroupMap = new HashMap<String, String>();
        List<String> reportList = new ArrayList<>();
        String propValue = "";

        try {
            propValue = getPropertyValue("regweb.reportview.availableReports");

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

        }

        if (propValue != null && propValue.length() > 0) {

            String[] reportKeyValues = propValue.split("[,]");

            for (String reportKeyValue : reportKeyValues) {

                String[] reportGroupArr = reportKeyValue.split("[:]");

                if (reportGroupArr != null && reportGroupArr.length >= 2) {

                    reportGroupMap.put(reportGroupArr[0], reportGroupArr[1]);
                    reportList.add(reportGroupArr[0]);

                } else {

                    LOG.log(Priority.ERROR, "Invalid report property - " + reportKeyValue);

                }
            }
        }

        ServletActionContext.getRequest().getSession().setAttribute(ReportViewerAction.REPORT_LIST, reportList);
        ServletActionContext.getRequest().getSession().setAttribute(ReportViewerAction.REPORT_ROLE_LIST,
                reportGroupMap);
    }

    /**
     * save.
     * 
     * @return String
     * @throws PAException
     *             the pa exception
     */
    @SuppressWarnings("unchecked")
    public String save() throws PAException {
        String failedToUpdateUsers = "";

        try {
            registryUsers = (List<RegistryUser>) ServletActionContext.getRequest().getSession()
                    .getAttribute(ReportViewerAction.REG_USERS_LIST);

            HashMap<String, String> reportGroupMap = (HashMap<String, String>) ServletActionContext.getRequest()
                    .getSession().getAttribute(ReportViewerAction.REPORT_ROLE_LIST);

            HashMap<String, String> permittedReportsFromViewMap = getPermittedReportMap();

            // pick updated users only.

            List<RegistryUser> dirtyUpdatedUsers = getDirtyUpdatedUsers(registryUsers, permittedReportsFromViewMap);

            for (RegistryUser regUser : dirtyUpdatedUsers) {
                String reportIds = permittedReportsFromViewMap.get(regUser.getId().toString());

                boolean dbEnableReports = regUser.getEnableReports() == null ? false : regUser.getEnableReports();

                if (!dbEnableReports && reportIds != null && reportIds.length() > 0) {
                    regUser.setEnableReports(true);
                }

                String csmUsername = regUser.getCsmUser().getLoginName();
                String loginName = gov.nih.nci.pa.util.CsmUserUtil.getGridIdentityUsername(csmUsername);

                String jasperClientResponse = jasperRestClient.checkAndUpdateUser(loginName, reportIds, reportGroupMap);

                if (jasperClientResponse != null && jasperClientResponse.length() > 0
                        && jasperClientResponse.startsWith("Error")) {
                    failedToUpdateUsers += "Unable to update user " + regUser.getFullName()
                            + " jasper role. Response from Jasper - (" + jasperClientResponse
                            + ") . Please contact Admin";

                } else {
                    regUser.setReportGroups(reportIds);
                    LOG.log(Priority.DEBUG, "save: reportIds: " + reportIds + " regId: " + regUser.getId());
                    getRegistryUserService().updateUser(regUser);
                    ServletActionContext.getRequest().setAttribute("successMessage",
                            getText("reportviewers.status.success"));
                }
            }

        } catch (Exception e) {
            failedToUpdateUsers += "Unable to update user - " + e.getMessage();
        }

        if (failedToUpdateUsers.length() > 0) {
            ServletActionContext.getRequest().setAttribute("failureMessage", failedToUpdateUsers);
        }

        return search();
    }

    private List<RegistryUser> getDirtyUpdatedUsers(List<RegistryUser> registryUsersParam,
            HashMap<String, String> permittedReportsFromViewMap) {

        List<RegistryUser> updatedUsers = new ArrayList<RegistryUser>();

        for (RegistryUser regUser : registryUsersParam) {
            String existingReportGrps = regUser.getReportGroups();
            String permittedReportIds = permittedReportsFromViewMap.get(regUser.getId().toString());

            if (existingReportGrps == null && permittedReportIds == null) {
                continue;
            } else if (existingReportGrps != null && permittedReportIds != null) {

                List<String> exRepoList = Arrays.asList(existingReportGrps.split(","));
                List<String> permitReports = Arrays.asList(permittedReportIds.split(","));

                if (exRepoList.size() != permitReports.size()) {
                    updatedUsers.add(regUser);

                } else {
                    if (exRepoList.size() > 0) {
                        if (!exRepoList.containsAll(permitReports)) {
                            updatedUsers.add(regUser);
                        }
                    }
                }
            } else {
                updatedUsers.add(regUser);
            }
        }

        LOG.debug(">>>>>Users to update: " + updatedUsers.size());
        return updatedUsers;
    }

    /**
     * @return
     */
    private HashMap<String, String> getPermittedReportMap() {
        String[] permittedReports = ServletActionContext.getRequest().getParameterValues("permittedReports");
        HashMap<String, String> permittedReportsFromViewMap = new HashMap<>();

        if (permittedReports != null) {
            for (String permittedReport : permittedReports) {

                String[] reportTokens = permittedReport.split("[~]");

                if (reportTokens.length >= 2) {

                    String reportId = reportTokens[0];
                    String regId = reportTokens[1];
                    String availableReportIds = permittedReportsFromViewMap.get(regId);

                    if (availableReportIds != null) {
                        permittedReportsFromViewMap.put(regId.trim(), availableReportIds + "," + reportId.trim());
                    } else {
                        permittedReportsFromViewMap.put(regId.trim(), reportId.trim());
                    }
                }
            }
        }
        return permittedReportsFromViewMap;
    }

    /**
     * @return the criteria
     */
    public ReportViewerCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria
     *            the criteria to set
     */
    public void setCriteria(ReportViewerCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the affiliatedOrgAdmins
     */
    public List<String> getAffiliatedOrgAdmins() {
        return affiliatedOrgAdmins;
    }

    /**
     * @param affiliatedOrgAdmins
     *            the affiliatedOrgAdmins to set
     */
    public void setAffiliatedOrgAdmins(List<String> affiliatedOrgAdmins) {
        this.affiliatedOrgAdmins = affiliatedOrgAdmins;
    }

    /**
     * @return the registryUsers.
     */
    public List<RegistryUser> getRegistryUsers() {
        return registryUsers;
    }

    /**
     * @param registryUsers
     *            the registryUsers to set
     */
    public void setRegistryUsers(List<RegistryUser> registryUsers) {
        this.registryUsers = registryUsers;
    }

    /**
     * 
     * @return return mail manager service
     */
    public MailManagerServiceLocal getMailManagerService() {
        return mailManagerService;
    }

    /**
     * 
     * @param mailManagerService
     *            set mail manager service
     */

    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * 
     * @return returns register user service
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * 
     * @param registryUserService
     *            set registry user service
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

}
