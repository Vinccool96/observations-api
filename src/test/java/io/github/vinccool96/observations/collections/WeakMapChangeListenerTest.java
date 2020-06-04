package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.sun.binding.MapExpressionHelper;
import io.github.vinccool96.observations.sun.collections.ObservableMapWrapper;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class WeakMapChangeListenerTest {

    @Test(expected = NullPointerException.class)
    public void testConstructWithNull() {
        new WeakMapChangeListener<Object, Object>(null);
    }

    @Test
    public void testHandle() {
        MockMapObserver<Object, Object> listener = new MockMapObserver<Object, Object>();
        final WeakMapChangeListener<Object, Object> weakListener = new WeakMapChangeListener<Object, Object>(listener);
        final ObservableMapMock map = new ObservableMapMock();
        final Object key = new Object();
        final Object value = new Object();
        final MapChangeListener.Change<Object, Object> change =
                new MapExpressionHelper.SimpleChange<Object, Object>(map).setRemoved(key, value);

        // regular call
        weakListener.onChanged(change);
        listener.assertRemoved(MockMapObserver.Tuple.tup(key, value));
        assertFalse(weakListener.wasGarbageCollected());

        // GC-ed call
        map.reset();
        listener = null;
        System.gc();
        assertTrue(weakListener.wasGarbageCollected());
        weakListener.onChanged(change);
        assertEquals(1, map.removeCounter);
    }

    private static class ObservableMapMock extends ObservableMapWrapper<Object, Object> {

        private int removeCounter;

        public ObservableMapMock() {
            super(new HashMap<Object, Object>());
        }

        private void reset() {
            removeCounter = 0;
        }

        @Override
        public void removeListener(MapChangeListener<? super Object, ? super Object> listener) {
            removeCounter++;
        }

    }

}
