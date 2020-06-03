package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.Observable;

import java.util.Set;

/**
 * A set that allows observers to track changes when they occur.
 *
 * @see SetChangeListener
 * @see SetChangeListener.Change
 * @since JavaFX 2.1
 */
public interface ObservableSet<E> extends Set<E>, Observable {

    /**
     * Add a listener to this observable set.
     *
     * @param listener
     *         the listener for listening to the set changes
     */
    void addListener(SetChangeListener<? super E> listener);

    /**
     * Tries to removed a listener from this observable set. If the listener is not attached to this list, nothing
     * happens.
     *
     * @param listener
     *         a listener to remove
     */
    void removeListener(SetChangeListener<? super E> listener);

}
