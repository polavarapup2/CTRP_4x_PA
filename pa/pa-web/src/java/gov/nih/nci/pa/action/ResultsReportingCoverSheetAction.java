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
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyRecordChange;
import gov.nih.nci.pa.dto.StudyContactWebDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactService;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.StudyRecordService;
import gov.nih.nci.pa.service.util.MailManagerService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Preparable;
/**
 *
 * @author Apratim Khandalkar
 * @since 06/2015
 * copyright NCI 2015.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@SuppressWarnings({ "PMD.TooManyFields", "PMD.TooManyMethods", "PMD.ExcessiveMethodLength"
    , "PMD.SignatureDeclareThrowsException" , "PMD.SimpleDateFormatNeedsLocale" , "PMD.ExcessiveClassLength" })
public class ResultsReportingCoverSheetAction extends AbstractMultiObjectDeleteAction implements
ServletRequestAware , ServletResponseAware , Preparable {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StudyRecordService studyRecordService;
    private Long studyProtocolId;
    private Long id;
    private String discrepancyType;
    private String actionTaken;
    private String actionCompletionDate;
    private List<StudyRecordChange> studyRecordChangeList;
    private String changeType;
    private String deleteType;
    private StudyProtocolDTO studyProtocolDTO;
    private StudyProtocolService studyProtocolService;
    
    private Boolean useStandardLanguage;
    private Boolean dateEnteredInPrs;
    private Boolean designeeAccessRevoked;
    private String designeeAccessRevokedDate;
    private Boolean changesInCtrpCtGov;
    private String changesInCtrpCtGovDate;
    private Boolean sendToCtGovUpdated;
    private MailManagerService mailManagerService;
    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    private List<StudyContactWebDTO> designeeContactList = new ArrayList<StudyContactWebDTO>();
    private StudyContactService studyContactService;
    private InputStream ajaxResponseStream;
    private static final String AJAX_RESPONSE = "ajaxResponse";

    
    private static final Logger LOG = Logger
            .getLogger(ResultsReportingCoverSheetAction.class);
    
    @Override
    public void prepare() throws Exception {
        studyRecordService = PaRegistry.getStudyRecordService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        mailManagerService = PaRegistry.getMailManagerService();
        studyContactService = PaRegistry.getStudyContactService();
        
    }
    
    /** 
     * @return result
     */
    public String query()  {
      
        
        
        try {
            Timestamp timestamp;
            Date date;
            SimpleDateFormat dateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
                   
            studyRecordChangeList = studyRecordService.
                    getStudyRecordsList(studyProtocolId);
            
            studyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
            useStandardLanguage = BlConverter.convertToBoolean(studyProtocolDTO.getUseStandardLanguage());
            dateEnteredInPrs = BlConverter.convertToBoolean(studyProtocolDTO.getDateEnteredInPrs());
            designeeAccessRevoked = BlConverter.convertToBoolean(studyProtocolDTO.getDesigneeAccessRevoked());
            timestamp = TsConverter.convertToTimestamp(studyProtocolDTO.getDesigneeAccessRevokedDate());
            
            if (timestamp != null) {
                date = new Date(timestamp.getTime()); 
                designeeAccessRevokedDate =  dateFormat.format(date);  
            }
           
            changesInCtrpCtGov = BlConverter.convertToBoolean(studyProtocolDTO.getChangesInCtrpCtGov());
            timestamp = null;
            timestamp =  TsConverter.convertToTimestamp(studyProtocolDTO.getChangesInCtrpCtGovDate());
            if (timestamp != null) {
                date = new Date(timestamp.getTime()); 
                changesInCtrpCtGovDate = dateFormat.format(date); 
            }

            sendToCtGovUpdated = BlConverter.convertToBoolean(studyProtocolDTO.getSendToCtGovUpdated());
            
           
            

            
        } catch (Exception e) {
            
            //remove attribute in case of failure
            request.removeAttribute(Constants.SUCCESS_MESSAGE);
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        return SUCCESS;
    }   
    
   
    
    /**
     * @return Action result.
     * @throws IOException
     *             IOException
     */
    public String addOrEditRecordChange() throws IOException {
        try {
            if (getId() != null) {
                editRecordChange();
            } else {
                addRecordChange();
            }
        } catch (PAException e) {
            LOG.error(e, e);
            response.addHeader("msg", e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());
        }
        return null;
    }
    
    /**
     * @return String
     * @throws PAException
     *             PAException
     * 
     */
    public String successfulAdd() throws PAException {
        request.setAttribute(Constants.SUCCESS_MESSAGE,
                "Data Discrepancy has been added successfully");
        return query();
    }
    
    /**
     * @return String
     * @throws PAException
     *             PAException
     * 
     */
    public String successfulAddRecordChange() throws PAException {
        request.setAttribute(Constants.SUCCESS_MESSAGE,
                "Record Change has been added successfully");
        return query();
    }
    
    
    
    private void addRecordChange() throws PAException {
        studyRecordService.
        addStudyRecordChange(changeType, actionTaken, actionCompletionDate, studyProtocolId);
            
    }

    private void editRecordChange() throws PAException {
        studyRecordService.editStudyRecordChange(changeType, actionTaken, actionCompletionDate, id);
    }
    
    
    /**
     * @return result
     * @throws PAException
     *             exception
     */
    public String delete() throws PAException {
        try {
            deleteSelectedObjects();
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    Constants.MULTI_DELETE_MESSAGE);
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return query();
    }
    
    /**
     * @return string
     * @throws PAException PAException
     */
    public String saveFinalChanges() throws PAException {
        
        try {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
        studyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        studyProtocolDTO.setUseStandardLanguage(BlConverter.convertToBl(useStandardLanguage));  
        studyProtocolDTO.setDateEnteredInPrs(BlConverter.convertToBl(dateEnteredInPrs));
        
        studyProtocolDTO.setDesigneeAccessRevoked(BlConverter.convertToBl(designeeAccessRevoked));
        if (designeeAccessRevokedDate != null) {
            Date date = dateFormat.parse(designeeAccessRevokedDate);
            studyProtocolDTO.setDesigneeAccessRevokedDate(TsConverter.convertToTs(date));
        } else {
            //save blank date
            studyProtocolDTO.setDesigneeAccessRevokedDate(null);
        }
            
        studyProtocolDTO.setChangesInCtrpCtGov(BlConverter.convertToBl(changesInCtrpCtGov));
        
        if (changesInCtrpCtGovDate != null) {
            Date date = dateFormat.parse(changesInCtrpCtGovDate);
            studyProtocolDTO.setChangesInCtrpCtGovDate(TsConverter.convertToTs(date));
        } else {
            //save blank date
            studyProtocolDTO.setChangesInCtrpCtGovDate(null);
        }
        
        studyProtocolDTO.setSendToCtGovUpdated(BlConverter.convertToBl(sendToCtGovUpdated));
        studyProtocolService.updateStudyProtocol(studyProtocolDTO);
        
        designeeContactList = new ArrayList<StudyContactWebDTO>();
        
     
      //get study contacts list
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
         StudyContactDTO searchCriteria = new StudyContactDTO();
         searchCriteria.setStudyProtocolIdentifier(studyProtocolIi);
         
         searchCriteria.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.DESIGNEE_CONTACT));
         List<StudyContactDTO>studyDesigneeContactDtos = studyContactService.search(searchCriteria, limit);
         
      
         if (CollectionUtils.isNotEmpty(studyDesigneeContactDtos)) {
             for (StudyContactDTO scDto : studyDesigneeContactDtos) {
                 FunctionalRoleStatusCode stsCd = CdConverter.convertCdToEnum(FunctionalRoleStatusCode.class, 
                         scDto.getStatusCode());
                 if (!FunctionalRoleStatusCode.ACTIVE.equals(stsCd) 
                         && !FunctionalRoleStatusCode.PENDING.equals(stsCd)
                         && !FunctionalRoleStatusCode.SUSPENDED.equals(stsCd)) {
                     continue;
                 }
                 designeeContactList.add(new StudyContactWebDTO(scDto));
             }
         }
        
        //set specified contats to pending so that they don't show up
        for (StudyContactWebDTO contactWebDTO : designeeContactList) {
            Ii ii = IiConverter.convertToStudyContactIi(Long.valueOf(contactWebDTO.getId()));
            StudyContactDTO studyContactDTO = studyContactService.get(ii);  
            if (designeeAccessRevoked) {
                studyContactDTO.setStatusCode(CdConverter.convertStringToCd("Suspended"));
            } else {
                studyContactDTO.setStatusCode(CdConverter.convertStringToCd("Active"));
            }   
            studyContactService.update(studyContactDTO);
        }
        
        
        request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        
        
        return query();
        
        } catch (Exception e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return ERROR;
    }
    
    /**
     * @return string
     * @throws PAException PAException
     */
    public String sendConverSheetEmail() throws PAException {
        
        String nciID = paServiceUtil.getTrialNciId(studyProtocolId);
        
        try { 
        
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
        studyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);    
      
        studyRecordChangeList = studyRecordService.
                getStudyRecordsList(studyProtocolId);
        
        //send cover sheet email
        mailManagerService.sendCoverSheetEmail(
                nciID, studyProtocolDTO, studyRecordChangeList);
     
        request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.EMAIL_MESSAGE);
        
        return query();
        
        } catch (Exception e) {
        request.setAttribute(Constants.FAILURE_MESSAGE,
                e.getLocalizedMessage());
        }
        return ERROR;
    }
    
    /**
     * @return flag
     * @throws PAException PAException
     */
    public String checkIfDesigneeExists() throws PAException {
       
        Boolean result = false;
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
        
        //get study contacts list
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
         StudyContactDTO searchCriteria = new StudyContactDTO();
         searchCriteria.setStudyProtocolIdentifier(studyProtocolIi);
         
         try {
         
         searchCriteria.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.DESIGNEE_CONTACT));
         List<StudyContactDTO>studyDesigneeContactDtos = studyContactService.search(searchCriteria, limit);
      
         if (CollectionUtils.isNotEmpty(studyDesigneeContactDtos)) {
             for (StudyContactDTO scDto : studyDesigneeContactDtos) {
                 FunctionalRoleStatusCode stsCd = CdConverter.convertCdToEnum(FunctionalRoleStatusCode.class, 
                         scDto.getStatusCode());
                 if (!FunctionalRoleStatusCode.ACTIVE.equals(stsCd)
                         && !FunctionalRoleStatusCode.PENDING.equals(stsCd)) {
                     continue;
                 }
                 result = true;
                 break;
             }
         }
         } catch (Exception e) {
           throw new PAException(e.getMessage());   
         }
       
        ajaxResponseStream = new ByteArrayInputStream(result.toString().getBytes());
        return AJAX_RESPONSE;
    }

    @Override
    public void deleteObject(Long objectId) throws PAException {
        studyRecordService.deleteStudyRecord(objectId);
        
    }



    @Override
    public void setServletResponse(HttpServletResponse httpResponse) {
        this.response = httpResponse;
        
    }

    @Override
    public void setServletRequest(HttpServletRequest httpRequest) {
        this.request = httpRequest; 
        
    }

    /**
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

   

   
    /**
     * @return discrepancyType
     */
    public String getDiscrepancyType() {
        return discrepancyType;
    }

    /**
     * @param discrepancyType discrepancyType
     */
    public void setDiscrepancyType(String discrepancyType) {
        this.discrepancyType = discrepancyType;
    }

    /**
     * @return actionTaken
     */
    public String getActionTaken() {
        return actionTaken;
    }

    /**
     * @param actionTaken actionTaken
     */
    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    /**
     * @return actionCompletionDate
     */
    public String getActionCompletionDate() {
        return actionCompletionDate;
    }

    /**
     * @param actionCompletionDate actionCompletionDate
     */
    public void setActionCompletionDate(String actionCompletionDate) {
        this.actionCompletionDate = actionCompletionDate;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return studyRecordChangeList studyRecordChangeList
     */
    public List<StudyRecordChange> getStudyRecordChangeList() {
        return studyRecordChangeList;
    }

    /**
     * @param studyRecordChangeList studyRecordChangeList
     */
    public void setStudyRecordChangeList(
            List<StudyRecordChange> studyRecordChangeList) {
        this.studyRecordChangeList = studyRecordChangeList;
    }

    /**
     * @return changeType changeType
     */
    public String getChangeType() {
        return changeType;
    }

    /**
     * @param changeType changeType
     */
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    /**
     * @return deleteType deleteType
     */
    public String getDeleteType() {
        return deleteType;
    }

    /**
     * @param deleteType deleteType
     */
    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    /**
     * @return studyProtocolDTO studyProtocolDTO
     */
    public StudyProtocolDTO getStudyProtocolDTO() {
        return studyProtocolDTO;
    }

    /**
     * @param studyProtocolDTO studyProtocolDTO
     */
    public void setStudyProtocolDTO(StudyProtocolDTO studyProtocolDTO) {
        this.studyProtocolDTO = studyProtocolDTO;
    }

    /**
     * @return studyProtocolService studyProtocolService
     */
    public StudyProtocolService getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @param studyProtocolService studyProtocolService
     */
    public void setStudyProtocolService(StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @return studyNotesService
     */
    public StudyRecordService getStudyNotesService() {
        return studyRecordService;
    }

    
   
    /**
     * @param studyRecordServiceSet studyRecordServiceSet
     */
    public void setStudyNotesService(StudyRecordService studyRecordServiceSet) {
        this.studyRecordService = studyRecordServiceSet;
    }

    /**
     * @return useStandardLanguage
     */
    public Boolean getUseStandardLanguage() {
        return useStandardLanguage;
    }

    /**
     * @param useStandardLanguage useStandardLanguage
     */
    public void setUseStandardLanguage(Boolean useStandardLanguage) {
        this.useStandardLanguage = useStandardLanguage;
    }

    /**
     * @return dateEnteredInPrs
     */
    public Boolean getDateEnteredInPrs() {
        return dateEnteredInPrs;
    }

    /**
     * @param dateEnteredInPrs dateEnteredInPrs
     */
    public void setDateEnteredInPrs(Boolean dateEnteredInPrs) {
        this.dateEnteredInPrs = dateEnteredInPrs;
    }

    /**
     * @return designeeAccessRevoked
     */
    public Boolean getDesigneeAccessRevoked() {
        return designeeAccessRevoked;
    }

    /**
     * @param designeeAccessRevoked designeeAccessRevoked
     */
    public void setDesigneeAccessRevoked(Boolean designeeAccessRevoked) {
        this.designeeAccessRevoked = designeeAccessRevoked;
    }

    /**
     * @return designeeAccessRevokedDate
     */
    public String getDesigneeAccessRevokedDate() {
        return designeeAccessRevokedDate;
    }

    /**
     * @param designeeAccessRevokedDate designeeAccessRevokedDate
     */
    public void setDesigneeAccessRevokedDate(String designeeAccessRevokedDate) {
        this.designeeAccessRevokedDate = designeeAccessRevokedDate;
    }

    /**
     * @return sendToCtGovUpdated
     */
    public Boolean getSendToCtGovUpdated() {
        return sendToCtGovUpdated;
    }

    /**
     * @param sendToCtGovUpdated sendToCtGovUpdated
     */
    public void setSendToCtGovUpdated(Boolean sendToCtGovUpdated) {
        this.sendToCtGovUpdated = sendToCtGovUpdated;
    }
    /**
     * @return changesInCtrpCtGovDate
     */
    public String getChangesInCtrpCtGovDate() {
        return changesInCtrpCtGovDate;
    }

    /**
     * @param changesInCtrpCtGovDate changesInCtrpCtGovDate
     */
    public void setChangesInCtrpCtGovDate(String changesInCtrpCtGovDate) {
        this.changesInCtrpCtGovDate = changesInCtrpCtGovDate;
    }

    /**
     * @return changesInCtrpCtGov
     */
    public Boolean getChangesInCtrpCtGov() {
        return changesInCtrpCtGov;
    }

    /**
     * @param changesInCtrpCtGov changesInCtrpCtGov
     */
    public void setChangesInCtrpCtGov(Boolean changesInCtrpCtGov) {
        this.changesInCtrpCtGov = changesInCtrpCtGov;
    }

    /**
     * @return paServiceUtil
     */
    public PAServiceUtils getPaServiceUtil() {
        return paServiceUtil;
    }

    /**
     * @param paServiceUtil paServiceUtil
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }

    /**
     * @return mailManagerService
     */
    public MailManagerService getMailManagerService() {
        return mailManagerService;
    }

    /**
     * @param mailManagerService mailManagerService
     */
    public void setMailManagerService(MailManagerService mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @return designeeContactList
     */
    public List<StudyContactWebDTO> getDesigneeContactList() {
        return designeeContactList;
    }

    /**
     * @param designeeContactList designeeContactList
     */
    public void setDesigneeContactList(List<StudyContactWebDTO> designeeContactList) {
        this.designeeContactList = designeeContactList;
    }

   

    /**
     * @return studyContactService
     */
    public StudyContactService getStudyContactService() {
        return studyContactService;
    }

    /**
     * @param studyContactService studyContactService
     */
    public void setStudyContactService(StudyContactService studyContactService) {
        this.studyContactService = studyContactService;
    }

    /**
     * @return ajaxResponseStream
     */
    public InputStream getAjaxResponseStream() {
        return ajaxResponseStream;
    }

    /**
     * @param ajaxResponseStream ajaxResponseStream
     */
    public void setAjaxResponseStream(InputStream ajaxResponseStream) {
        this.ajaxResponseStream = ajaxResponseStream;
    }

   
    
   
    

}
