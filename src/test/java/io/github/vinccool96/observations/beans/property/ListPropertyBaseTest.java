package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.value.ChangeListenerMock;
import io.github.vinccool96.observations.beans.value.ObservableObjectValueStub;
import io.github.vinccool96.observations.collections.MockListObserver;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.collections.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings({"SimplifiableAssertion", "MismatchedQueryAndUpdateOfCollection", "ConstantConditions"})
public class ListPropertyBaseTest {

    private static final Object NO_BEAN = null;

    private static final String NO_NAME_1 = null;

    private static final String NO_NAME_2 = "";

    private static final ObservableList<Object> UNDEFINED = ObservableCollections.observableArrayList();

    private static final ObservableList<Object> VALUE_1a = ObservableCollections.observableArrayList();

    private static final ObservableList<Object> VALUE_1b = ObservableCollections.observableArrayList(new Object());

    private static final ObservableList<Object> VALUE_2a = ObservableCollections.observableArrayList(new Object(),
            new Object());

    private static final ObservableList<Object> VALUE_2b = ObservableCollections.observableArrayList(new Object(),
            new Object(), new Object());

    private static final List<Object> EMPTY_LIST = Collections.emptyList();

    private ListPropertyMock property;

    private InvalidationListenerMock invalidationListener;

    private ChangeListenerMock<ObservableList<Object>> changeListener;

    private MockListObserver<Object> listChangeListener;

    @Before
    public void setUp() throws Exception {
        property = new ListPropertyMock();
        invalidationListener = new InvalidationListenerMock();
        changeListener = new ChangeListenerMock<>(UNDEFINED);
        listChangeListener = new MockListObserver<>();
    }

    private void attachInvalidationListener() {
        property.addListener(invalidationListener);
        property.get();
        invalidationListener.reset();
    }

    private void attachChangeListener() {
        property.addListener(changeListener);
        property.get();
        changeListener.reset();
    }

    private void attachListChangeListener() {
        property.addListener(listChangeListener);
        property.get();
        listChangeListener.clear();
    }

    @Test
    public void testConstructor() {
        final ListProperty<Object> p1 = new ListPropertyMock();
        assertEquals(null, p1.get());
        assertEquals(null, p1.getValue());
        assertFalse(p1.isBound());

        final ListProperty<Object> p2 = new ListPropertyMock(VALUE_1b);
        assertEquals(VALUE_1b, p2.get());
        assertEquals(VALUE_1b, p2.getValue());
        assertFalse(p1.isBound());
    }

    @Test
    public void testEmptyProperty() {
        assertEquals("empty", property.emptyProperty().getName());
        assertSame(property, property.emptyProperty().getBean());
        assertTrue(property.emptyProperty().get());

        property.set(VALUE_2a);
        assertFalse(property.emptyProperty().get());
        property.set(VALUE_1a);
        assertTrue(property.emptyProperty().get());
    }

    @Test
    public void testSizeProperty() {
        assertEquals("size", property.sizeProperty().getName());
        assertSame(property, property.sizeProperty().getBean());
        assertEquals(0, property.sizeProperty().get());

        property.set(VALUE_2a);
        assertEquals(2, property.sizeProperty().get());
        property.set(VALUE_1a);
        assertEquals(0, property.sizeProperty().get());
    }

    @Test
    public void testInvalidationListener() {
        attachInvalidationListener();
        property.set(VALUE_2a);
        invalidationListener.check(property, 1);
        property.removeListener(invalidationListener);
        invalidationListener.reset();
        property.set(VALUE_1a);
        invalidationListener.check(null, 0);
    }

    @Test
    public void testChangeListener() {
        attachChangeListener();
        property.set(VALUE_2a);
        changeListener.check(property, null, VALUE_2a, 1);
        property.removeListener(changeListener);
        changeListener.reset();
        property.set(VALUE_1a);
        changeListener.check(null, UNDEFINED, UNDEFINED, 0);
    }

    @Test
    public void testListChangeListener() {
        attachListChangeListener();
        property.set(VALUE_2a);
        listChangeListener.check1AddRemove(property, EMPTY_LIST, 0, 2);
        property.removeListener(listChangeListener);
        listChangeListener.clear();
        property.set(VALUE_1a);
        listChangeListener.check0();
    }

