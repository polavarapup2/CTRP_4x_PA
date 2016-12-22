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
package gov.nih.nci.accrual.service.batch;

import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.AccrualChangeCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.noniso.dto.AccrualOutOfScopeTrialDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.AccrualUtilityServiceRemote;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

/**
 * @author Igor Merenko
 */
@Stateless
@Local(CdusBatchUploadDataValidatorLocal.class)
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.TooManyMethods", 
                    "PMD.AvoidDeeplyNestedIfStmts", "PMD.AppendCharacterWithChar", "PMD.NPathComplexity",
                    "PMD.ExcessiveClassLength", "PMD.TooManyFields" })
public class CdusBatchUploadDataValidator extends BaseValidatorBatchUploadReader implements
        CdusBatchUploadDataValidatorLocal {
    
    private static final String MISSING_TRIAL = "Missing Trial";
    private static final String REJECTED = "Rejected";
    private static final Logger LOG = Logger.getLogger(CdusBatchUploadDataValidator.class); 
    private static final String PATIENTS = "PATIENTS";
    private RegistryUser ru;
    private StudyProtocolDTO sp;
    private Map<String, Long> listOfPoIds;
    private Map<String, String> listOfCtepIds;
    private Map<String, Ii> listOfOrgIds;
    private Map<String, Ii> listOfBlankIds;
    private Date blankOrgDate;
    private String blankOrgName;
    private static final int TIME_SECONDS = 1000;
    private String codeSystem;
    private boolean checkDisease;
    private boolean superAbstractor;
    private Set<SubjectAccrualKey> patientsFromBatchFile = new HashSet<SubjectAccrualKey>();
    private BatchFileErrors bfErrors = new BatchFileErrors();
    private String accrualSubmissionLevel;
    /**
     * UTF byte order marker.
     */
    public static final String UTF8_BOM = "\uFEFF";

    @EJB
    private SubjectAccrualServiceLocal subjectAccrualService;

    /**
     * {@inheritDoc}
     */
    //CHECKSTYLE:OFF
    @Override
    @SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.NcssMethodCount" })
    public BatchValidationResults validateSingleBatchData(File file, RegistryUser user)  {
        long startTime = System.currentTimeMillis();        
        ru = user;
        initializeOrganizationLists();
        checkDisease = false;
        patientsFromBatchFile = new HashSet<SubjectAccrualKey>();
        accrualSubmissionLevel = null;
        boolean notCtepDcpTrial = false;
        bfErrors = new BatchFileErrors();
        BatchValidationResults results = new BatchValidationResults();
        results.setFileName(file.getName());
        try {
            List<String[]> lines = new ArrayList<String[]>();
            LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
            long lineNumber = 0;
            String protocolId = null;
            while (lineIterator.hasNext()) {
                String str = lineIterator.nextLine();
                if (str.startsWith(UTF8_BOM)) {
                    str = str.substring(1);
                }
                String[] line = AccrualUtil.csvParseAndTrim(str);
                lines.add(line);
                ++lineNumber;
                if (line != null 
                        && StringUtils.equalsIgnoreCase("COLLECTIONS", line[BatchFileIndex.LINE_IDENTIFIER_INDEX]) 
                        && line.length > 1) {
                    protocolId = line[1];
                    String changeCode = line.length > BatchFileIndex.CHANGE_CODE_INDEX 
                            ? line[BatchFileIndex.CHANGE_CODE_INDEX] : null;
                    results.setNciIdentifier(protocolId);
                    if (StringUtils.isNotEmpty(changeCode)) {
                        AccrualChangeCode cc = AccrualChangeCode.getByCode(changeCode);
                        if (cc == null) {
                             bfErrors.append(new StringBuffer().append("Found invalid change code " + changeCode 
                                    + ". Valid value for COLLECTIONS.Change_Code are 1 and 2.\n"));
                        } else {
                            results.setChangeCode(cc);
                        }
                    }
                    
                    if (superAbstractor && isOutOfScopeTrial(protocolId)) {
                        results.setOutOfScope(true);
                        return results;
                    }
                    
                    sp = getStudyProtocol(protocolId, bfErrors);
                    
                    if (superAbstractor && sp == null) {
                        // PO-6889 kicks in here.
                        createOutOfScopeTrial(protocolId, ru);
                    } else if (superAbstractor && sp != null) {
                        // PO-6889 kicks in here.
                        removeOutOfScopeTrial(protocolId);
                    }
                    
                    if (sp != null) {
                        try {
                            codeSystem = StConverter.convertToString(sp.getAccrualDiseaseCodeSystem());
                            notCtepDcpTrial = !getSearchStudySiteService().isStudyHasCTEPId(sp.getIdentifier()) 
                                && !getSearchStudySiteService().isStudyHasDCPId(sp.getIdentifier());
                            if (notCtepDcpTrial || superAbstractor) {
                                Long spId = IiConverter.convertToLong(sp.getIdentifier());
                                checkDisease = getDiseaseService().diseaseCodeMandatory(spId);
                                Ii ii = DSetConverter.convertToIi(sp.getSecondaryIdentifiers());
                                results.setNciIdentifier(ii.getExtension());

                                setAccrualSubmissionLevel(spId);
                                List<Long> ids = new ArrayList<Long>();
                                List<SearchStudySiteResultDto> isoStudySiteList = getSearchStudySiteService()
                                        .getTreatingSites(spId);
                                boolean trialHasBlankTreatingSite = false;
                                for (SearchStudySiteResultDto iso : isoStudySiteList) {
                                    listOfPoIds.put(IiConverter.convertToString(iso.getOrganizationIi()),
                                            IiConverter.convertToLong(iso.getStudySiteIi()));
                                    if (StringUtils.equals(blankOrgName, 
                                            StConverter.convertToString(iso.getOrganizationName()))) {
                                        trialHasBlankTreatingSite = true;
                                    }
                                }
                                if (!trialHasBlankTreatingSite) {
                                    listOfBlankIds.clear();
                                }
                                for (Map.Entry<String, Long> entry : listOfPoIds.entrySet()) {
                                    ids.add(IiConverter.convertToLong(IiConverter.convertToPoOrganizationIi(
                                            entry.getKey())));
                                }

                                if (!ids.isEmpty()) {
                                    List<IdentifiedOrganizationDTO> identifiedOrgs = PoRegistry
                                            .getIdentifiedOrganizationCorrelationService()
                                            .getCorrelationsByPlayerIdsWithoutLimit(ids.toArray(
                                                    new Long[ids.size()])); // NOPMD
                                    for (IdentifiedOrganizationDTO idOrgDTO : identifiedOrgs) {
                                        if (IiConverter.CTEP_ORG_IDENTIFIER_ROOT.equals(
                                                idOrgDTO.getAssignedId().getRoot())) {
                                            listOfCtepIds.put(
                                                    idOrgDTO.getAssignedId().getExtension(), 
                                                    idOrgDTO.getPlayerIdentifier().getExtension());
                                        }
                                    }
                                }
                            }
                        } catch (PAException e) {
                            LOG.error("Error loading study sites for a trial in validateBatchData.", e);
                        }
                    }
                }
                if (notCtepDcpTrial || superAbstractor) {
                    try {
                        validateBatchData(line, lineNumber, protocolId);
                    } catch (Exception e) {
                        bfErrors.append(new StringBuffer().append(e.getLocalizedMessage()));
                    }
                } 
            }
            LineIterator.closeQuietly(lineIterator);
            if (StringUtils.isEmpty(protocolId)) {
                bfErrors.append(new StringBuffer()
                .append("No Study Protocol Identifier could be found in the given file."));
            }
            if (!notCtepDcpTrial && !superAbstractor && StringUtils.isNotEmpty(protocolId) && sp != null) {
                bfErrors.append(new StringBuffer()
                .append("Only CTRO Team can do batch upload for " + protocolId + " identifier.\n"));
            }
            if (CollectionUtils.isNotEmpty(lines) && StringUtils.isNotEmpty(protocolId)
                    && (notCtepDcpTrial || superAbstractor)) {
            Map<String, List<String>> raceMap = BatchUploadUtils.getPatientRaceInfo(lines);
            List<String[]> patientLines = BatchUploadUtils.getPatientInfo(lines);
            for (String[] p : patientLines) {
                List<String> races = raceMap.get(p[BatchFileIndex.PATIENT_ID_INDEX]);
                if (races == null) {
                    bfErrors.append(new StringBuffer().append("Patient race code is missing for patient ID ")
                    .append(p[BatchFileIndex.PATIENT_ID_INDEX]).append("\n"));
                } 
              }
            }
            results.setErrors(new StringBuilder(bfErrors.toString().trim())); 
            results.setHasNonSiteErrors(bfErrors.isHasNonSiteErrors());
            if (StringUtils.isEmpty(bfErrors.toString().trim())) {
                setResultParameters(results, lines);
            } else if (!bfErrors.isHasNonSiteErrors() 
                    && StringUtils.isNotEmpty(bfErrors.toString().trim()) && superAbstractor) {
                setResultParameters(results, lines);
            } else {
                LOG.info(bfErrors.toString());
            }
        } catch (IOException e) {
            bfErrors.append(new StringBuffer().append("Unable to open the batch file: ").append(file.getName()));
            results.setErrors(new StringBuilder(bfErrors.toString().trim())); 
            results.setHasNonSiteErrors(bfErrors.isHasNonSiteErrors());
            LOG.error("error reading the file " + file.getName(), e);
        }
        LOG.info("Time to validate a single Batch File data: " 
                + (System.currentTimeMillis() - startTime) / TIME_SECONDS + " seconds");
        return results;
    }

    private void removeOutOfScopeTrial(String protocolId) {
        if (StringUtils.isNotBlank(protocolId)) {
            try {
                AccrualUtilityServiceRemote util = PaServiceLocator
                        .getInstance().getAccrualUtilityService();
                AccrualOutOfScopeTrialDTO dto = util.getByCtepID(protocolId);
                if (dto != null) {
                    util.delete(dto);
                }
            } catch (PAException e) {
                LOG.error(e, e);
            }
        }
    }

    private boolean isOutOfScopeTrial(String protocolId) {
        if (StringUtils.isNotBlank(protocolId)) {
            try {
                AccrualUtilityServiceRemote util = PaServiceLocator
                        .getInstance().getAccrualUtilityService();
                AccrualOutOfScopeTrialDTO dto = util.getByCtepID(protocolId);
                return dto != null && StringUtils.isNotBlank(dto.getAction());
            } catch (PAException e) {
                LOG.error(e, e);
            }
        }
        return false;
    }

    private void createOutOfScopeTrial(String protocolId, RegistryUser ru) {
        if (StringUtils.isNotBlank(protocolId)) {
            try {
                AccrualUtilityServiceRemote util = PaServiceLocator
                        .getInstance().getAccrualUtilityService();
                AccrualOutOfScopeTrialDTO dto = util.getByCtepID(protocolId);
                if (dto == null) {
                    final String reason = determineMissingTrialReason(protocolId);
                    dto = new AccrualOutOfScopeTrialDTO();
                    dto.setCtepID(protocolId);
                    dto.setFailureReason(reason);
                    dto.setSubmissionDate(new Date());
                    dto.setUserLoginName(ru != null && ru.getCsmUser() != null ? ru
                            .getCsmUser().getLoginName() : null);
                    dto.setAction(REJECTED.equalsIgnoreCase(reason) ? REJECTED
                            : StringUtils.EMPTY);
                    util.create(dto);
                }
            } catch (PAException e) {
                LOG.error(e, e);
            }
        }
    }

    private String determineMissingTrialReason(String nciOrCtepOrDcpID) {
        try {
            String escaped = StringEscapeUtils.escapeSql(nciOrCtepOrDcpID);
            String sql = "select dws.status_code from rv_dwf_current dws left join rv_trial_id_nci nci on "
                    + " dws.study_protocol_identifier=nci.study_protocol_id "
                    + "left join rv_ctep_id ctep on dws.study_protocol_identifier=ctep.study_protocol_identifier "
                    + "left join rv_dcp_id dcp on dws.study_protocol_identifier=dcp.study_protocol_identifier "
                    + "inner join study_protocol sp on sp.identifier=dws.study_protocol_identifier "
                    + "where (nci.extension='"
                    + escaped
                    + "' or ctep.local_sp_indentifier='"
                    + escaped
                    + "'"
                    + " or dcp.local_sp_indentifier='"
                    + escaped
                    + "') "
                    + "AND sp.status_code<>'"
                    + ActiveInactiveCode.INACTIVE.name()
                    + "' and dws.status_code='"
                    + DocumentWorkflowStatusCode.REJECTED.name() + "' LIMIT 1";
            return PaHibernateUtil.getCurrentSession().createSQLQuery(sql)
                    .uniqueResult() != null ? REJECTED : MISSING_TRIAL;
        } catch (HibernateException e) {
            LOG.error(e, e);
            return MISSING_TRIAL;
        }
    }

    void initializeOrganizationLists() {
        superAbstractor = AccrualUtil.isSuAbstractor(ru);
        listOfPoIds = new HashMap<String, Long>();
        listOfCtepIds = new HashMap<String, String>();
        listOfOrgIds = new HashMap<String, Ii>();
        listOfBlankIds = new HashMap<String, Ii>();
        blankOrgName = "";
        blankOrgDate = new Date(0L);
        if (superAbstractor) {
            try {
                blankOrgName = PaServiceLocator.getInstance().getLookUpTableService().
                        getPropertyValue("AccrualBlankSiteName");
                OrganizationSearchCriteriaDTO crit = new OrganizationSearchCriteriaDTO();
                crit.setName(blankOrgName);
                List<OrganizationDTO> orgList = PoRegistry.getOrganizationEntityService().
                        search(crit, new LimitOffset(1, 0));
                if (!orgList.isEmpty()) {
                    Ii blankOrgId = orgList.get(0).getIdentifier();
                    listOfBlankIds.put(null, blankOrgId);
                    listOfBlankIds.put("", blankOrgId);
                    listOfBlankIds.put("CTSU", blankOrgId);
                    listOfBlankIds.put("00000", blankOrgId);
                    String rejectDateStr = PaServiceLocator.getInstance().getLookUpTableService().
                            getPropertyValue("AccrualBlankSiteRejectDate");
                    if (rejectDateStr != null) {
                        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                        blankOrgDate = df.parse(rejectDateStr);
                    }
                }
            } catch (Exception e) {
                LOG.error("Exception retrieving and parsing the AccrualBlankSite properties.", e);
            }   
        }
    }

    private void setAccrualSubmissionLevel(Long spId) throws PAException {
        if (sp.getStudyProtocolType().getValue().equals(
                NonInterventionalStudyProtocol.class.getSimpleName())) {
            Long patientAccruals = subjectAccrualService.getAccrualCounts(true, spId);
            Long summaryAccruals = subjectAccrualService.getAccrualCounts(false, spId);
            if (patientAccruals == 0 && summaryAccruals == 0) {
                accrualSubmissionLevel = AccrualUtil.BOTH;
            } else if (patientAccruals > 0) {
                accrualSubmissionLevel = AccrualUtil.SUBJECT_LEVEL;
            } else if (summaryAccruals > 0) {
                accrualSubmissionLevel = AccrualUtil.SUMMARY_LEVEL; 
            }
        }
    }

    private void setResultParameters(BatchValidationResults results, List<String[]> lines) {
        results.setValidatedLines(lines);
        results.setPassedValidation(
                    StringUtils.isNotEmpty(results.getErrors().toString()) ? false : true);
        results.setListOfOrgIds(listOfOrgIds);
        results.setListOfPoStudySiteIds(listOfPoIds);
        results.setDiseaseCodeSystem(codeSystem);

        if (!bfErrors.isHasNonSiteErrors() && superAbstractor) {
            for (Long studySiteId : listOfPoIds.values()) {
                try {
                    subjectAccrualService.createAccrualAccess(ru, studySiteId);
                } catch (NumberFormatException e) {
                    LOG.error("NumberFormatException while creating Accrual access.", e);
                } catch (PAException e) {
                    LOG.error("Error creating Accrual access.", e);
                }
            }
        }
    }

    /**
     * This method split the data into key and value.
     * @param data data
     * @param lineNumber lineNumber
     * @param the expected protocol id
     * @return errMsg if any
     */
    private String validateBatchData(String[] data, long lineNumber, String expectedProtocolId) {
        String key = data != null ? data[0] : null;
        if (!LIST_OF_ELEMENT.containsKey(key.toUpperCase(Locale.US))) {
            return StringUtils.EMPTY;
        }
        List<String> values = Arrays.asList((String[]) ArrayUtils.subarray(data, 1, data.length));
        if (LIST_OF_ELEMENT.containsKey(key.toUpperCase(Locale.US)) 
                && LIST_OF_ELEMENT.get(key.toUpperCase(Locale.US)) != values.size()) {
            bfErrors.append(new StringBuffer().append(key).append(appendLineNumber(lineNumber))
                    .append(" does not have correct number of elements.\n"));
        }
        validateProtocolNumber(key, values, lineNumber, expectedProtocolId);
        validatePatientID(key, values, lineNumber);
        String studySiteID = null;
        Date registrationDate = null;
        boolean correctOrganizationId = false;
        if (StringUtils.equalsIgnoreCase(PATIENTS, key)) {
            studySiteID = AccrualUtil.safeGet(values, BatchFileIndex.PATIENT_REG_INST_ID_INDEX - 1);
            String dateOfEntry = AccrualUtil.safeGet(values, PATIENT_DATE_OF_ENTRY_INDEX);
            if (new DateValidator().isValid(dateOfEntry, "yyyyMMdd", Locale.getDefault())) {
                registrationDate = BatchUploadUtils.getDate(dateOfEntry);
            }
            correctOrganizationId = isCorrectOrganizationId(studySiteID, registrationDate, true);
        } else if (StringUtils.equalsIgnoreCase("ACCRUAL_COUNT", key)) {
            studySiteID = AccrualUtil.safeGet(values, BatchFileIndex.ACCRUAL_COUNT_STUDY_SITE_ID_INDEX - 1);
            correctOrganizationId = isCorrectOrganizationId(studySiteID, registrationDate, false);
        }
        validateStudySiteAccrualAccessCode(key, values, lineNumber, correctOrganizationId);
        validatePatientsMandatoryData(key, values, bfErrors, lineNumber, 
                    sp, codeSystem, checkDisease, accrualSubmissionLevel, superAbstractor);
        validateRegisteringInstitutionCode(key, values, lineNumber, correctOrganizationId);
        validatePatientRaceData(key, values, bfErrors, lineNumber);
        validateAccrualCount(key, values, bfErrors, lineNumber, sp, accrualSubmissionLevel);
        return bfErrors.toString();
    }
    
    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    private void validateRegisteringInstitutionCode(String key, List<String> values, 
            long lineNumber, boolean correctOrganizationId) {
        if (StringUtils.equalsIgnoreCase(PATIENTS, key)) {
            String registeringInstitutionID = AccrualUtil.safeGet(values, 
                    BatchFileIndex.PATIENT_REG_INST_ID_INDEX - 1);
            if (StringUtils.isEmpty(registeringInstitutionID) && !superAbstractor) {
                bfErrors.append(new StringBuffer()
                    .append("Patient Registering Institution Code is missing for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
            } else {
                if (!correctOrganizationId) {
                    return;
                }
                validatePatientTreatingSite(registeringInstitutionID, values, lineNumber);
            }
        }
    }
    
    private void validateStudySiteAccrualAccessCode(String key, List<String> values, 
            long lineNumber, boolean correctOrganizationId) {
        String studySiteID = null;
        if (StringUtils.equalsIgnoreCase(PATIENTS, key)) {
            studySiteID = AccrualUtil.safeGet(values, BatchFileIndex.PATIENT_REG_INST_ID_INDEX - 1);
        } else if (StringUtils.equalsIgnoreCase("ACCRUAL_COUNT", key)) {
            studySiteID = AccrualUtil.safeGet(values, BatchFileIndex.ACCRUAL_COUNT_STUDY_SITE_ID_INDEX - 1);
        }
        if (!StringUtils.isEmpty(studySiteID)) {
            if (!correctOrganizationId) {
                return;
            }
            validateTreatingSiteAndAccrualAccess(studySiteID, lineNumber, values);
        }
    }

    /*
     * Test that Registering Institution Code is NCI PO ID or CTEP ID
     */
    boolean isCorrectOrganizationId(String registeringInstitutionID, Date registrationDate,
            boolean isDetailed) {
        String msg = "The Registering Institution Code must be a valid PO or CTEP ID. Code: " 
                + registeringInstitutionID;
        String additionalErrorInfo = "";
        if (isDetailed && registrationDate != null && registrationDate.before(blankOrgDate)
                && listOfBlankIds.containsKey(registeringInstitutionID)) {
            if (listOfOrgIds.get(registeringInstitutionID) == null) {
                listOfOrgIds.put(registeringInstitutionID, listOfBlankIds.get(registeringInstitutionID));
            }
            return true;
        }
        if (listOfPoIds.containsKey(registeringInstitutionID)) {
            if (listOfOrgIds.get(registeringInstitutionID) == null) {
                listOfOrgIds.put(registeringInstitutionID, 
                    IiConverter.convertToIi(registeringInstitutionID));
            }
            return true;
        } else if (listOfCtepIds.containsKey(registeringInstitutionID)) {
            if (listOfOrgIds.get(registeringInstitutionID) == null) {
                listOfOrgIds.put(registeringInstitutionID, 
                   IiConverter.convertToIi(listOfCtepIds.get(registeringInstitutionID)));
            }
            return true;
        }
        try {
            if (StringUtils.isNumeric(registeringInstitutionID)) {
                OrganizationDTO poOrganization = PoRegistry.getOrganizationEntityService()
                    .getOrganization(IiConverter.convertToPoOrganizationIi(registeringInstitutionID));
                if (poOrganization != null) {
                    return true;
                }
            }
        } catch (NullifiedEntityException e) {
            additionalErrorInfo = getErrorMessageForNullifiedOrg(e);
            bfErrors.appendSiteError(new StringBuffer().append(msg
                    + additionalErrorInfo + "\n"));
            return false;
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
        
        try {
            if (StringUtils.isNotBlank(registeringInstitutionID)) {
                Ii identifier = getPoHcfByCtepId(IiConverter.convertToIdentifiedOrgEntityIi(registeringInstitutionID));
                if (identifier != null && identifier.getExtension() != null) {
                    return true;
                }
            }
        } catch (PAException e) {
            LOG.info(e.getMessage());
        }
        additionalErrorInfo = getErrorMessageForNullifiedOrg(registeringInstitutionID);
        bfErrors.appendSiteError(new StringBuffer().append(msg
                + additionalErrorInfo + "\n"));
        return false;
    }

    private String getErrorMessageForNullifiedOrg(NullifiedEntityException e) {
        try {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            for (Map.Entry<Ii, Ii> entry : nullifiedEntities.entrySet()) {
                Ii dupeof = entry.getValue();
                return getErrorMessageForNullifiedOrg(dupeof);
            }
        } catch (Exception e1) {
            LOG.warn(e1.getMessage());
        }
        return "";
    }

    private String getErrorMessageForNullifiedOrg(String ctepID) {
        String msg = "";
        if (StringUtils.isNotBlank(ctepID)) {
            try {
                Ii mergedInto = PoRegistry.getOrganizationEntityService()
                        .getDuplicateOfNullifiedOrg(ctepID);
                if (!ISOUtil.isIiNull(mergedInto)) {
                    msg = getErrorMessageForNullifiedOrg(mergedInto);
                }
            } catch (Exception e) {
                LOG.warn(e.getMessage());
            }
        }
        return msg;
    }

    private String getErrorMessageForNullifiedOrg(Ii orgID)
            throws NullifiedEntityException, NullifiedRoleException,
            PAException, TooManyResultsException {
        StringBuilder sb = new StringBuilder();
        OrganizationDTO org = PoRegistry.getOrganizationEntityService()
                .getOrganization(orgID);
        String ctepID = AccrualUtil.findOrgCtepID(org);

        sb.append("*<br/>");
        sb.append("&nbsp;&nbsp;&nbsp; *This organization's record has been nullified and merged with another organization. <br/>");
        sb.append("&nbsp;&nbsp;&nbsp; The new organization is: <br/>");
        sb.append("&nbsp;&nbsp;&nbsp; Name: "
                + EnOnConverter.convertEnOnToString(org.getName()) + "<br/>");
        sb.append("&nbsp;&nbsp;&nbsp; PO ID: "
                + IiConverter.convertToLong(orgID) + "<br/>");
        sb.append("&nbsp;&nbsp;&nbsp; CTEP ID: "
                + StringUtils.defaultString(ctepID, "N/A") + "<br/>");
        return sb.toString();
    }

    /**
     * getPoHcfByCtepId.
     * @param ctepHcfIi ii
     * @return ii
     * @throws PAException when error
     */
    private Ii getPoHcfByCtepId(Ii ctepHcfIi) throws PAException {
        HealthCareFacilityDTO criteria = new HealthCareFacilityDTO();
        criteria.setIdentifier(DSetConverter.convertIiToDset(ctepHcfIi));
        List<HealthCareFacilityDTO> hcfs =
            PoRegistry.getHealthCareFacilityCorrelationService().search(criteria);
        if (hcfs.isEmpty()) {
            throw new PAException("Provided HCF CTEP ID: "
                    + ctepHcfIi.getExtension()
                    + " but did not find a corresponding HCF in PO.");
        } else if (hcfs.size() > 1) {
            throw new PAException("more than 1 HCF found for given CTEP ID: "
                    + ctepHcfIi.getExtension());
        }
        return DSetConverter.convertToIi(hcfs.get(0).getIdentifier());
    }
    
    /**
     * Test that the treating site ctep id exists for the particular trial being used.
     * Do not validate if trial cannot be found as that validation is already being done 
     * on the COLLECTION line.
     */
    private void validatePatientTreatingSite(String regInstID, List<String> values, long lineNumber) {
        if (listOfPoIds.containsKey(regInstID) || listOfCtepIds.containsKey(regInstID) 
                || listOfBlankIds.containsKey(regInstID)) {
            return;           
        }
        addUpPatientRegisteringInstitutionCode(values, lineNumber);
    }
    
    /**
     * Test that the treating site ctep id exists for the particular trial being used. 
     * And that the user has accrual access to the site.
     * Do not validate if trial cannot be found as that validation is already being done 
     * on the COLLECTION line.
     * @throws PAException 
     */
    private void validateTreatingSiteAndAccrualAccess(String regInstID, long lineNumber, 
            List<String> values) {
        if (listOfPoIds.containsKey(regInstID)  || listOfCtepIds.containsKey(regInstID)) {
            assertUserAllowedSiteAccess(sp.getIdentifier(), regInstID, lineNumber, values);
            return;
        }
        if (listOfBlankIds.containsKey(regInstID)) {
            return;
        }
        addAccrualSiteValidationError(regInstID, lineNumber);
    }
    
    /**
     * Assert batch submitter has accrual access to sites.
     * @param studyProtocolIi the study protocol ii
     * @param regInstID site ID provided in file.
     * @param lineNumber location of input
     * @param values the line values
     */
    protected void assertUserAllowedSiteAccess(Ii studyProtocolIi, String regInstID, 
            long lineNumber, List<String> values) {
        try {
            Long studySiteIi  = null;
            String poId = null;
            studySiteIi = listOfPoIds.get(regInstID);
            if (studySiteIi == null) {
                poId = listOfCtepIds.get(regInstID);
            }
            if (StringUtils.isNotEmpty(poId)) {
                studySiteIi = listOfPoIds.get(poId);
            }
            patientsFromBatchFile.add(new SubjectAccrualKey(studySiteIi, getPatientId(values)));
            boolean isSiteFamilySubmitter = false;
            StudySiteDTO ssDto = PaServiceLocator.getInstance().getStudySiteService()
                    .get(IiConverter.convertToIi(studySiteIi));
            StudySite ss = new StudySiteConverter().convertFromDtoToDomain(ssDto);
            if (ss.getHealthCareFacility() != null 
                && new AccrualUtil().isUserAllowedSiteOrFamilyAccrualAccess(
                     ss.getHealthCareFacility().getOrganization().getIdentifier())) {
                    isSiteFamilySubmitter = true;
            }
            if (!superAbstractor 
                    && !AccrualUtil.isUserAllowedAccrualAccess(IiConverter.convertToIi(studySiteIi), ru) 
                    && !isSiteFamilySubmitter) {
                addAccrualAccessBySiteError(regInstID, lineNumber);
            }
        } catch (PAException e) {
            addAccrualAccessBySiteError(regInstID, lineNumber);
        }
    }
    
    private void addUpPatientRegisteringInstitutionCode(List<String> values, long lineNumber) {
        bfErrors.appendSiteError(new StringBuffer()
                    .append("Patient Registering Institution Code is invalid for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
    }
    
    private void addAccrualAccessBySiteError(String studySiteID, long lineNumber) {
        bfErrors.append(new StringBuffer().append("User " + ru.getFirstName() + " " + ru.getLastName() 
                + " does not have accrual access to Study Site ID " + studySiteID)
        .append(appendLineNumber(lineNumber)).append("\n"));
    }
    
    private void addAccrualSiteValidationError(String siteId, long lineNumber) {
        bfErrors.appendSiteError(new StringBuffer().append("Accrual study site ").append(siteId)
        .append(" is not valid")
        .append(appendLineNumber(lineNumber)).append("\n"));
    }

    /**
     * 
     * @param key key
     * @param values values
     * @param lineNumber 
     * @param expectedProtocolId the protocol id expected to be seen.
     */
    private void validateProtocolNumber(String key, List<String> values, long lineNumber, String expectedProtocolId) {
        String protocolId = AccrualUtil.safeGet(values, BatchFileIndex.COLLECTION_PROTOCOL_INDEX);
        if (StringUtils.isEmpty(protocolId)) {
            bfErrors.append(new StringBuffer().append(key).append(appendLineNumber(lineNumber))
                .append(" must contain a valid NCI protocol identifier or the CTEP/DCP identifier.\n"));
        } else if (!StringUtils.equalsIgnoreCase(protocolId, expectedProtocolId)) {
            bfErrors.append(new StringBuffer().append(key).append(appendLineNumber(lineNumber))
            .append(" does not contain the same protocol identifier as the one specified in the COLLECTIONS line.\n"));
        } else if (StringUtils.equalsIgnoreCase(key, "COLLECTIONS")) {
            validateProtocolStatus(key, lineNumber, protocolId);    
        }
    }
    
    /**
     * Validates that the study protocol id is valid and active.
     * @param key  the key
     * @param errMsg error messages
     * @param lineNumber line number
     * @param protocolId the study protocol id
     */
    private void validateProtocolStatus(String key, long lineNumber, String protocolId) {
        if (sp == null) {
            bfErrors.append(new StringBuffer().append(key).append(appendLineNumber(lineNumber)).append(protocolId)
            .append(" is not a valid NCI or CTEP/DCP identifier.\n"));
        } else if (!StringUtils.equalsIgnoreCase(sp.getStatusCode().getCode(), ActStatusCode.ACTIVE.getCode())) {
            bfErrors.append(new StringBuffer().append(key).append(appendLineNumber(lineNumber))
                    .append(" with the identifier ").append(protocolId).append(" is not an Active study.\n")); 
        } else if (!hasAccrualAccess(sp.getIdentifier())) {
            bfErrors.append(new StringBuffer().append(key).append(appendLineNumber(lineNumber))
            .append(CsmUserUtil.getGridIdentityUsername(ru.getCsmUser().getLoginName()))
            .append(" does not have accrual access to the study protocol with the identifier ").append(protocolId)
            .append(" \n"));
        }
    }
    
    private boolean hasAccrualAccess(Ii spIi) {
        try {
            boolean hasAccess = BlConverter.convertToBool(getSearchTrialService().isAuthorized(spIi, 
                    IiConverter.convertToIi(ru.getId())));
           if (hasAccess || superAbstractor) {
                return true;
            }
        } catch (Exception e) {
            LOG.error("Error determining accrual access for " + ru.getCsmUser().getLoginName() + ".", e);
            return false;
        }
        return false;
    }  
  
    private void validatePatientID(String key, List<String> values, long lineNumber) {
        if (KEY_WITH_PATIENTS_IDS.contains(key) && StringUtils.isEmpty(getPatientId(values))) {
            bfErrors.append(new StringBuffer().append(key).append(appendLineNumber(lineNumber))
                .append(" must contain a patient identifier that is unique within the study.\n"));
        }
    }
    
    /**
     * @param subjectAccrualService the subject accrual service to set
     */
    public void setSubjectAccrualService(SubjectAccrualServiceLocal subjectAccrualService) {
        this.subjectAccrualService = subjectAccrualService;
    }
    
    /**
     * @return the subjectAccrualService
     */
    public SubjectAccrualServiceLocal getSubjectAccrualService() {
        return subjectAccrualService;
    }

    Map<String, Long> getListOfPoIds() {
        return listOfPoIds;
    }

    Map<String, String> getListOfCtepIds() {
        return listOfCtepIds;
    }

    Map<String, Ii> getListOfOrgIds() {
        return listOfOrgIds;
    }

    Map<String, Ii> getListOfBlankIds() {
        return listOfBlankIds;
    }

    Date getBlanksOrgDate() {
        return blankOrgDate;
    }

    void setRu(RegistryUser ru) {
        this.ru = ru;
    }
    
    /**
     * @return BatchFileErrors BatchFileErrors
     */
    public BatchFileErrors getBfErrors() {
        return bfErrors;
    }
}
