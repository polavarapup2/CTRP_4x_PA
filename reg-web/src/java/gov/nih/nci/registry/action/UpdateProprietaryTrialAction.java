/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.CodedEnumHelper;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialSessionUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Preparable;

/**
 * The Class UpdateProprietaryTrialAction.
 *
 * @author Kalpana Guthikonda
 * @since May 18 2010
 */
public class UpdateProprietaryTrialAction extends AbstractBaseProprietaryTrialAction implements Preparable {

    private static final Logger LOG = Logger.getLogger(UpdateProprietaryTrialAction.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
    
    private static final String STATUS_CHANGE_ERR_MSG = "You are attempting to change this status from: "
            + "<br>Old Status: %1s <br>Old Status Date: %2s <br><strong>Error</strong>: %3s";

    private static final long serialVersionUID = 1L;
    private TrialUtil  util = new TrialUtil();
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    private String currentUser;
    private final List<TrialDocumentWebDTO> existingDocuments = new ArrayList<TrialDocumentWebDTO>();

    /**
     * View.
     * @return res
     */
    public String view() {
        TrialSessionUtil.removeSessionAttributes();
        try {
            String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(pId));
            setTrialDTO(new ProprietaryTrialDTO());
            util.getProprietaryTrialDTOFromDb(studyProtocolIi, getTrialDTO());
            setCurrentTrialDocumentsInSession();
            ServletActionContext
                    .getRequest()
                    .getSession()
                    .setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE,
                            getTrialDTO());
            ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(Constants.PARTICIPATING_SITES_LIST,
                        getTrialDTO().getParticipatingSitesList());
            setPageFrom("updateProprietaryTrial");
            LOG.debug("Trial retrieved: " + getTrialDTO().getOfficialTitle());
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Review.
     * @return st
     */
    public String review() {
        clearErrorsAndMessages();
        try {
            updateEditableFields();
            enforceBusinessRules();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                return ERROR;
            }
            if (hasActionErrors()) {
                return ERROR;
            }
            getTrialDTO().setDocDtos(getTrialDocuments());              
        } catch (Exception e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        final HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute(
                DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName());
        session.removeAttribute(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getShortName());
        session.removeAttribute(DocumentTypeCode.PARTICIPATING_SITES.getShortName());
        session.removeAttribute(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getShortName());
        session.removeAttribute(DocumentTypeCode.OTHER.getShortName());
        session.setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, getTrialDTO());
        return "review";
    }

