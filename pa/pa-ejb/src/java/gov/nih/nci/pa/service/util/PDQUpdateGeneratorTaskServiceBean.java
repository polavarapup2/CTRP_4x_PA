/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * Implemention of task to generate, compress and store pdq update xml files.
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PDQUpdateGeneratorTaskServiceBean implements PDQUpdateGeneratorTaskServiceLocal {
    private static final String CTRP = "CTRP-";
    private final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final String ZIP_ARCHIVE_NAME = "CTRP-TRIALS-";
    private static final int MAX_FILE_AGE = -30;
    private static final Logger LOG = Logger.getLogger(PDQUpdateGeneratorTaskServiceBean.class);
    private static final String CLOSING_TD_TR = "</td></tr>";
    
    @EJB
    private PDQXmlGeneratorServiceRemote xmlGeneratorService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void performTask() throws PAException {
        Calendar startDateTime = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        long startTime = System.currentTimeMillis();
        String folderPath = PaEarPropertyReader.getPDQUploadPath();
        //First, delete all files older than 30 days.
        Date thirtyDaysAgo = DateUtils.addDays(new Date(), MAX_FILE_AGE);
        @SuppressWarnings("unchecked")
        Collection<File> filesToDelete =
            FileUtils.listFiles(new File(folderPath), FileFilterUtils.ageFileFilter(thirtyDaysAgo, true), null);
        for (File f : filesToDelete) {
            FileUtils.deleteQuietly(f);
        }
        //Name of the file will be CTRP-TRIALS-YYYY-MM-DD.zip
        Date now = new Date();
        LOG.info("PDQ trial exporter started." + dateFormat.format(startDateTime.getTime()));
        String zipFilePath = folderPath + File.separator + ZIP_ARCHIVE_NAME + date.format(now) + ".zip";
        //Using the temp path to ensure locking is correctly working.
        String tempZipFilePath = folderPath + File.separator + "TEMP-" + ZIP_ARCHIVE_NAME + date.format(now) + ".zip";
        File zipArchive = new File(tempZipFilePath);
        //Delete the temporary archive if it exists
        FileUtils.deleteQuietly(zipArchive);
        //Then get all abstracted collaborative trials and generate the xml for it, adding it as an entry in the
        //zip archive.
        List<StudyProtocolDTO> collaborativeTrials =
            PaRegistry.getStudyProtocolService().getAbstractedCollaborativeTrials();
        generateZipFile(zipArchive, collaborativeTrials, zipFilePath, folderPath);
        long endTime = System.currentTimeMillis();
        Calendar endDateTime = Calendar.getInstance();
        sendPDQExportSummaryEmail(createMailBody(
                dateFormat.format(startDateTime.getTime()),
                dateFormat.format(endDateTime.getTime()),
                collaborativeTrials.size(),
                getDurationBreakdown((endTime - startTime))));
        PdqXmlGenHelper.getFailedTrialsMap().clear();
        LOG.info("PDQ trial exporter complete :: Duration :: " + getDurationBreakdown((endTime - startTime)));
    }

    private void generateZipFile(File zipArchive, List<StudyProtocolDTO> collaborativeTrials, String zipFilePath,
            String folderPath) throws PAException {
        ZipOutputStream zipOutput = null;
        try {
            zipOutput = new ZipOutputStream(new BufferedOutputStream(FileUtils.openOutputStream(zipArchive)));
            FileChannel channel = new RandomAccessFile(zipArchive, "rw").getChannel();
            FileLock lock = channel.lock();
            for (StudyProtocolDTO sp : collaborativeTrials) {
                long eachTrialStartTime = System.currentTimeMillis();
                String pdqXml = xmlGeneratorService.generatePdqXml(sp.getIdentifier());
                String assignedIdentifier = PAUtil.getAssignedIdentifierExtension(sp);
                String fileName = assignedIdentifier + ".xml";
                File xmlFile = new File(folderPath + File.separator + fileName);
                //Write xml to temporary file.
                FileUtils.writeStringToFile(xmlFile, pdqXml);
                //Create new zip entry and write file date to archive
                ZipEntry entry = new ZipEntry(fileName);
                zipOutput.putNextEntry(entry);
                IOUtils.write(FileUtils.readFileToByteArray(xmlFile), zipOutput);
                //After file has been written to zip archive, delete it.
                FileUtils.deleteQuietly(xmlFile);
                PaHibernateUtil.getCurrentSession().clear();
                long eachTrialEndTime = System.currentTimeMillis();
                LOG.debug("Trial " + assignedIdentifier + " exported in :: "
                        + (eachTrialEndTime - eachTrialStartTime) + " ms");
            }
            lock.release();
            channel.close();
            zipOutput.close();
            //Finally move the generic file to the more specific path.
            FileUtils.moveFile(zipArchive, new File(zipFilePath));
        } catch (IOException e) {
            throw new PAException("Error attempting to create PDQ XML Archive.", e);
        } finally {
            IOUtils.closeQuietly(zipOutput);
            PdqXmlGenHelper.clearPOCache();
        }
    }

    /**
     * Returns the list of file names.
     * @return listOfFilesNames
     * @throws PAException on error
     */
    @Override
    public List<String> getListOfFileNames() throws PAException {
        List<String> listOfFileNames = new ArrayList<String>();
        File folder = new File(PaEarPropertyReader.getPDQUploadPath());
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(CTRP) && name.endsWith(".zip");
            }
        });
        for (int i = 0; i < listOfFiles.length; i++) {
            listOfFileNames.add(listOfFiles[i].getName());
        }
        Collections.sort(listOfFileNames);
        return listOfFileNames;
    }

    /**
     * Returns the file name.
     * @param requestedFileName requestedFileName
     * @return requested file name
     * @throws PAException on error
     */
    @Override
    public String getRequestedFileName(String requestedFileName) throws PAException {
        StringBuffer filePath = new StringBuffer();
        filePath.append(PaEarPropertyReader.getPDQUploadPath()).append(File.separator);
        String fileRequested = "";
        if (StringUtils.contains(requestedFileName, CTRP)) {
            fileRequested = requestedFileName;
        } else {
            fileRequested = ZIP_ARCHIVE_NAME + requestedFileName + "-T";
        }
        for (String fileName : getListOfFileNames()) {
            if (StringUtils.contains(fileName, fileRequested)) {
                filePath.append(fileName);
                break;
            }
        }
        return filePath.toString();
    }
    
    
    /**
     * Convert a millisecond duration to a string format.
     * 
     * @param millis
     *            A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    private static String getDurationBreakdown(long timeInMillis) {
        long millis = timeInMillis;
        if (millis < 0) {
            throw new IllegalArgumentException(
                    "Duration must be greater than zero!");
        }
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        StringBuilder sb = new StringBuilder();
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");
        return (sb.toString());
    }
    
    private void sendPDQExportSummaryEmail(String mailBody) {
        try {
            String mailTo = PaRegistry.getLookUpTableService().getPropertyValue("ctrp.support.email");
            PaRegistry.getMailManagerService()
                .sendMailWithHtmlBody(mailTo, "PDQ Export Summary", mailBody);
        } catch (PAException e) {
            LOG.error("Error sending error email during CTGov.xml generation.", e);
        }
    }   
    
    private String createMailBody(String jobStartTime, String jobEndTime,
            int totalTrials, String jobDuration) {
        StringBuilder mailBody = new StringBuilder();
        mailBody.append("<html><body><form>");
        mailBody.append("<table border=\"1\" width=\"50%\">");
        mailBody.append("<tr align=\"left\"><td>Duration</td><td>" + jobDuration + CLOSING_TD_TR);
        mailBody.append("<tr align=\"left\"><td>Start time</td><td>" + jobStartTime + CLOSING_TD_TR);
        mailBody.append("<tr align=\"left\"><td>Finish time</td><td>" + jobEndTime + CLOSING_TD_TR);
        mailBody.append("<tr align=\"left\"><td>Total trials processed</td><td>" + totalTrials + CLOSING_TD_TR);
        mailBody.append("</table>");
        if (!PdqXmlGenHelper.getFailedTrialsMap().isEmpty()) {
            mailBody.append("<br>");
            mailBody.append("List of erroneous trials. (" + PdqXmlGenHelper.getFailedTrialsMap().size() + " trials)");
            mailBody.append("<table border=\"1\" width=\"50%\">");
            mailBody.append("<tr><th style=\"width:20%;\">Identifier</th>");
            mailBody.append("<th style=\"width:80%;\">Error message</th></tr>");
            for (String id : PdqXmlGenHelper.getFailedTrialsMap().keySet()) {
                mailBody.append("<tr align=\"center\"><td>" + id + "</td><td>"
                        + PdqXmlGenHelper.getFailedTrialsMap().get(id)
                        + CLOSING_TD_TR);
            }
            mailBody.append("</table>");
        }
        mailBody.append("<br><br>");
        mailBody.append("Thank you,<br><br>NCI Clinical Trials Reporting Program");
        mailBody.append("</form></body></html>");
        return mailBody.toString();
    }

    /**
     * Sets the xml generator.
     * @param xmlGeneratorService the xml generator to set
     */
    public void setXmlGeneratorService(PDQXmlGeneratorServiceRemote xmlGeneratorService) {
        this.xmlGeneratorService = xmlGeneratorService;
    }
}
