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

package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for streaming the CTGov XML file.
 *
 */
public class CTGovXMLAction extends ActionSupport implements Preparable {
    private static final Logger LOG = Logger.getLogger(CTGovXMLAction.class);

    private CTGovXmlGeneratorServiceLocal ctService;
    private StudyProtocolServiceLocal studyProtocolService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private  RegistryUserService  registryUserService;

    private static final long serialVersionUID = 1L;
    private String id;
    private String root;
    private InputStream xmlFile;

    private static final String ERROR_XML =
        "<error>\n<error_description>Unable to generate the XML</error_description>\n"
        + "<study_identifier>${ID}</study_identifier>"
        + "<study_title>$TITLE</study_title><contact_info>"
        + "This issue has been reported to the CTRP Tech support</contact_info>\n"
        + "<error_type>Validation Error</error_type><error_messages><error_message>$TEXT"
        + "</error_message></error_messages></error>";

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        ctService = PaRegistry.getCTGovXmlGeneratorService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        registryUserService = PaRegistry.getRegistryUserService();
    }

    /**
     * Calls the CTGovXmlGeneratorService in PA to retrieve the CTGov xml for the given id.
     *
     * @return action result string
     */
    public String retrieveCtGovXML() {
        final Ii ii = createIi();
        StudyProtocolQueryDTO spqDto;
        try {
            spqDto = getStudyProtocolQueryDTO(ii);
        } catch (PAException e1) {
            xmlFile = new ByteArrayInputStream(convertToXML("No match found based on the NCI ID that was provided.",
                    null).getBytes());
            return "downloadXMLFile";
        }
        String xmlFileString = validateCtGovExportRules(spqDto, ii);
        if (xmlFileString != null) {
            xmlFileString = convertToXML(xmlFileString, spqDto);
        } else {
            try {
                xmlFileString = ctService.generateCTGovXml(ii);
            } catch (PAException e) {
                LOG.error("Error occurred while retrieving document from CT Service.", e);
                xmlFileString = convertToXML("An error occurred while retrieving the document for CT "
                        + "Gov. Please contact a System administrator.", null);
            }
        }

        xmlFile = new ByteArrayInputStream(xmlFileString.getBytes());
        return "downloadXMLFile";
    }

    private String convertToXML(String text, StudyProtocolQueryDTO spqDto) {
        StudyProtocolQueryDTO dto = spqDto;
        if (spqDto == null) {
            dto = new StudyProtocolQueryDTO();
            dto.setOfficialTitle("");
            dto.setNciIdentifier("");
        }
        String result = ERROR_XML.replace("${ID}", dto.getNciIdentifier());
        result = result.replace("$TITLE", dto.getOfficialTitle());
        result = result.replace("$TEXT", text);
        return result;
    }

    private Ii createIi() {
        final Ii ii = new Ii();
        ii.setExtension(getId());
        ii.setRoot(getRoot());
        return ii;
    }

    private StudyProtocolQueryDTO getStudyProtocolQueryDTO(Ii ii) throws PAException {
        StudyProtocolDTO studyProtocol = studyProtocolService.getStudyProtocol(ii);

        Long studyProtocolId = IiConverter.convertToLong(studyProtocol.getIdentifier());
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setStudyProtocolId(studyProtocolId);
        List<StudyProtocolQueryDTO> studyProtocolList = protocolQueryService
            .getStudyProtocolByCriteria(queryCriteria);

        return studyProtocolList.get(0);
    }

    private String validateCtGovExportRules(StudyProtocolQueryDTO spqDto, Ii spIi) {
        String dwfs = spqDto.getDocumentWorkflowStatusCode().getCode();
        List<String> abstractedCodes = Arrays.asList(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                .getCode(), DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode());
        if (spqDto.isProprietaryTrial()) {
            return "Abbreviated trials are not eligible for XML Export.";
        }
        if (!abstractedCodes.contains(dwfs)) {
            return "This trial is in review in CTRP by the CTRO and "
                    + "is not eligible to be uploaded to PRS at this time.";
        }
        if (!spqDto.getCtgovXmlRequiredIndicator()) {
            return "This trial cannot be uploaded to PRS.";
        }
        if (!isUserOwnerOfStudyProtocol(spIi)) {
            return "Authorization failed. User does not have ownership of the trial.";
        }
        return null;
    }

    /**
     * @param ii StudyProtocol ii
     * @return boolean. Return true if the user has access to the trial
     */
    boolean isUserOwnerOfStudyProtocol(Ii ii) {
        try {
            String loginName = getUserName();
            StudyProtocolDTO studyProtocol = studyProtocolService.getStudyProtocol(ii);
            Long studyProtocolId = IiConverter.convertToLong(studyProtocol.getIdentifier());
            RegistryUser registryUser = registryUserService.getUser(loginName);
            return registryUserService.isTrialOwner(registryUser.getId(), studyProtocolId);

        } catch (PAException e) {
            LOG.error("Error occurred while retrieving User. Please contact a System administrator.", e);
            return false;
        }
    }

    /**
     * @return user login name
     */
    private String getUserName() {
        if (ServletActionContext.getRequest().getUserPrincipal() == null) {
            return "";
        } else {
            return ServletActionContext.getRequest().getUserPrincipal().getName();
        }
    }

    /**
     * @return the xmlFile
     */
    public InputStream getXmlFile() {
        return xmlFile;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the root
     */
    public String getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * @param ctService the ctService to set
     */
    public void setCtService(CTGovXmlGeneratorServiceLocal ctService) {
        this.ctService = ctService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param service the service to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal service) {
        this.protocolQueryService = service;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserService registryUserService) {
        this.registryUserService = registryUserService;
    }

}
