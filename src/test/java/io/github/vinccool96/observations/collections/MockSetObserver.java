package io.github.vinccool96.observations.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
public class MockSetObserver<E> implements SetChangeListener<E> {

    private final List<Call<E>> calls = new ArrayList<>();

    @Override
    public void onChanged(Change<? extends E> c) {
        calls.add(new Call<>(c.getElementRemoved(), c.getElementAdded()));
    }

    public int getCallsNumber() {
        return calls.size();
    }

    public void clear() {
        calls.clear();
    }

    public void check0() {
        assertEquals(0, calls.size());
    }

    public void assertAdded(Tuple<E> tuple) {
        assertAdded(0, tuple);
    }

    public void assertAdded(int call, Tuple<E> tuple) {
        assertTrue("Missing call to the observer # " + call, call < calls.size());
        assertEquals(calls.get(call).added, tuple.val);
    }

    @SuppressWarnings("unchecked")
    public void assertMultipleCalls(Call<E>... calls) {
        assertEquals(this.calls.size(), calls.length);
        for (Call<E> c : calls) {
            assertTrue(Arrays.toString(calls) + " doesn't contain " + c, this.calls.contains(c));
        }
    }

    @SuppressWarnings("unchecked")
    public void assertMultipleRemove(Tuple<E>... tuples) {
        assertEquals(this.calls.size(), tuples.length);
        for (Tuple<E> t : tuples) {
            assertTrue(calls + " doesn't contain " + t, this.calls.contains(new Call<>(t.val, null)));
        }
    }

    public void assertRemoved(Tuple<E> tuple) {
        assertRemoved(0, tuple);
    }

    public void assertRemoved(int call, Tuple<E> tuple) {
        assertTrue("Missing call to the observer # " + call, call < calls.size());
        assertEquals(calls.get(call).removed, tuple.val);
    }

    @SuppressWarnings("unchecked")
    public void assertMultipleRemoved(Tuple<E>... tuples) {
        for (Tuple<E> t : tuples) {
            boolean found = false;
            for (Call<E> c : calls) {
                if (c.removed.equals(t.val)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    public static class Call<E> {

        private final E removed;

        private final E added;

        public Call(E removed, E added) {
            this.removed = removed;
            this.added = added;
        }

        public static <E> Call<E> call(E o, E n) {
            return new Call<>(o, n);
        }

        @Override
        @SuppressWarnings({"unchecked", "EqualsReplaceableByObjectsCall", "RedundantIfStatement"})
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Call<E> other = (Call<E>) obj;
            if (this.removed != other.removed && (this.removed == null || !this.removed.equals(other.removed))) {
                return false;
            }
            if (this.added != other.added && (this.added == null || !this.added.equals(other.added))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 47 * hash + (this.removed != null ? this.removed.hashCode() : 0);
            hash = 47 * hash + (this.added != null ? this.added.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "[ " + added + " (" + removed + ") ]";
        }

    }

    public static class Tuple<E> {

        public E val;

        private Tuple(E val) {
            this.val = val;
        }

        public static <E> Tuple<E> tup(E v) {
            return new Tuple<>(v);
        }

    }

}
