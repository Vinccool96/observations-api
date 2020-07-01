package io.github.vinccool96.observations.beans.value;

/**
 * An observable double value.
 *
 * @see ObservableValue
 * @see ObservableNumberValue
*/
public interface ObservableDoubleValue extends ObservableNumberValue {

    /**
     * Returns the current value of this {@code ObservableDoubleValue}.
     *
     * @return The current value
     */
    double get();

}
