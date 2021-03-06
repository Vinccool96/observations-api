package io.github.vinccool96.observations.beans.value;

/**
 * An observable long value.
 *
 * @see ObservableValue
 * @see ObservableNumberValue
 */
public interface ObservableLongValue extends ObservableNumberValue {

    /**
     * Returns the current value of this {@code ObservableLongValue}.
     *
     * @return The current value
     */
    long get();

}
