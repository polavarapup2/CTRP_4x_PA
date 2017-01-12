package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * @author Hugh Reinhart
 * @since Jul 20, 2012
 */
public class PriorSubmissionsAction extends AbstractListAccrualAction<HistoricalSubmissionDto> {

    private static final long serialVersionUID = 7146346266420362794L;
    
    private Long batchFileId;
    private String dateFrom;
    private String dateTo;

    @Override
    public void loadDisplayList() {
        try {
            RegistryUser ru = PaServiceLocator.getInstance().getRegistryUserService().getUser(
                    CaseSensitiveUsernameHolder.getUser());
            List<HistoricalSubmissionDto> submissionList = getSubmissionHistorySvc().search(
                    PAUtil.dateStringToTimestamp(getDateFrom()),
                    PAUtil.dateStringToTimestamp(getDateTo()), ru);
            setDisplayTagList(submissionList);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }
    /**
     * @return result
     * @throws PAException exception
     */
    public String viewDoc() throws PAException {
        // following code necessary to prevent viewing unauthorized files
        loadDisplayList();
        BatchFile batchFile = null;
        for (HistoricalSubmissionDto item : getDisplayTagList()) {
            if (getBatchFileId() != null && ObjectUtils.equals(item.getBatchFileIdentifier(), getBatchFileId())) {
                batchFile = getBatchFileSvc().getById(item.getBatchFileIdentifier());
                break;
            }
        }

        try {
            if (batchFile == null) {
                throw new PAException("File not found in authorized list.");
            }
            File file = new File(batchFile.getFileLocation());
            if (StringUtils.equals(FilenameUtils.getExtension(file.getName()), "zip")) {
                boolean superAbs = ServletActionContext.getRequest().isUserInRole(SUABSTRACTOR);
                String loginName = ServletActionContext.getRequest().getRemoteUser();
                if (!StringUtils.equals(loginName, batchFile.getUserLastCreated().getLoginName()) && !superAbs) {
                    addActionError("You do not have permission to download this file from this page. Only the user"
                    + " who uploaded this zip file can download it. Please contact the NCI CTRO"
                    + " if you have any questions about this file.");
                    return SUCCESS;
                }
            }
            InputStream in = new FileInputStream(file);
            byte[] byteArray = IOUtils.toByteArray(in);
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            HttpServletResponse servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/octet-stream");
            servletResponse.setContentLength(byteArray.length);
            servletResponse.setHeader("Cache-Control", "cache");
            servletResponse.setHeader("Pragma", "cache");
            servletResponse.setHeader("Content-Disposition",
                    "attachment; filename=\"" + AccrualUtil.getFileNameWithoutRandomNumbers(file.getName()) + "\"");
            ServletOutputStream out = servletResponse.getOutputStream();
            IOUtils.copy(bis, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            LOG.error("Exception occured while retrieving Accrual batch file: " + e);
            addActionError("Error retrieving file.");
            return SUCCESS;
        }
        return NONE;
    }

    /**
     * @return the dateFrom
     */
    public String getDateFrom() {
        return dateFrom;
    }
    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
    /**
     * @return the dateTo
     */
    public String getDateTo() {
        return dateTo;
    }
    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
    /**
     * @return the batchFileId
     */
    public Long getBatchFileId() {
        return batchFileId;
    }
    /**
     * @param batchFileId the batchFileId to set
     */
    public void setBatchFileId(Long batchFileId) {
        this.batchFileId = batchFileId;
    }
}
