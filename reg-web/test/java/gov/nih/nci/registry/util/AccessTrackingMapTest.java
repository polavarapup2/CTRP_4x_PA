/**
 * 
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 *
 */
public class AccessTrackingMapTest {

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
     * Test method for {@link gov.nih.nci.registry.util.AccessTrackingMap#clear()}.
     */
    @Test
    public final void testClear() {
        AccessTrackingMap map = new AccessTrackingMap();
        map.put("key", "value");
        map.get("key");
        assertTrue(map.getValuesWhoseKeysNeverAccessed().isEmpty());
        
        map.clear();
        map.put("key", "value");
        assertEquals("value", map.getValuesWhoseKeysNeverAccessed().iterator().next());
    }

    /**
     * Test method for {@link gov.nih.nci.registry.util.AccessTrackingMap#get(java.lang.Object)}.
     */
    @Test
    public final void testGetObject() {
        AccessTrackingMap map = new AccessTrackingMap();
        map.put("key", "value");
        assertEquals("value", map.getValuesWhoseKeysNeverAccessed().iterator().next());
        
        map.get("key");
        map.get("keyNonExistent");
        assertTrue(map.getValuesWhoseKeysNeverAccessed().isEmpty());
                
    }

    /**
     * Test method for {@link gov.nih.nci.registry.util.AccessTrackingMap#remove(java.lang.Object)}.
     */
    @Test
    public final void testRemoveObject() {
        AccessTrackingMap map = new AccessTrackingMap();
        map.put("key", "value");
        assertEquals("value", map.getValuesWhoseKeysNeverAccessed().iterator().next());
        
        map.get("key");
        assertTrue(map.getValuesWhoseKeysNeverAccessed().isEmpty());
        map.remove("key");
        map.put("key", "value");
        assertEquals("value", map.getValuesWhoseKeysNeverAccessed().iterator().next());
    }

    /**
     * Test method for {@link gov.nih.nci.registry.util.AccessTrackingMap#clearTrackedKeys()}.
     */
    @Test
    public final void testClearTrackedKeys() {
        AccessTrackingMap map = new AccessTrackingMap();
        map.put("key", "value");
        map.get("key");
        assertTrue(map.getValuesWhoseKeysNeverAccessed().isEmpty());
        
        map.clearTrackedKeys();
        assertEquals("value", map.getValuesWhoseKeysNeverAccessed().iterator().next());
    }

    /**
     * Test method for {@link gov.nih.nci.registry.util.AccessTrackingMap#getValuesWhoseKeysNeverAccessed()}.
     */
    @Test
    public final void testGetValuesWhoseKeysNeverAccessed() {
        AccessTrackingMap map = new AccessTrackingMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(2, map.getValuesWhoseKeysNeverAccessed().size());
        Iterator iterator = map.getValuesWhoseKeysNeverAccessed().iterator();
        assertEquals("value1", iterator.next());
        assertEquals("value2", iterator.next());
        
        map.get("key1");
        assertEquals(1, map.getValuesWhoseKeysNeverAccessed().size());
        iterator = map.getValuesWhoseKeysNeverAccessed().iterator();
        assertEquals("value2", iterator.next());

    }

}
