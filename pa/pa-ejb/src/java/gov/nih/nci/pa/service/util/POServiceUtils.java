/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.CTGovOrgMapping;
import gov.nih.nci.pa.domain.CTGovPersonMapping;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.AbstractPDQTrialServiceHelper.PersonWithFullNameDTO;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * Utility methods for communication with PO.
 * 
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings({ "unchecked", "PMD.TooManyMethods",
        "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public final class POServiceUtils {
    
    private POServiceUtils() {        
    }
    
    /**
     * For every PO object in the list, will attempt to find a match in PO
     * utilizing ctgov-to-pdq mapping tables (ctgov_org_map and ctgov_org_map)
     * if need be. If found, the existing PO object's ID will be applied to this
     * DTO to indicate the existence of the PO entity. If not found, a new PO
     * object will be created in Curation and its ID applied to this DTO.
     * 
     * @see https://tracker.nci.nih.gov/browse/PO-5935
     * @see https://tracker.nci.nih.gov/browse/PO-6368
     * @see CTRPSR-3-13 Requirement.
     * @param listOfObject
     *            List<PoDto>
     * @throws PAException
     *             PAException
     */
    public static void matchOrCreatePoObjects(List<? extends PoDto> listOfObject)
            throws PAException {
        for (PoDto poDto : listOfObject) {
            matchOrCreatePoObject(poDto);
        }
    }

    /**
     * @see #matchOrCreatePoObjects(List)
     * @param poDto PoDto
     * @throws PAException PAException
     */
    public static void matchOrCreatePoObject(PoDto poDto) throws PAException {
        try {
            if (poDto instanceof OrganizationDTO
                    && ISOUtil.isIiNull(((OrganizationDTO) poDto)
                            .getIdentifier())) {
                matchOrCreateInPO((OrganizationDTO) poDto);
            }
            if (poDto instanceof PersonDTO
                    && ISOUtil
                            .isIiNull(((PersonDTO) poDto).getIdentifier())) {
                matchOrCreateInPO((PersonDTO) poDto);
            }
        } catch (Exception e) {
            throw new PAException(e.getMessage(), e);
        }
    }

    private static void matchOrCreateInPO(PersonDTO person)
            throws NullifiedRoleException, PAException,
            TooManyResultsException, EntityValidationException,
            CurationException, NullifiedEntityException, IllegalAccessException, InvocationTargetException {
        
        final String firstName = EnPnConverter.getNamePart(person.getName(),
                EntityNamePartType.GIV, 0);
        final String lastName = EnPnConverter.getNamePart(person.getName(),
                EntityNamePartType.FAM);
        
        PersonDTO matchByName = findPersonInPoByFirstLastName(firstName, lastName);
        if (matchByName != null) {
            person.setIdentifier(matchByName.getIdentifier());
            return;
        }
        
        PersonDTO matchByMapping = findPersonInPoByMappingTables(person);
        if (matchByMapping != null) {
            person.setIdentifier(matchByMapping.getIdentifier());
            return;
        }
        
        person.setIdentifier(PoRegistry.getPersonEntityService().createPerson(
                clonePerson(person)));
    }

    private static PersonDTO clonePerson(PersonDTO person) throws IllegalAccessException, InvocationTargetException {
        PersonDTO clonedPerson = new PersonDTO();
        BeanUtils.copyProperties(clonedPerson, person);
        return clonedPerson;
    }

    private static PersonDTO findPersonInPoByMappingTables(PersonDTO person)
            throws NullifiedRoleException, PAException, TooManyResultsException, NullifiedEntityException {
        final String firstName = EnPnConverter.getNamePart(person.getName(),
                EntityNamePartType.GIV, 0);
        final String lastName = EnPnConverter.getNamePart(person.getName(),
                EntityNamePartType.FAM);
        // Full name at this point should be exactly how it came from ct.gov,
        // e.g.
        // <overall_official><last_name>Jeffrey Lyon, MD</last_name>...
        String fullname = person instanceof PersonWithFullNameDTO ? ((PersonWithFullNameDTO) person)
                .getFullName() : null;
        return findPersonInPoByMappingTables(firstName, lastName, fullname);
    }

    private static PersonDTO findPersonInPoByMappingTables(String firstName,
            String lastName, String fullname) throws NullifiedRoleException,
            PAException, TooManyResultsException, NullifiedEntityException {
        List<CTGovPersonMapping> mappings = findCtGovPersonMappings(firstName,
                lastName, fullname);
        for (CTGovPersonMapping mapping : mappings) {
            PersonDTO matchByMapping = findPersonInPoByMapping(mapping);
            if (matchByMapping != null) {
                return matchByMapping;
            }
        }
        return null;
    }

    private static PersonDTO findPersonInPoByMapping(CTGovPersonMapping mapping)
            throws NullifiedRoleException, PAException, TooManyResultsException, NullifiedEntityException {
        PersonDTO person = findPersonInPoByPoIds(mapping.getPoId());
        if (person == null) {
            person = findPersonInPoByCtepIds(mapping.getCtepId());
            if (person == null) {
                person = findPersonInPoByFirstLastName(mapping.getPdqPerson()
                        .getFirstName(), mapping.getPdqPerson().getLastName());
            }
        }
        return person;
    }

    private static List<CTGovPersonMapping> findCtGovPersonMappings(String firstName,
            String lastName, String fullname) {
        List<CTGovPersonMapping> list = new ArrayList<CTGovPersonMapping>();
        Session s = PaHibernateUtil.getCurrentSession();

        if (isNotBlank(fullname)) {
            list.addAll(s
                    .createCriteria(CTGovPersonMapping.class)
                    .add(Restrictions.ilike("ctgovPerson.fullName", fullname,
                            MatchMode.EXACT)).list());
        }
        if (isNotBlank(firstName) && isNotBlank(lastName)) {
            list.addAll(s
                    .createCriteria(CTGovPersonMapping.class)
                    .add(Restrictions.ilike("ctgovPerson.firstName", firstName,
                            MatchMode.EXACT))
                    .add(Restrictions.ilike("ctgovPerson.lastName", lastName,
                            MatchMode.EXACT)).list());
            if (list.isEmpty()) {
                list.addAll(s
                        .createCriteria(CTGovPersonMapping.class)
                        .add(Restrictions.ilike("pdqPerson.firstName",
                                firstName, MatchMode.EXACT))
                        .add(Restrictions.ilike("pdqPerson.lastName", lastName,
                                MatchMode.EXACT)).list());
            }
        }

        return list;
    }

    /**
     * @param firstName
     * @param lastName
     * @param matchByName
     * @return
     * @throws PAException
     * @throws TooManyResultsException
     * @throws NullifiedRoleException
     */
    private static PersonDTO findPersonInPoByFirstLastName(final String firstName,
            final String lastName) throws PAException, TooManyResultsException,
            NullifiedRoleException {
        PersonSearchCriteriaDTO criteria = new PersonSearchCriteriaDTO();
        criteria.setFirstName(firstName);
        criteria.setLastName(lastName);
        List<PaPersonDTO> persons = PADomainUtils.searchPoPersons(criteria);
        for (PaPersonDTO paPerson : persons) {
            if (equalsIgnoreCase(paPerson.getFirstName(), firstName)
                    && equalsIgnoreCase(paPerson.getLastName(),
                            lastName)) {
                return findPersonInPoByPoId(paPerson.getId().toString());
            }
        }
        return null;
    }

    private static void matchOrCreateInPO(OrganizationDTO org)
            throws NullifiedRoleException, NullifiedEntityException,
            TooManyResultsException, PAException, EntityValidationException,
            CurationException {

        final EnOn name = org.getName();
        // Try direct match in PO first.
        OrganizationDTO exactMatchInPO = findOrgInPoByName(name);
        if (exactMatchInPO != null) {
            org.setIdentifier(exactMatchInPO.getIdentifier());
            return;
        }

        // Not found. Now try using the mapping tables.
        OrganizationDTO matchWithMappingTables = findOrgInPoByNameAndMappingTables(name);
        if (matchWithMappingTables != null) {
            org.setIdentifier(matchWithMappingTables.getIdentifier());
            return;
        }
        // All fail. Create one.
        org.setIdentifier(PoRegistry.getOrganizationEntityService()
                .createOrganization(org));

    }

    private static OrganizationDTO findOrgInPoByNameAndMappingTables(EnOn name)
            throws NullifiedRoleException, NullifiedEntityException,
            TooManyResultsException, PAException {
        final String orgName = EnOnConverter.convertEnOnToString(name);
        List<CTGovOrgMapping> mappings = findCtGovOrgMappings(orgName);
        for (CTGovOrgMapping mapping : mappings) {
            OrganizationDTO matchByMapping = findOrgInPoByMapping(mapping);
            if (matchByMapping != null) {
                return matchByMapping;
            }
        }
        return null;
    }

    private static OrganizationDTO findOrgInPoByMapping(CTGovOrgMapping mapping)
            throws NullifiedRoleException, NullifiedEntityException,
            TooManyResultsException, PAException {
        OrganizationDTO org = findOrgInPoByPoIds(mapping.getPoId());
        if (org == null) {
            org = findOrgInPoByCtepIds(mapping.getCtepId());
            if (org == null) {
                org = findOrgInPoByName(mapping.getPdqOrg().getName());
            }
        }
        return org;
    }

    private static OrganizationDTO findOrgInPoByCtepIds(String ctepIds)
            throws NullifiedRoleException, NullifiedEntityException,
            TooManyResultsException, PAException {
        OrganizationDTO org = null;
        if (isNotBlank(ctepIds)) {
            for (String ctepid : ctepIds.split(";")) {
                org = findOrgInPoByCtepId(ctepid);
                if (org != null) {
                    break;
                }
            }
        }
        return org;
    }
    
    private static PersonDTO findPersonInPoByCtepIds(String ctepIds)
            throws NullifiedRoleException, NullifiedEntityException,
            TooManyResultsException, PAException {
        PersonDTO p = null;
        if (isNotBlank(ctepIds)) {
            for (String ctepid : ctepIds.split(";")) {
                p = findPersonInPoByCtepId(ctepid);
                if (p != null) {
                    break;
                }
            }
        }
        return p;
    }

    private static PersonDTO findPersonInPoByCtepId(String ctepid)
            throws NullifiedRoleException, PAException, TooManyResultsException {
        PersonSearchCriteriaDTO criteria = new PersonSearchCriteriaDTO();
        criteria.setCtepId(ctepid);
        List<PaPersonDTO> orgList = PADomainUtils.searchPoPersons(criteria);
        // PO will do a LIKE search by CTEP ID, not an exact one. So we have to
        // iterate over the results
        // and make sure we do have an exact match on CTEP ID.
        for (PaPersonDTO searchResult : orgList) {
            String foundCtepId = searchResult.getCtepId();
            if (ctepid.equals(foundCtepId)) {
                return findPersonInPoByPoId(searchResult.getId().toString());
            }
        }
        return null;
    }

    private static OrganizationDTO findOrgInPoByCtepId(final String ctepid)
            throws NullifiedRoleException, NullifiedEntityException,
            TooManyResultsException, PAException {
        OrganizationSearchCriteriaDTO criteria = new OrganizationSearchCriteriaDTO();
        criteria.setCtepId(ctepid);
        List<OrganizationDTO> orgList = PADomainUtils
                .searchPoOrganizations(criteria);
        // PO will do a LIKE search by CTEP ID, not an exact one. So we have to
        // iterate over the results
        // and make sure we do have an exact match on CTEP ID.
        for (OrganizationDTO searchResult : orgList) {
            String foundCtepId = findCtepId(searchResult);
            if (ctepid.equals(foundCtepId)) {
                return searchResult;
            }
        }
        return null;
    }

    private static String findCtepId(OrganizationDTO org)
            throws NullifiedRoleException {
        List<IdentifiedOrganizationDTO> identifiedOrgs = PoRegistry
                .getIdentifiedOrganizationEntityService()
                .getCorrelationsByPlayerIds(new Ii[] {org.getIdentifier() }); // NOPMD
        for (IdentifiedOrganizationDTO idOrgDTO : identifiedOrgs) {
            if (IiConverter.CTEP_ORG_IDENTIFIER_ROOT.equals(idOrgDTO
                    .getAssignedId().getRoot())) {
                return idOrgDTO.getAssignedId().getExtension();
            }
        }
        return null;
    }

    private static OrganizationDTO findOrgInPoByPoIds(String poIds) {
        OrganizationDTO org = null;
        if (isNotBlank(poIds)) {
            for (String poid : poIds.split(";")) {
                org = findOrgInPoByPoId(poid);
                if (org != null) {
                    break;
                }
            }
        }
        return org;
    }
    
    private static PersonDTO findPersonInPoByPoIds(String poIds) {
        PersonDTO p = null;
        if (isNotBlank(poIds)) {
            for (String poid : poIds.split(";")) {
                p = findPersonInPoByPoId(poid);
                if (p != null) {
                    break;
                }
            }
        }
        return p;
    }

    private static OrganizationDTO findOrgInPoByPoId(String poid) {
        return new PAServiceUtils().getPOOrganizationEntity(IiConverter
                .convertToPoOrganizationIi(poid));
    }
    
    private static PersonDTO findPersonInPoByPoId(String poid) {
        return new PAServiceUtils().getPoPersonEntity(IiConverter
                .convertToPoPersonIi(poid));
    }

    private static List<CTGovOrgMapping> findCtGovOrgMappings(String ctgovName) {
        Session s = PaHibernateUtil.getCurrentSession();
        Criteria c = s.createCriteria(CTGovOrgMapping.class)
                .add(Restrictions.ilike("ctgovOrg.name", ctgovName,
                        MatchMode.EXACT));
        return c.list();
    }

    /**
     * @param name
     * @return
     * @throws TooManyResultsException
     * @throws NullifiedRoleException
     * @throws NullifiedEntityException
     * @throws PAException
     */
    private static OrganizationDTO findOrgInPoByName(final EnOn name)
            throws TooManyResultsException, NullifiedRoleException,
            NullifiedEntityException, PAException {
        final String orgName = EnOnConverter.convertEnOnToString(name);
        return findOrgInPoByName(orgName);
    }

    /**
     * @param orgName
     * @return
     * @throws TooManyResultsException
     * @throws NullifiedRoleException
     * @throws NullifiedEntityException
     * @throws PAException
     */
    private static OrganizationDTO findOrgInPoByName(final String orgName)
            throws TooManyResultsException, NullifiedRoleException,
            NullifiedEntityException, PAException {
        OrganizationDTO exactMatchInPO = null;
        OrganizationSearchCriteriaDTO criteria = new OrganizationSearchCriteriaDTO();

        criteria.setName(orgName);
        List<OrganizationDTO> orgList = PADomainUtils
                .searchPoOrganizations(criteria);

        for (OrganizationDTO searchResult : orgList) {
            String foundName = EnOnConverter.convertEnOnToString(searchResult
                    .getName());
            if (isNotBlank(foundName)
                    && foundName.equalsIgnoreCase(orgName)) {
                exactMatchInPO = searchResult;
                break;
            }
        }
        return exactMatchInPO;
    }

}
