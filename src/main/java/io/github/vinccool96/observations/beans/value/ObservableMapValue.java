package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableMap;

/**
 * An observable reference to an {@link ObservableMap}.
 *
 * @param <K>
 *         the type of the key elements of the {@code Map}
 * @param <V>
 *         the type of the value elements of the {@code Map}
 *
 * @see ObservableMap
 * @see ObservableObjectValue
 * @see ObservableValue
 * @since JavaFX 2.1
 */
public interface ObservableMapValue<K, V> extends ObservableObjectValue<ObservableMap<K, V>>, ObservableMap<K, V> {

}
