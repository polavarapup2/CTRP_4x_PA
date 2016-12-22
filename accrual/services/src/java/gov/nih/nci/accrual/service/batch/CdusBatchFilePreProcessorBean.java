/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author dkrylov
 * 
 */
@Stateless
@Local(CdusBatchFilePreProcessorLocal.class)
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CdusBatchFilePreProcessorBean implements
        CdusBatchFilePreProcessorLocal {

    private final List<CdusBatchFilePreProcessor> preprocessorsChain = new ArrayList<CdusBatchFilePreProcessor>();
    { //NOPMD
        preprocessorsChain.add(new DuplicateSubjectLinePreprocessor());
        preprocessorsChain
                .add(new DuplicateSubjectAcrossSitesLinePreprocessor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.CdusBatchFilePreProcessorLocal#preprocess
     * (java.io.File)
     */
    @Override
    public PreprocessingResult preprocess(File cdusFile) throws IOException { // NOPMD
        List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        for (CdusBatchFilePreProcessor proc : preprocessorsChain) {
            PreprocessingResult result = proc.preprocess(cdusFile);
            cdusFile = result.getPreprocessedFile();
            validationErrors.addAll(result.getValidationErrors());
        }
        return new PreprocessingResult(cdusFile, validationErrors);
    }

}
