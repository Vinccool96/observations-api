package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.property.ReadOnlyBooleanProperty;
import io.github.vinccool96.observations.beans.property.ReadOnlyIntegerProperty;
import io.github.vinccool96.observations.beans.value.ChangeListenerMock;
import io.github.vinccool96.observations.beans.value.ObservableValueBase;
import io.github.vinccool96.observations.collections.ListChangeListener;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.sun.collections.annotations.ReturnsUnmodifiableCollection;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 *
 */
@SuppressWarnings({"SimplifiableJUnitAssertion", "SameParameterValue"})
public class ListBindingTest {

    private ObservableStub dependency;

    private ListBindingImpl binding0;

    private ListBindingImpl binding1;

    private ObservableList<Object> emptyList;

    private ObservableList<Object> list1;

    private ObservableList<Object> list2;

    private ListChangeListenerMock listener;

    @Before
    public void setUp() {
        dependency = new ObservableStub();
        binding0 = new ListBindingImpl();
        binding1 = new ListBindingImpl(dependency);
        emptyList = ObservableCollections.observableArrayList();
        list1 = ObservableCollections.observableArrayList(new Object());
        list2 = ObservableCollections.observableArrayList(new Object(), new Object());
        listener = new ListChangeListenerMock();
        binding0.setValue(list2);
        binding1.setValue(list2);
    }

    @Test
    public void testSizeProperty() {
        ObservableStub dependency2 = new ObservableStub();
        ListBindingImpl binding2 = new ListBindingImpl(dependency, dependency2);
        binding2.setValue(list2);

        assertEquals(binding0, binding0.sizeProperty().getBean());
        assertEquals(binding1, binding1.sizeProperty().getBean());
        assertEquals(binding2, binding2.sizeProperty().getBean());

        final ReadOnlyIntegerProperty size = binding1.sizeProperty();
        assertEquals("size", size.getName());

        assertEquals(2, size.get());
        binding1.setValue(emptyList);
        dependency.fireValueChangedEvent();
        assertEquals(0, size.get());
        binding1.setValue(list1);
        dependency.fireValueChangedEvent();
        assertEquals(1, size.get());
        binding1.setValue(null);
        dependency.fireValueChangedEvent();
        assertEquals(0, size.get());

        assertEquals(2, binding2.sizeProperty().get());
        binding2.setValue(emptyList);
        dependency2.fireValueChangedEvent();
        assertEquals(0, binding2.sizeProperty().get());
    }

    @Test
    public void testEmptyProperty() {
        ObservableStub dependency2 = new ObservableStub();
        ListBindingImpl binding2 = new ListBindingImpl(dependency, dependency2);
        binding2.setValue(list2);

        assertEquals(binding0, binding0.emptyProperty().getBean());
        assertEquals(binding1, binding1.emptyProperty().getBean());
        assertEquals(binding2, binding2.emptyProperty().getBean());

        final ReadOnlyBooleanProperty empty = binding1.emptyProperty();
        assertEquals("empty", empty.getName());

        assertFalse(empty.get());
        binding1.setValue(emptyList);
        dependency.fireValueChangedEvent();
        assertTrue(empty.get());
        binding1.setValue(list1);
        dependency.fireValueChangedEvent();
        assertFalse(empty.get());
        binding1.setValue(null);
        dependency.fireValueChangedEvent();
        assertTrue(empty.get());

        assertFalse(binding2.emptyProperty().get());
        binding2.setValue(emptyList);
        dependency2.fireValueChangedEvent();
        assertTrue(binding2.emptyProperty().get());
    }

    @Test
    public void testNoDependency_ListChangeListener() {
        binding0.getValue();
        binding0.addListener(listener);
        System.gc(); // making sure we did not not overdo weak references
        assertEquals(true, binding0.isValid());

        // calling getValue()
        binding0.reset();
        binding0.getValue();
        assertEquals(0, binding0.getComputeValueCounter());
        listener.checkNotCalled();
        assertEquals(true, binding0.isValid());
    }

