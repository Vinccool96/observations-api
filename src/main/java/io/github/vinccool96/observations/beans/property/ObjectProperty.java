package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.binding.Bindings;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.beans.value.WritableObjectValue;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.collections.ObservableSet;

/**
 * This class provides a full implementation of a {@link Property} wrapping an arbitrary {@code Object}.
 * <p>
 * The value of a {@code ObjectProperty} can be get and set with {@link #get()}, {@link #getValue()}, {@link
 * #set(Object)}, and {@link #setValue(Object)}.
 * <p>
 * A property can be bound and unbound unidirectional with {@link #bind(ObservableValue)} and {@link #unbind()}.
 * Bidirectional bindings can be created and removed with {@link #bindBidirectional(Property)} and {@link
 * #unbindBidirectional(Property)}.
 * <p>
 * The context of a {@code ObjectProperty} can be read with {@link #getBean()} and {@link #getName()}.
 * <p>
 * For specialized implementations for {@link ObservableList}, {@link ObservableSet} and {@link ObservableMap} that also
 * report changes inside the collections, see {@link ListProperty}, {@link SetProperty} and {@link MapProperty},
 * respectively.
 *
 * @param <T>
 *         the type of the wrapped {@code Object}
 *
 * @see io.github.vinccool96.observations.beans.value.ObservableObjectValue
 * @see WritableObjectValue
 * @see ReadOnlyObjectProperty
 * @see Property
 */
public abstract class ObjectProperty<T> extends ReadOnlyObjectProperty<T>
        implements Property<T>, WritableObjectValue<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(T v) {
        set(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(Property<T> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<T> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code ObjectProperty} object.
     *
     * @return a string representation of this {@code ObjectProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ObjectProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

}
