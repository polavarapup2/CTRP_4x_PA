/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.accrual.accweb.dto.util;
import static gov.nih.nci.accrual.service.batch.CdusBatchUploadReaderBean.ICD_O_3_CODESYSTEM;
import gov.nih.nci.accrual.accweb.action.AbstractAccrualAction;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;


/**
 * @author Hugh Reinhart
 * @since Sep 22, 2009
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.ExcessiveParameterList", "PMD.ExcessiveClassLength",
    "PMD.CyclomaticComplexity", "PMD.AvoidDeeplyNestedIfStmts" })
public class PatientWebDto implements Serializable {

    private static final long serialVersionUID = 5897506784500068758L;
    // from PatientDto
    private Long patientId;
    private Set<String> raceCode = new HashSet<String>();
    private String genderCode;
    private String ethnicCode;
    private String birthDate;
    private String zip;
    private Long countryIdentifier;
    private String countryName;

    // from StudySubjectDto
    private Long studySubjectId;
    private Long studyProtocolId;
    private Long studySiteId;
    private String identifier;
    private String paymentMethodCode;
    private String assignedIdentifier;
    private String statusCode;
    private String dateLastUpdated;
    private String userCreated;
    private String registrationGroupId;
    private String submissionTypeCode;

    // from PerformedSubjectMilestone
    private Long performedSubjectMilestoneId;
    private String registrationDate;

    // from StudySite...Organization
    private String organizationName;

    // disease    
    private String diseasePreferredName;
    private Long diseaseIdentifier;
    private String siteDiseasePreferredName;
    private Long siteDiseaseIdentifier;

    /**
     * Perform basic validations.
     * @param dto dto
     * @param action action to add for errors
     */
    public static void validate(PatientWebDto dto, AbstractAccrualAction action) {
        Long spId = IiConverter.convertToLong(action.getSpIi());
        boolean checkDisease = action.getDiseaseSvc().diseaseCodeMandatory(spId);
        action.clearActionErrors();
        action.addActionErrorIfEmpty(dto, "Error inputing study subject data.");
        if (!action.hasActionErrors()) {
            action.addActionErrorIfEmpty(dto.getAssignedIdentifier(), "Study Subject ID is required.");
            action.addActionErrorIfEmpty(dto.getBirthDate(), "Birth date is required.");
            action.addActionErrorIfEmpty(dto.getGenderCode(), "Gender is required.");
            action.addActionErrorIfEmpty(dto.getRaceCode(), "Race is required.");
            action.addActionErrorIfEmpty(dto.getEthnicCode(), "Ethnicity is required.");
            action.addActionErrorIfEmpty(dto.getCountryIdentifier(), "Country is required.");
            if (checkDisease) {
                String dCode = action.getAccrualDiseaseTerminologyService().getCodeSystem(spId);
                if (dCode.equals(ICD_O_3_CODESYSTEM) && dto.getSiteDiseaseIdentifier() == null) {
                    action.addActionErrorIfEmpty(dto.getDiseaseIdentifier(), "Disease is required.");
                } else if (!dCode.equals(ICD_O_3_CODESYSTEM)) {
                    action.addActionErrorIfEmpty(dto.getDiseaseIdentifier(), "Disease is required.");
                }
            }
            action.addActionErrorIfEmpty(dto.getStudySiteId(), "Participating Site is required.");
            action.addActionErrorIfEmpty(dto.getRegistrationDate(), "Registration Date is required.");
        }
        if (!action.hasActionErrors() && StringUtils.isNotEmpty(dto.getRegistrationDate())) {
            Timestamp registration = AccrualUtil.yearMonthStringToTimestamp(dto.getRegistrationDate());
            Timestamp birth = AccrualUtil.yearMonthStringToTimestamp(dto.getBirthDate());
            if (birth != null && birth.after(registration)) {
                action.addActionError("The birth date must be less then or equal to the registration date.");
            }
        }
    }

    /**
     * Default constructor.
     */
    public PatientWebDto() {
        // default constructor
    }

    /**
     * Constructor for new records status is always active.
     * @param studyProtocolIi study protocol id
     * @param unitedStatesId country id of the United States
     */
    public PatientWebDto(Ii studyProtocolIi, Long unitedStatesId) {
        studyProtocolId = IiConverter.convertToLong(studyProtocolIi);
        countryIdentifier = unitedStatesId;
        statusCode = FunctionalRoleStatusCode.ACTIVE.getCode();
    }

    /**
     * Construct from StudySubject.
     * @param ss StudySubject object
     */
    public PatientWebDto(StudySubject ss) {
        loadPatientData(ss.getPatient());

        studySubjectId = ss.getId();
        studyProtocolId = ss.getStudyProtocol().getId();
        studySiteId = ss.getStudySite().getId();
        identifier = ss.getId().toString();
        paymentMethodCode = AccrualUtil.getCode(ss.getPaymentMethodCode());
        assignedIdentifier = ss.getAssignedIdentifier();
        statusCode = ss.getStatusCode().getCode();
        registrationGroupId = ss.getRegistrationGroupId();
        submissionTypeCode = AccrualUtil.getCode(ss.getSubmissionTypeCode());

        if (ss.getStudySite().getHealthCareFacility() != null) {
            organizationName = ss.getStudySite().getHealthCareFacility().getOrganization().getName();
        }

        loadPerformedActivitiesData(ss.getPerformedActivities());
        if (ss.getDisease() != null) {
            diseasePreferredName = ss.getDisease().getPreferredName();
            diseaseIdentifier = ss.getDisease().getId();
        }
        if (ss.getSiteDisease() != null) {
            siteDiseasePreferredName = ss.getSiteDisease().getPreferredName();
            siteDiseaseIdentifier = ss.getSiteDisease().getId();
        }
        dateLastUpdated = DateFormatUtils.format(ss.getDateLastUpdated() == null 
                ? ss.getDateLastCreated() : ss.getDateLastUpdated(), "MM/dd/yyyy HH:mm");
    }
    
    private void loadPatientData(Patient p) {
        patientId = p.getId();
        loadRaces(p.getRaceCode());
        genderCode = AccrualUtil.getCode(p.getSexCode());
        ethnicCode = AccrualUtil.getCode(p.getEthnicCode());
        birthDate = AccrualUtil.timestampToYearMonthString(p.getBirthDate());
        countryIdentifier = p.getCountry().getId();
        countryName = p.getCountry().getName();
        zip = p.getZip();
    }
    
    private void loadRaces(String races) {
        raceCode = new HashSet<String>();
        if (StringUtils.isNotBlank(races)) {
            String[] tokens = races.split("[,]");
            for (String token : tokens) {
                if (StringUtils.isBlank(token)) {
                    continue;
                }
                String txt = token.trim();
                raceCode.add(PatientRaceCode.valueOf(txt).getCode());
            }
        }
    }

    private void loadPerformedActivitiesData(List<PerformedActivity> performedActivities) {
        if (performedActivities != null) {
            for (PerformedActivity pa : performedActivities) {
                if (pa instanceof PerformedSubjectMilestone) {
                    PerformedSubjectMilestone psm = (PerformedSubjectMilestone) pa;
                    performedSubjectMilestoneId = psm.getId();
                    registrationDate = psm.getRegistrationDate() == null ? null 
                            : PAUtil.normalizeDateString(psm.getRegistrationDate().toString());
                }
            }
        }
    }

    /**
     * @return patient iso dto
     */
    public PatientDto getPatientDto() {
        PatientDto pat = new PatientDto();
        pat.setIdentifier(IiConverter.convertToIi(getPatientId()));
        pat.setBirthDate(AccrualUtil.yearMonthStringToTs(getBirthDate()));
        pat.setCountryIdentifier(IiConverter.convertToCountryIi(getCountryIdentifier()));
        pat.setEthnicCode(CdConverter.convertToCd(PatientEthnicityCode.getByCode(getEthnicCode())));
        pat.setGenderCode(CdConverter.convertToCd(PatientGenderCode.getByCode(getGenderCode())));
        pat.setRaceCode(DSetEnumConverter.convertSetToDSet(getRaceCode()));
        pat.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.ACTIVE));
        pat.setZip(StConverter.convertToSt(getZip()));
        return pat;
    }

    /**
     * @return study subject iso dto
     */
    public StudySubjectDto getStudySubjectDto() {
        StudySubjectDto ssub = new StudySubjectDto();
        ssub.setIdentifier(IiConverter.convertToIi(getStudySubjectId()));
        ssub.setStudySiteIdentifier(IiConverter.convertToStudySiteIi(Long.valueOf(getStudySiteId())));
        ssub.setStudyProtocolIdentifier(IiConverter.convertToIi(getStudyProtocolId()));
        ssub.setPatientIdentifier(IiConverter.convertToIi(getPatientId()));
        ssub.setAssignedIdentifier(StConverter.convertToSt(getAssignedIdentifier()));
        ssub.setDiseaseIdentifier(IiConverter.convertToIi(getDiseaseIdentifier()));
        ssub.setSiteDiseaseIdentifier(IiConverter.convertToIi(getSiteDiseaseIdentifier()));
        ssub.setPaymentMethodCode(CdConverter.convertToCd(PaymentMethodCode.getByCode(getPaymentMethodCode())));
        ssub.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.getByCode(getStatusCode())));
        ssub.setRegistrationGroupId(StConverter.convertToSt(getRegistrationGroupId()));
        ssub.setSubmissionTypeCode(CdConverter.convertToCd(
                AccrualSubmissionTypeCode.getByCode(getSubmissionTypeCode())));
        return ssub;
    }

    /**
     * @return performed subject milestone dto
     */
    public PerformedSubjectMilestoneDto getPerformedStudySubjectMilestoneDto() {
        PerformedSubjectMilestoneDto psm = new PerformedSubjectMilestoneDto();
        psm.setIdentifier(IiConverter.convertToIi(getPerformedSubjectMilestoneId()));
        psm.setStudySubjectIdentifier(IiConverter.convertToIi(getStudySubjectId()));
        psm.setStudyProtocolIdentifier(IiConverter.convertToIi(getStudyProtocolId()));
        psm.setRegistrationDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(getRegistrationDate())));
        return psm;
    }
    /**
     * @return the raceCode
     */
    public Set<String> getRaceCode() {
        return raceCode;
    }
    /**
     * @param raceCode the raceCode to set
     */
    public void setRaceCode(Set<String> raceCode) {
        this.raceCode = raceCode;
    }
    /**
     * @return the genderCode
     */
    public String getGenderCode() {
        return genderCode;
    }
    /**
     * @param genderCode the genderCode to set
     */
    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }
    /**
     * @return the ethnicCode
     */
    public String getEthnicCode() {
        return ethnicCode;
    }
    /**
     * @param ethnicCode the ethnicCode to set
     */
    public void setEthnicCode(String ethnicCode) {
        this.ethnicCode = ethnicCode;
    }
    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }
    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = AccrualUtil.normalizeYearMonthString(birthDate);
    }
    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the dateLastUpdated
     */
    public String getDateLastUpdated() {
        return dateLastUpdated;
    }
    /**
     * @param dateLastUpdated the dateLastUpdated to set
     */
    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
    /**
     * @return the userCreated
     */
    public String getUserCreated() {
        return userCreated;
    }
    /**
     * @param userCreated the userCreated to set
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }
    /**
     * @return the paymentMethodCode
     */
    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }
    /**
     * @param paymentMethodCode the paymentMethodCode to set
     */
    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }
    /**
     * @return the assignedIdentifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = StringUtils.upperCase(StringUtils
                .trim(assignedIdentifier));
    }
    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }
   /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    /**
     * @return the identifier
     */
    public String getIdentifier() {
       return identifier;
     }
    /**
     * @param identifier the identifier to set
     */
     public void setIdentifier(String identifier) {
       this.identifier = identifier;
     }
    /**
     * @return the registrationDate
     */
    public String getRegistrationDate() {
        return registrationDate;
    }
    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = PAUtil.normalizeDateString(registrationDate);
    }
    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }
    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    /**
     * @return the countryIdentifier
     */
    public Long getCountryIdentifier() {
        return countryIdentifier;
    }
    /**
     * @param countryIdentifier the countryIdentifier to set
     */
    public void setCountryIdentifier(Long countryIdentifier) {
        this.countryIdentifier = countryIdentifier;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the patientId
     */
    public Long getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    /**
     * @return the studySubjectId
     */
    public Long getStudySubjectId() {
        return studySubjectId;
    }

    /**
     * @param studySubjectId the studySubjectId to set
     */
    public void setStudySubjectId(Long studySubjectId) {
        this.studySubjectId = studySubjectId;
    }

    /**
     * @return the performedSubjectMilestoneId
     */
    public Long getPerformedSubjectMilestoneId() {
        return performedSubjectMilestoneId;
    }

    /**
     * @param performedSubjectMilestoneId the performedSubjectMilestoneId to set
     */
    public void setPerformedSubjectMilestoneId(Long performedSubjectMilestoneId) {
        this.performedSubjectMilestoneId = performedSubjectMilestoneId;
    }

    /**
     * @return the studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return the studySiteId
     */
    public Long getStudySiteId() {
        return studySiteId;
    }

    /**
     * @param studySiteId the studySiteId to set
     */
    public void setStudySiteId(Long studySiteId) {
        this.studySiteId = studySiteId;
    }

    /**
     * @return the disease Preferred Name
     */
    public String getDiseasePreferredName() {
        return diseasePreferredName;
    } 
    
    /**
     * @param diseasePreferredName the diseasePreferredName to set
     */
    public void setDiseasePreferredName(String diseasePreferredName) {
        this.diseasePreferredName = diseasePreferredName;
    } 

    /**
     * @return the diseaseIdentifier
     */
    public Long getDiseaseIdentifier() {
        return diseaseIdentifier;
    }

    /**
     * @param diseaseIdentifier the diseaseIdentifier to set
     */
    public void setDiseaseIdentifier(Long diseaseIdentifier) {
        this.diseaseIdentifier = diseaseIdentifier;
    }

    /**
     * @return the registrationGroupId
     */
    public String getRegistrationGroupId() {
        return registrationGroupId;
    }

    /**
     * @param registrationGroupId the registrationGroupId to set
     */
    public void setRegistrationGroupId(String registrationGroupId) {
        this.registrationGroupId = registrationGroupId;
    }

    /**
     * @return the submissionTypeCode
     */
    public String getSubmissionTypeCode() {
        return submissionTypeCode;
    }

    /**
     * @param submissionTypeCode the submissionTypeCode to set
     */
    public void setSubmissionTypeCode(String submissionTypeCode) {
        this.submissionTypeCode = submissionTypeCode;
    }

    /**
     * @return the sitedisease Preferred Name
     */
    public String getSiteDiseasePreferredName() {
        return siteDiseasePreferredName;
    } 
    
    /**
     * @param siteDiseasePreferredName the siteDiseasePreferredName to set
     */
    public void setSiteDiseasePreferredName(String siteDiseasePreferredName) {
        this.siteDiseasePreferredName = siteDiseasePreferredName;
    } 

    /**
     * @return the diseaseIdentifier
     */
    public Long getSiteDiseaseIdentifier() {
        return siteDiseaseIdentifier;
    }

    /**
     * @param siteDiseaseIdentifier the siteDiseaseIdentifier to set
     */
    public void setSiteDiseaseIdentifier(Long siteDiseaseIdentifier) {
        this.siteDiseaseIdentifier = siteDiseaseIdentifier;
    }    
}
