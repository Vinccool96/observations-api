package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableFloatValue;

/**
 * A simple FloatExpression that represents a single constant value.
 */
public final class FloatConstant implements ObservableFloatValue {

    private final float value;

    private FloatConstant(float value) {
        this.value = value;
    }

    public static FloatConstant valueOf(float value) {
        return new FloatConstant(value);
    }

    @Override
    public float get() {
        return value;
    }

    @Override
    public Float getValue() {
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
    public void addListener(ChangeListener<? super Number> listener) {
        // no-op
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        // no-op
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super Number> listener) {
        // no-op
        return false;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

}