    private void updateEditableFields() throws NullifiedRoleException,
            PAException {
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long
                .valueOf(getTrialDTO().getStudyProtocolId()));
        ProprietaryTrialDTO currentDTO = new ProprietaryTrialDTO();
        util.getProprietaryTrialDTOFromDb(studyProtocolIi, currentDTO);
        currentDTO.setParticipatingSitesList(getTrialDTO()
                .getParticipatingSitesList());
        currentDTO.setNctIdentifier(getTrialDTO().getNctIdentifier());
        setTrialDTO(currentDTO);
        ServletActionContext.getRequest().getSession()
                .setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, currentDTO);
    }

    /**
     * Cancel.
     * @return s
     */
    public String cancel() {
        TrialSessionUtil.removeSessionAttributes();
        return "redirect_to_search";
    }

    /**
     * Edits the.
     * @return s
     */
    public String edit() {
        setTrialDTO((ProprietaryTrialDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE));
        setCurrentTrialDocumentsInSession();
        try {
            util.copyParticipatingSites(IiConverter.convertToStudyProtocolIi(
                    Long.parseLong(getTrialDTO().getIdentifier())), getTrialDTO());
        } catch (Exception e) {
            LOG.error("Exception occured while calling edit() " + e);
            return ERROR;
        }
        return "edit";
    }


    /**
     * Update.
     * @return the string
     */
    public String update() {
        setTrialDTO((ProprietaryTrialDTO) ServletActionContext.getRequest().getSession().
            getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE));
        final ProprietaryTrialDTO trialDTO = getTrialDTO();
        if (trialDTO == null) {
            return ERROR;
        }
        try {
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
            StudyProtocolDTO studyProtocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
            if (studyProtocolDTO instanceof NonInterventionalStudyProtocolDTO) {
                util.convertToNonInterventionalStudyProtocolDTO(trialDTO,
                        (NonInterventionalStudyProtocolDTO) studyProtocolDTO);
            } else {
                util.convertToStudyProtocolDTO(trialDTO, studyProtocolDTO);
            }           
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(currentUser));
            OrganizationDTO leadOrganizationDTO = util.convertToLeadOrgDTO(trialDTO);
            St leadOrganizationIdentifier = StConverter.convertToSt(trialDTO.getLeadOrgTrialIdentifier());
            St nctIdentifier = StConverter.convertToSt(trialDTO.getNctIdentifier());
            Cd summary4TypeCode = CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode());
            //OrganizationDTO summary4organizationDTO = util.convertToSummary4OrgDTO(trialDTO);
            List<StudySiteAccrualStatusDTO> siteAccrualStatusDTOList = getParticipatingSitesForUpdate(
                    trialDTO.getParticipatingSitesList());
            List<StudySiteDTO> siteDTOList = util.getStudySiteToUpdate(trialDTO.getParticipatingSitesList());
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());

           PaRegistry.getProprietaryTrialService().update(studyProtocolDTO, leadOrganizationDTO,
                   null, leadOrganizationIdentifier,
                    nctIdentifier, summary4TypeCode, documentDTOs, siteDTOList, siteAccrualStatusDTOList);
            StudyProtocolDTO protocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
            TrialSessionUtil.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("protocolId",
                    protocolDTO.getIdentifier().getExtension());
        } catch (PAException e) {
            LOG.error(e);
            setCurrentTrialDocumentsInSession();
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        }
        setTrialAction("update");
        return "redirect_to_search";
    }

    /**
     * Enforce business rules.
     */
    private void enforceBusinessRules() throws IOException {                
        validateDocuments();
        validateProtocolDocUpdate();        
        checkSubmittingOrgRules();
    }

    @SuppressWarnings("unchecked")
    private void checkSubmittingOrgRules() {        
        List<SubmittedOrganizationDTO> sessionPartList = (List<SubmittedOrganizationDTO>) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_SITES_LIST);
        List<SubmittedOrganizationDTO> currPartList = getTrialDTO().getParticipatingSitesList();
        if (CollectionUtils.isEmpty(currPartList)) {
            return;
        }
        for (int i = 0; i < currPartList.size(); i++)  {
            SubmittedOrganizationDTO currps = currPartList.get(i);
            SubmittedOrganizationDTO prevps = sessionPartList.get(i);
            
            if (StringUtils.isEmpty(currps.getSiteLocalTrialIdentifier())) {
                addFieldError("participatingsite.localTrialId" + i, 
                        "For " + currps.getName() 
                        + " Organization cannot have a null Local Trial Identifier ");
            }
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = convertToStudySiteAccrualStatusDTO(currps);
            StudySiteDTO studySiteDTO = getSubmittingStudySiteDTO(currps);
            String errMsg = paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO);
            if (StringUtils.isNotEmpty(errMsg)) {
                addActionError(errMsg);
            }
            
            if (StringUtils.equals(currps.getRecruitmentStatus(), prevps.getRecruitmentStatus())
                    && StringUtils.equals(currps.getRecruitmentStatusDate(), prevps.getRecruitmentStatusDate())) {
                continue;
            }
            
            Date prevDt, currDt = null;
            
            try {
                prevDt = SDF.parse(prevps.getRecruitmentStatusDate());
                currDt = SDF.parse(currps.getRecruitmentStatusDate());
            } catch (ParseException e) {
                addFieldError("participatingsite.recStatusDate" + i, 
                        "Error parsing the participating site recruitment status dates, " + e.getMessage());
                continue;
            }
            
            if (currDt.before(prevDt)) {
                String err = String.format(STATUS_CHANGE_ERR_MSG, 
                        new Object[] {prevps .getRecruitmentStatus(), prevps.getRecruitmentStatusDate(),
                        " New status date must be greater or equal to the most recent status date"});
                addFieldError("participatingsite.recStatus" + i, err);
                continue;
            } else {
                List<StatusDto> statusDtos;
                try {
                    statusDtos = getStatusTransitionService().validateStatusTransition(
                            AppName.REGISTRATION, TrialType.ABBREVIATED, TransitionFor.SITE_STATUS, 
                            CodedEnumHelper.getByClassAndCode(RecruitmentStatusCode.class, 
                                    prevps.getRecruitmentStatus()).name(),
                            prevDt, 
                            CodedEnumHelper.getByClassAndCode(RecruitmentStatusCode.class, 
                                    currps.getRecruitmentStatus()).name(),
                            currDt);
                } catch (PAException e) {
                    addFieldError("participatingsite.recStatus" + i, 
                            "Error validating participating site recruitment status transition, " + e.getMessage());
                    continue;
                }
                if (statusDtos.get(0).hasErrorOfType(ErrorType.ERROR)) {
                    String err = String.format(STATUS_CHANGE_ERR_MSG, 
                            new Object[] {prevps .getRecruitmentStatus(), prevps.getRecruitmentStatusDate(), 
                            statusDtos.get(0).getConsolidatedErrorMessage()});
                    addFieldError("participatingsite.recStatus" + i, err);
                }
            }
            
        }
    }

    /**
     * Gets the submitting study site dto.
     * @return the submitting study site dto
     */
    private StudySiteDTO getSubmittingStudySiteDTO(SubmittedOrganizationDTO dto) {
        StudySiteDTO siteDTO = new StudySiteDTO();
        siteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(dto.getSiteLocalTrialIdentifier()));
        siteDTO.setProgramCodeText(StConverter.convertToSt(dto.getProgramCode()));
        if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                && StringUtils.isNotEmpty(dto.getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(),
                    dto.getDateClosedforAccrual()));
        }
        if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                && StringUtils.isEmpty(dto.getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(),
                    null));
        }
        return siteDTO;
    }

    /**
     * Convert to study site accrual status dto.
     * @param trialDto the trial dto
     * @return the study site accrual status dto
     */
    private StudySiteAccrualStatusDTO convertToStudySiteAccrualStatusDTO(SubmittedOrganizationDTO dto) {
        StudySiteAccrualStatusDTO isoDto = new StudySiteAccrualStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(dto
                .getRecruitmentStatus())));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(dto
                .getRecruitmentStatusDate())));
        return isoDto;
    }

    private void setCurrentTrialDocumentsInSession() {
        List<DocumentDTO> documentISOList;
        try {
            documentISOList = PaRegistry.getDocumentService().getDocumentsByStudyProtocol(
                  IiConverter.convertToIi(getTrialDTO().getIdentifier()));
            if (!(documentISOList.isEmpty())) {
                TrialDocumentWebDTO webDto = null;
                for (DocumentDTO docDTO : documentISOList) {
                     webDto = new TrialDocumentWebDTO(docDTO);
                     addDocumentToSession(webDto);   
                     existingDocuments.add(webDto);
                }
            }
        } catch (PAException e) {
            LOG.error("exception while setting Document in session", e);
        }
    }

    private List<StudySiteAccrualStatusDTO> getParticipatingSitesForUpdate(List<SubmittedOrganizationDTO> ps)
    throws PAException {
        List<StudySiteAccrualStatusDTO> ssaDTO = new ArrayList<StudySiteAccrualStatusDTO>();
        for (SubmittedOrganizationDTO dto : ps) {
            StudySiteAccrualStatusDTO ssasOld = PaRegistry.getStudySiteAccrualStatusService()
             .getCurrentStudySiteAccrualStatusByStudySite(IiConverter.convertToIi(dto.getId()));
            StudySiteAccrualStatusDTO ssas =  new StudySiteAccrualStatusDTO();
            ssas.setStudySiteIi(ssasOld.getStudySiteIi());
            ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(dto.getRecruitmentStatus())));
            ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(dto.getRecruitmentStatusDate())));
            ssaDTO.add(ssas);
        }
        return ssaDTO;
    }

    @Override
    public void prepare() {
        super.prepare();
        currentUser = UsernameHolder.getUser();
    }    
    
    /**
     * @return the existingDocuments
     */
    public List<TrialDocumentWebDTO> getExistingDocuments() {
        return existingDocuments;
    }

    /**
     * @param util the util to set
     */
    public void setUtil(TrialUtil util) {
        this.util = util;
    }
    
    /**
     * @param paServiceUtils paServiceUtils
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }
    
}
