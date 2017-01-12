/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyInboxSectionCode;
import gov.nih.nci.pa.enums.StudyInboxTypeCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudyInboxConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TrialUpdatesRecorder;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Anupama Sharma
 * @since 09/08/2009
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyInboxServiceBean // NOPMD
    extends AbstractStudyIsoService<StudyInboxDTO, StudyInbox, StudyInboxConverter> 
        implements StudyInboxServiceLocal { //NOPMD

    private static final String PARTICIPATING_SITE_INFORMATION_WAS_CHANGED = 
            "Participating Site information was changed.";
    /** id for inboxDateRange.open. */
    public static final int FN_DATE_OPEN = 1;
    /** id for inboxDateRange.close. */
    public static final int FN_DATE_CLOSE = 2;
    @EJB
    private DocumentWorkflowStatusServiceLocal docWrkFlowStatusService;
    @EJB
    private ProtocolQueryServiceLocal protocolQueryServiceLocal;
    @EJB
    private StudyResourcingServiceLocal studyResourcingServiceLocal;
    @EJB
    private StudySiteServiceLocal studySiteServiceLocal;
    @EJB
    private DocumentServiceLocal documentServiceLocal;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    
    /**
     * 
     */
    public static final String SEPARATOR = TrialUpdatesRecorder.SEPARATOR;

    /**
     * @param dto dto
     * @return dto
     * @throws PAException exception
     */
    @Override
    public StudyInboxDTO create(StudyInboxDTO dto) throws PAException {
        setTimeIfToday(dto);
        dateRules(dto);
        return super.create(dto);
    }

   
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyInboxServiceLocal#create
     * (java.util.List, gov.nih.nci.iso21090.Ii, gov.nih.nci.pa.dto.StudyProtocolQueryDTO, 
     * gov.nih.nci.pa.iso.dto.StudyResourcingDTO, java.util.List)
     */
    @Override
    // CHECKSTYLE:OFF
    public String create( // NOPMD
            List<DocumentDTO> documentDTOs,
            List<DocumentDTO> existingDocs,
            Ii studyProtocolIi, // NOPMD
            StudyProtocolQueryDTO originalDTO,
            List<StudyResourcingDTO> originalSummary4,
            List<StudySiteDTO> originalSites,
            List<StudySiteAccrualStatusDTO> originalAccrualStatuses,
            List<DocumentDTO> updatedDocs) throws PAException {
        StringBuilder comments = new StringBuilder();
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException(" Study Protocol Identifier cannot be null");
        }       
        DocumentWorkflowStatusDTO dws = docWrkFlowStatusService.getCurrentByStudyProtocol(studyProtocolIi);
        if (dws == null) {
            throw new PAException(" Document workflow status is null for StudyProtocol identifier "
                + studyProtocolIi.getExtension());
        }
        
        StudyProtocolQueryDTO updatedDTO = protocolQueryServiceLocal
                .getTrialSummaryByStudyProtocolId(IiConverter
                        .convertToLong(studyProtocolIi));
        List<StudyResourcingDTO> updatedSummary4 = studyResourcingServiceLocal
                .getSummary4ReportedResourcing(studyProtocolIi);
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter
                .convertStringToCd(StudySiteFunctionalCode.TREATING_SITE
                        .getCode()));
        List<StudySiteDTO> updatedSites = studySiteServiceLocal
                .getByStudyProtocol(studyProtocolIi, srDTO);
        
        comments.append(createComments(originalDTO, updatedDTO));
        for (StudyResourcingDTO dto : originalSummary4) {
            for (StudyResourcingDTO updto : updatedSummary4) {
                if (!ISOUtil.isIiNull(updto.getIdentifier()) && !ISOUtil.isIiNull(dto.getIdentifier())
                        && updto.getIdentifier().getExtension().equals(dto.getIdentifier().getExtension())) {
                    recordChange(
                            dto.getOrganizationIdentifier().getExtension(),
                            updto.getOrganizationIdentifier().getExtension(),
                            "Data Table 4 Funding Sponsor was changed." + SEPARATOR, comments);
                    break;
                }
            }
        }
        
        comments.append(createComments(originalSites, updatedSites));
        comments.append(createCommentsForStudySiteAccrualStatus(originalAccrualStatuses));
        comments.append(createDeletedDocsComments(existingDocs));
        comments.append(createComments(documentDTOs));        
        StringBuilder abstractionErrors = new PAServiceUtils()
                .createAbstractionValidationErrorsTable(studyProtocolIi, dws); 
        
        // Store validation errors resulted from the update separately.   
        if (abstractionErrors.length() > 0 && comments.length() > 0) {
            createStudyInboxRecord(studyProtocolIi, abstractionErrors, null, StudyInboxTypeCode.VALIDATION);
        }        
        
        // Store changes resulted from the update.
        if (comments.length() > 0) {
            createStudyInboxRecord(studyProtocolIi, comments, updatedDocs, StudyInboxTypeCode.UPDATE);
        }
                
        return comments.toString();
    }
    // CHECKSTYLE:ON

    


    /**
     * @param studyProtocolIi
     * @param comments
     * @param docs 
     * @throws PAException
     */
    private void createStudyInboxRecord(Ii studyProtocolIi,
            StringBuilder comments, List<DocumentDTO> docs, StudyInboxTypeCode typeCode) throws PAException {
        StudyInboxDTO studyInboxDTO = new StudyInboxDTO();
        studyInboxDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studyInboxDTO.setInboxDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()),
                                                                              null));
        studyInboxDTO.setComments(StConverter.convertToSt(comments.toString()));
        studyInboxDTO.setTypeCode(CdConverter.convertToCd(typeCode));
        StudyInboxDTO createdInbox = create(studyInboxDTO);
        
        if (CollectionUtils.isNotEmpty(docs)) {
            documentServiceLocal.associateDocumentsWithStudyInbox(docs, createdInbox);
        }
    }

    private StringBuilder createComments(List<StudySiteDTO> originalSites,
            List<StudySiteDTO> updatedSites) {
        StringBuilder comments = new StringBuilder();
        if (CollectionUtils.isNotEmpty(originalSites)
                && CollectionUtils.isNotEmpty(updatedSites)
                && originalSites.size() == updatedSites.size()) {
            for (int i = 0; i < originalSites.size(); i++) {
                StudySiteDTO original = originalSites.get(i);
                StudySiteDTO updated = updatedSites.get(i);
                createComments(original, updated, comments);
            }
        }
        return comments;
    }

    @SuppressWarnings({ "rawtypes", "PMD.CyclomaticComplexity" })
    private void createComments(StudySiteDTO original, StudySiteDTO updated,
            StringBuilder comments) {
        IvlConverter conv = new IvlConverter(Ts.class);
        if (!ObjectUtils.equals(original.getLocalStudyProtocolIdentifier(),
                updated.getLocalStudyProtocolIdentifier())
                || !ObjectUtils.equals(original.getProgramCodeText(),
                        updated.getProgramCodeText())
                || !ObjectUtils.equals(original.getStatusCode(),
                        updated.getStatusCode())
                || !ObjectUtils.equals(original.getAccrualDateRange(),
                        updated.getAccrualDateRange())
                || !ObjectUtils
                        .equals(conv.convertHighToString(original
                                .getStatusDateRange()), conv
                                .convertHighToString(updated
                                        .getStatusDateRange()))) {
            final String msg = PARTICIPATING_SITE_INFORMATION_WAS_CHANGED + SEPARATOR;
            appendIfNotExists(comments, msg);
        }
    }


    /**
     * @param comments
     * @param msg
     */
    private void appendIfNotExists(StringBuilder comments, final String msg) {
        if (comments.indexOf(msg) == -1) {
            comments.append(msg);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "PMD.CyclomaticComplexity" })
    private StringBuilder createCommentsForStudySiteAccrualStatus(
            List<StudySiteAccrualStatusDTO> originalAccrualStatuses)
            throws PAException {
        StringBuilder comments = new StringBuilder();
        for (StudySiteAccrualStatusDTO original : originalAccrualStatuses) {
            if (original != null) {
                StudySiteAccrualStatusDTO updated = studySiteAccrualStatusService
                        .getCurrentStudySiteAccrualStatusByStudySite(original.getStudySiteIi());
                if (!ObjectUtils.equals(original.getStatusCode(),
                        updated.getStatusCode())
                        || !ObjectUtils.equals(TsConverter
                                .convertToString(original.getStatusDate()),
                                TsConverter.convertToString(updated
                                        .getStatusDate()))) {
                    final String msg = PARTICIPATING_SITE_INFORMATION_WAS_CHANGED
                            + SEPARATOR;
                    appendIfNotExists(comments, msg);
                }
            }
        }
        return comments;
    }

    private StringBuilder createComments(StudyProtocolQueryDTO originalDTO,
            StudyProtocolQueryDTO updatedDTO) {
        StringBuilder comments = new StringBuilder();
        if (originalDTO != null && updatedDTO != null) {
            recordChange(originalDTO.getLeadOrganizationName(),
                    updatedDTO.getLeadOrganizationName(),
                    "Lead Organization was changed." + SEPARATOR, comments);
            recordChange(originalDTO.getLocalStudyProtocolIdentifier(),
                    updatedDTO.getLocalStudyProtocolIdentifier(),
                    "Lead Organization Trial Identifier was changed." + SEPARATOR, comments);
            recordChange(originalDTO.getNctNumber(),
                    updatedDTO.getNctNumber(),
                    StringUtils.isEmpty(originalDTO.getNctNumber()) ? "ClinicalTrials.gov Identifier was added." 
                            : "ClinicalTrials.gov Identifier was changed." + SEPARATOR, comments);
            recordChange(originalDTO.getOfficialTitle(),
                    updatedDTO.getOfficialTitle(),
                    "Title was changed." + SEPARATOR, comments);           
            recordChange(originalDTO.getPrimaryPurpose(),
                    updatedDTO.getPrimaryPurpose(),
                    "Primary Purpose was changed." + SEPARATOR, comments);           
            recordChange(originalDTO.getPhaseCode(),
                    updatedDTO.getPhaseCode(),
                    "Phase was changed." + SEPARATOR, comments);    
            recordChange(originalDTO.getStudySubtypeCode(),
                    updatedDTO.getStudySubtypeCode(),
                    "Non-interventional Trial Type was changed." + SEPARATOR, comments);
            recordChange(originalDTO.getStudyModelCode(),
                    updatedDTO.getStudyModelCode(),
                    "Study Model Code was changed." + SEPARATOR, comments);
            recordChange(originalDTO.getStudyModelOtherText(),
                    updatedDTO.getStudyModelOtherText(),
                    "Study Model Description was changed." + SEPARATOR, comments);            
            recordChange(originalDTO.getTimePerspectiveCode(),
                    updatedDTO.getTimePerspectiveCode(),
                    "Time Perspective Code was changed." + SEPARATOR, comments);           
            recordChange(originalDTO.getTimePerspectiveOtherText(),
                    updatedDTO.getTimePerspectiveOtherText(),
                    "Time Perspective Description was changed." + SEPARATOR, comments);          
            
            
        }
        return comments;
    }

    private void recordChange(Object v1, Object v2, String comment,
            StringBuilder comments) {
        if (v1 == null  && v2 == null) {
            //no change so do nothing
        } else if (v1 == null  || v2 == null) {
            //There is a difference between the two so add the comment, one is null the other is not
            comments.append(comment);
        } else if (!StringUtils.equals(StringUtils.trim(v1.toString()), // NOPMD
                StringUtils.trim(v2.toString()))) { // NOPMD
            //There is a difference between the two so add the comment
            comments.append(comment);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyInboxDTO update(StudyInboxDTO dto) throws PAException {
        StudyInboxDTO wrkDto = super.get(dto.getIdentifier());
        if (wrkDto != null) {
            wrkDto.getInboxDateRange().setHigh(dto.getInboxDateRange().getHigh());
            wrkDto.setComments(ISOUtil.isStNull(dto.getComments()) ? wrkDto
                    .getComments() : dto.getComments());
            wrkDto.setAdmin(ISOUtil.isBlNull(dto.getAdmin()) ? wrkDto
                    .getAdmin() : dto.getAdmin());
            wrkDto.setScientific(ISOUtil.isBlNull(dto.getScientific()) ? wrkDto
                    .getScientific() : dto.getScientific());
            setTimeIfToday(wrkDto);
            dateRules(wrkDto);
        }
        return super.update(wrkDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyInboxDTO> getOpenInboxEntries(Ii studyProtocolIi) throws PAException {
        Criteria crit = PaHibernateUtil
                .getCurrentSession()
                .createCriteria(StudyInbox.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.isNull("closeDate"))
                .addOrder(Order.desc("openDate"))
                .createCriteria("studyProtocol")
                .add(Restrictions.eq("id",
                        IiConverter.convertToLong(studyProtocolIi)));
        try {
            @SuppressWarnings("unchecked")
            List<StudyInbox> entries = crit.list();
            return convertFromDomainToDTOs(entries);
        } catch (Exception e) {
            throw new PAException("Error retrieving open inbox entries.", e);
        }
    }

    private void setTimeIfToday(StudyInboxDTO dto) {
        Timestamp now = new Timestamp(new Date().getTime());
        Timestamp tsLow = IvlConverter.convertTs().convertLow(dto.getInboxDateRange());
        Timestamp tsHigh = IvlConverter.convertTs().convertHigh(dto.getInboxDateRange());
        if (tsLow != null && tsLow.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsLow = now;
        }
        if (tsHigh != null && tsHigh.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsHigh = now;
        }
        dto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(tsLow, tsHigh));
    }

    private void dateRules(StudyInboxDTO dto) throws PAException {
        Timestamp low = IvlConverter.convertTs().convertLow(dto.getInboxDateRange());
        if (low == null) {
            throw new PAFieldException(FN_DATE_OPEN, "Open date is required.");
        }
        Timestamp now = new Timestamp(new Date().getTime());
        if (now.before(low)) {
            throw new PAFieldException(FN_DATE_OPEN, "Open dates must be only past or current dates.");
        }
        Timestamp high = IvlConverter.convertTs().convertHigh(dto.getInboxDateRange());
        if (high != null) {
            if (now.before(high)) {
                throw new PAFieldException(FN_DATE_CLOSE, "Close dates must be only past or current dates.");
            }
            if (high.before(low)) {
                throw new PAFieldException(FN_DATE_CLOSE,
                    "Close date must be bigger than open date for the same open record.");
            }
        }
    }

    private StringBuilder createComments(List<DocumentDTO> documentDTOs) {
        StringBuilder comments = new StringBuilder();
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            for (DocumentDTO doc : documentDTOs) {
                String msg = CdConverter.convertCdToString(doc.getTypeCode())
                        + " Document was uploaded." + SEPARATOR;
                appendIfNotExists(comments, msg);
            }
        }
        return comments;
    }
    
    private StringBuilder createDeletedDocsComments(
            List<DocumentDTO> existingDocs) throws PAException {
        StringBuilder comments = new StringBuilder();
        if (CollectionUtils.isNotEmpty(existingDocs)) {
            for (DocumentDTO doc : existingDocs) {
                DocumentDTO latestDoc = documentServiceLocal.get(doc
                        .getIdentifier());
                if (latestDoc != null
                        && Boolean.FALSE.equals(BlConverter
                                .convertToBoolean(latestDoc
                                        .getActiveIndicator()))) {
                    comments.append(
                            CdConverter.convertCdToString(doc.getTypeCode()))
                            .append(" Document was deleted." + SEPARATOR);
                }
            }
        }
        return comments;
    }
    

    /**
     * @param docWrkFlowStatusService the docWrkFlowStatusService to set
     */
    public void setDocWrkFlowStatusService(
            DocumentWorkflowStatusServiceLocal docWrkFlowStatusService) {
        this.docWrkFlowStatusService = docWrkFlowStatusService;
    }
    

    /**
     * @return the protocolQueryServiceLocal
     */
    public ProtocolQueryServiceLocal getProtocolQueryServiceLocal() {
        return protocolQueryServiceLocal;
    }

    /**
     * @param protocolQueryServiceLocal the protocolQueryServiceLocal to set
     */
    public void setProtocolQueryServiceLocal(
            ProtocolQueryServiceLocal protocolQueryServiceLocal) {
        this.protocolQueryServiceLocal = protocolQueryServiceLocal;
    }

    /**
     * @return the studyResourcingServiceLocal
     */
    public StudyResourcingServiceLocal getStudyResourcingServiceLocal() {
        return studyResourcingServiceLocal;
    }

    /**
     * @param studyResourcingServiceLocal the studyResourcingServiceLocal to set
     */
    public void setStudyResourcingServiceLocal(
            StudyResourcingServiceLocal studyResourcingServiceLocal) {
        this.studyResourcingServiceLocal = studyResourcingServiceLocal;
    }

    /**
     * @return the studySiteServiceLocal
     */
    public StudySiteServiceLocal getStudySiteServiceLocal() {
        return studySiteServiceLocal;
    }

    /**
     * @param studySiteServiceLocal the studySiteServiceLocal to set
     */
    public void setStudySiteServiceLocal(StudySiteServiceLocal studySiteServiceLocal) {
        this.studySiteServiceLocal = studySiteServiceLocal;
    }


    /**
     * @param documentServiceLocal the documentServiceLocal to set
     */
    public void setDocumentServiceLocal(DocumentServiceLocal documentServiceLocal) {
        this.documentServiceLocal = documentServiceLocal;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<StudyInboxDTO> getAllTrialUpdates(Ii studyProtocolIi)
            throws PAException {
        Criteria crit = PaHibernateUtil
                .getCurrentSession()
                .createCriteria(StudyInbox.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("typeCode", StudyInboxTypeCode.UPDATE))
                .createCriteria("studyProtocol")
                .add(Restrictions.eq("id",
                        IiConverter.convertToLong(studyProtocolIi)));
        try {
            List<StudyInbox> entries = crit.list();
            return convertFromDomainToDTOs(entries);
        } catch (Exception e) {
            throw new PAException("Error retrieving inbox entries.", e);
        }
    }


    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(
            StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }


    @Override
    public void closeMostRecent(Ii studyProtocolIi) throws PAException {
        List<StudyInboxDTO> list = getOpenInboxEntries(studyProtocolIi);
        if (!list.isEmpty()) {
            StudyInboxDTO recent = list.get(0);
            recent.getInboxDateRange()
                    .setHigh(
                            TsConverter.convertToTs(new Timestamp(new Date()
                                    .getTime())));
            update(recent);
        }
    }


    @Override
    public void acknowledge(Ii inboxId, StudyInboxSectionCode code)
            throws PAException {
        StudyInboxDTO inbox = get(inboxId);
        Timestamp now = new Timestamp(new Date().getTime());
        User user = CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser());
        if (code == StudyInboxSectionCode.ADMIN) {
            inbox.setAdminCloseDate(TsConverter.convertToTs(now));
            if (user != null) {
                inbox.setAdminAcknowledgedUser(StConverter.convertToSt(user.getLoginName()));
            }
        } else if (code == StudyInboxSectionCode.SCIENTIFIC) {
            inbox.setScientificCloseDate(TsConverter.convertToTs(now));
            if (user != null) {
                inbox.setScientificAcknowledgedUser(StConverter.convertToSt(user.getLoginName()));
            }
        }
        if (code == null
                || code == StudyInboxSectionCode.BOTH
                || ((!ISOUtil.isTsNull(inbox.getAdminCloseDate()) || !BlConverter
                        .convertToBool(inbox.getAdmin()))
                        && (!ISOUtil.isTsNull(inbox.getScientificCloseDate())) || !BlConverter
                            .convertToBool(inbox.getScientific()))) {
            inbox.getInboxDateRange().setHigh(TsConverter.convertToTs(now));
        }
        super.update(inbox);
    }
 
    
}
