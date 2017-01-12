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
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO.ErrorMessageTypeEnum;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorOptions;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Kalpana Guthikonda
 */
public class AbstractionCompletionAction extends ActionSupport implements Preparable, ServletResponseAware {

    private static final long serialVersionUID = -8931205246262355662L;
    private static final Logger LOG  = Logger.getLogger(AbstractionCompletionAction.class);

    private static final int MAXIMUM_RANDOM_FILE_NUMBER = 1000;
    private static final String DISPLAY_XML = "displayXML";
    private static final String TSR = "TSR_";
    private static final String EXTENSION_RTF = ".rtf";
    private static final String ABSTRACTION_ERROR = "An error happened during abstraction: ";
    
    
    private AbstractionCompletionServiceLocal abstractionCompletionService;
    private CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService;   
    private TSRReportGeneratorServiceLocal tsrReportGeneratorService;

    private List<AbstractionCompletionDTO> abstractionList;
    private List<AbstractionCompletionDTO> abstractionAdminList;
    private List<AbstractionCompletionDTO> abstractionScientificList;
    private List<AbstractionCompletionDTO> absWarningList;
    private boolean abstractionError;
    private boolean absAdminError;
    private boolean absScientificError;

    private Long studyProtocolId;
    private HttpServletResponse servletResponse;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        abstractionCompletionService = PaRegistry.getAbstractionCompletionService();
        ctGovXmlGeneratorService = PaRegistry.getCTGovXmlGeneratorService();        
        tsrReportGeneratorService = PaRegistry.getTSRReportGeneratorServiceLocal();
    }

    /**
    * @return result
    */
    public String query() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Ii studyProtocolIi = (Ii) session.getAttribute(Constants.STUDY_PROTOCOL_II);
            abstractionList = abstractionCompletionService.validateAbstractionCompletion(studyProtocolIi);
            abstractionAdminList = new ArrayList<AbstractionCompletionDTO>();
            abstractionScientificList = new ArrayList<AbstractionCompletionDTO>();
            setAbsWarningList(getWarnings());
            setAbstractionError(errorExists());
                for (AbstractionCompletionDTO abs : abstractionList) {
                    if (abs.getErrorType().equalsIgnoreCase("error")) {
                        if (abs.getErrorMessageType().equals(ErrorMessageTypeEnum.ADMIN)) {
                            getAbstractionAdminList().add(abs);
                            setAbsAdminError(true);                            
                        } else if (abs.getErrorMessageType().equals(ErrorMessageTypeEnum.SCIENTIFIC)) {
                            getAbstractionScientificList().add(abs);
                            setAbsScientificError(true);                            
                        }  
                    }
                }
            
        } catch (Exception e) {
            LOG.error(ABSTRACTION_ERROR, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, ABSTRACTION_ERROR + e);
        }
        return SUCCESS;
    }


    /**
     * @return res
     */
    public String generateXML() {
        try {
            String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
            if (pId == null) {
                return DISPLAY_XML;
            }
            String xmlData = ctGovXmlGeneratorService.generateCTGovXml(
                    IiConverter.convertToStudyProtocolIi(getStudyProtocolId()),
                    CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
            servletResponse.setContentType("application/xml");
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentLength(xmlData.getBytes("UTF-8").length);
            OutputStreamWriter writer = new OutputStreamWriter(servletResponse.getOutputStream(), "UTF-8");
            writer.write(xmlData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            return DISPLAY_XML;
        }
        return NONE;
    }
    
    /**
     * @return res
     */
    public String viewTSR() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Ii studyProtocolIi = (Ii) session.getAttribute(Constants.STUDY_PROTOCOL_II);
            ByteArrayOutputStream reportData = tsrReportGeneratorService.generateRtfTsrReport(studyProtocolIi);
            int randomInt = new Random().nextInt(MAXIMUM_RANDOM_FILE_NUMBER);            
            String fileNameDateStr = DateFormatUtils.format(new Date(), PAConstants.TSR_DATE_FORMAT);
            String fileName = TSR + randomInt + "_" + fileNameDateStr 
                    + "_" + studyProtocolIi.getExtension() + EXTENSION_RTF;
            getServletResponse().setContentType("application/rtf");
            servletResponse.setContentLength(reportData.size());
            servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            servletResponse.setHeader("Pragma", "public");
            servletResponse.setHeader("Cache-Control", "max-age=0");
            reportData.writeTo(servletResponse.getOutputStream());
            reportData.flush();
            servletResponse.getOutputStream().flush();
            servletResponse.getOutputStream().close();
        } catch (Exception e) {
            return SUCCESS;
        }
        return NONE;
    }

    /**
     *
     * @return res
     */
    public String displayReportingXML() {
        String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
        ServletActionContext.getRequest().setAttribute("protocolIdForXmlGeneration", pId);
        return DISPLAY_XML;
    }

    /**
    * @return abstractionList
    */
    public List<AbstractionCompletionDTO> getAbstractionList() {
      return abstractionList;
    }

    /**
    * @param abstractionList abstractionList
    */
    public void setAbstractionList(List<AbstractionCompletionDTO> abstractionList) {
      this.abstractionList = abstractionList;
    }

    /**
     * Test if the abstraction list contains errors.
     * @return true if the abstraction list contains errors
     */
    boolean errorExists() {
        boolean errorExist = false;
        for (AbstractionCompletionDTO absDto : abstractionList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                errorExist = true;
                break;
            }
        }
        return errorExist;
    }
    /**
     * 
     * @return absWarnings with list of warnings.
     */
    List<AbstractionCompletionDTO> getWarnings() {
        List<AbstractionCompletionDTO> absWarnings = new ArrayList<AbstractionCompletionDTO>();
        for (AbstractionCompletionDTO absDto : abstractionList) {
            if (absDto.getErrorType().equalsIgnoreCase("warning")) {
                absWarnings.add(absDto);
            }
        }
        return absWarnings;      
    }
    

    /**
     *
     * @return abstractionError abstractionError
     */
    public boolean isAbstractionError() {
        return abstractionError;
    }

    /**
     *
     * @param abstractionError abstractionError
     */
    public void setAbstractionError(boolean abstractionError) {
        this.abstractionError = abstractionError;
    }

    /**
     *
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     *
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @param abstractionCompletionService the abstractionCompletionService to set
     */
    public void setAbstractionCompletionService(AbstractionCompletionServiceLocal abstractionCompletionService) {
        this.abstractionCompletionService = abstractionCompletionService;
    }

    /**
     * @param ctGovXmlGeneratorService the ctGovXmlGeneratorService to set
     */
    public void setCtGovXmlGeneratorService(CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService) {
        this.ctGovXmlGeneratorService = ctGovXmlGeneratorService;
    }

    /**
     * @param tsrReportGeneratorService the tsrReportGeneratorService to set
     */
    public void setTsrReportGeneratorService(TSRReportGeneratorServiceRemote tsrReportGeneratorService) {
        this.tsrReportGeneratorService = tsrReportGeneratorService;
    }
    
    /**
     * @return abstractionAdminList
     */
    public List<AbstractionCompletionDTO> getAbstractionAdminList() {
        return abstractionAdminList;
    }
    /**
     * @param abstractionAdminList abstractionAdminList
     */
    public void setAbstractionAdminList(
            List<AbstractionCompletionDTO> abstractionAdminList) {
        this.abstractionAdminList = abstractionAdminList;
    }
    /**
     * @return abstractionScientificList
     */
    public List<AbstractionCompletionDTO> getAbstractionScientificList() {
        return abstractionScientificList;
    }
    /**
     * @param abstractionScientificList abstractionScientificList
     */
    public void setAbstractionScientificList(
            List<AbstractionCompletionDTO> abstractionScientificList) {
        this.abstractionScientificList = abstractionScientificList;
    }
    /**
    *
    * @return absAdminError absAdminError
    */
    public boolean isAbsAdminError() {
        return absAdminError;
    }
    /**
    *
    * @param absAdminError absAdminError
    */
    public void setAbsAdminError(boolean absAdminError) {
        this.absAdminError = absAdminError;
    }
    /**
    *
    * @return absScientificError absScientificError
    */
    public boolean isAbsScientificError() {
        return absScientificError;
    }
    /**
    *
    * @param absScientificError absScientificError
    */  
    public void setAbsScientificError(boolean absScientificError) {
        this.absScientificError = absScientificError;
    }
    /**
     * @return absWarningList
     */
    public List<AbstractionCompletionDTO> getAbsWarningList() {
        return absWarningList;
    }
    /**
     * @param absWarningList absWarningList
     */
    public void setAbsWarningList(List<AbstractionCompletionDTO> absWarningList) {
        this.absWarningList = absWarningList;
    }
}

