package io.github.vinccool96.observations.beans.property.adapter;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.property.StringProperty;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.sun.binding.ExpressionHelper;
import io.github.vinccool96.observations.sun.property.adapter.Disposer;
import io.github.vinccool96.observations.sun.property.adapter.PropertyDescriptor;
import io.github.vinccool96.observations.util.ArrayUtils;
import sun.reflect.misc.MethodUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A {@code JavaBeanStringProperty} provides an adapter between a regular Java Bean property of type {@code String} and
 * a JavaFX {@code StringProperty}. It cannot be created directly, but a {@link JavaBeanStringPropertyBuilder} has to be
 * used.
 * <p>
 * As a minimum, the Java Bean must implement a getter and a setter for the property. If the getter of an instance of
 * this class is called, the property of the Java Bean is returned. If the setter is called, the value will be passed to
 * the Java Bean property. If the Java Bean property is bound (i.e. it supports PropertyChangeListeners), this {@code
 * JavaBeanStringProperty} will be aware of changes in the Java Bean. Otherwise it can be notified about changes by
 * calling {@link #fireValueChangedEvent()}. If the Java Bean property is also constrained (i.e. it supports
 * VetoableChangeListeners), this {@code JavaBeanStringProperty} will reject changes, if it is bound to an {@link
 * ObservableValue ObservableValue&lt;String&gt;}.
 *
 * @see StringProperty
 * @see JavaBeanStringPropertyBuilder
 */
public final class JavaBeanStringProperty extends StringProperty implements JavaBeanProperty<String> {

    private final PropertyDescriptor descriptor;

    private final PropertyDescriptor.Listener<String> listener;

    private ObservableValue<? extends String> observable = null;

    private ExpressionHelper<String> helper = null;

    private final AccessControlContext acc = AccessController.getContext();

    JavaBeanStringProperty(PropertyDescriptor descriptor, Object bean) {
        this.descriptor = descriptor;
        this.listener = descriptor.new Listener<String>(bean, this);
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
    public String get() {
        return AccessController.doPrivileged((PrivilegedAction<String>) () -> {
            try {
                return (String) MethodUtil.invoke(descriptor.getGetter(), getBean(), (Object[]) null);
            } catch (IllegalAccessException e) {
                throw new UndeclaredThrowableException(e);
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e);
            }
        }, acc);
    }

    /**
     * {@inheritDoc}
     *
     * @throws UndeclaredThrowableException
     *         if calling the getter of the Java Bean property throws an {@code IllegalAccessException} or an {@code
     *         InvocationTargetException}.
     */
    @Override
    public void set(final String value) {
        if (isBound()) {
            throw new RuntimeException("A bound value cannot be set.");
        }
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            try {
                MethodUtil.invoke(descriptor.getSetter(), getBean(), new Object[]{value});
                ExpressionHelper.fireValueChangedEvent(helper);
            } catch (IllegalAccessException e) {
                throw new UndeclaredThrowableException(e);
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e);
            }
            return null;
        }, acc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(ObservableValue<? extends String> observable) {
        if (observable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        if (!observable.equals(this.observable)) {
            unbind();
            set(observable.getValue());
            this.observable = observable;
            this.observable.addListener(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound() {
        return observable != null;
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
    public void addListener(ChangeListener<? super String> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(ChangeListener<? super String> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super String> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(InvalidationListener listener) {
        if (helper == null || !isInvalidationListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        descriptor.removeListener(listener);

    }

}
