/**
 * The package {@code io.github.vinccool96.observations.beans} contains the interfaces that define the most generic form
 * of observability.  All other classes in the ObservableApi library, that are observable, extend the {@link
 * io.github.vinccool96.observations.beans.Observable} interface.
 * <p>
 * An implementation of {@code Observable} allows to attach an {@link io.github.vinccool96.observations.beans.InvalidationListener}.
 * The contentBinding gets notified every time the {@code Observable} may have changed. Typical implementations of
 * {@code Observable} are all properties, all bindings, {@link io.github.vinccool96.observations.collections.ObservableList},
 * and {@link io.github.vinccool96.observations.collections.ObservableMap}.
 * <p>
 * An {@code InvalidationListener} will get no further information, e.g. it will not get the old and the new value of a
 * property. If you need more information consider using a {@code Observable}ChangeListener for properties and bindings,
 * {@link io.github.vinccool96.observations.collections.ListChangeListener} for {@code ObservableLists} or {@link
 * io.github.vinccool96.observations.collections.MapChangeListener} for {@code ObservableMap} instead.
 */
package io.github.vinccool96.observations.beans;