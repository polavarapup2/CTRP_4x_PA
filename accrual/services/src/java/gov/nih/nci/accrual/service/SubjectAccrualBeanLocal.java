/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
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
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
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
package gov.nih.nci.accrual.service;

import gov.nih.nci.accrual.convert.Converters;
import gov.nih.nci.accrual.convert.PatientConverter;
import gov.nih.nci.accrual.convert.StudySubjectConverter;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.enums.CDUSPaymentMethodCode;
import gov.nih.nci.accrual.service.batch.BatchFileService;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.service.util.AccrualDiseaseServiceLocal;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.SubjectAccrualCountService;
import gov.nih.nci.accrual.service.util.SubjectAccrualValidator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Implementation of the subject accrual service.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveMethodLength", "PMD.ExcessiveClassLength", 
    "PMD.NPathComplexity" })
public class SubjectAccrualBeanLocal implements SubjectAccrualServiceLocal {
    private static final Logger LOG = Logger.getLogger(SubjectAccrualBeanLocal.class);
//    private static final String IDENTIFIER = "identifier";

    /** Number of hours witch the batch processing thread will run before being killed. */
    public static final int BATCH_PROCESSING_THREAD_TIMEOUT_HOURS = 24;

    @EJB
    private PatientServiceLocal patientService;
    @EJB
    private StudySubjectServiceLocal studySubjectService;
    @EJB
    private PerformedActivityServiceLocal performedActivityService;
    @EJB
    private CountryService countryService;
    @EJB
    private SubjectAccrualCountService subjectAccrualCountSvc;
    @EJB
    private BatchFileService batchFileService;
    @EJB
    private SubjectAccrualValidator subjectAccrualValidator;
    @EJB
    private BatchUploadProcessingTaskServiceLocal batchUploadProcessingTaskService;
    @EJB
    private AccrualDiseaseServiceLocal diseaseSvc;
    
    private boolean useTestSeq = false;

    /** Cache for country Ii's. */
    private static CacheManager cacheManager;
    private static final String COUNTRY_CACHE_KEY = "COUNTRY_CACHE_KEY";
    private static final int CACHE_MAX_ELEMENTS = 50;
    private static final long CACHE_TIME = 43200;

    private Cache getCountryCache() {
        if (cacheManager == null || cacheManager.getStatus() != Status.STATUS_ALIVE) {
            cacheManager = CacheManager.create();
            Cache cache = new Cache(COUNTRY_CACHE_KEY, CACHE_MAX_ELEMENTS, null, false, null, false,
                CACHE_TIME, CACHE_TIME, false, CACHE_TIME, null, null, 0);
            cacheManager.removeCache(COUNTRY_CACHE_KEY);
            cacheManager.addCache(cache);
        }
        return cacheManager.getCache(COUNTRY_CACHE_KEY);
    }

    /**
     * Class used to run separate thread for processing batch submissions.
     */
    private class BatchFileProcessor implements Runnable {
        private final BatchFile batchFile;
        private final String user;

        public BatchFileProcessor(BatchFile batchFile, String user) {
            this.batchFile = batchFile;
            this.user = user;
        }

        @Override
        public void run() {
            try {
                CaseSensitiveUsernameHolder.setUser(user);
                batchUploadProcessingTaskService.processBatchUploads(batchFile);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }

    /**
     * Class used to manage batch processing thread.
     */
    private class BatchThreadManager implements Runnable {
        private final BatchFile batchFile;
        private final String user;
        public BatchThreadManager(BatchFile batchFile, String user) {
            this.batchFile = batchFile;
            this.user = user;
        }

        @Override
        public void run() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            try {
                executor.submit(new BatchFileProcessor(batchFile, user)).get(
                        BATCH_PROCESSING_THREAD_TIMEOUT_HOURS, TimeUnit.HOURS);
            } catch (Exception e) {
                LOG.error("Forcing shutdown of batch file processing thread.");
                executor.shutdownNow();
            }
        }
    }

