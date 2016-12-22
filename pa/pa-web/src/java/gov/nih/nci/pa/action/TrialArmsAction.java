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

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialArmsWebDTO;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
* @author Hugh Reinhart
* @since 10/31/2008
*/
public class TrialArmsAction extends AbstractMultiObjectDeleteAction implements Preparable {
    private static final long serialVersionUID = 1884666890L;

    private static final String ACT_EDIT = "edit";
    private static final String ACT_LIST = "list";
    private static final String ACT_LIST_ARM = "listArm";
    private static final String ACT_EDIT_NEW_ARM = "editNewArm";
    private static final String ACT_EDIT_ARM = "editArm";
    private static final String ACT_LIST_GROUP = "listGroup";
    private static final String ACT_EDIT_NEW_GROUP = "editNewGroup";
    private static final String ACT_EDIT_GROUP = "editGroup";

    private ArmServiceLocal armService;
    private PlannedActivityServiceLocal plaService;
    private InterventionServiceLocal intService;

    private Ii spIdIi;

    private List<TrialArmsWebDTO> armList;
    private List<InterventionWebDTO> intList;
    private String currentAction;
    private String selectedArmIdentifier;
    private String armName;
    private String armType;
    private String armDescription;
    private String checkBoxEntry;

    private StudyProtocolQueryDTO spDTO;


    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws PAException e
     */
    @SuppressWarnings("deprecation")
    public void prepare() throws PAException {
        armService = PaRegistry.getArmService();
        plaService = PaRegistry.getPlannedActivityService();
        intService = PaRegistry.getInterventionService();

        spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
    }

    /**
     * @return Action result.
     * @throws PAException exception.
     */
    @Override
    public String execute() throws PAException {         
        loadForm();
        if (isNonInterventionalStudy()) {
            setCurrentAction(ACT_LIST_GROUP);
        } else {
            setCurrentAction(ACT_LIST_ARM);
        }
        return ACT_LIST;
    }

    /**
     * @return
     */
    private boolean isNonInterventionalStudy() {
        return "NonInterventionalStudyProtocol".equalsIgnoreCase(spDTO
                .getStudyProtocolType());
    }
 

