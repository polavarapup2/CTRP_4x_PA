/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudySiteSortCriterion;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AssignedIdentifierEnum;
import gov.nih.nci.pa.util.CorrelationUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudySiteBeanLocal extends AbstractRoleIsoService<StudySiteDTO, StudySite, StudySiteConverter> implements
        StudySiteServiceLocal {

    private static final Logger LOG = Logger.getLogger(StudySiteBeanLocal.class);
    private CorrelationUtils corrUtils = new CorrelationUtils();

    /**
     * @param dto StudySiteDTO
     * @return StudySiteDTO
     * @throws PAException PAException
     */
    @Override
    public StudySiteDTO create(StudySiteDTO dto) throws PAException {
        StudySiteDTO createDto = businessRules(dto);
        createDto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        StudySiteDTO resultDto = super.create(createDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    /**
     * @param dto StudySiteDTO
     * @return StudySiteDTO
     * @throws PAException PAException
     */
    @Override
    public StudySiteDTO update(StudySiteDTO dto) throws PAException {
        StudySiteDTO updateDto = businessRules(dto);
        getStatusCode(updateDto);
        StudySiteDTO resultDto = super.update(updateDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    /**
     * creates a new record of studyprotocol by changing to new studyprotocol identifier.
     * @param fromStudyProtocolIi from where the study protocol objects to be copied
     * @param toStudyProtocolIi to where the study protocol objects to be copied
     * @return map
     * @throws PAException on error
     */
    @Override
    @SuppressWarnings({ "PMD.ExcessiveMethodLength", "unchecked" })
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();        
        
        Map<Ii, Ii> map = new HashMap<Ii, Ii>();        
        StudyProtocol targetProtocol = new StudyProtocol();
        targetProtocol.setId(IiConverter.convertToLong(toStudyProtocolIi));
                
        Collection<StudySite> studySites = getAllSitesByProtocol(fromStudyProtocolIi);        
        try {
            PaHibernateUtil.disableAudit();
            for (StudySite site : studySites) {
                // PO-8679: next line was ruining performance.
                // List<StudySiteContact> contacts = new ArrayList<StudySiteContact>(site.getStudySiteContacts());
                List<StudySiteContact> contacts = session
                        .createQuery("from StudySiteContact where studySite.id="
                                + site.getId()).list();
                List<StudySiteAccrualStatus> accrualStatuses = new ArrayList<StudySiteAccrualStatus>(
                        site.getStudySiteAccrualStatuses());
                session.evict(site);
                
                Ii from = IiConverter.convertToStudySiteIi(site.getId());
                Ii to = new Ii();
                
                site.setId(null);
                site.setStudyProtocol(targetProtocol);     
                site.setAccrualCounts(new TreeSet<StudySiteSubjectAccrualCount>());
                site.setStudySiteAccrualAccess(new ArrayList<StudySiteAccrualAccess>());
                site.setStudySiteAccrualStatuses(new ArrayList<StudySiteAccrualStatus>());
                site.setStudySiteContacts(new ArrayList<StudySiteContact>());
                site.setStudySiteOwners(new HashSet<RegistryUser>());
                site.setStudySubjects(new ArrayList<StudySubject>());                        
                session.save(site);
                session.flush();
                            
                to.setIdentifierName(from.getIdentifierName());
                to.setRoot(from.getRoot());
                to.setExtension(site.getId().toString());
                
                // create study contact
                for (StudySiteContact ssc : contacts) {
                    session.evict(ssc);
                    ssc.setId(null);
                    ssc.setStudySite(site);
                    ssc.setStudyProtocol(targetProtocol); 
                    session.save(ssc);
                }
                
                // create study accrual status
                if (StudySiteFunctionalCode.TREATING_SITE.equals(site
                        .getFunctionalCode())) {
                    for (StudySiteAccrualStatus status : accrualStatuses) {
                        session.evict(status);
                        status.setId(null);
                        status.setStudySite(site);
                        session.save(status);
                    }
                }
                map.put(from, to);
            }
        } finally {
            PaHibernateUtil.enableAudit();
        }
        return map;
    }
     
    @SuppressWarnings("unchecked")
    private Collection<StudySite> getAllSitesByProtocol(Ii ii) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select alias from "
                + StudySite.class.getSimpleName()
                + " alias join alias.studyProtocol sp left join fetch alias.studySiteAccrualStatuses "
                + "left join fetch alias.researchOrganization "
                + "left join fetch alias.researchOrganization.organization left join fetch alias.healthCareFacility "
                + "left join fetch alias.healthCareFacility.organization where sp.id = :studyProtocolId";
        Query query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));
        // left join fetch on studySiteAccrualStatuses may produce duplicate site entries.
        return filterOutDuplicateSites((List<StudySite>) query.list());
    }

    private Collection<StudySite> filterOutDuplicateSites(List<StudySite> list) {
        Collection<StudySite> set = new TreeSet<StudySite>(
                new Comparator<StudySite>() {
                    @Override
                    public int compare(StudySite o1, StudySite o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });
        set.addAll(list);
        return set;
    }

    private StudySiteDTO businessRules(StudySiteDTO dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getHealthcareFacilityIi()) && ISOUtil.isIiNull(dto.getResearchOrganizationIi())
            && ISOUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("Either healthcare facility or research organization or Oversight committee"
                + " must be set.");
        }
        if (!ISOUtil.isIiNull(dto.getHealthcareFacilityIi()) && !ISOUtil.isIiNull(dto.getResearchOrganizationIi())) {
            throw new PAException("Healthcare facility and research organization cannot both be set.");
        }
        if (!ISOUtil.isIiNull(dto.getHealthcareFacilityIi()) && !ISOUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("Healthcare facility and oversight committee cannot both be set.");
        }
        if (!ISOUtil.isIiNull(dto.getResearchOrganizationIi()) && !ISOUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("research organization and oversight committee cannot both be set.");
        }
        final String approvalStatusCodeString = CdConverter.convertCdToString(dto.getReviewBoardApprovalStatusCode());
        ReviewBoardApprovalStatusCode code = ReviewBoardApprovalStatusCode.getByCode(approvalStatusCodeString);
        if (code != null) {
            String approvalNumber = StConverter.convertToString(dto.getReviewBoardApprovalNumber());
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().toString().equals(code.getCode().toString())
                && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                throw new PAException("Review board approval number must be set for status '"
                    + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "'.");
            }
            if (ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().toString().equals(code.getCode().toString())
                || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().toString()
                                                                  .equals(code.getCode().toString())
                || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().toString()
                                                                 .equals(code.getCode().toString())
                && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                dto.setReviewBoardApprovalNumber(StConverter.convertToSt(null));
            }
            if (ISOUtil.isIiNull(dto.getOversightCommitteeIi())) {
                throw new PAException("Oversight committee (board) must be set when review board approval status is '"
                    + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "' or '"
                    + ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getDisplayName() + "'.");
            }
        } else {
            dto.setOversightCommitteeIi(null);
            dto.setReviewBoardApprovalDate(null);
            dto.setReviewBoardApprovalNumber(null);
        }
        enforceNoDuplicate(dto);
        enforceNoDuplicateTrial(dto);
        return dto;
    }

    private String getOrganizationId(StudySiteDTO dto) {
        if (ISOUtil.isIiNull(dto.getHealthcareFacilityIi())) {
            return (ISOUtil.isIiNull(dto.getResearchOrganizationIi())
                ? IiConverter.convertToString(dto.getOversightCommitteeIi())
                : IiConverter.convertToString(dto.getResearchOrganizationIi()));
        }
        return IiConverter.convertToString(dto.getHealthcareFacilityIi());
    }

    private String getFunctionalCode(StudySiteDTO dto) {
        return (ISOUtil.isCdNull(dto.getFunctionalCode())) ? ""
                : CdConverter.convertCdToString(dto.getFunctionalCode());
    }

    private void enforceNoDuplicate(StudySiteDTO dto) throws PAException {
        String newOrgId = getOrganizationId(dto);
        String newFunction = getFunctionalCode(dto);
        if (!ISOUtil.isIiNull(dto.getIdentifier())
                && CdConverter.convertCdToEnum(StudySiteFunctionalCode.class,
                        dto.getFunctionalCode()) == StudySiteFunctionalCode.TREATING_SITE) {
            // PO-8353: when we are updating an existing participating site, we
            // can skip this validation method
            // altogether to avoid the following 'getByStudyProtocol' call from
            // being made in a loop.
            // In CTRP, there is no option to change a participating site from
            // one org to a different one:
            // a new site would have to be created.
            // Either way, there is a database constraint that will prevent a
            // duplicate treating site existence
            // in any case.
            return;
        }
        List<StudySiteDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudySiteDTO sp : spList) {
            boolean sameSite = IiConverter.convertToLong(sp.getIdentifier())
                                          .equals(IiConverter.convertToLong(dto.getIdentifier()));
            boolean sameOrg = newOrgId.equals(getOrganizationId(sp));
            boolean sameFunction = newFunction.equals(getFunctionalCode(sp));
            if (!sameSite && sameOrg && sameFunction) {
                throw new PADuplicateException("This organization has already been entered as a '" + newFunction
                    + "' for this study.");
            }
        }

    }

    private void enforceOnlyOneOversightCommittee(StudySiteDTO dto) throws PAException {
        if (!ISOUtil.isCdNull(dto.getReviewBoardApprovalStatusCode())) {
            List<StudySiteDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudySiteDTO sp : spList) {
                if (!IiConverter.convertToLong(dto.getIdentifier())
                                .equals(IiConverter.convertToLong(sp.getIdentifier()))
                    && !ISOUtil.isCdNull(sp.getReviewBoardApprovalStatusCode())) {
                    sp.setReviewBoardApprovalStatusCode(null);
                    update(sp);
                }
            }
        }
    }

    /**
     *
     * @param dto dto
     * @throws PAException e
     */
    private void enforceNoDuplicateTrial(StudySiteDTO dto) throws PAException {
        Session session = null;
        List<StudySite> queryList = new ArrayList<StudySite>();
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        String nciId = null;
        if (!ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            nciId = paServiceUtil.getTrialNciId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        }
        session = PaHibernateUtil.getCurrentSession();
        Query query = null;
        // step 1: form the hql
        String hql = " select spart " + " from StudySite spart " + " join spart.researchOrganization as ro "
            + " join spart.studyProtocol as sp " + " join sp.documentWorkflowStatuses as dws  "
            + " where spart.localStudyProtocolIdentifier = :localStudyProtocolIdentifier "
            + " and spart.functionalCode = '" + StudySiteFunctionalCode.getByCode(dto.getFunctionalCode().getCode())
            + "'" + " and dws.statusCode  <> '" + DocumentWorkflowStatusCode.REJECTED + "'" + " and sp.statusCode ='"
            + ActStatusCode.ACTIVE + "'" + " and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
            + "  where dws.studyProtocol = dws1.studyProtocol ) or dws.id is null ) " + " and ro.id = :orgIdentifier";
        
        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("localStudyProtocolIdentifier",
                           StConverter.convertToString(dto.getLocalStudyProtocolIdentifier()));
        if (!ISOUtil.isIiNull(dto.getResearchOrganizationIi())
            && IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equalsIgnoreCase(dto.getResearchOrganizationIi()
                                                                            .getIdentifierName())) {
            ResearchOrganization ro = corrUtils.getStructuralRoleByIi(dto.getResearchOrganizationIi());
            query.setParameter("orgIdentifier", ro.getId());
        } else {
            query.setParameter("orgIdentifier", IiConverter.convertToLong(dto.getResearchOrganizationIi()));
        }
        // step 3: query the result
        queryList = query.list();
        if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equalsIgnoreCase(dto.getFunctionalCode().getCode())) {
            for (StudySite sp : queryList) {
                // When create DTO get Id will be null and if queryList is having value then its duplicate
                // When update check if the record is same if not then throw ex
                if ((dto.getIdentifier() == null)
                    || (!String.valueOf(sp.getId()).equals(dto.getIdentifier().getExtension()))) {
                    throw new PAValidationException("Duplicate Trial Submission: A trial exists in the system with the "
                        + "same Lead Organization Trial Identifier for the selected Lead Organization");
                }
            }
        }
        if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getCode().equalsIgnoreCase(dto.getFunctionalCode().getCode())
            && CollectionUtils.isNotEmpty(queryList)) {
            for (StudySite ss : queryList) {
                // When create DTO get Id will be null and if queryList is having value then its duplicate
                // When update check if the record is same if not then throw ex
                String ssNciId = paServiceUtil.getTrialNciId(ss.getStudyProtocol().getId());
                if ((dto.getIdentifier() == null && dto.getStudyProtocolIdentifier() == null)
                    || (!ISOUtil.isIiNull(dto.getIdentifier()) 
                            && !String.valueOf(ss.getId()).equals(dto.getIdentifier().getExtension()))
                            || (StringUtils.isNotEmpty(nciId) && !nciId.equals(ssNciId))) {
                    StringBuffer sbuf = new StringBuffer();
                    sbuf.append("The ").append(getIdentifierName(ss))
                    .append(" provided is tied to another trial in CTRP system.\n")
                    .append(getTrialInfo(ss))
                    .append("Please check the ID provided and try again.");

                    if (!CSMUserService.getInstance().isCurrentUserAbstractor()) {
                        sbuf.append(" If you believe that the ID provided is correct then please contact CTRO staff.");
                    }
                    throw new PAValidationException(sbuf.toString());
                }
            }
        }
    }

    private String getTrialInfo(StudySite ss) {
        StringBuffer sbuf = new StringBuffer();
        try {
            StudyProtocol sp = ss.getStudyProtocol();
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    IiConverter.convertToIi(sp.getId()));
            StudyProtocolQueryDTO spqDTO = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                    sp.getId());
            sbuf.append("NCI ID: ").append(PAUtil.getAssignedIdentifierExtension(spDTO))
            .append("\nLead Org ID: ").append(spqDTO.getLocalStudyProtocolIdentifier())
            .append("\nTitle: ")
            .append(spDTO.getOfficialTitle().getValue()).append(".\n");
                        
        } catch (Exception e) {
            LOG.error(e, e);
        }
        return sbuf.toString();
    }

    private String getIdentifierName(StudySite sp) {
        StringBuffer sbuf = new StringBuffer();
        String spOrgName = sp.getResearchOrganization().getOrganization().getName();
        if (AssignedIdentifierEnum.NCT.getDisplayValue().equals(spOrgName)) {
            sbuf.append(AssignedIdentifierEnum.NCT.getCode());
        } else if (AssignedIdentifierEnum.DCP.getDisplayValue().equals(spOrgName)) {
            sbuf.append(AssignedIdentifierEnum.DCP.getCode());
        } else if (AssignedIdentifierEnum.CTEP.getDisplayValue().equals(spOrgName)) {
            sbuf.append(AssignedIdentifierEnum.CTEP.getCode());
        } else if (AssignedIdentifierEnum.CCR.getDisplayValue().equals(spOrgName)) {
            sbuf.append(AssignedIdentifierEnum.CCR.getCode());
        } 

        return sbuf.append(" Trial Identifier").toString();
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteDTO> search(StudySiteDTO dto, LimitOffset pagingParams) throws PAException,
        TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudySiteDTO should not be null");
        }

        StudySite criteria = Converters.get(StudySiteConverter.class).convertFromDtoToDomain(dto);

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudySite> params = new PageSortParams<StudySite>(maxLimit, pagingParams.getOffset(),
                StudySiteSortCriterion.STUDY_SITE_ID, false);
        List<StudySite> studySiteList = search(new AnnotatedBeanSearchCriteria<StudySite>(criteria), params);

        if (studySiteList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTOs(studySiteList);
    }

    /**
     * @param dto dto
     * @throws PAException e
     */
    @Override
    public void validate(StudySiteDTO dto) throws PAException {
        enforceNoDuplicateTrial(dto);
    }

    private void getStatusCode(StudySiteDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        StructuralRole sr = null;
        if (!ISOUtil.isIiNull(dto.getHealthcareFacilityIi())) {
            Ii hcfIi = IiConverter.convertToPoHealthCareFacilityIi(dto.getHealthcareFacilityIi().getExtension());
            sr = paServiceUtil.getStructuralRole(hcfIi);
        }
        if (!ISOUtil.isIiNull(dto.getResearchOrganizationIi())) {
            Ii roIi = IiConverter.convertToPoResearchOrganizationIi(dto.getResearchOrganizationIi().getExtension());
            sr = paServiceUtil.getStructuralRole(roIi);
        }
        if (!ISOUtil.isIiNull(dto.getOversightCommitteeIi())) {
            Ii ocIi = IiConverter.convertToPoOversightCommitteeIi(dto.getOversightCommitteeIi().getExtension());
            sr = paServiceUtil.getStructuralRole(ocIi);
        }
        if (sr != null) {
            dto.setStatusCode(getFunctionalRoleStatusCode(CdConverter.convertStringToCd(sr.getStatusCode().getCode()),
                                                          ActStatusCode.ACTIVE));
        }

    }

    /**
     * getStudySiteIiByTrialAndPoHcfIi.
     * @param studyProtocolIi ii
     * @param poHcfIi ii
     * @return ii
     * @throws EntityValidationException when error
     * @throws CurationException when error
     * @throws PAException when error
     * @throws TooManyResultsException when error
     */
    public Ii getStudySiteIiByTrialAndPoHcfIi(Ii studyProtocolIi, Ii poHcfIi) throws EntityValidationException,
            CurationException, PAException, TooManyResultsException {
        StudySiteDTO criteria = new StudySiteDTO();
        criteria.setStudyProtocolIdentifier(studyProtocolIi);
        // get the pa hcf from the po hcf
        StructuralRole strRl = corrUtils.getStructuralRoleByIi(poHcfIi);
        Ii myIi = IiConverter.convertToPoHealthCareFacilityIi(strRl.getId().toString());
        criteria.setHealthcareFacilityIi(myIi);
        LimitOffset limit = new LimitOffset(1, 0);
        List<StudySiteDTO> freshStudySiteDTOList = search(criteria, limit);
        return freshStudySiteDTOList.get(0).getIdentifier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization getOrganizationByStudySiteId(Long ssid) {
        Session session = PaHibernateUtil.getCurrentSession();
        StudySite ss = (StudySite) session.get(StudySite.class, ssid);
        if (ss.getHealthCareFacility() != null) {
            return ss.getHealthCareFacility().getOrganization();
        }
        if (ss.getResearchOrganization() != null) {
            return ss.getResearchOrganization().getOrganization();
        }
        return ss.getOversightCommittee().getOrganization();
    }

    /**
     * @param corrUtils the corrUtils to set
     */
    public void setCorrUtils(CorrelationUtils corrUtils) {
        this.corrUtils = corrUtils;
    }

    /**
     * @return the corrUtils
     */
    public CorrelationUtils getCorrUtils() {
        return corrUtils;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Long> getAllAssociatedTrials(String poOrgId, StudySiteFunctionalCode functionalCode) throws PAException {
        Set<Long> result = new HashSet<Long>();
        if (poOrgId == null) {
            return result;
        }
        if (!ObjectUtils.equals(StudySiteFunctionalCode.TREATING_SITE, functionalCode)) {
            throw new PAException("Method does not currently support this functionalCode.");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "SELECT sp.id FROM StudyProtocol sp "
                + "JOIN sp.studySites ss JOIN ss.healthCareFacility role JOIN role.organization org "
                + "WHERE sp.statusCode = :statusCode AND ss.functionalCode = :functionalCode "
                + "AND org.identifier = :poOrgId";
        Query query = session.createQuery(hql);
        query.setParameter("statusCode", ActStatusCode.ACTIVE);
        query.setParameter("functionalCode", functionalCode);
        query.setParameter("poOrgId", poOrgId);
        result.addAll(query.list());
        return result;
    }

    @Override
    public Set<Long> getTrialsAssociatedWithTreatingSite(Long paHcfId) throws PAException {
        Set<Long> result = new HashSet<Long>();
        if (paHcfId == null) {
            return result;
        }
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "SELECT sp.id FROM StudyProtocol sp "
                + "JOIN sp.studySites ss JOIN ss.healthCareFacility role "
                + "WHERE sp.statusCode = :statusCode AND ss.functionalCode = :functionalCode "
                + "AND role.id = :paHcfId";
        Query query = session.createQuery(hql);
        query.setParameter("statusCode", ActStatusCode.ACTIVE);
        query.setParameter("functionalCode", StudySiteFunctionalCode.TREATING_SITE);
        query.setParameter("paHcfId", paHcfId);
        result.addAll(query.list());
        return result;
    }
}
