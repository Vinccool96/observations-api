package io.github.vinccool96.observations.beans.property;

/**
 * This class provides a full implementation of a {@link Property} wrapping an arbitrary {@code Object}.
 *
 * @param <T>
 *         the type of the wrapped {@code Object}
 *
 * @see ObjectPropertyBase
 */
public class SimpleObjectProperty<T> extends ObjectPropertyBase<T> {

    private static final Object DEFAULT_BEAN = null;

    private static final String DEFAULT_NAME = "";

    private final Object bean;

    private final String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * The constructor of {@code ObjectProperty}
     */
    public SimpleObjectProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    /**
     * The constructor of {@code ObjectProperty}
     *
     * @param initialValue
     *         the initial value of the wrapped value
     */
    public SimpleObjectProperty(T initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    /**
     * The constructor of {@code ObjectProperty}
     *
     * @param bean
     *         the bean of this {@code ObjectProperty}
     * @param name
     *         the name of this {@code ObjectProperty}
     */
    public SimpleObjectProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    /**
     * The constructor of {@code ObjectProperty}
     *
     * @param bean
     *         the bean of this {@code ObjectProperty}
     * @param name
     *         the name of this {@code ObjectProperty}
     * @param initialValue
     *         the initial value of the wrapped value
     */
    public SimpleObjectProperty(Object bean, String name, T initialValue) {
        super(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

}
