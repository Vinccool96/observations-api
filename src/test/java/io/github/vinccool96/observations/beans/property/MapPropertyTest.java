package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.collections.MapChangeListener;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableMap;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class MapPropertyTest {

    private static final Object NO_BEAN = null;

    private static final String NO_NAME_1 = null;

    private static final String NO_NAME_2 = "";

    private static final ObservableMap<Object, Object> VALUE_1 =
            ObservableCollections.observableMap(Collections.emptyMap());

    private static final ObservableMap<Object, Object> VALUE_2 =
            ObservableCollections.observableMap(Collections.singletonMap(new Object(), new Object()));

    private static final Object DEFAULT = null;

    @Test
    public void testBindBidirectional() {
        final MapProperty<Object, Object> p1 = new SimpleMapProperty<>(VALUE_2);
        final MapProperty<Object, Object> p2 = new SimpleMapProperty<>(VALUE_1);

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
        final MapProperty<Object, Object> v0 = new MapPropertyStub(NO_BEAN, NO_NAME_1);
        assertEquals("MapProperty [value: " + DEFAULT + "]", v0.toString());

        final MapProperty<Object, Object> v1 = new MapPropertyStub(NO_BEAN, NO_NAME_2);
        assertEquals("MapProperty [value: " + DEFAULT + "]", v1.toString());

        final Object bean = new Object();
        final String name = "My name";
        final MapProperty<Object, Object> v2 = new MapPropertyStub(bean, name);
        assertEquals("MapProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v2.toString());
        v2.set(VALUE_1);
        assertEquals("MapProperty [bean: " + bean.toString() + ", name: My name, value: " + VALUE_1 + "]",
                v2.toString());

        final MapProperty<Object, Object> v3 = new MapPropertyStub(bean, NO_NAME_1);
        assertEquals("MapProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v3.toString());
        v3.set(VALUE_1);
        assertEquals("MapProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v3.toString());

        final MapProperty<Object, Object> v4 = new MapPropertyStub(bean, NO_NAME_2);
        assertEquals("MapProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());
        v4.set(VALUE_1);
        assertEquals("MapProperty [bean: " + bean.toString() + ", value: " + VALUE_1 + "]", v4.toString());

        final MapProperty<Object, Object> v5 = new MapPropertyStub(NO_BEAN, name);
        assertEquals("MapProperty [name: My name, value: " + DEFAULT + "]", v5.toString());
        v5.set(VALUE_1);
        assertEquals("MapProperty [name: My name, value: " + VALUE_1 + "]", v5.toString());
    }

    private static class MapPropertyStub extends MapProperty<Object, Object> {

        private final Object bean;

        private final String name;

        private ObservableMap<Object, Object> value;

        private MapPropertyStub(Object bean, String name) {
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
        public ObservableMap<Object, Object> get() {
            return value;
        }

        @Override
        public void set(ObservableMap<Object, Object> value) {
            this.value = value;
        }

        @Override
        public void bind(ObservableValue<? extends ObservableMap<Object, Object>> observable) {
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

        @Override
        public void addListener(ChangeListener<? super ObservableMap<Object, Object>> listener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(ChangeListener<? super ObservableMap<Object, Object>> listener) {
            fail("Not in use");
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableMap<Object, Object>> listener) {
            fail("Not in use");
            return false;
        }

        @Override
        public void addListener(MapChangeListener<? super Object, ? super Object> listChangeListener) {
            fail("Not in use");
        }

        @Override
        public void removeListener(MapChangeListener<? super Object, ? super Object> listChangeListener) {
            fail("Not in use");
        }

        @Override
        public boolean isMapChangeListenerAlreadyAdded(MapChangeListener<? super Object, ? super Object> listener) {
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
