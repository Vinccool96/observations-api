package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadOnlyIntegerPropertyTest {

    private static final int DEFAULT = 0;

    @Test
    public void testToString() {
        final ReadOnlyIntegerProperty v1 = new ReadOnlyIntegerPropertyStub(null, "");
        assertEquals("ReadOnlyIntegerProperty [value: " + DEFAULT + "]", v1.toString());

        final ReadOnlyIntegerProperty v2 = new ReadOnlyIntegerPropertyStub(null, null);
        assertEquals("ReadOnlyIntegerProperty [value: " + DEFAULT + "]", v2.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ReadOnlyIntegerProperty v3 = new ReadOnlyIntegerPropertyStub(bean, name);
        assertEquals("ReadOnlyIntegerProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v3.toString());

        final ReadOnlyIntegerProperty v4 = new ReadOnlyIntegerPropertyStub(bean, "");
        assertEquals("ReadOnlyIntegerProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());

        final ReadOnlyIntegerProperty v5 = new ReadOnlyIntegerPropertyStub(bean, null);
        assertEquals("ReadOnlyIntegerProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v5.toString());

        final ReadOnlyIntegerProperty v6 = new ReadOnlyIntegerPropertyStub(null, name);
        assertEquals("ReadOnlyIntegerProperty [name: My name, value: " + DEFAULT + "]", v6.toString());

    }

    @Test
    public void testAsObject() {
        final ReadOnlyIntegerWrapper valueModel = new ReadOnlyIntegerWrapper();
        final ReadOnlyObjectProperty<Integer> exp = valueModel.getReadOnlyProperty().asObject();

        assertEquals(Integer.valueOf(0), exp.getValue());
        valueModel.set(-4354);
        assertEquals(Integer.valueOf(-4354), exp.getValue());
        valueModel.set(5);
        assertEquals(Integer.valueOf(5), exp.getValue());
    }

    @Test
    public void testObjectToInteger() {
        final ReadOnlyObjectWrapper<Integer> valueModel = new ReadOnlyObjectWrapper<>();
        final ReadOnlyIntegerProperty exp =
                ReadOnlyIntegerProperty.readOnlyIntegerProperty(valueModel.getReadOnlyProperty());

        assertEquals(0, exp.intValue());
        valueModel.set(-4354);
        assertEquals(-4354, exp.intValue());
        valueModel.set(5);
        assertEquals(5, exp.intValue());
    }

    private static class ReadOnlyIntegerPropertyStub extends ReadOnlyIntegerProperty {

        private final Object bean;

        private final String name;

        private ReadOnlyIntegerPropertyStub(Object bean, String name) {
            this.bean = bean;
            this.name = name;
        }

        @Override public Object getBean() {
            return bean;
        }

        @Override public String getName() {
            return name;
        }

        @Override public int get() {
            return 0;
        }

        @Override
        public void addListener(ChangeListener<? super Number> listener) {
        }

        @Override
        public void removeListener(ChangeListener<? super Number> listener) {
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super Number> listener) {
            return false;
        }

        @Override
        public void addListener(InvalidationListener listener) {
        }

        @Override
        public void removeListener(InvalidationListener listener) {
        }

        @Override
        public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
            return false;
        }

    }

}
