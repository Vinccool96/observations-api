package io.github.vinccool96.observations.beans.value;

/**
 * An observable typed {@code Object} value.
 *
 * @param <T>
 *         The type of the wrapped value
 *
 * @see ObservableValue
 */
public interface ObservableObjectValue<T> extends ObservableValue<T> {

    /**
     * Returns the current value of this {@code ObservableObjectValue<T>}.
     *
     * @return The current value
     */
    T get();

}
