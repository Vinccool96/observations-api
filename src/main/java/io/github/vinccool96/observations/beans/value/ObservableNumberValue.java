package io.github.vinccool96.observations.beans.value;

/**
 * A common interface of all sub-interfaces of {@link ObservableValue} that wrap a number.
 * <p>
 * For the {@code <T>} of {@link ObservableValue}, it is the type of the wrapped value (a {@code Number}).
 *
 * @see ObservableValue
 * @see ObservableDoubleValue
 * @see ObservableFloatValue
 * @see ObservableIntegerValue
 * @see ObservableLongValue
 */
public interface ObservableNumberValue extends ObservableValue<Number> {

    /**
     * Returns the value of this {@code ObservableNumberValue} as an {@code int} . If the value is not an {@code int}, a
     * standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as an {@code int}
     */
    int intValue();

    /**
     * Returns the value of this {@code ObservableNumberValue} as a {@code long} . If the value is not a {@code long}, a
     * standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a {@code long}
     */
    long longValue();

    /**
     * Returns the value of this {@code ObservableNumberValue} as a {@code float}. If the value is not a {@code float},
     * a standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a {@code float}
     */
    float floatValue();

    /**
     * Returns the value of this {@code ObservableNumberValue} as a {@code double}. If the value is not a {@code
     * double}, a standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a {@code double}
     */
    double doubleValue();

    /**
     * Returns the value of this {@code ObservableNumberValue} as a {@code short}. If the value is not a {@code short},
     * a standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a {@code short}
     */
    short shortValue();

}
