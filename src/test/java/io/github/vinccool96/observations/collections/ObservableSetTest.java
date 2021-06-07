package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.collections.TestedObservableSets.CallableTreeSetImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static io.github.vinccool96.observations.collections.MockSetObserver.Call.call;
import static io.github.vinccool96.observations.collections.MockSetObserver.Tuple.tup;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
@SuppressWarnings({"SimplifiableAssertion", "ConstantConditions"})
public class ObservableSetTest {

    final Callable<ObservableSet<String>> setFactory;

    private ObservableSet<String> set;

    private MockSetObserver<String> observer;

    public ObservableSetTest(final Callable<ObservableSet<String>> setFactory) {
        this.setFactory = setFactory;
    }

    @Parameterized.Parameters
    @SuppressWarnings("unchecked")
    public static Collection<?> createParameters() {
        Callable<ObservableSet<String>>[][] data = new Callable[][]{
                {TestedObservableSets.HASH_SET},
                {TestedObservableSets.TREE_SET},
                {TestedObservableSets.LINKED_HASH_SET},
                {TestedObservableSets.CHECKED_OBSERVABLE_HASH_SET},
                {TestedObservableSets.SYNCHRONIZED_OBSERVABLE_HASH_SET},
                {TestedObservableSets.OBSERVABLE_SET_PROPERTY}
        };
        return Arrays.asList(data);
    }

    @Before
    public void setUp() throws Exception {
        set = setFactory.call();
        observer = new MockSetObserver<>();
        set.addListener(observer);

        useSetData("one", "two", "foo");
    }

    /**
     * Modifies the set in the fixture to use the strings passed in instead of the default strings, and re-creates the
     * observable set and the observer. If no strings are passed in, the result is an empty set.
     *
     * @param strings
     *         the strings to use for the list in the fixture
     */
    void useSetData(String... strings) {
        set.clear();
        set.addAll(Arrays.asList(strings));
        observer.clear();
    }

    @Test
    public void testAddRemove() {
        set.add("observedFoo");
        set.add("foo");
        assertTrue(set.contains("observedFoo"));

        set.remove("observedFoo");
        set.remove("foo");
        set.remove("bar");
        set.add("one");

        assertFalse(set.contains("foo"));

        observer.assertAdded(0, tup("observedFoo"));
        observer.assertRemoved(1, tup("observedFoo"));
        observer.assertRemoved(2, tup("foo"));

        assertEquals(3, observer.getCallsNumber());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddAll() {
        Set<String> set = new HashSet<>();
        set.add("oFoo");
        set.add("pFoo");
        set.add("foo");
        set.add("one");
        this.set.addAll(set);

        assertTrue(this.set.contains("oFoo"));
        observer.assertMultipleCalls(call(null, "oFoo"), call(null, "pFoo"));
    }

    @Test
    @SuppressWarnings({"unchecked", "SlowAbstractSetRemoveAll"})
    public void testRemoveAll() {
        set.removeAll(Arrays.asList("one", "two", "three"));

        observer.assertMultipleRemoved(tup("one"), tup("two"));
        assertEquals(1, set.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClear() {
        set.clear();

        assertTrue(set.isEmpty());
        observer.assertMultipleRemoved(tup("one"), tup("two"), tup("foo"));

    }

    @Test
    public void testRetainAll() {
        set.retainAll(Arrays.asList("one", "two", "three"));

        observer.assertRemoved(tup("foo"));
        assertEquals(2, set.size());
    }

    @Test
    public void testIterator() {
        Iterator<String> iterator = set.iterator();
        assertTrue(iterator.hasNext());

        String toBeRemoved = iterator.next();
        iterator.remove();

        assertEquals(2, set.size());
        observer.assertRemoved(tup(toBeRemoved));
    }

    @Test
    public void testOther() {
        assertEquals(3, set.size());
        assertFalse(set.isEmpty());

        assertTrue(set.contains("foo"));
        assertFalse(set.contains("bar"));
    }

    @Test
    public void testNull() {
        if (setFactory instanceof CallableTreeSetImpl) {
            return; // TreeSet doesn't accept nulls
        }
        set.add(null);
        assertEquals(4, set.size());
        observer.assertAdded(tup(null));

        set.remove(null);
        assertEquals(3, set.size());
        observer.assertRemoved(tup(null));
    }

    @Test
    public void testObserverCanRemoveObservers() {
        final SetChangeListener<String> setObserver = change -> change.getSet().removeListener(observer);
        set.addListener(setObserver);
        set.add("x");
        observer.clear();
        set.add("y");
        observer.check0();
        set.removeListener(setObserver);

        final StringSetChangeListener listener = new StringSetChangeListener();
        set.addListener(listener);
        set.add("z");
        assertEquals(1, listener.counter);
        set.add("zz");
        assertEquals(1, listener.counter);
    }

    @Test
    public void testEqualsAndHashCode() {
        final Set<String> other = new HashSet<>(Arrays.asList("one", "two", "foo"));
        assertTrue(set.equals(other));
        assertEquals(other.hashCode(), set.hashCode());
    }

    private static class StringSetChangeListener implements SetChangeListener<String> {

        private int counter;

        @Override
        public void onChanged(final Change<? extends String> change) {
            change.getSet().removeListener(this);
            ++counter;
        }

    }

}
