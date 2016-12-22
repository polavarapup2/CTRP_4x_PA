/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.RegulatoryAuthorityConverter;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.RegulatoryAuthoritySortCriterion;
import gov.nih.nci.pa.util.PAConstants;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
public class RegulatoryAuthorityBeanLocal extends AbstractBaseIsoService<RegulatoryAuthorityDTO,
    RegulatoryAuthority, RegulatoryAuthorityConverter> implements RegulatoryAuthorityServiceLocal {

    private static final Logger LOG  = Logger.getLogger(RegulatoryAuthorityBeanLocal.class);
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";

    /**
     * @param dto RegulatoryAuthorityDTO
     * @return RegulatoryAuthorityDTO
     * @throws PAException PAException
     */
    @Override
    public RegulatoryAuthorityDTO update(RegulatoryAuthorityDTO dto) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }


    /**
     * @param dto RegulatoryAuthorityDTO
     * @return RegulatoryAuthorityDTO
     * @throws PAException PAException
     */
    @Override
    public RegulatoryAuthorityDTO create(RegulatoryAuthorityDTO dto) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param ii Ii
     * @throws PAException PAException
     */
    @Override
    public void delete(Ii ii) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     *
     * @param dto dto
     * @param pagingParams parms
     * @return list
     * @throws PAException on error
     * @throws TooManyResultsException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RegulatoryAuthorityDTO> search(RegulatoryAuthorityDTO dto, LimitOffset pagingParams) throws PAException,
        TooManyResultsException {
        if (dto == null) {
            throw new PAException(" RegulatoryAuthorityDTO should not be null ");
        }

        RegulatoryAuthority example = Converters.get(RegulatoryAuthorityConverter.class).convertFromDtoToDomain(dto);
        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<RegulatoryAuthority> params = new PageSortParams<RegulatoryAuthority>(maxLimit,
                pagingParams.getOffset(), RegulatoryAuthoritySortCriterion.REGULATORY_AUTHORITY_ID, false);
        List<RegulatoryAuthority> regulatoryList = search(new AnnotatedBeanSearchCriteria<RegulatoryAuthority>(example),
                params);
        if (regulatoryList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTOs(regulatoryList);
    }


    /**
     * gets the Id.
     *
     * @param authorityName
     *            orgName
     * @param countryName
     *            country Id
     * @return ii
     * @throws PAException
     *             e
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii getRegulatoryAuthorityId(St authorityName, St countryName) throws PAException {

        RegulatoryAuthority criteria = new RegulatoryAuthority();
        Country country = new Country();
        country.setName(StConverter.convertToString(countryName));
        criteria.setCountry(country);
        criteria.setAuthorityName(StConverter.convertToString(authorityName));
        PageSortParams<RegulatoryAuthority> params = new PageSortParams<RegulatoryAuthority>(1,
                0, RegulatoryAuthoritySortCriterion.REGULATORY_AUTHORITY_ID, false);
        List<RegulatoryAuthority> results =
            search(new AnnotatedBeanSearchCriteria<RegulatoryAuthority>(criteria), params);

        Long retRegAuthId = null;
        if (CollectionUtils.isNotEmpty(results)) {
            retRegAuthId = results.get(0).getId();
        }
        return IiConverter.convertToIi(retRegAuthId);
    }
}
