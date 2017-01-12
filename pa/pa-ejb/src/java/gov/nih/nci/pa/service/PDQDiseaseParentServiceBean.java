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
package gov.nih.nci.pa.service;

import static gov.nih.nci.pa.service.AbstractBaseIsoService.ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SCIENTIFIC_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUPER_ABSTRACTOR_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseParent;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.convert.PDQDiseaseParentConverter;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.PDQDiseaseParentSortCriterion;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.jboss.ejb3.annotation.SecurityDomain;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
* @author Hugh Reinhart
* @since 11/30/2008
*/
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed({ SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE, ABSTRACTOR_ROLE,
    SCIENTIFIC_ABSTRACTOR_ROLE, SUPER_ABSTRACTOR_ROLE })
public class PDQDiseaseParentServiceBean
        extends AbstractBaseIsoService<PDQDiseaseParentDTO, PDQDiseaseParent, PDQDiseaseParentConverter>
        implements PDQDiseaseParentServiceRemote {
    
 @EJB
private PDQDiseaseServiceLocal diseaseService;

    /**
     * @param ii index of disease
     * @return list of DiseaseParent associations for children of disease
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PDQDiseaseParentDTO> getByChildDisease(Ii ii) throws PAException {
        PDQDiseaseParent criteria = new PDQDiseaseParent();
        PDQDisease disease = new PDQDisease();
        disease.setId(IiConverter.convertToLong(ii));
        disease.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        criteria.setDisease(disease);
        PageSortParams<PDQDiseaseParent> params = new PageSortParams<PDQDiseaseParent>(PAConstants.MAX_SEARCH_RESULTS,
                    0, PDQDiseaseParentSortCriterion.DISEASE_PARENT_ID, false);
        List<PDQDiseaseParent> results = search(new AnnotatedBeanSearchCriteria<PDQDiseaseParent>(criteria), params);
        return convertFromDomainToDTOs(results);
    }

    /**
     * @param ii index of disease
     * @return list of DiseaseParent associations for parents of disease
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PDQDiseaseParentDTO> getByParentDisease(Ii ii) throws PAException {
        PDQDiseaseParent criteria = new PDQDiseaseParent();
        PDQDisease disease = new PDQDisease();
        disease.setId(IiConverter.convertToLong(ii));
        disease.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        criteria.setParentDisease(disease);
        PageSortParams<PDQDiseaseParent> params = new PageSortParams<PDQDiseaseParent>(PAConstants.MAX_SEARCH_RESULTS,
                    0, PDQDiseaseParentSortCriterion.DISEASE_PARENT_ID, false);
        List<PDQDiseaseParent> results = search(new AnnotatedBeanSearchCriteria<PDQDiseaseParent>(criteria), params);
        return convertFromDomainToDTOs(results);
    }

    /**
     * @param iis array of indexes of diseases
     * @return list of DiseaseParent associations for children of disease
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PDQDiseaseParentDTO> getByChildDisease(Ii[] iis) throws PAException {
        ArrayList<PDQDiseaseParentDTO> resultList = new ArrayList<PDQDiseaseParentDTO>();
        for (Ii ii : iis) {
            resultList.addAll(getByChildDisease(ii));
        }
        return resultList;
    }
    
    
   
/**
     * @param currDisease diseaseDTO
     * @param parents parents list to be deleted
     * @param children children list to be added
     * @param parentsToAdd parents list to add
     * @param childsToAdd  childs list to add
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void syncDisease(PDQDiseaseDTO currDisease, List<PDQDiseaseParentDTO> parents,
            List<PDQDiseaseParentDTO> children,
            List<PDQDiseaseParentDTO> parentsToAdd,
            List<PDQDiseaseParentDTO> childsToAdd) throws PAException {
        try {
            diseaseService.update(currDisease);
            // delete parents
            deleteList(parents);
            // delete childs
            deleteList(children);
            
            Map<String , PDQDiseaseParentDTO > parentsMap = new HashMap<String , PDQDiseaseParentDTO>();
            Map<String , PDQDiseaseParentDTO > childsMap = new HashMap<String , PDQDiseaseParentDTO>();
            
            //remove duplicates before saving to database
            for (PDQDiseaseParentDTO diseaseParentDTO : parentsToAdd) {
                parentsMap.put(diseaseParentDTO.getDiseaseIdentifier().getExtension() 
                        + diseaseParentDTO.getParentDiseaseIdentifier().getExtension()
                        , diseaseParentDTO);
            }
            
            for (PDQDiseaseParentDTO diseaseParentDTO : childsToAdd) {
                
                childsMap.put(diseaseParentDTO.getDiseaseIdentifier().getExtension() 
                        + diseaseParentDTO.getParentDiseaseIdentifier().getExtension()
                        , diseaseParentDTO);
            }
                    
            // add parents
            for (String key : parentsMap.keySet()) {
                    create(parentsMap.get(key));
            }
            
            for (String key : childsMap.keySet()) {
                create(childsMap.get(key));
             }
           
        } catch (Exception e) {
            throw new PAException(e.getMessage());
        }
    }

    private void deleteList(List<PDQDiseaseParentDTO> pDQDiseaseParentDTOList)
            throws Exception {

        for (PDQDiseaseParentDTO diseaseParentDTO : pDQDiseaseParentDTOList) {
            if (get(diseaseParentDTO.getIdentifier()) != null) {
                delete(diseaseParentDTO.getIdentifier());
            }

        }
    }

 /**
 * @param diseaseService diseaseService
 */
public void setDiseaseService(PDQDiseaseServiceLocal diseaseService) {
        this.diseaseService = diseaseService;
    }

}
