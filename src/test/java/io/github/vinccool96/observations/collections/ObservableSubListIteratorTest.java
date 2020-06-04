package io.github.vinccool96.observations.collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for iterators of sublists of ObservableList. Note that this is a subclass of ObservableListIteratorTest. As
 * such, it inherits all the tests from that class, but they are run using the sublist-based fixture. There are also
 * some additional tests that make assertions about the underlying list after mutating the sublist via the iterator.
 */
@RunWith(Parameterized.class)
public class ObservableSubListIteratorTest extends ObservableListIteratorTest {

    // ========== Test Fixture ==========

    List<String> fullList;

    public ObservableSubListIteratorTest(final Callable<? extends List<String>> listFactory) {
        super(listFactory);
    }

    @Parameterized.Parameters
    public static Collection createParameters() {
        Object[][] data = new Object[][]{
                {TestedObservableLists.ARRAY_LIST},
                {TestedObservableLists.LINKED_LIST},
                {TestedObservableLists.VETOABLE_LIST},
                {TestedObservableLists.CHECKED_OBSERVABLE_ARRAY_LIST},
                {TestedObservableLists.SYNCHRONIZED_OBSERVABLE_ARRAY_LIST}
        };
        return Arrays.asList(data);
    }

    @Before @Override
    public void setup() throws Exception {
        list = listFactory.call();
        list.addAll(
                Arrays.asList("P", "Q", "a", "b", "c", "d", "e", "f", "R", "S"));
        fullList = list;
        list = fullList.subList(2, 8);
        iter = list.listIterator();
    }

    // ========== Sublist Iterator Tests ==========

    @Test
    public void testSubAddBeginning() {
        iter.add("X");
        assertEquals("[P, Q, X, a, b, c, d, e, f, R, S]", fullList.toString());
    }

    @Test
    public void testSubAddMiddle() {
        advance(iter, 3);
        iter.add("X");
        assertEquals("[P, Q, a, b, c, X, d, e, f, R, S]", fullList.toString());
    }

    @Test
    public void testSubAddEnd() {
        toEnd(iter);
        iter.add("X");
        assertEquals("[P, Q, a, b, c, d, e, f, X, R, S]", fullList.toString());
    }

    @Test
    public void testSubRemoveBeginning() {
        iter.next();
        iter.remove();
        assertEquals("[P, Q, b, c, d, e, f, R, S]", fullList.toString());
    }

    @Test
    public void testSubRemoveMiddle() {
        advance(iter, 3);
        iter.remove();
        assertEquals("[P, Q, a, b, d, e, f, R, S]", fullList.toString());
    }

    @Test
    public void testSubRemoveEnd() {
        toEnd(iter);
        iter.remove();
        assertEquals("[P, Q, a, b, c, d, e, R, S]", fullList.toString());
    }

    @Test
    public void testSubSetBeginning() {
        iter.next();
        iter.set("X");
        assertEquals("[P, Q, X, b, c, d, e, f, R, S]", fullList.toString());
    }

    @Test
    public void testSubSetMiddle() {
        advance(iter, 3);
        iter.set("X");
        assertEquals("[P, Q, a, b, X, d, e, f, R, S]", fullList.toString());
    }

    @Test
    public void testSubSetEnd() {
        toEnd(iter);
        iter.set("X");
        assertEquals("[P, Q, a, b, c, d, e, X, R, S]", fullList.toString());
    }

}
