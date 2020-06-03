package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.collections.ObservableMap;

/**
 * A writable reference to an {@link ObservableMap}.
 *
 * @param <K>
 *         the type of the key elements of the {@code Map}
 * @param <V>
 *         the type of the value elements of the {@code Map}
 *
 * @see ObservableMap
 * @see WritableObjectValue
 * @see WritableMapValue
 * @since JavaFX 2.1
 */
public interface WritableMapValue<K, V> extends WritableObjectValue<ObservableMap<K, V>>, ObservableMap<K, V> {

}
