/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.EligibilityCriteriaDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import ognl.NoSuchPropertyException;
import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * @author Denis G. Krylov
 * 
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "unchecked", "PMD.CyclomaticComplexity",
        "PMD.TooManyMethods" })
public class ProtocolComparisonServiceBean implements
        ProtocolComparisonServiceLocal {

    @EJB
    private PlannedActivityServiceLocal plannedActivityService;

    @EJB
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;

    @EJB
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService;

    @EJB
    private RegulatoryInformationServiceLocal regulatoryInformationService;

    private static final Logger LOG = Logger
            .getLogger(ProtocolComparisonServiceBean.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal#captureSnapshot
     * (java.lang.Long)
     */
    @Override
    public ProtocolSnapshot captureSnapshot(Long protocolID) throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class,
                protocolID);
        return captureSnapshot(sp);
    }

    private ProtocolSnapshot captureSnapshot(StudyProtocol sp)
            throws PAException {
        ProtocolSnapshotImpl snapshot = new ProtocolSnapshotImpl();
        snapshot.studyProtocol = sp;
        initializeCollections(sp);
        captureAssociations(snapshot, sp);
        PaHibernateUtil.getCurrentSession().clear();
        return snapshot;
    }

    private void initializeCollections(StudyProtocol sp) {
        sp.getStudyResourcings().size();        
    }

    private void captureAssociations(ProtocolSnapshotImpl snapshot,
            StudyProtocol sp) throws PAException {
        captureEligibilityCriteria(snapshot, sp);
        captureCollaborators(snapshot, sp);
        captureLeadOrgAndSponsor(snapshot, sp);
        captureOverallStatus(snapshot, sp);
        captureStudyContacts(snapshot, sp);
        captureArms(snapshot, sp);
        captureOutcomes(snapshot, sp);
        captureRegAuthority(snapshot, sp);
        captureSecondaryIDs(snapshot, sp);
    }

    private void captureSecondaryIDs(ProtocolSnapshotImpl snapshot,
            StudyProtocol sp) {
        for (Ii id : sp.getOtherIdentifiers()) {
            snapshot.secondaryIDs.add(StringUtils.defaultString(id
                    .getExtension()));
        }

    }

    private void captureRegAuthority(ProtocolSnapshotImpl snapshot,
            StudyProtocol sp) throws PAException {
        StudyRegulatoryAuthorityDTO sraDTO = studyRegulatoryAuthorityService
                .getCurrentByStudyProtocol(IiConverter
                        .convertToStudyProtocolIi(sp.getId()));
        if (sraDTO != null) {
            RegulatoryAuthority ra = regulatoryInformationService.get(Long
                    .valueOf(sraDTO.getRegulatoryAuthorityIdentifier()
                            .getExtension()));
            if (ra != null) {
                snapshot.regulatoryAuthority = ra.getAuthorityName();
            }
        }
    }

    private void captureOutcomes(ProtocolSnapshotImpl snapshot, StudyProtocol sp)
            throws PAException {
        for (StudyOutcomeMeasureDTO outcome : studyOutcomeMeasureService
                .getByStudyProtocol(IiConverter.convertToStudyProtocolIi(sp
                        .getId()))) {
            snapshot.outcomes
                    .add(StConverter.convertToString(outcome.getName()));
        }
    }

    private void captureArms(ProtocolSnapshotImpl snapshot, StudyProtocol sp) {
        for (Arm arm : sp.getArms()) {
            snapshot.arms.add(arm.getName());
        }
    }

    private void captureStudyContacts(ProtocolSnapshotImpl snapshot,
            StudyProtocol studyProtocol) {
        for (StudyContact sc : studyProtocol.getStudyContacts()) {
            final ClinicalResearchStaff crs = sc.getClinicalResearchStaff();
            if (crs != null && crs.getPerson() != null) {
                Person person = crs.getPerson();
                if (StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR
                        .equals(sc.getRoleCode())
                        || StudyContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR
                                .equals(sc.getRoleCode())) {
                    snapshot.responsibleParty = person.getFullName();
                } else if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR
                        .equals(sc.getRoleCode())) {
                    snapshot.pi = person.getFullName();
                } else if (StudyContactRoleCode.CENTRAL_CONTACT.equals(sc
                        .getRoleCode())) {
                    snapshot.centralContact = person.getFullName();
                }
            }

        }

    }

    private void captureOverallStatus(ProtocolSnapshotImpl snapshot,
            StudyProtocol studyProtocol) {
        StudyOverallStatus status = studyProtocol.getStudyOverallStatuses()
                .isEmpty() ? null : studyProtocol.getStudyOverallStatuses()
                .iterator().next();
        if (status != null) {
            snapshot.status = status;
        }
    }

    private void captureLeadOrgAndSponsor(ProtocolSnapshotImpl snapshot,
            StudyProtocol studyProtocol) {
        for (StudySite studySite : studyProtocol.getStudySites()) {
            if (StudySiteFunctionalCode.SPONSOR.equals(studySite
                    .getFunctionalCode()) && roNotNull(studySite)) {
                snapshot.sponsor = studySite.getResearchOrganization()
                        .getOrganization().getName();
            }
            if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(studySite
                    .getFunctionalCode()) && roNotNull(studySite)) {
                snapshot.leadOrganization = studySite.getResearchOrganization()
                        .getOrganization().getName();
                snapshot.leadOrganizationTrialId = studySite
                        .getLocalStudyProtocolIdentifier();
            }
        }

    }

    private void captureCollaborators(ProtocolSnapshotImpl snapshot,
            StudyProtocol studyProtocol) {
        for (StudySite studySite : studyProtocol.getStudySites()) {
            if (studySite.getFunctionalCode() != null
                    && studySite.getFunctionalCode().isCollaboratorCode()
                    && roNotNull(studySite)) {
                snapshot.collaborators.add(studySite.getResearchOrganization()
                        .getOrganization().getName());
            }
        }
    }

    private void captureEligibilityCriteria(ProtocolSnapshotImpl snapshot,
            StudyProtocol sp) throws PAException {
        EligibilityCriteriaDTO eligibilityCriteriaDTO = new EligibilityCriteriaDTO();
        StudyProtocolDTO studyProtocolDto = PADomainUtils
                .convertStudyProtocol(sp);
        List<PlannedEligibilityCriterionDTO> paECs = plannedActivityService
                .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolDto
                        .getIdentifier());
        if (CollectionUtils.isNotEmpty(paECs)) {
            TSRReportGeneratorServiceBean.processAndDescribeEligCriteria(
                    studyProtocolDto, paECs, eligibilityCriteriaDTO);
        }
        snapshot.eligibilityCriteria = eligibilityCriteriaDTO;

    }

    private boolean roNotNull(StudySite studySite) {
        return studySite.getResearchOrganization() != null
                && studySite.getResearchOrganization().getOrganization() != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal#compare(gov
     * .nih.nci.pa.service.util.ProtocolComparisonServiceLocal.ProtocolSnapshot,
     * gov
     * .nih.nci.pa.service.util.ProtocolComparisonServiceLocal.ProtocolSnapshot)
     */
    @Override
    public Collection<Difference> compare(ProtocolSnapshot before,
            ProtocolSnapshot after, Collection<String> ognlExpr)
            throws PAException {
        Collection<Difference> diffs = new ArrayList<Difference>();
        for (String ognl : ognlExpr) {
            Difference diff = compare(before, after, ognl);
            if (diff != null) {
                diffs.add(diff);
            }
        }
        return diffs;
    }

    private Difference compare(ProtocolSnapshot before, ProtocolSnapshot after,
            String ognl) throws PAException {
        try {
            Object valueBefore = Ognl.getValue(ognl, before);
            Object valueAfter = Ognl.getValue(ognl, after);
            return compare(valueBefore, valueAfter, ognl);
        } catch (NoSuchPropertyException e) {
            LOG.error(e, e);
            return null;
        } catch (OgnlException e) {
            LOG.error(e, e);
            throw new PAException(e.getMessage(), e);
        }

    }

    private Difference compare(Object valueBefore, Object valueAfter,
            String fieldKey) {
        if (!ObjectUtils.equals(valueBefore, valueAfter)) {
            return new DifferenceImpl(fieldKey, valueBefore, valueAfter);
        } else {
            return null;
        }
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    // CHECKSTYLE:OFF
    private static final class ProtocolSnapshotImpl implements ProtocolSnapshot {

        StudyProtocol studyProtocol;
        EligibilityCriteriaDTO eligibilityCriteria;
        Collection<String> collaborators = new TreeSet<String>();
        Collection<String> arms = new TreeSet<String>();
        Collection<String> outcomes = new TreeSet<String>();
        Collection<String> secondaryIDs = new TreeSet<String>();
        String leadOrganization;
        String leadOrganizationTrialId;
        String sponsor;
        StudyOverallStatus status;
        String pi;
        String responsibleParty;
        String centralContact;
        String regulatoryAuthority;

        /**
         * @return the studyProtocol
         */
        public StudyProtocol getStudyProtocol() {
            return studyProtocol;
        }

        /**
         * @return the eligibilityCriteria
         */
        public EligibilityCriteriaDTO getEligibilityCriteria() {
            return eligibilityCriteria;
        }

        /**
         * @return the collaborators
         */
        public Collection<String> getCollaborators() {
            return collaborators;
        }

        /**
         * @return the arms
         */
        public Collection<String> getArms() {
            return arms;
        }

        /**
         * @return the outcomes
         */
        public Collection<String> getOutcomes() {
            return outcomes;
        }

        /**
         * @return the secondaryIDs
         */
        public Collection<String> getSecondaryIDs() {
            return secondaryIDs;
        }

        /**
         * @return the leadOrganization
         */
        public String getLeadOrganization() {
            return leadOrganization;
        }

        /**
         * @return the leadOrganizationTrialId
         */
        public String getLeadOrganizationTrialId() {
            return leadOrganizationTrialId;
        }

        /**
         * @return the sponsor
         */
        public String getSponsor() {
            return sponsor;
        }

        /**
         * @return the status
         */
        public StudyOverallStatus getStatus() {
            return status;
        }

        /**
         * @return the pi
         */
        public String getPi() {
            return pi;
        }

        /**
         * @return the responsibleParty
         */
        public String getResponsibleParty() {
            return responsibleParty;
        }

        /**
         * @return the centralContact
         */
        public String getCentralContact() {
            return centralContact;
        }

        /**
         * @return the regulatoryAuthority
         */
        public String getRegulatoryAuthority() {
            return regulatoryAuthority;
        }
        

    }

    private static final class DifferenceImpl implements Difference {

        private final String fieldKey;

        private final Object oldValue, newValue;

        /**
         * @param fieldKey
         * @param oldValue
         * @param newValue
         */
        public DifferenceImpl(String fieldKey, Object oldValue, Object newValue) {
            this.fieldKey = fieldKey;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        /**
         * @return the fieldKey
         */
        public String getFieldKey() {
            return fieldKey;
        }

        /**
         * @return the oldValue
         */
        public Object getOldValue() {
            return oldValue;
        }

        /**
         * @return the newValue
         */
        public Object getNewValue() {
            return newValue;
        }

    }

    /**
     * @param plannedActivityService
     *            the plannedActivityService to set
     */
    public void setPlannedActivityService(
            PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }

    /**
     * @param studyOutcomeMeasureService
     *            the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(
            StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }

    /**
     * @param studyRegulatoryAuthorityService
     *            the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(
            StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService) {
        this.studyRegulatoryAuthorityService = studyRegulatoryAuthorityService;
    }

    /**
     * @param regulatoryInformationService
     *            the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(
            RegulatoryInformationServiceLocal regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

}
