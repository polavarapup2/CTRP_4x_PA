/***
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
 * (i) use,install, disclose, access, operate, execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions, You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice, this list of  conditions  and the  disclaimer  of  Article  6  in the
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
 * party proprietary programs, You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation to  secure  any  required permissions
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
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.registry.util.BatchConstants;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.jboss.security.SecurityContextAssociation;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Vrushali
 */
public class BatchUploadAction extends ActionSupport implements ServletResponseAware { //NOPMD

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(BatchUploadAction.class);
    private final PAServiceUtils paServiceUtil = new PAServiceUtils();
    private File trialData;
    private String trialDataFileName;
    private String trialDataContentType;
    private File docZip;
    private String docZipFileName;
    private String orgName;
    private HttpServletResponse servletResponse;
    private String page;

    /**
     * @param response servletResponse
     */
    @Override
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
     * @return the trialDataContentType
     */
    public String getTrialDataContentType() {
        return trialDataContentType;
    }

    /**
     * @param trialDataContentType the trialDataContentType to set
     */
    public void setTrialDataContentType(String trialDataContentType) {
        this.trialDataContentType = trialDataContentType;
    }

    /**
     * @return docZip
     */
    public File getDocZip() {
        return docZip;
    }

    /**
     * @param docZip docZip
     */
    public void setDocZip(File docZip) {
        this.docZip = docZip;
    }

    /**
     * @return docZipFileName
     */
    public String getDocZipFileName() {
        return docZipFileName;
    }

    /**
     * @param docZipFileName docZipFileName
     */
    public void setDocZipFileName(String docZipFileName) {
        this.docZipFileName = docZipFileName;
    }

    /**
     * @return OrgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return trialData
     */
    public File getTrialData() {
        return trialData;
    }

    /**
     * @param trialData trialData
     */
    public void setTrialData(File trialData) {
        this.trialData = trialData;
    }

    /**
     * @return trialDataFileName
     */
    public String getTrialDataFileName() {
        return trialDataFileName;
    }

    /**
     * @param trialDataFileName trialDataFileName
     */
    public void setTrialDataFileName(String trialDataFileName) {
        this.trialDataFileName = trialDataFileName;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     *
     * @return res
     */
    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * process the batch.
     *
     * @return String
     */
    public String process() {
        // validate the form elements
        validateForm();
        if (hasFieldErrors()) {
            return ERROR;
        }
        // First save uploaded xls and then zip files
        String uploadedLoc;
        try {
            uploadedLoc = uploadFile();
            String unzipLoc = null;
            if (StringUtils.isNotBlank(docZipFileName)) {
                unzipLoc = unZipDoc(uploadedLoc + File.separator + docZipFileName);
            }
            // helper to unzip the zip files
            Thread batchProcessor =
                    new Thread(new BatchHelper(uploadedLoc, trialDataFileName, unzipLoc, ServletActionContext
                            .getRequest().getRemoteUser(), orgName, SecurityContextAssociation.getSecurityContext()));
            batchProcessor.start();

        } catch (PAException e) {
            LOG.error("error in Batch" + e.getMessage());
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            addActionError(e.getMessage());
            RegistryUtil.generateMail(Constants.ERROR_PROCESSING, ServletActionContext.getRequest().getRemoteUser(),
                    "", "", "", "", e.getMessage(), null, null);
            return ERROR;
        }
        return "batch_confirm";
    }

    private String uploadFile() throws PAException {
        String uploadedLoc = "";

        StringBuffer folderPath = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
        LOG.debug("folderPath: " + folderPath);
        DateFormat df = new SimpleDateFormat("MMddyyyyHHmmss", Locale.getDefault());
        df.setLenient(false);
        Date today = new Date();
        folderPath.append(File.separator).append(orgName).append(df.format(today));
        LOG.debug("sbFolderPath: " + folderPath);
        File f = new File(folderPath.toString());
        if (!f.exists()) {
            // create a new directory
            boolean md = f.mkdir();
            if (md) {
                // save the XLS file and then the ZIp file
                saveFile(folderPath.toString(), trialDataFileName, trialData);
                if (StringUtils.isNotBlank(docZipFileName)) { //NOPMD
                    saveFile(folderPath.toString(), docZipFileName, docZip);
                }
                uploadedLoc = folderPath.toString();
            }
        }
        return uploadedLoc;
    }

    /**
     * save the file.
     *
     * @param fileName file
     */
    private void saveFile(String folderPath, String fileName, File file) throws PAException {
        // create the file
        try {
            File outFile = new File(folderPath + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            int bytesRead = 0;
            byte[] buffer = new byte[BatchConstants.READ_SIZE];
            InputStream stream = new FileInputStream(file);
            while ((bytesRead = stream.read(buffer, 0, BatchConstants.READ_SIZE)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new PAException("Error while creating directory  " + fileName, e);
        }
    }

    /**
     * Validate form fields.
     */
    public void validateForm() { // NOPMD
        if (StringUtils.isBlank(orgName)) {
            addFieldError("orgName", getText("error.batch.orgName"));
        }
        if (StringUtils.isBlank(trialDataFileName)) {
            addFieldError("trialDataFileName", getText("error.batch.trialDataFileName"));
        } else {
            if (!trialData.exists()) {
                addFieldError("trialDataFileName", getText("error.batch.invalidDocument"));
            }
            if (!paServiceUtil.isValidFileType(trialDataFileName, "xls")) {
                addFieldError("trialDataFileName", getText("error.batch.trialDataFileName.invalidFileType"));
            }
        }
        if (StringUtils.isNotBlank(docZipFileName)) {
            validateDocZipFileName();
        }
    }

    private void validateDocZipFileName() {
        if (!docZip.exists()) {
            addFieldError("docZipFileName", getText("error.batch.invalidDocument"));
        }
        if (!paServiceUtil.isValidFileType(docZipFileName, "zip")) {
            addFieldError("docZipFileName", getText("error.batch.docZipFileName.invalidFileType"));
        }
    }

    /**
     *
     * @param zipname name
     * @return str
     * @throws PAException ex
     */
    public String unZipDoc(String zipname) throws PAException {
        String folderLocation = null;
        try {
            ZipFile zipFile = new ZipFile(zipname);
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            String unzipFolderName = zipFile.getName().substring(0, zipname.indexOf(".zip"));
            folderLocation = unzipFolderName;
            File f = new File(unzipFolderName);
            if (!f.exists()) {
                LOG.debug("BatchHelper:unZipDoc not exists.. so creating new");
                // create a new directory
                boolean md = f.mkdir();
                if (md) {
                    while (enumeration.hasMoreElements()) {
                        ZipEntry zipEntry = enumeration.nextElement();
                        LOG.debug("Unzipping: " + zipEntry.getName());

                        BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));

                        int size;
                        File fdoc = new File(unzipFolderName + File.separator + zipEntry.getName());
                        byte[] buffer = new byte[BatchConstants.READ_SIZE];
                        FileOutputStream fos = new FileOutputStream(fdoc);
                        BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

                        while ((size = bis.read(buffer, 0, buffer.length)) != -1) {
                            bos.write(buffer, 0, size);
                        }

                        bos.flush();
                        bos.close();
                        fos.close();

                        bis.close();
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("BatchHelper:unZipDoc-", e);
            throw new PAException("Unable to Unzip the document", e);
        }
        return folderLocation;
    }

}
