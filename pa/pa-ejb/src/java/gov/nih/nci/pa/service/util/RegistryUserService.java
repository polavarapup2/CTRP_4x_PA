/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author aevansel@5amsolutions.com
 */
public interface RegistryUserService {

    /**
     * Create a new Registry user.
     * @param user user
     * @return user
     * @throws PAException PAException
     */
    RegistryUser createUser(RegistryUser user) throws PAException;

    /**
     * Update an existing Registry user.
     * @param user user
     * @return user
     * @throws PAException PAException
     */
    RegistryUser updateUser(RegistryUser user) throws PAException;

    /**
     * Retrieves user by login name.
     * @param loginName loginName
     * @return user
     * @throws PAException PAException
     */
    RegistryUser getUser(String loginName) throws PAException;

    /**
     * @param userType of user
     * @return list of pending user admin
     * @throws PAException on error
     */
    List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException;

    /**
     * @param userId registry user id
     * @return user
     * @throws PAException on error
     */
    RegistryUser getUserById(Long userId) throws PAException;

    /**
     * Determine whether a user with the given login name exists.
     * @param loginName the login name of the user to check for
     * @return true iff a user with the give login name exists.
     */
    boolean doesRegistryUserExist(String loginName);

    /**
     *
     * @param regUser user
     * @return list of user
     * @throws PAException on error
     */
    List<RegistryUser> search(RegistryUser regUser) throws PAException;
    
    /**
     * @param uSet uSet
     * @return List<RegistryUser>
     * @throws PAException PAException
     */
    List<RegistryUser> searchByCsmUsers(Collection<User> uSet) throws PAException;


    /**
     * @param trialOwnershipInfo the criteria object.
     * @param affiliatedOrgId the affiliated org id.
     * @return list of trial ownership information objects.
     * @throws PAException on error.
     */
    List<DisplayTrialOwnershipInformation> searchTrialOwnership(DisplayTrialOwnershipInformation
            trialOwnershipInfo, List<Long> affiliatedOrgId) throws PAException;

    /**
     * Given a login name and study protocol id, find out if the user is
     * either a trial owner or the admin for the lead org.
     * @param loginName user name
     * @param studyProtocolId id
     * @return boolean
     * @throws PAException exception
     */
    boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException;
    /**
     * Given a Registry User and study protocol id, find out if the user is
     * either a trial owner or the admin for the lead org.
     * @param user user name
     * @param studyProtocolId id
     * @return boolean
     * @throws PAException exception
     */
    boolean hasTrialAccess(RegistryUser user, Long studyProtocolId) throws PAException;
    /**
     * Assign ownership of given protocol to given user.
     * @param userId user id
     * @param studyProtocolId study protocol id
     * @throws PAException on error
     */
    void assignOwnership(Long userId, Long studyProtocolId) throws PAException;
    /**
     * Removes ownership.
     * @param userId user id
     * @param studyProtocolId study protocol id
     * @throws PAException on error
     */
    void removeOwnership(Long userId, Long studyProtocolId) throws PAException;

    /**
     * Given a Registry User Id and study protocol id, find out if the user is a trial owner.
     * @param userId the user id
     * @param studyProtocolId id
     * @return boolean
     * @throws PAException exception
     */
    boolean isTrialOwner(Long userId, Long studyProtocolId) throws PAException;

    /**
     * Get all names based on trial id.
     * @param studyProtocolId trial id
     * @return list of login names.
     * @throws PAException when error.
     */
    List<String> getTrialOwnerNames(Long studyProtocolId) throws PAException;

    /**
     * Get all trial owners of a protocol.
     * @param studyProtocolId the protocol.
     * @return all registry users owning the trial.
     * @throws PAException when an error occurs.
     */
    Set<RegistryUser> getAllTrialOwners(Long studyProtocolId) throws PAException;

    /**
     * Gets the login names of the registry users having the given email address.
     * @param emailAddress The e-mail address
     * @return The list of login names of the registry users having the given email address.
     */
    List<RegistryUser> getLoginNamesByEmailAddress(String emailAddress);

    /**
     * Links a CSM and registryUser using the email address.
     * @param email registry user email.
     * @param username CN.
     * @throws PAException when an error occurs.
     */
    void activateAccount(String email, String username) throws PAException;

    
    /**
     * Assigns study site record ownership of the given participating site to the given user.
     * @param userId user id
     * @param studySiteId study site id
     * @throws PAException on error
     */
    void assignSiteOwnership(Long userId, Long studySiteId) throws PAException;
    
    /**
     * Removes study site record ownership of the given participating site for the given user.
     * @param userId user id
     * @param studySiteId study site id
     * @throws PAException on error
     */
    void removeSiteOwnership(Long userId, Long studySiteId) throws PAException;

