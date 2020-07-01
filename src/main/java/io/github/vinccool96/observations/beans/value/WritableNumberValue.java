package io.github.vinccool96.observations.beans.value;

/**
 * A tagging interface that marks all sub-interfaces of {@link WritableValue} that wrap a number.
 *
 * @see WritableValue
 * @see WritableDoubleValue
 * @see WritableFloatValue
 * @see WritableIntegerValue
 * @see WritableLongValue
 */
public interface WritableNumberValue extends WritableValue<Number> {

}
