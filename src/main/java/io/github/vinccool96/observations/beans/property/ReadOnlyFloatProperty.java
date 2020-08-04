package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.WeakInvalidationListener;
import io.github.vinccool96.observations.beans.binding.FloatExpression;

/**
 * Super class for all readonly properties wrapping a {@code float}.
 *
 * @see io.github.vinccool96.observations.beans.value.ObservableFloatValue
 * @see FloatExpression
 * @see ReadOnlyProperty
 */
public abstract class ReadOnlyFloatProperty extends FloatExpression implements ReadOnlyProperty<Number> {

    /**
     * The constructor of {@code ReadOnlyFloatProperty}.
     */
    public ReadOnlyFloatProperty() {
    }

    /**
     * Returns a string representation of this {@code ReadOnlyFloatProperty} object.
     *
     * @return a string representation of this {@code ReadOnlyFloatProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyFloatProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if (name != null && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

    /**
     * Returns a {@code ReadOnlyFloatProperty} that wraps a {@link ReadOnlyProperty}. If the {@code ReadOnlyProperty} is
     * already a {@code ReadOnlyFloatProperty}, it will be returned. Otherwise a new {@code ReadOnlyFloatProperty} is
     * created that is bound to the {@code ReadOnlyProperty}.
     * <p>
     * Note: null values will be interpreted as 0f
     *
     * @param property
     *         The source {@code ReadOnlyProperty}
     * @param <T>
     *         The type of the wrapped number
     *
     * @return A {@code ReadOnlyFloatProperty} that wraps the {@code ReadOnlyProperty} if necessary
     *
     * @throws NullPointerException
     *         if {@code property} is {@code null}
     */
    public static <T extends Number> ReadOnlyFloatProperty readOnlyFloatProperty(final ReadOnlyProperty<T> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }

        return property instanceof ReadOnlyFloatProperty ? (ReadOnlyFloatProperty) property :
                new ReadOnlyFloatPropertyBase() {

                    private boolean valid = true;

                    private final InvalidationListener listener = observable -> {
                        if (valid) {
                            valid = false;
                            fireValueChangedEvent();
                        }
                    };

                    {
                        property.addListener(new WeakInvalidationListener(listener));
                    }

                    @Override
                    public float get() {
                        valid = true;
                        final T value = property.getValue();
                        return value == null ? 0f : value.floatValue();
                    }

                    @Override
                    public Object getBean() {
                        return null; // Virtual property, no bean
                    }

                    @Override
                    public String getName() {
                        return property.getName();
                    }
                };
    }

    /**
     * Creates a {@link ReadOnlyObjectProperty} that holds the value of this {@code ReadOnlyFloatProperty}. If the value
     * of this {@code ReadOnlyFloatProperty} changes, the value of the {@code ReadOnlyObjectProperty} will be updated
     * automatically.
     *
     * @return the new {@code ReadOnlyObjectProperty}
     */
    @Override
    public ReadOnlyObjectProperty<Float> asObject() {
        return new ReadOnlyObjectPropertyBase<Float>() {

            private boolean valid = true;

            private final InvalidationListener listener = observable -> {
                if (valid) {
                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyFloatProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return ReadOnlyFloatProperty.this.getName();
            }

            @Override
            public Float get() {
                valid = true;
                return ReadOnlyFloatProperty.this.getValue();
            }
        };
    }

}
