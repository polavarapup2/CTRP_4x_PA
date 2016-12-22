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

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Enumeration for Trial Status codes.
 * 
 * @author Naveen Amiruddin
 * @since 05/22/2007 copyright NCI 2007. All rights reserved. This code may not be used without the express written
 *        permission of the copyright holder, NCI.
 */
public enum StudyStatusCode implements CodedEnum<String> {
    /**
     * In Review.
     */
    IN_REVIEW("In Review", true, false),
    /**
     * Approved.
     */
    APPROVED("Approved", true, false),
    /**
     * Active.
     */
    ACTIVE("Active", true, false),
    /**
     * Enrolling by Invitation.
     */
    ENROLLING_BY_INVITATION("Enrolling by Invitation", true, false),
    /**
     * Closed to Accrual.
     */
    CLOSED_TO_ACCRUAL("Closed to Accrual", false, true),
    /**
     * Closed To Accrual And Intervention.
     */
    CLOSED_TO_ACCRUAL_AND_INTERVENTION("Closed to Accrual and Intervention", false, true),
    /**
     * Temporarily Closed To Accrual.
     */
    TEMPORARILY_CLOSED_TO_ACCRUAL("Temporarily Closed to Accrual", false, false),
    /**
     * Temporarily Closed To Accrual and Intervention.
     */
    TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION("Temporarily Closed to Accrual and Intervention", false, false),
    /**
     * Withdrawn.
     */
    WITHDRAWN("Withdrawn", false, false),
    /**
     * Administratively Complete.
     */
    ADMINISTRATIVELY_COMPLETE("Administratively Complete", false, true),
    /**
     * Complete.
     */
    COMPLETE("Complete", true, true);

    private static final Set<StudyStatusCode> REASON_REQUIRED = EnumSet
        .of(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL,
            StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION, StudyStatusCode.WITHDRAWN,
            StudyStatusCode.ADMINISTRATIVELY_COMPLETE);

    private static final Map<StudyStatusCode, Set<StudyStatusCode>> TRANSITIONS;

