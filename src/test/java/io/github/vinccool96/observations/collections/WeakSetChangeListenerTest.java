package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.collections.MockSetObserver.Tuple;
import io.github.vinccool96.observations.collections.SetChangeListener.Change;
import io.github.vinccool96.observations.sun.binding.SetExpressionHelper.SimpleChange;
import io.github.vinccool96.observations.sun.collections.ObservableSetWrapper;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

@SuppressWarnings("UnusedAssignment")
public class WeakSetChangeListenerTest {

    @Test(expected = NullPointerException.class)
    public void testConstructWithNull() {
        new WeakSetChangeListener<>(null);
    }

    @Test
    public void testHandle() {
        MockSetObserver<Object> listener = new MockSetObserver<>();
        final WeakSetChangeListener<Object> weakListener = new WeakSetChangeListener<>(listener);
        final ObservableSetMock set = new ObservableSetMock();
        final Object removedElement = new Object();
        final Change<Object> change = new SimpleChange<>(set).setRemoved(removedElement);

        // regular call
        weakListener.onChanged(change);
        listener.assertRemoved(Tuple.tup(removedElement));
        assertFalse(weakListener.wasGarbageCollected());

        // GC-ed call
        set.reset();
        listener = null;
        System.gc();
        assertTrue(weakListener.wasGarbageCollected());
        weakListener.onChanged(change);
        assertEquals(1, set.removeCounter);
    }

    private static class ObservableSetMock extends ObservableSetWrapper<Object> {

        private int removeCounter;

        public ObservableSetMock() {
            super(new HashSet<>());
        }

        private void reset() {
            removeCounter = 0;
        }

        @Override
        public void removeListener(SetChangeListener<? super Object> listener) {
            removeCounter++;
        }

    }

}