    /**
     * Return the list of site trial ownerships for the given site
     * @param participatingSiteId the participating site id
     * @return list of trial ownership information objects.
     * @throws PAException on error.
     */
    List<DisplayTrialOwnershipInformation> searchSiteRecordOwnership(
            List<Long> participatingSiteId) throws PAException;
    
    /**
     * Retrieves user's ID by login name.
     * @param loginName loginName
     * @return id
     * @throws PAException PAException
     */
    Long getUserId(String loginName) throws PAException;
    
    /**
     * This method returns a user with the given ID. The difference between this method
     * and {@link #getUserById(Long)} is that this method is performance-oriented and will return
     * <b>partially initalized</b> {@link RegistryUser} object: its associations will not be initialized.
     * @param userId userId
     * @return RegistryUser
     * @throws PAException PAException
     */
    RegistryUser getPartialUserById(Long userId) throws PAException;

    /**
     * Determines whether email notifications for the given trial ownership are enabled.
     * This method takes into account both global level and trial level settings.
     * @param userId userId
     * @param trialId trialId
     * @return isEmailNotificationsEnabled
     * @exception PAException PAException
     */
    boolean isEmailNotificationsEnabled(Long userId, Long trialId) throws PAException;
    
    /**
     * Determines whether email notifications for the given trial ownership are enabled.
     * This method takes into account trial level settings ONLY.
     * @param userId userId
     * @param trialId trialId
     * @return isEmailNotificationsEnabled
     * @exception PAException PAException
     */
    boolean isEmailNotificationsEnabledOnTrialLevel(Long userId, Long trialId) throws PAException;    
    
    /**
     * Sets email notifications preference for the given trial ownership record.
     * 
     * @param userId
     *            userId
     * @param trialId
     *            trialId
     * @param enableEmails
     *            enableEmails
     * @exception PAException
     *                PAException
     */
    void setEmailNotificationsPreference(Long userId, Long trialId,
            boolean enableEmails) throws PAException;   
    
    
    /**
     * Returns all users affiliated with the given organization and having a
     * non-empty link to a CSM User. I.e. users that have not completed account
     * activation won't be included.
     * 
     * @param orgId orgId
     * @return  List<RegistryUser>
     * @throws PAException PAException
     */
    List<RegistryUser> findByAffiliatedOrg(Long orgId) throws PAException;

    /**
     * Returns all users affiliated with the given organizations and having a
     * non-empty link to a CSM User. I.e. users that have not completed account
     * activation won't be included.
     * 
     * @param orgIds the organization id's
     * @return  List<RegistryUser>
     * @throws PAException PAException
     */
    List<RegistryUser> findByAffiliatedOrgs(Collection<Long> orgIds) throws PAException;
    
    /**
     * Changes {@link UserOrgType} of the given user sending a email notification if needs be.
     * @param userID userID
     * @param userOrgType userOrgType
     * @param rejectReason rejectReason
     * @throws PAException PAException
     */
    void changeUserOrgType(Long userID, UserOrgType userOrgType, String rejectReason) throws PAException;
    
    /**
     * Get list of trials with active assignment to the specified site
     * @param participatingSiteId  participating site id
     * @return List<StudyProtocol> 
     * @throws PAException PAException
     */
    List<StudyProtocol> getTrialsByParticipatingSite(Long participatingSiteId) throws PAException;

    /**
     * A batch version of the regular assignSiteOwnership
     * @param userId the userids
     * @param studySiteId the siteids
     * @throws PAException database exceptions
     */
    void assignSiteOwnership(List<Long> userId, Set<Long> studySiteId) throws PAException;
    
    /**
     * A batch version of the regular removeSiteOwnership
     * @param userId the user ids
     * @param studySiteId the site ids 
     * @throws PAException the database exceptions
     */
    void removeSiteOwnership(List<Long> userId, Set<Long> studySiteId) throws PAException;
    
    /**
     * A batch version of the regular assignOwnership
     * @param userId the user ids
     * @param studyProtocolId the trial ids
     * @throws PAException database exceptions
     */
    void assignOwnership(List<Long> userId, Set<Long> studyProtocolId) throws PAException;
    
    /**
     * A batch version of the regular removeOwnership
     * @param userId the user ids
     * @param studyProtocolId the trial ids
     * @throws PAException database exceptions
     */
    void removeOwnership(List<Long> userId, Set<Long> studyProtocolId) throws PAException;

    /**
     * Remove all Site and trial ownership from a user.
     * @param registryUser The user whose ownership to remove.
     * @param list The list of Orgs to remove ownership from.
     * @throws PAException the exception if any.
     */
    void removeAllOwnership(RegistryUser registryUser, List<Long> list) throws PAException;

}