    static {
        Map<StudyStatusCode, Set<StudyStatusCode>> tmp = new HashMap<StudyStatusCode, Set<StudyStatusCode>>();

        Set<StudyStatusCode> tmpSet = EnumSet.of(APPROVED, ACTIVE, ENROLLING_BY_INVITATION, WITHDRAWN, IN_REVIEW);
        tmp.put(IN_REVIEW, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(ACTIVE, ENROLLING_BY_INVITATION, WITHDRAWN, APPROVED);
        tmp.put(APPROVED, Collections.unmodifiableSet(tmpSet));

        Set<StudyStatusCode> emptySet = Collections.emptySet();
        tmp.put(WITHDRAWN, emptySet);

        tmpSet = EnumSet.of(TEMPORARILY_CLOSED_TO_ACCRUAL,
                TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                ADMINISTRATIVELY_COMPLETE, CLOSED_TO_ACCRUAL,
                CLOSED_TO_ACCRUAL_AND_INTERVENTION, COMPLETE, ACTIVE);
        tmp.put(ACTIVE, Collections.unmodifiableSet(tmpSet));
        
        tmpSet = EnumSet.of(TEMPORARILY_CLOSED_TO_ACCRUAL,
                TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                ADMINISTRATIVELY_COMPLETE, CLOSED_TO_ACCRUAL,
                CLOSED_TO_ACCRUAL_AND_INTERVENTION, COMPLETE,
                ENROLLING_BY_INVITATION);
        tmp.put(ENROLLING_BY_INVITATION, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(ACTIVE, ENROLLING_BY_INVITATION,
                TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                ADMINISTRATIVELY_COMPLETE, CLOSED_TO_ACCRUAL,
                CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                TEMPORARILY_CLOSED_TO_ACCRUAL);
        tmp.put(TEMPORARILY_CLOSED_TO_ACCRUAL, Collections.unmodifiableSet(tmpSet));

        tmpSet =
                EnumSet.of(ACTIVE, ENROLLING_BY_INVITATION, ADMINISTRATIVELY_COMPLETE, CLOSED_TO_ACCRUAL,
                           CLOSED_TO_ACCRUAL_AND_INTERVENTION, TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        tmp.put(TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION, Collections.unmodifiableSet(tmpSet));

        tmp.put(ADMINISTRATIVELY_COMPLETE, emptySet);

        tmpSet = EnumSet.of(CLOSED_TO_ACCRUAL_AND_INTERVENTION, ADMINISTRATIVELY_COMPLETE, CLOSED_TO_ACCRUAL);
        tmp.put(CLOSED_TO_ACCRUAL, Collections.unmodifiableSet(tmpSet));

        tmpSet = EnumSet.of(ADMINISTRATIVELY_COMPLETE, COMPLETE, CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        tmp.put(CLOSED_TO_ACCRUAL_AND_INTERVENTION, Collections.unmodifiableSet(tmpSet));

        tmp.put(COMPLETE, emptySet);

        TRANSITIONS = Collections.unmodifiableMap(tmp);
    }

    private String code;
    private final boolean eligibleForSiteSelfRegistration;
    private final boolean closed;

    /**
     * Constructor for TrialStatusCode.
     * 
     * @param code
     */

    private StudyStatusCode(String code,
            boolean eligibleForSiteSelfRegistration, boolean closed) {
        this.code = code;
        this.eligibleForSiteSelfRegistration = eligibleForSiteSelfRegistration;
        this.closed = closed;
        register(this);
    }

    /**
     * @param code code
     * @return TrialStatusCode
     */
    public static StudyStatusCode getByCode(String code) {
        return getByClassAndCode(StudyStatusCode.class, code);
    }
    
    /**
     * Returns the corresponding study status code for a recruitment status
     * code.
     * 
     * @param statusCode
     *            RecruitmentStatusCode
     * @return StudyStatusCode
     */
    public static StudyStatusCode getByRecruitmentStatus(
            RecruitmentStatusCode statusCode) {
        if (RecruitmentStatusCode.COMPLETED.equals(statusCode)) {
            return COMPLETE;
        }
        return getByCode(statusCode.getCode());
    }

    /**
     * construct a array of display names for Study Status coded Enum.
     * @return String[] display names for StudyStatusCode
     */
    public static String[] getDisplayNames() {
        StudyStatusCode[] studyStatusCodes = StudyStatusCode.values();
        String[] codedNames = new String[studyStatusCodes.length];
        for (int i = 0; i < studyStatusCodes.length; i++) {
            codedNames[i] = studyStatusCodes[i].getCode();
        }
        return codedNames;
    }

    /**
     * construct a array of display names for Study Status coded Enum for amend.
     * @return String[] display names for StudyStatusCode
     */
    public static String[] getDisplayNamesForAmend() {
        return getDisplayNames();
    }

    /**
     * @return code coded value of enum
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return String DisplayName
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
     * Helper method that indicates whether a transition to the new entity status is allowed.
     * 
     * @param newStatus transition to status
     * @return whether the transition is allowed
     */
    public boolean canTransitionTo(StudyStatusCode newStatus) {
        return TRANSITIONS.get(this).contains(newStatus);
    }

    /**
     * Test if this status requires a reason text.
     * @return true if this status requires a reason text.
     */
    public boolean requiresReasonText() {
        return REASON_REQUIRED.contains(this);
    }
    
    /**
     * @return boolean an indication of whether this status permits
     *         self-registration of participating sites on the trial.
     * @see https
     *      ://tracker.nci.nih.gov/browse/PO-2034?focusedCommentId=142303#comment
     *      -142303
     */
    public boolean isEligibleForSiteSelfRegistration() {
        return eligibleForSiteSelfRegistration;
    }
    
    /**
     * Indicator whether a status means trial is closed.
     * @return closed
     */
    public boolean isClosed() {
        return closed;
    }

}
