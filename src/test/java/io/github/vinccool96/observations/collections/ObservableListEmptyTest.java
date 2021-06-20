package io.github.vinccool96.observations.collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Tests for initially empty ObservableList.
 */
@RunWith(Parameterized.class)
public class ObservableListEmptyTest {

    private final Callable<ObservableList<String>> listFactory;

    private ObservableList<String> list;

    private MockListObserver<String> mlo;

    public ObservableListEmptyTest(final Callable<ObservableList<String>> listFactory) {
        this.listFactory = listFactory;
    }

    @Parameters
    public static Collection<?> createParameters() {
        Object[][] data = new Object[][]{
                {TestedObservableLists.ARRAY_LIST},
                {TestedObservableLists.LINKED_LIST},
                {TestedObservableLists.CHECKED_OBSERVABLE_ARRAY_LIST},
                {TestedObservableLists.SYNCHRONIZED_OBSERVABLE_ARRAY_LIST},
                {TestedObservableLists.OBSERVABLE_LIST_PROPERTY}
        };
        return Arrays.asList(data);
    }

    @Before
    public void setUp() throws Exception {
        list = listFactory.call();
        mlo = new MockListObserver<>();
        list.addListener(mlo);
    }

    @Test
    public void testClearEmpty() {
        list.clear();
        mlo.check0();
    }

}
