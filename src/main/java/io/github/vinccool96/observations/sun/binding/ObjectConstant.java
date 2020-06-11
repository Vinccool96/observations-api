package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableObjectValue;

public class ObjectConstant<T> implements ObservableObjectValue<T> {

    private final T value;

    private ObjectConstant(T value) {
        this.value = value;
    }

    public static <T> ObjectConstant<T> valueOf(T value) {
        return new ObjectConstant<T>(value);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void addListener(InvalidationListener observer) {
        // no-op
    }

    @Override
    public void removeListener(InvalidationListener observer) {
        // no-op
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        // no-op
        return false;
    }

    @Override
    public void addListener(ChangeListener<? super T> observer) {
        // no-op
    }

    @Override
    public void removeListener(ChangeListener<? super T> observer) {
        // no-op
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super T> listener) {
        // no-op
        return false;
    }

}