    @Test
    public void testSingleDependency_ListChangeListener() {
        binding1.getValue();
        binding1.addListener(listener);
        System.gc(); // making sure we did not not overdo weak references
        assertEquals(true, binding1.isValid());

        // fire single change event
        binding1.reset();
        listener.reset();
        binding1.setValue(list1);
        dependency.fireValueChangedEvent();
        assertEquals(1, binding1.getComputeValueCounter());
        listener.check(list2, list1, 1);
        assertEquals(true, binding1.isValid());

        binding1.getValue();
        assertEquals(0, binding1.getComputeValueCounter());
        listener.checkNotCalled();
        assertEquals(true, binding1.isValid());

        // fire single change event with same value
        binding1.setValue(list1);
        dependency.fireValueChangedEvent();
        assertEquals(1, binding1.getComputeValueCounter());
        listener.checkNotCalled();
        assertEquals(true, binding1.isValid());

        binding1.getValue();
        assertEquals(0, binding1.getComputeValueCounter());
        listener.checkNotCalled();
        assertEquals(true, binding1.isValid());

        // fire two change events
        binding1.setValue(list2);
        dependency.fireValueChangedEvent();
        binding1.setValue(list1);
        dependency.fireValueChangedEvent();
        assertEquals(2, binding1.getComputeValueCounter());
        listener.check(list2, list1, 2);
        assertEquals(true, binding1.isValid());

        binding1.getValue();
        assertEquals(0, binding1.getComputeValueCounter());
        listener.checkNotCalled();
        assertEquals(true, binding1.isValid());

        // fire two change events with same value
        binding1.setValue(list2);
        dependency.fireValueChangedEvent();
        binding1.setValue(list2);
        dependency.fireValueChangedEvent();
        assertEquals(2, binding1.getComputeValueCounter());
        listener.check(list1, list2, 1);
        assertEquals(true, binding1.isValid());

        binding1.getValue();
        assertEquals(0, binding1.getComputeValueCounter());
        listener.checkNotCalled();
        assertEquals(true, binding1.isValid());
    }

    @Test
    public void testChangeContent_InvalidationListener() {
        final InvalidationListenerMock listenerMock = new InvalidationListenerMock();
        binding1.get();
        binding1.addListener(listenerMock);
        assertTrue(binding1.isValid());

        binding1.reset();
        listenerMock.reset();
        list2.add(new Object());
        assertEquals(0, binding1.getComputeValueCounter());
        listenerMock.check(binding1, 1);
        assertTrue(binding1.isValid());
    }

    @Test
    public void testChangeContent_ChangeListener() {
        final ChangeListenerMock<Object> listenerMock = new ChangeListenerMock<>(null);
        binding1.get();
        binding1.addListener(listenerMock);
        assertTrue(binding1.isValid());

        binding1.reset();
        listenerMock.reset();
        list2.add(new Object());
        assertEquals(0, binding1.getComputeValueCounter());
        listenerMock.check(binding1, list2, list2, 1);
        assertTrue(binding1.isValid());
    }

    @Test
    public void testChangeContent_ListChangeListener() {
        binding1.get();
        binding1.addListener(listener);
        assertTrue(binding1.isValid());

        final int oldSize = list2.size();
        final Object newObject = new Object();
        binding1.reset();
        listener.reset();
        list2.add(newObject);
        assertEquals(0, binding1.getComputeValueCounter());
        listener.check(oldSize, newObject, 1);
        assertTrue(binding1.isValid());
    }

    private static class ObservableStub extends ObservableValueBase<Object> {

        private final Object value = new Object();

        @Override
        public void fireValueChangedEvent() {
            super.fireValueChangedEvent();
        }

        @Override
        public Object getValue() {
            return value;
        }

    }

    private static class ListBindingImpl extends ListBinding<Object> {

        private int computeValueCounter = 0;

        private ObservableList<Object> value;

        public void setValue(ObservableList<Object> value) {
            this.value = value;
        }

        public ListBindingImpl(Observable... dep) {
            super.bind(dep);
        }

        public int getComputeValueCounter() {
            final int result = computeValueCounter;
            reset();
            return result;
        }

        public void reset() {
            computeValueCounter = 0;
        }

        @Override
        public ObservableList<Object> computeValue() {
            computeValueCounter++;
            return value;
        }

        @Override @ReturnsUnmodifiableCollection
        public ObservableList<?> getDependencies() {
            fail("Should not reach here");
            return null;
        }

    }

    private static class ListChangeListenerMock implements ListChangeListener<Object> {

        private Change<?> change;

        private int counter;

        @Override
        public void onChanged(Change<?> change) {
            this.change = change;
            counter++;
        }

        private void reset() {
            change = null;
            counter = 0;
        }

        private void checkNotCalled() {
            assertEquals(null, change);
            assertEquals(0, counter);
            reset();
        }

        private void check(ObservableList<Object> oldList, ObservableList<Object> newList, int counter) {
            assertTrue(change.next());
            assertTrue(change.wasReplaced());
            assertEquals(oldList, change.getRemoved());
            assertEquals(newList, change.getList());
            assertFalse(change.next());
            assertEquals(counter, this.counter);
            reset();
        }

        private void check(int pos, Object newObject, int counter) {
            assertTrue(change.next());
            assertTrue(change.wasAdded());
            assertEquals(pos, change.getFrom());
            assertEquals(Collections.singletonList(newObject), change.getAddedSubList());
            assertFalse(change.next());
            assertEquals(counter, this.counter);
            reset();
        }

    }

}
