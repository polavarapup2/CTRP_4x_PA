/**
 *
 */
package gov.nih.nci.pa.service.util;

import static gov.nih.nci.pa.util.PAUtil.getDset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PhoneUtil;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.jdom.Element;

/**
 * @author vrushali
 *
 */
@SuppressWarnings({ "unchecked", "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class PDQAbstractionXMLParser extends AbstractPDQXmlParser {

    private static final Logger LOG = Logger.getLogger(PDQAbstractionXMLParser.class);
    private static final String NAME_FIELD = "name";

    private List<OrganizationDTO> collaboratorOrgDTOs;
    private InterventionalStudyProtocolDTO ispDTO;
    private OrganizationDTO irbOrgDTO;
    private List<PlannedEligibilityCriterionDTO> eligibilityList;
    private List<StudyOutcomeMeasureDTO> outcomeMeasureDTOs;
    private List<PDQDiseaseDTO> listOfDiseaseDTOs;
    private List<InterventionDTO> listOfInterventionsDTOS;
    private Map<InterventionDTO, List<ArmDTO>> armInterventionMap;
    private List<ArmDTO> listOfArmDTOS;
    private Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>> locationsMap;
    private String healthyVolunteers;
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    private final CorrelationUtils corrUtils = new CorrelationUtils();

    private static final Map<String, RecruitmentStatusCode> RECRUITMENT_STATUS_MAP =
        new HashMap<String, RecruitmentStatusCode>();
    static {
        RECRUITMENT_STATUS_MAP.put("WITHDRAWN", RecruitmentStatusCode.WITHDRAWN);
        RECRUITMENT_STATUS_MAP.put("RECRUITING", RecruitmentStatusCode.ACTIVE);
        RECRUITMENT_STATUS_MAP.put("ENROLLING BY INVITATION", RecruitmentStatusCode.ENROLLING_BY_INVITATION);
        RECRUITMENT_STATUS_MAP.put("SUSPENDED", RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL);
        RECRUITMENT_STATUS_MAP.put("ACTIVE, NOT RECRUITING", RecruitmentStatusCode.CLOSED_TO_ACCRUAL);
        RECRUITMENT_STATUS_MAP.put("TERMINATED", RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE);
        RECRUITMENT_STATUS_MAP.put("COMPLETED", RecruitmentStatusCode.COMPLETED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void parse() throws PAException {
        super.parse();
        Element clinicalStudy = getDocument().getRootElement();
        readOutcomes(clinicalStudy);
        readConditons(clinicalStudy);
        readIrbInfo(clinicalStudy.getChild("oversight_info"));
        readEligibility(clinicalStudy.getChild("eligibility"));
        readStudyDesign(clinicalStudy);
        readInterventions(clinicalStudy);
        readArmGroups(clinicalStudy);
        readLocations(clinicalStudy);
        Element sponsorElmt = clinicalStudy.getChild("sponsors");
        setCollaboratorOrgDTOs(new ArrayList<OrganizationDTO>());
        List<Element> collaboratorElmtList = sponsorElmt.getChildren("collaborator");
        for (Element collaboratorElmt : collaboratorElmtList) {
            OrganizationDTO collaboratorDTO = new OrganizationDTO();
            collaboratorDTO.setName(EnOnConverter.convertToEnOn(getText(collaboratorElmt, "agency")));
            getCollaboratorOrgDTOs().add(collaboratorDTO);
        }
    }

    /**
     * @param clinicalStudy The clinical_study element
     */
    private void readLocations(Element clinicalStudy) throws PAException {
        Ts recrutingStatusDate = null;
        Element leadOrgStatusElmt = clinicalStudy.getChild("lead_org_status");
        if (leadOrgStatusElmt != null && CollectionUtils.isNotEmpty(leadOrgStatusElmt.getContent())) {
            recrutingStatusDate = tsFromString("yyyy-MM-dd", leadOrgStatusElmt.getAttributeValue("status_date"));
        }
        List<Element> locationElmtList = clinicalStudy.getChildren("location");
        setLocationsMap(new HashMap<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>>());
        for (Element locationElmt : locationElmtList) {
            OrganizationDTO orgDTO = new OrganizationDTO();
            // read Facility/Org
            Element facilityElmt = locationElmt.getChild("facility");
            String ctepId = facilityElmt.getAttributeValue("ctep-id");
            if (StringUtils.isNotEmpty(ctepId)) {
                Ii ctepHcfIi = new Ii();
                ctepHcfIi.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
                ctepHcfIi.setExtension(ctepId);
                try {
                    orgDTO.setIdentifier(corrUtils.getPoHcfByCtepId(ctepHcfIi));
                } catch (PAException e) {
                    logPartSiteLoadError(ctepId, facilityElmt);
                    continue;
                }
            } else {
                LOG.warn("Skipping location element. No ctep-id specified for name: "
                        + getText(facilityElmt, NAME_FIELD));
                continue;
            }
            orgDTO.setName(EnOnConverter.convertToEnOn(getText(facilityElmt, NAME_FIELD)));
            Element addressElmt = facilityElmt.getChild("address");
            String city = getText(addressElmt, "city");
            String countryName = getAlpha3CountryName(getText(addressElmt, "country"));
            String state = getText(addressElmt, "state");
            String zip = getText(addressElmt, "zip");
            orgDTO.setPostalAddress(AddressConverterUtil.create(null, null, city, state, zip, countryName));
            // read Status
            StudySiteAccrualStatusDTO siteStatus = new StudySiteAccrualStatusDTO();
            RecruitmentStatusCode recruitmentStatus = transformRecruitmentStatus(getText(locationElmt, "status"));
            siteStatus.setStatusCode(CdConverter.convertToCd(recruitmentStatus));
            siteStatus.setStatusDate(recrutingStatusDate);
            // read contact
            Map<StudySiteAccrualStatusDTO, Map<PoDto, String>> contactMap =
                new HashMap<StudySiteAccrualStatusDTO, Map<PoDto, String>>();
            contactMap.put(siteStatus, readContact(locationElmt, countryName));
            getLocationsMap().put(orgDTO, contactMap);
        }
    }

    /**
     * Transforms the recruitment status from the old values to the new, status status aligned values.
     * @param recruitmentStatus
     * @return the trial's recruitment status code
     * @throws PAException on error
     */
    private RecruitmentStatusCode transformRecruitmentStatus(String recruitmentStatus) throws PAException {
        RecruitmentStatusCode result = RECRUITMENT_STATUS_MAP.get(StringUtils.upperCase(recruitmentStatus));
        if (StringUtils.equalsIgnoreCase(recruitmentStatus, "NOT YET RECRUITING")) {
            StudyOverallStatusDTO studyStatus =
                PaRegistry.getStudyOverallStatusService().getCurrentByStudyProtocol(getIspDTO().getIdentifier());
            StudyStatusCode statusCode = StudyStatusCode.getByCode(studyStatus.getStatusCode().getCode());
            if (statusCode == StudyStatusCode.IN_REVIEW) {
                result = RecruitmentStatusCode.IN_REVIEW;
            } else {
                result = RecruitmentStatusCode.APPROVED;
            }
        }
        return result;
    }

    private void logPartSiteLoadError(String ctepId, Element facilityElmt) {
        StringBuffer errMsg = new StringBuffer(
                "Skipping location element. Error loading location with facility ctep-id: ");
        errMsg.append(ctepId);
        String facName = getText(facilityElmt, NAME_FIELD);
        if (StringUtils.isNotEmpty(facName)) {
            errMsg.append(" and name: ");
            errMsg.append(facName);
        }
        LOG.warn(errMsg.toString());
    }

    /**
     * Reads the contact of a location.
     * @param locationElmt the location element
     * @param countryName The country of the location
     * @return The Map containing the contact as key and its role as value.
     */
    private Map<PoDto, String> readContact(Element locationElmt, String countryName) throws PAException {
        Element contactElmt = locationElmt.getChild("contact");
        Map<PoDto, String> contactMap = new HashMap<PoDto, String>();
        if (contactElmt.getChild("first_name") != null) {
            PersonDTO contactDTO = new PersonDTO();
            String middleName = getText(contactElmt, "middle_name");
            if (middleName != null && middleName.indexOf('.') != -1) {
                middleName = middleName.substring(0, middleName.indexOf('.'));
            }
            contactDTO.setName(EnPnConverter.convertToEnPn(
                    getText(contactElmt, "first_name"), middleName,
                    getText(contactElmt, "last_name"), null, null));
            String phone = getText(contactElmt, "phone");
            try {
                if (StringUtils.isNotBlank(phone)) {
                    phone = PhoneUtil.formatPhoneNumber(countryName, phone);
                }
            } catch (IllegalArgumentException e) {
                throw new PAException(e.getMessage(), e);
            }
            String email = getText(contactElmt, "email");
            contactDTO.setTelecomAddress(getDset(email, phone));
            contactMap.put(contactDTO, StringUtils.equalsIgnoreCase(contactElmt.getAttributeValue("role"),
                                                                    "Principal investigator") ? "PI" : "");
        }

        if (contactElmt.getChild("last_name") != null) {
            LOG.info("No Generic Contact created for last_name: " + getText(contactElmt, "last_name"));
        }
        return contactMap;
    }

    /**
     * @param parent
     */
    private void readArmGroups(Element parent) {
        setListOfArmDTOS(new ArrayList<ArmDTO>());
        List<Element> armElmtList = parent.getChildren("arm_group");
        for (Element armElmt : armElmtList) {
            ArmDTO armDTO = new ArmDTO();
            armDTO.setName(StConverter.convertToSt(getText(armElmt, "arm_group_label")));
            armDTO.setTypeCode(CdConverter.convertToCd(ArmTypeCode.getByCode(WordUtils
                .capitalizeFully(getText(armElmt, "arm_type")))));
            armDTO.setDescriptionText(StConverter.convertToSt(getFullText(armElmt.getChild("arm_group_description"),
                                                                          "", "")));
            getListOfArmDTOS().add(armDTO);
        }
    }

    /**
     * @param parent
     */
    private void readInterventions(Element parent) {
        setListOfInterventionsDTOS(new ArrayList<InterventionDTO>());
        List<Element> interventionElmtList = parent.getChildren("intervention");
        setArmInterventionMap(new HashMap<InterventionDTO, List<ArmDTO>>());
        for (Element interventionElmt : interventionElmtList) {
            InterventionDTO interventionDto = new InterventionDTO();
            interventionDto.setPdqTermIdentifier(StConverter.convertToSt(interventionElmt.getAttributeValue("cdr-id")));
            interventionDto.setName(StConverter.convertToSt(getText(interventionElmt, "intervention_name")));
            interventionDto.setTypeCode(CdConverter.convertToCd(InterventionTypeCode
                .getByCode(getText(interventionElmt, "intervention_type"))));
            interventionDto.setDescriptionText(StConverter.convertToSt(getFullText(interventionElmt
                .getChild("intervention_description"), "", "")));
            listOfInterventionsDTOS.add(interventionDto);
            List<Element> armGrpLableElmtList = interventionElmt.getChildren("arm_group_label");
            List<ArmDTO> armList = new ArrayList<ArmDTO>();
            for (Element armGrpElmt : armGrpLableElmtList) {
                ArmDTO armDTO = new ArmDTO();
                armDTO.setName(StConverter.convertToSt(armGrpElmt.getText()));
                armList.add(armDTO);
            }
            getArmInterventionMap().put(interventionDto, armList);
        }

    }

    /**
     * reads the outcomes of the study.
     * @param parent The clinical_study element
     */
    private void readOutcomes(Element parent) {
        List<StudyOutcomeMeasureDTO> outcomes = new ArrayList<StudyOutcomeMeasureDTO>();
        for (Element primaryOutcome : (List<Element>) parent.getChildren("primary_outcome")) {
            outcomes.add(getOutcomeMeasure(primaryOutcome, Boolean.TRUE));
        }
        for (Element secondaryOutcome : (List<Element>) parent.getChildren("secondary_outcome")) {
            outcomes.add(getOutcomeMeasure(secondaryOutcome, Boolean.FALSE));
        }
        setOutcomeMeasureDTOs(outcomes);
    }

    /**
     * reads an outcome.
     * @param outcomeNode The primary_outcome or secondary_outcome element.
     * @param primaryIndicator indicates if the given element is a primary outcome
     * @return a StudyOutcomeMeasureDTO containing the data of the given element.
     */
    private StudyOutcomeMeasureDTO getOutcomeMeasure(Element outcomeNode, Boolean primaryIndicator) {
        StudyOutcomeMeasureDTO outcomeMeasure = new StudyOutcomeMeasureDTO();
        outcomeMeasure.setName(StConverter.convertToSt(getText(outcomeNode, "outcome_measure")));
        outcomeMeasure.setSafetyIndicator(BlConverter.convertToBl(BooleanUtils
            .toBoolean(getText(outcomeNode, "outcome_safety_issue"))));
        outcomeMeasure.setPrimaryIndicator(BlConverter.convertToBl(primaryIndicator));
        return outcomeMeasure;
    }

    /**
     * @param parent
     */
    private void readConditons(Element parent) {
        List<Element> conditionList = parent.getChildren("condition");
        setListOfDiseaseDTOs(new ArrayList<PDQDiseaseDTO>());
        for (Element conditionElt : conditionList) {
            PDQDiseaseDTO dDto = new PDQDiseaseDTO();
            dDto.setDiseaseCode(StConverter.convertToSt(conditionElt.getAttribute("cdr-id").getValue()));
            dDto.setPreferredName(StConverter.convertToSt(conditionElt.getText()));
            getListOfDiseaseDTOs().add(dDto);
        }

    }

    /**
     * @param parent
     */
    private void readIrbInfo(Element parent) {
        if (parent != null) {
            Element irbInfoElement = parent.getChild("irb_info");
            String orgName = getText(irbInfoElement, NAME_FIELD);
            String phone = getText(irbInfoElement, "phone");
            String email = getText(irbInfoElement, "email");
            getText(irbInfoElement, "full_address");
            if (StringUtils.isNotEmpty(orgName)) {
                setIrbOrgDTO(new OrganizationDTO());
                getIrbOrgDTO().setName(EnOnConverter.convertToEnOn(orgName));
                getIrbOrgDTO().setTelecomAddress(getDset(email, phone));
            }
        }
    }

    /**
     * @param parent
     */
    private void readEligibility(Element parent) {
        setHealthyVolunteers(parent.getChildText("healthy_volunteers"));
        PlannedEligibilityCriterionDTO pEligibiltyCriterionDTO;
        setEligibilityList(new ArrayList<PlannedEligibilityCriterionDTO>());
        String eligibleGenderCode = getText(parent, "gender");
        if (StringUtils.isNotEmpty(eligibleGenderCode)) {
            pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
            pEligibiltyCriterionDTO.setCriterionName(StConverter.convertToSt("GENDER"));
            pEligibiltyCriterionDTO.setEligibleGenderCode(CdConverter.convertToCd(EligibleGenderCode
                .getByCode(eligibleGenderCode)));
            pEligibiltyCriterionDTO
                .setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
            pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
            eligibilityList.add(pEligibiltyCriterionDTO);
        }
        // TODO: Handle Age criteria!
        String minimumValue = getText(parent, "minimum_age");
        String maximumValue = getText(parent, "maximum_age");
        if (minimumValue != null || maximumValue != null) {
            pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
            pEligibiltyCriterionDTO.setCriterionName(StConverter.convertToSt("AGE"));
            pEligibiltyCriterionDTO.setValue(convertToIvlPq("Years", minimumValue, "Years", maximumValue));
            pEligibiltyCriterionDTO
                .setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
            pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
            eligibilityList.add(pEligibiltyCriterionDTO);
        }

    }

    private void readStudyDesign(Element parent) {
        setIspDTO(new InterventionalStudyProtocolDTO());
        ispDTO.setPublicTitle(StConverter.convertToSt(getText(parent, "brief_title")));
        ispDTO.setPublicDescription(StConverter.convertToSt(getFullText(parent.getChild("brief_summary"), "", "")));
        ispDTO.setScientificDescription(StConverter.convertToSt(getFullText(parent.getChild("detailed_description"),
                                                                            "", "")));
        ispDTO.setRecordVerificationDate(tsFromString("yyyy-MM-dd", parent.getChildText("verification_date")));
        ispDTO.setTargetAccrualNumber(IvlConverter.convertInt().convertToIvl(getText(parent, "enrollment"), null));

        // PO-3339 Keywords are not loaded from PDQ because they are too long.
        ispDTO.setKeywordText(StConverter.convertToSt(""));

        Element studyDesignElmt = parent.getChild("study_design");
        if (studyDesignElmt == null) {
            return;
        }
        Element interventionalElement = studyDesignElmt.getChild("interventional_design");
        if (interventionalElement == null) {
            return;
        }
        ispDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(getText(interventionalElement,
                                                                           "interventional_subtype")));
        ispDTO.setAllocationCode(CdConverter.convertStringToCd(getText(interventionalElement, "allocation")));
        ispDTO.setBlindingSchemaCode(CdConverter.convertStringToCd(getText(interventionalElement, "masking")));
        ispDTO.setDesignConfigurationCode(CdConverter.convertStringToCd(getText(interventionalElement, "assignment")));
        ispDTO.setStudyClassificationCode(CdConverter.convertStringToCd(getText(interventionalElement, "endpoint")));
        ispDTO.setNumberOfInterventionGroups(IntConverter
            .convertToInt(getText(interventionalElement, "number_of_arms")));
        ispDTO.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode.ABBREVIATED));
    }

    /**
     * @param ispDTO the ispDTO to set
     */
    public void setIspDTO(InterventionalStudyProtocolDTO ispDTO) {
        this.ispDTO = ispDTO;
    }

    /**
     * @return the ispDTO
     */
    public InterventionalStudyProtocolDTO getIspDTO() {
        return ispDTO;
    }

    /**
     * @return the outcomeMeasureDTO
     */
    public List<StudyOutcomeMeasureDTO> getOutcomeMeasureDTOs() {
        return outcomeMeasureDTOs;
    }

    /**
     * @return the irbOrgDTO
     */
    public OrganizationDTO getIrbOrgDTO() {
        return irbOrgDTO;
    }

    /**
     * @return the collaboratorOrgDTOs
     */
    public List<OrganizationDTO> getCollaboratorOrgDTOs() {
        return collaboratorOrgDTOs;
    }

    /**
     * @return the listOfDiseaseDTOs
     */
    public List<PDQDiseaseDTO> getListOfDiseaseDTOs() {
        return listOfDiseaseDTOs;
    }

    /**
     * @return the listOfInterventionsDTOS
     */
    public List<InterventionDTO> getListOfInterventionsDTOS() {
        return listOfInterventionsDTOS;
    }

    /**
     * @param listOfInterventionsDTOS the listOfInterventionsDTOS to set
     */
    public void setListOfInterventionsDTOS(List<InterventionDTO> listOfInterventionsDTOS) {
        this.listOfInterventionsDTOS = listOfInterventionsDTOS;
    }

    /**
     * @return the armInterventionMap
     */
    public Map<InterventionDTO, List<ArmDTO>> getArmInterventionMap() {
        return armInterventionMap;
    }

    /**
     * @return the listOfArmDTOS
     */
    public List<ArmDTO> getListOfArmDTOS() {
        return listOfArmDTOS;
    }

    /**
     * @param eligibilityList the eligibilityList to set
     */
    public void setEligibilityList(List<PlannedEligibilityCriterionDTO> eligibilityList) {
        this.eligibilityList = eligibilityList;
    }

    /**
     * @return the eligibilityList
     */
    public List<PlannedEligibilityCriterionDTO> getEligibilityList() {
        return eligibilityList;
    }

    /**
     * @param collaboratorOrgDTOs the collaboratorOrgDTOs to set
     */
    public void setCollaboratorOrgDTOs(List<OrganizationDTO> collaboratorOrgDTOs) {
        this.collaboratorOrgDTOs = collaboratorOrgDTOs;
    }

    /**
     * @param irbOrgDTO the irbOrgDTO to set
     */
    public void setIrbOrgDTO(OrganizationDTO irbOrgDTO) {
        this.irbOrgDTO = irbOrgDTO;
    }

    /**
     * @param outcomeMeasureDTOs the outcomeMeasureDTOs to set
     */
    public void setOutcomeMeasureDTOs(List<StudyOutcomeMeasureDTO> outcomeMeasureDTOs) {
        this.outcomeMeasureDTOs = outcomeMeasureDTOs;
    }

    /**
     * @param listOfDiseaseDTOs the listOfDiseaseDTOs to set
     */
    public void setListOfDiseaseDTOs(List<PDQDiseaseDTO> listOfDiseaseDTOs) {
        this.listOfDiseaseDTOs = listOfDiseaseDTOs;
    }

    /**
     * @param armInterventionMap the armInterventionMap to set
     */
    public void setArmInterventionMap(Map<InterventionDTO, List<ArmDTO>> armInterventionMap) {
        this.armInterventionMap = armInterventionMap;
    }

    /**
     * @param listOfArmDTOS the listOfArmDTOS to set
     */
    public void setListOfArmDTOS(List<ArmDTO> listOfArmDTOS) {
        this.listOfArmDTOS = listOfArmDTOS;
    }

    /**
     * @param healthyVolunteers the healthyVolunteers to set
     */
    public void setHealthyVolunteers(String healthyVolunteers) {
        this.healthyVolunteers = healthyVolunteers;
    }

    /**
     * @return the healthyVolunteers
     */
    public String getHealthyVolunteers() {
        return healthyVolunteers;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    @Override
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @return the paServiceUtils
     */
    @Override
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }

    /**
     * @param locationsMap the locationsMap to set
     */
    public void setLocationsMap(Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>> locationsMap) {
        this.locationsMap = locationsMap;
    }

    /**
     * @return the locationsMap
     */
    public Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>> getLocationsMap() {
        return locationsMap;
    }
}
