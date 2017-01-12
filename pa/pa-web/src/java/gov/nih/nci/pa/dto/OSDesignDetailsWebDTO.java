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
package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * @author Kalpana Guthikonda
 * @since 10/27/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class OSDesignDetailsWebDTO implements Serializable {
    private static final long serialVersionUID = 7930733088990804000L;
    private String primaryPurposeCode;
    private String primaryPurposeOtherText;
    private String primaryPurposeAdditionalQualifierCode;
    private String studyType;
    private String studySubtypeCode;
    private String biospecimenDescription;
    private String biospecimenRetentionCode;
    private String numberOfGroups;
    private String studyModelCode;
    private String studyModelOtherText;
    private String timePerspectiveCode;
    private String timePerspectiveOtherText;
    private String minimumTargetAccrualNumber;
    private String finalAccrualNumber;
    private String phaseCode;
    private String phaseAdditionalQualifierCode;
    private boolean expandedIndicator;
    
    /**
     * @return biospecimenDescription
     */
    public String getBiospecimenDescription() {
        return biospecimenDescription;
    }
    /**
     * @param biospecimenDescription biospecimenDescription
     */
    public void setBiospecimenDescription(String biospecimenDescription) {
        this.biospecimenDescription = biospecimenDescription;
    }
    /**
     * @return biospecimenRetentionCode
     */
    public String getBiospecimenRetentionCode() {
        return biospecimenRetentionCode;
    }
    /**
     * @param biospecimenRetentionCode biospecimenRetentionCode
     */
    public void setBiospecimenRetentionCode(String biospecimenRetentionCode) {
        this.biospecimenRetentionCode = biospecimenRetentionCode;
    }
    /**
     * @return numberOfGroups
     */
    public String getNumberOfGroups() {
        return numberOfGroups;
    }
    /**
     * @param numberOfGroups numberOfGroups
     */
    public void setNumberOfGroups(String numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
    
    /**
     * @return studyModelCode
     */
    public String getStudyModelCode() {
        return studyModelCode;
    }
    /**
     * @param studyModelCode studyModelCode
     */
    public void setStudyModelCode(String studyModelCode) {
        this.studyModelCode = studyModelCode;
    }
    /**
     * @return studyModelOtherText
     */
    public String getStudyModelOtherText() {
        return studyModelOtherText;
    }
    /**
     * @param studyModelOtherText studyModelOtherText
     */
    public void setStudyModelOtherText(String studyModelOtherText) {
        this.studyModelOtherText = studyModelOtherText;
    }
    /**
     * @return timePerspectiveCode
     */
    public String getTimePerspectiveCode() {
        return timePerspectiveCode;
    }
    /**
     * @param timePerspectiveCode timePerspectiveCode
     */
    public void setTimePerspectiveCode(String timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
    }
    /**
     * @return timePerspectiveOtherText
     */
    public String getTimePerspectiveOtherText() {
        return timePerspectiveOtherText;
    }
    /**
     * @param timePerspectiveOtherText timePerspectiveOtherText
     */
    public void setTimePerspectiveOtherText(String timePerspectiveOtherText) {
        this.timePerspectiveOtherText = timePerspectiveOtherText;
    }
    /**
     * @return the minimumTargetAccrualNumber
     */
    public String getMinimumTargetAccrualNumber() {
        return minimumTargetAccrualNumber;
    }
    /**
     * @param minimumTargetAccrualNumber the minimumTargetAccrualNumber to set
     */
    public void setMinimumTargetAccrualNumber(String minimumTargetAccrualNumber) {
        this.minimumTargetAccrualNumber = minimumTargetAccrualNumber;
    }
    /**
     * @return the primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return the studySubtypeCode
     */
    public String getStudySubtypeCode() {
        return studySubtypeCode;
    }
    /**
     * @param studySubtypeCode the studySubtypeCode to set
     */
    public void setStudySubtypeCode(String studySubtypeCode) {
        this.studySubtypeCode = studySubtypeCode;
    }
    /**
     * @return the primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * @param primaryPurposeOtherText the primaryPurposeOtherText to set
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return the primaryPurposeAdditionalQualifierCode
     */
    public String getPrimaryPurposeAdditionalQualifierCode() {
        return primaryPurposeAdditionalQualifierCode;
    }
    /**
     * @param primaryPurposeAdditionalQualifierCode the primaryPurposeAdditionalQualifierCode to set
     */
    public void setPrimaryPurposeAdditionalQualifierCode(
            String primaryPurposeAdditionalQualifierCode) {
        this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
    }
    /**
     * @return the studyType
     */
    public String getStudyType() {
        return studyType;
    }
    /**
     * @param studyType the studyType to set
     */
    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }
    /**
     * @return the phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * @return the phaseAdditionalQualifierCode
     */
    public String getPhaseAdditionalQualifierCode() {
        return phaseAdditionalQualifierCode;
    }
    /**
     * @param phaseAdditionalQualifierCode the phaseAdditionalQualifierCode to set
     */
    public void setPhaseAdditionalQualifierCode(String phaseAdditionalQualifierCode) {
        this.phaseAdditionalQualifierCode = phaseAdditionalQualifierCode;
    }
    /**
     * @return the finalAccrualNumber
     */
    public String getFinalAccrualNumber() {
        return finalAccrualNumber;
    }
    /**
     * @param finalAccrualNumber the finalAccrualNumber to set
     */
    public void setFinalAccrualNumber(String finalAccrualNumber) {
        this.finalAccrualNumber = finalAccrualNumber;
    }
    
    /**
     * @return the expandedIndicator
     */
    public boolean isExpandedIndicator() {
        return expandedIndicator;
    }

    /**
     * @param expandedIndicator the expandedIndicator to set
     */
    public void setExpandedIndicator(boolean expandedIndicator) {
        this.expandedIndicator = expandedIndicator;
    }

}
