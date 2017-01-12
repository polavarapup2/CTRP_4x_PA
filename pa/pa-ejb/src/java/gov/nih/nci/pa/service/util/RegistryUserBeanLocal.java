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

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * @author aevansel@5amsolutions.com
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class RegistryUserBeanLocal implements RegistryUserServiceLocal {
    private static final String REG_USER = "regUser";
    private static final String UNCHECKED = "unchecked";
    private static final Logger LOG = Logger.getLogger(RegistryUserBeanLocal.class);
    private static final int INDEX_USER_ID = 0;
    private static final int INDEX_FIRST_NAME = 1;
    private static final int INDEX_LAST_NAME = 2;
    private static final int INDEX_EMAIL = 3;
    private static final int INDEX_TRIAL_ID = 4;
    private static final int INDEX_NCI_IDENTIFIER = 5;
    private static final int INDEX_ORG_ID = 6;
    private static final int INDEX_LEAD_ID = 7;
    private static final int STRING_SIZE = 2500;
    private static final String SEARCH_USER_BY_EMAIL_QUERY = "select ru from RegistryUser as ru "
            + "join fetch ru.csmUser as csmu where ru.emailAddress = :emailAddress";
    
    @EJB
    private MailManagerServiceLocal mailManagerService;
    
    private CSMUserUtil csmUtil = new CSMUserService();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser createUser(RegistryUser user) throws PAException {
        PaHibernateUtil.getCurrentSession().saveOrUpdate(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        PaHibernateUtil.getCurrentSession().update(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {
        RegistryUser myUser = getUser(loginName);
        return hasTrialAccess(myUser, studyProtocolId);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    @Override
    public boolean isTrialOwner(Long userId, Long studyProtocolId)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("select * from study_owner where study_id="
                        + studyProtocolId + " and user_id=" + userId); // NOPMD
        List<Boolean> results = query.list();
        return !results.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(RegistryUser user, Long studyProtocolId) throws PAException {
        if (user != null) {
            StudyProtocol studyProtocol =
                (StudyProtocol) PaHibernateUtil.getCurrentSession().get(StudyProtocol.class, studyProtocolId);
    
            // first check that the user isn't already a trial owner
            if (studyProtocol.getStudyOwners().contains(user)) {
                return true;
            }
    
            // check that the user is an admin of something in at all
            if (!UserOrgType.ADMIN.equals(user.getAffiliatedOrgUserType())) {
                return false;
            }
    
            // second check that the user isn't the lead org admin. The first
            // study site will be the lead org if
            // it exists.
            StudySite leadOrg = studyProtocol.getStudySites().isEmpty() ? null
                    : studyProtocol.getStudySites().iterator().next();
            if (leadOrg != null
                    && StringUtils.equals(leadOrg.getResearchOrganization()
                            .getOrganization().getIdentifier(), user
                            .getAffiliatedOrganizationId().toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RegistryUser getUser(String loginName) throws PAException {
        RegistryUser registryUser = null;
        try {
            User csmUser = CSMUserService.getInstance().getCSMUser(loginName);
            if (csmUser != null) {
                Session session = PaHibernateUtil.getCurrentSession();
                String hql = "select ru from RegistryUser ru join ru.csmUser cu where cu.id = :csmuser";
                Query query = session.createQuery(hql);
                query.setParameter("csmuser", csmUser.getUserId());
                registryUser = (RegistryUser) query.uniqueResult();
            }
        } catch (Exception cse) {
            throw new PAException("CSM exception while retrieving user: " + loginName, cse);
        }
        return registryUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUserById(Long userId) throws PAException {
        RegistryUser registryUser = null;
        if (userId != null) {
            try {
                Session session = PaHibernateUtil.getCurrentSession();
                registryUser = (RegistryUser) session.get(RegistryUser.class, userId);
            } catch (Exception e) {
                throw new PAException("CSM exception while retrieving  user with id: " + userId, e);
            }
        }
        return registryUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException {
        if (userType == null) {
            throw new PAException("UserOrgType cannot be null.");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, REG_USER)
            .add(Property.forName("regUser.affiliatedOrganizationId").isNotNull())
            .add(Restrictions.eq("regUser.affiliatedOrgUserType", userType));

        List<RegistryUser> registryUserList = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        for (RegistryUser usr : registryUserList) {
            PAServiceUtils servUtil = new PAServiceUtils();
            usr.setAffiliateOrg(servUtil.getOrgName(IiConverter.convertToPoOrganizationIi(
                    String.valueOf(usr.getAffiliatedOrganizationId()))));
        }
        return registryUserList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doesRegistryUserExist(String loginName) {
        RegistryUser registryUser = null;
        try {
            registryUser = getUser(CsmUserUtil.getGridIdentityUsername(
                    StringUtils.defaultString(loginName)).toLowerCase());
        } catch (PAException e) {
            LOG.error("Error retrieving user.", e);
        }
        return registryUser != null;
    }

    /**
     *
     * @param regUser user
     * @return list of user
     * @throws PAException on error
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, REG_USER);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (regUser != null) {
            if (regUser.getAffiliatedOrgUserType() != null
                    && StringUtils.isNotEmpty(regUser.getAffiliatedOrgUserType().getCode())) {
                criteria.add(Restrictions.eq("regUser.affiliatedOrgUserType", regUser.getAffiliatedOrgUserType()));
            }
            addCriteria(criteria, regUser.getAffiliatedOrganizationId(), "regUser.affiliatedOrganizationId");
            addCriteria(criteria, regUser.getPoOrganizationId(), "regUser.poOrganizationId");
            addCriteria(criteria, regUser.getPoPersonId(), "regUser.poPersonId");
            if (regUser.getCsmUser() != null) {
                addCriteria(criteria, regUser.getCsmUser().getUserId(), "regUser.csmUser.id");
            }
            addCriteria(criteria, regUser.getEmailAddress(), "regUser.emailAddress");
            addCriteria(criteria, regUser.getPrsOrgName(), "regUser.prsOrgName");
            addCriteria(criteria, regUser.getFirstName(), "regUser.firstName");
            addCriteria(criteria, regUser.getLastName(), "regUser.lastName");
            addCriteria(criteria, regUser.getToken(), "regUser.token");
        }
        return criteria.list();
    }

    /**
     * @param trialOwnershipInfo the criteria object.
     * @param siblings the affiliated org id and siblings.
     * @return list of trial ownership information objects.
     * @throws PAException on error.
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public List<DisplayTrialOwnershipInformation> searchTrialOwnership(DisplayTrialOwnershipInformation // NOPMD
            trialOwnershipInfo, List<Long> siblings) throws PAException {
        List<DisplayTrialOwnershipInformation> lst = new ArrayList<DisplayTrialOwnershipInformation>();
        // added below if for PO-9225
        if (siblings == null || siblings.isEmpty()) {
            return lst;
        }
        StringBuffer hql = new StringBuffer(STRING_SIZE);
        hql.append("select sowner.id, sowner.firstName, sowner.lastName, sowner.emailAddress, ")
            .append("sp.id, otherid.extension, sowner.affiliatedOrganizationId, sps.localStudyProtocolIdentifier ")
            .append("from StudyProtocol as sp left outer join sp.documentWorkflowStatuses as dws ")
            .append("left outer join sp.studySites as sps ")
            .append("left outer join sps.researchOrganization as ro left outer join ro.organization as org ")
            .append("left outer join sp.studyOwners as sowner left outer join sp.otherIdentifiers otherid where ")
            .append("(( sps.functionalCode = '").append(StudySiteFunctionalCode.LEAD_ORGANIZATION)
            .append("' and cast(org.identifier as long) in (:siblings)) or org.identifier is NULL) ")
            .append("and dws.statusCode  <> '")
            .append(DocumentWorkflowStatusCode.REJECTED)
            .append("' and (dws.id in (select max(id) from DocumentWorkflowStatus as dws1 ")
            .append("where sp.id=dws1.studyProtocol) or dws.id is null) and sps.functionalCode = '")
            .append(StudySiteFunctionalCode.LEAD_ORGANIZATION)
            .append("' and otherid.root = '")
            .append(IiConverter.STUDY_PROTOCOL_ROOT)
            .append("' and sowner.id IS NOT NULL and cast(sowner.affiliatedOrganizationId as long) in (:siblings) ");

        String criteriaClause = getTrialOwnershipInformationSearchCriteria(trialOwnershipInfo);
        if (StringUtils.isNotEmpty(criteriaClause)) {
            hql.append(criteriaClause);
        }

        Map<Long, List<Long>> emailEnabledTrialOwnershipMap = getEmailEnabledTrialOwnershipMap();
        
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql.toString());
        query.setParameterList("siblings", siblings);
        for (Iterator<Object[]> iter = query.iterate(); iter.hasNext();) {
            Object[] row = iter.next();
            DisplayTrialOwnershipInformation trialInfo = new DisplayTrialOwnershipInformation();
            trialInfo.setUserId(ObjectUtils.toString(row[INDEX_USER_ID]));
            trialInfo.setFirstName(ObjectUtils.toString(row[INDEX_FIRST_NAME]));
            trialInfo.setLastName(ObjectUtils.toString(row[INDEX_LAST_NAME]));
            trialInfo.setEmailAddress(ObjectUtils.toString(row[INDEX_EMAIL]));
            trialInfo.setTrialId(ObjectUtils.toString(row[INDEX_TRIAL_ID]));
            trialInfo.setNciIdentifier(ObjectUtils.toString(row[INDEX_NCI_IDENTIFIER]));
            trialInfo.setAffiliatedOrgId(ObjectUtils.toString(row[INDEX_ORG_ID]));
            trialInfo.setLeadOrgId(ObjectUtils.toString(row[INDEX_LEAD_ID]));
            trialInfo.setEmailsEnabled(emailEnabledTrialOwnershipMap
                    .get(row[INDEX_USER_ID]) != null
                    && emailEnabledTrialOwnershipMap.get(row[INDEX_USER_ID])
                            .contains(row[INDEX_TRIAL_ID]));
            lst.add(trialInfo);
        }

        return lst;
    }

    /**
     * @param siblings the participating site id and siblings
     * @return list of trial ownership information objects.
     * @throws PAException on error.
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public List<DisplayTrialOwnershipInformation> searchSiteRecordOwnership(
             List<Long> siblings) throws PAException {
        List<DisplayTrialOwnershipInformation> lst = new ArrayList<DisplayTrialOwnershipInformation>();
        StringBuffer hql = new StringBuffer(STRING_SIZE);
        hql.append("select sowner.id, sowner.firstName, sowner.lastName, sowner.emailAddress, ")
                .append("sp.id, otherid.extension, sowner.affiliatedOrganizationId, sps.localStudyProtocolIdentifier ")
                .append("from StudyProtocol as sp left outer join sp.documentWorkflowStatuses as dws ")
                .append("join sp.studySites as sps ")
                .append("join sps.healthCareFacility as hcf left join hcf.organization as org ")
                .append("join sps.studySiteOwners as sowner ")
                .append("left outer join sp.otherIdentifiers otherid where ")
                .append("sps.functionalCode = '").append(StudySiteFunctionalCode.TREATING_SITE).append("' ")
                .append("and cast(org.identifier as long) in (:siblings) ")
                .append("and dws.statusCode  <> '")
                .append(DocumentWorkflowStatusCode.REJECTED).append("' ")
                .append("and (dws.id in (select max(id) from DocumentWorkflowStatus ")
                .append("as dws1 where sp.id=dws1.studyProtocol) or dws.id is null) ")
                .append("and sps.functionalCode = '")
                .append(StudySiteFunctionalCode.TREATING_SITE).append("' ")
                .append("and otherid.root = '").append(IiConverter.STUDY_PROTOCOL_ROOT)
                .append("' and sps.statusCode <> 'NULLIFIED' ")
                .append("and cast(sowner.affiliatedOrganizationId as long) in (:siblings)");

                
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql.toString());
        query.setParameterList("siblings", siblings);
        
        for (Iterator<Object[]> iter = query.iterate(); iter.hasNext();) {
            Object[] row = iter.next();
            DisplayTrialOwnershipInformation trialInfo = new DisplayTrialOwnershipInformation();
            trialInfo.setUserId(ObjectUtils.toString(row[INDEX_USER_ID]));
            trialInfo.setFirstName(ObjectUtils.toString(row[INDEX_FIRST_NAME]));
            trialInfo.setLastName(ObjectUtils.toString(row[INDEX_LAST_NAME]));
            trialInfo.setEmailAddress(ObjectUtils.toString(row[INDEX_EMAIL]));
            trialInfo.setTrialId(ObjectUtils.toString(row[INDEX_TRIAL_ID]));
            trialInfo.setNciIdentifier(ObjectUtils.toString(row[INDEX_NCI_IDENTIFIER]));
            trialInfo.setAffiliatedOrgId(ObjectUtils.toString(row[INDEX_ORG_ID]));
            trialInfo.setLeadOrgId(ObjectUtils.toString(row[INDEX_LEAD_ID]));
            lst.add(trialInfo);
        }
        return lst;
    }
    
    @SuppressWarnings(UNCHECKED)
    private Map<Long, List<Long>> getEmailEnabledTrialOwnershipMap()
            throws PAException {
        Map<Long, List<Long>> map = new HashMap<Long, List<Long>>();
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            SQLQuery query = session
                    .createSQLQuery("select study_id, user_id from study_owner where enable_emails=true");
            List<Object[]> list = query.list();
            for (Object[] row : list) {
                Long studyId = ((BigInteger) row[0]).longValue();
                Long userId = ((BigInteger) row[1]).longValue();
                List<Long> studyList = map.get(userId);
                if (studyList == null) {
                    studyList = new ArrayList<Long>();
                    map.put(userId, studyList);
                }
                studyList.add(studyId);
            }
        } catch (Exception cse) {
            throw new PAException(cse);
        }
        return map;
    }

    private String getTrialOwnershipInformationSearchCriteria(DisplayTrialOwnershipInformation criteria) {
        StringBuffer criteriaClause = new StringBuffer();
        criteriaClause.append(addCriteria("sowner.firstName", criteria.getFirstName()))
            .append(addCriteria("sowner.lastName", criteria.getLastName()))
            .append(addCriteria("sowner.emailAddress", criteria.getEmailAddress()))
            .append(addCriteria("sp.otherIdentifiers.extension", criteria.getNciIdentifier()));
        return criteriaClause.toString();
    }

    private void addCriteria(Criteria criteria, Long id, String criteriaName) {
        if (id != null) {
            criteria.add(Restrictions.eq(criteriaName, id));
        }
    }

    private void addCriteria(Criteria criteria, String criteriaValue, String criteriaName) {
        if (StringUtils.isNotEmpty(criteriaValue)) {
            criteria.add(Restrictions.ilike(criteriaName, criteriaValue + "%"));
        }
    }

    private String addCriteria(String criteriaName, String criteriaValue) {
        StringBuffer retVal = new StringBuffer();
        if (StringUtils.isNotEmpty(criteriaValue)) {
            retVal.append(" and (lower(").append(criteriaName).append(") like lower('%").append(criteriaValue)
                .append("%')) ");
        }
        return retVal.toString();
    }

    /**
     * Assign ownership of given protocol to given user.
     * @param userId user id
     * @param studyProtocolId study protocol id
     * @throws PAException on error
     */
    @Override
    public void assignOwnership(Long userId, Long studyProtocolId)
            throws PAException {
        if (!isTrialOwner(userId, studyProtocolId)) {
            try {
                PaHibernateUtil
                        .getCurrentSession()
                        .createSQLQuery(
                                "insert into study_owner(study_id,user_id,enable_emails) values("
                                        + studyProtocolId + "," + userId
                                        + ", true)").executeUpdate();
            } catch (Exception cse) {
                throw new PAException(cse);
            }
        }
        PaHibernateUtil.getCurrentSession().flush();
    }
    
    @Override
    public void assignOwnership(List<Long> userId, final Set<Long> studyProtocolId)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("select study_id from study_owner where study_id in (:study) "
                        + "and user_id = :user"); // NOPMD
        SQLQuery update = session.createSQLQuery("insert into study_owner(study_id,user_id,enable_emails) "
                + "values(:study, :user, true)");
        query.setParameterList("study", studyProtocolId);
        try {
            for (Long user : userId) {
                Set<Long> study = new HashSet<Long>(studyProtocolId);
                update.setParameter("user", user);
                query.setParameter("user", user);
                
                //remove all the studies the user already has before running the update
                for (Object obj : query.list()) {
                    study.remove(((BigInteger) obj).longValue());
                }
             
                
                for (Long studyId : study) {
                    update.setParameter("study", studyId).executeUpdate();
                }
            }
        } catch (Exception cse) {
            throw new PAException(cse);
        }
        PaHibernateUtil.getCurrentSession().flush();
    }

    /**
     * remove ownership .
     * @param userId user id
     * @param studyProtocolId study protocol id
     * @throws PAException on error
     */
    @Override
    public void removeOwnership(Long userId, Long studyProtocolId)
            throws PAException {
        try {
            PaHibernateUtil
                    .getCurrentSession()
                    .createSQLQuery(
                            "delete from study_owner where study_id="
                                    + studyProtocolId + " and user_id="
                                    + userId).executeUpdate();
        } catch (Exception cse) {
            throw new PAException(cse);
        }
        PaHibernateUtil.getCurrentSession().flush();
    }
    
    @Override
    public void removeOwnership(List<Long> userId, Set<Long> studyProtocolId)
            throws PAException {
        try {
            PaHibernateUtil
                    .getCurrentSession()
                    .createSQLQuery(
                            "delete from study_owner where study_id in (:study)"
                                    + " and user_id in (:user)")
                                    .setParameterList("study", studyProtocolId)
                                    .setParameterList("user", userId)
                                    .executeUpdate();
        } catch (Exception cse) {
            throw new PAException(cse);
        }
        PaHibernateUtil.getCurrentSession().flush();
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void activateAccount(String token, String loginName) throws PAException {
        if (StringUtils.isBlank(token)) {
            throw new PAException("Cannot activate account with an empty token");
        }

        Session session = PaHibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class);
        criteria.add(Restrictions.eq("token", token));
        criteria.add(Restrictions.isNull("csmUser"));

        RegistryUser regUser = null;
        try {
            regUser = (RegistryUser) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new PAException("Multiple registry accounts for the same token were found.", e);
        }
        if (regUser == null) {
            throw new PAException("Unable to find user with token " + token
                    + " .Unable to activate account.");
        }
        
        User csmUser = CSMUserService.getInstance().getCSMUser(loginName);
        if (csmUser == null) {
            csmUser = csmUtil.createCSMUser(regUser, loginName, null);
        }
        if (!csmUtil.isUserInGroup(loginName,
                PaEarPropertyReader.getCSMSubmitterGroup())) {
            csmUtil.assignUserToGroup(loginName,
                    PaEarPropertyReader.getCSMSubmitterGroup());
        }
        regUser.setCsmUser(csmUser);
        regUser.setToken(null);
        session.update(regUser);
        mailManagerService.sendAccountActivationEmail(regUser);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTrialOwnerNames(Long studyProtocolId) throws PAException {
        List<String> names = new ArrayList<String>();
        StudyProtocol studyProtocol =
            (StudyProtocol) PaHibernateUtil.getCurrentSession().get(StudyProtocol.class, studyProtocolId);

        for (RegistryUser myUser : studyProtocol.getStudyOwners()) {
            names.add(myUser.getFirstName() + " " + myUser.getLastName());
        }

        return names;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<RegistryUser> getAllTrialOwners(Long studyProtocolId) throws PAException {
        return ((StudyProtocol) PaHibernateUtil.getCurrentSession()
                .get(StudyProtocol.class, studyProtocolId)).getStudyOwners();
    }

    // CHECKSTYLE:OFF
    /**
     * Gets the login names of registry users having the given e-mail address.
     * 
     * TO DO: The name of the method is inconsistent with its semantics.
     * 
     * @param emailAddress The e-mail address
     * @return The login names of registry users having the given e-mail address
     */
    //CHECKSTYLE:ON
    @Override
    @SuppressWarnings(UNCHECKED)
    public List<RegistryUser> getLoginNamesByEmailAddress(String emailAddress) {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(SEARCH_USER_BY_EMAIL_QUERY);
        query.setString("emailAddress", emailAddress);
        return query.list();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#assignSiteOwnership(java.lang.Long, java.lang.Long)
     */
    @Override
    public void assignSiteOwnership(Long userId, Long studySiteId)
            throws PAException {
        RegistryUser usr = getUserById(userId);
        Set<StudySite> studySites = usr.getStudySites();
        for (Iterator<StudySite> iter = studySites.iterator(); iter.hasNext();) {
            StudySite site = iter.next();
            if (site.getId().equals(studySiteId)) {
                return;
            }
        }        
        StudySite site = new StudySite();
        site.setId(studySiteId);
        usr.getStudySites().add(site);
        PaHibernateUtil.getCurrentSession().update(usr);
        PaHibernateUtil.getCurrentSession().flush();
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#assignSiteOwnership(java.lang.Long, java.lang.Long)
     */
    @Override
    public void assignSiteOwnership(List<Long> userId, Set<Long> newSiteId)
            throws PAException {
        for (Long id : userId) {
            RegistryUser usr = getUserById(id);
            
            Set<Long> addSites = new HashSet<Long>(newSiteId);
            Set<StudySite> studySites = usr.getStudySites();
            for (StudySite site : studySites) {
                addSites.remove(site.getId());
            }
            
            for (Long studySiteId : addSites) {
                StudySite site = new StudySite();
                site.setId(studySiteId);
                studySites.add(site);
            }
            PaHibernateUtil.getCurrentSession().update(usr);
        }
        PaHibernateUtil.getCurrentSession().flush();
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#removeSiteOwnership(java.lang.Long, java.lang.Long)
     */
    @Override
    public void removeSiteOwnership(List<Long> userId, Set<Long> studySiteId)
            throws PAException {
        
        for (Long id : userId) {
            RegistryUser usr = getUserById(id);
            Set<StudySite> studySites = usr.getStudySites();
            Iterator<StudySite> sites = studySites.iterator();
            while (sites.hasNext()) {
                final StudySite site = sites.next();
                if (studySiteId.contains(site.getId())) {
                    sites.remove();
                }
            }

            PaHibernateUtil.getCurrentSession().saveOrUpdate(usr);
        }
        PaHibernateUtil.getCurrentSession().flush();
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#removeSiteOwnership(java.lang.Long, java.lang.Long)
     */
    @Override
    public void removeSiteOwnership(Long userId, Long studySiteId)
            throws PAException {      
        RegistryUser usr = getUserById(userId);
        Set<StudySite> studySites = usr.getStudySites();
        for (Iterator<StudySite> iter = studySites.iterator(); iter.hasNext();) {
            StudySite site = iter.next();
            if (site.getId().equals(studySiteId)) {
                 iter.remove();
            }
        }
        PaHibernateUtil.getCurrentSession().saveOrUpdate(usr);
        PaHibernateUtil.getCurrentSession().flush();
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#getUserId(java.lang.String)
     */
    @Override
    public Long getUserId(String loginName) throws PAException {        
        Long id = null;
        try {
            User csmUser = CSMUserService.getInstance().getCSMUser(loginName);
            if (csmUser != null) {
                Session session = PaHibernateUtil.getCurrentSession();
                String hql = "select ru.id from RegistryUser ru join ru.csmUser cu where cu.id = :csmuser";
                Query query = session.createQuery(hql);
                query.setParameter("csmuser", csmUser.getUserId());
                id = (Long) query.uniqueResult();
            }
        } catch (Exception cse) {
            throw new PAException("CSM exception while retrieving user: " + loginName, cse);
        }
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.RegistryUserService#getPartialUserById(java
     * .lang.Long)
     */    
    @Override
    public RegistryUser getPartialUserById(Long userId) throws PAException {
        RegistryUser registryUser = null;
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            Criteria criteria = session.createCriteria(RegistryUser.class);
            criteria.add(Restrictions.eq("id", userId)).setMaxResults(1);
            registryUser = (RegistryUser) criteria.uniqueResult();
            if (registryUser != null) {
                session.evict(registryUser);
            }
        } catch (Exception cse) {
            throw new PAException(cse);
        }
        return registryUser;
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean isEmailNotificationsEnabled(Long userId, Long trialId)
            throws PAException {
        boolean trialLevel = isEmailNotificationsEnabledOnTrialLevel(userId,
                trialId);
        boolean globalLevel = isEmailNotificationsEnabledOnGlobalLevel(userId);
        return trialLevel && globalLevel;
    }


    @SuppressWarnings(UNCHECKED)
    private boolean isEmailNotificationsEnabledOnGlobalLevel(Long userId) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("select enable_emails from registry_user where identifier="
                        + userId);
        List<Boolean> results = query.list();
        if (!results.isEmpty()) {
            final Boolean val = results.get(0);
            return val != null ? val : true;
        } else {
            return true;
        }
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean isEmailNotificationsEnabledOnTrialLevel(Long userId,
            Long trialId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("select enable_emails from study_owner where study_id="
                        + trialId + " and user_id=" + userId);
        List<Boolean> results = query.list();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return true;
        }
    }

    @Override
    public void setEmailNotificationsPreference(Long userId, Long trialId,
            boolean enableEmails) throws PAException {
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            SQLQuery query = session
                    .createSQLQuery("update study_owner set enable_emails="
                            + enableEmails + " where study_id=" + trialId
                            + " and user_id=" + userId);
            if (query.executeUpdate() < 1) {
                throw new PAException("Missing study_owner record.");
            }
        } catch (Exception cse) {
            throw new PAException(cse);
        }
    }

    @Override
    public List<RegistryUser> findByAffiliatedOrg(Long orgId)
            throws PAException {
        return findByAffiliatedOrgs(Arrays.asList(new Long[] {orgId}));
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public List<RegistryUser> findByAffiliatedOrgs(Collection<Long> orgIds) throws PAException {
        if (CollectionUtils.isEmpty(orgIds)) {
            return new ArrayList<RegistryUser>();
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, REG_USER);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.in("affiliatedOrganizationId", orgIds));
        criteria.add(Restrictions.isNotNull("regUser.csmUser"));
        return criteria.list();
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public List<RegistryUser> searchByCsmUsers(Collection<User> users)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, REG_USER);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);     
        criteria.add(Restrictions.in("csmUser", users));
        criteria.setFetchMode("csmUser", FetchMode.JOIN);        
        return criteria.list();
    }

    @Override
    public void changeUserOrgType(Long userID, UserOrgType userOrgType,
            String rejectReason) throws PAException {
        RegistryUser pendingUsr = getUserById(userID);
        UserOrgType previousOrgType = pendingUsr.getAffiliatedOrgUserType();
        pendingUsr.setAffiliatedOrgUserType(userOrgType);
        updateUser(pendingUsr);
        
        if (userOrgType.equals(UserOrgType.ADMIN)
                && UserOrgType.PENDING_ADMIN.equals(previousOrgType)) {
            mailManagerService.sendAdminAcceptanceEmail(pendingUsr.getId());
        }
        if (!(userOrgType.equals(UserOrgType.ADMIN))
                && UserOrgType.PENDING_ADMIN.equals(previousOrgType)) {
            mailManagerService.sendAdminRejectionEmail(pendingUsr.getId(),
                    StringUtils.defaultString(rejectReason));
        }
    }

    @Override
    public List<StudyProtocol> getTrialsByParticipatingSite(Long participatingSiteId) throws PAException {
        StringBuffer hql = new StringBuffer(STRING_SIZE);
        hql.append("select sp from StudyProtocol as sp "
                + "join sp.studySites as sps "
                + "left outer join sp.documentWorkflowStatuses as dws "
                + "left outer join sp.otherIdentifiers otherid "
                + "where sps.statusCode <> 'NULLIFIED' and "
                + "sps.healthCareFacility.organization.identifier = '")
                .append(participatingSiteId.toString())
                .append("' and sps.functionalCode='")
                .append(StudySiteFunctionalCode.TREATING_SITE).append("'")
                .append(" and sp.proprietaryTrialIndicator= true")
                .append(" and dws.statusCode  <> '").append(DocumentWorkflowStatusCode.REJECTED).append("' ")
                .append("and (dws.id in (select max(id) from DocumentWorkflowStatus "
                        + "as dws1 where sp.id=dws1.studyProtocol) or dws.id is null) ")
                .append("and otherid.root = '").append(IiConverter.STUDY_PROTOCOL_ROOT).append("' ");
        
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql.toString());
        return  query.list();
    }
    
    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * Remove all Site and trial ownership from a user that belong to a family.
     * @param registryUser The user whose ownership to remove.
     * @param list The family of Orgs to remove ownership from.
     * @throws PAException the exception if any.
     */
    public void removeAllOwnership(RegistryUser registryUser, List<Long> list) throws PAException {
        Iterator<StudyProtocol> spItter = registryUser.getStudyProtocols().iterator();
        Set<Long> family = new HashSet<Long>(list);
        
        while (spItter.hasNext()) {
            final StudyProtocol sp = spItter.next();
            if (sp.getSubmitingOrganization() != null
                    && family.contains(sp.getSubmitingOrganization().getId())) {
                spItter.remove();
            }
        }
        
        Iterator<StudySite> ssItter = registryUser.getStudySites().iterator();
        while (ssItter.hasNext()) {
            final StudySite ss = ssItter.next();
            if (family.contains(ss.getHealthCareFacility().getOrganization().getId())) {
               ssItter.remove(); 
            }
        }
    }

    /**
     * @param csmUtil the csmUtil to set
     */
    public void setCsmUtil(CSMUserUtil csmUtil) {
        this.csmUtil = csmUtil;
    }
    

}
