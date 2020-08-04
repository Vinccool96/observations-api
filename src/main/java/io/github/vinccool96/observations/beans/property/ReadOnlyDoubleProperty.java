package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.WeakInvalidationListener;
import io.github.vinccool96.observations.beans.binding.DoubleExpression;

/**
 * Super class for all readonly properties wrapping a {@code double}.
 *
 * @see io.github.vinccool96.observations.beans.value.ObservableDoubleValue
 * @see DoubleExpression
 * @see ReadOnlyProperty
 */
public abstract class ReadOnlyDoubleProperty extends DoubleExpression implements ReadOnlyProperty<Number> {

    /**
     * The constructor of {@code ReadOnlyDoubleProperty}.
     */
    public ReadOnlyDoubleProperty() {
    }

    /**
     * Returns a string representation of this {@code ReadOnlyDoubleProperty} object.
     *
     * @return a string representation of this {@code ReadOnlyDoubleProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("ReadOnlyDoubleProperty [");
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
     * Returns a {@code ReadOnlyDoubleProperty} that wraps a {@link ReadOnlyProperty}. If the {@code ReadOnlyProperty}
     * is already a {@code ReadOnlyDoubleProperty}, it will be returned. Otherwise a new {@code ReadOnlyDoubleProperty}
     * is created that is bound to the {@code ReadOnlyProperty}.
     * <p>
     * Note: null values will be interpreted as 0.0
     *
     * @param property
     *         The source {@code ReadOnlyProperty}
     * @param <T>
     *         The type of the wrapped number
     *
     * @return A {@code ReadOnlyDoubleProperty} that wraps the {@code ReadOnlyProperty} if necessary
     *
     * @throws NullPointerException
     *         if {@code property} is {@code null}
     */
    public static <T extends Number> ReadOnlyDoubleProperty readOnlyDoubleProperty(final ReadOnlyProperty<T> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }

        return property instanceof ReadOnlyDoubleProperty ? (ReadOnlyDoubleProperty) property :
                new ReadOnlyDoublePropertyBase() {

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
                    public double get() {
                        valid = true;
                        final T value = property.getValue();
                        return value == null ? 0.0 : value.doubleValue();
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
     * Creates a {@link ReadOnlyObjectProperty} that holds the value of this {@code ReadOnlyDoubleProperty}. If the
     * value of this {@code ReadOnlyDoubleProperty} changes, the value of the {@code ReadOnlyObjectProperty} will be
     * updated automatically.
     *
     * @return the new {@code ReadOnlyObjectProperty}
     */
    @Override
    public ReadOnlyObjectProperty<Double> asObject() {
        return new ReadOnlyObjectPropertyBase<Double>() {

            private boolean valid = true;

            private final InvalidationListener listener = observable -> {
                if (valid) {
                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyDoubleProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return ReadOnlyDoubleProperty.this.getName();
            }

            @Override
            public Double get() {
                valid = true;
                return ReadOnlyDoubleProperty.this.getValue();
            }
        };
    }

}
