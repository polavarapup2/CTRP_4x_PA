/**
 * 
 */
package gov.nih.nci.pa.test.integration.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dkrylov
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Batch {
    public int number();
}
