package io.github.vinccool96.observations.beans.property.adapter;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.property.FloatProperty;
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
 * A {@code JavaBeanFloatProperty} provides an adapter between a regular Java Bean property of type {@code float} or
 * {@code Float} and a JavaFX {@code FloatProperty}. It cannot be created directly, but a {@link
 * JavaBeanFloatPropertyBuilder} has to be used.
 * <p>
 * As a minimum, the Java Bean must implement a getter and a setter for the property. If the getter of an instance of
 * this class is called, the property of the Java Bean is returned. If the setter is called, the value will be passed to
 * the Java Bean property. If the Java Bean property is bound (i.e. it supports PropertyChangeListeners), this {@code
 * JavaBeanFloatProperty} will be aware of changes in the Java Bean. Otherwise it can be notified about changes by
 * calling {@link #fireValueChangedEvent()}. If the Java Bean property is also constrained (i.e. it supports
 * VetoableChangeListeners), this {@code JavaBeanFloatProperty} will reject changes, if it is bound to an {@link
 * ObservableValue ObservableValue&lt;Float&gt;}.
 *
 * @see FloatProperty
 * @see JavaBeanFloatPropertyBuilder
 * @since JavaFX 2.1
 */
public final class JavaBeanFloatProperty extends FloatProperty implements JavaBeanProperty<Number> {

    private final PropertyDescriptor descriptor;

    private final PropertyDescriptor.Listener<Number> listener;

    private ObservableValue<? extends Number> observable = null;

    private ExpressionHelper<Number> helper = null;

    private final AccessControlContext acc = AccessController.getContext();

    JavaBeanFloatProperty(PropertyDescriptor descriptor, Object bean) {
        this.descriptor = descriptor;
        this.listener = descriptor.new Listener<Number>(bean, this);
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
    public float get() {
        return AccessController.doPrivileged((PrivilegedAction<Float>) () -> {
            try {
                return ((Number) MethodUtil.invoke(
                        descriptor.getGetter(), getBean(), (Object[]) null)).floatValue();
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
    public void set(final float value) {
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
    public void bind(ObservableValue<? extends Number> observable) {
        if (observable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        if (!observable.equals(this.observable)) {
            unbind();
            set(observable.getValue().floatValue());
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
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
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

    /**
     * Returns a string representation of this {@code JavaBeanFloatProperty} object.
     *
     * @return a string representation of this {@code JavaBeanFloatProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("FloatProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
        }
        result.append("value: ").append(get());
        result.append("]");
        return result.toString();
    }

}
