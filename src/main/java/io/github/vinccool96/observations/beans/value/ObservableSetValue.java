package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableSet;

/**
 * An observable reference to an {@link ObservableSet}.
 *
 * @param <E>
 *         the type of the {@code Set} elements
 *
 * @see ObservableSet
 * @see ObservableObjectValue
 * @see ObservableValue
 */
public interface ObservableSetValue<E> extends ObservableObjectValue<ObservableSet<E>>, ObservableSet<E> {

}
