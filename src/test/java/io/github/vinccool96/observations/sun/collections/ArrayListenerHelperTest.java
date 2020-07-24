package io.github.vinccool96.observations.sun.collections;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.collections.ArrayChangeListener;
import io.github.vinccool96.observations.collections.MockArrayObserver;
import io.github.vinccool96.observations.collections.ObservableIntegerArray;
import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class ArrayListenerHelperTest {

    private InvalidationListenerMock[] invalidationListenerMock;

    private MockArrayObserver[] changeListenerMock;

    private ArrayListenerHelper helper;

    private ObservableIntegerArray array;

    @Before
    public void setUp() {
        invalidationListenerMock = new InvalidationListenerMock[]{
                new InvalidationListenerMock(),
                new InvalidationListenerMock(),
                new InvalidationListenerMock(),
                new InvalidationListenerMock()
        };
        changeListenerMock = new MockArrayObserver[]{
                new MockArrayObserver<>(),
                new MockArrayObserver<>(),
                new MockArrayObserver<>(),
                new MockArrayObserver<>()
        };
        helper = null;
        array = new ObservableIntegerArrayImpl(0);
    }

    private void resetAllListeners() {
        for (final InvalidationListenerMock listener : invalidationListenerMock) {
            listener.reset();
        }
        for (final MockArrayObserver<ObservableIntegerArray> listener : changeListenerMock) {
            listener.reset();
        }
    }

    @Test(expected = NullPointerException.class)
    public void testAddInvalidationListener_Null() {
        ArrayListenerHelper.addListener(helper, array, (InvalidationListener) null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveInvalidationListener_Null() {
        ArrayListenerHelper.removeListener(helper, (InvalidationListener) null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveArrayChangeListener_Null() {
        ArrayListenerHelper.removeListener(helper, (ArrayChangeListener<ObservableIntegerArray>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddArrayChangeListener_Null() {
        ArrayListenerHelper.addListener(helper, array, (ArrayChangeListener<ObservableIntegerArray>) null);
    }

    @Test
    public void testEmpty() {
        assertFalse(ArrayListenerHelper.hasListeners(helper));

        // these should be no-ops
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        ArrayListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ArrayListenerHelper.removeListener(helper, changeListenerMock[0]);
    }

    @Test
    public void testInvalidation_Simple() {
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);

        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[1]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(null, 0);

        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        changeListenerMock[0].check0();

        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
    }

    @Test
    public void testInvalidation_AddInvalidation() {
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[1]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
    }

    @Test
    public void testInvalidation_AddChange() {
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        changeListenerMock[0].check1();
    }

    @Test
    public void testInvalidation_ChangeInPulse() {
        final InvalidationListener listener = observable -> {
            helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        };
        helper = ArrayListenerHelper.addListener(helper, array, listener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, listener);
        invalidationListenerMock[0].reset();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
    }

    @Test
    public void testChange_Simple() {
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[0].reset();

        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[1]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[1].check0();
        changeListenerMock[0].reset();

        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        invalidationListenerMock[0].check(null, 0);
        changeListenerMock[0].reset();

        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[0].reset();
    }

    @Test
    public void testChange_AddInvalidation() {
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        invalidationListenerMock[0].check(array, 1);
    }

    @Test
    public void testChange_AddChange() {
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[1]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
    }

    @Test
    public void testChange_ChangeInPulse() {
        final ArrayChangeListener listener = (observableArray, b, i, j) -> {
            helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        };
        helper = ArrayListenerHelper.addListener(helper, array, listener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, listener);
        changeListenerMock[0].reset();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
    }

    @Test
    public void testGeneric_AddInvalidation() {
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[1]);

        // first invalidation listener creates the array
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);

        // second and third invalidation listener enlarge the array
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[1]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[2]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(array, 1);

        // fourth invalidation listener fits into the array
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[3]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(array, 1);
        invalidationListenerMock[3].check(array, 1);
    }

    @Test
    public void testGeneric_AddInvalidationInPulse() {
        final ArrayChangeListener addListener =
                new ArrayChangeListener<ObservableIntegerArray>() {

                    int counter;

                    @Override
                    public void onChanged(ObservableIntegerArray observableArray, boolean sizeChanged, int from,
                            int to) {
                        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[counter++]);
                    }
                };
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(null, 0);

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(null, 0);

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(array, 1);
        invalidationListenerMock[3].check(null, 0);

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(array, 1);
        invalidationListenerMock[3].check(array, 1);
    }

    @Test
    public void testGeneric_RemoveInvalidation() {
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[1]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[2]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[3]);

        // remove first element
        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(array, 1);
        invalidationListenerMock[3].check(array, 1);

        // remove middle element
        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[2]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(array, 1);

        // remove last element
        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[3]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(null, 0);
        invalidationListenerMock[3].check(null, 0);

        // remove last invalidation with single change
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[1]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[1].check(null, 0);
        changeListenerMock[0].check1();
        changeListenerMock[0].reset();

        // remove invalidation if array is empty
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[1]);
        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[0].reset();
        changeListenerMock[1].reset();

        // remove last invalidation with two change
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
    }

    @Test
    public void testGeneric_RemoveInvalidationInPulse() {
        final ArrayChangeListener<ObservableIntegerArray> removeListener =
                new ArrayChangeListener<ObservableIntegerArray>() {

                    int counter;

                    @Override public void onChanged(ObservableIntegerArray observableArray, boolean sizeChanged,
                            int from, int to) {
                        helper = ArrayListenerHelper.removeListener(helper, invalidationListenerMock[counter++]);
                    }
                };
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[3]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[1]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[2]);

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        invalidationListenerMock[2].check(array, 1);

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(array, 1);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(array, 1);

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(array, 1);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(null, 0);

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(null, 0);
        invalidationListenerMock[3].check(null, 0);
        invalidationListenerMock[1].check(null, 0);
        invalidationListenerMock[2].check(null, 0);
    }

    @Test
    public void testGeneric_AddChange() {
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);

        // first change listener creates the array
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[0].reset();

        // second and third change listener enlarge the array
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[1]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[2]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        resetAllListeners();

        // fourth change listener fits into the array
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[3]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
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
                helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[counter++]);

            }
        };
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[1].check0();
        changeListenerMock[2].check0();
        changeListenerMock[3].check0();

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check0();
        changeListenerMock[3].check0();

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        changeListenerMock[3].check0();

        helper = ArrayListenerHelper.addListener(helper, array, addListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, addListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        changeListenerMock[3].check1();
    }

    @Test
    public void testGeneric_RemoveChange() {
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[1]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[2]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[3]);

        // remove first element
        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();
        changeListenerMock[3].check1();
        resetAllListeners();

        // remove middle element
        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[2]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[1].check1();
        changeListenerMock[2].check0();
        changeListenerMock[3].check1();
        resetAllListeners();

        // remove last element
        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[3]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[1].check1();
        changeListenerMock[2].check0();
        changeListenerMock[3].check0();
        resetAllListeners();

        // remove last change with single invalidation
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[1]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        changeListenerMock[1].check0();
        changeListenerMock[1].reset();

        // remove change if array is empty
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[1]);
        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        changeListenerMock[0].check0();
        changeListenerMock[0].reset();

        // remove last change with two invalidation
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[0]);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        invalidationListenerMock[0].check(array, 1);
        invalidationListenerMock[1].check(array, 1);
        changeListenerMock[0].check0();
        changeListenerMock[0].reset();
    }

    @Test
    public void testGeneric_RemoveChangeInPulse() {
        final InvalidationListener removeListener = new InvalidationListener() {

            int counter;

            @Override
            public void invalidated(Observable observable) {
                helper = ArrayListenerHelper.removeListener(helper, changeListenerMock[counter++]);
            }
        };
        helper = ArrayListenerHelper.addListener(helper, array, invalidationListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[0]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[3]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[1]);
        helper = ArrayListenerHelper.addListener(helper, array, changeListenerMock[2]);

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[3].check1();
        changeListenerMock[1].check1();
        changeListenerMock[2].check1();

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[3].check1();
        changeListenerMock[1].check0();
        changeListenerMock[2].check1();

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[3].check1();
        changeListenerMock[1].check0();
        changeListenerMock[2].check0();

        helper = ArrayListenerHelper.addListener(helper, array, removeListener);
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        helper = ArrayListenerHelper.removeListener(helper, removeListener);
        resetAllListeners();
        ArrayListenerHelper.fireValueChangedEvent(helper, false, 0, 1);
        changeListenerMock[0].check0();
        changeListenerMock[3].check0();
        changeListenerMock[1].check0();
        changeListenerMock[2].check0();
    }

    @Test
    public void testExceptionNotPropagatedFromSingleInvalidation() {
        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);
    }

    @Test
    public void testExceptionNotPropagatedFromMultipleInvalidation() {
        BitSet called = new BitSet();

        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            called.set(0);
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            called.set(1);
            throw new RuntimeException();
        });

        helper.fireValueChangedEvent(false, 0, 1);

        assertTrue(called.get(0));
        assertTrue(called.get(1));
    }

    @Test
    public void testExceptionNotPropagatedFromSingleChange() {
        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);
    }

    @Test
    public void testExceptionNotPropagatedFromMultipleChange() {
        BitSet called = new BitSet();

        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            called.set(0);
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            called.set(1);
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);

        assertTrue(called.get(0));
        assertTrue(called.get(1));
    }

    @Test
    public void testExceptionNotPropagatedFromMultipleChangeAndInvalidation() {
        BitSet called = new BitSet();

        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            called.set(0);
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            called.set(1);
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            called.set(2);
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            called.set(3);
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);

        assertTrue(called.get(0));
        assertTrue(called.get(1));
        assertTrue(called.get(2));
        assertTrue(called.get(3));
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInSingleInvalidation() {
        AtomicBoolean called = new AtomicBoolean(false);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.set(true));

        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);

        assertTrue(called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInMultipleInvalidation() {
        AtomicInteger called = new AtomicInteger(0);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.incrementAndGet());

        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);

        assertEquals(2, called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInSingleChange() {
        AtomicBoolean called = new AtomicBoolean(false);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.set(true));
        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);

        assertTrue(called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInMultipleChange() {
        AtomicInteger called = new AtomicInteger(0);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.incrementAndGet());

        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);

        assertEquals(2, called.get());
    }

    @Test
    public void testExceptionHandledByThreadUncaughtHandlerInMultipleChangeAndInvalidation() {
        AtomicInteger called = new AtomicInteger(0);

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> called.incrementAndGet());

        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (observableArray, sizeChanged, from, to) -> {
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            throw new RuntimeException();
        });
        helper = ArrayListenerHelper.addListener(helper, array, (Observable o) -> {
            throw new RuntimeException();
        });
        helper.fireValueChangedEvent(false, 0, 1);

        assertEquals(4, called.get());
    }

}
