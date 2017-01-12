/**
 * caBIG Open Source Software License
 * 
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
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
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
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
 *
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.PAHealthCareProviderLocal;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Gaurav Gupta
 *
 */
public class ParticipatingSitesAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = -447096616993689892L;

    private static final String INVESTIGATOR_DISPLAY_FMT = "[%s - %s, %s]</br>";
    
    private StudySiteServiceLocal studySiteService;
    private PAHealthCareProviderLocal paHealthCareProviderService;
    private CorrelationUtilsRemote correlationUtils = new CorrelationUtils();
    
    private List<PaOrganizationDTO> organizationList;
    
       
    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws PAException on error
     */
    @Override
    public void prepare() throws PAException {
        studySiteService = PaRegistry.getStudySiteService();
        paHealthCareProviderService = PaRegistry.getPAHealthCareProviderService();
    }
    
    /**
     * @return string
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    @Override
    public String execute() throws PAException {   // NOPMD
        String studyProtocolIdStr = ServletActionContext.getRequest().getParameter("studyProtocolId");        
        if (!StringUtils.isEmpty(studyProtocolIdStr)) {
            final Long studyProtocolId = Long.parseLong(studyProtocolIdStr);
            organizationList = (List<PaOrganizationDTO>) CacheUtils
                    .getFromCacheOrBackend(
                            CacheUtils.getViewParticipatingSitesCache(),
                            studyProtocolId.toString(),
                            new CacheUtils.Closure() {
                                @Override
                                public Object execute() throws PAException { // NOPMD
                                    final List<PaOrganizationDTO> list = new ArrayList<PaOrganizationDTO>();
                                    Ii spIi = IiConverter.convertToStudyProtocolIi(studyProtocolId);
                                    StudySiteDTO srDTO = new StudySiteDTO();
                                    srDTO.setFunctionalCode(CdConverter
                                            .convertStringToCd(StudySiteFunctionalCode.TREATING_SITE
                                                    .getCode()));
                                    final List<StudySiteDTO> studySites = studySiteService
                                            .getByStudyProtocol(spIi, srDTO);
                                    Long[] siteIds = collectSiteIDs(studySites);
                                    Map<Long, List<PaPersonDTO>> investigatorMap = paHealthCareProviderService
                                            .getPersonsByStudySiteId(
                                                    siteIds,
                                                    StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR
                                                            .getName());
                                    Map<Long, List<PaPersonDTO>> subInvestigatorMap = paHealthCareProviderService
                                            .getPersonsByStudySiteId(
                                                    siteIds,
                                                    StudySiteContactRoleCode.SUB_INVESTIGATOR
                                                            .getName());          
                                    for (StudySiteDTO studySiteDTO : studySites) {
                                        Organization orgBo = correlationUtils
                                                .getPAOrganizationByIi(studySiteDTO
                                                        .getHealthcareFacilityIi());
                                        PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
                                        orgWebDTO.setId(IiConverter.convertToString(studySiteDTO.getIdentifier()));
                                        orgWebDTO.setName(orgBo.getName());
                                        orgWebDTO.setNciNumber(orgBo.getIdentifier());   
                                        if (studySiteDTO.getStatusCode() != null) {
                                            orgWebDTO.setStatus(studySiteDTO.getStatusCode().getCode());
                                        }
                                        if (studySiteDTO.getProgramCodeText() != null) {
                                            orgWebDTO.setProgramCode(StConverter
                                                    .convertToString(studySiteDTO
                                                            .getProgramCodeText()));
                                        }
                                        if (studySiteDTO.getIdentifier() != null
                                                && studySiteDTO.getIdentifier()
                                                        .getExtension() != null) {
                                            final Long studySiteId = Long
                                                    .valueOf(studySiteDTO
                                                            .getIdentifier()
                                                            .getExtension()
                                                            .toString());
                                            orgWebDTO.setInvestigator(convertInvestigators(studySiteId,
                                                    investigatorMap, subInvestigatorMap));
                                        }
                                        list.add(orgWebDTO);
                                    }
                                    
                                    Collections.sort(list, new Comparator<PaOrganizationDTO>() {
                                        @Override
                                        public int compare(PaOrganizationDTO o1, PaOrganizationDTO o2) {
                                            return StringUtils.defaultString(o1.getName()).compareTo(
                                                    StringUtils.defaultString(o2.getName()));
                                        }
                                    });
                                    
                                    return list.isEmpty() ? null : list;
                                }
                            });
        }
        organizationList = organizationList == null ? new ArrayList<PaOrganizationDTO>()
                : organizationList;
        return SUCCESS;
    }
    
    private Long[] collectSiteIDs(List<StudySiteDTO> studySites) {
        List<Long> list = new ArrayList<Long>();
        for (StudySiteDTO studySiteDTO : studySites) {
            if (studySiteDTO.getIdentifier() != null
                    && studySiteDTO.getIdentifier().getExtension() != null) {
                final Long studySiteId = Long.valueOf(studySiteDTO
                        .getIdentifier().getExtension().toString());
                list.add(studySiteId);
            }
        }
        return list.toArray(new Long[0]); // NOPMD
    }

    private String convertInvestigators(final Long studySiteId,
            Map<Long, List<PaPersonDTO>> investigatorMap,
            Map<Long, List<PaPersonDTO>> subInvestigatorMap) throws PAException {
        StringBuffer invList = new StringBuffer();
        List<PaPersonDTO> principalInvestigators = investigatorMap.get(studySiteId);
        getInvestigatorDisplayString(invList, principalInvestigators);
        List<PaPersonDTO> sublInvestigators = subInvestigatorMap.get(studySiteId);
        getInvestigatorDisplayString(invList, sublInvestigators);
        return invList.toString();
    }
    
    private void getInvestigatorDisplayString(StringBuffer invList, List<PaPersonDTO> investigators) {
        if (investigators != null) {
            for (PaPersonDTO pi : investigators) {
                String fullName = StringUtils.defaultString(pi.getFullName());
                String roleName = PAUtil.getCode(pi.getRoleName());
                String status = PAUtil.getCode(pi.getStatusCode());
                invList.append(String.format(INVESTIGATOR_DISPLAY_FMT, fullName, roleName, status));
            }
        }
    }

    /**
     * 
     * @return StudySiteServiceLocal
     */
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * 
     * @param studySiteService StudySiteServiceLocal
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * 
     * @return CorrelationUtilsRemote
     */
    public CorrelationUtilsRemote getCorrelationUtils() {
        return correlationUtils;
    }

    /**
     * 
     * @param correlationUtils CorrelationUtilsRemote
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

    /**
     * 
     * @return List<PaOrganizationDTO>
     */
    public List<PaOrganizationDTO> getOrganizationList() {
        return organizationList;
    }

    /**
     * 
     * @param organizationList List<PaOrganizationDTO>
     */
    public void setOrganizationList(List<PaOrganizationDTO> organizationList) {
        this.organizationList = organizationList;
    }      
}
