/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.TrialDataException;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.RegulatoryInformation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * @author dkrylov
 * 
 */
public class StudyRegulatoryAuthorityDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return StudyRegulatoryAuthorityDTO
     * @throws PAException
     *             PAException
     */
    public StudyRegulatoryAuthorityDTO build(BaseTrialInformation reg)
            throws PAException {
        final RegulatoryInformation regInfo = reg.getRegulatoryInformation();
        if (regInfo == null) {
            return null;
        }

        StudyRegulatoryAuthorityDTO dto = new StudyRegulatoryAuthorityDTO();
        dto.setRegulatoryAuthorityIdentifier(IiConverter
                .convertToRegulatoryAuthorityIi(findRegulatoryAuthorityId(
                        regInfo.getCountry().value(),
                        regInfo.getAuthorityName())));
        return dto;

    }

    private Long findRegulatoryAuthorityId(final String countryCode,
            String authorityName) throws PAException {
        final RegulatoryInformationServiceLocal regService = PaRegistry
                .getRegulatoryInformationService();
        String countryName = ((CountryRegAuthorityDTO) CollectionUtils.find(
                regService.getDistinctCountryNames(), new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        CountryRegAuthorityDTO dto = (CountryRegAuthorityDTO) o;
                        return dto.getAlpha3().equals(countryCode);
                    }
                })).getName();
        Long id = regService.getRegulatoryAuthorityId(authorityName,
                countryName);
        if (id == null) {
            throw new TrialDataException(
                    "Unable to find a regulatory authority named "
                            + authorityName
                            + " in country "
                            + countryCode
                            + ". Authority name must be known to CTRP. "
                            + "The list of authorities known by CTRP is quite large and volatile;"
                            + "please contact CTRP Analysts or CTRO Office for details."
                            + " Failure to find a match in CTRP by name will cause"
                            + " the entire trial registration to fail.");
        }
        return id;
    }
}
