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
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugh Reinhart
 * @since 01/14/2009
 */
public enum MilestoneCode implements CodedEnum<String> {

    /** Submission Received. */
    SUBMISSION_RECEIVED("Submission Received Date", true, false, true, true),
    /** Submission Accepted. */
    SUBMISSION_ACCEPTED("Submission Acceptance Date", true, false, false, true),
    /** Submission Rejected. */
    SUBMISSION_REJECTED("Submission Rejection Date", true, false, false, true),
    
    /**
     * Submission Terminated Date.
     */
    SUBMISSION_TERMINATED("Submission Terminated Date", false, false, false, true),
    /**
     * Submission Reactivated Date.
     */
    SUBMISSION_REACTIVATED("Submission Reactivated Date", false, false, false, true),
    
    /** Administrative processing start. */
    ADMINISTRATIVE_PROCESSING_START_DATE("Administrative Processing Start Date", false, false, true, true),
    /** Administrative processing completed. */
    ADMINISTRATIVE_PROCESSING_COMPLETED_DATE("Administrative Processing Completed Date", false, false, true, true),
    /** Ready for Administrative QC Date. */
    ADMINISTRATIVE_READY_FOR_QC("Ready for Administrative QC Date", false, false, true, true),
    /** Administrative QC Start. */
    ADMINISTRATIVE_QC_START("Administrative QC Start Date", false, false, true, true),
    /** Administrative QC Completed. */
    ADMINISTRATIVE_QC_COMPLETE("Administrative QC Completed Date", false, false, false, false),
    /** Scientific processing start. */
    SCIENTIFIC_PROCESSING_START_DATE("Scientific Processing Start Date", false, false, true, true),
    /** Scientific processing completed. */
    SCIENTIFIC_PROCESSING_COMPLETED_DATE("Scientific Processing Completed Date", false, false, true, true),
    /** Ready for Scientific QC Date. */
    SCIENTIFIC_READY_FOR_QC("Ready for Scientific QC Date", false, false, true, true),
    /** Scientific QC Start. */
    SCIENTIFIC_QC_START("Scientific QC Start Date", false, false, true, true),
    /** Scientific QC Completed. */
    SCIENTIFIC_QC_COMPLETE("Scientific QC Completed Date", false, false, false, false),
    /** Ready for Trial Summary Report. */
    READY_FOR_TSR("Ready for Trial Summary Report Date", false, true, false, false),
    /** Trial Summary Report Sent. */
    TRIAL_SUMMARY_REPORT("Trial Summary Report Date", false, true, false, false),
    /** Submitter Trial Summary Report Feedback. */
    TRIAL_SUMMARY_FEEDBACK("Submitter Trial Summary Report Feedback Date", false, false, false, false),
    /** Initial Abstraction Verified. */
    INITIAL_ABSTRACTION_VERIFY("Initial Abstraction Verified Date", true, true, false, false),
    /** On-going Abstraction Verified. */
    ONGOING_ABSTRACTION_VERIFICATION("On-going Abstraction Verified Date", false, true, false, false),
    /** Late Rejection. */
    LATE_REJECTION_DATE("Late Rejection Date", true, false, false, false);

    private String code;
    private boolean unique;
    private boolean validationTrigger;
    private boolean allowedIfOnhold;
    private boolean allowedInInBox;
    private static final Map<MilestoneCode, Set<DocumentWorkflowStatusCode>> ALLOWED_DWF_STATUSES;
    /**
     * TRS_AND_ABOVE.
     */
    public static final Set<MilestoneCode> TRS_AND_ABOVE = EnumSet.of(MilestoneCode.TRIAL_SUMMARY_REPORT,
                                                                       MilestoneCode.TRIAL_SUMMARY_FEEDBACK,
                                                                       MilestoneCode.INITIAL_ABSTRACTION_VERIFY,
                                                                       MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION);
    /** Sequence of administrative milestones. */                                                                   
    public static final List<MilestoneCode> ADMIN_SEQ = Arrays
        .asList(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE,
                MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, 
                MilestoneCode.ADMINISTRATIVE_READY_FOR_QC,
                MilestoneCode.ADMINISTRATIVE_QC_START, 
                MilestoneCode.ADMINISTRATIVE_QC_COMPLETE);
    /** Sequence of scientific milestones. */  
    public static final List<MilestoneCode> SCIENTIFIC_SEQ = Arrays
        .asList(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, 
                MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE,
                MilestoneCode.SCIENTIFIC_READY_FOR_QC, 
                MilestoneCode.SCIENTIFIC_QC_START,
                MilestoneCode.SCIENTIFIC_QC_COMPLETE);
    
