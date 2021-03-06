package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadOnlyStringPropertyTest {

    private static final String DEFAULT = null;

    @Test
    public void testToString() {
        final ReadOnlyStringProperty v1 = new ReadOnlyStringPropertyStub(null, "");
        assertEquals("ReadOnlyStringProperty [value: " + DEFAULT + "]", v1.toString());

        final ReadOnlyStringProperty v2 = new ReadOnlyStringPropertyStub(null, null);
        assertEquals("ReadOnlyStringProperty [value: " + DEFAULT + "]", v2.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ReadOnlyStringProperty v3 = new ReadOnlyStringPropertyStub(bean, name);
        assertEquals("ReadOnlyStringProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v3.toString());

        final ReadOnlyStringProperty v4 = new ReadOnlyStringPropertyStub(bean, "");
        assertEquals("ReadOnlyStringProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());

        final ReadOnlyStringProperty v5 = new ReadOnlyStringPropertyStub(bean, null);
        assertEquals("ReadOnlyStringProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v5.toString());

        final ReadOnlyStringProperty v6 = new ReadOnlyStringPropertyStub(null, name);
        assertEquals("ReadOnlyStringProperty [name: My name, value: " + DEFAULT + "]", v6.toString());

    }

    private static class ReadOnlyStringPropertyStub extends ReadOnlyStringProperty {

        private final Object bean;

        private final String name;

        private ReadOnlyStringPropertyStub(Object bean, String name) {
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
            return null;
        }

        @Override
        public void addListener(ChangeListener<? super String> listener) {
        }

        @Override
        public void removeListener(ChangeListener<? super String> listener) {
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super String> listener) {
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
