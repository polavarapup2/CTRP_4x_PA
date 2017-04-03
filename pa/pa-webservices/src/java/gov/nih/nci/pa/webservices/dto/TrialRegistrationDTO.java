package gov.nih.nci.pa.webservices.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by chandrasekaranp on 3/25/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrialRegistrationDTO {

    private String nctID;
    private StudyProtocolWebServiceDTO studyProtocolDTO;
    private OrganizationDTO leadOrgDTO;
    private StudySiteDTO leadOrgID;
    private OrganizationDTO sponsorDTO;
    private PersonDTO investigatorDTO;
    private ResponsiblePartyDTO partyDTO;
    private PersonDTO centralContactDTO;
    private StudyOverallStatusDTO overallStatusDTO;
    private List<ArmDTO> arms;
    private List<PlannedEligibilityCriterionDTO> eligibility;
    private List<StudyOutcomeMeasureDTO> outcomes;
    private List<OrganizationDTO> collaborators;
    private DocumentDTO document;
    /**
     * Constructor
     */
    public TrialRegistrationDTO() {
        //TODO
    }
    //CHECKSTYLE:OFF
    /**
     * 
     * @param nctID nctID
     * @param studyProtocolWebServiceDTO studyProtocolWebServiceDTO
     * @param leadOrgDTO leadOrgDTO
     * @param leadOrgID leadOrgID
     * @param sponsorDTO sponsorDTO
     * @param investigatorDTO investigatorDTO
     * @param partyDTO partyDTO
     * @param centralContactDTO centralContactDTO
     * @param overallStatusDTO overallStatusDTO
     * @param arms arms
     * @param eligibility eligibility
     * @param outcomes outcomes
     * @param collaborators collaborators
     * @param document document
     */
    public TrialRegistrationDTO(String nctID, // NOPMD
                StudyProtocolWebServiceDTO studyProtocolWebServiceDTO, OrganizationDTO leadOrgDTO,
                                StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO, PersonDTO investigatorDTO,
                                ResponsiblePartyDTO partyDTO, PersonDTO centralContactDTO,
                                StudyOverallStatusDTO overallStatusDTO, List<ArmDTO> arms,
                                List<PlannedEligibilityCriterionDTO> eligibility, 
                                List<StudyOutcomeMeasureDTO> outcomes,
                                List<OrganizationDTO> collaborators, DocumentDTO document) {
        this.nctID = nctID;
        this.studyProtocolDTO = studyProtocolWebServiceDTO;
        this.leadOrgDTO = leadOrgDTO;
        this.leadOrgID = leadOrgID;
        this.sponsorDTO = sponsorDTO;
        this.investigatorDTO = investigatorDTO;
        this.partyDTO = partyDTO;
        this.centralContactDTO = centralContactDTO;
        this.overallStatusDTO = overallStatusDTO;
        this.arms = arms;
        this.eligibility = eligibility;
        this.outcomes = outcomes;
        this.collaborators = collaborators;
        this.document = document;
    }
    //CHECKSTYLE:ON
    /**
     * 
     * @return nctID
     */
    public String getNctID() {
        return nctID;
    }
    /**
     * 
     * @param nctID the nctID
     */
    public void setNctID(String nctID) {
        this.nctID = nctID;
    }
    /**
     * 
     * @return studyProtocolDTO
     */
    public StudyProtocolWebServiceDTO getStudyProtocolDTO() {
        return studyProtocolDTO;
    }
    /**
     * 
     * @param studyProtocolDTO studyProtocolDTO
     */
    public void setStudyProtocolDTO(StudyProtocolWebServiceDTO studyProtocolDTO) {
        this.studyProtocolDTO = studyProtocolDTO;
    }
    /**
     * 
     * @return leadOrgDTO
     */
    public OrganizationDTO getLeadOrgDTO() {
        return leadOrgDTO;
    }
    /**
     * 
     * @param leadOrgDTO the leadOrgDTO
     */
    public void setLeadOrgDTO(OrganizationDTO leadOrgDTO) {
        this.leadOrgDTO = leadOrgDTO;
    }
    /**
     * 
     * @return leadOrgID 
     */
    public StudySiteDTO getLeadOrgID() {
        return leadOrgID;
    }
    /**
     * 
     * @param leadOrgID the leadOrgID
     */
    public void setLeadOrgID(StudySiteDTO leadOrgID) {
        this.leadOrgID = leadOrgID;
    }
    /**
     * 
     * @return sponsorDTO
     */
    public OrganizationDTO getSponsorDTO() {
        return sponsorDTO;
    }
    /**
     * 
     * @param sponsorDTO sponsorDTO
     */
    public void setSponsorDTO(OrganizationDTO sponsorDTO) {
        this.sponsorDTO = sponsorDTO;
    }
    /**
     * 
     * @return investigatorDTO
     */
    public PersonDTO getInvestigatorDTO() {
        return investigatorDTO;
    }
    /**
     * 
     * @param investigatorDTO investigatorDTO
     */
    public void setInvestigatorDTO(PersonDTO investigatorDTO) {
        this.investigatorDTO = investigatorDTO;
    }
    /**
     * 
     * @return partyDTO
     */
    public ResponsiblePartyDTO getPartyDTO() {
        return partyDTO;
    }
    /**
     * 
     * @param partyDTO partyDTO
     */
    public void setPartyDTO(ResponsiblePartyDTO partyDTO) {
        this.partyDTO = partyDTO;
    }
    /**
     * 
     * @return centralContactDTO
     */
    public PersonDTO getCentralContactDTO() {
        return centralContactDTO;
    }
    /**
     * 
     * @param centralContactDTO centralContactDTO
     */
    public void setCentralContactDTO(PersonDTO centralContactDTO) {
        this.centralContactDTO = centralContactDTO;
    }
    /**
     * 
     * @return overallStatusDTO
     */
    public StudyOverallStatusDTO getOverallStatusDTO() {
        return overallStatusDTO;
    }
    /**
     * 
     * @param overallStatusDTO overallStatusDTO
     */
    public void setOverallStatusDTO(StudyOverallStatusDTO overallStatusDTO) {
        this.overallStatusDTO = overallStatusDTO;
    }
    /**
     * 
     * @return arms
     */
    public List<ArmDTO> getArms() {
        return arms;
    }
    /**
     * 
     * @param arms arms
     */
    public void setArms(List<ArmDTO> arms) {
        this.arms = arms;
    }
    /**
     * 
     * @return eligibility
     */
    public List<PlannedEligibilityCriterionDTO> getEligibility() {
        return eligibility;
    }
    /**
     * 
     * @param eligibility eligibility
     */
    public void setEligibility(List<PlannedEligibilityCriterionDTO> eligibility) {
        this.eligibility = eligibility;
    }
    /**
     * 
     * @return outcomes
     */
    public List<StudyOutcomeMeasureDTO> getOutcomes() {
        return outcomes;
    }
    /**
     * 
     * @param outcomes outcomes
     */
    public void setOutcomes(List<StudyOutcomeMeasureDTO> outcomes) {
        this.outcomes = outcomes;
    }
    /**
     * 
     * @return collaborators
     */
    public List<OrganizationDTO> getCollaborators() {
        return collaborators;
    }
    /**
     *  
     * @param collaborators collaborators
     */
    public void setCollaborators(List<OrganizationDTO> collaborators) {
        this.collaborators = collaborators;
    }
    /**
     * 
     * @return document
     */
    public DocumentDTO getDocument() {
        return document;
    }
    /**
     * 
     * @param document document
     */
    public void setDocument(DocumentDTO document) {
        this.document = document;
    }
}
