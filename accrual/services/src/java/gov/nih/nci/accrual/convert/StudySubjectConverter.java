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
package gov.nih.nci.accrual.convert;

import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.convert.AbstractStudyProtocolConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.ISOUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 */
public class StudySubjectConverter extends AbstractConverter<StudySubjectDto, StudySubject> {
    private static final Logger LOG = Logger.getLogger(StudySubjectConverter.class);
    /**
     * {@inheritDoc}
     */
    @Override
    public StudySubjectDto convertFromDomainToDto(StudySubject bo) {
        StudySubjectDto dto = new StudySubjectDto();
        dto.setAssignedIdentifier(StConverter.convertToSt(bo.getAssignedIdentifier()));
        dto.setIdentifier(IiConverter.convertToSubjectAccrualIi(bo.getId()));
        dto.setPatientIdentifier(IiConverter.convertToIi(bo.getPatient().getId()));
        dto.setPaymentMethodCode(CdConverter.convertToCd(bo.getPaymentMethodCode()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(
             bo.getStudyProtocol() == null ? null : bo.getStudyProtocol().getId()));
        dto.setStudySiteIdentifier(IiConverter.convertToIi(bo.getStudySite() == null ? null 
                : bo.getStudySite().getId()));
        dto.setDiseaseIdentifier(IiConverter.convertToIi(bo.getDisease() == null ? null : bo.getDisease().getId()));
        dto.setSiteDiseaseIdentifier(IiConverter.convertToIi(
                bo.getSiteDisease() == null ? null : bo.getSiteDisease().getId()));
        dto.setRegistrationGroupId(StConverter.convertToSt(bo.getRegistrationGroupId()));
        dto.setSubmissionTypeCode(CdConverter.convertToCd(bo.getSubmissionTypeCode()));
        dto.setDeleteReason(StConverter.convertToSt(bo.getDeleteReason()));
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySubject convertFromDtoToDomain(StudySubjectDto dto) {
        StudySubject bo = new StudySubject();
        bo.setAssignedIdentifier(StConverter.convertToString(dto.getAssignedIdentifier()));
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        }
        bo.setPatient(fKeySetter(Patient.class, dto.getPatientIdentifier()));
        if (!ISOUtil.isCdNull(dto.getPaymentMethodCode())) {
            bo.setPaymentMethodCode(PaymentMethodCode.getByCode(dto.getPaymentMethodCode().getCode()));
        }
        if (!ISOUtil.isCdNull(dto.getStatusCode())) {
            bo.setStatusCode(FunctionalRoleStatusCode.getByCode(dto.getStatusCode().getCode()));
        }
        bo.setStudyProtocol(fKeySetter(StudyProtocol.class, dto.getStudyProtocolIdentifier()));
        bo.setStudySite(fKeySetter(StudySite.class, dto.getStudySiteIdentifier()));
        if (!ISOUtil.isIiNull(dto.getDiseaseIdentifier())) {
            AccrualDisease ad = new AccrualDisease();
            ad.setId(IiConverter.convertToLong(dto.getDiseaseIdentifier()));
            bo.setDisease(ad);
        }
        if (!ISOUtil.isIiNull(dto.getSiteDiseaseIdentifier())) {
            AccrualDisease ad = new AccrualDisease();
            ad.setId(IiConverter.convertToLong(dto.getSiteDiseaseIdentifier()));
            bo.setSiteDisease(ad);
        }
        bo.setRegistrationGroupId(StConverter.convertToString(dto.getRegistrationGroupId()));
        if (!ISOUtil.isCdNull(dto.getSubmissionTypeCode())) {
            bo.setSubmissionTypeCode(AccrualSubmissionTypeCode.getByCode(dto.getSubmissionTypeCode().getCode()));
        }
        bo.setDeleteReason(StConverter.convertToString(dto.getDeleteReason()));
        if (!ISOUtil.isTsNull(dto.getDateLastCreated())) {
            bo.setDateLastCreated(TsConverter.convertToTimestamp(dto.getDateLastCreated()));
        }
        if (!ISOUtil.isTsNull(dto.getDateLastUpdated())) {
            bo.setDateLastUpdated(TsConverter.convertToTimestamp(dto.getDateLastUpdated()));
        }
        setUserFields(dto, bo);
        return bo;
    }

    private void setUserFields(StudySubjectDto dto, StudySubject bo) {
        String isoStUserLastCreated = StConverter.convertToString(dto
                .getUserLastCreated());
        if (StringUtils.isNotEmpty(isoStUserLastCreated)) {
            try {
                bo.setUserLastCreated(AbstractStudyProtocolConverter
                        .getCsmUserUtil().getCSMUserById(Long.valueOf(isoStUserLastCreated)));
            } catch (Exception e) {
                LOG.error("Exception in setting userLastCreated for Study Subject: "
                        + dto.getIdentifier() + ", for userid: "
                        + isoStUserLastCreated, e);
            }
        }

        String isoStUserLastUpdated = StConverter.convertToString(dto
                .getUserLastUpdated());
        if (StringUtils.isNotEmpty(isoStUserLastUpdated)) {
            try {
                bo.setUserLastUpdated(AbstractStudyProtocolConverter
                        .getCsmUserUtil().getCSMUserById(Long.valueOf(isoStUserLastUpdated)));
            } catch (Exception e) {
                LOG.error("Exception in setting userLastUpdated for Study Subject: "
                        + dto.getIdentifier() + ", for userid: "
                        + isoStUserLastUpdated, e);
            }
        }
    }
    /**
     * Converts a StudySubject to the condensed study subject/patient dto.
     * @param bo the business object to convert
     * @return the condensed study subject/patient dto
     */
    public SubjectAccrualDTO convertFromDomainToSubjectDTO(StudySubject bo) {
        SubjectAccrualDTO dto = new SubjectAccrualDTO();
        dto.setIdentifier(IiConverter.convertToSubjectAccrualIi(bo.getId()));
        dto.setAssignedIdentifier(StConverter.convertToSt(bo.getAssignedIdentifier()));
        
        convertPatient(bo, dto);
        convertPerformedActivities(bo, dto);
        dto.setPaymentMethod(CdConverter.convertToCd(bo.getPaymentMethodCode()));
        dto.setParticipatingSiteIdentifier(
                IiConverter.convertToIi(bo.getStudySite() == null ? null : bo.getStudySite().getId()));
        Long diseaseId = bo.getDisease() == null ? null : bo.getDisease().getId();
        dto.setDiseaseIdentifier(IiConverter.convertToIi(diseaseId));
        dto.setSiteDiseaseIdentifier(IiConverter.convertToIi(
                bo.getSiteDisease() == null ? null : bo.getSiteDisease().getId()));
        dto.setRegistrationGroupId(StConverter.convertToSt(bo.getRegistrationGroupId()));
        dto.setSubmissionTypeCode(CdConverter.convertToCd(bo.getSubmissionTypeCode()));
        return dto;
    }

    private void convertPerformedActivities(StudySubject bo, SubjectAccrualDTO dto) {
        if (CollectionUtils.isNotEmpty(bo.getPerformedActivities())) {
            //1st performed activity is the one that contains the registration date
            PerformedSubjectMilestone registered = 
                (PerformedSubjectMilestone) bo.getPerformedActivities().iterator().next();
            dto.setRegistrationDate(TsConverter.convertToTs(registered.getRegistrationDate()));
        }
    }

    private void convertPatient(StudySubject bo, SubjectAccrualDTO dto) {
        if (bo.getPatient() != null) {
            dto.setBirthDate(TsConverter.convertToTs(bo.getPatient().getBirthDate()));
            dto.setGender(CdConverter.convertToCd(bo.getPatient().getSexCode()));
            dto.setRace(DSetEnumConverter.convertCsvToDSet(PatientRaceCode.class, bo.getPatient().getRaceCode()));
            dto.setEthnicity(CdConverter.convertToCd(bo.getPatient().getEthnicCode()));
            dto.setCountryCode(CdConverter.convertStringToCd(bo.getPatient().getCountry().getAlpha2()));
            dto.setZipCode(StConverter.convertToSt(bo.getPatient().getZip()));
        }
    }
}
