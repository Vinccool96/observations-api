package io.github.vinccool96.observations.beans.value;

/**
 * A writable {@code int} value.
 *
 * @see WritableValue
 * @see WritableNumberValue
 */
public interface WritableIntegerValue extends WritableNumberValue {

    /**
     * Get the wrapped value. Unlike {@link #getValue()}, this method returns primitive {@code int}. Needs to be
     * identical to {@link #getValue()}.
     *
     * @return The current value
     */
    int get();

    /**
     * Set the wrapped value. Unlike {@link #setValue(Number)}, this method uses primitive {@code int}.
     *
     * @param value
     *         The new value
     */
    void set(int value);

    /**
     * Set the wrapped value.
     * <p>
     * Note: this method should accept {@code null}  without throwing an exception, setting "{@code 0}" instead.
     *
     * @param value
     *         The new value
     */
    @Override
    void setValue(Number value);

}
