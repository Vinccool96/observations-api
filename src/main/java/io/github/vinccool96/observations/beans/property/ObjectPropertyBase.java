package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.sun.binding.ExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

import java.lang.ref.WeakReference;

/**
 * The class {@code ObjectPropertyBase} is the base class for a property wrapping an arbitrary {@code Object}.
 * <p>
 * It provides all the functionality required for a property except for the {@link #getBean()} and {@link #getName()}
 * methods, which must be implemented by extending classes.
 *
 * @param <T>
 *         the type of the wrapped value
 *
 * @see ObjectProperty
 */
public abstract class ObjectPropertyBase<T> extends ObjectProperty<T> {

    private T value;

    private ObservableValue<? extends T> observable = null;

    private InvalidationListener listener = null;

    private boolean valid = true;

    private ExpressionHelper<T> helper = null;

    /**
     * The constructor of the {@code ObjectPropertyBase}.
     */
    public ObjectPropertyBase() {
    }

    /**
     * The constructor of the {@code ObjectPropertyBase}.
     *
     * @param initialValue
     *         the initial value of the wrapped {@code Object}
     */
    public ObjectPropertyBase(T initialValue) {
        this.value = initialValue;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        if (helper == null || !isInvalidationListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super T> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    /**
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners} and {@link ChangeListener
     * ChangeListeners}.
     * <p>
     * This method is called when the value is changed, either manually by calling {@link #set} or in case of a bound
     * property, if the binding becomes invalid.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    /**
     * The method {@code invalidated()} can be overridden to receive invalidation notifications. This is the preferred
     * option in {@code Objects} defining the property, because it requires less memory.
     * <p>
     * The default implementation is empty.
     */
    protected void invalidated() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get() {
        valid = true;
        return observable == null ? value : observable.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(T newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") +
                    "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            markInvalid();
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
    public void bind(final ObservableValue<? extends T> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        if (!newObservable.equals(this.observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.getValue();
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * Returns a string representation of this {@code ObjectPropertyBase} object.
     *
     * @return a string representation of this {@code ObjectPropertyBase} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("ObjectProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(get());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener implements InvalidationListener {

        private final WeakReference<ObjectPropertyBase<?>> wref;

        public Listener(ObjectPropertyBase<?> ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            ObjectPropertyBase<?> ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }

    }

}
