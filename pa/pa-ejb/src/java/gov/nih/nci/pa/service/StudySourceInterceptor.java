/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Binds study source, if applicable, to a ThreadLocal.
 * 
 * @author dkrylov
 * 
 */
@Interceptor
public class StudySourceInterceptor {

    /**
     * STUDY_SOURCE_CONTEXT
     */
    public static final ThreadLocal<StudySourceCode> STUDY_SOURCE_CONTEXT = new ThreadLocal<>();

    /**
     * @param invContext
     *            InvocationContext
     * @return Object
     * @throws Exception
     *             Exception
     */
    @AroundInvoke
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Object determineStudySource(InvocationContext invContext)
            throws Exception {
        try {
            for (Object obj : invContext.getParameters()) {
                if (obj instanceof StudyProtocolDTO) {
                    StudyProtocolDTO sp = (StudyProtocolDTO) obj;
                    STUDY_SOURCE_CONTEXT.set(CdConverter.convertCdToEnum(
                            StudySourceCode.class, sp.getStudySource()));
                    break;
                }
            }
            return invContext.proceed();
        } finally {
            STUDY_SOURCE_CONTEXT.remove();
        }
    }

}
