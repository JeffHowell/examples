package org.summa.experiments;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Bi-Directional Map. 2 interfaces to the Map, {@link #left} and {@link #right}
 * . The Keys of the Left Map are Values in the Right map and visa versa.
 * <p/>
 * <p>
 * This is useful for managing IDs when integrating two systems. The IDs of one
 * system are mapped with values being the corresponding ID is the other system.
 * The KeyMapKey can be populated from either side and accessed from either
 * side.
 * </p>
 * 
 * @param <L>
 *            key type of the Left Map
 * @param <R>
 *            key type of the Right Map
 */
public final class KeyMapKey<L, R> {
    private final Map<L, R> lMap = new HashMap<L, R>();
    private final Map<R, L> rMap = new HashMap<R, L>();

    // added references to other map that it's paired to
    public final Map<L, R> left = new MapMap<L, R>(lMap, rMap);
    public final Map<R, L> right = new MapMap<R, L>(rMap, lMap);

    private class MapMap<MY, OT> implements Map<MY, OT> {
	private final Map<MY, OT> myMap;
	private final Map<OT, MY> otherMap;

	private MapMap(Map<MY, OT> myMap, Map<OT, MY> otherMap) {
	    this.myMap = myMap;
	    this.otherMap = otherMap;
	}

	@Override
	public int size() {
	    return myMap.size();
	}

	@Override
	public boolean isEmpty() {
	    return myMap.isEmpty();
	}

	@Override()
	public boolean containsKey(Object key) {
	    return myMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
	    return myMap.containsValue(value);
	}

	@Override
	public OT get(Object key) {
	    return myMap.get(key);
	}

	@Override
	synchronized public OT put(MY key, OT value) {
	    otherMap.put(value, key);
	    return myMap.put(key, value);
	}

	@Override
	synchronized public OT remove(Object key) {
	    // noinspection SuspiciousMethodCalls
	    otherMap.remove(myMap.get(key));
	    return myMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends MY, ? extends OT> m) {
	    for (Entry<? extends MY, ? extends OT> entry : m.entrySet()) {
		MY myKey = entry.getKey();
		OT otherKey = entry.getValue();

		put(myKey, otherKey);
	    }
	}

	@Override
	synchronized public void clear() {
	    otherMap.clear();
	    myMap.clear();
	}

	@Override
	public Set<MY> keySet() {
	    return myMap.keySet();
	}

	@Override
	public Collection<OT> values() {
	    return myMap.values();
	}

	@Override
	public Set<Entry<MY, OT>> entrySet() {
	    return myMap.entrySet();
	}
    }
}