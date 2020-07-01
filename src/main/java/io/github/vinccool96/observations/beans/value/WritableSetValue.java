package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableSet;

/**
 * A writable reference to an {@link ObservableSet}.
 *
 * @param <E>
 *         the type of the {@code Set} elements
 *
 * @see ObservableSet
 * @see WritableObjectValue
 */
public interface WritableSetValue<E> extends WritableObjectValue<ObservableSet<E>>, ObservableSet<E> {

}
