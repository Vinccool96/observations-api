package io.github.vinccool96.observations.beans.value;

/**
 * An observable float value.
 *
 * @see ObservableValue
 * @see ObservableNumberValue
*/
public interface ObservableFloatValue extends ObservableNumberValue {

    /**
     * Returns the current value of this {@code ObservableFloatValue}.
     *
     * @return The current value
     */
    float get();

}
