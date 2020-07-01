package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableList;

/**
 * A writable reference to an {@link ObservableList}.
 *
 * @param <E>
 *         the type of the {@code List} elements
 *
 * @see ObservableList
 * @see WritableObjectValue
*/
public interface WritableListValue<E> extends WritableObjectValue<ObservableList<E>>, ObservableList<E> {

}
