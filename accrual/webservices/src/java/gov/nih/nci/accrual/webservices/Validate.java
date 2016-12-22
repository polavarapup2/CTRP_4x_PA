package gov.nih.nci.accrual.webservices;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.xml.bind.Unmarshaller;

import org.jboss.resteasy.annotations.Decorator;

/**
 * @see http
 *      ://stackoverflow.com/questions/7570326/jboss-resteasy-jax-rs-jaxb-schema
 *      -validation-with-decorator
 * 
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Decorator(processor = ValidateProcessor.class, target = Unmarshaller.class)
public @interface Validate {
}