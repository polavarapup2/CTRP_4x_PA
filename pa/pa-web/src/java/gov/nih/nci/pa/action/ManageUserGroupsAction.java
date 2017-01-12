package gov.nih.nci.pa.action;

import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author dkrylov
 * 
 */
// CHECKSTYLE:OFF
public class ManageUserGroupsAction extends ActionSupport implements
        Preparable, ServletResponseAware {
    /**
     * 
     */
    private static final long serialVersionUID = 8948560651424817629L;

    private static final Logger LOG = Logger
            .getLogger(ManageUserGroupsAction.class);

    private Long userID;
    private String newGroups;

    private UserProvisioningManager upm;

    private HttpServletResponse response;
    private static final String[] GROUPS = new String[] { "Submitter",
            "RegAdmin", "ReportViewer", "SuAbstractor", "gridClient",
            "AdminAbstractor", "ScientificAbstractor", "ResultsAbstractor",
            "SecurityAdmin", "ProgramCodeAdministrator" };
    private static final List<String> SYSTEM_USERS = Arrays
            .asList(new String[] { "ejbclient", "firebird", "pauser",
                    "subscriber", "csmadmin", "publisher",
                    "O=caBIG,OU=caGrid,OU=LOA1,OU=NCI,CN=qaproduser" });

    @Override
    public String execute() {
        return INPUT;
    }

    @SuppressWarnings("deprecation")
    public StreamResult saveRoles() throws IOException {
        try {
            User user = upm.getUserById(getUserID().toString());
            upm.assignGroupsToUser(getUserID().toString(), new String[] {});
            if (StringUtils.isNotBlank(getNewGroups())) {
                for (String newGroup : getNewGroups().split(";")) {
                    LOG.warn("Adding user " + user.getLoginName()
                            + " to group " + newGroup);
                    upm.assignUserToGroup(user.getLoginName(), newGroup);
                }
            }

        } catch (Exception e) {
            LOG.error(e, e);
            response.sendError(500);
        }
        return new StreamResult(new ByteArrayInputStream(
                StringUtils.EMPTY.getBytes()));

    }

    /**
     * @return
     */
    public List<String> getGroups() {
        return Arrays.asList(GROUPS);
    }

    @SuppressWarnings("unchecked")
    public List<String> getUserGroupsAsString(User user) {
        List<String> list = new ArrayList<String>();
        for (Group g : ((Collection<Group>) user.getGroups())) {
            list.add(g.getGroupName());
        }
        return list;
    }

    /**
     * @return List<User>
     * @throws CSObjectNotFoundException
     */
    @SuppressWarnings("unchecked")
    public Collection<User> getUsers() throws CSObjectNotFoundException {
        final Collection<User> users = new TreeSet<User>();
        final SessionFactory sf = (SessionFactory) ServletActionContext
                .getServletContext().getAttribute("csmSessionFactory");
        Session s = null;
        try {
            s = sf.openSession();
            users.addAll(((List<User>) s
                    .createCriteria(User.class)
                    .addOrder(Order.asc("loginName"))
                    .add(Restrictions.not(Restrictions.in("loginName",
                            SYSTEM_USERS)))
                    .add(Restrictions.not(Restrictions.ilike("loginName", "/O",
                            MatchMode.ANYWHERE))).list()));
            for (User user : users) {
                user.getGroups().isEmpty();
            }
            return users;
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public void prepare() throws CSConfigurationException, CSException {
        upm = SecurityServiceProvider.getUserProvisioningManager("pa");

    }

    /**
     * @return the userID
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * @param userID
     *            the userID to set
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * @return the newGroups
     */
    public String getNewGroups() {
        return newGroups;
    }

    /**
     * @param newGroups
     *            the newGroups to set
     */
    public void setNewGroups(String newGroups) {
        this.newGroups = newGroups;
    }

    @Override
    public void setServletResponse(HttpServletResponse r) {
        this.response = r;

    }

    /**
     * @param upm
     *            the upm to set
     */
    public void setUpm(UserProvisioningManager upm) {
        this.upm = upm;
    }

    /**
     * @return the upm
     */
    public UserProvisioningManager getUpm() {
        return upm;
    }

}
