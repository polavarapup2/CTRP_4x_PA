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
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.ExcelReader;
import gov.nih.nci.registry.util.RegistryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

import org.jboss.security.SecurityContext;
import org.jboss.security.SecurityContextAssociation;

/**
 *
 * @author Vrushali
 *
 *
 */
public class BatchHelper implements Runnable {

    private static final Logger LOG = Logger.getLogger(BatchHelper.class);
    private String uploadLoc = null;
    private String trialDataFileName = null;
    private String unzipLoc = null;
    private String userName = null;
    private String orgName = null;
    private final SecurityContext sc;

    /**
     *
     * @param uploadLoc loc
     * @param trialDataFileName name
     * @param unzipLoc loc
     * @param userName name
     * @param orgName orgName
     * @param sc SecurityContext
     */
    public BatchHelper(String uploadLoc, String trialDataFileName, // NOPMD
            String unzipLoc, String userName, String orgName, SecurityContext sc) {
        super();
        this.uploadLoc = uploadLoc;
        this.trialDataFileName = trialDataFileName;
        this.unzipLoc = unzipLoc;
        this.userName = userName;
        this.orgName = orgName;
        this.sc = sc;
    }

    /**
     *
     * @param fileName fileName
     * @throws PAException PAException
     * @return list list
     * @throws IOException i
     */
    public List<StudyProtocolBatchDTO> processExcel(String fileName) throws PAException, IOException {
        InputStream is;
        List<StudyProtocolBatchDTO> list = null;
        try {
            is = new FileInputStream(fileName);
            HSSFWorkbook wb = new ExcelReader().parseExcel(is);
            ExcelReader reader = new ExcelReader();
            list = reader.convertToDTOFromExcelWorkbook(wb, getOrgName());
        } catch (FileNotFoundException e) {
            LOG.error("BatchHelper:processExcel-" + e);
        }
        return list;
    }

    /**
     * starts the batch processing.
     */
    @Override
    public void run() {
        try {
            SecurityContextAssociation.setSecurityContext(sc);
            //Set Username for proper tracking.
            UsernameHolder.setUserCaseSensitive(userName);
            // open a new Hibernate session and bind to the context
            PaHibernateUtil.getHibernateHelper().openAndBindSession();

            // start reading the xls file and create the required DTO
            List<StudyProtocolBatchDTO> dtoList = processExcel(uploadLoc + File.separator + trialDataFileName);
            Map<String, String> map =
                    new BatchCreateProtocols().createProtocols(dtoList, unzipLoc + File.separator, userName);

            // get the Failed and Success count and remove it from map so that reporting of each trial
            String sucessCount = map.get("Success Trial Count");
            map.remove("Success Trial Count");
            String failedCount = map.get("Failed Trial Count");
            map.remove("Failed Trial Count");
            String warning = map.get("Delayed Posting Indicator warning");
            map.remove("Delayed Posting Indicator warning");
            Map<String, String> warningMap = new HashMap<String, String>();
            if (warning != null && !warning.isEmpty()) {
                warningMap = getWarningTypeWithNCIValues(warning);
            }
            String totalCount = Integer.toString(map.size());
            String attachFileName = generateExcelFileForAttachement(map);
            if (!MapUtils.isEmpty(warningMap)) {
                PaRegistry.getMailManagerService().generateCTROWarningEmail(userName, orgName, warningMap);
            }
           // generate the email
           RegistryUtil.sendEmail(Constants.PROCESSED, userName, sucessCount, failedCount, totalCount,
                    attachFileName, "", warningMap);
        } catch (Exception e) {
            LOG.error("Exception while processing batch" + e.getMessage());
            // generate the email
            RegistryUtil.generateMail(Constants.ERROR_PROCESSING, userName, "", "", "", "", e.getMessage(), null, null);
        } finally {
            SecurityContextAssociation.setSecurityContext(null);
            // unbind the Hibernate session
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
        }

    }
    /**
     * 
     * @param warning warning
     * @return map map
     */
    public Map<String, String> getWarningTypeWithNCIValues(String warning) {
        Map<String, String> uniqueWarningValues = new HashMap<String, String>();
        String[] innerPart = warning.trim().split("\\s+");
        if (innerPart.length > 0) {
            for (String seperatedValue : innerPart) {
               String[] secondinnerValue = seperatedValue.split(":+");
               if (secondinnerValue.length > 1) {
                   uniqueWarningValues.put(secondinnerValue[1], secondinnerValue[0]);
               }
            }
        }
        return uniqueWarningValues;
    }

    /**
     *
     * @param map ma
     * @return st
     */
    private String generateExcelFileForAttachement(Map<String, String> map) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("new sheet");
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cellId = headerRow.createCell(0);
            HSSFCell cellReason = headerRow.createCell(1);
            cellId.setCellValue(new HSSFRichTextString("Unique Trial Identifier"));
            cellReason.setCellValue(new HSSFRichTextString("Status"));
            Set<String> s = map.keySet();
            Iterator<String> iter = s.iterator();
            String mapLocalTrialId = null;
            int i = 0;
            HSSFRow detailRow;
            while (iter.hasNext()) {
                i++;
                mapLocalTrialId = iter.next();
                detailRow = sheet.createRow(i);
                detailRow.createCell(0).setCellValue(new HSSFRichTextString(mapLocalTrialId));
                detailRow.createCell(1).setCellValue(new HSSFRichTextString(map.get(mapLocalTrialId)));
            }
            sheet.autoSizeColumn((short) 0);
            sheet.autoSizeColumn((short) 1);

            FileOutputStream fileOut = new FileOutputStream(uploadLoc + File.separator + "batchUploadReport.xls");
            wb.write(fileOut);
            fileOut.close();
            LOG.info("Your file has been created succesfully");
        } catch (Exception ex) {
            LOG.error("exception while generating excel report" + ex.getMessage());
        }

        return uploadLoc + File.separator + "batchUploadReport.xls";
    }

    /**
     * @return the uploadLoc
     */
    public String getUploadLoc() {
        return uploadLoc;
    }

    /**
     * @param uploadLoc the uploadLoc to set
     */
    public void setUploadLoc(String uploadLoc) {
        this.uploadLoc = uploadLoc;
    }

    /**
     * @return the trialDataFileName
     */
    public String getTrialDataFileName() {
        return trialDataFileName;
    }

    /**
     * @param trialDataFileName the trialDataFileName to set
     */
    public void setTrialDataFileName(String trialDataFileName) {
        this.trialDataFileName = trialDataFileName;
    }

    /**
     * @return the unzipLoc
     */
    public String getUnzipLoc() {
        return unzipLoc;
    }

    /**
     * @param unzipLoc the unzipLoc to set
     */
    public void setUnzipLoc(String unzipLoc) {
        this.unzipLoc = unzipLoc;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

}
