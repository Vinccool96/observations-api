package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.collections.ListChangeListener;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ListPropertyTest {

    private static final Object NO_BEAN = null;

    private static final String NO_NAME_1 = null;

    private static final String NO_NAME_2 = "";

    private static final ObservableList<Object> VALUE_1 = ObservableCollections.emptyObservableList();

    private static final ObservableList<Object> VALUE_2 = ObservableCollections.singletonObservableList(new Object());

    private static final Object DEFAULT = null;

    @Test
    public void testBindBidirectional() {
        final ListProperty<Object> p1 = new SimpleListProperty<Object>(VALUE_2);
        final ListProperty<Object> p2 = new SimpleListProperty<Object>(VALUE_1);

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
        final ListProperty<Object> v0 = new ListPropertyStub(NO_BEAN, NO_NAME_1);
        assertEquals("ListProperty [value: " + DEFAULT + "]", v0.toString());

        final ListProperty<Object> v1 = new ListPropertyStub(NO_BEAN, NO_NAME_2);
        assertEquals("ListProperty [value: " + DEFAULT + "]", v1.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ListProperty<Object> v2 = new ListPropertyStub(bean, name);
        assertEquals("ListProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v2.toString());
        v2.set(VALUE_1);
        assertEquals("ListProperty [bean: " + bean.toString() + ", name: My name, value: " + VALUE_1 + "]",
                v2.toString());

        final ListProperty<Object> v3 = new ListPropertyStub(bean, NO_NAME_1);
        assertEquals("ListProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v3.toString());
        v3.set(VALUE_1);
        assertEquals("ListProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v3.toString());

        final ListProperty<Object> v4 = new ListPropertyStub(bean, NO_NAME_2);
        assertEquals("ListProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());
        v4.set(VALUE_1);
        assertEquals("ListProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v4.toString());

        final ListProperty<Object> v5 = new ListPropertyStub(NO_BEAN, name);
        assertEquals("ListProperty [name: My name, value: " + DEFAULT + "]", v5.toString());
        v5.set(VALUE_1);
        assertEquals("ListProperty [name: My name, value: " + VALUE_1 + "]", v5.toString());
    }

    private class ListPropertyStub extends ListProperty<Object> {

        private final Object bean;

        private final String name;

        private ObservableList<Object> value;

        private ListPropertyStub(Object bean, String name) {
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
        public ObservableList<Object> get() {
            return value;
        }

        @Override
        public void set(ObservableList<Object> value) {
            this.value = value;
        }

        @Override
        public void bind(ObservableValue<? extends ObservableList<Object>> observable) {
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
        public void addListener(ChangeListener<? super ObservableList<Object>> listener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(ChangeListener<? super ObservableList<Object>> listener) {
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

        @Override
        public void addListener(ListChangeListener<? super Object> listChangeListener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(ListChangeListener<? super Object> listChangeListener) {
            fail("Not in use");
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ListChangeListener<? super Object> listChangeListener) {
            fail("Not in use");
            return false;
        }

        @Override
        public ReadOnlyIntegerProperty sizeProperty() {
            fail("Not in use");
            return null;
        }

        @Override
        public ReadOnlyBooleanProperty emptyProperty() {
            fail("Not in use");
            return null;
        }

    }

}
