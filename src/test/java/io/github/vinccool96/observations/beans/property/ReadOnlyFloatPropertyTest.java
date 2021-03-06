package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadOnlyFloatPropertyTest {

    private static final float DEFAULT = 0.0f;

    private static final float EPSILON = 1e-6f;

    @Test
    public void testToString() {
        final ReadOnlyFloatProperty v1 = new ReadOnlyFloatPropertyStub(null, "");
        assertEquals("ReadOnlyFloatProperty [value: " + DEFAULT + "]", v1.toString());

        final ReadOnlyFloatProperty v2 = new ReadOnlyFloatPropertyStub(null, null);
        assertEquals("ReadOnlyFloatProperty [value: " + DEFAULT + "]", v2.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ReadOnlyFloatProperty v3 = new ReadOnlyFloatPropertyStub(bean, name);
        assertEquals("ReadOnlyFloatProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v3.toString());

        final ReadOnlyFloatProperty v4 = new ReadOnlyFloatPropertyStub(bean, "");
        assertEquals("ReadOnlyFloatProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());

        final ReadOnlyFloatProperty v5 = new ReadOnlyFloatPropertyStub(bean, null);
        assertEquals("ReadOnlyFloatProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v5.toString());

        final ReadOnlyFloatProperty v6 = new ReadOnlyFloatPropertyStub(null, name);
        assertEquals("ReadOnlyFloatProperty [name: My name, value: " + DEFAULT + "]", v6.toString());

    }

    @Test
    public void testAsObject() {
        final ReadOnlyFloatWrapper valueModel = new ReadOnlyFloatWrapper();
        final ReadOnlyObjectProperty<Float> exp = valueModel.getReadOnlyProperty().asObject();

        assertEquals(0.0, exp.getValue(), EPSILON);
        valueModel.set(-4354.3f);
        assertEquals(-4354.3f, exp.getValue(), EPSILON);
        valueModel.set(5e11f);
        assertEquals(5e11f, exp.getValue(), EPSILON);
    }

    @Test
    public void testObjectToFloat() {
        final ReadOnlyObjectWrapper<Float> valueModel = new ReadOnlyObjectWrapper<>();
        final ReadOnlyFloatProperty exp = ReadOnlyFloatProperty.readOnlyFloatProperty(valueModel.getReadOnlyProperty());

        assertEquals(0.0, exp.floatValue(), EPSILON);
        valueModel.set(-4354.3f);
        assertEquals(-4354.3f, exp.floatValue(), EPSILON);
        valueModel.set(5e11f);
        assertEquals(5e11f, exp.floatValue(), EPSILON);
    }

    private static class ReadOnlyFloatPropertyStub extends ReadOnlyFloatProperty {

        private final Object bean;

        private final String name;

        private ReadOnlyFloatPropertyStub(Object bean, String name) {
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
        public float get() {
            return 0.0f;
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
