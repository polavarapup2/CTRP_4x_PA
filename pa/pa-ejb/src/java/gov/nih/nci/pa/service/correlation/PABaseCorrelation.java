package gov.nih.nci.pa.service.correlation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.dto.PACorrelationDTO;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.iso.convert.AbstractPoConverter;
import gov.nih.nci.pa.iso.convert.POConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PAExceptionConstants;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author NAmiruddin
 *
 * @param <BO> domain object
 * @param <PODTO> dto
 * @param <PADTO> dtoPa
 * @param <CONVERTER>
 */
@SuppressWarnings("unchecked")
public class PABaseCorrelation <PADTO extends PACorrelationDTO, PODTO extends CorrelationDto, BO extends StructuralRole,
    CONVERTER extends AbstractPoConverter<PADTO, PODTO, BO>> implements PABaseCorrelationService<PADTO> {

    private  final Class<PADTO> typeArgument;
    private final Class<BO> srArgument;
    private final Class<CONVERTER> converterArgument;
    private static final int THREE = 3;
    private static final int TWO = 2;
    /**
     * default constructor.
     */
    @SuppressFBWarnings
    public PABaseCorrelation() {
        Type myType = getClass(); // get the parameterized type, recursively resolving type parameters
        ParameterizedType parameterizedType = (ParameterizedType) myType;
        typeArgument = (Class) parameterizedType.getActualTypeArguments()[0];
        srArgument = (Class) parameterizedType.getActualTypeArguments()[TWO];
        converterArgument = (Class) parameterizedType.getActualTypeArguments()[THREE];
    }

    /**
     *
     * @param typeArgument padto
     * @param srArgument bo
     * @param converterArgument converter
     */
    public PABaseCorrelation(Class<PADTO> typeArgument, Class<BO> srArgument,
            Class<CONVERTER> converterArgument) {
        super();
        this.typeArgument = typeArgument;
        this.srArgument = srArgument;
        this.converterArgument = converterArgument;
    }

    /**
     * @param dto dto
     * @return dto
     * @throws PAException on error
     */
    public Long create(PADTO dto) throws PAException {
        CorrelationUtils corrUtils = new CorrelationUtils();
        if (dto.isPersonMandatory() && ISOUtil.isIiNull(dto.getPersonIdentifier())) {
            throw new PAException(PAExceptionConstants.NULL_II_PERSON);
        }
        if (dto.isOrganizationMandatory() && ISOUtil.isIiNull(dto.getOrganizationIdentifier())) {
            throw new PAException(PAExceptionConstants.NULL_II_ORG);
        }
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService().
                getOrganization(dto.getOrganizationIdentifier());
        } catch (NullifiedEntityException e) {
           throw new PAException(PAExceptionConstants.NULLIFIED_ORG, e);
        }

        //check if Ii is of Person or SR

        // Step 2 : get the PO Person
        PersonDTO poPer = null;
        if (dto.getPersonIdentifier() != null) {
            try {
                poPer = PoRegistry.getPersonEntityService().
                getPerson(dto.getPersonIdentifier());
            } catch (NullifiedEntityException e) {
                throw new PAException(PAExceptionConstants.NULLIFIED_PERSON, e);
            }
        }
        CorrelationService corrService = getPoService(getTypeArgument());
        CorrelationDto cDto = convertFromPADtoToPODto(dto);
        Ii srPoIi = null;
        List<PODTO> poDtos = corrService.search(cDto);
        if (poDtos != null && poDtos.size() > 1 && dto.isUnique()) {
            throw new PAException("PO Correlation should not have more than 1  ");
        }
        if (poDtos == null || poDtos.isEmpty()) {
            try {
                if (new PAServiceUtils().isAutoCurationEnabled()
                        && corrService instanceof OrganizationalContactCorrelationServiceRemote
                        && cDto instanceof OrganizationalContactDTO) {
                    srPoIi = ((OrganizationalContactCorrelationServiceRemote) corrService)
                            .createActiveCorrelation((OrganizationalContactDTO) cDto);
                } else {
                    srPoIi = corrService.createCorrelation(cDto);
                }
            } catch (EntityValidationException e) {
                throw new PAException("Validation exception during  structural role creation" , e);
            } catch (CurationException e) {
                throw new PAException("Curation exception during  structural role creation" , e);
            }
        } else {
            srPoIi = DSetConverter.convertToIi(poDtos.get(0).getIdentifier());
        }
        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIi(dto.getOrganizationIdentifier());
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }
        Person paPer = null;
        if (dto.getPersonIdentifier() != null) {
        // Step 4 : check for pa person, if not create one
            paPer = corrUtils.getPAPersonByIi(dto.getPersonIdentifier());
            if (paPer == null) {
                paPer = corrUtils.createPAPerson(poPer);
            }
        }
        Long srPaIdentifier = getStructuralRole(srPoIi.getExtension());
        StructuralRole sr = null;
        if (srPaIdentifier == null) {
            // create a new oc
            sr = convertToDomain(dto, paOrg, paPer);
            sr.setIdentifier(srPoIi.getExtension());
            Session session =  PaHibernateUtil.getCurrentSession();
            session.save(sr);
            session.flush();
            srPaIdentifier = sr.getId();
        }
        return srPaIdentifier;
    }

    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<PADTO> getTypeArgument() {
        return typeArgument;
    }

    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<BO> getSrArgument() {
        return srArgument;
    }
    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<CONVERTER> getConverterArgument() {
        return converterArgument;
    }

    private StructuralRole convertToDomain(PADTO dto , Organization org, Person per) throws PAException {
       return POConverter.get(getConverterArgument()).convertToDomain(dto, org, per);
    }

    private CorrelationDto convertFromPADtoToPODto(PADTO dto) throws PAException {
        return POConverter.get(getConverterArgument()).convertFromPADtoToPoDto(dto);
     }

    /**
     *
     * @param <TYPE> the converter type to get
     * @param clazz class
     * @return service
     * @throws PAException on error
     */
     private <TYPE extends CorrelationService> TYPE getPoService(Class<PADTO> clazz) throws PAException {
         if (clazz.equals(PAOrganizationalContactDTO.class)) {
             return (TYPE) PoRegistry.getOrganizationalContactCorrelationService();
         }
         throw new PAException(" Unknown dto type for " + clazz.getName());
     }

     private Long getStructuralRole(String poIdentifier) throws PAException {
         Session session = null;
         List<BO> queryList = new ArrayList<BO>();
         session = PaHibernateUtil.getCurrentSession();
         Query query = null;
         Long paIdentifier = null;
         // step 1: form the hql
         String hql = "select alias "
                    + "from " + srArgument.getName() + " alias "
                    + "where alias.identifier = :identifier ";

         // step 2: construct query object
         query = session.createQuery(hql);
         query.setParameter("identifier", poIdentifier);

         // step 3: query the result
         queryList = query.list();
         if (queryList.size() > 1) {
             throw new PAException(" More than one Structural role found in " + srArgument.getName()
                     + " for identifier " + poIdentifier);
         } else if (!queryList.isEmpty()) {
             paIdentifier = queryList.get(0).getId();
         }
         return paIdentifier;
     }

}
