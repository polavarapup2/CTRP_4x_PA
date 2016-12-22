/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
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
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
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
package gov.nih.nci.accrual.service.batch;

import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.io.FileUtils;
import org.hibernate.HibernateException;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class BatchFileServiceBeanLocal implements BatchFileService {
    
    /**
     * {@inheritDoc}
     */
    public void save(BatchFile batchFile) throws PAException {
        if (batchFile.getId() != null) {
            throw new PAException("Please call update() with existing batch file objects.");
        }
        batchFile.setDateLastCreated(new Date());
        batchFile.setUserLastCreated(AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser()));
        try {
            PaHibernateUtil.getCurrentSession().save(batchFile);
        } catch (HibernateException hbe) {
            throw new PAException("Error while saving batch file.", hbe);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BatchFile createBatchFile(File file, String destinationFileName) throws PAException {
        BatchFile batchFile = new BatchFile();
        batchFile.setSubmitter(getBatchSubmitter());
        batchFile.setFileLocation(writeBatchFileToFilesystem(file, destinationFileName));
        batchFile.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        save(batchFile);
        return batchFile;
    }
    
    private RegistryUser getBatchSubmitter() throws PAException {
        return PaServiceLocator.getInstance().getRegistryUserService().getUser(AccrualCsmUtil.getInstance()
                .getCSMUser(CaseSensitiveUsernameHolder.getUser()).getLoginName());
    }
    
    private String writeBatchFileToFilesystem(File file, String destinationFileName) throws PAException {
        try {
            String destination = PaEarPropertyReader.getAccrualBatchUploadPath() + File.separator + UUID.randomUUID() 
                + "-" + destinationFileName;
            File destinationFile = new File(destination);
            FileUtils.copyFile(file, destinationFile);
            return destinationFile.getAbsolutePath();
        } catch (IOException e) {
            throw new PAException("An error has occurred while trying to submit your batch data. Please try again.", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void update(BatchFile batchFile) throws PAException {
        if (batchFile.getId() == null) {
            throw new PAException("Please call save() with new batch file objects.");
        }
        batchFile.setDateLastUpdated(new Date());
        batchFile.setUserLastUpdated(AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser()));
        try {
            PaHibernateUtil.getCurrentSession().merge(batchFile);
        } catch (HibernateException hbe) {
            throw new PAException("Error while updating BatchFile.", hbe);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(BatchFile batchFile, AccrualCollections collection) throws PAException {
        if (collection.getId() == null) {
            collection.setDateLastCreated(new Date());
            collection.setUserLastCreated(batchFile.getUserLastCreated());
        } else {
            collection.setDateLastUpdated(new Date());
            collection.setUserLastUpdated(batchFile.getUserLastCreated());
        }
        collection.setBatchFile(batchFile);
        try {
            PaHibernateUtil.getCurrentSession().save(collection);
        } catch (HibernateException hbe) {
            throw new PAException("Error while saving AccrualCollections.", hbe);
        }
        update(batchFile);
    }

    /**
     * {@inheritDoc}
     */
    public BatchFile getById(Long id) throws PAException {
        try {
            return (BatchFile) PaHibernateUtil.getCurrentSession().get(BatchFile.class, id);
        } catch (HibernateException hbe) {
            throw new PAException("Error retrieving batch file with the identifier " + id, hbe);
        }
    }
}
