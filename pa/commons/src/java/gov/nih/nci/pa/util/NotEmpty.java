//$Id: $
package gov.nih.nci.pa.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Check that a String is not empty (not null and length > 0) or that a Collection (or array) is not empty (not null and
 * length > 0).
 *
 * @author Emmanuel Bernard
 */
@Documented
@ValidatorClass(NotEmptyValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
    /**
     * get the message.
     */
    String message() default "Field must be set";
}