    @Test
    public void testSourceList_Invalidation() {
        final ObservableList<Object> source1 = ObservableCollections.observableArrayList();
        final ObservableList<Object> source2 = ObservableCollections.observableArrayList();
        final Object value1 = new Object();
        final Object value2 = new Object();

        // constructor
        property = new ListPropertyMock(source1);
        property.reset();
        attachInvalidationListener();

        // add element
        source1.add(value1);
        assertEquals(value1, property.get(0));
        property.check(1);
        invalidationListener.check(property, 1);

        // replace element
        source1.set(0, value2);
        assertEquals(value2, property.get(0));
        property.check(1);
        invalidationListener.check(property, 1);

        // remove element
        source1.remove(0);
        assertTrue(property.isEmpty());
        property.check(1);
        invalidationListener.check(property, 1);

        // set
        property.set(source2);
        property.get();
        property.reset();
        invalidationListener.reset();

        // add element
        source2.add(0, value1);
        assertEquals(value1, property.get(0));
        property.check(1);
        invalidationListener.check(property, 1);

        // replace element
        source2.set(0, value2);
        assertEquals(value2, property.get(0));
        property.check(1);
        invalidationListener.check(property, 1);

        // remove element
        source2.remove(0);
        assertTrue(property.isEmpty());
        property.check(1);
        invalidationListener.check(property, 1);
    }

    @Test
    public void testSourceList_Change() {
        final ObservableList<Object> source1 = ObservableCollections.observableArrayList();
        final ObservableList<Object> source2 = ObservableCollections.observableArrayList();
        final Object value1 = new Object();
        final Object value2 = new Object();

        // constructor
        property = new ListPropertyMock(source1);
        property.reset();
        attachChangeListener();

        // add element
        source1.add(value1);
        assertEquals(value1, property.get(0));
        property.check(1);
        changeListener.check(property, source1, source1, 1);

        // replace element
        source1.set(0, value2);
        assertEquals(value2, property.get(0));
        property.check(1);
        changeListener.check(property, source1, source1, 1);

        // remove element
        source1.remove(0);
        assertTrue(property.isEmpty());
        property.check(1);
        changeListener.check(property, source1, source1, 1);

        // set
        property.set(source2);
        property.get();
        property.reset();
        changeListener.reset();

        // add element
        source2.add(0, value1);
        assertEquals(value1, property.get(0));
        property.check(1);
        changeListener.check(property, source2, source2, 1);

        // replace element
        source2.set(0, value2);
        assertEquals(value2, property.get(0));
        property.check(1);
        changeListener.check(property, source2, source2, 1);

        // remove element
        source2.remove(0);
        assertTrue(property.isEmpty());
        property.check(1);
        changeListener.check(property, source2, source2, 1);
    }

    @Test
    public void testSourceList_ListChange() {
        final ObservableList<Object> source1 = ObservableCollections.observableArrayList();
        final ObservableList<Object> source2 = ObservableCollections.observableArrayList();
        final Object value1 = new Object();
        final Object value2 = new Object();

        // constructor
        property = new ListPropertyMock(source1);
        property.reset();
        attachListChangeListener();

        // add element
        source1.add(value1);
        assertEquals(value1, property.get(0));
        property.check(1);
        listChangeListener.check1AddRemove(property, EMPTY_LIST, 0, 1);
        listChangeListener.clear();

        // replace element
        source1.set(0, value2);
        assertEquals(value2, property.get(0));
        property.check(1);
        listChangeListener.check1AddRemove(property, Collections.singletonList(value1), 0, 1);
        listChangeListener.clear();

        // remove element
        source1.remove(0);
        assertTrue(property.isEmpty());
        property.check(1);
        listChangeListener.check1AddRemove(property, Collections.singletonList(value2), 0, 0);
        listChangeListener.clear();

        // set
        property.set(source2);
        property.get();
        property.reset();
        listChangeListener.clear();

        // add element
        source2.add(0, value1);
        assertEquals(value1, property.get(0));
        property.check(1);
        listChangeListener.check1AddRemove(property, EMPTY_LIST, 0, 1);
        listChangeListener.clear();

        // replace element
        source2.set(0, value2);
        assertEquals(value2, property.get(0));
        property.check(1);
        listChangeListener.check1AddRemove(property, Collections.singletonList(value1), 0, 1);
        listChangeListener.clear();

        // remove element
        source2.remove(0);
        assertTrue(property.isEmpty());
        property.check(1);
        listChangeListener.check1AddRemove(property, Collections.singletonList(value2), 0, 0);
        listChangeListener.clear();
    }

