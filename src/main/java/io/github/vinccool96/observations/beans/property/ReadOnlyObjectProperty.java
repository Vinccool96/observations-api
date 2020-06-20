package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.binding.ObjectExpression;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.collections.ObservableSet;

/**
 * Super class for all readonly properties wrapping an arbitrary {@code Object}.
 * <p>
 * For specialized implementations for {@link ObservableList}, {@link ObservableSet} and {@link ObservableMap} that also
 * report changes inside the collections, see {@link ReadOnlyListProperty}, {@link ReadOnlySetProperty} and {@link
 * ReadOnlyMapProperty}, respectively.
 *
 * @param <T>
 *         the type of the wrapped {@code Object}
 *
 * @see io.github.vinccool96.observations.beans.value.ObservableObjectValue
 * @see ObjectExpression
 * @see ReadOnlyProperty
 * @since JavaFX 2.0
 */
public abstract class ReadOnlyObjectProperty<T> extends ObjectExpression<T> implements ReadOnlyProperty<T> {

    /**
     * The constructor of {@code ReadOnlyObjectProperty}.
     */
    public ReadOnlyObjectProperty() {
    }

    /**
     * Returns a string representation of this {@code ReadOnlyObjectProperty} object.
     *
     * @return a string representation of this {@code ReadOnlyObjectProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("ReadOnlyObjectProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

}
