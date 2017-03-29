package gov.nih.nci.pa.webservices.converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.iso.util.IvlConverter.JavaPq;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.webservices.dto.AgeDTO;
import gov.nih.nci.pa.webservices.dto.StudyProtocolWebServiceDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

/**
 * 
 * @author Reshma
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
public class TrialRegisterationWebServiceDTOConverter {
    /**
     * 
     * @param webServiceDTO
     *            the webServiceDTO
     * @param dto dto
     * @return StudyProtocolDTO
     */
    public StudyProtocolDTO convertToStudyProtocolDTO(
            StudyProtocolWebServiceDTO webServiceDTO, StudyProtocolDTO dto) {
        if (webServiceDTO != null) {
            if (webServiceDTO.getStudyProtocolId() != null) {
                dto.setIdentifier(IiConverter.convertToIi(webServiceDTO
                        .getStudyProtocolId()));
            }
            dto.setAcronym(StConverter.convertToSt(webServiceDTO.getAcronym()));
            dto.setExpandedAccessIndicator(BlConverter
                    .convertToBl(webServiceDTO.isExpandedAccessIndicator()));
            dto.setOfficialTitle(StConverter.convertToSt(webServiceDTO
                    .getOfficialTitle()));
            dto.setPublicDescription(StConverter.convertToSt(webServiceDTO
                    .getPublicDescription()));
            dto.setPublicTitle(StConverter.convertToSt(webServiceDTO
                    .getPublicTitle()));
            dto.setRecordVerificationDate(TsConverter.convertToTs(PAUtil
                    .dateStringToTimestamp(webServiceDTO
                            .getRecordVerificationDate())));
            dto.setScientificDescription(StConverter.convertToSt(webServiceDTO
                    .getScientificDescription()));
            dto.setKeywordText(StConverter.convertToSt(webServiceDTO
                    .getKeywordText()));
            dto.setAcceptHealthyVolunteersIndicator(BlConverter
                    .convertToBl(webServiceDTO
                            .isAcceptHealthyVolunteersIndicator()));
            dto.setTargetAccrualNumber(IvlConverter.convertInt().convertToIvl(
                    webServiceDTO.getTargetAccrualNumber(), null));
            dto.setStartDateTypeCode(CdConverter
                    .convertStringToCd(webServiceDTO.getStartDateTypeCode()));
            dto.setPrimaryCompletionDateTypeCode(CdConverter
                    .convertStringToCd(webServiceDTO
                            .getPrimaryCompletionDateTypeCode()));
            dto.setCompletionDateTypeCode(CdConverter
                    .convertStringToCd(webServiceDTO
                            .getCompletionDateTypeCode()));
            dto.setStartDate(TsConverter.convertToTs(PAUtil
                    .dateStringToTimestamp(webServiceDTO.getStartDate())));
            dto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil
                    .dateStringToTimestamp(webServiceDTO
                            .getPrimaryCompletionDate())));
            dto.setCompletionDate(TsConverter.convertToTs(PAUtil
                    .dateStringToTimestamp(webServiceDTO.getCompletionDate())));
            dto.setPhaseCode(CdConverter.convertStringToCd(webServiceDTO
                    .getPhaseCode()));
            dto.setDataMonitoringCommitteeAppointedIndicator(BlConverter
                    .convertToBl(webServiceDTO
                            .isDataMonitoringCommitteeAppointedIndicator()));
            dto.setPrimaryPurposeCode(CdConverter
                    .convertStringToCd(webServiceDTO.getPrimaryPurposeCode()));
            dto.setPrimaryPurposeOtherText(StConverter
                    .convertToSt(webServiceDTO.getPrimaryPurposeOtherText()));
            dto.setPrimaryPurposeAdditionalQualifierCode(CdConverter
                    .convertStringToCd(webServiceDTO
                            .getPrimaryPurposeAdditionalQualifierCode()));
            dto.setUserLastCreated(StConverter.convertToSt(webServiceDTO
                    .getUserLastCreated()));
            dto.setStudySource(CdConverter.convertStringToCd(webServiceDTO
                    .getStudySource()));
            Set<Ii> secondaryIds = new LinkedHashSet<Ii>();
            for (String id : webServiceDTO.getSecondaryIdentifiers()) {
                secondaryIds.add(IiConverter.convertToIi(id));
            }
            if (!secondaryIds.isEmpty()) {
                dto.setSecondaryIdentifiers(DSetConverter
                        .convertIiSetToDset(secondaryIds));
            }
        }
        return dto;
    }

    /**
     * 
     * @param webServiceOrgDTO
     *            webServiceOrgDTO
     * @return OrganizationDTO
     */
    public OrganizationDTO convertToOrganizationDTO(
            gov.nih.nci.pa.webservices.dto.OrganizationDTO webServiceOrgDTO) {
        OrganizationDTO dto = null;
        if (webServiceOrgDTO != null) {
            dto = new OrganizationDTO();
            dto.setName(EnOnConverter.convertToEnOn(webServiceOrgDTO.getName()));
            dto.setPostalAddress(getUnknownPostalAddress(webServiceOrgDTO
                    .getPostalAddress()));
            dto.setTelecomAddress(getUnknownTelecomeAddress(webServiceOrgDTO
                    .getTelecomAddress()));
        }
        return dto;
    }
    /**
     * 
     * @param webServiceList webServiceList
     * @return list
     */
    public List<OrganizationDTO> convertToOrganizationDTOList(
            List<gov.nih.nci.pa.webservices.dto.OrganizationDTO> webServiceList) {
        List<OrganizationDTO> list = null;
        if (webServiceList != null) {
            list = new ArrayList<OrganizationDTO>();
            for (gov.nih.nci.pa.webservices.dto.OrganizationDTO orgDTO : webServiceList) {
                OrganizationDTO dto = convertToOrganizationDTO(orgDTO);
                list.add(dto);
            }
        }
        return list;
    }   
    /**
     * 
     * @param webServiceStudySiteDTO webServiceStudySiteDTO
     * @return  StudySiteDTO
     */
    public StudySiteDTO convertToLeadOrgID(
            gov.nih.nci.pa.webservices.dto.StudySiteDTO webServiceStudySiteDTO) {
        StudySiteDTO dto = null;
        if (webServiceStudySiteDTO != null) {
            dto = new StudySiteDTO();
            dto.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(webServiceStudySiteDTO
                            .getLocalStudyProtocolIdentifier()));
        } 
        return dto;
    }
    /**
     * 
     * @param webServicePersonDTO webServicePersonDTO
     * @return PersonDTO
     */
    public PersonDTO convertToPersonDTO(
            gov.nih.nci.pa.webservices.dto.PersonDTO webServicePersonDTO) {
        PersonDTO dto = null;
        if (webServicePersonDTO != null
                && webServicePersonDTO.getName() != null) {
            dto = new PersonDTO();
            dto.setName(EnPnConverter.convertToEnPn(webServicePersonDTO
                    .getName().getFirstName(), webServicePersonDTO.getName()
                    .getMiddleName(), webServicePersonDTO.getName()
                    .getLastName(), webServicePersonDTO.getName().getPrefix(),
                    webServicePersonDTO.getName().getSuffix()));
            dto.setTelecomAddress(getUnknownTelecomeAddress(webServicePersonDTO
                    .getTelecomAddress()));
            dto.setPostalAddress(getUnknownPostalAddress(webServicePersonDTO
                    .getPostalAddress()));
        }
        return dto;
    }
    /**
     * 
     * @param webServicePartyDTO webServicePartyDTO
     * @return ResponsiblePartyDTO
     */
    public ResponsiblePartyDTO convertToPartyDTO(
            gov.nih.nci.pa.webservices.dto.ResponsiblePartyDTO webServicePartyDTO) {
        ResponsiblePartyDTO dto = null;
        if (webServicePartyDTO != null) {
            dto = new ResponsiblePartyDTO();
            dto.setTitle(webServicePartyDTO.getTitle());
            dto.setType(ResponsiblePartyType.getByCode(webServicePartyDTO
                    .getType()));
            dto.setInvestigator(convertToPersonDTO(webServicePartyDTO
                    .getInvestigator()));
            dto.setAffiliation(convertToOrganizationDTO(webServicePartyDTO
                    .getAffiliation()));
        }
        return dto;
    }
    /**
     * 
     * @param webServiceStatusDTO webServiceStatusDTO
     * @return StudyOverallStatusDTO
     */
    public StudyOverallStatusDTO convertToOverallStatusDTO(
            gov.nih.nci.pa.webservices.dto.StudyOverallStatusDTO webServiceStatusDTO) {
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        if (webServiceStatusDTO != null) {
            dto.setReasonText(StConverter.convertToSt(webServiceStatusDTO
                    .getReasonText()));
            dto.setStatusDate(TsConverter.convertToTs(webServiceStatusDTO
                    .getStatusDate()));
            dto.setStatusCode(CdConverter.convertStringToCd(webServiceStatusDTO
                    .getStatusCode()));
        }
        return dto;
    }
    /**
     * 
     * @param arms arms
     * @return list
     */
    public List<ArmDTO> convertToArmsDTOList(
            List<gov.nih.nci.pa.webservices.dto.ArmDTO> arms) {
        List<ArmDTO> list = new ArrayList<ArmDTO>();
        if (arms != null && !arms.isEmpty()) {
            for (gov.nih.nci.pa.webservices.dto.ArmDTO arm : arms) {
                ArmDTO dto = new ArmDTO();
                dto.setName(StConverter.convertToSt(arm.getName()));
                dto.setTypeCode(CdConverter.convertStringToCd(arm.getTypeCode()));
                dto.setDescriptionText(StConverter.convertToSt(arm
                        .getDescriptionText()));
                list.add(dto);
            }
        }
        return list;
    }
    /**
     * 
     * @param eligibilityList eligibilityList
     * @return list
     */
    public List<PlannedEligibilityCriterionDTO> convertToEligibilityDTOList(
            List<gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO> eligibilityList) {
        List<PlannedEligibilityCriterionDTO> list = new ArrayList<PlannedEligibilityCriterionDTO>();
        if (eligibilityList != null && !eligibilityList.isEmpty()) {
            for (gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO eligibility : eligibilityList) {
                PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
                dto.setInclusionIndicator(BlConverter.convertToBl(eligibility
                        .isInclusionIndicator()));
                dto.setCriterionName(StConverter.convertToSt(eligibility
                        .getCriterionName()));
                dto.setOperator(StConverter.convertToSt(eligibility
                        .getOperator()));
                dto.setEligibleGenderCode(CdConverter
                        .convertStringToCd(eligibility.getEligibleGenderCode()));
                dto.setDisplayOrder(IntConverter.convertToInt(eligibility
                        .getDisplayOrder()));
                dto.setStructuredIndicator(BlConverter.convertToBl(eligibility
                        .isStructuredIndicator()));
                dto.setTextValue(StConverter.convertToSt(eligibility
                        .getTextValue()));
                dto.setCdePublicIdentifier(IiConverter.convertToIi(eligibility
                        .getCdePublicIdentifier()));
                dto.setCdeVersionNumber(StConverter.convertToSt(eligibility
                        .getCdeVersionNumber()));
                dto.setInterventionIdentifier(IiConverter
                        .convertToIi(eligibility.getInterventionIdentifier()));
                dto.setLeadProductIndicator(BlConverter.convertToBl(eligibility
                        .isLeadProductIndicator()));
                dto.setTextDescription(StConverter.convertToSt(eligibility
                        .getTextDescription()));
                dto.setUserLastCreated(StConverter.convertToSt(eligibility
                        .getUserLastCreated()));
                if (eligibility.getMinValue() != null
                        && eligibility.getMaxValue() != null) {
                    dto.setValue(convertAgeRangeToIvlPq(
                            eligibility.getMinValue(),
                            eligibility.getMaxValue()));
                }
                list.add(dto);
            }
        }
        return list;
    }
    private Ivl<Pq> convertAgeRangeToIvlPq(AgeDTO minAge, AgeDTO maxAge) {
        IvlConverter.JavaPq low = parseAgeValue(minAge);
        IvlConverter.JavaPq high = parseAgeValue(maxAge);
        return IvlConverter.convertPq().convertToIvl(low, high);
    }

    private JavaPq parseAgeValue(AgeDTO age) {
        return new JavaPq(age.getUnitCode(), age.getValue(), age.getPrecision());
    }
    /**
     * 
     * @param outcomeList outcomeList
     * @return list
     */
    public List<StudyOutcomeMeasureDTO> convertTOOutcomesDTOList(
            List<gov.nih.nci.pa.webservices.dto.StudyOutcomeMeasureDTO> outcomeList) {
        List<StudyOutcomeMeasureDTO> list = new ArrayList<StudyOutcomeMeasureDTO>();
        if (outcomeList != null && !outcomeList.isEmpty()) {
            for (gov.nih.nci.pa.webservices.dto.StudyOutcomeMeasureDTO outcome : outcomeList) {
                StudyOutcomeMeasureDTO dto = new StudyOutcomeMeasureDTO();
                dto.setDescription(StConverter.convertToSt(outcome
                        .getDescription()));
                dto.setName(StConverter.convertToSt(outcome.getName()));
                dto.setTimeFrame(StConverter.convertToSt(outcome.getTimeFrame()));
                dto.setPrimaryIndicator(BlConverter.convertToBl(outcome
                        .isPrimaryIndicator()));
                dto.setSafetyIndicator(BlConverter.convertToBl(outcome
                        .isSafetyIndicator()));
                dto.setDisplayOrder(IntConverter.convertToInt(outcome
                        .getDisplayOrder()));
                dto.setTypeCode(CdConverter.convertStringToCd(outcome
                        .getTypeCode()));
                list.add(dto);
            }
        }
        return list;
    }
    /**
     * 
     * @param webServiceDoc webServiceDoc
     * @return dto
     * @throws IOException IOException
     */
    public DocumentDTO convertToDocument(
            gov.nih.nci.pa.webservices.dto.DocumentDTO webServiceDoc)
            throws IOException {
        DocumentDTO document = new DocumentDTO();
        if (webServiceDoc != null) {
            document.setActiveIndicator(BlConverter.convertToBl(webServiceDoc
                    .isActiveIndicator()));
            document.setFileName(StConverter.convertToSt(webServiceDoc
                    .getFileName()));
            document.setText(EdConverter.convertToEd(IOUtils
                    .toByteArray(webServiceDoc.getText())));
            document.setTypeCode(CdConverter.convertStringToCd(webServiceDoc
                    .getTypeCode()));
        }
        return document;
    }

    /**
     * @return postal Address.
     *
     */
    private static Ad getUnknownPostalAddress(String name) {
        return AddressConverterUtil.create(name, "", "UNKNOWN", "UM", "96960",
                "USA");
    }

    /**
     * @return telcomeAddress
     */
    private static DSet<Tel> getUnknownTelecomeAddress(String email) {
        return DSetConverter.convertListToDSet(Arrays.asList(email),
                DSetConverter.TYPE_EMAIL, null);
    }

}