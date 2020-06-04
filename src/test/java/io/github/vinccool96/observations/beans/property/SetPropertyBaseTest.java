package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.value.ChangeListenerMock;
import io.github.vinccool96.observations.beans.value.ObservableObjectValueStub;
import io.github.vinccool96.observations.collections.MockSetObserver;
import io.github.vinccool96.observations.collections.MockSetObserver.Tuple;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableSet;
import org.junit.Before;
import org.junit.Test;

import static io.github.vinccool96.observations.collections.MockSetObserver.Call;
import static org.junit.Assert.*;

public class SetPropertyBaseTest {

    private static final Object NO_BEAN = null;

    private static final String NO_NAME_1 = null;

    private static final String NO_NAME_2 = "";

    private static final Object OBJECT_1b = new Object();

    private static final Object OBJECT_2a_0 = new Object();

    private static final Object OBJECT_2a_1 = new Object();

    private static final Object OBJECT_2b_0 = new Object();

    private static final Object OBJECT_2b_1 = new Object();

    private static final Object OBJECT_2b_2 = new Object();

    private static final ObservableSet<Object> UNDEFINED = ObservableCollections.observableSet();

    private static final ObservableSet<Object> VALUE_1a = ObservableCollections.observableSet();

    private static final ObservableSet<Object> VALUE_1b = ObservableCollections.observableSet(OBJECT_1b);

    private static final ObservableSet<Object> VALUE_2a = ObservableCollections.observableSet(OBJECT_2a_0, OBJECT_2a_1);

    private static final ObservableSet<Object> VALUE_2b =
            ObservableCollections.observableSet(OBJECT_2b_0, OBJECT_2b_1, OBJECT_2b_2);

    private SetPropertyMock property;

    private InvalidationListenerMock invalidationListener;

    private ChangeListenerMock<ObservableSet<Object>> changeListener;

    private MockSetObserver<Object> setChangeListener;

