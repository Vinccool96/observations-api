package io.github.vinccool96.observations.beans.value;

/**
 * A writable {@code double} value.
 *
 * @see WritableValue
 * @see io.github.vinccool96.observations.beans.value.WritableNumberValue
 * @since JavaFX 2.0
 */
public interface WritableDoubleValue extends WritableNumberValue {

    /**
     * Get the wrapped value. Unlike {@link #getValue()}, this method returns primitive {@code double}. Needs to be
     * identical to {@link #getValue()}.
     *
     * @return The current value
     */
    double get();

    /**
     * Set the wrapped value. Unlike {@link #setValue(Number)}, this method uses primitive {@code double}.
     *
     * @param value
     *         The new value
     */
    void set(double value);

    /**
     * Set the wrapped value.
     * <p>
     * Note: this method should accept {@code null} without throwing an exception, setting "{@code 0.0}" instead.
     *
     * @param value
     *         The new value
     */
    @Override
    void setValue(Number value);

}
