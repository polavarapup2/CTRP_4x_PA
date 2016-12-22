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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDocumentWebDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
/**
 *
 * @author Apratim Khandalkar
 * @since 06/2015
 * copyright NCI 2015.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.ExcessiveMethodLength"
    , "PMD.ExcessiveClassLength" })
public class ResultsReportingDocumentAction extends AbstractMultiObjectDeleteAction implements
ServletRequestAware , ServletResponseAware {

    private static final long serialVersionUID = 2798002815820961877L;
    private static final Logger LOG  = Logger.getLogger(ResultsReportingDocumentAction.class);
    private static final String DELETE_RESULT = "delete";
    private File upload;
    private String uploadFileName;
    private List<TrialDocumentWebDTO> trialDocumentList;
    private TrialDocumentWebDTO trialDocumentWebDTO = new TrialDocumentWebDTO();
    private Long id = null;
    private HttpServletResponse servletResponse;
    private String page;
    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    private Long studyProtocolId;
    private   Map<Long, String> usersMap = new HashMap<Long, String>();
    private Long ctroUserId;
    private String ccctUserName;
    private HttpServletRequest request;
    private String parentPage;
    private InputStream ajaxResponseStream;
    private static final String AJAX_RESPONSE = "ajaxResponse";
    private static final String ERROR_DOCUMENT = "errorDocument";
   

    /**
     * @return result
     */
    public String query()  {
        
       
      
        try {
            usersMap = CSMUserService.getInstance().getCcctAndCtroUsers();
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
            List<DocumentDTO> isoList = PaRegistry.getDocumentService().
                    getReportsDocumentsByStudyProtocol(studyProtocolIi);
            if (isoList != null && !(isoList.isEmpty())) {
                trialDocumentList = new ArrayList<TrialDocumentWebDTO>();
                for (DocumentDTO dto : isoList) {
                    trialDocumentList.add(new TrialDocumentWebDTO(dto));
                }
            }
            return SUCCESS;

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR_DOCUMENT;
        }
        
       
    }

    /**
     * @return result
     */
     @Override
    public String input() {
         return INPUT;
     }

     /**
      * @return result
      */
     public String create() {
         if (StringUtils.isEmpty(trialDocumentWebDTO.getTypeCode())) {
             addFieldError("trialDocumentWebDTO.typeCode",
                     getText("error.trialDocument.typeCode"));
         }
         if (StringUtils.isEmpty(uploadFileName)) {
             addFieldError("trialDocumentWebDTO.uploadFileName",
                     getText("error.trialDocument.uploadFileName"));
         }
         if (hasFieldErrors()) {
             return ERROR_DOCUMENT;
         }
         try {
             Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
             DocumentDTO docDTO = new DocumentDTO();
             docDTO.setStudyProtocolIdentifier(studyProtocolIi);
             docDTO.setTypeCode(
                     CdConverter.convertStringToCd(trialDocumentWebDTO.getTypeCode()));
             docDTO.setFileName(StConverter.convertToSt(uploadFileName));
             docDTO.setText(EdConverter.convertToEd(IOUtils.toByteArray(new FileInputStream(upload))));
             PaRegistry.getDocumentService().create(docDTO);
             
           
              
             
             
             //send notification email  if comparison document is uploaded
             if (docDTO.getTypeCode().getCode().equals(DocumentTypeCode.COMPARISON.getCode())) {
                 File newFile = new File(upload.getParent(), uploadFileName);
                 FileUtils.copyFile(upload, newFile);

                 sendCtroNotificationEmail(docDTO.getStudyProtocolIdentifier(), newFile);
             }
             
             query();
             request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
             return SUCCESS;
         } catch (Exception e) {
             request.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return ERROR_DOCUMENT;
         }
     }

     /**
      * @return result
      */
     public String saveFile() {
         try {
             DocumentDTO  docDTO =
                 PaRegistry.getDocumentService().get(IiConverter.convertToIi(id));

             StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
             .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);

             StringBuffer fileName = new StringBuffer();
             fileName.append(spDTO.getNciIdentifier()).append('-').append(docDTO.getFileName().getValue());

             servletResponse.setContentType("application/octet-stream");
             servletResponse.setContentLength(docDTO.getText().getData().length);
             servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toString() + "\"");
             servletResponse.setHeader("Pragma", "public");
             servletResponse.setHeader("Cache-Control", "max-age=0");
             
           

            
             ByteArrayInputStream bStream = new ByteArrayInputStream(docDTO.getText().getData());
             ServletOutputStream out = servletResponse.getOutputStream();
             IOUtils.copy(bStream, out);
            
         } catch (FileNotFoundException err) {
             LOG.error("TrialDocumentAction failed with FileNotFoundException: "
                     + err);
             this.addActionError("File not found: " + err.getLocalizedMessage());
             query();
             return ERROR_DOCUMENT;
         } catch (Exception e) {
             request.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             query();
             return ERROR_DOCUMENT;
         }
         return NONE;
     }

     /**
      * @return result
      */
     public String edit() {
         try {
              DocumentDTO  docDTO =
                 PaRegistry.getDocumentService().get(IiConverter.convertToIi(id));
             trialDocumentWebDTO = new TrialDocumentWebDTO(docDTO);
         } catch (Exception e) {
             request.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return INPUT;
         }
         return INPUT;
     }

     /**
      * @return result
      */
     public String update() {
         if (StringUtils.isEmpty(trialDocumentWebDTO.getTypeCode())) {
             addFieldError("trialDocumentWebDTO.typeCode", getText("error.trialDocument.typeCode"));
         }
         if (StringUtils.isEmpty(uploadFileName)) {
             addFieldError("trialDocumentWebDTO.uploadFileName", getText("error.trialDocument.uploadFileName"));
         }
         if (hasFieldErrors()) {
             return ERROR_DOCUMENT;
         }
         try {

             Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
             DocumentDTO  docDTO = new DocumentDTO();
             docDTO.setIdentifier(IiConverter.convertToIi(id));
             docDTO.setStudyProtocolIdentifier(studyProtocolIi);
             docDTO.setTypeCode(
                     CdConverter.convertStringToCd(trialDocumentWebDTO.getTypeCode()));
             docDTO.setFileName(StConverter.convertToSt(uploadFileName));
             
             final FileInputStream stream = new FileInputStream(upload);
             docDTO.setText(EdConverter.convertToEd(IOUtils.toByteArray(stream)));
             IOUtils.closeQuietly(stream);
             PaRegistry.getDocumentService().update(docDTO);
             //check if before or after results document is updated
             if (docDTO.getTypeCode().getCode().equals(DocumentTypeCode.BEFORE_RESULTS.getCode()) 
                 || docDTO.getTypeCode().getCode().equals(DocumentTypeCode.AFTER_RESULTS.getCode())) {
                 
                 List<DocumentDTO> isoList = PaRegistry.getDocumentService().
                         getReportsDocumentsByStudyProtocol(studyProtocolIi);
                 //delete comparison document
                 for (DocumentDTO documentDTO : isoList) {
                    if (documentDTO.getTypeCode().getCode().equals("Comparison")) {
                        String [] delteObjectsArray = new String[1];
                        delteObjectsArray[0] = IiConverter.convertToString(documentDTO.getIdentifier());
                        setObjectsToDelete(delteObjectsArray);
                    }
                 }
                 if (getObjectsToDelete() != null &&  getObjectsToDelete().length > 0) {
                     deleteSelectedObjects();
                 }
             }
             //send notification email  if comparison document is uploaded
             if (docDTO.getTypeCode().getCode().equals(DocumentTypeCode.COMPARISON.getCode())) {
                 File newFile = new File(upload.getParent(), uploadFileName);
                 FileUtils.copyFile(upload, newFile);
                 sendCtroNotificationEmail(docDTO.getStudyProtocolIdentifier() , newFile);
             }
             request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
             query();
         } catch (Exception e) {
             request.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return ERROR_DOCUMENT;
         }
         return SUCCESS;
     }
     
     /**
      * @return result
      */
     public String reviewCtro() {
       
         try {

             DocumentDTO  docDTO =
             PaRegistry.getDocumentService().get(IiConverter.convertToIi(id));
             docDTO.setCtroUserId(getCtroUserId());
             docDTO.setCtroUserReviewDateTime(TsConverter.convertToTs(new Date()));
             
             PaRegistry.getDocumentService().updateForReview(docDTO);
             request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
             
             File file = new File(SystemUtils.JAVA_IO_TMPDIR + "/" + docDTO.getFileName().getValue());
             FileUtils.writeByteArrayToFile(file, docDTO.getText().getData());

             sendCcctNotificationEmail(docDTO.getStudyProtocolIdentifier() , file);
             
             query();
         } catch (Exception e) {
             request.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return ERROR;
         }
         return SUCCESS;
     }
     
     /**
      * @return result
      */
     public String reviewCcct() {
       
         try {

             DocumentDTO  docDTO =
             PaRegistry.getDocumentService().get(IiConverter.convertToIi(id));
             docDTO.setCcctUserName(StConverter.convertToSt(getCcctUserName()));
             docDTO.setCcctUserReviewDateTime(TsConverter.convertToTs(new Date()));
             
             PaRegistry.getDocumentService().updateForReview(docDTO);
             request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
             query();
         } catch (Exception e) {
             request.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return ERROR;
         }
         return SUCCESS;
     }

    /**
     * @return result
     */
    public String delete() {
        try {
            checkIfAnythingSelected();
          
            if (hasFieldErrors()) {
                return DELETE_RESULT;
            }
            deleteSelectedObjects();
            request.setAttribute(
                    Constants.SUCCESS_MESSAGE, Constants.MULTI_DELETE_MESSAGE);
        } catch (Exception e) {
            request.setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return query();
    }
     
    
    @Override
    public void deleteObject(Long objectId) throws PAException {
        PaRegistry.getDocumentService().delete(
                IiConverter.convertToDocumentIi(objectId),
                StConverter.convertToSt(trialDocumentWebDTO
                        .getInactiveCommentText()));
    }
    
  
  
    /**
     * @return flag
     * @throws PAException PAException
     */
    public String checkIfComparisionDocExists() throws PAException {
       
        Boolean result = false;
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
        List<DocumentDTO> isoList = PaRegistry.getDocumentService().
                getReportsDocumentsByStudyProtocol(studyProtocolIi);
        
        //check if comaprision document exists
        for (DocumentDTO documentDTO : isoList) {
            if (documentDTO.getTypeCode().getCode().equals(DocumentTypeCode.COMPARISON.getCode())) {
                result = true;
                break;
            }
        }
        ajaxResponseStream = new ByteArrayInputStream(result.toString().getBytes());
        return AJAX_RESPONSE;
    }
    
    private void sendCtroNotificationEmail(Ii studyProtocolIdentifier , File attachment) throws PAException {
      
       
        String nciID = paServiceUtil.getTrialNciId(Long.valueOf(studyProtocolIdentifier.getExtension()));
        String nctId = paServiceUtil.getStudyIdentifier(studyProtocolIdentifier, PAConstants.NCT_IDENTIFIER_TYPE);
        PaRegistry.getMailManagerService().sendComparisonDocumentToCtro(nciID , nctId,  attachment);
    }
    
    private void sendCcctNotificationEmail(Ii studyProtocolIdentifier , File attachment) throws PAException {
        String nciID = paServiceUtil.getTrialNciId(Long.valueOf(studyProtocolIdentifier.getExtension()));
        String nctId = paServiceUtil.getStudyIdentifier(studyProtocolIdentifier, PAConstants.NCT_IDENTIFIER_TYPE);
        PaRegistry.getMailManagerService().sendComparisonDocumentToCcct(nciID , nctId,  attachment);
    }

     /**
      * @return fileName
      */
     public String getUploadFileName() {
          return uploadFileName;
      }

      /**
      * @param uploadFileName uploadFileName
      */
     public void setUploadFileName(String uploadFileName) {
         this.uploadFileName = uploadFileName;
      }

      /**
      * @return upload
      */
     public File getUpload() {
         return upload;
      }

      /**
      * @param upload upload
      */
     public void setUpload(File upload) {
          this.upload = upload;
      }

    /**
     * @return trialDocumentList
     */
    public List<TrialDocumentWebDTO> getTrialDocumentList() {
        return trialDocumentList;
    }

    /**
     * @param trialDocumentList trialDocumentList
     */
    public void setTrialDocumentList(List<TrialDocumentWebDTO> trialDocumentList) {
        this.trialDocumentList = trialDocumentList;
    }

    /**
     * @return trialDocumentWebDTO
     */
    public TrialDocumentWebDTO getTrialDocumentWebDTO() {
        return trialDocumentWebDTO;
    }

    /**
     * @param trialDocumentWebDTO trialDocumentWebDTO
     */
    public void setTrialDocumentWebDTO(TrialDocumentWebDTO trialDocumentWebDTO) {
        this.trialDocumentWebDTO = trialDocumentWebDTO;
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
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }
    /**
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }
    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return the paServiceUtil
     */
    public PAServiceUtils getPaServiceUtil() {
        return paServiceUtil;
    }

    /**
     * @param paServiceUtil the paServiceUtil to set
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
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
     * @return Map
     */
    public Map<Long, String> getUsersMap() {
        return usersMap;
    }

    /**
     * @param usersMap usersMap
     */ 
    public void setUsersMap(Map<Long, String> usersMap) {
        this.usersMap = usersMap;
    }

    /**
     * @return ctroUserId
     */
    public Long getCtroUserId() {
        return ctroUserId;
    }

    /**
     * @param ctroUserId ctroUserId
     */
    public void setCtroUserId(Long ctroUserId) {
        this.ctroUserId = ctroUserId;
    }

   

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.request = servletRequest;
        
    }

    /**
     * @return parentPage
     */
    public String getParentPage() {
        return parentPage;
    }

    /**
     * @param parentPage parentPage
     */
    public void setParentPage(String parentPage) {
        this.parentPage = parentPage;
    }

    /**
     * @return ccctUserName
     */
    public String getCcctUserName() {
        return ccctUserName;
    }

    /**
     * @param ccctUserName ccctUserName
     */
    public void setCcctUserName(String ccctUserName) {
        this.ccctUserName = ccctUserName;
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
