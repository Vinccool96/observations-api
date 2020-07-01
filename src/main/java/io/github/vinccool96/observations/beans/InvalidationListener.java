package io.github.vinccool96.observations.beans;

import io.github.vinccool96.observations.beans.value.ObservableValue;

/**
 * An {@code InvalidationListener} is notified whenever an {@link Observable} becomes invalid. It can be registered and
 * unregistered with {@link Observable#addListener(InvalidationListener)} respectively {@link
 * Observable#removeListener(InvalidationListener)}
 * <p>
 * For an in-depth explanation of invalidation events and how they differ from change events, see the documentation of
 * {@code ObservableValue}.
 * <p>
 * The same instance of {@code InvalidationListener} can be registered to listen to multiple {@code Observables}.
 *
 * @see Observable
 * @see ObservableValue
 */
@FunctionalInterface
public interface InvalidationListener {

    /**
     * This method needs to be provided by an implementation of {@code InvalidationListener}. It is called if an {@link
     * Observable} becomes invalid.
     * <p>
     * In general is is considered bad practice to modify the observed value in this method.
     *
     * @param observable
     *         The {@code Observable} that became invalid
     */
    void invalidated(Observable observable);

}
