package gov.nih.nci.pa.action;

import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.NCICommonsUtils;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Monish
 * 
 */
public class UserAccountDetailsAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = -768848603259442783L;
    private User user;
    private CSMUserUtil userService;
    private OrganizationEntityServiceRemote organizationEntityService;

    //UI fields
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String organization;
    private String emailId;

    @Override
    public void prepare() {
        userService = CSMUserService.getInstance();
        organizationEntityService = PoRegistry.getOrganizationEntityService();
    }
    
    /**
     * @return res
     * @throws PAException
     *             exception
     */
    @Override
    public String execute() throws PAException {
        user = userService.getCSMUser(UsernameHolder.getUser());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setOrganization(user.getOrganization());
        setPhoneNumber(user.getPhoneNumber());
        setEmailId(user.getEmailId());
        return SUCCESS;
    }    
    
    /**
     * 
     * @return string
     * @throws PAException PAException
     */
    public String save() throws PAException {
        user = userService.getCSMUser(UsernameHolder.getUser());
        user.setFirstName(NCICommonsUtils.performXSSFilter(
                StringUtils.defaultString(getFirstName()), true, false, true));
        user.setLastName(NCICommonsUtils.performXSSFilter(StringUtils.defaultString(getLastName()), true, false, true));
        user.setOrganization(getOrganization());
        user.setPhoneNumber(NCICommonsUtils.performXSSFilter(
                StringUtils.defaultString(getPhoneNumber()), true, false, true));
        user.setEmailId(NCICommonsUtils.performXSSFilter(StringUtils.defaultString(getEmailId()), true, false, true));
        user = userService.updateCSMUser(user);
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        return SUCCESS;
    }

    /**
     * @return action
     */
    public String updateOrgName() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        try {
            execute();
            OrganizationDTO poOrg = organizationEntityService.
                getOrganization(IiConverter.convertToPoOrganizationIi(orgId));
            if (poOrg == null) {
                throw new PAException("Error getting organization data from PO for id = " + orgId
                    + ".  Check that PO service is running and databases are synchronized.  ");
            }
            setOrganization(EnOnConverter.convertEnOnToString(poOrg.getName()));
        } catch (PAException e) {
            addActionError(e.getMessage());
        } catch (NullifiedEntityException e) {
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        if (StringUtils.isEmpty(user.getLoginName())) {
            return null;
        }
        return CsmUserUtil.getGridIdentityUsername(user.getLoginName());
    }    

    /**
     * @param userService the userService to set
     */
    public void setUserService(CSMUserUtil userService) {
        this.userService = userService;
    }
    
    /**
     * @param organizationEntityService the organizationEntityService to set
     */
    public void setOrganizationEntityService(
            OrganizationEntityServiceRemote organizationEntityService) {
        this.organizationEntityService = organizationEntityService;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
