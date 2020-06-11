package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.beans.InvalidationListener;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeakChangeListenerTest {

    @Test(expected = NullPointerException.class)
    public void testConstructWithNull() {
        new WeakChangeListener<Object>(null);
    }

    @Test
    public void testHandle() {
        ChangeListenerMock<Object> listener = new ChangeListenerMock<Object>(new Object());
        final WeakChangeListener<Object> weakListener = new WeakChangeListener<Object>(listener);
        final ObservableMock o = new ObservableMock();
        final Object obj1 = new Object();
        final Object obj2 = new Object();

        // regular call
        weakListener.changed(o, obj1, obj2);
        listener.check(o, obj1, obj2, 1);
        assertFalse(weakListener.wasGarbageCollected());

        // GC-ed call
        o.reset();
        listener = null;
        System.gc();
        assertTrue(weakListener.wasGarbageCollected());
        weakListener.changed(o, obj2, obj1);
        assertEquals(1, o.removeCounter);
    }

    private static class ObservableMock implements ObservableValue<Object> {

        private int removeCounter;

        private void reset() {
            removeCounter = 0;
        }

        @Override
        public Object getValue() {
            return null;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            // not used
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            // not used
        }

        @Override
        public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
            // not used
            return false;
        }

        @Override
        public void addListener(ChangeListener<? super Object> listener) {
            // not used
        }

        @Override
        public void removeListener(ChangeListener<? super Object> listener) {
            removeCounter++;
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super Object> listener) {
            return false;
        }

    }

}
