/**
 * 
 */
package gov.nih.nci.pa.webservices;

import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ObjectFactory;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class ValidateProcessorTest {

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.ValidateProcessor#decorate(javax.xml.bind.Unmarshaller, gov.nih.nci.pa.webservices.Validate, java.lang.Class, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)}
     * .
     * 
     * @throws JAXBException
     */
    @SuppressWarnings("unused")
    @Test(expected = JAXBException.class)
    public final void testDecorate() throws JAXBException {

        ValidateProcessor processor = new ValidateProcessor();

        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        processor.decorate(u, null, getClass(), null, null);

        URL url = getClass().getResource(
                "/register_complete_schemaViolation.xml");
        CompleteTrialRegistration o = ((JAXBElement<CompleteTrialRegistration>) u
                .unmarshal(url)).getValue();

    }

}
