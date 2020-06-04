package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ObjectPropertyTest {

    private static final Object NO_BEAN = null;

    private static final String NO_NAME_1 = null;

    private static final String NO_NAME_2 = "";

    private static final Object VALUE_1 = new Object();

    private static final Object VALUE_2 = new Object();

    private static final Object DEFAULT = null;

    @Test
    public void testBindBidirectional() {
        final ObjectProperty<Object> p1 = new SimpleObjectProperty<Object>(VALUE_2);
        final ObjectProperty<Object> p2 = new SimpleObjectProperty<Object>(VALUE_1);

        p1.bindBidirectional(p2);
        assertEquals(VALUE_1, p1.get());
        assertEquals(VALUE_1, p2.get());

        p1.set(VALUE_2);
        assertEquals(VALUE_2, p1.get());
        assertEquals(VALUE_2, p2.get());

        p2.set(VALUE_1);
        assertEquals(VALUE_1, p1.get());
        assertEquals(VALUE_1, p2.get());

        p1.unbindBidirectional(p2);
        p1.set(VALUE_2);
        assertEquals(VALUE_2, p1.get());
        assertEquals(VALUE_1, p2.get());

        p1.set(VALUE_1);
        p2.set(VALUE_2);
        assertEquals(VALUE_1, p1.get());
        assertEquals(VALUE_2, p2.get());
    }

    @Test
    public void testToString() {
        final ObjectProperty<Object> v0 = new ObjectPropertyStub(NO_BEAN, NO_NAME_1);
        assertEquals("ObjectProperty [value: " + DEFAULT + "]", v0.toString());

        final ObjectProperty<Object> v1 = new ObjectPropertyStub(NO_BEAN, NO_NAME_2);
        assertEquals("ObjectProperty [value: " + DEFAULT + "]", v1.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ObjectProperty<Object> v2 = new ObjectPropertyStub(bean, name);
        assertEquals("ObjectProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v2.toString());
        v2.set(VALUE_1);
        assertEquals("ObjectProperty [bean: " + bean.toString() + ", name: My name, value: " + VALUE_1 + "]",
                v2.toString());

        final ObjectProperty<Object> v3 = new ObjectPropertyStub(bean, NO_NAME_1);
        assertEquals("ObjectProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v3.toString());
        v3.set(VALUE_1);
        assertEquals("ObjectProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v3.toString());

        final ObjectProperty<Object> v4 = new ObjectPropertyStub(bean, NO_NAME_2);
        assertEquals("ObjectProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());
        v4.set(VALUE_1);
        assertEquals("ObjectProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v4.toString());

        final ObjectProperty<Object> v5 = new ObjectPropertyStub(NO_BEAN, name);
        assertEquals("ObjectProperty [name: My name, value: " + DEFAULT + "]", v5.toString());
        v5.set(VALUE_1);
        assertEquals("ObjectProperty [name: My name, value: " + VALUE_1 + "]", v5.toString());
    }

    private class ObjectPropertyStub extends ObjectProperty<Object> {

        private final Object bean;

        private final String name;

        private Object value;

        private ObjectPropertyStub(Object bean, String name) {
            this.bean = bean;
            this.name = name;
        }

        @Override
        public Object getBean() {
            return bean;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object get() {
            return value;
        }

        @Override
        public void set(Object value) {
            this.value = value;
        }

        @Override
        public void bind(ObservableValue<? extends Object> observable) {
            fail("Not in use");
        }

        @Override
        public void unbind() {
            fail("Not in use");
        }

        @Override
        public boolean isBound() {
            fail("Not in use");
            return false;
        }

        @Override
        public void addListener(ChangeListener<? super Object> listener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(ChangeListener<? super Object> listener) {
            fail("Not in use");
        }

        @Override
        public void addListener(InvalidationListener listener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            fail("Not in use");
        }

    }

}
