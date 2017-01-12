/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyDocumentStage;
import gov.nih.nci.pa.domain.StudyFundingStage;
import gov.nih.nci.pa.domain.StudyIndIdeStage;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.convert.OrganizationalContactConverter;
import gov.nih.nci.pa.iso.convert.StudyDocumentStageConverter;
import gov.nih.nci.pa.iso.convert.StudyFundingStageConverter;
import gov.nih.nci.pa.iso.convert.StudyIndIdeStageConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolStageConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.correlation.PABaseCorrelation;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyProtocolStageSortCriterion;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Vrushali
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyProtocolStageBeanLocal extends AbstractBaseSearchBean<StudyProtocolStage>
    implements StudyProtocolStageServiceLocal {

    private static final String II_CAN_NOT_BE_NULL = "Ii cannot be null";
    private static final Logger LOG  = Logger.getLogger(StudyProtocolStageBeanLocal.class);
    @EJB
    private MailManagerServiceLocal mailManagerSerivceLocal;
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private RegistryUserServiceLocal registryUserServiceLocal;

    /**
     * @param dto dto
     * @param pagingParams pagingParams
     * @return list
     * @throws PAException on err.
     * @throws TooManyResultsException on err
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolStageDTO> search(StudyProtocolStageDTO dto, LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyProtocolDTO should not be null.");
        }
        StudyProtocolStage criteria = new StudyProtocolStage();
        criteria.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(dto.getPhaseCode())));
        criteria.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.getByCode(
                CdConverter.convertCdToString(dto.getPhaseAdditionalQualifierCode())));
        criteria.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(
                CdConverter.convertCdToString(dto.getPrimaryPurposeCode())));
        if (!ISOUtil.isStNull(dto.getOfficialTitle())) {
            criteria.setOfficialTitle(StConverter.convertToString(dto.getOfficialTitle()));
        }
        if (!ISOUtil.isStNull(dto.getNctIdentifier())) {
            criteria.setNctIdentifier(StConverter.convertToString(dto.getNctIdentifier()));
        }
        if (!ISOUtil.isStNull(dto.getLocalProtocolIdentifier())) {
            criteria.setLocalProtocolIdentifier(StConverter.convertToString(dto.getLocalProtocolIdentifier()));
        }
        if (dto.getSecondaryIdentifierList() != null && !dto.getSecondaryIdentifierList().isEmpty()) {
            Set<Ii> otherIdentifiers = new HashSet<Ii>(dto.getSecondaryIdentifierList());
            criteria.setOtherIdentifiers(otherIdentifiers);
        }
        if (!ISOUtil.isIiNull(dto.getPiIdentifier())) {
            criteria.setPiIdentifier(IiConverter.convertToString(dto.getPiIdentifier()));
        }

        if (!ISOUtil.isStNull(dto.getUserLastCreated())) {
            String userName = dto.getUserLastCreated().getValue();
            try {
                criteria.setUserLastCreated(CSMUserService.getInstance().getCSMUser(userName));
            } catch (PAException e) {
                LOG.info("Exception in setting userLastCreated for Study Protocol Stage: "
                        + dto.getIdentifier() + ", for username" + userName, e);
            }
        }

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyProtocolStage> params = new PageSortParams<StudyProtocolStage>(maxLimit,
                pagingParams.getOffset(), StudyProtocolStageSortCriterion.STUDY_PROTOCOL_STAGE_ID, false);
        List<StudyProtocolStage> studyProtocolList =
            search(new AnnotatedBeanSearchCriteria<StudyProtocolStage>(criteria), params);
        if (studyProtocolList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTO(studyProtocolList);
    }

    /**
     * @param ii ii to delete
     * @throws PAException e
     */
    public void delete(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocolStage tempSp = (StudyProtocolStage)
            session.load(StudyProtocolStage.class, Long.valueOf(ii.getExtension()));
        session.delete(tempSp);
        deleteDocumentsFromFileSystem(ii);
    }

    /**
     * @param studyProtocolStageii
     * @throws PAException
     */
    private void deleteDocumentsFromFileSystem(Ii studyProtocolStageii) throws PAException {
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StringBuffer fileName = new StringBuffer(studyProtocolStageii.getExtension());
        File dirToDelete = new File(folderPath + File.separator + fileName);
        try {
            FileUtils.deleteDirectory(dirToDelete);
        } catch (IOException e) {
            throw new PAException(e);
        }
    }

    /**
     * @param ii ii
     * @return Dto
     * @throws PAException on err
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolStageDTO get(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        StudyProtocolStage studyProtocol = null;
        Session session = PaHibernateUtil.getCurrentSession();
        studyProtocol = (StudyProtocolStage)
        session.get(StudyProtocolStage.class, Long.valueOf(ii.getExtension()));
        if (studyProtocol == null) {
            throw new PAException("Ii could not be found.");
        }
        return new StudyProtocolStageConverter().convertFromDomainToDto(studyProtocol);
    }

    /**
     *
     * @param studyProtocolStageIi ii
     * @return list
     * @throws PAException e
     */
    public List<StudyFundingStageDTO> getGrantsByStudyProtocolStage(Ii studyProtocolStageIi)throws PAException {
        if (ISOUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        List<StudyFundingStageDTO> resultList = new ArrayList<StudyFundingStageDTO>();
        String hql = "select spart from StudyFundingStage spart join spart.studyProtocolStage spro where spro.id = "
            + " :studyProtocolId";
        List<? extends AbstractEntity> queryList = getResultList(studyProtocolStageIi, hql);
        for (AbstractEntity bo : queryList) {
            resultList.add(StudyFundingStageConverter.convertFromDomainToDTO((StudyFundingStage) bo));
        }
        return resultList;
    }

    /**
     * @param studyProtocolStageIi ii
     * @return List
     * @throws PAException e
     */
    @SuppressWarnings("unchecked")
    public List<StudyIndIdeStageDTO> getIndIdesByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        String hql = "select spart from StudyIndIdeStage spart join spart.studyProtocolStage spro where spro.id = "
            + " :studyProtocolId";
        List<StudyIndIdeStage> queryList = (List<StudyIndIdeStage>) getResultList(studyProtocolStageIi, hql);
        return new StudyIndIdeStageConverter().convertFromDomainToDtos(queryList);
    }

    /**
     * @param ispDTO dto
     * @param fundDTOs dto
     * @param indDTOs dto
     * @param docDTOs for document
     * @return ii
     * @throws PAException on err
     */
    public Ii create(StudyProtocolStageDTO ispDTO, List<StudyFundingStageDTO> fundDTOs,
            List<StudyIndIdeStageDTO> indDTOs, List<DocumentDTO> docDTOs) throws PAException {
        Ii studyProtocolStageIi = createOrUpdateStudyProtocol(ispDTO, "Create");
        createGrants(fundDTOs, studyProtocolStageIi);
        createIndIde(indDTOs, studyProtocolStageIi);
        createDocuments(docDTOs, studyProtocolStageIi);
        sendPartialSubmissionMail(studyProtocolStageIi);
        return studyProtocolStageIi;
    }

    /**
     * @param docDTOs
     * @param studyProtocolStageIi
     * @throws PAException on err
     */
    private void createDocuments(List<DocumentDTO> docDTOs, Ii studyProtocolStageIi) throws PAException {
        if (CollectionUtils.isNotEmpty(docDTOs)) {
            if (ISOUtil.isIiNull(studyProtocolStageIi)) {
                throw new PAException("StudyProtocolStageIi can not be null");
            }
            Session session = PaHibernateUtil.getCurrentSession();
            for (DocumentDTO docDTO : docDTOs) {
                docDTO.setStudyProtocolIdentifier(studyProtocolStageIi);
                StudyDocumentStage docStage = new StudyDocumentStageConverter().convertFromDtoToDomain(docDTO);
                docStage.setDateLastCreated(new Timestamp((new Date()).getTime()));
                session.save(docStage);
                docDTO.setIdentifier(IiConverter.convertToDocumentIi(docStage.getId()));
                saveFile(docDTO, studyProtocolStageIi);

            }
        }


    }

    /**
     * @param isoDTO  for spStage
     *  @param fundDTOs for funding
     *  @param indDTOs for ind
     *  @param docDTOs for document
     *  @return dto
     *  @throws PAException on err
     */
    public StudyProtocolStageDTO update(StudyProtocolStageDTO isoDTO, List<StudyFundingStageDTO> fundDTOs,
            List<StudyIndIdeStageDTO> indDTOs, List<DocumentDTO> docDTOs) throws PAException {
        Ii spIi = createOrUpdateStudyProtocol(isoDTO, "update");
        deleteGrants(spIi);
        deleteIndIdesForStudyProtocolStage(spIi);
        deleteDocuments(spIi);
        createGrants(fundDTOs, spIi);
        createIndIde(indDTOs, spIi);
        createDocuments(docDTOs, spIi);
        return get(spIi);

    }

    private Ii createOrUpdateStudyProtocol(StudyProtocolStageDTO isoDTO, String operation)
            throws PAException {
        StudyProtocolStage sp = new StudyProtocolStageConverter().convertFromDtoToDomain(isoDTO);
        Session session = PaHibernateUtil.getCurrentSession();
        if ("Create".equalsIgnoreCase(operation)) {
            sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
            User user = null;
            try {
                if (!ISOUtil.isStNull(isoDTO.getUserLastCreated())) {
                    user =  CSMUserService.getInstance().getCSMUser(isoDTO.getUserLastCreated().getValue());
                }
            } catch (PAException e) {
                LOG.error("Unable to set User for auditing", e);
            }
            sp.setUserLastCreated(user);
            session.save(sp);
        } else {
            sp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(sp);
        }
        PAServiceUtils paServiceUtil  = new PAServiceUtils();
        createPAOrganizationByPoId(paServiceUtil, sp.getLeadOrganizationIdentifier());
        //createPAOrganizationByPoId(paServiceUtil, sp.getSiteSummaryFourOrgIdentifier());
        for (String sfOrgId : sp.getSummaryFourOrgIdentifiers()) {
            createPAOrganizationByPoId(paServiceUtil, sfOrgId);
        }
        createPAOrganizationByPoId(paServiceUtil, sp.getSponsorIdentifier());
        createPAOrganizationByPoId(paServiceUtil, sp.getSubmitterOrganizationIdentifier());
        createPAPersonByPoId(paServiceUtil, sp.getPiIdentifier());
        createPAPersonByPoId(paServiceUtil, sp.getResponsibleIdentifier());
        createPAPersonByPoId(paServiceUtil, sp.getSitePiIdentifier());
        createPAOrgContactByPoId(sp);
        return IiConverter.convertToStudyProtocolIi(sp.getId());
    }

    private Long createPAOrgContactByPoId(
            StudyProtocolStage sp) throws PAException {
        if (StringUtils.isNotBlank(sp.getResponsibleOcIdentifier())) {
            PABaseCorrelation<PAOrganizationalContactDTO, OrganizationalContactDTO, OrganizationalContact, 
            OrganizationalContactConverter> oc = new PABaseCorrelation<PAOrganizationalContactDTO, 
            OrganizationalContactDTO, OrganizationalContact, OrganizationalContactConverter>(
                    PAOrganizationalContactDTO.class,
                    OrganizationalContact.class,
                    OrganizationalContactConverter.class);
            PAOrganizationalContactDTO orgContacPaDto = new PAOrganizationalContactDTO();
            orgContacPaDto.setOrganizationIdentifier(IiConverter
                    .convertToPoOrganizationIi(sp.getSponsorIdentifier()));
            orgContacPaDto.setIdentifier(IiConverter
                    .convertToPoOrganizationalContactIi(sp
                            .getResponsibleOcIdentifier()));
            orgContacPaDto.setTypeCode(PAConstants.RESPONSIBLE_PARTY);
            return oc.create(orgContacPaDto);
        }
        return null;
    }

    private void createPAOrganizationByPoId(PAServiceUtils paServiceUtil, String id) throws PAException {
        if (!StringUtils.isEmpty(id)) {
            paServiceUtil.getOrCreatePAOrganizationByIi(IiConverter
                    .convertToPoOrganizationIi(id));
        }
    }

    private void createPAPersonByPoId(PAServiceUtils paServiceUtil, String id) throws PAException {
        if (!StringUtils.isEmpty(id)) {
            paServiceUtil.getOrCreatePAPersonByPoIi(IiConverter
                    .convertToPoPersonIi(id));
        }
    }

    private List<StudyProtocolStageDTO> convertFromDomainToDTO(List<StudyProtocolStage> studyProtocolList) {
        List<StudyProtocolStageDTO> studyProtocolDTOList = null;
        if (studyProtocolList != null) {
            studyProtocolDTOList = new ArrayList<StudyProtocolStageDTO>();
            for (StudyProtocolStage sp : studyProtocolList) {
                StudyProtocolStageDTO studyProtocolDTO = new StudyProtocolStageConverter().convertFromDomainToDto(sp);
                studyProtocolDTOList.add(studyProtocolDTO);
            }
        }
        return studyProtocolDTOList;
    }

    private void createGrants(List<StudyFundingStageDTO> studyFundingStageDTOs,
            Ii studyProtocolStageIi) throws PAException {
        if (CollectionUtils.isNotEmpty(studyFundingStageDTOs)) {
            if (ISOUtil.isIiNull(studyProtocolStageIi)) {
                throw new PAException("StudyProtocolStageIi can not be null");
            }
            Session session = PaHibernateUtil.getCurrentSession();
            for (StudyFundingStageDTO fundingDTO : studyFundingStageDTOs) {
                fundingDTO.setStudyProtocolStageIi(studyProtocolStageIi);
                StudyFundingStage studyFundingStage = StudyFundingStageConverter.convertFromDTOToDomain(
                        fundingDTO);
                studyFundingStage.setDateLastCreated(new Timestamp((new Date()).getTime()));
                session.save(studyFundingStage);
            }
        }

    }

    private void deleteGrants(Ii studyProtocolStageIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        StringBuffer sql = new StringBuffer("DELETE FROM STUDY_FUNDING_STAGE WHERE STUDY_PROTOCOL_STAGE_IDENTIFIER  = ")
            .append(IiConverter.convertToString(studyProtocolStageIi));
        PaHibernateUtil.getCurrentSession().createSQLQuery(sql.toString()).executeUpdate();
    }

    private void createIndIde(List<StudyIndIdeStageDTO> studyIndIdeStageDTOs, Ii studyProtocolStageIi)
            throws PAException {
        if (CollectionUtils.isNotEmpty(studyIndIdeStageDTOs)) {
            if (ISOUtil.isIiNull(studyProtocolStageIi)) {
                throw new PAException("StudyProtocolStageIi can not be null");
            }
            Session session = PaHibernateUtil.getCurrentSession();
            for (StudyIndIdeStageDTO indDto : studyIndIdeStageDTOs) {
                indDto.setStudyProtocolStageIi(studyProtocolStageIi);
                StudyIndIdeStage studyIndIdeStage = new StudyIndIdeStageConverter().convertFromDtoToDomain(indDto);
                studyIndIdeStage.setDateLastCreated(new Timestamp((new Date()).getTime()));
                session.save(studyIndIdeStage);
            }
        }
    }

    private void deleteIndIdesForStudyProtocolStage(Ii studyProtocolStageIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        StringBuffer sql = new StringBuffer("DELETE FROM STUDY_INDIDE_STAGE WHERE STUDY_PROTOCOL_STAGE_IDENTIFIER  = ")
            .append(IiConverter.convertToString(studyProtocolStageIi));
        PaHibernateUtil.getCurrentSession().createSQLQuery(sql.toString()).executeUpdate();

    }

    private void sendPartialSubmissionMail(Ii studyProtocolStageIi) {
       try {
            PAServiceUtils paServiceUtil = new PAServiceUtils();
            StudyProtocolStageDTO spDTO = get(studyProtocolStageIi);
            String submissionMailBody = lookUpTableService.getPropertyValue("trial.partial.register.body");
            submissionMailBody = submissionMailBody.replace("${CurrentDate}", PAUtil.today());
            submissionMailBody = submissionMailBody.replace("${submissionDate}", PAUtil.today());
            submissionMailBody = submissionMailBody.replace("${leadOrgTrialIdentifier}",
                StConverter.convertToString(spDTO.getLocalProtocolIdentifier()));
            submissionMailBody = submissionMailBody.replace("${leadOrgName}",
                    paServiceUtil.getOrgName(IiConverter.convertToPoOrganizationIi(
                        spDTO.getLeadOrganizationIdentifier().getExtension())));
            submissionMailBody = submissionMailBody.replace("${temporaryidentifier}", IiConverter.convertToString(
                spDTO.getIdentifier()));
            String title = "";
            if (!ISOUtil.isStNull(spDTO.getOfficialTitle())) {
                title = StConverter.convertToString(spDTO.getOfficialTitle());
            }
            submissionMailBody = submissionMailBody.replace("${trialTitle}", title);
            RegistryUser registryUser = registryUserServiceLocal.getUser(
                    StConverter.convertToString(spDTO.getUserLastCreated()));
            submissionMailBody = submissionMailBody.replace("${SubmitterName}",
                 registryUser.getFirstName() + " " + registryUser.getLastName());

            String mailSubject = lookUpTableService.getPropertyValue("trial.partial.register.subject");
            mailSubject = mailSubject.replace("${leadOrgTrialIdentifier}",
                StConverter.convertToString(spDTO.getLocalProtocolIdentifier()));
            mailManagerSerivceLocal.sendMailWithHtmlBody(registryUser.getEmailAddress(), 
                    mailSubject, submissionMailBody);
          } catch (Exception e) {
               LOG.error("Send Mail error Partial Submission Mail", e);
          }
    }

    /**
     * {@inheritDoc}
     */
    public List<DocumentDTO> getDocumentsByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        List<DocumentDTO> resultList = new ArrayList<DocumentDTO>();

        String hql = "select doc from StudyDocumentStage doc join doc.studyProtocolStage spro where spro.id = "
            +  ":studyProtocolId";
        List<? extends AbstractEntity> queryList = getResultList(studyProtocolStageIi, hql);
        for (AbstractEntity bo : queryList) {
            DocumentDTO docDTO = new StudyDocumentStageConverter().convertFromDomainToDto((StudyDocumentStage) bo);
            if (docDTO != null) {
                try {
                    StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
                    sb.append(File.separator).append(studyProtocolStageIi.getExtension()).append(File.separator)
                      .append(docDTO.getIdentifier().getExtension()).append('-')
                      .append(StConverter.convertToString(docDTO.getFileName()));
                    
                    File downloadFile = new File(sb.toString());
                    final InputStream stream = FileUtils.openInputStream(downloadFile);
                    try {
                        docDTO.setText(EdConverter.convertToEd(
                            IOUtils.toByteArray(stream)));
                    } finally {
                        IOUtils.closeQuietly(stream);
                    }
                    
                } catch (FileNotFoundException fe) {
                    throw new PAException("File Not found " + fe.getLocalizedMessage(), fe);
                } catch (IOException io) {
                    throw new PAException("IO Exception" + io.getLocalizedMessage(), io);
                }
                resultList.add(docDTO);
            }
        }
        return resultList;
    }

    private void deleteDocuments(Ii studyProtocolStageIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException(II_CAN_NOT_BE_NULL);
        }
        StringBuffer sql = new StringBuffer(
                "DELETE FROM STUDY_DOCUMENT_STAGE WHERE STUDY_PROTOCOL_STAGE_IDENTIFIER  = ")
                .append(IiConverter.convertToString(studyProtocolStageIi));
        PaHibernateUtil.getCurrentSession().createSQLQuery(sql.toString()).executeUpdate();
        deleteDocumentsFromFileSystem(studyProtocolStageIi);
    }
    /**
     * @param studyProtocolStageIi
     * @param hql
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<? extends AbstractEntity> getResultList(Ii studyProtocolStageIi, String hql) {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolStageIi));
        return query.list();
    }

    private void saveFile(DocumentDTO docDTO, Ii studyProtocolIi) throws PAException {
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StringBuffer fileName = new StringBuffer(studyProtocolIi.getExtension());
        fileName.append(File.separator).append(docDTO.getIdentifier().getExtension()).append('-').append(
                docDTO.getFileName().getValue());
        File fileToUpload = new File(folderPath + File.separator + fileName);
        try {            
            FileUtils.writeByteArrayToFile(fileToUpload, docDTO.getText().getData());           
        } catch (IOException e) {
                throw new PAException("save draft - Error during uploading file.", e);
        }
    }
}
