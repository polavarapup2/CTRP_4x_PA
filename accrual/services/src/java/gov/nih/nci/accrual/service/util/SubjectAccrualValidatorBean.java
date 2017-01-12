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
package gov.nih.nci.accrual.service.util;

import static gov.nih.nci.accrual.service.batch.CdusBatchUploadReaderBean.ICD_O_3_CODESYSTEM;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.enums.CDUSPaymentMethodCode;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.exception.IndexedInputValidationException;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author merenkoi
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class SubjectAccrualValidatorBean implements SubjectAccrualValidator {

    private static final String REQUIRED_MSG = "%s is a required field.\n";
    private static final String INVALID_VALUE = "%s is not a valid value for %s.\n";
    private static final String DELETED_STATUS_CODE = FunctionalRoleStatusCode.NULLIFIED.getCode();
    private static final String DIDENTIFIERSTRING = "Disease Identifier";

    @EJB
    private StudySubjectServiceLocal studySubjectService;

    @EJB
    private CountryService countryService;

    @EJB
    private AccrualDiseaseServiceLocal diseaseService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(List<SubjectAccrualDTO> subjects) throws PAException {
        if (subjects == null) {
            return;
        }
        for (int i = 0; i < subjects.size(); i++) {
            validateRequiredFields(subjects.get(i), i);
            validateNoStudySubjectDuplicates(subjects.get(i), i);
        }
    }

    private void validateRequiredFields(SubjectAccrualDTO subjectAccrual, int index) 
            throws PAException {
        StringBuffer errors = new StringBuffer();
        if (ISOUtil.isStNull(subjectAccrual.getAssignedIdentifier())) {
            errors.append(String.format(REQUIRED_MSG, "Assigned Identifier"));
        }
        validateDatesAndPaymentMethod(subjectAccrual, errors);
        validateGenderAndEthnicity(subjectAccrual, errors);
        validateRaces(subjectAccrual, errors);
        validateCountry(subjectAccrual, errors);
        validateDiseaseAndParticipatingSite(subjectAccrual, errors);
        if (errors.length() != 0) {
            throw new IndexedInputValidationException(errors.toString(), index);
        }
    }

    void validateRaces(SubjectAccrualDTO subjectAccrual, StringBuffer errors) {
        if (subjectAccrual.getRace() == null || CollectionUtils.isEmpty(subjectAccrual.getRace().getItem())) {
            errors.append(String.format(REQUIRED_MSG, "Race"));
        } else {
            for (Cd cd : subjectAccrual.getRace().getItem()) {
                String code = CdConverter.convertCdToString(cd);
                if (CDUSPatientRaceCode.getByCode(code) == null) {
                    errors.append(String.format(INVALID_VALUE, code, "Race Code"));
                }
            }
        }
    }

    /**
     * Validate that if an id is supplied that it is valid. Then validate not creating a duplicate
     * patient with the same assigned identifier for a given site.
     * @param subjectAccrual Study Subject DTO
     * @param index index
     * @throws PAException exception thrown if validation fails
     */
    void validateNoStudySubjectDuplicates(SubjectAccrualDTO subjectAccrual, int index) throws PAException {
        Ii ii = subjectAccrual.getIdentifier();
        if (!ISOUtil.isIiNull(ii) && studySubjectService.get(ii) == null) {
            throw new IndexedInputValidationException("Subject identifier not found.", index);
        }
        StudySubject ssub = studySubjectService.get(new SubjectAccrualKey(
                subjectAccrual.getParticipatingSiteIdentifier(), subjectAccrual.getAssignedIdentifier()));
        if (ssub != null 
                && !ObjectUtils.equals(IiConverter.convertToLong(subjectAccrual.getIdentifier()), ssub.getId()) 
                && !DELETED_STATUS_CODE.equals(ssub.getStatusCode().getCode())) {
            throw new IndexedInputValidationException("This Study Subject Id ("
                    + StConverter.convertToString(subjectAccrual.getAssignedIdentifier())
                    + ") has already been added to this study.", index);
        }
    }

    void validateDatesAndPaymentMethod(SubjectAccrualDTO dto, StringBuffer errMsg) {
        if (ISOUtil.isTsNull(dto.getBirthDate())) {
            errMsg.append(String.format(REQUIRED_MSG, "Birth Date"));
        }
        if (ISOUtil.isTsNull(dto.getRegistrationDate())) {
            errMsg.append(String.format(REQUIRED_MSG, "Registration Date"));
        }
        String code = CdConverter.convertCdToString(dto.getPaymentMethod());
        if (code != null && CDUSPaymentMethodCode.getByCode(code) == null) {
            errMsg.append(String.format(INVALID_VALUE, code, "Payment Method Code"));
        }
    }

    private void validateCountry(SubjectAccrualDTO dto, StringBuffer errMsg) throws PAException {
        String code = CdConverter.convertCdToString(dto.getCountryCode());
        if (ISOUtil.isCdNull(dto.getCountryCode())) {
            errMsg.append(String.format(REQUIRED_MSG, "Country Code"));
        } else if (countryService.getByCode(code) == null) {
            errMsg.append(String.format(INVALID_VALUE, code, "Country Code"));
        } else if ((StringUtils.equals("US", code) || StringUtils.equals("USA",
                code)) && ISOUtil.isStNull(dto.getZipCode())) {
            errMsg.append("Zip Code must be specified when the subject's country is the US.\n");
        }
    }

    void validateGenderAndEthnicity(SubjectAccrualDTO subjectAccrual, StringBuffer errMsg) {
        String gender = CdConverter.convertCdToString(subjectAccrual.getGender());
        if (StringUtils.isEmpty(gender)) {
            errMsg.append(String.format(REQUIRED_MSG, "Gender"));
        } else if (CDUSPatientGenderCode.getByCode(gender) == null) {
            errMsg.append(String.format(INVALID_VALUE, gender, "Gender"));
        }
        String ethnicity = CdConverter.convertCdToString(subjectAccrual.getEthnicity());
        if (StringUtils.isEmpty(ethnicity)) {
            errMsg.append(String.format(REQUIRED_MSG, "Ethnicity"));
        } else if (CDUSPatientEthnicityCode.getByCode(ethnicity) == null) {
            errMsg.append(String.format(INVALID_VALUE, ethnicity, "Ethnicity"));
        }
    }

     void validateDiseaseAndParticipatingSite(SubjectAccrualDTO subjectAccrual,
            StringBuffer errMsg) throws PAException {
        Ii siteIi = subjectAccrual.getParticipatingSiteIdentifier();
        if (ISOUtil.isIiNull(siteIi)) {
            errMsg.append(String.format(REQUIRED_MSG, "Participating Site Identifier"));
            return;
        }
        StudySiteDTO studySite = PaServiceLocator.getInstance().getStudySiteService().get(siteIi);
        if (studySite == null) {
            errMsg.append(String.format(INVALID_VALUE, subjectAccrual.getParticipatingSiteIdentifier().getExtension(),
                                        "Participating Site Identifier"));
            return;
        }
        Long spId = IiConverter.convertToLong(studySite.getStudyProtocolIdentifier());
        String dCode = PaServiceLocator.getInstance().getAccrualDiseaseTerminologyService().getCodeSystem(spId);
        if (ISOUtil.isIiNull(subjectAccrual.getDiseaseIdentifier())) {
            if (diseaseService.diseaseCodeMandatory(spId)
                && !(dCode.equals(ICD_O_3_CODESYSTEM)
                      && !ISOUtil.isIiNull(subjectAccrual.getSiteDiseaseIdentifier()))) {
                    errMsg.append(String.format(REQUIRED_MSG, DIDENTIFIERSTRING));
               
            }
        } else  if (StringUtils.equalsIgnoreCase(IiConverter.convertToString(subjectAccrual
                    .getDiseaseIdentifier()), "not found")) {
            errMsg.append("Disease code does not exist for given Disease code System.");
        } else {
            AccrualDisease disease = diseaseService.get(subjectAccrual.getDiseaseIdentifier());
            if (disease == null) {
               errMsg.append(String.format(INVALID_VALUE, subjectAccrual.getDiseaseIdentifier().getExtension(),
                         DIDENTIFIERSTRING));
            } else {
                if (!dCode.equals(disease.getCodeSystem())) {
                    errMsg.append("The subject's disease's coding system ");
                    errMsg.append(disease.getCodeSystem());
                    errMsg.append(" is different from the one used on the trial: ");
                    errMsg.append(dCode);
                }
            }
            if (!ISOUtil.isIiNull(subjectAccrual.getSiteDiseaseIdentifier())) {
            disease = diseaseService.get(subjectAccrual.getSiteDiseaseIdentifier());
            if (disease == null) {
                errMsg.append(String.format(INVALID_VALUE, subjectAccrual.getSiteDiseaseIdentifier().getExtension(),
                                            "Site Disease Identifier"));
            } else {
                if (!dCode.equals(disease.getCodeSystem())) {
                    errMsg.append(String.format(INVALID_VALUE, subjectAccrual.getSiteDiseaseIdentifier().getExtension(),
                            "Site Disease Identifier"));
                }
            }
          }
        }
    }

    /**
     * @param studySubjectService the studySubjectService to set
     */
    public void setStudySubjectService(StudySubjectServiceLocal studySubjectService) {
        this.studySubjectService = studySubjectService;
    }    

    /**
     * @param countryService the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * @param diseaseService the diseaseService to set
     */
    public void setDiseaseService(AccrualDiseaseServiceLocal diseaseService) {
        this.diseaseService = diseaseService;
    }  

}
