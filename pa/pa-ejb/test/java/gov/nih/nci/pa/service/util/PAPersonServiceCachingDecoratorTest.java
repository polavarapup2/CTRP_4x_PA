/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import gov.nih.nci.pa.util.CacheUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class PAPersonServiceCachingDecoratorTest {

    private PAPersonServiceCachingDecorator decorator;
    private PAPersonServiceRemote remote;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        remote = mock(PAPersonServiceCachingDecorator.class);
        decorator = new PAPersonServiceCachingDecorator(remote);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.PAPersonServiceCachingDecorator#getAllPrincipalInvestigators()}
     * .
     * 
     * @throws Exception
     */
    @Test
    public final void testGetAllPrincipalInvestigators() throws Exception {
        decorator.getAllPrincipalInvestigators();
        verify(remote).getAllPrincipalInvestigators();

        setUp();

        decorator.getAllPrincipalInvestigators();
        verify(remote, never()).getAllPrincipalInvestigators();

        CacheUtils.getCriteriaCollectionsCache().removeAll();
        decorator.getAllPrincipalInvestigators();
        verify(remote).getAllPrincipalInvestigators();
    }
    
    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.PAPersonServiceCachingDecorator#getAllPrincipalInvestigatorsByName(String personTerm)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public final void testGetAllPrincipalInvestigatorsByName() throws Exception {
        decorator.getAllPrincipalInvestigatorsByName("Mayo");
        verify(remote).getAllPrincipalInvestigatorsByName("Mayo");
    }

}
