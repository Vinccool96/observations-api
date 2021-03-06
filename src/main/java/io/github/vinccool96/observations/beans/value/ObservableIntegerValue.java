package io.github.vinccool96.observations.beans.value;

/**
 * An observable integer value.
 *
 * @see ObservableValue
 * @see ObservableNumberValue
 */
public interface ObservableIntegerValue extends ObservableNumberValue {

    /**
     * Returns the current value of this {@code ObservableIntegerValue}.
     *
     * @return The current value
     */
    int get();

}
