package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.sun.collections.NonIterableChange;
import io.github.vinccool96.observations.sun.collections.ObservableListWrapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WeakListChangeListenerTest {

    @Test(expected = NullPointerException.class)
    public void testConstructWithNull() {
        new WeakListChangeListener<Object>(null);
    }

    @Test
    public void testHandle() {
        MockListObserver<Object> listener = new MockListObserver<Object>();
        final WeakListChangeListener<Object> weakListener = new WeakListChangeListener<Object>(listener);
        final ObservableListWrapper<Object> list = new ObservableListWrapper<Object>(new ArrayList<Object>());
        final Object removedElement = new Object();
        final ListChangeListener.Change<Object> change =
                new NonIterableChange.SimpleRemovedChange<Object>(0, 1, removedElement, list);

        // regular call
        weakListener.onChanged(change);
        listener.check1AddRemove(list, Collections.singletonList(removedElement), 0, 1);
        assertFalse(weakListener.wasGarbageCollected());

        // GC-ed call
        listener = null;
        System.gc();
        assertTrue(weakListener.wasGarbageCollected());
        weakListener.onChanged(change);

    }

}
