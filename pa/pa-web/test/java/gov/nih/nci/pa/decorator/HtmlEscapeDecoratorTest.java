/**
 * 
 */
package gov.nih.nci.pa.decorator;

import static org.junit.Assert.*;

import org.displaytag.exception.DecoratorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 *
 */
public class HtmlEscapeDecoratorTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link gov.nih.nci.pa.decorator.HtmlEscapeDecorator#decorate(java.lang.Object)}.
     * @throws DecoratorException 
     */
    @Test
    public final void testDecorate() throws DecoratorException {
        HtmlEscapeDecorator decorator = new HtmlEscapeDecorator();
        assertEquals("", decorator.decorate(null));
        assertEquals("1", decorator.decorate(1));
        assertEquals("&lt;", decorator.decorate("<"));
        assertEquals("&gt;", decorator.decorate(">"));
        assertEquals("'", decorator.decorate("'"));
    }

}
