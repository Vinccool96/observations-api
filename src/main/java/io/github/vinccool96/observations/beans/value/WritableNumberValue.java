package io.github.vinccool96.observations.beans.value;

/**
 * A tagging interface that marks all sub-interfaces of {@link io.github.vinccool96.observations.beans.value.WritableValue}
 * that wrap a number.
 *
 * @see WritableValue
 * @see WritableDoubleValue
 * @see WritableFloatValue
 * @see WritableIntegerValue
 * @see WritableLongValue
 * @since JavaFX 2.0
 */
public interface WritableNumberValue extends WritableValue<Number> {

}
