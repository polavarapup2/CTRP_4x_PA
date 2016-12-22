/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockRegistryUserService implements RegistryUserServiceLocal {
    public static List<RegistryUser> userList;
    static {
        userList = new ArrayList<RegistryUser>();
        RegistryUser dto = new RegistryUser();
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setId(1L);
        dto.setCsmUser(new User());
        dto.setAffiliatedOrganizationId(1L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        dto.setSiteAccrualSubmitter(false);
        dto.setFamilyAccrualSubmitter(false);
        userList.add(dto);

        dto = new RegistryUser();
        dto.setFirstName("affiliated Org");
        dto.setLastName("lastName");
        dto.setId(2L);
        dto.setCsmUser(new User());
        dto.setAffiliatedOrganizationId(2L);
        dto.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        dto.setSiteAccrualSubmitter(false);
        dto.setFamilyAccrualSubmitter(false);
        userList.add(dto);

        // add three members.
        dto = new RegistryUser();
        dto.setFirstName("RegUser");
        dto.setLastName("lastName");
        dto.setId(3L);
        dto.setCsmUser(new User());
        dto.setAffiliatedOrganizationId(3L);
        dto.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        dto.setSiteAccrualSubmitter(false);
        dto.setFamilyAccrualSubmitter(false);
        userList.add(dto);

        dto = new RegistryUser();
        dto.setFirstName("reguser2");
        dto.setLastName("lastName Two");
        dto.setId(4L);
        dto.setCsmUser(new User());
        dto.setAffiliatedOrganizationId(3L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        dto.setSiteAccrualSubmitter(false);
        dto.setFamilyAccrualSubmitter(false);
        userList.add(dto);

        dto = new RegistryUser();
        dto.setFirstName("reguser3");
        dto.setLastName("lastName Three");
        dto.setId(5L);
        dto.setCsmUser(new User());
        dto.setAffiliatedOrganizationId(3L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        dto.setSiteAccrualSubmitter(false);
        dto.setFamilyAccrualSubmitter(false);
        userList.add(dto);

        dto = new RegistryUser();
        dto.setFirstName("userLastCreated");
        dto.setLastName("lastName");
        dto.setId(1L);
        dto.setCsmUser(new User());
        dto.setAffiliatedOrganizationId(1L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        dto.setSiteAccrualSubmitter(false);
        dto.setFamilyAccrualSubmitter(false);
        userList.add(dto);
        
        dto = new RegistryUser();
        dto.setFirstName("FirstName");
        dto.setLastName("lastName");
        dto.setId(1L);
        dto.setCsmUser(new User());
        dto.setAffiliatedOrganizationId(2L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        dto.setSiteAccrualSubmitter(false);
        dto.setFamilyAccrualSubmitter(false);
        dto.setReportGroups("DT4");
        userList.add(dto);
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
        if (loginName != null && loginName.equals("throwException")) {
            throw new PAException("testing");
        }
        for (RegistryUser usrDto : userList) {
            if (usrDto.getFirstName().equals(loginName)) {
                return usrDto;
            }
        }
        return null;
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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUserById(Long userId) {
        for (RegistryUser usrDto : userList) {
            if (usrDto.getId().equals(userId)) {
                return usrDto;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        List<RegistryUser> returnList = new ArrayList<RegistryUser>();
        for (RegistryUser usrDto : userList) {
            if (regUser.getAffiliatedOrgUserType() != null) {
                if (usrDto.getAffiliatedOrganizationId().equals(regUser.getAffiliatedOrganizationId())
                        && usrDto.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN)) {
                    returnList.add(usrDto);
                }
            } else {
                if (usrDto.getAffiliatedOrganizationId().equals(regUser.getAffiliatedOrganizationId())) {
                    returnList.add(usrDto);
                }
            }

        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {
        if (loginName != null && loginName.equalsIgnoreCase("userLastCreated") && studyProtocolId != null
                && studyProtocolId == 1) {
            return true;
        }
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
        return new HashSet<RegistryUser>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateAccount(String email, String username) throws PAException {
    }

    public List<RegistryUser> getLoginNamesByEmailAddress(String emailAddress) {
        return null;
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

   
    public List<RegistryUser> searchByCsmUsers(Collection<User> uSet)
            throws PAException {
        return new ArrayList<RegistryUser>();
    }

   
    public void changeUserOrgType(Long userID, UserOrgType userOrgType, String rejectReason)
            throws PAException {
    }

	public void assignOwnership(Long userId, String nciID) throws PAException {
	}

   
    public List<DisplayTrialOwnershipInformation> searchSiteRecordOwnership(
            Long participatingSiteId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param spDto
     * @return
     */
    private StudyProtocol createStudyProtocol() {
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        Set<Ii> others = new HashSet<Ii>();
        Ii nciid = IiConverter.convertToAssignedIdentifierIi("NCI-2000-11212");
        others.add(nciid);
        sp.setOtherIdentifiers(others);
        sp.setOfficialTitle("Official title");
        sp.setProprietaryTrialIndicator(true);
        sp.setCtgovXmlRequiredIndicator(false);
        sp.setUserLastCreated(null);
        StudySite ss = new StudySite();
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setLocalStudyProtocolIdentifier("LEAD-ID");
        sp.getStudySites().add(ss);
        return sp;
    }
    
    @Override
    public List<StudyProtocol> getTrialsByParticipatingSite(
            Long participatingSiteId) throws PAException {
        List<StudyProtocol> protocols = new ArrayList<StudyProtocol>();
        protocols.add(createStudyProtocol());
        return  protocols;
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
