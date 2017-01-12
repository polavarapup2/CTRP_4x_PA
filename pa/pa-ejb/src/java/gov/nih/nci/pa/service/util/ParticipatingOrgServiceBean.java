package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.hibernate.Query;

/**
 * @author Hugh Reinhart
 * @since Jun 18, 2012
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class ParticipatingOrgServiceBean implements ParticipatingOrgServiceLocal {

    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private PAHealthCareProviderLocal paHealthCareProviderService;

    private static final String BY_PROTOCOL_HQL =
            "select ss, org.name, org.identifier, sp.id "
                    + "from StudySite ss "
                    + "join ss.studyProtocol sp "
                    + "join ss.healthCareFacility hf "
                    + "join hf.organization org "
                    + "where sp.id = :spId "
                    + "and ss.functionalCode = :functionalCode "
                    + "order by upper(org.name)";
    
    private static final String BY_PROTOCOL_AND_ORG_IDS_HQL =
            "select org.identifier "
                    + "from StudySite ss "
                    + "join ss.studyProtocol sp "
                    + "join ss.healthCareFacility hf "
                    + "join hf.organization org "
                    + "where sp.id = :spId "
                    + "and ss.functionalCode = :functionalCode and org.identifier in (:poOrgIDs)";
                        

    private static final String BY_STUDY_SITE_HQL =
            "select ss, org.name, org.identifier, sp.id "
                    + "from StudySite ss "
                    + "join ss.studyProtocol sp "
                    + "join ss.healthCareFacility hf "
                    + "join hf.organization org "
                    + "where ss.id = :studySiteId "
                    + "and ss.functionalCode = :functionalCode";

    private static final int STUDY_SITE_IDX = 0;
    private static final int ORG_NAME_IDX = 1;
    private static final int ORG_IDENTIFIER_IDS = 2;
    private static final int PROTOCOL_IDENTIFIER_IDX = 3;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ParticipatingOrgDTO> getTreatingSites(Long studyProtocolId) throws PAException {
        if (studyProtocolId == null) {
            return new ArrayList<ParticipatingOrgDTO>();
        }
        List<ParticipatingOrgDTO> result = new ArrayList<ParticipatingOrgDTO>();
        List<Long> studySiteIds = new ArrayList<Long>();
        try {
            Query qry = PaHibernateUtil.getCurrentSession().createQuery(BY_PROTOCOL_HQL);
            qry.setParameter("spId", studyProtocolId);
            qry.setParameter("functionalCode", StudySiteFunctionalCode.TREATING_SITE);
            processQueryResults(result, studySiteIds, qry);
        } catch (Exception e) {
            throw new PAException(e);
        }
        addAccrualAndPersonData(result, studySiteIds);
        return result;
    }

    @Override
    public ParticipatingOrgDTO getTreatingSite(Long studySiteId)
            throws PAException {
        List<ParticipatingOrgDTO> result = new ArrayList<ParticipatingOrgDTO>();
        List<Long> studySiteIds = new ArrayList<Long>();
        try {
            Query qry = PaHibernateUtil.getCurrentSession().createQuery(
                    BY_STUDY_SITE_HQL);
            qry.setParameter("studySiteId", studySiteId);
            qry.setParameter("functionalCode",
                    StudySiteFunctionalCode.TREATING_SITE);
            processQueryResults(result, studySiteIds, qry);
        } catch (Exception e) {
            throw new PAException(e);
        }
        addAccrualAndPersonData(result, studySiteIds);
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * @param result
     * @param studySiteIds
     * @throws PAException
     */
    private void addAccrualAndPersonData(List<ParticipatingOrgDTO> result,
            List<Long> studySiteIds) throws PAException {
        if (CollectionUtils.isNotEmpty(studySiteIds)) {
            Long[] ssIdArray = studySiteIds.toArray(new Long[studySiteIds.size()]);
            addAccrualAndPersonData(result, ssIdArray);
        }
    }

    /**
     * @param result
     * @param studySiteIds
     * @param qry
     */
    @SuppressWarnings("unchecked")
    private void processQueryResults(List<ParticipatingOrgDTO> result,
            List<Long> studySiteIds, Query qry) {
        List<Object[]> queryList = qry.list();
        for (Object[] row : queryList) {
            StudySite studySite = (StudySite) row[STUDY_SITE_IDX];
            ParticipatingOrgDTO site = new ParticipatingOrgDTO();
            site.setName((String) row[ORG_NAME_IDX]);
            site.setPoId((String) row[ORG_IDENTIFIER_IDS]);
            site.setStudyProtocolId((Long) row[PROTOCOL_IDENTIFIER_IDX]);
            site.setStudySiteId(studySite.getId());
            site.setStatusCode(studySite.getStatusCode());
            site.setTargetAccrualNumber(studySite.getTargetAccrualNumber());
            site.setProgramCodeText(studySite.getProgramCodeText());
            site.setLocalProtocolIdentifier(studySite.getLocalStudyProtocolIdentifier());            
            site.setDateOpenedForAccrual(studySite.getAccrualDateRangeLow());
            site.setDateClosedForAccrual(studySite.getAccrualDateRangeHigh());            
            result.add(site);
            studySiteIds.add(site.getStudySiteId());
        }
    }

    private void addAccrualAndPersonData(List<ParticipatingOrgDTO> poList, Long[] ssIdArray) throws PAException {
        Map<Long, StudySiteAccrualStatus> siteStatuses =
                studySiteAccrualStatusService.getCurrentStudySiteAccrualStatus(ssIdArray);
        Map<Long, List<PaPersonDTO>> pcs =
                paHealthCareProviderService.getPersonsByStudySiteId(ssIdArray,
                        StudySiteContactRoleCode.PRIMARY_CONTACT.getName());
        Map<Long, List<PaPersonDTO>> pis =
                paHealthCareProviderService.getPersonsByStudySiteId(ssIdArray,
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        Map<Long, List<PaPersonDTO>> sis =
                paHealthCareProviderService.getPersonsByStudySiteId(ssIdArray,
                        StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        for (ParticipatingOrgDTO org : poList) {
            StudySiteAccrualStatus ssas = siteStatuses.get(org.getStudySiteId());
            if (ssas != null) {
                org.setRecruitmentStatus(ssas.getStatusCode());
                org.setRecruitmentStatusDate(ssas.getStatusDate());
                org.setRecruitmentStatusComments(ssas.getComments());
            }
            org.setPrimaryContacts(getPeople(pcs.get(org.getStudySiteId())));
            org.setPrincipalInvestigators(getPeople(pis.get(org.getStudySiteId())));
            org.setSubInvestigators(getPeople(sis.get(org.getStudySiteId())));
        }
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param paHealthCareProviderService the paHealthCareProviderService to set
     */
    public void setPaHealthCareProviderService(PAHealthCareProviderLocal paHealthCareProviderService) {
        this.paHealthCareProviderService = paHealthCareProviderService;
    }

    private List<PaPersonDTO> getPeople(List<PaPersonDTO> people) {
        return people == null ? new ArrayList<PaPersonDTO>() : people;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<OrganizationDTO> getOrganizationsThatAreNotSiteYet(
            Long studyProtocolId, Collection<OrganizationDTO> orgsToCheck)
            throws PAException {        
        Collection<String> orgIdList = CollectionUtils.collect(orgsToCheck,
                new Transformer() {
                    @Override
                    public Object transform(Object o) {
                        OrganizationDTO dto = (OrganizationDTO) o;
                        return IiConverter.convertToString(dto.getIdentifier());
                    }
                });

        Query qry = PaHibernateUtil.getCurrentSession().createQuery(
                BY_PROTOCOL_AND_ORG_IDS_HQL);
        qry.setParameter("spId", studyProtocolId);
        qry.setParameter("functionalCode",
                StudySiteFunctionalCode.TREATING_SITE);
        qry.setParameterList("poOrgIDs", orgIdList);
        final List<String> poOrgIdsThatAreSites = qry.list();
        return CollectionUtils.select(orgsToCheck, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                OrganizationDTO dto = (OrganizationDTO) o;
                return !poOrgIdsThatAreSites.contains(IiConverter
                        .convertToString(dto.getIdentifier()));
            }
        });
    }
}
