package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyAccrualAccess;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Nov 30, 2012
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class FamilySynchronizationServiceBean implements FamilySynchronizationServiceLocal {

    private static final long PO_WAIT_TIME = 5000L;

    private static final Logger LOG = Logger.getLogger(FamilySynchronizationServiceBean.class);

    private static final String COMMENT = "Change in PO organization family structure.";
    private static final String SOURCE = "source";

    @EJB
    private StudySiteAccrualAccessServiceLocal studySiteAccess;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private FamilyServiceLocal familyService;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void synchronizeFamilyOrganizationRelationship(Long familyOrgRelId) throws PAException {
        if (familyOrgRelId == null) {
            LOG.warn("Called FamilySynchronizationServiceBean.synchronizeFamilyOrganizationRelationship()"
                    + " with a null.");
            return;
        }
        FamilyOrganizationRelationshipDTO dto = PoRegistry.getFamilyService().getFamilyOrganizationRelationship(
                IiConverter.convertToPoFamilyOrgRelationshipIi(familyOrgRelId.toString()));
        if (dto == null) {
            LOG.error("Referenced object not found in PO in FamilySynchronizationServiceBean."
                    + "synchronizeFamilyOrganizationRelationship().");
            return;
        }        
        if (isDelete(dto)) {
            boolean isOrgBeingNullifiedIntoFamilyMember = isOrgBeingNullifiedIntoFamilyMember(
                    dto.getOrgIdentifier(), dto.getFamilyIdentifier());
            if (!isOrgBeingNullifiedIntoFamilyMember) {
                Long orgId = IiConverter.convertToLong(dto.getOrgIdentifier());
                removeFamilyAccessToOrgsSubmitters(orgId);
                removeFamilyAccessToOrgsTrials(orgId,
                        FamilyServiceBeanLocal.HQL_COMPLETE,
                        StudySiteFunctionalCode.LEAD_ORGANIZATION);
                removeFamilyAccessToOrgsTrials(orgId,
                        FamilyServiceBeanLocal.HQL_ABBR,
                        StudySiteFunctionalCode.TREATING_SITE);
            }
        } else {
            addOrgToFamily(IiConverter.convertToLong(dto.getFamilyIdentifier()));
        }
    }

    private boolean isOrgBeingNullifiedIntoFamilyMember(final Ii orgIdentifier,
            final Ii familyIdentifier) {
        // Sleep for a bit just in case to let PO fully commit its transaction,
        // if any.
        try {
            Thread.sleep(PO_WAIT_TIME);
            final Ii dupId = new PAServiceUtils()
                    .getDuplicateOrganizationIi(orgIdentifier);
            if (!ISOUtil.isIiNull(dupId)) {
                return getAllOrgs(IiConverter.convertToLong(familyIdentifier))
                        .contains(IiConverter.convertToLong(dupId));
            }
        } catch (InterruptedException | PAException e) {
            LOG.error(e, e);
        }
        return false;
    }

    private boolean isDelete(FamilyOrganizationRelationshipDTO dto) {
        List<FamilyOrganizationRelationshipDTO> activeList = PoRegistry.getFamilyService().getActiveRelationships(
                IiConverter.convertToLong(dto.getFamilyIdentifier()));
        Set<Ii> activeMap = new HashSet<Ii>();
        for (FamilyOrganizationRelationshipDTO active : activeList) {
            activeMap.add(active.getIdentifier());
        }
        return !activeMap.contains(dto.getIdentifier());
    }

    private void addOrgToFamily(Long familyId) throws PAException {
        Collection<Long> orgs = getAllOrgs(familyId);
        List<RegistryUser> users = registryUserService.findByAffiliatedOrgs(orgs);
        for (RegistryUser user : users) {
            if (user.getFamilyAccrualSubmitter()) {
                familyService.assignFamilyAccrualAccess(user, null, COMMENT);
            }
        }
        PaHibernateUtil.getCurrentSession().flush();
    }

    private Collection<Long> getAllOrgs(Long familyId) {
        Set<Long> result = new HashSet<Long>();
        List<FamilyOrganizationRelationshipDTO> activeList = 
                PoRegistry.getFamilyService().getActiveRelationships(familyId);
        for (FamilyOrganizationRelationshipDTO active : activeList) {
            result.add(IiConverter.convertToLong(active.getOrgIdentifier()));
        }
        return result;
    }

    /**
     * Remove all family accrual access from family accrual submitters affiliated with given organization.
     * @param orgId the affiliated organization
     * @param isOrgBeingNullifiedIntoFamilyMember isOrgBeingNullifiedIntoFamilyMember
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    private void removeFamilyAccessToOrgsSubmitters(Long orgId) throws PAException {      
        Session session = PaHibernateUtil.getCurrentSession();
        List<RegistryUser> users = registryUserService.findByAffiliatedOrg(orgId);
        for (RegistryUser user : users) {
            if (user.getFamilyAccrualSubmitter()) {
                String hql = "from StudyAccrualAccess saa where saa.registryUser.id = :userId "
                        + "and saa.statusCode = :statusCode and saa.actionCode = :actionCode and saa.source = :source";
                Query query = session.createQuery(hql);
                query.setParameter("userId", user.getId());
                query.setParameter("statusCode", ActiveInactiveCode.ACTIVE);
                query.setParameter("actionCode", AssignmentActionCode.ASSIGNED);
                query.setParameter(SOURCE, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
                List<StudyAccrualAccess> accessList = query.list();
                for (StudyAccrualAccess studyAccrualAccess : accessList) {
                    studyAccrualAccess.setStatusCode(ActiveInactiveCode.INACTIVE);
                    session.update(studyAccrualAccess);
                    studySiteAccess.createTrialAccessHistory(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, 
                            studyAccrualAccess.getStudyProtocol().getId(), COMMENT, null);
                }

                hql = "UPDATE StudySiteAccrualAccess ssaa "
                    + "SET ssaa.userLastUpdated = NULL, ssaa.dateLastUpdated = current_timestamp(), "
                    + "    ssaa.statusCode = :inactiveCode "
                    + "WHERE ssaa.id in ( SELECT ssaa2.id FROM StudySiteAccrualAccess ssaa2 JOIN ssaa2.registryUser ru "
                    + "                   WHERE ssaa2.statusCode = :activeCode AND ru.id = :userId "
                    + "                     AND ssaa2.source = :source )";
                query = session.createQuery(hql);
                query.setParameter("userId", user.getId());
                query.setParameter("inactiveCode", ActiveInactiveCode.INACTIVE);
                query.setParameter("activeCode", ActiveInactiveCode.ACTIVE);
                query.setParameter(SOURCE, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
                query.executeUpdate();
                user.setFamilyAccrualSubmitter(false);
                registryUserService.updateUser(user);
            }
        }
        session.flush();
   }

    

    /**
     * Remove all family accrual access to trials for which this is lead org.
     * @param orgId the affiliated organization
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    private void removeFamilyAccessToOrgsTrials(Long orgId, String hql, StudySiteFunctionalCode siteType) 
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query  query = session.createQuery(hql);
        query.setParameter("statusCode", ActStatusCode.ACTIVE);
        query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
        query.setParameter("siteCode", siteType);
        query.setParameterList("orgIds", Arrays.asList(orgId.toString()));
        List<Long> queryList = query.list();
        if (CollectionUtils.isNotEmpty(queryList)) {
            Set<Long> trialIds = new HashSet<Long>();
            trialIds.addAll(queryList);
            String hql2 = "select saa from StudyAccrualAccess saa where saa.studyProtocol.id in (:trialIds) "
                    + "and saa.statusCode = :statusCode and saa.actionCode = :actionCode and saa.source = :source";
            query = session.createQuery(hql2);
            query.setParameterList("trialIds", trialIds);
            query.setParameter("statusCode", ActiveInactiveCode.ACTIVE);
            query.setParameter("actionCode", AssignmentActionCode.ASSIGNED);
            query.setParameter(SOURCE, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
            List<StudyAccrualAccess> accessList = query.list();
            for (StudyAccrualAccess studyAccrualAccess : accessList) {
                studyAccrualAccess.setStatusCode(ActiveInactiveCode.INACTIVE);
                session.saveOrUpdate(studyAccrualAccess);
                studySiteAccess.createTrialAccessHistory(studyAccrualAccess.getRegistryUser(), 
                        AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, studyAccrualAccess.getStudyProtocol().getId(),
                        COMMENT, null);
            }

            hql2 = "UPDATE StudySiteAccrualAccess ssaa "
                    + "SET ssaa.userLastUpdated = NULL, ssaa.dateLastUpdated = current_timestamp(), "
                    + "    ssaa.statusCode = :inactiveCode "
                    + "WHERE ssaa.id IN ( SELECT ssaa2.id FROM StudySiteAccrualAccess ssaa2 "
                    + "                   JOIN ssaa2.studySite ss JOIN ss.studyProtocol sp " 
                    + "                   WHERE sp.id IN (:trialIds) AND ssaa.statusCode = :activeCode "
                    + "                     AND ssaa.source = :source )";
            query = session.createQuery(hql2);
            query.setParameter("inactiveCode", ActiveInactiveCode.INACTIVE);
            query.setParameterList("trialIds", trialIds);
            query.setParameter("activeCode", ActiveInactiveCode.ACTIVE);
            query.setParameter(SOURCE, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
            query.executeUpdate();
            session.flush();
        }
    }

    /**
     * @param studySiteAccess the studySiteAccess to set
     */
    public void setStudySiteAccess(StudySiteAccrualAccessServiceLocal studySiteAccess) {
        this.studySiteAccess = studySiteAccess;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param familyService the familyService to set
     */
    public void setFamilyService(FamilyServiceLocal familyService) {
        this.familyService = familyService;
    }
}
