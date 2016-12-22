/**
 * 
 */
package gov.nih.nci.registry.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * An extension of {@link LinkedHashMap} that remembers key values that have
 * been accessed.
 * 
 * @author Denis G. Krylov
 * @param <K>
 * @param <V>
 * 
 */
public final class AccessTrackingMap<K, V> extends LinkedHashMap<K, V> {

    private final Collection<Object> keysAccessed = new HashSet<Object>();

    /**
     * 
     */
    private static final long serialVersionUID = 4290815065912612862L;

    /**
     * Default constructor.
     */
    public AccessTrackingMap() {
        super();
    }

    @Override
    public V get(Object key) {
        final V value = super.get(key);
        if (value != null) {
            keysAccessed.add(key);
        }
        return value;
    }

    @Override
    public void clear() {
        clearTrackedKeys();
        super.clear();
    }

    @Override
    public V remove(Object key) {
        keysAccessed.remove(key);
        return super.remove(key);
    }

    /**
     * Clear tracked keys.
     */
    public void clearTrackedKeys() {
        keysAccessed.clear();
    }

    /**
     * @return ValuesWhoseKeysNeverAccessed
     */
    public Collection<V> getValuesWhoseKeysNeverAccessed() {
        Collection<V> values = new LinkedHashSet<V>();
        for (Map.Entry<K, V> e : super.entrySet()) {
            if (!keysAccessed.contains(e.getKey())) {
                values.add(e.getValue());
            }
        }
        return values;
    }

}
