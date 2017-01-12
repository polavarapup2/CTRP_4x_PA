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

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Action class for Disease/Condition use-case.
 * @author Hugh Reinhart
 * @since 12/1/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class DiseaseAction extends AbstractListEditAction implements Preparable {

    private static final long serialVersionUID = 9154119501160489767L;

    private PDQDiseaseParentServiceRemote pdqDiseaseParentService;
    private PDQDiseaseServiceLocal pdqDiseaseService;
    private StudyDiseaseServiceLocal studyDiseaseService;

    private DiseaseWebDTO disease;
    private List<DiseaseWebDTO> diseaseList;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        super.prepare();
        pdqDiseaseParentService = PaRegistry.getDiseaseParentService();
        pdqDiseaseService = PaRegistry.getDiseaseService();
        studyDiseaseService = PaRegistry.getStudyDiseaseService();
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    @Override
    public String edit() throws PAException {
        StudyDiseaseDTO sd = studyDiseaseService.get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        disease = new DiseaseWebDTO();
        disease.setDiseaseIdentifier(IiConverter.convertToString(sd.getDiseaseIdentifier()));
        disease.setStudyDiseaseIdentifier(getSelectedRowIdentifier());
        disease.setCtGovXmlIndicator(ISOUtil.isBlNull(sd.getCtGovXmlIndicator()) ? null : BlConverter
            .convertToBoolean(sd.getCtGovXmlIndicator()).toString());
        return super.edit();
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    @Override
    public String delete() throws PAException {
        try {
            deleteSelectedObjects();
            return super.delete();
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return execute();
        }
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void deleteObject(Long objectId) throws PAException {
        studyDiseaseService.delete(IiConverter.convertToIi(objectId));        
    }

    /**
     * @return result
     * @throws PAException exception
     */
    @Override
    public String update() throws PAException {
        StudyDiseaseDTO pa = studyDiseaseService.get(IiConverter.convertToIi(disease.getStudyDiseaseIdentifier()));
        pa.setCtGovXmlIndicator(BlConverter.convertToBl(Boolean.valueOf(getDisease().getCtGovXmlIndicator())));
        try {
            studyDiseaseService.update(pa);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        if (hasActionErrors()) {
            return super.edit();
        }
        return super.update();
    }

    /**
     * Method called from pop-up. Loads selected disease.
     * @return result
     * @throws PAException on error.
     */
    public String display() throws PAException {
        setDisease(new DiseaseWebDTO());
        getDisease().setDiseaseIdentifier(ServletActionContext.getRequest().getParameter("diseaseId"));
        return super.create();
    }

    private String buildParentPreferredName(String diseaseId) throws PAException {
        List<PDQDiseaseParentDTO> parentList = pdqDiseaseParentService.getByChildDisease(IiConverter
            .convertToIi(diseaseId));
        StringBuffer ppBuff = new StringBuffer();
        for (PDQDiseaseParentDTO parent : parentList) {
            if (parentList.get(0) != parent) {
                ppBuff.append(", ");
            }
            PDQDiseaseDTO temp = pdqDiseaseService.get(parent.getParentDiseaseIdentifier());
            ppBuff.append(StConverter.convertToString(temp.getPreferredName()));
        }
        return ppBuff.toString();
    }

    /**
     * @throws PAException exception
     */
    @Override
    protected void loadListForm() throws PAException {
        List<DiseaseWebDTO> nl = new ArrayList<DiseaseWebDTO>();
        List<StudyDiseaseDTO> sdList = studyDiseaseService.getByStudyProtocol(getSpIi());
        for (StudyDiseaseDTO sd : sdList) {
            PDQDiseaseDTO d = pdqDiseaseService.get(sd.getDiseaseIdentifier());
            DiseaseWebDTO n = new DiseaseWebDTO();
            n.setStudyDiseaseIdentifier(IiConverter.convertToString(sd.getIdentifier()));
            n.setDiseaseIdentifier(IiConverter.convertToString(d.getIdentifier()));
            n.setCode(StConverter.convertToString(d.getDiseaseCode()));
            n.setConceptId(StConverter.convertToString(d.getNtTermIdentifier()));
            Bl xmlIndicator = sd.getCtGovXmlIndicator();
            if (!ISOUtil.isBlNull(xmlIndicator) && BlConverter.convertToBoolean(xmlIndicator)) {
                n.setCtGovXmlIndicator("Yes");
            } else {
                n.setCtGovXmlIndicator("No");
            }
            n.setMenuDisplayName(StConverter.convertToString(d.getDisplayName()));
            n.setParentPreferredName(buildParentPreferredName(IiConverter.convertToString(d.getIdentifier())));
            n.setPreferredName(StConverter.convertToString(d.getPreferredName()));
            nl.add(n);
        }
        setDiseaseList(nl);
    }

    /**
     * Uses data from disease.diseaseIdentifier, disease.lead, and disease.studyDiseaseIdentifier. Reads remainder from
     * database.
     * @throws PAException exception
     */
    @Override
    protected void loadEditForm() throws PAException {
        if (disease == null || StringUtils.isEmpty(disease.getDiseaseIdentifier())) {
            disease = new DiseaseWebDTO();
        } else {
            Ii ii = IiConverter.convertToIi(disease.getDiseaseIdentifier());
            PDQDiseaseDTO dto = pdqDiseaseService.get(ii);
            disease.setCode(StConverter.convertToString(dto.getDiseaseCode()));
            disease.setConceptId(StConverter.convertToString(dto.getNtTermIdentifier()));
            disease.setMenuDisplayName(StConverter.convertToString(dto.getDisplayName()));
            disease.setPreferredName(StConverter.convertToString(dto.getPreferredName()));
            disease.setParentPreferredName(buildParentPreferredName(disease.getDiseaseIdentifier()));
        }
    }

    /**
     * @return the disease
     */
    public DiseaseWebDTO getDisease() {
        return disease;
    }

    /**
     * @param disease the disease to set
     */
    public void setDisease(DiseaseWebDTO disease) {
        this.disease = disease;
    }

    /**
     * @return the diseaseList
     */
    public List<DiseaseWebDTO> getDiseaseList() {
        return diseaseList;
    }

    /**
     * @param diseaseList the diseaseList to set
     */
    public void setDiseaseList(List<DiseaseWebDTO> diseaseList) {
        this.diseaseList = diseaseList;
    }

    /**
     * @param pdqDiseaseParentService the pdqDiseaseParentService to set
     */
    public void setPdqDiseaseParentService(PDQDiseaseParentServiceRemote pdqDiseaseParentService) {
        this.pdqDiseaseParentService = pdqDiseaseParentService;
    }

    /**
     * @param pdqDiseaseService the pdqDiseaseService to set
     */
    public void setPdqDiseaseService(PDQDiseaseServiceLocal pdqDiseaseService) {
        this.pdqDiseaseService = pdqDiseaseService;
    }

    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
    }

}
