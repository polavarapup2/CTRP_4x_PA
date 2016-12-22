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

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Harsha
 *
 */
public class InboxProcessingAction extends ActionSupport implements ServletResponseAware {
    private static final long serialVersionUID = -2308994602660261367L;
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    private Long studyProtocolId = null;
    private HttpServletResponse servletResponse;
    private List<RegistryUser> pendingAdminUsers = null;

    /**
     * @return res
     * @throws PAException exception
     */
    @Override
    public String execute() throws PAException {
        if (!userRoleInSession()) {
            return showCriteria();
        }
        getPendingAdminUserRole();
        return SUCCESS;
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String showCriteria() throws PAException {
        boolean isAbstractor = ServletActionContext.getRequest().isUserInRole(Constants.ABSTRACTOR);
        boolean isSuAbstractor = ServletActionContext.getRequest().isUserInRole(Constants.SUABSTRACTOR);
        boolean isScientificAbstractor =
            ServletActionContext.getRequest().isUserInRole(Constants.SCIENTIFIC_ABSTRACTOR);
        boolean isAdminAbstractor = ServletActionContext.getRequest().isUserInRole(Constants.ADMIN_ABSTRACTOR);
        boolean isReportViewer = ServletActionContext.getRequest().isUserInRole(Constants.REPORT_VIEWER);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.IS_ABSTRACTOR, isAbstractor);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.IS_SU_ABSTRACTOR, isSuAbstractor);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.IS_ADMIN_ABSTRACTOR, isAdminAbstractor);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR,
                isScientificAbstractor);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.IS_REPORT_VIEWER, isReportViewer);
        if (isAbstractor || isSuAbstractor || isScientificAbstractor || isAdminAbstractor) {
            return "criteriaProtected";
        } else if (ServletActionContext.getRequest().isUserInRole(Constants.REPORT_VIEWER)) {
            return "criteriaReport";
        }
        throw new PAException("User configured improperly.  Use UPT to assign user to a valid group "
                + "for this application.");
    }

    /**
     *
     * @return StudyProtocolQueryCriteria StudyProtocolQueryCriteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        return criteria;
    }

    /**
     *
     * @param criteria
     *            StudyProtocolQueryCriteria
     */
    public void setCriteria(StudyProtocolQueryCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     *
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     *
     * @param studyProtocolId
     *            studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String view() throws PAException {
        if (!userRoleInSession()) {
            return showCriteria();
        }
        try {
            StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry
                    .getProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(studyProtocolId);
            // put an entry in the session and store StudyProtocolQueryDTO
            studyProtocolQueryDTO.setOfficialTitle(StringUtils.abbreviate(studyProtocolQueryDTO.getOfficialTitle(),
                    PAAttributeMaxLen.DISPLAY_OFFICIAL_TITLE));
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            session.setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToStudyProtocolIi(studyProtocolId));
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    /**
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * @param response
     *            servletResponse
     */
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    private boolean userRoleInSession() {
        boolean isSuAbstractor = BooleanUtils.toBoolean(
                (Boolean) ServletActionContext.getRequest().getSession().getAttribute(Constants.IS_SU_ABSTRACTOR));
        boolean isAbstractor = BooleanUtils.toBoolean(
                (Boolean) ServletActionContext.getRequest().getSession().getAttribute(Constants.IS_ABSTRACTOR));
        boolean isAdminAbstractor = BooleanUtils.toBoolean(
                (Boolean) ServletActionContext.getRequest().getSession().getAttribute(Constants.IS_ADMIN_ABSTRACTOR));
        boolean isScientificAbstractor = BooleanUtils.toBoolean((Boolean)
                ServletActionContext.getRequest().getSession().getAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR));
        boolean isReportViewer = BooleanUtils.toBoolean(
                (Boolean) ServletActionContext.getRequest().getSession().getAttribute(Constants.IS_REPORT_VIEWER));
        return isAbstractor || isSuAbstractor || isScientificAbstractor || isAdminAbstractor || isReportViewer;
    }

    /**
     *@return success
     */
    public String getPendingAdminUserRole() {
        try {
            pendingAdminUsers = PaRegistry.getRegistryUserService().getUserByUserOrgType(UserOrgType.PENDING_ADMIN);
        } catch (PAException e) {
            LOG.error("Exception while getting pending admin request:-" + e.getMessage());
        }
        return SUCCESS;
    }

    /**
     * @param pendingAdminUser the pendingAdminUser to set
     */
    public void setPendingAdminUsers(List<RegistryUser> pendingAdminUser) {
        this.pendingAdminUsers = pendingAdminUser;
    }

    /**
     * @return the pendingAdminUser
     */
    public List<RegistryUser> getPendingAdminUsers() {
        return pendingAdminUsers;
    }
    /**
     *
     * @return view
     */
    public String viewPendingUserAdmin() {
        String userId = ServletActionContext.getRequest().getParameter("id");
        if (StringUtils.isEmpty(userId)) {
            return SUCCESS;
        }
        try {
            RegistryUser pendingUsr = PaRegistry.getRegistryUserService().getUserById(Long.parseLong(userId));
            ServletActionContext.getRequest().setAttribute("user", pendingUsr);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "viewpendingUserAdmin";
    }
    /**
     *
     * @return success
     */
    public String processUserRole() {
        String abstractorResponse = ServletActionContext.getRequest().getParameter("action");
        String userId = ServletActionContext.getRequest().getParameter("id");
        if (StringUtils.isNotEmpty(abstractorResponse) && StringUtils.isNotEmpty(userId)) {
            try {
                if (abstractorResponse.equalsIgnoreCase("accept")) {
                    updateUserRole(UserOrgType.ADMIN, userId, "");
                } else {
                    updateUserRole(UserOrgType.MEMBER, userId, ServletActionContext.getRequest().getParameter(
                            "rejectReason"));
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            }
        }
     return getPendingAdminUserRole();
    }

    /**
     * @param userOrgType
     * @param userId
     * @throws PAException
     */
    private void updateUserRole(UserOrgType affiliatedOrgUserType,
            String userId, String rejectReason) throws PAException {

        PaRegistry.getRegistryUserService().changeUserOrgType(
                Long.parseLong(userId), affiliatedOrgUserType, rejectReason);
        ServletActionContext.getRequest().setAttribute(
                Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
    }
}