    /**
     * List of milestones indicating Trial Ready for Scientific Processing.
     * Trial Ready for Scientific Processing = trial that does not have a
     * 'Scientific Processing Start' milestone AND its most recent 'Active
     * milestone' * is any of the following: ('Submission Acceptance',
     * 'Administrative Processing Start', 'Administrative Processing Completed',
     * 'Ready for Administrative QC', 'Administrative QC Start', 'Administrative
     * QC Completed')
     */
    public static final List<MilestoneCode> READY_FOR_SCIENTIFIC_PROCESSING_LIST = Arrays
            .asList(MilestoneCode.SUBMISSION_ACCEPTED,
                    MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE,
                    MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                    MilestoneCode.ADMINISTRATIVE_READY_FOR_QC,
                    MilestoneCode.ADMINISTRATIVE_QC_COMPLETE,
                    MilestoneCode.ADMINISTRATIVE_QC_START);
    static {
        Map<MilestoneCode, Set<DocumentWorkflowStatusCode>> tmp = 
            new HashMap<MilestoneCode, Set<DocumentWorkflowStatusCode>>();

        Set<DocumentWorkflowStatusCode> tmpSet = EnumSet.of(DocumentWorkflowStatusCode.SUBMITTED);
        tmp.put(SUBMISSION_RECEIVED, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.ACCEPTED);
        tmp.put(SUBMISSION_ACCEPTED, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.REJECTED);
        tmp.put(SUBMISSION_REJECTED, Collections.unmodifiableSet(tmpSet));
        
        tmpSet = EnumSet.complementOf(EnumSet.of(
                DocumentWorkflowStatusCode.REJECTED,
                DocumentWorkflowStatusCode.SUBMISSION_TERMINATED));
        tmp.put(SUBMISSION_TERMINATED, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.SUBMISSION_TERMINATED);
        tmp.put(SUBMISSION_REACTIVATED, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.ACCEPTED, DocumentWorkflowStatusCode.ABSTRACTED,
                            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE,
                            DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        Set<DocumentWorkflowStatusCode> inProcessOrQCStatuses = Collections.unmodifiableSet(tmpSet);
        tmp.put(ADMINISTRATIVE_PROCESSING_START_DATE, inProcessOrQCStatuses);
        tmp.put(ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, inProcessOrQCStatuses);
        tmp.put(ADMINISTRATIVE_READY_FOR_QC, inProcessOrQCStatuses);
        tmp.put(ADMINISTRATIVE_QC_START, inProcessOrQCStatuses);
        tmp.put(ADMINISTRATIVE_QC_COMPLETE, inProcessOrQCStatuses);
        tmp.put(SCIENTIFIC_PROCESSING_START_DATE, inProcessOrQCStatuses);
        tmp.put(SCIENTIFIC_PROCESSING_COMPLETED_DATE, inProcessOrQCStatuses);
        tmp.put(SCIENTIFIC_READY_FOR_QC, inProcessOrQCStatuses);
        tmp.put(SCIENTIFIC_QC_START, inProcessOrQCStatuses);
        tmp.put(SCIENTIFIC_QC_COMPLETE, inProcessOrQCStatuses);
        tmp.put(READY_FOR_TSR, inProcessOrQCStatuses);
        
        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.ABSTRACTED,
                            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE,
                            DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        Set<DocumentWorkflowStatusCode> tsrStatuses = Collections.unmodifiableSet(tmpSet);
        tmp.put(TRIAL_SUMMARY_REPORT, tsrStatuses);
        tmp.put(TRIAL_SUMMARY_FEEDBACK, tsrStatuses);

        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.ABSTRACTED, DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(INITIAL_ABSTRACTION_VERIFY, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(ONGOING_ABSTRACTION_VERIFICATION, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(DocumentWorkflowStatusCode.ACCEPTED, DocumentWorkflowStatusCode.ABSTRACTED,
                            DocumentWorkflowStatusCode.VERIFICATION_PENDING,
                            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(LATE_REJECTION_DATE, Collections.unmodifiableSet(tmpSet));

        ALLOWED_DWF_STATUSES = Collections.unmodifiableMap(tmp);
    }
    
    private static final String READY_FOR = "ready for";

    private static final String START = "start";

    /**
     * Constructor for MilestoneCode.
     * @param code code
     * @param unique only one allowed per study
     * @param requiredDwfStatus required document workflow status
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    private MilestoneCode(String code, boolean unique, boolean validationTrigger, boolean allowedIfOnhold,
            boolean allowedIfInBox) {
        this.code = code;
        this.unique = unique;
        this.validationTrigger = validationTrigger;
        this.allowedIfOnhold = allowedIfOnhold;
        this.allowedInInBox = allowedIfInBox;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     *@return String DisplayName
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }

    /**
     * @return the uniqueConstraint
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * @return the validationTrigger
     */
    public boolean isValidationTrigger() {
        return validationTrigger;
    }

    /**
     * @return the allowedIfOnhold
     */
    public boolean isAllowedIfOnhold() {
        return allowedIfOnhold;
    }

    /**
     * 
     * @return the allowedIfInBox
     */
    public boolean isAllowedIfInBox() {
        return allowedInInBox;
    }

    /**
     * @param documentWorkflowStatusCode dwf status code to check
     * @return if milestone is valid for given dwf status code
     */
    public boolean isValidDwfStatus(DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        return ALLOWED_DWF_STATUSES.get(this).contains(documentWorkflowStatusCode);
    }

    /**
     * Test if this code represents an administrative milestone.
     * @return true if this code represents an administrative milestone
     */
    public boolean isAdminMilestone() {
        return ADMIN_SEQ.contains(this);
    }

    /**
     * Test if this code represents an scientific milestone.
     * @return true if this code represents an scientific milestone
     */
    public boolean isScientificMilestone() {
        return SCIENTIFIC_SEQ.contains(this);
    }

    /**
     * @return all the valid document workflow statuses for this milestone
     */
    public List<DocumentWorkflowStatusCode> getValidDwfStatuses() {
        return new ArrayList<DocumentWorkflowStatusCode>(ALLOWED_DWF_STATUSES.get(this));
    }

    /**
     * @param code code
     * @return Study Type Code
     */
    public static MilestoneCode getByCode(String code) {
        return getByClassAndCode(MilestoneCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[] getDisplayNames() {
        MilestoneCode[] l = MilestoneCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }

    /**
     * Checks if is above trial summary report.
     * @param mc the mc
     * @return true, if is above trial summary report
     */
    public static boolean isAboveTrialSummaryReport(MilestoneCode mc) {
        return TRS_AND_ABOVE.contains(mc);
    }
    
    /**
     * Gets the codes of the given MilestoneCode collection.
     * @param milestones The milestones
     * @return The list of codes of the given milestones
     */
    public static List<String> getCodes(Collection<MilestoneCode> milestones) {
        List<String> milestoneCodes = new ArrayList<String>();
        for (MilestoneCode milestoneCode : MilestoneCode.values()) {
            if (milestones.contains(milestoneCode)) {
                milestoneCodes.add(milestoneCode.getCode());
            }
        }
        return milestoneCodes;
    }
    
    /**
     * @return MilestoneCodesForReporting
     */
    public static List<MilestoneCode> getMilestoneCodesForReporting() {
        List<MilestoneCode> list = new ArrayList<MilestoneCode>();
        for (MilestoneCode code : values()) {
            if (!code.getCode().toLowerCase().contains(START)
                    && !code.getCode().toLowerCase().contains(READY_FOR)) {
                list.add(code);
            }
        }
        return list;
    }
}
