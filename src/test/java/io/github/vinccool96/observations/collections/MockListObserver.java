package io.github.vinccool96.observations.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A mock observer that tracks calls to its onChanged() method, combined with utility methods to make assertions on the
 * calls made.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class MockListObserver<E> implements ListChangeListener<E> {

    private boolean tooManyCalls;

    static class Call<E> {

        ObservableList<? extends E> list;

        List<? extends E> removed;

        int from;

        int to;

        private int[] permutation;

        private boolean update;

        @Override
        public String toString() {
            return "removed: " + removed + ", from: " + from + ", to: " + to + ", permutation: " +
                    Arrays.toString(permutation);
        }

    }

    List<Call<E>> calls = new LinkedList<>();

    @Override
    public void onChanged(Change<? extends E> change) {
        if (calls.isEmpty()) {
            while (change.next()) {
                Call<E> call = new Call<>();
                call.list = change.getList();
                call.removed = change.getRemoved();
                call.from = change.getFrom();
                call.to = change.getTo();
                if (change.wasPermutated()) {
                    call.permutation = new int[call.to - call.from];
                    for (int i = 0; i < call.permutation.length; ++i) {
                        call.permutation[i] = change.getPermutation(i + call.from);
                    }
                } else {
                    call.permutation = new int[0];
                }
                call.update = change.wasUpdated();
                calls.add(call);

                // Check generic change assertions
                assertFalse(change.wasPermutated() && change.wasUpdated());
                assertFalse((change.wasAdded() || change.wasRemoved()) && change.wasUpdated());
                assertFalse((change.wasAdded() || change.wasRemoved()) && change.wasPermutated());
            }
        } else {
            tooManyCalls = true;
        }
    }

    public void check0() {
        assertEquals(0, calls.size());
    }

    public void check1AddRemove(ObservableList<E> list, List<E> removed, int from, int to) {
        if (!tooManyCalls) {
            assertFalse(tooManyCalls);
        }
        assertEquals(1, calls.size());
        checkAddRemove(0, list, removed, from, to);
    }

    public void checkAddRemove(int idx, ObservableList<E> list, List<E> removed, int from, int to) {
        if (removed == null) {
            removed = Collections.emptyList();
        }
        if (!tooManyCalls) {
            assertFalse(tooManyCalls);
        }
        Call<E> call = calls.get(idx);
        assertSame(list, call.list);
        assertEquals(removed, call.removed);
        assertEquals(from, call.from);
        assertEquals(to, call.to);
        assertEquals(0, call.permutation.length);
    }

    public void check1Permutation(ObservableList<E> list, int[] perm) {
        assertFalse(tooManyCalls);
        assertEquals(1, calls.size());
        checkPermutation(0, list, 0, list.size(), perm);
    }

    public void check1Permutation(ObservableList<E> list, int from, int to, int[] perm) {
        assertFalse(tooManyCalls);
        assertEquals(1, calls.size());
        checkPermutation(0, list, from, to, perm);
    }

    public void checkPermutation(int idx, ObservableList<E> list, int from, int to, int[] perm) {
        assertFalse(tooManyCalls);
        Call<E> call = calls.get(idx);
        assertEquals(list, call.list);
        assertEquals(Collections.emptyList(), call.removed);
        assertEquals(from, call.from);
        assertEquals(to, call.to);
        assertArrayEquals(perm, call.permutation);
    }

    public void check1Update(ObservableList<E> list, int from, int to) {
        assertFalse(tooManyCalls);
        assertEquals(1, calls.size());
        checkUpdate(0, list, from, to);
    }

    public void checkUpdate(int idx, ObservableList<E> list, int from, int to) {
        assertFalse(tooManyCalls);
        Call<E> call = calls.get(idx);
        assertEquals(list, call.list);
        assertEquals(Collections.emptyList(), call.removed);
        assertArrayEquals(new int[0], call.permutation);
        assertTrue(call.update);
        assertEquals(from, call.from);
        assertEquals(to, call.to);
    }

    public void check1() {
        assertFalse(tooManyCalls);
        assertEquals(1, calls.size());
    }

    public void clear() {
        calls.clear();
        tooManyCalls = false;
    }

}
