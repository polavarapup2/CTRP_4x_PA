/**
 *  The software subject to this notice and license includes both human readable
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
package gov.nih.nci.accrual.service.batch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.enums.CDUSPaymentMethodCode;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.PatientStage;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualChangeCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.CodedEnumHelper;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

/**
 * This class read CSV file and validates the input.
 * @author vrushali
 *
 */
@Stateless
@Local(CdusBatchUploadReaderServiceLocal.class)
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.NPathComplexity", 
                "PMD.ExcessiveClassLength", "PMD.ExcessiveParameterList", "PMD.ExcessiveMethodLength" })
public class CdusBatchUploadReaderBean extends BaseBatchUploadReader implements CdusBatchUploadReaderServiceLocal {
    private static final Logger LOG = Logger.getLogger(CdusBatchUploadReaderBean.class);
    /** The ICD-O-3 disease terminology coding system. */
    public static final String ICD_O_3_CODESYSTEM = "ICD-O-3";
    private static final String SDC_CODESYSTEM = "SDC";
    private static final String ICD9_CODESYSTEM = "ICD9";
    private static final String ICD10_CODESYSTEM = "ICD10";
    private static final String ICDO3_HIST_DEFAULT_DISEASECODE = "7001";
    private static final String ICDO3_DEFAULT_SITEDISEASECODE = "C998";
    private static final String SDC_DEFAULT_DISEASECODE = "80000001";
    private static final String ICD9_DEFAULT_DISEASECODE = "V100";
    private static final String ICD10_DEFAULT_DISEASECODE = "Z1000";
    private static final int PREFIXLENGTH = 3;
    
    @EJB
    private CdusBatchUploadDataValidatorLocal cdusBatchUploadDataValidator;
    @EJB
    private SubjectAccrualServiceLocal subjectAccrualService;
    @EJB
    private BatchFileService batchFileSvc;
    @EJB
    private CdusBatchFilePreProcessorLocal cdusBatchFilePreProcessorLocal;
    
    private static final int RESULTS_LEN = 1000;
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final String DEFAULTBIRTHDATE = "100101";
    private static final String NCI_TRIAL_IDENTIFIER = "${nciTrialIdentifier}";
    private static final String FILE_NAME = "${fileName}";

    // Cache for disease Ii's
    private static CacheManager cacheManager;
    private static final String DISEASE_CACHE_KEY = "BATCH_DISEASE_CACHE_KEY";
    private static final int CACHE_MAX_ELEMENTS = 500;
    private static final long CACHE_TIME = 43200;    
    
    private Cache getDiseaseCache() {
        if (cacheManager == null || cacheManager.getStatus() != Status.STATUS_ALIVE) {
            cacheManager = CacheManager.create();
            Cache cache = new Cache(DISEASE_CACHE_KEY, CACHE_MAX_ELEMENTS, null, false, null, false,
                    CACHE_TIME, CACHE_TIME, false, CACHE_TIME, null, null, 0);
            cacheManager.removeCache(DISEASE_CACHE_KEY);
            cacheManager.addCache(cache);
        }
        return cacheManager.getCache(DISEASE_CACHE_KEY);
    }
    
    
    private static final Map<Long, ReentrantLock> MAIN_BATCH_FILE_CRC32_LOCKS = new HashMap<Long, ReentrantLock>();
    private static final long LOCK_WAIT_SECONDS = 43200L; // 12 hours

    private static synchronized ReentrantLock getMainBatchFileLock(
            final Long crc32) {
        ReentrantLock lock = MAIN_BATCH_FILE_CRC32_LOCKS.get(crc32);
        if (lock == null) {
            lock = new ReentrantLock();
            MAIN_BATCH_FILE_CRC32_LOCKS.put(crc32, lock);
        }
        return lock;
    }

    private static synchronized void removeMainBatchFileLock(final Long crc32) {
        ReentrantLock lock = getMainBatchFileLock(crc32);
        if (!lock.isLocked() && !lock.hasQueuedThreads()) {
            MAIN_BATCH_FILE_CRC32_LOCKS.remove(crc32);
        }
    }

