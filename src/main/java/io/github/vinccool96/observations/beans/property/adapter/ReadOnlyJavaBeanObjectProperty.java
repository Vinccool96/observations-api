package io.github.vinccool96.observations.beans.property.adapter;

import io.github.vinccool96.observations.beans.property.ReadOnlyObjectPropertyBase;
import io.github.vinccool96.observations.sun.property.adapter.Disposer;
import io.github.vinccool96.observations.sun.property.adapter.ReadOnlyPropertyDescriptor;
import sun.reflect.misc.MethodUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A {@code ReadOnlyJavaBeanObjectProperty} provides an adapter between a regular read only Java Bean property of {@code
 * T} and a JavaFX {@code ReadOnlyObjectProperty}. It cannot be created directly, but a {@link
 * ReadOnlyJavaBeanObjectPropertyBuilder} has to be used.
 * <p>
 * As a minimum, the Java Bean must implement a getter for the property. If the getter of an instance of this class is
 * called, the property of the Java Bean is returned. If the Java Bean property is bound (i.e. it supports
 * PropertyChangeListeners), this {@code ReadOnlyJavaBeanObjectProperty} will be aware of changes in the Java Bean.
 * Otherwise it can be notified about changes by calling {@link #fireValueChangedEvent()}.
 *
 * @param <T>
 *         the type of the wrapped {@code Object}
 *
 * @see io.github.vinccool96.observations.beans.property.ReadOnlyObjectProperty
 * @see ReadOnlyJavaBeanObjectPropertyBuilder
 */
public final class ReadOnlyJavaBeanObjectProperty<T> extends ReadOnlyObjectPropertyBase<T>
        implements ReadOnlyJavaBeanProperty<T> {

    private final ReadOnlyPropertyDescriptor descriptor;

    private final ReadOnlyPropertyDescriptor.ReadOnlyListener<T> listener;

    private final AccessControlContext acc = AccessController.getContext();

    ReadOnlyJavaBeanObjectProperty(ReadOnlyPropertyDescriptor descriptor, Object bean) {
        this.descriptor = descriptor;
        this.listener = descriptor.new ReadOnlyListener<T>(bean, this);
        descriptor.addListener(listener);
        Disposer.addRecord(this, new DescriptorListenerCleaner(descriptor, listener));
    }

    /**
     * {@inheritDoc}
     *
     * @throws UndeclaredThrowableException
     *         if calling the getter of the Java Bean property throws an {@code IllegalAccessException} or an {@code
     *         InvocationTargetException}.
     */
    @Override
    public T get() {
        return AccessController.doPrivileged((PrivilegedAction<T>) () -> {
            try {
                return (T) MethodUtil.invoke(descriptor.getGetter(), getBean(), (Object[]) null);
            } catch (IllegalAccessException e) {
                throw new UndeclaredThrowableException(e);
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e);
            }
        }, acc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return listener.getBean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return descriptor.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireValueChangedEvent() {
        super.fireValueChangedEvent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        descriptor.removeListener(listener);

    }

}
