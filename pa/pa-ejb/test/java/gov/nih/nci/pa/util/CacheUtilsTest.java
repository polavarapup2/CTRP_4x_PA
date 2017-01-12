/**
 * 
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils.Closure;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class CacheUtilsTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        CacheUtils.getCriteriaCollectionsCache().removeAll();
        CacheUtils.getSearchResultsCache().removeAll();
        CacheUtils.getSubmittersCache().removeAll();
        CacheUtils.getCaDSRClassificationSchemesCache().removeAll();
        CacheUtils.getReportingResultsCache().removeAll();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        CacheUtils.getCriteriaCollectionsCache().removeAll();
        CacheUtils.getSearchResultsCache().removeAll();
        CacheUtils.getSubmittersCache().removeAll();
        CacheUtils.getCaDSRClassificationSchemesCache().removeAll();
        CacheUtils.getReportingResultsCache().removeAll();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.CacheUtils#getCriteriaCollectionsCache()}.
     */
    @Test
    public final void testGetCriteriaCollectionsCache() {
        assertEquals("CRITERIA_COLLECTIONS_CACHE", CacheUtils
                .getCriteriaCollectionsCache().getName());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.CacheUtils#getSearchResultsCache()}.
     */
    @Test
    public final void testGetSearchResultsCache() {
        assertEquals("SEARCH_RESULTS_CACHE", CacheUtils.getSearchResultsCache()
                .getName());
    }
    
    @Test
    public final void testGetSubmittersCache() {
        assertEquals("SUBMITTER_REGISTRY_USERS", CacheUtils.getSubmittersCache()
                .getName());
    }
    
    @Test
    public final void testGetCaDSRClassificationSchemesCache() {
        assertEquals("CADSR_CLASSIFICATION_SCHEMES", CacheUtils.getCaDSRClassificationSchemesCache()
                .getName());
    }
    @Test
    public final void testGetReportingResultsCache() {
        assertEquals("REPORTING_RESULTS_CACHE_KEY", CacheUtils.getReportingResultsCache()
                .getName());
    }



    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.CacheUtils#getFromCacheOrBackend(net.sf.ehcache.Cache, java.lang.String, gov.nih.nci.pa.util.CacheUtils.Closure)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetFromCacheOrBackend() throws PAException {
        String key = "key";
        Cache cache = CacheUtils.getCriteriaCollectionsCache();
        Closure closure = mock(Closure.class);
        Object closureResult = "result";
        when(closure.execute()).thenReturn(closureResult);

        // First invocation will result in a backend call.
        Object result = CacheUtils.getFromCacheOrBackend(cache, key, closure);
        assertEquals(closureResult, result);
        verify(closure, times(1)).execute();
        assertEquals(closureResult, cache.get(key).getObjectValue());

        // Second should not invoke backend, rather get from cache.
        closure = mock(Closure.class);
        when(closure.execute()).thenReturn(closureResult);
        result = CacheUtils.getFromCacheOrBackend(cache, key, closure);
        assertEquals(closureResult, result);
        verify(closure, times(0)).execute();
        assertEquals(closureResult, cache.get(key).getObjectValue());

        // clear cache.
        cache.removeAll();

        // now again backend call, check for cached item.
        closure = mock(Closure.class);
        when(closure.execute()).thenReturn(closureResult);
        result = CacheUtils.getFromCacheOrBackend(cache, key, closure);
        assertEquals(closureResult, result);
        verify(closure, times(1)).execute();
        assertEquals(closureResult, cache.get(key).getObjectValue());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.CacheUtils#removeItemFromCache(net.sf.ehcache.Cache, java.lang.String)}
     * .
     */
    @Test
    public final void testRemoveItemFromCache() {
        Element el = new Element("key", "value");
        CacheUtils.getCriteriaCollectionsCache().put(el);
        CacheUtils.removeItemFromCache(
                CacheUtils.getCriteriaCollectionsCache(), "key");
        assertNull(CacheUtils.getCriteriaCollectionsCache().get("key"));
    }

}
