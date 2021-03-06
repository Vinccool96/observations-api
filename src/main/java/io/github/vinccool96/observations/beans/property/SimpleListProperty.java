package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.collections.ObservableList;

/**
 * This class provides a full implementation of a {@link Property} wrapping an {@code ObservableList}.
 *
 * @param <E>
 *         the type of the {@code List} elements
 *
 * @see ListPropertyBase
 */
public class SimpleListProperty<E> extends ListPropertyBase<E> {

    private static final Object DEFAULT_BEAN = null;

    private static final String DEFAULT_NAME = "";

    private final Object bean;

    private final String name;

    /**
     * The constructor of {@code SimpleListProperty}
     *
     * @param bean
     *         the bean of this {@code ListProperty}
     * @param name
     *         the name of this {@code ListProperty}
     * @param initialValue
     *         the initial value of the wrapped value
     */
    public SimpleListProperty(Object bean, String name, ObservableList<E> initialValue) {
        super(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    /**
     * The constructor of {@code SimpleListProperty}
     *
     * @param bean
     *         the bean of this {@code SetProperty}
     * @param name
     *         the name of this {@code SetProperty}
     */
    public SimpleListProperty(Object bean, String name) {
        super();
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    /**
     * The constructor of {@code SimpleListProperty}
     *
     * @param initialValue
     *         the initial value of the wrapped value
     */
    public SimpleListProperty(ObservableList<E> initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    /**
     * The constructor of {@code SimpleListProperty}
     */
    public SimpleListProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

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

}
