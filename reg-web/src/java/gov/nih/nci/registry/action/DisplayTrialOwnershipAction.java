/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class for displaying the trial ownership information.
 * @author kkanchinadam
 *
 */
public class DisplayTrialOwnershipAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private List<DisplayTrialOwnershipInformation> trialOwnershipInfo =
            new ArrayList<DisplayTrialOwnershipInformation>();
    private DisplayTrialOwnershipInformation criteria = new DisplayTrialOwnershipInformation();
    private static final String TRIAL_INFO = "trialInfoList";
    private static final String VIEW_RESULTS = "viewResults";
    private static final String SUCCESS_MSG = "successMessage";
    private static final String FAILURE_MSG = "failureMessage";
    private Long userId;
    private Long trialId;
    private boolean enableEmails;

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the trialId
     */
    public Long getTrialId() {
        return trialId;
    }

    /**
     * @param trialId the trialId to set
     */
    public void setTrialId(Long trialId) {
        this.trialId = trialId;
    }

    /**
     * load initial view.
     * @return String the action result.
     * @throws PAException the pa exception.
     */
    public String search() throws PAException {
        performSearch();
        return VIEW_RESULTS;
    }
    
    /**
     * saveEmailPreference.
     * 
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    public void saveEmailPreference() throws PAException {
        PaRegistry.getRegistryUserService().setEmailNotificationsPreference(
                userId, trialId, enableEmails);
        List<DisplayTrialOwnershipInformation> list = (List<DisplayTrialOwnershipInformation>) ServletActionContext
                .getRequest().getSession()
                .getAttribute(DisplayTrialOwnershipAction.TRIAL_INFO);
        if (list != null) {
            for (DisplayTrialOwnershipInformation webDTO : list) {
                if (userId.toString().equals(webDTO.getUserId())
                        && trialId.toString().equals(webDTO.getTrialId())) {
                    webDTO.setEmailsEnabled(enableEmails);
                }
            }
        }
    }

    /**
     * view results from session..
     * @return String the action result.
     */
    @SuppressWarnings("unchecked")
    public String view() {
        trialOwnershipInfo =
                (List<DisplayTrialOwnershipInformation>) ServletActionContext.getRequest().getSession().getAttribute(
                        DisplayTrialOwnershipAction.TRIAL_INFO);
        return VIEW_RESULTS;
    }

    /**
     * remove ownership.
     * @return String the action result.
     * @throws PAException the exception.
     */
    public String removeOwnership() throws PAException {
        try {
            PaRegistry.getRegistryUserService().removeOwnership(userId, trialId);
            ServletActionContext.getRequest().setAttribute(SUCCESS_MSG, "Trial ownership successfully removed");
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG, e.getMessage());
            throw new PAException(e);
        }
        return search();
    }

    private void performSearch() throws PAException {
        try {
            ServletActionContext.getRequest().getSession().removeAttribute(DisplayTrialOwnershipAction.TRIAL_INFO);
            String loginName = ServletActionContext.getRequest().getRemoteUser();
            RegistryUser loggedInUser = PaRegistry.getRegistryUserService().getUser(loginName);
            trialOwnershipInfo =
                    PaRegistry.getRegistryUserService().searchTrialOwnership(criteria,
                            FamilyHelper.getAllRelatedOrgs(loggedInUser.getAffiliatedOrganizationId()));
            ServletActionContext.getRequest().getSession().setAttribute(DisplayTrialOwnershipAction.TRIAL_INFO,
                    trialOwnershipInfo);
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * @return the trialOwnershipInfo
     */
    public List<DisplayTrialOwnershipInformation> getTrialOwnershipInfo() {
        return trialOwnershipInfo;
    }

    /**
     * @param trialOwnershipInfo the trialOwnershipInfo to set
     */
    public void setTrialOwnershipInfo(List<DisplayTrialOwnershipInformation> trialOwnershipInfo) {
        this.trialOwnershipInfo = trialOwnershipInfo;
    }

    /**
     * @return the criteria
     */
    public DisplayTrialOwnershipInformation getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(DisplayTrialOwnershipInformation criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the enableEmails
     */
    public boolean isEnableEmails() {
        return enableEmails;
    }

    /**
     * @param enableEmails the enableEmails to set
     */
    public void setEnableEmails(boolean enableEmails) {
        this.enableEmails = enableEmails;
    }

}
