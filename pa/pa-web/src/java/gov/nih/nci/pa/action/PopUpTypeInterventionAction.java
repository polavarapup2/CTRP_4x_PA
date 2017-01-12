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

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.domain.DoseForm;
import gov.nih.nci.pa.domain.DoseFrequency;
import gov.nih.nci.pa.domain.MethodCode;
import gov.nih.nci.pa.domain.RouteOfAdministration;
import gov.nih.nci.pa.domain.TargetSite;
import gov.nih.nci.pa.domain.UnitOfMeasurement;
import gov.nih.nci.pa.dto.LookUpWebDTO;
import gov.nih.nci.pa.service.BaseLookUpService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Common module to call the Intervention type related Search.
 *
 * @author Anupama Sharma
 */
public class PopUpTypeInterventionAction extends ActionSupport {
    private static final long serialVersionUID = 4960297232842560635L;
    private List<LookUpWebDTO> lookupDtos = new ArrayList<LookUpWebDTO>();
    private LookUpWebDTO lookupSearchCriteria = new LookUpWebDTO();

    /**
    *
    * @return String success or failure
    */
   public String type() {
       try {
         String className = ServletActionContext.getRequest().getParameter("className");
         String div = ServletActionContext.getRequest().getParameter("divName");

         lookupSearchCriteria.setType(className);
         lookupSearchCriteria.setDivName(div);

         lookupDtos = null;
       } catch (Exception e) {
           addActionError(e.getLocalizedMessage());
           return ERROR;
       }
       return "lookUp";
   }

   /**
    * @return result
    */
   public String displayLookUpList() {
       return processDisplayLookUp(SUCCESS);
   }

   /**
    * @return result
    */
   public String displayLookUpListDisplayTag() {
       return processDisplayLookUp("lookUp");
   }

   private String processDisplayLookUp(String retvalue) {
       try {
           String code = ServletActionContext.getRequest().getParameter("code");
           String publicId = ServletActionContext.getRequest().getParameter("publicId");
           String description = ServletActionContext.getRequest().getParameter("description");
           String displayName = ServletActionContext.getRequest().getParameter("displayName");
           String className = ServletActionContext.getRequest().getParameter("className");
           String div = ServletActionContext.getRequest().getParameter("divName");
           if (searchCriteriaEmpty(code, publicId, description, displayName)) {
               lookupDtos = null;
               return retvalue;
           }
           // Set the values; so paging will retain them
           lookupSearchCriteria.setCode(code);
           lookupSearchCriteria.setPublicId(publicId);
           lookupSearchCriteria.setDescription(description);
           lookupSearchCriteria.setDisplayName(displayName);
           lookupSearchCriteria.setDivName(div);
           lookupSearchCriteria.setType(className);

           lookupDtos.addAll(getTypeList(className, lookupSearchCriteria));
           return retvalue;
       } catch (Exception e) {
           return retvalue;
       }
   }

   private List<LookUpWebDTO> getTypeList(String className, LookUpWebDTO lookupCriteria) throws PAException {
       List<LookUpWebDTO> lookUpList = new ArrayList<LookUpWebDTO>();
       if ("DoseForm".equalsIgnoreCase(className)) {
           DoseForm criteria = new DoseForm();
           lookUpList = generateWebDTO(new BaseLookUpService<DoseForm>(DoseForm.class), criteria, lookupCriteria);
       } else if ("DoseFrequency".equalsIgnoreCase(className)) {
           DoseFrequency criteria = new DoseFrequency();
           lookUpList = generateWebDTO(new BaseLookUpService<DoseFrequency>(DoseFrequency.class), criteria,
                       lookupCriteria);
       } else if ("RouteOfAdministration".equalsIgnoreCase(className)) {
           RouteOfAdministration criteria = new RouteOfAdministration();
           lookUpList = generateWebDTO(new BaseLookUpService<RouteOfAdministration>(RouteOfAdministration.class),
                       criteria, lookupCriteria);
       } else if ("MethodCode".equalsIgnoreCase(className)) {
           MethodCode criteria = new MethodCode();
           lookUpList = generateWebDTO(new BaseLookUpService<MethodCode>(MethodCode.class), criteria, lookupCriteria);
       } else if ("UnitOfMeasurement".equalsIgnoreCase(className)) {
           UnitOfMeasurement criteria = new UnitOfMeasurement();
           lookUpList = generateWebDTO(new BaseLookUpService<UnitOfMeasurement>(UnitOfMeasurement.class), criteria,
                       lookupCriteria);
       } else if ("TargetSite".equalsIgnoreCase(className)) {
           TargetSite criteria = new TargetSite();
           lookUpList = generateWebDTO(new BaseLookUpService<TargetSite>(TargetSite.class), criteria, lookupCriteria);
       }
       return lookUpList;
   }

   private <T extends AbstractLookUpEntity> List<LookUpWebDTO> generateWebDTO(BaseLookUpService<T> service, T criteria,
           LookUpWebDTO lookupCriteria) throws PAException {
       if (StringUtils.isNotEmpty(lookupCriteria.getCode())) {
           criteria.setCode(lookupCriteria.getCode());
       }

       if (StringUtils.isNotEmpty(lookupCriteria.getPublicId())) {
           criteria.setPublicId(lookupCriteria.getPublicId());
       }

       if (StringUtils.isNotEmpty(lookupCriteria.getDescription())) {
           criteria.setDescription(lookupCriteria.getDescription());
       }

       if (StringUtils.isNotEmpty(lookupCriteria.getDisplayName())) {
           criteria.setDisplayName(lookupCriteria.getDisplayName());
       }

       List<T> results = service.search(new AnnotatedBeanSearchCriteria<T>(criteria));
       List<LookUpWebDTO> lookUpList = new ArrayList<LookUpWebDTO>();
       for (T df :  results) {
           LookUpWebDTO lookupdto = new LookUpWebDTO();
           lookupdto.setCode(df.getCode());
           lookupdto.setDescription(df.getDescription());
           lookupdto.setDisplayName(df.getDisplayName());
           lookupdto.setId(df.getId().toString());
           lookupdto.setPublicId(df.getPublicId());
           lookupdto.setDivName(lookupCriteria.getDivName());
           lookupdto.setType(lookupCriteria.getType());
           lookUpList.add(lookupdto);
       }
       return lookUpList;
   }

   private boolean searchCriteriaEmpty(String code, String publicId, String description, String displayName) {
     boolean criteriaEmpty = false;
     if ("".equals(code) && "".equals(publicId)
         && "".equals(description) && "".equals(displayName)) {
         String message = "Please enter at least one search criteria";
         ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
         criteriaEmpty = true;
     }
     return criteriaEmpty;
   }

   /**
    * @return the lookupDtos
    */
   public List<LookUpWebDTO> getLookupDtos() {
       return lookupDtos;
   }
   /**
    * @param lookupDtos the lookupDtos to set
    */
   public void setLookupDtos(List<LookUpWebDTO> lookupDtos) {
       this.lookupDtos = lookupDtos;
   }
   /**
    * @return the lookupSearchCriteria
    */
   public LookUpWebDTO getLookupSearchCriteria() {
       return lookupSearchCriteria;
   }
   /**
    * @param lookupSearchCriteria the lookupSearchCriteria to set
    */
   public void setLookupSearchCriteria(LookUpWebDTO lookupSearchCriteria) {
       this.lookupSearchCriteria = lookupSearchCriteria;
   }

}
