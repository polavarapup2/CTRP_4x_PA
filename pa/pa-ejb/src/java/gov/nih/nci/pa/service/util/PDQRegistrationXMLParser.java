/**
 *
 */
package gov.nih.nci.pa.service.util;

import static gov.nih.nci.pa.util.PAUtil.getDset;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Element;

/**
 * @author vrushali
 *
 */
public class PDQRegistrationXMLParser extends AbstractPDQXmlParser {
    /**
     *
     */
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private StudyProtocolDTO studyProtocolDTO;
    private StudyOverallStatusDTO studyOverallStatusDTO;
    private List<StudyIndldeDTO> studyIndldeDTOs;
    private OrganizationDTO leadOrganizationDTO;
    private PersonDTO principalInvestigatorDTO;
    private OrganizationDTO sponsorOrganizationDTO;
    private StudySiteDTO leadOrganizationSiteIdentifierDTO;
    private Map<String, String> studyIdentifierMap;
    private PersonDTO responsiblePartyContact;
    private Map<String, String> regAuthMap;
    private String respPartytype;
    private static final Map<String, String> PHASE_MAP = new HashMap<String, String>();
    private static final Logger LOG  = Logger.getLogger(PDQRegistrationXMLParser.class);
    static {
        PHASE_MAP.put("0", "0");
        PHASE_MAP.put("1", "I");
        PHASE_MAP.put("I", "I");
        PHASE_MAP.put("2", "II");
        PHASE_MAP.put("II", "II");
        PHASE_MAP.put("3", "III");
        PHASE_MAP.put("III", "III");
        PHASE_MAP.put("4", "IV");
        PHASE_MAP.put("IV", "IV");
        PHASE_MAP.put("I/II", "I/II");
        PHASE_MAP.put("1/2", "I/II");
        PHASE_MAP.put("II/III", "II/III");
        PHASE_MAP.put("2/3", "II/III");
        PHASE_MAP.put("NA", "NA");
        PHASE_MAP.put("N/A", "NA");
    }
    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    /**
     * {@inheritDoc}
     */
    @Override
    public void parse() throws PAException {
        super.parse();
        Element clinicalStudy = getDocument().getRootElement();
        doStudyOverallStatus(clinicalStudy);
        readStudyDesign(clinicalStudy);
        readStudyIdentifiers(clinicalStudy);
        readIndInfo(clinicalStudy);
        readSponsor(clinicalStudy.getChild("sponsors"));
        readOversightInfo(clinicalStudy.getChild("oversight_info"));
        readOverallOfficials(clinicalStudy);
        Element leadOrgElmt = clinicalStudy.getChild("lead_org");
        if (leadOrgElmt != null && CollectionUtils.isNotEmpty(leadOrgElmt.getContent())) {
            String ctepId = leadOrgElmt.getAttributeValue("ctep-id");
            if (StringUtils.isNotEmpty(ctepId)) {
                leadOrganizationDTO = getPaServiceUtils().getOrganizationByCtepId(ctepId);
            }
            if (leadOrganizationDTO == null) {
                leadOrganizationDTO = new OrganizationDTO();
            }
            leadOrganizationDTO.setName(EnOnConverter.convertToEnOn(leadOrgElmt.getText()));
        }
    }

