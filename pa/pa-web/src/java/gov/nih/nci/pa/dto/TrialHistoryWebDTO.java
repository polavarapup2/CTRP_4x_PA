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

import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * The Class TrialHistoryWebDTO.
 *
 * @author Anupama Sharma
 * @since 4/21/2009
 */
public class TrialHistoryWebDTO implements Comparable<TrialHistoryWebDTO>, Serializable {
    private static final long serialVersionUID = -177398658732200978L;
    private static final String AMEND = "Amendment";
    private static final String ACTUAL = "Original";
    private static final String UPDATE = "Update";

    /** The submission number. */
    private String submissionNumber;
    
    private String submitter;

    /** The amendment number. */
    private String amendmentNumber;

    /** The amendment date. */
    private String amendmentDate;

    /** The type. */
    private String type;

    /** The submission date. */
    private Date submissionDate;

    /** The amendment reason code. */
    private String amendmentReasonCode;

    /** The documents. */
    private String documents;

    /** The identifier. */
    private String identifier;

    /** The submission number. */
    private double submissionNumberToSort;
    /**
     * lastMileStone 
     */
    private String lastMileStone;
    
    private String rejectComment;
    
    private String comment;
    
    /**
     * Instantiates a new trial history web dto.
     */
    public TrialHistoryWebDTO() {
        // default constructor
    }

    /**
     * The Constructor.
     *
     * @param isoDto the is dto
     * @param initialStatus initialStatus
     */
    public TrialHistoryWebDTO(StudyProtocolDTO isoDto, DocumentWorkflowStatusDTO initialStatus) {
        this.identifier = IiConverter.convertToString(isoDto.getIdentifier());
        this.submissionNumber = IntConverter.convertToInteger(isoDto.getSubmissionNumber()).toString();
        this.amendmentNumber = StConverter.convertToString(isoDto.getAmendmentNumber());
        this.amendmentReasonCode = CdConverter.convertCdToString(isoDto.getAmendmentReasonCode());
        if (isoDto.getAmendmentDate() != null && isoDto.getAmendmentDate().getValue() != null) {
            this.amendmentDate = PAUtil.normalizeDateString(TsConverter.convertToTimestamp(isoDto.getAmendmentDate())
                .toString());
        } else {
            this.amendmentDate = "";
        }
        if (initialStatus != null
                && IvlConverter.convertTs().convertLow(
                        initialStatus.getStatusDateRange()) != null) {
            this.submissionDate = IvlConverter.convertTs().convertLow(
                    initialStatus.getStatusDateRange());
        } else if (isoDto.getDateLastCreated() != null
                && isoDto.getDateLastCreated().getValue() != null) {
            this.submissionDate = TsConverter.convertToTimestamp(isoDto
                    .getDateLastCreated());
        } else {
            this.submissionDate = null;
        }
        this.documents = "";
        this.type = getType(isoDto);
    }

    /**
     * @param isoDto StudyInboxDTO
     */
    public TrialHistoryWebDTO(StudyInboxDTO isoDto) {
        this.identifier = IiConverter.convertToString(isoDto.getIdentifier());
        if (isoDto.getInboxDateRange().getLow() != null
                && isoDto.getInboxDateRange().getLow().getValue() != null) {
            this.submissionDate = TsConverter.convertToTimestamp(isoDto
                    .getInboxDateRange().getLow());
        }        
        this.type = UPDATE;
        
    }

