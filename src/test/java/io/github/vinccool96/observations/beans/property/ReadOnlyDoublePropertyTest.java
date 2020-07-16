package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadOnlyDoublePropertyTest {

    private static final double DEFAULT = 0.0;

    private static final float EPSILON = 1e-6f;

    @Test
    public void testToString() {
        final ReadOnlyDoubleProperty v1 = new ReadOnlyDoublePropertyStub(null, "");
        assertEquals("ReadOnlyDoubleProperty [value: " + DEFAULT + "]", v1.toString());

        final ReadOnlyDoubleProperty v2 = new ReadOnlyDoublePropertyStub(null, null);
        assertEquals("ReadOnlyDoubleProperty [value: " + DEFAULT + "]", v2.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ReadOnlyDoubleProperty v3 = new ReadOnlyDoublePropertyStub(bean, name);
        assertEquals("ReadOnlyDoubleProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v3.toString());

        final ReadOnlyDoubleProperty v4 = new ReadOnlyDoublePropertyStub(bean, "");
        assertEquals("ReadOnlyDoubleProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());

        final ReadOnlyDoubleProperty v5 = new ReadOnlyDoublePropertyStub(bean, null);
        assertEquals("ReadOnlyDoubleProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v5.toString());

        final ReadOnlyDoubleProperty v6 = new ReadOnlyDoublePropertyStub(null, name);
        assertEquals("ReadOnlyDoubleProperty [name: My name, value: " + DEFAULT + "]", v6.toString());

    }

    @Test
    public void testAsObject() {
        final ReadOnlyDoubleWrapper valueModel = new ReadOnlyDoubleWrapper();
        final ReadOnlyObjectProperty<Double> exp = valueModel.getReadOnlyProperty().asObject();

        assertEquals(0.0, exp.getValue(), EPSILON);
        valueModel.set(-4354.3);
        assertEquals(-4354.3, exp.getValue(), EPSILON);
        valueModel.set(5e11);
        assertEquals(5e11, exp.getValue(), EPSILON);
    }

    @Test
    public void testObjectToDouble() {
        final ReadOnlyObjectWrapper<Double> valueModel = new ReadOnlyObjectWrapper<>();
        final ReadOnlyDoubleProperty exp =
                ReadOnlyDoubleProperty.readOnlyDoubleProperty(valueModel.getReadOnlyProperty());

        assertEquals(0.0, exp.doubleValue(), EPSILON);
        valueModel.set(-4354.3);
        assertEquals(-4354.3, exp.doubleValue(), EPSILON);
        valueModel.set(5e11);
        assertEquals(5e11, exp.doubleValue(), EPSILON);
    }

    private static class ReadOnlyDoublePropertyStub extends ReadOnlyDoubleProperty {

        private final Object bean;

        private final String name;

        private ReadOnlyDoublePropertyStub(Object bean, String name) {
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
        public double get() {
            return 0.0;
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
