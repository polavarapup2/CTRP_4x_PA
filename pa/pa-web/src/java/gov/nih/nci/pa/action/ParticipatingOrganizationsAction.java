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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.StudySubjectWebDto;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.StudySubjectServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.PAHealthCareProviderLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PhoneUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the participating organizations.
 *
 * @author Hugh Reinhart, Harsha
 * @since 08/20/2008
 */
public class ParticipatingOrganizationsAction 
    extends AbstractMultiObjectDeleteAction implements Preparable { // NOPMD  
    private static final long serialVersionUID = 123412653L;
    private static final Logger LOG = Logger.getLogger(ParticipatingOrganizationsAction.class);
    
    private static final String PRIMARY_CONTACT_DISPLAY_FMT = "[%s - %s]";
    private static final String INVESTIGATOR_DISPLAY_FMT = "[%s - %s, %s]%s";
    private static final String REC_STATUS_DATE = "recStatusDate";
    private static final String EDIT_ORG_NAME = "editOrg.name";
    private static final String ACT_FACILITY_SAVE = "facilitySave";
    private static final String ACT_EDIT = "edit";
    private static final String ACT_DELETE = "delete";
    private static final String DISPLAY_SP_CONTACTS = "display_StudyPartipants_Contacts";
    private static final String DISPLAY_PRIM_CONTACTS = "display_primContacts";
    private static final String DISPLAY_SPART_CONTACTS = "display_spContacts";
    private static final String DISPLAYJSP = "displayJsp";
    private static final String DISPLAY_STUDY_PART_CONTACTS = "display_StudyPartipants";
    private static final String REFRESH_PRIMARY_CONTACT = "refreshPrimaryContact";
    private static final String ERROR_PRIMARY_CONTACTS = "error_prim_contacts";
    
    private CorrelationUtilsRemote correlationUtils = new CorrelationUtils();
    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    
    private OrganizationalContactCorrelationServiceRemote organizationalContactCorrelationService;
    private OrganizationEntityServiceRemote organizationEntityService;
    private PAHealthCareProviderLocal paHealthCareProviderService;
    private ParticipatingOrgServiceLocal participatingOrgService;
    private ParticipatingSiteServiceLocal participatingSiteService;
    private PersonEntityServiceRemote personEntityService;
    private StudyProtocolServiceLocal studyProtocolService;
    private StudySiteServiceLocal studySiteService;
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    private StudySiteContactServiceLocal studySiteContactService;
    private StudySubjectServiceLocal studySubjectService;
    private StatusTransitionService statusTransitionService;
    
    private Ii spIi;
    private StudyProtocolQueryDTO spDTO;
    private List<PaOrganizationDTO> organizationList;
    private OrganizationDTO selectedOrgDTO;
    private Organization editOrg;
    private Long cbValue;
    private String recStatus;
    private String recStatusDate;
    private String recStatusComments;
    private PersonDTO selectedPersTO = null;
    private List<StudySiteContactDTO> spContactDTO;
    private List<PaPersonDTO> personWebDTOList;
    private boolean newParticipation = false;
    private PaPersonDTO personContactWebDTO;
    private String organizationName;
    private PaOrganizationDTO orgFromPO = new PaOrganizationDTO();
    private String currentAction = "create";
    private String targetAccrualNumber;
    private String proprietaryTrialIndicator = "false";
    private String siteLocalTrialIdentifier;
    private String siteProgramCodeText;
    private String dateOpenedForAccrual;
    private String dateClosedForAccrual;
    private Long studySiteIdentifier;
    private String programCode;
    private String statusCode;
    private String statusTransitionWarnings;
    private String statusTransitionErrors;
    private List<StudyOverallStatusWebDTO> siteStatusList;
    private List<StudySubjectWebDto> subjects;
    
    //tracks status transition validity
    private boolean isValidTransition;

    // Cache for org list
    private static CacheManager cacheManager;
    private static final String CACHE_KEY = "PARTICIPATING_ORG_CACHE_KEY";
    private static final int CACHE_MAX_ELEMENTS = 10;
    private static final long CACHE_TIME = 120;

    private Cache getPartSiteCache() {
        if (cacheManager == null || cacheManager.getStatus() != Status.STATUS_ALIVE) {
            cacheManager = CacheManager.create();
            Cache cache = new Cache(CACHE_KEY, CACHE_MAX_ELEMENTS, null, false, null, false,
                    CACHE_TIME, CACHE_TIME, false, CACHE_TIME, null, null, 0);
            cacheManager.removeCache(CACHE_KEY);
            cacheManager.addCache(cache);
        }
        return cacheManager.getCache(CACHE_KEY);
    }

    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws PAException on error
     */
    @Override
    public void prepare() throws PAException {
        organizationalContactCorrelationService = PoRegistry.getOrganizationalContactCorrelationService();
        organizationEntityService = PoRegistry.getOrganizationEntityService();
        paHealthCareProviderService = PaRegistry.getPAHealthCareProviderService();
        participatingOrgService = PaRegistry.getParticipatingOrgService();
        participatingSiteService = PaRegistry.getParticipatingSiteService();
        personEntityService = PoRegistry.getPersonEntityService();
        studyProtocolService =  PaRegistry.getStudyProtocolService();
        studySiteService = PaRegistry.getStudySiteService();
        studySiteAccrualStatusService = PaRegistry.getStudySiteAccrualStatusService();
        studySiteContactService = PaRegistry.getStudySiteContactService();
        studySubjectService = PaRegistry.getStudySubjectService();
        statusTransitionService = PaRegistry.getStatusTransitionService();
        spDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
        spIi = IiConverter.convertToStudyProtocolIi(spDTO.getStudyProtocolId());
        setProprietaryTrialIndicator(spDTO.isProprietaryTrial() ? "true" : "false");
    }

    /**
     * @return Action result.
     *
     */
    @Override
    public String execute() {
        String retString = SUCCESS;
        try {
            loadForm();
        } catch (PAException e) {
            LOG.error(e);
            addActionError(e.getMessage());
        }
        return retString;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String create() throws PAException {
        getPartSiteCache().remove(spIi);
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        loadForm();
        setNewParticipation(true);
        return Action.SUCCESS;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String facilitySave() throws PAException {
        facilitySaveOrUpdate();
        if (hasFieldErrors() || !isValidTransition) {
            return ERROR;
        }
        if (hasActionErrors()) {
            consolidateAndClearActionErrors();
            return ERROR;
        } else {
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        }
        return ACT_FACILITY_SAVE;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String facilityUpdate() throws PAException {
        setRecStatus(ServletActionContext.getRequest().getParameter("recStatus"));
        setRecStatusDate(ServletActionContext.getRequest().getParameter(REC_STATUS_DATE));
        setRecStatusComments(ServletActionContext.getRequest().getParameter("recStatusComments"));
        setTargetAccrualNumber(ServletActionContext.getRequest().getParameter("targetAccrualNumber"));
        setProgramCode(ServletActionContext.getRequest().getParameter("programCode"));
        setDateOpenedForAccrual(ServletActionContext.getRequest().getParameter("dateOpenedForAccrual"));
        setDateClosedForAccrual(ServletActionContext.getRequest().getParameter("dateClosedForAccrual"));
        setSiteLocalTrialIdentifier(ServletActionContext.getRequest().getParameter("localProtocolIdenfier"));
        
        facilitySaveOrUpdate();
        if (hasFieldErrors() || !isValidTransition) {
            return "error_edit";
        }
        if (hasActionErrors()) {
            consolidateAndClearActionErrors();
            return "error_edit";
        } else {
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        }
        return ACT_EDIT;
    }

    /**
     * Consolidates all action error messages into a single string and sets it as failure message
     */
    private void consolidateAndClearActionErrors() {
        StringBuffer sb = new StringBuffer();
        for (String actionErr : getActionErrors()) {
            sb.append("\n").append(actionErr);
        }
        ServletActionContext.getRequest().setAttribute(
                Constants.FAILURE_MESSAGE, sb.toString().replaceAll("\\.\\s?", "\n"));
        clearActionErrors();
    }

    /**
     * @throws PAException exception
     */
    @SuppressWarnings("deprecation")
    public void facilitySaveOrUpdate() throws PAException {
        //reset flag
        isValidTransition = true;
        setStatusTransitionErrors("");
        setStatusTransitionWarnings("");
        getPartSiteCache().remove(spIi);
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab != null) {
            Organization org = tab.getFacilityOrganization();
            orgFromPO.setCity(org.getCity());
            orgFromPO.setCountry(org.getCountryName());
            orgFromPO.setName(org.getName());
            orgFromPO.setZip(org.getPostalCode());
        }
        enforceBusinessRules();
        if ("true".equalsIgnoreCase(getProprietaryTrialIndicator())) {
            enforceBusinessRulesForProprietary();
        }
        if (hasFieldErrors()) {
            return;
        }
        StudySiteDTO sp = null;
        String errorOrgName = EDIT_ORG_NAME;
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(recStatus)));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToDate(recStatusDate)));
        ssas.setComments(StConverter.convertToSt(recStatusComments));
        Ii ssIi = null;
        if (tab.getStudyParticipationId() != null) {
            sp = saveNonPropWithCurrentSite(ssas, tab, errorOrgName);
            ssIi = sp.getIdentifier();
        } else {
            sp = new StudySiteDTO();
            ssIi = saveNonPropWithNewSite(sp, ssas, tab, errorOrgName);
        }
        if (hasFieldErrors() || !isValidTransition) {
            return;
        }
        tab.setStudyParticipationId(IiConverter.convertToLong(ssIi));
        setCbValue(tab.getStudyParticipationId());
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        if (StringUtils.isNotEmpty(tab.getFacilityOrganization().getName())) {
            setOrganizationName("for " + tab.getFacilityOrganization().getName());
        }
        setStatusCode(sp.getStatusCode().getCode());
        setCurrentAction("edit");
    }

    @SuppressWarnings("deprecation")
    private StudySiteDTO saveNonPropWithCurrentSite(StudySiteAccrualStatusDTO ssas,
            ParticipatingOrganizationsTabWebDTO tab, String errorOrgName) throws PAException {
        Ii studySiteIi = IiConverter.convertToIi(tab.getStudyParticipationId());
        StudySiteDTO studySite = studySiteService.get(studySiteIi);
        StudySiteAccrualStatusDTO studySiteAccrualStatus =
            studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(studySite.getIdentifier());
        boolean spUpdated = isSiteUpdated(studySite, studySiteAccrualStatus);
        if (isRecruitmentStatusUpdated(studySiteAccrualStatus)) {
            List<StatusDto> statusDtos = null;
            try {
                statusDtos = statusTransitionService.validateStatusTransition(
                        AppName.PA, 
                        spDTO.isProprietaryTrial()? TrialType.ABBREVIATED : TrialType.COMPLETE,
                        TransitionFor.SITE_STATUS, 
                        CdConverter.convertCdToEnum(RecruitmentStatusCode.class, 
                                studySiteAccrualStatus.getStatusCode()).name(), 
                        studySiteAccrualStatus.getStatusDate().getValue(), 
                        CdConverter.convertCdToEnum(RecruitmentStatusCode.class, ssas.getStatusCode()).name(),
                        TsConverter.convertToTimestamp(ssas.getStatusDate()));
                StatusDto dto = statusDtos.get(0);
                isValidTransition = checkStatusTransitionResults(dto);
            } catch (PAException e) {
                addActionError(e.getMessage());
                isValidTransition = false;
            }
        }
        if (spUpdated && isValidTransition) {
            try {
                    participatingSiteService.updateStudySiteParticipant(studySite, ssas);
            } catch (PADuplicateException e) {
                addFieldError(errorOrgName, e.getMessage());
            }
        }
        return studySite;
    }
    
    private boolean checkStatusTransitionResults(StatusDto dto) {
        boolean isValid = true;
        if (dto.hasErrorOfType(ErrorType.ERROR)) {
             setStatusTransitionErrors(dto.getConsolidatedErrorMessage().replaceAll("\\.\\s?", "\n"));
             isValid = false;
         } 
         if (dto.hasErrorOfType(ErrorType.WARNING)) {
             setStatusTransitionWarnings(dto.getConsolidatedWarningMessage().replaceAll("\\.\\s?", "\n"));
         } 
         return isValid;
    }

    private boolean isSiteUpdated(StudySiteDTO studySite, StudySiteAccrualStatusDTO ssas) {
        String prgCode = getProgramCode();
        Integer iTargetAccrual = (targetAccrualNumber == null) ? null : Integer.parseInt(targetAccrualNumber);
        String localId = getSiteLocalTrialIdentifier();
        final boolean programCodeOrTargetAccrualNumberUpdated = isProgramCodeOrTargetAccrualNumberUpdated(
                studySite, prgCode, iTargetAccrual);
        final boolean localStudyProtocolIdentifierUpdated = isLocalStudyProtocolIdentifierUpdated(
                studySite, localId);
        final boolean accrualDatesUpdated = isAccrualDatesUpdated(studySite);
        return programCodeOrTargetAccrualNumberUpdated
                || localStudyProtocolIdentifierUpdated
                || isRecruitmentStatusUpdated(ssas) || accrualDatesUpdated;
    }


    private boolean isAccrualDatesUpdated(StudySiteDTO studySite) {
        boolean updated = false;
        if (StringUtils.isNotEmpty(dateOpenedForAccrual)
                && StringUtils.isNotEmpty(dateClosedForAccrual)) {
            studySite.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(dateOpenedForAccrual, dateClosedForAccrual));
            updated = true;
        }
        if (StringUtils.isNotEmpty(dateOpenedForAccrual)
                && StringUtils.isEmpty(dateClosedForAccrual)) {
            studySite.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(dateOpenedForAccrual, null));
            updated = true;
        }
        
        if (StringUtils.isEmpty(dateOpenedForAccrual)
                && StringUtils.isEmpty(dateClosedForAccrual)
                && studySite.getAccrualDateRange() != null) {
            studySite.setAccrualDateRange(null);
            updated = true;
        }
        return updated;
    }

    /**
     * This method checks if either program code or target accrual have changed. If yes, update
     * the StudySiteDTO as appropriate and return true. Made package visibility for testing.
     *
     * @param studySite the study site object
     * @param prgCode program code
     * @param iTargetAccrual target accrual
     * @return if either has been updated
     */
    boolean isProgramCodeOrTargetAccrualNumberUpdated(StudySiteDTO studySite, String prgCode, Integer iTargetAccrual) {
        boolean spUpdated = false;
        Integer oldTargetAccrualNumber = IntConverter.convertToInteger(studySite.getTargetAccrualNumber());
        if (!ObjectUtils.equals(oldTargetAccrualNumber, iTargetAccrual)) {
            studySite.setTargetAccrualNumber(IntConverter.convertToInt(iTargetAccrual));
            spUpdated = true;
        }
        String oldProgramCode = StConverter.convertToString(studySite.getProgramCodeText());
        if (!ObjectUtils.equals(oldProgramCode, prgCode)) {
            studySite.setProgramCodeText(StConverter.convertToSt(prgCode));
            spUpdated = true;
        }

        return spUpdated;
    }

    private boolean isLocalStudyProtocolIdentifierUpdated(StudySiteDTO sp, String localId) {
        boolean spUpdated = false;
        if (ISOUtil.isStNull(sp.getLocalStudyProtocolIdentifier()) || (!ISOUtil.isStNull(
                sp.getLocalStudyProtocolIdentifier()) && !StConverter.convertToString(
                sp.getLocalStudyProtocolIdentifier()).equalsIgnoreCase(localId))) {
            sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt(getSiteLocalTrialIdentifier()));
            spUpdated = true;
        }
        return spUpdated;
    }

    private boolean isRecruitmentStatusUpdated(StudySiteAccrualStatusDTO ssas) {
        String statusDate = TsConverter.convertToString(ssas.getStatusDate());
        return !StringUtils.equalsIgnoreCase(ssas.getStatusCode().getCode(), recStatus)
                || !StringUtils.equalsIgnoreCase(statusDate, recStatusDate);
    }

    private Ii saveNonPropWithNewSite(StudySiteDTO ss, StudySiteAccrualStatusDTO ssas,
            ParticipatingOrganizationsTabWebDTO tab, String errorOrgName) throws PAException {
        
        List<StatusDto> statusDtos = null;
        try {
            statusDtos = statusTransitionService.validateStatusTransition(
                    AppName.PA, 
                    spDTO.isProprietaryTrial()? TrialType.ABBREVIATED : TrialType.COMPLETE,
                    TransitionFor.SITE_STATUS, null, null, 
                    CdConverter.convertCdToEnum(RecruitmentStatusCode.class, 
                            ssas.getStatusCode()).name(),
                    TsConverter.convertToTimestamp(ssas.getStatusDate()));
            StatusDto dto = statusDtos.get(0);
            isValidTransition = checkStatusTransitionResults(dto);
        } catch (PAException e) {
            addActionError(e.getMessage());
            isValidTransition = false;
        }
        if (!isValidTransition) {
            return null;
        }
        
        String poOrgId = tab.getFacilityOrganization().getIdentifier();
        Ii poHcfIi = paServiceUtil.getPoHcfIi(poOrgId);

        ss.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        ss.setIdentifier(null);
        ss.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        ss.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        ss.setStudyProtocolIdentifier(spIi);
        ss.setTargetAccrualNumber(IntConverter.convertToInt(getTargetAccrualNumber()));
        ss.setProgramCodeText(StConverter.convertToSt(getProgramCode()));
        ss.setLocalStudyProtocolIdentifier(StConverter.convertToSt(siteLocalTrialIdentifier));
        isAccrualDatesUpdated(ss);

        try {
            return participatingSiteService.createStudySiteParticipant(ss, ssas, poHcfIi).getIdentifier();
        } catch (DuplicateParticipatingSiteException e) {
            addFieldError(errorOrgName, e.getMessage());
            return null;
        }
    }

    private void handleSetMethodForEdit(StudySiteDTO spDto, StudySiteAccrualStatusDTO status) {
        if (status != null) {
            setRecStatus(status.getStatusCode().getCode());
            setRecStatusDate(TsConverter.convertToTimestamp(status.getStatusDate()).toString());
            if (!ISOUtil.isStNull(status.getComments())) {
                setRecStatusComments(status.getComments().getValue());
            }
        }
        if (IntConverter.convertToInteger(spDto.getTargetAccrualNumber()) == null) {
            setTargetAccrualNumber(null);
        } else {
            setTargetAccrualNumber(IntConverter.convertToInteger(spDto.getTargetAccrualNumber()).toString());
        }
        if (StConverter.convertToString(spDto.getProgramCodeText()) == null) {
            setProgramCode(null);
        } else {
            setProgramCode(StConverter.convertToString(spDto.getProgramCodeText()));
        }
        if (!ISOUtil.isStNull(spDto.getLocalStudyProtocolIdentifier())) {
            setSiteLocalTrialIdentifier(StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier()));
        } else {
            setSiteLocalTrialIdentifier(null);
        }
        setDateOpenedForAccrual(IvlConverter.convertTs().convertLowToString(spDto.getAccrualDateRange()));
        setDateClosedForAccrual(IvlConverter.convertTs().convertHighToString(spDto.getAccrualDateRange()));
        setStatusCode(spDto.getStatusCode().getCode());
        setNewParticipation(false);
    }

    /**
     * @return result
     * @throws PAException  exception
     */
    @SuppressWarnings("deprecation")
    public String edit() throws PAException {
        getPartSiteCache().remove(spIi);
        setCurrentAction("edit");
        StudySiteDTO spDto = studySiteService.get(IiConverter.convertToIi(cbValue));
        editOrg = correlationUtils.getPAOrganizationByIi(spDto.getHealthcareFacilityIi());
        orgFromPO.setCity(editOrg.getCity());
        orgFromPO.setCountry(editOrg.getCountryName());
        orgFromPO.setName(editOrg.getName());
        orgFromPO.setState(editOrg.getState());
        orgFromPO.setZip(editOrg.getPostalCode());
        StudySiteAccrualStatusDTO status = studySiteAccrualStatusService
                .getCurrentStudySiteAccrualStatusByStudySite(spDto.getIdentifier());
        handleSetMethodForEdit(spDto, status);
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(cbValue);
        tab.setFacilityOrganization(editOrg);
        tab.setPoHealthCareFacilityIi(null);
        tab.setPoOrganizationIi(null);
        tab.setNewParticipation(false);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        if (editOrg.getName() != null) {
            organizationName = "for " + editOrg.getName();
        }
        // prepare the other two variables for display [personWebDTOList and
        // personWebDTO]
        personWebDTOList = new ArrayList<PaPersonDTO>();
        List<PaPersonDTO> principalInvresults = paHealthCareProviderService.getPersonsByStudySiteId(tab
            .getStudyParticipationId(), StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        List<PaPersonDTO> subInvresults = paHealthCareProviderService.getPersonsByStudySiteId(tab
            .getStudyParticipationId(), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add(principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add(subInvresults.get(i));
        }
        getStudyParticipationPrimContact();
        return ACT_EDIT;
    }
    /**
     * 
     * @return String string
     * @throws PAException PAException
     */
    public String accrualDeleteWarning() throws PAException {
        String[] str = getObjectsToDelete();
        subjects = new ArrayList<StudySubjectWebDto>();
        for (String strValue : str) {
            List<StudySubject> subjectList = (studySubjectService.getBySiteAndStudyId(
                 IiConverter.convertToLong(spIi), Long.parseLong(strValue)));
            if (!subjectList.isEmpty()) { 
                StudySubjectWebDto dto = new StudySubjectWebDto();
                dto.setAccuralCount((long) subjectList.size());
                dto.setPoID(subjectList.get(0).getStudySite()
                     .getHealthCareFacility().getOrganization().getIdentifier());
                dto.setSiteName(subjectList.get(0).getStudySite()
                     .getHealthCareFacility().getOrganization().getName());
                subjects.add(dto);
            }
            subjectList.clear();
        }
        return "deleteStatus";
    }
    
    /**
     * @return result
     * @throws PAException  exception
     */
    public String delete() throws PAException {
        getPartSiteCache().remove(spIi);
        clearErrorsAndMessages();
        try {
           deleteSelectedObjects();
           ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.MULTI_DELETE_MESSAGE);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        loadForm();        
        return ParticipatingOrganizationsAction.ACT_DELETE;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void deleteObject(Long objectId) throws PAException {     
        studySiteService.delete(IiConverter.convertToIi(objectId));
    }

    @SuppressWarnings("unchecked")
    private void loadForm() throws PAException {
        Element cachedOrgList = getPartSiteCache().get(spIi);
        if (cachedOrgList != null) {
            setOrganizationList((List<PaOrganizationDTO>) cachedOrgList.getObjectValue());
            return;
        }
        organizationList = new ArrayList<PaOrganizationDTO>();
        List<ParticipatingOrgDTO> orgList = participatingOrgService.getTreatingSites(IiConverter.convertToLong(spIi));
        for (ParticipatingOrgDTO dto : orgList) {
            PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
            orgWebDTO.setId(dto.getStudySiteId().toString());
            orgWebDTO.setName(dto.getName());
            orgWebDTO.setNciNumber(dto.getPoId());
            orgWebDTO.setRecruitmentStatus(dto.getRecruitmentStatus() == null ? "unknown" : dto.getRecruitmentStatus()
                    .getCode());
            orgWebDTO.setRecruitmentStatusDate(dto.getRecruitmentStatusDate() == null ? "unknown" : PAUtil
                    .normalizeDateString(dto.getRecruitmentStatusDate().toString()));
            orgWebDTO.setRecruitmentStatusComments(dto.getRecruitmentStatusComments());
            orgWebDTO.setTargetAccrualNumber(dto.getTargetAccrualNumber() == null ? null : dto.getTargetAccrualNumber()
                    .toString());
            orgWebDTO.setStatus(dto.getStatusCode().getCode());
            orgWebDTO.setProgramCode(dto.getProgramCodeText());
            orgWebDTO.setInvestigator(convertInvestigators(dto.getPrincipalInvestigators(), dto.getSubInvestigators()));
            
            Map<Long, String> investigatorsMap = new HashMap<Long, String>();
            for (PaPersonDTO eachPi : dto.getPrincipalInvestigators()) {
                investigatorsMap.put(eachPi.getPaPersonId(), getInvestigatorDisplayString(eachPi));
            }
            for (PaPersonDTO eachSi : dto.getSubInvestigators()) {
                investigatorsMap.put(eachSi.getPaPersonId(), getInvestigatorDisplayString(eachSi));
            }
            orgWebDTO.setInvestigators(investigatorsMap);
            List<PaPersonDTO> primaryContacts = dto.getPrimaryContacts();
            StringBuffer primaryContactDisplay = new StringBuffer();
            for (PaPersonDTO primaryContact : primaryContacts) {
                String fullName = StringUtils.defaultString(primaryContact.getFullName());
                String status = getCode(primaryContact.getStatusCode());
                primaryContactDisplay.append(String.format(PRIMARY_CONTACT_DISPLAY_FMT, fullName, status));
            }
            //check if primary contact is title
            if (primaryContacts.isEmpty()) {
                StudySiteContactDTO siteConDto =
                        getPrimaryContact(IiConverter.convertToStudySiteIi(dto.getStudySiteId()));
                if (!ISOUtil.isIiNull(siteConDto.getOrganizationalContactIi())) {
                    try {
                        PAContactDTO paDto = correlationUtils.getContactByPAOrganizationalContactId((
                            Long.valueOf(siteConDto.getOrganizationalContactIi().getExtension())));
                        if (paDto.getTitle() != null)  {
                            String statusCodeDispName =
                                StConverter.convertToString(siteConDto.getStatusCode().getDisplayName());
                            primaryContactDisplay.append(String.format(PRIMARY_CONTACT_DISPLAY_FMT, paDto.getTitle(),
                                                                 statusCodeDispName));
                        }
                    } catch (NullifiedRoleException e) {
                        LOG.error("NullifiedRoleException while getting site contact Info" + e.getMessage());
                    }
                }
            }
            orgWebDTO.setPrimarycontact(primaryContactDisplay.toString());
            organizationList.add(orgWebDTO);
        }
        Element element = new Element(spIi, organizationList);
        getPartSiteCache().put(element);
    }

    /**
     * @return the statusTransitionWarnings
     */
    public String getStatusTransitionWarnings() {
        return statusTransitionWarnings;
    }

    /**
     * @param statusTransitionWarnings the statusTransitionWarnings to set
     */
    public void setStatusTransitionWarnings(String statusTransitionWarnings) {
        this.statusTransitionWarnings = statusTransitionWarnings;
    }

    /**
     * @return the statusTransitionErrors
     */
    public String getStatusTransitionErrors() {
        return statusTransitionErrors;
    }

    /**
     * @param statusTransitionErrors the statusTransitionErrors to set
     */
    public void setStatusTransitionErrors(String statusTransitionErrors) {
        this.statusTransitionErrors = statusTransitionErrors;
    }

    private String convertInvestigators(List<PaPersonDTO> pis, List<PaPersonDTO> sis) throws PAException {
        StringBuffer invList = new StringBuffer();
        getInvestigatorDisplayString(invList, pis);
        getInvestigatorDisplayString(invList, sis);
        return invList.toString();
    }

    private void getInvestigatorDisplayString(StringBuffer invList, List<PaPersonDTO> investigators) {
        for (PaPersonDTO pi : investigators) {
            String fullName = StringUtils.defaultString(pi.getFullName());
            String roleName = getCode(pi.getRoleName());
            String status = getCode(pi.getStatusCode());
            String comma = investigators.indexOf(pi) == investigators.size() - 1 ? ""
                    : ", ";
            invList.append(String.format(INVESTIGATOR_DISPLAY_FMT, fullName, roleName, status, comma));
        }
    }
    
    private String getInvestigatorDisplayString(PaPersonDTO investigator) {
        String fullName = StringUtils.defaultString(investigator.getFullName());
        String roleName = getCode(investigator.getRoleName());
        String status = getCode(investigator.getStatusCode());
        return String.format(INVESTIGATOR_DISPLAY_FMT, fullName, roleName, status, "");
    }    

    /**
     * Null-safe method to get the code from a coded enum; returns "" if the enum is null.
     * @param ce CodedEnum from which to get the code (may be null)
     * @return the code or the empty string if <code>ce</code> was null
     */
    private String getCode(CodedEnum<String> ce) {
        String str = "";
        if (ce != null) {
            str = ce.getCode();
        }
        return str;
    }

    /**
     * @return the organizationList
     * @throws PAException on error.
     */
    public String displayOrg() throws PAException {
        //gov.nih.nci.pa.dto.OrganizationDTO paOrgDTO = new gov.nih.nci.pa.dto.OrganizationDTO();
        PaOrganizationDTO paOrgDTO = new PaOrganizationDTO();
        clearErrorsAndMessages();
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));

        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedOrgDTO = organizationEntityService.search(criteria, limit).get(0);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }

        // convert the PO DTO to the pa domain
        paOrgDTO = PADomainUtils.convertPoOrganizationDTO(selectedOrgDTO, null);

        // store selection
        Organization org = new Organization();
        org.setCity(paOrgDTO.getCity());
        org.setCountryName(paOrgDTO.getCountry());
        org.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        org.setName(paOrgDTO.getName());
        org.setPostalCode(paOrgDTO.getZip());
        editOrg = new Organization();
        editOrg.setCity(paOrgDTO.getCity());
        editOrg.setCountryName(paOrgDTO.getCountry());
        editOrg.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        editOrg.setName(paOrgDTO.getName());
        editOrg.setPostalCode(paOrgDTO.getZip());
        // setting the org values to the member var
        orgFromPO.setCity(paOrgDTO.getCity());
        orgFromPO.setCountry(paOrgDTO.getCountry());
        orgFromPO.setState(paOrgDTO.getState());
        orgFromPO.setName(paOrgDTO.getName());
        orgFromPO.setZip(paOrgDTO.getZip());
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setPoOrganizationIi(selectedOrgDTO.getIdentifier());
        tab.setFacilityOrganization(org);
        setNewParticipation(false);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return DISPLAYJSP;
    }

    /**
     * This method is called upon clicking the second tab (Investigators).
     *
     * @return result
     * @throws PAException on error
     */
    public String getStudyParticipationContacts() throws PAException {
        clearErrorsAndMessages();
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        returnDisplaySPContacts(tab);
        return DISPLAY_STUDY_PART_CONTACTS;
    }

    /**
     * @return the result
     */
    public String saveStudyParticipationContact() {
        clearErrorsAndMessages();

        String roleCode = ServletActionContext.getRequest().getParameter("rolecode");
        String persId = ServletActionContext.getRequest().getParameter("persid");
        if (selectedPersTO == null) {
            try {
                selectedPersTO = personEntityService.getPerson(EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            } catch (NullifiedEntityException ne) {
                addActionError(Constants.FAILURE_MESSAGE + "This person is no longer available");
                return ERROR_PRIMARY_CONTACTS;
            } catch (NumberFormatException e) {
                addActionError(Constants.FAILURE_MESSAGE + e.getMessage());
                return ERROR_PRIMARY_CONTACTS;
            }
        }
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
            .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        String poOrgId = tab.getFacilityOrganization().getIdentifier();
        try {
            Ii ssIi = IiConverter.convertToStudySiteIi(tab.getStudyParticipationId());
            addInvestigator(ssIi, selectedPersTO.getIdentifier(), roleCode, poOrgId);
        } catch (PAException e) {
            if (StringUtils.isNotEmpty(e.getLocalizedMessage())) {
                addActionError(e.getLocalizedMessage());
            } else {
                addActionError("Investigator cannot be added to the Nullified Org " + poOrgId);
            }
            return DISPLAY_SPART_CONTACTS;
        }
        // This makes a fresh db call to show the result on the JSP
        return returnDisplaySPContacts(tab);
    }

    /**
     * @return the result
     * @throws PAException on error
     */
    @SuppressWarnings("deprecation")
    public String deleteStudyPartContact() throws PAException {
        clearErrorsAndMessages();
        String invId = ServletActionContext.getRequest().getParameter("persid");
        //StudyParticipationContactDTO contactDTO = sPartContactService.get(IiConverter.convertToIi(invId));
        // Long identifier = IiConverter.convertToLong(contactDTO.getHealthCareProviderIi());
        ParticipatingOrganizationsTabWebDTO orgsTabWebDTO = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        studySiteContactService.delete(IiConverter.convertToIi(invId));
        // If a primary contact also exists delete that as well - Changed the requirement DO NOT DELETE*
        //        List returnlist = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
        //                orgsTabWebDTO.getStudyParticipationId(), Constants.STUDY_PRIMARY_CONTACT);
        //        if (!returnlist.isEmpty()) {
        //            StudyParticipationContactDTO contactDTODB = sPartContactService.get(IiConverter
        //                    .convertToIi(((PaPersonDTO) returnlist.get(0)).getId()));
        //            Long identifierDB = IiConverter.convertToLong(contactDTODB.getClinicalResearchStaffIi());
        //            if (identifier.equals(identifierDB)) {

        //            }
        //        }
        return returnDisplaySPContacts(orgsTabWebDTO);
    }

    /**
     * Gets the value from Database.
     * @return the result
     */
    @SuppressWarnings("deprecation")
    private String getStudyParticipationPrimContact() {
        clearErrorsAndMessages();
        ParticipatingOrganizationsTabWebDTO orgsTabWebDTO =
                (ParticipatingOrganizationsTabWebDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        try {
            List<PaPersonDTO> resultsList =
                    paHealthCareProviderService.getPersonsByStudySiteId(orgsTabWebDTO.getStudyParticipationId(),
                                                                        StudySiteContactRoleCode.PRIMARY_CONTACT
                                                                            .getName());
            if (resultsList != null && !resultsList.isEmpty()) {
                personContactWebDTO = resultsList.get(0);
            } else {
                Ii studySiteIi = IiConverter.convertToIi(orgsTabWebDTO.getStudyParticipationId());
                StudySiteContactDTO primaryContactDto = getPrimaryContact(studySiteIi);
                personContactWebDTO = createOrganizationalContactWebDTO(primaryContactDto);
            }
        } catch (NullifiedRoleException e) {
            LOG.error("NullifiedRoleException while getting site contact Info", e);
            addActionError("NullifiedRoleException while getting site contact Info: " + e.getMessage());
        } catch (PAException e) {
            LOG.error("exception: ", e);
            addActionError("Exception: " + e.getMessage());
        }
        return DISPLAY_SP_CONTACTS;
    }
    
    private StudySiteContactDTO getPrimaryContact(Ii studySiteIi) throws PAException {
        StudySiteContactDTO primaryContactDto = new StudySiteContactDTO();
        List<StudySiteContactDTO> siteContactDtos = studySiteContactService.getByStudySite(studySiteIi);
        for (StudySiteContactDTO contactDto : siteContactDtos) {
            String roleCode = contactDto.getRoleCode().getCode();
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equalsIgnoreCase(roleCode)) {
                primaryContactDto = contactDto;
            }
        }
        return primaryContactDto;
    }
    
    private PaPersonDTO createOrganizationalContactWebDTO(StudySiteContactDTO primaryContactDto)
            throws NullifiedRoleException, PAException {
        PaPersonDTO webDTO = null;
        if (!ISOUtil.isIiNull(primaryContactDto.getOrganizationalContactIi())) {
            PAContactDTO paDto = correlationUtils.getContactByPAOrganizationalContactId((Long.valueOf(primaryContactDto
                .getOrganizationalContactIi().getExtension())));
            if (paDto.getTitle() != null) {
                webDTO = new PaPersonDTO();
                webDTO.setTitle(paDto.getTitle());
                webDTO.setTelephone(DSetConverter.getFirstElement(primaryContactDto.getTelecomAddresses(), "PHONE"));
                webDTO.setEmail(DSetConverter.getFirstElement(primaryContactDto.getTelecomAddresses(), "EMAIL"));
                webDTO.setSelectedPersId(Long.valueOf(paDto.getSrIdentifier().getExtension()));
                webDTO.setId(Long.valueOf(paDto.getSrIdentifier().getExtension()));
                webDTO.setStatusCode(FunctionalRoleStatusCode.getByCode(CdConverter.convertCdToString(primaryContactDto
                    .getStatusCode())));
            }
        }
        return webDTO;
    }

    private String returnDisplaySPContacts(ParticipatingOrganizationsTabWebDTO orgsTabWebDTO) {
        personWebDTOList = new ArrayList<PaPersonDTO>();
        try {
            List<PaPersonDTO> principalInvresults = paHealthCareProviderService.getPersonsByStudySiteId(orgsTabWebDTO
                .getStudyParticipationId(), StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
            List<PaPersonDTO> subInvresults = paHealthCareProviderService.getPersonsByStudySiteId(orgsTabWebDTO
                .getStudyParticipationId(), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
            for (int i = 0; i < principalInvresults.size(); i++) {
                personWebDTOList.add(principalInvresults.get(i));
            }
            for (int i = 0; i < subInvresults.size(); i++) {
                personWebDTOList.add(subInvresults.get(i));
            }
        } catch (PAException e) {
            addActionError(Constants.FAILURE_MESSAGE + e.getMessage());
        }
        return DISPLAY_SPART_CONTACTS;
    }

    /**
     * @return the result
     * @throws PAException on error
     */
    public String getDisplaySPContacts() throws PAException {
        ParticipatingOrganizationsTabWebDTO orgsTabWebDTO = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
            .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        personWebDTOList = new ArrayList<PaPersonDTO>();
        List<PaPersonDTO> principalInvresults = paHealthCareProviderService.getPersonsByStudySiteId(orgsTabWebDTO
            .getStudyParticipationId(), StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        List<PaPersonDTO> subInvresults = paHealthCareProviderService.getPersonsByStudySiteId(orgsTabWebDTO
            .getStudyParticipationId(), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add(principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add(subInvresults.get(i));
        }
        return DISPLAY_SPART_CONTACTS;
    }

    /**
     * @return the result
     * @throws PAException on error.
     * @throws NullifiedEntityException on deletes
     */
    public String saveStudyParticipationPrimContact() throws PAException, NullifiedEntityException {
        clearErrorsAndMessages();
        final HttpServletRequest request = ServletActionContext.getRequest();
        String persId = request.getParameter("contactpersid");
        String email = request.getParameter("email");
        String telephone = request.getParameter("tel");
        try {
            final HttpSession session = request.getSession();
            ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) session
                    .getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
            String poOrgId = tab.getFacilityOrganization().getIdentifier();

            validatePrimaryContact(poOrgId, persId, email, telephone);
            if (hasFieldErrors()) {
                reloadPrimaryContact(persId, email, telephone);
                return ERROR_PRIMARY_CONTACTS;
            }

            Ii selectedPersTOIi = getSelectedPersonIi(persId);
            Ii ssIi = IiConverter.convertToStudySiteIi(tab.getStudyParticipationId());
            StringUtils.replace(telephone, " ", "");
            List<String> emailList = addToList(email);
            List<String> telList = addToList(telephone);
            DSet<Tel> list = new DSet<Tel>();
            list = DSetConverter.convertListToDSet(emailList, "EMAIL", list);
            list = DSetConverter.convertListToDSet(telList, "PHONE", list);
            if (IiConverter.ORGANIZATIONAL_CONTACT_ROOT.equals(selectedPersTOIi.getRoot())) {
                addGenericContact(ssIi, selectedPersTOIi, list);
            } else if (IiConverter.PERSON_ROOT.equals(selectedPersTOIi.getRoot())) {
                addPrimaryContact(ssIi, selectedPersTOIi, poOrgId, list);
            }
            session
                    .setAttribute(Constants.SUCCESS_MESSAGE,
                            Constants.CREATE_MESSAGE);
        } catch (PAException e) {
            addActionError("Exception:Investigator can not be added to the Nullified Org" + e.getLocalizedMessage());
            return DISPLAY_SPART_CONTACTS;
        } catch (NullifiedRoleException e) {
            addActionError(Constants.FAILURE_MESSAGE + e.getMessage());
            return DISPLAY_PRIM_CONTACTS;
        }
        return REFRESH_PRIMARY_CONTACT;
    }

    private List<String> addToList(String value) {
        List<String> toList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(value)) {
            toList.add(value);
        }
        return toList;
    }

    private Ii getSelectedPersonIi(String persId) throws NullifiedEntityException, PAException, NullifiedRoleException {
        Ii selectedPersTOIi = null;
        PersonDTO isoPerDTO = personEntityService.getPerson(IiConverter.convertToPoPersonIi(persId));
        if (isoPerDTO == null) {
            DSet<Ii> iiDset = organizationalContactCorrelationService
                .getCorrelation(IiConverter.convertToPoOrganizationalContactIi(persId)).getIdentifier();
            selectedPersTOIi = DSetConverter.convertToIi(iiDset);
        } else {
            selectedPersTOIi = isoPerDTO.getIdentifier();
        }
        return selectedPersTOIi;
    }

    private void validatePrimaryContact(String poOrgId, String persId, String email, String telephone) {
        validatePersonIdAndAtLeastOneTelecom(persId, email, telephone);
        validateEmail(email);
        validatePhone(poOrgId, telephone);
    }

    private void validatePersonIdAndAtLeastOneTelecom(String persId, String email, String telephone) {
        if (StringUtils.isEmpty(persId)) {
            addFieldError("personContactWebDTO.firstName", getText("Please lookup and select person"));
        }
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(telephone)) {
            addFieldError("personContactWebDTO.telephone", getText("error.enterEmailAddressOrPhone"));
        }
    }

    private void validatePhone(String poOrgId, String telephone) {
        Ii orgIi = IiConverter.convertToPoOrganizationIi(poOrgId);
        String countryName = paServiceUtil.getEntityCountryName(orgIi);
        if (!StringUtils.isBlank(telephone) && !PhoneUtil.isPhoneNumberValid(countryName, telephone)) {
            addFieldError("personContactWebDTO.telephone", getText("error.usOrCanPhone"));
       }
    }

    private void validateEmail(String email) {
        if (StringUtils.isNotBlank(email) && !PAUtil.isValidEmail(email)) {
            addFieldError("personContactWebDTO.email", getText("error.enterValidEmail"));
        }
    }

    private void reloadPrimaryContact(String persId, String email, String telephone) throws PAException,
        NullifiedRoleException {
        personContactWebDTO = new PaPersonDTO();
        personContactWebDTO.setEmail(email);
        personContactWebDTO.setTelephone(telephone);
        if (StringUtils.isNotEmpty(persId)) {
            Long valueOfPerId = Long.valueOf(persId);
            personContactWebDTO.setSelectedPersId(valueOfPerId);
            reloadPersonContactWebDto(persId, valueOfPerId);
        }
    }

    private void reloadPersonContactWebDto(String persId, Long valueOfPerId)
        throws PAException, NullifiedRoleException {
        if (selectedPersTO != null && selectedPersTO.getName() != null) {
            gov.nih.nci.pa.dto.PaPersonDTO personDTO = PADomainUtils.convertToPaPersonDTO(selectedPersTO);
            personContactWebDTO.setFirstName(personDTO.getFirstName());
            personContactWebDTO.setLastName(personDTO.getLastName());
            personContactWebDTO.setMiddleName(personDTO.getMiddleName());
        } else {
            Person paPerson =  correlationUtils.getPAPersonByIi(IiConverter.convertToPoPersonIi(persId));
            if (paPerson != null) {
                personContactWebDTO.setFirstName(paPerson.getFirstName());
                personContactWebDTO.setLastName(paPerson.getLastName());
                personContactWebDTO.setMiddleName(paPerson.getMiddleName());
            } else {
                OrganizationalContactDTO paDTO = organizationalContactCorrelationService.getCorrelation(IiConverter
                    .convertToPoOrganizationalContactIi(valueOfPerId.toString()));
                if (paDTO != null && paDTO.getTitle() != null) {
                    personContactWebDTO.setTitle(StConverter.convertToString(paDTO.getTitle()));
                }
            }
        }
    }
    /**
     * @return the result
     * @throws PAException
     *             on error.
     */
    public String refreshPrimaryContact() throws PAException {
        getStudyParticipationPrimContact();
        return DISPLAY_PRIM_CONTACTS;
    }

    /**
     * This method is called when a primary contact is chosen.
     * @return the result
     * @throws PAException  on error.
     *  * @throws NullifiedEntityException on deletes
     */
    public String displayStudyParticipationPrimContact() throws PAException, NullifiedEntityException {
        clearErrorsAndMessages();
        String contactPersId = ServletActionContext.getRequest().getParameter("contactpersid");
        String editmode = ServletActionContext.getRequest().getParameter("editmode");
        PaPersonDTO webDTO = null;
        personContactWebDTO = new PaPersonDTO();
        if ("yes".equals(editmode)) {
            webDTO = paHealthCareProviderService.getIdentifierBySPCId(Long.valueOf(contactPersId));
            personContactWebDTO.setFirstName(webDTO.getFirstName());
            personContactWebDTO.setLastName(webDTO.getLastName());
            personContactWebDTO.setMiddleName(webDTO.getMiddleName());
            personContactWebDTO.setSelectedPersId(webDTO.getSelectedPersId());

        } else {
            selectedPersTO = personEntityService.getPerson(EnOnConverter.convertToOrgIi(Long.valueOf(contactPersId)));
            personContactWebDTO.setSelectedPersId(Long.valueOf(selectedPersTO.getIdentifier().getExtension()));
            gov.nih.nci.pa.dto.PaPersonDTO personDTO = PADomainUtils.convertToPaPersonDTO(selectedPersTO);
            personContactWebDTO.setFirstName(personDTO.getFirstName());
            personContactWebDTO.setLastName(personDTO.getLastName());
            personContactWebDTO.setMiddleName(personDTO.getMiddleName());
        }
        String email = (String) ServletActionContext.getRequest().getSession().getAttribute("emailEntered");
        String telephone = (String) ServletActionContext.getRequest().getSession().getAttribute("telephoneEntered");
        if (email != null) {
            personContactWebDTO.setEmail(email);
        }
        if (telephone != null) {
            personContactWebDTO.setTelephone(telephone);
        }
        return DISPLAY_PRIM_CONTACTS;
    }


    /**
     * This method is used to enforce the business rules which are form specific or based on an interaction between
     * services.
     */
    private void enforceBusinessRules() {
        clearErrorsAndMessages();
        if (StringUtils.isEmpty(getRecStatus())) {
            addFieldError("recStatus", getText("error.participatingStatus"));
        }
        if (StringUtils.isEmpty(getRecStatusDate())) {
            addFieldError(REC_STATUS_DATE, getText("error.participatingStatusDate"));
        }
        if (StringUtils.isEmpty(orgFromPO.getName())) {
            addFieldError(EDIT_ORG_NAME, "Please choose an organization");
        }
        if (StringUtils.isNotEmpty(getRecStatusDate())) {
            Timestamp newDate = PAUtil.dateStringToTimestamp(getRecStatusDate());
            if (newDate.after(new Timestamp(new Date().getTime()))) {
                addFieldError(REC_STATUS_DATE, getText("error.participatingStatusDateCheck"));
            }
        }
    }

    /**
     * @return the organizationList
     */
    public List<PaOrganizationDTO> getOrganizationList() {
        return organizationList;
    }

    /**
     * @param organizationList the organizationList to set
     */
    public void setOrganizationList(List<PaOrganizationDTO> organizationList) {
        this.organizationList = organizationList;
    }

    /**
     * @return the selectedOrgDTO
     */
    public OrganizationDTO getSelectedOrgDTO() {
        return selectedOrgDTO;
    }

    /**
     * @param selectedOrgDTO the selectedOrgDTO to set
     */
    public void setSelectedOrgDTO(OrganizationDTO selectedOrgDTO) {
        this.selectedOrgDTO = selectedOrgDTO;
    }

    /**
     * @return the cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue the cbValue to set
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return the recStatus
     */
    public String getRecStatus() {
        return recStatus;
    }

    /**
     * @param recStatus the recStatus to set
     */
    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    /**
     * @return the recStatusDate
     */
    public String getRecStatusDate() {
        return recStatusDate;
    }

    /**
     * @param recStatusDate  the recStatusDate to set
     */
    public void setRecStatusDate(String recStatusDate) {
        this.recStatusDate = PAUtil.normalizeDateString(recStatusDate);
    }

    /**
     * @return the recStatusComments
     */
    public String getRecStatusComments() {
        return recStatusComments;
    }

    /**
     * @param recStatusComments the recStatusComments to set
     */
    public void setRecStatusComments(String recStatusComments) {
        this.recStatusComments = recStatusComments;
    }

    /**
     * @return the spContactDTO
     */
    public List<StudySiteContactDTO> getSpContactDTO() {
        return spContactDTO;
    }

    /**
     * @param spContactDTO  the spContactDTO to set
     */
    public void setSpContactDTO(List<StudySiteContactDTO> spContactDTO) {
        this.spContactDTO = spContactDTO;
    }

    /**
     * @return the newParticipation
     */
    public boolean isNewParticipation() {
        return newParticipation;
    }

    /**
     * @param newParticipation the newParticipation to set
     */
    public void setNewParticipation(boolean newParticipation) {
        this.newParticipation = newParticipation;
    }

    /**
     * @return the editOrg
     */
    public Organization getEditOrg() {
        return editOrg;
    }

    /**
     * @param editOrg  the editOrg to set
     */
    public void setEditOrg(Organization editOrg) {
        this.editOrg = editOrg;
    }

    /**
     * @return the personWebDTOList
     */
    public List<PaPersonDTO> getPersonWebDTOList() {
        return personWebDTOList;
    }

    /**
     * @param personWebDTOList  the personWebDTOList to set
     */
    public void setPersonWebDTOList(List<PaPersonDTO> personWebDTOList) {
        this.personWebDTOList = personWebDTOList;
    }

    /**
     * @return the personContactWebDTO
     */
    public PaPersonDTO getPersonContactWebDTO() {
        return personContactWebDTO;
    }

    /**
     * @param personContactWebDTO
     *            the personContactWebDTO to set
     */
    public void setPersonContactWebDTO(PaPersonDTO personContactWebDTO) {
        this.personContactWebDTO = personContactWebDTO;
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
     * @return the orgFromPO
     */
    public PaOrganizationDTO getOrgFromPO() {
        return orgFromPO;
    }

    /**
     * @param orgFromPO the orgFromPO to set
     */
    public void setOrgFromPO(PaOrganizationDTO orgFromPO) {
        this.orgFromPO = orgFromPO;
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * @return the targetAccrualNumber
     */
    public String getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(String targetAccrualNumber) {
        Integer tInt;
        try {
            tInt = Integer.parseInt(targetAccrualNumber);
            this.targetAccrualNumber = tInt.toString();
        } catch (NumberFormatException e) {
            this.targetAccrualNumber = null;
        }
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
     * @return the siteLocalTrialIdentifier
     */
    public String getSiteLocalTrialIdentifier() {
        return siteLocalTrialIdentifier;
    }

    /**
     * @param siteLocalTrialIdentifier the siteLocalTrialIdentifier to set
     */
    public void setSiteLocalTrialIdentifier(String siteLocalTrialIdentifier) {
        this.siteLocalTrialIdentifier = siteLocalTrialIdentifier;
    }

    /**
     * @return the siteProgramCodeText
     */
    public String getSiteProgramCodeText() {
        return siteProgramCodeText;
    }

    /**
     * @param siteProgramCodeText the siteProgramCodeText to set
     */
    public void setSiteProgramCodeText(String siteProgramCodeText) {
        this.siteProgramCodeText = siteProgramCodeText;
    }

    /**
     * @return the dateOpenedForAccrual
     */
    public String getDateOpenedForAccrual() {
        return dateOpenedForAccrual;
    }

    /**
     * @param dateOpenedForAccrual the dateOpenedForAccrual to set
     */
    public void setDateOpenedForAccrual(String dateOpenedForAccrual) {
        this.dateOpenedForAccrual = dateOpenedForAccrual;
    }

    /**
     * @return the dateClosedForAccrual
     */
    public String getDateClosedForAccrual() {
        return dateClosedForAccrual;
    }

    /**
     * @param dateClosedForAccrual the dateClosedForAccrual to set
     */
    public void setDateClosedForAccrual(String dateClosedForAccrual) {
        this.dateClosedForAccrual = dateClosedForAccrual;
    }

    /**
     * @return the proprietaryTrialIndicator
     */
    public String getProprietaryTrialIndicator() {
        return proprietaryTrialIndicator;
    }

    /**
     * @param proprietaryTrialIndicator the proprietaryTrialIndicator to set
     */
    public void setProprietaryTrialIndicator(String proprietaryTrialIndicator) {
        this.proprietaryTrialIndicator = proprietaryTrialIndicator;
    }

    /**
     * @return the studySiteIdentifier
     */
    public Long getStudySiteIdentifier() {
        return studySiteIdentifier;
    }

    /**
     * @param studySiteIdentifier the studySiteIdentifier to set
     */
    public void setStudySiteIdentifier(Long studySiteIdentifier) {
        this.studySiteIdentifier = studySiteIdentifier;
    }

    /**
     *
     * @return s
     */
    public String displayPerson() {
        String perId = ServletActionContext.getRequest().getParameter("perId");
        PersonDTO criteria = new PersonDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(perId)));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        PersonDTO perDTO;
        try {
            perDTO = personEntityService.search(criteria, limit).get(0);
            // convert the PO DTO to the pa domain
            personContactWebDTO = PADomainUtils.convertToPaPersonDTO(perDTO);
            personContactWebDTO.setSelectedPersId(personContactWebDTO.getId());
        } catch (TooManyResultsException e) {
            LOG.error(e);
            addActionError(e.getMessage());
        }
        return "displayPerson";
    }
    
    private void addInvestigator(Ii ssIi, Ii investigatorIi, String role, String poOrgId) throws PAException {
        ClinicalResearchStaffDTO crsDTO = paServiceUtil.getCrsDTO(investigatorIi, poOrgId);
        StudyProtocolDTO tmpSpDTO = studyProtocolService.getStudyProtocol(spIi);
        HealthCareProviderDTO hcpDTO = paServiceUtil.getHcpDTO(
                tmpSpDTO.getStudyProtocolType().getValue(), 
                investigatorIi, poOrgId);
        participatingSiteService.addStudySiteInvestigator(
                ssIi, crsDTO, hcpDTO, null, role);
    }

    private void addPrimaryContact(Ii ssIi, Ii investigatorIi, String poOrgId, DSet<Tel> list) throws PAException {
        ClinicalResearchStaffDTO crsDTO = paServiceUtil.getCrsDTO(investigatorIi, poOrgId);
        StudyProtocolDTO tmpSpDTO = studyProtocolService.getStudyProtocol(spIi);
        HealthCareProviderDTO hcpDTO = paServiceUtil.getHcpDTO(
                tmpSpDTO.getStudyProtocolType().getValue(), 
                investigatorIi, poOrgId);
        participatingSiteService.addStudySitePrimaryContact(ssIi, crsDTO, hcpDTO, null, list);
    }

    private void addGenericContact(Ii ssIi, Ii orgContIi, DSet<Tel> list) throws PAException {
        OrganizationalContactDTO orgContDTO;
        try {
            orgContDTO = organizationalContactCorrelationService.getCorrelation(orgContIi);
        } catch (NullifiedRoleException e) {
            throw new PAException("Generic Contact is no longer available.", e);
        }
        participatingSiteService.addStudySiteGenericContact(ssIi, orgContDTO, true, list);
    }

    private void enforceBusinessRulesForProprietary() {
        String err = "error.submit.invalidDate"; // validate date and its format
        enforcePartialRulesForProp1();
        String strDateOpenedForAccrual = "dateOpenedForAccrual";
        String strDateClosedForAccrual = "dateClosedForAccrual";
        enforcePartialRulesForProp2(err, strDateOpenedForAccrual, strDateClosedForAccrual);
        enforcePartialRulesForProp3(strDateOpenedForAccrual, strDateClosedForAccrual);
        enforcePartialRulesForProp4(strDateOpenedForAccrual, strDateClosedForAccrual);
    }

    private void enforcePartialRulesForProp2(String err, String strDateOpenedForAccrual, 
            String strDateClosedForAccrual) {
        if (StringUtils.isNotEmpty(dateOpenedForAccrual)) {
            if (!PAUtil.isValidDate(dateOpenedForAccrual)) {
                addFieldError(strDateOpenedForAccrual, getText(err));
            } else {
                checkFieldError(PAUtil.isDateCurrentOrPast(dateOpenedForAccrual), strDateOpenedForAccrual,
                                "error.submit.invalidStatusDate");
            }
        }
        if (StringUtils.isNotEmpty(dateClosedForAccrual)) {
            if (!PAUtil.isValidDate(dateClosedForAccrual)) {
                addFieldError(strDateClosedForAccrual, getText(err));
            } else {
                checkFieldError(PAUtil.isDateCurrentOrPast(dateClosedForAccrual), strDateClosedForAccrual,
                                "error.submit.invalidStatusDate");

            }
        }
    }
    
    private void enforcePartialRulesForProp1() {        
        checkFieldError(StringUtils.isEmpty(siteLocalTrialIdentifier), "siteLocalTrialIdentifier",
                        "error.siteLocalTrialIdentifier.required");
    }
    
    private void enforcePartialRulesForProp3(String strDateOpenedForAccrual, String strDateClosedForAccrual) {
        checkFieldError(StringUtils.isNotEmpty(dateClosedForAccrual) && StringUtils.isEmpty(dateOpenedForAccrual),
                        strDateOpenedForAccrual, "error.proprietary.dateOpenReq");
        if (StringUtils.isNotEmpty(dateOpenedForAccrual) && StringUtils.isNotEmpty(dateClosedForAccrual)) {
            Timestamp dateOpenedDateStamp = PAUtil.dateStringToTimestamp(dateOpenedForAccrual);
            Timestamp dateClosedDateStamp = PAUtil.dateStringToTimestamp(dateClosedForAccrual);
            checkFieldError(dateClosedDateStamp.before(dateOpenedDateStamp), strDateClosedForAccrual,
                            "error.proprietary.dateClosedAccrualBigger");
        }

    }

    private void enforcePartialRulesForProp4(String strDateOpenedForAccrual, String strDateClosedForAccrual) {
        if (StringUtils.isNotEmpty(recStatus)) {
            RecruitmentStatusCode recruitmentStatus = RecruitmentStatusCode.getByCode(recStatus);
            if (recruitmentStatus.isNonRecruiting()) {
                if (StringUtils.isNotEmpty(dateOpenedForAccrual)) {
                    addFieldError(strDateOpenedForAccrual, "Date Opened for Acrual must be null for " + recStatus);
                }
            }  else if (StringUtils.isEmpty(dateOpenedForAccrual)) {
                addFieldError(strDateOpenedForAccrual, "Date Opened for Acrual must be not null for " + recStatus);
            }
            if ((RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equalsIgnoreCase(recStatus)
                    || RecruitmentStatusCode.COMPLETED.getCode().equalsIgnoreCase(recStatus))
                    && StringUtils.isEmpty(dateClosedForAccrual)) {
                addFieldError(strDateClosedForAccrual, "Date Closed for Acrual must not be null for " + recStatus);
            }
        }
    }

    /**
     * @return result
     */
    public String historyPopup()  {
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        String studySiteId = tab != null
                && tab.getStudyParticipationId() != null ? tab
                .getStudyParticipationId().toString() : null;
        if (StringUtils.isEmpty(studySiteId)) {
            return "historypopup";
        }
        siteStatusList = new ArrayList<StudyOverallStatusWebDTO>();
        List<StudySiteAccrualStatusDTO> isoList;
        try {
            isoList = studySiteAccrualStatusService.getStudySiteAccrualStatusByStudySite(
                    IiConverter.convertToStudySiteIi(Long.valueOf(studySiteId)));
            StudyOverallStatusWebDTO studySiteStatus = null;
            for (StudySiteAccrualStatusDTO iso : isoList) {
                studySiteStatus = new StudyOverallStatusWebDTO();
                studySiteStatus.setStatusCode(RecruitmentStatusCode.getByCode(iso
                    .getStatusCode().getCode()).getDisplayName());
                studySiteStatus.setStatusDate(PAUtil.normalizeDateString(
                TsConverter.convertToTimestamp(iso.getStatusDate()).toString()));
                studySiteStatus.setComments(iso.getComments().getValue());
                siteStatusList.add(studySiteStatus);
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return "historypopup";
    }
    
    private void checkFieldError(boolean condition, String fieldName, String textKey) {
        if (condition) {
            addFieldError(fieldName, getText(textKey));
        }
    }

    /**
     * @return the siteStatusList
     */
    public List<StudyOverallStatusWebDTO> getSiteStatusList() {
        return siteStatusList;
    }

    /**
     * @param siteStatusList the SiteStatusList to set
     */
    public void setSiteStatusList(
            List<StudyOverallStatusWebDTO> siteStatusList) {
        this.siteStatusList = siteStatusList;
    }
    /**
      * @return the programCode
    */
    public String getProgramCode() {
      return programCode;
    }

    /**
     * @param programCode the programCode to set
     */
     public void setProgramCode(String programCode) {
       this.programCode = programCode;
     }

    /**
     * @param correlationUtils the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

    /**
     * @param paServiceUtil the paServiceUtil to set
     */
    protected void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }

    /**
     * @param organizationalContactCorrelationService the organizationalContactCorrelationService to set
     */
    public void setOrganizationalContactCorrelationService(
            OrganizationalContactCorrelationServiceRemote organizationalContactCorrelationService) {
        this.organizationalContactCorrelationService = organizationalContactCorrelationService;
    }

    /**
     * @param organizationEntityService the organizationEntityService to set
     */
    public void setOrganizationEntityService(OrganizationEntityServiceRemote organizationEntityService) {
        this.organizationEntityService = organizationEntityService;
    }

    /**
     * @param paHealthCareProviderService the paHealthCareProviderService to set
     */
    public void setPaHealthCareProviderService(PAHealthCareProviderLocal paHealthCareProviderService) {
        this.paHealthCareProviderService = paHealthCareProviderService;
    }

    /**
     * @param participatingSiteService the participatingSiteService to set
     */
    public void setParticipatingSiteService(ParticipatingSiteServiceLocal participatingSiteService) {
        this.participatingSiteService = participatingSiteService;
    }

    /**
     * @param personEntityService the personEntityService to set
     */
    public void setPersonEntityService(PersonEntityServiceRemote personEntityService) {
        this.personEntityService = personEntityService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * 
     * @return studySubjectService studySubjectService
     */
    public StudySubjectServiceLocal getStudySubjectService() {
        return studySubjectService;
    }
    /**
     * 
     * @param studySubjectService studySubjectService
     */
    public void setStudySubjectService(StudySubjectServiceLocal studySubjectService) {
        this.studySubjectService = studySubjectService;
    }
    
    
    /**
     * @return the statusTransitionService
     */
    public StatusTransitionService getStatusTransitionService() {
        return statusTransitionService;
    }

    /**
     * @param statusTransitionService the statusTransitionService to set
     */
    public void setStatusTransitionService(
            StatusTransitionService statusTransitionService) {
        this.statusTransitionService = statusTransitionService;
    }

    /**
     * 
     * @return subjects subjects
     */
    public List<StudySubjectWebDto> getSubjects() {
        return subjects;
    }
    /**
     *  
     * @param subjects subjects
     */
    public void setSubjects(List<StudySubjectWebDto> subjects) {
        this.subjects = subjects;
    }
}
