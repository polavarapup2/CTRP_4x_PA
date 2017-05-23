/**
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
 */
package gov.nih.nci.registry.util;

import static gov.nih.nci.registry.dto.TrialDTO.RESPONSIBLE_PARTY_TYPE_PI;
import static gov.nih.nci.registry.dto.TrialDTO.RESPONSIBLE_PARTY_TYPE_SI;
import static gov.nih.nci.registry.dto.TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Class TrialConvertUtils.
 * @author mshestopalov
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength",
        "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class TrialConvertUtils {

    /**
     * NULLIFIED_PERSON.
    **/
    protected static final String NULLIFIED_PERSON = "Nullified Person";
    /**
     * NULLIFIED_ORGANIZATION.
     * **/
    protected static final String NULLIFIED_ORGANIZATION = "Nullified Organization";
    /**
     * The Constant MAXF.
     **/
    protected static final int MAXF = 1024;

    private final PAServiceUtils paServiceUtil = new PAServiceUtils();

    /**
     * Convert to study protocol dto for amendment.
     * @param trialDTO dtotoConvert
     * @return isoDto
     * @throws PAException on error
     */
    public StudyProtocolDTO convertToStudyProtocolDTOForAmendment(BaseTrialDTO trialDTO) throws PAException {
        StudyProtocolDTO isoDto = PaRegistry.getStudyProtocolService()
                .getStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(Long
                                .parseLong(trialDTO.getIdentifier())));     
        addSecondaryIdentifiers(isoDto, (TrialDTO) trialDTO);
        convertToStudyProtocolDTO(trialDTO, isoDto);
        if (isoDto instanceof NonInterventionalStudyProtocolDTO) {
            convertNonInterventionalTrialFields(trialDTO, (NonInterventionalStudyProtocolDTO) isoDto);
        }
        if (isoDto.getSecondaryIdentifiers() != null && isoDto.getSecondaryIdentifiers().getItem() != null) {
            for (Ii ii : isoDto.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    ii.setExtension(trialDTO.getAssignedIdentifier());
                }
            }
        }
        isoDto.setAmendmentDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
                ((TrialDTO) trialDTO).getAmendmentDate())));
        isoDto.setAmendmentNumber(StConverter.convertToSt(((TrialDTO) trialDTO).getLocalAmendmentNumber()));
        return isoDto;
    }

    /**
     * Convert to study protocol dto.
     *
     * @param trialDTO the trial dto
     * @param isoDto the iso dto
     *    
     */
    public void convertToStudyProtocolDTO(BaseTrialDTO trialDTO,
            StudyProtocolDTO isoDto) {
        if (StringUtils.isNotEmpty(trialDTO.getOfficialTitle())) {
            isoDto.setOfficialTitle(StConverter.convertToSt(trialDTO.getOfficialTitle()));
        }
        isoDto.setNciGrant(BlConverter.convertToBl(trialDTO.getNciGrant()));
        isoDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDTO.getPhaseCode())));
        isoDto.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode.getByCode(
                 trialDTO.getPhaseAdditionalQualifier())));
        setPrimaryPurposeToDTO(trialDTO, isoDto);
        if (trialDTO.getPropritaryTrialIndicator() == null) {
            isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(null));
        } else if  (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getPropritaryTrialIndicator())) {
            isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));            
        } else {
            isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        isoDto.setConsortiaTrialCategoryCode(CdConverter
                .convertStringToCd(trialDTO.getConsortiaTrialCategoryCode()));
        if (trialDTO instanceof TrialDTO) {
            convertToStudyProtocolDTO((TrialDTO) trialDTO, isoDto);
        }
    }

    /**
     * @param trialDTO
     * @param isoDto
     */    
    private void setPrimaryPurposeToDTO(BaseTrialDTO trialDTO, StudyProtocolDTO isoDto) {
       isoDto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.getByCode(
           trialDTO.getPrimaryPurposeCode())));
        isoDto.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(
             PrimaryPurposeAdditionalQualifierCode.getByCode(trialDTO.getPrimaryPurposeAdditionalQualifierCode())));

        if (StringUtils.equals(trialDTO.getPrimaryPurposeCode(), PrimaryPurposeCode.OTHER.getCode())) {
            isoDto.setPrimaryPurposeOtherText(
                    StConverter.convertToSt(trialDTO.getPrimaryPurposeOtherText()));
        } else {
            isoDto.setPrimaryPurposeOtherText(StConverter.convertToSt(null));
        }
        if (CollectionUtils.isNotEmpty(trialDTO.getSecondaryPurposes())) {
            isoDto.setSecondaryPurposes(DSetConverter
                    .convertListStToDSet(trialDTO.getSecondaryPurposes()));
            isoDto.setSecondaryPurposeOtherText(
                    StConverter.convertToSt(trialDTO.getSecondaryPurposeOtherText()));
        } else {
            isoDto.setSecondaryPurposeOtherText(
                    StConverter.convertToSt(null));
        }
        
    }

    /**
     * @param trialDTO trialDTO
     * @param isoDto isoDto
     */
    protected void convertToStudyProtocolDTO(TrialDTO trialDTO, StudyProtocolDTO isoDto) {
        setPrimaryPurposeToDTO(trialDTO, isoDto);
        isoDto.setStudySource(CdConverter.convertToCd(trialDTO.getStudySource()));
        isoDto.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getStartDate())));
        isoDto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDTO
                .getStartDateType())));
        isoDto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getPrimaryCompletionDate())));
        isoDto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode
                .getByCode(trialDTO.getPrimaryCompletionDateType())));
        isoDto.setCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getCompletionDate())));
        isoDto.setCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDTO
            .getCompletionDateType())));
        isoDto.setStudyProtocolType(StConverter.convertToSt(trialDTO.getTrialType()));
        isoDto.setProgramCodeText(StConverter.convertToSt(trialDTO.getProgramCodeText()));
        if (trialDTO.getFdaRegulatoryInformationIndicator() == null) {
            isoDto.setFdaRegulatedIndicator(BlConverter.convertToBl(null));
        } else if  (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getFdaRegulatoryInformationIndicator())) {
            isoDto.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDTO.getSection801Indicator() == null) {
            isoDto.setSection801Indicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getSection801Indicator())) {
            isoDto.setSection801Indicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));
        }

        isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        if (trialDTO.getDelayedPostingIndicator() == null) {
            isoDto.setDelayedpostingIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getDelayedPostingIndicator())) {
            isoDto.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDTO.getDataMonitoringCommitteeAppointedIndicator() == null) {
            isoDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getDataMonitoringCommitteeAppointedIndicator())) {
            isoDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if  (trialDTO.isXmlRequired()) {
            isoDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        isoDto.setNciGrant(BlConverter.convertToBl(trialDTO.getNciGrant()));
        if (trialDTO.getSecondaryIdentifierList() != null && !trialDTO.getSecondaryIdentifierList().isEmpty()) {
           List<Ii> iis = new ArrayList<Ii>();
           for (Ii sps : trialDTO.getSecondaryIdentifierList()) {
             if (sps != null &&  StringUtils.isNotEmpty(sps.getExtension())) {
               if (IiConverter.STUDY_PROTOCOL_ROOT.equals(sps.getRoot())) {
                  iis.add(IiConverter.convertToAssignedIdentifierIi(sps.getExtension()));
               } else {
                  iis.add(IiConverter.convertToOtherIdentifierIi(sps.getExtension()));
               }
             }
           }
           isoDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(new HashSet<Ii>(iis)));
        }
        isoDto.setAccrualDiseaseCodeSystem(StConverter.convertToSt(trialDTO.getAccrualDiseaseCodeSystem()));
    }
    /**
     * Convert to study overall status dto.
     * @param trialDTO Dto
     * @return isoDto
     */
    public StudyOverallStatusDTO convertToStudyOverallStatusDTO(TrialDTO trialDTO) {
        StudyOverallStatusDTO isoDto = new StudyOverallStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDTO
                .getStatusCode())));
        isoDto.setReasonText(StConverter.convertToSt(trialDTO.getReason()));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getStatusDate())));
        return isoDto;
    }
    
    /**
     * @param trialDTO
     *            TrialDTO
     * @return List<StudyOverallStatusDTO>
     */   
    public List<StudyOverallStatusDTO> convertStatusHistory(TrialDTO trialDTO) {
        final Collection<StatusDto> statusHistory = trialDTO.getStatusHistory();        
        return convertStatusHistory(statusHistory);
    }

 
    /**
     * @param statusHistory
     *            Collection<StatusDto>
     * @return List<StudyOverallStatusDTO>
     */
    @SuppressWarnings("deprecation")
    public List<StudyOverallStatusDTO> convertStatusHistory(
            final Collection<StatusDto> statusHistory) {
        List<StudyOverallStatusDTO> list = new ArrayList<StudyOverallStatusDTO>();        
        for (StatusDto status : statusHistory) {
            StudyOverallStatusDTO isoDto = new StudyOverallStatusDTO();
            isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode
                    .valueOf(status.getStatusCode())));
            isoDto.setReasonText(StConverter.convertToSt(status.getReason()));
            isoDto.setStatusDate(TsConverter.convertToTs((status
                    .getStatusDate())));
            isoDto.setAdditionalComments(StConverter.convertToSt(status
                    .getComments()));
            isoDto.setIdentifier(IiConverter.convertToIi(status.getId()));
            isoDto.setDeleted(BlConverter.convertToBl(status.isDeleted()));
            list.add(isoDto);
        }
        return list;
    }

    /**
     * Convert to study overall status dto.
     * @param isoDto StudyOverallStatusDTO
     * @param trialDTO Dto
     */
    public void convertToStudyOverallStatusDTO(TrialDTO trialDTO, StudyOverallStatusDTO isoDto) {
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDTO
                .getStatusCode())));
        isoDto.setReasonText(StConverter.convertToSt(trialDTO.getReason()));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getStatusDate())));
      }

    /**
     * Convert to summary4 org dto.
     * @param trialDTO do
     * @return iso
     */
    public List<OrganizationDTO> convertToSummary4OrgDTO(BaseTrialDTO trialDTO) {
       List<OrganizationDTO> summary4OrganizationList = new ArrayList<OrganizationDTO>();
       for (SummaryFourSponsorsWebDTO summary4Org : trialDTO.getSummaryFourOrgIdentifiers()) { 
            if (StringUtils.isNotEmpty(summary4Org.getOrgId())) {
                OrganizationDTO isoDto = new OrganizationDTO();
                isoDto.setIdentifier(IiConverter.convertToPoOrganizationIi(summary4Org.getOrgId()));
                summary4OrganizationList.add(isoDto);
            }
       }
        return summary4OrganizationList;
    }
    /**
     * Convert to lead org dto.
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToLeadOrgDTO(BaseTrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToPoOrganizationIi(trialDTO.getLeadOrganizationIdentifier()));
        return isoDto;
    }

    /**
     * Convert to sponsor org dto.
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToSponsorOrgDTO(TrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToPoOrganizationIi(trialDTO.getSponsorIdentifier()));
        return isoDto;
    }

    /**
     * Convert to lead pi.
     * @param trialDTO dto
     * @return iso
     */
    public PersonDTO convertToLeadPI(TrialDTO trialDTO) {
        PersonDTO  isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToPoPersonIi(trialDTO.getPiIdentifier()));
        return isoDto;
    }
    
    /**
     * Converts responsible party.
     * @param trialDTO trialDTO
     * @return ResponsiblePartyDTO
     */
    public ResponsiblePartyDTO convertToResponsiblePartyDTO(TrialDTO trialDTO) {
        ResponsiblePartyDTO party = new ResponsiblePartyDTO();
        if (RESPONSIBLE_PARTY_TYPE_SPONSOR.equalsIgnoreCase(trialDTO.getResponsiblePartyType())) {
            party.setType(ResponsiblePartyType.SPONSOR);
        } else if (RESPONSIBLE_PARTY_TYPE_PI.equalsIgnoreCase(trialDTO.getResponsiblePartyType())) {
            party.setType(ResponsiblePartyType.PRINCIPAL_INVESTIGATOR);
            convertResponsiblePartyInvestigator(party, trialDTO);
        } else if (RESPONSIBLE_PARTY_TYPE_SI.equalsIgnoreCase(trialDTO.getResponsiblePartyType())) {
            party.setType(ResponsiblePartyType.SPONSOR_INVESTIGATOR);
            convertResponsiblePartyInvestigator(party, trialDTO);
        } 
        return party;
    } 

    private void convertResponsiblePartyInvestigator(ResponsiblePartyDTO party,
            TrialDTO trialDTO) {
        PersonDTO investigator = new PersonDTO();
        investigator.setIdentifier(IiConverter.convertToPoPersonIi(trialDTO
                .getResponsiblePersonIdentifier()));

        OrganizationDTO affiliation = new OrganizationDTO();
        affiliation.setIdentifier(IiConverter
                .convertToPoOrganizationIi(trialDTO
                        .getResponsiblePersonAffiliationOrgId()));

        party.setAffiliation(affiliation);
        party.setInvestigator(investigator);
        party.setTitle(trialDTO.getResponsiblePersonTitle());
    }

    /**
     * Convert to summary4 study resourcing dto.
     * @param trialDTO dto
     * @return iso
     * @throws PAException e
     */
    public StudyResourcingDTO convertToSummary4StudyResourcingDTO(BaseTrialDTO trialDTO)
        throws PAException {
        StudyResourcingDTO isoDto = null;
        if (StringUtils.isNotEmpty(trialDTO.getSummaryFourFundingCategoryCode())) {
            isoDto = new StudyResourcingDTO();
            isoDto.setTypeCode(CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode()));
        }
        return isoDto;
    }
    /**
     * Convert to study site dto.
     * @param trialDTO dto
     * @return iso
     */
    public StudySiteDTO convertToleadOrgSiteIdDTO(BaseTrialDTO trialDTO) {
        StudySiteDTO isoDto = new StudySiteDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getLeadOrgTrialIdentifier()));
        return isoDto;
    }
    /**
     * Convert to nct study site dto.
     *
     * @param trialDTO dto
     * @param studyProtocolIi Ii
     * @return iso
     * @throws PAException e
     */
    public StudySiteDTO convertToNCTStudySiteDTO(BaseTrialDTO trialDTO, Ii studyProtocolIi) throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        Ii nctROIi = null;
        String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                PAConstants.NCT_IDENTIFIER_TYPE);
        nctROIi = PaRegistry.getOrganizationCorrelationService().getPoResearchOrganizationByEntityIdentifier(
                IiConverter.convertToPoOrganizationIi(String.valueOf(poOrgId)));
        if (StringUtils.isNotEmpty(trialDTO.getNctIdentifier())) {
            if (!ISOUtil.isIiNull(studyProtocolIi)) {
                //find if the NCT number is there
                StudySiteDTO criteriaNCTStudySite = new StudySiteDTO();
                criteriaNCTStudySite.setStudyProtocolIdentifier(studyProtocolIi);
                criteriaNCTStudySite.setFunctionalCode(CdConverter.convertToCd(
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));

                criteriaNCTStudySite.setResearchOrganizationIi(nctROIi);
                StudySiteDTO ssNctIdDto = PAUtil.getFirstObj(paServiceUtil.getStudySite(criteriaNCTStudySite,
                        true));
                if (ssNctIdDto != null) {
                    isoDto = ssNctIdDto;
                }
            }
            isoDto.setResearchOrganizationIi(nctROIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getNctIdentifier()));
        }
        return isoDto;
    }
   
   

   /**
    * Convert to iso document list.
    *
    * @param docList dto
    *
    * @return isoDTOList
    *
    * @throws PAException ex
    */
    public List<DocumentDTO> convertToISODocumentList(List<TrialDocumentWebDTO> docList) throws PAException {
        List<DocumentDTO> studyDocDTOList = new ArrayList<DocumentDTO>();
        for (TrialDocumentWebDTO dto : docList) {
            DocumentDTO isoDTO = convertTrialDocumentDTOToDocumentDTO(dto);
            studyDocDTOList.add(isoDTO);
        }
        return studyDocDTOList;
    }

    private DocumentDTO convertTrialDocumentDTOToDocumentDTO(TrialDocumentWebDTO dto) {
        DocumentDTO isoDTO = new DocumentDTO();
        isoDTO.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
        isoDTO.setFileName(StConverter.convertToSt(dto.getFileName()));
        isoDTO.setText(EdConverter.convertToEd(dto.getText()));
        if (StringUtils.isNotEmpty(dto.getStudyProtocolId())) {
            isoDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long.valueOf(dto
                .getStudyProtocolId())));
        }
        if (StringUtils.isNotEmpty(dto.getId())) {
            isoDTO.setIdentifier(IiConverter.convertToDocumentIi(Long.valueOf((dto.getId()))));
        }
        return isoDTO;
    }

    /**
     * Convert to iso irb document .
     *
     * @param docList the doc list
     * @param studyProtocolIi the study protocol ii
     *
     * @return isoDTOList
     *
     * @throws PAException the PA exception
     */
    public List<DocumentDTO> convertToISODocument(List<TrialDocumentWebDTO> docList, Ii studyProtocolIi)
            throws PAException {
        List<DocumentDTO> studyDocDTOList = new ArrayList<DocumentDTO>();

        List<DocumentDTO> docs = PaRegistry.getDocumentService().getDocumentsByStudyProtocol(studyProtocolIi);

        if (CollectionUtils.isNotEmpty(docList)) {
            for (TrialDocumentWebDTO newDoc : docList) {                             
                DocumentDTO docToUpdate = findDocumentById(docs, newDoc.getId());
                if (docToUpdate == null) {
                    docToUpdate = findDocumentByType(docs, newDoc.getTypeCode());
                    if (docToUpdate != null) {
                        docToUpdate.setFileName(StConverter.convertToSt(newDoc
                                .getFileName()));
                        docToUpdate.setText(EdConverter.convertToEd(newDoc
                                .getText()));
                    } else {
                        docToUpdate = convertTrialDocumentDTOToDocumentDTO(newDoc);
                    }
                    studyDocDTOList.add(docToUpdate);
                }                
            }
        }
        return studyDocDTOList;
    }

    private DocumentDTO findDocumentByType(List<DocumentDTO> docs,
            String typeCode) {
        for (DocumentDTO doc : docs) {
            if (typeCode
                    .equals(CdConverter.convertCdToString(doc.getTypeCode()))
                    && !DocumentTypeCode.OTHER.getCode().equals(
                            CdConverter.convertCdToString(doc.getTypeCode()))) {
                return doc;
            }
        }
        return null;
    }

    private DocumentDTO findDocumentById(List<DocumentDTO> docs, String id) {
        for (DocumentDTO doc : docs) {
            if (StringUtils.isNotBlank(id)
                    && !ISOUtil.isIiNull(doc.getIdentifier())
                    && id.equals(doc.getIdentifier().getExtension())) {
                return doc;
            }

        }
        return null;
    }

   /**
    * Convert to document dto.
    *
    * @param docTypeCode doc
    * @param fileName file
    * @param file file
    *
    * @return isoDto
    *
    * @throws IOException io
    */
   public TrialDocumentWebDTO convertToDocumentDTO(String docTypeCode, String fileName, File file) throws IOException {
       TrialDocumentWebDTO docDTO = new TrialDocumentWebDTO();
       docDTO.setTypeCode(docTypeCode);
       docDTO.setFileName(fileName);
       docDTO.setText((readInputStream(new FileInputStream(file))));
       return docDTO;
   }



   /**
    * Read an input stream in its entirety into a byte array.
    *
    * @param inputStream the input stream
    *
    * @return the byte[]
    *
    * @throws IOException Signals that an I/O exception has occurred.
    */
   protected static byte[] readInputStream(InputStream inputStream) throws IOException {
       return IOUtils.toByteArray(inputStream);
   }

   /**
    * Convert isoindide list.
    *
    * @param indList ind
    * @param studyProtocolIi ii
    *
    * @return isoList
    */
   public List<StudyIndldeDTO>  convertISOINDIDEList(List<TrialIndIdeDTO> indList, Ii studyProtocolIi) {
       if (indList == null || indList.isEmpty()) {
           return null;
       }
       List<StudyIndldeDTO>  studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();
       //loop thru the non-iso dto
       StudyIndldeDTO isoDTO = null;
       for (TrialIndIdeDTO dto : indList) {
           isoDTO = new StudyIndldeDTO();
           if (!ISOUtil.isIiNull(studyProtocolIi)) {
               isoDTO.setStudyProtocolIdentifier(studyProtocolIi);
           }
           isoDTO.setIndldeTypeCode(CdConverter.convertStringToCd(dto.getIndIde()));
           isoDTO.setIndldeNumber(StConverter.convertToSt(dto.getNumber()));
           isoDTO.setGrantorCode(CdConverter.convertStringToCd(dto.getGrantor()));
           isoDTO.setHolderTypeCode(CdConverter.convertStringToCd(dto.getHolderType()));
           if (dto.getHolderType().equalsIgnoreCase("NIH")) {
               if (StringUtils.isNotEmpty(dto.getNihInstHolder())) {
                   isoDTO.setNihInstHolderCode(CdConverter.convertStringToCd(dto.getNihInstHolder()));
               } else {
                   isoDTO.setNihInstHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
               }
           }
           if (dto.getHolderType().equalsIgnoreCase("NCI")) {
                if (StringUtils.isNotEmpty(dto.getNciDivProgHolder())) {
                   isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(dto.getNciDivProgHolder()));
               } else {
                   isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
               }
           }
           if (dto.getExpandedAccess().equalsIgnoreCase(CommonsConstant.YES)) {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
           } else {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
           }
           if (!"-".equalsIgnoreCase(dto.getExpandedAccessType())) {
               isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(dto.getExpandedAccessType()));
           }
           if (StringUtils.isNotEmpty(dto.getStudyProtocolId())) {
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long.valueOf(
                       dto.getStudyProtocolId())));
           }
           if (StringUtils.isNotEmpty(dto.getIndIdeId())) {
               isoDTO.setIdentifier(IiConverter.convertToIi(dto.getIndIdeId()));
           }
           isoDTO.setExemptIndicator(BlConverter.convertYesNoStringToBl(dto.getExemptIndicator()));
           studyIndldeDTOList.add(isoDTO);
       }
       return studyIndldeDTOList;
   }

   /**
    * Convert iso grants list.
    *
    * @param grantList list
    *
    * @return isoList
    */
   public List<StudyResourcingDTO>  convertISOGrantsList(List<TrialFundingWebDTO> grantList) {
       if (grantList == null ||  grantList.isEmpty()) {
           return null;
       }
       List<StudyResourcingDTO>  grantsDTOList = new ArrayList<StudyResourcingDTO>();
       StudyResourcingDTO isoDTO = null;
       for (TrialFundingWebDTO dto : grantList) {
           isoDTO = new StudyResourcingDTO();
           isoDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
           isoDTO.setFundingMechanismCode(CdConverter.convertStringToCd(dto.getFundingMechanismCode()));
           isoDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                               NciDivisionProgramCode.getByCode(dto.getNciDivisionProgramCode())));
           isoDTO.setNihInstitutionCode(CdConverter.convertStringToCd(dto.getNihInstitutionCode()));
           isoDTO.setSerialNumber(StConverter.convertToSt(dto.getSerialNumber()));
           isoDTO.setFundingPercent(RealConverter.convertToReal(dto.getFundingPercent()));
           if (StringUtils.isNotEmpty(dto.getStudyProtocolId())) {
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long.valueOf(
                       dto.getStudyProtocolId())));
           }
           if (StringUtils.isNotEmpty(dto.getId())) {
               isoDTO.setIdentifier(IiConverter.convertToIi(dto.getId()));
           }
           grantsDTOList.add(isoDTO);
       }
       return grantsDTOList;
   }

   /**
    * Convert iso grants list.
    *
    * @param grantList list
    * @param studyProtocolIi Ii
    *
    * @return isoList
    */
   public List<StudyResourcingDTO>  convertISOGrantsList(List<TrialFundingWebDTO> grantList, Ii studyProtocolIi) {
       if (grantList != null && grantList.isEmpty()) {
           return null;
       }
       List<StudyResourcingDTO>  grantsDTOList = new ArrayList<StudyResourcingDTO>();
       StudyResourcingDTO isoDTO = null;
       for (TrialFundingWebDTO dto : grantList) {
           isoDTO = new StudyResourcingDTO();
           isoDTO.setStudyProtocolIdentifier(studyProtocolIi);
           isoDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
           isoDTO.setFundingMechanismCode(CdConverter.convertStringToCd(dto.getFundingMechanismCode()));
           isoDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                               NciDivisionProgramCode.getByCode(dto.getNciDivisionProgramCode())));
           isoDTO.setNihInstitutionCode(CdConverter.convertStringToCd(dto.getNihInstitutionCode()));
           isoDTO.setSerialNumber(StConverter.convertToSt(dto.getSerialNumber()));
           isoDTO.setFundingPercent(RealConverter.convertToReal(dto.getFundingPercent()));
           grantsDTOList.add(isoDTO);
       }
       return grantsDTOList;
   }

    /**
     * Adds the secondary identifiers.
     * @param spDTO the sp dto
     * @param trialDTO the trial dto
     */
    public void addSecondaryIdentifiers(StudyProtocolDTO spDTO, TrialDTO trialDTO) {
        List<Ii> secondaryIis = calculateSecondaryIis(spDTO, trialDTO);

        spDTO.getSecondaryIdentifiers().setItem(null);
        trialDTO.getSecondaryIdentifierList().clear();
        trialDTO.setSecondaryIdentifierList(secondaryIis);
    }

    private List<Ii> calculateSecondaryIis(StudyProtocolDTO spDTO, TrialDTO trialDTO) {
        List<Ii> secondaryIis = new ArrayList<Ii>();

        addSecondaryIdentifiersFromTrialIdentifierList(trialDTO, secondaryIis);

        addSecondaryIdentifiersFromTrialIdentifierAddList(trialDTO, secondaryIis);

        addSecondaryIdentifiersFromStudyProtocol(spDTO, secondaryIis);

        return secondaryIis;
    }

    private void addSecondaryIdentifiersFromTrialIdentifierAddList(TrialDTO trialDTO, List<Ii> secondaryIis) {
        if (trialDTO.getSecondaryIdentifierAddList() != null && !trialDTO.getSecondaryIdentifierAddList().isEmpty()) {
            secondaryIis.addAll(trialDTO.getSecondaryIdentifierAddList());
        }
    }

    private void addSecondaryIdentifiersFromTrialIdentifierList(TrialDTO trialDTO, List<Ii> secondaryIis) {
        if (trialDTO.getSecondaryIdentifierList() != null && !trialDTO.getSecondaryIdentifierList().isEmpty()) {
            secondaryIis.addAll(trialDTO.getSecondaryIdentifierList());
        }
    }

    private void addSecondaryIdentifiersFromStudyProtocol(StudyProtocolDTO spDTO, List<Ii> secondaryIis) {
        if (spDTO.getSecondaryIdentifiers() != null && spDTO.getSecondaryIdentifiers().getItem() != null) {
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    secondaryIis.add(ii);
                    break;
                }
            }
        }
    }

   /**
    * Convert to Ctep study site dto.
    * @param trialDTO dto
    * @param studyProtocolIi Ii
    * @return iso
    * @throws PAException e
    */
   public StudySiteDTO convertToCTEPStudySiteDTO(TrialDTO trialDTO, Ii studyProtocolIi) throws PAException {
       StudySiteDTO isoDto = new StudySiteDTO();
       Ii ctepROIi = null;
       if (StringUtils.isNotEmpty(trialDTO.getCtepIdentifier())) {
           String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                   PAConstants.CTEP_IDENTIFIER_TYPE);
           ctepROIi = PaRegistry.getOrganizationCorrelationService().getPoResearchOrganizationByEntityIdentifier(
                   IiConverter.convertToPoOrganizationIi(String.valueOf(poOrgId)));
           if (!ISOUtil.isIiNull(studyProtocolIi)) {
               //find if the CTEP Identifier is there
               StudySiteDTO criteriaCTEPStudySite = new StudySiteDTO();
               criteriaCTEPStudySite.setStudyProtocolIdentifier(studyProtocolIi);
               criteriaCTEPStudySite.setFunctionalCode(CdConverter.convertToCd(
                       StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
               criteriaCTEPStudySite.setResearchOrganizationIi(ctepROIi);
               StudySiteDTO ssCTEPIdDto = PAUtil.getFirstObj(paServiceUtil.getStudySite(criteriaCTEPStudySite,
                       true));
               if (ssCTEPIdDto != null) {
                   isoDto = ssCTEPIdDto;
               }
           }
           isoDto.setResearchOrganizationIi(ctepROIi);
           isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getCtepIdentifier()));

       }
       return isoDto;
   }
   /**
    * Convert to Dcp study site dto.
    * @param trialDTO dto
    * @param studyProtocolIi Ii
    * @return iso
    * @throws PAException e
    */
   public StudySiteDTO convertToDCPStudySiteDTO(TrialDTO trialDTO, Ii studyProtocolIi) throws PAException {
       StudySiteDTO isoDto = new StudySiteDTO();
       if (StringUtils.isNotEmpty(trialDTO.getDcpIdentifier())) {
           Ii dcpRoIi = null;
           String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                   PAConstants.DCP_IDENTIFIER_TYPE);
           dcpRoIi =  PaRegistry.getOrganizationCorrelationService()
           .getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(
                   String.valueOf(poOrgId)));
           if (!ISOUtil.isIiNull(studyProtocolIi)) {
               //find if the DCP Identifier is there
               StudySiteDTO criteriaDCPStudySite = new StudySiteDTO();
               criteriaDCPStudySite.setStudyProtocolIdentifier(studyProtocolIi);
               criteriaDCPStudySite.setFunctionalCode(CdConverter.convertToCd(
                       StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
               criteriaDCPStudySite.setResearchOrganizationIi(dcpRoIi);
               StudySiteDTO ssDcpIdDto = PAUtil.getFirstObj(paServiceUtil.getStudySite(criteriaDCPStudySite,
                       true));
               if (ssDcpIdDto != null) {
                   isoDto = ssDcpIdDto;
               }
           }
           isoDto.setResearchOrganizationIi(dcpRoIi);
           isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getDcpIdentifier()));
       }
       return isoDto;
   }

   /**
    * @param trialDto dot
    * @return isoDto
    */
   @SuppressWarnings("deprecation")
