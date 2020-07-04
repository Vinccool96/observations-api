package io.github.vinccool96.observations.beans.value;

/**
 * A writable {@code short} value.
 *
 * @see WritableValue
 * @see WritableNumberValue
 */
public interface WritableShortValue extends WritableNumberValue {

    /**
     * Get the wrapped value. Unlike {@link #getValue()}, this method returns primitive {@code short}. Needs to be
     * identical to {@link #getValue()}.
     *
     * @return The current value
     */
    short get();

    /**
     * Set the wrapped value. Unlike {@link #setValue(Number)}, this method uses primitive {@code short}.
     *
     * @param value
     *         The new value
     */
    void set(short value);

    /**
     * Set the wrapped value.
     * <p>
     * Note: this method should accept {@code null} without throwing an exception, setting "{@code 0}" instead.
     *
     * @param value
     *         The new value
     */
    @Override
    void setValue(Number value);

}
