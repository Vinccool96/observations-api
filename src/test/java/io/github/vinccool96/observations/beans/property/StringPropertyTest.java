package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StringPropertyTest {

    private static final Object NO_BEAN = null;

    private static final String NO_NAME_1 = null;

    private static final String NO_NAME_2 = "";

    private static final String VALUE_1 = "Hello World";

    private static final String VALUE_2 = "Goodbye World";

    private static final String DEFAULT = null;

    @Test
    public void testBindBidirectional() {
        final StringProperty p1 = new SimpleStringProperty(VALUE_2);
        final StringProperty p2 = new SimpleStringProperty(VALUE_1);

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
        final StringProperty v0 = new StringPropertyStub(NO_BEAN, NO_NAME_1);
        assertEquals("StringProperty [value: " + DEFAULT + "]", v0.toString());

        final StringProperty v1 = new StringPropertyStub(NO_BEAN, NO_NAME_2);
        assertEquals("StringProperty [value: " + DEFAULT + "]", v1.toString());

        final Object bean = new Object();
        final String name = "My name";
        final StringProperty v2 = new StringPropertyStub(bean, name);
        assertEquals("StringProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v2.toString());
        v2.set(VALUE_1);
        assertEquals("StringProperty [bean: " + bean.toString() + ", name: My name, value: " + VALUE_1 + "]",
                v2.toString());

        final StringProperty v3 = new StringPropertyStub(bean, NO_NAME_1);
        assertEquals("StringProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v3.toString());
        v3.set(VALUE_1);
        assertEquals("StringProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v3.toString());

        final StringProperty v4 = new StringPropertyStub(bean, NO_NAME_2);
        assertEquals("StringProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());
        v4.set(VALUE_1);
        assertEquals("StringProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v4.toString());

        final StringProperty v5 = new StringPropertyStub(NO_BEAN, name);
        assertEquals("StringProperty [name: My name, value: " + DEFAULT + "]", v5.toString());
        v5.set(VALUE_1);
        assertEquals("StringProperty [name: My name, value: " + VALUE_1 + "]", v5.toString());
    }

    private static class StringPropertyStub extends StringProperty {

        private final Object bean;

        private final String name;

        private String value;

        private StringPropertyStub(Object bean, String name) {
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
        public String get() {
            return value;
        }

        @Override
        public void set(String value) {
            this.value = value;
        }

        @Override
        public void bind(ObservableValue<? extends String> observable) {
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
        public void addListener(ChangeListener<? super String> listener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(ChangeListener<? super String> listener) {
            fail("Not in use");
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super String> listener) {
            fail("Not in use");
            return false;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            fail("Not in use");
        }

        @Override
        public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
            fail("Not in use");
            return false;
        }

    }

}
