package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableList;

/**
 * An observable reference to an {@link io.github.vinccool96.observations.collections.ObservableList}.
 *
 * @param <E>
 *         the type of the {@code List} elements
 *
 * @see io.github.vinccool96.observations.collections.ObservableList
 * @see ObservableObjectValue
 * @see ObservableValue
*/
public interface ObservableListValue<E> extends ObservableObjectValue<ObservableList<E>>, ObservableList<E> {

}