    /**
     * @return result
     * @throws PAException exception
     */
    public String create() throws PAException {
        loadEditForm(null);
        setCurrentAction(ACT_EDIT_NEW_ARM);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String createGroup() throws PAException {
        loadEditForm(null);
        setCurrentAction(ACT_EDIT_NEW_GROUP);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String edit() throws PAException {
        loadEditForm(getSelectedArmIdentifier());
        setCurrentAction(ACT_EDIT_ARM);
        return ACT_EDIT;
    }
    /**
     * @return result
     * @throws PAException exception
     */
    public String editGroup() throws PAException {
        loadEditForm(getSelectedArmIdentifier());
        setCurrentAction(ACT_EDIT_GROUP);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String delete() throws PAException {
        try {
            deleteSelectedObjects();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.MULTI_DELETE_MESSAGE);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return execute();
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void deleteObject(Long objectId) throws PAException {
        armService.delete(IiConverter.convertToIi(objectId));        
    }
    
    /**
     * @return result
     * @throws PAException exception
     */
    public String add() throws PAException {
        businessRules();
        if (hasActionErrors() || hasFieldErrors()) {
            reloadInterventions();
            return ACT_EDIT;
        }
        ArmDTO newArm = new ArmDTO();
        newArm.setIdentifier(null);
        newArm.setDescriptionText(StConverter.convertToSt(getArmDescription()));
        newArm.setName(StConverter.convertToSt(getArmName()));
        newArm.setStudyProtocolIdentifier(spIdIi);
        if (getCurrentAction().equals(ACT_EDIT_NEW_ARM)) {
            newArm.setTypeCode(CdConverter.convertStringToCd(getArmType()));
        }
        newArm.setInterventions(getAssociatedInterventions());
        try {
            armService.create(newArm);
        } catch (PAException e) {
            addActionError(e.getMessage());
            reloadInterventions();
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        setCurrentAction(getCurrentAction().equals(ACT_EDIT_NEW_ARM) ? ACT_LIST_ARM : ACT_LIST_GROUP);
        return ACT_LIST;
    }
    /**
     * @return result
     * @throws PAException exception
     */
    public String update() throws PAException {
        businessRules();
        if (hasActionErrors() || hasFieldErrors()) {
            reloadInterventions();
            return ACT_EDIT;
        }
        ArmDTO updatedArm = new ArmDTO();
        updatedArm.setIdentifier(IiConverter.convertToIi(getSelectedArmIdentifier()));
        updatedArm.setDescriptionText(StConverter.convertToSt(getArmDescription()));
        updatedArm.setName(StConverter.convertToSt(getArmName()));
        updatedArm.setStudyProtocolIdentifier(spIdIi);
        if (getCurrentAction().equals(ACT_EDIT_ARM)) {
            updatedArm.setTypeCode(CdConverter.convertStringToCd(getArmType()));
        }
        updatedArm.setInterventions(getAssociatedInterventions());
        try {
            armService.update(updatedArm);
        } catch (PAException e) {
            addActionError(e.getMessage());
            reloadInterventions();
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        setCurrentAction(getCurrentAction().equals(ACT_EDIT_ARM) ? ACT_LIST_ARM : ACT_LIST_GROUP);
        return ACT_LIST;
    }

    private void businessRules() { //NOPMD
        if (StringUtils.isEmpty(getArmType()) && !isNonInterventionalStudy()) {
            addFieldError("armType", "Select an Arm Type");
        }
        
        if (StringUtils.isNotEmpty(getArmDescription())) {
            if (PAUtil.isGreaterThan(StConverter.convertToSt(getArmDescription()), PAAttributeMaxLen.LEN_1000)) {
                addFieldError("armDescription", "Description length should not be greater than 1000");
            }
        } else {
            addFieldError("armDescription", "Provide Description");
        }
        
        if ((getCurrentAction().equals(ACT_EDIT_ARM) || getCurrentAction().equals(ACT_EDIT_NEW_ARM))
                && (StringUtils.isNotEmpty(armType) && armType.equals(ArmTypeCode.NO_INTERVENTION.getCode())
                && (getAssociatedIds().size() > 0)) && !isNonInterventionalStudy()) {
            addActionError("Arms of type " + armType + " cannot have associated interventions.  ");
        }
    }

    private void loadForm() throws PAException {
        setArmList(new ArrayList<TrialArmsWebDTO>());
        List<ArmDTO> armIsoList = armService.getByStudyProtocol(spIdIi);
        for (ArmDTO arm : armIsoList) {
            TrialArmsWebDTO webDto = new TrialArmsWebDTO();
            webDto.setDescription(StConverter.convertToString(arm.getDescriptionText()));
            webDto.setIdentifier(IiConverter.convertToString(arm.getIdentifier()));
            StringBuffer intBuff = new StringBuffer();
            List<PlannedActivityDTO> paList = plaService.getByArm(arm.getIdentifier());
            for (PlannedActivityDTO pa : paList) {
                InterventionDTO inter = intService.get(pa.getInterventionIdentifier());
                if (intBuff.length() > 0) {
                    intBuff.append(", ");
                }
                intBuff.append(StConverter.convertToString(inter.getName()));
            }
            webDto.setInterventions(intBuff.toString());
            webDto.setName(StConverter.convertToString(arm.getName()));
            webDto.setType(CdConverter.convertCdToString(arm.getTypeCode()));
            getArmList().add(webDto);
        }
    }

    private void loadEditForm(String armId) throws PAException {
        Set<Long> cInterventions = new HashSet<Long>();
        if (armId != null) {
            ArmDTO cArm = armService.get(IiConverter.convertToIi(armId));
            setArmDescription(StConverter.convertToString(cArm.getDescriptionText()));
            setArmName(StConverter.convertToString(cArm.getName()));
            setArmType(CdConverter.convertCdToString(cArm.getTypeCode()));
            setSelectedArmIdentifier(armId);
            List<PlannedActivityDTO> paList = plaService.getByArm(IiConverter.convertToIi(armId));
            for (PlannedActivityDTO pa : paList) {
                cInterventions.add(IiConverter.convertToLong(pa.getIdentifier()));
            }
        } else {
            setSelectedArmIdentifier(null);
        }
        setIntList(new ArrayList<InterventionWebDTO>());
        setCheckBoxEntry("");
        List<PlannedActivityDTO> plaList = plaService.getByStudyProtocol(spIdIi);
        for (PlannedActivityDTO pla : plaList) {
            if (PAUtil.isTypeIntervention(pla.getCategoryCode())
                 && (!ISOUtil.isIiNull(pla.getInterventionIdentifier()))) {
                InterventionDTO intDto = intService.get(pla.getInterventionIdentifier());
                InterventionWebDTO intWebDto = new InterventionWebDTO();
                intWebDto.setDescription(StConverter.convertToString(pla.getTextDescription()));
                intWebDto.setIdentifier(IiConverter.convertToString(pla.getIdentifier()));
                intWebDto.setName(StConverter.convertToString(intDto.getName()));
                intWebDto.setArmAssignment(cInterventions.contains(IiConverter.convertToLong(pla.getIdentifier())));
                getIntList().add(intWebDto);
                if (intWebDto.getArmAssignment()) {
                    setCheckBoxEntry(getCheckBoxEntry() + intWebDto.getIdentifier() + ",");
                }
            }
        }
    }

    private void reloadInterventions() throws PAException {
        setIntList(new ArrayList<InterventionWebDTO>());
        List<PlannedActivityDTO> plaList = plaService.getByStudyProtocol(spIdIi);
        for (PlannedActivityDTO pla : plaList) {
            if (PAUtil.isTypeIntervention(pla.getCategoryCode())
                && (!ISOUtil.isIiNull(pla.getInterventionIdentifier()))) {
                InterventionDTO intDto = intService.get(pla.getInterventionIdentifier());
                InterventionWebDTO intWebDto = new InterventionWebDTO();
                intWebDto.setDescription(StConverter.convertToString(pla.getTextDescription()));
                intWebDto.setIdentifier(IiConverter.convertToString(pla.getIdentifier()));
                intWebDto.setName(StConverter.convertToString(intDto.getName()));
                intWebDto.setArmAssignment(getAssociatedIds().contains(
                        IiConverter.convertToLong(pla.getIdentifier())));
                getIntList().add(intWebDto);
            }
        }
    }

    private Set<Long> getAssociatedIds() {
        String clicks = (getCheckBoxEntry() == null) ? "" : getCheckBoxEntry();
        Set<Long> tSet = new HashSet<Long>();
        while (clicks.indexOf(',') > 1) {
            Long lid = Long.valueOf(clicks.substring(0, clicks.indexOf(',')));
            clicks = clicks.substring(clicks.indexOf(',') + 1);
            if (tSet.contains(lid)) {
                tSet.remove(lid);
            } else {
                tSet.add(lid);
            }
        }
        return tSet;
    }

    private DSet<Ii> getAssociatedInterventions() {
        Set<Long> tSet = getAssociatedIds();
        Set<Ii> intSet = new HashSet<Ii>();
        for (Long t : tSet) {
            intSet.add(IiConverter.convertToActivityIi(t));
        }
        DSet<Ii> interventions = new DSet<Ii>();
        interventions.setItem(intSet);
        return interventions;
    }
    /**
     * @return the armsList
     */
    public List<TrialArmsWebDTO> getArmList() {
        return armList;
    }

    /**
     * @param armList the armList to set
     */
    public void setArmList(List<TrialArmsWebDTO> armList) {
        this.armList = armList;
    }

    /**
     * @return the intList
     */
    public List<InterventionWebDTO> getIntList() {
        return intList;
    }

    /**
     * @param intList the intList to set
     */
    public void setIntList(List<InterventionWebDTO> intList) {
        this.intList = intList;
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * @return the armName
     */
    public String getArmName() {
        return armName;
    }

    /**
     * @param armName the armName to set
     */
    public void setArmName(String armName) {
        this.armName = StringUtils.left(armName, Arm.NAME_LENGTH);
    }

    /**
     * @return the armType
     */
    public String getArmType() {
        return armType;
    }

    /**
     * @param armType the armType to set
     */
    public void setArmType(String armType) {
        this.armType = armType;
    }

    /**
     * @return the armDescription
     */
    public String getArmDescription() {
        return armDescription;
    }

    /**
     * @param armDescription the armDescription to set
     */
    public void setArmDescription(String armDescription) {
        this.armDescription = armDescription;
    }

    /**
     * @return the checkBoxEntry
     */
    public String getCheckBoxEntry() {
        return checkBoxEntry;
    }

    /**
     * @param checkBoxEntry the checkBoxEntry to set
     */
    public void setCheckBoxEntry(String checkBoxEntry) {
        this.checkBoxEntry = checkBoxEntry;
    }

    /**
     * @return the selectedArmIdentifier
     */
    public String getSelectedArmIdentifier() {
        return selectedArmIdentifier;
    }

    /**
     * @param selectedArmIdentifier the selectedArmIdentifier to set
     */
    public void setSelectedArmIdentifier(String selectedArmIdentifier) {
        this.selectedArmIdentifier = StringUtils.left(selectedArmIdentifier, PAAttributeMaxLen.LONG_TEXT_LENGTH);
    }
}