    @Before
    public void setUp() throws Exception {
        property = new SetPropertyMock();
        invalidationListener = new InvalidationListenerMock();
        changeListener = new ChangeListenerMock<ObservableSet<Object>>(UNDEFINED);
        setChangeListener = new MockSetObserver<Object>();
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

    private void attachSetChangeListener() {
        property.addListener(setChangeListener);
        property.get();
        setChangeListener.clear();
    }

    @Test
    public void testConstructor() {
        final SetProperty<Object> p1 = new SimpleSetProperty<Object>();
        assertEquals(null, p1.get());
        assertEquals(null, p1.getValue());
        assertFalse(property.isBound());

        final SetProperty<Object> p2 = new SimpleSetProperty<Object>(VALUE_1b);
        assertEquals(VALUE_1b, p2.get());
        assertEquals(VALUE_1b, p2.getValue());
        assertFalse(property.isBound());
    }

    @Test
    public void testEmptyProperty() {
        assertEquals("empty", property.emptyProperty().getName());
        assertEquals(property, property.emptyProperty().getBean());
        assertTrue(property.emptyProperty().get());

        property.set(VALUE_2a);
        assertFalse(property.emptyProperty().get());
        property.set(VALUE_1a);
        assertTrue(property.emptyProperty().get());
    }

    @Test
    public void testSizeProperty() {
        assertEquals("size", property.sizeProperty().getName());
        assertEquals(property, property.sizeProperty().getBean());
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
    public void testSetChangeListener() {
        attachSetChangeListener();
        property.set(VALUE_2a);
        setChangeListener.assertMultipleCalls(new Call[]{new Call(null, OBJECT_2a_0), new Call(null, OBJECT_2a_1)});
        property.removeListener(setChangeListener);
        setChangeListener.clear();
        property.set(VALUE_1a);
        assertEquals(0, setChangeListener.getCallsNumber());
    }

    @Test
    public void testSourceSet_Invalidation() {
        final ObservableSet<Object> source1 = ObservableCollections.observableSet();
        final ObservableSet<Object> source2 = ObservableCollections.observableSet();
        final Object value = new Object();

        // constructor
        property = new SetPropertyMock(source1);
        property.reset();
        attachInvalidationListener();

        // add element
        source1.add(value);
        assertTrue(property.contains(value));
        property.check(1);
        invalidationListener.check(property, 1);

        // remove element
        source1.remove(value);
        assertFalse(property.contains(value));
        property.check(1);
        invalidationListener.check(property, 1);

        // set
        property.set(source2);
        property.get();
        property.reset();
        invalidationListener.reset();

        // add element
        source2.add(value);
        assertTrue(property.contains(value));
        property.check(1);
        invalidationListener.check(property, 1);

        // remove element
        source2.remove(value);
        assertFalse(property.contains(value));
        property.check(1);
        invalidationListener.check(property, 1);
    }

    @Test
    public void testSourceSet_Change() {
        final ObservableSet<Object> source1 = ObservableCollections.observableSet();
        final ObservableSet<Object> source2 = ObservableCollections.observableSet();
        final Object value = new Object();

        // constructor
        property = new SetPropertyMock(source1);
        property.reset();
        attachChangeListener();

        // add element
        source1.add(value);
        assertTrue(property.contains(value));
        property.check(1);
        changeListener.check(property, source1, source1, 1);

        // remove element
        source1.remove(value);
        assertFalse(property.contains(value));
        property.check(1);
        changeListener.check(property, source1, source1, 1);

        // set
        property.set(source2);
        property.get();
        property.reset();
        changeListener.reset();

        // add element
        source2.add(value);
        assertTrue(property.contains(value));
        property.check(1);
        changeListener.check(property, source2, source2, 1);

        // remove element
        source2.remove(value);
        assertFalse(property.contains(value));
        property.check(1);
        changeListener.check(property, source2, source2, 1);
    }

    @Test
    public void testSourceSet_SetChange() {
        final ObservableSet<Object> source1 = ObservableCollections.observableSet();
        final ObservableSet<Object> source2 = ObservableCollections.observableSet();
        final Object value = new Object();

        // constructor
        property = new SetPropertyMock(source1);
        property.reset();
        attachSetChangeListener();

        // add element
        source1.add(value);
        assertTrue(property.contains(value));
        property.check(1);
        setChangeListener.assertAdded(Tuple.tup(value));
        setChangeListener.clear();

        // remove element
        source1.remove(value);
        assertFalse(property.contains(value));
        property.check(1);
        setChangeListener.assertRemoved(Tuple.tup(value));
        setChangeListener.clear();

        // set
        property.set(source2);
        property.get();
        property.reset();
        setChangeListener.clear();

        // add element
        source2.add(value);
        assertTrue(property.contains(value));
        property.check(1);
        setChangeListener.assertAdded(Tuple.tup(value));
        setChangeListener.clear();

        // remove element
        source2.remove(value);
        assertFalse(property.contains(value));
        property.check(1);
        setChangeListener.assertRemoved(Tuple.tup(value));
        setChangeListener.clear();
    }

    @Test
    public void testSet_Invalidation() {
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
    public void testSet_Change() {
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
    public void testSet_SetChange() {
        attachSetChangeListener();

        // set value once
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        setChangeListener.assertMultipleCalls(new Call[]{new Call(null, OBJECT_2a_0), new Call(null, OBJECT_2a_1)});

        // set same value again
        setChangeListener.clear();
        property.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        assertEquals(0, setChangeListener.getCallsNumber());

        // set value twice without reading
        property.set(VALUE_1a);
        setChangeListener.clear();
        property.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        setChangeListener.assertAdded(MockSetObserver.Tuple.tup(OBJECT_1b));
    }

    @Test
    public void testSetValue_Invalidation() {
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
    public void testSetValue_Change() {
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
    public void testSetValue_SetChange() {
        attachSetChangeListener();

        // set value once
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        setChangeListener.assertMultipleCalls(new Call[]{new Call(null, OBJECT_2a_0), new Call(null, OBJECT_2a_1)});

        // set same value again
        setChangeListener.clear();
        property.setValue(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(0);
        assertEquals(0, setChangeListener.getCallsNumber());

        // set value twice without reading
        property.setValue(VALUE_1a);
        setChangeListener.clear();
        property.setValue(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        setChangeListener.assertAdded(MockSetObserver.Tuple.tup(OBJECT_1b));
    }

    @Test(expected = RuntimeException.class)
    public void testSetBoundValue() {
        final SetProperty<Object> v = new SimpleSetProperty<Object>(VALUE_1a);
        property.bind(v);
        property.set(VALUE_1a);
    }

    @Test
    public void testBind_Invalidation() {
        attachInvalidationListener();
        final ObservableObjectValueStub<ObservableSet<Object>> v =
                new ObservableObjectValueStub<ObservableSet<Object>>(ObservableCollections.observableSet(VALUE_1a));

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
        final ObservableObjectValueStub<ObservableSet<Object>> v =
                new ObservableObjectValueStub<ObservableSet<Object>>(ObservableCollections.observableSet(VALUE_1a));

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
    public void testBind_SetChange() {
        attachSetChangeListener();
        final ObservableObjectValueStub<ObservableSet<Object>> v =
                new ObservableObjectValueStub<ObservableSet<Object>>(ObservableCollections.observableSet(VALUE_1a));

        property.bind(v);
        assertEquals(VALUE_1a, property.get());
        assertTrue(property.isBound());
        property.check(1);
        assertEquals(0, setChangeListener.getCallsNumber());

        // change binding once
        setChangeListener.clear();
        v.set(VALUE_2a);
        assertEquals(VALUE_2a, property.get());
        property.check(1);
        setChangeListener.assertMultipleCalls(new Call[]{new Call(null, OBJECT_2a_0), new Call(null, OBJECT_2a_1)});

        // change binding twice without reading
        v.set(VALUE_1a);
        setChangeListener.clear();
        v.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        property.check(2);
        setChangeListener.assertAdded(MockSetObserver.Tuple.tup(OBJECT_1b));

        // change binding twice to same value
        v.set(VALUE_1a);
        setChangeListener.clear();
        v.set(VALUE_1a);
        assertEquals(VALUE_1a, property.get());
        property.check(2);
        assertEquals(0, setChangeListener.getCallsNumber());
    }

    @Test(expected = NullPointerException.class)
    public void testBindToNull() {
        property.bind(null);
    }

    @Test
    public void testRebind() {
        attachInvalidationListener();
        final SetProperty<Object> v1 = new SimpleSetProperty<Object>(VALUE_1a);
        final SetProperty<Object> v2 = new SimpleSetProperty<Object>(VALUE_2a);
        property.bind(v1);
        property.get();
        property.reset();
        invalidationListener.reset();

        // rebind causes invalidation event
        property.bind(v2);
        assertEquals(VALUE_2a, property.get());
        assertTrue(property.isBound());
        assertEquals(1, property.counter);
        invalidationListener.check(property, 1);
        property.reset();

        // change old binding
        v1.set(VALUE_1b);
        assertEquals(VALUE_2a, property.get());
        assertEquals(0, property.counter);
        invalidationListener.check(null, 0);
        property.reset();

        // change new binding
        v2.set(VALUE_2b);
        assertEquals(VALUE_2b, property.get());
        assertEquals(1, property.counter);
        invalidationListener.check(property, 1);
        property.reset();

        // rebind to same observable should have no effect
        property.bind(v2);
        assertEquals(VALUE_2b, property.get());
        assertTrue(property.isBound());
        assertEquals(0, property.counter);
        invalidationListener.check(null, 0);
    }

    @Test
    public void testUnbind() {
        attachInvalidationListener();
        final SetProperty<Object> v = new SimpleSetProperty<Object>(VALUE_1a);
        property.bind(v);
        property.unbind();
        assertEquals(VALUE_1a, property.get());
        assertFalse(property.isBound());
        property.reset();
        invalidationListener.reset();

        // change binding
        v.set(VALUE_2a);
        assertEquals(VALUE_1a, property.get());
        assertEquals(0, property.counter);
        invalidationListener.check(null, 0);
        property.reset();

        // set value
        property.set(VALUE_1b);
        assertEquals(VALUE_1b, property.get());
        assertEquals(1, property.counter);
        invalidationListener.check(property, 1);
    }

    @Test
    public void testAddingListenerWillAlwaysReceiveInvalidationEvent() {
        final SetProperty<Object> v = new SimpleSetProperty<Object>(VALUE_1a);
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
    public void testToString() {
        final ObservableSet<Object> value0 = null;
        final ObservableSet<Object> value1 = ObservableCollections.observableSet(new Object(), new Object());
        final ObservableSet<Object> value2 = ObservableCollections.observableSet();
        final SetProperty<Object> v = new SimpleSetProperty<Object>(value2);

        property.set(value1);
        assertEquals("SetProperty [value: " + value1 + "]", property.toString());

        property.bind(v);
        assertEquals("SetProperty [bound, invalid]", property.toString());
        property.get();
        assertEquals("SetProperty [bound, value: " + value2 + "]", property.toString());
        v.set(value1);
        assertEquals("SetProperty [bound, invalid]", property.toString());
        property.get();
        assertEquals("SetProperty [bound, value: " + value1 + "]", property.toString());

        final Object bean = new Object();
        final String name = "My name";
        final SetProperty<Object> v1 = new SetPropertyMock(bean, name);
        assertEquals("SetProperty [bean: " + bean.toString() + ", name: My name, value: " + null + "]", v1.toString());
        v1.set(value1);
        assertEquals("SetProperty [bean: " + bean.toString() + ", name: My name, value: " + value1 + "]",
                v1.toString());
        v1.set(value0);
        assertEquals("SetProperty [bean: " + bean.toString() + ", name: My name, value: " + value0 + "]",
                v1.toString());

        final SetProperty<Object> v2 = new SetPropertyMock(bean, NO_NAME_1);
        assertEquals("SetProperty [bean: " + bean.toString() + ", value: " + null + "]", v2.toString());
        v2.set(value1);
        assertEquals("SetProperty [bean: " + bean.toString() + ", value: " + value1 + "]", v2.toString());
        v1.set(value0);
        assertEquals("SetProperty [bean: " + bean.toString() + ", name: My name, value: " + value0 + "]",
                v1.toString());

        final SetProperty<Object> v3 = new SetPropertyMock(bean, NO_NAME_2);
        assertEquals("SetProperty [bean: " + bean.toString() + ", value: " + null + "]", v3.toString());
        v3.set(value1);
        assertEquals("SetProperty [bean: " + bean.toString() + ", value: " + value1 + "]", v3.toString());
        v1.set(value0);
        assertEquals("SetProperty [bean: " + bean.toString() + ", name: My name, value: " + value0 + "]",
                v1.toString());

        final SetProperty<Object> v4 = new SetPropertyMock(NO_BEAN, name);
        assertEquals("SetProperty [name: My name, value: " + null + "]", v4.toString());
        v4.set(value1);
        v1.set(value0);
        assertEquals("SetProperty [bean: " + bean.toString() + ", name: My name, value: " + value0 + "]",
                v1.toString());
        assertEquals("SetProperty [name: My name, value: " + value1 + "]", v4.toString());
    }

    private static class SetPropertyMock extends SetPropertyBase<Object> {

        private final Object bean;

        private final String name;

        private int counter;

        private SetPropertyMock() {
            this.bean = NO_BEAN;
            this.name = NO_NAME_1;
        }

        private SetPropertyMock(Object bean, String name) {
            this.bean = bean;
            this.name = name;
        }

        private SetPropertyMock(ObservableSet<Object> initialValue) {
            super(initialValue);
            this.bean = NO_BEAN;
            this.name = NO_NAME_1;
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
