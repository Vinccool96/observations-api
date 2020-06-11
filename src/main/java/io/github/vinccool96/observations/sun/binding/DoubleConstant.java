package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableDoubleValue;

/**
 * A simple DoubleExpression that represents a single constant value.
 */
public final class DoubleConstant implements ObservableDoubleValue {

    private final double value;

    private DoubleConstant(double value) {
        this.value = value;
    }

    public static DoubleConstant valueOf(double value) {
        return new DoubleConstant(value);
    }

    @Override
    public double get() {
        return value;
    }

    @Override
    public Double getValue() {
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
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

}
