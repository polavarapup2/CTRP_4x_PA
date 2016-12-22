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

import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Web DTO class for displaying list of diseases associated with SP.
 * 
 * @author Hugh Reinhart
 * @since 12/02/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public final class DiseaseWebDTO implements Serializable {
    private static final long serialVersionUID = 5484432673126229919L;
    private String studyDiseaseIdentifier;
    private String diseaseIdentifier;
    private String preferredName;
    private String code;
    private String conceptId;
    private String menuDisplayName;
    private String parentPreferredName;
    private String alternames;
    private String ctGovXmlIndicator;
    private boolean selected;

    private String ntTermIdentifier;
    private List<String> alterNameList = new ArrayList<String>();
    private List<String> parentTermList = new ArrayList<String>();
    private List<String> childTermList = new ArrayList<String>();

    // List of existing displanames
    private List<String> displayNameList = new ArrayList<String>();
    
    /**
     * Default.
     */
    public DiseaseWebDTO() {
        // NOOP
    }
    
    /**
     * @param disease
     *            PDQDiseaseDTO
     */
    public DiseaseWebDTO(final PDQDiseaseDTO disease) {
        setDiseaseIdentifier(IiConverter.convertToString(disease
                .getIdentifier()));
        setPreferredName(StConverter
                .convertToString(disease.getPreferredName()));
        setCode(StConverter.convertToString(disease.getDiseaseCode()));
        setConceptId(StConverter.convertToString(disease.getNtTermIdentifier()));
        setMenuDisplayName(StConverter
                .convertToString(disease.getDisplayName()));

    }

    
    /**
     * @return the studyDiseaseIdentifier
     */
    public String getStudyDiseaseIdentifier() {
        return studyDiseaseIdentifier;
    }

    /**
     * @param studyDiseaseIdentifier
     *            the studyDiseaseIdentifier to set
     */
    public void setStudyDiseaseIdentifier(String studyDiseaseIdentifier) {
        this.studyDiseaseIdentifier = studyDiseaseIdentifier;
    }

    /**
     * @return the diseaseIdentifier
     */
    public String getDiseaseIdentifier() {
        return diseaseIdentifier;
    }

    /**
     * @param diseaseIdentifier
     *            the diseaseIdentifier to set
     */
    public void setDiseaseIdentifier(String diseaseIdentifier) {
        this.diseaseIdentifier = diseaseIdentifier;
    }

    /**
     * @return the preferredName
     */
    public String getPreferredName() {
        return preferredName;
    }

    /**
     * @param preferredName
     *            the preferredName to set
     */
    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the conceptId
     */
    public String getConceptId() {
        return conceptId;
    }

    /**
     * @param conceptId
     *            the conceptId to set
     */
    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    /**
     * @return the menuDisplayName
     */
    public String getMenuDisplayName() {
        return menuDisplayName;
    }

    /**
     * @param menuDisplayName
     *            the menuDisplayName to set
     */
    public void setMenuDisplayName(String menuDisplayName) {
        this.menuDisplayName = menuDisplayName;
    }

    /**
     * @return the parentPreferredName
     */
    public String getParentPreferredName() {
        return parentPreferredName;
    }

    /**
     * @param parentPreferredName
     *            the parentPreferredName to set
     */
    public void setParentPreferredName(String parentPreferredName) {
        this.parentPreferredName = parentPreferredName;
    }

    /**
     * @return the alternames
     */
    public String getAlternames() {
        return alternames;
    }

    /**
     * @param alternames
     *            the alternames to set
     */
    public void setAlternames(String alternames) {
        this.alternames = alternames;
    }

    /**
     * @return the ctGovXmlIndicator
     */
    public String getCtGovXmlIndicator() {
        return ctGovXmlIndicator;
    }

    /**
     * @param ctGovXmlIndicator
     *            the ctGovXmlIndicator to set
     */
    public void setCtGovXmlIndicator(String ctGovXmlIndicator) {
        this.ctGovXmlIndicator = ctGovXmlIndicator;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected
     *            the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return altername list
     */
    public List<String> getAlterNameList() {
        return alterNameList;
    }

    /**
     * @param alterNameList
     *            altername list
     */
    public void setAlterNameList(List<String> alterNameList) {
        this.alterNameList = alterNameList;
    }

    /**
     * @return the parentTermList
     */
    public List<String> getParentTermList() {
        return parentTermList;
    }

    /**
     * @param parentTermList the parentTermList to set
     */
    public void setParentTermList(List<String> parentTermList) {
        this.parentTermList = parentTermList;
    }

    /**
     * @return the childTermList
     */
    public List<String> getChildTermList() {
        return childTermList;
    }

    /**
     * @param childTermList the childTermList to set
     */
    public void setChildTermList(List<String> childTermList) {
        this.childTermList = childTermList;
    }

    /**
     * @return the ntTermIdentifier
     */
    public String getNtTermIdentifier() {
        return ntTermIdentifier;
    }

    /**
     * @param ntTermIdentifier the ntTermIdentifier to set
     */
    public void setNtTermIdentifier(String ntTermIdentifier) {
        this.ntTermIdentifier = ntTermIdentifier;
    }

    /**
     * @return the displayNameList
     */
    public List<String> getDisplayNameList() {
        return displayNameList;
    }

    /**
     * @param displayNameList the displayNameList to set
     */
    public void setDisplayNameList(List<String> displayNameList) {
        this.displayNameList = displayNameList;
    }

    
}