public StudyProtocolStageDTO convertToStudyProtocolStageDTO(BaseTrialDTO trialDto) {
       StudyProtocolStageDTO spStageDTO = new StudyProtocolStageDTO();
       spStageDTO.setIdentifier(IiConverter.convertToIi(trialDto.getStudyProtocolId()));
       spStageDTO.setNctIdentifier(StConverter.convertToSt(trialDto.getNctIdentifier()));
       spStageDTO.setOfficialTitle(StConverter.convertToSt(trialDto.getOfficialTitle()));
       spStageDTO.setNciGrant(BlConverter.convertToBl(trialDto.getNciGrant()));
       spStageDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDto.getPhaseCode())));
       spStageDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode.getByCode(
              trialDto.getPhaseAdditionalQualifier())));
       spStageDTO.setPrimaryPurposeCode(CdConverter.convertToCd(
               PrimaryPurposeCode.getByCode(trialDto.getPrimaryPurposeCode())));
       spStageDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(
             PrimaryPurposeAdditionalQualifierCode.getByCode(trialDto.getPrimaryPurposeAdditionalQualifierCode())));
       spStageDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(trialDto.getPrimaryPurposeOtherText()));
        if (trialDto.getSecondaryPurposes() != null) {
            spStageDTO.setSecondaryPurposes(StConverter.convertToSt(StringUtils.join(
                    trialDto.getSecondaryPurposes(), ';')));
        }
        spStageDTO.setSecondaryPurposeOtherText(StConverter.convertToSt(trialDto.getSecondaryPurposeOtherText()));
       spStageDTO.setLocalProtocolIdentifier(StConverter.convertToSt(trialDto.getLeadOrgTrialIdentifier()));
       spStageDTO.setLeadOrganizationIdentifier(IiConverter.convertToIi(trialDto.getLeadOrganizationIdentifier()));
       for (SummaryFourSponsorsWebDTO sfOrg : trialDto.getSummaryFourOrgIdentifiers()) {
            spStageDTO.getSummaryFourOrgIdentifiers().add(IiConverter.convertToIi(sfOrg.getOrgId()));
       }
       spStageDTO.setSummaryFourFundingCategoryCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.getByCode(
               trialDto.getSummaryFourFundingCategoryCode())));
       spStageDTO.setTrialType(StConverter.convertToSt(trialDto.getTrialType()));
       
        spStageDTO.setStudyModelCode(CdConverter.convertStringToCd(trialDto
                .getStudyModelCode()));
        spStageDTO.setStudyModelOtherText(StConverter.convertToSt(trialDto
                .getStudyModelOtherText()));
        spStageDTO.setTimePerspectiveCode(CdConverter
                .convertStringToCd(trialDto.getTimePerspectiveCode()));
        spStageDTO.setTimePerspectiveOtherText(StConverter.convertToSt(trialDto
                .getTimePerspectiveOtherText()));
        spStageDTO.setStudySubtypeCode(CdConverter.convertStringToCd(trialDto
                .getStudySubtypeCode()));
       
       
       if (trialDto instanceof TrialDTO) {
           convertNonPropDtoToStage((TrialDTO) trialDto, spStageDTO);
       } else if (trialDto instanceof ProprietaryTrialDTO) {
           convertPropDtoToStage((ProprietaryTrialDTO) trialDto, spStageDTO);
       }
       spStageDTO.setUserLastCreated(StConverter.convertToSt(UsernameHolder.getUser()));
        spStageDTO.setConsortiaTrialCategoryCode(CdConverter
                .convertStringToCd(trialDto.getConsortiaTrialCategoryCode()));
       spStageDTO.setNciGrant(BlConverter.convertToBl(trialDto.getNciGrant()));
       spStageDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt(trialDto.getAccrualDiseaseCodeSystem()));
       return spStageDTO;
   }


   @SuppressWarnings("deprecation")
   private void convertPropDtoToStage(ProprietaryTrialDTO trialDto,
           StudyProtocolStageDTO spStageDTO) {
       spStageDTO.setSubmitterOrganizationIdentifier(IiConverter.convertToIi(
               trialDto.getSiteOrganizationIdentifier()));
       spStageDTO.setSiteProtocolIdentifier(IiConverter.convertToIi(trialDto.getLocalSiteIdentifier()));
       spStageDTO.setSitePiIdentifier(IiConverter.convertToIi(trialDto.getSitePiIdentifier()));
       spStageDTO.setSiteSummaryFourFundingTypeCode(CdConverter.convertStringToCd(
               trialDto.getSummaryFourFundingCategoryCode()));
       spStageDTO.setSiteProgramCodeText(StConverter.convertToSt(trialDto.getSiteProgramCodeText()));
       spStageDTO.setSiteRecruitmentStatus(CdConverter.convertStringToCd(trialDto.getSiteStatusCode()));
       spStageDTO.setSiteRecruitmentStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
               trialDto.getSiteStatusDate())));
       spStageDTO.setOpendedForAccrualDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
               trialDto.getDateOpenedforAccrual())));
       spStageDTO.setClosedForAccrualDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
               trialDto.getDateClosedforAccrual())));
       spStageDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
       trialDto.setPropritaryTrialIndicator(CommonsConstant.YES);
   }

    @SuppressWarnings({ "deprecation" })
    private void convertNonPropDtoToStage(TrialDTO trialDto, StudyProtocolStageDTO spStageDTO) {
        spStageDTO.setPiIdentifier(IiConverter.convertToIi(trialDto.getPiIdentifier()));
        spStageDTO.setSponsorIdentifier(IiConverter.convertToIi(trialDto.getSponsorIdentifier()));
        spStageDTO.setResponsiblePartyType(StConverter.convertToSt(trialDto.getResponsiblePartyType()));
        spStageDTO.setResponsibleIdentifier(IiConverter.convertToIi(trialDto.getResponsiblePersonIdentifier()));
        spStageDTO.setResponsibleTitle(StConverter.convertToSt(trialDto.getResponsiblePersonTitle()));
        spStageDTO.setResponsibleAffilId(IiConverter.convertToIi(trialDto.getResponsiblePersonAffiliationOrgId()));
        spStageDTO.setProgramCodeText(StConverter.convertToSt(trialDto.getProgramCodeText()));
        spStageDTO.setTrialStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDto.getStatusCode())));
        spStageDTO.setTrialStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto.getStatusDate())));
        spStageDTO.setStatusReason(StConverter.convertToSt(trialDto.getReason()));
        spStageDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto.getStartDate())));
        spStageDTO.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDto
            .getStartDateType())));
        spStageDTO.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto
            .getPrimaryCompletionDate())));
        spStageDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode
            .getByCode(trialDto.getPrimaryCompletionDateType())));
        spStageDTO
            .setCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto.getCompletionDate())));
        spStageDTO.setCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDto
            .getCompletionDateType())));
        if (trialDto.getFdaRegulatoryInformationIndicator() == null) {
            spStageDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDto.getFdaRegulatoryInformationIndicator())) {
            spStageDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDto.getSection801Indicator() == null) {
            spStageDTO.setSection801Indicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDto.getSection801Indicator())) {
            spStageDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDto.getDelayedPostingIndicator() == null) {
            spStageDTO.setDelayedpostingIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDto.getDelayedPostingIndicator())) {
            spStageDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDto.getDataMonitoringCommitteeAppointedIndicator() == null) {
            spStageDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDto.getDataMonitoringCommitteeAppointedIndicator())) {
            spStageDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        spStageDTO.setOversightAuthorityCountryId(IiConverter.convertToIi(trialDto.getLst()));
        spStageDTO.setOversightAuthorityOrgId(IiConverter.convertToIi(trialDto.getSelectedRegAuth()));
        spStageDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        trialDto.setPropritaryTrialIndicator(CommonsConstant.NO);
        spStageDTO.setCtgovXmlRequiredIndicator(trialDto.isXmlRequired() ? BlConverter.convertToBl(Boolean.TRUE)
                : BlConverter.convertToBl(Boolean.FALSE));
        spStageDTO.setNciGrant(BlConverter.convertToBl(trialDto.getNciGrant()));
        spStageDTO.getSecondaryIdentifierList().addAll(trialDto.getSecondaryIdentifierList());
        spStageDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt(trialDto.getAccrualDiseaseCodeSystem()));
        
        // status history
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder e = new XMLEncoder(bos);
        e.writeObject(new ArrayList<StatusDto>(trialDto.getStatusHistory()));
        e.flush();
        e.close();
        try {
            spStageDTO.setStatusHistory(bos.toString("UTF-8"));
        } catch (UnsupportedEncodingException e1) { //NOPMD
        }
    }
    
    
   /**
    * @param spStageDTO isoDto
    * @return webDto
    * @throws NullifiedRoleException on err
    * @throws PAException on err
    */
   public BaseTrialDTO convertToTrialDTO(StudyProtocolStageDTO spStageDTO)
       throws NullifiedRoleException, PAException {
       BaseTrialDTO trialDto = new BaseTrialDTO();
       if (isProprietary(spStageDTO)) {
           trialDto = convertToPropTrialDTO(spStageDTO);
       } else {
           trialDto = convertToNonPropTrialDTO(spStageDTO);
       }
       trialDto.setStudyProtocolId(IiConverter.convertToString(spStageDTO.getIdentifier()));
       trialDto.setNctIdentifier(StConverter.convertToString(spStageDTO.getNctIdentifier()));
       trialDto.setOfficialTitle(StConverter.convertToString(spStageDTO.getOfficialTitle()));
       trialDto.setNciGrant(BlConverter.convertToBoolean(spStageDTO.getNciGrant()));
       convertPhaseAndPurposeToTrialDTO(spStageDTO, trialDto);
       trialDto.setPrimaryPurposeOtherText(StConverter.convertToString(spStageDTO.getPrimaryPurposeOtherText()));
       if (!ISOUtil.isStNull(spStageDTO.getSecondaryPurposes())) {
            trialDto.setSecondaryPurposes(Arrays.asList(StConverter
                    .convertToString(spStageDTO.getSecondaryPurposes()).split(
                            ";")));
       }
        trialDto.setSecondaryPurposeOtherText(StConverter
                .convertToString(spStageDTO.getSecondaryPurposeOtherText()));    
       
       trialDto.setLeadOrgTrialIdentifier(StConverter.convertToString(spStageDTO.getLocalProtocolIdentifier()));
       trialDto.setLeadOrganizationIdentifier(IiConverter.convertToString(spStageDTO.getLeadOrganizationIdentifier()));
       if (!ISOUtil.isIiNull(spStageDTO.getLeadOrganizationIdentifier())) {
           trialDto.setLeadOrganizationName(getOrgName(spStageDTO.getLeadOrganizationIdentifier()));
           if (trialDto.getLeadOrganizationName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
               trialDto.setLeadOrganizationIdentifier(null);
           }
       }
       
        trialDto.setStudyModelCode(CdConverter.convertCdToString(spStageDTO
                .getStudyModelCode()));
        trialDto.setStudyModelOtherText(StConverter.convertToString(spStageDTO
                .getStudyModelOtherText()));
        trialDto.setTimePerspectiveCode(CdConverter
                .convertCdToString(spStageDTO.getTimePerspectiveCode()));
        trialDto.setTimePerspectiveOtherText(StConverter
                .convertToString(spStageDTO.getTimePerspectiveOtherText()));
        trialDto.setStudySubtypeCode(CdConverter.convertCdToString(spStageDTO
                .getStudySubtypeCode()));
       
       return trialDto;
   }

   private boolean isProprietary(StudyProtocolStageDTO spStageDTO) {
       return !ISOUtil.isBlNull(spStageDTO.getProprietaryTrialIndicator())
               && BlConverter.convertToBoolean(spStageDTO.getProprietaryTrialIndicator());
   }

    private void convertPhaseAndPurposeToTrialDTO(StudyProtocolStageDTO spStageDTO, BaseTrialDTO trialDto) {
        if (!ISOUtil.isCdNull(spStageDTO.getPhaseCode())) {
            trialDto.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(spStageDTO.getPhaseCode()))
                .getCode());
        }
        if (!ISOUtil.isCdNull(spStageDTO.getPhaseAdditionalQualifierCode())) {
            trialDto.setPhaseAdditionalQualifier(PhaseAdditionalQualifierCode
                .getByCode(CdConverter.convertCdToString(spStageDTO.getPhaseAdditionalQualifierCode())).getCode());
        }
        if (!ISOUtil.isCdNull(spStageDTO.getPrimaryPurposeCode())) {
            trialDto.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(spStageDTO
                                                                            .getPrimaryPurposeCode())).getCode());
        }
        if (!ISOUtil.isCdNull(spStageDTO.getPrimaryPurposeAdditionalQualifierCode())) {
            trialDto.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode
                .getByCode(CdConverter.convertCdToString(spStageDTO.getPrimaryPurposeAdditionalQualifierCode()))
                .getCode());
        }
    }

   private ProprietaryTrialDTO convertToPropTrialDTO(StudyProtocolStageDTO spStageDTO) {
       ProprietaryTrialDTO trialDto = new ProprietaryTrialDTO();
       trialDto.setSiteOrganizationIdentifier(IiConverter.convertToString(
               spStageDTO.getSubmitterOrganizationIdentifier()));
       if (!ISOUtil.isIiNull(spStageDTO.getSubmitterOrganizationIdentifier())) {
          trialDto.setSiteOrganizationName(getOrgName(spStageDTO.getSubmitterOrganizationIdentifier()));
          if (trialDto.getSiteOrganizationName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
              trialDto.setSiteOrganizationIdentifier(null);
          }
       }
       trialDto.setLocalSiteIdentifier(IiConverter.convertToString(spStageDTO.getSiteProtocolIdentifier()));
       trialDto.setSitePiIdentifier(IiConverter.convertToString(spStageDTO.getSitePiIdentifier()));
       if (!ISOUtil.isIiNull(spStageDTO.getSitePiIdentifier())) {
           trialDto.setSitePiName(getPersonName(spStageDTO.getSitePiIdentifier()));
           if (trialDto.getSitePiName().equalsIgnoreCase(NULLIFIED_PERSON)) {
               trialDto.setSitePiIdentifier(null);
           }
       }
       trialDto.setSiteProgramCodeText(StConverter.convertToString(spStageDTO.getSiteProgramCodeText()));
       checkSummaryFourOrgIds(spStageDTO, trialDto);
       trialDto.setSummaryFourFundingCategoryCode(CdConverter.convertCdToString(
                  spStageDTO.getSiteSummaryFourFundingTypeCode()));
       if (!ISOUtil.isCdNull(spStageDTO.getSiteRecruitmentStatus())) {
           trialDto.setSiteStatusCode(CdConverter.convertCdToString(spStageDTO.getSiteRecruitmentStatus()));
       }
       trialDto.setSiteStatusDate(TsConverter.convertToString(spStageDTO.getSiteRecruitmentStatusDate()));
       trialDto.setDateOpenedforAccrual(TsConverter.convertToString(spStageDTO.getOpendedForAccrualDate()));
       trialDto.setDateClosedforAccrual(TsConverter.convertToString(spStageDTO.getClosedForAccrualDate()));
       trialDto.setPropritaryTrialIndicator(CommonsConstant.YES);
       trialDto.setTrialType(StConverter.convertToString(spStageDTO.getTrialType()));
       trialDto.setConsortiaTrialCategoryCode(CdConverter.convertCdToString(spStageDTO
               .getConsortiaTrialCategoryCode()));
       trialDto.setNciGrant(BlConverter.convertToBoolean(spStageDTO.getNciGrant()));
       return trialDto;
   }

   /**
    * @param spStageDTO
    * @param trialDto
    * @throws PAException
    */
    @SuppressWarnings("unchecked")
    private TrialDTO convertToNonPropTrialDTO(StudyProtocolStageDTO spStageDTO) throws PAException {
        TrialDTO trialDto = new TrialDTO();
        trialDto.setPiIdentifier(IiConverter.convertToString(spStageDTO.getPiIdentifier()));
        trialDto.setSponsorIdentifier(IiConverter.convertToString(spStageDTO.getSponsorIdentifier()));
        trialDto.setResponsiblePartyType(StConverter.convertToString(spStageDTO.getResponsiblePartyType()));
        trialDto.setResponsiblePersonIdentifier(IiConverter.convertToString(spStageDTO.getResponsibleIdentifier()));
        trialDto.setResponsiblePersonTitle(StConverter.convertToString(spStageDTO.getResponsibleTitle()));
        trialDto.setResponsiblePersonAffiliationOrgId(IiConverter.convertToString(spStageDTO.getResponsibleAffilId()));
        trialDto.setProgramCodeText(StConverter.convertToString(spStageDTO.getProgramCodeText()));
        if (!ISOUtil.isCdNull(spStageDTO.getTrialStatusCode())) {
            trialDto.setStatusCode(StudyStatusCode.getByCode(CdConverter.convertCdToString(spStageDTO
                                                                 .getTrialStatusCode())).getCode());
        }
        trialDto.setStatusDate(TsConverter.convertToString(spStageDTO.getTrialStatusDate()));
        trialDto.setReason(StConverter.convertToString(spStageDTO.getStatusReason()));

        trialDto.setStartDate(TsConverter.convertToString(spStageDTO.getStartDate()));
        trialDto.setStartDateType(CdConverter.convertCdToString(spStageDTO.getStartDateTypeCode()));
        trialDto.setPrimaryCompletionDate(TsConverter.convertToString(spStageDTO.getPrimaryCompletionDate()));
        trialDto.setPrimaryCompletionDateType(CdConverter.convertCdToString(spStageDTO
            .getPrimaryCompletionDateTypeCode()));
        trialDto.setCompletionDate(TsConverter.convertToString(spStageDTO.getCompletionDate()));
        trialDto.setCompletionDateType(CdConverter.convertCdToString(spStageDTO.getCompletionDateTypeCode()));
        trialDto.setTrialType(StConverter.convertToString(spStageDTO.getTrialType()));

        trialDto.setLst(IiConverter.convertToString(spStageDTO.getOversightAuthorityCountryId()));
        trialDto.setSelectedRegAuth(IiConverter.convertToString(spStageDTO.getOversightAuthorityOrgId()));

        Boolean fdaRegIndicator = BlConverter.convertToBoolean(spStageDTO.getFdaRegulatedIndicator());
        if (fdaRegIndicator == null) {
            trialDto.setFdaRegulatoryInformationIndicator("");
        } else if (fdaRegIndicator) {
            trialDto.setFdaRegulatoryInformationIndicator(CommonsConstant.YES);
        } else {
            trialDto.setFdaRegulatoryInformationIndicator(CommonsConstant.NO);
        }
        Boolean sec801Indicator = BlConverter.convertToBoolean(spStageDTO.getSection801Indicator());
        if (sec801Indicator == null) {
            trialDto.setSection801Indicator("");
        } else if (sec801Indicator) {
            trialDto.setSection801Indicator(CommonsConstant.YES);
        } else {
            trialDto.setSection801Indicator(CommonsConstant.NO);
        }
        Boolean delayedPostIndicator = BlConverter.convertToBoolean(spStageDTO.getDelayedpostingIndicator());
        if (delayedPostIndicator == null) {
            trialDto.setDelayedPostingIndicator("");
        } else if (delayedPostIndicator) {
            trialDto.setDelayedPostingIndicator(CommonsConstant.YES);
        } else {
            trialDto.setDelayedPostingIndicator(CommonsConstant.NO);
        }
        Boolean dataMonitoringIndicator =
                BlConverter.convertToBoolean(spStageDTO.getDataMonitoringCommitteeAppointedIndicator());
        if (dataMonitoringIndicator == null) {
            trialDto.setDataMonitoringCommitteeAppointedIndicator("");
        } else if (dataMonitoringIndicator) {
            trialDto.setDataMonitoringCommitteeAppointedIndicator(CommonsConstant.YES);
        } else {
            trialDto.setDataMonitoringCommitteeAppointedIndicator(CommonsConstant.NO);
        }
        checkSummaryFourOrgIds(spStageDTO, trialDto);
        
        if (!ISOUtil.isIiNull(spStageDTO.getPiIdentifier())) {
            trialDto.setPiName(getPersonName(spStageDTO.getPiIdentifier()));
            if (trialDto.getPiName().equalsIgnoreCase(NULLIFIED_PERSON)) {
                trialDto.setPiIdentifier(null);
            }
        }
        if (!ISOUtil.isIiNull(spStageDTO.getSponsorIdentifier())) {
            trialDto.setSponsorName(getOrgName(spStageDTO.getSponsorIdentifier()));
            if (trialDto.getSponsorName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
                trialDto.setSponsorIdentifier(null);
            }
        }
        if (!ISOUtil.isIiNull(spStageDTO.getResponsibleAffilId())) {
            trialDto.setResponsiblePersonAffiliationOrgName(getOrgName(spStageDTO.getResponsibleAffilId()));
            if (trialDto.getResponsiblePersonAffiliationOrgName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
                trialDto.setResponsiblePersonAffiliationOrgId(null);
            }
        }
        
        if (!ISOUtil.isIiNull(spStageDTO.getResponsibleIdentifier())) {
            PersonDTO perDto =
                    paServiceUtil.getPoPersonEntity(IiConverter.convertToPoPersonIi(spStageDTO
                        .getResponsibleIdentifier().getExtension()));
            if (perDto != null) {
                trialDto.setResponsiblePersonName(PADomainUtils.convertToPaPersonDTO(perDto).getFullName());
            } 
        }
        
        if (StringUtils.isEmpty(trialDto.getResponsibleGenericContactName())
                && StringUtils.isEmpty(trialDto.getResponsiblePersonName())) {
            trialDto.setResponsiblePersonIdentifier(null);            
        }        
        
        trialDto.setSummaryFourFundingCategoryCode(CdConverter.convertCdToString(spStageDTO
            .getSummaryFourFundingCategoryCode()));
        trialDto.setPropritaryTrialIndicator(CommonsConstant.NO);
        if (ISOUtil.isBlNull(spStageDTO.getCtgovXmlRequiredIndicator())
                || spStageDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            trialDto.setXmlRequired(true);
        } else {
            trialDto.setXmlRequired(false);
        }
        trialDto.setNciGrant(BlConverter.convertToBoolean(spStageDTO.getNciGrant()));
        trialDto.setSecondaryIdentifierList(spStageDTO.getSecondaryIdentifierList());
        trialDto.setAccrualDiseaseCodeSystem(StConverter.convertToString(spStageDTO.getAccrualDiseaseCodeSystem()));
        
        if (StringUtils.isNotBlank(spStageDTO.getStatusHistory())) {
            try {
                // status history
                XMLDecoder d = new XMLDecoder(new ByteArrayInputStream(
                        spStageDTO.getStatusHistory().getBytes("UTF-8")));
                Collection<StatusDto> statusHistory = (Collection<StatusDto>) d
                        .readObject();
                trialDto.setStatusHistory(statusHistory);
                d.close();
            } catch (UnsupportedEncodingException e) { // NOPMD
            }
        }
        return trialDto;
    }

    private void checkSummaryFourOrgIds(StudyProtocolStageDTO spStageDTO, BaseTrialDTO trialDto) {
        if (CollectionUtils.isNotEmpty(spStageDTO.getSummaryFourOrgIdentifiers())) {
            for (Ii sfOrgId : spStageDTO.getSummaryFourOrgIdentifiers()) {
                String orgName = getOrgName(sfOrgId);
                String orgId = sfOrgId.getExtension();
                if (orgName.equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
                    orgId = null;
                }
                SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
                summarySp.setRowId(UUID.randomUUID().toString());
                summarySp.setOrgId(orgId);
                summarySp.setOrgName(orgName);
                if (!trialDto.getSummaryFourOrgIdentifiers().contains(summarySp)) {
                    trialDto.getSummaryFourOrgIdentifiers().add(summarySp);
                }
            }
        }
    }
   /**
    * @param grant webDto
    * @return isoDto
    */
   public StudyFundingStageDTO convertToStudyFundingStage(TrialFundingWebDTO grant) {
       StudyFundingStageDTO tempFundingDTO = new StudyFundingStageDTO();
       tempFundingDTO.setFundingMechanismCode(CdConverter.convertStringToCd(grant.getFundingMechanismCode()));
       tempFundingDTO.setNciDivisionProgramCode(CdConverter.convertStringToCd(grant.getNciDivisionProgramCode()));
       tempFundingDTO.setNihInstitutionCode(CdConverter.convertStringToCd(grant.getNihInstitutionCode()));
       tempFundingDTO.setSerialNumber(StConverter.convertToSt(grant.getSerialNumber()));
       tempFundingDTO.setFundingPercent(RealConverter.convertToReal(grant.getFundingPercent()));
       return tempFundingDTO;
   }
   /**
    * @param isoDTO isoDto
    * @return webDto
    */
   public TrialFundingWebDTO  convertToTrialFundingWebDTO(StudyFundingStageDTO isoDTO) {
       TrialFundingWebDTO  retDTO = new TrialFundingWebDTO();
       retDTO.setFundingMechanismCode(CdConverter.convertCdToString(isoDTO.getFundingMechanismCode()));
       retDTO.setNciDivisionProgramCode(CdConverter.convertCdToString(isoDTO.getNciDivisionProgramCode()));
       retDTO.setNihInstitutionCode(CdConverter.convertCdToString(isoDTO.getNihInstitutionCode()));
       retDTO.setSerialNumber(StConverter.convertToString(isoDTO.getSerialNumber()));
       retDTO.setFundingPercent(RealConverter.convertToString(isoDTO.getFundingPercent()));
       retDTO.setRowId(UUID.randomUUID().toString());
       return retDTO;
   }
   /**
    * @param indDto indIdedto
    * @return tempStudyIndIdeDto
    */
   public StudyIndIdeStageDTO convertToStudyIndIdeStage(TrialIndIdeDTO indDto) {

       StudyIndIdeStageDTO isoDTO = new StudyIndIdeStageDTO();
       isoDTO.setIndldeTypeCode(CdConverter.convertStringToCd(indDto.getIndIde()));
       isoDTO.setIndldeNumber(StConverter.convertToSt(indDto.getNumber()));
       isoDTO.setGrantorCode(CdConverter.convertStringToCd(indDto.getGrantor()));
       isoDTO.setHolderTypeCode(CdConverter.convertStringToCd(indDto.getHolderType()));
       if (indDto.getHolderType().equalsIgnoreCase("NIH")) {
           isoDTO.setNihInstHolderCode(CdConverter.convertStringToCd(indDto.getProgramCode()));
       }
       if (indDto.getHolderType().equalsIgnoreCase("NCI")) {
           isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(indDto.getProgramCode()));
       }
       if (indDto.getExpandedAccess().equalsIgnoreCase(CommonsConstant.YES)) {
           isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
       } else {
           isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
       }
       if (!"-".equalsIgnoreCase(indDto.getExpandedAccessType())) {
           isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(indDto.getExpandedAccessType()));
       }
       isoDTO.setExemptIndicator(BlConverter.convertYesNoStringToBl(indDto.getExemptIndicator()));
       return isoDTO;
   }

   /**
    *
    * @param isoDto isoDto
    * @return webDto
    */
   public TrialIndIdeDTO convertToTrialIndIdeDTO(StudyIndIdeStageDTO isoDto) {
       TrialIndIdeDTO webDto = new TrialIndIdeDTO();
       webDto.setExpandedAccessType(CdConverter.convertCdToString(isoDto.getExpandedAccessStatusCode()));
       webDto.setExpandedAccess(BlConverter.convertBlToYesNoString(isoDto.getExpandedAccessIndicator()));
       webDto.setExemptIndicator(BlConverter.convertBlToYesNoString(isoDto.getExemptIndicator()));
       webDto.setGrantor(CdConverter.convertCdToString(isoDto.getGrantorCode()));
       webDto.setHolderType(CdConverter.convertCdToString(isoDto.getHolderTypeCode()));
       if (!ISOUtil.isCdNull(isoDto.getNihInstHolderCode())) {
           webDto.setProgramCode(CdConverter.convertCdToString(isoDto.getNihInstHolderCode()));
       }
       if (!ISOUtil.isCdNull(isoDto.getNciDivProgHolderCode())) {
           webDto.setProgramCode(CdConverter.convertCdToString(isoDto.getNciDivProgHolderCode()));
       }
       webDto.setNumber(StConverter.convertToString(isoDto.getIndldeNumber()));
       webDto.setIndIde(CdConverter.convertCdToString(isoDto.getIndldeTypeCode()));
       webDto.setRowId(UUID.randomUUID().toString());
       return webDto;
   }

   /**
    * Convert to interventional study protocol dto.
    * @param trialDTO dtotoConvert
    * @return isoDto
    * @throws PAException on error
    */
    public InterventionalStudyProtocolDTO convertToInterventionalStudyProtocolDTO(BaseTrialDTO trialDTO)
            throws PAException {
        InterventionalStudyProtocolDTO isoDto = new InterventionalStudyProtocolDTO();
        convertToStudyProtocolDTO(trialDTO, isoDto);
        return isoDto;
    }
    
    /**
     * Convert to non-interventional study protocol dto.
     * @param trialDTO dtotoConvert
     * @return isoDto
     * @throws PAException on error
     */
     public NonInterventionalStudyProtocolDTO convertToNonInterventionalStudyProtocolDTO(BaseTrialDTO trialDTO)
             throws PAException {
         NonInterventionalStudyProtocolDTO isoDto = new NonInterventionalStudyProtocolDTO();
         convertToNonInterventionalStudyProtocolDTO(trialDTO, isoDto);         
         return isoDto;
     }

    /**
     * @param trialDTO
     *            trialDTO
     * @param isoDto
     *            isoDto
     * 
     */
    public void convertToNonInterventionalStudyProtocolDTO(
            BaseTrialDTO trialDTO, NonInterventionalStudyProtocolDTO isoDto) {
        convertToStudyProtocolDTO(trialDTO, isoDto);
        convertNonInterventionalTrialFields(trialDTO, isoDto);

    }

    /**
     * @param trialDTO
     * @param isoDto
     */
    private void convertNonInterventionalTrialFields(BaseTrialDTO trialDTO,
            NonInterventionalStudyProtocolDTO isoDto) {
        isoDto.setStudyModelCode(CdConverter.convertStringToCd(trialDTO.getStudyModelCode()));
         isoDto.setStudyModelOtherText(StConverter.convertToSt(trialDTO.getStudyModelOtherText()));
         isoDto.setTimePerspectiveCode(CdConverter.convertStringToCd(trialDTO.getTimePerspectiveCode()));
         isoDto.setTimePerspectiveOtherText(StConverter.convertToSt(trialDTO.getTimePerspectiveOtherText()));         
         isoDto.setStudySubtypeCode(CdConverter.convertStringToCd(trialDTO.getStudySubtypeCode()));
    }
    
    /**
     * @param trialDTO
     *            BaseTrialDTO
     * @return StudyProtocolDTO
     * @throws PAException
     *             PAException
     */
    public StudyProtocolDTO convertToStudyProtocolDTO(BaseTrialDTO trialDTO)
            throws PAException {
        if (PAConstants.NON_INTERVENTIONAL.equals(trialDTO.getTrialType())) {
            return convertToNonInterventionalStudyProtocolDTO(trialDTO);
        } else {
            return convertToInterventionalStudyProtocolDTO(trialDTO);
        }
    }    

    /**
     * Convert to iso document list.
     * @param docList the doc list
     * @param studyProtocolIi the study protocol ii
     * @return the list< document dto>
     * @throws PAException the PA exception
     */
    public List<DocumentDTO> convertToISODocumentList(List<TrialDocumentWebDTO> docList, Ii studyProtocolIi)
            throws PAException {
       List<DocumentDTO> docs = PaRegistry.getDocumentService().getDocumentsByStudyProtocol(studyProtocolIi);
       DocumentDTO protocolDocToUpdate = null;
       DocumentDTO otherDocToUpdate = null;
       List<DocumentDTO> docToUpdateList = new ArrayList<DocumentDTO>();
       if (docList != null && !docList.isEmpty()) {
           for (DocumentDTO doc : docs) {
               if (DocumentTypeCode.PROTOCOL_DOCUMENT.getCode().equals(
                       CdConverter.convertCdToString(doc.getTypeCode()))) {
                   protocolDocToUpdate = doc;
               } else if (DocumentTypeCode.OTHER.getCode().equals(
                       CdConverter.convertCdToString(doc.getTypeCode()))) {
                   otherDocToUpdate = doc;
               }
           }
           for (TrialDocumentWebDTO dto : docList) {
               if (DocumentTypeCode.PROTOCOL_DOCUMENT.getCode().equals(dto.getTypeCode())) {
                   if (protocolDocToUpdate != null) {
                       protocolDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                       protocolDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                   } else {
                       protocolDocToUpdate = new DocumentDTO();
                       protocolDocToUpdate.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
                       protocolDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                       protocolDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                   }
               } else if (DocumentTypeCode.OTHER.getCode().equals(dto.getTypeCode())) {
                   if (otherDocToUpdate != null) {
                       otherDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                       otherDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                   } else {
                       otherDocToUpdate = new DocumentDTO();
                       otherDocToUpdate.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
                       otherDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                       otherDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                   }
               }
           }
           if (protocolDocToUpdate != null && protocolDocToUpdate.getText() != null) {
               docToUpdateList.add(protocolDocToUpdate);
           }
           if (otherDocToUpdate != null && otherDocToUpdate.getText() != null) {
               docToUpdateList.add(otherDocToUpdate);
           }
       }
       return docToUpdateList;
   }

   /**
    * @param entityIi ii
    * @return name
    */
   public String getPersonName(Ii entityIi) {
       PersonDTO perDto = paServiceUtil.getPoPersonEntity(
               entityIi);
       if (perDto == null) {
           return NULLIFIED_PERSON;
       }
       return PADomainUtils.convertToPaPersonDTO(perDto).getFullName();
   }

   /**
    * @param entityIi ii
    * @return name
    */
   public String getOrgName(Ii entityIi) {
       OrganizationDTO orgDto = paServiceUtil.getPOOrganizationEntity(
               entityIi);
       if (orgDto == null) {
           return NULLIFIED_ORGANIZATION;
       }
       return EnOnConverter.convertEnOnToString(
               orgDto.getName());
   }

    /**
     * @return the paServiceUtil
     */
    public PAServiceUtils getPaServiceUtil() {
        return paServiceUtil;
    }
}