    /**
     * Gets the iso dto.
     *
     * @param isoDto the iso dto
     *
     * @return the iso dto
     */
    public StudyProtocolDTO getIsoDto(StudyProtocolDTO isoDto) {
        isoDto.setAmendmentDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(getAmendmentDate())));
        isoDto.setAmendmentReasonCode(CdConverter.convertStringToCd(getAmendmentReasonCode()));
        isoDto.setAmendmentNumber(StConverter.convertToSt(getAmendmentNumber()));
        return isoDto;
    }

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the type.
     *
     * @param isoDto the iso dto
     *
     * @return the type
     */
    private String getType(StudyProtocolDTO isoDto) {
        int submissionNum = 0;
        submissionNum = IntConverter.convertToInteger(isoDto.getSubmissionNumber());
        if (submissionNum == 1) {
            return ACTUAL;
        }
        return AMEND;
    }

    /**
     * Gets the submission number.
     *
     * @return the submissionNumber
     */
    public String getSubmissionNumber() {
        return submissionNumber;
    }

    /**
     * Sets the submission number.
     *
     * @param submissionNumber the submissionNumber to set
     */
    public void setSubmissionNumber(String submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    /**
     * Gets the amendment number.
     *
     * @return the amendmentNumber
     */
    public String getAmendmentNumber() {
        return amendmentNumber;
    }

    /**
     * Sets the amendment number.
     *
     * @param amendmentNumber the amendmentNumber to set
     */
    public void setAmendmentNumber(String amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }

    /**
     * Gets the amendment date.
     *
     * @return the amendmentDate
     */
    public String getAmendmentDate() {
        return amendmentDate;
    }

    /**
     * Sets the amendment date.
     *
     * @param amendmentDate the amendmentDate to set
     */
    public void setAmendmentDate(String amendmentDate) {
        this.amendmentDate = amendmentDate;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    
    /**
     * @return date
     */
    public String getSubmissionDateAsString() {
        return submissionDate != null ? DateFormatUtils.format(submissionDate,
                PAUtil.DATE_FORMAT) : "";
    }
    
    /**
     * Gets the submission date.
     *
     * @return the submissionDate
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Sets the submission date.
     *
     * @param submissionDate the submissionDate to set
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * Gets the amendment reason code.
     *
     * @return the amendmentReasonCode
     */
    public String getAmendmentReasonCode() {
        return amendmentReasonCode;
    }

    /**
     * Sets the amendment reason code.
     *
     * @param amendmentReasonCode the amendmentReasonCode to set
     */
    public void setAmendmentReasonCode(String amendmentReasonCode) {
        this.amendmentReasonCode = amendmentReasonCode;
    }

    /**
     * Gets the documents.
     *
     * @return the documents
     */
    public String getDocuments() {
        return documents;
    }

    /**
     * Sets the documents.
     *
     * @param documents the documents to set
     */
    public void setDocuments(String documents) {
        this.documents = documents;
    }

    /**
     * @return the submissionNumberToSort
     */
    public double getSubmissionNumberToSort() {
        submissionNumberToSort = Double.parseDouble(submissionNumber);
        return submissionNumberToSort;
    }

    /**
     * @param submissionNumberToSort the submissionNumberToSort to set
     */
    public void setSubmissionNumberToSort(double submissionNumberToSort) {
        this.submissionNumberToSort = submissionNumberToSort;
    }

    /**
     * @return the submitter
     */
    public String getSubmitter() {
        return submitter;
    }

    /**
     * @param submitter the submitter to set
     */
    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    @Override
    public int compareTo(TrialHistoryWebDTO o) {
        if (this.submissionDate == null && o.submissionDate == null) {
            return 0;
        }
        if (this.submissionDate == null) {
            return 1;
        }
        if (o.submissionDate == null) {
            return -1;
        }
        return -this.submissionDate.compareTo(o.submissionDate);
    }
    /**
     * 
     * @return lastMileStone lastMileStone
     */
    public String getLastMileStone() {
       return lastMileStone;
    }
    /**
     * 
     * @param lastMileStone lastMileStone
     */
    public void setLastMileStone(String lastMileStone) {
       this.lastMileStone = lastMileStone;
    }

    /**
     *  
     * @return rejectComment rejectComment
     */
    public String getRejectComment() { 
        return rejectComment;
    }
    /**
     * 
     * @param rejectComment rejectCommentc
     */
    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }
    /**
     * 
     * @return comment comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * 
     * @param comment comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    

}
