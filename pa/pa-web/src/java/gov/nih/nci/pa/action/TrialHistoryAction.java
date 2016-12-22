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

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDocumentWebDTO;
import gov.nih.nci.pa.dto.TrialHistoryWebDTO;
import gov.nih.nci.pa.dto.TrialUpdateWebDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.StudyInboxSectionCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailService;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.AuditTrailCode;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;

/**
 * Action for handling history of trial submissions, updates and
 * audit trail.
 * 
 * @author Anupama Sharma
 * @since 04/16/2009
 */
public final class TrialHistoryAction extends AbstractListEditAction implements ServletResponseAware {

    private static final long serialVersionUID = 3505660792038543678L;

    private DocumentServiceLocal documentService;
    private StudyInboxServiceLocal studyInboxService;
    private StudyProtocolServiceLocal studyProtocolService;
    private RegistryUserServiceLocal registryUserServiceLocal;
    private DocumentWorkflowStatusServiceLocal documentWorkflowStatusServiceLocal;
    private StudyMilestoneServicelocal studyMilestoneService;
    private List<TrialHistoryWebDTO> trialHistoryWebDTO;
    private TrialHistoryWebDTO trialHistoryWbDto;
    private String studyProtocolii;
    private String docii;
    private String docFileName;
    private HttpServletResponse servletResponse;
    private List<TrialUpdateWebDTO> records = new ArrayList<TrialUpdateWebDTO>();
    private final StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    private List<TrialDocumentWebDTO> deletedDocuments = new ArrayList<TrialDocumentWebDTO>();

    private static final int DEFAULT_AUDIT_PERIOD = -30;
    private AuditTrailCode auditTrailCode;
    private Set<AuditLogDetail> auditTrail;
    private AuditTrailServiceLocal auditTrailService;

    private String startDate;
    private String endDate;
    private String nciID;
    
