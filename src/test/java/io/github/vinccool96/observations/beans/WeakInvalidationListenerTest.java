package io.github.vinccool96.observations.beans;

import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeakInvalidationListenerTest {

    @Test(expected = NullPointerException.class)
    public void testConstructWithNull() {
        new WeakInvalidationListener(null);
    }

    @Test
    public void testHandle() {
        InvalidationListenerMock listener = new InvalidationListenerMock();
        final WeakInvalidationListener weakListener = new WeakInvalidationListener(listener);
        final ObservableMock o = new ObservableMock();

        // regular call
        weakListener.invalidated(o);
        listener.check(o, 1);
        assertFalse(weakListener.wasGarbageCollected());

        // GC-ed call
        o.reset();
        listener = null;
        System.gc();
        assertTrue(weakListener.wasGarbageCollected());
        weakListener.invalidated(o);
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
        public void removeListener(ChangeListener<? super Object> listener) {
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
        public void removeListener(InvalidationListener listener) {
            removeCounter++;
        }

        @Override
        public boolean isChangeListenerAlreadyAdded(ChangeListener<? super Object> listener) {
            return false;
        }

    }

}
