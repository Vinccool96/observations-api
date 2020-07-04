package io.github.vinccool96.observations.beans.value;

/**
 * An observable short value.
 *
 * @see ObservableValue
 * @see ObservableNumberValue
 */
public interface ObservableShortValue extends ObservableNumberValue {

    /**
     * Returns the current value of this {@code ObservableIntegerValue}.
     *
     * @return The current value
     */
    short get();

}
