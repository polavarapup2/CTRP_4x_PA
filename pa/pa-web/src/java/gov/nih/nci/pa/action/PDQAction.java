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
package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author vrushali
 *
 */
public class PDQAction extends ActionSupport implements Preparable, ServletResponseAware {

    private static final long serialVersionUID = -1336481874691716853L;

    private PDQUpdateGeneratorTaskServiceLocal pdqUpdateGeneratorTaskService;
    private PDQXmlGeneratorServiceRemote pdqXmlGenerator;

    private HttpServletResponse servletResponse;
    private String date;
    private List<String> listOfFileNames = new ArrayList<String>();
    private String studyProtocolId = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        pdqUpdateGeneratorTaskService = PaRegistry.getPDQUpdateGeneratorTaskService();
        pdqXmlGenerator = PaRegistry.getPDQXmlGeneratorService();
    }

    /**
     * @return String
     *
     */
    public String startProcess() {
        try {
            pdqUpdateGeneratorTaskService.performTask();
        } catch (PAException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        ServletActionContext.getRequest().setAttribute("showButton", "no");
        addActionMessage("Process has been started...");
        return SUCCESS;
    }

    /**
     *
     * @return success
     */
    public String getAvailableFiles() {
        try {
            listOfFileNames = pdqUpdateGeneratorTaskService.getListOfFileNames();
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        ServletActionContext.getRequest().setAttribute("listOfFileNames", listOfFileNames);
        return SUCCESS;
    }

    /**
     *
     * @return success
     */
    public String getFileByDate() {
        if (StringUtils.isEmpty(date)) {
            return ERROR;
        }
        try {
            File downloadFile = new File(pdqUpdateGeneratorTaskService.getRequestedFileName(date));
            servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/x-unknown");
            FileInputStream fileToDownload = new FileInputStream(downloadFile);
            servletResponse.setHeader("Cache-Control", "cache");
            servletResponse.setHeader("Pragma", "cache");
            servletResponse.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
            servletResponse.setContentLength(fileToDownload.available());
            int data;
            ServletOutputStream out = servletResponse.getOutputStream();
            while ((data = fileToDownload.read()) != -1) {
                out.write(data);
            }
            out.flush();
            out.close();
        } catch (PAException e) {
            addActionError(e.getMessage());
        } catch (IOException e) {
            addActionError("Requested file is not found" + e.getMessage());
        }
        return SUCCESS;
    }


    /**
     *
     * @return A single XML file.
     */
    public String getSingleExport() {
        try {
            if (StringUtils.isEmpty(studyProtocolId)) {
                return "singleExport";
            }
            Ii ii = new Ii();
            ii.setExtension(studyProtocolId);
            String xmlData = pdqXmlGenerator.generatePdqXml(ii);
            servletResponse.setContentType("application/xml");
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentLength(xmlData.getBytes("UTF-8").length);
            OutputStreamWriter writer = new OutputStreamWriter(servletResponse.getOutputStream(), "UTF-8");
            writer.write(xmlData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            addActionMessage("unable to get PDQ file " + e.getMessage());
            LOG.error(e.getMessage(), e);
            return "singleExport";
        }
        return NONE;
    }


    /**
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * @param response servletResponse
     */
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param listOffileName the listOffileName to set
     */
    public void setListOffileName(List<String> listOffileName) {
        this.listOfFileNames = listOffileName;
    }

    /**
     * @return the listOffileName
     */
    public List<String> getListOffileName() {
        return listOfFileNames;
    }

    /**
     * @param pdqUpdateGeneratorTaskService the pdqUpdateGeneratorTaskService to set
     */
    public void setPdqUpdateGeneratorTaskService(PDQUpdateGeneratorTaskServiceLocal pdqUpdateGeneratorTaskService) {
        this.pdqUpdateGeneratorTaskService = pdqUpdateGeneratorTaskService;
    }

    /**
     * @param pdqXmlGen the generator to set.
     */
    public void setPdqXmlGenerator(PDQXmlGeneratorServiceRemote pdqXmlGen) {
        this.pdqXmlGenerator = pdqXmlGen;
    }

    /**
     * @param id the ID.
     */
    public void setStudyProtocolId(String id) {
        this.studyProtocolId = id;
    }

    /**
     * @return the ID.
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }


}
