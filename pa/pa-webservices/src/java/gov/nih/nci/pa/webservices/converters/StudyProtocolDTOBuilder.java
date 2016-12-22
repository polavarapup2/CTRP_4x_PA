/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.TrialDataException;
import gov.nih.nci.pa.webservices.types.AbbreviatedTrialUpdate;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialAmendment;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.InterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.NonInterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.PrimaryPurpose;
import gov.nih.nci.pa.webservices.types.TrialDateType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class StudyProtocolDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return StudyProtocolDTO
     */
    public StudyProtocolDTO build(CompleteTrialRegistration reg) {
        StudyProtocolDTO dto = instantitateStudyProtocolDTO(reg);
        dto.setStudyProtocolType(StConverter
                .convertToSt(dto instanceof InterventionalStudyProtocolDTO ? PAConstants.INTERVENTIONAL
                        : PAConstants.NON_INTERVENTIONAL));
        convertOtherIdentifiers(reg.getOtherTrialID(), dto);
        dto.setAccrualDiseaseCodeSystem(StConverter.convertToSt(reg
                .getAccrualDiseaseTerminology().value()));

        convertFieldsCommonToAmendmentAndRegistration(reg, dto);
        return dto;
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertFieldsCommonToAmendmentAndRegistration(
            BaseTrialInformation reg, StudyProtocolDTO dto) {
        if (StringUtils.isNotEmpty(reg.getTitle())) {
            dto.setOfficialTitle(StConverter.convertToSt(reg.getTitle()));
        }
        dto.setNciGrant(BlConverter.convertToBl(reg.isFundedByNciGrant()));
        dto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));

        convertPhase(reg, dto);
        convertPrimaryPurpose(reg, dto);
        convertTrialDates(reg, dto);

        dto.setProgramCodeText(StConverter.convertToSt(reg.getProgramCode()));
        dto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(reg
                .isClinicalTrialsDotGovXmlRequired()));

        convertRegulatoryInfo(reg, dto);

        convertStudyDesignSpecifics(reg, dto);
        dto.setStudySource(CdConverter
                .convertToCd(StudySourceCode.REST_SERVICE));
        setUser(dto);
    }

    /**
     * @param spDTO
     *            StudyProtocolDTO
     * @param upd
     *            CompleteTrialUpdate
     */
    public void build(StudyProtocolDTO spDTO, CompleteTrialUpdate upd) {
        addAdditionalOtherIdentifiers(upd.getOtherTrialID(), spDTO);
        if (upd.getAccrualDiseaseTerminology() != null
                && PaRegistry
                        .getAccrualDiseaseTerminologyService()
                        .canChangeCodeSystem(
                                IiConverter.convertToLong(spDTO.getIdentifier()))) {
            spDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt(upd
                    .getAccrualDiseaseTerminology().value()));
        }
        convertTrialDates(upd, spDTO);
        setUser(spDTO);
        spDTO.setStudySource(CdConverter
                .convertToCd(StudySourceCode.REST_SERVICE));
    }

    /**
     * @param spDTO
     *            StudyProtocolDTO
     * @param upd
     *            CompleteTrialUpdate
     */
    public void build(StudyProtocolDTO spDTO, CompleteTrialAmendment upd) {
        verifyNoAttemptToChangeDesignType(spDTO, upd);
        addAdditionalOtherIdentifiers(upd.getOtherTrialID(), spDTO);
        if (upd.getAccrualDiseaseTerminology() != null
                && PaRegistry
                        .getAccrualDiseaseTerminologyService()
                        .canChangeCodeSystem(
                                IiConverter.convertToLong(spDTO.getIdentifier()))) {
            spDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt(upd
                    .getAccrualDiseaseTerminology().value()));
        }
        spDTO.setAmendmentNumber(StConverter.convertToSt(upd
                .getAmendmentNumber()));
        spDTO.setAmendmentDate(TsConverter.convertToTs((upd.getAmendmentDate()
                .toGregorianCalendar().getTime())));
        convertFieldsCommonToAmendmentAndRegistration(upd, spDTO);

    }

    private void verifyNoAttemptToChangeDesignType(StudyProtocolDTO spDTO,
            CompleteTrialAmendment upd) {
        if ((spDTO instanceof InterventionalStudyProtocolDTO && upd
                .getNonInterventionalDesign() != null)
                || (spDTO instanceof NonInterventionalStudyProtocolDTO && upd
                        .getInterventionalDesign() != null)) {
            throw new TrialDataException(
                    "An amendment cannot change a trial from Interventional to Non-Interventional or vice versa.");
        }
    }

    private void addAdditionalOtherIdentifiers(List<String> otherIDList,
            StudyProtocolDTO spDTO) {
        if (CollectionUtils.isNotEmpty(otherIDList)) {
            for (String otherID : otherIDList) {
                Ii ii = IiConverter.convertToOtherIdentifierIi(otherID);
                if (!PAUtil.containsIi(spDTO.getSecondaryIdentifiers()
                        .getItem(), ii.getExtension(), ii.getRoot())) {
                    spDTO.getSecondaryIdentifiers().getItem().add(ii);
                }
            }
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertOtherIdentifiers(List<String> otherIDList,
            StudyProtocolDTO dto) {
        if (CollectionUtils.isNotEmpty(otherIDList)) {
            List<Ii> iis = new ArrayList<Ii>();
            for (String otherID : otherIDList) {
                iis.add(IiConverter.convertToOtherIdentifierIi(otherID));
            }
            dto.setSecondaryIdentifiers(DSetConverter
                    .convertIiSetToDset(new HashSet<Ii>(iis)));
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertRegulatoryInfo(BaseTrialInformation reg,
            StudyProtocolDTO dto) {
        if (reg.getRegulatoryInformation() != null) {
            dto.setFdaRegulatedIndicator(BlConverter.convertToBl(reg
                    .getRegulatoryInformation().isFdaRegulated()));
            dto.setSection801Indicator(BlConverter.convertToBl(reg
                    .getRegulatoryInformation().isSection801()));
            dto.setDelayedpostingIndicator(BlConverter.convertToBl(reg
                    .getRegulatoryInformation().isDelayedPosting()));
            dto.setDataMonitoringCommitteeAppointedIndicator(BlConverter
                    .convertToBl(reg.getRegulatoryInformation()
                            .isDataMonitoringCommitteeAppointed()));
        } else {
            dto.setFdaRegulatedIndicator(null);
            dto.setSection801Indicator(null);
            dto.setDelayedpostingIndicator(null);
            dto.setDataMonitoringCommitteeAppointedIndicator(null);
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertTrialDates(BaseTrialInformation reg,
            StudyProtocolDTO dto) {
        dto.setStartDate(TsConverter.convertToTs(reg.getTrialStartDate()
                .getValue().toGregorianCalendar().getTime()));
        dto.setStartDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                        .getTrialStartDate().getType())));

        // DCP trials can have a NULL PCD.
        if (reg.getPrimaryCompletionDate().getValue().getValue() != null) {
            dto.setPrimaryCompletionDate(TsConverter.convertToTs((reg
                    .getPrimaryCompletionDate().getValue().getValue()
                    .toGregorianCalendar().getTime())));
        } else {
            dto.setPrimaryCompletionDate(null);
        }

        dto.setPrimaryCompletionDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                        .getPrimaryCompletionDate().getValue().getType())));
        if (reg.getCompletionDate() != null) {
            dto.setCompletionDate(TsConverter.convertToTs(reg
                    .getCompletionDate().getValue().toGregorianCalendar()
                    .getTime()));
            dto.setCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                            .getCompletionDate().getType())));
        } else {
            dto.setCompletionDate(null);
            dto.setCompletionDateTypeCode(null);
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertTrialDates(CompleteTrialUpdate reg, StudyProtocolDTO dto) {
        if (reg.getTrialStartDate() != null) {
            dto.setStartDate(TsConverter.convertToTs(reg.getTrialStartDate()
                    .getValue().toGregorianCalendar().getTime()));
            dto.setStartDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                            .getTrialStartDate().getType())));
        }
        final JAXBElement<TrialDateType> pcd = reg.getPrimaryCompletionDate();
        if (pcd != null) {
            if (!pcd.isNil()) {
                dto.setPrimaryCompletionDate(TsConverter.convertToTs((pcd
                        .getValue().getValue().toGregorianCalendar().getTime())));
            } else {
                dto.setPrimaryCompletionDate(null);
            }
            dto.setPrimaryCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(pcd
                            .getValue().getType())));
        }
        if (reg.getCompletionDate() != null) {
            dto.setCompletionDate(TsConverter.convertToTs(reg
                    .getCompletionDate().getValue().toGregorianCalendar()
                    .getTime()));
            dto.setCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                            .getCompletionDate().getType())));
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertPhase(BaseTrialInformation reg, StudyProtocolDTO dto) {
        dto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(reg
                .getPhase())));
        dto.setPhaseAdditionalQualifierCode(CdConverter
                .convertToCd(PhaseAdditionalQualifierCode
                        .getByCode(Boolean.TRUE.equals(reg.isPilot()) ? "Pilot"
                                : "")));
    }

    private void convertStudyDesignSpecifics(BaseTrialInformation reg,
            StudyProtocolDTO dto) {
        if (reg.getInterventionalDesign() != null) {
            convertInterventionalDesign(reg.getInterventionalDesign(),
                    (InterventionalStudyProtocolDTO) dto);
        } else if (reg.getNonInterventionalDesign() != null) {
            convertNonInterventionalDesign(reg.getNonInterventionalDesign(),
                    (NonInterventionalStudyProtocolDTO) dto);
        }
    }

    private void convertNonInterventionalDesign(
            NonInterventionalTrialDesign design,
            NonInterventionalStudyProtocolDTO dto) {
        dto.setStudyModelCode(CdConverter.convertStringToCd(design
                .getStudyModelCode().value()));
        dto.setStudyModelOtherText(StConverter.convertToSt(design
                .getStudyModelCodeOtherDescription()));
        dto.setTimePerspectiveCode(CdConverter.convertStringToCd(design
                .getTimePerspectiveCode().value()));
        dto.setTimePerspectiveOtherText(StConverter.convertToSt(design
                .getTimePerspectiveCodeOtherDescription()));
        dto.setStudySubtypeCode(CdConverter.convertStringToCd(design
                .getTrialType().value()));

    }

    private void convertInterventionalDesign(InterventionalTrialDesign design,
            InterventionalStudyProtocolDTO dto) {
        if (design.getSecondaryPurpose() != null) {
            dto.setSecondaryPurposes(DSetConverter.convertListStToDSet(Arrays
                    .asList(design.getSecondaryPurpose().value())));
            dto.setSecondaryPurposeOtherText(StConverter.convertToSt(design
                    .getSecondaryPurposeOtherDescription()));
        } else {
            dto.setSecondaryPurposes(DSetConverter
                    .convertListStToDSet(new ArrayList<String>()));
            dto.setSecondaryPurposeOtherText(null);
        }
    }

    private void convertPrimaryPurpose(BaseTrialInformation reg,
            StudyProtocolDTO dto) {
        dto.setPrimaryPurposeCode(CdConverter.convertStringToCd((reg
                .getPrimaryPurpose().value())));
        if (reg.getPrimaryPurpose() == PrimaryPurpose.OTHER) {
            dto.setPrimaryPurposeOtherText(StConverter.convertToSt(reg
                    .getPrimaryPurposeOtherDescription()));
            dto.setPrimaryPurposeAdditionalQualifierCode(CdConverter
                    .convertToCd(PrimaryPurposeAdditionalQualifierCode.OTHER));
        } else {
            dto.setPrimaryPurposeOtherText(null);
            dto.setPrimaryPurposeAdditionalQualifierCode(null);
        }

    }

    private StudyProtocolDTO instantitateStudyProtocolDTO(
            BaseTrialInformation reg) {
        return reg.getInterventionalDesign() != null ? new InterventionalStudyProtocolDTO()
                : new NonInterventionalStudyProtocolDTO();
    }

    /**
     * @param dto
     */
    private void setUser(StudyProtocolDTO dto) {
        String identity = UsernameHolder.getUser();
        St loggedInUser = new St();
        loggedInUser.setValue(identity);
        dto.setUserLastCreated(loggedInUser);
    }

    /**
     * @param spDTO
     *            StudyProtocolDTO
     * @param reg
     *            AbbreviatedTrialUpdate
     */
    public void build(StudyProtocolDTO spDTO, AbbreviatedTrialUpdate reg) {
        setUser(spDTO);
    }

}
