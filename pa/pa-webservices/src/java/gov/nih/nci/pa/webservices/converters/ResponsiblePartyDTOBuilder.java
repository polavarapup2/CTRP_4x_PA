/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.ResponsibleParty;
import gov.nih.nci.pa.webservices.types.ResponsiblePartyType;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import org.apache.commons.lang.StringUtils;

/**
 * @author dkrylov
 * 
 */
public class ResponsiblePartyDTOBuilder {

    private final PersonDTO trialInvestigator;
    private final OrganizationDTO trialSponsor;

    /**
     * @param principalInvestigatorDTO
     *            PersonDTO
     * @param sponsorOrgDTO
     *            OrganizationDTO
     */
    public ResponsiblePartyDTOBuilder(PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrgDTO) {
        this.trialInvestigator = principalInvestigatorDTO;
        this.trialSponsor = sponsorOrgDTO;
    }

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return ResponsiblePartyDTO
     * @throws PAException
     *             PAException
     * @throws TooManyResultsException
     *             TooManyResultsException
     * @throws NullifiedRoleException
     *             NullifiedRoleException
     * @throws NullifiedEntityException
     *             NullifiedEntityException
     */
    public ResponsiblePartyDTO build(BaseTrialInformation reg)
            throws NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        ResponsiblePartyDTO partyDTO = new ResponsiblePartyDTO();
        if (reg.isClinicalTrialsDotGovXmlRequired()
                && reg.getResponsibleParty() != null) {
            ResponsibleParty rp = reg.getResponsibleParty();
            if (ResponsiblePartyType.SPONSOR == rp.getType()) {
                partyDTO.setType(ResponsiblePartyDTO.ResponsiblePartyType.SPONSOR);
            } else if (ResponsiblePartyType.PRINCIPAL_INVESTIGATOR == rp
                    .getType()) {
                partyDTO.setType(ResponsiblePartyDTO.ResponsiblePartyType.PRINCIPAL_INVESTIGATOR);
                convertResponsiblePartyInvestigator(partyDTO, reg);
            } else if (ResponsiblePartyType.SPONSOR_INVESTIGATOR == rp
                    .getType()) {
                partyDTO.setType(ResponsiblePartyDTO.ResponsiblePartyType.SPONSOR_INVESTIGATOR);
                convertResponsiblePartyInvestigator(partyDTO, reg);
            }
        }
        return partyDTO;
    }

    private void convertResponsiblePartyInvestigator(
            ResponsiblePartyDTO partyDTO, BaseTrialInformation reg)
            throws NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {

        ResponsibleParty rp = reg.getResponsibleParty();

        PersonDTO investigator = trialInvestigator;
        OrganizationDTO affiliation = trialSponsor;

        if (ResponsiblePartyType.PRINCIPAL_INVESTIGATOR == rp.getType()
                && rp.getInvestigatorAffiliation() != null) {
            affiliation = new OrganizationDTOBuilder().build(rp
                    .getInvestigatorAffiliation());
        } else if (ResponsiblePartyType.SPONSOR_INVESTIGATOR == rp.getType()
                && rp.getInvestigator() != null) {
            investigator = new PersonDTOBuilder().build(rp.getInvestigator());
        }

        partyDTO.setAffiliation(affiliation);
        partyDTO.setInvestigator(investigator);
        partyDTO.setTitle(StringUtils.isBlank(rp.getInvestigatorTitle()) ? "Principal Investigator"
                : rp.getInvestigatorTitle());

    }
}
