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
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Convert Document from domain to DTO.
 *
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", 
    "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
public class DocumentConverter extends AbstractDocumentConverter<DocumentDTO, Document> {
    
    private static final Logger LOG = Logger.getLogger(DocumentConverter.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentDTO convertFromDomainToDto(Document doc) {
        DocumentDTO docDTO = new DocumentDTO();
        convertFromDomainToDto(doc, docDTO);
        return docDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document convertFromDtoToDomain(DocumentDTO docDTO) {
        Document doc = new Document();
        convertFromDtoToDomain(docDTO, doc);
        return doc;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("deprecation")
    @Override
    public void convertFromDomainToDto(Document doc, DocumentDTO docDTO) {
        super.convertFromDomainToDto(doc, docDTO);
        docDTO.setActiveIndicator(BlConverter.convertToBl(doc.getActiveIndicator()));
        docDTO.setInactiveCommentText(StConverter.convertToSt(doc.getInactiveCommentText()));
        docDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(doc.getStudyProtocol().getId()));
        docDTO.setDateLastUpdated(TsConverter.convertToTs(doc.getDateLastUpdated()));
        docDTO.setDateLastCreated(TsConverter.convertToTs(doc.getDateLastCreated()));
        docDTO.setOriginal(BlConverter.convertToBl(doc.getOriginal()));
        docDTO.setDeleted(BlConverter.convertToBl(doc.getDeleted()));
        docDTO.setStudyInboxIdentifier(IiConverter.convertToIi(doc
                .getStudyInbox() != null ? doc.getStudyInbox().getId() : null));
        if (doc.getUserLastUpdated() != null) {
            docDTO.setUserLastUpdated(StConverter.convertToSt(doc
                    .getUserLastUpdated().getLoginName()));
        }
        if (doc.getUserLastCreated() != null) {
            docDTO.setUserLastCreated(StConverter.convertToSt(doc
                    .getUserLastCreated().getLoginName()));
        }
        docDTO.setCtroUserReviewDateTime((TsConverter.convertToTs(doc.getCtroUserCreatedDate())));
        docDTO.setCcctUserReviewDateTime((TsConverter.convertToTs(doc.getCcctUserCreatedDate())));
        if (doc.getCtroUser() != null) {
            docDTO.setCtroUserName(StConverter.convertToSt(doc
                    .getCtroUser().getLoginName()));
        }
        if (doc.getCcctUserName() != null) {
            docDTO.setCcctUserName(StConverter.convertToSt(doc.getCcctUserName()));
        }
        
        if (doc.getCtroUser() != null) {
        docDTO.setCtroUserId(doc.getCtroUser().getUserId());
        }
        
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void convertFromDtoToDomain(DocumentDTO docDTO, Document doc) {
        super.convertFromDtoToDomain(docDTO, doc);
        doc.setActiveIndicator(BlConverter.convertToBoolean(docDTO.getActiveIndicator()));
        doc.setInactiveCommentText(StConverter.convertToString(docDTO.getInactiveCommentText()));
        doc.setOriginal(BlConverter.convertToBoolean(docDTO.getOriginal()));
        doc.setDeleted(BlConverter.convertToBoolean(docDTO.getDeleted()));
        
        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(docDTO.getStudyProtocolIdentifier()));
        doc.setStudyProtocol(spBo);

        if (ISOUtil.isBlNull(docDTO.getActiveIndicator())) {
            doc.setActiveIndicator(Boolean.TRUE);
        }
        
        if (!ISOUtil.isIiNull(docDTO.getStudyInboxIdentifier())) {
            StudyInbox studyInbox = new StudyInbox();
            studyInbox.setId(IiConverter.convertToLong(docDTO
                    .getStudyInboxIdentifier()));
            doc.setStudyInbox(studyInbox);
        }
        
        if (!ISOUtil.isTsNull(docDTO.getDateLastCreated())) {
            doc.setDateLastCreated(TsConverter.convertToTimestamp(docDTO.getDateLastCreated()));
        }
        if (!ISOUtil.isTsNull(docDTO.getDateLastUpdated())) {
            doc.setDateLastUpdated(TsConverter.convertToTimestamp(docDTO.getDateLastUpdated()));
        }
        if (!ISOUtil.isTsNull(docDTO.getCtroUserReviewDateTime())) {
            doc.setCtroUserCreatedDate(TsConverter.convertToTimestamp(docDTO.getCtroUserReviewDateTime()));
        }
        if (!ISOUtil.isTsNull(docDTO.getCcctUserReviewDateTime())) {
            doc.setCcctUserCreatedDate(TsConverter.convertToTimestamp(docDTO.getCcctUserReviewDateTime()));
        }
        
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            
            if (docDTO.getCtroUserId() != null && docDTO.getCtroUserId() > 0) {
                 User ctroUser = upManager.getUserById(docDTO.getCtroUserId().toString());
                doc.setCtroUser(ctroUser);
            }    
            
            if (!ISOUtil.isStNull(docDTO.getCcctUserName())) {
               doc.setCcctUserName(StConverter.convertToString(docDTO.getCcctUserName()));
            }
        } catch (Exception e) {
            LOG.error("Exception in setting ctroUser for Document: "
                    + docDTO.getIdentifier() , e);
        }
        
        setUserFields(docDTO, doc);

        
    }

    private void setUserFields(DocumentDTO docDTO, Document doc) {
        String isoStUserLastCreated = StConverter.convertToString(docDTO
                .getUserLastCreated());
        if (StringUtils.isNotEmpty(isoStUserLastCreated)) {
            try {
                doc.setUserLastCreated(AbstractStudyProtocolConverter
                        .getCsmUserUtil().getCSMUser(isoStUserLastCreated));
            } catch (Exception e) {
                LOG.error("Exception in setting userLastCreated for Document: "
                        + docDTO.getIdentifier() + ", for username: "
                        + isoStUserLastCreated, e);
            }
        }

        String isoStUserLastUpdated = StConverter.convertToString(docDTO
                .getUserLastUpdated());
        if (StringUtils.isNotEmpty(isoStUserLastUpdated)) {
            try {
                doc.setUserLastUpdated(AbstractStudyProtocolConverter
                        .getCsmUserUtil().getCSMUser(isoStUserLastUpdated));
            } catch (Exception e) {
                LOG.error("Exception in setting userLastUpdated for Document: "
                        + docDTO.getIdentifier() + ", for username: "
                        + isoStUserLastUpdated, e);
            }
        }
    }
}
