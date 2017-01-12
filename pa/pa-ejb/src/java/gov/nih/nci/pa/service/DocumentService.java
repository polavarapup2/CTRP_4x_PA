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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Bala Nair
 * @since 03/23/2009
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public interface DocumentService extends StudyPaService <DocumentDTO> {
    

    /**
     * @param studyProtocolIi Ii 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    /**
     * Returns all documents of the given protocol including TSRs of all previous amendments and
     * original submission (if any). If this protocol has never been amended, the invocation
     * of this method is equivalent to that of {@link #getDocumentsByStudyProtocol(Ii)}.
     * @param studyProtocolIi study ID
     * @return List<DocumentDTO>
     * @throws PAException PAException
     */
    List<DocumentDTO> getDocumentsAndAllTSRByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    /**
     * Returns the document which is of type COMPARISON for a given study protocol.
     * @param studyProtocolIi study ID
     * @return DocumentDTO
     * @throws PAException PAException
     */
    DocumentDTO getComparisonDocumentByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    /**
     * Forces delete of the given document thereby bypassing validation checks.
     * @param documentIi documentIi
     * @throws PAException PAException
     */
    void forceDelete(Ii documentIi) throws PAException;
    

    /**
     * Marks the given trial documents as 'original submission' to indicate that these specific documents
     * originally came with a trial submission or trial amendment. This will allow Trial History to properly display
     * historical documents. 
     * @param savedDocs trial documents
     * @throws PAException PAException
     */
    void markAsOriginalSubmission(List<DocumentDTO> savedDocs) throws PAException;
    
    /**
     * Links the given documents to a {@link StudyInbox} record. This will allow Trial History page to precisely
     * determine which documents came with a trial update. 
     * @param docs docs
     * @param createdInbox createdInbox
     * @throws PAException PAException
     */
    void associateDocumentsWithStudyInbox(List<DocumentDTO> docs,
            StudyInboxDTO createdInbox) throws PAException;
    
    /**
     * Returns <b>original documents</b> submitted with a protocol or an
     * amendment. For pre-3.9 trials, falls back to old functionality.
     * 
     * @param identifier identifier
     * @throws PAException PAException
     * @return List<DocumentDTO>
     */
    List<DocumentDTO> getOriginalDocumentsByStudyProtocol(Ii identifier)
            throws PAException;;

        
    /**
     * Returns <b>original documents</b> submitted with a protocol or an
     * amendment. For pre-3.9 trials, falls back to old functionality.
     * 
     * @param dto
     *            dto
     * @throws PAException
     *             PAException
     * @return List<DocumentDTO>
     */
    List<DocumentDTO> getOriginalDocumentsByStudyInbox(StudyInboxDTO dto) throws PAException;
    
    /**
     * Returns deleted documents for the given trial.
     * @param studyProtocolIi studyProtocolIi
     * @throws PAException
     *             PAException
     * @return List<DocumentDTO> 
     */
    List<DocumentDTO> getDeletedDocumentsByTrial(Ii studyProtocolIi) throws PAException;   
    
    
    /**
     * Deletes the document and specified the reason.
     * @see #delete(Ii).
     * @param docID docID
     * @param reasonToDelete reasonToDelete
     * @throws PAException PAException
     */
    void delete(Ii docID, St reasonToDelete) throws PAException;   
    /**
     * 
     * @param listOfTrialIDs listOfTrialIDs
     * @param type type
     * @return map
     * @throws PAException PAException
     */
    Map<Long, DocumentDTO> getDocumentByIDListAndType(List<Long> listOfTrialIDs
         , DocumentTypeCode type) throws PAException;
    
    /**
     * @param studyProtocolIi PAException
     * @return DocumentDTO list
     * @throws PAException PAException
     */
    List<DocumentDTO> getReportsDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    /**
     * @param docDTO docDTO
     * @throws PAException PAException
     */
    void updateForReview(DocumentDTO docDTO) throws PAException;
}