    @Test
    public void testList_Invalidation() {
        attachInvalidationListener();

        // set value once
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        invalidationListener.check(property, 1);

        // set same value again
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        invalidationListener.check(null, 0);

        // set value twice without reading
        property.set(VALUE_1a);
        property.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(1);
        invalidationListener.check(property, 1);
    }

    @Test
    public void testList_Change() {
        attachChangeListener();

        // set value once
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        changeListener.check(property, null, VALUE_2a, 1);

        // set same value again
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        changeListener.check(null, UNDEFINED, UNDEFINED, 0);

        // set value twice without reading
        property.set(VALUE_1a);
        property.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        changeListener.check(property, VALUE_1a, VALUE_1b, 2);
    }

    @Test
    public void testList_ListChange() {
        attachListChangeListener();

        // set value once
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        listChangeListener.check1AddRemove(property, EMPTY_LIST, 0, 2);

        // set same value again
        listChangeListener.clear();
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        listChangeListener.check0();

        // set value twice without reading
        property.set(VALUE_1a);
        listChangeListener.clear();
        property.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        listChangeListener.check1AddRemove(property, VALUE_1a, 0, 1);
    }

    @Test
    public void testListValue_Invalidation() {
        attachInvalidationListener();

        // set value once
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        invalidationListener.check(property, 1);

        // set same value again
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        invalidationListener.check(null, 0);

        // set value twice without reading
        property.setValue(VALUE_1a);
        property.setValue(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(1);
        invalidationListener.check(property, 1);
    }

    @Test
    public void testListValue_Change() {
        attachChangeListener();

        // set value once
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        changeListener.check(property, null, VALUE_2a, 1);

        // set same value again
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        changeListener.check(null, UNDEFINED, UNDEFINED, 0);

        // set value twice without reading
        property.setValue(VALUE_1a);
        property.setValue(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        changeListener.check(property, VALUE_1a, VALUE_1b, 2);
    }

    @Test
    public void testListValue_ListChange() {
        attachListChangeListener();

        // set value once
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        listChangeListener.check1AddRemove(property, EMPTY_LIST, 0, 2);

        // set same value again
        listChangeListener.clear();
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        listChangeListener.check0();

        // set value twice without reading
        property.setValue(VALUE_1a);
        listChangeListener.clear();
        property.setValue(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        listChangeListener.check1AddRemove(property, VALUE_1a, 0, 1);
    }

    @Test(expected = RuntimeException.class)
    public void testListBoundValue() {
        final ListProperty<Object> v = new SimpleListProperty<>(VALUE_2b);
        property.bind(v);
        property.set(VALUE_1a);
    }

    @Test
    public void testBind_Invalidation() {
        attachInvalidationListener();
        final ObservableObjectValueStub<ObservableList<Object>> v =
                new ObservableObjectValueStub<>(ObservableCollections.observableArrayList(VALUE_1a));

        property.bind(v);
        assertEquals(VALUE_1a, property.get());
        assertTrue(property.isBound());
        property.check(1);
        invalidationListener.check(property, 1);

        // change binding once
        v.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        invalidationListener.check(property, 1);

        // change binding twice without reading
        v.set(VALUE_1a);
        v.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(1);
        invalidationListener.check(property, 1);

        // change binding twice to same value
        v.set(VALUE_1a);
        v.set(VALUE_1a);
        assertEquals(VALUE_1a, property.get());
        property.check(1);
        invalidationListener.check(property, 1);
    }

    @Test
    public void testBind_Change() {
        attachChangeListener();
        final ObservableObjectValueStub<ObservableList<Object>> v =
                new ObservableObjectValueStub<>(ObservableCollections.observableArrayList(VALUE_1a));

        property.bind(v);
        assertEquals(VALUE_1a, property.get());
        assertTrue(property.isBound());
        property.check(1);
        changeListener.check(property, null, VALUE_1a, 1);

        // change binding once
        v.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        changeListener.check(property, VALUE_1a, VALUE_2a, 1);

        // change binding twice without reading
        v.set(VALUE_1a);
        v.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        changeListener.check(property, VALUE_1a, VALUE_1b, 2);

        // change binding twice to same value
        v.set(VALUE_1a);
        v.set(VALUE_1a);
        assertEquals(VALUE_1a, property.get());
        property.check(2);
        changeListener.check(property, VALUE_1b, VALUE_1a, 1);
    }

    @Test
    public void testBind_ListChange() {
        attachListChangeListener();
        final ObservableObjectValueStub<ObservableList<Object>> v =
                new ObservableObjectValueStub<>(ObservableCollections.observableArrayList(VALUE_1a));

        property.bind(v);
        assertEquals(VALUE_1a, property.get());
        assertTrue(property.isBound());
        property.check(1);
        listChangeListener.check1AddRemove(property, EMPTY_LIST, 0, 0);

        // change binding once
        listChangeListener.clear();
        v.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        listChangeListener.check1AddRemove(property, VALUE_1a, 0, 2);

        // change binding twice without reading
        v.set(VALUE_1a);
        listChangeListener.clear();
        v.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        listChangeListener.check1AddRemove(property, VALUE_1a, 0, 1);

        // change binding twice to same value
        v.set(VALUE_1a);
        listChangeListener.clear();
        v.set(VALUE_1a);
        assertEquals(VALUE_1a, property.get());
        property.check(2);
        listChangeListener.check0();
    }

    @Test(expected = NullPointerException.class)
    public void testBindToNull() {
        property.bind(null);
    }

    @Test
    public void testRebind() {
        attachInvalidationListener();
        final ListProperty<Object> v1 = new SimpleListProperty<>(VALUE_1a);
        final ListProperty<Object> v2 = new SimpleListProperty<>(VALUE_2a);
        property.bind(v1);
        property.get();
        property.reset();
        invalidationListener.reset();

        // rebind causes invalidation event
        property.bind(v2);
        assertEquals(VALUE_2a, property.get());
        assertTrue(property.isBound());
        property.check(1);
        invalidationListener.check(property, 1);

        // change old binding
        v1.set(VALUE_1b);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        invalidationListener.check(null, 0);

        // change new binding
        v2.set(VALUE_2b);
        assertEquals(VALUE_2b, property.get());
        property.check(1);
        invalidationListener.check(property, 1);

        // rebind to same observable should have no effect
        property.bind(v2);
        assertEquals(VALUE_2b, property.get());
        assertTrue(property.isBound());
        property.check(0);
        invalidationListener.check(null, 0);
    }

    @Test
    public void testUnbind() {
        attachInvalidationListener();
        final ListProperty<Object> v = new SimpleListProperty<>(VALUE_1a);
        property.bind(v);
        property.unbind();
        assertEquals(VALUE_1a, property.get());
        assertFalse(property.isBound());
        property.reset();
        invalidationListener.reset();

        // change binding
        v.set(VALUE_2a);
        assertEquals(VALUE_1a, property.get());
        property.check(0);
        invalidationListener.check(null, 0);

        // set value
        property.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(1);
        invalidationListener.check(property, 1);
    }

    @Test
    public void testAddingListenerWillAlwaysReceiveInvalidationEvent() {
        final ListProperty<Object> v = new SimpleListProperty<>(VALUE_1a);
        final InvalidationListenerMock listener2 = new InvalidationListenerMock();
        final InvalidationListenerMock listener3 = new InvalidationListenerMock();

        // setting the property
        property.set(VALUE_1a);
        property.addListener(listener2);
        listener2.reset();
        property.set(VALUE_1b);
        listener2.check(property, 1);

        // binding the property
        property.bind(v);
        v.set(VALUE_2a);
        property.addListener(listener3);
        v.get();
        listener3.reset();
        v.set(VALUE_2b);
        listener3.check(property, 1);
    }

    @Test
    public void testUpdate() {
        ObservableList<Person> list = createPersonsList();
        ListProperty<Person> property = new SimpleListProperty<>(list);
        MockListObserver<Person> mlo = new MockListObserver<>();
        property.addListener(mlo);
        list.get(3).name.set("zero"); // four -> zero
        ObservableList<Person> expected = ObservableCollections.observableArrayList(new Person("one"),
                new Person("two"), new Person("three"), new Person("zero"), new Person("five"));
        mlo.check1Update(expected, 3, 4);
    }

    @Test
    public void testPermutation() {
        ObservableList<Person> list = createPersonsList();
        ListProperty<Person> property = new SimpleListProperty<>(list);
        MockListObserver<Person> mlo = new MockListObserver<>();
        property.addListener(mlo);
        ObservableCollections.sort(list);
        ObservableList<Person> expected = ObservableCollections.observableArrayList(new Person("five"),
                new Person("four"), new Person("one"), new Person("three"), new Person("two"));
        mlo.check1Permutation(expected, new int[]{2, 4, 3, 1, 0});
    }

    @Test
    public void testPermutationUpdate() {
        ObservableList<Person> list = createPersonsList();
        ObservableList<Person> sorted = list.sorted(Person::compareTo);
        ListProperty<Person> property = new SimpleListProperty<>(sorted);
        MockListObserver<Person> mlo = new MockListObserver<>();
        property.addListener(mlo);
        // add another listener to test Generic code path instead of SingleChange
        property.addListener(new MockListObserver<>());
        list.get(3).name.set("zero"); // four -> zero
        ObservableList<Person> expected = ObservableCollections.observableArrayList(new Person("five"),
                new Person("one"), new Person("three"), new Person("two"), new Person("zero"));
        mlo.checkPermutation(0, expected, 0, expected.size(), new int[]{0, 4, 1, 2, 3});
        mlo.checkUpdate(1, expected, 4, 5);
    }

    private ObservableList<Person> createPersonsList() {
        ObservableList<Person> list = ObservableCollections.observableArrayList((Person p) -> new Observable[]{p.name});
        list.addAll(new Person("one"), new Person("two"), new Person("three"),
                new Person("four"), new Person("five"));
        return list;
    }

    @Test
    public void testToString() {
        final ObservableList<Object> value0 = null;
        final ObservableList<Object> value1 = ObservableCollections.observableArrayList(new Object(), new Object());
        final ObservableList<Object> value2 = ObservableCollections.observableArrayList();
        final ListProperty<Object> v = new SimpleListProperty<>(value2);

        property.set(value1);
        assertEquals("ListProperty [value: " + value1 + "]", property.toString());

        property.bind(v);
        assertEquals("ListProperty [bound, invalid]", property.toString());
        property.get();
        assertEquals("ListProperty [bound, value: " + value2 + "]", property.toString());
        v.set(value1);
        assertEquals("ListProperty [bound, invalid]", property.toString());
        property.get();
        assertEquals("ListProperty [bound, value: " + value1 + "]", property.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ListProperty<Object> v1 = new ListPropertyMock(bean, name);
        assertEquals("ListProperty [bean: " + bean + ", name: My name, value: " + null + "]", v1.toString());
        v1.set(value1);
        assertEquals("ListProperty [bean: " + bean + ", name: My name, value: " + value1 + "]", v1.toString());
        v1.set(value0);
        assertEquals("ListProperty [bean: " + bean + ", name: My name, value: " + value0 + "]", v1.toString());

        final ListProperty<Object> v2 = new ListPropertyMock(bean, NO_NAME_1);
        assertEquals("ListProperty [bean: " + bean + ", value: " + null + "]", v2.toString());
        v2.set(value1);
        assertEquals("ListProperty [bean: " + bean + ", value: " + value1 + "]", v2.toString());
        v1.set(value0);
        assertEquals("ListProperty [bean: " + bean + ", name: My name, value: " + value0 + "]", v1.toString());

        final ListProperty<Object> v3 = new ListPropertyMock(bean, NO_NAME_2);
        assertEquals("ListProperty [bean: " + bean + ", value: " + null + "]", v3.toString());
        v3.set(value1);
        assertEquals("ListProperty [bean: " + bean + ", value: " + value1 + "]", v3.toString());
        v1.set(value0);
        assertEquals("ListProperty [bean: " + bean + ", name: My name, value: " + value0 + "]", v1.toString());

        final ListProperty<Object> v4 = new ListPropertyMock(NO_BEAN, name);
        assertEquals("ListProperty [name: My name, value: " + null + "]", v4.toString());
        v4.set(value1);
        v1.set(value0);
        assertEquals("ListProperty [bean: " + bean + ", name: My name, value: " + value0 + "]", v1.toString());
        assertEquals("ListProperty [name: My name, value: " + value1 + "]", v4.toString());
    }

    private static class ListPropertyMock extends ListPropertyBase<Object> {

        private final Object bean;

        private final String name;

        private int counter;

        private ListPropertyMock(Object bean, String name) {
            super();
            this.bean = bean;
            this.name = name;
        }

        private ListPropertyMock(ObservableList<Object> initialValue) {
            super(initialValue);
            this.bean = NO_BEAN;
            this.name = NO_NAME_1;
        }

        private ListPropertyMock() {
            this(NO_BEAN, NO_NAME_1);
        }

        @Override
        protected void invalidated() {
            counter++;
        }

        private void check(int expected) {
            assertEquals(expected, counter);
            reset();
        }

        private void reset() {
            counter = 0;
        }

        @Override
        public Object getBean() {
            return bean;
        }

        @Override
        public String getName() {
            return name;
        }

    }

}
