package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.binding.Bindings;
import io.github.vinccool96.observations.beans.value.WritableSetValue;
import io.github.vinccool96.observations.collections.ObservableSet;

/**
 * This class provides a full implementation of a {@link Property} wrapping a {@link ObservableSet}.
 * <p>
 * The value of a {@code SetProperty} can be get and set with {@link #get()}, {@link #getValue()}, {@link #set(Object)},
 * and {@link #setValue(ObservableSet)}.
 * <p>
 * A property can be bound and unbound unidirectional with {@link #bind(io.github.vinccool96.observations.beans.value.ObservableValue)}
 * and {@link #unbind()}. Bidirectional bindings can be created and removed with {@link #bindBidirectional(Property)}
 * and {@link #unbindBidirectional(Property)}.
 * <p>
 * The context of a {@code SetProperty} can be read with {@link #getBean()} and {@link #getName()}.
 *
 * @param <E>
 *         the type of the {@code Set} elements
 *
 * @see ObservableSet
 * @see io.github.vinccool96.observations.beans.value.ObservableSetValue
 * @see WritableSetValue
 * @see ReadOnlySetProperty
 * @see Property
 */
public abstract class SetProperty<E> extends ReadOnlySetProperty<E> implements Property<ObservableSet<E>>,
        WritableSetValue<E> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(ObservableSet<E> v) {
        set(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(Property<ObservableSet<E>> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<ObservableSet<E>> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code SetProperty} object.
     *
     * @return a string representation of this {@code SetProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("SetProperty [");
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
