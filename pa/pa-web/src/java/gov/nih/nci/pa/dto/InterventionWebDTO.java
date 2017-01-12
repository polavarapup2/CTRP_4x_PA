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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Hugh Reinhart
 * @since 11/01/2008
 */
public class InterventionWebDTO implements Serializable {
    private static final long serialVersionUID = 7601311544392121273L;
    private String identifier;
    private String plannedActivityIdentifier;
    private String name;
    private String description;
    private String otherNames;
    private String leadIndicator;
    private String type;
    private Boolean armAssignment;
    private String ctGovType;
    private String ntTermIdentifier;
    private Map<String, String> alterNames = new HashMap<String, String>();
    private List<String> alterNamesList = new ArrayList<>();
    
    //DRUG & RADIATION Attributes

    private String minDoseValue;
    private String maxDoseValue;
    private String doseUOM;
    private String doseDurationValue;
    private String doseDurationUOM;
    private String doseRegimen;
    private String minDoseTotalValue;
    private String maxDoseTotalValue;
    private String doseTotalUOM;
    private String routeOfAdministration;
    private String doseForm;
    private String doseFrequency;
    private String doseFrequencyCode;

    private String approachSite;
    private String targetSite;


    //PLANNED PROCEDURE Attributes
    private String procedureName;
    private Integer displayOrder;



    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the plannedActivityIdentifier
     */
    public String getPlannedActivityIdentifier() {
        return plannedActivityIdentifier;
    }
    /**
     * @param plannedActivityIdentifier the plannedActivityIdentifier to set
     */
    public void setPlannedActivityIdentifier(String plannedActivityIdentifier) {
        this.plannedActivityIdentifier = plannedActivityIdentifier;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the otherNames
     */
    public String getOtherNames() {
        return otherNames;
    }
    /**
     * @param otherNames the otherNames to set
     */
    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }
    /**
     * @return the leadIndicator
     */
    public String getLeadIndicator() {
        return leadIndicator;
    }
    /**
     * @param leadIndicator the leadIndicator to set
     */
    public void setLeadIndicator(String leadIndicator) {
        this.leadIndicator = leadIndicator;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the armAssignment
     */
    public Boolean getArmAssignment() {
        return armAssignment;
    }
    /**
     * @param armAssignment the armAssignment to set
     */
    public void setArmAssignment(Boolean armAssignment) {
        this.armAssignment = armAssignment;
    }
    /**
     * @return the ctGovType
     */
    public String getCtGovType() {
        return ctGovType;
    }
    /**
     * @param ctGovType the ctGovType to set
     */
    public void setCtGovType(String ctGovType) {
        this.ctGovType = ctGovType;
    }
    /**
     * @return the minDoseValue
     */
    public String getMinDoseValue() {
      return minDoseValue;
    }
    /**
     * @param minDoseValue the minDoseValue to set
     */
    public void setMinDoseValue(String minDoseValue) {
       this.minDoseValue = minDoseValue;
    }
    /**
     * @return the maxDoseValue
     */
     public String getMaxDoseValue() {
      return maxDoseValue;
     }
    /**
     * @param maxDoseValue the maxDoseValue to set
     */
    public void setMaxDoseValue(String maxDoseValue) {
      this.maxDoseValue = maxDoseValue;
    }
    /**
     * @return the doseUOM
     */
     public String getDoseUOM() {
      return doseUOM;
     }
    /**
     * @param doseUOM the doseUOM to set
     */
     public void setDoseUOM(String doseUOM) {
      this.doseUOM = doseUOM;
     }
     /**
      * @return the doseDurationValue
      */
     public String getDoseDurationValue() {
       return doseDurationValue;
     }
 /**
  * @param doseDurationValue the doseDurationValue to set
  */
   public void setDoseDurationValue(String doseDurationValue) {
     this.doseDurationValue = doseDurationValue;
   }
  /**
   * @return the doseDurationUOM
   */
   public String getDoseDurationUOM() {
     return doseDurationUOM;
   }
    /**
     * @param doseDurationUOM the doseDurationUOM to set
     */
     public void setDoseDurationUOM(String doseDurationUOM) {
       this.doseDurationUOM = doseDurationUOM;
     }
    /**
     * @return the doseRegimen
     */
     public String getDoseRegimen() {
       return doseRegimen;
     }
    /**
     * @param doseRegimen the doseRegimen to set
     */
     public void setDoseRegimen(String doseRegimen) {
        this.doseRegimen = doseRegimen;
     }
    /**
     * @return the minDoseTotalValue
     */
     public String getMinDoseTotalValue() {
       return minDoseTotalValue;
    }
    /**
     * @param minDoseTotalValue the minDoseTotalValue to set
     */
    public void setMinDoseTotalValue(String minDoseTotalValue) {
       this.minDoseTotalValue = minDoseTotalValue;
    }
    /**
     * @return the maxDoseTotalValue
     */
     public String getMaxDoseTotalValue() {
       return maxDoseTotalValue;
     }
    /**
     * @param maxDoseTotalValue the maxDoseTotalValue to set
     */
     public void setMaxDoseTotalValue(String maxDoseTotalValue) {
        this.maxDoseTotalValue = maxDoseTotalValue;
     }
     /**
      * @return the doseTotalUOM
      */
      public String getDoseTotalUOM() {
        return doseTotalUOM;
      }
     /**
      * @param doseTotalUOM the doseTotalUOM to set
      */
     public void setDoseTotalUOM(String doseTotalUOM) {
       this.doseTotalUOM = doseTotalUOM;
     }
    /**
     * @return the routeOfAdministration
     */
     public String getRouteOfAdministration() {
      return routeOfAdministration;
     }
    /**
     * @param routeOfAdministration the routeOfAdministration to set
     */
     public void setRouteOfAdministration(String routeOfAdministration) {
      this.routeOfAdministration = routeOfAdministration;
     }
     /**
      * @return the doseForm
      */
      public String getDoseForm() {
       return doseForm;
      }
      /**
       * @param doseForm the doseForm to set
       */
       public void setDoseForm(String doseForm) {
         this.doseForm = doseForm;
       }
     /**
      * @return the doseFrequency
      */
      public String getDoseFrequency() {
       return doseFrequency;
     }
     /**
      * @param doseFrequency the doseFrequency to set
      */
      public void setDoseFrequency(String doseFrequency) {
        this.doseFrequency = doseFrequency;
      }
     /**
      * @return the procedureName
      */
     public String getProcedureName() {
       return procedureName;
     }
    /**
     * @param procedureName the procedureName to set
     */
     public void setProcedureName(String procedureName) {
       this.procedureName = procedureName;
     }
    /**
     * @return the approachSite
     */
    public String getApproachSite() {
       return approachSite;
    }
    /**
     * @param approachSite the approachSite to set
     */
     public void setApproachSite(String approachSite) {
       this.approachSite = approachSite;
     }
    /**
     * @return the targetSite
     */
     public String getTargetSite() {
       return targetSite;
     }
    /**
     * @param targetSite the targetSite to set
     */
     public void setTargetSite(String targetSite) {
       this.targetSite = targetSite;
     }
    /**
     * @return the doseFrequencyCode
     */
     public String getDoseFrequencyCode() {
       return doseFrequencyCode;
     }
    /**
     * @param doseFrequencyCode the doseFrequencyCode to set
     */
     public void setDoseFrequencyCode(String doseFrequencyCode) {
       this.doseFrequencyCode = doseFrequencyCode;
     }
    /**
     * @return the displayOrder
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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
      * @return the alterNames
      */
      public Map<String, String> getAlterNames() {
        return alterNames;
      }
     /**
      * @param alterNames the alterNames to set
      */
      public void setAlterNames(Map<String, String> alterNames) {
        this.alterNames = alterNames;
      }
      

      /**
       * @return the alterNamesList
       */  
    public List<String> getAlterNamesList() {
        return alterNamesList;
    }
    
    /**
     * @param alterNamesList the alterNamesList to set
     */
    public void setAlterNamesList(List<String> alterNamesList) {
        this.alterNamesList = alterNamesList;
    }
}
