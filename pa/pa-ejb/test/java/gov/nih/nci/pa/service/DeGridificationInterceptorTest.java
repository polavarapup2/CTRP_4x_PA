/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.ISOUtil;

import javax.interceptor.InvocationContext;

import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class DeGridificationInterceptorTest {

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.DeGridificationInterceptor#deGridify(javax.interceptor.InvocationContext)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public final void testDeGridify() throws Exception {
        DeGridificationInterceptor interceptor = new DeGridificationInterceptor();
        InvocationContext invContext = mock(InvocationContext.class);
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setUserLastCreated(StConverter
                .convertToSt("/O=caBIG/OU=caGrid/OU=QA LOA1/OU=NCICB QA AuthnSvc IdP/CN=CTRPQATester1"));
        when(invContext.getParameters()).thenReturn(new Object[] { spDTO });

        interceptor.deGridify(invContext);
        verify(invContext).proceed();
        assertEquals("ctrpqatester1", spDTO.getUserLastCreated().getValue());
        reset(invContext);

        spDTO.setUserLastCreated(StConverter.convertToSt("CTRPQATester2"));
        when(invContext.getParameters()).thenReturn(new Object[] { spDTO });
        interceptor.deGridify(invContext);
        verify(invContext).proceed();
        assertEquals("CTRPQATester2", spDTO.getUserLastCreated().getValue());
        reset(invContext);

        spDTO.setUserLastCreated(StConverter.convertToSt(null));
        when(invContext.getParameters()).thenReturn(new Object[] { spDTO });
        interceptor.deGridify(invContext);
        verify(invContext).proceed();
        assertTrue(ISOUtil.isStNull(spDTO.getUserLastCreated()));
        reset(invContext);

        spDTO.setUserLastCreated(StConverter.convertToSt(""));
        when(invContext.getParameters()).thenReturn(new Object[] { spDTO });
        interceptor.deGridify(invContext);
        verify(invContext).proceed();
        assertTrue(ISOUtil.isStNull(spDTO.getUserLastCreated()));
        reset(invContext);

    }

}
