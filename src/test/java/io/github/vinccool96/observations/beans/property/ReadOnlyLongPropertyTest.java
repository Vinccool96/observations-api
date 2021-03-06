package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadOnlyLongPropertyTest {

    private static final long DEFAULT = 0L;

    @Test
    public void testToString() {
        final ReadOnlyLongProperty v1 = new ReadOnlyLongPropertyStub(null, "");
        assertEquals("ReadOnlyLongProperty [value: " + DEFAULT + "]", v1.toString());

        final ReadOnlyLongProperty v2 = new ReadOnlyLongPropertyStub(null, null);
        assertEquals("ReadOnlyLongProperty [value: " + DEFAULT + "]", v2.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ReadOnlyLongProperty v3 = new ReadOnlyLongPropertyStub(bean, name);
        assertEquals("ReadOnlyLongProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v3.toString());

        final ReadOnlyLongProperty v4 = new ReadOnlyLongPropertyStub(bean, "");
        assertEquals("ReadOnlyLongProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());

        final ReadOnlyLongProperty v5 = new ReadOnlyLongPropertyStub(bean, null);
        assertEquals("ReadOnlyLongProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v5.toString());

        final ReadOnlyLongProperty v6 = new ReadOnlyLongPropertyStub(null, name);
        assertEquals("ReadOnlyLongProperty [name: My name, value: " + DEFAULT + "]", v6.toString());

    }

    @Test
    public void testAsObject() {
        final ReadOnlyLongWrapper valueModel = new ReadOnlyLongWrapper();
        final ReadOnlyObjectProperty<Long> exp = valueModel.getReadOnlyProperty().asObject();

        assertEquals(Long.valueOf(0L), exp.getValue());
        valueModel.set(-4354L);
        assertEquals(Long.valueOf(-4354L), exp.getValue());
        valueModel.set(5L);
        assertEquals(Long.valueOf(5L), exp.getValue());
    }

    @Test
    public void testObjectToLong() {
        final ReadOnlyObjectWrapper<Long> valueModel = new ReadOnlyObjectWrapper<>();
        final ReadOnlyLongProperty exp = ReadOnlyLongProperty.readOnlyLongProperty(valueModel.getReadOnlyProperty());

        assertEquals(0L, exp.longValue());
        valueModel.set(-4354L);
        assertEquals(-4354L, exp.longValue());
        valueModel.set(5L);
        assertEquals(5L, exp.longValue());
    }

    private static class ReadOnlyLongPropertyStub extends ReadOnlyLongProperty {

        private final Object bean;

        private final String name;

        private ReadOnlyLongPropertyStub(Object bean, String name) {
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
        public long get() {
            return 0L;
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
