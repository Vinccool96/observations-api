package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableList;

/**
 * An observable reference to an {@link ObservableList}.
 *
 * @param <E> the type of the {@code List} elements
 * @see ObservableList
 * @see ObservableObjectValue
 * @see ObservableValue
 */
public interface ObservableListValue<E> extends ObservableObjectValue<ObservableList<E>>, ObservableList<E> {

}
