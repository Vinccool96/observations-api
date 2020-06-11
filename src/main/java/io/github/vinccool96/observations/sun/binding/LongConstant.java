package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableLongValue;

/**
 * A simple LongExpression that represents a single constant value.
 */
public final class LongConstant implements ObservableLongValue {

    private final long value;

    private LongConstant(long value) {
        this.value = value;
    }

    public static LongConstant valueOf(long value) {
        return new LongConstant(value);
    }

    @Override
    public long get() {
        return value;
    }

    @Override
    public Long getValue() {
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
    public void addListener(ChangeListener<? super Number> observer) {
        // no-op
    }

    @Override
    public void removeListener(ChangeListener<? super Number> observer) {
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
        return value;
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
