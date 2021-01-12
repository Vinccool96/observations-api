package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.value.ChangeListenerMock;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.fail;

public class ReadOnlyMapPropertyBaseTest {

    private static final Object UNDEFINED = null;

    private static final Object DEFAULT = null;

    private static final ObservableMap<Object, Object> VALUE_1 =
            ObservableCollections.observableMap(Collections.emptyMap());

    private static final ObservableMap<Object, Object> VALUE_2 =
            ObservableCollections.observableMap(Collections.singletonMap(new Object(), new Object()));

    private ReadOnlyPropertyMock property;

    private InvalidationListenerMock invalidationListener;

    private ChangeListenerMock<Object> changeListener;

    @Before
    public void setUp() {
        property = new ReadOnlyPropertyMock();
        invalidationListener = new InvalidationListenerMock();
        changeListener = new ChangeListenerMock<>(UNDEFINED);
    }

    @Test
    public void testInvalidationListener() {
        property.addListener(invalidationListener);
        property.get();
        invalidationListener.reset();
        property.set(VALUE_1);
        invalidationListener.check(property, 1);
        property.removeListener(invalidationListener);
        invalidationListener.reset();
        property.set(VALUE_2);
        invalidationListener.check(null, 0);
    }

    @Test
    public void testChangeListener() {
        property.addListener(changeListener);
        property.get();
        changeListener.reset();
        property.set(VALUE_1);
        changeListener.check(property, DEFAULT, VALUE_1, 1);
        property.removeListener(changeListener);
        changeListener.reset();
        property.set(VALUE_2);
        changeListener.check(null, UNDEFINED, UNDEFINED, 0);
    }

    private static class ReadOnlyPropertyMock extends ReadOnlyMapPropertyBase<Object, Object> {

        private ObservableMap<Object, Object> value;

        @Override
        public Object getBean() {
            // not used
            return null;
        }

        @Override
        public String getName() {
            // not used
            return null;
        }

        private void set(ObservableMap<Object, Object> value) {
            this.value = value;
            fireValueChangedEvent();
        }

        @Override
        public ObservableMap<Object, Object> get() {
            return value;
        }

        @Override
        public ReadOnlyIntegerProperty sizeProperty() {
            fail("Not in use");
            return null;
        }

        @Override
        public ReadOnlyBooleanProperty emptyProperty() {
            fail("Not in use");
            return null;
        }

    }

}
