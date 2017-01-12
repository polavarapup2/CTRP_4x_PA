package gov.nih.nci.pa.webservices;

import java.lang.annotation.Annotation;

import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.DecorateTypes;
import org.jboss.resteasy.spi.interception.DecoratorProcessor;
import org.xml.sax.SAXException;

/**
 * @see http 
 *      ://stackoverflow.com/questions/7570326/jboss-resteasy-jax-rs-jaxb-schema
 *      -validation-with-decorator
 * 
 */
@DecorateTypes({ "text/*+xml", "application/*+xml", MediaType.APPLICATION_XML,
        MediaType.TEXT_XML })
public class ValidateProcessor implements
        DecoratorProcessor<Unmarshaller, Validate> {
    private static final Logger LOGGER = Logger
            .getLogger(ValidateProcessor.class);

    @SuppressWarnings("rawtypes")
    @Override
    public Unmarshaller decorate(Unmarshaller target, Validate annotation,
            Class type, Annotation[] annotations, MediaType mediaType) {
        try {
            SchemaFactory sf = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new Source[] {
                    new StreamSource(getClass().getResource("/ws.xsd")
                            .toExternalForm()),
                    new StreamSource(getClass().getResource("/po.xsd")
                            .toExternalForm()) });
            target.setSchema(schema);
            return target;
        } catch (SAXException e) {
            LOGGER.error(e, e);
            throw new RuntimeException(e); // NOPMD
        }
    }
}
