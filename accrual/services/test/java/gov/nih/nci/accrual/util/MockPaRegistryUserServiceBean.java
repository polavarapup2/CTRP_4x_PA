/**
 *
 */
package gov.nih.nci.accrual.util;

import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockPaRegistryUserServiceBean implements RegistryUserServiceLocal {

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser createUser(RegistryUser user) throws PAException {
        RegistryUser regUser = null;
        for (User userBean : MockCsmUtil.users) {
            if (user.getCsmUser().getUserId().equals(userBean.getUserId())) {
                regUser = new RegistryUser();
                regUser.setState("Texas");
                regUser.setCsmUser(userBean);
                regUser.setFirstName("firstName");
                regUser.setMiddleName("middleName");
                regUser.setLastName("lastName");
                regUser.setAddressLine("address");
                regUser.setAffiliateOrg("testOrg");
                regUser.setCity("Dallas");
                regUser.setCountry("USA");
                regUser.setPhone("phone");
                regUser.setPoOrganizationId(1L);
                regUser.setPoPersonId(1L);
                regUser.setPostalCode("postAdd");
                regUser.setPrsOrgName("prsOrg");
                regUser.setId(1L);
                regUser.setUserLastCreated(TestSchema.createUser());
                regUser.setUserLastUpdated(TestSchema.createUser());
            }
        }
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUser(String loginName) throws PAException {
        if (loginName != null && loginName.equals("exceptionName")) {
            throw new PAException("test");
        }
        RegistryUser regUser = null;
        for (User user : MockCsmUtil.users) {
            if (loginName.equals(user.getLoginName())) {
                regUser = new RegistryUser();
                regUser.setCity("city");
                regUser.setCountry("USA");
                regUser.setState("Texas");
                regUser.setCsmUser(user);
                regUser.setUserLastCreated(TestSchema.createUser());
                regUser.setUserLastUpdated(TestSchema.createUser());
            }
        }
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        RegistryUser regUser = null;
        for (User userBean : MockCsmUtil.users) {
            if (user.getCsmUser().getUserId().equals(userBean.getUserId())) {
                regUser = new RegistryUser();
                regUser.setState("Texas");
                regUser.setCsmUser(userBean);
                regUser.setFirstName("firstName");
                regUser.setMiddleName("middleName");
                regUser.setLastName("lastName");
                regUser.setAddressLine("address");
                regUser.setAffiliateOrg("testOrg");
                regUser.setCity("Dallas");
                regUser.setCountry("USA");
                regUser.setPhone("phone");
                regUser.setPoOrganizationId(1L);
                regUser.setPoPersonId(1L);
                regUser.setPostalCode("postAdd");
                regUser.setPrsOrgName("prsOrg");
                regUser.setId(1L);
                regUser.setUserLastCreated(TestSchema.createUser());
                regUser.setUserLastUpdated(TestSchema.createUser());
            }
        }
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUserById(Long userId) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> search(RegistryUser user) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(RegistryUser user, Long studyProtocolId) throws PAException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignOwnership(Long userId, Long studyProtocolId) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeOwnership(Long userId, Long studyProtocolId) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTrialOwner(Long userId, Long studyProtocolId) throws PAException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DisplayTrialOwnershipInformation> searchTrialOwnership(
            DisplayTrialOwnershipInformation trialOwnershipInfo, List<Long> affiliatedOrgId) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doesRegistryUserExist(String loginName) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTrialOwnerNames(Long studyProtocolId) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<RegistryUser> getAllTrialOwners(Long studyProtocolId) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> getLoginNamesByEmailAddress(String emailAddress) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateAccount(String email, String username) throws PAException {
    }


    public void assignSiteOwnership(Long userId, Long studySiteId)
            throws PAException {
    }


    public void removeSiteOwnership(Long userId, Long studySiteId)
            throws PAException {

    }

    public Long getUserId(String loginName) throws PAException {
        return 0L;
    }


    public RegistryUser getPartialUserById(Long userId) throws PAException {
        return getUserById(userId);
    }


    public boolean isEmailNotificationsEnabled(Long userId, Long trialId)
            throws PAException {
        return false;
    }


    public void setEmailNotificationsPreference(Long userId, Long trialId,
            boolean enableEmails) throws PAException {
        // TODO Auto-generated method stub

    }


    public boolean isEmailNotificationsEnabledOnTrialLevel(Long userId,
            Long trialId) throws PAException {

        return false;
    }


    public List<RegistryUser> findByAffiliatedOrg(Long orgId)
            throws PAException {
        return new ArrayList<RegistryUser>();
    }


    public List<RegistryUser> findByAffiliatedOrgs(Collection<Long> orgIds)
            throws PAException {
        return new ArrayList<RegistryUser>();
    }


    public List<RegistryUser> searchByCsmUsers(Collection<User> uSet)
            throws PAException {
        return new ArrayList<RegistryUser>();
    }


    public void changeUserOrgType(Long userID, UserOrgType userOrgType, String rejectReason)
            throws PAException {


    }


    public List<DisplayTrialOwnershipInformation> searchSiteRecordOwnership(
            List<Long> participatingSiteId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
	public List<StudyProtocol> getTrialsByParticipatingSite(
	            Long participatingSiteId) throws PAException {
	        return  null;
    }

    @Override
    public void assignSiteOwnership(List<Long> userId, Set<Long> studySiteId) throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeSiteOwnership(List<Long> userId, Set<Long> studySiteId) throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void assignOwnership(List<Long> userId, Set<Long> studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeOwnership(List<Long> userId, Set<Long> studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeAllOwnership(RegistryUser registryUser, List<Long> list) throws PAException {
        // TODO Auto-generated method stub
        
    }

}
