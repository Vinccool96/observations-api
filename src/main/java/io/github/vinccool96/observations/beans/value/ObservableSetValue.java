package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableSet;

/**
 * An observable reference to an {@link io.github.vinccool96.observations.collections.ObservableSet}.
 *
 * @param <E>
 *         the type of the {@code Set} elements
 *
 * @see io.github.vinccool96.observations.collections.ObservableSet
 * @see ObservableObjectValue
 * @see ObservableValue
 * @since JavaFX 2.1
 */
public interface ObservableSetValue<E> extends ObservableObjectValue<ObservableSet<E>>, ObservableSet<E> {

}
