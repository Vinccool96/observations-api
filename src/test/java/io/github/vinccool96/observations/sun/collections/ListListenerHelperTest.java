package io.github.vinccool96.observations.sun.collections;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.collections.ListChangeListener;
import io.github.vinccool96.observations.collections.MockListObserver;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class ListListenerHelperTest {

    private InvalidationListenerMock[] invalidationListenerMock;

    private MockListObserver<Object>[] changeListenerMock;

    private ListListenerHelper<Object> helper;

    private ObservableList<Object> list;

    private ListChangeListener.Change<Object> change;

    @Before
    public void setUp() {
        invalidationListenerMock = new InvalidationListenerMock[]{
                new InvalidationListenerMock(),
                new InvalidationListenerMock(),
                new InvalidationListenerMock(),
                new InvalidationListenerMock()
        };
        changeListenerMock = new MockListObserver[]{
                new MockListObserver<Object>(),
                new MockListObserver<Object>(),
                new MockListObserver<Object>(),
                new MockListObserver<Object>()
        };
        helper = null;
        list = ObservableCollections.emptyObservableList();
        change = new NonIterableChange.SimpleRemovedChange<Object>(0, 1, new Object(), list);
    }

    private void resetAllListeners() {
        for (final InvalidationListenerMock listener : invalidationListenerMock) {
            listener.reset();
        }
        for (final MockListObserver<Object> listener : changeListenerMock) {
            listener.clear();
        }
    }

    @Test(expected = NullPointerException.class)
    public void testAddInvalidationListener_Null() {
        ListListenerHelper.addListener(helper, (InvalidationListener) null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveInvalidationListener_Null() {
        ListListenerHelper.removeListener(helper, (InvalidationListener) null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveListChangeListener_Null() {
        ListListenerHelper.removeListener(helper, (ListChangeListener<Object>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddListChangeListener_Null() {
        ListListenerHelper.addListener(helper, (ListChangeListener<Object>) null);
    }

    @Test
    public void testEmpty() {
        assertFalse(ListListenerHelper.hasListeners(helper));

        // these should be no-ops
        ListListenerHelper.fireValueChangedEvent(helper, change);
        ListListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.removeListener(helper, changeListenerMock[0]);
    }

    @Test
    public void testInvalidation_Simple() {
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);

        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[1]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(null, 0);

        helper = ListListenerHelper.removeListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        changeListenerMock[0].check0();

        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
    }

    @Test
    public void testInvalidation_AddInvalidation() {
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[1]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
    }

    @Test
    public void testInvalidation_AddChange() {
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        changeListenerMock[0].check1();
    }

    @Test
    public void testInvalidation_ChangeInPulse() {
        final InvalidationListener listener = observable -> {
            helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        };
        helper = ListListenerHelper.addListener(helper, listener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, listener);
        invalidationListenerMock[0].reset();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
    }

    @Test
    public void testChange_Simple() {
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[0].clear();

        helper = ListListenerHelper.removeListener(helper, changeListenerMock[1]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check0();
        changeListenerMock[0].clear();

        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        invalidationListenerMock[0].check(null, 0);
        changeListenerMock[0].clear();

        helper = ListListenerHelper.removeListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[0].clear();
    }

    @Test
    public void testChange_AddInvalidation() {
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        invalidationListenerMock[0].check(list, 1);
    }

    @Test
    public void testChange_AddChange() {
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[1]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
    }

    @Test
    public void testChange_ChangeInPulse() {
        final ListChangeListener<Object> listener = c -> {
            helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        };
        helper = ListListenerHelper.addListener(helper, listener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, listener);
        changeListenerMock[0].clear();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
    }

    @Test
    public void testGeneric_AddInvalidation() {
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[1]);

        // first invalidation listener creates the array
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);

        // second and third invalidation listener enlarge the array
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[1]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[2]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(list, 1);

        // fourth invalidation listener fits into the array
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[3]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(list, 1);
        invalidationListenerMock[3].check(list, 1);
    }

    @Test
    public void testGeneric_AddInvalidationInPulse() {
        final ListChangeListener<Object> addListener = new ListChangeListener<Object>() {

            int counter;

            @Override
            public void onChanged(Change<? extends Object> change) {
                helper = ListListenerHelper.addListener(helper, invalidationListenerMock[counter++]);
            }
        };
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(null, 0);

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(null, 0);

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(list, 1);
        invalidationListenerMock[3].check(null, 0);

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(list, 1);
        invalidationListenerMock[3].check(list, 1);
    }

    @Test
    public void testGeneric_RemoveInvalidation() {
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[1]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[2]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[3]);

        // remove first element
        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(list, 1);
        invalidationListenerMock[3].check(list, 1);

        // remove middle element
        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[2]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(list, 1);

        // remove last element
        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[3]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(null, 0);

        // remove last invalidation with single change
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[1]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[1].check(null, 0);
        changeListenerMock[0].check1();
        changeListenerMock[0].clear();

        // remove invalidation if array is empty
        helper = ListListenerHelper.addListener(helper, changeListenerMock[1]);
        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[0].clear();
        changeListenerMock[1].clear();

        // remove last invalidation with two change
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
    }

    @Test
    public void testGeneric_RemoveInvalidationInPulse() {
        final ListChangeListener<Object> removeListener = new ListChangeListener<Object>() {

            int counter;

            @Override
            public void onChanged(Change<? extends Object> change) {
                helper = ListListenerHelper.removeListener(helper, invalidationListenerMock[counter++]);
            }
        };
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[3]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[1]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[2]);

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        invalidationListenerMock[2].check(list, 1);

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(list, 1);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(list, 1);

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(list, 1);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(null, 0);

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(null, 0);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(null, 0);
    }

    @Test
    public void testGeneric_AddChange() {
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);

        // first change listener creates the array
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[0].clear();

        // second and third change listener enlarge the array
        helper = ListListenerHelper.addListener(helper, changeListenerMock[1]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[2]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        resetAllListeners();

        // fourth change listener fits into the array
        helper = ListListenerHelper.addListener(helper, changeListenerMock[3]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        changeListenerMock[3].check1();
    }

    @Test
    public void testGeneric_AddChangeInPulse() {
        final InvalidationListener addListener = new InvalidationListener() {

            int counter;

            @Override
            public void invalidated(Observable observable) {
                helper = ListListenerHelper.addListener(helper, changeListenerMock[counter++]);

            }
        };
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check0();
        changeListenerMock[2].check0();
        changeListenerMock[3].check0();

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check0();
        changeListenerMock[3].check0();

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        changeListenerMock[3].check0();

        helper = ListListenerHelper.addListener(helper, addListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        changeListenerMock[3].check1();
    }

    @Test
    public void testGeneric_RemoveChange() {
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[1]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[2]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[3]);

        // remove first element
        helper = ListListenerHelper.removeListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        changeListenerMock[3].check1();
        resetAllListeners();

        // remove middle element
        helper = ListListenerHelper.removeListener(helper, changeListenerMock[2]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[1].check1();
        changeListenerMock[2].check0();
        changeListenerMock[3].check1();
        resetAllListeners();

        // remove last element
        helper = ListListenerHelper.removeListener(helper, changeListenerMock[3]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[1].check1();
        changeListenerMock[2].check0();
        changeListenerMock[3].check0();
        resetAllListeners();

        // remove last change with single invalidation
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.removeListener(helper, changeListenerMock[1]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        changeListenerMock[1].check0();
        changeListenerMock[1].clear();

        // remove change if array is empty
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[1]);
        helper = ListListenerHelper.removeListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        changeListenerMock[0].check0();
        changeListenerMock[0].clear();

        // remove last change with two invalidation
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.removeListener(helper, changeListenerMock[0]);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        invalidationListenerMock[0].check(list, 1);
        invalidationListenerMock[1].check(list, 1);
        changeListenerMock[0].check0();
        changeListenerMock[0].clear();
    }

    @Test
    public void testGeneric_RemoveChangeInPulse() {
        final InvalidationListener removeListener = new InvalidationListener() {

            int counter;

            @Override
            public void invalidated(Observable observable) {
                helper = ListListenerHelper.removeListener(helper, changeListenerMock[counter++]);
            }
        };
        helper = ListListenerHelper.addListener(helper, invalidationListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[0]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[3]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[1]);
        helper = ListListenerHelper.addListener(helper, changeListenerMock[2]);

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[3].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[3].check1();
        changeListenerMock[1].check0();
        changeListenerMock[2].check1();

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[3].check1();
        changeListenerMock[1].check0();
        changeListenerMock[2].check0();

        helper = ListListenerHelper.addListener(helper, removeListener);
        ListListenerHelper.fireValueChangedEvent(helper, change);
        helper = ListListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ListListenerHelper.fireValueChangedEvent(helper, change);
        changeListenerMock[0].check0();
        changeListenerMock[3].check0();
        changeListenerMock[1].check0();
        changeListenerMock[2].check0();
    }

    @Test
    public void testExceptionNotPropagatedFromSingleInvalidation() {
        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);
    }

    @Test
    public void testExceptionNotPropagatedFromMultipleInvalidation() {
        BitSet called = new BitSet();

        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            called.set(0);
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            called.set(1);
            throw new RuntimeException();
        });

        helper.fireValueChangedEvent(change);

        assertTrue(called.get(0));
        assertTrue(called.get(1));
    }

    @Test
    public void testExceptionNotPropagatedFromSingleChange() {
        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);
    }

    @Test
    public void testExceptionNotPropagatedFromMultipleChange() {
        BitSet called = new BitSet();

        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            called.set(0);
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            called.set(1);
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);

        assertTrue(called.get(0));
        assertTrue(called.get(1));
    }

    @Test
    public void testExceptionNotPropagatedFromMultipleChangeAndInvalidation() {
        BitSet called = new BitSet();

        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            called.set(0);
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            called.set(1);
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            called.set(2);
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            called.set(3);
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);

        assertTrue(called.get(0));
        assertTrue(called.get(1));
        assertTrue(called.get(2));
        assertTrue(called.get(3));
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInSingleInvalidation() {
        AtomicBoolean called = new AtomicBoolean(false);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.set(true));

        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);

        assertTrue(called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInMultipleInvalidation() {
        AtomicInteger called = new AtomicInteger(0);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.incrementAndGet());

        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);

        assertEquals(2, called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInSingleChange() {
        AtomicBoolean called = new AtomicBoolean(false);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.set(true));
        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);

        assertTrue(called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInMultipleChange() {
        AtomicInteger called = new AtomicInteger(0);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.incrementAndGet());

        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);

        assertEquals(2, called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInMultipleChangeAndInvalidation() {
        AtomicInteger called = new AtomicInteger(0);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.incrementAndGet());

        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (ListChangeListener.Change<? extends Object> c) -> {
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            throw new RuntimeException();
        });
        helper = ListListenerHelper.addListener(helper, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(change);

        assertEquals(4, called.get());
    }

}
