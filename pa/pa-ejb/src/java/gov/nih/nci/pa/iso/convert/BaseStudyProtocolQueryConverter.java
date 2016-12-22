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
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert protocol query objects into dto.
 * @author mshestopalov
 *
 */
public class BaseStudyProtocolQueryConverter {


    /**
     * List of document workflow statuses for which TSR reports can't be
     * generated.
     */
    public static final List<DocumentWorkflowStatusCode> NON_TSR_DWF;
    static {
        NON_TSR_DWF = new ArrayList<DocumentWorkflowStatusCode>();
        NON_TSR_DWF.add(DocumentWorkflowStatusCode.SUBMITTED);
        NON_TSR_DWF.add(DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED);
        NON_TSR_DWF.add(DocumentWorkflowStatusCode.REJECTED);
    }

    private final RegistryUserServiceLocal registryUserService;
    private final PAServiceUtils paServiceUtils;

    /**
     * StudyProtocolQueryConverter.
     * @param registryUserSvc RegistryUserService
     * @param paSvcUtils pa service utils.
     */
    public BaseStudyProtocolQueryConverter(RegistryUserServiceLocal registryUserSvc,
            PAServiceUtils paSvcUtils) {
        registryUserService = registryUserSvc;
        paServiceUtils = paSvcUtils;
    }

    /**
     * Check that user has access to trial.
     * @param potentialOwner possible trial owner
     * @param studyProtocolDto dto
     * @param studyProtocolId domain obhect id for trial.
     * @param myTrialsOnly whether only trials owned by user should be
     * brought back.
     * @return boolean.
     * @throws PAException on error.
     */
    protected boolean userHasAccess(RegistryUser potentialOwner, StudyProtocolQueryDTO studyProtocolDto,
            Long studyProtocolId, boolean myTrialsOnly) throws PAException {
        if (potentialOwner != null) {
            studyProtocolDto.setSearcherTrialOwner(getRegistryUserService().hasTrialAccess(potentialOwner,
                    studyProtocolId));
        }
        return (myTrialsOnly && studyProtocolDto.isSearcherTrialOwner()) || !myTrialsOnly;

    }

    /**
     * Set view TSR.
     * @param studyProtocolDto trial dto
     * @param documentWorkflowStatusCode process status code.
     */
    protected void setViewTSR(StudyProtocolQueryDTO studyProtocolDto,
                DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        studyProtocolDto.setViewTSR(!NON_TSR_DWF.contains(documentWorkflowStatusCode));
    }



    /**
     * Private class for passing long param lists.
     * @author mshestopalov
     *
     */
    protected class SubmissionTypeVars {
        private boolean inboxExists;
        private Timestamp closedDate;
        private String amendmentNumber;
        private Timestamp amendmentDate;
        private Integer submissionNumber;
        /**
         * @param inboxExists the inboxExists to set
         */
        public void setInboxExists(boolean inboxExists) {
            this.inboxExists = inboxExists;
        }
        /**
         * @return the inboxExists
         */
        public boolean isInboxExists() {
            return inboxExists;
        }
        /**
         * @param closedDate the closedDate to set
         */
        public void setClosedDate(Timestamp closedDate) {
            this.closedDate = closedDate;
        }
        /**
         * @return the closedDate
         */
        public Timestamp getClosedDate() {
            return closedDate;
        }
        /**
         * @param amendmentNumber the amendmentNumber to set
         */
        public void setAmendmentNumber(String amendmentNumber) {
            this.amendmentNumber = amendmentNumber;
        }
        /**
         * @return the amendmentNumber
         */
        public String getAmendmentNumber() {
            return amendmentNumber;
        }
        /**
         * @param amendmentDate the amendmentDate to set
         */
        public void setAmendmentDate(Timestamp amendmentDate) {
            this.amendmentDate = amendmentDate;
        }
        /**
         * @return the amendmentDate
         */
        public Timestamp getAmendmentDate() {
            return amendmentDate;
        }
        /**
         * @param submissionNumber the submissionNumber to set
         */
        public void setSubmissionNumber(Integer submissionNumber) {
            this.submissionNumber = submissionNumber;
        }
        /**
         * @return the submissionNumber
         */
        public Integer getSubmissionNumber() {
            return submissionNumber;
        }
    }

    /**
     * Set Submission Type.
     * @param spDto trial dto
     * @param subVars submission variables.
     */
    protected void setSubmissionType(StudyProtocolQueryDTO spDto, SubmissionTypeVars subVars) {
        if (isUpdated(subVars.isInboxExists(), subVars.getClosedDate())) {
            //Studies are considered updated if they have a study inbox entry without a closed date
            spDto.setSubmissionTypeCode(SubmissionTypeCode.U);
        } else if (isAmended(subVars.getSubmissionNumber())) {
            // return amendment number and date only for amended trials
            spDto.setAmendmentNumber(subVars.getAmendmentNumber());
            spDto.setAmendmentDate(subVars.getAmendmentDate());
            spDto.setSubmissionTypeCode(SubmissionTypeCode.A);
        } else if (isOriginal(subVars.getSubmissionNumber())) {
            spDto.setSubmissionTypeCode(SubmissionTypeCode.O);
        }
    }

    private boolean isUpdated(boolean inboxExists, Timestamp closedDate) {
        return inboxExists && closedDate == null;
    }

    private boolean isAmended(Integer subNumber) {
        return subNumber != null &&  subNumber.intValue() > 1;
    }

    private boolean isOriginal(Integer subNumber) {
        return subNumber != null &&  subNumber.intValue() == 1;
    }

    /**
     * @return the registryUserService
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }


    /**
     * @return the paServiceUtils
     */
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }



}
