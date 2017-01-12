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

import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.ISOUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO class for displaying study status history as a list.
 * 
 * @author Hugh Reinhart
 * @since 09/10/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class StudyOverallStatusWebDTO implements Serializable {
    private static final long serialVersionUID = 1309182659162634720L;
    private String statusCode;
    private String statusDate;
    private Date statusDateRaw;
    private String reason;
    private String comments;
    private String updatedBy;
    private Date updatedOn;
    private boolean systemCreated;
    private Long id;
    
    private boolean editable;
    private boolean deletable;
    private boolean undoable;
    
    private String errors;
    private String warnings;
    

    /**
     * @param dto The iso dto object.
     * @throws PAException  PAException
     */
    public StudyOverallStatusWebDTO(StudyOverallStatusDTO dto) throws PAException {
        this.id = IiConverter.convertToLong(dto.getIdentifier());
        this.statusCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode()).getCode();
        this.statusDate = TsConverter.convertToString(dto.getStatusDate());
        this.statusDateRaw = TsConverter.convertToTimestamp(dto.getStatusDate());
        this.reason = StConverter.convertToString(dto.getReasonText());
        this.comments = StConverter.convertToString(dto.getAdditionalComments());
        this.systemCreated = BlConverter.convertToBool(dto.getSystemCreated());
        this.updatedOn = TsConverter.convertToTimestamp(dto.getLastUpdatedDate());
        this.updatedBy = ISOUtil.isStNull(dto.getLastUpdatedUser()) ? ""
                : CsmUserUtil.getDisplayUsername(CSMUserService.getInstance()
                        .getCSMUser(
                                StConverter.convertToString(dto
                                        .getLastUpdatedUser())));      
        this.errors = StConverter.convertToString(dto.getErrors());
        this.warnings = StConverter.convertToString(dto.getWarnings());
                
    }

    /**
     * Default constructor.
     */
    public StudyOverallStatusWebDTO() {
        super();
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the systemCreated
     */
    public boolean isSystemCreated() {
        return systemCreated;
    }

    /**
     * @param systemCreated the systemCreated to set
     */
    public void setSystemCreated(boolean systemCreated) {
        this.systemCreated = systemCreated;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the deletable
     */
    public boolean isDeletable() {
        return deletable;
    }

    /**
     * @param deletable the deletable to set
     */
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    /**
     * @return the undoable
     */
    public boolean isUndoable() {
        return undoable;
    }

    /**
     * @param undoable the undoable to set
     */
    public void setUndoable(boolean undoable) {
        this.undoable = undoable;
    }
    

    /**
     * @param statusDateRaw the statusDateRaw to set
     */
    public void setStatusDateRaw(Date statusDateRaw) {
        this.statusDateRaw = statusDateRaw;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @param updatedOn the updatedOn to set
     */
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(String errors) {
        this.errors = errors;
    }

    /**
     * @param warnings the warnings to set
     */
    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @return the updatedOn
     */
    public Date getUpdatedOn() {
        return updatedOn;
    }

    /**
     * @return the statusDateRaw
     */
    public Date getStatusDateRaw() {
        return statusDateRaw;
    }

    /**
     * @return the errors
     */
    public String getErrors() {
        return errors;
    }

    /**
     * @return the warnings
     */
    public String getWarnings() {
        return warnings;
    }
  
}
