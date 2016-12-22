/**
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
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
/**
 *
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.TooManyFields" })
public class DocumentDTO extends StudyDTO {
    private static final long serialVersionUID = 6363271404447384900L;
    private Cd typeCode;
    private Bl activeIndicator;
    private St fileName;
    private St inactiveCommentText;
    private Ed text;
    private Ts dateLastUpdated;
    private Ts dateLastCreated;
    private Bl original;
    private Bl deleted;
    private Ii studyInboxIdentifier;   
    private St userLastUpdated;
    private St userLastCreated;
    private Long ctroUserId;
    private Long ccctUserId;
    private St ctroUserName;
    private St ccctUserName;
    private Ts ctroUserReviewDateTime;
    private Ts ccctUserReviewDateTime;
    
    
    
    /**
     * @return typeCode
     */
    public Cd getTypeCode() {
        return typeCode;
    }    
    
    /**
     * @param typeCode typeCode
     */
    public void setTypeCode(Cd typeCode) {
        this.typeCode = typeCode;
    }
   
    /**
     * @return activeIndicator
     */
    public Bl getActiveIndicator() {
        return activeIndicator;
    }
    
    /**
     * @param activeIndicator activeIndicator
     */
    public void setActiveIndicator(Bl activeIndicator) {
        this.activeIndicator = activeIndicator;
    }
    
    /**
     * @return fileName
     */
    public St getFileName() {
        return fileName;
    }
    
    /**
     * @param fileName fileName
     */
    public void setFileName(St fileName) {
        this.fileName = fileName;
    }
    
    /**
     * 
     * @return inactiveCommentText
     */
    public St getInactiveCommentText() {
        return inactiveCommentText;
    }
    
    /**
     * 
     * @param inactiveCommentText inactiveCommentText
     */
    public void setInactiveCommentText(St inactiveCommentText) {
        this.inactiveCommentText = inactiveCommentText;
    }

    /**
     * 
     * @return text t
     */
    public Ed getText() {
        return text;
    }

    /**
     * 
     * @param text t
     */
    public void setText(Ed text) {
        this.text = text;
    }

    /**
     * @return the dateLastUpdated
     */
    public Ts getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * @param dateLastUpdated the dateLastUpdated to set
     */
    public void setDateLastUpdated(Ts dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    /**
     * @return the original
     */
    public Bl getOriginal() {
        return original;
    }

    /**
     * @param original the original to set
     */
    public void setOriginal(Bl original) {
        this.original = original;
    }

    /**
     * @return the deleted
     */
    public Bl getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(Bl deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the studyInboxIdentifier
     */
    public Ii getStudyInboxIdentifier() {
        return studyInboxIdentifier;
    }

    /**
     * @param studyInboxIdentifier the studyInboxIdentifier to set
     */
    public void setStudyInboxIdentifier(Ii studyInboxIdentifier) {
        this.studyInboxIdentifier = studyInboxIdentifier;
    }

    /**
     * @return the userLastUpdated
     */
    public St getUserLastUpdated() {
        return userLastUpdated;
    }

    /**
     * @param userLastUpdated the userLastUpdated to set
     */
    public void setUserLastUpdated(St userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }

    /**
     * @return the userLastCreated
     */
    public St getUserLastCreated() {
        return userLastCreated;
    }

    /**
     * @param userLastCreated the userLastCreated to set
     */
    public void setUserLastCreated(St userLastCreated) {
        this.userLastCreated = userLastCreated;
    }

    /**
     * @return the dateLastCreated
     */
    public Ts getDateLastCreated() {
        return dateLastCreated;
    }

    /**
     * @param dateLastCreated the dateLastCreated to set
     */
    public void setDateLastCreated(Ts dateLastCreated) {
        this.dateLastCreated = dateLastCreated;
    }

    /**
     * @return ctroUserId
     */
    public Long getCtroUserId() {
        return ctroUserId;
    }

    /**
     * @param ctroUserId ctroUserId
     */
    public void setCtroUserId(Long ctroUserId) {
        this.ctroUserId = ctroUserId;
    }

    /**
     * @return getCcctUserId
     */
    public Long getCcctUserId() {
        return ccctUserId;
    }

    /**
     * @param ccctUserId ccctUserId
     */
    public void setCcctUserId(Long ccctUserId) {
        this.ccctUserId = ccctUserId;
    }

    /**
     * @return getCtroUserName
     */
    public St getCtroUserName() {
        return ctroUserName;
    }

    /**
     * @param ctroUserName ctroUserName
     */
    public void setCtroUserName(St ctroUserName) {
        this.ctroUserName = ctroUserName;
    }

    /**
     * @return ccctUserName
     */
    public St getCcctUserName() {
        return ccctUserName;
    }

    /**
     * @param ccctUserName ccctUserName
     */
    public void setCcctUserName(St ccctUserName) {
        this.ccctUserName = ccctUserName;
    }

    /**
     * @return ctroUserReviewDateTime
     */
    public Ts getCtroUserReviewDateTime() {
        return ctroUserReviewDateTime;
    }

    /**
     * @param ctroUserReviewDateTime ctroUserReviewDateTime
     */
    public void setCtroUserReviewDateTime(Ts ctroUserReviewDateTime) {
        this.ctroUserReviewDateTime = ctroUserReviewDateTime;
    }

    /**
     * @return ccctUserReviewDateTime
     */
    public Ts getCcctUserReviewDateTime() {
        return ccctUserReviewDateTime;
    }

    /**
     * @param ccctUserReviewDateTime ccctUserReviewDateTime
     */
    public void setCcctUserReviewDateTime(Ts ccctUserReviewDateTime) {
        this.ccctUserReviewDateTime = ccctUserReviewDateTime;
    }

    
    
}
