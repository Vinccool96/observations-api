package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.Observable;

import java.util.Map;

/**
 * A map that allows observers to track changes when they occur.
 *
 * @param <K>
 *         the type of keys maintained by this map
 * @param <V>
 *         the type of mapped values
 *
 * @see MapChangeListener
 * @see MapChangeListener.Change
 * @since JavaFX 2.0
 */
public interface ObservableMap<K, V> extends Map<K, V>, Observable {

    /**
     * Add a listener to this observable map.
     *
     * @param listener
     *         the listener for listening to the list changes
     */
    void addListener(MapChangeListener<? super K, ? super V> listener);

    /**
     * Tries to removed a listener from this observable map. If the listener is not attached to this map, nothing
     * happens.
     *
     * @param listener
     *         a listener to remove
     */
    void removeListener(MapChangeListener<? super K, ? super V> listener);

    /**
     * Verify if a {@code MapChangeListener} already exist for this {@code ObservableMap}.
     *
     * @param listener
     *         the {@code MapChangeListener} to verify
     *
     * @return {@code true}, if the listener already listens, {@code false} otherwise.
     */
    boolean isMapChangeListenerAlreadyAdded(MapChangeListener<? super K, ? super V> listener);

}
