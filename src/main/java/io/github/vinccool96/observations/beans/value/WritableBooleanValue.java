package io.github.vinccool96.observations.beans.value;

/**
 * A writable {@code boolean} value.
 *
 * @see WritableValue
 */
public interface WritableBooleanValue extends WritableValue<Boolean> {

    /**
     * Get the wrapped value. Unlike {@link #getValue()}, this method returns primitive {@code boolean}. Needs to be
     * identical to {@link #getValue()}.
     *
     * @return The current value
     */
    boolean get();

    /**
     * Set the wrapped value. Unlike {@link #setValue(Boolean)}, this method uses primitive {@code boolean}.
     *
     * @param value
     *         The new value
     */
    void set(boolean value);

    /**
     * Set the wrapped value.
     * <p>
     * Note: this method should accept {@code null} without throwing an exception, setting "{@code false}" instead.
     *
     * @param value
     *         The new value
     */
    @Override
    void setValue(Boolean value);

}
