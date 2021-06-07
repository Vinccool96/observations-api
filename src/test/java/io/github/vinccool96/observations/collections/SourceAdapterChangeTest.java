package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.collections.transformation.SortedList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SourceAdapterChangeTest {

    @FunctionalInterface
    public interface ListFactory<E> {

        public ObservableList<E> createList(ObservableList<E> items);

    }

    private static final ListFactory unmodifiableObservableList =
            items -> ObservableCollections.unmodifiableObservableList(items);

    private static final ListFactory checkedObservableList =
            items -> ObservableCollections.checkedObservableList(items, Person.class);

    private static final ListFactory synchronizedObservableList =
            items -> ObservableCollections.synchronizedObservableList(items);

    @Parameterized.Parameters
    public static Collection createParameters() {
        Object[][] data = new Object[][]{
                {unmodifiableObservableList},
                {checkedObservableList},
                {synchronizedObservableList},
        };
        return Arrays.asList(data);
    }

    final ListFactory listFactory;

    ObservableList<Person> items;

    ObservableList<Person> list;

    MockListObserver<Person> mlo;

    public SourceAdapterChangeTest(ListFactory listFactory) {
        this.listFactory = listFactory;
    }

    @Before
    public void setUp() throws Exception {
        items = ObservableCollections.observableArrayList(
                (Person p) -> new Observable[]{p.name});
        items.addAll(
                new Person("one"), new Person("two"), new Person("three"),
                new Person("four"), new Person("five"));
        list = listFactory.createList(items);
        mlo = new MockListObserver<>();
        list.addListener(mlo);
    }

    @Test
    public void testUpdate() {
        items.get(3).name.set("zero"); // four -> zero
        ObservableList<Person> expected = ObservableCollections.observableArrayList(
                new Person("one"), new Person("two"), new Person("three"),
                new Person("zero"), new Person("five"));
        mlo.check1Update(expected, 3, 4);
    }

    @Test
    public void testPermutation() {
        ObservableCollections.sort(items);
        ObservableList<Person> expected = ObservableCollections.observableArrayList(
                new Person("five"), new Person("four"), new Person("one"),
                new Person("three"), new Person("two"));
        mlo.check1Permutation(expected, new int[]{2, 4, 3, 1, 0});
    }

    @Test
    public void testPermutationUpdate() {
        SortedList<Person> sorted = items.sorted((o1, o2) -> o1.compareTo(o2));
        list.removeListener(mlo);
        list = listFactory.createList(sorted);
        list.addListener(mlo);
        items.get(3).name.set("zero"); // four -> zero
        ObservableList<Person> expected = ObservableCollections.observableArrayList(
                new Person("five"), new Person("one"), new Person("three"),
                new Person("two"), new Person("zero"));
        mlo.checkPermutation(0, expected, 0, expected.size(), new int[]{0, 4, 1, 2, 3});
        mlo.checkUpdate(1, expected, 4, 5);
    }

}
