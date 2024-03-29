package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.sun.collections.ElementObservableListDecorator;
import io.github.vinccool96.observations.util.Callback;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ObservableListWithExtractorTest {

    private final Mode mode;

    private ObservableList<Person> modifiedList;

    private ObservableList<Person> observedList;

    private MockListObserver<Person> obs;

    private Person p0;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{Mode.OBSERVABLE_LIST_WRAPPER}, {Mode.DECORATOR}});
    }

    public ObservableListWithExtractorTest(Mode mode) {
        this.mode = mode;
    }

    @Before
    public void setUp() {
        p0 = new Person();
        obs = new MockListObserver<>();
        Callback<Person, Observable[]> callback = param -> new Observable[]{param.name};
        if (mode == Mode.OBSERVABLE_LIST_WRAPPER) {
            observedList = modifiedList = ObservableCollections.observableArrayList(callback);
        } else {
            modifiedList = ObservableCollections.observableArrayList();
            observedList = new ElementObservableListDecorator<>(modifiedList, callback);
        }

        modifiedList.add(p0);
        observedList.addListener(obs);
    }

    private void updateP0() {
        p0.name.set("bar");
    }

    @Test
    public void testUpdate_add() {
        updateP0();
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_add1() {
        modifiedList.clear();
        modifiedList.add(0, p0);
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_addAll() {
        modifiedList.clear();
        modifiedList.addAll(Arrays.asList(p0, p0));
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 2);
    }

    @Test
    public void testUpdate_addAll1() {
        modifiedList.clear();
        modifiedList.addAll(0, Arrays.asList(p0, p0));
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 2);
    }

    @Test
    public void testUpdate_addAll2() {
        modifiedList.clear();
        modifiedList.addAll(p0, p0);
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 2);
    }

    @Test
    public void testUpdate_set() {
        Person p1 = new Person();
        modifiedList.set(0, p1);
        obs.clear();
        updateP0();
        obs.check0();
        p1.name.set("bar");
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_setAll() {
        Person p1 = new Person();
        modifiedList.setAll(p1);
        obs.clear();
        updateP0();
        obs.check0();
        p1.name.set("bar");
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_remove() {
        modifiedList.remove(p0);
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_remove1() {
        modifiedList.remove(0);
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_removeAll() {
        modifiedList.removeAll(p0);
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_retainAll() {
        modifiedList.retainAll();
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_iterator_add() {
        modifiedList.clear();
        modifiedList.listIterator().add(p0);
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_iterator_set() {
        Person p1 = new Person();
        ListIterator<Person> listIterator = modifiedList.listIterator();
        listIterator.next();
        listIterator.set(p1);
        obs.clear();
        updateP0();
        obs.check0();
        p1.name.set("bar");
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_sublist_add() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.add(p0);
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 2);
    }

    @Test
    public void testUpdate_sublist_add1() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.clear();
        sublist.add(0, p0);
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_sublist_addAll() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.clear();
        sublist.addAll(Arrays.asList(p0, p0));
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 2);
    }

    @Test
    public void testUpdate_sublist_addAll1() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.clear();
        sublist.addAll(0, Arrays.asList(p0, p0));
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 2);
    }

    @Test
    public void testUpdate_sublist_set() {
        List<Person> sublist = modifiedList.subList(0, 1);
        Person p1 = new Person();
        sublist.set(0, p1);
        obs.clear();
        updateP0();
        obs.check0();
        p1.name.set("bar");
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_sublist_remove() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.remove(p0);
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_sublist_remove1() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.remove(0);
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_sublist_removeAll() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.removeAll(Collections.singletonList(p0));
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_sublist_retainAll() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.retainAll(Collections.<Person>emptyList());
        obs.clear();
        updateP0();
        obs.check0();
    }

    @Test
    public void testUpdate_iterator_sublist_add() {
        List<Person> sublist = modifiedList.subList(0, 1);
        sublist.clear();
        sublist.listIterator().add(p0);
        obs.clear();
        updateP0();
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testUpdate_iterator_sublist_set() {
        List<Person> sublist = modifiedList.subList(0, 1);
        Person p1 = new Person();
        ListIterator<Person> listIterator = sublist.listIterator();
        listIterator.next();
        listIterator.set(p1);
        obs.clear();
        updateP0();
        obs.check0();
        p1.name.set("bar");
        obs.check1Update(observedList, 0, 1);
    }

    @Test
    public void testMultipleUpdate() {
        modifiedList.add(new Person());
        modifiedList.addAll(p0, p0);

        obs.clear();

        updateP0();

        obs.checkUpdate(0, observedList, 0, 1);
        obs.checkUpdate(1, observedList, 2, 4);
        assertEquals(2, obs.calls.size());
    }

    @Test
    public void testPreFilledList() {
        ArrayList<Person> arrayList = new ArrayList<>();
        arrayList.add(p0);
        obs = new MockListObserver<>();
        Callback<Person, Observable[]> callback = param -> new Observable[]{param.name};
        if (mode == Mode.OBSERVABLE_LIST_WRAPPER) {
            observedList = modifiedList = ObservableCollections.observableList(arrayList, callback);
        } else {
            modifiedList = ObservableCollections.observableArrayList(arrayList);
            observedList = new ElementObservableListDecorator<>(modifiedList, callback);
        }

        observedList.addListener(obs);

        updateP0();

        obs.check1Update(observedList, 0, 1);
    }

    private enum Mode {

        OBSERVABLE_LIST_WRAPPER,

        DECORATOR

    }

}
