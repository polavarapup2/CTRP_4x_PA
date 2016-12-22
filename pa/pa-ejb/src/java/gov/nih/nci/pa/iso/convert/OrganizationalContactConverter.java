package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author NAmiruddin
 *
 */
public class OrganizationalContactConverter
    extends AbstractPoConverter <PAOrganizationalContactDTO , OrganizationalContactDTO ,  OrganizationalContact > {

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationalContact convertToDomain(
            PAOrganizationalContactDTO dto , Organization org , Person per) throws PAException {

        OrganizationalContact oc = new OrganizationalContact();
        oc.setPerson(per);
        oc.setOrganization(org);
        oc.setStatusCode(StructuralRoleStatusCode.PENDING);
        return oc;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationalContactDTO convertFromPADtoToPoDto(PAOrganizationalContactDTO dto)
            throws PAException {
        OrganizationalContactDTO poOcDto = null;
        if (dto != null) {
            poOcDto = new OrganizationalContactDTO();
            if (!ISOUtil.isIiNull(dto.getPersonIdentifier())) {
                poOcDto.setPlayerIdentifier(dto.getPersonIdentifier());
            }
            poOcDto.setScoperIdentifier(dto.getOrganizationIdentifier());
            poOcDto.setTitle(StConverter.convertToSt(dto.getTitle()));
            if (!ISOUtil.isIiNull(dto.getIdentifier())) {
             Ii ii = dto.getIdentifier();
             ii.setReliability(IdentifierReliability.ISS);
             poOcDto.setIdentifier(DSetConverter.convertIiToDset(ii));
            }
            if (StringUtils.isNotEmpty(dto.getTypeCode())) {
                poOcDto.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
            }
        }
        return poOcDto;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationalContact convertFromPODtoToPADto(
            OrganizationalContactDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }


}
