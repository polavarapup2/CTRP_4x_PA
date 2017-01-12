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

import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;

import java.io.Serializable;


/**
 * @author Hong Gao
 *
 */
public class StudyIndldeWebDTO implements Serializable {
   private static final long serialVersionUID = -6261047208109210957L;
    private String studyProtocolIi;
    private String expandedAccessStatus;
    private String expandedAccessIndicator;
    private String indldeNumber;
    private String indldeType;
    private String programCode;
    private String grantor;
    private String holderType;
    private String nihInstHolder;
    private String nciDivProgHolder;
    private String id;
    private Boolean exemptIndicator;

    /**
     * @param iso StudyResourcingDTO object
     */
    public StudyIndldeWebDTO(StudyIndldeDTO iso) {
        super();
          this.expandedAccessStatus = iso.getExpandedAccessStatusCode().getCode();
          this.grantor = iso.getGrantorCode().getCode();

          if (iso.getExpandedAccessIndicator().getValue() != null) {
            if (iso.getExpandedAccessIndicator().getValue().toString().equalsIgnoreCase("true")) {
              this.expandedAccessIndicator = "Yes";
            } else {
              this.expandedAccessIndicator = "No";
            }
          }
          //this.expandedAccessIndicator = iso.getExpandedAccessIndicator().getValue().toString();
          this.indldeNumber = iso.getIndldeNumber().getValue();
          this.indldeType = iso.getIndldeTypeCode().getCode();
          this.holderType = iso.getHolderTypeCode().getCode();
          this.nihInstHolder = CdConverter.convertCdToString(iso.getNihInstHolderCode());
          this.nciDivProgHolder = CdConverter.convertCdToString(iso.getNciDivProgHolderCode());
          this.id = iso.getIdentifier().getExtension();
          this.exemptIndicator = BlConverter.convertToBoolean(iso.getExemptIndicator());
    }

    /** .
     *  Default Constructor
     */
    public StudyIndldeWebDTO() {
        super();
    }

    /**
     * @return the studyProtocolIi
     */
    public String getStudyProtocolIi() {
        return studyProtocolIi;
    }

    /**
     * @param studyProtocolIi the studyProtocolIi to set
     */
    public void setStudyProtocolIi(String studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }

    /**
     * @return the expandedAccessStatus
     */
    public String getExpandedAccessStatus() {
        return expandedAccessStatus;
    }

    /**
     * @param expandedAccessStatus the expandedAccessStatus to set
     */
    public void setExpandedAccessStatus(String expandedAccessStatus) {
        this.expandedAccessStatus = expandedAccessStatus;
    }

    /**
     * @return the expandedAccessIndicator
     */
    public String getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }

    /**
     * @param expandedAccessIndicator the expandedAccessIndicator to set
     */
    public void setExpandedAccessIndicator(String expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }

    /**
     * @return the indldeNumber
     */
    public String getIndldeNumber() {
        return indldeNumber;
    }

    /**
     * @param indldeNumber the indldeNumber to set
     */
    public void setIndldeNumber(String indldeNumber) {
        this.indldeNumber = indldeNumber;
    }

    /**
     * @return the indldeType
     */
    public String getIndldeType() {
        return indldeType;
    }

    /**
     * @param indldeType the indldeType to set
     */
    public void setIndldeType(String indldeType) {
        this.indldeType = indldeType;
    }

    /**
     * @return the programCode
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * @param programCode the programCode to set
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * @return the grantor
     */
    public String getGrantor() {
        return grantor;
    }

    /**
     * @param grantor the grantor to set
     */
    public void setGrantor(String grantor) {
        this.grantor = grantor;
    }

    /**
     * @return the holderType
     */
    public String getHolderType() {
        return holderType;
    }

    /**
     * @param holderType the holderType to set
     */
    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }

    /**
     * @return the nihInstHolder
     */
    public String getNihInstHolder() {
        return nihInstHolder;
    }

    /**
     * @param nihInstHolder the nihInstHolder to set
     */
    public void setNihInstHolder(String nihInstHolder) {
        this.nihInstHolder = nihInstHolder;
    }

    /**
     * @return the nciDivProgHolder
     */
    public String getNciDivProgHolder() {
        return nciDivProgHolder;
    }

    /**
     * @param nciDivProgHolder the nciDivProgHolder to set
     */
    public void setNciDivProgHolder(String nciDivProgHolder) {
        this.nciDivProgHolder = nciDivProgHolder;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param exemptIndicator the exemptIndicator to set
     */
    public void setExemptIndicator(Boolean exemptIndicator) {
        this.exemptIndicator = exemptIndicator;
    }

    /**
     * @return the exemptIndicator
     */
    public Boolean getExemptIndicator() {
        return exemptIndicator;
    }

}

