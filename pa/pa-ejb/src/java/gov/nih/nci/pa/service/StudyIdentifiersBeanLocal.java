package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.StudyIdentifierDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyIdentifierType;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Denis G. Krylov
 * 
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings("PMD.TooManyMethods")
public class StudyIdentifiersBeanLocal extends
        AbstractBaseSearchBean<StudyProtocol> implements
        StudyIdentifiersServiceLocal {

    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;

    @EJB
    private StudyProtocolServiceLocal studyProtocolService;

    @EJB
    private OrganizationCorrelationServiceRemote organizationCorrelationService;

    @EJB
    private MailManagerServiceLocal mailManagerService;

    @EJB
    private StudySiteServiceLocal studySiteService;

    private final ThreadLocal<Boolean> suppressEmailsToDcp = new ThreadLocal<Boolean>();

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyIdentifierDTO> getStudyIdentifiers(Ii studyProtocolID)
            throws PAException {
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        StudyProtocolQueryDTO queryDTO = findProtocolUsingSearch(studyProtocolID);
        StudyProtocolDTO spDTO = studyProtocolService
                .getStudyProtocol(studyProtocolID);
        List<StudyIdentifierDTO> list = new ArrayList<StudyIdentifierDTO>();
        addIdentifierToList(list, StudyIdentifierType.LEAD_ORG_ID,
                queryDTO.getLocalStudyProtocolIdentifier());
        addIdentifierToList(list, StudyIdentifierType.CTGOV,
                queryDTO.getNctIdentifier());
        addIdentifierToList(list, StudyIdentifierType.CTEP,
                queryDTO.getCtepId());
        addIdentifierToList(list, StudyIdentifierType.DCP, queryDTO.getDcpId());
        addIdentifierToList(list, StudyIdentifierType.CCR, queryDTO.getCcrId());

        for (Ii otherID : PAUtil.getOtherIdentifiers(spDTO)) {
            addOtherIdentifierToList(list, otherID);
        }
        sort(list);
        return list;
    }

    private void sort(List<StudyIdentifierDTO> studyIdentifiers) {
        // default sort order is the order in which study identifier types
        // appear in the enum.
        Collections.sort(studyIdentifiers,
                new Comparator<StudyIdentifierDTO>() {
                    @Override
                    public int compare(StudyIdentifierDTO dto1,
                            StudyIdentifierDTO dto2) {
                        return dto1.getType().ordinal()
                                - dto2.getType().ordinal();
                    }
                });

    }

    private void addOtherIdentifierToList(List<StudyIdentifierDTO> list,
            Ii otherID) {
        if (IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME
                .equalsIgnoreCase(otherID.getIdentifierName())) {
            addIdentifierToList(list, StudyIdentifierType.OBSOLETE_CTGOV,
                    otherID.getExtension());
        } else if (IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME
                .equalsIgnoreCase(otherID.getIdentifierName())) {
            addIdentifierToList(list, StudyIdentifierType.DUPLICATE_NCI,
                    otherID.getExtension());
        } else {
            addIdentifierToList(list, StudyIdentifierType.OTHER,
                    otherID.getExtension());
        }
    }

    private void addIdentifierToList(final List<StudyIdentifierDTO> list,
            StudyIdentifierType type, String value) {
        if (StringUtils.isNotBlank(value)) {
            list.add(new StudyIdentifierDTO(type, value));
        }
    }

    private StudyProtocolQueryDTO findProtocolUsingSearch(Ii studyProtocolID)
            throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        final Long numericStudyID = IiConverter.convertToLong(studyProtocolID);
        criteria.setStudyProtocolId(numericStudyID);
        criteria.setExcludeRejectProtocol(false);
        List<StudyProtocolQueryDTO> list = protocolQueryService
                .getStudyProtocolByCriteria(criteria);
        if (list.isEmpty()) {
            throw new PAException("Unable to find a study: " + numericStudyID);
        }
        return list.get(0);
    }

    /**
     * @param protocolQueryServiceLocal
     *            the protocolQueryServiceLocal to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryServiceLocal) {
        this.protocolQueryService = protocolQueryServiceLocal;
    }

    /**
     * @param studyProtocolService
     *            the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    @Override
    public void delete(Ii studyProtocolID, StudyIdentifierDTO dto)
            throws PAException {
        if (StudyIdentifierType.LEAD_ORG_ID.equals(dto.getType())) {
            throw new PAException(
                    "Lead Organization Identifier cannot be removed from a trial.");
        } else if (StudyIdentifierType.CTGOV.equals(dto.getType())) {
            StudyProtocolQueryDTO before = findProtocolUsingSearch(studyProtocolID);
            deleteIdentifierAssignerSite(studyProtocolID,
                    PAConstants.CTGOV_ORG_NAME);
            PaHibernateUtil.getCurrentSession().flush();
            StudyProtocolQueryDTO after = findProtocolUsingSearch(studyProtocolID);
            sendNctChangeEmailIfNeeded(studyProtocolID, before, after);
        } else if (StudyIdentifierType.CTEP.equals(dto.getType())) {
            deleteIdentifierAssignerSite(studyProtocolID,
                    PAConstants.CTEP_ORG_NAME);
        } else if (StudyIdentifierType.DCP.equals(dto.getType())) {
            deleteIdentifierAssignerSite(studyProtocolID,
                    PAConstants.DCP_ORG_NAME);
        } else if (StudyIdentifierType.CCR.equals(dto.getType())) {
            deleteIdentifierAssignerSite(studyProtocolID,
                    PAConstants.CCR_ORG_NAME);
        } else {
            deleteOtherIdentifier(studyProtocolID, dto.getType(),
                    dto.getValue());
        }
    }

    private void deleteOtherIdentifier(Ii studyProtocolID,
            StudyIdentifierType type, String value) {
        String identifierName = determineOtherIdentifierName(type);
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class,
                IiConverter.convertToLong(studyProtocolID));
        for (Ii otherID : sp.getOtherIdentifiers()) {
            if (IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT.equals(otherID
                    .getRoot())
                    && identifierName.equals(otherID.getIdentifierName())
                    && StringUtils.equals(value, otherID.getExtension())) {
                Set<Ii> newSet = new LinkedHashSet<Ii>(sp.getOtherIdentifiers());
                newSet.remove(otherID);
                sp.setOtherIdentifiers(newSet);
                s.save(sp);
                s.flush();
                break;
            }
        }
    }

    private void deleteIdentifierAssignerSite(Ii studyProtocolID, String orgName) {
        Session s = PaHibernateUtil.getCurrentSession();
        StudySite ss = findIdentifierAssignerSite(studyProtocolID, orgName);
        if (ss != null) {
            s.delete(ss);
        }

    }

    private StudySite findIdentifierAssignerSite(Ii studyProtocolID,
            String orgName) {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class,
                IiConverter.convertToLong(studyProtocolID));
        for (StudySite ss : sp.getStudySites()) {
            if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.equals(ss
                    .getFunctionalCode())
                    && ss.getResearchOrganization() != null
                    && ss.getResearchOrganization().getOrganization() != null
                    && ss.getResearchOrganization().getOrganization().getName()
                            .equals(orgName)) {
                return ss;
            }
        }
        return null;
    }

    @Override
    public void add(Ii studyProtocolID, StudyIdentifierDTO dto)
            throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyIdentifierType type = dto.getType();
        String value = dto.getValue();
        if (type == null) {
            throw new PAException("Identifier type is missing");
        }
        if (StringUtils.isBlank(value)) {
            throw new PAValidationException("Identifier value is required");
        }
        if (StudyIdentifierType.LEAD_ORG_ID.equals(type)) {
            throw new PAException(
                    "Lead Organization Identifier cannot be added using this method. "
                            + "It is created at trial registration time and then can only be edited "
                            + "on General Trial Details/Trial Acceptance screens");
        } else if (StudyIdentifierType.CTGOV.equals(type)) {
            StudyProtocolQueryDTO before = findProtocolUsingSearch(studyProtocolID);
            addIdentifierAssignerSite(studyProtocolID,
                    PAConstants.CTGOV_ORG_NAME, value);
            s.flush();
            StudyProtocolQueryDTO after = findProtocolUsingSearch(studyProtocolID);
            sendNctChangeEmailIfNeeded(studyProtocolID, before, after);
        } else if (StudyIdentifierType.CTEP.equals(type)) {
            addIdentifierAssignerSite(studyProtocolID,
                    PAConstants.CTEP_ORG_NAME, value);
        } else if (StudyIdentifierType.DCP.equals(type)) {
            addIdentifierAssignerSite(studyProtocolID,
                    PAConstants.DCP_ORG_NAME, value);
        } else if (StudyIdentifierType.CCR.equals(type)) {
            addIdentifierAssignerSite(studyProtocolID,
                    PAConstants.CCR_ORG_NAME, value);
        } else {
            addOtherIdentifier(studyProtocolID, type, value);
        }
    }

    private void sendNctChangeEmailIfNeeded(Ii studyProtocolID,
            StudyProtocolQueryDTO before, StudyProtocolQueryDTO after) {
        if (StringUtils.isNotEmpty(after.getDcpId())
                && !StringUtils.equals(before.getNctIdentifier(),
                        after.getNctIdentifier())
                && !Boolean.TRUE.equals(suppressEmailsToDcp.get())) {
            mailManagerService.sendNCTIDChangeNotificationMail(studyProtocolID,
                    after.getNctIdentifier(), before.getNctIdentifier());
        }
    }

    private void addOtherIdentifier(Ii studyProtocolID,
            StudyIdentifierType type, String value) throws PAException {
        String identifierName = determineOtherIdentifierName(type);
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class,
                IiConverter.convertToLong(studyProtocolID));

        Set<Ii> newSet = new LinkedHashSet<Ii>(sp.getOtherIdentifiers());
        Ii otherID = new Ii();
        otherID.setExtension(value);
        otherID.setIdentifierName(identifierName);
        otherID.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);

        validateNewOtherIdAgainstExistingSet(otherID, studyProtocolID, newSet);

        newSet.add(otherID);
        sp.setOtherIdentifiers(newSet); 
        s.save(sp);
        s.flush();

    }

    private void validateNewOtherIdAgainstExistingSet(Ii otherID, // NOPMD
            Ii studyProtocolID, Collection<Ii> existing) throws PAException {
        for (Ii ii : existing) {
            final boolean sameExt = StringUtils.equals(ii.getExtension(),
                    otherID.getExtension());
            final boolean sameRoot = StringUtils.equals(ii.getRoot(),
                    otherID.getRoot());
            final boolean sameIdName = StringUtils.equals(
                    ii.getIdentifierName(), otherID.getIdentifierName());
            if (sameExt && sameRoot && sameIdName) {
                throw new PAValidationException(
                        "An identifier with the given type and value already exists");
            } else if (sameExt && sameRoot) {
                throw new PAValidationException(
                        "An identifier with the given value, albeit a different type, already exists. "
                                + "Duplicate NCI Identifiers, Obsolete ClinicalTrials.gov Identifier, "
                                + "and Other Identifier cannot share the same value");
            }
        }

        StudyProtocolQueryDTO queryDTO = findProtocolUsingSearch(studyProtocolID);
        if (StringUtils.isNotBlank(queryDTO.getNciIdentifier())
                && IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME
                        .equals(otherID.getIdentifierName())
                && StringUtils.equalsIgnoreCase(queryDTO.getNciIdentifier(),
                        otherID.getExtension())) {
            throw new PAValidationException(
                    "Duplicate NCI Identifier cannot match NCI Identifier of this trial");
        }
        if (StringUtils.isNotBlank(queryDTO.getNctIdentifier())
                && IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME
                        .equals(otherID.getIdentifierName())
                && StringUtils.equalsIgnoreCase(queryDTO.getNctIdentifier(),
                        otherID.getExtension())) {
            throw new PAValidationException(
                    "Obsolete ClinicalTrials.gov Identifier cannot match the current "
                            + "ClinicalTrials.gov Identifier of this trial");
        }

        if (IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME
                .equals(otherID.getIdentifierName())) {
            PAServiceUtils util = new PAServiceUtils();
            String nctValidationResultString = util.validateTrialObsoleteNctId(
                    IiConverter.convertToLong(studyProtocolID),
                    otherID.getExtension());
            if (StringUtils.isNotEmpty(nctValidationResultString)) {
                String duplicateErrorMessage = "This Obsolete ClinicalTrials.gov Identifier "
                        + "already exist on a different trial: "
                        + nctValidationResultString;
                throw new PAValidationException(duplicateErrorMessage);
            }
        }

    }

    /**
     * @param type
     */
    private String determineOtherIdentifierName(StudyIdentifierType type) {
        return type == StudyIdentifierType.OBSOLETE_CTGOV ? IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME
                : (type == StudyIdentifierType.DUPLICATE_NCI ? IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME
                        : IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
    }

    private void addIdentifierAssignerSite(Ii studyProtocolID, String orgName,
            String value) throws PAException {
        StudySite ss = findIdentifierAssignerSite(studyProtocolID, orgName);
        if (ss != null) {
            ss.setLocalStudyProtocolIdentifier(value);
            validateAndSaveIdentifierAssignerSite(ss);
        } else {
            createIdentifierAssignerSite(studyProtocolID, orgName, value);
        }
    }

    private void createIdentifierAssignerSite(Ii studyProtocolID,
            String orgName, String value) throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class,
                IiConverter.convertToLong(studyProtocolID));

        StudySite ss = new StudySite();
        ss.setStudyProtocol(sp);
        ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        ss.setLocalStudyProtocolIdentifier(value);
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);

        String poOrgId = organizationCorrelationService
                .getPOOrgIdentifierByOrgName(orgName);
        Long roID = organizationCorrelationService
                .createResearchOrganizationCorrelations(poOrgId);
        ss.setResearchOrganization((ResearchOrganization) s.load(
                ResearchOrganization.class, roID));

        validateAndSaveIdentifierAssignerSite(ss);

    }

    private void validateAndSaveIdentifierAssignerSite(StudySite ss)
            throws PAException {
        final StudySiteDTO dto = new StudySiteConverter()
                .convertFromDomainToDto(ss);
        studySiteService.validate(dto);
        if (ss.getId() == null) {
            studySiteService.create(dto);
        } else {
            studySiteService.update(dto);
        }
    }

    /**
     * @param organizationCorrelationService
     *            the organizationCorrelationService to set
     */
    public void setOrganizationCorrelationService(
            OrganizationCorrelationServiceRemote organizationCorrelationService) {
        this.organizationCorrelationService = organizationCorrelationService;
    }

    @Override
    public void update(Ii studyProtocolID, StudyIdentifierDTO dto,
            String newValue) throws PAException {
        if (StringUtils.isBlank(newValue)) {
            throw new PAValidationException("Identifier value is required");
        }
        if (StringUtils.equals(newValue, dto.getValue())) {
            return;
        }
        StudyIdentifierType type = dto.getType();
        if (StudyIdentifierType.LEAD_ORG_ID.equals(type)) {
            updateLeadOrganizationID(studyProtocolID, newValue);
        } else {
            updateNonLeadOrgIdentifier(studyProtocolID, dto, newValue);
        }
    }

    /**
     * @param studyProtocolID
     * @param dto
     * @param newValue
     * @throws PAException
     * @throws HibernateException
     */
    private void updateNonLeadOrgIdentifier(Ii studyProtocolID,
            StudyIdentifierDTO dto, String newValue) throws PAException {

        StudyProtocolQueryDTO before = findProtocolUsingSearch(studyProtocolID);

        try {
            // "delete" and "add" methods will try to send an email to DCP if
            // NCT number changes
            // need to suppress that.
            suppressEmailsToDcp.set(Boolean.TRUE);
            Session s = PaHibernateUtil.getCurrentSession();
            delete(studyProtocolID, dto);
            s.flush();
            s.clear();
            dto.setValue(newValue);
            add(studyProtocolID, dto);
            s.flush();
            s.clear();
        } finally {
            suppressEmailsToDcp.set(Boolean.FALSE);
        }

        StudyProtocolQueryDTO after = findProtocolUsingSearch(studyProtocolID);
        sendNctChangeEmailIfNeeded(studyProtocolID, before, after);

    }

    private void updateLeadOrganizationID(Ii studyProtocolID, String newValue)
            throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class,
                IiConverter.convertToLong(studyProtocolID));
        for (StudySite ss : sp.getStudySites()) {
            if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(ss
                    .getFunctionalCode())
                    && ss.getResearchOrganization() != null) {
                ss.setLocalStudyProtocolIdentifier(newValue);
                validateAndSaveIdentifierAssignerSite(ss);
            }
        }
    }

    /**
     * @param mailManagerService
     *            the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @param studySiteService
     *            the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

}
