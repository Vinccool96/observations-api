package io.github.vinccool96.observations.beans.value;

/**
 * An observable boolean value.
 *
 * @see ObservableValue
 */
public interface ObservableBooleanValue extends ObservableValue<Boolean> {

    /**
     * Returns the current value of this {@code ObservableBooleanValue}.
     *
     * @return The current value
     */
    boolean get();

}
