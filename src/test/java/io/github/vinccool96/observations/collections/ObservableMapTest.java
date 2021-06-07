package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.collections.TestedObservableMaps.CallableConcurrentHashMapImpl;
import io.github.vinccool96.observations.collections.TestedObservableMaps.CallableTreeMapImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;
import java.util.Map.Entry;

import static io.github.vinccool96.observations.collections.MockMapObserver.Call.call;
import static io.github.vinccool96.observations.collections.MockMapObserver.Tuple.tup;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
@SuppressWarnings({"OverwrittenKey", "ConstantConditions", "SimplifiableAssertion"})
public class ObservableMapTest {

    final Callable<ObservableMap<String, String>> mapFactory;

    private ObservableMap<String, String> map;

    private MockMapObserver<String, String> observer;

    public ObservableMapTest(final Callable<ObservableMap<String, String>> mapFactory) {
        this.mapFactory = mapFactory;
    }

    @Parameters
    public static Collection<?> createParameters() {
        Object[][] data = new Object[][]{
                {TestedObservableMaps.HASH_MAP},
                {TestedObservableMaps.TREE_MAP},
                {TestedObservableMaps.LINKED_HASH_MAP},
                {TestedObservableMaps.CONCURRENT_HASH_MAP},
                {TestedObservableMaps.CHECKED_OBSERVABLE_HASH_MAP},
                {TestedObservableMaps.SYNCHRONIZED_OBSERVABLE_HASH_MAP},
                {TestedObservableMaps.OBSERVABLE_MAP_PROPERTY}
        };
        return Arrays.asList(data);
    }

    @Before
    public void setUp() throws Exception {
        map = mapFactory.call();
        observer = new MockMapObserver<>();
        map.addListener(observer);

        map.clear();
        map.put("one", "1");
        map.put("two", "2");
        map.put("foo", "bar");
        observer.clear();
    }

    @Test
    public void testPutRemove() {
        map.put("observedFoo", "barVal");
        map.put("foo", "barfoo");
        assertEquals("barVal", map.get("observedFoo"));

        map.remove("observedFoo");
        map.remove("foo");
        map.remove("bar");
        map.put("one", "1");

        assertFalse(map.containsKey("foo"));

        observer.assertAdded(0, tup("observedFoo", "barVal"));
        observer.assertAdded(1, tup("foo", "barfoo"));
        observer.assertRemoved(1, tup("foo", "bar"));
        observer.assertRemoved(2, tup("observedFoo", "barVal"));
        observer.assertRemoved(3, tup("foo", "barfoo"));

        assertEquals(4, observer.getCallsNumber());
    }

    @Test
    public void testPutRemove_Null() {
        if (mapFactory instanceof CallableConcurrentHashMapImpl) {
            return; // Do not perform on ConcurrentHashMap, as it doesn't accept nulls
        }
        map.clear();
        observer.clear();

        map.put("bar", null);
        map.put("foo", "x");
        map.put("bar", "x");
        map.put("foo", null);

        assertEquals(2, map.size());

        map.remove("bar");
        map.remove("foo");

        assertEquals(0, map.size());

        observer.assertAdded(0, tup("bar", null));
        observer.assertAdded(1, tup("foo", "x"));
        observer.assertAdded(2, tup("bar", "x"));
        observer.assertRemoved(2, tup("bar", null));
        observer.assertAdded(3, tup("foo", null));
        observer.assertRemoved(3, tup("foo", "x"));
        observer.assertRemoved(4, tup("bar", "x"));
        observer.assertRemoved(5, tup("foo", null));

        assertEquals(6, observer.getCallsNumber());
    }

    @Test
    public void testPutRemove_NullKey() {
        if (mapFactory instanceof CallableConcurrentHashMapImpl || mapFactory instanceof CallableTreeMapImpl) {
            return; // Do not perform on ConcurrentHashMap and TreeMap, as they don't accept null keys
        }

        map.put(null, "abc");

        assertEquals(4, map.size());

        map.remove(null);

        assertEquals(3, map.size());

        observer.assertAdded(0, tup(null, "abc"));
        observer.assertRemoved(1, tup(null, "abc"));

        assertEquals(2, observer.getCallsNumber());
    }

