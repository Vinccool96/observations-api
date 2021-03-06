package io.github.vinccool96.observations.collections.transformation;

import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.property.SimpleObjectProperty;
import io.github.vinccool96.observations.collections.*;
import io.github.vinccool96.observations.sun.collections.NonIterableChange.SimplePermutationChange;
import io.github.vinccool96.observations.sun.collections.ObservableListWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

@SuppressWarnings("FieldMayBeFinal")
public class SortedListTest {

    private ObservableList<String> list;

    private MockListObserver<String> mockListObserver;

    private SortedList<String> sortedList;

    @Before
    public void setUp() {
        list = ObservableCollections.observableArrayList();
        list.addAll("a", "c", "d", "c");
        sortedList = list.sorted();
        mockListObserver = new MockListObserver<>();
        sortedList.addListener(mockListObserver);
    }

    @Test
    public void testNoChange() {
        assertEquals(Arrays.asList("a", "c", "c", "d"), sortedList);
        mockListObserver.check0();

        compareIndices();
    }

    @Test
    public void testAdd() {
        list.clear();
        mockListObserver.clear();
        assertEquals(Collections.emptyList(), sortedList);
        list.addAll("a", "c", "d", "c");
        assertEquals(Arrays.asList("a", "c", "c", "d"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.emptyList(), 0, 4);
        assertEquals(0, sortedList.getSourceIndex(0));
        assertEquals(2, sortedList.getSourceIndex(3));

        compareIndices();
    }

    private <E> int getViewIndex(SortedList<E> sorted, int sourceIndex) {
        for (int i = 0; i < sorted.size(); i++) {
            if (sourceIndex == sorted.getSourceIndex(i)) {
                return i;
            }
        }
        return -1;
    }

    private <E> void compareIndices(SortedList<E> sorted) {
        ObservableList<? extends E> source = sorted.getSource();
        for (int i = 0; i < sorted.size(); i++) {
            // i as a view index
            int sourceIndex = sorted.getSourceIndex(i);
            assertEquals(i, getViewIndex(sorted, sourceIndex));
            assertSame(sorted.get(i), source.get(sourceIndex));

            // i as a source index
            int viewIndex = getViewIndex(sorted, i);
            assertEquals(i, sorted.getSourceIndex(viewIndex));
            assertSame(source.get(i), sorted.get(viewIndex));
        }
    }

    private void compareIndices() {
        compareIndices(sortedList);
    }

    @Test
    public void testAddSingle() {
        list.add("b");
        assertEquals(Arrays.asList("a", "b", "c", "c", "d"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.emptyList(), 1, 2);
        assertEquals(0, sortedList.getSourceIndex(0));
        assertEquals(4, sortedList.getSourceIndex(1));
        assertEquals(1, sortedList.getSourceIndex(2));
        assertEquals(3, sortedList.getSourceIndex(3));
        assertEquals(2, sortedList.getSourceIndex(4));

        compareIndices();
    }

    @Test
    public void testRemove() {
        list.removeAll(Collections.singletonList("c")); // removes "c", "d", "c", adds "d"
        assertEquals(Arrays.asList("a", "d"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Arrays.asList("c", "c"), 1, 1);
        assertEquals(0, sortedList.getSourceIndex(0));
        assertEquals(1, sortedList.getSourceIndex(1));
        mockListObserver.clear();
        list.removeAll(Arrays.asList("a", "d"));
        mockListObserver.check1AddRemove(sortedList, Arrays.asList("a", "d"), 0, 0);

        compareIndices();
    }

    @Test
    public void testRemoveSingle() {
        list.remove("a");
        assertEquals(Arrays.asList("c", "c", "d"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.singletonList("a"), 0, 0);
        assertEquals(0, sortedList.getSourceIndex(0));
        assertEquals(2, sortedList.getSourceIndex(1));
        assertEquals(1, sortedList.getSourceIndex(2));

        compareIndices();
    }

    @Test
    public void testMultipleOperations() {
        list.remove(2);
        assertEquals(Arrays.asList("a", "c", "c"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.singletonList("d"), 3, 3);
        mockListObserver.clear();
        list.add("b");
        assertEquals(Arrays.asList("a", "b", "c", "c"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.emptyList(), 1, 2);

        compareIndices();
    }

    @Test
    public void testPureRemove() {
        list.removeAll(Arrays.asList("c", "d"));
        mockListObserver.check1AddRemove(sortedList, Arrays.asList("c", "c", "d"), 1, 1);
        assertEquals(0, sortedList.getSourceIndex(0));

        compareIndices();
    }

    @Test
    public void testChangeComparator() {
        SimpleObjectProperty<Comparator<String>> op = new SimpleObjectProperty<>(Comparator.naturalOrder());

        sortedList = new SortedList<>(list);
        assertEquals(Arrays.asList("a", "c", "d", "c"), sortedList);
        compareIndices();

        sortedList.comparatorProperty().bind(op);
        assertEquals(Arrays.asList("a", "c", "c", "d"), sortedList);
        compareIndices();

        sortedList.addListener(mockListObserver);

        op.set((String o1, String o2) -> -o1.compareTo(o2));
        assertEquals(Arrays.asList("d", "c", "c", "a"), sortedList);
        mockListObserver.check1Permutation(sortedList, new int[]{3, 1, 2, 0});
        // could be also 3, 2, 1, 0, but the algorithm goes this way
        compareIndices();

        mockListObserver.clear();
        op.set(null);
        assertEquals(Arrays.asList("a", "c", "d", "c"), sortedList);
        mockListObserver.check1Permutation(sortedList, new int[]{2, 1, 3, 0});
        compareIndices();
    }

    /**
     * A slightly updated test provided by "Kleopatra" (https://bugs.openjdk.java.net/browse/JDK-8112763)
     */
    @Test
    public void testSourceIndex() {
        final ObservableList<Double> sourceList = ObservableCollections.observableArrayList(1300.0, 400.0, 600.0);
        // the list to be removed again, note that its highest value is greater then the highest in the base list before
        // adding
        List<Double> other = Arrays.asList(50.0, -300.0, 4000.0);
        sourceList.addAll(other);
        // wrap into a sorted list and add a listener to the sorted
        final SortedList<Double> sorted = sourceList.sorted();
        ListChangeListener<Double> listener = c -> {
            assertEquals(Arrays.asList(400.0, 600.0, 1300.0), c.getList());

            c.next();
            assertEquals(Arrays.asList(-300.0, 50.0), c.getRemoved());
            assertEquals(0, c.getFrom());
            assertEquals(0, c.getTo());
            assertTrue(c.next());
            assertEquals(Collections.singletonList(4000.0), c.getRemoved());
            assertEquals(3, c.getFrom());
            assertEquals(3, c.getTo());
            assertFalse(c.next());

            // grab sourceIndex of last (aka: highest) value in sorted list
            int sourceIndex = sorted.getSourceIndex(sorted.size() - 1);
            assertEquals(0, sourceIndex);
        };
        sorted.addListener(listener);
        sourceList.removeAll(other);

        compareIndices(sorted);
    }

    @Test
    public void testMutableElement() {
        ObservableList<Person> list = createPersonsList();

        SortedList<Person> sorted = list.sorted();
        assertEquals(Arrays.asList(new Person("five"), new Person("four"), new Person("one"), new Person("three"),
                new Person("two")), sorted);
        MockListObserver<Person> listener = new MockListObserver<>();
        sorted.addListener(listener);
        list.get(3).name.set("zero"); // four -> zero
        ObservableList<Person> expected = ObservableCollections.observableArrayList(new Person("five"),
                new Person("one"), new Person("three"), new Person("two"), new Person("zero"));
        listener.checkPermutation(0, expected, 0, list.size(), new int[]{0, 4, 1, 2, 3});
        listener.checkUpdate(1, expected, 4, 5);
        assertEquals(expected, sorted);

        compareIndices(sorted);
    }

    @Test
    public void testMutableElementUnsorted_rt39541() {
        ObservableList<Person> list = createPersonsList();
        SortedList<Person> unsorted = new SortedList<>(list);
        MockListObserver<Person> listener = new MockListObserver<>();
        unsorted.addListener(listener);
        list.get(3).name.set("zero"); // four -> zero
        ObservableList<Person> expected = ObservableCollections.observableArrayList(new Person("one"),
                new Person("two"), new Person("three"), new Person("zero"), new Person("five"));
        listener.check1Update(expected, 3, 4);

        compareIndices(unsorted);
    }

    @Test
    public void testMutableElementUnsortedChain_rt39541() {
        ObservableList<Person> items = createPersonsList();

        SortedList<Person> sorted = items.sorted();
        SortedList<Person> unsorted = new SortedList<>(sorted);

        assertEquals(sorted, unsorted);

        MockListObserver<Person> listener = new MockListObserver<>();
        unsorted.addListener(listener);
        items.get(3).name.set("zero"); // "four" -> "zero"
        ObservableList<Person> expected = ObservableCollections.observableArrayList(new Person("five"),
                new Person("one"), new Person("three"), new Person("two"), new Person("zero"));
        listener.checkPermutation(0, expected, 0, expected.size(), new int[]{0, 4, 1, 2, 3});
        listener.checkUpdate(1, expected, 4, 5);
        assertEquals(expected, sorted);
        assertEquals(expected, unsorted);

        compareIndices(sorted);
        compareIndices(unsorted);
    }

    @Test
    public void testMutableElementSortedFilteredChain() {
        ObservableList<Person> items =
                ObservableCollections.observableArrayList((Person p) -> new Observable[]{p.name});
        items.addAll(new Person("b"), new Person("c"), new Person("a"), new Person("f"), new Person("e"),
                new Person("d"));

        FilteredList<Person> filtered = items.filtered(e -> !e.name.get().startsWith("z"));
        MockListObserver<Person> filterListener = new MockListObserver<>();
        filtered.addListener(filterListener);

        SortedList<Person> sorted = filtered.sorted(Comparator.comparing(x -> x.name.get()));
        MockListObserver<Person> sortListener = new MockListObserver<>();
        sorted.addListener(sortListener);
        items.get(2).name.set("z"); // "a" -> "z"
        filterListener.check1AddRemove(filtered, Collections.singletonList(new Person("z")), 2, 2);
        sortListener.check1AddRemove(sorted, Collections.singletonList(new Person("z")), 0, 0);
        ObservableList<Person> expected = ObservableCollections.observableArrayList(new Person("b"), new Person("c"),
                new Person("d"), new Person("e"), new Person("f"));
        assertEquals(expected, sorted);

        compareIndices(sorted);
    }

    private ObservableList<Person> createPersonsList() {
        ObservableList<Person> list = ObservableCollections.observableArrayList((Person p) -> new Observable[]{p.name});
        list.addAll(new Person("one"), new Person("two"), new Person("three"), new Person("four"),
                new Person("five"));
        return list;
    }

    @Test
    public void testNotComparable() {
        final Object o1 = new Object() {

            @Override
            public String toString() {
                return "c";
            }
        };
        final Object o2 = new Object() {

            @Override
            public String toString() {
                return "a";
            }
        };
        final Object o3 = new Object() {

            @Override
            public String toString() {
                return "d";
            }
        };
        ObservableList<Object> list = ObservableCollections.observableArrayList(o1, o2, o3);

        SortedList<Object> sorted = list.sorted();
        assertEquals(Arrays.asList(o2, o1, o3), sorted);

        compareIndices(sorted);
    }

    @Test
    public void testCompareNulls() {
        ObservableList<String> list = ObservableCollections.observableArrayList("g", "a", null, "z");

        SortedList<String> sorted = list.sorted();
        assertEquals(Arrays.asList(null, "a", "g", "z"), sorted);

        compareIndices(sorted);
    }

    private static class Permutator<E> extends ObservableListWrapper<E> {

        private List<E> backingList;

        public Permutator(List<E> list) {
            super(list);
            this.backingList = list;
        }

        public void swap() {
            E first = get(0);
            backingList.set(0, get(size() - 1));
            backingList.set(size() - 1, first);
            fireChange(new SimplePermutationChange<>(0, size(), new int[]{2, 1, 0}, this));
        }

    }

    /**
     * SortedList cant cope with permutations.
     */
    @Test
    public void testPermutate() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        Permutator<Integer> permutator = new Permutator<>(list);
        SortedList<Integer> sorted = new SortedList<>(permutator);
        permutator.swap();

        compareIndices(sorted);
    }

    @Test
    public void testUnsorted() {
        SortedList<String> sorted = new SortedList<>(list);
        assertEquals(sorted, list);
        assertEquals(list, sorted);

        list.removeAll("a", "d");

        assertEquals(sorted, list);

        list.addAll(0, Arrays.asList("a", "b", "c"));

        assertEquals(sorted, list);

        ObservableCollections.sort(list);

        assertEquals(sorted, list);

        compareIndices(sorted);
    }

    @Test
    public void testUnsorted2() {
        list.setAll("a", "b", "c", "d", "e", "f");
        SortedList<String> sorted = new SortedList<>(list);
        assertEquals(sorted, list);

        list.removeAll("b", "c", "d");

        assertEquals(sorted, list);

        compareIndices(sorted);
    }

    @Test
    public void testSortedNaturalOrder() {
        assertEquals(Arrays.asList("a", "c", "c", "d"), list.sorted());
    }

    @Test
    public void testRemoveFromDuplicates() {
        String toRemove = "A";
        String other = "A";
        list = ObservableCollections.observableArrayList(other, toRemove);
        Comparator<String> c = Comparator.naturalOrder();
        SortedList<String> sorted = list.sorted(c);

        list.remove(1);

        assertEquals(1, sorted.size());
        assertSame(sorted.get(0), other);

        compareIndices(sorted);
    }

    @Test
    public void testAddAllOnEmpty() {
        list = ObservableCollections.observableArrayList();
        SortedList<String> sl = list.sorted(String.CASE_INSENSITIVE_ORDER);
        list.addAll("B", "A");

        assertEquals(Arrays.asList("A", "B"), sl);

        compareIndices(sl);
    }

    @Test
    public void test_rt36353_sortedList() {
        ObservableList<String> data = ObservableCollections.observableArrayList("2", "1", "3");
        SortedList<String> sortedList = new SortedList<>(data);

        HashMap<Integer, Integer> pMap = new HashMap<>();
        sortedList.addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); i++) {
                        pMap.put(i, c.getPermutation(i));
                    }
                }
            }
        });

