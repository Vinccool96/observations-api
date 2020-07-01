package io.github.vinccool96.observations.beans;

/**
 * An {@code Observable} is an entity that wraps content and allows to observe the content for invalidations.
 * <p>
 * An implementation of {@code Observable} may support lazy evaluation, which means that the content is not immediately
 * recomputed after changes, but lazily the next time it is requested. All bindings and properties in this library
 * support lazy evaluation.
 * <p>
 * Implementations of this class should strive to generate as few events as possible to avoid wasting too much time in
 * event handlers. Implementations in this library mark themselves as invalid when the first invalidation event occurs.
 * They do not generate anymore invalidation events until their value is recomputed and valid again.
 *
 * @see io.github.vinccool96.observations.beans.value.ObservableValue
 * @see io.github.vinccool96.observations.collections.ObservableList
 * @see io.github.vinccool96.observations.collections.ObservableMap
 */
public interface Observable {

    /**
     * Adds an {@link InvalidationListener} which will be notified whenever the {@code Observable} becomes invalid. If
     * the same listener is added more than once, then it will be notified more than once. That is, no check is made to
     * ensure uniqueness.
     * <p>
     * Note that the same actual {@code InvalidationListener} instance may be safely registered for different {@code
     * Observables}.
     * <p>
     * The {@code Observable} stores a strong reference to the listener which will prevent the listener from being
     * garbage collected and may result in a memory leak. It is recommended to either unregister a listener by calling
     * {@link #removeListener(InvalidationListener) removeListener} after use or to use an instance of {@link
     * WeakInvalidationListener} avoid this situation.
     *
     * @param listener
     *         The listener to register
     *
     * @throws NullPointerException
     *         if the listener is null
     * @see #removeListener(InvalidationListener)
     */
    void addListener(InvalidationListener listener);

    /**
     * Removes the given listener from the collections of listeners, that are notified whenever the value of the {@code
     * Observable} becomes invalid.
     * <p>
     * If the given listener has not been previously registered (i.e. it was never added) then this method call is a
     * no-op. If it had been previously added then it will be removed. If it had been added more than once, then only
     * the first occurrence will be removed.
     *
     * @param listener
     *         The listener to remove
     *
     * @throws NullPointerException
     *         if the listener is null
     * @see #addListener(InvalidationListener)
     */
    void removeListener(InvalidationListener listener);

    /**
     * Verify if the specified {@code InvalidationListener} already exists for this {@code Observable}.
     *
     * @param listener
     *         the {@code InvalidationListener} to verify
     *
     * @return {@code true}, if the listener already listens, {@code false} otherwise.
     */
    boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener);

}
