package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.collections.ObservableSet;
import io.github.vinccool96.observations.collections.SetChangeListener;
import io.github.vinccool96.observations.collections.SetChangeListener.Change;
import io.github.vinccool96.observations.sun.binding.SetExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

import java.lang.ref.WeakReference;

/**
 * The class {@code SetPropertyBase} is the base class for a property wrapping an {@link ObservableSet}.
 * <p>
 * It provides all the functionality required for a property except for the {@link #getBean()} and {@link #getName()}
 * methods, which must be implemented by extending classes.
 *
 * @param <E>
 *         the type of the {@code Set} elements
 *
 * @see ObservableSet
 * @see SetProperty
 */
public abstract class SetPropertyBase<E> extends SetProperty<E> {

    private ObservableSet<E> value;

    private ObservableValue<? extends ObservableSet<E>> observable = null;

    private InvalidationListener listener = null;

    private boolean valid = true;

    private SetExpressionHelper<E> helper = null;

    private final SetChangeListener<E> setChangeListener = change -> {
        invalidateProperties();
        invalidated();
        fireValueChangedEvent(change);
    };

    private SizeProperty size0;

    private EmptyProperty empty0;

    /**
     * The constructor of the {@code SetPropertyBase}.
     *
     * @param initialValue
     *         the initial value of the wrapped value
     */
    public SetPropertyBase(ObservableSet<E> initialValue) {
        this.value = initialValue;
        if (initialValue != null) {
            initialValue.addListener(setChangeListener);
        }
    }

    /**
     * The constructor of {@code SetPropertyBase}
     */
    public SetPropertyBase() {
    }

    @Override
    public ReadOnlyIntegerProperty sizeProperty() {
        if (size0 == null) {
            size0 = new SizeProperty();
        }
        return size0;
    }

    private class SizeProperty extends ReadOnlyIntegerPropertyBase {

        @Override
        public int get() {
            return size();
        }

        @Override
        public Object getBean() {
            return SetPropertyBase.this;
        }

        @Override
        public String getName() {
            return "size";
        }

        @Override
        protected void fireValueChangedEvent() {
            super.fireValueChangedEvent();
        }

    }

    @Override
    public ReadOnlyBooleanProperty emptyProperty() {
        if (empty0 == null) {
            empty0 = new EmptyProperty();
        }
        return empty0;
    }

    private class EmptyProperty extends ReadOnlyBooleanPropertyBase {

        @Override
        public boolean get() {
            return isEmpty();
        }

        @Override
        public Object getBean() {
            return SetPropertyBase.this;
        }

        @Override
        public String getName() {
            return "empty";
        }

        @Override
        protected void fireValueChangedEvent() {
            super.fireValueChangedEvent();
        }

    }

    @Override
    public void addListener(InvalidationListener listener) {
        if (!isInvalidationListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        if (isInvalidationListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableSet<E>> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableSet<E>> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableSet<E>> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        if (!isSetChangeListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
        if (isSetChangeListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isSetChangeListenerAlreadyAdded(SetChangeListener<? super E> listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getSetChangeListeners(), listener);
    }

    /**
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link SetChangeListener}.
     * <p>
     * This method is called when the value is changed, either manually by calling {@link #set(ObservableSet)} or in
     * case of a bound property, if the binding becomes invalid.
     */
    protected void fireValueChangedEvent() {
        SetExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link SetChangeListener}.
     * <p>
     * This method is called when the content of the list changes.
     *
     * @param change
     *         the change that needs to be propagated
     */
    protected void fireValueChangedEvent(Change<? extends E> change) {
        SetExpressionHelper.fireValueChangedEvent(helper, change);
    }

    private void invalidateProperties() {
        if (size0 != null) {
            size0.fireValueChangedEvent();
        }
        if (empty0 != null) {
            empty0.fireValueChangedEvent();
        }
    }

    private void markInvalid(ObservableSet<E> oldValue) {
        if (valid) {
            if (oldValue != null) {
                oldValue.removeListener(setChangeListener);
            }
            valid = false;
            invalidateProperties();
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

    @Override
    public ObservableSet<E> get() {
        if (!valid) {
            value = observable == null ? value : observable.getValue();
            valid = true;
            if (value != null) {
                value.addListener(setChangeListener);
            }
        }
        return value;
    }

    @Override
    public void set(ObservableSet<E> newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") +
                    "A bound value cannot be set.");
        }
        if (value != newValue) {
            final ObservableSet<E> oldValue = value;
            value = newValue;
            markInvalid(oldValue);
        }
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(final ObservableValue<? extends ObservableSet<E>> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        if (!newObservable.equals(this.observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener<>(this);
            }
            observable.addListener(listener);
            markInvalid(value);
        }
    }

    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.getValue();
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * Returns a string representation of this {@code SetPropertyBase} object.
     *
     * @return a string representation of this {@code SetPropertyBase} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("SetProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if (name != null && !name.equals("")) {
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

    private static class Listener<E> implements InvalidationListener {

        private final WeakReference<SetPropertyBase<E>> wref;

        public Listener(SetPropertyBase<E> ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            SetPropertyBase<E> ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid(ref.value);
            }
        }

    }

}