    private static final Logger LOG = Logger
            .getLogger(TrialHistoryAction.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {       
        documentService = PaRegistry.getDocumentService();
        studyInboxService = PaRegistry.getStudyInboxService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        registryUserServiceLocal = PaRegistry.getRegistryUserService();
        documentWorkflowStatusServiceLocal = PaRegistry.getDocumentWorkflowStatusService();
        studyMilestoneService = PaRegistry.getStudyMilestoneService();
        auditTrailService  = PaRegistry.getAuditTrailService();
        
        if (StringUtils.isNotBlank(getNciID())) {
            loadTrialData();
        }
        
        super.prepare();
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        Date today = DateUtils.truncate(new Date(), Calendar.DATE);
        Date thirtyDaysAgo = DateUtils.addDays(today, DEFAULT_AUDIT_PERIOD);
        setStartDate(PAUtil.normalizeDateString(sdf.format(thirtyDaysAgo)));
        setEndDate(PAUtil.normalizeDateString(sdf.format(today)));
    }

    /**
     * Gets the study protocolii.
     * 
     * @return the studyProtocolii
     */
    public String getStudyProtocolii() {
        return studyProtocolii;
    }

    /**
     * Sets the study protocolii.
     * 
     * @param studyProtocolii the studyProtocolii to set
     */
    public void setStudyProtocolii(String studyProtocolii) {
        this.studyProtocolii = studyProtocolii;
    }

    /**
     * Gets the docii.
     * 
     * @return the docii
     */
    public String getDocii() {
        return docii;
    }

    /**
     * Sets the docii.
     * 
     * @param docii the docii to set
     */
    public void setDocii(String docii) {
        this.docii = docii;
    }

    /**
     * Gets the doc file name.
     * @return the docFileName
     */
    public String getDocFileName() {
        return docFileName;
    }

    /**
     * Sets the doc file name.
     * @param docFileName the docFileName to set
     */
    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    /**
     * Gets the servlet response.
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * Sets the servlet response.
     * @param servletResponse the servletResponse to set
     */
    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    /**
     * Call initial list jsp.
     * @return action result
     * @throws PAException exception
     */
    @Override
    public String execute() throws PAException {        
        loadListForm();
        loadTrialUpdates();        
        return AR_LIST;
    }

    

    private void loadTrialData() {
        try {
            Ii ii = new Ii();
            ii.setExtension(getNciID());
            ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
            StudyProtocolDTO dto = studyProtocolService.getStudyProtocol(ii);
            StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry
                    .getCachingProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(
                            IiConverter.convertToLong(dto.getIdentifier()));
            ActionUtils.loadProtocolDataInSession(studyProtocolQueryDTO,
                    new CorrelationUtils(), new PAServiceUtils());
        } catch (PAException e) {
            LOG.error(e, e);
            addActionError(e.getMessage());
        }
    }

    /**
     * @return res
     * @throws PAException
     *             exception
     */
    @SuppressWarnings("deprecation")
    public String acceptUpdate() throws PAException {
        try {
            final HttpServletRequest request = ServletActionContext.getRequest();
            String sInbxId = request.getParameter(
                    "studyInboxId");
            final Ii inboxId = IiConverter.convertToIi(sInbxId);
            studyInboxService.acknowledge(inboxId, StudyInboxSectionCode
                    .getByCode(request.getParameter(
                            "updateType")));
        } catch (Exception e) {
            LOG.error("Error while accepting the trial update.", e);
            addActionError("Error while accepting the trial update.");
        } finally {
            loadTrialUpdates();
        }
        return AR_LIST;
    }

    private void loadTrialUpdates() throws PAException {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.STUDY_PROTOCOL_II);
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        criteria.setInBoxProcessing(Boolean.TRUE);
        criteria.setNciIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
        List<StudyInboxDTO> inboxEntries = studyInboxService.getOpenInboxEntries(studyProtocolIi);
        for (StudyInboxDTO dto : inboxEntries) {
            TrialUpdateWebDTO webDTO = new TrialUpdateWebDTO();
            webDTO.setId(IiConverter.convertToLong(dto.getIdentifier()));
            webDTO.setComment(StConverter.convertToString(dto.getComments()));
            webDTO.setUpdatedDate(TsConverter.convertToTimestamp(dto.getInboxDateRange().getLow()));
            webDTO.setAdmin(BlConverter.convertToBoolean(dto.getAdmin()));
            webDTO.setScientific(BlConverter.convertToBoolean(dto.getScientific()));
            webDTO.setAdminCloseDate(TsConverter.convertToTimestamp(dto.getAdminCloseDate()));
            webDTO.setScientificCloseDate(TsConverter.convertToTimestamp(dto.getScientificCloseDate()));
            records.add(webDTO);
        }

    }

    /**
     * Load list form.
     * @throws PAException exception
     */
    @Override
    protected void loadListForm() throws PAException {
        List<TrialHistoryWebDTO> trialHistoryWebdtos = new ArrayList<TrialHistoryWebDTO>();
        loadTrialSubmissions(trialHistoryWebdtos);
        loadTrialUpdates(trialHistoryWebdtos);
        Collections.sort(trialHistoryWebdtos , new Comparator<TrialHistoryWebDTO>() {
            @Override
            public int compare(TrialHistoryWebDTO o1, TrialHistoryWebDTO o2) {
                if (o1.getSubmissionNumber() == null) {
                    return (o2.getSubmissionNumber() == null) ? 0 : -1;
                }
                if (o2.getSubmissionNumber() == null) {
                    return 1;
                }
                int sn1 = Integer.parseInt(o1.getSubmissionNumber());
                int sn2 = Integer.parseInt(o2.getSubmissionNumber());
                return new CompareToBuilder().append(sn2, sn1).toComparison();
            }
        });
        setTrialHistoryWebDTO(trialHistoryWebdtos);
        
        loadDeletedDocuments();
    }

    private void loadDeletedDocuments() throws PAException {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
        final List<DocumentDTO> deletedDocs = documentService
                .getDeletedDocumentsByTrial(studyProtocolIi);
        for (DocumentDTO documentDTO : deletedDocs) {
            final TrialDocumentWebDTO docWebDTO = new TrialDocumentWebDTO(documentDTO);
            this.deletedDocuments.add(docWebDTO);
        }
    }

