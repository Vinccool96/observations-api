package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.value.ChangeListenerMock;
import io.github.vinccool96.observations.beans.value.ObservableObjectValueStub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("SimplifiableJUnitAssertion")
public class ObjectPropertyBaseTest {

    private static final Object NO_BEAN = null;

    private static final String NO_NAME_1 = null;

    private static final String NO_NAME_2 = "";

    private static final Object UNDEFINED = new Object();

    private static final Object VALUE_1a = new Object();

    private static final Object VALUE_1b = new Object();

    private static final Object VALUE_2a = new Object();

    private static final Object VALUE_2b = new Object();

    private ObjectPropertyMock property;

    private InvalidationListenerMock invalidationListener;

    private ChangeListenerMock<Object> changeListener;

    @Before
    public void setUp() throws Exception {
        property = new ObjectPropertyMock();
        invalidationListener = new InvalidationListenerMock();
        changeListener = new ChangeListenerMock<>(UNDEFINED);
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

    @Test
    public void testConstructor() {
        final ObjectProperty<Object> p1 = new SimpleObjectProperty<>();
        assertEquals(null, p1.get());
        assertEquals(null, p1.getValue());
        assertFalse(property.isBound());

        final ObjectProperty<Object> p2 = new SimpleObjectProperty<>(VALUE_1b);
        assertEquals(VALUE_1b, p2.get());
        assertEquals(VALUE_1b, p2.getValue());
        assertFalse(property.isBound());
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
    public void testLazySet() {
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
    public void testEagerSet() {
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
    public void testLazySetValue() {
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
    public void testEagerSetValue() {
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

    @Test(expected = RuntimeException.class)
    public void testSetBound() {
        final ObjectProperty<Object> v = new SimpleObjectProperty<>(VALUE_1a);
        property.bind(v);
        property.set(VALUE_2b);
    }

    @Test(expected = RuntimeException.class)
    public void testSetValueBound() {
        final ObjectProperty<Object> v = new SimpleObjectProperty<>(VALUE_1a);
        property.bind(v);
        property.setValue(VALUE_2b);
    }

    @Test
    public void testLazyBind() {
        attachInvalidationListener();
        final ObservableObjectValueStub<Object> v = new ObservableObjectValueStub<>(VALUE_1a);
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
    public void testEagerBind() {
        attachChangeListener();
        final ObservableObjectValueStub<Object> v = new ObservableObjectValueStub<>(VALUE_1a);
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

    @Test(expected = NullPointerException.class)
    public void testBindToNull() {
        property.bind(null);
    }

    @Test
    public void testRebind() {
        attachInvalidationListener();
        final ObjectProperty<Object> v1 = new SimpleObjectProperty<>(VALUE_1a);
        final ObjectProperty<Object> v2 = new SimpleObjectProperty<>(VALUE_2a);
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
        final ObjectProperty<Object> v = new SimpleObjectProperty<>(VALUE_1a);
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
        final ObjectProperty<Object> v = new SimpleObjectProperty<>(VALUE_1a);
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
        final Object value1 = new Object();
        final Object value2 = new Object();
        final ObjectProperty<Object> v = new SimpleObjectProperty<>(value2);

        property.set(value1);
        assertEquals("ObjectProperty [value: " + value1 + "]", property.toString());

        property.bind(v);
        assertEquals("ObjectProperty [bound, invalid]", property.toString());
        property.get();
        assertEquals("ObjectProperty [bound, value: " + value2 + "]", property.toString());
        v.set(value1);
        assertEquals("ObjectProperty [bound, invalid]", property.toString());
        property.get();
        assertEquals("ObjectProperty [bound, value: " + value1 + "]", property.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ObjectPropertyBase<Object> v1 = new ObjectPropertyMock(bean, name);
        assertEquals("ObjectProperty [bean: " + bean + ", name: My name, value: " + null + "]", v1.toString());
        v1.set(value1);
        assertEquals("ObjectProperty [bean: " + bean + ", name: My name, value: " + value1 + "]", v1.toString());

        final ObjectPropertyBase<Object> v2 = new ObjectPropertyMock(bean, NO_NAME_1);
        assertEquals("ObjectProperty [bean: " + bean + ", value: " + null + "]", v2.toString());
        v2.set(value1);
        assertEquals("ObjectProperty [bean: " + bean + ", value: " + value1 + "]", v2.toString());

        final ObjectPropertyBase<Object> v3 = new ObjectPropertyMock(bean, NO_NAME_2);
        assertEquals("ObjectProperty [bean: " + bean + ", value: " + null + "]", v3.toString());
        v3.set(value1);
        assertEquals("ObjectProperty [bean: " + bean + ", value: " + value1 + "]", v3.toString());

        final ObjectPropertyBase<Object> v4 = new ObjectPropertyMock(NO_BEAN, name);
        assertEquals("ObjectProperty [name: My name, value: " + null + "]", v4.toString());
        v4.set(value1);
        assertEquals("ObjectProperty [name: My name, value: " + value1 + "]", v4.toString());
    }

    private static class ObjectPropertyMock extends ObjectPropertyBase<Object> {

        private final Object bean;

        private final String name;

        private int counter;

        private ObjectPropertyMock() {
            this.bean = NO_BEAN;
            this.name = NO_NAME_1;
        }

        private ObjectPropertyMock(Object bean, String name) {
            this.bean = bean;
            this.name = name;
        }

        private void check(int expected) {
            assertEquals(expected, counter);
            reset();
        }

        private void reset() {
            counter = 0;
        }

        @Override
        protected void invalidated() {
            counter++;
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
