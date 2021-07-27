package io.github.vinccool96.observations.collections;

import static org.junit.Assert.*;

/**
 * A mock observer that tracks calls to its onChanged() method, combined with utility methods to make assertions on the
 * calls made.
 */
@SuppressWarnings("SimplifiableAssertion")
public class MockArrayObserver<T extends ObservableArray<T>> implements ArrayChangeListener<T> {

    private boolean tooManyCalls;

    private Call<T> call;

    private static class Call<T> {

        private T array;

        private boolean sizeChanged;

        private int from;

        private int to;

        @Override
        public String toString() {
            return "sizeChanged: " + sizeChanged + ", from: " + from + ", to: " + to;
        }

    }

    @Override
    public void onChanged(T observableArray, boolean sizeChanged, int from, int to) {
        if (call == null) {
            call = new Call<>();
            call.array = observableArray;
            call.sizeChanged = sizeChanged;
            call.from = from;
            call.to = to;

            // Check generic change assertions
            assertFalse("Negative from index", from < 0);
            assertFalse("Negative to index", to < 0);
            assertFalse("from index is greater then to index", from > to);
            assertFalse("No change in both elements and size", from == to && !sizeChanged);
            assertFalse("from index is greater than array size", from < to && from >= observableArray.size());
            assertFalse("to index is greater than array size", from < to && to > observableArray.size());
        } else {
            tooManyCalls = true;
        }
    }

    public void check0() {
        assertNull(call);
    }

    public void checkOnlySizeChanged(T array) {
        assertFalse("Too many array change events", tooManyCalls);
        assertSame(array, call.array);
        assertEquals(true, call.sizeChanged);
    }

    public void checkOnlyElementsChanged(T array, int from, int to) {
        assertFalse("Too many array change events", tooManyCalls);
        assertSame(array, call.array);
        assertEquals(false, call.sizeChanged);
        assertEquals(from, call.from);
        assertEquals(to, call.to);
    }

    public void check(T array, boolean sizeChanged, int from, int to) {
        assertFalse("Too many array change events", tooManyCalls);
        assertSame(array, call.array);
        assertEquals(sizeChanged, call.sizeChanged);
        assertEquals(from, call.from);
        assertEquals(to, call.to);
    }

    public void check1() {
        assertFalse("Too many array change events", tooManyCalls);
        assertNotNull(call);
    }

    public void reset() {
        call = null;
        tooManyCalls = false;
    }

}