    @Test
    public void testPutAll() {
        Map<String, String> map = new HashMap<>();
        map.put("oFoo", "OFoo");
        map.put("pFoo", "PFoo");
        map.put("foo", "foofoo");
        map.put("one", "1");
        this.map.putAll(map);

        assertTrue(this.map.containsKey("oFoo"));
        observer.assertMultipleCalls(call("oFoo", null, "OFoo"), call("pFoo", null, "PFoo"),
                call("foo", "bar", "foofoo"));
    }

    @Test
    public void testClear() {
        map.clear();

        assertTrue(map.isEmpty());
        observer.assertMultipleRemoved(tup("one", "1"), tup("two", "2"), tup("foo", "bar"));
    }

    @Test
    public void testOther() {
        assertEquals(3, map.size());
        assertFalse(map.isEmpty());

        assertTrue(map.containsKey("foo"));
        assertFalse(map.containsKey("bar"));

        assertFalse(map.containsValue("foo"));
        assertTrue(map.containsValue("bar"));
    }

    @Test
    public void testKeySet_Remove() {
        map.keySet().remove("one");
        map.keySet().remove("two");
        map.keySet().remove("three");

        observer.assertRemoved(0, tup("one", "1"));
        observer.assertRemoved(1, tup("two", "2"));
        assertEquals(2, observer.getCallsNumber());
    }

    @Test
    @SuppressWarnings("SlowAbstractSetRemoveAll")
    public void testKeySet_RemoveAll() {
        map.keySet().removeAll(Arrays.asList("one", "two", "three"));

        observer.assertMultipleRemoved(tup("one", "1"), tup("two", "2"));
        assertEquals(1, map.size());
    }

    @Test
    public void testKeySet_RetainAll() {
        map.keySet().retainAll(Arrays.asList("one", "two", "three"));

        observer.assertRemoved(tup("foo", "bar"));
        assertEquals(2, map.size());
    }

    @Test
    public void testKeySet_Clear() {
        map.keySet().clear();
        assertTrue(map.keySet().isEmpty());
        observer.assertMultipleRemoved(tup("one", "1"), tup("two", "2"), tup("foo", "bar"));
    }

    @Test
    public void testKeySet_Iterator() {
        Iterator<String> iterator = map.keySet().iterator();
        assertTrue(iterator.hasNext());

        String toBeRemoved = iterator.next();
        String toBeRemovedVal = map.get(toBeRemoved);
        iterator.remove();

        assertEquals(2, map.size());
        observer.assertRemoved(tup(toBeRemoved, toBeRemovedVal));
    }

    @Test
    @SuppressWarnings("RedundantCollectionOperation")
    public void testKeySet_Other() {
        assertEquals(3, map.keySet().size());
        assertTrue(map.keySet().contains("foo"));
        assertFalse(map.keySet().contains("bar"));

        assertTrue(map.keySet().containsAll(Arrays.asList("one", "two")));
        assertFalse(map.keySet().containsAll(Arrays.asList("one", "three")));

        assertEquals(3, map.keySet().toArray(new String[0]).length);
        assertEquals(3, map.keySet().toArray().length);
    }

    @Test
    public void testValues_Remove() {
        map.values().remove("1");
        map.values().remove("2");
        map.values().remove("3");

        observer.assertRemoved(0, tup("one", "1"));
        observer.assertRemoved(1, tup("two", "2"));
        assertEquals(2, observer.getCallsNumber());
    }

    @Test
    public void testValues_RemoveAll() {
        map.values().removeAll(Arrays.asList("1", "2", "3"));

        observer.assertMultipleRemoved(tup("one", "1"), tup("two", "2"));
        assertEquals(1, map.size());
    }

    @Test
    public void testValues_RetainAll() {
        map.values().retainAll(Arrays.asList("1", "2", "3"));

        observer.assertRemoved(tup("foo", "bar"));
        assertEquals(2, map.size());
    }

    @Test
    public void testValues_Clear() {
        map.values().clear();
        assertTrue(map.values().isEmpty());
        observer.assertMultipleRemoved(tup("one", "1"), tup("two", "2"), tup("foo", "bar"));
    }

    @Test
    public void testValues_Iterator() {
        Iterator<String> iterator = map.values().iterator();
        assertTrue(iterator.hasNext());

        String toBeRemovedVal = iterator.next();
        iterator.remove();

        assertEquals(2, map.size());
        observer.assertRemoved(tup(toBeRemovedVal.equals("1") ? "one"
                : toBeRemovedVal.equals("2") ? "two"
                : toBeRemovedVal.equals("bar") ? "foo" : null, toBeRemovedVal));
    }

