package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.WeakInvalidationListener;
import io.github.vinccool96.observations.beans.binding.LongExpression;

/**
 * Super class for all readonly properties wrapping a {@code long}.
 *
 * @see io.github.vinccool96.observations.beans.value.ObservableLongValue
 * @see LongExpression
 * @see ReadOnlyProperty
*/
public abstract class ReadOnlyLongProperty extends LongExpression implements ReadOnlyProperty<Number> {

    /**
     * The constructor of {@code ReadOnlyLongProperty}.
     */
    public ReadOnlyLongProperty() {
    }

    /**
     * Returns a string representation of this {@code ReadOnlyLongProperty} object.
     *
     * @return a string representation of this {@code ReadOnlyLongProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("ReadOnlyLongProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

    /**
     * Returns a {@code ReadOnlyLongProperty} that wraps a {@link ReadOnlyProperty}. If the {@code ReadOnlyProperty} is
     * already a {@code ReadOnlyLongProperty}, it will be returned. Otherwise a new {@code ReadOnlyLongProperty} is
     * created that is bound to the {@code ReadOnlyProperty}.
     * <p>
     * Note: null values will be interpreted as 0L
     *
     * @param property
     *         The source {@code ReadOnlyProperty}
     * @param <T>
     *         the type of the wrapped number
     *
     * @return A {@code ReadOnlyLongProperty} that wraps the {@code ReadOnlyProperty} if necessary
     *
     * @throws NullPointerException
     *         if {@code property} is {@code null}
     */
    public static <T extends Number> ReadOnlyLongProperty readOnlyLongProperty(final ReadOnlyProperty<T> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }

        return property instanceof ReadOnlyLongProperty ? (ReadOnlyLongProperty) property :
                new ReadOnlyLongPropertyBase() {

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
                    public long get() {
                        valid = true;
                        final T value = property.getValue();
                        return value == null ? 0L : value.longValue();
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
     * Creates a {@link ReadOnlyObjectProperty} that holds the value of this {@code ReadOnlyLongProperty}. If the value
     * of this {@code ReadOnlyLongProperty} changes, the value of the {@code ReadOnlyObjectProperty} will be updated
     * automatically.
     *
     * @return the new {@code ReadOnlyObjectProperty}
     */
    @Override
    public ReadOnlyObjectProperty<Long> asObject() {
        return new ReadOnlyObjectPropertyBase<Long>() {

            private boolean valid = true;

            private final InvalidationListener listener = observable -> {
                if (valid) {
                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyLongProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return ReadOnlyLongProperty.this.getName();
            }

            @Override
            public Long get() {
                valid = true;
                return ReadOnlyLongProperty.this.getValue();
            }
        };
    }

}