    private void loadTrialUpdates(List<TrialHistoryWebDTO> list) throws PAException {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);    
        List<StudyInboxDTO> inboxEntries = studyInboxService
                .getAllTrialUpdates(studyProtocolIi);
        for (StudyInboxDTO dto : inboxEntries) {
            TrialHistoryWebDTO webDTO = new TrialHistoryWebDTO(dto);   
            webDTO.setDocuments(getDocuments(dto));
            webDTO.setSubmitter(getSubmitterName(dto));            
            list.add(webDTO);
        }

    }

    private String getDocuments(StudyInboxDTO dto) throws PAException {
        List<DocumentDTO> documentDTO = documentService.
                getOriginalDocumentsByStudyInbox(dto);
        return getDocumentsAsString(documentDTO);
    }

    /**
     * @param trialHistoryWebdtos
     * @throws PAException
     */
    private void loadTrialSubmissions(
            List<TrialHistoryWebDTO> trialHistoryWebdtos) throws PAException {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.STUDY_PROTOCOL_II);
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        StudyProtocolDTO toSearchspDTO = new StudyProtocolDTO();
        toSearchspDTO.setSecondaryIdentifiers(DSetConverter.convertIiToDset(PAUtil.getAssignedIdentifier(spDTO)));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        toSearchspDTO.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
        List<StudyProtocolDTO> spList = new ArrayList<StudyProtocolDTO>();
        try {
            List<StudyProtocolDTO> activeList = studyProtocolService.search(toSearchspDTO, limit);
            if (CollectionUtils.isNotEmpty(activeList)) {
                spList.addAll(activeList);
            } else {
                StudyProtocolDTO rejectActiveList = studyProtocolService.
                      getStudyProtocol(studyProtocolIi);
                    spList.add(rejectActiveList);
            }
            toSearchspDTO.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
            List<StudyProtocolDTO> inactiveList = studyProtocolService.search(toSearchspDTO, limit);
            if (CollectionUtils.isNotEmpty(inactiveList)) {
                spList.addAll(inactiveList);
            }
            List<Long> ids = studyProtocolService.
                   getActiveAndInActiveTrialsByspId(IiConverter.convertToLong(studyProtocolIi));
            List<Long> uniqueIds = new ArrayList<Long>();
            List<Long> spListIdentifiers = new ArrayList<Long>();
            for (StudyProtocolDTO sp : spList) {
                  spListIdentifiers.add(IiConverter.convertToLong(sp.getIdentifier()));
            }
            for (Long id : ids) {
                     if (!spListIdentifiers.contains(id)) {
                         uniqueIds.add(id);
                     }
                       
            }
            for (Long id : uniqueIds) {
                  StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(IiConverter.convertToIi(id));
                  spList.add(sp);
            }
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        
        if (!spList.isEmpty()) {
            for (StudyProtocolDTO sp : spList) {
                TrialHistoryWebDTO dto = new TrialHistoryWebDTO(sp,
                        documentWorkflowStatusServiceLocal.getInitialStatus(sp
                                .getIdentifier()));
                dto.setDocuments(getDocuments(sp));
                dto.setSubmitter(getSubmitterName(sp));
                getLastMileStone(dto, sp.getIdentifier());
                trialHistoryWebdtos.add(dto);
            }
        }
        
    }

    private String getSubmitterName(StudyProtocolDTO studyProtocolDTO)
            throws PAException {
        final St userLoginName = studyProtocolDTO.getUserLastCreated();
        return getSubmitterName(userLoginName);
    }
    
    private void getLastMileStone(TrialHistoryWebDTO dto, Ii spIi) 
            throws PAException {
        StudyMilestoneDTO smDto = studyMilestoneService.getCurrentByStudyProtocol(spIi);
        if (smDto != null) {
             dto.setLastMileStone(CdConverter.convertCdToString(smDto.getMilestoneCode()));
             dto.setRejectComment(StConverter.convertToString(smDto.getCommentText()));
             dto.setComment(StConverter.convertToString(smDto.getCommentText()));
        }
    }
    

    private String getSubmitterName(StudyInboxDTO dto) throws PAException {
        final St userLoginName = dto.getUserLastCreated();
        return getSubmitterName(userLoginName);
    }

    /**
     * @param userLoginName
     * @return
     * @throws PAException
     */
    private String getSubmitterName(final St userLoginName) throws PAException {
        final String str = StConverter
                .convertToString(userLoginName);
        RegistryUser usr = registryUserServiceLocal.getUser(str);
        return usr != null ? usr.getFullName() : CsmUserUtil.getGridIdentityUsername(str);
    }
    
    
    /**
     * Gets the documents.
     * @param sp the sp
     * @return the documents
     * @throws PAException the PA exception
     */
    private String getDocuments(StudyProtocolDTO sp) throws PAException {
        List<DocumentDTO> documentDTO = documentService.getByStudyProtocol(sp
                .getIdentifier());
        return getDocumentsAsString(documentDTO);
    }

    /**
     * @param documentDTO
     * @return
     */
    private String getDocumentsAsString(List<DocumentDTO> documentDTO) {
        StringBuffer documents = new StringBuffer();
        for (DocumentDTO docDto : documentDTO) {
            String fileName = StConverter.convertToString(docDto.getFileName());
            documents.append("<a href='#' onclick=\"handlePopup('");
            documents.append(docDto.getStudyProtocolIdentifier().getExtension());
            documents.append("','");
            documents.append(docDto.getIdentifier().getExtension());
            documents.append("','");
            documents.append(docDto.getFileName().getValue());
            documents.append("')\">");
            documents.append(CdConverter.convertCdToString(docDto.getTypeCode()));
            documents.append("&nbsp;- <B>");
            documents.append(fileName);
            documents.append("</B></a>");
            if (Boolean.TRUE.equals(BlConverter.convertToBoolean(docDto
                    .getOriginal()))) {
                documents
                        .append("&nbsp;&nbsp;&nbsp;<i style='font-size:90%;'>Original</i>");
            }
            if (Boolean.FALSE.equals(BlConverter.convertToBoolean(docDto
                    .getActiveIndicator()))) {
                documents
                        .append("&nbsp;&nbsp;&nbsp;<i style='font-size:90%;'>Deleted</i>");
            }
            documents.append("<br>");           
        }
        return documents.toString();
    }

    /**
     * Open.
     * 
     * @return the string
     * 
     * @throws PAException the PA exception
     */
    public String open() throws PAException {
        try {
            DocumentDTO docDTO = documentService.get(IiConverter.convertToIi(getDocii()));

            final Ii spIi = IiConverter.convertToIi(getStudyProtocolii());
            StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(spIi);

            StringBuffer fileName = new StringBuffer();
            fileName.append(PAUtil.getAssignedIdentifier(spDTO).getExtension()).append('-').
                append(docDTO.getFileName().getValue());

            ByteArrayInputStream bStream = new ByteArrayInputStream(docDTO.getText().getData());

            servletResponse.setContentType("application/octet-stream");
            servletResponse.setContentLength(docDTO.getText().getData().length);
            servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toString() + "\"");
            servletResponse.setHeader("Pragma", "public");
            servletResponse.setHeader("Cache-Control", "max-age=0");

            int data;
            ServletOutputStream out = servletResponse.getOutputStream();
            while ((data = bStream.read()) != -1) {
                out.write(data);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException err) {
            LOG.error("TrialHistoryAction failed with FileNotFoundException: " + err);
            addActionError(err.getMessage());
            return super.execute();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return super.execute();
        }
        return NONE;
    }

    /**
     * Update.
     * @return action result
     * @throws PAException exception
     */
    @Override
    public String update() throws PAException {
        try {
            enforceBusinessRules();
            if (hasFieldErrors()) {
                return AR_EDIT;
            }
            final Ii spIi = IiConverter.convertToIi(trialHistoryWbDto.getIdentifier());
            StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(spIi);
            studyProtocolService.updateStudyProtocol(trialHistoryWbDto.getIsoDto(sp));
        } catch (PAFieldException e) {
            addActionError(e.getMessage());
            return AR_EDIT;
        } catch (PAException e) {
            addActionError(e.getMessage());
            return AR_EDIT;
        }
        return super.update();
    }

    /**
     * Load edit form.
     * 
     * @throws PAException exception
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void loadEditForm() throws PAException {
        if (CA_EDIT.equals(getCurrentAction())) {
            final StudyProtocolDTO studyProtocol = studyProtocolService
                    .getStudyProtocol(IiConverter
                            .convertToIi(getSelectedRowIdentifier()));
            setTrialHistoryWbDto(new TrialHistoryWebDTO(studyProtocol,
                    documentWorkflowStatusServiceLocal
                            .getInitialStatus(studyProtocol.getIdentifier())));
        } else {
            setTrialHistoryWbDto(new TrialHistoryWebDTO());
        }
    }

    /**
     * Enforce business rules.
     */
    private void enforceBusinessRules() {
        if (StringUtils.isEmpty(trialHistoryWbDto.getAmendmentDate())) {
            addFieldError("trialHistoryWbDto.amendmentDate", getText("Amendment Date must be Entered/Selected"));
        }
        if (StringUtils.isEmpty(trialHistoryWbDto.getAmendmentReasonCode())) {
            addFieldError("trialHistoryWbDTO.amendmentReasonCode", getText("Reason code must be Selected"));
        }
        if (!isValidDate(trialHistoryWbDto.getAmendmentDate())) {
            addFieldError("trialHistoryWbDto.amendmentDate",
                          getText("Please enter the Date in the correct format MM/dd/yyyy"));
        }
    }

    /**
     * Checks if String is valid date.
     * @param dateStr the date str
     * @return true, if is valid date
     */
    private boolean isValidDate(String dateStr) {
        if (dateStr == null) {
            return false;
        }
        // set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        if (dateStr.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }
        dateFormat.setLenient(false);
        try {
            // parse the dateStr parameter
            dateFormat.parse(dateStr.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Gets the trial history web dto.
     * 
     * @return the trialHistoryWebDTO
     */
    public List<TrialHistoryWebDTO> getTrialHistoryWebDTO() {
        return trialHistoryWebDTO;
    }

    /**
     * Sets the trial history web dto.
     * 
     * @param trialHistoryWebDTO the trialHistoryWebDTO to set
     */
    public void setTrialHistoryWebDTO(List<TrialHistoryWebDTO> trialHistoryWebDTO) {
        this.trialHistoryWebDTO = trialHistoryWebDTO;
    }

    /**
     * Gets the trial history wb dto.
     * 
     * @return the trialHistoryWbDto
     */
    public TrialHistoryWebDTO getTrialHistoryWbDto() {
        return trialHistoryWbDto;
    }

    /**
     * Sets the trial history wb dto.
     * 
     * @param trialHistoryWbDto the trialHistoryWbDto to set
     */
    public void setTrialHistoryWbDto(TrialHistoryWebDTO trialHistoryWbDto) {
        this.trialHistoryWbDto = trialHistoryWbDto;
    }

    /**
     * @return the records
     */
    public List<TrialUpdateWebDTO> getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(List<TrialUpdateWebDTO> records) {
        this.records = records;
    }

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }

    /**
     * @param studyInboxService the studyInboxService to set
     */
    public void setStudyInboxService(StudyInboxServiceLocal studyInboxService) {
        this.studyInboxService = studyInboxService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
    
    @Override
    public void deleteObject(Long objectId) throws PAException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param registryUserServiceLocal the registryUserServiceLocal to set
     */
    public void setRegistryUserServiceLocal(
            RegistryUserServiceLocal registryUserServiceLocal) {
        this.registryUserServiceLocal = registryUserServiceLocal;
    }

    /**
     * @return the deletedDocuments
     */
    public List<TrialDocumentWebDTO> getDeletedDocuments() {
        return deletedDocuments;
    }

    /**
     * @param deletedDocuments the deletedDocuments to set
     */
    public void setDeletedDocuments(List<TrialDocumentWebDTO> deletedDocuments) {
        this.deletedDocuments = deletedDocuments;
    }

    /**
     * @param documentWorkflowStatusServiceLocal the documentWorkflowStatusServiceLocal to set
     */
    public void setDocumentWorkflowStatusServiceLocal(
            DocumentWorkflowStatusServiceLocal documentWorkflowStatusServiceLocal) {
        this.documentWorkflowStatusServiceLocal = documentWorkflowStatusServiceLocal;
    }
    
    /**
     * 
     * @param studyMilestoneService studyMilestoneService
     */
    public void setStudyMilestoneService(
           StudyMilestoneServicelocal studyMilestoneService) {
        this.studyMilestoneService = studyMilestoneService;
    }

     /**
     * @param auditTrail the auditTrail to set
     */
    public void setAuditTrail(Set<AuditLogDetail> auditTrail) {
        this.auditTrail = auditTrail;
    }

    /**
     * @return the auditTrailCode
     */
    public AuditTrailCode getAuditTrailCode() {
        return auditTrailCode;
    }

    /**
     * @param auditTrailCode the auditTrailCode to set
     */
    public void setAuditTrailCode(AuditTrailCode auditTrailCode) {
        this.auditTrailCode = auditTrailCode;
    }

    /**
     * @return the auditTrail
     */
    public Set<AuditLogDetail> getAuditTrail() {
        return auditTrail;
    }

    /**
     * @return the auditTrailService
     */
    public AuditTrailService getAuditTrailService() {
        return auditTrailService;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }        
    
    /**
     * Retrieves the audit trail for the given object, in the order in which they were created.
     * @return success
     * @throws PAException on error
     */
    @SuppressWarnings("unchecked")
    public String view() throws PAException {
        loadListForm();
        loadTrialUpdates(); 
        validateDateFilters();
        if (hasActionErrors() || hasFieldErrors()) {
            return AR_LIST;
        }
        Ii studyProtocolIi =
            (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
        setAuditTrail(new TreeSet<AuditLogDetail>(new BeanComparator("id", new NullComparator())));
        if (getAuditTrailCode() == AuditTrailCode.NCI_SPECIFIC_INFORMATION) {
            loadNciSpecificInformation(studyProtocolIi);
        } else if (getAuditTrailCode() != null) {
            List<AuditLogDetail> results =
                getAuditTrailService().getAuditTrailByStudyProtocol(getAuditTrailCode().getClazz(), studyProtocolIi,
                        PAUtil.dateStringToDateTime(startDate), PAUtil.dateStringToDateTime(endDate));
            getAuditTrail().addAll(results);
        }
        return AR_LIST;
    }       
    
    /**
     * Loads the audit trail for NCI specific information. NCI specific information is split between the study
     * protocol object itself and in the study resourcing object.
     */
    private void loadNciSpecificInformation(Ii studyProtocolIi) throws PAException {
        List<StudyResourcingDTO> nciSpecificInfoList =
            PaRegistry.getStudyResourcingService().getSummary4ReportedResourcing(studyProtocolIi);
        List<AuditLogDetail> studyContactDetails = new ArrayList<AuditLogDetail>();
        for (StudyResourcingDTO nciSpecificInfo : nciSpecificInfoList) {
            List<AuditLogDetail> auditLogDetails = getAuditTrailService().getAuditTrail(getAuditTrailCode().getClazz(), 
                        nciSpecificInfo.getIdentifier(),
                        PAUtil.dateStringToDateTime(startDate), PAUtil.dateStringToDateTime(endDate));
            studyContactDetails.addAll(auditLogDetails);
        }
        List<AuditLogDetail> spDetails = getAuditTrailService().getAuditTrailByFields(StudyProtocol.class,
                studyProtocolIi, PAUtil.dateStringToDateTime(startDate), PAUtil.dateStringToDateTime(endDate),
                "programCodeText", "accrualReportingMethodCode");

        getAuditTrail().addAll(studyContactDetails);
        getAuditTrail().addAll(spDetails);
    }


    /**
     * Validates date input.
     */
    private void validateDateFilters() {
        Date start = PAUtil.dateStringToDateTime(startDate);
        Date end = PAUtil.dateStringToDateTime(endDate);
        if (startDate != null && start == null) {
            addFieldError("startDate", getText("error.auditTrail.startDate"));
        }
        if (endDate != null && end == null) {
            addFieldError("endDate", getText("error.auditTrail.endDate"));
        }
        validateDateOrder(start, end);
    }
    
    /**
     * Validates end date should be after start date.
     * @param start start date
     * @param end end date
     */
    private void validateDateOrder(Date start, Date end) {
        if (start != null && end != null && start.after(end)) {            
            addFieldError("startDate", getText("error.auditTrail.orderedDate"));
        }
    }

    /**
     * @return the nciID
     */
    public String getNciID() {
        return nciID;
    }

    /**
     * @param nciID the nciID to set
     */
    public void setNciID(String nciID) {
        this.nciID = nciID;
    }
}
