/**
 * The package {@code io.github.vinccool96.observations.beans.property} defines read-only properties and writable
 * properties, plus a number of implementations.
 * <h1>Read-only Properties</h1>
 * <p>
 * Read-only properties have two getters, {@code get()} returns the primitive value, {@code getValue()} returns the
 * boxed value.
 * <p>
 * It is possible to observe read-only properties for changes. They define methods to add and remove {@link
 * io.github.vinccool96.observations.beans.InvalidationListener InvalidationListeners} and {@link
 * io.github.vinccool96.observations.beans.value.ChangeListener ChangeListeners}.
 * <p>
 * To get the context of a read-only property, two methods {@code getBean()} and {@code getName()} are defined. They
 * return the containing bean and the name of a property.
 * <h1>Writable Properties</h1>
 * In addition to the functionality defined for read-only properties, writable properties contain the following
 * methods.
 * <p>
 * A writable property defines two setters in addition to the getters defined for read-only properties. The setter
 * {@code set()} takes a primitive value, the second setter {@code setValue()} takes the boxed value.
 * <p>
 * All properties can be bound to {@link io.github.vinccool96.observations.beans.value.ObservableValue ObservableValues}
 * of the same type, which means that the property will always contain the same value as the bound {@code
 * ObservableValue}. It is also possible to define a bidirectional binding between two properties, so that both
 * properties always contain the same value. If one of the properties changes, the other one will be updated.
 */
package io.github.vinccool96.observations.beans.property;