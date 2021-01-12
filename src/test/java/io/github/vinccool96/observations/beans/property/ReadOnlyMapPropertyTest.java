package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.collections.MapChangeListener;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.util.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class ReadOnlyMapPropertyTest {

    private static final Object DEFAULT = null;

    @Test
    public void testBidirectionalContentBinding() {
        ObservableMap<String, String> model = ObservableCollections.observableHashMap(new Pair<>("A", "a"),
                new Pair<>("B", "b"));
        ObservableMap<String, String> map2 = ObservableCollections.observableHashMap(new Pair<>("A", "a"),
                new Pair<>("B", "b"),
                new Pair<>("C", "c"));
        ReadOnlyMapProperty<String, String> map1 = new SimpleMapProperty<>(model);
        assertNotEquals(map1, map2);

        map1.bindContentBidirectional(map2);
        assertEquals(map1, map2);
        map2.put("D", "d");
        assertEquals(map1, map2);
        map1.put("E", "e");
        assertEquals(map1, map2);
        map1.unbindContentBidirectional(map2);
        assertEquals(map1, map2);
        map2.remove("E");
        assertNotEquals(map1, map2);
        map2.put("E", "e");
        assertEquals(map1, map2);
        map1.put("F", "f");
        assertNotEquals(map1, map2);
    }

    @Test
    public void testContentBinding() {
        ObservableMap<String, String> model = ObservableCollections.observableHashMap(new Pair<>("A", "a"),
                new Pair<>("B", "b"));
        ObservableMap<String, String> map2 = ObservableCollections.observableHashMap(new Pair<>("A", "a"),
                new Pair<>("B", "b"),
                new Pair<>("C", "c"));
        ReadOnlyMapProperty<String, String> map1 = new SimpleMapProperty<>(model);
        assertNotEquals(map1, map2);

        map1.bindContent(map2);
        assertEquals(map1, map2);
        map2.put("D", "d");
        assertEquals(map1, map2);
        map1.put("E", "e");
        assertNotEquals(map1, map2);
        map1.remove("E");
        map1.unbindContent(map2);
        map2.put("E", "e");
        assertNotEquals(map1, map2);
    }

    @Test
    public void testToString() {
        final ReadOnlyMapProperty<Object, Object> v1 = new ReadOnlyMapPropertyStub(null, "");
        assertEquals("ReadOnlyMapProperty [value: " + DEFAULT + "]", v1.toString());

        final ReadOnlyMapProperty<Object, Object> v2 = new ReadOnlyMapPropertyStub(null, null);
        assertEquals("ReadOnlyMapProperty [value: " + DEFAULT + "]", v2.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ReadOnlyMapProperty<Object, Object> v3 = new ReadOnlyMapPropertyStub(bean, name);
        assertEquals("ReadOnlyMapProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v3.toString());

        final ReadOnlyMapProperty<Object, Object> v4 = new ReadOnlyMapPropertyStub(bean, "");
        assertEquals("ReadOnlyMapProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());

        final ReadOnlyMapProperty<Object, Object> v5 = new ReadOnlyMapPropertyStub(bean, null);
        assertEquals("ReadOnlyMapProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v5.toString());

        final ReadOnlyMapProperty<Object, Object> v6 = new ReadOnlyMapPropertyStub(null, name);
        assertEquals("ReadOnlyMapProperty [name: My name, value: " + DEFAULT + "]", v6.toString());

    }

    private static class ReadOnlyMapPropertyStub extends ReadOnlyMapProperty<Object, Object> {

        private final Object bean;

        private final String name;

        private ReadOnlyMapPropertyStub(Object bean, String name) {
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
            return null;
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

        @Override
        public void addListener(ChangeListener<? super ObservableMap<Object, Object>> listener) {
        }

        @Override
        public void removeListener(ChangeListener<? super ObservableMap<Object, Object>> listener) {
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableMap<Object, Object>> listener) {
            return false;
        }

        @Override
        public void addListener(MapChangeListener<? super Object, ? super Object> listChangeListener) {
        }

        @Override
        public void removeListener(MapChangeListener<? super Object, ? super Object> listChangeListener) {
        }

        @Override
        public boolean isMapChangeListenerAlreadyAdded(MapChangeListener<? super Object, ? super Object> listener) {
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
