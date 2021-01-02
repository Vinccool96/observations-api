package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.binding.Bindings;
import io.github.vinccool96.observations.beans.value.WritableMapValue;
import io.github.vinccool96.observations.collections.ObservableMap;

/**
 * This class provides a full implementation of a {@link Property} wrapping a {@link ObservableMap}.
 * <p>
 * The value of a {@code MapProperty} can be get and set with {@link #get()}, {@link #getValue()}, {@link #set(Object)},
 * and {@link #setValue(ObservableMap)}.
 * <p>
 * A property can be bound and unbound unidirectional with {@link #bind(io.github.vinccool96.observations.beans.value.ObservableValue)}
 * and {@link #unbind()}. Bidirectional bindings can be created and removed with {@link #bindBidirectional(Property)}
 * and {@link #unbindBidirectional(Property)}.
 * <p>
 * The context of a {@code MapProperty} can be read with {@link #getBean()} and {@link #getName()}.
 *
 * @param <K>
 *         the type of the key elements of the {@code Map}
 * @param <V>
 *         the type of the value elements of the {@code Map}
 *
 * @see ObservableMap
 * @see io.github.vinccool96.observations.beans.value.ObservableMapValue
 * @see WritableMapValue
 * @see ReadOnlyMapProperty
 * @see Property
 */
public abstract class MapProperty<K, V> extends ReadOnlyMapProperty<K, V> implements Property<ObservableMap<K, V>>,
        WritableMapValue<K, V> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(ObservableMap<K, V> v) {
        set(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(Property<ObservableMap<K, V>> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<ObservableMap<K, V>> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code MapProperty} object.
     *
     * @return a string representation of this {@code MapProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("MapProperty [");
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