    private void doStudyOverallStatus(Element parent) {
        setStudyOverallStatusDTO(new StudyOverallStatusDTO());
        getStudyOverallStatusDTO().setStatusCode(CdConverter.convertStringToCd(getText(parent, "overall_status")));
        Element leadOrgStatusElmt = parent.getChild("lead_org_status");
        if (leadOrgStatusElmt != null && CollectionUtils.isNotEmpty(leadOrgStatusElmt.getContent())) {
            getStudyOverallStatusDTO().setStatusDate(tsFromString(YYYY_MM_DD,
                leadOrgStatusElmt.getAttributeValue("status_date")));
        }
    }
    private void readStudyDesign(Element parent) throws PAException {
        if (parent == null) {
            return;
        }
        Element studyDesignElt = parent.getChild("study_design");
        String studyType = getText(studyDesignElt, "study_type");
        if (StringUtils.equalsIgnoreCase("interventional", studyType)) {
            studyProtocolDTO = new InterventionalStudyProtocolDTO();
        } else {
            throw new PAException("Study_type can only be interventional.");
        }
        studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt(studyType));
        studyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(getText(studyDesignElt.getChild(
                "interventional_design"), "interventional_subtype")));
        if (parent.getChild("start_date") != null) {
            studyProtocolDTO.setStartDate(tsFromString(YYYY_MM_DD, parent.getChildText("start_date")));
            studyProtocolDTO.setStartDateTypeCode(CdConverter.convertStringToCd(parent.getChild("start_date")
                .getAttributeValue("date_type")));
        }
        setPrimaryCompletion(parent);
        studyProtocolDTO.setPhaseCode(CdConverter.convertStringToCd(PHASE_MAP.get(parent.getChildText("phase"))));
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt(getText(parent, "official_title")));
        doIds(parent.getChild("id_info"));
        doFda(parent);
    }

    private void setPrimaryCompletion(Element parent) {
        String primaryCompletionDate = getText(parent, "primary_compl_date");
        String primaryCompletionDateType = getText(parent, "primary_compl_date_type");
        if (StringUtils.isEmpty(primaryCompletionDate)) {
            primaryCompletionDate = "2100-01-01";
            LOG.warn("Empty Primary Completion Date found - defaulting to: " + primaryCompletionDate);
        }
        if (StringUtils.isEmpty(primaryCompletionDateType)) {
            primaryCompletionDateType = ActualAnticipatedTypeCode.ANTICIPATED.getCode();
            LOG.warn("Empty Primary Completion Date Type found - defaulting to: " + primaryCompletionDateType);
        }
        if (!isPrimaryCompletedDateValid(primaryCompletionDate, primaryCompletionDateType)) {
            primaryCompletionDate = "2100-01-01";
            primaryCompletionDateType = ActualAnticipatedTypeCode.ANTICIPATED.getCode();
            LOG.warn("Invalid Primary Completion Date and Type found - defaulting to: " + primaryCompletionDate
                    + " and " + primaryCompletionDateType);
        }
        studyProtocolDTO.setPrimaryCompletionDate(tsFromString(YYYY_MM_DD, primaryCompletionDate));
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(
          CdConverter.convertStringToCd(primaryCompletionDateType));
    }

    private boolean isPrimaryCompletedDateValid(String primaryCompletionDate, String primaryCompletionDateType) {
        try {
            Date prCmpDate = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault()).parse(primaryCompletionDate);
            Date today = new Date();

            if ((primaryCompletionDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode()) && today
                    .after(prCmpDate))
                    || (primaryCompletionDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode()) && !(today
                            .after(prCmpDate)))) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private void doIds(Element parent) {
        DSet<Ii> otherIds = new DSet<Ii>();
        Set<Ii> iis = new HashSet<Ii>();

        List<Element> secIdList = parent.getChildren("secondary_id");
        for (Element secIdElt : secIdList) {
            Ii ii = new Ii();
            ii.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
            ii.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
            ii.setExtension(secIdElt.getText());
            iis.add(ii);
        }
        otherIds.setItem(iis);
        studyProtocolDTO.setSecondaryIdentifiers(otherIds);
        leadOrganizationSiteIdentifierDTO = new StudySiteDTO();
        leadOrganizationSiteIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(
                parent.getChildText("org_study_id")));
    }
    private void doFda(Element parent) {
        studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(
                BooleanUtils.toBoolean(parent.getChildText("is_fda_regulated"))));
        studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(
                BooleanUtils.toBoolean(parent.getChildText("is_section_801"))));
        studyProtocolDTO.setDelayedpostingIndicator(BlConverter.convertToBl(
                BooleanUtils.toBoolean(parent.getChildText("delayed_posting"))));

    }
    @SuppressWarnings("unchecked")
    private void readIndInfo(Element parent) {
        Boolean isIndStudy = BooleanUtils.toBoolean(parent.getChildText("is_ind_study"));
        List<Element> indInfoList = parent.getChildren("ind_info");
        for (Element indInfoNodeElt : indInfoList) {
            studyIndldeDTOs = new ArrayList<StudyIndldeDTO>();
            if (indInfoNodeElt != null) {
                StudyIndldeDTO indDTO = new StudyIndldeDTO();
                indDTO.setExemptIndicator(BlConverter.convertToBl(!isIndStudy));
                indDTO.setGrantorCode(CdConverter.convertStringToCd(getText(indInfoNodeElt, "ind_grantor")));
                indDTO.setIndldeNumber(StConverter.convertToSt(getText(indInfoNodeElt, "ind_number")));
                studyIndldeDTOs.add(indDTO);
                studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
            }
        }
    }
    /**
     * @param parent
     */
    private void readStudyIdentifiers(Element parent) {
        studyIdentifierMap = new HashMap<String, String>();
        String id = parent.getAttribute("nct-id").getValue();
        if (StringUtils.isNotEmpty(id)) {
            studyIdentifierMap.put(PAConstants.NCT_IDENTIFIER_TYPE, id);
        }
        id = parent.getAttribute("ctep-id").getValue();
        if (StringUtils.isNotEmpty(id)) {
            studyIdentifierMap.put(PAConstants.CTEP_IDENTIFIER_TYPE, id);
        }
        id = parent.getAttribute("dcp-id").getValue();
        if (StringUtils.isNotEmpty(id)) {
            studyIdentifierMap.put(PAConstants.DCP_IDENTIFIER_TYPE, id);
        }
    }


    /**
     * @param parent
     */
    private void readSponsor(Element parent) {
        sponsorOrganizationDTO = new OrganizationDTO();
        String sponsorOrgName = getText(parent.getChild("lead_sponsor"), "agency");
        sponsorOrganizationDTO.setName(EnOnConverter.convertToEnOn(sponsorOrgName));
        //resp party
        Element respParty = parent.getChild("resp_party");
        if (respParty != null) {
            respPartytype = respParty.getAttributeValue("party-type");  
        }
        
        String email = getText(respParty, "email");
        if (StringUtils.isEmpty(email)) {
            email = "PIO@ctep.nci.nih.gov";
            LOG.warn("Responsible party email was defaulted.");
        }
        String phone = getText(respParty, "phone");
        if (StringUtils.isEmpty(phone)) {
            phone = "000-000-0000";
            LOG.warn("Responsible party phone was defaulted.");
        }
        responsiblePartyContact = new PersonDTO();
        responsiblePartyContact.setName(EnPnConverter.convertToEnPn(getText(respParty, "name_title"), null, null, null,
                null));
        responsiblePartyContact.setTelecomAddress(getDset(email, phone));
    }

    /**
     * @param parent
     */
    private void readOversightInfo(Element parent) {
        if (parent == null) {
            return;
        }
        regAuthMap = new HashMap<String, String>();
        String regulatoryAuthority = getText(parent, "regulatory_authority");
        String authorityName = "";
        String countryName = "";
        if (StringUtils.isNotEmpty(regulatoryAuthority)) {
            int index = StringUtils.indexOf(regulatoryAuthority, ':');
            authorityName = regulatoryAuthority.substring(index);
            countryName = regulatoryAuthority.substring(0, index);
            regAuthMap.put("AuthorityName", authorityName);
            regAuthMap.put("CountryName", countryName);
        }
    }

    /**
     * @param parent
     */
    @SuppressWarnings("unchecked")
    private void readOverallOfficials(Element parent) {
        List<Element> overallOfficialElmtList = parent.getChildren("overall_official");
        for (Element overallOfficialElement : overallOfficialElmtList) {
            leadOrganizationDTO = new OrganizationDTO();
            if (overallOfficialElmtList.size() > 1) {
                // if multiple overall official then check if it one has role PI else pick first.
                for (Element elmt : overallOfficialElmtList) {
                    String role = getText(elmt, "role");
                    if (StringUtils.equalsIgnoreCase(role, "Principal Investigator")) {
                        principalInvestigatorDTO = readPrincipalInvestigatorInfo(elmt);
                        leadOrganizationDTO.setName(EnOnConverter.convertToEnOn(getText(elmt, "affiliation")));
                    }
                }
                // if there are no PI role then pick the first
                if (principalInvestigatorDTO == null) {
                    principalInvestigatorDTO = readPrincipalInvestigatorInfo(overallOfficialElmtList.get(0));
                    leadOrganizationDTO.setName(EnOnConverter.convertToEnOn(getText(overallOfficialElmtList.get(0),
                    "affiliation")));
                }
                break;
            }
            //No multiple Overall official so pick whatever provided as PI
            principalInvestigatorDTO =  readPrincipalInvestigatorInfo(overallOfficialElement);
            //lead org
            leadOrganizationDTO.setName(EnOnConverter.convertToEnOn(getText(overallOfficialElement, "affiliation")));
        }
    }

    /**
     * @return the studyIndldeDTOs
     */
    public List<StudyIndldeDTO> getStudyIndldeDTOs() {
        return studyIndldeDTOs;
    }
    /**
     * @param studyIndldeDTOs the studyIndldeDTOs to set
     */
    public void setStudyIndldeDTOs(List<StudyIndldeDTO> studyIndldeDTOs) {
        this.studyIndldeDTOs = studyIndldeDTOs;
    }
    /**
     * @return the leadOrganizationDTO
     */
    public OrganizationDTO getLeadOrganizationDTO() {
        return leadOrganizationDTO;
    }
    /**
     * @param leadOrganizationDTO the leadOrganizationDTO to set
     */
    public void setLeadOrganizationDTO(OrganizationDTO leadOrganizationDTO) {
        this.leadOrganizationDTO = leadOrganizationDTO;
    }
    /**
     * @return the principalInvestigatorDTO
     */
    public PersonDTO getPrincipalInvestigatorDTO() {
        return principalInvestigatorDTO;
    }
    /**
     * @param principalInvestigatorDTO the principalInvestigatorDTO to set
     */
    public void setPrincipalInvestigatorDTO(PersonDTO principalInvestigatorDTO) {
        this.principalInvestigatorDTO = principalInvestigatorDTO;
    }
    /**
     * @return the sponsorOrganizationDTO
     */
    public OrganizationDTO getSponsorOrganizationDTO() {
        return sponsorOrganizationDTO;
    }
    /**
     * @param sponsorOrganizationDTO the sponsorOrganizationDTO to set
     */
    public void setSponsorOrganizationDTO(OrganizationDTO sponsorOrganizationDTO) {
        this.sponsorOrganizationDTO = sponsorOrganizationDTO;
    }
    /**
     * @return the leadOrganizationSiteIdentifierDTO
     */
    public StudySiteDTO getLeadOrganizationSiteIdentifierDTO() {
        return leadOrganizationSiteIdentifierDTO;
    }
    /**
     * @param leadOrganizationSiteIdentifierDTO the leadOrganizationSiteIdentifierDTO to set
     */
    public void setLeadOrganizationSiteIdentifierDTO(
            StudySiteDTO leadOrganizationSiteIdentifierDTO) {
        this.leadOrganizationSiteIdentifierDTO = leadOrganizationSiteIdentifierDTO;
    }
    /**
     * @return the studyIdentifierMap
     */
    public Map<String, String> getStudyIdentifierMap() {
        return studyIdentifierMap;
    }
    /**
     * @param studyIdentifierMap the studyIdentifierMap to set
     */
    public void setStudyIdentifierMap(Map<String, String> studyIdentifierMap) {
        this.studyIdentifierMap = studyIdentifierMap;
    }
    /**
     * @return the responsiblePartyContact
     */
    public PersonDTO getResponsiblePartyContact() {
        return responsiblePartyContact;
    }
    /**
     * @param responsiblePartyContact the responsiblePartyContact to set
     */
    public void setResponsiblePartyContact(PersonDTO responsiblePartyContact) {
        this.responsiblePartyContact = responsiblePartyContact;
    }
    /**
     * @return the regAuthMap
     */
    public Map<String, String> getRegAuthMap() {
        return regAuthMap;
    }
    /**
     * @param regAuthMap the regAuthMap to set
     */
    public void setRegAuthMap(Map<String, String> regAuthMap) {
        this.regAuthMap = regAuthMap;
    }

    /**
     * @param studyProtocolDTO the studyProtocolDTO to set
     */
    public void setStudyProtocolDTO(StudyProtocolDTO studyProtocolDTO) {
        this.studyProtocolDTO = studyProtocolDTO;
    }

    /**
     * @return the studyProtocolDTO
     */
    public StudyProtocolDTO getStudyProtocolDTO() {
        return studyProtocolDTO;
    }

    /**
     * @param studyOverallStatusDTO the studyOverallStatusDTO to set
     */
    public void setStudyOverallStatusDTO(StudyOverallStatusDTO studyOverallStatusDTO) {
        this.studyOverallStatusDTO = studyOverallStatusDTO;
    }

    /**
     * @return the studyOverallStatusDTO
     */
    public StudyOverallStatusDTO getStudyOverallStatusDTO() {
        return studyOverallStatusDTO;
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
     * @return the respPartytype
     */
    public String getRespPartytype() {
        return respPartytype;
    }

    /**
     * @param respPartytype the respPartytype to set
     */
    public void setRespPartytype(String respPartytype) {
        this.respPartytype = respPartytype;
    }


}