    /**
     * The 1st 4 bytes of a byte of a file that indicates a zip file. Used to determine if the information
     * passed in to the submitBatchData method is a zip file.
     */
    private static final int ZIP_FILE_SIGNATURE = 0x504b0304;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<SubjectAccrualDTO> manageSubjectAccruals(List<SubjectAccrualDTO> subjects) throws PAException {
        subjectAccrualValidator.validate(subjects);
        List<SubjectAccrualDTO> results = new ArrayList<SubjectAccrualDTO>();
        if (subjects != null) {
            for (SubjectAccrualDTO subject : subjects) {
                if (!AccrualUtil.isUserAllowedAccrualAccess(subject.getParticipatingSiteIdentifier()) 
                      && !isUserSiteFamilySubmitter(subject.getParticipatingSiteIdentifier())) {
                    throw new PAException("User does not have accrual access to site "
                            + subject.getParticipatingSiteIdentifier().getExtension());
                }
                StudySiteDTO participatingSite = PaServiceLocator.getInstance().getStudySiteService().get(
                                subject.getParticipatingSiteIdentifier());
                subject.setSubmissionTypeCode(CdConverter.convertToCd(AccrualSubmissionTypeCode.SERVICE_MSA));
                AccrualDisease disease = diseaseSvc.get(subject.getDiseaseIdentifier()); 
                if (disease != null) {
                    subject.setDiseaseIdentifier(IiConverter.convertToIi(disease.getId()));
                }
                AccrualDisease siteDisease = diseaseSvc.get(subject.getSiteDiseaseIdentifier()); 
                if (siteDisease != null) {
                    subject.setSiteDiseaseIdentifier(IiConverter.convertToIi(siteDisease.getId()));
                }

                Long userId = AccrualCsmUtil.getInstance().getCSMUser(
                        UsernameHolder.getUser()).getUserId();
                Long[] ids;
                if (ISOUtil.isIiNull(subject.getIdentifier())) {
                    ids = create(subject, participatingSite.getStudyProtocolIdentifier(), userId);
                } else {
                    StudySubject ssub = (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class, 
                            IiConverter.convertToLong(subject.getIdentifier()));
                    Long[] currentIds = {ssub.getId(), ssub.getPatient().getId()};
                    ids = update(subject, participatingSite.getStudyProtocolIdentifier(), userId, currentIds);
                }
                PaHibernateUtil.getCurrentSession().flush();
                PaHibernateUtil.getCurrentSession().clear();
                StudySubject ssub = (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class, ids[0]);
                results.add(Converters.get(StudySubjectConverter.class).convertFromDomainToSubjectDTO(ssub));
            }
        }
        return results;
    }
    private boolean isUserSiteFamilySubmitter(Ii psID) throws PAException {
        StudySiteDTO ssDto = PaServiceLocator.getInstance().getStudySiteService()
                 .get(psID);
        StudySite ss = new StudySiteConverter().convertFromDtoToDomain(ssDto);
        boolean isSiteFamilySubmitter = false;
        if (ss.getHealthCareFacility() != null 
                  && new AccrualUtil().isUserAllowedSiteOrFamilyAccrualAccess(
             ss.getHealthCareFacility().getOrganization().getIdentifier())) {
             isSiteFamilySubmitter = true;
        }
        return isSiteFamilySubmitter;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createAccrualAccess(RegistryUser ru, Long ssId) throws PAException {
        StudySiteAccrualAccess ssaa = null;
        List<StudySiteAccrualAccess> ssasList = PaHibernateUtil.getCurrentSession()
                .createCriteria(StudySiteAccrualAccess.class)
                .add(Restrictions.eq("studySite.id", ssId))
                .add(Restrictions.eq("registryUser.id", ru.getId())).list();
        if (!ssasList.isEmpty()) {
            ssaa = ssasList.get(0);
        }
        if (ssaa == null) {
             ssaa = new StudySiteAccrualAccess();
             StudySite ss = new StudySite();
             ss.setId(ssId);
             ssaa.setStudySite(ss);
             ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
             ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
             ssaa.setDateLastCreated(new Date());
             ssaa.setDateLastUpdated(new Date());
             ssaa.setRegistryUser(ru);
             ssaa.setUserLastCreated(ru.getCsmUser());
             ssaa.setUserLastUpdated(ru.getCsmUser());
             ssaa.setSource(AccrualAccessSourceCode.ACC_GENERATED);
             try {
                 PaHibernateUtil.getCurrentSession().save(ssaa);
                 PaHibernateUtil.getCurrentSession().flush();
             } catch (HibernateException hbe) {
                 throw new PAException("Error while saving study site accrual access.", hbe);
             }
        } else if (ssaa.getStatusCode().getName().equals(ActiveInactiveCode.INACTIVE.getName())) {
            ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
            ssaa.setDateLastUpdated(new Date());
            ssaa.setUserLastUpdated(ru.getCsmUser());
            try {
                PaHibernateUtil.getCurrentSession().merge(ssaa);
                PaHibernateUtil.getCurrentSession().flush();
            } catch (HibernateException hbe) {
                throw new PAException("Error while saving study site accrual access.", hbe);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long[] create(SubjectAccrualDTO dto, Ii spIi, Long userId) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Cannot create a subject accrual with an identifier set. Please use update().");
        }
        Long spId = IiConverter.convertToLong(spIi);
        Long newId = updatePatientTable(dto, userId, null);
        Long newSSId = updateStudySubjectTable(dto, userId, null, spId, newId);

        Session session = PaHibernateUtil.getCurrentSession();
        
        String sql = "INSERT INTO performed_activity(identifier, study_protocol_identifier, performed_activity_type," 
                + "date_last_created, date_last_updated, study_subject_identifier, registration_date, " 
                + "user_last_created_id, user_last_updated_id) "
                + "VALUES (:psmId, :spId, 'PerformedSubjectMilestone', now(), now(),"
                + " :ssId, :regDate, :userId, :userId)";
        SQLQuery qry = session.createSQLQuery(sql);
        qry.setLong("psmId", newId);
        qry.setLong("spId", spId);
        qry.setLong("ssId", newSSId);
        qry.setTimestamp("regDate", TsConverter.convertToTimestamp(dto.getRegistrationDate()));
        qry.setLong("userId", userId);
        qry.executeUpdate();
        return new Long[]{newSSId, newId};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long[] update(SubjectAccrualDTO dto, Ii spIi, Long userId, Long[] ids) throws PAException {
        Long ssId = IiConverter.convertToLong(dto.getIdentifier());
        if (ssId == null) {
            throw new PAException("Cannot update a subject accrual without an identifier set. Please use create().");
        }
        if (!ObjectUtils.equals(ssId, ids[0])) {
            throw new PAException("Ids passed into update() do not match dto.");
        }
        updateStudySubjectTable(dto, userId, ids, null, null);
        updatePatientTable(dto, userId, ids);
        String sql = "UPDATE performed_activity SET registration_date=:registrationDate, "
                   + "user_last_updated_id=:userId, date_last_updated=now() "
                   + "WHERE study_subject_identifier=:ssId ";
        SQLQuery qry = PaHibernateUtil.getCurrentSession().createSQLQuery(sql);
        qry.setTimestamp("registrationDate", TsConverter.convertToTimestamp(dto.getRegistrationDate()));
        qry.setLong("userId", userId);
        qry.setLong("ssId", ssId);
        qry.executeUpdate();
        return ids;
    }

    private Long updatePatientTable(SubjectAccrualDTO dto, Long userId, Long[] ids) 
            throws PAException {
        Element element = getCountryCache().get(dto.getCountryCode());
        if (element == null) {
            Country country = getCountryService().getByCode(CdConverter.convertCdToString(dto.getCountryCode()));
            Ii countryIi = IiConverter.convertToCountryIi(country.getId());
            element = new Element(dto.getCountryCode(), countryIi);
            getCountryCache().put(element);
        }

        PatientDto patientDTO = new PatientDto();
        patientDTO.setBirthDate(dto.getBirthDate());
        patientDTO.setCountryIdentifier((Ii) element.getValue());
        patientDTO.setEthnicCode(CdConverter.convertToCd(
                CDUSPatientEthnicityCode.getByCode(CdConverter.convertCdToString(dto.getEthnicity()))));
        patientDTO.setGenderCode(CdConverter.convertToCd(
                CDUSPatientGenderCode.getByCode(CdConverter.convertCdToString(dto.getGender()))));
        DSet<Cd> races = new DSet<Cd>();
        races.setItem(new HashSet<Cd>());
        if (dto.getRace() != null && dto.getRace().getItem() != null) {
            for (Cd cd : dto.getRace().getItem()) {
                races.getItem().add(CdConverter.convertToCd(
                        CDUSPatientRaceCode.getByCode(CdConverter.convertCdToString(cd))));
            }
        }
        patientDTO.setRaceCode(races);
        patientDTO.setZip(dto.getZipCode());
        patientDTO.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        patientDTO.setDateLastUpdated(TsConverter.convertToTs(new Date()));
        patientDTO.setUserLastUpdated(StConverter.convertToSt(userId.toString()));
        Long result;
        if (ids != null) {
            result = ids[1];
            patientDTO.setIdentifier(IiConverter.convertToIi(result));
            Patient p = Converters.get(PatientConverter.class).convertFromDtoToDomain(patientDTO);
            try {
                PaHibernateUtil.getCurrentSession().merge(p);
                PaHibernateUtil.getCurrentSession().flush();
            } catch (HibernateException hbe) {
                throw new PAException("Error while updating patient.", hbe);
            }
        } else {
           patientDTO.setDateLastCreated(TsConverter.convertToTs(new Date()));
           patientDTO.setUserLastCreated(StConverter.convertToSt(userId.toString()));
           Patient p = Converters.get(PatientConverter.class).convertFromDtoToDomain(patientDTO);
            try {
                PaHibernateUtil.getCurrentSession().save(p);
                PaHibernateUtil.getCurrentSession().flush();
            } catch (HibernateException hbe) {
                throw new PAException("Error while saving study subject.", hbe);
            }
            result = p.getId();
        }
        return result;
    }
    

    private Long updateStudySubjectTable(SubjectAccrualDTO dto, Long userId, Long[] ids, Long spId, Long newPatientId) 
            throws PAException {
        return saveStudySubject(dto, userId, ids, spId, newPatientId);
    }

    private Long saveStudySubject(SubjectAccrualDTO dto, Long userId, Long[] ids, 
             Long spId, Long newPatientId) throws PAException {
       StudySubject ss = new StudySubject();
       StudySubjectDto sdto = new StudySubjectDto();
       Long result;
       sdto.setStudySiteIdentifier(dto.getParticipatingSiteIdentifier());
     
       dto.setPaymentMethod(CdConverter.convertToCd(
               CDUSPaymentMethodCode.getByCode(CdConverter.convertCdToString(
               dto.getPaymentMethod()))));
       sdto.setPaymentMethodCode(dto.getPaymentMethod());
       sdto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
       sdto.setDateLastUpdated(TsConverter.convertToTs(new Date()));
       sdto.setAssignedIdentifier(upperCase(dto.getAssignedIdentifier()));
       sdto.setUserLastUpdated(StConverter.convertToSt(userId.toString()));
       sdto.setDiseaseIdentifier(dto.getDiseaseIdentifier());
       sdto.setSiteDiseaseIdentifier(dto.getSiteDiseaseIdentifier());
       sdto.setRegistrationGroupId(dto.getRegistrationGroupId());
       sdto.setSubmissionTypeCode(dto.getSubmissionTypeCode());
       if (ids != null) {
           result = ids[0];
           sdto.setIdentifier(IiConverter.convertToIi(result));
           sdto.setPatientIdentifier(IiConverter.convertToIi(ids[1]));
//           sdto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(spId));
           ss = Converters.get(StudySubjectConverter.class).convertFromDtoToDomain(sdto);
           try {
               PaHibernateUtil.getCurrentSession().merge(ss);
               PaHibernateUtil.getCurrentSession().flush();
           } catch (HibernateException hbe) {
               throw new PAException("Error while updating study subject.", hbe);
           }
       } else {
           sdto.setDateLastCreated(TsConverter.convertToTs(new Date()));
           sdto.setUserLastCreated(StConverter.convertToSt(userId.toString()));
           sdto.setPatientIdentifier(IiConverter.convertToIi(newPatientId));
           sdto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(spId));
           ss = Converters.get(StudySubjectConverter.class).convertFromDtoToDomain(sdto);
           try {
               PaHibernateUtil.getCurrentSession().save(ss);
               PaHibernateUtil.getCurrentSession().flush();
           } catch (HibernateException hbe) {
               throw new PAException("Error while saving study subject.", hbe);
           }
           result = ss.getId();
       }
       return result;
    }
    
    /**
     * @param st St
     * @return St
     */
    St upperCase(St st) {
        return ISOUtil.isStNull(st) ? st : StConverter.convertToSt(StringUtils
                .upperCase(StConverter.convertToString(st)));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSubjectAccrual(Ii subjectAccrualIi, String deleteReason) throws PAException {
        if (ISOUtil.isIiNull(subjectAccrualIi)) {
            throw new PAException("Study Subject Ii must be valid.");
        }
        StudySubject studySubject = (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class,
                IiConverter.convertToLong(subjectAccrualIi));
        if (studySubject == null) {
            throw new PAException("A Study Subject with id " + subjectAccrualIi.getExtension()
                    + " does not exist.");
        }
        boolean isSiteFamilySubmitter = false;
        if (studySubject.getStudySite().getHealthCareFacility() != null 
            && new AccrualUtil().isUserAllowedSiteOrFamilyAccrualAccess(studySubject
                .getStudySite().getHealthCareFacility().getOrganization().getIdentifier())) {
                isSiteFamilySubmitter = true;
        }
        if (!AccrualUtil.isUserAllowedAccrualAccess(IiConverter
                    .convertToStudySiteIi(studySubject.getStudySite().getId())) && !isSiteFamilySubmitter) {
            throw new PAException("User does not have accrual access to site.");
        }
        nullifyStudySubject(studySubject, deleteReason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSubjectAccrualCount(Ii participatingSiteIi, Int count, AccrualSubmissionTypeCode submissionType)
            throws PAException {
        if (!AccrualUtil.isValidTreatingSite(participatingSiteIi)) {
            throw new PAException(
                    "The treating site that is having an accrual count added to it does not exist.");
        }
        if (!AccrualUtil.isUserAllowedAccrualAccess(participatingSiteIi) 
            && !isUserSiteFamilySubmitter(participatingSiteIi)) {
            throw new PAException("User does not have accrual access to site.");
        }
        doUpdateToSubjectAccrual(participatingSiteIi, count, submissionType);
    }

    private void doUpdateToSubjectAccrual(Ii participatingSiteIi, Int count, AccrualSubmissionTypeCode submissionType)
            throws PAException {
        StudySiteSubjectAccrualCount ssAccCount = getSubjectAccrualCountSvc()
                .getCountByStudySiteId(participatingSiteIi);
        if (ssAccCount == null) {
            StudySiteDTO ssDto = PaServiceLocator.getInstance().getStudySiteService().get(participatingSiteIi);
            StudySite ss = new StudySiteConverter().convertFromDtoToDomain(ssDto);
            ssAccCount = new StudySiteSubjectAccrualCount();
            ssAccCount.setStudySite(ss);
            ssAccCount.setStudyProtocol(ss.getStudyProtocol());
        } 
        ssAccCount.setAccrualCount(count.getValue());
        ssAccCount.setSubmissionTypeCode(submissionType);
        List<StudySiteSubjectAccrualCount> counts = new ArrayList<StudySiteSubjectAccrualCount>();
        counts.add(ssAccCount);
        getSubjectAccrualCountSvc().save(counts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSubjectAccrualCount(Ii participatingSiteIi, Int count, RegistryUser user,
            AccrualSubmissionTypeCode submissionType) throws PAException {
        if (ISOUtil.isIiNull(participatingSiteIi)) {
            throw new PAException("Study Site Ii cannot be null.");
        }
        
        if (!AccrualUtil.isUserAllowedAccrualAccess(participatingSiteIi, user) 
             && !isUserSiteFamilySubmitter(participatingSiteIi)) {
            throw new PAException("User does not have accrual access to site.");
        }
        doUpdateToSubjectAccrual(participatingSiteIi, count, submissionType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitBatchData(Ed batchFile) throws PAException {
        if (ISOUtil.isEdNull(batchFile)) {
            throw new PAException("Null batch files are not allowed. Please provide a valid batch file.");
        }
        String filePath = generateFileLocation(batchFile);
        RegistryUser submitter = getBatchSubmitter();
        writeBatchFileToFilesystem(batchFile, filePath);

        BatchFile batch = new BatchFile();
        batch.setSubmitter(submitter);
        batch.setFileLocation(filePath);
        batch.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        getBatchFileService().save(batch);
        processBatchFiles(batch);
    }

    @Override
    public void processBatchFiles(BatchFile batchFile) {
        Thread batchThread = new Thread(new BatchThreadManager(batchFile, CaseSensitiveUsernameHolder.getUser()));
        batchThread.start();
    }


    private void writeBatchFileToFilesystem(Ed batchFile, String filePath) throws PAException {
        try {
            File file = new File(filePath);
            FileUtils.writeByteArrayToFile(file, batchFile.getData());
        } catch (IOException e) {
            throw new PAException("An error has occurred while trying to submit your batch data. Please try again.", e);
        }
    }

    private String generateFileLocation(Ed batchFile) throws PAException {
        try {
            DataInputStream in =
                new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(batchFile.getData())));
            String extension = in.readInt() == ZIP_FILE_SIGNATURE ? ".zip" : ".txt";
            return PaEarPropertyReader.getAccrualBatchUploadPath() + File.separator + UUID.randomUUID() + "-batchFile"
                + extension;
        } catch (IOException e) {
            throw new PAException("Unable to determine whether batch is an archive or a single file.", e);
        }
    }

    private RegistryUser getBatchSubmitter() throws PAException {
        return PaServiceLocator.getInstance().getRegistryUserService().getUser(AccrualCsmUtil.getInstance()
                .getCSMUser(CaseSensitiveUsernameHolder.getUser()).getLoginName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SubjectAccrualDTO> search(Ii studyIdentifier, Ii participatingSiteIdentifier, Ts startDate, Ts endDate,
            LimitOffset pagingParams) throws PAException {
        if (ISOUtil.isIiNull(studyIdentifier)) {
            throw new PAException("Study identifier must not be null when calling seach.");
        }

        List<StudySubjectDto> studySubjectDtoList = getStudySubjectService()
            .search(IiConverter.convertToLong(studyIdentifier), IiConverter.convertToLong(participatingSiteIdentifier),
                    TsConverter.convertToTimestamp(startDate), TsConverter.convertToTimestamp(endDate), pagingParams);

        return convertStudySubjectDtoToSubjectAccrualDTOList(studySubjectDtoList);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Long getAccrualCounts(Boolean subjects, Long studyProtocolId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Long result = 0L;
        Query sqlCount;
        if (!subjects) {
            sqlCount = session.createSQLQuery("select count(*) from study_site_subject_accrual_count where "
                        + "study_protocol_identifier = " + studyProtocolId);
            result = Long.valueOf(sqlCount.uniqueResult().toString());
        } else {
            sqlCount = session.createSQLQuery("select count(*) from study_subject where "
                        + "study_protocol_identifier = " + studyProtocolId + " and status_code <> 'NULLIFIED'");
            result = Long.valueOf(sqlCount.uniqueResult().toString());
        }
        return result;
    }

    private List<SubjectAccrualDTO> convertStudySubjectDtoToSubjectAccrualDTOList(
            List<StudySubjectDto> studySubjectDtoList) {
        List<SubjectAccrualDTO> result = new ArrayList<SubjectAccrualDTO>();
        for (StudySubjectDto studySubjectDto : studySubjectDtoList) {
            Long studySubjectId = IiConverter.convertToLong(studySubjectDto.getIdentifier());
            StudySubject studySubject =
                    (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class, studySubjectId);
            result.add(Converters.get(StudySubjectConverter.class).convertFromDomainToSubjectDTO(studySubject));

        }
        return result;
    }

    private void nullifyStudySubject(StudySubject ss, String deleteReason) throws PAException {
        ss.setStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        ss.setDeleteReason(deleteReason);
        ss.setDateLastUpdated(new Date());
        ss.setUserLastUpdated(AccrualCsmUtil.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        ss.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        Patient patient = ss.getPatient();
        patient.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
        patient.setDateLastUpdated(new Date());
        patient.setUserLastUpdated(AccrualCsmUtil.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        PaHibernateUtil.getCurrentSession().merge(ss);
        PaHibernateUtil.getCurrentSession().merge(patient);
    }

    /**
     * @param subjectAccrualCountSvc the subjectAccrualCountSvc to set
     */
    public void setSubjectAccrualCountSvc(SubjectAccrualCountService subjectAccrualCountSvc) {
        this.subjectAccrualCountSvc = subjectAccrualCountSvc;
    }

    /**
     * @return the subjectAccrualCountSvc
     */
    public SubjectAccrualCountService getSubjectAccrualCountSvc() {
        return subjectAccrualCountSvc;
    }

    /**
     * @return the patientService
     */
    public PatientServiceLocal getPatientService() {
        return patientService;
    }

    /**
     * @param patientService the patientService to set
     */
    public void setPatientService(PatientServiceLocal patientService) {
        this.patientService = patientService;
    }

    /**
     * @return the studySubjectService
     */
    public StudySubjectServiceLocal getStudySubjectService() {
        return studySubjectService;
    }

    /**
     * @param studySubjectService the studySubjectService to set
     */
    public void setStudySubjectService(StudySubjectServiceLocal studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    /**
     * @return the performedActivityService
     */
    public PerformedActivityServiceLocal getPerformedActivityService() {
        return performedActivityService;
    }

    /**
     * @param performedActivityService the performedActivityService to set
     */
    public void setPerformedActivityService(PerformedActivityServiceLocal performedActivityService) {
        this.performedActivityService = performedActivityService;
    }

    /**
     * @return the countryService
     */
    public CountryService getCountryService() {
        return countryService;
    }

    /**
     * @param countryService the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * @return the batchFileService
     */
    public BatchFileService getBatchFileService() {
        return batchFileService;
    }

    /**
     * @param batchFileService the batchFileService to set
     */
    public void setBatchFileService(BatchFileService batchFileService) {
        this.batchFileService = batchFileService;
    }

    /**
     * @param subjectAccrualValidator the subjectAccrualValidator to set
     */
    public void setSubjectAccrualValidator(SubjectAccrualValidator subjectAccrualValidator) {
        this.subjectAccrualValidator = subjectAccrualValidator;
    }
    /**
     * @param diseaseSvc the diseaseSvc to set
     */
    public void setDiseaseSvc(AccrualDiseaseServiceLocal diseaseSvc) {
        this.diseaseSvc = diseaseSvc;
    }

    /**
     * @return useTestSeq
     */
    public boolean isUseTestSeq() {
        return useTestSeq;
    }

    /**
     * @param useTestSeq the useTestSeq to set
     */
    public void setUseTestSeq(boolean useTestSeq) {
        this.useTestSeq = useTestSeq;
    }
}