    @Test
    @SuppressWarnings("RedundantCollectionOperation")
    public void testValues_Other() {
        assertEquals(3, map.values().size());
        assertFalse(map.values().contains("foo"));
        assertTrue(map.values().contains("bar"));

        assertTrue(map.values().containsAll(Arrays.asList("1", "2")));
        assertFalse(map.values().containsAll(Arrays.asList("1", "3")));

        assertEquals(3, map.values().toArray(new String[0]).length);
        assertEquals(3, map.values().toArray().length);
    }

    @Test
    public void testEntrySet_Remove() {
        map.entrySet().remove(entry("one", "1"));
        map.entrySet().remove(entry("two", "2"));
        map.entrySet().remove(entry("three", "3"));

        observer.assertRemoved(0, tup("one", "1"));
        observer.assertRemoved(1, tup("two", "2"));
        assertEquals(2, observer.getCallsNumber());
    }

    @Test
    @SuppressWarnings("SlowAbstractSetRemoveAll")
    public void testEntrySet_RemoveAll() {
        map.entrySet().removeAll(Arrays.asList(entry("one", "1"), entry("two", "2"), entry("three", "3")));

        observer.assertMultipleRemoved(tup("one", "1"), tup("two", "2"));
        assertEquals(1, map.size());
    }

    @Test
    public void testEntrySet_RetainAll() {
        map.entrySet().retainAll(Arrays.asList(entry("one", "1"), entry("two", "2"), entry("three", "3")));

        observer.assertRemoved(tup("foo", "bar"));
        assertEquals(2, map.size());
    }

    @Test
    public void testEntrySet_Clear() {
        map.entrySet().clear();
        assertTrue(map.entrySet().isEmpty());
        observer.assertMultipleRemoved(tup("one", "1"), tup("two", "2"), tup("foo", "bar"));
    }

    @Test
    public void testEntrySet_Iterator() {
        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        assertTrue(iterator.hasNext());

        Entry<String, String> toBeRemoved = iterator.next();
        String toBeRemovedKey = toBeRemoved.getKey();
        String toBeRemovedVal = toBeRemoved.getValue();

        iterator.remove();

        assertEquals(2, map.size());
        observer.assertRemoved(tup(toBeRemovedKey, toBeRemovedVal));
    }

    @Test
    public void testEntrySet_Other() {
        assertEquals(3, map.entrySet().size());
        assertTrue(map.entrySet().contains(entry("foo", "bar")));
        assertFalse(map.entrySet().contains(entry("bar", "foo")));

        assertTrue(map.entrySet().containsAll(Arrays.asList(entry("one", "1"), entry("two", "2"))));
        assertFalse(map.entrySet().containsAll(Arrays.asList(entry("one", "1"), entry("three", "3"))));

        assertEquals(3, map.entrySet().toArray(new Entry[0]).length);
        assertEquals(3, map.entrySet().toArray().length);
    }

    @Test
    public void testObserverCanRemoveObservers() {
        final MapChangeListener<String, String> mapObserver = change -> change.getMap().removeListener(observer);
        map.addListener(mapObserver);
        map.put("x", "x");
        observer.clear();
        map.put("y", "y");
        observer.check0();
        map.removeListener(mapObserver);

        final StringMapChangeListener listener = new StringMapChangeListener();
        map.addListener(listener);
        map.put("z", "z");
        assertEquals(1, listener.counter);
        map.put("zz", "zz");
        assertEquals(1, listener.counter);
    }

    @Test
    public void testEqualsAndHashCode() {
        final Map<String, String> other = new HashMap<>(map);
        assertTrue(map.equals(other));
        assertEquals(map.hashCode(), other.hashCode());
    }

    private <K, V> Entry<K, V> entry(final K key, final V value) {
        return new Entry<K, V>() {

            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }

            @Override
            public V setValue(V value) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof Entry)) {
                    return false;
                }
                Entry<?, ?> entry = (Entry<?, ?>) obj;
                return (getKey() == null ? entry.getKey() == null : getKey().equals(entry.getKey())) &&
                        (getValue() == null ? entry.getValue() == null : getValue().equals(entry.getValue()));
            }

            @Override
            public int hashCode() {
                return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
            }

        };
    }

    private static class StringMapChangeListener implements MapChangeListener<String, String> {

        private int counter;

        @Override
        public void onChanged(Change<? extends String, ? extends String> change) {
            change.getMap().removeListener(this);
            counter++;
        }

    }

}
