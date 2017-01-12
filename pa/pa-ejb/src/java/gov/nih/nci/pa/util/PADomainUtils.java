/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.PaOrganizationContactInfoDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.NonInterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils.Closure;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.AbstractRoleDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;

/**
 * This set of utils handles conversions, etc for domain objects. It cannot be used in any of the grid services.
 * Use PAIsoUtils if you need utilities to use in the grid services.
 *
 * @author Abraham J. Evans-EL
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CyclomaticComplexity",
        "PMD.ExcessiveClassLength" })
public class PADomainUtils {
    private static final String CDR_ID_PREFIX = "CDR";
    /**
     * The code for a participating site.
     */
    public static final String PARTICIPATING_SITE = "Participating Site";
    /**
     * The code for a Lead Organization
     */
    public static final String LEAD_ORGANIZATION = "Lead Organization";
    /**
     * The code for a Healthcare Facility
     */
    public static final String HEALTHCARE_FACILITY = "Healthcare Facility";
    /**
     * The code for a Research Organization
     */
    public static final String RESEARCH_ORGANIZATION = "Research Organization";
    private static final Logger LOG = Logger.getLogger(PADomainUtils.class);
    private static final int EMAIL_IDX = 7;

    /**
     *
     * @param poPerson as arg
     * @return Person as pa object
     */
    public static Person convertToPaPerson(PersonDTO poPerson) {
        Person person = new Person();
        List<Enxp> list = poPerson.getName().getPart();
        Iterator<Enxp> ite = list.iterator();
        while (ite.hasNext()) {
            Enxp part = ite.next();
            if (EntityNamePartType.FAM == part.getType()) {
                person.setLastName(part.getValue());
            } else if (EntityNamePartType.GIV == part.getType()) {
                if (person.getFirstName() == null) {
                    person.setFirstName(part.getValue());
                } else {
                    person.setMiddleName(part.getValue());
                }
            }
        }
        return person;
    }

    /**
     *
     * @param poPerson as arg
     * @return PaPersonDTO as pa DTO object
     */
    public static PaPersonDTO convertToPaPersonDTO(PersonDTO poPerson) {
        gov.nih.nci.pa.dto.PaPersonDTO personDTO = new gov.nih.nci.pa.dto.PaPersonDTO();
        personDTO.setId(Long.valueOf(poPerson.getIdentifier().getExtension()));
        personDTO.setStatus(poPerson.getStatusCode() != null
                && poPerson.getStatusCode().getCode() != null ? poPerson
                .getStatusCode().getCode().toUpperCase() : "");
        personDTO.setStatusDate(TsConverter.convertToString(poPerson.getStatusDate()));
        final EnPn personName = poPerson.getName();
        setPersonName(personDTO, personName);
        
        //TelEmail; I need only EMAIL, So I am not using the DSETCONVERTER Class
        DSet<Tel> telList = poPerson.getTelecomAddress();
        setPersonContactInfo(personDTO, telList);
        
        //Populate the address information now!
        final Ad address = poPerson.getPostalAddress();
        setPersonAddress(personDTO, address);
        return personDTO;
    }

    /**
     * @param personDTO
     * @param address
     */
    private static void setPersonAddress(
            gov.nih.nci.pa.dto.PaPersonDTO personDTO, final Ad address) {
        personDTO.setStreetAddress(null);
        personDTO.setStreetAddress2(null);
        personDTO.setState(null);
        personDTO.setCity(null);
        personDTO.setCountry(null);
        personDTO.setZip(null);
        
        List<Adxp> addressList = address.getPart();
        Iterator<Adxp> addressIte = addressList.iterator();
        while (addressIte.hasNext()) {
            Adxp part = addressIte.next();
            if (AddressPartType.AL == part.getType()) {
                personDTO.setStreetAddress(part.getValue());
            }
            if (AddressPartType.ADL == part.getType()) {
                personDTO.setStreetAddress2(part.getValue());
            }            
            if (AddressPartType.STA == part.getType()) {
                personDTO.setState(part.getValue());
            }
            if (AddressPartType.CTY == part.getType()) {
                personDTO.setCity(part.getValue());
            }
            if (AddressPartType.CNT == part.getType()) {
                personDTO.setCountry(part.getCode());
            }
            if (AddressPartType.ZIP == part.getType()) {
                personDTO.setZip(part.getValue());
            }
        }
    }

    /**
     * @param personDTO
     * @param telList
     */
    private static void setPersonContactInfo(
            gov.nih.nci.pa.dto.PaPersonDTO personDTO, DSet<Tel> telList) {
        if (telList != null) {
            personDTO.setEmail(null);
            personDTO.setUrl(null);

            Set<Tel> set = telList.getItem();
            Iterator<Tel> iter = set.iterator();
            while (iter.hasNext()) {
                Tel obj = iter.next();
                if (obj instanceof TelEmail) {
                    personDTO.setEmail(((TelEmail) obj).getValue().toString()
                            .substring(EMAIL_IDX));
                }
                if (obj instanceof TelUrl) {
                    personDTO.setUrl(obj.getValue().toString());
                }
            }

            final List<String> phones = DSetConverter.getTelByType(telList,
                    TelPhone.SCHEME_TEL + ":");
            final List<String> faxes = DSetConverter.getTelByType(telList,
                    TelPhone.SCHEME_X_TEXT_FAX + ":");
            personDTO.setPhone(CollectionUtils.isNotEmpty(phones) ? phones
                    .get(0) : null);
            personDTO.setFax(CollectionUtils.isNotEmpty(faxes) ? faxes.get(0)
                    : null);
        }
    }

    /**
     * @param personDTO
     * @param personName
     */
    private static void setPersonName(gov.nih.nci.pa.dto.PaPersonDTO personDTO,
            final EnPn personName) {
        List<Enxp> nameList = personName.getPart();
        Iterator<Enxp> nameIte = nameList.iterator();
        while (nameIte.hasNext()) {
            Enxp part = nameIte.next();
            if (EntityNamePartType.PFX == part.getType()) {
                personDTO.setPreFix(part.getValue());
            } else if (EntityNamePartType.SFX == part.getType()) {
                personDTO.setSuffix(part.getValue());
            }
            if (EntityNamePartType.FAM == part.getType()) {
                personDTO.setLastName(part.getValue());
            } else if (EntityNamePartType.GIV == part.getType()) {
                if (personDTO.getFirstName() == null) {
                    personDTO.setFirstName(part.getValue());
                } else {
                    personDTO.setMiddleName(part.getValue());
                }
            }
        }
    }

    /**
     * @return OrganizationWebDTO for display.
     * @param poOrgDto
     *            OrganizationDTO
     * @param countryList
     *            CountryList
     * @throws PAException
     *             on error
     */
    public static PaOrganizationDTO convertPoOrganizationDTO(
            OrganizationDTO poOrgDto, List<Country> countryList)
            throws PAException {
        PaOrganizationDTO paOrgDTO = null;
        paOrgDTO = new PaOrganizationDTO();
        paOrgDTO.setId(poOrgDto.getIdentifier().getExtension().toString());
        paOrgDTO.setName(poOrgDto.getName().getPart().get(0).getValue());
        paOrgDTO.setStatus(poOrgDto.getStatusCode() != null
                && poOrgDto.getStatusCode().getCode() != null ? poOrgDto
                .getStatusCode().getCode().toUpperCase() : null);
        paOrgDTO.setStatusDate(TsConverter.convertToString(poOrgDto.getStatusDate()));
        final Ad postalAddress = poOrgDto.getPostalAddress();
        setPostalAddressFields(countryList, paOrgDTO, postalAddress);

        final DSet<Tel> telecomAddr = poOrgDto.getTelecomAddress();
        setContactInfoFields(paOrgDTO, telecomAddr);
        return paOrgDTO;
    }

    /**
     * @param paOrgDTO
     * @param telecomAddr
     */
    private static void setContactInfoFields(PaOrganizationDTO paOrgDTO,
            final DSet<Tel> telecomAddr) {
        if (telecomAddr != null) {
            final PaOrganizationContactInfoDTO contactInfo = new PaOrganizationContactInfoDTO();
            paOrgDTO.setContactInfo(contactInfo);
            for (Tel tel : telecomAddr.getItem()) {
                if (tel instanceof TelUrl) {
                    contactInfo.getWebsites().add(tel.getValue().toString());
                }
            }
            contactInfo.getEmails().addAll(
                    DSetConverter.getTelByType(telecomAddr,
                            TelEmail.SCHEME_MAILTO + ":"));
            contactInfo.getPhones().addAll(
                    DSetConverter.getTelByType(telecomAddr, TelPhone.SCHEME_TEL
                            + ":"));
            contactInfo.getFaxes().addAll(
                    DSetConverter.getTelByType(telecomAddr,
                            TelPhone.SCHEME_X_TEXT_FAX + ":"));
        }
    }

    /**
     * @param countryList List<Country>
     * @param paOrgDTO PaOrganizationDTO
     * @param postalAddress postalAddress
     */
    public static void setPostalAddressFields(List<Country> countryList, // NOPMD
            PaOrganizationDTO paOrgDTO, final Ad postalAddress) {
        paOrgDTO.setAddress1(null);
        paOrgDTO.setAddress2(null);
        paOrgDTO.setState(null);
        paOrgDTO.setCity(null);
        paOrgDTO.setCountry(null);
        paOrgDTO.setZip(null);
        
        List<Adxp> addressList = postalAddress.getPart();
        Iterator<Adxp> addressIte = addressList.iterator();
        while (addressIte.hasNext()) {
            Adxp part = addressIte.next();
            if (AddressPartType.AL == part.getType()) {
                paOrgDTO.setAddress1(part.getValue());
            }
            if (AddressPartType.ADL == part.getType()) {
                paOrgDTO.setAddress2(part.getValue());
            }
            if (AddressPartType.STA == part.getType()) {
                paOrgDTO.setState(part.getValue());
            }
            if (AddressPartType.CTY == part.getType()) {
                paOrgDTO.setCity(part.getValue());
            }
            if (AddressPartType.CNT == part.getType()) {
                if (countryList != null) {
                    paOrgDTO.setCountry(getCountryNameUsingCode(part.getCode(), countryList));
                } else {
                    paOrgDTO.setCountry(part.getCode());
                }
            }
            if (AddressPartType.ZIP == part.getType()) {
                paOrgDTO.setZip(part.getValue());
            }
        }
    }

    /**
     * Check if value exists.
     *
     * @param value the value
     * @param tableName the table name
     * @param column the column
     *
     * @return true, if successful
     *
     * @throws PAException the PA exception
     */
    @SuppressFBWarnings
    public static boolean checkIfValueExists(String value, String tableName, String column) throws PAException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = '" + value + "'";
        Session session = null;
        boolean exists = false;
        int count = 0;
        try {
            session = PaHibernateUtil.getCurrentSession();
            Statement st = session.connection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                exists = true;
            }

        }  catch (SQLException sqle) {
            LOG.error("Hibernate exception while checking for value " + value + " from table " + tableName , sqle);
        }
        return exists;
    }

    /**
     * Check assigned identifier exists domain.
     *
     * @param sp the sp
     *
     * @return true, if successful
     */
    public static boolean checkAssignedIdentifier(StudyProtocol sp) {
        boolean assignedIdentifierExists = false;
        if (sp.getOtherIdentifiers() != null && !sp.getOtherIdentifiers().isEmpty()) {
            for (Ii spSecId : sp.getOtherIdentifiers()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(spSecId.getRoot())) {
                    return true;
                }
            }
        }
        return assignedIdentifierExists;
    }

    /**
     * Gets the assigned identifier.
     *
     * @param sp the sp
     *
     * @return the assigned identifier
     */
    public static String getAssignedIdentifierExtension(StudyProtocol sp) {
        String assignedIdentifier = "";
        if (sp.getOtherIdentifiers() != null && !sp.getOtherIdentifiers().isEmpty()) {
            for (Ii spSecId : sp.getOtherIdentifiers()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(spSecId.getRoot())) {
                    return spSecId.getExtension();
                }
            }
        }
        return assignedIdentifier;
    }

    /**
     * Extract lead org sp id from study protocol.
     * @param sp trial
     * @return lead org id.
     * @throws PAException on error.
     */
    public static String getLeadOrgSpId(StudyProtocol sp) throws PAException {
        String returnVal = null;
        if (CollectionUtils.isNotEmpty(sp.getStudySites())) {
            for (StudySite ss : sp.getStudySites()) {
                if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(ss.getFunctionalCode())) {
                    returnVal = ss.getLocalStudyProtocolIdentifier();
                }
            }
        }

        if (returnVal == null) {
            throw new PAException("Trial with id " + sp.getId() + " is missing a Lead Org Id");
        }
        return returnVal;
    }

    /**
     * Returns a listing of a study protocols other identifier extensions.
     * @param sp the study protocol to get the other identifier extensions for
     * @return the other identifier extensions.
     */
    public static List<String> getOtherIdentifierExtensions(StudyProtocol sp) {
        List<String> results = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(sp.getOtherIdentifiers())) {
            for (Ii id : sp.getOtherIdentifiers()) {
                if (StringUtils.equals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, id.getRoot())) {
                    results.add(id.getExtension());
                }
            }
        }
        return results;
    }
    
    /**
     * Returns trial's CDR ID. 
     * 
     * @param sp
     *            the study protocol to get the other identifier extensions for
     * @return trial's CDR ID. 
     */
    public static String getCDRId(StudyProtocol sp) {
        if (CollectionUtils.isNotEmpty(sp.getOtherIdentifiers())) {
            for (Ii id : sp.getOtherIdentifiers()) {
                if (StringUtils.startsWith(id.getExtension(), CDR_ID_PREFIX)) {
                    return id.getExtension();
                }
            }
        }
        return null;
    }    

    /**
     * Returns a listing of a disease names associated with a studyProtocol.
     * @param sp the study protocol to get the diseases for
     * @return the disease names.
     */
    public static Set<String> getDiseaseNames(StudyProtocol sp) {
        Set<String> results = new TreeSet<String>();
        for (StudyDisease sd : sp.getStudyDiseases()) {
                results.add(sd.getDisease().getPreferredName());
        }
        return results;
    }

    /**
     * Returns a listing of a intervention type names associated with a studyProtocol.
     * @param sp the study protocol to get the intervention types for
     * @return the intervention type names.
     */
    public static Set<String> getInterventionTypes(StudyProtocol sp) {
        Set<String> results = new TreeSet<String>();
        for (PlannedActivity pa : sp.getPlannedActivities()) {
            if (pa.getIntervention() != null && pa.getIntervention().getTypeCode() != null) {
                results.add(pa.getIntervention().getTypeCode().getCode());
            }
        }
        return results;
    }

    private static String getCountryNameUsingCode(String code, List<Country> countryList) {
        for (int i = 0; i < countryList.size(); i++) {
            Country country = countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }
    /**
     *
     * @param code Alpha3 countryCode
     * @return Country name
     */
    public static String getCountryNameUsingAlpha3Code(String code) {
        String countryName = "";
        if (StringUtils.isEmpty(code)) {
            return countryName;
        }
        Country criteriaCountry = null;
        try {
            criteriaCountry = new Country();
            criteriaCountry.setAlpha3(code);
            List<Country> countryList = PaRegistry.getLookUpTableService().searchCountry(criteriaCountry);
            if (CollectionUtils.isNotEmpty(countryList)) {
                countryName =  (countryList.get(0)).getName();
           }
        } catch (PAException e) {
            countryName = "";
        }
        return countryName;
    }

    /**
     * Creates a research organization example object with an organization with the given name.
     * @param orgName the name of the organization to create the research organization with
     * @return the research organization with an organization with the given name
     */
    public static ResearchOrganization createROExampleObjectByOrgName(String orgName) {
        Organization org = new Organization();
        org.setName(orgName);
        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(org);
        return ro;
    }

    /**
     * Search PO organizations by a broad criteria.
     * @param searchCriteria criteria
     * @return PO org list.
     * @throws TooManyResultsException when too many exceptions.
     * @throws NullifiedRoleException  NullifiedRoleException
     * @throws NullifiedEntityException NullifiedEntityException
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    public static List<OrganizationDTO> searchPoOrganizations(
            OrganizationSearchCriteriaDTO searchCriteria)
            // NOPMD
            throws TooManyResultsException, NullifiedRoleException,
            NullifiedEntityException, PAException {

        validateSearchCriteria(searchCriteria);
        
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> results = convertToEnhancedOrgDTO(PoRegistry
                .getOrganizationEntityService().search(searchCriteria, limit));

        return results;
    }

    /**
     * @param orgSearchCriteria
     * @throws PAException
     */
    private static void validateSearchCriteria(
            OrganizationSearchCriteriaDTO orgSearchCriteria) throws PAException {
        if (isEmptyCriteria(orgSearchCriteria)) {
            throw new PAException("At least one criterion must be provided");
        }
        if (StringUtils.isNotBlank(orgSearchCriteria.getIdentifier())
                && !NumberUtils.isDigits(orgSearchCriteria.getIdentifier())) {
            throw new PAException("PO ID must be numerical");
        }
    }

    /**
     * @param orgs
     * @return
     */
    private static List<OrganizationDTO> convertToEnhancedOrgDTO(
            List<OrganizationDTO> orgs) {
        List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
        for (OrganizationDTO orgDTO : orgs) {
            list.add(new EnhancedOrganizationDTO(orgDTO));
        }
        return list;
    }

    private static boolean isEmptyCriteria(OrganizationSearchCriteriaDTO criteria) {
        return StringUtils.isBlank(criteria.getCtepId())
                && isEmptyCoreCriteria(criteria)
                && StringUtils.isBlank(criteria.getFunctionalRole());
    }

    private static boolean isEmptyCoreCriteria(OrganizationSearchCriteriaDTO criteria) {
        return StringUtils.isBlank(criteria.getIdentifier())
                && StringUtils.isBlank(criteria.getStatus())
                && StringUtils.isBlank(criteria.getName())
                && StringUtils.isBlank(criteria.getFamilyName())
                && StringUtils.isBlank(criteria.getCountry())
                && StringUtils.isBlank(criteria.getCity())
                && StringUtils.isBlank(criteria.getState())
                && StringUtils.isBlank(criteria.getZip());
    }
    
    /**
     * Search PO organizations by name, address or ctep id.
     * @param orgSearchCriteria criteria
     * @return PO org list.
     * @throws TooManyResultsException when too many exceptions.
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static List<OrganizationDTO> orgSearchByNameAddressCtepId(PaOrganizationDTO orgSearchCriteria)
            throws TooManyResultsException {

        OrganizationDTO criteria = new OrganizationDTO();
        if (StringUtils.isNotBlank(orgSearchCriteria.getCtepId())) {
            IdentifiedOrganizationDTO identifiedOrganizationDTO = new IdentifiedOrganizationDTO();
            identifiedOrganizationDTO.setAssignedId(IiConverter
                    .convertToIdentifiedOrgEntityIi(orgSearchCriteria.getCtepId()));
            List<IdentifiedOrganizationDTO> identifiedOrgs = PoRegistry
                    .getIdentifiedOrganizationEntityService().search(identifiedOrganizationDTO);
            if (CollectionUtils.isNotEmpty(identifiedOrgs)) {
                criteria.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
            }
        } else {
            criteria.setName(EnOnConverter.convertToEnOn(orgSearchCriteria.getName()));
            criteria.setPostalAddress(AddressConverterUtil.create(
                    null, null, orgSearchCriteria.getCity(), orgSearchCriteria.getState(),
                        orgSearchCriteria.getZip(), orgSearchCriteria.getCountry()));
        }
        
        List<OrganizationDTO> orgSearchResults = new ArrayList<OrganizationDTO>();
        if (criteria.getIdentifier() != null
                || criteria.getName() != null
                || criteria.getPostalAddress() != null
                || StringUtils.isNotEmpty(orgSearchCriteria.getFamilyName())) {
            LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
            orgSearchResults = PoRegistry.getOrganizationEntityService().search(criteria,
                    EnOnConverter.convertToEnOn(orgSearchCriteria.getFamilyName()), limit);
        }
        return orgSearchResults;
    }
    

    /**
     * Gets families.
     * @param familyOrganizationRelationships DSet<Ii>
     * @param familyMap Map<Ii, FamilyDTO>
     * @return Map<Long, String>
     */
    public static Map<Long, String> getFamilies(
            DSet<Ii> familyOrganizationRelationships,
            Map<Ii, FamilyDTO> familyMap) {
        Map<Long, String> retMap = new HashMap<Long, String>();
        Set<Ii> famOrgIis = familyOrganizationRelationships.getItem();
        for (Ii ii : famOrgIis) {
            FamilyDTO dto = familyMap.get(ii);
            retMap.put(IiConverter.convertToLong(dto.getIdentifier()),
                    EnOnConverter.convertEnOnToString(dto.getName()));
        }
        return retMap;
    }
    
    /**
     * {@link OrganizationDTO} with equals and hashcode and ctepId.
     * @author Denis G. Krylov
     *
     */
    private static final class EnhancedOrganizationDTO extends // NOPMD
            OrganizationDTO {

        /**
         * 
         */
        private static final long serialVersionUID = 1406045576246541515L;

        public EnhancedOrganizationDTO(OrganizationDTO organizationDTO) {
            try {
                PropertyUtils.copyProperties(this, organizationDTO);
            } catch (Exception e) {
                LOG.error(e, e);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((getIdentifier() == null) ? 0 : getIdentifier().hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) { // NOPMD
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof OrganizationDTO)) {
                return false;
            }
            OrganizationDTO other = (OrganizationDTO) obj;
            if (getIdentifier() == null) {
                if (other.getIdentifier() != null) {
                    return false;
                }
            } else if (!getIdentifier().equals(other.getIdentifier())) {
                return false;
            }
            return true;
        }

    }

    /**
     * Retrieve ctep IDs.
     * 
     * @param paOrgs
     *            List<PaOrganizationDTO>
     * @throws NullifiedRoleException NullifiedRoleException
     */
    public static void addOrganizationCtepIDs(List<PaOrganizationDTO> paOrgs) // NOPMD
            throws NullifiedRoleException {
        if (CollectionUtils.isEmpty(paOrgs)) {
            return;
        }
        
        List<Ii> ids = new ArrayList<Ii>();
        for (PaOrganizationDTO organizationDTO : paOrgs) {
            ids.add(IiConverter.convertToPoOrganizationIi(organizationDTO
                    .getId()));
        }
        List<IdentifiedOrganizationDTO> identifiedOrgs = PoRegistry
                .getIdentifiedOrganizationEntityService()
                .getCorrelationsByPlayerIds(ids.toArray(new Ii[0])); // NOPMD
        for (IdentifiedOrganizationDTO idOrgDTO : identifiedOrgs) {
            if (IiConverter.CTEP_ORG_IDENTIFIER_ROOT.equals(idOrgDTO
                    .getAssignedId().getRoot())) {
                String ctepID = idOrgDTO.getAssignedId().getExtension();
                for (PaOrganizationDTO organizationDTO : paOrgs) {
                    if (organizationDTO.getId().equals(
                            idOrgDTO.getPlayerIdentifier().getExtension())) {
                        organizationDTO.setCtepId(ctepID);
                    }
                }
            }
        }
    }

    /**
     * Iterates over organizations in the list and determines/sets their type.
     * 
     * @param orgs
     *            List<PaOrganizationDTO>
     * @throws NullifiedRoleException NullifiedRoleException
     */
    public static void determineOrganizationType(List<PaOrganizationDTO> orgs)
            throws NullifiedRoleException {
        for (PaOrganizationDTO orgDTO : orgs) {
            Ii[] playerIds = new Ii[] {IiConverter
                    .convertToPoOrganizationIi(orgDTO.getId()) };
            ResearchOrganizationCorrelationServiceRemote researchOrganizationService = PoRegistry
                    .getResearchOrganizationCorrelationService();
            if (!researchOrganizationService.getCorrelationsByPlayerIds(
                    playerIds).isEmpty()) {
                orgDTO.getOrganizationTypes().add(LEAD_ORGANIZATION);
                orgDTO.setFunctionalRole(LEAD_ORGANIZATION);
            }
            HealthCareFacilityCorrelationServiceRemote hfRemote = PoRegistry
                    .getHealthCareFacilityCorrelationService();
            if (!hfRemote.getCorrelationsByPlayerIds(playerIds).isEmpty()) {
                orgDTO.getOrganizationTypes().add(PARTICIPATING_SITE);
                orgDTO.setFunctionalRole(PARTICIPATING_SITE);
            }
        }
    }

    /**
     * Looks into this organization's roles to see if any of them override
     * entity's address or contanct info. If true, this DTO gets updated to
     * reflect the role's address or contact info.
     * 
     * @see https
     *      ://tracker.nci.nih.gov/browse/PO-4648?focusedCommentId=145421#comment
     *      -145421
     * @param paOrgDTO
     *            PaOrganizationDTO
     * @param countries
     *            List<Country>
     * @throws NullifiedRoleException NullifiedRoleException
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static void retrieveAddressAndContactInfoFromRole(
            PaOrganizationDTO paOrgDTO, List<Country> countries)
            throws NullifiedRoleException {
        Ii[] playerIds = new Ii[] {IiConverter
                .convertToPoOrganizationIi(paOrgDTO.getId()) };
        ResearchOrganizationCorrelationServiceRemote researchOrganizationService = PoRegistry
                .getResearchOrganizationCorrelationService();
        final List<ResearchOrganizationDTO> roDTOs = researchOrganizationService
                .getCorrelationsByPlayerIds(playerIds);
        for (ResearchOrganizationDTO roDTO : roDTOs) {
            if (EntityStatusCode.ACTIVE.name().equalsIgnoreCase(
                    roDTO.getStatus().getCode())) {
                if (roDTO.getPostalAddress() != null
                        && CollectionUtils.isNotEmpty(roDTO.getPostalAddress()
                                .getItem())) {
                    setPostalAddressFields(countries, paOrgDTO, (Ad) roDTO
                            .getPostalAddress().getItem().iterator().next());
                }
                if (roDTO.getTelecomAddress() != null
                        && CollectionUtils.isNotEmpty(roDTO.getTelecomAddress()
                                .getItem())) {
                    setContactInfoFields(paOrgDTO, roDTO.getTelecomAddress());
                }

            }
        }
        HealthCareFacilityCorrelationServiceRemote hcService = PoRegistry
                .getHealthCareFacilityCorrelationService();
        final List<HealthCareFacilityDTO> hcfDTOs = hcService
                .getCorrelationsByPlayerIds(playerIds);
        for (HealthCareFacilityDTO hcfDTO : hcfDTOs) {
            if (EntityStatusCode.ACTIVE.name().equalsIgnoreCase(
                    hcfDTO.getStatus().getCode())) {
                if (hcfDTO.getPostalAddress() != null
                        && CollectionUtils.isNotEmpty(hcfDTO.getPostalAddress()
                                .getItem())) {
                    setPostalAddressFields(countries, paOrgDTO, (Ad) hcfDTO
                            .getPostalAddress().getItem().iterator().next());
                }
                if (hcfDTO.getTelecomAddress() != null
                        && CollectionUtils.isNotEmpty(hcfDTO
                                .getTelecomAddress().getItem())) {
                    setContactInfoFields(paOrgDTO, hcfDTO.getTelecomAddress());
                }              
            }
        }

    }

    /**
     * Searches for Persons in PO using a general criteria.
     * 
     * @param criteria
     *            PaPersonDTO
     * @return List<PaPersonDTO>
     * @throws PAException PAException
     * @throws TooManyResultsException TooManyResultsException
     * @throws NullifiedRoleException NullifiedRoleException
     */
    public static List<PaPersonDTO> searchPoPersons(PersonSearchCriteriaDTO criteria)
            throws PAException, TooManyResultsException, NullifiedRoleException {

        validateSearchCriteria(criteria);

        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<PersonDTO> personDTOs = PoRegistry.getPersonEntityService()
                .search(criteria, limit);
        List<PaPersonDTO> paPersonDTOs = new ArrayList<PaPersonDTO>();
        for (PersonDTO dto : personDTOs) {
            paPersonDTOs.add(convertToPaPersonDTO(dto));
        }
        addPersonCtepIDs(paPersonDTOs);        
        return convertToEnhancedPersonDTO(paPersonDTOs);
    }
    
    
    /**
     * 
     * @param personDto person to search
     * @return list of persondtos
     * @throws PAException exception condition
     * @throws TooManyResultsException exception condition
     * @throws NullifiedRoleException exception condition
     */
    public static List<PaPersonDTO> searchPoPersons(PersonDTO personDto)
            throws PAException, TooManyResultsException, NullifiedRoleException {

        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<PersonDTO> personDTOs = PoRegistry.getPersonEntityService()
                .search(personDto, limit);
        List<PaPersonDTO> paPersonDTOs = new ArrayList<PaPersonDTO>();
        for (PersonDTO dto : personDTOs) {
            paPersonDTOs.add(convertToPaPersonDTO(dto));
        }
        addPersonCtepIDs(paPersonDTOs);        
        return convertToEnhancedPersonDTO(paPersonDTOs);
    }    
    
    
    /**
     * @param persons
     * @return 
     */
    private static List<PaPersonDTO> convertToEnhancedPersonDTO(
            List<PaPersonDTO> persons) {
        List<PaPersonDTO> list = new ArrayList<PaPersonDTO>();
        for (PaPersonDTO orgDTO : persons) {
            list.add(new EnhancedPersonDTO(orgDTO));
        }
        return list;
    } 
    
    /**
     * Retrieve ctep IDs.
     * 
     * @param paPersons
     *            List<PaOrganizationDTO>
     * @throws NullifiedRoleException exception condition.
     */
    private static void addPersonCtepIDs(List<PaPersonDTO> paPersons) // NOPMD
            throws NullifiedRoleException {
        if (CollectionUtils.isEmpty(paPersons)) {
            return;
        }

        List<Ii> ids = new ArrayList<Ii>();
        for (PaPersonDTO personDTO : paPersons) {
            ids.add(IiConverter.convertToPoPersonIi(personDTO.getId()
                    .toString()));
        }
        List<IdentifiedPersonDTO> identifiedPersons = PoRegistry
                .getIdentifiedPersonEntityService().getCorrelationsByPlayerIds(
                        ids.toArray(new Ii[0])); // NOPMD
        for (IdentifiedPersonDTO idPersonDTO : identifiedPersons) {
            if (IiConverter.CTEP_PERSON_IDENTIFIER_ROOT.equals(idPersonDTO
                    .getAssignedId().getRoot())) {
                String ctepID = idPersonDTO.getAssignedId().getExtension();
                for (PaPersonDTO person : paPersons) {
                    if (person
                            .getId()
                            .toString()
                            .equals(idPersonDTO.getPlayerIdentifier()
                                    .getExtension())) {
                        person.setCtepId(ctepID);
                    }
                }
            }
        }
    }    

    /**
     * @param criteria
     * @throws PAException
     */
    private static void validateSearchCriteria(PersonSearchCriteriaDTO criteria)
            throws PAException {
        if (isEmptyCriteria(criteria)) {
            throw new PAException("At least one criterion must be provided");
        }
        if (StringUtils.isNotBlank(criteria.getId())
                && !NumberUtils.isDigits(criteria.getId())) {
            throw new PAException("PO ID must be numerical");
        }
    }

    private static boolean isEmptyCriteria(PersonSearchCriteriaDTO criteria) {
        return  StringUtils.isBlank(criteria.getId()) 
                && StringUtils.isBlank(criteria.getStatus())
                && StringUtils.isBlank(criteria.getFirstName())
                && StringUtils.isBlank(criteria.getLastName())
                && StringUtils.isBlank(criteria.getAffiliation())
                && StringUtils.isBlank(criteria.getFunctionalRole())
                && StringUtils.isBlank(criteria.getCtepId());
    }
    
    /**
     * Provides additional on-demand functionality to {@link PaPersonDTO}.
     * 
     * @author Denis G. Krylov
     * 
     */
    public static final class EnhancedPersonDTO extends PaPersonDTO {
        /**
         * 
         */
        @SuppressWarnings("unused")
        private static final long serialVersionUID = 1406045576246541515L;
        
        private Collection<OrganizationDTO> organizations;
        private Collection<String> roles;

        /**
         * @param personDTO PaPersonDTO
         */
        public EnhancedPersonDTO(PaPersonDTO personDTO) {
            try {
                PropertyUtils.copyProperties(this, personDTO);
            } catch (Exception e) {
                LOG.error(e, e);
            }
        }
        
        /**
         * @return Collection<String>
         * @throws NullifiedRoleException NullifiedRoleException
         * @throws NullifiedEntityException NullifiedEntityException
         * @throws PAException PAException
         */
        public Collection<String> getRoles() throws NullifiedRoleException,
                NullifiedEntityException, PAException {
            getOrganizations();
            return roles;
        }
        
        /**
         * Gets affiliated organizations.
         * @return Collection<OrganizationDTO>
         * @throws NullifiedRoleException NullifiedRoleException
         * @throws PAException PAException
         * @throws NullifiedEntityException NullifiedEntityException
         */
        @SuppressWarnings("unused")
        public Collection<OrganizationDTO> getOrganizations()
                throws NullifiedRoleException, NullifiedEntityException,
                PAException {
            if (organizations == null) {
                organizations = new LinkedHashSet<OrganizationDTO>();
                roles = new ArrayList<String>();
                
                Ii[] playerIds = new Ii[] {IiConverter
                        .convertToPoPersonIi(getId().toString()) };
                final List<OrganizationDTO> crsOrgs = findAffiliatedOrgs(
                        PoRegistry.getClinicalResearchStaffCorrelationService(),
                        playerIds);
                organizations.addAll(crsOrgs);
                final List<OrganizationDTO> hcpOrgs = findAffiliatedOrgs(
                        PoRegistry.getHealthCareProviderCorrelationService(),
                        playerIds);
                organizations.addAll(hcpOrgs);
                final List<OrganizationDTO> ocOrgs = findAffiliatedOrgs(
                        PoRegistry.getOrganizationalContactCorrelationService(),
                        playerIds);
                organizations.addAll(ocOrgs);
                organizations.addAll(findAffiliatedOrgs(
                        PoRegistry.getIdentifiedPersonEntityService(),
                        playerIds));
                
                if (!crsOrgs.isEmpty()) {
                    roles.add("Clinical Research Staff");
                }
                if (!hcpOrgs.isEmpty()) {
                    roles.add("Healthcare Provider");
                }
                if (!ocOrgs.isEmpty()) {
                    roles.add("Organizational Contact");
                }
                
            }
            return organizations;
        }
        
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static List<OrganizationDTO> findAffiliatedOrgs(
            CorrelationService correlationService, Ii[] playerIds) {
        List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
        try {
            List<AbstractRoleDTO> correlations = correlationService
                    .getCorrelationsByPlayerIds(playerIds);
            for (AbstractRoleDTO role : correlations) {
                //Ii orgID = role.getScoperIdentifier();
                Ii orgID = (Ii) PropertyUtils.getProperty(role, "scoperIdentifier");
                OrganizationDTO orgDTO = PoRegistry
                        .getOrganizationEntityService().getOrganization(orgID);
                orgs.add(new EnhancedOrganizationDTO(orgDTO));
            }
        } catch (Exception e) {
            LOG.error(e, e);
        }
        return (orgs);
    }

    /**
     * @param person PaPersonDTO
     * @throws NullifiedRoleException NullifiedRoleException
     */
    public static void retrieveAddressAndContactInfoFromRole(PaPersonDTO person)
            throws NullifiedRoleException {
        Ii[] ids = new Ii[] {IiConverter.convertToPoPersonIi(person.getId()
                .toString()) };
        
        for (OrganizationalContactDTO dto : PoRegistry
                .getOrganizationalContactCorrelationService()
                .getCorrelationsByPlayerIds(ids)) {
            transferAddressAndContactsFromRole(person, dto);
        }

        for (HealthCareProviderDTO dto : PoRegistry
                .getHealthCareProviderCorrelationService()
                .getCorrelationsByPlayerIds(ids)) {
            transferAddressAndContactsFromRole(person, dto);
        }        
        
        for (ClinicalResearchStaffDTO dto : PoRegistry
                .getClinicalResearchStaffCorrelationService()
                .getCorrelationsByPlayerIds(ids)) {
            transferAddressAndContactsFromRole(person, dto);
        }

    }
    
    /**
     * gets the CTEP id of the org from the cache or PO if needed.
     * @param poId the organization's po id
     * @return the ctep id
     * @throws PAException problems retrieving the id.
     */
    public static String getCtepId(final String poId) throws PAException {
       return (String) CacheUtils.getFromCacheOrBackend(CacheUtils.getOrganizationCtepIdCache(),
               poId,
               new Closure() {
                
                @Override
                public Object execute() throws PAException {
                    PaOrganizationDTO poOrg;
                    try {
                        poOrg = PADomainUtils.getOrgDetailsPopup(poId);
                    } catch (Exception e) {
                        throw new PAException(
                                "An exception occured while trying to retrieve the CTEP id for " + poId, e);
                    } 
                    if (poOrg != null) {
                        return poOrg.getCtepId();
                    }
                    return null;
                }
            });
    }
    
    /**
     * @param orgID the orgid
     * @return organizationDto
     * @throws NullifiedEntityException NullifiedEntityException
     * @throws PAException PAException
     * @throws NullifiedRoleException NullifiedRoleException
     * @throws TooManyResultsException TooManyResultsException
     */
    public static PaOrganizationDTO getOrgDetailsPopup(String orgID) throws NullifiedEntityException, PAException, 
    NullifiedRoleException, TooManyResultsException {
        PaOrganizationDTO paOrgDTO = null;
        OrganizationSearchCriteriaDTO searchCriteria = new OrganizationSearchCriteriaDTO();
        searchCriteria.setIdentifier(orgID);
        final List<OrganizationDTO> orgs = searchPoOrganizations(searchCriteria);
        if (!orgs.isEmpty()) {
            OrganizationDTO orgDTO = orgs.get(0);
            final List<Country> countries = PaRegistry.getLookUpTableService().getCountries();
            paOrgDTO = convertPoOrganizationDTO(orgDTO, countries);

            // Add families
            Set<Ii> famOrgRelIiList = new HashSet<Ii>();
            if (CollectionUtils.isNotEmpty(orgDTO.getFamilyOrganizationRelationships().getItem())) {
                famOrgRelIiList.addAll(orgDTO.getFamilyOrganizationRelationships().getItem());
            }
            Map<Ii, FamilyDTO> familyMap = PoRegistry.getFamilyService()
                    .getFamilies(famOrgRelIiList);
            paOrgDTO.setFamilies(getFamilies(orgDTO.getFamilyOrganizationRelationships(), familyMap));

            // Now add CTEP
            addOrganizationCtepIDs(Arrays.asList(paOrgDTO));
            
            // One of the organization's roles may override the address or contact info.
            retrieveAddressAndContactInfoFromRole(paOrgDTO, countries);
            
            // Finally, determine organization type
            determineOrganizationType(Arrays.asList(paOrgDTO));              
        }
        return paOrgDTO;
    }
    
    /**
     * @param personID the personid
     * @return the persondto
     * @throws NullifiedEntityException NullifiedEntityException
     * @throws PAException PAException
     * @throws NullifiedRoleException NullifiedRoleException
     * @throws TooManyResultsException TooManyResultsException
     */
    public static PaPersonDTO getPersonDetailsPopup(String personID) throws NullifiedEntityException,
    PAException, NullifiedRoleException, TooManyResultsException {
        PaPersonDTO person = null;
        PersonSearchCriteriaDTO criteriaDTO = new PersonSearchCriteriaDTO();
        criteriaDTO.setId((personID));
        final List<PaPersonDTO> persons = searchPoPersons(criteriaDTO);
        if (!persons.isEmpty()) {
            person = persons.get(0);
            retrieveAddressAndContactInfoFromRole(person);
        }
        return person;
    }
    /**
     * @param person
     * @param dto
     */
    private static void transferAddressAndContactsFromRole(PaPersonDTO person,
            AbstractPersonRoleDTO dto) {
        if (dto.getStatus() != null
                && EntityStatusCode.ACTIVE.name().equalsIgnoreCase(
                        dto.getStatus().getCode())) {
            if (dto.getPostalAddress() != null
                    && CollectionUtils.isNotEmpty(dto.getPostalAddress()
                            .getItem())) {
                setPersonAddress(person, (Ad) dto.getPostalAddress().getItem()
                        .iterator().next());
            }
            if (dto.getTelecomAddress() != null
                    && CollectionUtils.isNotEmpty(dto.getTelecomAddress()
                            .getItem())) {
                setPersonContactInfo(person, dto.getTelecomAddress());
            }
        }
    }
    
    /**
     * @param spIn StudyProtocol
     * @return StudyProtocolDTO
     */
    public static StudyProtocolDTO convertStudyProtocol(final StudyProtocol spIn) {
        Hibernate.initialize(spIn);
        StudyProtocol sp = (spIn instanceof HibernateProxy)
                ? (StudyProtocol) ((HibernateProxy) spIn).getHibernateLazyInitializer().getImplementation() : spIn;
        StudyProtocolDTO studyProtocolDTO;
        if (sp instanceof NonInterventionalStudyProtocol) {
            studyProtocolDTO = NonInterventionalStudyProtocolConverter
                    .convertFromDomainToDTO((NonInterventionalStudyProtocol) sp);
        } else if (sp instanceof InterventionalStudyProtocol) {
            studyProtocolDTO = InterventionalStudyProtocolConverter
                    .convertFromDomainToDTO((InterventionalStudyProtocol) sp);
        } else {
            studyProtocolDTO = StudyProtocolConverter
                    .convertFromDomainToDTO(sp);
        }
        return studyProtocolDTO;
    }
    
}