        Map<Integer, Integer> expected = new HashMap<>();

        // comparator that will create list of [1,2,3]. Sort indices based on previous order [2,1,3].
        sortedList.setComparator(Comparator.naturalOrder());
        assertEquals(ObservableCollections.observableArrayList("1", "2", "3"), sortedList);
        expected.put(0, 1);     // item "2" has moved from index 0 to index 1
        expected.put(1, 0);     // item "1" has moved from index 1 to index 0
        expected.put(2, 2);     // item "3" has remained in index 2
        assertEquals(expected, pMap);
        compareIndices(sortedList);

        // comparator that will create list of [3,2,1]. Sort indices based on previous order [1,2,3].
        sortedList.setComparator(Comparator.reverseOrder());
        assertEquals(ObservableCollections.observableArrayList("3", "2", "1"), sortedList);
        expected.clear();
        expected.put(0, 2);     // item "1" has moved from index 0 to index 2
        expected.put(1, 1);     // item "2" has remained in index 1
        expected.put(2, 0);     // item "3" has moved from index 2 to index 0
        assertEquals(expected, pMap);
        compareIndices(sortedList);

        // null comparator so sort order should return to [2,1,3]. Sort indices based on previous order [3,2,1].
        sortedList.setComparator(null);
        assertEquals(ObservableCollections.observableArrayList("2", "1", "3"), sortedList);
        expected.clear();
        expected.put(0, 2);     // item "3" has moved from index 0 to index 2
        expected.put(1, 0);     // item "2" has moved from index 1 to index 0
        expected.put(2, 1);     // item "1" has moved from index 2 to index 1
        assertEquals(expected, pMap);
        compareIndices(sortedList);
    }

    @Test
    public void testAddWhenUnsorted() {
        sortedList.setComparator(null);
        mockListObserver.clear();
        list.add(2, "b");
        assertEquals(5, sortedList.size());
        assertEquals(Arrays.asList("a", "c", "b", "d", "c"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.emptyList(), 2, 3);
        compareIndices();

        mockListObserver.clear();
        sortedList.setComparator(Comparator.naturalOrder());
        mockListObserver.check1Permutation(sortedList, new int[]{0, 2, 1, 4, 3});
        assertEquals(5, sortedList.size());
        assertEquals(Arrays.asList("a", "b", "c", "c", "d"), sortedList);
        compareIndices();

        mockListObserver.clear();
        sortedList.setComparator(null);
        assertEquals(5, sortedList.size());
        assertEquals(Arrays.asList("a", "c", "b", "d", "c"), sortedList);
        mockListObserver.check1Permutation(sortedList, new int[]{0, 2, 1, 4, 3});
        compareIndices();
    }

    @Test
    public void testRemoveWhenUnsorted() {
        sortedList.setComparator(null);
        mockListObserver.clear();
        list.remove(1);
        assertEquals(3, sortedList.size());
        assertEquals(Arrays.asList("a", "d", "c"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.singletonList("c"), 1, 1);
        compareIndices();

        mockListObserver.clear();
        sortedList.setComparator(Comparator.naturalOrder());
        mockListObserver.check1Permutation(sortedList, new int[]{0, 2, 1});
        assertEquals(3, sortedList.size());
        assertEquals(Arrays.asList("a", "c", "d"), sortedList);
        compareIndices();

        mockListObserver.clear();
        sortedList.setComparator(null);
        assertEquals(3, sortedList.size());
        assertEquals(Arrays.asList("a", "d", "c"), sortedList);
        mockListObserver.check1Permutation(sortedList, new int[]{0, 2, 1});
        compareIndices();
    }

    @Test
    public void testSetWhenUnsorted() {
        sortedList.setComparator(null);
        mockListObserver.clear();
        list.set(1, "e");
        assertEquals(4, sortedList.size());
        assertEquals(Arrays.asList("a", "e", "d", "c"), sortedList);
        mockListObserver.check1AddRemove(sortedList, Collections.singletonList("c"), 1, 2);
        compareIndices();

        mockListObserver.clear();
        sortedList.setComparator(Comparator.naturalOrder());
        mockListObserver.check1Permutation(sortedList, new int[]{0, 3, 2, 1});
        assertEquals(4, sortedList.size());
        assertEquals(Arrays.asList("a", "c", "d", "e"), sortedList);
        compareIndices();

        mockListObserver.clear();
        sortedList.setComparator(null);
        assertEquals(4, sortedList.size());
        assertEquals(Arrays.asList("a", "e", "d", "c"), sortedList);
        mockListObserver.check1Permutation(sortedList, new int[]{0, 3, 2, 1});
        compareIndices();
    }

}
