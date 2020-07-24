package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.Observable;
import org.junit.Before;
import org.junit.Test;

public class ObservableValueBaseTest {

    private static final Object UNDEFINED_VALUE = new Object();

    private static final Object V1 = new Object();

    private static final Object V2 = new Object();

    private ObservableObjectValueStub<Object> valueModel;

    private InvalidationListenerMock invalidationListener;

    private ChangeListenerMock<Object> changeListener;

    @Before
    public void setUp() {
        valueModel = new ObservableObjectValueStub<>();
        invalidationListener = new InvalidationListenerMock();
        changeListener = new ChangeListenerMock<>(UNDEFINED_VALUE);
    }

    @Test
    public void testInitialState() {
        // no exceptions etc.
        valueModel.fireValueChangedEvent();
    }

    @Test
    public void testOneInvalidationListener() {
        // adding one observer
        valueModel.addListener(invalidationListener);
        System.gc(); // making sure we did not not overdo weak references
        valueModel.set(V1);
        invalidationListener.check(valueModel, 1);

        // remove observer
        valueModel.removeListener(invalidationListener);
        valueModel.set(V2);
        invalidationListener.check(null, 0);

        // remove observer again
        valueModel.removeListener(invalidationListener);
        valueModel.set(V1);
        invalidationListener.check(null, 0);
    }

    @Test
    public void testOneChangeListener() {
        // adding one observer
        valueModel.addListener(changeListener);
        System.gc(); // making sure we did not not overdo weak references
        valueModel.set(V1);
        changeListener.check(valueModel, null, V1, 1);

        // set same value again
        valueModel.set(V1);
        changeListener.check(null, UNDEFINED_VALUE, UNDEFINED_VALUE, 0);

        // set null
        valueModel.set(null);
        changeListener.check(valueModel, V1, null, 1);
        valueModel.set(null);
        changeListener.check(null, UNDEFINED_VALUE, UNDEFINED_VALUE, 0);

        // remove observer
        valueModel.removeListener(changeListener);
        valueModel.set(V2);
        changeListener.check(null, UNDEFINED_VALUE, UNDEFINED_VALUE, 0);

        // remove observer again
        valueModel.removeListener(changeListener);
        valueModel.set(V1);
        changeListener.check(null, UNDEFINED_VALUE, UNDEFINED_VALUE, 0);
    }

    @Test
    public void testTwoObservers() {
        final InvalidationListenerMock observer2 = new InvalidationListenerMock();

        // adding two observers
        valueModel.addListener(invalidationListener);
        valueModel.addListener(observer2);
        System.gc(); // making sure we did not not overdo weak references
        valueModel.fireValueChangedEvent();
        invalidationListener.check(valueModel, 1);
        observer2.check(valueModel, 1);

        // remove first observer
        valueModel.removeListener(invalidationListener);
        valueModel.fireValueChangedEvent();
        invalidationListener.check(null, 0);
        observer2.check(valueModel, 1);

        // remove second observer
        valueModel.removeListener(observer2);
        valueModel.fireValueChangedEvent();
        invalidationListener.check(null, 0);
        observer2.check(null, 0);

        // remove observers in reverse order
        valueModel.removeListener(observer2);
        valueModel.removeListener(invalidationListener);
        valueModel.fireValueChangedEvent();
        invalidationListener.check(null, 0);
        observer2.check(null, 0);
    }

    @Test
    public void testConcurrentAdd() {
        final InvalidationListenerMock observer2 = new AddingListenerMock();
        valueModel.addListener(observer2);

        // fire event that adds a second observer
        // Note: there is no assumption if observer that is being added is notified
        valueModel.fireValueChangedEvent();
        observer2.check(valueModel, 1);

        // fire event again, this time both observers need to be notified
        invalidationListener.reset();
        valueModel.fireValueChangedEvent();
        invalidationListener.check(valueModel, 1);
        observer2.check(valueModel, 1);
    }

    @Test
    public void testConcurrentRemove() {
        final InvalidationListenerMock observer2 = new RemovingListenerMock();
        valueModel.addListener(observer2);
        valueModel.addListener(invalidationListener);

        // fire event that removes one observer
        // Note: there is no assumption if observer that is being removed is notified
        valueModel.fireValueChangedEvent();
        observer2.check(valueModel, 1);

        // fire event again, this time only non-removed observer is notified
        invalidationListener.reset();
        valueModel.fireValueChangedEvent();
        invalidationListener.check(null, 0);
        observer2.check(valueModel, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testAddingNull_InvalidationListener() {
        valueModel.addListener((InvalidationListener) null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddingNull_ChangeListener() {
        valueModel.addListener((ChangeListener<Object>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemovingNull_InvalidationListener() {
        valueModel.removeListener((InvalidationListener) null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemovingNull_ChangeListener() {
        valueModel.removeListener((ChangeListener<Object>) null);
    }

    private class AddingListenerMock extends InvalidationListenerMock {

        @Override
        public void invalidated(Observable valueModel) {
            super.invalidated(valueModel);
            valueModel.addListener(invalidationListener);
        }

    }

    private class RemovingListenerMock extends InvalidationListenerMock {

        @Override
        public void invalidated(Observable valueModel) {
            super.invalidated(valueModel);
            valueModel.removeListener(invalidationListener);
        }

    }

}
