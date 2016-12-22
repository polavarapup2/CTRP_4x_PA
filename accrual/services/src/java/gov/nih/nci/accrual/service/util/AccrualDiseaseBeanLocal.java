package gov.nih.nci.accrual.service.util;

import static gov.nih.nci.accrual.service.batch.CdusBatchUploadReaderBean.ICD_O_3_CODESYSTEM;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Hugh Reinhart
 * @since Dec 14, 2012
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class AccrualDiseaseBeanLocal extends AbstractBaseSearchBean<AccrualDisease> 
        implements AccrualDiseaseServiceLocal {

    private static final Logger LOG = Logger.getLogger(AccrualDiseaseBeanLocal.class);

    /**
     * Class required by the AbstractBaseSearchBean sort functionality.
     */
    private enum AccrualDiseaseSortCriterion implements SortCriterion<AccrualDisease> {
        DISEASE_CODE("diseaseCode", null), CODE_SYSTEM("codeSystem", null);

        private final String orderField;
        private final String leftJoinField;

        private AccrualDiseaseSortCriterion(String orderField, String leftJoinField) {
            this.orderField = orderField;
            this.leftJoinField = leftJoinField;
        }

        /**
         * {@inheritDoc}
         */
        public String getOrderField() {
            return orderField;
        }
        /**
         * {@inheritDoc}
         */
        public String getLeftJoinField() {
            return leftJoinField;
        }
    }

    @EJB
    private SearchStudySiteService searchStudySiteSvc;
    

    /**
     * {@inheritDoc}
     */
    @Override
    public AccrualDisease get(Long id) {
        return (AccrualDisease) PaHibernateUtil.getCurrentSession().get(AccrualDisease.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccrualDisease get(Ii ii) {
        if (ISOUtil.isIiNull(ii)) {
            return null;
        }
        boolean isCode = false;
        String cs = ii.getIdentifierName();
        if (StringUtils.isNotBlank(cs)) {
            List<String> csList = PaServiceLocator.getInstance().getAccrualDiseaseTerminologyService().
                    getValidCodeSystems();
            if (csList.contains(cs)) {
                isCode = true;
            }
        }
        if (isCode) {
            return getByCode(cs, ii.getExtension());
        }
        return get(IiConverter.convertToLong(ii));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccrualDisease getByCode(String codeSystem, String diseaseCode) {
        if (StringUtils.isBlank(codeSystem) || StringUtils.isBlank(diseaseCode)) {
            return null;
        }
        AccrualDisease criteria = new AccrualDisease();
        criteria.setCodeSystem(codeSystem);
        criteria.setDiseaseCode(diseaseCode);
        List<AccrualDisease> dList = search(criteria);
        return dList.isEmpty() ? null : dList.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccrualDisease> search(AccrualDisease searchCriteria) {
        PageSortParams<AccrualDisease> params = new PageSortParams<AccrualDisease>(
        PAConstants.MAX_SEARCH_RESULTS, 0, Arrays.asList(AccrualDiseaseSortCriterion.CODE_SYSTEM, 
        AccrualDiseaseSortCriterion.DISEASE_CODE), false);
        SearchCriteria<AccrualDisease> criteria = new AnnotatedBeanSearchCriteria<AccrualDisease>(searchCriteria);
        List<AccrualDisease> result = super.search(criteria, params);
        if (result.isEmpty() && StringUtils.isNotEmpty(searchCriteria.getDiseaseCode())
                && searchCriteria.getDiseaseCode().toUpperCase(Locale.US).charAt(0) == 'C'
                && (StringUtils.equals(ICD_O_3_CODESYSTEM, searchCriteria.getCodeSystem())
                    || StringUtils.isEmpty(searchCriteria.getCodeSystem()))) {
            searchCriteria.setCodeSystem(ICD_O_3_CODESYSTEM);
            int length = searchCriteria.getDiseaseCode().length();
            String appendedDC = searchCriteria.getDiseaseCode().substring(0, length - 1) 
                    + "." + searchCriteria.getDiseaseCode().substring(length - 1, length);
            searchCriteria.setDiseaseCode(appendedDC);
            criteria = new AnnotatedBeanSearchCriteria<AccrualDisease>(searchCriteria);
            result = super.search(criteria, params);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diseaseCodeMandatory(Long spId) {
        Ii spIi = IiConverter.convertToStudyProtocolIi(spId);
        try {
            if (searchStudySiteSvc.isStudyHasDCPId(spIi)) {
                return false;
            }
            StudyProtocolDTO spDto = PaServiceLocator.getInstance().getStudyProtocolService().getStudyProtocol(spIi);
            if (PrimaryPurposeCode.PREVENTION.equals(PrimaryPurposeCode
                    .getByCode(CdConverter.convertCdToString(spDto
                            .getPrimaryPurposeCode())))) {
                return false;
            }
        } catch (PAException e) {
            LOG.error(e);
        }
        return true;
    }

    /**
     * @param searchStudySiteSvc the searchStudySiteSvc to set
     */
    public void setSearchStudySiteSvc(SearchStudySiteService searchStudySiteSvc) {
        this.searchStudySiteSvc = searchStudySiteSvc;
    }
}
