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

    private ObservableSet<String> observableSet;

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
        observableSet = setFactory.call();
        observer = new MockSetObserver<>();
        observableSet.addListener(observer);

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
        observableSet.clear();
        observableSet.addAll(Arrays.asList(strings));
        observer.clear();
    }

    @Test
    public void testAddRemove() {
        observableSet.add("observedFoo");
        observableSet.add("foo");
        assertTrue(observableSet.contains("observedFoo"));

        observableSet.remove("observedFoo");
        observableSet.remove("foo");
        observableSet.remove("bar");
        observableSet.add("one");

        assertFalse(observableSet.contains("foo"));

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
        observableSet.addAll(set);

        assertTrue(observableSet.contains("oFoo"));
        observer.assertMultipleCalls(call(null, "oFoo"), call(null, "pFoo"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveAll() {
        observableSet.removeAll(Arrays.asList("one", "two", "three"));

        observer.assertMultipleRemoved(tup("one"), tup("two"));
        assertEquals(1, observableSet.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClear() {
        observableSet.clear();

        assertTrue(observableSet.isEmpty());
        observer.assertMultipleRemoved(tup("one"), tup("two"), tup("foo"));

    }

    @Test
    public void testRetainAll() {
        observableSet.retainAll(Arrays.asList("one", "two", "three"));

        observer.assertRemoved(tup("foo"));
        assertEquals(2, observableSet.size());
    }

    @Test
    public void testIterator() {
        Iterator<String> iterator = observableSet.iterator();
        assertTrue(iterator.hasNext());

        String toBeRemoved = iterator.next();
        iterator.remove();

        assertEquals(2, observableSet.size());
        observer.assertRemoved(tup(toBeRemoved));
    }

    @Test
    public void testOther() {
        assertEquals(3, observableSet.size());
        assertFalse(observableSet.isEmpty());

        assertTrue(observableSet.contains("foo"));
        assertFalse(observableSet.contains("bar"));
    }

    @Test
    public void testNull() {
        if (setFactory instanceof CallableTreeSetImpl) {
            return; // TreeSet doesn't accept nulls
        }
        observableSet.add(null);
        assertEquals(4, observableSet.size());
        observer.assertAdded(tup(null));

        observableSet.remove(null);
        assertEquals(3, observableSet.size());
        observer.assertRemoved(tup(null));
    }

    @Test
    public void testObserverCanRemoveObservers() {
        final SetChangeListener<String> setObserver = change -> change.getSet().removeListener(observer);
        observableSet.addListener(setObserver);
        observableSet.add("x");
        observer.clear();
        observableSet.add("y");
        observer.check0();
        observableSet.removeListener(setObserver);

        final StringSetChangeListener listener = new StringSetChangeListener();
        observableSet.addListener(listener);
        observableSet.add("z");
        assertEquals(1, listener.counter);
        observableSet.add("zz");
        assertEquals(1, listener.counter);
    }

    @Test
    public void testEqualsAndHashCode() {
        final Set<String> other = new HashSet<>(Arrays.asList("one", "two", "foo"));
        assertTrue(observableSet.equals(other));
        assertEquals(other.hashCode(), observableSet.hashCode());
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
