package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.binding.Bindings;
import io.github.vinccool96.observations.beans.value.WritableListValue;
import io.github.vinccool96.observations.collections.ObservableList;

/**
 * This class provides a full implementation of a {@link Property} wrapping a {@link ObservableList}.
 * <p>
 * The value of a {@code ListProperty} can be get and set with {@link #get()}, {@link #getValue()}, {@link
 * #set(Object)}, and {@link #setValue(ObservableList)}.
 * <p>
 * A property can be bound and unbound unidirectional with {@link #bind(io.github.vinccool96.observations.beans.value.ObservableValue)}
 * and {@link #unbind()}. Bidirectional bindings can be created and removed with {@link #bindBidirectional(Property)}
 * and {@link #unbindBidirectional(Property)}.
 * <p>
 * The context of a {@code ListProperty} can be read with {@link #getBean()} and {@link #getName()}.
 *
 * @param <E>
 *         the type of the {@code List} elements
 *
 * @see ObservableList
 * @see io.github.vinccool96.observations.beans.value.ObservableListValue
 * @see WritableListValue
 * @see ReadOnlyListProperty
 * @see Property
 */
public abstract class ListProperty<E> extends ReadOnlyListProperty<E>
        implements Property<ObservableList<E>>, WritableListValue<E> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(ObservableList<E> v) {
        set(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(Property<ObservableList<E>> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<ObservableList<E>> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code ListProperty} object.
     *
     * @return a string representation of this {@code ListProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ListProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if (name != null && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

}
