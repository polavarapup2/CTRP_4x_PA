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

import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.DateValidator;

/**
 * @author Igor Merenko
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.AppendCharacterWithChar", "PMD.ExcessiveParameterList" })
public class BaseValidatorBatchUploadReader extends BaseBatchUploadReader {  

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     * @param sp studyprotocol
     * @param accrualSubmissionLevel the accrualSubmissionLevel
     */
    @SuppressWarnings({ "PMD.ExcessiveParameterList" })
    protected void validateAccrualCount(String key, List<String> values, BatchFileErrors errMsg, long lineNumber,
            StudyProtocolDTO sp, String accrualSubmissionLevel) {
        if (StringUtils.equalsIgnoreCase("ACCRUAL_COUNT", key)) {
            validateStudyType(errMsg, sp, accrualSubmissionLevel);
            String accrualStudySite = AccrualUtil.safeGet(values, ACCRUAL_STUDY_SITE_INDEX);
            if (StringUtils.isEmpty(accrualStudySite)) {
                errMsg.append(new StringBuffer().append("Accrual study site is missing")
                        .append(appendLineNumber(lineNumber)).append("\n"));
            }
            String accrualCount = AccrualUtil.safeGet(values, ACCRUAL_COUNT_INDEX);
            if (StringUtils.isEmpty(accrualCount)) {
                errMsg.append(new StringBuffer().append("Accrual count is missing")
                        .append(appendLineNumber(lineNumber)).append("\n"));
            }
        }
    }

    private void validateStudyType(BatchFileErrors errMsg, StudyProtocolDTO sp, String accrualSubmissionLevel) {
        if (sp != null && !sp.getProprietaryTrialIndicator().getValue()
                && sp.getStudyProtocolType().getValue().equals(InterventionalStudyProtocol.class.getSimpleName())) {
            errMsg.append(new StringBuffer()
            .append("Accrual count has been provided for a non Industrial study. This is not allowed.\n"));
        } else if (accrualSubmissionLevel != null 
                    && accrualSubmissionLevel.equals(AccrualUtil.SUBJECT_LEVEL)) {
            errMsg.append(new StringBuffer().append("Non-interventional study has subject level accruals. "
                   + "Summary level accruals cannot be accepted now.\n"));
        }
    }
    
    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     * @param sp study protocol
     * @param codeSystem diseasecode
     * @param checkDisease checkDisease
     * @param accrualSubmissionLevel submissionLevel
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    @SuppressWarnings({ "PMD.AvoidDeeplyNestedIfStmts" })
    protected void validatePatientsMandatoryData(String key, List<String> values, BatchFileErrors errMsg, long lineNumber,
            StudyProtocolDTO sp, String codeSystem, boolean checkDisease, String accrualSubmissionLevel, boolean superAbstractor) {
        if (StringUtils.equalsIgnoreCase("PATIENTS", key)) {
            boolean validatePatients = true;            
            if (sp != null && sp.getProprietaryTrialIndicator().getValue()
                    && sp.getStudyProtocolType().getValue().equals(InterventionalStudyProtocol.class.getSimpleName())
                    || accrualSubmissionLevel != null 
                    		&& accrualSubmissionLevel.equals(AccrualUtil.SUMMARY_LEVEL)) {
                validatePatients = false;
            }
            if (validatePatients) {
                List<String> patientsIdList = new ArrayList<String>();
                isPatientIdUnique(getPatientId(values), errMsg, lineNumber, patientsIdList);
                String pBirthDate = AccrualUtil.safeGet(values, PATIENT_BRITH_DATE_INDEX);
                if (!StringUtils.isEmpty(pBirthDate) 
                        && !new DateValidator().isValid(pBirthDate, "yyyyMM", Locale.getDefault())) {
                	errMsg.append(new StringBuffer()
                	.append("Patient birth date must be in YYYYMM format for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
                }
                String countryCode = AccrualUtil.safeGet(values, PATIENT_COUNTRY_CODE_INDEX);
                if (!getCountryService().isValidAlpha2(countryCode)) {
                	errMsg.append(new StringBuffer().append("Please enter valid alpha2 country code for patient ID ")
                		.append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
                }
                validateGender(values, errMsg, lineNumber);
                validateEthnicity(values, errMsg, lineNumber);
                validateDateOfEntry(values, errMsg, lineNumber);
                validateDiseaseCode(values, errMsg, lineNumber, sp, codeSystem, checkDisease, superAbstractor);
                String paymentMethod = AccrualUtil.safeGet(values, PATIENT_PAYMENT_METHOD_INDEX);
                if (!StringUtils.isEmpty(paymentMethod) && !PATIENT_PAYMENT_METHOD.contains(paymentMethod.trim())) {
                	errMsg.append(new StringBuffer().append("Please enter valid patient payment method for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
                }
            } else if (accrualSubmissionLevel != null 
            		&& accrualSubmissionLevel.equals(AccrualUtil.SUMMARY_LEVEL)) {
            	errMsg.append(new StringBuffer().append("Non-interventional study has summary level accruals."
                     + " Subject level accruals cannot be accepted now for patient ID ")
                .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));            	
            } else {
            	errMsg.append(new StringBuffer()
            	.append("Individual Patients should not be added to Industrial Trials for patient ID ")
                .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
            }
        }
    }

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validatePatientRaceData(String key, List<String> values, BatchFileErrors errMsg, long lineNumber) {
        if (StringUtils.equalsIgnoreCase("PATIENT_RACES", key)) {
            String pRaceCode = AccrualUtil.safeGet(values, PATIENT_RACE_CODE_INDEX);
            if (StringUtils.isEmpty(pRaceCode)) {
            	errMsg.append(new StringBuffer().append("Patient race code is missing for patient ID ")
            			.append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
            } else if (!PATIENT_RACE_CODE.contains(pRaceCode.trim())) {
            	errMsg.append(new StringBuffer().append("Patient race code is not valid for patient ID ")
            			.append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
            }
        }
    }

    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validateDateOfEntry(List<String> values, BatchFileErrors errMsg, long lineNumber) {
        String dateOfEntry = AccrualUtil.safeGet(values, PATIENT_DATE_OF_ENTRY_INDEX);
        if (StringUtils.isEmpty(dateOfEntry)) {
        	errMsg.append(new StringBuffer().append("Patient date of entry is missing for patient ID ")
        			.append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
        } else if (!new DateValidator().isValid(dateOfEntry, "yyyyMMdd", Locale.getDefault())) {
        	errMsg.append(new StringBuffer().append("Patient date of entry must be in YYYYMMDD format for patient ID ")
                .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
        }
    }

    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validateEthnicity(List<String> values, BatchFileErrors errMsg, long lineNumber) {
        String ethnicity = AccrualUtil.safeGet(values, PATIENT_ETHNICITY_INDEX);
        if (StringUtils.isEmpty(ethnicity)) {
        	errMsg.append(new StringBuffer().append("Patient ethnicity is missing for patient ID ")
        			.append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
        } else if (!PATIENT_ETHNICITY.contains(ethnicity.trim())) {
        	errMsg.append(new StringBuffer().append("Please enter valid patient ethnicity for patient ID ")
        			.append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
        }
    }

    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    private void validateGender(List<String> values, BatchFileErrors errMsg, long lineNumber) {
        String genderCode = AccrualUtil.safeGet(values, PATIENT_GENDER_CODE_INDEX);
        if (StringUtils.isEmpty(genderCode)) {
        	errMsg.append(new StringBuffer().append("Patient gender is missing for patient ID ")
        		 .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
        } else if (!PATIENT_GENDER.contains(genderCode.trim())) {
        	errMsg.append(new StringBuffer().append("Must be a valid patient gender for patient ID ")
        			.append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
        }
    }

    /**
     * Validates that the patient disease is provided and valid. If a study has the primary purpose 'Prevention', the
     * Meddra/ICD9 Disease code is not required.
     */
    void validateDiseaseCode(List<String> values, BatchFileErrors errMsg, long lineNumber, StudyProtocolDTO sp, 
        String codeSystem, boolean checkDisease, boolean superAbstractor) {
        String code = AccrualUtil.safeGet(values, PATIENT_DISEASE_INDEX);
        if (StringUtils.isEmpty(code)) {
            if (checkDisease && !superAbstractor) {
            	errMsg.append(new StringBuffer().append("Patient Disease Code is missing or not recognized for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
            }
        } else {
            StringTokenizer disease = new StringTokenizer(code, ";");
            if (!disease.hasMoreElements() && !superAbstractor) {
            	errMsg.append(new StringBuffer().append("Patient Disease Code is missing or not recognized for patient ID ")
                .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));            
            } else {
                while (disease.hasMoreElements()) {
                   String diseaseCode = AccrualUtil.checkIfStringHasForwardSlash(disease.nextElement().toString());
                   AccrualDisease dis = getDiseaseService().getByCode(codeSystem, diseaseCode);
                   if (dis == null) {
                      errMsg.append(new StringBuffer()
                              .append("Patient " + codeSystem + " Disease Code is invalid for patient ID ")
                              .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n"));
                   }
                }
            }
        }
    }

    private void isPatientIdUnique(String patId, BatchFileErrors errMsg, long lineNumber, List<String> patientsIdList) {
        if (patientsIdList.contains(patId)) {
        	errMsg.append(new StringBuffer().append("Patient identifier " + patId + " is not unique ")
        			.append(appendLineNumber(lineNumber)).append("\n"));
        } else {
            patientsIdList.add(patId);
        }
    }  

}
