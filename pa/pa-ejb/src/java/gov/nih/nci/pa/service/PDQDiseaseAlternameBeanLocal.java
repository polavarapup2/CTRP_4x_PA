/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseAltername;
import gov.nih.nci.pa.iso.convert.PDQDiseaseAlternameConverter;
import gov.nih.nci.pa.iso.dto.PDQDiseaseAlternameDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.PDQDiseaseAlternameSortCriterion;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class PDQDiseaseAlternameBeanLocal
    extends AbstractBaseIsoService<PDQDiseaseAlternameDTO, PDQDiseaseAltername, PDQDiseaseAlternameConverter>
    implements PDQDiseaseAlternameServiceLocal {

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PDQDiseaseAlternameDTO> getByDisease(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.  ");
        }

        PDQDiseaseAltername criteria = new PDQDiseaseAltername();
        PDQDisease disease = new PDQDisease();
        disease.setId(IiConverter.convertToLong(ii));
        criteria.setDisease(disease);

        PageSortParams<PDQDiseaseAltername> params = new PageSortParams<PDQDiseaseAltername>(
                PAConstants.MAX_SEARCH_RESULTS, 0, PDQDiseaseAlternameSortCriterion.DISEASE_ALTERNAME_ID, false);
        // step 3: query the result
        List<PDQDiseaseAltername> results = search(new AnnotatedBeanSearchCriteria<PDQDiseaseAltername>(criteria),
                params);
        return convertFromDomainToDTOs(results);
    }
}
