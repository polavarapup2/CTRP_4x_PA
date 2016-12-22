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
*/
package gov.nih.nci.accrual.accweb.action;

import static gov.nih.nci.accrual.service.batch.CdusBatchUploadReaderBean.ICD_O_3_CODESYSTEM;
import gov.nih.nci.accrual.accweb.dto.util.DiseaseWebDTO;
import gov.nih.nci.accrual.accweb.dto.util.PatientWebDto;
import gov.nih.nci.accrual.accweb.dto.util.SearchPatientsCriteriaWebDto;
import gov.nih.nci.accrual.accweb.dto.util.SearchStudySiteResultWebDto;
import gov.nih.nci.accrual.dto.PatientListDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.SearchSSPCriteriaDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Patient actions.
 * @author Hugh Reinhart
 */
@SuppressWarnings("PMD.TooManyMethods")
public class PatientAction extends AbstractListEditAccrualAction<PatientListDto> implements Preparable {
    private static final long serialVersionUID = -6820189447703204634L;
    private static List<Country> listOfCountries = null;
    private static Long unitedStatesId = null;
    private Long studyProtocolId;

    private SearchPatientsCriteriaWebDto criteria;
    private List<SearchStudySiteResultWebDto> listOfStudySites = null;
    private PatientWebDto patient;
    private final PatientHelper helper = new PatientHelper(this);
    private String deleteReason;
    private static List<String> reasonsList = new ArrayList<String>();
    private boolean showSite;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        super.prepare();
        try {
            if (getStudyProtocolId() != null) {
                setSpIi(IiConverter.convertToStudyProtocolIi(getStudyProtocolId()));
                loadTrialSummaryIntoSession();
            }
            String dCode = getAccrualDiseaseTerminologyService().getCodeSystem(IiConverter.convertToLong(getSpIi()));
            if (dCode == null || ICD_O_3_CODESYSTEM.equals(dCode)) {
                setShowSite(true);
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        if (isSessionTrialIndustrial()) {
            addActionError(getText("error.invalidaccess.industrial"));
            return "invalid";
        } 
        getListOfCountries();
        getListOfStudySites();
        return super.execute();
    }

    /**
     * @return success
     */
    public String getDeleteReasons() {
        if (reasonsList.isEmpty()) {
            String deleteReasons;
            try {
                deleteReasons = getLookupTableSvc().getPropertyValue("subject.delete.reasons");
                StringTokenizer st = new StringTokenizer(deleteReasons, ",");
                while (st.hasMoreTokens()) {
                    String reason = st.nextToken();
                    reasonsList.add(reason);
                }
            } catch (PAException e) {
                LOG.error("Error loading delete reasons.", e);
            }
        }
        return "deleteReason";
    }

    private boolean isSessionTrialIndustrial() {
        SearchTrialResultDto trialSummary = (SearchTrialResultDto) ServletActionContext.getRequest().getSession()
                .getAttribute("trialSummary");
        boolean result = false;
        if (StConverter.convertToString(trialSummary.getTrialType()).equals(AccrualUtil.INTERVENTIONAL)) {
            result = BlConverter.convertToBoolean(trialSummary.getIndustrial());
        } else {
            if (!ISOUtil.isStNull(trialSummary.getAccrualSubmissionLevel())
                    && trialSummary.getAccrualSubmissionLevel().getValue().equals(AccrualUtil.SUMMARY_LEVEL)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String create() {
        getListOfStudySites();
        patient = new PatientWebDto(getSpIi(), unitedStatesId);
        return super.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieve() {
        try {
            loadPatient(getSelectedRowIdentifier(), true);
            if (patient != null) {
                patient.setRaceCode(getParsedCode(patient.getRaceCode()));
                patient.setEthnicCode(getParsedCode(patient.getEthnicCode()));
            } else {
                addActionError("Error retrieving study subject info.");
                return execute();
            }
        } catch (Exception e) {
            patient = null;
            LOG.error("Error in PatientAction.retrieve().", e);
        }
        return super.retrieve();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String update() {
        try {
            loadPatient(getSelectedRowIdentifier(), false);
            if (patient == null) {
                addActionError("Error retrieving study subject info for update.");
                return execute();
            }
        } catch (Exception e) {
            patient = null;
            LOG.error("Error in PatientAction.update().", e);
        }
        return super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws PAException {
        try {
            getSubjectAccrualSvc().deleteSubjectAccrual(IiConverter.convertToIi(getSelectedRowIdentifier()), 
                    getDeleteReason());
            checkIfNonInterventionalTrialChanges();
        } catch (PAException e) {
            LOG.error("Error in PatientAction.delete().", e);
        }
        return super.delete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws PAException {
        helper.validate();
        getListOfStudySites();
        if (hasActionErrors()) {
            return super.create();
        }
        PatientDto pat = patient.getPatientDto();
        StudySubjectDto ssub = patient.getStudySubjectDto();
        ssub.setStudyProtocolIdentifier(getSpIi());
        ssub.setSubmissionTypeCode(CdConverter.convertToCd(AccrualSubmissionTypeCode.UI));
        PerformedSubjectMilestoneDto psm = patient
                .getPerformedStudySubjectMilestoneDto();
        try {
            StudySubject existingSs = getStudySubjectSvc().get(new SubjectAccrualKey(
                    ssub.getStudySiteIdentifier(), ssub.getAssignedIdentifier()));
            if (existingSs != null) {
                pat.setIdentifier(IiConverter.convertToIi(existingSs.getPatient().getId()));
                ssub.setIdentifier(IiConverter.convertToIi(existingSs.getId()));
                ssub.setPatientIdentifier(IiConverter.convertToIi(existingSs.getPatient().getId()));
                psm.setIdentifier(IiConverter.convertToIi(existingSs.getPerformedActivities().get(0).getId()));
                pat = getPatientSvc().update(pat);
                ssub = getStudySubjectSvc().update(ssub);
                setRegistrationDate(psm);
            } else {
                pat.setIdentifier(null);
                ssub.setIdentifier(null);
                psm.setIdentifier(null);
                pat = getPatientSvc().create(pat);
                ssub.setPatientIdentifier(pat.getIdentifier());
                ssub = getStudySubjectSvc().create(ssub);
                psm.setStudySubjectIdentifier(ssub.getIdentifier());
                setRegistrationDate(psm);
            }
            checkIfNonInterventionalTrialChanges();
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        }
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws PAException {
        helper.validate();
        getListOfStudySites();
        if (hasActionErrors()) {
            return super.update();
        }
        if (keyChanged()) {
            Ii ssubIi = IiConverter.convertToIi(patient.getStudySubjectId());
            getSubjectAccrualSvc().deleteSubjectAccrual(ssubIi, "Change ID or Site");
            return add();
        }
        PatientDto pat = patient.getPatientDto();
        StudySubjectDto ssub = patient.getStudySubjectDto();
        ssub.setSubmissionTypeCode(CdConverter.convertToCd(AccrualSubmissionTypeCode.UI));
        PerformedSubjectMilestoneDto psm = patient
                .getPerformedStudySubjectMilestoneDto();
        try {
            pat = getPatientSvc().update(pat);
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return super.update();
        }
        ssub = getStudySubjectSvc().update(ssub);
        setRegistrationDate(psm);
        return super.edit();
    }

    private boolean keyChanged() {
        try {
            StudySubject ssDb = getStudySubjectSvc().get(patient.getStudySubjectId());
            return !ssDb.getAssignedIdentifier().equals(patient.getAssignedIdentifier()) 
                    || !ssDb.getStudySite().getId().equals(patient.getStudySiteId());
        } catch (Exception e) {
            LOG.error("Exception in PatientAction.keyChanged()", e);
            return false;
        }
    }

    private void loadPatient(String id, boolean loadusername) throws PAException {
        if (id == null) {
            patient = null;
            return;
        }
        StudySubject ss = getStudySubjectSvc().get(Long.valueOf(id));
        ss.getPerformedActivities();
        patient = new PatientWebDto(ss);
        if (loadusername && ss.getUserLastCreated() != null) {
            RegistryUser regUser = PaServiceLocator.getInstance()
                    .getRegistryUserService().getUser(ss.getUserLastCreated().getLoginName());
            patient.setUserCreated(regUser != null ? regUser.getFirstName()
                    + " " + regUser.getLastName() : "");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
        try {
            SearchSSPCriteriaDto searchCriteria = new SearchSSPCriteriaDto();
            searchCriteria.setPatientBirthDate(getCriteria().getBirthDate());
            searchCriteria.setStudySubjectAssignedIdentifier(getCriteria().getAssignedIdentifier());
            searchCriteria.setStudySubjectStatusCode(FunctionalRoleStatusCode.ACTIVE);
            if (getCriteria().getStudySiteId() != null) {
                searchCriteria.getStudySiteIds().add(getCriteria().getStudySiteId());
            } else {
                for (SearchStudySiteResultWebDto ss : getListOfStudySites()) {
                    searchCriteria.getStudySiteIds().add(Long.valueOf(ss.getSsIi()));
                }
            }
            setDisplayTagList(getStudySubjectSvc().searchFast(searchCriteria));
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
    }

    /**
     * Method called from pop-up. Loads selected disease.
     *
     * @return result
     */
    public String getDisplayDisease() {
        DiseaseWebDTO webDTO = new DiseaseWebDTO();
        String siteLookUp = ServletActionContext.getRequest().getParameter("siteLookUp");
        webDTO.setDiseaseIdentifier(ServletActionContext.getRequest().getParameter("diseaseId"));
        if (StringUtils.isEmpty(webDTO.getDiseaseIdentifier())) {
            webDTO = new DiseaseWebDTO();
        } else {
            AccrualDisease bo = getDiseaseSvc().get(Long.valueOf(webDTO.getDiseaseIdentifier()));
            webDTO.setPreferredName(bo.getPreferredName());
            webDTO.setDiseaseIdentifier(bo.getId().toString());
        }
        setPatientDisease(webDTO, siteLookUp);
        if (!StringUtils.isEmpty(siteLookUp) && siteLookUp.equalsIgnoreCase("true")) {
            return "displaySiteDiseases";
        }
        return "displayDiseases";
    }

    void setPatientDisease(DiseaseWebDTO webDTO, String siteLookUp) {
        patient = new PatientWebDto();
        if (!StringUtils.isEmpty(siteLookUp) && siteLookUp.equalsIgnoreCase("true")) {
            patient.setSiteDiseasePreferredName(webDTO.getPreferredName());
            patient.setSiteDiseaseIdentifier(Long.valueOf(webDTO.getDiseaseIdentifier()));
        } else {
            patient.setDiseasePreferredName(webDTO.getPreferredName());
            patient.setDiseaseIdentifier(Long.valueOf(webDTO.getDiseaseIdentifier()));
        }
    }

    /**
     * @return the criteria
     */
    public SearchPatientsCriteriaWebDto getCriteria() {
        if (criteria == null) {
            setCriteria(new SearchPatientsCriteriaWebDto());
        }
        return criteria;
    }

    /**
     * @param criteria
     *            the criteria to set
     */
    public void setCriteria(SearchPatientsCriteriaWebDto criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the patient
     */
    public PatientWebDto getPatient() {
        return patient;
    }

    /**
     * @param patient
     *            the patient to set
     */
    public void setPatient(PatientWebDto patient) {
        this.patient = patient;
    }

    /**
     * @return the listOfCountries
     */
    public List<Country> getListOfCountries() {
        if (listOfCountries == null) {
            try {
                listOfCountries = getCountrySvc().getCountries();
                for (Country c : listOfCountries) {
                    if ("United States".equals(c.getName())) {
                        unitedStatesId = c.getId();
                    }
                }
            } catch (PAException e) {
                LOG.error("Error loading countries.", e);
            }
        }
        return listOfCountries;
    }

    /**
     * @return the listOfStudySites
     */
    public List<SearchStudySiteResultWebDto> getListOfStudySites() {
        if (listOfStudySites == null) {
            try {
                RegistryUser ru = PaServiceLocator.getInstance().getRegistryUserService().getUser(
                        CaseSensitiveUsernameHolder.getUser());
                Ii ruIi = IiConverter.convertToIi(ru.getId());
                List<SearchStudySiteResultDto> isoStudySiteList = getSearchStudySiteSvc().search(getSpIi(), ruIi);
                listOfStudySites = new ArrayList<SearchStudySiteResultWebDto>();
                for (SearchStudySiteResultDto iso : isoStudySiteList) {
                    listOfStudySites.add(new SearchStudySiteResultWebDto(iso));
                }
            } catch (PAException e) {
                LOG.error("Error loading study sites.", e);
            }
        }
        return listOfStudySites;
    }

    private void setRegistrationDate(PerformedSubjectMilestoneDto dto) {
        try {
            if (!ISOUtil.isIiNull(dto.getIdentifier())) {
                getPerformedActivitySvc().updatePerformedSubjectMilestone(dto);
            } else {
                getPerformedActivitySvc().createPerformedSubjectMilestone(dto);
            }
        } catch (Exception e) {
            LOG.error("Exception setting registration date.", e);
            addActionError(e.getLocalizedMessage());
        }
    }

    private Set<String> getParsedCode(Set<String> codes) {
        Set<String> newCodes = new HashSet<String>();
        for (String rc : codes) {
            newCodes.add(rc.replace('_', ' '));
        }
        return newCodes;
    }

    private String getParsedCode(String code) {
        return code.replace('_', ' ');
    }

    /**
     * @return the unitedStatesId
     */
    public Long getUnitedStatesId() {
        return PatientAction.unitedStatesId;
    }
    
    /**
     * @param unitedStatesId the unitedStatesId to set
     */
    public void setUnitedStatesId(Long unitedStatesId) {
        PatientAction.unitedStatesId = unitedStatesId;
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
     * @return the deleteReason
     */
    public String getDeleteReason() {
        return deleteReason;
    }

    /**
     * @param deleteReason the deleteReason to set
     */
    public void setDeleteReason(String deleteReason) {
         this.deleteReason = deleteReason;
    }

    /**
     * @return reasonsList
     */
    public static List<String> getReasonsList() {
        return reasonsList;
    }

    /**
     * @return showSite
     */
    public boolean isShowSite() {
        return showSite;
    }

    /**
     * @param showSite the showSite to set
     */
    public void setShowSite(boolean showSite) {
        this.showSite = showSite;
    }
}
