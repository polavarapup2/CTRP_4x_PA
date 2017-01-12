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
 * execute, copy, modify, translate, market, protectedly display, protectedly perform,
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
package gov.nih.nci.pa.service; // NOPMD

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.convert.OrganizationalContactConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.PABaseCorrelation;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;


/**
 * Helper for ParticipatingSiteBeanLocal.
 * @author mshestopalov
 *
 */
public abstract class AbstractParticipatingSitesBean
    extends AbstractBaseParticipatingSiteBean {

    /**
     * createStudySiteAccrualStatus.
     * @param studySiteIi ii
     * @param newStatus status
     * @throws PAException when error
     */
    @SuppressWarnings({ "deprecation", "unused" })
    protected void createStudySiteAccrualStatus(Ii studySiteIi,
            StudySiteAccrualStatusDTO newStatus) throws PAException {
        StudySiteAccrualStatusDTO currentStatus = getStudySiteAccrualStatusService()
                .getCurrentStudySiteAccrualStatusByStudySite(studySiteIi);
        if (currentStatus == null || currentStatus.getStatusCode() == null
                || currentStatus.getStatusDate() == null
                || !currentStatus
                        .getStatusCode()
                        .getCode()
                        .equals(CdConverter.convertCdToString(newStatus
                                .getStatusCode()))
                || !currentStatus.getStatusDate().equals(
                        newStatus.getStatusDate())) {
                 List<StatusDto> statusList = (ArrayList<StatusDto>) 
                             getStudySiteAccrualStatusService().getStatusHistory(studySiteIi);
                 if (!statusList.isEmpty()) {
                     for (StatusDto sitedto : statusList) {
                       StudySiteAccrualStatusDTO studySiteAccrualConverterDto = 
                            convertStatusDtoToAccrualStatusDTO(sitedto);
                       if (studySiteAccrualConverterDto.getStatusCode().equals(newStatus.getStatusCode())
                           && DateUtils.isSameDay(TsConverter.convertToTimestamp(studySiteAccrualConverterDto
                               .getStatusDate()), TsConverter.convertToTimestamp(newStatus.getStatusDate()))) {
                           String comments = StConverter.convertToString(studySiteAccrualConverterDto.getComments());
                           try {
                              studySiteAccrualConverterDto.setComments(StConverter.convertToSt(comments 
                                + "Deleted because another "
                                + " record with the same site status and status date was received on" + PAUtil
                                .getCurrentTime().toString()));
                            } catch (ParseException e) {
                                 e.printStackTrace();
                           }
                           getStudySiteAccrualStatusService().softDelete(studySiteAccrualConverterDto);
                       }
                     }
                 }
                 newStatus.setIdentifier(IiConverter.convertToIi((Long) null));
                 newStatus.setStudySiteIi(studySiteIi);
                 getStudySiteAccrualStatusService().createStudySiteAccrualStatus(newStatus);
        }
    }
    
    private StudySiteAccrualStatusDTO convertStatusDtoToAccrualStatusDTO(StatusDto sitedto) 
                throws PAException {
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode
                 .valueOf(sitedto.getStatusCode())));
        dto.setIdentifier(IiConverter.convertToIi(sitedto.getId()));
        dto.setStatusDate(TsConverter.convertToTs(sitedto.getStatusDate()));
        dto.setComments(StConverter.convertToSt(sitedto.getComments()));
        return dto;
    }
    /**Site
     * createGenericContactRecord.
     * @param participationContactDTO dto
     * @param srMap map
     * @throws PAException when error
     */
    protected void createGenericContactRecord(StudySiteContactDTO participationContactDTO,
            Map<String, Ii> srMap) throws PAException {
        if (srMap != null
                && srMap.containsKey(IiConverter.ORGANIZATIONAL_CONTACT_ROOT)
                && srMap.containsKey(IiConverter.ORG_ROOT)) {
            Ii poGenericContactIi = srMap.get(IiConverter.ORGANIZATIONAL_CONTACT_ROOT);
            Ii poOrgIi = srMap.get(IiConverter.ORG_ROOT);
            //means title is selected for contact
            // now create study SITE contact as
            PABaseCorrelation<PAOrganizationalContactDTO , OrganizationalContactDTO , OrganizationalContact ,
                OrganizationalContactConverter> oc = new PABaseCorrelation<PAOrganizationalContactDTO ,
            OrganizationalContactDTO , OrganizationalContact , OrganizationalContactConverter>(
            PAOrganizationalContactDTO.class, OrganizationalContact.class, OrganizationalContactConverter.class);

            PAOrganizationalContactDTO orgContacPaDto = new PAOrganizationalContactDTO();
            orgContacPaDto.setOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(poOrgIi.getExtension()));
            orgContacPaDto.setIdentifier(poGenericContactIi);
            Long ocId = oc.create(orgContacPaDto);
            participationContactDTO.setOrganizationalContactIi(IiConverter.convertToIi(ocId));
        }
    }

    /**
     * createPersonContactRecord.
     * @param participationContactDTO dto
     * @param trialType string
     * @param srMap ii map
     * @throws PAException when error
     */
    protected void createPersonContactRecord(StudySiteContactDTO participationContactDTO, String trialType,
            Map<String, Ii> srMap) throws PAException {
        if (srMap != null && srMap.containsKey(IiConverter.CLINICAL_RESEARCH_STAFF_ROOT)) {
            Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean()
                .createClinicalResearchStaffCorrelationsWithExistingPoCrs(srMap
                    .get(IiConverter.CLINICAL_RESEARCH_STAFF_ROOT));
            participationContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid));
        }

        if (srMap != null && srMap.containsKey(IiConverter.HEALTH_CARE_PROVIDER_ROOT)
                && trialType.startsWith("Interventional")) {
            Ii hcpIi = srMap.get(IiConverter.HEALTH_CARE_PROVIDER_ROOT);
            Long healthCareProviderIi = new HealthCareProviderCorrelationBean()
                .createHealthCareProviderCorrelationsWithExistingPoHcp(hcpIi);
            participationContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));
        }
    }

    /**
     * recreatePrimaryContactRecord.
     * @param participationContactDTO dto
     * @param studySiteIi ii
     * @throws PAException when error
     */
    protected void recreatePrimaryContactRecord(StudySiteContactDTO participationContactDTO, Ii studySiteIi)
            throws PAException {
        // if a old record exists delete it and create a new one
        StudySiteContactDTO siteConDto = new StudySiteContactDTO();
        List<StudySiteContactDTO> siteContactDtos = getStudySiteContactService().getByStudySite(studySiteIi);
        for (StudySiteContactDTO cDto : siteContactDtos) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equalsIgnoreCase(cDto.getRoleCode().getCode())) {
                siteConDto = cDto;
            }
        }
        if (siteConDto.getIdentifier() != null) {
            getStudySiteContactService().delete(siteConDto.getIdentifier());
        }
        getStudySiteContactService().create(participationContactDTO);
    }
    
    /**
     * createStudyParticationContactRecord.
     * @param studySiteDTO dto.
     * @param srMap map
     * @param isPrimaryContact prim contact
     * @param roleCode role
     * @param telecom email and telephone.
     * @return bool
     * @throws PAException when error.
     */
    protected boolean createStudyParticationContactRecord(StudySiteDTO studySiteDTO, Map<String, Ii> srMap,
            boolean isPrimaryContact, String roleCode, DSet<Tel> telecom) throws PAException {
        StudyProtocolDTO studyProtocolDTO = PaRegistry.getStudyProtocolService()
            .getStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());

        StudySiteContactDTO newContactDTO = new StudySiteContactDTO();

        createPersonContactRecord(newContactDTO, StConverter.convertToString(studyProtocolDTO.getStudyProtocolType()),
                                  srMap);
        createGenericContactRecord(newContactDTO, srMap);

        if (!isPrimaryContact) {
            newContactDTO.setRoleCode(CdConverter.convertStringToCd(roleCode));
        } else {
            newContactDTO.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.PRIMARY_CONTACT));
        }
        newContactDTO.setStudyProtocolIdentifier(studySiteDTO.getStudyProtocolIdentifier());
        newContactDTO.setStatusCode(CdConverter.convertStringToCd(FunctionalRoleStatusCode.PENDING.getCode()));
        newContactDTO.setStudySiteIi(studySiteDTO.getIdentifier());
        newContactDTO.setTelecomAddresses(telecom);
        if (isPrimaryContact) {
            recreatePrimaryContactRecord(newContactDTO, studySiteDTO.getIdentifier());
        }
        if (!isPrimaryContact
                && srMap.containsKey(IiConverter.PERSON_ROOT)
                && !doesSPCRecordExistforPerson(Long.valueOf(srMap.get(IiConverter.PERSON_ROOT).getExtension()),
                                                studySiteDTO.getIdentifier())) {
            getStudySiteContactService().create(newContactDTO);
        }
        return true;
    }

    private boolean iterateSubInvresults(Long persid, List<PaPersonDTO> subInvresults) throws PAException {
        for (PaPersonDTO paPersonDTO : subInvresults) {
            if (paPersonDTO.getPaPersonId() != null && paPersonDTO.getPaPersonId().equals(persid)) {
                return true;
            }
        }
        return false;
    }

    private boolean iteratePrincipalInvresults(Long persid, List<PaPersonDTO> principalInvresults) throws PAException {
        for (PaPersonDTO paPersonDTO : principalInvresults) {
            if (paPersonDTO.getSelectedPersId() != null && paPersonDTO.getSelectedPersId().equals(persid)) {
                return true;
            }
        }
        return false;
    }

    private boolean doesSPCRecordExistforPerson(Long persid, Ii studfySiteIi) throws PAException {
        List<PaPersonDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudySiteId(Long.valueOf(studfySiteIi.getExtension()),
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        List<PaPersonDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudySiteId(
                Long.valueOf(studfySiteIi.getExtension()), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        return iteratePrincipalInvresults(persid, principalInvresults)
            && iterateSubInvresults(persid, subInvresults);
    }

}
