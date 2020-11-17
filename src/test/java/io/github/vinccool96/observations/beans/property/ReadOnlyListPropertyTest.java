package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.collections.ListChangeListener;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class ReadOnlyListPropertyTest {

    private static final Object DEFAULT = null;

    @Test
    public void testBidirectionalContentBinding() {
        ObservableList<Object> model = ObservableCollections.observableArrayList(new Object(), new Object());
        ObservableList<Object> list2 = ObservableCollections.observableArrayList(new Object(), new Object(),
                new Object());
        ReadOnlyListProperty<Object> list1 = new SimpleListProperty<>(model);
        assertNotEquals(list1, list2);
        list1.bindContentBidirectional(list2);
        assertEquals(list1, list2);
        list2.add(new Object());
        assertEquals(list1, list2);
        list1.add(new Object());
        assertEquals(list1, list2);
        list1.unbindContentBidirectional(list2);
        list2.add(new Object());
        assertNotEquals(list1, list2);
        list1.add(new Object());
        assertNotEquals(list1, list2);
    }

    @Test
    public void testContentBinding() {
        ObservableList<Object> model = ObservableCollections.observableArrayList(new Object(), new Object());
        ObservableList<Object> list2 = ObservableCollections.observableArrayList(new Object(), new Object(),
                new Object());
        ReadOnlyListProperty<Object> list1 = new SimpleListProperty<>(model);
        assertNotEquals(list1, list2);
        list1.bindContent(list2);
        assertEquals(list1, list2);
        list2.add(new Object());
        assertEquals(list1, list2);
        list1.add(new Object());
        assertNotEquals(list1, list2);
        list1.remove(list1.size() - 1);
        list1.unbindContent(list2);
        list2.add(new Object());
        assertNotEquals(list1, list2);
        list1.add(new Object());
        assertNotEquals(list1, list2);
    }

    @Test
    public void testToString() {
        final ReadOnlyListProperty<Object> v1 = new ReadOnlyListPropertyStub(null, "");
        assertEquals("ReadOnlyListProperty [value: " + DEFAULT + "]", v1.toString());

        final ReadOnlyListProperty<Object> v2 = new ReadOnlyListPropertyStub(null, null);
        assertEquals("ReadOnlyListProperty [value: " + DEFAULT + "]", v2.toString());

        final Object bean = new Object();
        final String name = "My name";
        final ReadOnlyListProperty<Object> v3 = new ReadOnlyListPropertyStub(bean, name);
        assertEquals("ReadOnlyListProperty [bean: " + bean.toString() + ", name: My name, value: " + DEFAULT + "]",
                v3.toString());

        final ReadOnlyListProperty<Object> v4 = new ReadOnlyListPropertyStub(bean, "");
        assertEquals("ReadOnlyListProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v4.toString());

        final ReadOnlyListProperty<Object> v5 = new ReadOnlyListPropertyStub(bean, null);
        assertEquals("ReadOnlyListProperty [bean: " + bean.toString() + ", value: " + DEFAULT + "]", v5.toString());

        final ReadOnlyListProperty<Object> v6 = new ReadOnlyListPropertyStub(null, name);
        assertEquals("ReadOnlyListProperty [name: My name, value: " + DEFAULT + "]", v6.toString());

    }

    private static class ReadOnlyListPropertyStub extends ReadOnlyListProperty<Object> {

        private final Object bean;

        private final String name;

        private ReadOnlyListPropertyStub(Object bean, String name) {
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
            return null;
        }

        @Override
        public void addListener(ChangeListener<? super ObservableList<Object>> listener) {
        }

        @Override
        public void removeListener(ChangeListener<? super ObservableList<Object>> listener) {
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableList<Object>> listener) {
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

        @Override
        public void addListener(ListChangeListener<? super Object> listChangeListener) {
        }

        @Override
        public void removeListener(ListChangeListener<? super Object> listChangeListener) {
        }

        @Override
        public boolean isListChangeListenerAlreadyAdded(ListChangeListener<? super Object> listener) {
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
