/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
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
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
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

package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Add a participating site to the trial.
 * 
 * @author Denis G. Krylov
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.TooManyFields" })
public class AddUpdateSiteAction extends StatusHistoryManagementAction
        implements Preparable {

    static final String SUCCESS_MESSAGE_KEY = "successMessage";
    private static final String WAIT = "wait";
    private static final String SESSION_TRIAL_NCI_ID_ATTRIBUTE = "NCI_ID";
    private static final String SESSION_TRIAL_TITLE_ATTRIBUTE = "TITLE";
    private static final String SESSION_TRIAL_LEAD_ORG_IDENTIFIER_ATTRIBUTE = "LEAD_ORG_ID";
    private static final String SESSION_CANCER_TRIAL_ATTRIBUTE = "CANCER_TRIAL";
    private static final String SESSION_PGC_MASTER_LIST_ATTRIBUTE = "PGC_MASTER_LIST";
    private static final String SESSION_PGC_ID_LIST_ATTRIBUTE = "PGC_ID_LIST";
    private static final String SESSION_PGC_FAMILY_INDEX_ATTRIBUTE = "PGC_FAMILY_INDEX";
    private static final String SESSION_FAMILY_ID_ATTRIBUTE = "FAMILY_ID";
    private static final String SESSION_IS_SITE_ADMIN_ATTRIBUTE = "isSiteAdmin";
    private static final String REQUEST_SITE_ADMIN_ATTRIBUTE = "SiteAdmin";

    private static final long serialVersionUID = -5720501246071254426L;
    private static final Logger LOG = Logger
            .getLogger(AddUpdateSiteAction.class);
    private static final String PICK_A_SITE = "pickASite";

    private SubmittedOrganizationDTO siteDTO = new SubmittedOrganizationDTO();

    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    private TrialUtil trialUtil = new TrialUtil();

    private RegistryUserServiceLocal registryUserService;
    private ParticipatingSiteServiceLocal participatingSiteService;
    private StudyProtocolServiceLocal studyProtocolService;
    private StudySiteContactServiceLocal studySiteContactService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private OrganizationEntityServiceRemote organizationService;
    private StatusTransitionService statusTransitionService;
    private FamilyProgramCodeService familyProgramCodeService;

    private boolean addSitesMultiple = false;
    private boolean redirectToSummary;
    private String studyProtocolId;
    private String pickedSiteOrgPoId;
    private String programCode;

    @SuppressWarnings("rawtypes")
    @Override
    protected final Class getStatusEnumClass() {
        return RecruitmentStatusCode.class;
    }

    @Override
    protected final CodedEnum<String> getStatusEnumByCode(String code) {
        return RecruitmentStatusCode.getByCode(code);
    }

    @Override
    protected final boolean requiresReasonText(CodedEnum<String> statEnum) {
        return false;
    }

    @Override
    protected final TransitionFor getStatusTypeHandledByThisClass() {
        return TransitionFor.SITE_STATUS;
    }

    @Override
    protected TrialType getTrialTypeHandledByThisClass() {
        return TrialType.ABBREVIATED;
    }

    @Override
    public boolean isOpenSitesWarningRequired() {
        return false;
    }

    /**
     * Prepare and display the site add/update pop-up.
     * 
     * @return status page if successful, error page otherwise
     */
    public String view() {
        try {
            setInitialStatusHistory(new ArrayList<StatusDto>());
            prepareProtocolData();
            populateSiteDTO();
            setSiteDtoInSession();
            prepareProgramCodeData();

            setAddSitesMultiple(StringUtils.isBlank(siteDTO.getId())
                    && getListOfSitesUserCanAdd().size() > 1);

            // PO-8268 kicks in here. If user can actually update multiple
            // sites, we need to present a dialog
            // asking which one she or he wants to update.
            if (canUpdateMultipleSites()) {
                return PICK_A_SITE;
            } else {
                // PO-9100 Add Multiple sites check is performed to Allow user
                // to specify any organization in Family
                // as participating site at time of importing Industrial Trial
                if (addSitesMultiple) {
                    return PICK_A_SITE;
                } else {
                    return SUCCESS;
                }
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
            LOG.error(e, e);
            return ERROR;
        }
    }

    /**
     * @return addSitesMultiple
     */
    public boolean isAddSitesMultiple() {
        return addSitesMultiple;
    }

    /**
     * @param addSitesMultiple
     *            for the view
     */
    public void setAddSitesMultiple(boolean addSitesMultiple) {
        this.addSitesMultiple = addSitesMultiple;
    }

    /**
     * @throws NumberFormatException
     * @throws PAException
     */
    private void prepareProtocolData() throws PAException {
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long
                .parseLong(studyProtocolId));
        StudyProtocolDTO spDTO = studyProtocolService
                .getStudyProtocol(studyProtocolIi);
        getServletRequest().getSession()
                .setAttribute(Constants.TRIAL_SUMMARY, spDTO);
        StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
        studyProtocolQueryDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(Long
                        .parseLong(studyProtocolId));
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(SESSION_TRIAL_NCI_ID_ATTRIBUTE,
                        studyProtocolQueryDTO.getNciIdentifier());
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(SESSION_TRIAL_TITLE_ATTRIBUTE,
                        studyProtocolQueryDTO.getOfficialTitle());
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(SESSION_TRIAL_LEAD_ORG_IDENTIFIER_ATTRIBUTE,
                        studyProtocolQueryDTO.getLocalStudyProtocolIdentifier());
    }

    private void prepareProgramCodeData() throws PAException {
        StudyProtocolDTO spDTO = (StudyProtocolDTO) getServletRequest()
                .getSession().getAttribute(Constants.TRIAL_SUMMARY);
        List<OrgFamilyDTO> ofList = FamilyHelper.getByOrgId(Long.parseLong(getPickedSiteOrgPoId()));
        List<ProgramCodeDTO> masterPgcList = new ArrayList<ProgramCodeDTO>();
        List<Long> selectedPgcIdList = new ArrayList<Long>();
        Map<ProgramCodeDTO, FamilyDTO> pgcFamilyIndex = new LinkedHashMap<ProgramCodeDTO, FamilyDTO>();
        boolean cancerTrial = false;
        FamilyDTO aFamily = null;
        if (!ofList.isEmpty()) {
            cancerTrial = true;
            for (OrgFamilyDTO of : ofList) {
                FamilyDTO family = familyProgramCodeService.getFamilyDTOByPoId(of.getId());
                if (family != null) {
                    for (ProgramCodeDTO pgc : family.getProgramCodes()) {
                        if (pgc.isActive()) {
                           pgcFamilyIndex.put(pgc, family);
                            aFamily = family;
                            masterPgcList.add(pgc);
                        }

                    }
                }
            }
            if (CollectionUtils.isNotEmpty(spDTO.getProgramCodes())) {
                for (ProgramCodeDTO p : spDTO.getProgramCodes()) {
                    final ProgramCodeDTO thePgc = p;
                    ProgramCodeDTO found = (ProgramCodeDTO) CollectionUtils.find(masterPgcList, new Predicate() {
                        @Override
                        public boolean evaluate(Object o) {
                            return ((ProgramCodeDTO) o).getId().equals(thePgc.getId());
                        }
                    });
                    if (found != null) {
                        selectedPgcIdList.add(found.getId());
                    }
                }
            }
        }
        programCode = StringUtils.join(selectedPgcIdList, ",");
        Collections.sort(masterPgcList, new Comparator<ProgramCodeDTO>() {
            @Override
            public int compare(ProgramCodeDTO o1, ProgramCodeDTO o2) {
                return o1.getProgramCode().compareTo(o2.getProgramCode());
            }
        });
        getServletRequest()
                .getSession().setAttribute(SESSION_PGC_MASTER_LIST_ATTRIBUTE, masterPgcList);
        getServletRequest()
                .getSession().setAttribute(SESSION_PGC_ID_LIST_ATTRIBUTE, selectedPgcIdList);
        getServletRequest()
                .getSession().setAttribute(SESSION_CANCER_TRIAL_ATTRIBUTE, cancerTrial);
        getServletRequest()
                .getSession().setAttribute(SESSION_PGC_FAMILY_INDEX_ATTRIBUTE, pgcFamilyIndex);
        if (aFamily != null) {
            getServletRequest()
                    .getSession().setAttribute(SESSION_FAMILY_ID_ATTRIBUTE, aFamily.getId());
        } else {
            getServletRequest()
                    .getSession().removeAttribute(SESSION_FAMILY_ID_ATTRIBUTE);
        }
        boolean isSiteAdmin = getServletRequest().isUserInRole(REQUEST_SITE_ADMIN_ATTRIBUTE);
        getServletRequest()
                .getSession().setAttribute(SESSION_IS_SITE_ADMIN_ATTRIBUTE, isSiteAdmin);

    }

    /**
     * @return String
     */
    public String pickSite() {
        try {
            if (canUpdateMultipleSites()) {
                makeSureUserDidNotManipulateSiteIdInForm();
            }
            prepareProtocolData();
            populateSiteDTOBasedOnOrg(getPickedSiteOrgPoId());
            setSiteDtoInSession();
            prepareProgramCodeData();
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getMessage());
            LOG.error(e, e);
            return ERROR;
        }

    }

    private void makeSureUserDidNotManipulateSiteIdInForm()
            throws NullifiedRoleException, PAException {
        if (CollectionUtils.find(getListOfSitesUserCanUpdate(),
                new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        return StringUtils.equals(
                                ((Organization) o).getIdentifier(),
                                getPickedSiteOrgPoId());

                    }
                }) == null) {
            throw new PAException(
                    "User is not allowed to update the selected site.");
        }
    }

    /**
     * 
     */
    private void setSiteDtoInSession() {
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(TrialUtil.SESSION_TRIAL_SITE_ATTRIBUTE,
                        getSiteDTO());
        setInitialStatusHistory(getSiteDTO().getStatusHistory());
    }

    private boolean canUpdateMultipleSites() throws PAException,
            NullifiedRoleException {
        return StringUtils.isNotBlank(siteDTO.getId())
                && getListOfSitesUserCanUpdate().size() > 1;
    }

    /**
     * @return List<Organization>
     * @throws PAException
     *             PAException
     * @throws NullifiedRoleException
     *             NullifiedRoleException
     */
    public final List<Organization> getListOfSitesUserCanUpdate()
            throws PAException, NullifiedRoleException {
        RegistryUser loggedInUser = getRegistryUser();
        Ii spID = IiConverter.convertToStudyProtocolIi(Long
                .parseLong(getStudyProtocolId()));
        return participatingSiteService.getListOfSitesUserCanUpdate(
                loggedInUser, spID);
    }

    /**
     * @return List<Organization>
     * @throws PAException
     *             PAException
     * @throws NullifiedRoleException
     *             NullifiedRoleException
     */
    public final List<Organization> getListOfSitesUserCanAdd()
            throws PAException, NullifiedRoleException {
        RegistryUser loggedInUser = getRegistryUser();
        Ii spID = IiConverter.convertToStudyProtocolIi(Long
                .parseLong(getStudyProtocolId()));
        return participatingSiteService.getListOfSitesUserCanAdd(loggedInUser,
                spID);
    }

    String populateSiteDTO() throws PAException, NullifiedEntityException {
        String poOrgId = getUserAffiliationPoOrgId();
        setPickedSiteOrgPoId(poOrgId);
        populateSiteDTOBasedOnOrg(poOrgId);
        return poOrgId;
    }

    /**
     * @param poOrgId
     * @throws NumberFormatException
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void populateSiteDTOBasedOnOrg(String poOrgId) throws PAException,
            NullifiedEntityException {
        Ii spID = IiConverter.convertToStudyProtocolIi(Long
                .parseLong(getStudyProtocolId()));
        StudySiteDTO studySiteDTO = participatingSiteService
                .getParticipatingSite(spID, poOrgId);
        siteDTO.setId(null);
        siteDTO.setName(EnOnConverter.convertEnOnToString(organizationService
                .getOrganization(IiConverter.convertToPoOrganizationIi(poOrgId))
                .getName()));
        if (studySiteDTO != null) {
            // Participating site already exists. Preparing for 'update' mode.
            siteDTO = trialUtil.getSubmittedOrganizationDTO(studySiteDTO);
        }
    }

    /**
     * @return UserAffiliationPoOrgId
     * @throws PAException
     *             PAException
     */
    public String getUserAffiliationPoOrgId() throws PAException {
        RegistryUser loggedInUser = getRegistryUser();
        final Long orgId = loggedInUser.getAffiliatedOrganizationId();
        if (orgId == null) {
            throw new PAException(
                    "We are unable to determine your affiliation with an organization. "
                            + "You will not be able to add your site to this trial. Sorry.");
        }
        return orgId.toString();
    }

    /**
     * @return
     * @throws PAException
     */
    private RegistryUser getRegistryUser() throws PAException {
        String loginName = getServletRequest().getRemoteUser();
        return registryUserService.getUser(loginName);
    }

    /**
     * Save or update the participating site.
     * 
     * @return status page if successful, error page otherwise
     */
    public String save() {
        String fwd = ERROR;
        final HttpSession session = getServletRequest()
                .getSession();
        try {
            clearErrorsAndMessages();
            siteDTO.setStatusHistory(getStatusHistoryFromSession());
            new ParticipatingSiteValidator(siteDTO, this, this, paServiceUtil,
                    statusTransitionService).validate();
            if (!(hasActionErrors() || hasFieldErrors())) {
              String poOrgId  = isAddSitesMultiple()   ? getPickedSiteOrgPoId() 
                      : getRegistryUser().getAffiliatedOrganizationId().toString();
                final AddUpdateSiteHelper helper = new AddUpdateSiteHelper(
                        paServiceUtil, siteDTO, participatingSiteService,
                        studyProtocolService, getRegistryUser(),
                        registryUserService, studySiteContactService, this,
                        getStudyProtocolId(), poOrgId);

                //populate the initial program code selection
                List<Long> initialPgcList = (List<Long>) session.getAttribute(SESSION_PGC_ID_LIST_ATTRIBUTE);
                if (CollectionUtils.isNotEmpty(initialPgcList)) {
                    for (Long id : initialPgcList) {
                        helper.addToInitialProgramCodeIds(id);
                    }
                }

                //populate the currently selected list of program codes
                if (StringUtils.isNotEmpty(programCode)) {
                    for (String id : programCode.trim().split("\\s*,\\s*")) {
                        helper.addToFinalProgramCodeIds(Long.parseLong(id));
                    }
                }

                //populate the program code id to family po id mapping
                Map<ProgramCodeDTO, FamilyDTO> map = (Map<ProgramCodeDTO, FamilyDTO>) session
                                            .getAttribute(SESSION_PGC_FAMILY_INDEX_ATTRIBUTE);
                if (map != null) {
                    helper.addAllToFamilyProgramCodeIndex(map);
                }

                if (StringUtils.isNotBlank(siteDTO.getId())) {
                    // User might have deleted some of the existing site status
                    // records.
                    // For an update, this does matter. So we need to merge the
                    // deleted and non-deleted into one collection.
                    final Collection<StatusDto> merged = new ArrayList<StatusDto>();
                    // PO-8771: deletes must go FIRST (TODO: fix this in the
                    // service layer).
                    merged.addAll(getDeletedStatusHistoryFromSession());
                    merged.addAll(siteDTO.getStatusHistory());
                    siteDTO.setStatusHistory(merged);
                    helper.updateSite();
                    session.setAttribute(SUCCESS_MESSAGE_KEY,
                            getText("add.site.updateSuccess"));
                } else {
                    helper.addSite();
                    session.setAttribute(SUCCESS_MESSAGE_KEY,
                            getText("add.site.success"));
                }
                redirectToSummary = true;
                fwd = SUCCESS;
            }
        } catch (Exception e) {
            LOG.error(e, e);
            if (!(hasActionErrors() || hasFieldErrors())) {
                RegistryUtil.setFailureMessage(e);
                addActionError("An internal error has occured.");
            }
        }
        return fwd;
    }

    /**
     * Keeps the user looking at the spinning wheel until a SUCCESS/EXCEPTION
     * occurs.
     * 
     * @return res
     */
    public String showWaitDialog() {
        getServletRequest().setAttribute(
                TrialUtil.SESSION_WAIT_MESSAGE_ATTRIBUTE,
                getText("add.site.wait"));
        return WAIT;
    }

    /**
     * @return the siteDTO
     */
    public SubmittedOrganizationDTO getSiteDTO() {
        return siteDTO;
    }

    /**
     * @param siteDTO
     *            the siteDTO to set
     */
    public void setSiteDTO(SubmittedOrganizationDTO siteDTO) {
        this.siteDTO = siteDTO;
    }

    /**
     * @return the redirectToSummary
     */
    public boolean isRedirectToSummary() {
        return redirectToSummary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        super.prepare();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        registryUserService = PaRegistry.getRegistryUserService();
        participatingSiteService = PaRegistry.getParticipatingSiteService();
        studySiteContactService = PaRegistry.getStudySiteContactService();
        organizationService = PoRegistry.getOrganizationEntityService();
        statusTransitionService = PaRegistry.getStatusTransitionService();
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
    }

    /**
     * @return the studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId
     *            the studyProtocolId to set
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @param paServiceUtil
     *            PAServiceUtils
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }

    /**
     * @param trialUtil
     *            TrialUtil
     */
    public void setTrialUtil(TrialUtil trialUtil) {
        this.trialUtil = trialUtil;
    }

    /**
     * @param registryUserService
     *            RegistryUserServiceLocal
     */
    public void setRegistryUserService(
            RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param participatingSiteService
     *            ParticipatingSiteServiceLocal
     */
    public void setParticipatingSiteService(
            ParticipatingSiteServiceLocal participatingSiteService) {
        this.participatingSiteService = participatingSiteService;
    }

    /**
     * @param studySiteContactService
     *            StudySiteContactServiceLocal
     */
    public void setStudySiteContactService(
            StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @param studyProtocolService
     *            StudyProtocolServiceLocal
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param protocolQueryService
     *            the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @return the pickedSiteOrgPoId
     */
    public String getPickedSiteOrgPoId() {
        return pickedSiteOrgPoId;
    }

    /**
     * @param pickedSiteOrgPoId
     *            the pickedSiteOrgPoId to set
     */
    public void setPickedSiteOrgPoId(String pickedSiteOrgPoId) {
        this.pickedSiteOrgPoId = pickedSiteOrgPoId;
    }

    /**
     * @param organizationService
     *            the organizationService to set
     */
    public void setOrganizationService(
            OrganizationEntityServiceRemote organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * @param statusTransitionService
     *            the statusTransitionService to set
     */
    public void setStatusTransitionService(
            StatusTransitionService statusTransitionService) {
        this.statusTransitionService = statusTransitionService;
    }

    /**
     * Sets familyProgramCodeService
     * @param familyProgramCodeService  the familyProgramCodeService
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }

    /**
     * Will get the program code Ids
     * @return  programCode - a comma separated string of program code ID
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * Set the program code
     * @param programCode - a comma separated string of program code ID
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
}
