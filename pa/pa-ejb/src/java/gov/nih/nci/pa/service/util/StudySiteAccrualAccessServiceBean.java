/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/

package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyAccrualAccess;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentByTrialDTO;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentHistoryDTO;
import gov.nih.nci.pa.dto.AccrualSubmissionAccessDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyAccrualAccessConverter;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualAccessConverter;
import gov.nih.nci.pa.iso.dto.StudyAccrualAccessDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.AbstractBaseIsoService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * @author Hugh Reinhart
 * @since Sep 2, 2009
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudySiteAccrualAccessServiceBean // NOPMD
    extends AbstractBaseIsoService<StudySiteAccrualAccessDTO, StudySiteAccrualAccess, StudySiteAccrualAccessConverter>
    implements StudySiteAccrualAccessServiceLocal { // NOPMD

    private static final String ACTION_CODE = "actionCode";
    private static final String STATUS_CODE = "statusCode";
    private static final int MAX_HISTORY_RECORDS = 5000;
    private static final String DEPRECATION = "deprecation";
    private static final String USER_ID = "userId";
    private static final String SP_ID = "spId";
    private static final String UNCHECKED_STR = "unchecked";
    private static final long REFRESH_TIME = 1000 * 60 * 10;  // 10 minutes
    private static Set<User> submitterList = null;
    private static Timestamp lastUpdate = null;

    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private ParticipatingOrgServiceLocal participatingOrgServiceLocal;
    
    private static final Logger LOG = Logger.getLogger(StudySiteAccrualAccessServiceBean.class);

    /** Cache for industrial trial flag. */
    private static CacheManager cacheManager;
    private static final String IND_TRIAL_CACHE_KEY = "IND_TRIAL_CACHE_KEY";
    private static final int CACHE_MAX_ELEMENTS = 50;
    private static final long CACHE_TIME = 43200;

    private static Cache getIndTrialCache() {
        if (cacheManager == null || cacheManager.getStatus() != Status.STATUS_ALIVE) {
            cacheManager = CacheManager.create();
            Cache cache = new Cache(IND_TRIAL_CACHE_KEY, CACHE_MAX_ELEMENTS, null, false, null, false,
                CACHE_TIME, CACHE_TIME, false, CACHE_TIME, null, null, 0);
            cacheManager.removeCache(IND_TRIAL_CACHE_KEY);
            cacheManager.addCache(cache);
        }
        return cacheManager.getCache(IND_TRIAL_CACHE_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<User> getSubmitters() throws PAException {
        if (lastUpdate == null
                || lastUpdate.getTime() + REFRESH_TIME < new Timestamp(new Date().getTime()).getTime()) {
            submitterList = CSMUserService.getInstance().getCSMUsers();
        }
        return submitterList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, String> getTreatingSites(Long studyProtocolId) throws PAException {
        Map<Long, Organization> orgMap = getTreatingOrganizations(studyProtocolId);
        Map<Long, String> result = new LinkedHashMap<Long, String>();
        for (Map.Entry<Long, Organization> org : orgMap.entrySet()) {
            result.put(org.getKey(), org.getValue().getName());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED_STR)
    public Map<Long, Organization> getTreatingOrganizations(Long studyProtocolId) throws PAException {
        Session session = null;
        List<Object[]> queryList = null;
        session = PaHibernateUtil.getCurrentSession();
        Query query = null;
        String hql = "select ss.id, org, ssas from StudyProtocol sp join sp.studySites ss"
            + " join ss.studySiteAccrualStatuses ssas "
            + " join ss.healthCareFacility hcf join hcf.organization org "
            + " where sp.id = :spId "
            + " and ss.functionalCode = '" + StudySiteFunctionalCode.TREATING_SITE.getName() + "' "
            + " and ssas.id = (select max(id) "
            + "    from StudySiteAccrualStatus where studySite.id = ss.id"
            + "     and deleted=false"
            + "    and statusDate = (select max(statusDate) "
            + "        from StudySiteAccrualStatus where studySite.id =ss.id"
            + "         and deleted=false)) "
            + " order by org.name, ss.id ";
        query = session.createQuery(hql);
        query.setParameter(SP_ID, studyProtocolId);
        queryList = query.list();

        Map<Long, Organization> siteIdToOrgMap = new LinkedHashMap<Long, Organization>();
        Map<Long, StudySiteAccrualStatus> accrualStatusMap = new LinkedHashMap<Long, StudySiteAccrualStatus>();
        for (Object[] oArr : queryList) {
            siteIdToOrgMap.put((Long) oArr[0], (Organization) oArr[1]);
            accrualStatusMap.put((Long) oArr[0], (StudySiteAccrualStatus) oArr[2]);
        }
        
        Map<Long, Organization> result = new LinkedHashMap<Long, Organization>();
        for (Entry<Long, StudySiteAccrualStatus> site : accrualStatusMap.entrySet()) {         
            if (site.getValue() != null && site.getValue().getStatusCode().isEligibleForAccrual()) {
                result.put(site.getKey(), siteIdToOrgMap.get(site.getKey()));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessDTO create(StudySiteAccrualAccessDTO dto) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Id is not null when calling StudySiteAccrualAccess.create().");
        }
        validateElibibleForCreate(dto);
        validateValues(dto);
        validate(dto);
        return super.create(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySiteAccrualAccessDTO> getByStudyProtocol(Long studyProtocolId) throws PAException {
        List<StudySiteAccrualAccess> ssaaList = getBosByStudyProtocol(studyProtocolId);
        return convertFromDomainToDTOs(ssaaList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySiteAccrualAccessDTO> getByStudySite(Long studySiteId) throws PAException {
        return convertFromDomainToDTOs(getBosByStudySite(studySiteId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessDTO getByStudySiteAndUser(Long studySiteId,
            Long registryUserId) throws PAException {
        final StudySiteAccrualAccess bo = getBosByStudySiteAndUser(studySiteId,
                registryUserId);
        return bo != null ? convertFromDomainToDto(bo) : null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySiteAccrualAccessDTO> getActiveByUser(Long registryUserId) throws PAException {
        return  convertFromDomainToDTOs(getActiveBosByUser(registryUserId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessDTO update(StudySiteAccrualAccessDTO dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Id is null when calling StudySiteAccrualAccess.update().");
        }
        validateValues(dto);
        validate(dto);
        return super.update(dto);
    }

    @SuppressWarnings(UNCHECKED_STR)
    private List<StudySiteAccrualAccess> getBosByStudyProtocol(Long studyProtocolId) throws PAException {
        Session session = null;
        List<StudySiteAccrualAccess> queryList = null;
        session = PaHibernateUtil.getCurrentSession();
        Query query = null;
        String hql = "select ssaa "
            + "from StudyProtocol sp "
            + "join sp.studySites ss "
            + "join ss.studySiteAccrualAccess ssaa "
            + "where sp.id = :spId "
            + "order by ss.id, ssaa.id ";
        query = session.createQuery(hql);
        query.setParameter(SP_ID, studyProtocolId);
        queryList = query.list();
        return queryList;
    }

    @SuppressWarnings(UNCHECKED_STR)
    private List<StudySiteAccrualAccess> getBosByStudySite(Long studySiteId) throws PAException {
        Session session = null;
        List<StudySiteAccrualAccess> queryList = null;
        session = PaHibernateUtil.getCurrentSession();
        Query query = null;
        String hql = "select ssaa from StudySite ss join ss.studySiteAccrualAccess ssaa where ss.id = :ssId "
            + "order by ss.id, ssaa.id ";
        query = session.createQuery(hql);
        query.setParameter("ssId", studySiteId);
        queryList = query.list();
        return queryList;
    }
    
    @SuppressWarnings(UNCHECKED_STR)
    private StudySiteAccrualAccess getBosByStudySiteAndUser(Long studySiteId,
            Long registryUserId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select ssaa from StudySite ss join ss.studySiteAccrualAccess ssaa where ss.id = :ssId "
                + "and ssaa.registryUser.id = :userId "
                + "order by ss.id, ssaa.id ";
        Query query = session.createQuery(hql);
        query.setParameter("ssId", studySiteId);
        query.setParameter(USER_ID, registryUserId);
        List<StudySiteAccrualAccess> queryList = query.list();
        return queryList.isEmpty() ? null : queryList.get(0);
    }

    @SuppressWarnings(UNCHECKED_STR)
    private List<StudySiteAccrualAccess> getActiveBosByUser(Long registryUserId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select ssaa from StudySite ss join ss.studySiteAccrualAccess ssaa "
                + "where ssaa.registryUser.id = :userId "
                + "  and ssaa.statusCode = :statusCode "
                + "order by ss.id, ssaa.id ";
        Query query = session.createQuery(hql);
        query.setParameter(USER_ID, registryUserId);
        query.setParameter(STATUS_CODE, ActiveInactiveCode.ACTIVE);
        List<StudySiteAccrualAccess> queryList = query.list();
        return queryList;
    }

    private void validateElibibleForCreate(StudySiteAccrualAccessDTO access) throws PAException {
        List<StudySiteAccrualAccess> aList =
            getBosByStudySite(IiConverter.convertToLong(access.getStudySiteIdentifier()));
        for (StudySiteAccrualAccess a : aList) {
            if (a.getStudySite().getId().equals(IiConverter.convertToLong(access.getStudySiteIdentifier()))
                    && a.getRegistryUser().getId().equals(
                            IiConverter.convertToLong(access.getRegistryUserIdentifier()))) {
                throw new PAException("User has already been assigned to the study site.  "
                        + "To update click the edit link in existing accrual users list.");
            }
        }
    }

    private void validateValues(StudySiteAccrualAccessDTO dto) throws PAException {
        checkNull(dto.getRegistryUserIdentifier(), "User Name must be set.");
        checkNull(dto.getStudySiteIdentifier(), "Accessing Site must be set.");
        checkNull(dto.getStatusCode(), "Access Status must be set.");
        checkNull(dto.getSource(), "Access Source must be set.");
        dto.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
    }

    private static void checkNull(Object obj, String errMsg) throws PAException {
        if (obj == null) {
            throw new PAException(errMsg);
        }
    }

    /**
     * @param user The registry user object
     * @return Name of the user formated last, first.  If no name is entered use email add.
     */
    public static String getFullName(RegistryUser user) {
        String fullName = null;
        if (user.getLastName() != null) {
            fullName = user.getLastName();
        }
        if (user.getFirstName() != null) {
            fullName = user.getLastName() + ", " + user.getFirstName();
        }
        if (StringUtils.isEmpty(fullName)) {
            fullName = user.getEmailAddress();
        }
        return fullName;
    }

    /**
     * @return the studySiteAccrualStatusService
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @return the registryUserService
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @return the submitterList
     */
    public static Set<User> getSubmitterList() {
        return submitterList;
    }

    /**
     * @param submitterList the submitterList to set
     */
    public static void setSubmitterList(Set<User> submitterList) {
        StudySiteAccrualAccessServiceBean.submitterList = submitterList;
    }

    /**
     * @return the lastUpdate
     */
    public static Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public static void setLastUpdate(Timestamp lastUpdate) {
        StudySiteAccrualAccessServiceBean.lastUpdate = lastUpdate;
    }

   
    @Override
    public List<AccrualSubmissionAccessDTO> getAccrualSubmissionAccess(
            RegistryUser user) throws PAException {
        List<AccrualSubmissionAccessDTO> list = new ArrayList<AccrualSubmissionAccessDTO>();
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select sp.id as trialId, sp.officialTitle as trialTitle, "
                + "ss.healthCareFacility.organization.name as participatingSiteOrgName, "
                + "ss.healthCareFacility.organization.identifier as participatingSitePoOrgId, "
                + "ss.id as ssID "
                + "from StudyProtocol sp "
                + "inner join sp.studySites ss "
                + "inner join ss.studySiteAccrualAccess ssaa "
                + "where ssaa.registryUser.id = :userId and ssaa.statusCode = :statusCode "
                + "and ss.functionalCode = :fcode";
        Query query = session.createQuery(hql);
        query.setParameter(USER_ID, user.getId());
        query.setParameter(STATUS_CODE, ActiveInactiveCode.ACTIVE);
        query.setParameter("fcode", StudySiteFunctionalCode.TREATING_SITE);
                                                                                                       // CHECKSTYLE:OFF
        for (Object row : query.list()) {
            Object[] objArr = (Object[]) row;            
            AccrualSubmissionAccessDTO dto = new AccrualSubmissionAccessDTO();
            dto.setTrialId((Long) objArr[0]);
            dto.setTrialTitle((String) objArr[1]);            
            dto.setParticipatingSiteOrgName((String) objArr[2]);
            dto.setParticipatingSitePoOrgId((String) objArr[3]);
            dto.setStudySiteId((Long) objArr[4]);
            dto.setTrialNciId(new PAServiceUtils().getTrialNciId((Long) objArr[0]));            
            list.add(dto);            
        }
        return list;
    }

    @Override
    public List<Long> getActiveTrialLevelAccrualAccess(RegistryUser user)
            throws PAException {
        List<Long> trialIDs = new ArrayList<Long>();
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select saa.studyProtocol.id from StudyAccrualAccess saa where saa.registryUser.id = :userId "
                + "and saa.statusCode = :statusCode and saa.actionCode = :actionCode";
        Query query = session.createQuery(hql);
        query.setParameter(USER_ID, user.getId());
        query.setParameter(STATUS_CODE, ActiveInactiveCode.ACTIVE);
        query.setParameter(ACTION_CODE, AssignmentActionCode.ASSIGNED);
        for (Object obj : query.list()) {
            trialIDs.add(((Long) obj));
        }
        return trialIDs;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void assignTrialLevelAccrualAccess(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comments, RegistryUser creator)
            throws PAException {
        assignTrialLevelAccrualAccessPrivate(user, source, trialIDs, comments, creator);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void assignTrialLevelAccrualAccessNoTransaction(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comments, RegistryUser creator)
            throws PAException {
        assignTrialLevelAccrualAccessPrivate(user, source, trialIDs, comments, creator);
    }

    private void assignTrialLevelAccrualAccessPrivate(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comments, RegistryUser creator)
            throws PAException {
        List<Long> currentTrialAccess = getActiveTrialLevelAccrualAccess(user);
        List<AccrualSubmissionAccessDTO> siteLevelAccess = getAccrualSubmissionAccess(user);
        for (Long trialID : trialIDs) {
            if (!currentTrialAccess.contains(trialID)) {
                assignTrialLevelAccrualAccess(user, source, trialID, comments, creator, siteLevelAccess);
            }
        }
    }

    @SuppressWarnings(DEPRECATION)
    private void assignTrialLevelAccrualAccess(RegistryUser user, AccrualAccessSourceCode source, Long trialID,
            String comments, RegistryUser creator, List<AccrualSubmissionAccessDTO> userCurrentSiteLevelAccess) 
                    throws PAException {
        StudyAccrualAccessDTO dto = new StudyAccrualAccessDTO();
        dto.setActionCode(CdConverter
                .convertToCd(AssignmentActionCode.ASSIGNED));
        dto.setSource(CdConverter.convertToCd(source));
        dto.setComments(StConverter.convertToSt(StringUtils.left(comments,
                32768)));
        dto.setLastCreatedDate(TsConverter.convertToTs(new Date()));
        dto.setRegistryUserIdentifier(IiConverter.convertToIi(user.getId()));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(new Date()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(trialID));
        if (creator != null && creator.getCsmUser() != null) {
            dto.setUserLastCreatedIdentifier(IiConverter.convertToIi(creator.getCsmUser()
                    .getUserId()));
        }
        StudyAccrualAccessConverter converter = new StudyAccrualAccessConverter();
        StudyAccrualAccess bo =  converter.convertFromDtoToDomain(dto);
        Session session = PaHibernateUtil.getCurrentSession();
        session.save(bo);
        
        breakDownTrialLevelAccrualAccessIntoSites(user, dto, userCurrentSiteLevelAccess);
    }

    @SuppressWarnings(DEPRECATION)
    private void breakDownTrialLevelAccrualAccessIntoSites(
            final RegistryUser user, StudyAccrualAccessDTO accessDTO,
            List<AccrualSubmissionAccessDTO> userCurrentSiteLevelAccess)
            throws PAException {
        
        final Long trialID = IiConverter.convertToLong(accessDTO
                .getStudyProtocolIdentifier());     
        AccrualAccessSourceCode source = CdConverter.convertCdToEnum(AccrualAccessSourceCode.class, 
                accessDTO.getSource());
        List<ParticipatingOrgDTO> sites = new LinkedList<ParticipatingOrgDTO>(
                participatingOrgServiceLocal.getTreatingSites(trialID)); 
        
        breakDownTrialLevelAccrualAccessIntoSites(user, source,
                userCurrentSiteLevelAccess, trialID, sites);
    }

    /**
     * @param user
     * @param userCurrentSiteLevelAccess
     * @param trialID
     * @param sites
     * @throws PAException
     */
    private void breakDownTrialLevelAccrualAccessIntoSites(
            final RegistryUser user, AccrualAccessSourceCode source,
            List<AccrualSubmissionAccessDTO> userCurrentSiteLevelAccess,
            final Long trialID, List<ParticipatingOrgDTO> sites)
            throws PAException {
        final Ii registryUserIdentifier = IiConverter.convertToIi(user.getId());
        boolean isIndustrial = isIndustrialTrial(trialID);        
        if (isIndustrial) {
            // For industrial trials, since the user is ONLY allowed to submit
            // accruals for their affiliated org,
            // an entry in the existing table shall be used which is as-is
            // functionality.
            if (ObjectUtils.equals(AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, source)) {
                CollectionUtils.filter(sites, new Predicate() {
                    @Override
                    public boolean evaluate(Object arg) {
                        ParticipatingOrgDTO site = (ParticipatingOrgDTO) arg;
                        try {
                            List<Long> orgIds = FamilyHelper.getAllRelatedOrgs(Long.valueOf(site.getPoId()));
                            Long affiliatedOrgId = user.getAffiliatedOrganizationId();
                            return orgIds.contains(affiliatedOrgId);
                        } catch (Exception e) {
                            return false;
                        }
                    }
                });
            } else {
                CollectionUtils.filter(sites, new Predicate() {
                    @Override
                    public boolean evaluate(Object arg) {
                        ParticipatingOrgDTO site = (ParticipatingOrgDTO) arg;
                        return StringUtils.equals(user
                                .getAffiliatedOrganizationId().toString(), site
                                .getPoId());
                    }
                });
            }
        }
        for (ParticipatingOrgDTO site : sites) {
            if (!hasAccrualAccess(site.getStudySiteId(),
                    userCurrentSiteLevelAccess)) {
                createStudySiteAccrualIfEligible(registryUserIdentifier, site, source);
            }
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void synchronizeSiteAccrualAccess(Long trialID, RegistryUser user)
            throws PAException {
        
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        
        try {
             List<AccrualSubmissionAccessDTO> siteLevelAccess = getAccrualSubmissionAccess(user);
             List<ParticipatingOrgDTO> sites = new LinkedList<ParticipatingOrgDTO>(
                        participatingOrgServiceLocal.getTreatingSites(trialID)); 
             breakDownTrialLevelAccrualAccessIntoSites(user, user.getFamilyAccrualSubmitter().equals(true) 
                        ? AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE : AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, 
                		siteLevelAccess, trialID, sites);
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    /**
     * @param registryUserIdentifier
     * @param site
     * @param source
     * @throws PAException
     */
    @SuppressWarnings(DEPRECATION)
    private void createStudySiteAccrualIfEligible(
            final Ii registryUserIdentifier, ParticipatingOrgDTO site, AccrualAccessSourceCode source)
            throws PAException {
        StudySiteAccrualStatusDTO ssas = studySiteAccrualStatusService
                .getCurrentStudySiteAccrualStatusByStudySite(IiConverter
                        .convertToStudySiteIi(site.getStudySiteId()));
        RecruitmentStatusCode recruitmentStatus = null;
        if (ssas != null) {
            recruitmentStatus = RecruitmentStatusCode.getByCode(ssas
                    .getStatusCode().getCode());
            if (recruitmentStatus.isEligibleForAccrual()) {
                createStudySiteAccrualAccess(IiConverter.convertToLong(registryUserIdentifier), 
                        site.getStudySiteId(), source);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createStudySiteAccrualAccess(Long registryUserId, Long siteId, AccrualAccessSourceCode source)
            throws PAException {
            StudySiteAccrualAccessDTO ssaa = getByStudySiteAndUser(siteId, registryUserId);
            if (ssaa == null) {
                ssaa = new StudySiteAccrualAccessDTO();
                ssaa.setRegistryUserIdentifier(IiConverter.convertToIi(registryUserId));
                ssaa.setSource(CdConverter.convertToCd(source));
                ssaa.setRequestDetails(StConverter.convertToSt(StringUtils.EMPTY));
                ssaa.setStudySiteIdentifier(IiConverter.convertToIi(siteId));
                ssaa.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
                ssaa.setStatusDate(TsConverter.convertToTs(new Date()));
                create(ssaa);
            } else {
                if (StringUtils.equals(ActiveInactiveCode.INACTIVE.getCode(), 
                        CdConverter.convertCdToString(ssaa.getStatusCode()))) {
                    ssaa.setSource(CdConverter.convertToCd(source));
                    ssaa.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
                    ssaa.setStatusDate(TsConverter.convertToTs(new Date()));
                    update(ssaa);
                }
            }
    }

    public static boolean isIndustrialTrial(Long trialID) {
        Element element = getIndTrialCache().get(trialID);
        if (element == null) {
            Boolean isIndustrial = (Boolean) PaHibernateUtil.getCurrentSession().createQuery(
                "select sp.proprietaryTrialIndicator from StudyProtocol sp where sp.id=" + trialID).uniqueResult();
            element = new Element(trialID, isIndustrial);
            getIndTrialCache().put(element);
        }
        return (Boolean) element.getValue();
    }

    private boolean hasAccrualAccess(Long studySiteId,
            List<AccrualSubmissionAccessDTO> userCurrentSiteLevelAccess) {
        for (AccrualSubmissionAccessDTO dto : userCurrentSiteLevelAccess) {
            if (studySiteId.equals(dto.getStudySiteId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param participatingOrgServiceLocal the participatingOrgServiceLocal to set
     */
    public void setParticipatingOrgServiceLocal(
            ParticipatingOrgServiceLocal participatingOrgServiceLocal) {
        this.participatingOrgServiceLocal = participatingOrgServiceLocal;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void unassignTrialLevelAccrualAccess(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comment, RegistryUser creator)
            throws PAException {
        unassignTrialLevelAccrualAccessPrivate(user, source, trialIDs, comment, creator);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void unassignTrialLevelAccrualAccessNoTransaction(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comment, RegistryUser creator) throws PAException {
        unassignTrialLevelAccrualAccessPrivate(user, source, trialIDs, comment, creator);
    }

    private void unassignTrialLevelAccrualAccessPrivate(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comment, RegistryUser creator)
            throws PAException {
        List<Long> currentTrialAccess = getActiveTrialLevelAccrualAccess(user);        
        for (Long trialID : trialIDs) {
            if (currentTrialAccess.contains(trialID)) {
                unassignTrialLevelAccrualAccess(user, source, trialID, comment, creator, true);
            }
        }        
    }

    @SuppressWarnings(UNCHECKED_STR)
    private void unassignTrialLevelAccrualAccess(RegistryUser user, AccrualAccessSourceCode source,
            Long trialID, String comment, RegistryUser creator, boolean cascade) throws PAException {

        Session session = PaHibernateUtil.getCurrentSession();
        
        // De-activate current access first.        
        String hql = "select saa from StudyAccrualAccess saa where saa.registryUser.id = :userId "
                + "and saa.statusCode = :statusCode and saa.actionCode = :actionCode and saa.studyProtocol.id = :spId";
        Query query = session.createQuery(hql);
        query.setParameter(USER_ID, user.getId());
        query.setParameter(STATUS_CODE, ActiveInactiveCode.ACTIVE);
        query.setParameter(ACTION_CODE, AssignmentActionCode.ASSIGNED);     
        query.setParameter(SP_ID, trialID); 
        List<StudyAccrualAccess> accessList = query.list();
        for (StudyAccrualAccess studyAccrualAccess : accessList) {
            studyAccrualAccess.setStatusCode(ActiveInactiveCode.INACTIVE);
            session.saveOrUpdate(studyAccrualAccess);
        }

        createTrialAccessHistory(user, source, trialID, comment, creator);
        if (cascade) {
            removeStudySiteAccrualAccess(user, trialID, source);
        }
    }

    @Override
    public void unassignAllAccrualAccess(RegistryUser user, AccrualAccessSourceCode source, String comment,
            RegistryUser creator) throws PAException {
        Set<Long> trialIds = new HashSet<Long>(getActiveTrialLevelAccrualAccess(user));
        for (Long trialId : trialIds) {
            unassignTrialLevelAccrualAccess(user, source, trialId, comment, creator, false);
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = null;
        try {
            String hql = "UPDATE StudySiteAccrualAccess ssaa "
                + "SET ssaa.statusCode = :inactv, ssaa.statusDateRangeLow = CURRENT_TIMESTAMP, "
                + "    ssaa.dateLastUpdated = CURRENT_TIMESTAMP, ssaa.userLastUpdated = :creator, "
                + "    ssaa.source = :source, ssaa.requestDetails = :comment "
                + "WHERE ssaa.registryUser = :ru AND ssaa.statusCode =  :actv";
            query = session.createQuery(hql);
            query.setParameter("inactv", ActiveInactiveCode.INACTIVE);
            query.setParameter("creator", creator.getCsmUser());
            query.setParameter("source", source);
            query.setParameter("comment", comment);
            query.setParameter("ru", user);
            query.setParameter("actv", ActiveInactiveCode.ACTIVE);
            query.executeUpdate();
        } catch (HibernateException e) {
            LOG.error("Hibernate exception while inactivating StudySiteAccrualAccess", e);
        }
    }

    @SuppressWarnings(DEPRECATION)
    public void createTrialAccessHistory(RegistryUser user, AccrualAccessSourceCode source,
            Long trialID, String comment, RegistryUser creator) {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyAccrualAccessConverter converter = new StudyAccrualAccessConverter();
        StudyAccrualAccessDTO dto = new StudyAccrualAccessDTO();
        dto.setActionCode(CdConverter
                .convertToCd(AssignmentActionCode.UNASSIGNED));
        dto.setSource(CdConverter.convertToCd(source));
        dto.setComments(StConverter.convertToSt(StringUtils.left(comment,
                32768)));
        dto.setLastCreatedDate(TsConverter.convertToTs(new Date()));
        dto.setRegistryUserIdentifier(IiConverter.convertToIi(user.getId()));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.INACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(new Date()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(trialID));
        if (creator != null && creator.getCsmUser() != null) {
            dto.setUserLastCreatedIdentifier(IiConverter.convertToIi(creator.getCsmUser().getUserId()));
        }
        session.save(converter.convertFromDtoToDomain(dto));
    }

    private void removeStudySiteAccrualAccess(RegistryUser user, Long trialID, AccrualAccessSourceCode source)
            throws PAException {
        List<StudySiteAccrualAccessDTO> list = getByStudyProtocol(trialID);
        removeStudySiteAccrualAccess(user, list, source);
    }


    public void removeStudySiteAccrualAccess(RegistryUser user, List<StudySiteAccrualAccessDTO> list, 
            AccrualAccessSourceCode source) throws PAException {
        for (StudySiteAccrualAccessDTO dto : list) {
            if (ActiveInactiveCode.ACTIVE.name().equalsIgnoreCase(
                    dto.getStatusCode().getCode())
                    && user.getId().equals(
                            IiConverter.convertToLong(dto
                                    .getRegistryUserIdentifier()))) {
                dto.setSource(CdConverter.convertToCd(source));
                dto.setStatusCode(CdConverter
                        .convertToCd(ActiveInactiveCode.INACTIVE));
                dto.setStatusDate(TsConverter.convertToTs(new Date()));
                update(dto);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccrualAccessAssignmentHistoryDTO> getAccrualAccessAssignmentHistory(Collection<Long> trialIds)
            throws PAException {
        List<AccrualAccessAssignmentHistoryDTO> list = new ArrayList<AccrualAccessAssignmentHistoryDTO>();
        if (CollectionUtils.isNotEmpty(trialIds)) {
            Session session = PaHibernateUtil.getCurrentSession();
            final Query query = session.createQuery("from " + StudyAccrualAccess.class.getName()
                    + " saa where saa.studyProtocol.id in (:trialIds) order by id desc");
            query.setMaxResults(MAX_HISTORY_RECORDS);
            query.setParameterList("trialIds", trialIds);
            final List<StudyAccrualAccess> boList = query.list();
            for (StudyAccrualAccess saa : boList) {
                AccrualAccessAssignmentHistoryDTO dto = new AccrualAccessAssignmentHistoryDTO();
                dto.setAction(saa.getActionCode().getCode());
                dto.setAssignee(saa.getRegistryUser().getFullName());
                dto.setAssigner(getUserName(saa.getUserLastCreated()));
                dto.setComments(saa.getComments());
                dto.setDate(DateFormatUtils.format(saa.getDateLastCreated(),
                        PAUtil.DATE_FORMAT));
                dto.setTrialNciId(new PAServiceUtils().getTrialNciId(saa.getStudyProtocol().getId()));
                list.add(dto);
            }
        }
        return list;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<AccrualAccessAssignmentByTrialDTO> getAccrualAccessAssignmentByTrial(Collection<Long> trialIds)
            throws PAException {
        List<AccrualAccessAssignmentByTrialDTO> list = new ArrayList<AccrualAccessAssignmentByTrialDTO>();
        if (CollectionUtils.isNotEmpty(trialIds)) {
        	Session session = PaHibernateUtil.getCurrentSession();

        	String hql = "select saa from StudyAccrualAccess saa where "
        			+ "saa.statusCode = :statusCode and saa.actionCode = :actionCode "
        			+ "and saa.studyProtocol.id in (:trialIds)";
        	Query query = session.createQuery(hql);        
        	query.setParameter(STATUS_CODE, ActiveInactiveCode.ACTIVE);
        	query.setParameter(ACTION_CODE, AssignmentActionCode.ASSIGNED);
        	query.setParameterList("trialIds", trialIds);
        	List<StudyAccrualAccess> accessList = query.list();
        	for (StudyAccrualAccess saa : accessList) {
        		final StudyProtocol protocol = saa.getStudyProtocol();
        		String trialNciId = new PAServiceUtils().getTrialNciId(protocol.getId());
        		String title = protocol.getOfficialTitle();
        		String submitter = saa.getRegistryUser().getFullName();
        		SummaryFourFundingCategoryCode code = Boolean.TRUE.equals(protocol
        				.getProprietaryTrialIndicator()) ? SummaryFourFundingCategoryCode.INDUSTRIAL
        						: getFundingCode(protocol);
        		AccrualAccessAssignmentByTrialDTO dto = findDTOInListByTrialID(
        				list, trialNciId);
        		if (dto == null) {
        			dto = new AccrualAccessAssignmentByTrialDTO();
        			dto.setCategoryCode(code);
        			dto.setTrialNciId(trialNciId);
        			dto.setTrialTitle(title);                
        			list.add(dto);
        		}
        		dto.getAccrualSubmitters().add(submitter);
        	}
        }
        return list;
    }

    private AccrualAccessAssignmentByTrialDTO findDTOInListByTrialID(
            List<AccrualAccessAssignmentByTrialDTO> list,
            final String trialNciId) {
        return (AccrualAccessAssignmentByTrialDTO) CollectionUtils.find(list,
                new org.apache.commons.collections.Predicate() {
                    @Override
                    public boolean evaluate(Object dto) {
                        return ((AccrualAccessAssignmentByTrialDTO) dto)
                                .getTrialNciId().equals(trialNciId);
                    }
                });
    }

    private SummaryFourFundingCategoryCode getFundingCode(
            StudyProtocol studyProtocol) {
        for (StudyResourcing funding : studyProtocol.getStudyResourcings()) {
            if (Boolean.TRUE.equals(funding
                    .getSummary4ReportedResourceIndicator())
                    && (Boolean.TRUE.equals(funding.getActiveIndicator()))) {
                return funding.getTypeCode();
            }
        }
        return null;
    }

    private String getUserName(User user) throws PAException {
        if (user == null) {
            return "";
        }
        RegistryUser ru = registryUserService.getUser(user.getLoginName());
        if (ru != null) {
            return ru.getFullName();
        } else {
            return CsmUserUtil.getDisplayUsername(user);
        }
    }
}
