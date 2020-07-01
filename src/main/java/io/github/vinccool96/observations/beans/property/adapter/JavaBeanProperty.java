package io.github.vinccool96.observations.beans.property.adapter;

import io.github.vinccool96.observations.beans.property.Property;

/**
 * {@code JavaBeanProperty&lt;T&gt;} is the super interface of all adapters between writable Java Bean properties and
 * JavaFX properties.
 *
 * @param <T>
 *         The type of the wrapped property
 */
public interface JavaBeanProperty<T> extends ReadOnlyJavaBeanProperty<T>, Property<T> {

}