    /**
     * {@inheritDoc}
     * @throws IOException IOException 
     */
    @Override
    public List<BatchValidationResults> validateBatchData(BatchFile batchFile) throws IOException  {
        List<BatchValidationResults> results = new ArrayList<BatchValidationResults>();
        ZipFile zip = null;        
        File file = new File(batchFile.getFileLocation());
        
        final Long crc32 = FileUtils.checksumCRC32(file);
        final ReentrantLock lock = getMainBatchFileLock(crc32);
        try {
            if (!lock.tryLock(LOCK_WAIT_SECONDS, TimeUnit.SECONDS)) {
                throw new RuntimeException(// NOPMD
                        "Unable to acquire lock to process the given batch file: "
                                + file.getAbsolutePath()); 
            }
        } catch (InterruptedException e) {
            LOG.error(e, e);
            throw new RuntimeException(e); // NOPMD
        }
        try {
            boolean archive = StringUtils.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()), "zip");
            if (archive) {
                zip = new ZipFile(file, ZipFile.OPEN_READ);
                Enumeration<? extends ZipEntry> files = zip.entries();
                while (files.hasMoreElements()) {
                    ZipEntry entry = files.nextElement();
                    StringBuffer prefix = new StringBuffer(FilenameUtils.getBaseName(entry.getName()));
                    if (prefix.length() < PREFIXLENGTH) {
                        prefix = prefix.append("_tmp");
                    }
                    File f = File.createTempFile(prefix.toString(), ".txt");
                    final FileOutputStream outStream = FileUtils.openOutputStream(f);
                    IOUtils.copy(zip.getInputStream(entry), outStream);
                    outStream.close();
                    try {
                        batchFileProcessing(results, batchFile, entry.getName(), f);
                    } catch (Exception e) {
                        LOG.error("Error validating batch files.", e);            
                        sendFormatIssueEmail(batchFile, entry.getName(), e);
                    } 
                }
            } else {
                batchFileProcessing(results, batchFile, 
                        AccrualUtil.getFileNameWithoutRandomNumbers(file.getName()), file);
            }
            if (zip != null) {
                zip.close();
            }
        } catch (Exception e) {
            LOG.error("Error validating batch files.", e);
            sendFormatIssueEmail(batchFile, AccrualUtil.getFileNameWithoutRandomNumbers(file.getName()), e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
            removeMainBatchFileLock(crc32);
        }
        return results;
    }

    private void sendFormatIssueEmail(BatchFile batchFile, String fileName, Exception e) {
        //send an email to user that the format is not correct.
        batchFile.setResults("Failed proceesing a batch file: " + fileName + " due to " + e.getLocalizedMessage());
        AccrualCollections collection = new AccrualCollections();
        collection.setResults("Failed proceesing a batch file: " + fileName + " due to " + e.getLocalizedMessage());
        collection.setPassedValidation(batchFile.isPassedValidation());
        try {
            String subj = PaServiceLocator.getInstance().getLookUpTableService()
                    .getPropertyValue("accrual.exception.subject");
            String body = PaServiceLocator.getInstance().getLookUpTableService()
                    .getPropertyValue("accrual.exception.body");
            String regUserName = batchFile.getSubmitter().getFirstName() 
                    + " " + batchFile.getSubmitter().getLastName();

            body = body.replace("${submissionDate}", getFormatedCurrentDate());
            body = body.replace("${SubmitterName}", regUserName);
            body = body.replace("${CurrentDate}", getFormatedCurrentDate());
            body = body.replace(FILE_NAME, fileName);
            subj = subj.replace(FILE_NAME, fileName);
            sendEmail(batchFile.getSubmitter().getEmailAddress(), subj, body);

            batchFileSvc.update(batchFile, collection);
        } catch (PAException e1) {
            LOG.error("Error while sending an email/updating the BatchFile table with the error.", e1);
        }
    }
    
    void batchFileProcessing(List<BatchValidationResults> results,
            BatchFile batchFile, String fileName, File file)
            throws PAException, IOException {
        PreprocessingResult preprocessingResult = cdusBatchFilePreProcessorLocal
                .preprocess(file);
        File processedFile = preprocessingResult.getPreprocessedFile();
        BatchValidationResults result = cdusBatchUploadDataValidator
                .validateSingleBatchData(processedFile,
                        batchFile.getSubmitter());
        result.setFileName(fileName);
        result.setPreprocessingResult(preprocessingResult);
        results.add(result);
        if (!result.isOutOfScope()) {
            validateAndProcessData(batchFile, result);
        }
    }

    private void validateAndProcessData(BatchFile batchFile, BatchValidationResults validationResult)
            throws PAException {
        AccrualCollections collection = new AccrualCollections();
        collection.setNciNumber(validationResult.getNciIdentifier());
        collection.setPassedValidation(validationResult.isPassedValidation());
        batchFileSvc.update(batchFile, collection);
        boolean suAbstractor = AccrualUtil.isSuAbstractor(batchFile.getSubmitter());
        if (!validationResult.isPassedValidation() && (validationResult.isHasNonSiteErrors() || !suAbstractor)) {
            if (validationResult.getErrors() != null) {
                collection.setResults(StringUtils.left(
                        validationResult.getErrors().toString()
                                + validationResult.getPreprocessingErrors(),
                        RESULTS_LEN));
            }
            sendValidationErrorEmail(validationResult, batchFile);
        } else  {
            batchFile.setPassedValidation(
                    StringUtils.isNotEmpty(validationResult.getErrors().toString()) ? false : true);
            batchFile.setProcessed(true);
            BatchImportResults importResults = importBatchData(batchFile, validationResult);
            StringBuffer sb = new StringBuffer();
            // to insert in database results column
            sb.append(
                    validationResult.getErrors() != null ? StringUtils.left(
                            validationResult.getErrors().toString(),
                            RESULTS_LEN) : "")
                    .append(validationResult.getPreprocessingErrors())
                    .append(importResults.getErrors() != null ? StringUtils
                            .left(importResults.getErrors().toString(),
                                    RESULTS_LEN) : "");
            collection.setResults(StringUtils.left(sb.toString(), RESULTS_LEN));
            collection.setTotalImports(importResults.getTotalImports());
            
            sb = new StringBuffer();
            // this is to display the errors in the confirmation email
            sb.append(
                    validationResult.getErrors() != null ? validationResult
                            .getErrors().toString() : "").append(
                    importResults.getErrors() != null ? importResults
                            .getErrors().toString() : "");
            importResults.setErrors(new StringBuilder(sb.toString()));
            sendConfirmationEmail(importResults, validationResult.getPreprocessingResult(), batchFile);
        }
        collection.setChangeCode(validationResult.getChangeCode());
        if (StringUtils.isNotBlank(collection.getResults())) {
            batchFile.setResults(collection.getResults());
        }
        batchFileSvc.update(batchFile, collection);
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    public BatchImportResults importBatchData(BatchFile batchFile, BatchValidationResults validationResult) 
            throws PAException {
        CaseSensitiveUsernameHolder.setUser(batchFile.getUserLastCreated().getLoginName());
        RegistryUser user = batchFile.getSubmitter();
        BatchImportResults importResult = new BatchImportResults();
        StringBuffer errMsg = new StringBuffer();
        int count = 0;        
        importResult.setFileName(validationResult.getFileName());
        List<String[]> lines = validationResult.getValidatedLines();
        String[] studyLine = BatchUploadUtils.getStudyLine(lines);
        String studyProtocolId = studyLine[1];
        List<String[]> patientLines = BatchUploadUtils.getPatientInfo(lines);
        StudyProtocolDTO spDto = getStudyProtocol(studyProtocolId, new BatchFileErrors());
        if (spDto != null) {
            Ii ii = DSetConverter.convertToIi(spDto.getSecondaryIdentifiers()); 
            importResult.setNciIdentifier(ii.getExtension());        
            if (AccrualChangeCode.NO.equals(validationResult.getChangeCode())) {
                Long accrualCnts = subjectAccrualService.getAccrualCounts(CollectionUtils.isNotEmpty(patientLines), 
                    IiConverter.convertToLong(spDto.getIdentifier()));
                if (accrualCnts == 0) {
                    validationResult.setChangeCode(AccrualChangeCode.YES);
                }
            }
        }
        if (AccrualChangeCode.NO.equals(validationResult.getChangeCode())) {
            importResult.setSkipBecauseOfChangeCode(true);
            return importResult;
        }
        Map<Ii, Int> accrualLines = BatchUploadUtils.getAccrualCounts(lines);
        Map<String, List<String>> raceMap = BatchUploadUtils.getPatientRaceInfo(lines);
        count = generateSubjectAccruals(spDto, patientLines, importResult.getNciIdentifier(),
                batchFile.getSubmissionTypeCode(), raceMap, validationResult, errMsg, user);
        if (spDto != null && spDto.getProprietaryTrialIndicator().getValue()) {
            importResult.setIndustrialTrial(true);
        }

        Map<String, Integer> industrialCounts = new HashMap<String, Integer>();
        List<Ii> partiSiteList = new ArrayList<Ii>();
        Map<String, String> siteNameWithID = new HashMap<String, String>();
        
        if (spDto != null && CollectionUtils.isNotEmpty(accrualLines.keySet())) {
            for (Ii partSiteIi : accrualLines.keySet()) {
                //We're assuming this is the assigned identifier for the organization associated with the health care 
                //facility of the study site.
                SearchStudySiteResultDto studySite = 
                        getSearchStudySiteService().getStudySiteByOrg(spDto.getIdentifier(), partSiteIi);
                if (studySite != null) {
                    subjectAccrualService.updateSubjectAccrualCount(studySite.getStudySiteIi(),  
                            accrualLines.get(partSiteIi), user, batchFile.getSubmissionTypeCode());
                    partiSiteList.add(partSiteIi);
                    siteNameWithID.put(partSiteIi.getExtension(), StConverter.convertToString(
                    studySite.getOrganizationName()));
                    count++;
                } else {
                    // insert the incorrect sites into the patient_stage table
                    savePatientStageCounts(user, spDto.getIdentifier(), 
                            importResult.getNciIdentifier(), accrualLines, partSiteIi, importResult.getFileName());
                }
                importResult.setShowCountinEmail(true);
            }
        }
        importResult.setSiteNames(siteNameWithID);
        if (CollectionUtils.isNotEmpty(partiSiteList)) {
            Map<String, Integer> studySiteCounts = BatchUploadUtils.getStudySiteCounts(lines);
            if (AccrualUtil.isSuAbstractor(user)) {
                for (String orgId : studySiteCounts.keySet()) {
                    Ii partSiteIi = BatchUploadUtils.getOrganizationIi(orgId);
                    if (partSiteIi != null) {
                        for (Ii siteIi : partiSiteList) {
                            if (partSiteIi.getExtension().equals(siteIi.getExtension())) {
                                industrialCounts.put(orgId, studySiteCounts.get(orgId));
                                break;
                            }
                        }
                    }
                }
                importResult.setIndustrialCounts(industrialCounts);
            } else {
                importResult.setIndustrialCounts(studySiteCounts);
            }
        }
        importResult.setTotalImports(count);
        importResult.setErrors(new StringBuilder(errMsg.toString().trim()));
        return importResult;
    }

    private void savePatientStageCounts(RegistryUser user, Ii spId, String nciId, 
            Map<Ii, Int> accrualLines, Ii partSiteIi, String fileName) {
        Session session = PaHibernateUtil.getCurrentSession();
        PatientStage ps = new PatientStage();
        ps.setStudyIdentifier(nciId);
        ps.setStudyProtocolIdentifier(spId != null ? IiConverter.convertToLong(spId) : null);
        ps.setStudySite(partSiteIi.getExtension());
        ps.setAccrualCount(accrualLines.get(partSiteIi).getValue());
        ps.setUserLastCreated(user.getCsmUser());
        ps.setDateLastCreated(new Date());
        ps.setSubmissionStatus("Failed");
        ps.setFileName(fileName);
        session.save(ps);
    }

    // CHECKSTYLE:OFF More than 7 Parameters
    private int generateSubjectAccruals(StudyProtocolDTO studyProtocol, List<String[]> patientLines, String nciID,
            AccrualSubmissionTypeCode submissionType, Map<String, List<String>> raceMap,
            BatchValidationResults results, StringBuffer errMsg, RegistryUser ru) throws PAException {
        int count = 0;
        User user = AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser());
        if (studyProtocol != null) {
            long startTime = System.currentTimeMillis();
            Map<SubjectAccrualKey, Long[]> listOfStudySubjects = getStudySubjectService().getSubjectAndPatientKeys(
                    IiConverter.convertToLong(studyProtocol.getIdentifier()), true);
            for (String[] p : patientLines) {
                List<String> races = raceMap.get(p[BatchFileIndex.PATIENT_ID_INDEX]);
                Ii studySiteOrgIi = results.getListOfOrgIds().get(p[BatchFileIndex.PATIENT_REG_INST_ID_INDEX]);
                if (studySiteOrgIi != null) {
                    Long studySiteIi  = results.getListOfPoStudySiteIds().get(studySiteOrgIi.getExtension()); 
                    SubjectAccrualDTO saDTO = parserSubjectAccrual(p, submissionType, races, 
                            IiConverter.convertToIi(studySiteIi), ru, studyProtocol.getIdentifier(), 
                            results.getDiseaseCodeSystem());
                    try {
                        Long[] ids = listOfStudySubjects.get(new SubjectAccrualKey(IiConverter.convertToLong(
                                saDTO.getParticipatingSiteIdentifier()), 
                                StConverter.convertToString(saDTO.getAssignedIdentifier())));
                        if (ids == null) {
                            subjectAccrualService.create(saDTO, studyProtocol.getIdentifier(), user.getUserId());
                        } else {
                            saDTO.setIdentifier(IiConverter.convertToIi(ids[0]));
                            subjectAccrualService.update(saDTO, studyProtocol.getIdentifier(), user.getUserId(), ids);
                        }
                        count++;
                    } catch (PAException e) {
                        errMsg.append("Error for StudySubject Id: " 
                            + saDTO.getAssignedIdentifier().getValue() + ", " + e.getLocalizedMessage() + "\n");
                    }
                } else {
                    // insert the incorrect sites into the patient_stage table
                      savePatientStage(p, races, user, nciID, 
                            IiConverter.convertToLong(studyProtocol.getIdentifier()), 
                            results.getFileName(), ru, results.getDiseaseCodeSystem());
                }
            }
            LOG.info("Time to process a single Batch File data: " 
                    + (System.currentTimeMillis() - startTime) / RESULTS_LEN + " seconds");
        }
        return count;
    }
    
    private void savePatientStage(String[] p, List<String> races, User user, String nciId, Long spId, 
            String fileName, RegistryUser ru, String diseaseCodeSystem) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        PatientStage ps = new PatientStage();
        ps.setAssignedIdentifier(p[BatchFileIndex.PATIENT_ID_INDEX]);
        ps.setBirthDate(AccrualUtil.yearMonthStringToTimestamp(StringUtils.isEmpty(p[BatchFileIndex.PATIENT_DOB_INDEX]) 
                ? DEFAULTBIRTHDATE : p[BatchFileIndex.PATIENT_DOB_INDEX]));
        ps.setCountryCode(StringUtils.isEmpty(p[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX]) ? "US" 
                : p[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX]);
        ps.setDateLastCreated(new Date());
        SubjectAccrualDTO saDTO = new SubjectAccrualDTO();
        parseSubjectDisease(p, saDTO, ru, IiConverter.convertToStudyProtocolIi(spId), diseaseCodeSystem);
        String disease = ISOUtil.isIiNull(saDTO.getDiseaseIdentifier()) 
                ? "NULL" : IiConverter.convertToString(saDTO.getDiseaseIdentifier());
        String siteDisease = ISOUtil.isIiNull(saDTO.getSiteDiseaseIdentifier()) 
                ? "NULL" : IiConverter.convertToString(saDTO.getSiteDiseaseIdentifier());
        
        ps.setDiseaseCode(disease);
        ps.setSiteDiseaseCode(siteDisease);
        ps.setEthnicCode(CodedEnumHelper.getByClassAndCode(PatientEthnicityCode.class,
                 CDUSPatientEthnicityCode.getByCode(p[BatchFileIndex.PATIENT_ETHNICITY_INDEX]).getCode()).name());
        CDUSPaymentMethodCode pmc = CDUSPaymentMethodCode.getByCode(p[BatchFileIndex.PATIENT_PAYMENT_METHOD_INDEX]);
        if (pmc != null) {
            ps.setPaymentMethodCode(CodedEnumHelper.getByClassAndCode(PaymentMethodCode.class, pmc.getCode()).name());
        }
        DSet<Cd> race = DSetEnumConverter.convertSetToDSet(CDUSPatientRaceCode.getCodesByCdusCodes(races));
        DSet<Cd> raceCds = new DSet<Cd>();
        raceCds.setItem(new HashSet<Cd>());
        if (race != null && race.getItem() != null) {
            for (Cd cd : race.getItem()) {
                raceCds.getItem().add(CdConverter.convertToCd(
                        CDUSPatientRaceCode.getByCode(CdConverter.convertCdToString(cd))));
            }
        }
        ps.setRaceCode(DSetEnumConverter.convertDSetToCsv(PatientRaceCode.class, raceCds));
        ps.setRegistrationDate(new Timestamp(
                BatchUploadUtils.getDate(p[BatchFileIndex.PATIENT_REG_DATE_INDEX]).getTime()));
        ps.setRegistrationGroupId(p[BatchFileIndex.PATIENT_REG_GROUP_ID_INDEX]);
        ps.setSexCode(CodedEnumHelper.getByClassAndCode(PatientGenderCode.class, 
                CDUSPatientGenderCode.getByCode(p[BatchFileIndex.PATIENT_GENDER_INDEX]).getCode()).name());
        ps.setStudyIdentifier(nciId);
        ps.setStudyProtocolIdentifier(spId);
        ps.setStudySite(p[BatchFileIndex.PATIENT_REG_INST_ID_INDEX]);
        ps.setUserLastCreated(user);
        ps.setZip(p[BatchFileIndex.PATIENT_ZIP_INDEX]);
        ps.setSubmissionStatus("Failed");
        ps.setFileName(fileName);
        session.save(ps);
    }
    
    private SubjectAccrualDTO parserSubjectAccrual(String[] line, AccrualSubmissionTypeCode submissionType, 
            List<String> races, Ii studySiteIi, RegistryUser ru, Ii spId, String diseaseCodeSystem) throws PAException {
        SubjectAccrualDTO saDTO = new SubjectAccrualDTO();
        saDTO.setAssignedIdentifier(StConverter.convertToSt(StringUtils
                .upperCase(StringUtils
                        .trim(line[BatchFileIndex.PATIENT_ID_INDEX]))));
        saDTO.setRegistrationDate(
                TsConverter.convertToTs(BatchUploadUtils.getDate(line[BatchFileIndex.PATIENT_REG_DATE_INDEX])));
        saDTO.setZipCode(StConverter.convertToSt(line[BatchFileIndex.PATIENT_ZIP_INDEX]));
        saDTO.setBirthDate(AccrualUtil.yearMonthStringToTs(StringUtils.isEmpty(line[BatchFileIndex.PATIENT_DOB_INDEX]) 
                ? DEFAULTBIRTHDATE : line[BatchFileIndex.PATIENT_DOB_INDEX]));
        saDTO.setGender(CdConverter.convertToCd(
                CDUSPatientGenderCode.getByCode(line[BatchFileIndex.PATIENT_GENDER_INDEX]).getValue()));
        saDTO.setEthnicity(CdConverter.convertToCd(
                CDUSPatientEthnicityCode.getByCode(line[BatchFileIndex.PATIENT_ETHNICITY_INDEX]).getValue()));
        if (CollectionUtils.isNotEmpty(races)) {
            saDTO.setRace(DSetEnumConverter.convertSetToDSet(CDUSPatientRaceCode.getCodesByCdusCodes(races)));
        }
        
        //Default to United States if no country code is provided
        String countryCode = StringUtils.isEmpty(line[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX]) ? "US" 
                : line[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX];
        saDTO.setCountryCode(CdConverter.convertStringToCd(countryCode));        
        CDUSPaymentMethodCode pmc = CDUSPaymentMethodCode.getByCode(line[BatchFileIndex.PATIENT_PAYMENT_METHOD_INDEX]);
        if (pmc != null) {
            saDTO.setPaymentMethod(CdConverter.convertToCd(pmc.getValue()));
        }
        saDTO.setParticipatingSiteIdentifier(studySiteIi);
        saDTO.setRegistrationGroupId(StConverter.convertToSt(line[BatchFileIndex.PATIENT_REG_GROUP_ID_INDEX]));
        saDTO.setSubmissionTypeCode(CdConverter.convertToCd(submissionType));
        parseSubjectDisease(line, saDTO, ru, spId, diseaseCodeSystem);
        return saDTO;
    }

    private void parseSubjectDisease(String[] line, SubjectAccrualDTO saDTO, RegistryUser ru,  
          Ii spId, String diseaseCodeSystem) throws PAException {
        String diseaseCode = line[BatchFileIndex.PATIENT_DISEASE_INDEX];
        if (StringUtils.isEmpty(diseaseCode) && AccrualUtil.isSuAbstractor(ru) 
              && !getSearchStudySiteService().isStudyHasDCPId(spId)) {
            String codeSystemDB = PaServiceLocator.getInstance().getAccrualDiseaseTerminologyService()
                    .getCodeSystem(IiConverter.convertToLong(spId));
            if (ICD9_CODESYSTEM.equals(codeSystemDB)) {
                getSubjectAccrualDisease(saDTO, diseaseCodeSystem, ICD9_DEFAULT_DISEASECODE);
            } else if (ICD10_CODESYSTEM.equals(codeSystemDB)) {
                getSubjectAccrualDisease(saDTO, diseaseCodeSystem, ICD10_DEFAULT_DISEASECODE);
            } else if (SDC_CODESYSTEM.equals(codeSystemDB)) {
                getSubjectAccrualDisease(saDTO, diseaseCodeSystem, SDC_DEFAULT_DISEASECODE);
            } else if (ICD_O_3_CODESYSTEM.equals(codeSystemDB)) {
                getSubjectAccrualDisease(saDTO, diseaseCodeSystem, ICDO3_DEFAULT_SITEDISEASECODE);
                getSubjectAccrualDisease(saDTO, diseaseCodeSystem, ICDO3_HIST_DEFAULT_DISEASECODE);
            }
        }
        if (StringUtils.isEmpty(diseaseCode)) {
            return;
        }        
        StringTokenizer disease = new StringTokenizer(diseaseCode, ";");
        while (disease.hasMoreElements()) {
            String code = AccrualUtil.checkIfStringHasForwardSlash(disease.nextElement().toString());
            if (StringUtils.isEmpty(code)) {
                continue;
            }
            getSubjectAccrualDisease(saDTO, diseaseCodeSystem, code);
        }
    }

   private Element getSubjectAccrualDisease(SubjectAccrualDTO saDTO, String codeSystem, String code) {
      String key = codeSystem + "||" + code;
      AccrualDisease dis = null;
      Element element = getDiseaseCache().get(key);
      if (element == null) {
          dis = getDiseaseService().getByCode(codeSystem, code);
          if (dis != null) {
              element = new Element(key, IiConverter.convertToIi(dis.getId()));
              getDiseaseCache().put(element);
          }
      }
      if (StringUtils.equals(ICD_O_3_CODESYSTEM, codeSystem) && code.toUpperCase(Locale.US).charAt(0) == 'C') {
          saDTO.setSiteDiseaseIdentifier((Ii) element.getValue());
      } else {
          saDTO.setDiseaseIdentifier((Ii) element.getValue());
      }
      return element;
   }  
   

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendValidationErrorEmail(BatchValidationResults result, BatchFile batchFile) throws PAException {
        String subj = PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("accrual.error.subject");
        String body = PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("accrual.error.body");
        String regUserName = batchFile.getSubmitter().getFirstName() + " " + batchFile.getSubmitter().getLastName();
        
        body = body.replace("${submissionDate}", getFormatedCurrentDate());
        body = body.replace("${SubmitterName}", regUserName);
        body = body.replace("${CurrentDate}", getFormatedCurrentDate());
        
        if (!result.isPassedValidation()) {           
            StringBuffer numberedErrors = setErrorsInEmail(result);            
            body = body.replace(FILE_NAME, result.getFileName());
            body = body.replace("${errors}", numberedErrors.toString().replace("\n", "<br/>"));
            if (result.getNciIdentifier() == null) {
                result.setNciIdentifier("");                
            } 
            body = body.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
            subj = subj.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
        }
        sendEmail(batchFile.getSubmitter().getEmailAddress(), subj, body);
    }

    private StringBuffer setErrorsInEmail(BatchValidationResults result) {
        String errors = result.getErrors().toString();
        final PreprocessingResult preprocessingResult = result.getPreprocessingResult();
        
        return setErrorsInEmail(errors, preprocessingResult);
    }

    /**
     * @param errors
     * @param preprocessingResult
     * @return
     */
    private StringBuffer setErrorsInEmail(String errors,
            final PreprocessingResult preprocessingResult) {
        int count = 1;
        StringBuffer numberedErrors = new StringBuffer();
        StringTokenizer st1 = new StringTokenizer(errors, "\n");
        while (st1.hasMoreTokens()) {
            numberedErrors.append(count).append(".  ").append(st1.nextToken());
            if (!numberedErrors.toString().endsWith("\n")) {
                numberedErrors.append(" \n");
            }
            count++;
        }
       
        if (preprocessingResult != null) {
            for (ValidationError error : preprocessingResult
                    .getValidationErrors()) {
                StringBuilder errorMsg = new StringBuilder();
                errorMsg.append(count)
                        .append(".  ")
                        .append(error.getErrorMessage())
                        .append("\n")
                        .append("&nbsp;&nbsp;&nbsp;")
                        .append(StringUtils.join(error.getErrorDetails(), "\n&nbsp;&nbsp;&nbsp;"))
                        .append("\n");
                numberedErrors.append(errorMsg);
                count++;

            }
        }
        
        return numberedErrors;
    }
    
    private CharSequence preparePreprocessingErrorsAsHtml(
            List<ValidationError> validationErrors) {
        StringBuilder errorMsg = new StringBuilder();
        for (ValidationError error : validationErrors) {
            errorMsg.append(String.format("<p>%s</p>", error.getErrorMessage()));
            errorMsg.append("<ul>");
            for (String s : error.getErrorDetails()) {
                errorMsg.append(String.format("<li>%s</li>", s));
            }
            errorMsg.append("</ul>");
        }
        return errorMsg;
    }

    
    /**
     * {@inheritDoc}
     * @param preprocessingResult 
     */
    @Override
    public void sendConfirmationEmail(BatchImportResults result,
            PreprocessingResult preprocessingResult, BatchFile batchFile)
            throws PAException {
        String subject = PaServiceLocator.getInstance().getLookUpTableService()
                .getPropertyValue("accrual.confirmation.subject");
        String body = "";
        if (result.isSkipBecauseOfChangeCode()) {
            body = PaServiceLocator.getInstance().getLookUpTableService()
                    .getPropertyValue("accrual.changeCode.body");
        } else if (result.isIndustrialTrial() && CollectionUtils.isNotEmpty(result.getIndustrialCounts().keySet()) 
             || result.isShowCountinEmail())  {
            body = PaServiceLocator.getInstance().getLookUpTableService()
                    .getPropertyValue("accrual.industrialTrial.body");
            StringBuffer studySiteCounts = new StringBuffer();
            for (String partSiteIi : result.getIndustrialCounts().keySet()) {
                studySiteCounts.append(partSiteIi).append(" - ")
                .append(result.getSiteNames().get(partSiteIi)).append(" - ")
                .append(result.getIndustrialCounts().get(partSiteIi)).append(" \n");
            }
            body = body.replace("${studySiteCounts}", studySiteCounts.toString().replace("\n", "<br/>"));
        } else {
            body = PaServiceLocator.getInstance().getLookUpTableService()
                .getPropertyValue("accrual.confirmation.body");
            body = body.replace("${count}", String.valueOf(result.getTotalImports()));
        }
        String regUserName = batchFile.getSubmitter().getFirstName() + " " + batchFile.getSubmitter().getLastName();
        body = body.replace("${submissionDate}", getFormatedCurrentDate());
        body = body.replace("${SubmitterName}", regUserName);
        body = body.replace("${CurrentDate}", getFormatedCurrentDate());
        
        body = body.replace(FILE_NAME, result.getFileName());
        if (StringUtils.isNotEmpty(result.getErrors().toString())) {
            body = body.replace("${errorsDesc}", 
                    "However, some patient records could not be processed because the accruing"
                    + " site is not listed on the trial in CTRP. Please see details below.");
            StringBuffer numberedErrors = setErrorsInEmail(result.getErrors()
                    .toString(), null);
            body = body.replace("${errors}", numberedErrors.toString().replace("\n", "<br/>"));
        } else {
            body = body.replace("<p>${errorsDesc}</p>", "");
            body = body.replace("<ul>${errors}</ul>", "");
        }
        
        if (preprocessingResult != null
                && !preprocessingResult.getValidationErrors().isEmpty()) {
            body = body.replace("${preprocessingErrors}",
                    preparePreprocessingErrorsAsHtml(preprocessingResult
                            .getValidationErrors()));
        } else {
            body = body.replace("${preprocessingErrors}", "");
        }
        
        body = body.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
        body = body
                .replace(
                        "${trialIdentifiers}",
                        StringUtils.defaultString(PaServiceLocator
                                .getInstance()
                                .getMailManagerService()
                                .getStudyIdentifiersHTMLTable(
                                        result.getNciIdentifier())));
        subject = subject.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
        sendEmail(batchFile.getSubmitter().getEmailAddress(), subject, body);
    }

   
    private void sendEmail(String to, String subject, String msg) {
        if (StringUtils.isNotBlank(msg)) {
            PaServiceLocator.getInstance().getMailManagerService().sendMailWithHtmlBody(to, subject, msg);
        }
    }

    /**
     * Gets the current date properly formatted.
     * @return The current date properly formatted.
     */
    String getFormatedCurrentDate() {
        return DateFormatUtils.format(new Date(), DATE_PATTERN);
    }

    /**
     * @param cdusBatchUploadDataValidator the cdusBatchUploadDataValidator to set
     */
    public void setCdusBatchUploadDataValidator(CdusBatchUploadDataValidatorLocal cdusBatchUploadDataValidator) {
        this.cdusBatchUploadDataValidator = cdusBatchUploadDataValidator;
    }
    
    /**
     * @param subjectAccrualService the subject accrual service to set
     */
    public void setSubjectAccrualService(SubjectAccrualServiceLocal subjectAccrualService) {
        this.subjectAccrualService = subjectAccrualService;
    }

    /**
     * @param batchFileSvc the batchFileSvc to set
     */
    public void setBatchFileSvc(BatchFileService batchFileSvc) {
        this.batchFileSvc = batchFileSvc;
    }

    /**
     * @param cdusBatchFilePreProcessorLocal the cdusBatchFilePreProcessorLocal to set
     */
    public void setCdusBatchFilePreProcessorLocal(
            CdusBatchFilePreProcessorLocal cdusBatchFilePreProcessorLocal) {
        this.cdusBatchFilePreProcessorLocal = cdusBatchFilePreProcessorLocal;
    }
}
