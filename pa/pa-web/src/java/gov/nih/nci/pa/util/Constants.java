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
package gov.nih.nci.pa.util;

/**
 *
 * @author gnaveh
 */
public class Constants {
    /**
     * @USERNAME_REQ_ERROR an error message that is fired during login when user not insert userName
    */
    public static final String USERNAME_REQ_ERROR = "User Name is required field";
    /**
     *@PASSWORD_REQ_ERROR an error message that is fired during login when user not insert password
     */
    public static final String PASSWORD_REQ_ERROR = "Password is required field";
    /** logged user name is stored in session using loggerUserName variable . */
    public static final String LOGGED_USER_NAME = "loggedUserName";
    /** Trial Summary . */
    public static final String TRIAL_SUMMARY = "trialSummary";
    /** Study Protocol II. */
    public static final String STUDY_PROTOCOL_II = "studyProtocolIi";
    /** Funding Mechanism code. */
    public static final String FUNDING_MECHANISM = "fundingMechanism";
    /** nih Institute Code . */
    public static final String NIH_INSTITUTE = "nihInstitute";
    /** Country . */
    public static final String ISO_COUNTRY = "isoCounty";
    /** Success Message . */
    public static final String SUCCESS_MESSAGE = "successMessage";
    /** Failure Message . */
    public static final String FAILURE_MESSAGE = "failureMessage";
    /** Deleted Message . */
    public static final String DELETE_MESSAGE = "Record Deleted";
    /** Deleted Message . */
    public static final String NOTHING_TO_DELETE_MESSAGE = "Please select one, or more, record(s) to delete";    
    /** Deleted Message . */
    public static final String MULTI_DELETE_MESSAGE = "Record(s) Deleted";
    /** Updated Message . */
    public static final String UPDATE_MESSAGE = "Record Updated";
    /** Create Message . */
    public static final String CREATE_MESSAGE = "Record Created";
    /** Email Message . */
    public static final String EMAIL_MESSAGE = "Email Sent Successfully";
    /** Participating Organization Tab Data. */
    public static final String PARTICIPATING_ORGANIZATIONS_TAB = "participatingOrganizationsTabs";
    /** Is user in ANY of the abstractor roles.**/
    public static final String IS_ANY_ABSTRACTOR = "isAnyAbstractor";
    /** Is user in the abstractor role.**/
    public static final String IS_ABSTRACTOR = "isAbstractor";
    /** Is user in the su abstractor role.**/
    public static final String IS_SU_ABSTRACTOR = "isSuAbstractor";
    /** Is user in the scientific abstractor role.**/
    public static final String IS_SCIENTIFIC_ABSTRACTOR = "isScientificAbstractor";
    /** Is user in the admin abstractor role.**/
    public static final String IS_ADMIN_ABSTRACTOR = "isAdminAbstractor";
    /** Is user in the report viewer role.**/
    public static final String IS_REPORT_VIEWER = "isReportViewer";
    /** Is user in the SecurityAdmin role.**/
    public static final String IS_SECURITY_ADMIN = "isSecurityAdmin";
    /** CSM Group used to define reporting role. **/
    public static final String REPORT_VIEWER = "ReportViewer";
    /** CSM Group used to define SECURITY_ADMIN role. **/
    public static final String SECURITY_ADMIN = "SecurityAdmin";
    /** CSM Group used to define abstractor role. **/
    public static final String ABSTRACTOR = "Abstractor";
    /** CSM Group used to define suabstractor role. **/
    public static final String SUABSTRACTOR = "SuAbstractor";
    /** CSM Group used to define scientific abstractor role.**/
    public static final String SCIENTIFIC_ABSTRACTOR = "ScientificAbstractor";
    /** CSM Group used to define admin abstractor role.**/
    public static final String ADMIN_ABSTRACTOR = "AdminAbstractor";
    /** CSM Group used to define results abstractor role.**/
    public static final String RESULTS_ABSTRACTOR = "ResultsAbstractor";
    /** CSM Group used to define ProgramCode Administrator role.**/
    public static final String PROGRAMCODE_ADMINISTRATOR = "ProgramCodeAdministrator";
    /** Is user in the results abstractor role.**/
    public static final String IS_RESULTS_ABSTRACTOR = "isResultsAbstractor";
    /** NCI. */
    public static final String NCI = "NCI";
    /** OTHER_IDENTIFIERS_LIST. */
    public static final String OTHER_IDENTIFIERS_LIST = "otherIdentifiersList";
    /** OTHER_IDENTIFIERS_TYPES_LIST. */
    public static final String OTHER_IDENTIFIERS_TYPES_LIST = "otherIdentifiersTypesList";
    /** Trial Submitter Organization.  */
    public static final String TRIAL_SUBMITTER_ORG = "trialSubmitterOrg";
    /** Trial Submitter Organization.  */
    public static final String TRIAL_SUBMITTER_ORG_PO_ID = "trialSubmitterOrgPOId";
    /** Principal Investigator PO_ID  */
    public static final String PI_PO_ID = "principalInvestigatorPOId";
    /** STUDY_ALTERNATE_TITLES_LIST. */
    public static final String STUDY_ALTERNATE_TITLES_LIST = "studyAlternateTitlesList";
    /** STUDY ALTERNATE TITLES. */
    public static final String STUDY_ALTERNATE_TITLES = "studyAlternateTitles";
    /**
     * STUDY_IDENTIFIERS
     */
    public static final String STUDY_IDENTIFIERS = "studyIdentifiers";
    /**
     * STUDY_IDENTIFIERS_GROUPED_BY_TYPE
     */
    public static final String STUDY_IDENTIFIERS_GROUPED_BY_TYPE = "studyIdentifiersByType";
    /**
     * TRIAL_HAS_STATUS_ERRORS
     */
    public static final String TRIAL_HAS_STATUS_ERRORS = "trialHasStatusErrors";
    /**
     * TRIAL_HAS_STATUS_WARNINGS
     */
    public static final String TRIAL_HAS_STATUS_WARNINGS = "trialHasStatusWarnings";
}
