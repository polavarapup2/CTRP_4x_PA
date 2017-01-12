/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author Vrushali
 *
 */
public class MockRegistryUserService implements RegistryUserServiceLocal {
    static List<RegistryUser> usrList;
    static {
        usrList = new ArrayList<RegistryUser>();
        RegistryUser usr = new RegistryUser();
        usr.setId(1L);
        usr.setFirstName("firstName");
        usr.setLastName("lastName");
        usr.setAffiliatedOrganizationId(1L);
        usr.setAffiliatedOrgUserType(UserOrgType.PENDING_ADMIN);
        usrList.add(usr);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser createUser(RegistryUser user) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUser(String loginName) throws PAException {
        if (StringUtils.equals(loginName, "exceptionName")) {
            throw new PAException("test");
        }
        if (StringUtils.equals(loginName, "nullRegistryUser")) {
            return null;
        }

        RegistryUser regUser = new RegistryUser();
        regUser.setFirstName("Test");
        regUser.setLastName("User");
        regUser.setCity("city");
        regUser.setCountry("country");
        User csmUser = new User();
        csmUser.setUserId(1L);
        regUser.setCsmUser(csmUser);
        User userLastCreated = new User();
        userLastCreated.setLoginName("userLastCreated");
        regUser.setUserLastCreated(userLastCreated);
        User userLastUpdated = new User();
        userLastUpdated.setLoginName("userLastUpdated");
        regUser.setUserLastUpdated(userLastUpdated);
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException {
        List<RegistryUser> userTypeList = new ArrayList<RegistryUser>();
        for (RegistryUser regUser : usrList) {
            if (regUser.getAffiliatedOrgUserType().equals(userType)) {
                userTypeList.add(regUser);
            }
        }
        return userTypeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUserById(Long userId) throws PAException {
        for (RegistryUser regUser : usrList) {
            if (userId != null && userId == 4) {
                throw new PAException("test");
            }
            if (regUser.getId().equals(userId)) {
                return regUser;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        return new ArrayList<RegistryUser>();
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
        return new ArrayList<DisplayTrialOwnershipInformation>();
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
        return new HashSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> getLoginNamesByEmailAddress(String emailAddress) {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#activateAccount(java.lang.String, java.lang.String)
     */
    @Override
    public void activateAccount(String email, String username) throws PAException {
        // TODO Auto-generated method stub

    }

    @Override
    public void assignSiteOwnership(Long userId, Long studySiteId)
            throws PAException { 
    }

    @Override
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
    
        
    }

    @Override
    public boolean isEmailNotificationsEnabledOnTrialLevel(Long userId,
            Long trialId) throws PAException {
      
        return false;
    }
    
    @Override
    public List<RegistryUser> findByAffiliatedOrg(Long orgId)
            throws PAException {
        return new ArrayList<RegistryUser>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> findByAffiliatedOrgs(Collection<Long> orgIds) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    

    @Override
    public List<RegistryUser> searchByCsmUsers(Collection<User> uSet)
            throws PAException {
        return new ArrayList<RegistryUser>();
    }

  
    public void changeUserOrgType(Long userID, UserOrgType userOrgType, String rejectReason)
            throws PAException {
        
    }

   
    public List<DisplayTrialOwnershipInformation> searchSiteRecordOwnership(
            Long participatingSiteId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

   
    
    @Override
    public List<StudyProtocol> getTrialsByParticipatingSite(
            Long participatingSiteId) throws PAException {
        return new ArrayList<StudyProtocol>();
    }

    @Override
    public List<DisplayTrialOwnershipInformation> searchSiteRecordOwnership(List<Long> participatingSiteId)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
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
