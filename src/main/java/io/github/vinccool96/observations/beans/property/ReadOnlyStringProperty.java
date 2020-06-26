package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.binding.StringExpression;

/**
 * Super class for all readonly properties wrapping a {@code String}.
 *
 * @see io.github.vinccool96.observations.beans.value.ObservableStringValue
 * @see StringExpression
 * @see ReadOnlyProperty
 * @since JavaFX 2.0
 */
public abstract class ReadOnlyStringProperty extends StringExpression implements ReadOnlyProperty<String> {

    /**
     * The constructor of {@code ReadOnlyStringProperty}.
     */
    public ReadOnlyStringProperty() {
    }

    /**
     * Returns a string representation of this {@code ReadOnlyStringProperty} object.
     *
     * @return a string representation of this {@code ReadOnlyStringProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("ReadOnlyStringProperty [");
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
